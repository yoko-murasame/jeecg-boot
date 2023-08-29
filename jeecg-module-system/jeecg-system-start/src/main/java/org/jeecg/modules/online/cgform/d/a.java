package org.jeecg.modules.online.cgform.d;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;
import org.jeecgframework.poi.handler.impl.ExcelDataHandlerDefaultImpl;
import org.jeecgframework.poi.util.PoiPublicUtil;

/* compiled from: CgFormExcelHandler.java */
/* loaded from: hibernate-common-ol-5.4.74(2).jar:org/jeecg/modules/online/cgform/d/a.class */
public class a extends ExcelDataHandlerDefaultImpl {
    Map<String, OnlCgformField> a;
    String c;
    String e;
    String d = "online";
    ISysBaseAPI sysBaseAPI = (ISysBaseAPI) SpringContextUtils.getBean(ISysBaseAPI.class);

    public a(List<OnlCgformField> list, String str, String str2) {
        this.a = a(list);
        this.c = str;
        this.e = str2;
    }

    private Map<String, OnlCgformField> a(List<OnlCgformField> list) {
        HashMap hashMap = new HashMap();
        for (OnlCgformField onlCgformField : list) {
            hashMap.put(onlCgformField.getDbFieldTxt(), onlCgformField);
        }
        return hashMap;
    }

    public void setMapValue(Map<String, Object> map, String originKey, Object value) {
        String a = a(originKey);
        if (value instanceof Double) {
            map.put(a, PoiPublicUtil.doubleToString((Double) value));
        } else if (value instanceof byte[]) {
            String a2 = b.a((byte[]) value, this.c, this.d, this.e);
            if (a2 != null) {
                map.put(a, a2);
            }
        } else {
            map.put(a, value == null ? "" : value.toString());
        }
    }

    private String a(String str) {
        if (this.a.containsKey(str)) {
            return "$mainTable$" + this.a.get(str).getDbFieldName();
        }
        return "$subTable$" + str;
    }
}