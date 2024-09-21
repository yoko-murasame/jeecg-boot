package cn.com.hyit.audit.core.logger.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 角色枚举
 * FIXME 仅供参考，实际项目中应该使用hyit-core包中的RoleEnum
 */
@Getter
@AllArgsConstructor
public enum RoleEnum {

    SYSTEM_ADMIN("system_admin", "系统管理员"),
    SYSTEM_AUDITOR("system_auditor", "系统审计员"),
    SYSTEM_EXAMINE("system_examine", "系统审核员"),
    BUSINESS_ADMIN("business_admin", "业务管理员"),
    BUSINESS_CONFIGURATOR("business_configurator", "业务配置员"),
    BUSINESS_AUDITOR("business_auditor", "业务审计员"),
    BUSINESS_EXAMINE("business_examine", "业务审核员"),
    GENERAL("general", "普通角色");

    private final String code;
    private final String name;

}
