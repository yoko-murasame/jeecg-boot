package org.jeecg.modules.online.cgform.d;

import org.jeecgframework.poi.util.MyX509TrustManager;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.*;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Scanner;

/* compiled from: MachineSn.java */
/* loaded from: hibernate-common-ol-5.4.74(2).jar:org/jeecg/modules/online/cgform/d/h.class */
public class h {
    private static String a;
    private static String b;
    private static String c;

//    private static String e() {
//        String str = "ERROR";
//        PropertyResourceBundle propertyResourceBundle = null;
//        try {
//            try {
//                BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(System.getProperty("user.dir") + File.separator + "config" + File.separator + g.e()));
//                propertyResourceBundle = new PropertyResourceBundle(bufferedInputStream);
//                bufferedInputStream.close();
//            } catch (IOException e) {
//            }
//            if (propertyResourceBundle == null) {
//                propertyResourceBundle = (PropertyResourceBundle) ResourceBundle.getBundle(g.d());
//            }
//            str = new String(g.a(g.i(), propertyResourceBundle.getString(g.g())), "UTF-8");
//            if (!str.split("\\|")[1].equals(c())) {
//                System.err.println(g.h() + c());
//                System.err.println(f.d("PT6fjZBhw1iUFkSPqBqOUKZQYx+dBuoP7V/7OVt4JbJwDAAPnQTMV/iPp+jlKEzDcxjwN+9hwyTZ4rWB++Rmy/nkYa9AfUIJ3lx2GlIEL6I=", "bpm56"));
//                System.exit(0);
//            }
//        } catch (Exception e2) {
//            System.err.println(g.h() + c());
//            System.err.println(f.d("cXOssg0bPpUm6/yeK/bPE7U63FDMOQ95i6uJeH0gN9vcs4qeF82uHBdThVR7zOMPH4EKQ9Ctj/VxeSQSJ/bF3w==", "bpm13"));
//            System.exit(0);
//        }
//        return str;
//    }

//    public static String a() {
//        if ("".equals(b) || b == null) {
//            a = a(i() + f() + g());
//            String[] split = e().split("\\|");
//            if (!split[1].equals(a.toUpperCase())) {
//                System.err.println(g.h() + a.toUpperCase());
//                System.err.println(f.d("PT6fjZBhw1iUFkSPqBqOUKZQYx+dBuoP7V/7OVt4JbJwDAAPnQTMV/iPp+jlKEzDcxjwN+9hwyTZ4rWB++Rmy/nkYa9AfUIJ3lx2GlIEL6I=", "bpm56"));
//                System.exit(0);
//            }
//            b = a(a.toUpperCase() + h() + split[0] + "_ " + split[1] + "_ " + split[2] + "20210922");
//        }
//        return b.toUpperCase();
//    }
//
//    public static String b() {
//        if ("".equals(c) || c == null) {
//            a = a(i() + f() + g());
//            String[] split = e().split("\\|");
//            if (!split[1].equals(a.toUpperCase())) {
//                System.err.println(g.h() + a.toUpperCase());
//                System.err.println(f.d("PT6fjZBhw1iUFkSPqBqOUKZQYx+dBuoP7V/7OVt4JbJwDAAPnQTMV/iPp+jlKEzDcxjwN+9hwyTZ4rWB++Rmy/nkYa9AfUIJ3lx2GlIEL6I=", "bpm56"));
//                System.exit(0);
//            }
//            c = a(a.toUpperCase() + split[0] + "_ " + split[1] + "_ " + split[2] + "Combating Piracy");
//        }
//        return c.toUpperCase();
//    }

