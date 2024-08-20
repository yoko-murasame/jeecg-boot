package org.jeecg.modules.online.cgreport.util;

import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.online.cgform.d.b;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: hibernate-common-ol-5.4.74(2).jar:org/jeecg/modules/online/cgreport/util/SqlUtil.class */
public class SqlUtil {
    private static final Logger sm = LoggerFactory.getLogger(SqlUtil.class);
    public static final String sa = "select * from ( {0}) sel_tab00 limit {1},{2}";
    public static final String sb = "select * from ( {0}) sel_tab00 limit {2} offset {1}";
    public static final String sc = "select * from (select row_.*,rownum rownum_ from ({0}) row_ where rownum <= {1}) where rownum_>{2}";
    public static final String sd = "select * from ( select row_number() over(order by tempColumn) tempRowNumber, * from (select top {1} tempColumn = 0, {0}) t ) tt where tempRowNumber > {2}";
    public static final String se = "select distinct table_name from information_schema.columns where table_schema = {0}";
    public static final String sf = "SELECT distinct c.relname AS  table_name FROM pg_class c";
    public static final String sg = "select distinct colstable.table_name as  table_name from user_tab_cols colstable";
    public static final String sh = "select distinct c.name as  table_name from sys.objects c";
    public static final String si = "select column_name from information_schema.columns where table_name = {0} and table_schema = {1}";
    public static final String sj = "select table_name from information_schema.columns where table_name = {0}";
    public static final String sk = "select column_name from all_tab_columns where table_name ={0}";
    public static final String sl = "select name from syscolumns where id={0}";

    public static String a(String str, Map map) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t.* FROM ( ");
        sb.append(str + " ");
        sb.append(") t ");
        if (map != null && map.size() >= 1) {
            sb.append("WHERE 1=1  ");
            for (Object obj : map.keySet()) {
                String valueOf = String.valueOf(obj);
                String valueOf2 = String.valueOf(map.get(valueOf));
                if (oConvertUtils.isNotEmpty(valueOf2)) {
                    sb.append(b.AND);
                    sb.append(" " + valueOf + valueOf2);
                }
            }
        }
        return sb.toString();
    }

    public static String b(String str, Map map) {
        return "SELECT COUNT(*) COUNT FROM (" + a(str, map) + ") t2";
    }

    public static String a(String str, Map map, int i2, int i3) {
        String[] strArr = {str, ((i2 - 1) * i3) + "", i3 + ""};
        if ("jdbc:mysql://127.0.0.1:3306/jeecg-boot?characterEncoding=UTF-8&useUnicode=true&useSSL=false".indexOf("MYSQL") != -1) {
            str = MessageFormat.format(sa, strArr);
        } else if ("jdbc:mysql://127.0.0.1:3306/jeecg-boot?characterEncoding=UTF-8&useUnicode=true&useSSL=false".indexOf("POSTGRESQL") != -1) {
            str = MessageFormat.format(sb, strArr);
        } else {
            int i4 = (i2 - 1) * i3;
            strArr[2] = Integer.toString(i4);
            strArr[1] = Integer.toString(i4 + i3);
            if ("jdbc:mysql://127.0.0.1:3306/jeecg-boot?characterEncoding=UTF-8&useUnicode=true&useSSL=false".indexOf("ORACLE") != -1) {
                str = MessageFormat.format(sc, strArr);
            } else if ("jdbc:mysql://127.0.0.1:3306/jeecg-boot?characterEncoding=UTF-8&useUnicode=true&useSSL=false".indexOf("SQLSERVER") != -1) {
                strArr[0] = str.substring(d(str));
                str = MessageFormat.format(sd, strArr);
            }
        }
        return str;
    }

    public static String a(String str, String str2, String str3, Map map, int i2, int i3) {
        String a2 = a(str3, map);
        String[] strArr = {a2, ((i2 - 1) * i3) + "", i3 + ""};
        if (!"MYSQL".equalsIgnoreCase("")) {
            if ("POSTGRESQL".equalsIgnoreCase("")) {
                a2 = MessageFormat.format(sb, strArr);
            } else {
                int i4 = (i2 - 1) * i3;
                strArr[2] = Integer.toString(i4);
                strArr[1] = Integer.toString(i4 + i3);
                if (!"ORACLE".equalsIgnoreCase("")) {
                    if ("SQLSERVER".equalsIgnoreCase("")) {
                        strArr[0] = a2.substring(d(a2));
                        a2 = MessageFormat.format(sd, strArr);
                    }
                } else {
                    a2 = MessageFormat.format(sc, strArr);
                }
            }
        } else {
            a2 = MessageFormat.format(sa, strArr);
        }
        return a2;
    }

