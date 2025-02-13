//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jeecg.modules.online.desform.b;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Scanner;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import org.jeecgframework.poi.util.MyX509TrustManager;

public class i {
    private static String a;
    private static String b;
    private static String c;

    public i() {
    }

    private static String e() {
        return null;
        // String var0 = "ERROR";
        //
        // try {
        //     Object var1 = null;
        //
        //     try {
        //         String var3 = System.getProperty("user.dir") + File.separator + "config" + File.separator + h.e();
        //         BufferedInputStream var2 = new BufferedInputStream(new FileInputStream(var3));
        //         var1 = new PropertyResourceBundle(var2);
        //         var2.close();
        //     } catch (IOException var4) {
        //     }
        //
        //     if (var1 == null) {
        //         var1 = ResourceBundle.getBundle(h.d());
        //     }
        //
        //     String var6 = ((ResourceBundle)var1).getString(h.g());
        //     byte[] var7 = h.a(h.i(), var6);
        //     var0 = new String(var7, "UTF-8");
        //     String[] var8 = var0.split("\\|");
        //     if (!var8[1].equals(c())) {
        //         System.out.println(h.h() + c());
        //         System.err.println(g.d("PT6fjZBhw1iUFkSPqBqOUKZQYx+dBuoP7V/7OVt4JbJwDAAPnQTMV/iPp+jlKEzDcxjwN+9hwyTZ4rWB++Rmy/nkYa9AfUIJ3lx2GlIEL6I=", "bpm56"));
        //         System.exit(0);
        //     }
        // } catch (Exception var5) {
        //     System.out.println(h.h() + c());
        //     System.err.println(g.d("cXOssg0bPpUm6/yeK/bPE7U63FDMOQ95i6uJeH0gN9vcs4qeF82uHBdThVR7zOMPH4EKQ9Ctj/VxeSQSJ/bF3w==", "bpm13"));
        //     System.exit(0);
        // }
        //
        // return var0;
    }

    public static String a() {
        return b;
        // if ("".equals(b) || b == null) {
        //     String var0 = "";
        //     var0 = i() + f() + g();
        //     a = a(var0);
        //     String[] var1 = e().split("\\|");
        //     if (!var1[1].equals(a.toUpperCase())) {
        //         System.out.println(h.h() + a.toUpperCase());
        //         System.err.println(g.d("PT6fjZBhw1iUFkSPqBqOUKZQYx+dBuoP7V/7OVt4JbJwDAAPnQTMV/iPp+jlKEzDcxjwN+9hwyTZ4rWB++Rmy/nkYa9AfUIJ3lx2GlIEL6I=", "bpm56"));
        //         System.exit(0);
        //     }
        //
        //     b = a(a.toUpperCase() + h() + var1[0] + "_ " + var1[1] + "_ " + var1[2] + "20210922");
        // }
        //
        // return b.toUpperCase();
    }

    public static String b() {
        return c;
        // if ("".equals(c) || c == null) {
        //     String var0 = "";
        //     var0 = i() + f() + g();
        //     a = a(var0);
        //     String[] var1 = e().split("\\|");
        //     if (!var1[1].equals(a.toUpperCase())) {
        //         System.out.println(h.h() + a.toUpperCase());
        //         System.err.println(g.d("PT6fjZBhw1iUFkSPqBqOUKZQYx+dBuoP7V/7OVt4JbJwDAAPnQTMV/iPp+jlKEzDcxjwN+9hwyTZ4rWB++Rmy/nkYa9AfUIJ3lx2GlIEL6I=", "bpm56"));
        //         System.exit(0);
        //     }
        //
        //     c = a(a.toUpperCase() + var1[0] + "_ " + var1[1] + "_ " + var1[2] + "Combating Piracy");
        // }
        //
        // return c.toUpperCase();
    }

    private static String a(String var0) {
        MessageDigest var1 = null;

        try {
            var1 = MessageDigest.getInstance("MD5");
            var1.reset();
            var1.update(var0.getBytes("UTF-8"));
        } catch (Exception var5) {
        }

        byte[] var2 = var1.digest();
        StringBuffer var3 = new StringBuffer();

        for(int var4 = 0; var4 < var2.length; ++var4) {
            if (Integer.toHexString(255 & var2[var4]).length() == 1) {
                var3.append("0").append(Integer.toHexString(255 & var2[var4]));
            } else {
                var3.append(Integer.toHexString(255 & var2[var4]));
            }
        }

        return var3.toString();
    }

