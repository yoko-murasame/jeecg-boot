package org.jeecg.common.aspect.annotation;

import org.jeecg.common.constant.enums.TransDictType;

import java.lang.annotation.*;

/**
 * 需要自动翻译字典的注解
 * @author yoko
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AutoTransDict {

	/**
	 * 类型
	 *
	 * Page
	 * List
	 * Object
	 *
	 * @return
	 */
	TransDictType type() default TransDictType.OBJECT;

	/**
	 * 装载反转值
	 *
	 */
	boolean reverseValue() default false;
}
