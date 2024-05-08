package cn.com.hyit.config;

import cn.com.hyit.config.constant.CommonConstant;
import cn.com.hyit.config.util.oConvertUtils;
import cn.com.hyit.entity.system.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.binding.MapperMethod.ParamMap;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.jeecg.config.JeecgDubboCondition;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Properties;

/**
 * mybatis拦截器，自动注入创建人、创建时间、修改人、修改时间、软删
 */
@Conditional(JeecgDubboCondition.class) // todo 去掉
@Slf4j
@Component
@Intercepts({ @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }) })
public class MybatisFieldInterceptor implements Interceptor {

	public MybatisFieldInterceptor() {
		log.info("==========加载 MybatisFieldInterceptor ===========");
	}

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
		String sqlId = mappedStatement.getId();
		log.debug("------sqlId------" + sqlId);
		SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
		Object parameter = invocation.getArgs()[1];
		log.debug("------sqlCommandType------" + sqlCommandType);

		if (parameter == null) {
			return invocation.proceed();
		}
		if (SqlCommandType.INSERT == sqlCommandType) {
			SysUser sysUser = this.getLoginUser();
			Field[] fields = oConvertUtils.getAllFields(parameter);
			for (Field field : fields) {
				log.debug("------field.name------" + field.getName());
				try {
					if ("createdBy".equals(field.getName())) {
						field.setAccessible(true);
						Object localCreateBy = field.get(parameter);
						field.setAccessible(false);
						if (localCreateBy == null || "".equals(localCreateBy)) {
							if (sysUser != null) {
								// 登录人账号
								field.setAccessible(true);
								field.set(parameter, sysUser.getUsername());
								field.setAccessible(false);
							}
						}
					}
					// 注入创建时间
					if ("createdTime".equals(field.getName())) {
						field.setAccessible(true);
						Object localCreateDate = field.get(parameter);
						field.setAccessible(false);
						if (localCreateDate == null || "".equals(localCreateDate)) {
							field.setAccessible(true);
							field.set(parameter, new Date());
							field.setAccessible(false);
						}
					}
					// 注入部门编码
					if ("sysOrgCode".equals(field.getName())) {
						field.setAccessible(true);
						Object localSysOrgCode = field.get(parameter);
						field.setAccessible(false);
						if (localSysOrgCode == null || "".equals(localSysOrgCode)) {
							// 获取登录用户信息
							if (sysUser != null) {
								field.setAccessible(true);
								field.set(parameter, sysUser.getOrgCode());
								field.setAccessible(false);
							}
						}
					}
					// 注入软删
					if ("delFlag".equals(field.getName())) {
						field.setAccessible(true);
						Object delFlag = field.get(parameter);
						field.setAccessible(false);
						if (delFlag == null) {
							// 设置正常状态
							field.setAccessible(true);
							field.set(parameter, CommonConstant.DEL_FLAG_0);
							field.setAccessible(false);
						}
					}
				} catch (Exception ignore) {
				}
			}
		}
		if (SqlCommandType.UPDATE == sqlCommandType) {
			SysUser sysUser = this.getLoginUser();
			Field[] fields = null;
			if (parameter instanceof ParamMap) {
				ParamMap<?> p = (ParamMap<?>) parameter;
                String et = "et";
				if (p.containsKey(et)) {
					parameter = p.get(et);
				} else {
					parameter = p.get("param1");
				}

				if (parameter == null) {
					return invocation.proceed();
				}
				fields = oConvertUtils.getAllFields(parameter);
			} else {
				fields = oConvertUtils.getAllFields(parameter);
			}

			for (Field field : fields) {
				log.debug("------field.name------" + field.getName());
				try {
					if ("updatedBy".equals(field.getName())) {
						//获取登录用户信息
						if (sysUser != null) {
							// 登录账号
							field.setAccessible(true);
							field.set(parameter, sysUser.getUsername());
							field.setAccessible(false);
						}
					}
					if ("updatedTime".equals(field.getName())) {
						field.setAccessible(true);
						field.set(parameter, new Date());
						field.setAccessible(false);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
		// TODO Auto-generated method stub
	}

    /**
     * TODO 获取登录用户
     * @return
     */
	private SysUser getLoginUser() {
		SysUser sysUser = null;
		try {
			// todo 从接口获取
			sysUser = new SysUser();
			sysUser.setId("admin");
			sysUser.setUsername("admin");
			sysUser.setOrgCode("A01");
		} catch (Exception e) {
			sysUser = null;
		}
		return sysUser;
	}

}