    public static String c() {
        return a;
        // if ("".equals(a) || a == null) {
        //     String var0 = "";
        //     var0 = i() + f() + g();
        //     a = a(var0);
        // }
        //
        // return a.toUpperCase();
    }

    private static String f() {
        String var0 = "CPUID000";

        // try {
        //     Process var1 = Runtime.getRuntime().exec(new String[]{"wmic", "cpu", "get", "ProcessorId"});
        //     var1.getOutputStream().close();
        //     Scanner var2 = new Scanner(var1.getInputStream());
        //     String var3 = var2.next();
        //     var0 = var2.next();
        // } catch (Exception var4) {
        // }

        return var0;
    }

    private static String b(String var0) {
        String var1 = "";

        // try {
        //     File var2 = File.createTempFile("damn", ".vbs");
        //     var2.deleteOnExit();
        //     FileWriter var3 = new FileWriter(var2);
        //     String var4 = "Set objFSO = CreateObject(\"Scripting.FileSystemObject\")\nSet colDrives = objFSO.Drives\nSet objDrive = colDrives.item(\"" + var0 + "\")\nWscript.Echo objDrive.SerialNumber";
        //     var3.write(var4);
        //     var3.close();
        //     Process var5 = Runtime.getRuntime().exec("cscript //NoLogo " + var2.getPath());
        //
        //     BufferedReader var6;
        //     String var7;
        //     for(var6 = new BufferedReader(new InputStreamReader(var5.getInputStream())); (var7 = var6.readLine()) != null; var1 = var1 + var7) {
        //     }
        //
        //     var6.close();
        // } catch (Exception var8) {
        //     var8.printStackTrace();
        // }

        return var1.trim();
    }

    private static String g() {
        String var0 = "ZBXLH000";

        // try {
        //     File var1 = File.createTempFile("realhowto", ".vbs");
        //     var1.deleteOnExit();
        //     FileWriter var2 = new FileWriter(var1);
        //     String var3 = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\nSet colItems = objWMIService.ExecQuery _ \n   (\"Select * from Win32_BaseBoard\") \nFor Each objItem in colItems \n    Wscript.Echo objItem.SerialNumber \n    exit for  ' do the first cpu only! \nNext \n";
        //     var2.write(var3);
        //     var2.close();
        //     Process var4 = Runtime.getRuntime().exec("cscript //NoLogo " + var1.getPath());
        //
        //     BufferedReader var5;
        //     String var6;
        //     for(var5 = new BufferedReader(new InputStreamReader(var4.getInputStream())); (var6 = var5.readLine()) != null; var0 = var0 + var6) {
        //     }
        //
        //     var5.close();
        // } catch (Exception var7) {
        // }

        return var0.trim();
    }

    private static String h() {
        SimpleDateFormat var0 = new SimpleDateFormat("yyyy");
        String var1 = var0.format(new Date());
        return var1;
    }

    public static boolean d() {
        return true;
        // Date var0 = new Date();
        // String var1 = "2015-04-18";
        // SimpleDateFormat var2 = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        // Date var3 = null;
        //
        // try {
        //     var3 = var2.parse(var1);
        // } catch (Exception var5) {
        //     var5.printStackTrace();
        // }
        //
        // boolean var4 = var3.before(var0);
        // return var4;
    }

    private static String i() {
        return j();
    }

    private static String j() {
        return null;
        // String var0 = m();
        // Object var1 = null;
        // String var2;
        // if (var0.startsWith("windows")) {
        //     var2 = k();
        // } else {
        //     var2 = l();
        // }
        //
        // return var2;
    }

