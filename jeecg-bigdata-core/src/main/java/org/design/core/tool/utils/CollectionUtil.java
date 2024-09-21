package org.design.core.tool.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;

/* loaded from: bigdata-core-tool-3.1.0.jar:org/design/core/tool/utils/CollectionUtil.class */
public class CollectionUtil extends CollectionUtils {
    public static <T> boolean contains(@Nullable T[] array, T element) {
        if (array == null) {
            return false;
        }
        return Arrays.stream(array).anyMatch(x -> {
            return ObjectUtil.nullSafeEquals(x, element);
        });
    }

    public static boolean isArray(Object obj) {
        if (null == obj) {
            return false;
        }
        return obj.getClass().isArray();
    }

    public static boolean isNotEmpty(@Nullable Collection<?> coll) {
        return !CollectionUtils.isEmpty(coll);
    }

    public static boolean isNotEmpty(@Nullable Map<?, ?> map) {
        return !CollectionUtils.isEmpty(map);
    }
}
