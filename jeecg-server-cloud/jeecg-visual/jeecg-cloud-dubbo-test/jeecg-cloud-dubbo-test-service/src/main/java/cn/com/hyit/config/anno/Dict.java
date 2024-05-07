package cn.com.hyit.config.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字典注解
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Dict {

    /**
     * 方法描述:  数据code
     */
    String dicCode();

    /**
     * 方法描述:  数据Text
     */
    String dicText() default "";

    /**
     * 方法描述: 数据字典表
     */
    String dictTable() default "";

}
