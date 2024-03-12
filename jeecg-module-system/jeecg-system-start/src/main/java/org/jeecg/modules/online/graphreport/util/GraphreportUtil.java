package org.jeecg.modules.online.graphreport.util;

import com.alibaba.fastjson.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.DictModel;
import org.jeecg.modules.online.cgform.d.b;
import org.jeecg.modules.online.cgform.d.k;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: GraphreportUtil.java */
/* renamed from: org.jeecg.modules.online.graphreport.b.a */
/* loaded from: hibernate-common-ol-5.4.74.jar:org/jeecg/modules/online/graphreport/b/a.class */
public class GraphreportUtil extends b {

    /* renamed from: ay */
    public static final String f4ay = "SELECT * FROM (";

    /* renamed from: az */
    public static final String f5az = ") temp_table WHERE 1=1";

    /* renamed from: g */
    public static List<Map<String, Object>> m2g(String str) {
        ArrayList arrayList = new ArrayList();
        try {
            JSONArray parseArray = JSONArray.parseArray(URLDecoder.decode(str, "UTF-8"));
            if (parseArray == null) {
                return null;
            }
            for (int i = 0; i < parseArray.size(); i++) {
                arrayList.add(parseArray.getJSONObject(i));
            }
            return arrayList;
        } catch (Exception e) {
            return null;
        }
    }

    /* renamed from: a */
    public static String m5a(String str, String str2) {
        return m3e(str, m2g(str2));
    }

    /* renamed from: e */
    public static String m3e(String str, List<Map<String, Object>> list) {
        if (list == null) {
            return str;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(f4ay);
        if (str.trim().lastIndexOf(";") == str.length() - 1) {
            str = str.substring(0, str.length() - 1);
        }
        stringBuffer.append(str);
        stringBuffer.append(f5az);
        try {
            for (Map<String, Object> map : list) {
                String obj = map.get("value").toString();
                String obj2 = map.get("fieldName").toString();
                String obj3 = map.get("fieldType").toString();
                if (!"single".equalsIgnoreCase(map.get("searchMode").toString())) {
                    JSONArray parseArray = JSONArray.parseArray(obj);
                    Object obj4 = parseArray.get(0);
                    Object obj5 = parseArray.get(1);
                    if (obj4 != null) {
                        stringBuffer.append(" AND " + obj2 + ">=");
                        if (k.isNumber(obj3)) {
                            stringBuffer.append(obj4.toString());
                        } else {
                            stringBuffer.append("'" + obj4.toString() + "'");
                        }
                    }
                    if (obj5 != null) {
                        stringBuffer.append(" AND " + obj2 + "<=");
                        if (k.isNumber(obj3)) {
                            stringBuffer.append(obj5.toString());
                        } else {
                            stringBuffer.append("'" + obj5.toString() + "'");
                        }
                    }
                } else if (!StringUtils.isEmpty(obj)) {
                    stringBuffer.append(" AND " + QueryGenerator.getSingleQueryConditionSql(obj2.toLowerCase(), "", obj, !k.isNumber(obj3)));
                }
            }
            return stringBuffer.toString();
        } catch (Exception e) {
            return str;
        }
    }

    /* renamed from: c */
    public static String m4c(List<DictModel> list, String str) {
        if (str == null || list == null || list.size() <= 0) {
            return str;
        }
        ArrayList arrayList = new ArrayList();
        String[] split = str.trim().split(",");
        for (String str2 : split) {
            String str3 = str2;
            Iterator<DictModel> it = list.iterator();
            while (true) {
                if (it.hasNext()) {
                    DictModel next = it.next();
                    if (next.getValue().equals(str2)) {
                        str3 = next.getText();
                        break;
                    }
                }
            }
            arrayList.add(str3);
        }
        return String.join(",", arrayList);
    }
}
