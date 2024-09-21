package org.design.core.tool.utils;

import org.springframework.lang.Nullable;
import org.springframework.util.NumberUtils;

/* loaded from: bigdata-core-tool-3.1.0.jar:org/design/core/tool/utils/NumberUtil.class */
public class NumberUtil extends NumberUtils {
    private static final char[] DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    public static int toInt(String str) {
        return toInt(str, -1);
    }

    public static int toInt(@Nullable String str, int defaultValue) {
        if (str == null) {
            return defaultValue;
        }
        try {
            return Integer.valueOf(str).intValue();
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static long toLong(String str) {
        return toLong(str, 0);
    }

    public static long toLong(@Nullable String str, long defaultValue) {
        if (str == null) {
            return defaultValue;
        }
        try {
            return Long.valueOf(str).longValue();
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static Double toDouble(String value) {
        return toDouble(value, null);
    }

    public static Double toDouble(@Nullable String value, Double defaultValue) {
        if (value != null) {
            return Double.valueOf(value.trim());
        }
        return defaultValue;
    }

    public static Float toFloat(String value) {
        return toFloat(value, null);
    }

    public static Float toFloat(@Nullable String value, Float defaultValue) {
        if (value != null) {
            return Float.valueOf(value.trim());
        }
        return defaultValue;
    }

    public static String to62String(long i) {
        int radix = DIGITS.length;
        char[] buf = new char[65];
        int charPos = 64;
        long i2 = -i;
        while (i2 <= ((long) (-radix))) {
            charPos--;
            buf[charPos] = DIGITS[(int) (-(i2 % ((long) radix)))];
            i2 /= (long) radix;
        }
        buf[charPos] = DIGITS[(int) (-i2)];
        return new String(buf, charPos, 65 - charPos);
    }
}
