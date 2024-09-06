package org.design.core.tool.utils;

import java.text.ParseException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalQuery;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import org.springframework.util.Assert;

/* loaded from: bigdata-core-tool-3.1.0.jar:org/design/core/tool/utils/DateUtil.class */
public final class DateUtil {
    public static final String PATTERN_DATETIME = "yyyy-MM-dd HH:mm:ss";
    public static final ConcurrentDateFormat DATETIME_FORMAT = ConcurrentDateFormat.of(PATTERN_DATETIME);
    public static final String PATTERN_DATE = "yyyy-MM-dd";
    public static final ConcurrentDateFormat DATE_FORMAT = ConcurrentDateFormat.of(PATTERN_DATE);
    public static final String PATTERN_TIME = "HH:mm:ss";
    public static final ConcurrentDateFormat TIME_FORMAT = ConcurrentDateFormat.of(PATTERN_TIME);
    public static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern(PATTERN_DATETIME);
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(PATTERN_DATE);
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(PATTERN_TIME);

    private DateUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static Date now() {
        return new Date();
    }

    public static Date plusYears(Date date, int yearsToAdd) {
        return set(date, 1, yearsToAdd);
    }

    public static Date plusMonths(Date date, int monthsToAdd) {
        return set(date, 2, monthsToAdd);
    }

    public static Date plusWeeks(Date date, int weeksToAdd) {
        return plus(date, Period.ofWeeks(weeksToAdd));
    }

    public static Date plusDays(Date date, long daysToAdd) {
        return plus(date, Duration.ofDays(daysToAdd));
    }

    public static Date plusHours(Date date, long hoursToAdd) {
        return plus(date, Duration.ofHours(hoursToAdd));
    }

    public static Date plusMinutes(Date date, long minutesToAdd) {
        return plus(date, Duration.ofMinutes(minutesToAdd));
    }

    public static Date plusSeconds(Date date, long secondsToAdd) {
        return plus(date, Duration.ofSeconds(secondsToAdd));
    }

    public static Date plusMillis(Date date, long millisToAdd) {
        return plus(date, Duration.ofMillis(millisToAdd));
    }

    public static Date plusNanos(Date date, long nanosToAdd) {
        return plus(date, Duration.ofNanos(nanosToAdd));
    }

    public static Date plus(Date date, TemporalAmount amount) {
        return Date.from(date.toInstant().plus(amount));
    }

    public static Date minusYears(Date date, int years) {
        return set(date, 1, -years);
    }

    public static Date minusMonths(Date date, int months) {
        return set(date, 2, -months);
    }

    public static Date minusWeeks(Date date, int weeks) {
        return minus(date, Period.ofWeeks(weeks));
    }

    public static Date minusDays(Date date, long days) {
        return minus(date, Duration.ofDays(days));
    }

    public static Date minusHours(Date date, long hours) {
        return minus(date, Duration.ofHours(hours));
    }

    public static Date minusMinutes(Date date, long minutes) {
        return minus(date, Duration.ofMinutes(minutes));
    }

    public static Date minusSeconds(Date date, long seconds) {
        return minus(date, Duration.ofSeconds(seconds));
    }

    public static Date minusMillis(Date date, long millis) {
        return minus(date, Duration.ofMillis(millis));
    }

    public static Date minusNanos(Date date, long nanos) {
        return minus(date, Duration.ofNanos(nanos));
    }

    public static Date minus(Date date, TemporalAmount amount) {
        return Date.from(date.toInstant().minus(amount));
    }

    private static Date set(Date date, int calendarField, int amount) {
        Assert.notNull(date, "The date must not be null");
        Calendar c = Calendar.getInstance();
        c.setLenient(false);
        c.setTime(date);
        c.add(calendarField, amount);
        return c.getTime();
    }

    public static String formatDateTime(Date date) {
        return DATETIME_FORMAT.format(date);
    }

    public static String formatDate(Date date) {
        return DATE_FORMAT.format(date);
    }

    public static String formatTime(Date date) {
        return TIME_FORMAT.format(date);
    }

    public static String format(Date date, String pattern) {
        return ConcurrentDateFormat.of(pattern).format(date);
    }

    public static String formatDateTime(TemporalAccessor temporal) {
        return DATETIME_FORMATTER.format(temporal);
    }

    public static String formatDate(TemporalAccessor temporal) {
        return DATE_FORMATTER.format(temporal);
    }

    public static String formatTime(TemporalAccessor temporal) {
        return TIME_FORMATTER.format(temporal);
    }

    public static String format(TemporalAccessor temporal, String pattern) {
        return DateTimeFormatter.ofPattern(pattern).format(temporal);
    }

    public static Date parse(String dateStr, String pattern) {
        try {
            return ConcurrentDateFormat.of(pattern).parse(dateStr);
        } catch (ParseException e) {
            throw Exceptions.unchecked(e);
        }
    }

    public static Date parse(String dateStr, ConcurrentDateFormat format) {
        try {
            return format.parse(dateStr);
        } catch (ParseException e) {
            throw Exceptions.unchecked(e);
        }
    }

    public static <T> T parse(String dateStr, String pattern, TemporalQuery<T> query) {
        return (T) DateTimeFormatter.ofPattern(pattern).parse(dateStr, query);
    }

    public static Instant toInstant(LocalDateTime dateTime) {
        return dateTime.atZone(ZoneId.systemDefault()).toInstant();
    }

    public static LocalDateTime toDateTime(Instant instant) {
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    public static Date toDate(LocalDateTime dateTime) {
        return Date.from(toInstant(dateTime));
    }

    public static Date toDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static Calendar toCalendar(LocalDateTime localDateTime) {
        return GregorianCalendar.from(ZonedDateTime.of(localDateTime, ZoneId.systemDefault()));
    }

    public static long toMilliseconds(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public static long toMilliseconds(LocalDate localDate) {
        return toMilliseconds(localDate.atStartOfDay());
    }

    public static LocalDateTime fromCalendar(Calendar calendar) {
        TimeZone tz = calendar.getTimeZone();
        return LocalDateTime.ofInstant(calendar.toInstant(), tz == null ? ZoneId.systemDefault() : tz.toZoneId());
    }

    public static LocalDateTime fromInstant(Instant instant) {
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    public static LocalDateTime fromDate(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    public static LocalDateTime fromMilliseconds(long milliseconds) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(milliseconds), ZoneId.systemDefault());
    }

    public static Duration between(Temporal startInclusive, Temporal endExclusive) {
        return Duration.between(startInclusive, endExclusive);
    }

    public static Period between(LocalDate startDate, LocalDate endDate) {
        return Period.between(startDate, endDate);
    }

    public static Duration between(Date startDate, Date endDate) {
        return Duration.between(startDate.toInstant(), endDate.toInstant());
    }

    public static String secondToTime(Long second) {
        if (second == null || second.longValue() == 0) {
            return StringPool.EMPTY;
        }
        long days = second.longValue() / 86400;
        Long second2 = Long.valueOf(second.longValue() % 86400);
        long hours = second2.longValue() / 3600;
        Long second3 = Long.valueOf(second2.longValue() % 3600);
        long minutes = second3.longValue() / 60;
        Long second4 = Long.valueOf(second3.longValue() % 60);
        if (days > 0) {
            return StringUtil.format("{}天{}小时{}分{}秒", Long.valueOf(days), Long.valueOf(hours), Long.valueOf(minutes), second4);
        }
        return StringUtil.format("{}小时{}分{}秒", Long.valueOf(hours), Long.valueOf(minutes), second4);
    }

    public static String today() {
        return format(new Date(), "yyyyMMdd");
    }
}
