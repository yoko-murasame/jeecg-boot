package org.jeecg.common.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.jeecg.common.api.CommonAPI;
import org.jeecg.common.aspect.annotation.PermissionData;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.constant.SymbolConstant;
import org.jeecg.common.system.query.QueryRuleEnum;
import org.jeecg.common.system.util.JeecgDataAutorUtils;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.system.vo.SysPermissionDataRuleModel;
import org.jeecg.common.system.vo.SysUserCacheInfo;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.common.util.oConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 数据权限切面处理类
 *  当被请求的方法有注解PermissionData时,会在往当前request中写入数据权限信息
 * @Date 2019年4月10日
 * @Version: 1.0
 * @author: jeecg-boot
 */
@Aspect
@Component
@Slf4j
public class PermissionDataAspect {
    @Lazy
    @Autowired
    private CommonAPI commonApi;

    private static final String SPOT_DO = ".do";

    @Pointcut("@annotation(org.jeecg.common.aspect.annotation.PermissionData)")
    public void pointCut() {

    }

    @Around("pointCut()")
    public Object arround(ProceedingJoinPoint point) throws  Throwable{
        HttpServletRequest request = SpringContextUtils.getHttpServletRequest();
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        PermissionData pd = method.getAnnotation(PermissionData.class);
        installDataRuleFromComponent(request, pd.pageComponent());
        installDataRuleFromPerms(request, pd);
        return point.proceed();
    }

    /**
     * 从component参数注入数据规则
     *
     * @author Yoko
     * @since 2024/8/13 下午3:39
     * @param request 请求上下文
     * @param component 组件名称
     */
    private void installDataRuleFromComponent(HttpServletRequest request, String component) {
        if (!StringUtils.hasText(component)) {
            return;
        }
        String requestMethod = request.getMethod();
        String requestPath = request.getRequestURI().substring(request.getContextPath().length());
        requestPath = filterUrl(requestPath);
        //update-begin-author:taoyan date:20211027 for:JTC-132【online报表权限】online报表带参数的菜单配置数据权限无效
        //先判断是否online报表请求
        // TODO 参数顺序调整有隐患
        if(requestPath.contains(UrlMatchEnum.CGREPORT_DATA.getMatchUrl())){
            // 获取地址栏参数
            String urlParamString = request.getParameter(CommonConstant.ONL_REP_URL_PARAM_STR);
            if(oConvertUtils.isNotEmpty(urlParamString)){
                requestPath+="?"+urlParamString;
            }
        }
        //update-end-author:taoyan date:20211027 for:JTC-132【online报表权限】online报表带参数的菜单配置数据权限无效
        log.info("component数据权限：拦截请求 >> {} ; 请求类型 >> {} . ", requestPath, requestMethod);
        String username = JwtUtil.getUserNameByToken(request);
        //查询数据权限信息
        //TODO 微服务情况下也得支持缓存机制
        List<SysPermissionDataRuleModel> dataRules = commonApi.queryPermissionDataRule(component, requestPath, username);
        if(dataRules!=null && !dataRules.isEmpty()) {
            //临时存储
            JeecgDataAutorUtils.installDataSearchConditon(request, dataRules);
            //TODO 微服务情况下也得支持缓存机制
            SysUserCacheInfo userinfo = commonApi.getCacheUser(username);
            JeecgDataAutorUtils.installUserInfo(request, userinfo);
        }
    }

