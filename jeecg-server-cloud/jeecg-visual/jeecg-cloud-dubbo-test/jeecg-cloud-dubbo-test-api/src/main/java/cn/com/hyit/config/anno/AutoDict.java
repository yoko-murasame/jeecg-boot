package cn.com.hyit.config.anno;

import java.lang.annotation.*;

/**
 * 通过此注解声明的接口，自动实现字典翻译
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AutoDict {

	/**
	 * 暂时无用
	 * @return
	 */
	String value() default "";

}
