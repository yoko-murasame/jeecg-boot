package org.jeecg.common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.jeecg.common.aspect.annotation.AntiShake;
import org.springframework.stereotype.Component;

/**
 * AOP接口防抖
 *
 * @author Yoko
 * @since 2024/12/3 15:52
 */
@Aspect
@Component
public class AntiShakeAspect {
    private final ThreadLocal<Long> lastInvokeTime = new ThreadLocal<>();

    @Around("@annotation(antiShake)")
    public Object aroundAdvice(ProceedingJoinPoint joinPoint, AntiShake antiShake) throws Throwable {
        long currentTime = System.currentTimeMillis();
        long lastTime = lastInvokeTime.get() != null ? lastInvokeTime.get() : 0;

        if (currentTime - lastTime < antiShake.value()) {
            return null;  // 请求过于频繁，拒绝处理
        }

        lastInvokeTime.set(currentTime);
        return joinPoint.proceed();
    }
}
