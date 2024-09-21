package cn.com.hyit.audit.core.logger.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 日志等级枚举
 * FIXME 仅供参考，实际项目中应该使用hyit-core包中的LogLevelEnum
 */
@Getter
@AllArgsConstructor
public enum LogLevelEnum {

    NORMAL("normal"),
    LOW_RISK("lowRisk"),
    MIDDLE_RISK("middleRisk"),
    HIGH_RISK("highRisk");

    private final String level;

}
