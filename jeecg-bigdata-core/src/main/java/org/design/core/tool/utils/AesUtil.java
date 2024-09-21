package org.design.core.tool.utils;

import java.nio.charset.Charset;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.util.Assert;

/* loaded from: bigdata-core-tool-3.1.0.jar:org/design/core/tool/utils/AesUtil.class */
public class AesUtil {
    private AesUtil() {
    }

    public static String genAesKey() {
        return StringUtil.random(32);
    }

    public static byte[] encrypt(byte[] content, String aesTextKey) {
        return encrypt(content, aesTextKey.getBytes(Charsets.UTF_8));
    }

    public static byte[] encrypt(String content, String aesTextKey) {
        return encrypt(content.getBytes(Charsets.UTF_8), aesTextKey.getBytes(Charsets.UTF_8));
    }

    public static byte[] encrypt(String content, Charset charset, String aesTextKey) {
        return encrypt(content.getBytes(charset), aesTextKey.getBytes(Charsets.UTF_8));
    }

    public static byte[] decrypt(byte[] content, String aesTextKey) {
        return decrypt(content, aesTextKey.getBytes(Charsets.UTF_8));
    }

    public static String decryptToStr(byte[] content, String aesTextKey) {
        return new String(decrypt(content, aesTextKey.getBytes(Charsets.UTF_8)), Charsets.UTF_8);
    }

    public static String decryptToStr(byte[] content, String aesTextKey, Charset charset) {
        return new String(decrypt(content, aesTextKey.getBytes(Charsets.UTF_8)), charset);
    }

    public static byte[] encrypt(byte[] content, byte[] aesKey) {
        Assert.isTrue(aesKey.length == 32, "IllegalAesKey, aesKey's length must be 32");
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            cipher.init(1, new SecretKeySpec(aesKey, "AES"), new IvParameterSpec(aesKey, 0, 16));
            return cipher.doFinal(Pkcs7Encoder.encode(content));
        } catch (Exception e) {
            throw Exceptions.unchecked(e);
        }
    }

    public static byte[] decrypt(byte[] encrypted, byte[] aesKey) {
        Assert.isTrue(aesKey.length == 32, "IllegalAesKey, aesKey's length must be 32");
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            cipher.init(2, new SecretKeySpec(aesKey, "AES"), new IvParameterSpec(Arrays.copyOfRange(aesKey, 0, 16)));
            return Pkcs7Encoder.decode(cipher.doFinal(encrypted));
        } catch (Exception e) {
            throw Exceptions.unchecked(e);
        }
    }

    /* access modifiers changed from: package-private */
    /* loaded from: bigdata-core-tool-3.1.0.jar:org/design/core/tool/utils/AesUtil$Pkcs7Encoder.class */
    public static class Pkcs7Encoder {
        static int BLOCK_SIZE = 32;

        Pkcs7Encoder() {
        }

        static byte[] encode(byte[] src) {
            int count = src.length;
            int amountToPad = BLOCK_SIZE - (count % BLOCK_SIZE);
            if (amountToPad == 0) {
                amountToPad = BLOCK_SIZE;
            }
            byte pad = (byte) (amountToPad & 255);
            byte[] pads = new byte[amountToPad];
            for (int index = 0; index < amountToPad; index++) {
                pads[index] = pad;
            }
            byte[] dest = new byte[count + amountToPad];
            System.arraycopy(src, 0, dest, 0, count);
            System.arraycopy(pads, 0, dest, count, amountToPad);
            return dest;
        }

        static byte[] decode(byte[] decrypted) {
            int pad = decrypted[decrypted.length - 1];
            if (pad < 1 || pad > BLOCK_SIZE) {
                pad = 0;
            }
            if (pad > 0) {
                return Arrays.copyOfRange(decrypted, 0, decrypted.length - pad);
            }
            return decrypted;
        }
    }
}
