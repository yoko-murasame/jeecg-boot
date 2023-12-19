package org.jeecg.modules.online.cgform.d;

import javax.crypto.Cipher;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/* compiled from: MachineRsaUtil.java */
/* loaded from: hibernate-common-ol-5.4.74(2).jar:org/jeecg/modules/online/cgform/d/g.class */
public class g {
    private static final String stra = "CSX+J5DZ16LFy3/Uj6rZg+5OEcZ8gkcAw2rrt/NYr1IMy8TsDm7z11ID8/FJLyyadfzUuMnV9zcpN0u4QI1FiuZDdkNF5stf7blkZyY17mAwT33bADoyDvw7EsXWo2xE9J1r3rwdndi/73ygeW4GBbb+W5/r+ckECGjdwpp8maA=";
    private static final String strb = "h0iOcPq0oEL0asJ0OSc+jWDgikWjbPdXr8ihKSvMrRs8Q0V7MVhA1gG/xK2b7/b12cRiMTIQYkZWcnEBSMAUAW/SsDpQpZz8fNjIaVgxHcoQ+xvHbg9b+3OwHMzyOvrOWRDHoDTUzJ0mfrXEo1DAo9lkFRbsMAtigMvFAzl42C0=";
    private static final String strc = "eCLYNuaRrlruM843wkli2BKwuH4mWzmHv0x+owiHVOp4aR05F+i0IujP8L9eUeuHNej8xbgXPJhj6MQHt+JMX20BIB2dEi8zgSOEvgkzm59Z5LL5VQGWR1U2e2awMRpHZ2Fa5pBJTuaBPRXy9fjCzLM/DP5W/q6/nFBYvIA1UYI=";
    private static final String strd = "ktbzFlOkfUKQ9gEgtUEymZ7lhnoT3pmPblhICfJaoCX4H6MqPpe9f0gB2DisXRWrH6o2XJ4BoRH7Y+N6LUCURerNNh4+uZqO2yDnlkbhR7xDgR38IM8Z10nc1vkwAIte9qoa8+JWzUYe5EokZ0VYukI4UALGbwl6dfYrzCWIN2w=";
    private static final String stre = "f9LjNkXlpn9Aw1XKTkgpjd0DhveOx4ZqmFzR+c2VvZVjrtKwKUhlNpPuiRVInaSUOTaYXBLt/Hyp7TecreO0GT75r92DLqzH6Ak0srm3p+hnFLdqw/YSDVz7WM5W0h6xlU4hDNQ2k8q1IsdVY8D/XpdTIS5Rf1edIsz8ALpgvpg=";
    private static final String strf = "v6yjGWISezwJHfIAbbEi9Ty/0tSVXGmcC6F/daHjST6xeOV/SH2WIjdmgwTQgtAabiYt7cijsXXjzSBBjuK/nqv6v3b3fiCfRNEvkWI7gFcgek9fM/jwfNMeNXZIZ+K/rW9D2JtEOojLYRMD6lz7YZi7xGDhpOVh+hL8ABztt9qbI/tZ5ArrW25w1DsnQrv84mRfHJN2UBVGjNzKpCeHYDt24bJ3kLGHT7vAizB0S7PCgjzcA5Re/JrDg/3fcvySlP7dj/0JKRto4X3po5VTh1jR15xM28Ivvm4uKKpArAU=";

