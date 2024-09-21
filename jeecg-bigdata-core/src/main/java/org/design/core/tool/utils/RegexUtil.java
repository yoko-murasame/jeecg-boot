package org.design.core.tool.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.lang.Nullable;

/* loaded from: bigdata-core-tool-3.1.0.jar:org/design/core/tool/utils/RegexUtil.class */
public class RegexUtil {
    public static final String USER_NAME = "^[a-zA-Z\\u4E00-\\u9FA5][a-zA-Z0-9_\\u4E00-\\u9FA5]{1,11}$";
    public static final String USER_PASSWORD = "^.{6,32}$";
    public static final String EMAIL = "^\\w+([-+.]*\\w+)*@([\\da-z](-[\\da-z])?)+(\\.{1,2}[a-z]+)+$";
    public static final String PHONE = "^1[3456789]\\d{9}$";
    public static final String EMAIL_OR_PHONE = "^\\w+([-+.]*\\w+)*@([\\da-z](-[\\da-z])?)+(\\.{1,2}[a-z]+)+$|^1[3456789]\\d{9}$";
    public static final String URL = "^(https?:\\/\\/)?([\\da-z\\.-]+)\\.([a-z\\.]{2,6})(:[\\d]+)?([\\/\\w\\.-]*)*\\/?$";
    public static final String ID_CARD = "^\\d{15}$|^\\d{17}([0-9]|X)$";
    public static final String DOMAIN = "^[0-9a-zA-Z]+[0-9a-zA-Z\\.-]*\\.[a-zA-Z]{2,4}$";

    public static boolean match(String regex, String beTestString) {
        return Pattern.compile(regex, 2).matcher(beTestString).matches();
    }

    public static boolean find(String regex, String beTestString) {
        return Pattern.compile(regex).matcher(beTestString).find();
    }

    @Nullable
    public static String findResult(String regex, String beFoundString) {
        Matcher matcher = Pattern.compile(regex).matcher(beFoundString);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }
}
