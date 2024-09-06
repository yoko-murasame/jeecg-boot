package org.design.core.tool.utils;

import java.nio.charset.Charset;
import org.springframework.util.Base64Utils;

/* loaded from: bigdata-core-tool-3.1.0.jar:org/design/core/tool/utils/Base64Util.class */
public class Base64Util extends Base64Utils {
    public static String encode(String value) {
        return encode(value, Charsets.UTF_8);
    }

    public static String encode(String value, Charset charset) {
        return new String(encode(value.getBytes(charset)), charset);
    }

    public static String encodeUrlSafe(String value) {
        return encodeUrlSafe(value, Charsets.UTF_8);
    }

    public static String encodeUrlSafe(String value, Charset charset) {
        return new String(encodeUrlSafe(value.getBytes(charset)), charset);
    }

    public static String decode(String value) {
        return decode(value, Charsets.UTF_8);
    }

    public static String decode(String value, Charset charset) {
        return new String(decode(value.getBytes(charset)), charset);
    }

    public static String decodeUrlSafe(String value) {
        return decodeUrlSafe(value, Charsets.UTF_8);
    }

    public static String decodeUrlSafe(String value, Charset charset) {
        return new String(decodeUrlSafe(value.getBytes(charset)), charset);
    }
}
