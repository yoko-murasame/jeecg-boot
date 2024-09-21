package org.design.core.tool.convert;

import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.design.core.tool.support.Try;
import org.design.core.tool.utils.ClassUtil;
import org.design.core.tool.utils.ConvertUtil;
import org.design.core.tool.utils.ReflectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.core.Converter;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.lang.Nullable;

/* loaded from: bigdata-core-tool-3.1.0.jar:org/design/core/tool/convert/BaseConverter.class */
public class BaseConverter implements Converter {
    private static final Logger log = LoggerFactory.getLogger(BaseConverter.class);
    private static final ConcurrentMap<String, TypeDescriptor> TYPE_CACHE = new ConcurrentHashMap();
    private final Class<?> targetClazz;

    public BaseConverter(Class<?> targetClazz) {
        this.targetClazz = targetClazz;
    }

    @Nullable
    public Object convert(Object value, Class target, Object fieldName) {
        if (value == null) {
            return null;
        }
        if (ClassUtil.isAssignableValue(target, value)) {
            return value;
        }
        try {
            return ConvertUtil.convert(value, getTypeDescriptor(this.targetClazz, (String) fieldName));
        } catch (Throwable e) {
            log.warn("BaseConverter error", e);
            return null;
        }
    }

    private static TypeDescriptor getTypeDescriptor(Class<?> clazz, String fieldName) {
        return TYPE_CACHE.computeIfAbsent(clazz.getName() + fieldName, Try.of(k -> {
            Field field = ReflectUtil.getField(clazz, fieldName);
            if (field != null) {
                return new TypeDescriptor(field);
            }
            throw new NoSuchFieldException(fieldName);
        }));
    }
}
