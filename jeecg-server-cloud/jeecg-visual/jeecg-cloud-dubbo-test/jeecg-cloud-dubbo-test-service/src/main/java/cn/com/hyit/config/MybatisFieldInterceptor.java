package cn.com.hyit.config;

import cn.com.hyit.config.constant.CommonConstant;
import cn.com.hyit.config.util.oConvertUtils;
import cn.com.hyit.entity.system.SysUser;
import cn.hutool.core.util.ReflectUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.binding.MapperMethod.ParamMap;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Properties;

/**
 * mybatis拦截器，自动注入创建人、创建时间、修改人、修改时间、软删
 */
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
					if ("createdBy".equals(field.getName()) || "createBy".equals(field.getName())) {
						Object localCreateBy = ReflectUtil.getFieldValue(parameter, field);
						if (localCreateBy == null || "".equals(localCreateBy)) {
							if (sysUser != null) {
								// 登录人账号
								ReflectUtil.setFieldValue(parameter, field, sysUser.getUsername());
							}
						}
					}
					// 注入创建时间
					if ("createdTime".equals(field.getName()) || "createTime".equals(field.getName())) {
						Object localCreateDate = ReflectUtil.getFieldValue(parameter, field);
						if (localCreateDate == null || "".equals(localCreateDate)) {
							ReflectUtil.setFieldValue(parameter, field, new Date());
						}
					}
					// 注入部门编码
					if ("sysOrgCode".equals(field.getName())) {
						Object localSysOrgCode = ReflectUtil.getFieldValue(parameter, field);
						if (localSysOrgCode == null || "".equals(localSysOrgCode)) {
							// 获取登录用户信息
							if (sysUser != null) {
								ReflectUtil.setFieldValue(parameter, field, sysUser.getOrgCode());
							}
						}
					}
					// 注入软删
					if ("delFlag".equals(field.getName())) {
						Object delFlag = ReflectUtil.getFieldValue(parameter, field);
						if (delFlag == null) {
							// 设置正常状态
							ReflectUtil.setFieldValue(parameter, field, CommonConstant.DEL_FLAG_0);
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
					if ("updatedBy".equals(field.getName()) || "updateBy".equals(field.getName())) {
						//获取登录用户信息
						if (sysUser != null) {
							// 登录账号
							ReflectUtil.setFieldValue(parameter, field, sysUser.getUsername());
						}
					}
					if ("updatedTime".equals(field.getName()) || "updateTime".equals(field.getName())) {
						ReflectUtil.setFieldValue(parameter, field, new Date());
					}
				} catch (Exception e) {
					log.error(e.getMessage(), e);
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
