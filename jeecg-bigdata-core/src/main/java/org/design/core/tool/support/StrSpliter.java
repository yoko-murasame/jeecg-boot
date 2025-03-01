package org.design.core.tool.support;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.design.core.tool.utils.Func;
import org.design.core.tool.utils.StringPool;
import org.design.core.tool.utils.StringUtil;

/* loaded from: bigdata-core-tool-3.1.0.jar:org/design/core/tool/support/StrSpliter.class */
public class StrSpliter {
    public static List<String> splitPath(String str) {
        return splitPath(str, 0);
    }

    public static String[] splitPathToArray(String str) {
        return toArray(splitPath(str));
    }

    public static List<String> splitPath(String str, int limit) {
        return split(str, StringPool.SLASH, limit, true, true);
    }

    public static String[] splitPathToArray(String str, int limit) {
        return toArray(splitPath(str, limit));
    }

    public static List<String> splitTrim(String str, char separator, boolean ignoreEmpty) {
        return split(str, separator, 0, true, ignoreEmpty);
    }

    public static List<String> split(String str, char separator, boolean isTrim, boolean ignoreEmpty) {
        return split(str, separator, 0, isTrim, ignoreEmpty);
    }

    public static List<String> splitTrim(String str, char separator, int limit, boolean ignoreEmpty) {
        return split(str, separator, limit, true, ignoreEmpty, false);
    }

    public static List<String> split(String str, char separator, int limit, boolean isTrim, boolean ignoreEmpty) {
        return split(str, separator, limit, isTrim, ignoreEmpty, false);
    }

    public static List<String> splitIgnoreCase(String str, char separator, int limit, boolean isTrim, boolean ignoreEmpty) {
        return split(str, separator, limit, isTrim, ignoreEmpty, true);
    }

    public static List<String> split(String str, char separator, int limit, boolean isTrim, boolean ignoreEmpty, boolean ignoreCase) {
        if (StringUtil.isEmpty(str)) {
            return new ArrayList(0);
        }
        if (limit == 1) {
            return addToList(new ArrayList(1), str, isTrim, ignoreEmpty);
        }
        ArrayList<String> list = new ArrayList<>(limit > 0 ? limit : 16);
        int len = str.length();
        int start = 0;
        for (int i = 0; i < len; i++) {
            if (Func.equals(Character.valueOf(separator), Character.valueOf(str.charAt(i)))) {
                addToList(list, str.substring(start, i), isTrim, ignoreEmpty);
                start = i + 1;
                if (limit > 0 && list.size() > limit - 2) {
                    break;
                }
            }
        }
        return addToList(list, str.substring(start, len), isTrim, ignoreEmpty);
    }

    public static String[] splitToArray(String str, char separator, int limit, boolean isTrim, boolean ignoreEmpty) {
        return toArray(split(str, separator, limit, isTrim, ignoreEmpty));
    }

    public static List<String> split(String str, String separator, boolean isTrim, boolean ignoreEmpty) {
        return split(str, separator, -1, isTrim, ignoreEmpty, false);
    }

    public static List<String> splitTrim(String str, String separator, boolean ignoreEmpty) {
        return split(str, separator, true, ignoreEmpty);
    }

    public static List<String> split(String str, String separator, int limit, boolean isTrim, boolean ignoreEmpty) {
        return split(str, separator, limit, isTrim, ignoreEmpty, false);
    }

    public static List<String> splitTrim(String str, String separator, int limit, boolean ignoreEmpty) {
        return split(str, separator, limit, true, ignoreEmpty);
    }

    public static List<String> splitIgnoreCase(String str, String separator, int limit, boolean isTrim, boolean ignoreEmpty) {
        return split(str, separator, limit, isTrim, ignoreEmpty, true);
    }

    public static List<String> splitTrimIgnoreCase(String str, String separator, int limit, boolean ignoreEmpty) {
        return split(str, separator, limit, true, ignoreEmpty, true);
    }