    /**
     * 从perms参数注入数据规则
     *
     * @author Yoko
     * @since 2024/8/13 下午3:39
     * @param request 请求上下文
     * @param pd 权限注解
     */
    private void installDataRuleFromPerms(HttpServletRequest request, PermissionData pd) {
        String perms = pd.perms();
        boolean autoPermsByCurrentUser = pd.autoPermsByCurrentUser();
        if (!StringUtils.hasText(perms) && !autoPermsByCurrentUser) {
            return;
        }
        String requestMethod = request.getMethod();
        String requestPath = request.getRequestURI().substring(request.getContextPath().length());
        requestPath = filterUrl(requestPath);
        //update-begin-author:taoyan date:20211027 for:JTC-132【online报表权限】online报表带参数的菜单配置数据权限无效
        //先判断是否online报表请求
        // TODO 参数顺序调整有隐患
        if(requestPath.contains(UrlMatchEnum.CGREPORT_DATA.getMatchUrl())){
            // 获取地址栏参数
            String urlParamString = request.getParameter(CommonConstant.ONL_REP_URL_PARAM_STR);
            if(oConvertUtils.isNotEmpty(urlParamString)){
                requestPath+="?"+urlParamString;
            }
        }
        //update-end-author:taoyan date:20211027 for:JTC-132【online报表权限】online报表带参数的菜单配置数据权限无效
        log.info("perms数据权限：拦截请求 >> {} ; 请求类型 >> {} . ", requestPath, requestMethod);
        String username = JwtUtil.getUserNameByToken(request);
        //查询数据权限信息
        // 获取当前用户的所有权限标识
        if (autoPermsByCurrentUser) {
            Set<String> newPerms = Arrays.stream(Optional.ofNullable(perms).orElse("").split(",")).collect(Collectors.toSet());
            List<String> currentUserPerms = commonApi.queryCurrentUserPerms(username, null, pd.autoPermsLimitPrefix());
            newPerms.addAll(currentUserPerms);
            perms = newPerms.stream().filter(StringUtils::hasText).collect(Collectors.joining(","));
        }

        //TODO 微服务情况下也得支持缓存机制
        List<SysPermissionDataRuleModel> dataRules = commonApi.queryPermissionDataRuleByPerms(perms, username, QueryRuleEnum.IN);

        if(dataRules!=null && !dataRules.isEmpty()) {
            //临时存储
            JeecgDataAutorUtils.installDataSearchConditon(request, dataRules);
            //TODO 微服务情况下也得支持缓存机制
            SysUserCacheInfo userinfo = commonApi.getCacheUser(username);
            JeecgDataAutorUtils.installUserInfo(request, userinfo);
        }
    }

    private String filterUrl(String requestPath){
        String url = "";
        if(oConvertUtils.isNotEmpty(requestPath)){
            url = requestPath.replace("\\", "/");
            url = url.replace("//", "/");
            if(url.indexOf(SymbolConstant.DOUBLE_SLASH)>=0){
                url = filterUrl(url);
            }
			/*if(url.startsWith("/")){
				url=url.substring(1);
			}*/
        }
        return url;
    }

    /**
     * 获取请求地址
     * @param request
     * @return
     */
    @Deprecated
    private String getJgAuthRequsetPath(HttpServletRequest request) {
        String queryString = request.getQueryString();
        String requestPath = request.getRequestURI();
        if(oConvertUtils.isNotEmpty(queryString)){
            requestPath += "?" + queryString;
        }
        // 去掉其他参数(保留一个参数) 例如：loginController.do?login
        if (requestPath.indexOf(SymbolConstant.AND) > -1) {
            requestPath = requestPath.substring(0, requestPath.indexOf("&"));
        }
        if(requestPath.indexOf(QueryRuleEnum.EQ.getValue())!=-1){
            if(requestPath.indexOf(SPOT_DO)!=-1){
                requestPath = requestPath.substring(0,requestPath.indexOf(".do")+3);
            }else{
                requestPath = requestPath.substring(0,requestPath.indexOf("?"));
            }
        }
        // 去掉项目路径
        requestPath = requestPath.substring(request.getContextPath().length() + 1);
        return filterUrl(requestPath);
    }

    @Deprecated
    private boolean moHuContain(List<String> list,String key){
        for(String str : list){
            if(key.contains(str)){
                return true;
            }
        }
        return false;
    }


}
