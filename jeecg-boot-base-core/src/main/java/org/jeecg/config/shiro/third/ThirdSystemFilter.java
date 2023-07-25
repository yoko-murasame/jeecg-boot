package org.jeecg.config.shiro.third;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.jeecg.common.config.TenantContext;
import org.jeecg.common.constant.CommonConstant;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 第三方系统权限过滤器
 *
 * @author Yoko
 * @since 2023/7/25 14:19
 */
@Slf4j
public class ThirdSystemFilter extends BasicHttpAuthenticationFilter {

    @Setter
    private boolean allowOrigin = true;

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) {
        try {
            executeLogin(servletRequest, servletResponse);
            return true;
        } catch (Exception e) {
            throw new AuthenticationException("无权限访问", e);
        }
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String systemName = httpServletRequest.getHeader(ThirdSystemConfig.THIRD_SYSTEM_NAME);
        String systemKey = httpServletRequest.getHeader(ThirdSystemConfig.THIRD_SYSTEM_KEY);
        ThirdSystemProperty property = new ThirdSystemProperty();
        property.setName(systemName);
        property.setKey(systemKey);
        // 提交给realm进行登入，如果错误他会抛出异常并被捕获
        Subject subject = getSubject(request, response);
        subject.login(new ThirdSystemToken(property));
        // 如果没有抛出异常则代表登入成功，返回true
        return true;
    }

    /**
     * 对跨域提供支持
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        if(allowOrigin){
            httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
            httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
            httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
            httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
        }
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        String tenant_id = httpServletRequest.getHeader(CommonConstant.TENANT_ID);
        TenantContext.setTenant(tenant_id);
        return super.preHandle(request, response);
    }

}