    private static String k() {
        return null;
        // String var0 = "";
        // Object var2 = null;
        //
        // try {
        //     InetAddress var1 = InetAddress.getLocalHost();
        //     var9 = NetworkInterface.getByInetAddress(var1).getHardwareAddress();
        // } catch (Exception var7) {
        //     return null;
        // }
        //
        // StringBuffer var3 = new StringBuffer("");
        // if (var9 == null) {
        //     return null;
        // } else {
        //     for(int var4 = 0; var4 < var9.length; ++var4) {
        //         if (var4 != 0) {
        //             var3.append("-");
        //         }
        //
        //         int var5 = var9[var4] & 255;
        //         String var6 = Integer.toHexString(var5);
        //         if (var6.length() == 1) {
        //             var3.append("0" + var6);
        //         } else {
        //             var3.append(var6);
        //         }
        //     }
        //
        //     var0 = var3.toString().toUpperCase();
        //     if ("".equals(var0)) {
        //         return null;
        //     } else {
        //         return var0;
        //     }
        // }
    }

    private static String l() {
        return null;
        // String var0 = "";
        //
        // try {
        //     Enumeration var1 = NetworkInterface.getNetworkInterfaces();
        //
        //     while(var1.hasMoreElements()) {
        //         byte[] var2 = ((NetworkInterface)var1.nextElement()).getHardwareAddress();
        //         if (var2 != null) {
        //             StringBuilder var3 = new StringBuilder();
        //
        //             for(byte var7 : var2) {
        //                 var3.append(a(var7));
        //                 var3.append("-");
        //             }
        //
        //             var3.deleteCharAt(var3.length() - 1);
        //             var0 = var3.toString().toUpperCase();
        //         }
        //     }
        // } catch (Exception var8) {
        //     var8.printStackTrace();
        // }
        //
        // return var0;
    }

    private static String a(byte var0) {
        // String var1 = "000000" + Integer.toHexString(var0);
        // return var1.substring(var1.length() - 2);
        return null;
    }

    private static String m() {
        return System.getProperty("os.name").toLowerCase();
    }

    private static String c(String var0) {
        return null;
        // String var1 = null;
        // BufferedReader var2 = null;
        // Object var3 = null;
        //
        // try {
        //     Process var17 = Runtime.getRuntime().exec(var0);
        //     var2 = new BufferedReader(new InputStreamReader(var17.getInputStream()));
        //     Object var4 = null;
        //     int var5 = -1;
        //
        //     while((var19 = var2.readLine()) != null) {
        //         if (var19.indexOf("本地连接") == -1) {
        //             var5 = var19.toLowerCase().indexOf("physical address");
        //             if (var5 != -1) {
        //                 var5 = var19.indexOf(":");
        //                 if (var5 != -1) {
        //                     var1 = var19.substring(var5 + 1).trim();
        //                 }
        //                 break;
        //             }
        //         }
        //     }
        // } catch (IOException var14) {
        //     var14.printStackTrace();
        // } finally {
        //     try {
        //         if (var2 != null) {
        //             var2.close();
        //         }
        //     } catch (IOException var13) {
        //         var13.printStackTrace();
        //     }
        //
        //     Object var16 = null;
        //     var3 = null;
        // }
        //
        // return var1;
    }

    private static String n() {
        return null;
        // InetAddress var0 = null;
        //
        // try {
        //     var0 = InetAddress.getLocalHost();
        // } catch (UnknownHostException var6) {
        //     var6.printStackTrace();
        // }
        //
        // byte[] var1 = null;
        //
        // try {
        //     var1 = NetworkInterface.getByInetAddress(var0).getHardwareAddress();
        // } catch (SocketException var5) {
        // }
        //
        // StringBuffer var2 = new StringBuffer();
        //
        // for(int var3 = 0; var3 < var1.length; ++var3) {
        //     if (var3 != 0) {
        //         var2.append("-");
        //     }
        //
        //     String var4 = Integer.toHexString(var1[var3] & 255);
        //     var2.append(var4.length() == 1 ? 0 + var4 : var4);
        // }
        //
        // return var2.toString().toUpperCase();
    }

    private static String o() {
        return null;
        // String var0 = null;
        // BufferedReader var1 = null;
        // Object var2 = null;
        //
        // try {
        //     Process var16 = Runtime.getRuntime().exec("ifconfig eth0");
        //     var1 = new BufferedReader(new InputStreamReader(var16.getInputStream()));
        //     Object var3 = null;
        //     int var4 = -1;
        //
        //     while((var18 = var1.readLine()) != null) {
        //         var4 = var18.toLowerCase().indexOf("硬件地址");
        //         if (var4 != -1) {
        //             var0 = var18.substring(var4 + 4).trim();
        //             break;
        //         }
        //     }
        // } catch (IOException var13) {
        //     var13.printStackTrace();
        // } finally {
        //     try {
        //         if (var1 != null) {
        //             var1.close();
        //         }
        //     } catch (IOException var12) {
        //         var12.printStackTrace();
        //     }
        //
        //     Object var15 = null;
        //     var2 = null;
        // }
        //
        // return var0;
    }

