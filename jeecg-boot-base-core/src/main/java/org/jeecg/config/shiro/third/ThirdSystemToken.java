package org.jeecg.config.shiro.third;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * 第三方系统简单配置型认证token
 */
public class ThirdSystemToken implements AuthenticationToken {
    private static final long serialVersionUID = 1L;
    private final ThirdSystemProperty property;

    public ThirdSystemToken(ThirdSystemProperty property) {
        this.property = property;
    }

    @Override
    public Object getPrincipal() {
        return property;
    }

    @Override
    public Object getCredentials() {
        return property;
    }
}
