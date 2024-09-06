package org.design.core.tool.utils;

import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;

/* loaded from: bigdata-core-tool-3.1.0.jar:org/design/core/tool/utils/ObjectUtil.class */
public class ObjectUtil extends ObjectUtils {
    public static boolean isNotEmpty(@Nullable Object obj) {
        return !isEmpty(obj);
    }
}