    private static String p() {
        return null;
        // String var0 = null;
        // BufferedReader var1 = null;
        // Object var2 = null;
        //
        // try {
        //     Process var16 = Runtime.getRuntime().exec("ifconfig eth0");
        //     var1 = new BufferedReader(new InputStreamReader(var16.getInputStream()));
        //     Object var3 = null;
        //     int var4 = -1;
        //
        //     while((var18 = var1.readLine()) != null) {
        //         var4 = var18.toLowerCase().indexOf("hwaddr");
        //         if (var4 != -1) {
        //             var0 = var18.substring(var4 + "hwaddr".length() + 1).trim();
        //             break;
        //         }
        //     }
        // } catch (IOException var13) {
        //     var13.printStackTrace();
        // } finally {
        //     try {
        //         if (var1 != null) {
        //             var1.close();
        //         }
        //     } catch (IOException var12) {
        //         var12.printStackTrace();
        //     }
        //
        //     Object var15 = null;
        //     var2 = null;
        // }
        //
        // return var0;
    }

    private static String q() {
        // Object var0 = null;
        // Object var1 = null;
        // Object var2 = null;
        // String var3 = "windir";
        // String var10 = System.getProperty("os.name").toLowerCase();
        // String var9;
        // if (var10.startsWith("windows")) {
        //     var9 = "cmd /c SET";
        // } else {
        //     var9 = "env";
        // }
        //
        // try {
        //     Process var4 = Runtime.getRuntime().exec(var9);
        //     InputStreamReader var5 = new InputStreamReader(var4.getInputStream());
        //     BufferedReader var6 = new BufferedReader(var5);
        //     Object var7 = null;
        //
        //     while((var12 = var6.readLine()) != null) {
        //         String var13 = var12.toLowerCase();
        //         if (var13.indexOf(var3) > -1) {
        //             String var11 = var13.substring(var13.indexOf(var3) + var3.length() + 1);
        //             return var11;
        //         }
        //     }
        // } catch (Exception var8) {
        // }

        return null;
    }

    public static String a(String var0, String var1, String var2) {
        return null;
        // Object var3 = null;
        // StringBuffer var4 = new StringBuffer();
        //
        // try {
        //     TrustManager[] var5 = new TrustManager[]{new MyX509TrustManager()};
        //     SSLContext var6 = SSLContext.getInstance("SSL", "SunJSSE");
        //     var6.init((KeyManager[])null, var5, new SecureRandom());
        //     SSLSocketFactory var7 = var6.getSocketFactory();
        //     URL var8 = new URL(var0);
        //     HttpURLConnection var9 = (HttpURLConnection)var8.openConnection();
        //     var9.setConnectTimeout(4500);
        //     var9.setReadTimeout(4500);
        //     var9.setDoOutput(true);
        //     var9.setDoInput(true);
        //     var9.setUseCaches(false);
        //     var9.setRequestMethod(var1);
        //     if ("GET".equalsIgnoreCase(var1)) {
        //         var9.connect();
        //     }
        //
        //     if (null != var2) {
        //         OutputStream var10 = var9.getOutputStream();
        //         var10.write(var2.getBytes("UTF-8"));
        //         var10.close();
        //     }
        //
        //     InputStream var16 = var9.getInputStream();
        //     InputStreamReader var11 = new InputStreamReader(var16, "utf-8");
        //     BufferedReader var12 = new BufferedReader(var11);
        //     Object var13 = null;
        //
        //     while((var18 = var12.readLine()) != null) {
        //         var4.append(var18);
        //     }
        //
        //     var12.close();
        //     var11.close();
        //     var16.close();
        //     Object var17 = null;
        //     var9.disconnect();
        // } catch (ConnectException var14) {
        // } catch (Exception var15) {
        // }
        //
        // return var4.toString();
    }
}
