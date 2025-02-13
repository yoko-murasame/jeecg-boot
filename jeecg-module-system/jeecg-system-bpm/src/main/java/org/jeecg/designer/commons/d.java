//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jeecg.designer.commons;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class d {
    static final int a = 4096;
    private static String[] b;

    public d() {
    }

    public static String a(InputStream var0) {
        ByteArrayOutputStream var1 = new ByteArrayOutputStream();
        byte[] var2 = new byte[4096];
        String var3 = null;
        int var4 = 0;

        try {
            while((var4 = var0.read(var2, 0, 4096)) != -1) {
                var1.write(var2, 0, var4);
            }
        } catch (IOException var7) {
            var7.printStackTrace();
        }

        Object var8 = null;

        try {
            var3 = new String(var1.toByteArray(), "UTF-8");
        } catch (UnsupportedEncodingException var6) {
            var6.printStackTrace();
        }

        return var3;
    }

    public static String a(InputStream var0, String var1) {
        String var2 = null;
        ByteArrayOutputStream var3 = new ByteArrayOutputStream();
        byte[] var4 = new byte[4096];
        int var5 = -1;

        try {
            while((var5 = var0.read(var4, 0, 4096)) != -1) {
                var3.write(var4, 0, var5);
            }
        } catch (IOException var8) {
            var8.printStackTrace();
        }

        Object var9 = null;

        try {
            var2 = new String(var3.toByteArray(), var1);
        } catch (UnsupportedEncodingException var7) {
            var7.printStackTrace();
        }

        return var2;
    }

    public static InputStream a(String var0) throws Exception {
        ByteArrayInputStream var1 = new ByteArrayInputStream(var0.getBytes("UTF-8"));
        return var1;
    }

    public static byte[] b(String var0) {
        byte[] var1 = null;

        try {
            var1 = b(a(var0));
        } catch (IOException var3) {
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return var1;
    }

    public static byte[] b(InputStream var0) throws IOException {
        ByteArrayOutputStream var1 = new ByteArrayOutputStream();
        byte[] var2 = new byte[4096];
        int var3 = -1;

        while((var3 = var0.read(var2, 0, 4096)) != -1) {
            var1.write(var2, 0, var3);
        }

        Object var4 = null;
        return var1.toByteArray();
    }

    public static InputStream a(byte[] var0) throws Exception {
        ByteArrayInputStream var1 = new ByteArrayInputStream(var0);
        return var1;
    }

    public static String b(byte[] var0) {
        InputStream var1 = null;

        try {
            var1 = a(var0);
        } catch (Exception var3) {
            var3.printStackTrace();
        }

        return a(var1, "UTF-8");
    }

    public static String c(String var0) {
        String var1 = null;

        try {
            var1 = b(b(var0));
        } catch (Exception var3) {
            var3.printStackTrace();
        }

        return var1;
    }

    public byte[] c(InputStream var1) throws IOException {
        ByteArrayOutputStream var2 = new ByteArrayOutputStream();
        byte[] var3 = new byte[4096];
        int var4 = 0;

        while((var4 = var1.read(var3, 0, 4096)) != -1) {
            var2.write(var3, 0, var4);
        }

        var2.flush();
        byte[] var5 = var2.toByteArray();
        return var5;
    }

    public static FileInputStream d(String var0) {
        FileInputStream var1 = null;

        try {
            var1 = new FileInputStream(var0);
        } catch (FileNotFoundException var3) {
            System.out.print("错误信息:文件不存在");
            var3.printStackTrace();
        }

        return var1;
    }

    private static ResourceBundle d() {
        PropertyResourceBundle var0 = null;
        // String var2 = System.getProperty("user.dir") + File.separator + "config" + File.separator + org.jeecg.designer.commons.a.d.e();
        //
        // try {
        //     BufferedInputStream var1 = new BufferedInputStream(new FileInputStream(var2));
        //     var0 = new PropertyResourceBundle(var1);
        //     var1.close();
        // } catch (FileNotFoundException var4) {
        // } catch (IOException var5) {
        // }

        return var0;
    }

    public static FileInputStream a(File var0) {
        FileInputStream var1 = null;

        try {
            var1 = new FileInputStream(var0);
        } catch (FileNotFoundException var3) {
            System.out.print("错误信息:文件不存在");
            var3.printStackTrace();
        }

        return var1;
    }

    public static FileOutputStream a(File var0, boolean var1) {
        FileOutputStream var2 = null;

        try {
            var2 = new FileOutputStream(var0, var1);
        } catch (FileNotFoundException var4) {
            System.out.print("错误信息:文件不存在");
            var4.printStackTrace();
        }

        return var2;
    }

    public static void a() {
        // String var0 = "kOzlPKX8Es8/FwQdXX60NZDs5Tyl/BLPPxcEHV1+tDWQ7OU8pfwSzz8XBB1dfrQ1iN5zqKZaPYep6qn8X+n2QLKRXOKp2vFQjVEFS7T75CGCnTDPggguLlLiSRDR+DN00a2paweobc4zkqYP/0gU+d4jODOhQvZ9DFAsE4kQZpsXOzNfRrTeD+fWUnMFtAcuP5wfzEsz9Gm/ENqSW/sf0JQaTCWAxHmqaMpauPUZXNWEiuzRhMaXSDRmD4nV1DOcTqvDj5BtUWWdnJQV+by4VjVceI6gYuC5E3R+m4p6QG4wiASRPe+mpGacCousLwjL6a6Zx+iA2MXhQiPM6WjQTWIWA3TKwu105/ojzopukCuQ7OU8pfwSzz8XBB1dfrQ1kOzlPKX8Es8/FwQdXX60Nb+YHwc5536J89tvlGzFHGI=";
        //
        // try {
        //     if (b == null || b.length == 0) {
        //         ResourceBundle var1 = d();
        //         if (var1 == null) {
        //             var1 = ResourceBundle.getBundle(org.jeecg.designer.commons.a.d.d());
        //         }
        //
        //         if (c.b()) {
        //             b = new String[]{f.a()};
        //         } else {
        //             b = var1.getString(org.jeecg.designer.commons.a.d.f()).split(",");
        //         }
        //     }
        //
        //     if (!a(b, c.d()) && !a(b, c.a())) {
        //         System.out.println(org.jeecg.designer.commons.a.d.h() + c.c());
        //         String var6 = org.jeecg.designer.commons.a.c(var0, "123456");
        //         System.err.println(var6);
        //         System.exit(0);
        //     }
        // } catch (Exception var5) {
        //     try {
        //         String var2 = org.jeecg.designer.commons.a.c(var0, "123456");
        //         System.err.println(var2);
        //         System.exit(0);
        //     } catch (Exception var4) {
        //     }
        // }

    }

    public static FileOutputStream a(String var0, boolean var1) {
        FileOutputStream var2 = null;

        try {
            var2 = new FileOutputStream(var0, var1);
        } catch (FileNotFoundException var4) {
            System.out.print("错误信息:文件不存在");
            var4.printStackTrace();
        }

        return var2;
    }

    public static boolean b() {
        // ResourceBundle var0 = c.g();
        // if (var0 == null) {
        //     var0 = ResourceBundle.getBundle(org.jeecg.designer.commons.a.d.d());
        //     String var1 = var0.getString(org.jeecg.designer.commons.a.d.f());
        //     if ("docker".equals(var1)) {
        //         return true;
        //     }
        // }

        return false;
    }

    private static boolean a(String[] var0, String var1) {
        List var2 = Arrays.asList(var0);
        return var2.contains(var1);
    }

    public static File e(String var0) {
        return new File(var0);
    }

    public static ByteArrayOutputStream c() {
        return new ByteArrayOutputStream();
    }

    static {
        // a();
        b = null;
    }
}
