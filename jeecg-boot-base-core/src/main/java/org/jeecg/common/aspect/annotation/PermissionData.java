package org.jeecg.common.aspect.annotation;

import java.lang.annotation.*;

/**
  *  数据权限注解
 * @Author taoyan
 * @Date 2019年4月11日
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
@Documented
public @interface PermissionData {
	/**
	 * 暂时没用
	 * @return
	 */
	String value() default "";


	/**
	 * 配置菜单的组件路径,用于数据权限
	 */
	String pageComponent() default "";

	/**
	 * 基于授权标识，管理查找数据权限，多个以逗号分隔
	 * 例如：
	 * 在菜单里新增两个授权标识：data:rule:test1,data:rule:test2
	 * 这里配置上，就会找这个标识下绑定的数据权限规则
	 */
	String perms() default "";

	/**
	 * 自动根据用户角色权限配置获取数据权限规则
	 * 例如：
	 * 在菜单里新增两个授权标识：data:rule:test1,data:rule:test2
	 * 在角色授权里配置上，就会找到当前用户绑定的数据权限规则
	 */
	boolean autoPermsByCurrentUser() default false;

	/**
	 * 自动根据用户角色权限配置获取数据权限规则，限制查询的前缀（因为系统有多个数据权限，可能很多不需要）
	 */
	String autoPermsLimitPrefix() default "";

}
