package org.jeecg.modules.activiti.jeecg.commons;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import javax.crypto.Cipher;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class CommonD {
    static final String a = "U5VwTnDVppT0puHtkE/Uj3/K4JUu+7l0Qa2Q0BRGCucOyujbPZYFmQEu76sx0fxnXA4OK8TgoaUXWaU3lnWm3skMMel6Q/jRJn8LzsP4FE7Q/g4mTJYU3WqxxRCzRNtQqgtFq/St6ujClGeuaxwIrWUB5HqVbaRoDJoRZ5ecl+0=";
    static final String b = "LVuEjlAGl2JzUyxk4if+PICodE5wcpHs59W21jayXfyWVB8e2ctlf8y4gFsp2Df0HEQSpUyd4cBDhGIKr5rNZ7m/60S26z54QCqAIcEpOWBuhDg8OwTVD3WpN8r4jIVxMmUQEEeWY/hdikZKjh8ASrrGoGbNvxxcquPYuyXx1R8=";
    static final String c = "HwvseLgXb0ADdzRJc1GjH684ejHzHA2CvsU3DIIfceB1WzQlQ9pk9Xvp8U6X6SZZCXJGfUGO0ufvE6vRC2mNfYtaZBXcissamXmFft+8arpmCHB3NcwPwcWDnFmmjz5vWCWSeFN4pgwkxTzBZCDCedVnQfe69k+ztW4mQ5nkVcQ=";
    static final String d = "qeRqo/rXGiHD4rcSAJ07ZlSIT8mKBltrDmOq/XgOoadOr28QK+fSIq/hAwDP3AKQv5eWMHQ+1iHxluJY30jVoyW6UK+2RqCrTx5cDF45/gP+8y5Les0JJUy0tQ7QvNLgRGiM6S4lSyBtBK7TQwJA04pyorNNN0jjaH42k2wP6Wk=";
    static final String e = "Hpe2Y2xbC9O5/UTpTy7vo1b/uVB2TlRpshb1pTsOCdR+DJbrjCktyA37LCrUXaeMioqwmwkZqlg8cQKErWjhUKPjDN45o7wcCnYxmToc7rds9LTWGRszoTdzanz7LiIm0b6Nn7yRMZMKnkwYga+PIT9gqUsI0CLscU8XKN7HZcs=";
    public static final String f = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCxHEhhLwdDhZ57SlLt/5JWFeGRy4r+NKgbA0VwkCXs7p0w1bNgKlRJOzUfjg6kMi4ygkVNNPWrXIhcdMniTbcHmawawLTa3cRM1jNdG0xg808KKNVdDilFHOFQz8AF6cvLDpsWkqzmoe2+64v0zlWbp5EpYxMMRxOwSMVKxLB8BwIDAQAB";

    public CommonD() {
    }

    public static byte[] a(byte[] var0, byte[] var1) {
        try {
            PKCS8EncodedKeySpec var2 = new PKCS8EncodedKeySpec(var0);
            KeyFactory var3 = KeyFactory.getInstance("RSA");
            PrivateKey var4 = var3.generatePrivate(var2);
            Signature var5 = Signature.getInstance("SHA1withRSA");
            var5.initSign(var4);
            var5.update(var1);
            return var5.sign();
        } catch (Exception var6) {
            return null;
        }
    }

    public static boolean a(byte[] var0, byte[] var1, byte[] var2) {
        try {
            KeyFactory var3 = KeyFactory.getInstance("RSA");
            Signature var4 = Signature.getInstance("SHA1withRSA");
            X509EncodedKeySpec var5 = new X509EncodedKeySpec(var0);
            PublicKey var6 = var3.generatePublic(var5);
            var4.initVerify(var6);
            var4.update(var1);
            return var4.verify(var2);
        } catch (Exception var7) {
            return false;
        }
    }

    public static Object[] a() {
        KeyPair var0 = c();
        if (var0 == null) {
            return null;
        } else {
            Object[] var1 = new Object[2];
            if (var0 != null) {
                PrivateKey var2 = var0.getPrivate();
                byte[] var3 = var2.getEncoded();
                PublicKey var4 = var0.getPublic();
                byte[] var5 = var4.getEncoded();
                var1[0] = var3;
                var1[1] = var5;
                return var1;
            } else {
                return null;
            }
        }
    }

    public static String[] b() {
        KeyPair var0 = c();
        if (var0 == null) {
            return null;
        } else {
            String[] var1 = new String[2];
            if (var0 != null) {
                PrivateKey var2 = var0.getPrivate();
                byte[] var3 = var2.getEncoded();
                String var4 = Base64.getEncoder().encodeToString(var3);
                PublicKey var5 = var0.getPublic();
                byte[] var6 = var5.getEncoded();
                String var7 = Base64.getEncoder().encodeToString(var6);
                var1[0] = var4;
                var1[1] = var7;
                return var1;
            } else {
                return null;
            }
        }
    }

    public static KeyPair c() {
        long var1 = System.currentTimeMillis();

        try {
            KeyPairGenerator var3 = KeyPairGenerator.getInstance("RSA");
            SecureRandom var4 = SecureRandom.getInstance("SHA1PRNG", "SUN");
            var4.setSeed(var1);
            var3.initialize(1024, var4);
            KeyPair var0 = var3.generateKeyPair();
            return var0;
        } catch (Exception var5) {
            return null;
        }
    }

    public static byte[] b(byte[] var0, byte[] var1) {
        try {
            KeyFactory var2 = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec var3 = new X509EncodedKeySpec(var0);
            PublicKey var4 = var2.generatePublic(var3);
            Cipher var5 = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            var5.init(1, var4);
            return var5.doFinal(var1);
        } catch (Exception var6) {
            return null;
        }
    }

    public static byte[] c(byte[] var0, byte[] var1) {
        try {
            PKCS8EncodedKeySpec var2 = new PKCS8EncodedKeySpec(var0);
            KeyFactory var3 = KeyFactory.getInstance("RSA");
            PrivateKey var4 = var3.generatePrivate(var2);
            Cipher var5 = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            var5.init(2, var4);
            return var5.doFinal(var1);
        } catch (Exception var6) {
            return null;
        }
    }

    public static byte[] d(byte[] var0, byte[] var1) {
        try {
            PKCS8EncodedKeySpec var2 = new PKCS8EncodedKeySpec(var0);
            KeyFactory var3 = KeyFactory.getInstance("RSA");
            PrivateKey var4 = var3.generatePrivate(var2);
            Cipher var5 = Cipher.getInstance(var3.getAlgorithm());
            var5.init(1, var4);
            return var5.doFinal(var1);
        } catch (Exception var6) {
            return null;
        }
    }

    public static byte[] a(String var0, byte[] var1) {
        try {
            byte[] var2 = Base64.getDecoder().decode(var0);
            PKCS8EncodedKeySpec var3 = new PKCS8EncodedKeySpec(var2);
            KeyFactory var4 = KeyFactory.getInstance("RSA");
            PrivateKey var5 = var4.generatePrivate(var3);
            Cipher var6 = Cipher.getInstance(var4.getAlgorithm());
            var6.init(1, var5);
            return var6.doFinal(var1);
        } catch (Exception var7) {
            return null;
        }
    }

    public static byte[] e(byte[] var0, byte[] var1) {
        try {
            KeyFactory var2 = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec var3 = new X509EncodedKeySpec(var0);
            PublicKey var4 = var2.generatePublic(var3);
            Cipher var5 = Cipher.getInstance(var2.getAlgorithm());
            var5.init(2, var4);
            return var5.doFinal(var1);
        } catch (Exception var6) {
            return null;
        }
    }

    public static byte[] b(String var0, byte[] var1) {
        try {
            byte[] var2 = Base64.getDecoder().decode(var0);
            KeyFactory var3 = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec var4 = new X509EncodedKeySpec(var2);
            PublicKey var5 = var3.generatePublic(var4);
            Cipher var6 = Cipher.getInstance(var3.getAlgorithm());
            var6.init(2, var5);
            return var6.doFinal(var1);
        } catch (Exception var7) {
            return null;
        }
    }

    public static byte[] a(String var0, String var1) {
        try {
            byte[] var2 = Base64.getDecoder().decode(var1);
            byte[] var3 = Base64.getDecoder().decode(var0);
            KeyFactory var4 = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec var5 = new X509EncodedKeySpec(var3);
            PublicKey var6 = var4.generatePublic(var5);
            Cipher var7 = Cipher.getInstance(var4.getAlgorithm());
            var7.init(2, var6);
            return var7.doFinal(var2);
        } catch (Exception var8) {
            return null;
        }
    }

    public static byte[] a(String var0) {
        try {
            MessageDigest var1 = MessageDigest.getInstance("SHA");
            byte[] var2 = var1.digest(var0.getBytes("UTF-8"));
            return var2;
        } catch (Exception var3) {
            return null;
        }
    }

    public static String d() {
        String var0 = "";
        byte[] var1 = a("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCxHEhhLwdDhZ57SlLt/5JWFeGRy4r+NKgbA0VwkCXs7p0w1bNgKlRJOzUfjg6kMi4ygkVNNPWrXIhcdMniTbcHmawawLTa3cRM1jNdG0xg808KKNVdDilFHOFQz8AF6cvLDpsWkqzmoe2+64v0zlWbp5EpYxMMRxOwSMVKxLB8BwIDAQAB", "U5VwTnDVppT0puHtkE/Uj3/K4JUu+7l0Qa2Q0BRGCucOyujbPZYFmQEu76sx0fxnXA4OK8TgoaUXWaU3lnWm3skMMel6Q/jRJn8LzsP4FE7Q/g4mTJYU3WqxxRCzRNtQqgtFq/St6ujClGeuaxwIrWUB5HqVbaRoDJoRZ5ecl+0=");

        try {
            var0 = new String(var1, "UTF-8");
        } catch (UnsupportedEncodingException var3) {
            var3.printStackTrace();
        }

        return var0;
    }

    public static String e() {
        String var0 = "";
        byte[] var1 = a("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCxHEhhLwdDhZ57SlLt/5JWFeGRy4r+NKgbA0VwkCXs7p0w1bNgKlRJOzUfjg6kMi4ygkVNNPWrXIhcdMniTbcHmawawLTa3cRM1jNdG0xg808KKNVdDilFHOFQz8AF6cvLDpsWkqzmoe2+64v0zlWbp5EpYxMMRxOwSMVKxLB8BwIDAQAB", "LVuEjlAGl2JzUyxk4if+PICodE5wcpHs59W21jayXfyWVB8e2ctlf8y4gFsp2Df0HEQSpUyd4cBDhGIKr5rNZ7m/60S26z54QCqAIcEpOWBuhDg8OwTVD3WpN8r4jIVxMmUQEEeWY/hdikZKjh8ASrrGoGbNvxxcquPYuyXx1R8=");

        try {
            var0 = new String(var1, "UTF-8");
        } catch (UnsupportedEncodingException var3) {
            var3.printStackTrace();
        }

        return var0;
    }

    public static String f() {
        String var0 = "";
        byte[] var1 = a("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCxHEhhLwdDhZ57SlLt/5JWFeGRy4r+NKgbA0VwkCXs7p0w1bNgKlRJOzUfjg6kMi4ygkVNNPWrXIhcdMniTbcHmawawLTa3cRM1jNdG0xg808KKNVdDilFHOFQz8AF6cvLDpsWkqzmoe2+64v0zlWbp5EpYxMMRxOwSMVKxLB8BwIDAQAB", "HwvseLgXb0ADdzRJc1GjH684ejHzHA2CvsU3DIIfceB1WzQlQ9pk9Xvp8U6X6SZZCXJGfUGO0ufvE6vRC2mNfYtaZBXcissamXmFft+8arpmCHB3NcwPwcWDnFmmjz5vWCWSeFN4pgwkxTzBZCDCedVnQfe69k+ztW4mQ5nkVcQ=");

        try {
            var0 = new String(var1, "UTF-8");
        } catch (UnsupportedEncodingException var3) {
            var3.printStackTrace();
        }

        return var0;
    }

    public static String g() {
        String var0 = "";
        byte[] var1 = a("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCxHEhhLwdDhZ57SlLt/5JWFeGRy4r+NKgbA0VwkCXs7p0w1bNgKlRJOzUfjg6kMi4ygkVNNPWrXIhcdMniTbcHmawawLTa3cRM1jNdG0xg808KKNVdDilFHOFQz8AF6cvLDpsWkqzmoe2+64v0zlWbp5EpYxMMRxOwSMVKxLB8BwIDAQAB", "qeRqo/rXGiHD4rcSAJ07ZlSIT8mKBltrDmOq/XgOoadOr28QK+fSIq/hAwDP3AKQv5eWMHQ+1iHxluJY30jVoyW6UK+2RqCrTx5cDF45/gP+8y5Les0JJUy0tQ7QvNLgRGiM6S4lSyBtBK7TQwJA04pyorNNN0jjaH42k2wP6Wk=");

        try {
            var0 = new String(var1, "UTF-8");
        } catch (UnsupportedEncodingException var3) {
            var3.printStackTrace();
        }

        return var0;
    }

    public static String h() {
        String var0 = "";
        byte[] var1 = a("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCxHEhhLwdDhZ57SlLt/5JWFeGRy4r+NKgbA0VwkCXs7p0w1bNgKlRJOzUfjg6kMi4ygkVNNPWrXIhcdMniTbcHmawawLTa3cRM1jNdG0xg808KKNVdDilFHOFQz8AF6cvLDpsWkqzmoe2+64v0zlWbp5EpYxMMRxOwSMVKxLB8BwIDAQAB", "Hpe2Y2xbC9O5/UTpTy7vo1b/uVB2TlRpshb1pTsOCdR+DJbrjCktyA37LCrUXaeMioqwmwkZqlg8cQKErWjhUKPjDN45o7wcCnYxmToc7rds9LTWGRszoTdzanz7LiIm0b6Nn7yRMZMKnkwYga+PIT9gqUsI0CLscU8XKN7HZcs=");

        try {
            var0 = new String(var1, "UTF-8");
        } catch (UnsupportedEncodingException var3) {
            var3.printStackTrace();
        }

        return var0;
    }
}
