package org.jeecg.modules.online.cgform.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.CharSetUtils;
import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.commons.lang.text.StrBuilder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component("onlCgformSignServiceImpl")
/* loaded from: hibernate-common-ol-5.4.74(2).jar:org/jeecg/modules/online/cgform/service/impl/a.class */
public class a {
    public static final String a = "";
    public static final int b = -1;
    private static final int d = 8192;
    static String[] c = null;
    private static String[] e;

    public static boolean a(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean a(Object obj) {
        if (obj == null || obj.equals("") || obj.equals("null")) {
            return true;
        }
        return false;
    }

    public static boolean a(String str, List<String> list) {
        if (str != null && list != null) {
            for (String str2 : list) {
                if (str2 != null && str.indexOf(str2) != -1) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    public static boolean b(String str) {
        return !StringUtils.isEmpty(str);
    }

    public static boolean b(Object obj) {
        return obj != null;
    }

    public static boolean c(String str) {
        int length;
        if (str == null || (length = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < length; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean d(String str) {
        return !StringUtils.isBlank(str);
    }

    public static String e(String str) {
        return str == null ? "" : str.trim();
    }

    public static String f(String str) {
        if (str == null) {
            return null;
        }
        return str.trim();
    }

    public static String g(String str) {
        String f = f(str);
        if (a(f)) {
            return null;
        }
        return f;
    }

    public static String h(String str) {
        return str == null ? "" : str.trim();
    }

    public static String i(String str) {
        return a(str, (String) null);
    }

    public static String j(String str) {
        if (str == null) {
            return null;
        }
        String a2 = a(str, (String) null);
        if (a2.length() == 0) {
            return null;
        }
        return a2;
    }

    public static String k(String str) {
        return str == null ? "" : a(str, (String) null);
    }

    public static String a(String str, String str2) {
        if (a(str)) {
            return str;
        }
        return c(b(str, str2), str2);
    }

    public static String b(String str, String str2) {
        int length;
        if (str == null || (length = str.length()) == 0) {
            return str;
        }
        int i = 0;
        if (str2 == null) {
            while (i != length && Character.isWhitespace(str.charAt(i))) {
                i++;
            }
        } else if (str2.length() == 0) {
            return str;
        } else {
            while (i != length && str2.indexOf(str.charAt(i)) != -1) {
                i++;
            }
        }
        return str.substring(i);
    }

    public static String c(String str, String str2) {
        if (str != null) {
            int length = str.length();
            int i = length;
            if (length != 0) {
                if (str2 == null) {
                    while (i != 0 && Character.isWhitespace(str.charAt(i - 1))) {
                        i--;
                    }
                } else if (str2.length() == 0) {
                    return str;
                } else {
                    while (i != 0 && str2.indexOf(str.charAt(i - 1)) != -1) {
                        i--;
                    }
                }
                return str.substring(0, i);
            }
        }
        return str;
    }

    public static String[] a(String[] strArr) {
        return a(strArr, (String) null);
    }

    public static String[] a(String[] strArr, String str) {
        int length;
        if (strArr == null || (length = strArr.length) == 0) {
            return strArr;
        }
        String[] strArr2 = new String[length];
        for (int i = 0; i < length; i++) {
            strArr2[i] = a(strArr[i], str);
        }
        return strArr2;
    }

    public static boolean d(String str, String str2) {
        return str == null ? str2 == null : str.equals(str2);
    }

    public static boolean e(String str, String str2) {
        return str == null ? str2 == null : str.equalsIgnoreCase(str2);
    }

    public static int a(String str, char c2) {
        if (a(str)) {
            return -1;
        }
        return str.indexOf(c2);
    }

    public static int a(String str, char c2, int i) {
        if (a(str)) {
            return -1;
        }
        return str.indexOf(c2, i);
    }

    public static int f(String str, String str2) {
        if (str == null || str2 == null) {
            return -1;
        }
        return str.indexOf(str2);
    }

    public static int a(String str, String str2, int i) {
        return a(str, str2, i, false);
    }

    private static int a(String str, String str2, int i, boolean z) {
        if (str == null || str2 == null || i <= 0) {
            return -1;
        }
        if (str2.length() == 0) {
            if (z) {
                return str.length();
            }
            return 0;
        }
        int i2 = 0;
        int length = z ? str.length() : -1;
        do {
            if (z) {
                length = str.lastIndexOf(str2, length - 1);
            } else {
                length = str.indexOf(str2, length + 1);
            }
            if (length < 0) {
                return length;
            }
            i2++;
        } while (i2 < i);
        return length;
    }

    public static int b(String str, String str2, int i) {
        if (str == null || str2 == null) {
            return -1;
        }
        if (str2.length() == 0 && i >= str.length()) {
            return str.length();
        }
        return str.indexOf(str2, i);
    }

    public static int g(String str, String str2) {
        return c(str, str2, 0);
    }

    public static int c(String str, String str2, int i) {
        if (str == null || str2 == null) {
            return -1;
        }
        if (i < 0) {
            i = 0;
        }
        int length = (str.length() - str2.length()) + 1;
        if (i > length) {
            return -1;
        }
        if (str2.length() == 0) {
            return i;
        }
        for (int i2 = i; i2 < length; i2++) {
            if (str.regionMatches(true, i2, str2, 0, str2.length())) {
                return i2;
            }
        }
        return -1;
    }

    public static int b(String str, char c2) {
        if (a(str)) {
            return -1;
        }
        return str.lastIndexOf(c2);
    }

    public static int b(String str, char c2, int i) {
        if (a(str)) {
            return -1;
        }
        return str.lastIndexOf(c2, i);
    }

    static {
//        a();
//        if (e == null || e.length == 0) {
//            ResourceBundle a2 = org.jeecg.modules.online.cgform.d.c.a();
//            if (a2 == null) {
//                a2 = ResourceBundle.getBundle(org.jeecg.modules.online.cgform.d.g.d());
//            }
//            try {
//                if (StreamUtils.isr()) {
//                    e = new String[]{StringUtil.dl()};
//                } else {
//                    e = a2.getString(org.jeecg.modules.online.cgform.d.g.f()).split(org.jeecg.modules.online.cgform.d.b.sB);
//                }
//            } catch (Exception e2) {
//            }
//        }
    }

//    public static void a() {
//        try {
//            if (c == null || c.length == 0) {
//                ResourceBundle a2 = org.jeecg.modules.online.cgform.d.c.a();
//                if (a2 == null) {
//                    a2 = ResourceBundle.getBundle(org.jeecg.modules.online.cgform.d.g.d());
//                }
//                if (StreamUtils.isr()) {
//                    c = new String[]{StringUtil.dl()};
//                } else {
//                    c = a2.getString(org.jeecg.modules.online.cgform.d.g.f()).split(org.jeecg.modules.online.cgform.d.b.sB);
//                }
//            }
//            if (!org.jeecg.modules.online.cgform.d.c.b(c, org.jeecg.modules.online.cgform.d.h.b()) && !org.jeecg.modules.online.cgform.d.c.b(c, org.jeecg.modules.online.cgform.d.h.a())) {
//                System.err.println(org.jeecg.modules.online.cgform.d.g.h() + org.jeecg.modules.online.cgform.d.h.c());
//                System.err.println(org.jeecg.modules.online.cgform.d.f.c("kOzlPKX8Es8/FwQdXX60NZDs5Tyl/BLPPxcEHV1+tDWQ7OU8pfwSzz8XBB1dfrQ1iN5zqKZaPYep6qn8X+n2QLKRXOKp2vFQjVEFS7T75CGCnTDPggguLlLiSRDR+DN00a2paweobc4zkqYP/0gU+d4jODOhQvZ9DFAsE4kQZpsXOzNfRrTeD+fWUnMFtAcuP5wfzEsz9Gm/ENqSW/sf0JQaTCWAxHmqaMpauPUZXNWEiuzRhMaXSDRmD4nV1DOcTqvDj5BtUWWdnJQV+by4VjVceI6gYuC5E3R+m4p6QG4wiASRPe+mpGacCousLwjL6a6Zx+iA2MXhQiPM6WjQTWIWA3TKwu105/ojzopukCuQ7OU8pfwSzz8XBB1dfrQ1kOzlPKX8Es8/FwQdXX60Nb+YHwc5536J89tvlGzFHGI=", "123456"));
//                System.exit(0);
//            }
//        } catch (Exception e2) {
//            try {
//                System.err.println(org.jeecg.modules.online.cgform.d.g.h() + org.jeecg.modules.online.cgform.d.h.c());
//                System.err.println(org.jeecg.modules.online.cgform.d.f.c("kOzlPKX8Es8/FwQdXX60NZDs5Tyl/BLPPxcEHV1+tDWQ7OU8pfwSzz8XBB1dfrQ1iN5zqKZaPYep6qn8X+n2QLKRXOKp2vFQjVEFS7T75CGCnTDPggguLlLiSRDR+DN00a2paweobc4zkqYP/0gU+d4jODOhQvZ9DFAsE4kQZpsXOzNfRrTeD+fWUnMFtAcuP5wfzEsz9Gm/ENqSW/sf0JQaTCWAxHmqaMpauPUZXNWEiuzRhMaXSDRmD4nV1DOcTqvDj5BtUWWdnJQV+by4VjVceI6gYuC5E3R+m4p6QG4wiASRPe+mpGacCousLwjL6a6Zx+iA2MXhQiPM6WjQTWIWA3TKwu105/ojzopukCuQ7OU8pfwSzz8XBB1dfrQ1kOzlPKX8Es8/FwQdXX60Nb+YHwc5536J89tvlGzFHGI=", "123456"));
//                System.exit(0);
//            } catch (Exception e3) {
//            }
//        }
//    }

    public static int h(String str, String str2) {
        if (str == null || str2 == null) {
            return -1;
        }
        return str.lastIndexOf(str2);
    }

    public static int d(String str, String str2, int i) {
        return a(str, str2, i, true);
    }

    public static int e(String str, String str2, int i) {
        if (str == null || str2 == null) {
            return -1;
        }
        return str.lastIndexOf(str2, i);
    }

    public static int i(String str, String str2) {
        if (str == null || str2 == null) {
            return -1;
        }
        return f(str, str2, str.length());
    }

    public static int f(String str, String str2, int i) {
        if (str == null || str2 == null) {
            return -1;
        }
        if (i > str.length() - str2.length()) {
            i = str.length() - str2.length();
        }
        if (i < 0) {
            return -1;
        }
        if (str2.length() == 0) {
            return i;
        }
        for (int i2 = i; i2 >= 0; i2--) {
            if (str.regionMatches(true, i2, str2, 0, str2.length())) {
                return i2;
            }
        }
        return -1;
    }

    public static boolean c(String str, char c2) {
        return !a(str) && str.indexOf(c2) >= 0;
    }

    public static boolean j(String str, String str2) {
        return (str == null || str2 == null || str.indexOf(str2) < 0) ? false : true;
    }

    public static boolean k(String str, String str2) {
        if (str == null || str2 == null) {
            return false;
        }
        int length = str2.length();
        int length2 = str.length() - length;
        for (int i = 0; i <= length2; i++) {
            if (str.regionMatches(true, i, str2, 0, length)) {
                return true;
            }
        }
        return false;
    }

    public static int a(String str, char[] cArr) {
        if (a(str) || ArrayUtils.isEmpty(cArr)) {
            return -1;
        }
        int length = str.length();
        int i = length - 1;
        int length2 = cArr.length;
        int i2 = length2 - 1;
        for (int i3 = 0; i3 < length; i3++) {
            char charAt = str.charAt(i3);
            for (int i4 = 0; i4 < length2; i4++) {
                if (cArr[i4] == charAt) {
                    if (i3 < i && i4 < i2) {
                        if (cArr[i4 + 1] == str.charAt(i3 + 1)) {
                            return i3;
                        }
                    } else {
                        return i3;
                    }
                }
            }
        }
        return -1;
    }

    public static int l(String str, String str2) {
        if (a(str) || a(str2)) {
            return -1;
        }
        return a(str, str2.toCharArray());
    }

    public static boolean b(String str, char[] cArr) {
        if (a(str) || ArrayUtils.isEmpty(cArr)) {
            return false;
        }
        int length = str.length();
        int length2 = cArr.length;
        int i = length - 1;
        int i2 = length2 - 1;
        for (int i3 = 0; i3 < length; i3++) {
            char charAt = str.charAt(i3);
            for (int i4 = 0; i4 < length2; i4++) {
                if (cArr[i4] == charAt) {
                    if (i4 == i2) {
                        return true;
                    }
                    if (i3 < i && cArr[i4 + 1] == str.charAt(i3 + 1)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean m(String str, String str2) {
        if (str2 == null) {
            return false;
        }
        return b(str, str2.toCharArray());
    }

    public static int c(String str, char[] cArr) {
        if (a(str) || ArrayUtils.isEmpty(cArr)) {
            return -1;
        }
        int length = str.length();
        int i = length - 1;
        int length2 = cArr.length;
        int i2 = length2 - 1;
        for (int i3 = 0; i3 < length; i3++) {
            char charAt = str.charAt(i3);
            for (int i4 = 0; i4 < length2; i4++) {
                if (cArr[i4] == charAt && (i3 >= i || i4 >= i2 || cArr[i4 + 1] == str.charAt(i3 + 1))) {
                }
            }
            return i3;
        }
        return -1;
    }

    public static int n(String str, String str2) {
        if (a(str) || a(str2)) {
            return -1;
        }
        int length = str.length();
        for (int i = 0; i < length; i++) {
            boolean z = str2.indexOf(str.charAt(i)) >= 0;
            if (i + 1 < length) {
                char charAt = str.charAt(i + 1);
                if (z && str2.indexOf(charAt) < 0) {
                    return i;
                }
            } else if (!z) {
                return i;
            }
        }
        return -1;
    }

    public static boolean d(String str, char[] cArr) {
        if (cArr == null || str == null) {
            return false;
        }
        if (str.length() == 0) {
            return true;
        }
        return cArr.length != 0 && c(str, cArr) == -1;
    }

    public static boolean o(String str, String str2) {
        if (str == null || str2 == null) {
            return false;
        }
        return d(str, str2.toCharArray());
    }

    public static boolean e(String str, char[] cArr) {
        if (str == null || cArr == null) {
            return true;
        }
        int length = str.length();
        int i = length - 1;
        int length2 = cArr.length;
        int i2 = length2 - 1;
        for (int i3 = 0; i3 < length; i3++) {
            char charAt = str.charAt(i3);
            for (int i4 = 0; i4 < length2; i4++) {
                if (cArr[i4] == charAt) {
                    if (i4 == i2) {
                        return false;
                    }
                    if (i3 < i && cArr[i4 + 1] == str.charAt(i3 + 1)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static boolean p(String str, String str2) {
        if (str == null || str2 == null) {
            return true;
        }
        return e(str, str2.toCharArray());
    }

    public static int a(String str, String[] strArr) {
        int indexOf;
        if (str == null || strArr == null) {
            return -1;
        }
        int i = Integer.MAX_VALUE;
        for (String str2 : strArr) {
            if (str2 != null && (indexOf = str.indexOf(str2)) != -1 && indexOf < i) {
                i = indexOf;
            }
        }
        if (i == Integer.MAX_VALUE) {
            return -1;
        }
        return i;
    }

    public static int b(String str, String[] strArr) {
        int lastIndexOf;
        if (str == null || strArr == null) {
            return -1;
        }
        int i = -1;
        for (String str2 : strArr) {
            if (str2 != null && (lastIndexOf = str.lastIndexOf(str2)) > i) {
                i = lastIndexOf;
            }
        }
        return i;
    }

    public static String a(String str, int i) {
        if (str == null) {
            return null;
        }
        if (i < 0) {
            i = str.length() + i;
        }
        if (i < 0) {
            i = 0;
        }
        if (i > str.length()) {
            return "";
        }
        return str.substring(i);
    }

    public static String a(String str, int i, int i2) {
        if (str == null) {
            return null;
        }
        if (i2 < 0) {
            i2 = str.length() + i2;
        }
        if (i < 0) {
            i = str.length() + i;
        }
        if (i2 > str.length()) {
            i2 = str.length();
        }
        if (i > i2) {
            return "";
        }
        if (i < 0) {
            i = 0;
        }
        if (i2 < 0) {
            i2 = 0;
        }
        return str.substring(i, i2);
    }

    public static String b(String str, int i) {
        if (str == null) {
            return null;
        }
        if (i < 0) {
            return "";
        }
        if (str.length() <= i) {
            return str;
        }
        return str.substring(0, i);
    }

    public static String c(String str, int i) {
        if (str == null) {
            return null;
        }
        if (i < 0) {
            return "";
        }
        if (str.length() <= i) {
            return str;
        }
        return str.substring(str.length() - i);
    }

    public static String b(String str, int i, int i2) {
        if (str == null) {
            return null;
        }
        if (i2 < 0 || i > str.length()) {
            return "";
        }
        if (i < 0) {
            i = 0;
        }
        if (str.length() <= i + i2) {
            return str.substring(i);
        }
        return str.substring(i, i + i2);
    }

    public static String q(String str, String str2) {
        if (a(str) || str2 == null) {
            return str;
        }
        if (str2.length() == 0) {
            return "";
        }
        int indexOf = str.indexOf(str2);
        if (indexOf == -1) {
            return str;
        }
        return str.substring(0, indexOf);
    }

    public static String r(String str, String str2) {
        int indexOf;
        if (a(str)) {
            return str;
        }
        if (str2 == null || (indexOf = str.indexOf(str2)) == -1) {
            return "";
        }
        return str.substring(indexOf + str2.length());
    }

    public static String s(String str, String str2) {
        if (a(str) || a(str2)) {
            return str;
        }
        int lastIndexOf = str.lastIndexOf(str2);
        if (lastIndexOf == -1) {
            return str;
        }
        return str.substring(0, lastIndexOf);
    }

    public static String t(String str, String str2) {
        int lastIndexOf;
        if (a(str)) {
            return str;
        }
        if (a(str2) || (lastIndexOf = str.lastIndexOf(str2)) == -1 || lastIndexOf == str.length() - str2.length()) {
            return "";
        }
        return str.substring(lastIndexOf + str2.length());
    }

    public static String u(String str, String str2) {
        return a(str, str2, str2);
    }

    public static String a(String str, String str2, String str3) {
        int indexOf;
        int indexOf2;
        if (str != null && str2 != null && str3 != null && (indexOf = str.indexOf(str2)) != -1 && (indexOf2 = str.indexOf(str3, indexOf + str2.length())) != -1) {
            return str.substring(indexOf + str2.length(), indexOf2);
        }
        return null;
    }

    public static String[] b(String str, String str2, String str3) {
        int indexOf;
        int i;
        int indexOf2;
        if (str == null || a(str2) || a(str3)) {
            return null;
        }
        int length = str.length();
        if (length == 0) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        int length2 = str3.length();
        int length3 = str2.length();
        ArrayList arrayList = new ArrayList();
        int i2 = 0;
        while (true) {
            int i3 = i2;
            if (i3 >= length - length2 || (indexOf = str.indexOf(str2, i3)) < 0 || (indexOf2 = str.indexOf(str3, (i = indexOf + length3))) < 0) {
                break;
            }
            arrayList.add(str.substring(i, indexOf2));
            i2 = indexOf2 + length2;
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    public static String v(String str, String str2) {
        return a(str, str2, str2);
    }

    public static String c(String str, String str2, String str3) {
        return a(str, str2, str3);
    }

    public static String[] l(String str) {
        return g(str, null, -1);
    }

    public static String[] d(String str, char c2) {
        return a(str, c2, false);
    }

    public static String[] w(String str, String str2) {
        return c(str, str2, -1, false);
    }

    public static String[] g(String str, String str2, int i) {
        return c(str, str2, i, false);
    }

    public static String[] x(String str, String str2) {
        return b(str, str2, -1, false);
    }

    public static String[] h(String str, String str2, int i) {
        return b(str, str2, i, false);
    }

    public static String[] y(String str, String str2) {
        return b(str, str2, -1, true);
    }

    public static String[] i(String str, String str2, int i) {
        return b(str, str2, i, true);
    }

    private static String[] b(String str, String str2, int i, boolean z) {
        if (str == null) {
            return null;
        }
        int length = str.length();
        if (length == 0) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        if (str2 == null || "".equals(str2)) {
            return c(str, null, i, z);
        }
        int length2 = str2.length();
        ArrayList arrayList = new ArrayList();
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        while (i4 < length) {
            i4 = str.indexOf(str2, i3);
            if (i4 > -1) {
                if (i4 > i3) {
                    i2++;
                    if (i2 == i) {
                        i4 = length;
                        arrayList.add(str.substring(i3));
                    } else {
                        arrayList.add(str.substring(i3, i4));
                        i3 = i4 + length2;
                    }
                } else {
                    if (z) {
                        i2++;
                        if (i2 == i) {
                            i4 = length;
                            arrayList.add(str.substring(i3));
                        } else {
                            arrayList.add("");
                        }
                    }
                    i3 = i4 + length2;
                }
            } else {
                arrayList.add(str.substring(i3));
                i4 = length;
            }
        }
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    @Pointcut("execution(* org.jeecg.modules.online.cgform..*.*(..))")
    public void pointCut() {
    }

    public static String[] m(String str) {
        return c(str, null, -1, true);
    }

//    @Before("pointCut()")
//    public void a(JoinPoint joinPoint) throws Exception {
//        try {
//            if (e == null || e.length == 0) {
//                ResourceBundle a2 = org.jeecg.modules.online.cgform.d.c.a();
//                if (a2 == null) {
//                    a2 = ResourceBundle.getBundle(org.jeecg.modules.online.cgform.d.g.d());
//                }
//                if (StreamUtils.isr()) {
//                    e = new String[]{StringUtil.dl()};
//                } else {
//                    e = a2.getString(org.jeecg.modules.online.cgform.d.g.f()).split(org.jeecg.modules.online.cgform.d.b.sB);
//                }
//            }
//            if (!org.jeecg.modules.online.cgform.d.c.b(e, org.jeecg.modules.online.cgform.d.h.b()) && !org.jeecg.modules.online.cgform.d.c.b(e, org.jeecg.modules.online.cgform.d.h.a())) {
//                System.err.println(org.jeecg.modules.online.cgform.d.g.h() + org.jeecg.modules.online.cgform.d.h.c());
//                System.err.println(org.jeecg.modules.online.cgform.d.f.c("kOzlPKX8Es8/FwQdXX60NZDs5Tyl/BLPPxcEHV1+tDWQ7OU8pfwSzz8XBB1dfrQ1iN5zqKZaPYep6qn8X+n2QLKRXOKp2vFQjVEFS7T75CGCnTDPggguLlLiSRDR+DN00a2paweobc4zkqYP/0gU+d4jODOhQvZ9DFAsE4kQZpsXOzNfRrTeD+fWUnMFtAcuP5wfzEsz9Gm/ENqSW/sf0JQaTCWAxHmqaMpauPUZXNWEiuzRhMaXSDRmD4nV1DOcTqvDj5BtUWWdnJQV+by4VjVceI6gYuC5E3R+m4p6QG4wiASRPe+mpGacCousLwjL6a6Zx+iA2MXhQiPM6WjQTWIWA3TKwu105/ojzopukCuQ7OU8pfwSzz8XBB1dfrQ1kOzlPKX8Es8/FwQdXX60Nb+YHwc5536J89tvlGzFHGI=", "123456"));
//                System.exit(0);
//            }
//        } catch (Exception e2) {
//            try {
//                System.err.println(org.jeecg.modules.online.cgform.d.g.h() + org.jeecg.modules.online.cgform.d.h.c());
//                System.err.println(org.jeecg.modules.online.cgform.d.f.c("kOzlPKX8Es8/FwQdXX60NZDs5Tyl/BLPPxcEHV1+tDWQ7OU8pfwSzz8XBB1dfrQ1iN5zqKZaPYep6qn8X+n2QLKRXOKp2vFQjVEFS7T75CGCnTDPggguLlLiSRDR+DN00a2paweobc4zkqYP/0gU+d4jODOhQvZ9DFAsE4kQZpsXOzNfRrTeD+fWUnMFtAcuP5wfzEsz9Gm/ENqSW/sf0JQaTCWAxHmqaMpauPUZXNWEiuzRhMaXSDRmD4nV1DOcTqvDj5BtUWWdnJQV+by4VjVceI6gYuC5E3R+m4p6QG4wiASRPe+mpGacCousLwjL6a6Zx+iA2MXhQiPM6WjQTWIWA3TKwu105/ojzopukCuQ7OU8pfwSzz8XBB1dfrQ1kOzlPKX8Es8/FwQdXX60Nb+YHwc5536J89tvlGzFHGI=", "123456"));
//                System.exit(0);
//            } catch (Exception e3) {
//            }
//        }
//    }

    public static String[] e(String str, char c2) {
        return a(str, c2, true);
    }

    private static String[] a(String str, char c2, boolean z) {
        if (str == null) {
            return null;
        }
        int length = str.length();
        if (length == 0) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        ArrayList arrayList = new ArrayList();
        int i = 0;
        int i2 = 0;
        boolean z2 = false;
        boolean z3 = false;
        while (i < length) {
            if (str.charAt(i) == c2) {
                if (z2 || z) {
                    arrayList.add(str.substring(i2, i));
                    z2 = false;
                    z3 = true;
                }
                i++;
                i2 = i;
            } else {
                z3 = false;
                z2 = true;
                i++;
            }
        }
        if (z2 || (z && z3)) {
            arrayList.add(str.substring(i2, i));
        }
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    public static String[] z(String str, String str2) {
        return c(str, str2, -1, true);
    }

    public static String[] j(String str, String str2, int i) {
        return c(str, str2, i, true);
    }

    private static String[] c(String str, String str2, int i, boolean z) {
        if (str == null) {
            return null;
        }
        int length = str.length();
        if (length == 0) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        ArrayList arrayList = new ArrayList();
        int i2 = 1;
        int i3 = 0;
        int i4 = 0;
        boolean z2 = false;
        boolean z3 = false;
        if (str2 == null) {
            while (i3 < length) {
                if (Character.isWhitespace(str.charAt(i3))) {
                    if (z2 || z) {
                        z3 = true;
                        int i5 = i2;
                        i2++;
                        if (i5 == i) {
                            i3 = length;
                            z3 = false;
                        }
                        arrayList.add(str.substring(i4, i3));
                        z2 = false;
                    }
                    i3++;
                    i4 = i3;
                } else {
                    z3 = false;
                    z2 = true;
                    i3++;
                }
            }
        } else if (str2.length() == 1) {
            char charAt = str2.charAt(0);
            while (i3 < length) {
                if (str.charAt(i3) == charAt) {
                    if (z2 || z) {
                        z3 = true;
                        int i6 = i2;
                        i2++;
                        if (i6 == i) {
                            i3 = length;
                            z3 = false;
                        }
                        arrayList.add(str.substring(i4, i3));
                        z2 = false;
                    }
                    i3++;
                    i4 = i3;
                } else {
                    z3 = false;
                    z2 = true;
                    i3++;
                }
            }
        } else {
            while (i3 < length) {
                if (str2.indexOf(str.charAt(i3)) >= 0) {
                    if (z2 || z) {
                        z3 = true;
                        int i7 = i2;
                        i2++;
                        if (i7 == i) {
                            i3 = length;
                            z3 = false;
                        }
                        arrayList.add(str.substring(i4, i3));
                        z2 = false;
                    }
                    i3++;
                    i4 = i3;
                } else {
                    z3 = false;
                    z2 = true;
                    i3++;
                }
            }
        }
        if (z2 || (z && z3)) {
            arrayList.add(str.substring(i4, i3));
        }
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    public static String[] n(String str) {
        return a(str, false);
    }

    public static String[] o(String str) {
        return a(str, true);
    }

    private static String[] a(String str, boolean z) {
        if (str == null) {
            return null;
        }
        if (str.length() == 0) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        char[] charArray = str.toCharArray();
        ArrayList arrayList = new ArrayList();
        int i = 0;
        int type = Character.getType(charArray[0]);
        for (int i2 = 0 + 1; i2 < charArray.length; i2++) {
            int type2 = Character.getType(charArray[i2]);
            if (type2 != type) {
                if (z && type2 == 2 && type == 1) {
                    int i3 = i2 - 1;
                    if (i3 != i) {
                        arrayList.add(new String(charArray, i, i3 - i));
                        i = i3;
                    }
                } else {
                    arrayList.add(new String(charArray, i, i2 - i));
                    i = i2;
                }
                type = type2;
            }
        }
        arrayList.add(new String(charArray, i, charArray.length - i));
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    public static String a(Object[] objArr) {
        return a(objArr, (String) null);
    }

    public static String b(Object[] objArr) {
        return a(objArr, (String) null);
    }

    public static String a(Object[] objArr, char c2) {
        if (objArr == null) {
            return null;
        }
        return a(objArr, c2, 0, objArr.length);
    }

    public static String a(Object[] objArr, char c2, int i, int i2) {
        if (objArr == null) {
            return null;
        }
        int i3 = i2 - i;
        if (i3 <= 0) {
            return "";
        }
        StrBuilder strBuilder = new StrBuilder(i3 * ((objArr[i] == null ? 16 : objArr[i].toString().length()) + 1));
        for (int i4 = i; i4 < i2; i4++) {
            if (i4 > i) {
                strBuilder.append(c2);
            }
            if (objArr[i4] != null) {
                strBuilder.append(objArr[i4]);
            }
        }
        return strBuilder.toString();
    }

    public static String a(Object[] objArr, String str) {
        if (objArr == null) {
            return null;
        }
        return a(objArr, str, 0, objArr.length);
    }

    public static String a(Object[] objArr, String str, int i, int i2) {
        if (objArr == null) {
            return null;
        }
        if (str == null) {
            str = "";
        }
        int i3 = i2 - i;
        if (i3 <= 0) {
            return "";
        }
        StrBuilder strBuilder = new StrBuilder(i3 * ((objArr[i] == null ? 16 : objArr[i].toString().length()) + str.length()));
        for (int i4 = i; i4 < i2; i4++) {
            if (i4 > i) {
                strBuilder.append(str);
            }
            if (objArr[i4] != null) {
                strBuilder.append(objArr[i4]);
            }
        }
        return strBuilder.toString();
    }

    public static String a(Iterator it, char c2) {
        if (it == null) {
            return null;
        }
        if (!it.hasNext()) {
            return "";
        }
        Object next = it.next();
        if (!it.hasNext()) {
            return ObjectUtils.toString(next);
        }
        StrBuilder strBuilder = new StrBuilder(256);
        if (next != null) {
            strBuilder.append(next);
        }
        while (it.hasNext()) {
            strBuilder.append(c2);
            Object next2 = it.next();
            if (next2 != null) {
                strBuilder.append(next2);
            }
        }
        return strBuilder.toString();
    }

    public static String a(Iterator it, String str) {
        if (it == null) {
            return null;
        }
        if (!it.hasNext()) {
            return "";
        }
        Object next = it.next();
        if (!it.hasNext()) {
            return ObjectUtils.toString(next);
        }
        StrBuilder strBuilder = new StrBuilder(256);
        if (next != null) {
            strBuilder.append(next);
        }
        while (it.hasNext()) {
            if (str != null) {
                strBuilder.append(str);
            }
            Object next2 = it.next();
            if (next2 != null) {
                strBuilder.append(next2);
            }
        }
        return strBuilder.toString();
    }

    public static String a(Collection collection, char c2) {
        if (collection == null) {
            return null;
        }
        return a(collection.iterator(), c2);
    }

    public static String a(Collection collection, String str) {
        if (collection == null) {
            return null;
        }
        return a(collection.iterator(), str);
    }

    public static String p(String str) {
        if (str == null) {
            return null;
        }
        return CharSetUtils.delete(str, " \t\r\n\b");
    }

    public static String q(String str) {
        if (a(str)) {
            return str;
        }
        int length = str.length();
        char[] cArr = new char[length];
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            if (!Character.isWhitespace(str.charAt(i2))) {
                int i3 = i;
                i++;
                cArr[i3] = str.charAt(i2);
            }
        }
        if (i == length) {
            return str;
        }
        return new String(cArr, 0, i);
    }

    public static String A(String str, String str2) {
        if (a(str) || a(str2)) {
            return str;
        }
        if (str.startsWith(str2)) {
            return str.substring(str2.length());
        }
        return str;
    }

    public static String B(String str, String str2) {
        if (a(str) || a(str2)) {
            return str;
        }
        if (T(str, str2)) {
            return str.substring(str2.length());
        }
        return str;
    }

    public static String C(String str, String str2) {
        if (a(str) || a(str2)) {
            return str;
        }
        if (str.endsWith(str2)) {
            return str.substring(0, str.length() - str2.length());
        }
        return str;
    }

    public static String D(String str, String str2) {
        if (a(str) || a(str2)) {
            return str;
        }
        if (V(str, str2)) {
            return str.substring(0, str.length() - str2.length());
        }
        return str;
    }

    public static String E(String str, String str2) {
        if (a(str) || a(str2)) {
            return str;
        }
        return a(str, str2, "", -1);
    }

    public static String f(String str, char c2) {
        if (a(str) || str.indexOf(c2) == -1) {
            return str;
        }
        char[] charArray = str.toCharArray();
        int i = 0;
        for (int i2 = 0; i2 < charArray.length; i2++) {
            if (charArray[i2] != c2) {
                int i3 = i;
                i++;
                charArray[i3] = charArray[i2];
            }
        }
        return new String(charArray, 0, i);
    }

    public static String d(String str, String str2, String str3) {
        return a(str, str2, str3, 1);
    }

    public static String e(String str, String str2, String str3) {
        return a(str, str2, str3, -1);
    }

    public static String a(String str, String str2, String str3, int i) {
        if (a(str) || a(str2) || str3 == null || i == 0) {
            return str;
        }
        int i2 = 0;
        int indexOf = str.indexOf(str2, 0);
        if (indexOf == -1) {
            return str;
        }
        int length = str2.length();
        int length2 = str3.length() - length;
        StrBuilder strBuilder = new StrBuilder(str.length() + ((length2 < 0 ? 0 : length2) * (i < 0 ? 16 : i > 64 ? 64 : i)));
        while (indexOf != -1) {
            strBuilder.append(str.substring(i2, indexOf)).append(str3);
            i2 = indexOf + length;
            i--;
            if (i == 0) {
                break;
            }
            indexOf = str.indexOf(str2, i2);
        }
        strBuilder.append(str.substring(i2));
        return strBuilder.toString();
    }

    public static String a(String str, String[] strArr, String[] strArr2) {
        return a(str, strArr, strArr2, false, 0);
    }

    public static String b(String str, String[] strArr, String[] strArr2) {
        return a(str, strArr, strArr2, true, strArr == null ? 0 : strArr.length);
    }

    private static String a(String str, String[] strArr, String[] strArr2, boolean z, int i) {
        int length;
        if (str == null || str.length() == 0 || strArr == null || strArr.length == 0 || strArr2 == null || strArr2.length == 0) {
            return str;
        }
        if (i < 0) {
            throw new IllegalStateException("TimeToLive of " + i + " is less than 0: " + str);
        }
        int length2 = strArr.length;
        int length3 = strArr2.length;
        if (length2 != length3) {
            throw new IllegalArgumentException("Search and Replace array lengths don't match: " + length2 + " vs " + length3);
        }
        boolean[] zArr = new boolean[length2];
        int i2 = -1;
        int i3 = -1;
        for (int i4 = 0; i4 < length2; i4++) {
            if (!zArr[i4] && strArr[i4] != null && strArr[i4].length() != 0 && strArr2[i4] != null) {
                int indexOf = str.indexOf(strArr[i4]);
                if (indexOf == -1) {
                    zArr[i4] = true;
                } else if (i2 == -1 || indexOf < i2) {
                    i2 = indexOf;
                    i3 = i4;
                }
            }
        }
        if (i2 == -1) {
            return str;
        }
        int i5 = 0;
        int i6 = 0;
        for (int i7 = 0; i7 < strArr.length; i7++) {
            if (strArr[i7] != null && strArr2[i7] != null && (length = strArr2[i7].length() - strArr[i7].length()) > 0) {
                i6 += 3 * length;
            }
        }
        StrBuilder strBuilder = new StrBuilder(str.length() + Math.min(i6, str.length() / 5));
        while (i2 != -1) {
            for (int i8 = i5; i8 < i2; i8++) {
                strBuilder.append(str.charAt(i8));
            }
            strBuilder.append(strArr2[i3]);
            i5 = i2 + strArr[i3].length();
            i2 = -1;
            i3 = -1;
            for (int i9 = 0; i9 < length2; i9++) {
                if (!zArr[i9] && strArr[i9] != null && strArr[i9].length() != 0 && strArr2[i9] != null) {
                    int indexOf2 = str.indexOf(strArr[i9], i5);
                    if (indexOf2 == -1) {
                        zArr[i9] = true;
                    } else if (i2 == -1 || indexOf2 < i2) {
                        i2 = indexOf2;
                        i3 = i9;
                    }
                }
            }
        }
        int length4 = str.length();
        for (int i10 = i5; i10 < length4; i10++) {
            strBuilder.append(str.charAt(i10));
        }
        String strBuilder2 = strBuilder.toString();
        if (!z) {
            return strBuilder2;
        }
        return a(strBuilder2, strArr, strArr2, z, i - 1);
    }

    public static String a(String str, char c2, char c3) {
        if (str == null) {
            return null;
        }
        return str.replace(c2, c3);
    }

    public static String f(String str, String str2, String str3) {
        if (a(str) || a(str2)) {
            return str;
        }
        if (str3 == null) {
            str3 = "";
        }
        boolean z = false;
        int length = str3.length();
        int length2 = str.length();
        StrBuilder strBuilder = new StrBuilder(length2);
        for (int i = 0; i < length2; i++) {
            char charAt = str.charAt(i);
            int indexOf = str2.indexOf(charAt);
            if (indexOf >= 0) {
                z = true;
                if (indexOf < length) {
                    strBuilder.append(str3.charAt(indexOf));
                }
            } else {
                strBuilder.append(charAt);
            }
        }
        if (z) {
            return strBuilder.toString();
        }
        return str;
    }

    public static String a(String str, String str2, int i, int i2) {
        return new StrBuilder((((i + str2.length()) + str.length()) - i2) + 1).append(str.substring(0, i)).append(str2).append(str.substring(i2)).toString();
    }

    public static String b(String str, String str2, int i, int i2) {
        if (str == null) {
            return null;
        }
        if (str2 == null) {
            str2 = "";
        }
        int length = str.length();
        if (i < 0) {
            i = 0;
        }
        if (i > length) {
            i = length;
        }
        if (i2 < 0) {
            i2 = 0;
        }
        if (i2 > length) {
            i2 = length;
        }
        if (i > i2) {
            int i3 = i;
            i = i2;
            i2 = i3;
        }
        return new StrBuilder(((length + i) - i2) + str2.length() + 1).append(str.substring(0, i)).append(str2).append(str.substring(i2)).toString();
    }

    public static String r(String str) {
        if (a(str)) {
            return str;
        }
        if (str.length() == 1) {
            char charAt = str.charAt(0);
            if (charAt == '\r' || charAt == '\n') {
                return "";
            }
            return str;
        }
        int length = str.length() - 1;
        char charAt2 = str.charAt(length);
        if (charAt2 == '\n') {
            if (str.charAt(length - 1) == '\r') {
                length--;
            }
        } else if (charAt2 != '\r') {
            length++;
        }
        return str.substring(0, length);
    }

    public static String F(String str, String str2) {
        if (a(str) || str2 == null) {
            return str;
        }
        if (str.endsWith(str2)) {
            return str.substring(0, str.length() - str2.length());
        }
        return str;
    }

    public static String s(String str) {
        return G(str, "\n");
    }

    public static String G(String str, String str2) {
        if (str.length() == 0) {
            return str;
        }
        if (str2.equals(str.substring(str.length() - str2.length()))) {
            return str.substring(0, str.length() - str2.length());
        }
        return str;
    }

    public static String H(String str, String str2) {
        int lastIndexOf = str.lastIndexOf(str2);
        if (lastIndexOf == str.length() - str2.length()) {
            return str2;
        }
        if (lastIndexOf != -1) {
            return str.substring(lastIndexOf);
        }
        return "";
    }

    public static String I(String str, String str2) {
        int indexOf = str.indexOf(str2);
        if (indexOf == -1) {
            return str;
        }
        return str.substring(indexOf + str2.length());
    }

    public static String J(String str, String str2) {
        int indexOf = str.indexOf(str2);
        if (indexOf == -1) {
            return "";
        }
        return str.substring(0, indexOf + str2.length());
    }

    public static String t(String str) {
        if (str == null) {
            return null;
        }
        int length = str.length();
        if (length < 2) {
            return "";
        }
        int i = length - 1;
        String substring = str.substring(0, i);
        if (str.charAt(i) == '\n' && substring.charAt(i - 1) == '\r') {
            return substring.substring(0, i - 1);
        }
        return substring;
    }

    public static String u(String str) {
        int length = str.length() - 1;
        if (length <= 0) {
            return "";
        }
        if (str.charAt(length) == '\n') {
            if (str.charAt(length - 1) == '\r') {
                length--;
            }
        } else {
            length++;
        }
        return str.substring(0, length);
    }

    public static String v(String str) {
        return StringEscapeUtils.escapeJava(str);
    }

    public static String d(String str, int i) {
        if (str == null) {
            return null;
        }
        if (i <= 0) {
            return "";
        }
        int length = str.length();
        if (i == 1 || length == 0) {
            return str;
        }
        if (length == 1 && i <= d) {
            return a(i, str.charAt(0));
        }
        int i2 = length * i;
        switch (length) {
            case 1:
                char charAt = str.charAt(0);
                char[] cArr = new char[i2];
                for (int i3 = i - 1; i3 >= 0; i3--) {
                    cArr[i3] = charAt;
                }
                return new String(cArr);
            case 2:
                char charAt2 = str.charAt(0);
                char charAt3 = str.charAt(1);
                char[] cArr2 = new char[i2];
                for (int i4 = (i * 2) - 2; i4 >= 0; i4 = (i4 - 1) - 1) {
                    cArr2[i4] = charAt2;
                    cArr2[i4 + 1] = charAt3;
                }
                return new String(cArr2);
            default:
                StrBuilder strBuilder = new StrBuilder(i2);
                for (int i5 = 0; i5 < i; i5++) {
                    strBuilder.append(str);
                }
                return strBuilder.toString();
        }
    }

    public static String k(String str, String str2, int i) {
        if (str == null || str2 == null) {
            return d(str, i);
        }
        return C(d(str + str2, i), str2);
    }

    private static String a(int i, char c2) throws IndexOutOfBoundsException {
        if (i < 0) {
            throw new IndexOutOfBoundsException("Cannot pad a negative amount: " + i);
        }
        char[] cArr = new char[i];
        for (int i2 = 0; i2 < cArr.length; i2++) {
            cArr[i2] = c2;
        }
        return new String(cArr);
    }

    public static String e(String str, int i) {
        return a(str, i, ' ');
    }

    public static String a(String str, int i, char c2) {
        if (str == null) {
            return null;
        }
        int length = i - str.length();
        if (length <= 0) {
            return str;
        }
        if (length > d) {
            return a(str, i, String.valueOf(c2));
        }
        return str.concat(a(length, c2));
    }

    public static String a(String str, int i, String str2) {
        if (str == null) {
            return null;
        }
        if (a(str2)) {
            str2 = " ";
        }
        int length = str2.length();
        int length2 = i - str.length();
        if (length2 <= 0) {
            return str;
        }
        if (length == 1 && length2 <= d) {
            return a(str, i, str2.charAt(0));
        }
        if (length2 == length) {
            return str.concat(str2);
        }
        if (length2 < length) {
            return str.concat(str2.substring(0, length2));
        }
        char[] cArr = new char[length2];
        char[] charArray = str2.toCharArray();
        for (int i2 = 0; i2 < length2; i2++) {
            cArr[i2] = charArray[i2 % length];
        }
        return str.concat(new String(cArr));
    }

    public static String f(String str, int i) {
        return b(str, i, ' ');
    }

    public static String b(String str, int i, char c2) {
        if (str == null) {
            return null;
        }
        int length = i - str.length();
        if (length <= 0) {
            return str;
        }
        if (length > d) {
            return b(str, i, String.valueOf(c2));
        }
        return a(length, c2).concat(str);
    }

    public static String b(String str, int i, String str2) {
        if (str == null) {
            return null;
        }
        if (a(str2)) {
            str2 = " ";
        }
        int length = str2.length();
        int length2 = i - str.length();
        if (length2 <= 0) {
            return str;
        }
        if (length == 1 && length2 <= d) {
            return b(str, i, str2.charAt(0));
        }
        if (length2 == length) {
            return str2.concat(str);
        }
        if (length2 < length) {
            return str2.substring(0, length2).concat(str);
        }
        char[] cArr = new char[length2];
        char[] charArray = str2.toCharArray();
        for (int i2 = 0; i2 < length2; i2++) {
            cArr[i2] = charArray[i2 % length];
        }
        return new String(cArr).concat(str);
    }

    public static int w(String str) {
        if (str == null) {
            return 0;
        }
        return str.length();
    }

    public static String g(String str, int i) {
        return c(str, i, ' ');
    }

    public static String c(String str, int i, char c2) {
        if (str == null || i <= 0) {
            return str;
        }
        int length = str.length();
        int i2 = i - length;
        if (i2 <= 0) {
            return str;
        }
        return a(b(str, length + (i2 / 2), c2), i, c2);
    }

    public static String c(String str, int i, String str2) {
        if (str == null || i <= 0) {
            return str;
        }
        if (a(str2)) {
            str2 = " ";
        }
        int length = str.length();
        int i2 = i - length;
        if (i2 <= 0) {
            return str;
        }
        return a(b(str, length + (i2 / 2), str2), i, str2);
    }

    public static String x(String str) {
        if (str == null) {
            return null;
        }
        return str.toUpperCase();
    }

    public static String a(String str, Locale locale) {
        if (str == null) {
            return null;
        }
        return str.toUpperCase(locale);
    }

    public static String y(String str) {
        if (str == null) {
            return null;
        }
        return str.toLowerCase();
    }

    public static String b(String str, Locale locale) {
        if (str == null) {
            return null;
        }
        return str.toLowerCase(locale);
    }

    public static String z(String str) {
        int length;
        if (str == null || (length = str.length()) == 0) {
            return str;
        }
        return new StrBuilder(length).append(Character.toTitleCase(str.charAt(0))).append(str.substring(1)).toString();
    }

    public static String A(String str) {
        return z(str);
    }

    public static String B(String str) {
        int length;
        if (str == null || (length = str.length()) == 0) {
            return str;
        }
        return new StrBuilder(length).append(Character.toLowerCase(str.charAt(0))).append(str.substring(1)).toString();
    }

    public static String C(String str) {
        return B(str);
    }

    public static String D(String str) {
        int length;
        if (str == null || (length = str.length()) == 0) {
            return str;
        }
        StrBuilder strBuilder = new StrBuilder(length);
        for (int i = 0; i < length; i++) {
            char charAt = str.charAt(i);
            if (Character.isUpperCase(charAt)) {
                charAt = Character.toLowerCase(charAt);
            } else if (Character.isTitleCase(charAt)) {
                charAt = Character.toLowerCase(charAt);
            } else if (Character.isLowerCase(charAt)) {
                charAt = Character.toUpperCase(charAt);
            }
            strBuilder.append(charAt);
        }
        return strBuilder.toString();
    }

    public static String E(String str) {
        return WordUtils.capitalize(str);
    }

    public static int K(String str, String str2) {
        if (a(str) || a(str2)) {
            return 0;
        }
        int i = 0;
        int i2 = 0;
        while (true) {
            int indexOf = str.indexOf(str2, i2);
            if (indexOf != -1) {
                i++;
                i2 = indexOf + str2.length();
            } else {
                return i;
            }
        }
    }

    public static boolean F(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        for (int i = 0; i < length; i++) {
            if (!Character.isLetter(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean G(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        for (int i = 0; i < length; i++) {
            if (!Character.isLetter(str.charAt(i)) && str.charAt(i) != ' ') {
                return false;
            }
        }
        return true;
    }

    public static boolean H(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        for (int i = 0; i < length; i++) {
            if (!Character.isLetterOrDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean I(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        for (int i = 0; i < length; i++) {
            if (!Character.isLetterOrDigit(str.charAt(i)) && str.charAt(i) != ' ') {
                return false;
            }
        }
        return true;
    }

    public static boolean J(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        for (int i = 0; i < length; i++) {
            if (!CharUtils.isAsciiPrintable(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean K(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        for (int i = 0; i < length; i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean L(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        for (int i = 0; i < length; i++) {
            if (!Character.isDigit(str.charAt(i)) && str.charAt(i) != ' ') {
                return false;
            }
        }
        return true;
    }

    public static boolean M(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        for (int i = 0; i < length; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean N(String str) {
        if (str == null || a(str)) {
            return false;
        }
        int length = str.length();
        for (int i = 0; i < length; i++) {
            if (!Character.isLowerCase(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean O(String str) {
        if (str == null || a(str)) {
            return false;
        }
        int length = str.length();
        for (int i = 0; i < length; i++) {
            if (!Character.isUpperCase(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static String P(String str) {
        return str == null ? "" : str;
    }

    public static String L(String str, String str2) {
        return str == null ? str2 : str;
    }

    public static String M(String str, String str2) {
        return StringUtils.isBlank(str) ? str2 : str;
    }

    public static String N(String str, String str2) {
        return StringUtils.isEmpty(str) ? str2 : str;
    }

    public static String Q(String str) {
        if (str == null) {
            return null;
        }
        return new StrBuilder(str).reverse().toString();
    }

    public static String g(String str, char c2) {
        if (str == null) {
            return null;
        }
        String[] d2 = d(str, c2);
        ArrayUtils.reverse(d2);
        return a(d2, c2);
    }

    public static String O(String str, String str2) {
        if (str == null) {
            return null;
        }
        String[] w = w(str, str2);
        ArrayUtils.reverse(w);
        if (str2 == null) {
            return a((Object[]) w, ' ');
        }
        return a((Object[]) w, str2);
    }

    public static String h(String str, int i) {
        return c(str, 0, i);
    }

    public static String c(String str, int i, int i2) {
        if (str == null) {
            return null;
        }
        if (i2 < 4) {
            throw new IllegalArgumentException("Minimum abbreviation width is 4");
        }
        if (str.length() <= i2) {
            return str;
        }
        if (i > str.length()) {
            i = str.length();
        }
        if (str.length() - i < i2 - 3) {
            i = str.length() - (i2 - 3);
        }
        if (i <= 4) {
            return str.substring(0, i2 - 3) + "...";
        }
        if (i2 < 7) {
            throw new IllegalArgumentException("Minimum abbreviation width with offset is 7");
        }
        if (i + (i2 - 3) < str.length()) {
            return "..." + h(str.substring(i), i2 - 3);
        }
        return "..." + str.substring(str.length() - (i2 - 3));
    }

    public static String l(String str, String str2, int i) {
        if (a(str) || a(str2)) {
            return str;
        }
        if (i >= str.length() || i < str2.length() + 2) {
            return str;
        }
        int length = i - str2.length();
        int i2 = (length / 2) + (length % 2);
        int length2 = str.length() - (length / 2);
        StrBuilder strBuilder = new StrBuilder(i);
        strBuilder.append(str.substring(0, i2));
        strBuilder.append(str2);
        strBuilder.append(str.substring(length2));
        return strBuilder.toString();
    }

    public static String P(String str, String str2) {
        if (str == null) {
            return str2;
        }
        if (str2 == null) {
            return str;
        }
        int Q = Q(str, str2);
        if (Q == -1) {
            return "";
        }
        return str2.substring(Q);
    }

    public static int Q(String str, String str2) {
        if (str == str2) {
            return -1;
        }
        if (str == null || str2 == null) {
            return 0;
        }
        int i = 0;
        while (i < str.length() && i < str2.length() && str.charAt(i) == str2.charAt(i)) {
            i++;
        }
        if (i < str2.length() || i < str.length()) {
            return i;
        }
        return -1;
    }

    public static int b(String[] strArr) {
        if (strArr == null || strArr.length <= 1) {
            return -1;
        }
        boolean z = false;
        boolean z2 = true;
        int length = strArr.length;
        int i = Integer.MAX_VALUE;
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3++) {
            if (strArr[i3] == null) {
                z = true;
                i = 0;
            } else {
                z2 = false;
                i = Math.min(strArr[i3].length(), i);
                i2 = Math.max(strArr[i3].length(), i2);
            }
        }
        if (z2) {
            return -1;
        }
        if (i2 == 0 && !z) {
            return -1;
        }
        if (i == 0) {
            return 0;
        }
        int i4 = -1;
        for (int i5 = 0; i5 < i; i5++) {
            char charAt = strArr[0].charAt(i5);
            int i6 = 1;
            while (true) {
                if (i6 < length) {
                    if (strArr[i6].charAt(i5) == charAt) {
                        i6++;
                    } else {
                        i4 = i5;
                        break;
                    }
                } else {
                    break;
                }
            }
            if (i4 != -1) {
                break;
            }
        }
        if (i4 == -1 && i != i2) {
            return i;
        }
        return i4;
    }

    public static String c(String[] strArr) {
        if (strArr == null || strArr.length == 0) {
            return "";
        }
        int b2 = b(strArr);
        if (b2 == -1) {
            if (strArr[0] == null) {
                return "";
            }
            return strArr[0];
        } else if (b2 == 0) {
            return "";
        } else {
            return strArr[0].substring(0, b2);
        }
    }

    public static int R(String str, String str2) {
        if (str == null || str2 == null) {
            throw new IllegalArgumentException("Strings must not be null");
        }
        int length = str.length();
        int length2 = str2.length();
        if (length == 0) {
            return length2;
        }
        if (length2 == 0) {
            return length;
        }
        if (length > length2) {
            str = str2;
            str2 = str;
            length = length2;
            length2 = str2.length();
        }
        int[] iArr = new int[length + 1];
        int[] iArr2 = new int[length + 1];
        for (int i = 0; i <= length; i++) {
            iArr[i] = i;
        }
        for (int i2 = 1; i2 <= length2; i2++) {
            char charAt = str2.charAt(i2 - 1);
            iArr2[0] = i2;
            for (int i3 = 1; i3 <= length; i3++) {
                iArr2[i3] = Math.min(Math.min(iArr2[i3 - 1] + 1, iArr[i3] + 1), iArr[i3 - 1] + (str.charAt(i3 - 1) == charAt ? 0 : 1));
            }
            int[] iArr3 = iArr;
            iArr = iArr2;
            iArr2 = iArr3;
        }
        return iArr[length];
    }

    public static boolean S(String str, String str2) {
        return a(str, str2, false);
    }

    public static boolean T(String str, String str2) {
        return a(str, str2, true);
    }

    private static boolean a(String str, String str2, boolean z) {
        if (str == null || str2 == null) {
            return str == null && str2 == null;
        } else if (str2.length() > str.length()) {
            return false;
        } else {
            return str.regionMatches(z, 0, str2, 0, str2.length());
        }
    }

    public static boolean c(String str, String[] strArr) {
        if (a(str) || ArrayUtils.isEmpty(strArr)) {
            return false;
        }
        for (String str2 : strArr) {
            if (StringUtils.startsWith(str, str2)) {
                return true;
            }
        }
        return false;
    }

    public static boolean U(String str, String str2) {
        return b(str, str2, false);
    }

    public static boolean V(String str, String str2) {
        return b(str, str2, true);
    }

    private static boolean b(String str, String str2, boolean z) {
        if (str == null || str2 == null) {
            return str == null && str2 == null;
        } else if (str2.length() > str.length()) {
            return false;
        } else {
            return str.regionMatches(z, str.length() - str2.length(), str2, 0, str2.length());
        }
    }

    public static String R(String str) {
        String i = i(str);
        if (i == null || i.length() <= 2) {
            return i;
        }
        StrBuilder strBuilder = new StrBuilder(i.length());
        for (int i2 = 0; i2 < i.length(); i2++) {
            char charAt = i.charAt(i2);
            if (Character.isWhitespace(charAt)) {
                if (i2 > 0 && !Character.isWhitespace(i.charAt(i2 - 1))) {
                    strBuilder.append(' ');
                }
            } else {
                strBuilder.append(charAt);
            }
        }
        return strBuilder.toString();
    }

    public static boolean d(String str, String[] strArr) {
        if (a(str) || ArrayUtils.isEmpty(strArr)) {
            return false;
        }
        for (String str2 : strArr) {
            if (StringUtils.endsWith(str, str2)) {
                return true;
            }
        }
        return false;
    }
}