    private static String a(String str) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(str.getBytes("UTF-8"));
        } catch (Exception e) {
        }
        byte[] digest = messageDigest.digest();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < digest.length; i++) {
            if (Integer.toHexString(255 & digest[i]).length() == 1) {
                stringBuffer.append("0").append(Integer.toHexString(255 & digest[i]));
            } else {
                stringBuffer.append(Integer.toHexString(255 & digest[i]));
            }
        }
        return stringBuffer.toString();
    }

    public static String c() {
        if ("".equals(a) || a == null) {
            a = a(i() + f() + g());
        }
        return a.toUpperCase();
    }

    private static String f() {
        String str = "CPUID000";
        try {
            Process exec = Runtime.getRuntime().exec(new String[]{"wmic", "cpu", "get", "ProcessorId"});
            exec.getOutputStream().close();
            Scanner scanner = new Scanner(exec.getInputStream());
            scanner.next();
            str = scanner.next();
        } catch (Exception e) {
        }
        return str;
    }

    private static String b(String str) {
        String str2 = "";
        try {
            File createTempFile = File.createTempFile("damn", ".vbs");
            createTempFile.deleteOnExit();
            FileWriter fileWriter = new FileWriter(createTempFile);
            fileWriter.write("Set objFSO = CreateObject(\"Scripting.FileSystemObject\")\nSet colDrives = objFSO.Drives\nSet objDrive = colDrives.item(\"" + str + "\")\nWscript.Echo objDrive.SerialNumber");
            fileWriter.close();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("cscript //NoLogo " + createTempFile.getPath()).getInputStream()));
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break;
                }
                str2 = str2 + readLine;
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str2.trim();
    }

    private static String g() {
        String str = "ZBXLH000";
        try {
            File createTempFile = File.createTempFile("realhowto", ".vbs");
            createTempFile.deleteOnExit();
            FileWriter fileWriter = new FileWriter(createTempFile);
            fileWriter.write("Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\nSet colItems = objWMIService.ExecQuery _ \n   (\"Select * from Win32_BaseBoard\") \nFor Each objItem in colItems \n    Wscript.Echo objItem.SerialNumber \n    exit for  ' do the first cpu only! \nNext \n");
            fileWriter.close();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("cscript //NoLogo " + createTempFile.getPath()).getInputStream()));
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break;
                }
                str = str + readLine;
            }
            bufferedReader.close();
        } catch (Exception e) {
        }
        return str.trim();
    }

    private static String h() {
        return new SimpleDateFormat("yyyy").format(new Date());
    }

    public static boolean d() {
        Date date = new Date();
        Date date2 = null;
        try {
            date2 = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).parse("2015-04-18");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date2.before(date);
    }

    private static String i() {
        return j();
    }

    private static String j() {
        String l;
        if (m().startsWith("windows")) {
            l = k();
        } else {
            l = l();
        }
        return l;
    }

    private static String k() {
        try {
            byte[] hardwareAddress = NetworkInterface.getByInetAddress(InetAddress.getLocalHost()).getHardwareAddress();
            StringBuffer stringBuffer = new StringBuffer("");
            if (hardwareAddress == null) {
                return null;
            }
            for (int i = 0; i < hardwareAddress.length; i++) {
                if (i != 0) {
                    stringBuffer.append("-");
                }
                String hexString = Integer.toHexString(hardwareAddress[i] & 255);
                if (hexString.length() == 1) {
                    stringBuffer.append("0" + hexString);
                } else {
                    stringBuffer.append(hexString);
                }
            }
            String upperCase = stringBuffer.toString().toUpperCase();
            if ("".equals(upperCase)) {
                return null;
            }
            return upperCase;
        } catch (Exception e) {
            return null;
        }
    }

    private static String l() {
        String str = "";
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                byte[] hardwareAddress = networkInterfaces.nextElement().getHardwareAddress();
                if (hardwareAddress != null) {
                    StringBuilder sb = new StringBuilder();
                    for (byte b2 : hardwareAddress) {
                        sb.append(a(b2));
                        sb.append("-");
                    }
                    sb.deleteCharAt(sb.length() - 1);
                    str = sb.toString().toUpperCase();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    private static String a(byte b2) {
        String str = "000000" + Integer.toHexString(b2);
        return str.substring(str.length() - 2);
    }

    private static String m() {
        return System.getProperty("os.name").toLowerCase();
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0051, code lost:
        r0 = r0.indexOf(":");
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x005d, code lost:
        if (r0 == (-1)) goto L16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0060, code lost:
        r7 = r0.substring(r0 + 1).trim();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static String c(String r6) {
        /*
            Method dump skipped, instructions count: 198
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jeecg.modules.online.cgform.d.h.c(java.lang.String):java.lang.String");
    }

    private static String n() {
        InetAddress inetAddress = null;
        try {
            inetAddress = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        byte[] bArr = null;
        try {
            bArr = NetworkInterface.getByInetAddress(inetAddress).getHardwareAddress();
        } catch (SocketException e2) {
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < bArr.length; i++) {
            if (i != 0) {
                stringBuffer.append("-");
            }
            String hexString = Integer.toHexString(bArr[i] & 255);
            stringBuffer.append(hexString.length() == 1 ? 0 + hexString : hexString);
        }
        return stringBuffer.toString().toUpperCase();
    }

    /* JADX WARN: Code restructure failed: missing block: B:8:0x0041, code lost:
        r6 = r0.substring(r0 + 4).trim();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static String o() {
        /*
            r0 = 0
            r6 = r0
            r0 = 0
            r7 = r0
            r0 = 0
            r8 = r0
            java.lang.Runtime r0 = java.lang.Runtime.getRuntime()     // Catch: java.io.IOException -> L67 java.lang.Throwable -> L83
            java.lang.String r1 = "ifconfig eth0"
            java.lang.Process r0 = r0.exec(r1)     // Catch: java.io.IOException -> L67 java.lang.Throwable -> L83
            r8 = r0
            java.io.BufferedReader r0 = new java.io.BufferedReader     // Catch: java.io.IOException -> L67 java.lang.Throwable -> L83
            r1 = r0
            java.io.InputStreamReader r2 = new java.io.InputStreamReader     // Catch: java.io.IOException -> L67 java.lang.Throwable -> L83
            r3 = r2
            r4 = r8
            java.io.InputStream r4 = r4.getInputStream()     // Catch: java.io.IOException -> L67 java.lang.Throwable -> L83
            r3.<init>(r4)     // Catch: java.io.IOException -> L67 java.lang.Throwable -> L83
            r1.<init>(r2)     // Catch: java.io.IOException -> L67 java.lang.Throwable -> L83
            r7 = r0
            r0 = 0
            r9 = r0
            r0 = -1
            r10 = r0
        L27:
            r0 = r7
            java.lang.String r0 = r0.readLine()     // Catch: java.io.IOException -> L67 java.lang.Throwable -> L83
            r1 = r0
            r9 = r1
            if (r0 == 0) goto L50
            r0 = r9
            java.lang.String r0 = r0.toLowerCase()     // Catch: java.io.IOException -> L67 java.lang.Throwable -> L83
            java.lang.String r1 = "硬件地址"
            int r0 = r0.indexOf(r1)     // Catch: java.io.IOException -> L67 java.lang.Throwable -> L83
            r10 = r0
            r0 = r10
            r1 = -1
            if (r0 == r1) goto L27
            r0 = r9
            r1 = r10
            r2 = 4
            int r1 = r1 + r2
            java.lang.String r0 = r0.substring(r1)     // Catch: java.io.IOException -> L67 java.lang.Throwable -> L83
            java.lang.String r0 = r0.trim()     // Catch: java.io.IOException -> L67 java.lang.Throwable -> L83
            r6 = r0
            goto L50
        L50:
            r0 = r7
            if (r0 == 0) goto L58
            r0 = r7
            r0.close()     // Catch: java.io.IOException -> L5b
        L58:
            goto L60
        L5b:
            r9 = move-exception
            r0 = r9
            r0.printStackTrace()
        L60:
            r0 = 0
            r7 = r0
            r0 = 0
            r8 = r0
            goto L9e
        L67:
            r9 = move-exception
            r0 = r9
            r0.printStackTrace()     // Catch: java.lang.Throwable -> L83
            r0 = r7
            if (r0 == 0) goto L74
            r0 = r7
            r0.close()     // Catch: java.io.IOException -> L77
        L74:
            goto L7c
        L77:
            r9 = move-exception
            r0 = r9
            r0.printStackTrace()
        L7c:
            r0 = 0
            r7 = r0
            r0 = 0
            r8 = r0
            goto L9e
        L83:
            r11 = move-exception
            r0 = r7
            if (r0 == 0) goto L8d
            r0 = r7
            r0.close()     // Catch: java.io.IOException -> L90
        L8d:
            goto L97
        L90:
            r12 = move-exception
            r0 = r12
            r0.printStackTrace()
        L97:
            r0 = 0
            r7 = r0
            r0 = 0
            r8 = r0
            r0 = r11
            throw r0
        L9e:
            r0 = r6
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jeecg.modules.online.cgform.d.h.o():java.lang.String");
    }

    /* JADX WARN: Code restructure failed: missing block: B:8:0x0041, code lost:
        r6 = r0.substring((r0 + "hwaddr".length()) + 1).trim();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static String p() {
        /*
            r0 = 0
            r6 = r0
            r0 = 0
            r7 = r0
            r0 = 0
            r8 = r0
            java.lang.Runtime r0 = java.lang.Runtime.getRuntime()     // Catch: java.io.IOException -> L6d java.lang.Throwable -> L89
            java.lang.String r1 = "ifconfig eth0"
            java.lang.Process r0 = r0.exec(r1)     // Catch: java.io.IOException -> L6d java.lang.Throwable -> L89
            r8 = r0
            java.io.BufferedReader r0 = new java.io.BufferedReader     // Catch: java.io.IOException -> L6d java.lang.Throwable -> L89
            r1 = r0
            java.io.InputStreamReader r2 = new java.io.InputStreamReader     // Catch: java.io.IOException -> L6d java.lang.Throwable -> L89
            r3 = r2
            r4 = r8
            java.io.InputStream r4 = r4.getInputStream()     // Catch: java.io.IOException -> L6d java.lang.Throwable -> L89
            r3.<init>(r4)     // Catch: java.io.IOException -> L6d java.lang.Throwable -> L89
            r1.<init>(r2)     // Catch: java.io.IOException -> L6d java.lang.Throwable -> L89
            r7 = r0
            r0 = 0
            r9 = r0
            r0 = -1
            r10 = r0
        L27:
            r0 = r7
            java.lang.String r0 = r0.readLine()     // Catch: java.io.IOException -> L6d java.lang.Throwable -> L89
            r1 = r0
            r9 = r1
            if (r0 == 0) goto L56
            r0 = r9
            java.lang.String r0 = r0.toLowerCase()     // Catch: java.io.IOException -> L6d java.lang.Throwable -> L89
            java.lang.String r1 = "hwaddr"
            int r0 = r0.indexOf(r1)     // Catch: java.io.IOException -> L6d java.lang.Throwable -> L89
            r10 = r0
            r0 = r10
            r1 = -1
            if (r0 == r1) goto L27
            r0 = r9
            r1 = r10
            java.lang.String r2 = "hwaddr"
            int r2 = r2.length()     // Catch: java.io.IOException -> L6d java.lang.Throwable -> L89
            int r1 = r1 + r2
            r2 = 1
            int r1 = r1 + r2
            java.lang.String r0 = r0.substring(r1)     // Catch: java.io.IOException -> L6d java.lang.Throwable -> L89
            java.lang.String r0 = r0.trim()     // Catch: java.io.IOException -> L6d java.lang.Throwable -> L89
            r6 = r0
            goto L56
        L56:
            r0 = r7
            if (r0 == 0) goto L5e
            r0 = r7
            r0.close()     // Catch: java.io.IOException -> L61
        L5e:
            goto L66
        L61:
            r9 = move-exception
            r0 = r9
            r0.printStackTrace()
        L66:
            r0 = 0
            r7 = r0
            r0 = 0
            r8 = r0
            goto La4
        L6d:
            r9 = move-exception
            r0 = r9
            r0.printStackTrace()     // Catch: java.lang.Throwable -> L89
            r0 = r7
            if (r0 == 0) goto L7a
            r0 = r7
            r0.close()     // Catch: java.io.IOException -> L7d
        L7a:
            goto L82
        L7d:
            r9 = move-exception
            r0 = r9
            r0.printStackTrace()
        L82:
            r0 = 0
            r7 = r0
            r0 = 0
            r8 = r0
            goto La4
        L89:
            r11 = move-exception
            r0 = r7
            if (r0 == 0) goto L93
            r0 = r7
            r0.close()     // Catch: java.io.IOException -> L96
        L93:
            goto L9d
        L96:
            r12 = move-exception
            r0 = r12
            r0.printStackTrace()
        L9d:
            r0 = 0
            r7 = r0
            r0 = 0
            r8 = r0
            r0 = r11
            throw r0
        La4:
            r0 = r6
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jeecg.modules.online.cgform.d.h.p():java.lang.String");
    }

    private static String q() {
        String str;
        String lowerCase;
        if (System.getProperty("os.name").toLowerCase().startsWith("windows")) {
            str = "cmd /c SET";
        } else {
            str = "env";
        }
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec(str).getInputStream()));
            do {
                String readLine = bufferedReader.readLine();
                if (readLine != null) {
                    lowerCase = readLine.toLowerCase();
                } else {
                    return null;
                }
            } while (lowerCase.indexOf("windir") <= -1);
            return lowerCase.substring(lowerCase.indexOf("windir") + "windir".length() + 1);
        } catch (Exception e) {
            return null;
        }
    }

    public static String a(String str, String str2, String str3) {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            TrustManager[] trustManagerArr = {new MyX509TrustManager()};
            SSLContext sSLContext = SSLContext.getInstance("SSL", "SunJSSE");
            sSLContext.init(null, trustManagerArr, new SecureRandom());
            sSLContext.getSocketFactory();
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
            httpURLConnection.setConnectTimeout(4500);
            httpURLConnection.setReadTimeout(4500);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setRequestMethod(str2);
            if ("GET".equalsIgnoreCase(str2)) {
                httpURLConnection.connect();
            }
            if (null != str3) {
                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(str3.getBytes("UTF-8"));
                outputStream.close();
            }
            InputStream inputStream = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break;
                }
                stringBuffer.append(readLine);
            }
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
        } catch (ConnectException e) {
        } catch (Exception e2) {
        }
        return stringBuffer.toString();
    }
}
