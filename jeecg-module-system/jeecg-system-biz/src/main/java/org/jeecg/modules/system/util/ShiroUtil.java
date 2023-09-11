package org.jeecg.modules.system.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;

@Slf4j
public class ShiroUtil {

    private static LoginUser loginUser;

    static {
        try {
            loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        } catch (Exception e) {
            log.debug("ShiroUtil无法获取用户信息");
        }
    }

    public static String getLoginUsername() {
        String username = null;
        try {
            username = loginUser.getUsername();
        } catch (Exception e) {
            log.debug("ShiroUtil无法获取用户信息");
        }
        return username;
    }

    public static String getLoginRealname() {
        String realname = null;
        try {
            realname = loginUser.getRealname();
        } catch (Exception e) {
            log.debug("ShiroUtil无法获取用户信息");
        }
        return realname;
    }

    public static String getLoginUserId() {
        String userid = null;
        try {
            userid = loginUser.getId();
        } catch (Exception e) {
            log.debug("ShiroUtil无法获取用户信息");
        }
        return userid;
    }

    public static String getLoginAvatar() {
        String avatar = null;
        try {
            avatar = loginUser.getAvatar();
        } catch (Exception e) {
            log.debug("ShiroUtil无法获取用户信息");
        }
        return avatar;
    }
}