    public static List<String> split(String str, String separator, int limit, boolean isTrim, boolean ignoreEmpty, boolean ignoreCase) {
        if (StringUtil.isEmpty(str)) {
            return new ArrayList(0);
        }
        if (limit == 1) {
            return addToList(new ArrayList(1), str, isTrim, ignoreEmpty);
        }
        if (StringUtil.isEmpty(separator)) {
            return split(str, limit);
        }
        if (separator.length() == 1) {
            return split(str, separator.charAt(0), limit, isTrim, ignoreEmpty, ignoreCase);
        }
        ArrayList<String> list = new ArrayList<>();
        int len = str.length();
        int separatorLen = separator.length();
        int start = 0;
        int i = 0;
        while (i < len) {
            i = StringUtil.indexOf(str, separator, start, ignoreCase);
            if (i <= -1) {
                break;
            }
            addToList(list, str.substring(start, i), isTrim, ignoreEmpty);
            start = i + separatorLen;
            if (limit > 0 && list.size() > limit - 2) {
                break;
            }
        }
        return addToList(list, str.substring(start, len), isTrim, ignoreEmpty);
    }

    public static String[] splitToArray(String str, String separator, int limit, boolean isTrim, boolean ignoreEmpty) {
        return toArray(split(str, separator, limit, isTrim, ignoreEmpty));
    }

    public static List<String> split(String str, int limit) {
        if (StringUtil.isEmpty(str)) {
            return new ArrayList(0);
        }
        if (limit == 1) {
            return addToList(new ArrayList(1), str, true, true);
        }
        ArrayList<String> list = new ArrayList<>();
        int len = str.length();
        int start = 0;
        for (int i = 0; i < len; i++) {
            if (Func.isEmpty(Character.valueOf(str.charAt(i)))) {
                addToList(list, str.substring(start, i), true, true);
                start = i + 1;
                if (limit > 0 && list.size() > limit - 2) {
                    break;
                }
            }
        }
        return addToList(list, str.substring(start, len), true, true);
    }

    public static String[] splitToArray(String str, int limit) {
        return toArray(split(str, limit));
    }

    public static List<String> split(String str, Pattern separatorPattern, int limit, boolean isTrim, boolean ignoreEmpty) {
        if (StringUtil.isEmpty(str)) {
            return new ArrayList(0);
        }
        if (limit == 1) {
            return addToList(new ArrayList(1), str, isTrim, ignoreEmpty);
        }
        if (null == separatorPattern) {
            return split(str, limit);
        }
        Matcher matcher = separatorPattern.matcher(str);
        ArrayList<String> list = new ArrayList<>();
        int len = str.length();
        int start = 0;
        while (matcher.find()) {
            addToList(list, str.substring(start, matcher.start()), isTrim, ignoreEmpty);
            start = matcher.end();
            if (limit > 0 && list.size() > limit - 2) {
                break;
            }
        }
        return addToList(list, str.substring(start, len), isTrim, ignoreEmpty);
    }

    public static String[] splitToArray(String str, Pattern separatorPattern, int limit, boolean isTrim, boolean ignoreEmpty) {
        return toArray(split(str, separatorPattern, limit, isTrim, ignoreEmpty));
    }

    public static String[] splitByLength(String str, int len) {
        int partCount = str.length() / len;
        int lastPartCount = str.length() % len;
        int fixPart = 0;
        if (lastPartCount != 0) {
            fixPart = 1;
        }
        String[] strs = new String[partCount + fixPart];
        for (int i = 0; i < partCount + fixPart; i++) {
            if (i != (partCount + fixPart) - 1 || lastPartCount == 0) {
                strs[i] = str.substring(i * len, (i * len) + len);
            } else {
                strs[i] = str.substring(i * len, (i * len) + lastPartCount);
            }
        }
        return strs;
    }

    private static List<String> addToList(List<String> list, String part, boolean isTrim, boolean ignoreEmpty) {
        String part2 = part.toString();
        if (isTrim) {
            part2 = part2.trim();
        }
        if (false == ignoreEmpty || false == part2.isEmpty()) {
            list.add(part2);
        }
        return list;
    }

    private static String[] toArray(List<String> list) {
        return (String[]) list.toArray(new String[list.size()]);
    }
}