    public static byte[] a(byte[] bArr, byte[] bArr2) {
        try {
            PrivateKey generatePrivate = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(bArr));
            Signature signature = Signature.getInstance("SHA1withRSA");
            signature.initSign(generatePrivate);
            signature.update(bArr2);
            return signature.sign();
        } catch (Exception e2) {
            return null;
        }
    }

    public static boolean a(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            Signature signature = Signature.getInstance("SHA1withRSA");
            signature.initVerify(keyFactory.generatePublic(new X509EncodedKeySpec(bArr)));
            signature.update(bArr2);
            return signature.verify(bArr3);
        } catch (Exception e2) {
            return false;
        }
    }

    public static Object[] a() {
        KeyPair c2 = c();
        if (c2 == null) {
            return null;
        }
        Object[] objArr = new Object[2];
        if (c2 != null) {
            byte[] encoded = c2.getPrivate().getEncoded();
            byte[] encoded2 = c2.getPublic().getEncoded();
            objArr[0] = encoded;
            objArr[1] = encoded2;
            return objArr;
        }
        return null;
    }

    public static String[] b() {
        KeyPair c2 = c();
        if (c2 == null) {
            return null;
        }
        String[] strArr = new String[2];
        if (c2 != null) {
            String encodeToString = Base64.getEncoder().encodeToString(c2.getPrivate().getEncoded());
            String encodeToString2 = Base64.getEncoder().encodeToString(c2.getPublic().getEncoded());
            strArr[0] = encodeToString;
            strArr[1] = encodeToString2;
            return strArr;
        }
        return null;
    }

    public static KeyPair c() {
        long currentTimeMillis = System.currentTimeMillis();
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG", "SUN");
            secureRandom.setSeed(currentTimeMillis);
            keyPairGenerator.initialize(1024, secureRandom);
            return keyPairGenerator.generateKeyPair();
        } catch (Exception e2) {
            return null;
        }
    }

    public static byte[] b(byte[] bArr, byte[] bArr2) {
        try {
            PublicKey generatePublic = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(bArr));
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(1, generatePublic);
            return cipher.doFinal(bArr2);
        } catch (Exception e2) {
            return null;
        }
    }

    public static byte[] c(byte[] bArr, byte[] bArr2) {
        try {
            PrivateKey generatePrivate = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(bArr));
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(2, generatePrivate);
            return cipher.doFinal(bArr2);
        } catch (Exception e2) {
            return null;
        }
    }

    public static byte[] d(byte[] bArr, byte[] bArr2) {
        try {
            PKCS8EncodedKeySpec pKCS8EncodedKeySpec = new PKCS8EncodedKeySpec(bArr);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey generatePrivate = keyFactory.generatePrivate(pKCS8EncodedKeySpec);
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(1, generatePrivate);
            return cipher.doFinal(bArr2);
        } catch (Exception e2) {
            return null;
        }
    }

    public static byte[] a(String str, byte[] bArr) {
        try {
            PKCS8EncodedKeySpec pKCS8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(str));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey generatePrivate = keyFactory.generatePrivate(pKCS8EncodedKeySpec);
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(1, generatePrivate);
            return cipher.doFinal(bArr);
        } catch (Exception e2) {
            return null;
        }
    }

    public static byte[] e(byte[] bArr, byte[] bArr2) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey generatePublic = keyFactory.generatePublic(new X509EncodedKeySpec(bArr));
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(2, generatePublic);
            return cipher.doFinal(bArr2);
        } catch (Exception e2) {
            return null;
        }
    }

    public static byte[] b(String str, byte[] bArr) {
        try {
            byte[] decode = Base64.getDecoder().decode(str);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey generatePublic = keyFactory.generatePublic(new X509EncodedKeySpec(decode));
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(2, generatePublic);
            return cipher.doFinal(bArr);
        } catch (Exception e2) {
            return null;
        }
    }

    public static byte[] a(String str, String str2) {
        try {
            byte[] decode = Base64.getDecoder().decode(str2);
            byte[] decode2 = Base64.getDecoder().decode(str);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey generatePublic = keyFactory.generatePublic(new X509EncodedKeySpec(decode2));
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(2, generatePublic);
            return cipher.doFinal(decode);
        } catch (Exception e2) {
            return null;
        }
    }

    public static byte[] a(String str) {
        try {
            return MessageDigest.getInstance("SHA").digest(str.getBytes("UTF-8"));
        } catch (Exception e2) {
            return null;
        }
    }

    public static String d() {
        String str = "";
        try {
            str = new String(a(i(), stra), "UTF-8");
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
        }
        return str.substring(0, str.indexOf(b.DOT_STRING));
    }

    public static String e() {
        String str = "";
        try {
            str = new String(a(i(), strb), "UTF-8");
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
        }
        return str.substring(0, str.indexOf(b.DOT_STRING));
    }

    public static String f() {
        String str = "";
        try {
            str = new String(a(i(), strc), "UTF-8");
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
        }
        return str.substring(0, str.indexOf(b.DOT_STRING));
    }

    public static String g() {
        String str = "";
        try {
            str = new String(a(i(), strd), "UTF-8");
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
        }
        return str.substring(0, str.indexOf(b.DOT_STRING));
    }

    public static String h() {
        String str = "";
        try {
            str = new String(a(i(), stre), "UTF-8");
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
        }
        return str.substring(0, str.indexOf(b.DOT_STRING));
    }

    public static String i() {
        try {
            return f.c(strf, "olc");
        } catch (Exception e2) {
            return null;
        }
    }

    public static String j() {
        try {
            return f.c("UK7uWeAmszT3tZrTdKi7RlCu7lngJrM097Wa03Sou0ZQru5Z4CazNPe1mtN0qLtG1wWMTTIS7X9Lms7hddw8pLTeQ0Vr7e2xsiyEaXOU1PVMPvFdOw9ZGrSAWV7+Vbf6Mgc8wKNeAlWxqlIeonl++WzPE1GgxMjWxlNkoExxH0PluOO+E2WeZ5BojNUfMwLgvyZMfFGjpWkplwgWqMY3Z6gI9uGP8ivLPZWJzlgyJ9Gg4MTS5UxNHD+pyYic0V7PDBdzRNo72OySOHOikcRQBdJu+Yt5bbbakhNPn2Spd5bdf0zoUzHqSU8bfECvSUdYTOSWqot6pa5MeHoSbKyyxwRFL+3bQ1dh27kwZNPtGlVQru5Z4CazNPe1mtN0qLtGUK7uWeAmszT3tZrTdKi7RoH7X6peq5loaodX/iFBoBc=", "l3");
        } catch (Exception e2) {
            return null;
        }
    }
}
