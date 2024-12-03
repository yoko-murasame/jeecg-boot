package org.jeecg.common.aspect.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 接口限流注解
 * @author YOKO
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AccessLimit {

    /**
     * 限流时间窗口，默认10秒。
     */
    int seconds() default 10;

    /**
     * 在时间窗口内允许的最大请求次数，默认5次。
     */
    int maxCount() default 5;

    /**
     * 当超过请求限制时返回的提示信息。
     */
    String msg() default "您操作频率太过频繁，稍后再试";

}
