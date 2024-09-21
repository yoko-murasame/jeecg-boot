package org.jeecg.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.api.CommonAPI;
import org.jeecg.common.constant.CacheConstant;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.constant.TenantConstant;
import org.jeecg.common.desensitization.util.SensitiveInfoUtil;
import org.jeecg.common.exception.JeecgBoot401Exception;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.system.vo.LoginUser;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * @Author scott
 * @Date 2019/9/23 14:12
 * @Description: 编程校验token有效性
 */
@Slf4j
public class TokenUtils {

    /**
     * 获取 request 里传递的 token
     *
     * @param request
     * @return
     */
    public static String getTokenByRequest(HttpServletRequest request) {
        String token = request.getParameter(CommonConstant.REQUEST_PARAM_NAME_FOR_TOKEN);
        if (token == null) {
            token = request.getHeader(CommonConstant.X_ACCESS_TOKEN);
        }
        if (StringUtils.isBlank(token)) {
            token = (String) request.getAttribute(CommonConstant.REQUEST_ATTRIBUTE_NAME_FOR_TOKEN);
        }
        return token;
    }

    /**
     * 获取 request 里传递的 token
     * @return
     */
    public static String getTokenByRequest() {
        String token = null;
        try {
            HttpServletRequest request = SpringContextUtils.getHttpServletRequest();
            token = TokenUtils.getTokenByRequest(request);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return token;
    }

    /**
     * 获取 request 里传递的 tenantId (租户ID)
     *
     * @param request
     * @return
     */
    public static String getTenantIdByRequest(HttpServletRequest request) {
        String tenantId = request.getParameter(TenantConstant.TENANT_ID);
        if (tenantId == null) {
            tenantId = oConvertUtils.getString(request.getHeader(CommonConstant.TENANT_ID));
        }
        return tenantId;
    }

    /**
     * 获取 request 里传递的 lowAppId (低代码应用ID)
     *
     * @param request
     * @return
     */
    public static String getLowAppIdByRequest(HttpServletRequest request) {
        String lowAppId = request.getParameter(TenantConstant.FIELD_LOW_APP_ID);
        if (lowAppId == null) {
            lowAppId = oConvertUtils.getString(request.getHeader(TenantConstant.X_LOW_APP_ID));
        }
        return lowAppId;
    }

    /**
     * 验证Token
     */
    public static boolean verifyToken(HttpServletRequest request, CommonAPI commonApi, RedisUtil redisUtil) {
        log.debug(" -- url --" + request.getRequestURL());
        String token = getTokenByRequest(request);
        return TokenUtils.verifyToken(token, commonApi, redisUtil);
    }

    /**
     * 验证Token
     */
    public static boolean verifyToken(String token, CommonAPI commonApi, RedisUtil redisUtil) {
        if (StringUtils.isBlank(token)) {
            throw new JeecgBoot401Exception("token不能为空!");
        }

        // 解密获得username，用于和数据库进行对比
        String username = JwtUtil.getUsername(token);
        if (username == null) {
            throw new JeecgBoot401Exception("token非法无效!");
        }

        // 查询用户信息
        LoginUser user = TokenUtils.getLoginUser(username, commonApi, redisUtil);
        //LoginUser user = commonApi.getUserByName(username);
        if (user == null) {
            throw new JeecgBoot401Exception("用户不存在!");
        }
        // 判断用户状态
        if (user.getStatus() != 1) {
            throw new JeecgBoot401Exception("账号已被锁定,请联系管理员!");
        }
        // 校验token是否超时失效 & 或者账号密码是否错误
        if (!jwtTokenRefresh(token, username, user.getPassword(), redisUtil)) {
            throw new JeecgBoot401Exception(CommonConstant.TOKEN_IS_INVALID_MSG);
        }
        return true;
    }

    /**
     * 刷新token（保证用户在线操作不掉线）
     * @param token
     * @param userName
     * @param passWord
     * @param redisUtil
     * @return
     */
    private static boolean jwtTokenRefresh(String token, String userName, String passWord, RedisUtil redisUtil) {
        String cacheToken = oConvertUtils.getString(redisUtil.get(CommonConstant.PREFIX_USER_TOKEN + token));
        if (oConvertUtils.isNotEmpty(cacheToken)) {
            // 校验token有效性
            if (!JwtUtil.verify(cacheToken, userName, passWord)) {
                String newAuthorization = JwtUtil.sign(userName, passWord);
                // 设置Toekn缓存有效时间
                redisUtil.set(CommonConstant.PREFIX_USER_TOKEN + token, newAuthorization);
                redisUtil.expire(CommonConstant.PREFIX_USER_TOKEN + token, JwtUtil.EXPIRE_TIME * 2 / 1000);
            }
            return true;
        }
        return false;
    }

    /**
     * 获取登录用户
     *
     * @param commonApi
     * @param username
     * @return
     */
    public static LoginUser getLoginUser(String username, CommonAPI commonApi, RedisUtil redisUtil) {
        LoginUser loginUser = null;
        String loginUserKey = CacheConstant.SYS_USERS_CACHE + "::" + username;
        //【重要】此处通过redis原生获取缓存用户，是为了解决微服务下system服务挂了，其他服务互调不通问题---
        if (redisUtil.hasKey(loginUserKey)) {
            try {
                loginUser = (LoginUser) redisUtil.get(loginUserKey);
                //解密用户
                SensitiveInfoUtil.handlerObject(loginUser, false);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            // 查询用户信息
            loginUser = commonApi.getUserByName(username);
        }
        return loginUser;
    }

    /**
     * 开启临时令牌
     *
     * @author Yoko
     * @since 2024/8/6 下午5:24
     * @param redisUtil reids操作类
     * @param tempTokenConsumer 临时token具体使用实现函数
     * @param time token失效时间（单位：秒）
     * @param username 指定token的用户名
     */
    public static void doSomethingWithTempToken(RedisUtil redisUtil, Consumer<String> tempTokenConsumer, long time, String username) {
        String uuid = UUID.randomUUID().toString() + System.currentTimeMillis();
        String token = JWT.create().withClaim("username", StringUtils.isNotBlank(username) ? username : uuid).sign(Algorithm.HMAC256(uuid));
        String tempTokenKey = CommonConstant.PREFIX_SSO_TEMP_TOKEN + token;
        LoginUser tempUser = new LoginUser();
        tempUser.setId(uuid);
        tempUser.setStatus(1);
        tempUser.setUsername(StringUtils.isNotBlank(username) ? username : uuid);
        tempUser.setPassword(uuid);
        String loginUserKey = CacheConstant.SYS_USERS_CACHE + "::" + uuid;
        // 临时token开始
        redisUtil.set(tempTokenKey, token, time);
        redisUtil.set(loginUserKey, tempUser, time);
        // 在临时token中去调用特殊业务
        tempTokenConsumer.accept(token);
        // 临时token结束
        redisUtil.removeAll(tempTokenKey);
        redisUtil.removeAll(loginUserKey);
    }

    public static void doSomethingWithTempToken(RedisUtil redisUtil, Consumer<String> tempTokenConsumer, long time) {
        doSomethingWithTempToken(redisUtil, tempTokenConsumer, time, null);
    }

    public static void doSomethingWithTempToken(RedisUtil redisUtil, Consumer<String> tempTokenConsumer, String username) {
        doSomethingWithTempToken(redisUtil, tempTokenConsumer, 60, username);
    }

    public static void doSomethingWithTempToken(RedisUtil redisUtil, Consumer<String> tempTokenConsumer) {
        doSomethingWithTempToken(redisUtil, tempTokenConsumer, 60, null);
    }

}
