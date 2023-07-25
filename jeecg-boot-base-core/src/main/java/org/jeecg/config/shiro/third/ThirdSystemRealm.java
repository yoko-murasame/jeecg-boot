package org.jeecg.config.shiro.third;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.jeecg.common.system.vo.LoginUser;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 第三方系统简单配置型认证realm
 */
@Component
@Slf4j
public class ThirdSystemRealm extends AuthorizingRealm {

    @Setter
    private List<ThirdSystemProperty> thirdSystem;

    /**
     * 必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof ThirdSystemToken;
    }

    /**
     * 权限信息认证(包括角色以及权限)是用户访问controller的时候才进行验证
     *
     * @param principals 身份信息
     * @return AuthorizationInfo 权限信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        log.info("===============第三方系统权限直接放行============ [roles、permissions]==========");
        return new SimpleAuthorizationInfo();
    }

    /**
     * 用户信息认证是在用户进行登录的时候进行验证(不存redis)
     * 也就是说验证用户输入的账号和密码是否正确，错误抛出异常
     *
     * @param auth 用户登录的账号密码信息
     * @return 返回封装了用户信息的 AuthenticationInfo 实例
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        log.debug("===============Shiro第三方系统简单配置型认证开始============doGetAuthenticationInfo==========");
        ThirdSystemProperty credentials = (ThirdSystemProperty) auth.getCredentials();
        // filter配置的不可能为空了
        this.checkPermission(credentials);
        // 创建LoginUser，注入这个方便使用@AutoLog自动记录日志
        LoginUser loginUser = new LoginUser();
        loginUser.setUsername(credentials.getName());
        loginUser.setRealname(credentials.getName());
        loginUser.setPassword(credentials.getKey());
        return new SimpleAuthenticationInfo(loginUser, credentials, getName());
    }

    public void checkPermission(ThirdSystemProperty token) {
        String systemName = token.getName();
        String systemKey = token.getKey();
        for (ThirdSystemProperty property : thirdSystem) {
            if (property.getName().equals(systemName) && property.getKey().equals(systemKey)) {
                // 记录日志交给@AutoLog处理
                return;
            }
        }
        throw new AuthenticationException("无权限访问!");
    }

    /**
     * 清除当前用户的权限认证缓存
     *
     * @param principals 权限信息
     */
    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }

}
