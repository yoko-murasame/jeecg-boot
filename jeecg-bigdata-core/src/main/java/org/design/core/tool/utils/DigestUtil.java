package org.design.core.tool.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.springframework.lang.Nullable;
import org.springframework.util.DigestUtils;

/* loaded from: bigdata-core-tool-3.1.0.jar:org/design/core/tool/utils/DigestUtil.class */
public class DigestUtil extends DigestUtils {
    private static final char[] HEX_DIGITS = "0123456789abcdef".toCharArray();

    public static String md5Hex(String data) {
        return md5DigestAsHex(data.getBytes(Charsets.UTF_8));
    }

    public static String md5Hex(byte[] bytes) {
        return md5DigestAsHex(bytes);
    }

    public static String sha1(String srcStr) {
        return hash("SHA-1", srcStr);
    }

    public static String sha256(String srcStr) {
        return hash("SHA-256", srcStr);
    }

    public static String sha384(String srcStr) {
        return hash("SHA-384", srcStr);
    }

    public static String sha512(String srcStr) {
        return hash("SHA-512", srcStr);
    }

    public static String hash(String algorithm, String srcStr) {
        try {
            return toHex(MessageDigest.getInstance(algorithm).digest(srcStr.getBytes(Charsets.UTF_8)));
        } catch (NoSuchAlgorithmException e) {
            throw Exceptions.unchecked(e);
        }
    }

    public static String toHex(byte[] bytes) {
        StringBuilder ret = new StringBuilder(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            ret.append(HEX_DIGITS[(bytes[i] >> 4) & 15]);
            ret.append(HEX_DIGITS[bytes[i] & 15]);
        }
        return ret.toString();
    }

    public static boolean slowEquals(@Nullable String a, @Nullable String b) {
        if (a == null || b == null) {
            return false;
        }
        return slowEquals(a.getBytes(Charsets.UTF_8), b.getBytes(Charsets.UTF_8));
    }

    public static boolean slowEquals(@Nullable byte[] a, @Nullable byte[] b) {
        if (a == null || b == null || a.length != b.length) {
            return false;
        }
        int diff = a.length ^ b.length;
        int i = 0;
        while (i < a.length && i < b.length) {
            diff |= a[i] ^ b[i];
            i++;
        }
        return diff == 0;
    }

    public static String encrypt(String data) {
        return sha1(md5Hex(data));
    }
}
