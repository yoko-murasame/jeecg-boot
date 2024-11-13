package org.jeecg.modules.online.cgreport.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.DateUtils;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.online.cgform.d.b;
import org.jeecg.modules.online.cgform.d.i;
import org.jeecg.modules.online.cgreport.entity.OnlCgreportItem;
import org.jeecg.modules.online.config.b.d;
import org.jeecg.modules.online.config.exception.DBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/* compiled from: CgReportQueryParamUtil.java */
/* loaded from: hibernate-common-ol-5.4.74(2).jar:org/jeecg/modules/online/cgreport/util/a.class */
public class a {
    private static final Logger a = LoggerFactory.getLogger(a.class);

    public static void a(HttpServletRequest httpServletRequest, Map<String, Object> map, Map<String, Object> map2, Map<String, Object> map3) {
        String str = (String) map.get(org.jeecg.modules.online.cgreport.b.a.o);
        String str2 = (String) map.get(org.jeecg.modules.online.cgreport.b.a.r);
        String str3 = (String) map.get(org.jeecg.modules.online.cgreport.b.a.q);
        if (!b.SINGLE.equals(str2)) {
            if ("group".equals(str2)) {
                String parameter = httpServletRequest.getParameter(str.toLowerCase() + "_begin");
                String parameter2 = httpServletRequest.getParameter(str.toLowerCase() + "_end");
                if (oConvertUtils.isNotEmpty(parameter)) {
                    map2.put(str, " >= :" + str + "_begin");
                    map3.put(str + "_begin", a(str3, parameter, true));
                }
                if (oConvertUtils.isNotEmpty(parameter2)) {
                    map2.put(new String(str), " <= :" + str + "_end");
                    map3.put(str + "_end", a(str3, parameter2, false));
                    return;
                }
                return;
            }
            return;
        }
        String parameter3 = httpServletRequest.getParameter(str.toLowerCase());
        try {
            if (oConvertUtils.isEmpty(parameter3)) {
                return;
            }
            if (httpServletRequest.getQueryString().contains(str + b.EQ)) {
                parameter3 = new String(parameter3.getBytes("ISO-8859-1"), "UTF-8");
            }
            if (oConvertUtils.isNotEmpty(parameter3)) {
                if (parameter3.contains("*")) {
                    parameter3 = parameter3.replaceAll("\\*", "%");
                    map2.put(str, " LIKE :" + str);
                } else {
                    map2.put(str, " = :" + str);
                }
            }
            map3.put(str, a(str3, parameter3, true));
        } catch (UnsupportedEncodingException e) {
            a.error(e.getMessage(), e);
        }
    }

