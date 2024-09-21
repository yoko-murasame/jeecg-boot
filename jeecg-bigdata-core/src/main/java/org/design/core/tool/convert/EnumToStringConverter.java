package org.design.core.tool.convert;

import com.fasterxml.jackson.annotation.JsonValue;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.design.core.tool.utils.ConvertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalGenericConverter;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.lang.Nullable;

/* loaded from: bigdata-core-tool-3.1.0.jar:org/design/core/tool/convert/EnumToStringConverter.class */
public class EnumToStringConverter implements ConditionalGenericConverter {
    private static final Logger log = LoggerFactory.getLogger(EnumToStringConverter.class);
    private static final ConcurrentMap<Class<?>, AccessibleObject> ENUM_CACHE_MAP = new ConcurrentHashMap(8);

    @Nullable
    private static AccessibleObject getAnnotation(Class<?> clazz) {
        Set<AccessibleObject> accessibleObjects = new HashSet<>();
        Collections.addAll(accessibleObjects, clazz.getDeclaredFields());
        Collections.addAll(accessibleObjects, clazz.getDeclaredMethods());
        for (AccessibleObject accessibleObject : accessibleObjects) {
            JsonValue jsonValue = accessibleObject.getAnnotation(JsonValue.class);
            if (jsonValue != null && jsonValue.value()) {
                accessibleObject.setAccessible(true);
                return accessibleObject;
            }
        }
        return null;
    }

    public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
        return true;
    }

    public Set<GenericConverter.ConvertiblePair> getConvertibleTypes() {
        Set<GenericConverter.ConvertiblePair> pairSet = new HashSet<>(3);
        pairSet.add(new GenericConverter.ConvertiblePair(Enum.class, String.class));
        pairSet.add(new GenericConverter.ConvertiblePair(Enum.class, Integer.class));
        pairSet.add(new GenericConverter.ConvertiblePair(Enum.class, Long.class));
        return Collections.unmodifiableSet(pairSet);
    }

    public Object convert(@Nullable Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        if (source == null) {
            return null;
        }
        Class<?> sourceClazz = sourceType.getType();
        AccessibleObject accessibleObject = ENUM_CACHE_MAP.computeIfAbsent(sourceClazz, EnumToStringConverter::getAnnotation);
        Class<?> targetClazz = targetType.getType();
        if (accessibleObject != null) {
            try {
                return invoke(sourceClazz, accessibleObject, source, targetClazz);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                return null;
            }
        } else if (String.class == targetClazz) {
            return ((Enum) source).name();
        } else {
            return ConvertUtil.convert(Integer.valueOf(((Enum) source).ordinal()), targetClazz);
        }
    }

    @Nullable
    private static Object invoke(Class<?> clazz, AccessibleObject accessibleObject, Object source, Class<?> targetClazz) throws IllegalAccessException, InvocationTargetException {
        Object value = null;
        if (accessibleObject instanceof Field) {
            value = ((Field) accessibleObject).get(source);
        } else if (accessibleObject instanceof Method) {
            Method method = (Method) accessibleObject;
            value = method.invoke(clazz, ConvertUtil.convert(source, method.getParameterTypes()[0]));
        }
        if (value == null) {
            return null;
        }
        return ConvertUtil.convert(value, targetClazz);
    }
}
