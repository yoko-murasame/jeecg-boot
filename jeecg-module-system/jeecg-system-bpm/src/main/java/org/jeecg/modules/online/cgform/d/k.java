package org.jeecg.modules.online.cgform.d;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.util.DateUtils;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Map;

/* compiled from: OnlineDbHandler.java */
/* loaded from: hibernate-common-ol-5.4.74(2).jar:org/jeecg/modules/online/cgform/d/k.class */
@Slf4j
public class k {
    public static final String a = "int";
    public static final String b = "Integer";
    public static final String c = "double";
    public static final String d = "BigDecimal";
    public static final String e = "Blob";
    public static final String f = "Date";
    public static final String g = "datetime";
    public static final String h = "Timestamp";

    public static boolean isNumber(String str) {
        return "int".equals(str) || "double".equals(str) || "BigDecimal".equals(str) || "Integer".equals(str);
    }

    public static boolean b(String str) {
        return "Date".equalsIgnoreCase(str) || "datetime".equalsIgnoreCase(str) || h.equalsIgnoreCase(str);
    }

    public static String a(String str, OnlCgformField onlCgformField, JSONObject jSONObject, Map<String, Object> map) {
        String dbType = onlCgformField.getDbType();
        String dbFieldName = onlCgformField.getDbFieldName();
        String fieldShowType = onlCgformField.getFieldShowType();
        if (jSONObject.get(dbFieldName) == null) {
            return "null";
        }
        if ("int".equals(dbType)) {
            map.put(dbFieldName, Integer.valueOf(jSONObject.getIntValue(dbFieldName)));
            return "#{" + dbFieldName + ",jdbcType=INTEGER}";
        } else if ("double".equals(dbType)) {
            map.put(dbFieldName, Double.valueOf(jSONObject.getDoubleValue(dbFieldName)));
            return "#{" + dbFieldName + ",jdbcType=DOUBLE}";
        } else if ("BigDecimal".equals(dbType)) {
            map.put(dbFieldName, new BigDecimal(jSONObject.getString(dbFieldName)));
            return "#{" + dbFieldName + ",jdbcType=DECIMAL}";
        } else if ("Blob".equals(dbType)) {
            map.put(dbFieldName, jSONObject.getString(dbFieldName) != null ? jSONObject.getString(dbFieldName).getBytes() : null);
            return "#{" + dbFieldName + ",jdbcType=BLOB}";
        } else if ("Date".equals(dbType)) {
            String string = jSONObject.getString(dbFieldName);
            // 这里需要判断传入是否已经是时间戳，是的话先格式化成string
            Object dateValue = jSONObject.get(dbFieldName);
            if (dateValue instanceof Long) {
                // 将时间戳转换为日期字符串
                String dateStr = DateUtils.timestamptoStr(new Timestamp((Long) dateValue));
                log.info("注意::jSONObject::字段::{}::由long值转换成字符串: {}", dbFieldName, dateStr);
                string = dateStr;
            }
            if ("ORACLE".equals(str)) {
                // 日期类型
                if (i.DATE.equals(fieldShowType)) {
                    map.put(dbFieldName, string.length() > 10 ? string.substring(0, 10) : string);
                    return "to_date(#{" + dbFieldName + "},'yyyy-MM-dd')";
                }
                map.put(dbFieldName, string.length() == 10 ? jSONObject.getString(dbFieldName) + " 00:00:00" : string);
                return "to_date(#{" + dbFieldName + "},'yyyy-MM-dd HH24:mi:ss')";
            } else if ("POSTGRESQL".equals(str)) {
                // 日期类型
                if (i.DATE.equals(fieldShowType)) {
                    map.put(dbFieldName, string.length() > 10 ? string.substring(0, 10) : string);
                    return "CAST(#{" + dbFieldName + "} as TIMESTAMP)";
                }
                map.put(dbFieldName, string.length() == 10 ? jSONObject.getString(dbFieldName) + " 00:00:00" : string);
                return "CAST(#{" + dbFieldName + "} as TIMESTAMP)";
            } else {
                map.put(dbFieldName, jSONObject.getString(dbFieldName));
                return "#{" + dbFieldName + "}";
            }
        } else {
            map.put(dbFieldName, jSONObject.getString(dbFieldName));
            return "#{" + dbFieldName + ",jdbcType=VARCHAR}";
        }
    }
}