    private static int d(String str) {
        int indexOf = str.toLowerCase().indexOf("select");
        return indexOf + (str.toLowerCase().indexOf("select distinct") == indexOf ? 15 : 6);
    }

    public static String a(String str, String... strArr) {
        if (oConvertUtils.isNotEmpty(str)) {
            if ("MYSQL".equals(str)) {
                return MessageFormat.format(se, strArr);
            }
            if ("ORACLE".equals(str)) {
                return sg;
            }
            if ("POSTGRESQL".equals(str)) {
                return sf;
            }
            if ("SQLSERVER".equals(str)) {
                return sh;
            }
            return null;
        }
        return null;
    }

    public static String b(String str, String... strArr) {
        if (oConvertUtils.isNotEmpty(str)) {
            if ("MYSQL".equals(str)) {
                return MessageFormat.format(si, strArr);
            }
            if ("ORACLE".equals(str)) {
                return MessageFormat.format(sk, strArr);
            }
            if ("POSTGRESQL".equals(str)) {
                return MessageFormat.format(sj, strArr);
            }
            if ("SQLSERVER".equals(str)) {
                return MessageFormat.format(sl, strArr);
            }
            return null;
        }
        return null;
    }

    public static String a(String str) {
        String replace;
        String lowerCase = str.toLowerCase();
        Matcher matcher = Pattern.compile("(\\w|\\.)+ *\\S+ *\\S*\\$\\{\\w+\\}\\S*").matcher(lowerCase);
        while (matcher.find()) {
            String group = matcher.group();
            sm.info("${}匹配带参SQL片段 ==>" + group);
            if (group.indexOf("where") != -1) {
                replace = lowerCase.replace(group, "where 1=1");
            } else if (group.indexOf("and") != -1) {
                replace = lowerCase.replace(group, "and 1=1");
            } else if (group.indexOf("or") != -1) {
                replace = lowerCase.replace(group, "or 1=1");
            } else {
                replace = lowerCase.replace(group, "1=1");
            }
            lowerCase = replace;
            sm.info("${}替换后结果 ==>" + lowerCase);
        }
        return lowerCase.replaceAll("(?i)AND *1=1", "");
    }

    public static void main(String[] args) {
        System.out.println(a("select * from sys_user where id   ='${id}' and del_flag=  ${flag}"));
    }

    public static Map<String, Object> a(HttpServletRequest httpServletRequest) {
        String str;
        String[] strArr;
        Map<String,String[]> parameterMap = httpServletRequest.getParameterMap();
        HashMap hashMap = new HashMap();
        String str2 = "";
        for (Map.Entry entry : parameterMap.entrySet()) {
            String str3 = (String) entry.getKey();
            Object value = entry.getValue();
            if ("_t".equals(str3) || null == value) {
                str = "";
            } else if (value instanceof String[]) {
                strArr=new String[((String[]) value).length];
                for (int i2 = 0; i2 < ((String[]) value).length; i2++) {
                    str2 = strArr[i2] + b.DOT_STRING;
                }
                str = str2.substring(0, str2.length() - 1);
            } else {
                str = value.toString();
            }
            str2 = str;
            hashMap.put(str3, str2);
        }
        return hashMap;
    }

    public static String b(String str) {
        String convertSystemVariables = QueryGenerator.convertSystemVariables(str);
        String allConfigAuth = QueryGenerator.getAllConfigAuth();
        if (str.toLowerCase().indexOf("where") > 0) {
            return convertSystemVariables + allConfigAuth;
        }
        return convertSystemVariables + b.WHERE_1_1 + allConfigAuth;
    }

    public static boolean c(String str) {
        return str.toLowerCase().indexOf("select") == 0;
    }
}
