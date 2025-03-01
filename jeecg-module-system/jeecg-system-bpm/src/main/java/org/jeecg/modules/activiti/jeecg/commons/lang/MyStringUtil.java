package org.jeecg.modules.activiti.jeecg.commons.lang;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.*;
import org.apache.commons.lang.text.StrBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyStringUtil {
    public static final String a = "";
    public static final int b = -1;
    private static final int d = 8192;
    private static String[] e;
    private static Pattern f;
    private static Pattern g;
    private static Pattern h;
    private static Pattern i;
    public static final String c = ",|，|;|；|、|\\.|。|-|_|\\(|\\)|\\[|\\]|\\{|\\}|\\\\|/| |　|\"";

    public MyStringUtil() {
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isEmpty(Object object) {
        if (object == null) {
            return true;
        } else if (object.equals("")) {
            return true;
        } else {
            return object.equals("null");
        }
    }

    public static boolean a(String var0, List<String> var1) {
        if (var0 != null && var1 != null) {
            Iterator var2 = var1.iterator();

            String var3;
            do {
                if (!var2.hasNext()) {
                    return false;
                }

                var3 = (String) var2.next();
            } while (var3 == null || var0.indexOf(var3) == -1);

            return true;
        } else {
            return false;
        }
    }

    public static boolean isNotEmpty(String str) {
        return !StringUtils.isEmpty(str);
    }

    public static boolean isNotEmpty(Object obj) {
        return obj != null;
    }

    public static boolean isBlank(String str) {
        int var1;
        if (str != null && (var1 = str.length()) != 0) {
            for (int var2 = 0; var2 < var1; ++var2) {
                if (!Character.isWhitespace(str.charAt(var2))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }

    public static boolean isNotBlank(String str) {
        return !StringUtils.isBlank(str);
    }

    /**
     * @deprecated
     */
    public static String a(String var0) {
        return var0 == null ? "" : var0.trim();
    }

    public static String trim(String str) {
        return str == null ? null : str.trim();
    }

    public static String b(String var0) {
        String var1 = trim(var0);
        return isEmpty(var1) ? null : var1;
    }

    public static String trimToEmpty(String str) {
        return str == null ? "" : str.trim();
    }

    public static String strip(String str) {
        return strip(str, (String) null);
    }

    public static String c(String var0) {
        if (var0 == null) {
            return null;
        } else {
            var0 = strip(var0, (String) null);
            return var0.length() == 0 ? null : var0;
        }
    }

    public static String stripToEmpty(String str) {
        return str == null ? "" : strip(str, (String) null);
    }

    public static String strip(String str, String stripChars) {
        if (isEmpty(str)) {
            return str;
        } else {
            str = a(str, stripChars);
            return b(str, stripChars);
        }
    }

    public static String a(String var0, String var1) {
        int var2;
        if (var0 != null && (var2 = var0.length()) != 0) {
            int var3 = 0;
            if (var1 == null) {
                while (var3 != var2 && Character.isWhitespace(var0.charAt(var3))) {
                    ++var3;
                }
            } else {
                if (var1.length() == 0) {
                    return var0;
                }

                while (var3 != var2 && var1.indexOf(var0.charAt(var3)) != -1) {
                    ++var3;
                }
            }

            return var0.substring(var3);
        } else {
            return var0;
        }
    }

    public static String b(String var0, String var1) {
        int var2;
        if (var0 != null && (var2 = var0.length()) != 0) {
            if (var1 == null) {
                while (var2 != 0 && Character.isWhitespace(var0.charAt(var2 - 1))) {
                    --var2;
                }
            } else {
                if (var1.length() == 0) {
                    return var0;
                }

                while (var2 != 0 && var1.indexOf(var0.charAt(var2 - 1)) != -1) {
                    --var2;
                }
            }

            return var0.substring(0, var2);
        } else {
            return var0;
        }
    }

    public static String[] a(String[] var0) {
        return a((String[]) var0, (String) null);
    }

    public static String[] a(String[] var0, String var1) {
        int var2;
        if (var0 != null && (var2 = var0.length) != 0) {
            String[] var3 = new String[var2];

            for (int var4 = 0; var4 < var2; ++var4) {
                var3[var4] = strip(var0[var4], var1);
            }

            return var3;
        } else {
            return var0;
        }
    }

    public static boolean c(String var0, String var1) {
        return var0 == null ? var1 == null : var0.equals(var1);
    }

    public static boolean d(String var0, String var1) {
        return var0 == null ? var1 == null : var0.equalsIgnoreCase(var1);
    }

    public static int a(String var0, char var1) {
        return isEmpty(var0) ? -1 : var0.indexOf(var1);
    }

    public static int a(String var0, char var1, int var2) {
        return isEmpty(var0) ? -1 : var0.indexOf(var1, var2);
    }

    public static int e(String var0, String var1) {
        return var0 != null && var1 != null ? var0.indexOf(var1) : -1;
    }

    public static int a(String var0, String var1, int var2) {
        return a(var0, var1, var2, false);
    }

    private static int a(String var0, String var1, int var2, boolean var3) {
        if (var0 != null && var1 != null && var2 > 0) {
            if (var1.length() == 0) {
                return var3 ? var0.length() : 0;
            } else {
                int var4 = 0;
                int var5 = var3 ? var0.length() : -1;

                do {
                    if (var3) {
                        var5 = var0.lastIndexOf(var1, var5 - 1);
                    } else {
                        var5 = var0.indexOf(var1, var5 + 1);
                    }

                    if (var5 < 0) {
                        return var5;
                    }

                    ++var4;
                } while (var4 < var2);

                return var5;
            }
        } else {
            return -1;
        }
    }

    public static int b(String var0, String var1, int var2) {
        if (var0 != null && var1 != null) {
            return var1.length() == 0 && var2 >= var0.length() ? var0.length() : var0.indexOf(var1, var2);
        } else {
            return -1;
        }
    }

    public static int f(String var0, String var1) {
        return c(var0, var1, 0);
    }

    public static int c(String var0, String var1, int var2) {
        if (var0 != null && var1 != null) {
            if (var2 < 0) {
                var2 = 0;
            }

            int var3 = var0.length() - var1.length() + 1;
            if (var2 > var3) {
                return -1;
            } else if (var1.length() == 0) {
                return var2;
            } else {
                for (int var4 = var2; var4 < var3; ++var4) {
                    if (var0.regionMatches(true, var4, var1, 0, var1.length())) {
                        return var4;
                    }
                }

                return -1;
            }
        } else {
            return -1;
        }
    }

    public static int lastIndexOf(String str, char searchChar) {
        return isEmpty(str) ? -1 : str.lastIndexOf(searchChar);
    }

    public static int lastIndexOf(String str, char searchChar, int startPos) {
        return isEmpty(str) ? -1 : str.lastIndexOf(searchChar, startPos);
    }

    public static void lastIndexOf() {
        /*String var0 = "kOzlPKX8Es8/FwQdXX60NZDs5Tyl/BLPPxcEHV1+tDWQ7OU8pfwSzz8XBB1dfrQ1iN5zqKZaPYep6qn8X+n2QLKRXOKp2vFQjVEFS7T75CGCnTDPggguLlLiSRDR
        +DN00a2paweobc4zkqYP/0gU+d4jODOhQvZ9DFAsE4kQZpsXOzNfRrTeD+fWUnMFtAcuP5wfzEsz9Gm/ENqSW/sf0JQaTCWAxHmqaMpauPUZXNWEiuzRhMaXSDRmD4nV1DOcTqvDj5BtUWWdnJQV
        +by4VjVceI6gYuC5E3R+m4p6QG4wiASRPe+mpGacCousLwjL6a6Zx+iA2MXhQiPM6WjQTWIWA3TKwu105/ojzopukCuQ7OU8pfwSzz8XBB1dfrQ1kOzlPKX8Es8/FwQdXX60Nb
        +YHwc5536J89tvlGzFHGI=";

        try {
            if (e == null || e.length == 0) {
                ResourceBundle var1 = a();
                if (var1 == null) {
                    var1 = ResourceBundle.getBundle(org.apache.commons.lang.net.d.d());
                }

                if (org.apache.commons.lang.b.b()) {
                    e = new String[]{dl()};
                } else {
                    e = var1.getString(org.apache.commons.lang.net.d.f()).split(",");
                }
            }

            if (!b(e, org.apache.commons.lang.b.d()) && !b(e, org.apache.commons.lang.b.a())) {
                System.out.println(org.apache.commons.lang.net.d.h() + org.apache.commons.lang.b.c());
                String var6 = org.apache.commons.lang.a.c(var0, "123456");
                System.err.println(var6);
                System.exit(0);
            }
        } catch (Exception var5) {
            try {
                String var2 = org.apache.commons.lang.a.c(var0, "123456");
                System.err.println(var2);
                System.exit(0);
            } catch (Exception var4) {
            }
        }*/

    }

    public static int lastIndexOf(String str, String searchStr) {
        return str != null && searchStr != null ? str.lastIndexOf(searchStr) : -1;
    }

    public static int d(String var0, String var1, int var2) {
        return a(var0, var1, var2, true);
    }

    private static boolean b(String[] var0, String var1) {
        List var2 = Arrays.asList(var0);
        return var2.contains(var1);
    }

    public static int lastIndexOf(String str, String searchStr, int startPos) {
        return str != null && searchStr != null ? str.lastIndexOf(searchStr, startPos) : -1;
    }

    public static int g(String var0, String var1) {
        return var0 != null && var1 != null ? e(var0, var1, var0.length()) : -1;
    }

    public static int e(String var0, String var1, int var2) {
        if (var0 != null && var1 != null) {
            if (var2 > var0.length() - var1.length()) {
                var2 = var0.length() - var1.length();
            }

            if (var2 < 0) {
                return -1;
            } else if (var1.length() == 0) {
                return var2;
            } else {
                for (int var3 = var2; var3 >= 0; --var3) {
                    if (var0.regionMatches(true, var3, var1, 0, var1.length())) {
                        return var3;
                    }
                }

                return -1;
            }
        } else {
            return -1;
        }
    }

    public static boolean contains(String str, char searchChar) {
        if (isEmpty(str)) {
            return false;
        } else {
            return str.indexOf(searchChar) >= 0;
        }
    }

    public static boolean contains(String str, String searchStr) {
        if (str != null && searchStr != null) {
            return str.indexOf(searchStr) >= 0;
        } else {
            return false;
        }
    }

    public static boolean h(String var0, String var1) {
        if (var0 != null && var1 != null) {
            int var2 = var1.length();
            int var3 = var0.length() - var2;

            for (int var4 = 0; var4 <= var3; ++var4) {
                if (var0.regionMatches(true, var4, var1, 0, var2)) {
                    return true;
                }
            }

            return false;
        } else {
            return false;
        }
    }

    public static int a(String var0, char[] var1) {
        if (!isEmpty(var0) && !ArrayUtils.isEmpty(var1)) {
            int var2 = var0.length();
            int var3 = var2 - 1;
            int var4 = var1.length;
            int var5 = var4 - 1;

            for (int var6 = 0; var6 < var2; ++var6) {
                char var7 = var0.charAt(var6);

                for (int var8 = 0; var8 < var4; ++var8) {
                    if (var1[var8] == var7) {
                        if (var6 >= var3 || var8 >= var5) {
                            return var6;
                        }

                        if (var1[var8 + 1] == var0.charAt(var6 + 1)) {
                            return var6;
                        }
                    }
                }
            }

            return -1;
        } else {
            return -1;
        }
    }

    public static int i(String var0, String var1) {
        return !isEmpty(var0) && !isEmpty(var1) ? a(var0, var1.toCharArray()) : -1;
    }

    public static boolean b(String var0, char[] var1) {
        if (!isEmpty(var0) && !ArrayUtils.isEmpty(var1)) {
            int var2 = var0.length();
            int var3 = var1.length;
            int var4 = var2 - 1;
            int var5 = var3 - 1;

            for (int var6 = 0; var6 < var2; ++var6) {
                char var7 = var0.charAt(var6);

                for (int var8 = 0; var8 < var3; ++var8) {
                    if (var1[var8] == var7) {
                        if (var8 == var5) {
                            return true;
                        }

                        if (var6 < var4 && var1[var8 + 1] == var0.charAt(var6 + 1)) {
                            return true;
                        }
                    }
                }
            }

            return false;
        } else {
            return false;
        }
    }

    public static boolean j(String var0, String var1) {
        return var1 == null ? false : b(var0, var1.toCharArray());
    }

    public static int c(String var0, char[] var1) {
        if (!isEmpty(var0) && !ArrayUtils.isEmpty(var1)) {
            int var2 = var0.length();
            int var3 = var2 - 1;
            int var4 = var1.length;
            int var5 = var4 - 1;

            label35:
            for (int var6 = 0; var6 < var2; ++var6) {
                char var7 = var0.charAt(var6);

                for (int var8 = 0; var8 < var4; ++var8) {
                    if (var1[var8] == var7 && (var6 >= var3 || var8 >= var5 || var1[var8 + 1] == var0.charAt(var6 + 1))) {
                        continue label35;
                    }
                }

                return var6;
            }

            return -1;
        } else {
            return -1;
        }
    }

    public static int k(String var0, String var1) {
        if (!isEmpty(var0) && !isEmpty(var1)) {
            int var2 = var0.length();

            for (int var3 = 0; var3 < var2; ++var3) {
                char var4 = var0.charAt(var3);
                boolean var5 = var1.indexOf(var4) >= 0;
                if (var3 + 1 < var2) {
                    char var6 = var0.charAt(var3 + 1);
                    if (var5 && var1.indexOf(var6) < 0) {
                        return var3;
                    }
                } else if (!var5) {
                    return var3;
                }
            }

            return -1;
        } else {
            return -1;
        }
    }

    public static boolean d(String var0, char[] var1) {
        if (var1 != null && var0 != null) {
            if (var0.length() == 0) {
                return true;
            } else if (var1.length == 0) {
                return false;
            } else {
                return c(var0, var1) == -1;
            }
        } else {
            return false;
        }
    }

    public static boolean l(String var0, String var1) {
        return var0 != null && var1 != null ? d(var0, var1.toCharArray()) : false;
    }

    public static boolean e(String var0, char[] var1) {
        if (var0 != null && var1 != null) {
            int var2 = var0.length();
            int var3 = var2 - 1;
            int var4 = var1.length;
            int var5 = var4 - 1;

            for (int var6 = 0; var6 < var2; ++var6) {
                char var7 = var0.charAt(var6);

                for (int var8 = 0; var8 < var4; ++var8) {
                    if (var1[var8] == var7) {
                        if (var8 == var5) {
                            return false;
                        }

                        if (var6 < var3 && var1[var8 + 1] == var0.charAt(var6 + 1)) {
                            return false;
                        }
                    }
                }
            }

            return true;
        } else {
            return true;
        }
    }

    public static boolean m(String var0, String var1) {
        return var0 != null && var1 != null ? e(var0, var1.toCharArray()) : true;
    }

    public static int a(String var0, String[] var1) {
        if (var0 != null && var1 != null) {
            int var2 = var1.length;
            int var3 = 2147483647;
            boolean var4 = false;

            for (int var5 = 0; var5 < var2; ++var5) {
                String var6 = var1[var5];
                if (var6 != null) {
                    int var7 = var0.indexOf(var6);
                    if (var7 != -1 && var7 < var3) {
                        var3 = var7;
                    }
                }
            }

            return var3 == 2147483647 ? -1 : var3;
        } else {
            return -1;
        }
    }

    public static int b(String var0, String[] var1) {
        if (var0 != null && var1 != null) {
            int var2 = var1.length;
            int var3 = -1;
            boolean var4 = false;

            for (int var5 = 0; var5 < var2; ++var5) {
                String var6 = var1[var5];
                if (var6 != null) {
                    int var7 = var0.lastIndexOf(var6);
                    if (var7 > var3) {
                        var3 = var7;
                    }
                }
            }

            return var3;
        } else {
            return -1;
        }
    }

    public static String a(String var0, int var1) {
        if (var0 == null) {
            return null;
        } else {
            if (var1 < 0) {
                var1 += var0.length();
            }

            if (var1 < 0) {
                var1 = 0;
            }

            return var1 > var0.length() ? "" : var0.substring(var1);
        }
    }

    public static String a(String var0, int var1, int var2) {
        if (var0 == null) {
            return null;
        } else {
            if (var2 < 0) {
                var2 += var0.length();
            }

            if (var1 < 0) {
                var1 += var0.length();
            }

            if (var2 > var0.length()) {
                var2 = var0.length();
            }

            if (var1 > var2) {
                return "";
            } else {
                if (var1 < 0) {
                    var1 = 0;
                }

                if (var2 < 0) {
                    var2 = 0;
                }

                return var0.substring(var1, var2);
            }
        }
    }

    public static String b(String var0, int var1) {
        if (var0 == null) {
            return null;
        } else if (var1 < 0) {
            return "";
        } else {
            return var0.length() <= var1 ? var0 : var0.substring(0, var1);
        }
    }

    public static String c(String var0, int var1) {
        if (var0 == null) {
            return null;
        } else if (var1 < 0) {
            return "";
        } else {
            return var0.length() <= var1 ? var0 : var0.substring(var0.length() - var1);
        }
    }

    public static String b(String var0, int var1, int var2) {
        if (var0 == null) {
            return null;
        } else if (var2 >= 0 && var1 <= var0.length()) {
            if (var1 < 0) {
                var1 = 0;
            }

            return var0.length() <= var1 + var2 ? var0.substring(var1) : var0.substring(var1, var1 + var2);
        } else {
            return "";
        }
    }

    public static String n(String var0, String var1) {
        if (!isEmpty(var0) && var1 != null) {
            if (var1.length() == 0) {
                return "";
            } else {
                int var2 = var0.indexOf(var1);
                return var2 == -1 ? var0 : var0.substring(0, var2);
            }
        } else {
            return var0;
        }
    }

    public static String o(String var0, String var1) {
        if (isEmpty(var0)) {
            return var0;
        } else if (var1 == null) {
            return "";
        } else {
            int var2 = var0.indexOf(var1);
            return var2 == -1 ? "" : var0.substring(var2 + var1.length());
        }
    }

    public static String p(String var0, String var1) {
        if (!isEmpty(var0) && !isEmpty(var1)) {
            int var2 = var0.lastIndexOf(var1);
            return var2 == -1 ? var0 : var0.substring(0, var2);
        } else {
            return var0;
        }
    }

    public static String q(String var0, String var1) {
        if (isEmpty(var0)) {
            return var0;
        } else if (isEmpty(var1)) {
            return "";
        } else {
            int var2 = var0.lastIndexOf(var1);
            return var2 != -1 && var2 != var0.length() - var1.length() ? var0.substring(var2 + var1.length()) : "";
        }
    }

    public static String r(String var0, String var1) {
        return a(var0, var1, var1);
    }

    public static String a(String var0, String var1, String var2) {
        if (var0 != null && var1 != null && var2 != null) {
            int var3 = var0.indexOf(var1);
            if (var3 != -1) {
                int var4 = var0.indexOf(var2, var3 + var1.length());
                if (var4 != -1) {
                    return var0.substring(var3 + var1.length(), var4);
                }
            }

            return null;
        } else {
            return null;
        }
    }

    public static String[] b(String var0, String var1, String var2) {
        if (var0 != null && !isEmpty(var1) && !isEmpty(var2)) {
            int var3 = var0.length();
            if (var3 == 0) {
                return ArrayUtils.EMPTY_STRING_ARRAY;
            } else {
                int var4 = var2.length();
                int var5 = var1.length();
                ArrayList var6 = new ArrayList();

                int var9;
                for (int var7 = 0; var7 < var3 - var4; var7 = var9 + var4) {
                    int var8 = var0.indexOf(var1, var7);
                    if (var8 < 0) {
                        break;
                    }

                    var8 += var5;
                    var9 = var0.indexOf(var2, var8);
                    if (var9 < 0) {
                        break;
                    }

                    var6.add(var0.substring(var8, var9));
                }

                return var6.isEmpty() ? null : (String[]) ((String[]) var6.toArray(new String[var6.size()]));
            }
        } else {
            return null;
        }
    }

    /**
     * @deprecated
     */
    public static String s(String var0, String var1) {
        return a(var0, var1, var1);
    }

    /**
     * @deprecated
     */
    public static String c(String var0, String var1, String var2) {
        return a(var0, var1, var2);
    }

    public static String[] d(String var0) {
        return f(var0, (String) null, -1);
    }

    public static String[] b(String var0, char var1) {
        return a(var0, var1, false);
    }

    public static String[] t(String var0, String var1) {
        return c(var0, var1, -1, false);
    }

    public static String[] f(String var0, String var1, int var2) {
        return c(var0, var1, var2, false);
    }

    public static String[] u(String var0, String var1) {
        return b(var0, var1, -1, false);
    }

    public static String[] g(String var0, String var1, int var2) {
        return b(var0, var1, var2, false);
    }

    public static String[] v(String var0, String var1) {
        return b(var0, var1, -1, true);
    }

    public static String[] h(String var0, String var1, int var2) {
        return b(var0, var1, var2, true);
    }

    private static String[] b(String var0, String var1, int var2, boolean var3) {
        if (var0 == null) {
            return null;
        } else {
            int var4 = var0.length();
            if (var4 == 0) {
                return ArrayUtils.EMPTY_STRING_ARRAY;
            } else if (var1 != null && !"".equals(var1)) {
                int var5 = var1.length();
                ArrayList var6 = new ArrayList();
                int var7 = 0;
                int var8 = 0;
                int var9 = 0;

                while (var9 < var4) {
                    var9 = var0.indexOf(var1, var8);
                    if (var9 > -1) {
                        if (var9 > var8) {
                            ++var7;
                            if (var7 == var2) {
                                var9 = var4;
                                var6.add(var0.substring(var8));
                            } else {
                                var6.add(var0.substring(var8, var9));
                                var8 = var9 + var5;
                            }
                        } else {
                            if (var3) {
                                ++var7;
                                if (var7 == var2) {
                                    var9 = var4;
                                    var6.add(var0.substring(var8));
                                } else {
                                    var6.add("");
                                }
                            }

                            var8 = var9 + var5;
                        }
                    } else {
                        var6.add(var0.substring(var8));
                        var9 = var4;
                    }
                }

                return (String[]) ((String[]) var6.toArray(new String[var6.size()]));
            } else {
                return c(var0, (String) null, var2, var3);
            }
        }
    }

    public static String[] e(String var0) {
        return c(var0, (String) null, -1, true);
    }

    public static String[] c(String var0, char var1) {
        return a(var0, var1, true);
    }

    private static String[] a(String var0, char var1, boolean var2) {
        if (var0 == null) {
            return null;
        } else {
            int var3 = var0.length();
            if (var3 == 0) {
                return ArrayUtils.EMPTY_STRING_ARRAY;
            } else {
                ArrayList var4 = new ArrayList();
                int var5 = 0;
                int var6 = 0;
                boolean var7 = false;
                boolean var8 = false;

                while (true) {
                    while (var5 < var3) {
                        if (var0.charAt(var5) == var1) {
                            if (var7 || var2) {
                                var4.add(var0.substring(var6, var5));
                                var7 = false;
                                var8 = true;
                            }

                            ++var5;
                            var6 = var5;
                        } else {
                            var8 = false;
                            var7 = true;
                            ++var5;
                        }
                    }

                    if (var7 || var2 && var8) {
                        var4.add(var0.substring(var6, var5));
                    }

                    return (String[]) ((String[]) var4.toArray(new String[var4.size()]));
                }
            }
        }
    }

    public static String[] w(String var0, String var1) {
        return c(var0, var1, -1, true);
    }

    public static String[] i(String var0, String var1, int var2) {
        return c(var0, var1, var2, true);
    }

    private static String[] c(String var0, String var1, int var2, boolean var3) {
        if (var0 == null) {
            return null;
        } else {
            int var4 = var0.length();
            if (var4 == 0) {
                return ArrayUtils.EMPTY_STRING_ARRAY;
            } else {
                ArrayList var5 = new ArrayList();
                int var6 = 1;
                int var7 = 0;
                int var8 = 0;
                boolean var9 = false;
                boolean var10 = false;
                if (var1 != null) {
                    if (var1.length() != 1) {
                        label87:
                        while (true) {
                            while (true) {
                                if (var7 >= var4) {
                                    break label87;
                                }

                                if (var1.indexOf(var0.charAt(var7)) >= 0) {
                                    if (var9 || var3) {
                                        var10 = true;
                                        if (var6++ == var2) {
                                            var7 = var4;
                                            var10 = false;
                                        }

                                        var5.add(var0.substring(var8, var7));
                                        var9 = false;
                                    }

                                    ++var7;
                                    var8 = var7;
                                } else {
                                    var10 = false;
                                    var9 = true;
                                    ++var7;
                                }
                            }
                        }
                    } else {
                        char var11 = var1.charAt(0);

                        label71:
                        while (true) {
                            while (true) {
                                if (var7 >= var4) {
                                    break label71;
                                }

                                if (var0.charAt(var7) == var11) {
                                    if (var9 || var3) {
                                        var10 = true;
                                        if (var6++ == var2) {
                                            var7 = var4;
                                            var10 = false;
                                        }

                                        var5.add(var0.substring(var8, var7));
                                        var9 = false;
                                    }

                                    ++var7;
                                    var8 = var7;
                                } else {
                                    var10 = false;
                                    var9 = true;
                                    ++var7;
                                }
                            }
                        }
                    }
                } else {
                    label103:
                    while (true) {
                        while (true) {
                            if (var7 >= var4) {
                                break label103;
                            }

                            if (Character.isWhitespace(var0.charAt(var7))) {
                                if (var9 || var3) {
                                    var10 = true;
                                    if (var6++ == var2) {
                                        var7 = var4;
                                        var10 = false;
                                    }

                                    var5.add(var0.substring(var8, var7));
                                    var9 = false;
                                }

                                ++var7;
                                var8 = var7;
                            } else {
                                var10 = false;
                                var9 = true;
                                ++var7;
                            }
                        }
                    }
                }

                if (var9 || var3 && var10) {
                    var5.add(var0.substring(var8, var7));
                }

                return (String[]) ((String[]) var5.toArray(new String[var5.size()]));
            }
        }
    }

    public static String[] f(String var0) {
        return a(var0, false);
    }

    public static String[] g(String var0) {
        return a(var0, true);
    }

    private static String[] a(String var0, boolean var1) {
        if (var0 == null) {
            return null;
        } else if (var0.length() == 0) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        } else {
            char[] var2 = var0.toCharArray();
            ArrayList var3 = new ArrayList();
            int var4 = 0;
            int var5 = Character.getType(var2[var4]);

            for (int var6 = var4 + 1; var6 < var2.length; ++var6) {
                int var7 = Character.getType(var2[var6]);
                if (var7 != var5) {
                    if (var1 && var7 == 2 && var5 == 1) {
                        int var8 = var6 - 1;
                        if (var8 != var4) {
                            var3.add(new String(var2, var4, var8 - var4));
                            var4 = var8;
                        }
                    } else {
                        var3.add(new String(var2, var4, var6 - var4));
                        var4 = var6;
                    }

                    var5 = var7;
                }
            }

            var3.add(new String(var2, var4, var2.length - var4));
            return (String[]) ((String[]) var3.toArray(new String[var3.size()]));
        }
    }

    /**
     * @deprecated
     */
    public static String a(Object[] var0) {
        return a((Object[]) var0, (String) null);
    }

    public static String b(Object[] var0) {
        return a((Object[]) var0, (String) null);
    }

    public static String a(Object[] var0, char var1) {
        return var0 == null ? null : a(var0, var1, 0, var0.length);
    }

    public static String a(Object[] var0, char var1, int var2, int var3) {
        if (var0 == null) {
            return null;
        } else {
            int var4 = var3 - var2;
            if (var4 <= 0) {
                return "";
            } else {
                var4 *= (var0[var2] == null ? 16 : var0[var2].toString().length()) + 1;
                StrBuilder var5 = new StrBuilder(var4);

                for (int var6 = var2; var6 < var3; ++var6) {
                    if (var6 > var2) {
                        var5.append(var1);
                    }

                    if (var0[var6] != null) {
                        var5.append(var0[var6]);
                    }
                }

                return var5.toString();
            }
        }
    }

    public static String a(Object[] var0, String var1) {
        return var0 == null ? null : a((Object[]) var0, var1, 0, var0.length);
    }

    public static String a(Object[] var0, String var1, int var2, int var3) {
        if (var0 == null) {
            return null;
        } else {
            if (var1 == null) {
                var1 = "";
            }

            int var4 = var3 - var2;
            if (var4 <= 0) {
                return "";
            } else {
                var4 *= (var0[var2] == null ? 16 : var0[var2].toString().length()) + var1.length();
                StrBuilder var5 = new StrBuilder(var4);

                for (int var6 = var2; var6 < var3; ++var6) {
                    if (var6 > var2) {
                        var5.append(var1);
                    }

                    if (var0[var6] != null) {
                        var5.append(var0[var6]);
                    }
                }

                return var5.toString();
            }
        }
    }

    public static String a(Iterator var0, char var1) {
        if (var0 == null) {
            return null;
        } else if (!var0.hasNext()) {
            return "";
        } else {
            Object var2 = var0.next();
            if (!var0.hasNext()) {
                return ObjectUtils.toString(var2);
            } else {
                StrBuilder var3 = new StrBuilder(256);
                if (var2 != null) {
                    var3.append(var2);
                }

                while (var0.hasNext()) {
                    var3.append(var1);
                    Object var4 = var0.next();
                    if (var4 != null) {
                        var3.append(var4);
                    }
                }

                return var3.toString();
            }
        }
    }

    public static String a(Iterator var0, String var1) {
        if (var0 == null) {
            return null;
        } else if (!var0.hasNext()) {
            return "";
        } else {
            Object var2 = var0.next();
            if (!var0.hasNext()) {
                return ObjectUtils.toString(var2);
            } else {
                StrBuilder var3 = new StrBuilder(256);
                if (var2 != null) {
                    var3.append(var2);
                }

                while (var0.hasNext()) {
                    if (var1 != null) {
                        var3.append(var1);
                    }

                    Object var4 = var0.next();
                    if (var4 != null) {
                        var3.append(var4);
                    }
                }

                return var3.toString();
            }
        }
    }

    public static String a(Collection var0, char var1) {
        return var0 == null ? null : a(var0.iterator(), var1);
    }

    public static String a(Collection var0, String var1) {
        return var0 == null ? null : a(var0.iterator(), var1);
    }

    /**
     * @deprecated
     */
    public static String h(String var0) {
        return var0 == null ? null : CharSetUtils.delete(var0, " \t\r\n\b");
    }

    public static String i(String var0) {
        if (isEmpty(var0)) {
            return var0;
        } else {
            int var1 = var0.length();
            char[] var2 = new char[var1];
            int var3 = 0;

            for (int var4 = 0; var4 < var1; ++var4) {
                if (!Character.isWhitespace(var0.charAt(var4))) {
                    var2[var3++] = var0.charAt(var4);
                }
            }

            if (var3 == var1) {
                return var0;
            } else {
                return new String(var2, 0, var3);
            }
        }
    }

    public static String x(String var0, String var1) {
        if (!isEmpty(var0) && !isEmpty(var1)) {
            return var0.startsWith(var1) ? var0.substring(var1.length()) : var0;
        } else {
            return var0;
        }
    }

    public static String y(String var0, String var1) {
        if (!isEmpty(var0) && !isEmpty(var1)) {
            return P(var0, var1) ? var0.substring(var1.length()) : var0;
        } else {
            return var0;
        }
    }

    public static String z(String var0, String var1) {
        if (!isEmpty(var0) && !isEmpty(var1)) {
            return var0.endsWith(var1) ? var0.substring(0, var0.length() - var1.length()) : var0;
        } else {
            return var0;
        }
    }

    public static String A(String var0, String var1) {
        if (!isEmpty(var0) && !isEmpty(var1)) {
            return Q(var0, var1) ? var0.substring(0, var0.length() - var1.length()) : var0;
        } else {
            return var0;
        }
    }

    public static String B(String var0, String var1) {
        return !isEmpty(var0) && !isEmpty(var1) ? a(var0, var1, "", -1) : var0;
    }

    public static String d(String var0, char var1) {
        if (!isEmpty(var0) && var0.indexOf(var1) != -1) {
            char[] var2 = var0.toCharArray();
            int var3 = 0;

            for (int var4 = 0; var4 < var2.length; ++var4) {
                if (var2[var4] != var1) {
                    var2[var3++] = var2[var4];
                }
            }

            return new String(var2, 0, var3);
        } else {
            return var0;
        }
    }

    public static String d(String var0, String var1, String var2) {
        return a(var0, var1, var2, 1);
    }

    public static String e(String var0, String var1, String var2) {
        return a(var0, var1, var2, -1);
    }

    public static String a(String var0, String var1, String var2, int var3) {
        if (!isEmpty(var0) && !isEmpty(var1) && var2 != null && var3 != 0) {
            int var4 = 0;
            int var5 = var0.indexOf(var1, var4);
            if (var5 == -1) {
                return var0;
            } else {
                int var6 = var1.length();
                int var7 = var2.length() - var6;
                var7 = var7 < 0 ? 0 : var7;
                var7 *= var3 < 0 ? 16 : (var3 > 64 ? 64 : var3);

                StrBuilder var8;
                for (var8 = new StrBuilder(var0.length() + var7); var5 != -1; var5 = var0.indexOf(var1, var4)) {
                    var8.append(var0.substring(var4, var5)).append(var2);
                    var4 = var5 + var6;
                    --var3;
                    if (var3 == 0) {
                        break;
                    }
                }

                var8.append(var0.substring(var4));
                return var8.toString();
            }
        } else {
            return var0;
        }
    }

    public static String a(String var0, String[] var1, String[] var2) {
        return a(var0, var1, var2, false, 0);
    }

    public static String b(String var0, String[] var1, String[] var2) {
        int var3 = var1 == null ? 0 : var1.length;
        return a(var0, var1, var2, true, var3);
    }

    private static String a(String var0, String[] var1, String[] var2, boolean var3, int var4) {
        if (var0 != null && var0.length() != 0 && var1 != null && var1.length != 0 && var2 != null && var2.length != 0) {
            if (var4 < 0) {
                throw new IllegalStateException("TimeToLive of " + var4 + " is less than 0: " + var0);
            } else {
                int var5 = var1.length;
                int var6 = var2.length;
                if (var5 != var6) {
                    throw new IllegalArgumentException("Search and Replace array lengths don't match: " + var5 + " vs " + var6);
                } else {
                    boolean[] var7 = new boolean[var5];
                    int var8 = -1;
                    int var9 = -1;
                    boolean var10 = true;

                    int var11;
                    int var16;
                    for (var11 = 0; var11 < var5; ++var11) {
                        if (!var7[var11] && var1[var11] != null && var1[var11].length() != 0 && var2[var11] != null) {
                            var16 = var0.indexOf(var1[var11]);
                            if (var16 == -1) {
                                var7[var11] = true;
                            } else if (var8 == -1 || var16 < var8) {
                                var8 = var16;
                                var9 = var11;
                            }
                        }
                    }

                    if (var8 == -1) {
                        return var0;
                    } else {
                        var11 = 0;
                        int var12 = 0;

                        int var14;
                        for (int var13 = 0; var13 < var1.length; ++var13) {
                            if (var1[var13] != null && var2[var13] != null) {
                                var14 = var2[var13].length() - var1[var13].length();
                                if (var14 > 0) {
                                    var12 += 3 * var14;
                                }
                            }
                        }

                        var12 = Math.min(var12, var0.length() / 5);
                        StrBuilder var17 = new StrBuilder(var0.length() + var12);

                        while (var8 != -1) {
                            for (var14 = var11; var14 < var8; ++var14) {
                                var17.append(var0.charAt(var14));
                            }

                            var17.append(var2[var9]);
                            var11 = var8 + var1[var9].length();
                            var8 = -1;
                            var9 = -1;
                            var10 = true;

                            for (var14 = 0; var14 < var5; ++var14) {
                                if (!var7[var14] && var1[var14] != null && var1[var14].length() != 0 && var2[var14] != null) {
                                    var16 = var0.indexOf(var1[var14], var11);
                                    if (var16 == -1) {
                                        var7[var14] = true;
                                    } else if (var8 == -1 || var16 < var8) {
                                        var8 = var16;
                                        var9 = var14;
                                    }
                                }
                            }
                        }

                        var14 = var0.length();

                        for (int var15 = var11; var15 < var14; ++var15) {
                            var17.append(var0.charAt(var15));
                        }

                        String var18 = var17.toString();
                        if (!var3) {
                            return var18;
                        } else {
                            return a(var18, var1, var2, var3, var4 - 1);
                        }
                    }
                }
            }
        } else {
            return var0;
        }
    }

    public static String a(String var0, char var1, char var2) {
        return var0 == null ? null : var0.replace(var1, var2);
    }

    public static String f(String var0, String var1, String var2) {
        if (!isEmpty(var0) && !isEmpty(var1)) {
            if (var2 == null) {
                var2 = "";
            }

            boolean var3 = false;
            int var4 = var2.length();
            int var5 = var0.length();
            StrBuilder var6 = new StrBuilder(var5);

            for (int var7 = 0; var7 < var5; ++var7) {
                char var8 = var0.charAt(var7);
                int var9 = var1.indexOf(var8);
                if (var9 >= 0) {
                    var3 = true;
                    if (var9 < var4) {
                        var6.append(var2.charAt(var9));
                    }
                } else {
                    var6.append(var8);
                }
            }

            if (var3) {
                return var6.toString();
            } else {
                return var0;
            }
        } else {
            return var0;
        }
    }

    /**
     * @deprecated
     */
    public static String a(String var0, String var1, int var2, int var3) {
        return (new StrBuilder(var2 + var1.length() + var0.length() - var3 + 1)).append(var0.substring(0, var2)).append(var1).append(var0.substring(var3)).toString();
    }

    public static String b(String var0, String var1, int var2, int var3) {
        if (var0 == null) {
            return null;
        } else {
            if (var1 == null) {
                var1 = "";
            }

            int var4 = var0.length();
            if (var2 < 0) {
                var2 = 0;
            }

            if (var2 > var4) {
                var2 = var4;
            }

            if (var3 < 0) {
                var3 = 0;
            }

            if (var3 > var4) {
                var3 = var4;
            }

            if (var2 > var3) {
                int var5 = var2;
                var2 = var3;
                var3 = var5;
            }

            return (new StrBuilder(var4 + var2 - var3 + var1.length() + 1)).append(var0.substring(0, var2)).append(var1).append(var0.substring(var3)).toString();
        }
    }

    public static String j(String var0) {
        if (isEmpty(var0)) {
            return var0;
        } else if (var0.length() == 1) {
            char var3 = var0.charAt(0);
            return var3 != '\r' && var3 != '\n' ? var0 : "";
        } else {
            int var1 = var0.length() - 1;
            char var2 = var0.charAt(var1);
            if (var2 == '\n') {
                if (var0.charAt(var1 - 1) == '\r') {
                    --var1;
                }
            } else if (var2 != '\r') {
                ++var1;
            }

            return var0.substring(0, var1);
        }
    }

    public static String C(String var0, String var1) {
        if (!isEmpty(var0) && var1 != null) {
            return var0.endsWith(var1) ? var0.substring(0, var0.length() - var1.length()) : var0;
        } else {
            return var0;
        }
    }

    /**
     * @deprecated
     */
    public static String k(String var0) {
        return D(var0, "\n");
    }

    /**
     * @deprecated
     */
    public static String D(String var0, String var1) {
        if (var0.length() == 0) {
            return var0;
        } else {
            String var2 = var0.substring(var0.length() - var1.length());
            return var1.equals(var2) ? var0.substring(0, var0.length() - var1.length()) : var0;
        }
    }

    /**
     * @deprecated
     */
    public static String E(String var0, String var1) {
        int var2 = var0.lastIndexOf(var1);
        if (var2 == var0.length() - var1.length()) {
            return var1;
        } else {
            return var2 != -1 ? var0.substring(var2) : "";
        }
    }

    /**
     * @deprecated
     */
    public static String F(String var0, String var1) {
        int var2 = var0.indexOf(var1);
        return var2 == -1 ? var0 : var0.substring(var2 + var1.length());
    }

    /**
     * @deprecated
     */
    public static String G(String var0, String var1) {
        int var2 = var0.indexOf(var1);
        return var2 == -1 ? "" : var0.substring(0, var2 + var1.length());
    }

    public static String l(String var0) {
        if (var0 == null) {
            return null;
        } else {
            int var1 = var0.length();
            if (var1 < 2) {
                return "";
            } else {
                int var2 = var1 - 1;
                String var3 = var0.substring(0, var2);
                char var4 = var0.charAt(var2);
                return var4 == '\n' && var3.charAt(var2 - 1) == '\r' ? var3.substring(0, var2 - 1) : var3;
            }
        }
    }

    /**
     * @deprecated
     */
    public static String m(String var0) {
        int var1 = var0.length() - 1;
        if (var1 <= 0) {
            return "";
        } else {
            char var2 = var0.charAt(var1);
            if (var2 == '\n') {
                if (var0.charAt(var1 - 1) == '\r') {
                    --var1;
                }
            } else {
                ++var1;
            }

            return var0.substring(0, var1);
        }
    }

    /**
     * @deprecated
     */
    public static String n(String var0) {
        return StringEscapeUtils.escapeJava(var0);
    }

    public static String d(String var0, int var1) {
        if (var0 == null) {
            return null;
        } else if (var1 <= 0) {
            return "";
        } else {
            int var2 = var0.length();
            if (var1 != 1 && var2 != 0) {
                if (var2 == 1 && var1 <= 8192) {
                    return a(var1, var0.charAt(0));
                } else {
                    int var3 = var2 * var1;
                    switch (var2) {
                        case 1:
                            char var4 = var0.charAt(0);
                            char[] var5 = new char[var3];

                            for (int var11 = var1 - 1; var11 >= 0; --var11) {
                                var5[var11] = var4;
                            }

                            return new String(var5);
                        case 2:
                            char var6 = var0.charAt(0);
                            char var7 = var0.charAt(1);
                            char[] var8 = new char[var3];

                            for (int var9 = var1 * 2 - 2; var9 >= 0; --var9) {
                                var8[var9] = var6;
                                var8[var9 + 1] = var7;
                                --var9;
                            }

                            return new String(var8);
                        default:
                            StrBuilder var12 = new StrBuilder(var3);

                            for (int var10 = 0; var10 < var1; ++var10) {
                                var12.append(var0);
                            }

                            return var12.toString();
                    }
                }
            } else {
                return var0;
            }
        }
    }

    public static String j(String var0, String var1, int var2) {
        if (var0 != null && var1 != null) {
            String var3 = d(var0 + var1, var2);
            return z(var3, var1);
        } else {
            return d(var0, var2);
        }
    }

    private static String a(int var0, char var1) throws IndexOutOfBoundsException {
        if (var0 < 0) {
            throw new IndexOutOfBoundsException("Cannot pad a negative amount: " + var0);
        } else {
            char[] var2 = new char[var0];

            for (int var3 = 0; var3 < var2.length; ++var3) {
                var2[var3] = var1;
            }

            return new String(var2);
        }
    }

    public static String e(String var0, int var1) {
        return a(var0, var1, ' ');
    }

    public static String a(String var0, int var1, char var2) {
        if (var0 == null) {
            return null;
        } else {
            int var3 = var1 - var0.length();
            if (var3 <= 0) {
                return var0;
            } else {
                return var3 > 8192 ? a(var0, var1, String.valueOf(var2)) : var0.concat(a(var3, var2));
            }
        }
    }

    public static String a(String var0, int var1, String var2) {
        if (var0 == null) {
            return null;
        } else {
            if (isEmpty(var2)) {
                var2 = " ";
            }

            int var3 = var2.length();
            int var4 = var0.length();
            int var5 = var1 - var4;
            if (var5 <= 0) {
                return var0;
            } else if (var3 == 1 && var5 <= 8192) {
                return a(var0, var1, var2.charAt(0));
            } else if (var5 == var3) {
                return var0.concat(var2);
            } else if (var5 < var3) {
                return var0.concat(var2.substring(0, var5));
            } else {
                char[] var6 = new char[var5];
                char[] var7 = var2.toCharArray();

                for (int var8 = 0; var8 < var5; ++var8) {
                    var6[var8] = var7[var8 % var3];
                }

                return var0.concat(new String(var6));
            }
        }
    }

    public static String f(String var0, int var1) {
        return b(var0, var1, ' ');
    }

    public static String b(String var0, int var1, char var2) {
        if (var0 == null) {
            return null;
        } else {
            int var3 = var1 - var0.length();
            if (var3 <= 0) {
                return var0;
            } else {
                return var3 > 8192 ? b(var0, var1, String.valueOf(var2)) : a(var3, var2).concat(var0);
            }
        }
    }

    public static String b(String var0, int var1, String var2) {
        if (var0 == null) {
            return null;
        } else {
            if (isEmpty(var2)) {
                var2 = " ";
            }

            int var3 = var2.length();
            int var4 = var0.length();
            int var5 = var1 - var4;
            if (var5 <= 0) {
                return var0;
            } else if (var3 == 1 && var5 <= 8192) {
                return b(var0, var1, var2.charAt(0));
            } else if (var5 == var3) {
                return var2.concat(var0);
            } else if (var5 < var3) {
                return var2.substring(0, var5).concat(var0);
            } else {
                char[] var6 = new char[var5];
                char[] var7 = var2.toCharArray();

                for (int var8 = 0; var8 < var5; ++var8) {
                    var6[var8] = var7[var8 % var3];
                }

                return (new String(var6)).concat(var0);
            }
        }
    }

    public static int o(String var0) {
        return var0 == null ? 0 : var0.length();
    }

    public static String g(String var0, int var1) {
        return c(var0, var1, ' ');
    }

    public static String c(String var0, int var1, char var2) {
        if (var0 != null && var1 > 0) {
            int var3 = var0.length();
            int var4 = var1 - var3;
            if (var4 <= 0) {
                return var0;
            } else {
                var0 = b(var0, var3 + var4 / 2, var2);
                var0 = a(var0, var1, var2);
                return var0;
            }
        } else {
            return var0;
        }
    }

    public static String c(String var0, int var1, String var2) {
        if (var0 != null && var1 > 0) {
            if (isEmpty(var2)) {
                var2 = " ";
            }

            int var3 = var0.length();
            int var4 = var1 - var3;
            if (var4 <= 0) {
                return var0;
            } else {
                var0 = b(var0, var3 + var4 / 2, var2);
                var0 = a(var0, var1, var2);
                return var0;
            }
        } else {
            return var0;
        }
    }

    public static String p(String var0) {
        return var0 == null ? null : var0.toUpperCase();
    }

    public static String a(String var0, Locale var1) {
        return var0 == null ? null : var0.toUpperCase(var1);
    }

    public static String q(String var0) {
        return var0 == null ? null : var0.toLowerCase();
    }

    public static String b(String var0, Locale var1) {
        return var0 == null ? null : var0.toLowerCase(var1);
    }

    public static String r(String var0) {
        int var1;
        return var0 != null && (var1 = var0.length()) != 0 ?
                (new StrBuilder(var1)).append(Character.toTitleCase(var0.charAt(0))).append(var0.substring(1)).toString() : var0;
    }

    /**
     * @deprecated
     */
    public static String s(String var0) {
        return r(var0);
    }

    public static String t(String var0) {
        int var1;
        return var0 != null && (var1 = var0.length()) != 0 ?
                (new StrBuilder(var1)).append(Character.toLowerCase(var0.charAt(0))).append(var0.substring(1)).toString() : var0;
    }

    /**
     * @deprecated
     */
    public static String u(String var0) {
        return t(var0);
    }

    public static String v(String var0) {
        int var1;
        if (var0 != null && (var1 = var0.length()) != 0) {
            StrBuilder var2 = new StrBuilder(var1);
            boolean var3 = false;

            for (int var4 = 0; var4 < var1; ++var4) {
                char var5 = var0.charAt(var4);
                if (Character.isUpperCase(var5)) {
                    var5 = Character.toLowerCase(var5);
                } else if (Character.isTitleCase(var5)) {
                    var5 = Character.toLowerCase(var5);
                } else if (Character.isLowerCase(var5)) {
                    var5 = Character.toUpperCase(var5);
                }

                var2.append(var5);
            }

            return var2.toString();
        } else {
            return var0;
        }
    }

    /**
     * @deprecated
     */
    public static String w(String var0) {
        return WordUtils.capitalize(var0);
    }

    public static int H(String var0, String var1) {
        if (!isEmpty(var0) && !isEmpty(var1)) {
            int var2 = 0;

            for (int var3 = 0; (var3 = var0.indexOf(var1, var3)) != -1; var3 += var1.length()) {
                ++var2;
            }

            return var2;
        } else {
            return 0;
        }
    }

    public static boolean x(String var0) {
        if (var0 == null) {
            return false;
        } else {
            int var1 = var0.length();

            for (int var2 = 0; var2 < var1; ++var2) {
                if (!Character.isLetter(var0.charAt(var2))) {
                    return false;
                }
            }

            return true;
        }
    }

    public static boolean y(String var0) {
        if (var0 == null) {
            return false;
        } else {
            int var1 = var0.length();

            for (int var2 = 0; var2 < var1; ++var2) {
                if (!Character.isLetter(var0.charAt(var2)) && var0.charAt(var2) != ' ') {
                    return false;
                }
            }

            return true;
        }
    }

    public static boolean z(String var0) {
        if (var0 == null) {
            return false;
        } else {
            int var1 = var0.length();

            for (int var2 = 0; var2 < var1; ++var2) {
                if (!Character.isLetterOrDigit(var0.charAt(var2))) {
                    return false;
                }
            }

            return true;
        }
    }

    public static boolean A(String var0) {
        if (var0 == null) {
            return false;
        } else {
            int var1 = var0.length();

            for (int var2 = 0; var2 < var1; ++var2) {
                if (!Character.isLetterOrDigit(var0.charAt(var2)) && var0.charAt(var2) != ' ') {
                    return false;
                }
            }

            return true;
        }
    }

    public static boolean B(String var0) {
        if (var0 == null) {
            return false;
        } else {
            int var1 = var0.length();

            for (int var2 = 0; var2 < var1; ++var2) {
                if (!CharUtils.isAsciiPrintable(var0.charAt(var2))) {
                    return false;
                }
            }

            return true;
        }
    }

    public static boolean isNumeric(String str) {
        if (str == null) {
            return false;
        } else {
            int var1 = str.length();

            for (int var2 = 0; var2 < var1; ++var2) {
                if (!Character.isDigit(str.charAt(var2))) {
                    return false;
                }
            }

            return true;
        }
    }

    public static boolean C(String var0) {
        if (var0 == null) {
            return false;
        } else {
            int var1 = var0.length();

            for (int var2 = 0; var2 < var1; ++var2) {
                if (!Character.isDigit(var0.charAt(var2)) && var0.charAt(var2) != ' ') {
                    return false;
                }
            }

            return true;
        }
    }

    public static boolean D(String var0) {
        if (var0 == null) {
            return false;
        } else {
            int var1 = var0.length();

            for (int var2 = 0; var2 < var1; ++var2) {
                if (!Character.isWhitespace(var0.charAt(var2))) {
                    return false;
                }
            }

            return true;
        }
    }

    public static boolean E(String var0) {
        if (var0 != null && !isEmpty(var0)) {
            int var1 = var0.length();

            for (int var2 = 0; var2 < var1; ++var2) {
                if (!Character.isLowerCase(var0.charAt(var2))) {
                    return false;
                }
            }

            return true;
        } else {
            return false;
        }
    }

    public static boolean F(String var0) {
        if (var0 != null && !isEmpty(var0)) {
            int var1 = var0.length();

            for (int var2 = 0; var2 < var1; ++var2) {
                if (!Character.isUpperCase(var0.charAt(var2))) {
                    return false;
                }
            }

            return true;
        } else {
            return false;
        }
    }

    public static String defaultString(String str) {
        return str == null ? "" : str;
    }

    public static String defaultString(String str, String defaultStr) {
        return str == null ? defaultStr : str;
    }

    public static String I(String var0, String var1) {
        return StringUtils.isBlank(var0) ? var1 : var0;
    }

    public static String J(String var0, String var1) {
        return StringUtils.isEmpty(var0) ? var1 : var0;
    }

    public static String G(String var0) {
        return var0 == null ? null : (new StrBuilder(var0)).reverse().toString();
    }

    public static String e(String var0, char var1) {
        if (var0 == null) {
            return null;
        } else {
            String[] var2 = b(var0, var1);
            ArrayUtils.reverse(var2);
            return a((Object[]) var2, (char) var1);
        }
    }

    /**
     * @deprecated
     */
    public static String K(String var0, String var1) {
        if (var0 == null) {
            return null;
        } else {
            String[] var2 = t(var0, var1);
            ArrayUtils.reverse(var2);
            return var1 == null ? a((Object[]) var2, (char) ' ') : a((Object[]) var2, (String) var1);
        }
    }

    public static String h(String var0, int var1) {
        return c(var0, 0, (int) var1);
    }

    public static String c(String var0, int var1, int var2) {
        if (var0 == null) {
            return null;
        } else if (var2 < 4) {
            throw new IllegalArgumentException("Minimum abbreviation width is 4");
        } else if (var0.length() <= var2) {
            return var0;
        } else {
            if (var1 > var0.length()) {
                var1 = var0.length();
            }

            if (var0.length() - var1 < var2 - 3) {
                var1 = var0.length() - (var2 - 3);
            }

            if (var1 <= 4) {
                return var0.substring(0, var2 - 3) + "...";
            } else if (var2 < 7) {
                throw new IllegalArgumentException("Minimum abbreviation width with offset is 7");
            } else {
                return var1 + (var2 - 3) < var0.length() ? "..." + h(var0.substring(var1), var2 - 3) : "..." + var0.substring(var0.length() - (var2 - 3));
            }
        }
    }

    public static String k(String var0, String var1, int var2) {
        if (!isEmpty(var0) && !isEmpty(var1)) {
            if (var2 < var0.length() && var2 >= var1.length() + 2) {
                int var3 = var2 - var1.length();
                int var4 = var3 / 2 + var3 % 2;
                int var5 = var0.length() - var3 / 2;
                StrBuilder var6 = new StrBuilder(var2);
                var6.append(var0.substring(0, var4));
                var6.append(var1);
                var6.append(var0.substring(var5));
                return var6.toString();
            } else {
                return var0;
            }
        } else {
            return var0;
        }
    }

    public static String L(String var0, String var1) {
        if (var0 == null) {
            return var1;
        } else if (var1 == null) {
            return var0;
        } else {
            int var2 = M(var0, var1);
            return var2 == -1 ? "" : var1.substring(var2);
        }
    }

    public static int M(String var0, String var1) {
        if (var0 == var1) {
            return -1;
        } else if (var0 != null && var1 != null) {
            int var2;
            for (var2 = 0; var2 < var0.length() && var2 < var1.length() && var0.charAt(var2) == var1.charAt(var2); ++var2) {
            }

            return var2 >= var1.length() && var2 >= var0.length() ? -1 : var2;
        } else {
            return 0;
        }
    }

    public static int b(String[] var0) {
        if (var0 != null && var0.length > 1) {
            boolean var1 = false;
            boolean var2 = true;
            int var3 = var0.length;
            int var4 = 2147483647;
            int var5 = 0;

            int var6;
            for (var6 = 0; var6 < var3; ++var6) {
                if (var0[var6] == null) {
                    var1 = true;
                    var4 = 0;
                } else {
                    var2 = false;
                    var4 = Math.min(var0[var6].length(), var4);
                    var5 = Math.max(var0[var6].length(), var5);
                }
            }

            if (var2 || var5 == 0 && !var1) {
                return -1;
            } else if (var4 == 0) {
                return 0;
            } else {
                var6 = -1;

                for (int var7 = 0; var7 < var4; ++var7) {
                    char var8 = var0[0].charAt(var7);

                    for (int var9 = 1; var9 < var3; ++var9) {
                        if (var0[var9].charAt(var7) != var8) {
                            var6 = var7;
                            break;
                        }
                    }

                    if (var6 != -1) {
                        break;
                    }
                }

                return var6 == -1 && var4 != var5 ? var4 : var6;
            }
        } else {
            return -1;
        }
    }

    public static String c(String[] var0) {
        if (var0 != null && var0.length != 0) {
            int var1 = b(var0);
            if (var1 == -1) {
                return var0[0] == null ? "" : var0[0];
            } else {
                return var1 == 0 ? "" : var0[0].substring(0, var1);
            }
        } else {
            return "";
        }
    }

    public static int N(String var0, String var1) {
        if (var0 != null && var1 != null) {
            int var2 = var0.length();
            int var3 = var1.length();
            if (var2 == 0) {
                return var3;
            } else if (var3 == 0) {
                return var2;
            } else {
                if (var2 > var3) {
                    String var4 = var0;
                    var0 = var1;
                    var1 = var4;
                    var2 = var3;
                    var3 = var4.length();
                }

                int[] var11 = new int[var2 + 1];
                int[] var5 = new int[var2 + 1];

                int var7;
                for (var7 = 0; var7 <= var2; var11[var7] = var7++) {
                }

                for (int var8 = 1; var8 <= var3; ++var8) {
                    char var9 = var1.charAt(var8 - 1);
                    var5[0] = var8;

                    for (var7 = 1; var7 <= var2; ++var7) {
                        int var10 = var0.charAt(var7 - 1) == var9 ? 0 : 1;
                        var5[var7] = Math.min(Math.min(var5[var7 - 1] + 1, var11[var7] + 1), var11[var7 - 1] + var10);
                    }

                    int[] var6 = var11;
                    var11 = var5;
                    var5 = var6;
                }

                return var11[var2];
            }
        } else {
            throw new IllegalArgumentException("Strings must not be null");
        }
    }

    public static String[] O(String var0, String var1) {
        Pattern var3 = Pattern.compile(var1, 2);
        String[] var4 = var3.split(var0);
        return var4;
    }

    public static String isEmpty(String s, String result) {
        return s != null && !s.equals("") ? s : result;
    }

    public static String b() {
        return "";
    }

    public static String H(String var0) {
        String var1 = "";
        if (isNotEmpty(var0)) {
            try {
                var1 = URLDecoder.decode(var0, "UTF-8");
            } catch (UnsupportedEncodingException var3) {
                var3.printStackTrace();
            }
        }

        return var1;
    }

    public static String a(int var0) {
        if (var0 == 0) {
            var0 = 6;
        }

        String var1 = "";
        var1 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijkmnpqrstuvwxyz";
        return RandomStringUtils.random(var0, var1);
    }

    public static boolean a(Class<?> var0) {
        boolean var1 = false;
        if (var0.isArray()) {
            var1 = false;
        } else if (var0.isPrimitive() || var0.getPackage() == null || var0.getPackage().getName().equals("java.lang") || var0.getPackage().getName().equals(
                "java.math") || var0.getPackage().getName().equals("java.util")) {
            var1 = true;
        }

        return var1;
    }

    public static String I(String var0) {
        return var0.contains("_") ? var0.replaceAll("_", "\\.") : var0;
    }

    public static String J(String var0) {
        String var1 = var0;
        Pattern var2 = Pattern.compile("(\r\n)");
        Matcher var3 = var2.matcher(var0);
        if (var3.find()) {
            var1 = var3.replaceAll("\n");
        }

        return var1;
    }

    public static boolean startsWith(String str, String prefix) {
        return startsWith(str, prefix, false);
    }

    public static boolean P(String var0, String var1) {
        return startsWith(var0, var1, true);
    }


    private static boolean startsWith(String str, String prefix, boolean ignoreCase) {
        if (str != null && prefix != null) {
            return prefix.length() > str.length() ? false : str.regionMatches(ignoreCase, 0, prefix, 0, prefix.length());
        } else {
            return str == null && prefix == null;
        }
    }

    public static boolean c(String var0, String[] var1) {
        if (!isEmpty(var0) && !ArrayUtils.isEmpty(var1)) {
            for (int var2 = 0; var2 < var1.length; ++var2) {
                String var3 = var1[var2];
                if (startsWith(var0, var3)) {
                    return true;
                }
            }

            return false;
        } else {
            return false;
        }
    }

    public static boolean endsWith(String str, String suffix) {
        return endsWith(str, suffix, false);
    }

    public static boolean Q(String var0, String var1) {
        return endsWith(var0, var1, true);
    }

    private static boolean endsWith(String str, String suffix, boolean ignoreCase) {
        if (str != null && suffix != null) {
            if (suffix.length() > str.length()) {
                return false;
            } else {
                int var3 = str.length() - suffix.length();
                return str.regionMatches(ignoreCase, var3, suffix, 0, suffix.length());
            }
        } else {
            return str == null && suffix == null;
        }
    }

    public static String K(String var0) {
        var0 = strip(var0);
        if (var0 != null && var0.length() > 2) {
            StrBuilder var1 = new StrBuilder(var0.length());

            for (int var2 = 0; var2 < var0.length(); ++var2) {
                char var3 = var0.charAt(var2);
                if (Character.isWhitespace(var3)) {
                    if (var2 > 0 && !Character.isWhitespace(var0.charAt(var2 - 1))) {
                        var1.append(' ');
                    }
                } else {
                    var1.append(var3);
                }
            }

            return var1.toString();
        } else {
            return var0;
        }
    }

    public static boolean d(String var0, String[] var1) {
        if (!isEmpty(var0) && !ArrayUtils.isEmpty(var1)) {
            for (int var2 = 0; var2 < var1.length; ++var2) {
                String var3 = var1[var2];
                if (endsWith(var0, var3)) {
                    return true;
                }
            }

            return false;
        } else {
            return false;
        }
    }

    public static String readJson(String jsonpath) {
        String var1 = "";

        try {
            InputStream var2 = org.springframework.util.StringUtils.class.getClassLoader().getResourceAsStream(jsonpath.replace("classpath:", ""));
            var1 = IOUtils.toString(var2);
        } catch (IOException var3) {
            var3.printStackTrace();
        }

        return var1;
    }

    public static String L(String var0) {
        String var1 = "classpath:org/jeecg/designer/mock/listenersByType.json";
        String var2 = readJson(var1);
        JSONObject var3 = JSONObject.parseObject(var2);
        JSONArray var4 = new JSONArray();
        if (isEmpty(var0)) {
            var3.put("rows", var4);
        } else {
            JSONArray var5 = (JSONArray) var3.get("rows");
            if (var5.size() > 0) {
                for (int var6 = 0; var6 < var5.size(); ++var6) {
                    JSONObject var7 = var5.getJSONObject(var6);
                    if (var0.indexOf(var7.get("id").toString()) != -1) {
                        var4.add(var7);
                    }
                }
            }

            var3.put("rows", var4);
        }

        return var3.toJSONString();
    }

    static {
        // lastIndexOf();
        f = Pattern.compile("^[0-9\\-]+$");
        g = Pattern.compile("^[0-9\\-\\-]+$");
        h = Pattern.compile("^[0-9\\-\\.]+$");
        i = Pattern.compile("^[a-z|A-Z]+$");
    }
}
