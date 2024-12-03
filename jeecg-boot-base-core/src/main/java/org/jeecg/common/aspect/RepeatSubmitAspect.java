package org.jeecg.common.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.RepeatSubmit;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.util.Md5Util;
import org.jeecg.common.util.RedisUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * AOP防重复提交
 *
 * @author Yoko
 * @since 2024/12/3 15:25
 */
@Aspect
@Slf4j
@Component
public class RepeatSubmitAspect {

    @Resource
    private RedisUtil redisUtil;

    @Around("@annotation(repeatSubmit)")
    public Object around(ProceedingJoinPoint joinPoint, RepeatSubmit repeatSubmit) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String serviceId = repeatSubmit.serviceId();
        boolean res = false;

        // 基于方法参数的防重提交
        String key;
        if (repeatSubmit.limitType() == RepeatSubmit.Type.PARAM) {
            String ipAddr = request.getRemoteAddr();
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            Method method = methodSignature.getMethod();
            key = serviceId + ":repeat_submit_params:" + Md5Util.md5Encode(ipAddr + method.getName(), StandardCharsets.UTF_8.name());
        } else {
            // 基于Token的防重提交
            String requestToken = request.getHeader(CommonConstant.X_ACCESS_TOKEN);
            key = serviceId + ":repeat_submit_token:" + requestToken;
        }
        res = redisUtil.tryLock(key, "", repeatSubmit.lockTime());

        if (!res) {
            // log.error("请求重复提交");
            // return null;
            return Result.error("请勿重复提交，请稍后再试！");
        }

        return joinPoint.proceed();
    }
}
