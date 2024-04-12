package org.jeecg.modules.core.logger.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 操作类型枚举
 * FIXME 仅供参考，实际项目中应该使用hyit-core包中的OperTypeEnum
 */
@Getter
@AllArgsConstructor
public enum OperTypeEnum {

    ADD("add"),
    DELETE("delete"),
    UPDATE("update"),
    QUERY("query"),
    IMPORT("import"),
    EXPORT("export"),
    UPLOAD("upload"),
    DOWNLOAD("download"),
    OTHER("other"),
    START("start"),
    STOP("stop"),
    AUDIT_QUERY("auditQuery"),
    LOG_BACKUP("logBackup"),
    LOG_STATISTICS("LogStatistics"),
    LOG_CONFIGURATION("logConfiguration"),
    SORT("sort"),
    UNAUTHORIZED("unauthorized");

    private final String type;

}
