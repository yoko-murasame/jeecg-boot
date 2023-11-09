package org.jeecg.modules.system.util;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

@Slf4j
public class DbUtil {

    private static List<String> ignoreFields;
    static {
        ignoreFields = Arrays.asList("serialVersionUID", "createBy", "createTime", "updateBy", "updateTime");
    }

    public static <T> QueryWrapper<T> getAllEqQueryWraper(T t) {
        QueryWrapper<T> qr = new QueryWrapper<>();
        Field[] fields = t.getClass().getDeclaredFields();
        Field[] superFields = t.getClass().getSuperclass().getDeclaredFields();
        handleFields(t, qr, superFields);
        handleFields(t, qr, fields);
        return qr;
    }

    private static <T> void handleFields(T t, QueryWrapper<T> qr, Field[] fields) {
        for (Field field : fields) {
            String fieldName = field.getName();
            if (ignoreFields.contains(fieldName)) {
                continue;
            }
            field.setAccessible(true);
            Object fieldValue = null;
            try {
                fieldValue = field.get(t);
            } catch (IllegalAccessException e) {
                log.error(e.getMessage());
            }

            if (null == fieldValue) {
                continue;
            }

            Class<?> type = field.getType();

            //处理TableField注解，映射数据库字段名
            TableField tableField = field.getAnnotation(TableField.class);
            if (tableField != null) {
                fieldName = tableField.value();
            }

            if (type == String.class && StringUtils.isNotEmpty((String) fieldValue)) {
                qr.eq(fieldName, fieldValue);
            }
            if (type == Integer.class && fieldValue != null) {
                qr.eq(fieldName, fieldValue);
            }
            if (type == Date.class && fieldValue != null) {
                //处理DateTimeFormat注解
                DateTimeFormat dateTimeFormat = field.getAnnotation(DateTimeFormat.class);
                String pattern = "yyyy-MM-dd";
                if (dateTimeFormat != null && StringUtils.isNotEmpty(dateTimeFormat.pattern())) {
                    pattern = dateTimeFormat.pattern();
                }
                String dateStr = new SimpleDateFormat(pattern).format(fieldValue);
                qr.eq(fieldName, dateStr);
            }
            if (type == LocalDateTime.class && fieldValue != null) {
                //处理DateTimeFormat注解
                DateTimeFormat dateTimeFormat = field.getAnnotation(DateTimeFormat.class);
                String pattern = "yyyy-MM-dd HH:mm:ss";
                if (dateTimeFormat != null && StringUtils.isNotEmpty(dateTimeFormat.pattern())) {
                    pattern = dateTimeFormat.pattern();
                }
                LocalDateTime timeFieldValue = (LocalDateTime) fieldValue;
                String stringFieldValue = timeFieldValue.format(DateTimeFormatter.ofPattern(pattern));
                qr.eq(fieldName, stringFieldValue);
            }
            if (type == LocalDate.class && fieldValue != null) {
                //处理DateTimeFormat注解
                DateTimeFormat dateTimeFormat = field.getAnnotation(DateTimeFormat.class);
                String pattern = "yyyy-MM-dd";
                if (dateTimeFormat != null && StringUtils.isNotEmpty(dateTimeFormat.pattern())) {
                    pattern = dateTimeFormat.pattern();
                }
                LocalDate timeFieldValue = (LocalDate) fieldValue;
                String stringFieldValue = timeFieldValue.format(DateTimeFormatter.ofPattern(pattern));
                qr.eq(fieldName, stringFieldValue);
            }
            //自行扩充
        }
    }

    /**
     * Stream工具，对象数组去重
     * @example stream.filter(distinctByKey(Object::getSomething))
     * @param keyExtractor
     * @param <T>
     * @return
     */
    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return object -> seen.putIfAbsent(keyExtractor.apply(object), Boolean.TRUE) == null;
    }


}
