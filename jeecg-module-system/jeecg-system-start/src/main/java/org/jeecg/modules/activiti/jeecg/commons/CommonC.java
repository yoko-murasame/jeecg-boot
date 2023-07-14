package org.jeecg.modules.activiti.jeecg.commons;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//
import java.security.MessageDigest;

public class CommonC {
    private static final String[] a = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    public CommonC() {
    }

    private static String a(byte[] var0) {
        StringBuffer var1 = new StringBuffer();

        for(int var2 = 0; var2 < var0.length; ++var2) {
            var1.append(a(var0[var2]));
        }

        return var1.toString();
    }

    private static String a(byte var0) {
        int var1 = var0;
        if (var0 < 0) {
            var1 = var0 + 256;
        }

        int var2 = var1 / 16;
        int var3 = var1 % 16;
        return a[var2] + a[var3];
    }

    public static String a(String var0, String var1) {
        String var2 = null;

        try {
            var2 = new String(var0);
            MessageDigest var3 = MessageDigest.getInstance("MD5");
            if (var1 != null && !"".equals(var1)) {
                var2 = a(var3.digest(var2.getBytes(var1)));
            } else {
                var2 = a(var3.digest(var2.getBytes()));
            }
        } catch (Exception var4) {
        }

        return var2;
    }
}
