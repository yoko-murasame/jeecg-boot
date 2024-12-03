package org.jeecg.common.aspect.annotation;


import java.lang.annotation.*;

/**
 * 接口放重复提交注解
 *
 * @author Yoko
 * @since 2024/12/3 15:01
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RepeatSubmit {

    enum Type { PARAM, TOKEN }

    /**
     * 防重提交的方式（基于参数或令牌）。
     */
    Type limitType() default Type.PARAM;

    /**
     * 防重提交的时间窗口，单位秒。
     */
    long lockTime() default 5;

    /**
     * 用于标识不同的业务逻辑。
     */
    String serviceId() default "repeat_submit";

}
