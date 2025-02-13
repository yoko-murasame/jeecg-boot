//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jeecg.designer.commons;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Scanner;
import org.jeecg.designer.commons.a.d;

public class c {
    public static String a;
    private static String b;
    private static String c;
    private static String d;
    private static String[] e = null;

    public c() {
    }

    private static boolean a(String[] var0, String var1) {
        List var2 = Arrays.asList(var0);
        return var2.contains(var1);
    }

    public static String a() {
        return c;
        // if ("".equals(c) || c == null) {
        //     String var0 = "";
        //     var0 = i() + j() + k();
        //     a = h.c(var0);
        //     String[] var1 = f().split("\\|");
        //     if (!var1[1].equals(a.toUpperCase())) {
        //         System.out.println(org.jeecg.designer.commons.a.d.h() + a.toUpperCase());
        //         System.err.println(org.jeecg.designer.commons.a.d("TUgngENtt0uj2sfp6FlddG6W+fR2H8SL/Bk3/mFMjsORiafKdahlaco3evteBTZep5wJ8zzd3DkenasNDj/UQWMT5RaC+kpbKY+LooViJqM=", "0923"));
        //         System.exit(0);
        //     }
        //
        //     c = h.c(a.toUpperCase() + h() + var1[0] + "_ " + var1[1] + "_ " + var1[2] + "20210922");
        // }
        //
        // return c.toUpperCase();
    }

    public static boolean b() {
        // ResourceBundle var0 = g();
        // if (var0 == null) {
        //     var0 = ResourceBundle.getBundle(org.jeecg.designer.commons.a.d.d());
        //     String var1 = var0.getString(org.jeecg.designer.commons.a.d.f());
        //     if ("docker".equals(var1)) {
        //         return true;
        //     }
        // }

        return false;
    }

    public static String c() {
        if ("".equals(c) || c == null) {
            String var0 = "";
            var0 = i() + j() + k();
            a = h.c(var0);
        }

        return a.toUpperCase();
    }

    public static String d() {
        return d;
        // if ("".equals(d) || d == null) {
        //     String var0 = "";
        //     var0 = i() + j() + k();
        //     a = h.c(var0);
        //     String[] var1 = f().split("\\|");
        //     if (!var1[1].equals(a.toUpperCase())) {
        //         System.out.println(org.jeecg.designer.commons.a.d.h() + a.toUpperCase());
        //         System.err.println(org.jeecg.designer.commons.a.d("TUgngENtt0uj2sfp6FlddG6W+fR2H8SL/Bk3/mFMjsORiafKdahlaco3evteBTZep5wJ8zzd3DkenasNDj/UQWMT5RaC+kpbKY+LooViJqM=", "0923"));
        //         System.exit(0);
        //     }
        //
        //     d = h.c(a.toUpperCase() + var1[0] + "_ " + var1[1] + "_ " + var1[2] + "Combating Piracy");
        // }
        //
        // return d.toUpperCase();
    }

    private static String h() {
        SimpleDateFormat var0 = new SimpleDateFormat("yyyy");
        String var1 = var0.format(new Date());
        return var1;
    }