    private static Object a(String str, String str2, boolean z) {
        Object obj = null;
        if (oConvertUtils.isNotEmpty(str2)) {
            if (org.jeecg.modules.online.cgreport.b.a.z.equalsIgnoreCase(str)) {
                obj = str2;
            } else if ("Date".equalsIgnoreCase(str)) {
                if (str2.length() != 19 && str2.length() == 10) {
                    if (z) {
                        str2 = str2 + " 00:00:00";
                    } else {
                        str2 = str2 + " 23:59:59";
                    }
                }
                obj = DateUtils.str2Date(str2, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
            } else if (org.jeecg.modules.online.cgreport.b.a.B.equalsIgnoreCase(str)) {
                obj = str2;
            } else if ("Integer".equalsIgnoreCase(str)) {
                obj = str2;
            } else {
                obj = str2;
            }
        }
        return obj;
    }

    public static String a(List<Map<String, Object>> list, Long l) {
        JSONObject jSONObject = new JSONObject();
        JSONArray jSONArray = new JSONArray();
        jSONObject.put("total", l);
        if (list != null) {
            for (Map<String, Object> map : list) {
                JSONObject jSONObject2 = new JSONObject();
                for (String str : map.keySet()) {
                    String valueOf = String.valueOf(map.get(str));
                    String lowerCase = str.toLowerCase();
                    if (lowerCase.contains("time") || lowerCase.contains(i.DATE)) {
                        valueOf = a(valueOf);
                    }
                    jSONObject2.put(lowerCase, valueOf);
                }
                jSONArray.add(jSONObject2);
            }
        }
        jSONObject.put("rows", jSONArray);
        return jSONObject.toString();
    }

    public static String a(List<Map<String, Object>> list) {
        JSONArray jSONArray = new JSONArray();
        for (Map<String, Object> map : list) {
            JSONObject jSONObject = new JSONObject();
            for (String str : map.keySet()) {
                String valueOf = String.valueOf(map.get(str));
                String lowerCase = str.toLowerCase();
                if (lowerCase.contains("time") || lowerCase.contains(i.DATE)) {
                    valueOf = a(valueOf);
                }
                jSONObject.put(lowerCase, valueOf);
            }
            jSONArray.add(jSONObject);
        }
        return jSONArray.toString();
    }

    public static String a(String str) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(simpleDateFormat.parse(str));
        } catch (Exception e) {
            return str;
        }
    }

    public static String a(List<OnlCgreportItem> list, Map<String, Object> map, String str, String str2) {
        String substring="";
        StringBuffer stringBuffer = new StringBuffer();
        String str3 = str2;
        if (str3 == null) {
            try {
                str3 = d.getDatabaseType();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (DBException e2) {
                e2.printStackTrace();
            }
        }
        HashSet hashSet = new HashSet();
        for (OnlCgreportItem onlCgreportItem : list) {
            String fieldName = onlCgreportItem.getFieldName();
            String fieldType = onlCgreportItem.getFieldType();
            if (1 == onlCgreportItem.getIsSearch().intValue()) {
                if ("group".equals(onlCgreportItem.getSearchMode())) {
                    Object obj = map.get(fieldName + "_begin");
                    if (obj != null) {
                        stringBuffer.append(" and " + str + fieldName + org.jeecg.modules.online.cgreport.b.a.v);
                        if ("Long".equals(fieldType) || "Integer".equals(fieldType)) {
                            stringBuffer.append(obj.toString());
                        } else if ("ORACLE".equals(str3)) {
                            if (fieldType.toLowerCase().equals("datetime")) {
                                stringBuffer.append(b.a(obj.toString()));
                            } else if (fieldType.toLowerCase().equals(i.DATE)) {
                                stringBuffer.append(b.b(obj.toString()));
                            }
                        } else {
                            stringBuffer.append(b.SINGLE_QUOTE + obj.toString() + b.SINGLE_QUOTE);
                        }
                    }
                    Object obj2 = map.get(fieldName + "_end");
                    if (obj2 != null) {
                        stringBuffer.append(" and " + str + fieldName + org.jeecg.modules.online.cgreport.b.a.w);
                        if ("Long".equals(fieldType) || "Integer".equals(fieldType)) {
                            stringBuffer.append(obj2.toString());
                        } else if ("ORACLE".equals(str3)) {
                            if (fieldType.toLowerCase().equals("datetime")) {
                                stringBuffer.append(b.a(obj2.toString()));
                            } else if (fieldType.toLowerCase().equals(i.DATE)) {
                                stringBuffer.append(b.b(obj2.toString()));
                            }
                        } else {
                            stringBuffer.append(b.SINGLE_QUOTE + obj2.toString() + b.SINGLE_QUOTE);
                        }
                    }
                } else {
                    String str4 = Optional.ofNullable(map.get(fieldName)).orElse("").toString();
                    if (StringUtils.hasText(str4)) {
                        boolean z = ("Long".equals(fieldType) || "Integer".equals(fieldType)) ? false : true;
                        if ("ORACLE".equals(str3)) {
                            if (fieldType.toLowerCase().equals("datetime")) {
                                str4 = b.a(str4.toString());
                                z = false;
                            } else if (fieldType.toLowerCase().equals(i.DATE)) {
                                str4 = b.b(str4.toString());
                                z = false;
                            }
                        }
                        stringBuffer.append(" and " + QueryGenerator.getSingleQueryConditionSql(fieldName, str, str4, z, str3));
                        hashSet.add(fieldName);
                    }
                }
            }
        }
        for (String str5 : map.keySet()) {
            if (str5.startsWith(org.jeecg.modules.online.cgreport.b.a.D)) {
                String obj3 = map.get(str5).toString();
                if (!hashSet.contains(str5.substring(org.jeecg.modules.online.cgreport.b.a.D.length()))) {
                    stringBuffer.append(" and " + QueryGenerator.getSingleQueryConditionSql(substring, str, obj3, false));
                }
            }
        }
        return stringBuffer.toString();
    }
}
