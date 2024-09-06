package org.design.core.tool.support;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import org.design.core.tool.utils.Func;
import org.springframework.util.LinkedCaseInsensitiveMap;

/* loaded from: bigdata-core-tool-3.1.0.jar:org/design/core/tool/support/Kv.class */
public class Kv extends LinkedCaseInsensitiveMap<Object> {
    private Kv() {
    }

    public static Kv init() {
        return new Kv();
    }

    public static <K, V> HashMap<K, V> newMap() {
        return new HashMap<>(16);
    }

    public Kv set(String attr, Object value) {
        put(attr, value);
        return this;
    }

    public Kv setIgnoreNull(String attr, Object value) {
        if (!(null == attr || null == value)) {
            set(attr, value);
        }
        return this;
    }

    public Object getObj(String key) {
        return get(key);
    }

    public <T> T get(String attr, T defaultValue) {
        T t = (T) get(attr);
        return t != null ? t : defaultValue;
    }

    public String getStr(String attr) {
        return Func.toStr(get(attr), null);
    }

    public Integer getInt(String attr) {
        return Integer.valueOf(Func.toInt(get(attr), -1));
    }

    public Long getLong(String attr) {
        return Long.valueOf(Func.toLong(get(attr), -1));
    }

    public Float getFloat(String attr) {
        return Func.toFloat(get(attr), null);
    }

    public Double getDouble(String attr) {
        return Func.toDouble(get(attr), null);
    }

    public Boolean getBool(String attr) {
        return Func.toBoolean(get(attr), null);
    }

    public byte[] getBytes(String attr) {
        return (byte[]) get(attr, null);
    }

    public Date getDate(String attr) {
        return (Date) get(attr, null);
    }

    public Time getTime(String attr) {
        return (Time) get(attr, null);
    }

    public Timestamp getTimestamp(String attr) {
        return (Timestamp) get(attr, null);
    }

    public Number getNumber(String attr) {
        return (Number) get(attr, null);
    }

    /* JADX DEBUG: Multi-variable search result rejected for r3v0, resolved type: org.design.core.tool.support.Kv */
    /* JADX WARN: Multi-variable type inference failed */
    public Kv clone() {
        Kv clone = new Kv();
        clone.putAll(this);
        return clone;
    }
}
