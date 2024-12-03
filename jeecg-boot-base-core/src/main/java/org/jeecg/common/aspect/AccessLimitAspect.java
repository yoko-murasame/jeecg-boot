package org.jeecg.common.aspect;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.jeecg.common.aspect.annotation.AccessLimit;
import org.aspectj.lang.reflect.MethodSignature;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.util.RedisUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * AOP限流
 * 使用在Controller方法上
 *
 * @author Yoko
 * @since 2024/12/3 14:50
 */
@Aspect
@Slf4j
@Component
public class AccessLimitAspect {

    @Resource
    private RedisUtil redisUtil;

    @Around("@annotation(accessLimit)")
    public Object around(ProceedingJoinPoint point, AccessLimit accessLimit) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = null;
        if (attributes != null) {
            response = attributes.getResponse();
        }
        if (attributes == null) return null;

        // 获取方法和类名
        MethodSignature signature = (MethodSignature) point.getSignature();
        String methodName = signature.getMethod().getName();
        String className = point.getTarget().getClass().getName();

        // 生成Redis中的限流key
        String key = className + ":" + methodName;
        int seconds = accessLimit.seconds();
        int maxCount = accessLimit.maxCount();

        // 检查请求次数
        if (!redisUtil.hasKey(key)) {
            redisUtil.expire(key, seconds);
        }
        long count = redisUtil.incr(key, 1);

        if (count > maxCount) {
            throw new JeecgBootException(accessLimit.msg());
        }

        return point.proceed();
    }
}
