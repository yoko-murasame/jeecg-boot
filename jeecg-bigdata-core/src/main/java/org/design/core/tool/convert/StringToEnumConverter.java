//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.design.core.tool.convert;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import org.design.core.tool.utils.ConvertUtil;
import org.design.core.tool.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalGenericConverter;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.lang.Nullable;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class StringToEnumConverter implements ConditionalGenericConverter {
    private static final Logger log = LoggerFactory.getLogger(StringToEnumConverter.class);
    private static final ConcurrentMap<Class<?>, AccessibleObject> ENUM_CACHE_MAP = new ConcurrentHashMap(8);

    public StringToEnumConverter() {
    }

    @Nullable
    private static AccessibleObject getAnnotation(Class<?> clazz) {
        Set<AccessibleObject> accessibleObjects = new HashSet();
        Constructor<?>[] constructors = clazz.getConstructors();
        Collections.addAll(accessibleObjects, constructors);
        Method[] methods = clazz.getDeclaredMethods();
        Collections.addAll(accessibleObjects, methods);
        Iterator var4 = accessibleObjects.iterator();

        AccessibleObject accessibleObject;
        JsonCreator jsonCreator;
        do {
            if (!var4.hasNext()) {
                return null;
            }

            accessibleObject = (AccessibleObject)var4.next();
            jsonCreator = (JsonCreator)accessibleObject.getAnnotation(JsonCreator.class);
        } while(jsonCreator == null || Mode.DISABLED == jsonCreator.mode());

        accessibleObject.setAccessible(true);
        return accessibleObject;
    }

    public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
        return true;
    }

    public Set<GenericConverter.ConvertiblePair> getConvertibleTypes() {
        return Collections.singleton(new GenericConverter.ConvertiblePair(String.class, Enum.class));
    }

    @Nullable
    public Object convert(@Nullable Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        if (StringUtil.isBlank((String)source)) {
            return null;
        } else {
            Class clazz = targetType.getType();
            AccessibleObject accessibleObject = (AccessibleObject)ENUM_CACHE_MAP.computeIfAbsent(clazz, StringToEnumConverter::getAnnotation);
            String value = ((String)source).trim();
            if (accessibleObject == null) {
                return valueOf(clazz, value);
            } else {
                try {
                    return invoke(clazz, accessibleObject, value);
                } catch (Exception var8) {
                    log.error(var8.getMessage(), var8);
                    return null;
                }
            }
        }
    }

    private static <T extends Enum<T>> T valueOf(Class<T> clazz, String value) {
        return Enum.valueOf(clazz, value);
    }

    @Nullable
    private static Object invoke(Class<?> clazz, AccessibleObject accessibleObject, String value) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Class paramType;
        Object object;
        if (accessibleObject instanceof Constructor) {
            Constructor constructor = (Constructor)accessibleObject;
            paramType = constructor.getParameterTypes()[0];
            object = ConvertUtil.convert(value, paramType);
            return constructor.newInstance(object);
        } else if (accessibleObject instanceof Method) {
            Method method = (Method)accessibleObject;
            paramType = method.getParameterTypes()[0];
            object = ConvertUtil.convert(value, paramType);
            return method.invoke(clazz, object);
        } else {
            return null;
        }
    }
}
