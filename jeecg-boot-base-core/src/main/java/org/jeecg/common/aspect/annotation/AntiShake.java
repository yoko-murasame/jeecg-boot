package org.jeecg.common.aspect.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 接口防抖注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AntiShake {

    /**
     * 默认1秒内不允许重复请求
     */
    long value() default 1000L;

}
