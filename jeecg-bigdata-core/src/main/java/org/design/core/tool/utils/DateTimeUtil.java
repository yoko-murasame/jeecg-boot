package org.design.core.tool.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

/* loaded from: bigdata-core-tool-3.1.0.jar:org/design/core/tool/utils/DateTimeUtil.class */
public class DateTimeUtil {
    public static final DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern(DateUtil.PATTERN_DATETIME);
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern(DateUtil.PATTERN_DATE);
    public static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern(DateUtil.PATTERN_TIME);

    public static String formatDateTime(TemporalAccessor temporal) {
        return DATETIME_FORMAT.format(temporal);
    }

    public static String formatDate(TemporalAccessor temporal) {
        return DATE_FORMAT.format(temporal);
    }

    public static String formatTime(TemporalAccessor temporal) {
        return TIME_FORMAT.format(temporal);
    }

    public static String format(TemporalAccessor temporal, String pattern) {
        return DateTimeFormatter.ofPattern(pattern).format(temporal);
    }

    public static TemporalAccessor parse(String dateStr, String pattern) {
        return DateTimeFormatter.ofPattern(pattern).parse(dateStr);
    }

    public static TemporalAccessor parse(String dateStr, DateTimeFormatter formatter) {
        return formatter.parse(dateStr);
    }

    public static Instant toInstant(LocalDateTime dateTime) {
        return dateTime.atZone(ZoneId.systemDefault()).toInstant();
    }

    public static LocalDateTime toDateTime(Instant instant) {
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }
}
