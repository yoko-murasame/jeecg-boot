package org.jeecg.modules.system.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;

@Slf4j
public class ShiroUtil {

    public static String getLoginUsername() {
        String username = null;
        try {
            username = ((LoginUser) SecurityUtils.getSubject().getPrincipal()).getUsername();
        } catch (Exception e) {
            log.debug("ShiroUtil无法获取用户信息");
        }
        return username;
    }

    public static String getLoginRealname() {
        String realname = null;
        try {
            realname = ((LoginUser) SecurityUtils.getSubject().getPrincipal()).getRealname();
        } catch (Exception e) {
            log.debug("ShiroUtil无法获取用户信息");
        }
        return realname;
    }

    public static String getLoginUserId() {
        String userid = null;
        try {
            userid = ((LoginUser) SecurityUtils.getSubject().getPrincipal()).getId();
        } catch (Exception e) {
            log.debug("ShiroUtil无法获取用户信息");
        }
        return userid;
    }

    public static String getLoginAvatar() {
        String avatar = null;
        try {
            avatar = ((LoginUser) SecurityUtils.getSubject().getPrincipal()).getAvatar();
        } catch (Exception e) {
            log.debug("ShiroUtil无法获取用户信息");
        }
        return avatar;
    }
}
