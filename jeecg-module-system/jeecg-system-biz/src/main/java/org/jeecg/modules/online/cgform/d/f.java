package org.jeecg.modules.online.cgform.d;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import org.jeecg.common.util.oConvertUtils;

/* compiled from: MachineEncodeUtil.java */
/* loaded from: hibernate-common-ol-5.4.74(2).jar:org/jeecg/modules/online/cgform/d/f.class */
public class f {
    public static String a(byte[] bArr, int i) {
        return new BigInteger(1, bArr).toString(i);
    }

    public static String a(byte[] bArr) {
        return Base64.getEncoder().encodeToString(bArr);
    }

    public static byte[] a(String str) throws Exception {
        if (oConvertUtils.isEmpty(str)) {
            return null;
        }
        return Base64.getDecoder().decode(str);
    }

    public static byte[] b(byte[] bArr) throws Exception {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(bArr);
        return messageDigest.digest();
    }

    public static byte[] b(String str) throws Exception {
        if (oConvertUtils.isEmpty(str)) {
            return null;
        }
        return b(str.getBytes());
    }

    public static String c(String str) throws Exception {
        if (oConvertUtils.isEmpty(str)) {
            return null;
        }
        return a(b(str));
    }

    public static byte[] a(String str, String str2) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(str2.getBytes());
        keyGenerator.init(128, secureRandom);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(1, new SecretKeySpec(keyGenerator.generateKey().getEncoded(), "AES"));
        return cipher.doFinal(str.getBytes("utf-8"));
    }

    public static String b(String str, String str2) throws Exception {
        return a(a(str, str2));
    }

    public static String a(byte[] bArr, String str) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(str.getBytes());
        keyGenerator.init(128, secureRandom);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(2, new SecretKeySpec(keyGenerator.generateKey().getEncoded(), "AES"));
        return new String(cipher.doFinal(bArr));
    }

    public static String c(String str, String str2) throws Exception {
        if (oConvertUtils.isEmpty(str)) {
            return null;
        }
        return a(a(str), str2);
    }

    public static String d(String str, String str2) {
        try {
            if (oConvertUtils.isEmpty(str)) {
                return null;
            }
            return a(a(str), str2);
        } catch (Exception e) {
            return "CC ERROR";
        }
    }
}