    public static boolean e() {
        Date var0 = new Date();
        String var1 = "2015-04-18";
        SimpleDateFormat var2 = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        Date var3 = null;

        try {
            var3 = var2.parse(var1);
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        boolean var4 = var3.before(var0);
        return var4;
    }

    public static String f() {
        return null;
        // String var0 = "ERROR";
        //
        // try {
        //     ResourceBundle var1 = g();
        //     if (var1 == null) {
        //         var1 = ResourceBundle.getBundle(org.jeecg.designer.commons.a.d.d());
        //     }
        //
        //     String var6 = var1.getString(org.jeecg.designer.commons.a.d.g());
        //     String var2 = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCxHEhhLwdDhZ57SlLt/5JWFeGRy4r+NKgbA0VwkCXs7p0w1bNgKlRJOzUfjg6kMi4ygkVNNPWrXIhcdMniTbcHmawawLTa3cRM1jNdG0xg808KKNVdDilFHOFQz8AF6cvLDpsWkqzmoe2+64v0zlWbp5EpYxMMRxOwSMVKxLB8BwIDAQAB";
        //     byte[] var3 = org.jeecg.designer.commons.a.d.a(var2, var6);
        //     var0 = new String(var3, "UTF-8");
        //     String[] var4 = var0.split("\\|");
        //     if (!var4[1].equals(c())) {
        //         System.out.println(org.jeecg.designer.commons.a.d.h() + c());
        //         System.err.println(org.jeecg.designer.commons.a.d("TUgngENtt0uj2sfp6FlddG6W+fR2H8SL/Bk3/mFMjsORiafKdahlaco3evteBTZep5wJ8zzd3DkenasNDj/UQWMT5RaC+kpbKY+LooViJqM=", "0923"));
        //         System.exit(0);
        //     }
        // } catch (Exception var5) {
        //     System.out.println(org.jeecg.designer.commons.a.d.h() + c());
        //     System.err.println(org.jeecg.designer.commons.a.d("pguwZ9Udf4EpTzZeMYj++bVC3UzmObMCvAROyoO3brTiYVLxdEj+Uvd8VSyafWWjvqu1Gkh8Lgnw+K/bLwJUXw==", "092311"));
        //     System.exit(0);
        // }
        //
        // return var0;
    }

    private static String i() {
        return f.a();
    }

    private static String j() {
        String var0 = "CPUID000";

        try {
            Process var1 = Runtime.getRuntime().exec(new String[]{"wmic", "cpu", "get", "ProcessorId"});
            var1.getOutputStream().close();
            Scanner var2 = new Scanner(var1.getInputStream());
            String var3 = var2.next();
            var0 = var2.next();
        } catch (Exception var4) {
        }

        return var0;
    }

    private static String b(String var0) {
        String var1 = "";

        try {
            File var2 = File.createTempFile("damn", ".vbs");
            var2.deleteOnExit();
            FileWriter var3 = new FileWriter(var2);
            String var4 = "Set objFSO = CreateObject(\"Scripting.FileSystemObject\")\nSet colDrives = objFSO.Drives\nSet objDrive = colDrives.item(\"" + var0 + "\")\nWscript.Echo objDrive.SerialNumber";
            var3.write(var4);
            var3.close();
            Process var5 = Runtime.getRuntime().exec("cscript //NoLogo " + var2.getPath());

            BufferedReader var6;
            String var7;
            for(var6 = new BufferedReader(new InputStreamReader(var5.getInputStream())); (var7 = var6.readLine()) != null; var1 = var1 + var7) {
            }

            var6.close();
        } catch (Exception var8) {
            var8.printStackTrace();
        }

        return var1.trim();
    }

    private static String k() {
        String var0 = "ZBXLH000";

        try {
            File var1 = File.createTempFile("realhowto", ".vbs");
            var1.deleteOnExit();
            FileWriter var2 = new FileWriter(var1);
            String var3 = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\nSet colItems = objWMIService.ExecQuery _ \n   (\"Select * from Win32_BaseBoard\") \nFor Each objItem in colItems \n    Wscript.Echo objItem.SerialNumber \n    exit for  ' do the first cpu only! \nNext \n";
            var2.write(var3);
            var2.close();
            Process var4 = Runtime.getRuntime().exec("cscript //NoLogo " + var1.getPath());

            BufferedReader var5;
            String var6;
            for(var5 = new BufferedReader(new InputStreamReader(var4.getInputStream())); (var6 = var5.readLine()) != null; var0 = var0 + var6) {
            }

            var5.close();
        } catch (Exception var7) {
        }

        return var0.trim();
    }

    private static String l() {
        return b;
        // if (g.c(b)) {
        //     return b;
        // } else {
        //     String var0 = m();
        //     String var1 = q() + "/system32/ipconfig /all";
        //     Object var2 = null;
        //     String var3;
        //     if (var0.startsWith("windows")) {
        //         if (var0.equals("windows xp")) {
        //             var3 = c(var1);
        //         } else if (var0.equals("windows 2003")) {
        //             var3 = c(var1);
        //         } else {
        //             var3 = n();
        //         }
        //     } else if (var0.startsWith("linux")) {
        //         var3 = o();
        //     } else {
        //         var3 = p();
        //     }
        //
        //     b = var3;
        //     return var3;
        // }
    }

    private static String m() {
        return System.getProperty("os.name").toLowerCase();
    }

    private static String c(String var0) {
        String var1 = null;
        BufferedReader var2 = null;
        Object var3 = null;

        try {
            Process var17 = Runtime.getRuntime().exec(var0);
            var2 = new BufferedReader(new InputStreamReader(var17.getInputStream()));
            Object var4 = null;
            int var5 = -1;

            // while((var19 = var2.readLine()) != null) {
            //     if (var19.indexOf("本地连接") == -1) {
            //         var5 = var19.toLowerCase().indexOf("physical address");
            //         if (var5 != -1) {
            //             var5 = var19.indexOf(":");
            //             if (var5 != -1) {
            //                 var1 = var19.substring(var5 + 1).trim();
            //             }
            //             break;
            //         }
            //     }
            // }
        } catch (IOException var14) {
            var14.printStackTrace();
        } finally {
            try {
                if (var2 != null) {
                    var2.close();
                }
            } catch (IOException var13) {
                var13.printStackTrace();
            }

            Object var16 = null;
            var3 = null;
        }

        return var1;
    }

    private static String n() {
        InetAddress var0 = null;

        try {
            var0 = InetAddress.getLocalHost();
        } catch (UnknownHostException var6) {
            var6.printStackTrace();
        }

        byte[] var1 = null;

        try {
            var1 = NetworkInterface.getByInetAddress(var0).getHardwareAddress();
        } catch (SocketException var5) {
        }

        StringBuffer var2 = new StringBuffer();

        for(int var3 = 0; var3 < var1.length; ++var3) {
            if (var3 != 0) {
                var2.append("-");
            }

            String var4 = Integer.toHexString(var1[var3] & 255);
            var2.append(var4.length() == 1 ? 0 + var4 : var4);
        }

        return var2.toString().toUpperCase();
    }

    public static boolean a(String var0) {
        return true;
        // String var1 = "kOzlPKX8Es8/FwQdXX60NZDs5Tyl/BLPPxcEHV1+tDWQ7OU8pfwSzz8XBB1dfrQ1iN5zqKZaPYep6qn8X+n2QLKRXOKp2vFQjVEFS7T75CGCnTDPggguLlLiSRDR+DN00a2paweobc4zkqYP/0gU+d4jODOhQvZ9DFAsE4kQZpsXOzNfRrTeD+fWUnMFtAcuP5wfzEsz9Gm/ENqSW/sf0JQaTCWAxHmqaMpauPUZXNWEiuzRhMaXSDRmD4nV1DOcTqvDj5BtUWWdnJQV+by4VjVceI6gYuC5E3R+m4p6QG4wiASRPe+mpGacCousLwjL6a6Zx+iA2MXhQiPM6WjQTWIWA3TKwu105/ojzopukCuQ7OU8pfwSzz8XBB1dfrQ1kOzlPKX8Es8/FwQdXX60Nb+YHwc5536J89tvlGzFHGI=";
        //
        // try {
        //     if (e == null || e.length == 0) {
        //         ResourceBundle var2 = g();
        //         if (var2 == null) {
        //             var2 = ResourceBundle.getBundle(org.jeecg.designer.commons.a.d.d());
        //         }
        //
        //         if (b()) {
        //             e = new String[]{org.jeecg.designer.commons.a.f.a()};
        //         } else {
        //             e = var2.getString(org.jeecg.designer.commons.a.d.f()).split(",");
        //         }
        //     }
        //
        //     if (!a(e, d()) && !a(e, a())) {
        //         System.out.println(org.jeecg.designer.commons.a.d.h() + c());
        //         String var7 = org.jeecg.designer.commons.b.c(var1, "123456");
        //         System.err.println(var7);
        //         System.exit(0);
        //     }
        // } catch (Exception var6) {
        //     try {
        //         String var3 = org.jeecg.designer.commons.b.c(var1, "123456");
        //         System.err.println(var3);
        //         System.exit(0);
        //     } catch (Exception var5) {
        //         System.exit(0);
        //     }
        // }
        //
        // if (var0 == null) {
        //     return true;
        // } else if (var0.equals("")) {
        //     return true;
        // } else {
        //     return var0.equals("null");
        // }
    }

    private static String o() {
        String var0 = null;
        BufferedReader var1 = null;
        Object var2 = null;

        try {
            Process var16 = Runtime.getRuntime().exec("ifconfig eth0");
            var1 = new BufferedReader(new InputStreamReader(var16.getInputStream()));
            Object var3 = null;
            int var4 = -1;

            // while((var18 = var1.readLine()) != null) {
            //     var4 = var18.toLowerCase().indexOf("硬件地址");
            //     if (var4 != -1) {
            //         var0 = var18.substring(var4 + 4).trim();
            //         break;
            //     }
            // }
        } catch (IOException var13) {
            var13.printStackTrace();
        } finally {
            try {
                if (var1 != null) {
                    var1.close();
                }
            } catch (IOException var12) {
                var12.printStackTrace();
            }

            Object var15 = null;
            var2 = null;
        }

        return var0;
    }

    public static ResourceBundle g() {
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

    private static String p() {
        String var0 = null;
        BufferedReader var1 = null;
        Object var2 = null;

        try {
            Process var16 = Runtime.getRuntime().exec("ifconfig eth0");
            var1 = new BufferedReader(new InputStreamReader(var16.getInputStream()));
            Object var3 = null;
            int var4 = -1;

            // while((var18 = var1.readLine()) != null) {
            //     var4 = var18.toLowerCase().indexOf("hwaddr");
            //     if (var4 != -1) {
            //         var0 = var18.substring(var4 + "hwaddr".length() + 1).trim();
            //         break;
            //     }
            // }
        } catch (IOException var13) {
            var13.printStackTrace();
        } finally {
            try {
                if (var1 != null) {
                    var1.close();
                }
            } catch (IOException var12) {
                var12.printStackTrace();
            }

            Object var15 = null;
            var2 = null;
        }

        return var0;
    }

    private static String q() {
        Object var0 = null;
        Object var1 = null;
        Object var2 = null;
        String var3 = "windir";
        String var10 = System.getProperty("os.name").toLowerCase();
        String var9;
        if (var10.startsWith("windows")) {
            var9 = "cmd /c SET";
        } else {
            var9 = "env";
        }

        try {
            Process var4 = Runtime.getRuntime().exec(var9);
            InputStreamReader var5 = new InputStreamReader(var4.getInputStream());
            BufferedReader var6 = new BufferedReader(var5);
            Object var7 = null;

            // while((var12 = var6.readLine()) != null) {
            //     String var13 = var12.toLowerCase();
            //     if (var13.indexOf(var3) > -1) {
            //         String var11 = var13.substring(var13.indexOf(var3) + var3.length() + 1);
            //         return var11;
            //     }
            // }
        } catch (Exception var8) {
        }

        return null;
    }
}
