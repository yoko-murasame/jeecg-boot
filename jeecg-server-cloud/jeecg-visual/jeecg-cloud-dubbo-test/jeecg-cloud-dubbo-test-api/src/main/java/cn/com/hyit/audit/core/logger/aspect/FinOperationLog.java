package cn.com.hyit.audit.core.logger.aspect;

import cn.com.hyit.audit.core.logger.enums.LogLevelEnum;
import cn.com.hyit.audit.core.logger.enums.LogTypeEnum;
import cn.com.hyit.audit.core.logger.enums.OperTypeEnum;
import cn.com.hyit.audit.core.logger.enums.RoleEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 日志注解
 * FIXME 仅供参考，实际项目中应该使用hyit-core包中的FinOperationLog
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FinOperationLog {

    OperTypeEnum type();

    String value();

    LogTypeEnum logType() default LogTypeEnum.SYSTEM;

    LogLevelEnum logLevel() default LogLevelEnum.NORMAL;

    String operModule() default "";

    RoleEnum role() default RoleEnum.GENERAL;

    boolean queryLogs() default false;

}
