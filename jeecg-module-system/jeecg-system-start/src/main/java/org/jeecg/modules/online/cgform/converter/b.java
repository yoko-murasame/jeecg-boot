package org.jeecg.modules.online.cgform.converter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;

/* compiled from: ConvertUtil.java */
/* loaded from: hibernate-common-ol-5.4.74(2).jar:org/jeecg/modules/online/cgform/converter/b.class */
public class b {
    public static final int sa = 2;
    public static final int sb = 1;

    public static void a(int i, List<Map<String, Object>> list, List<OnlCgformField> list2) {
        Map<String, FieldCommentConverter> a2 = a.a(list2);
        for (Map<String, Object> map : list) {
            HashMap<String, Object> hashMap = new HashMap();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                Object value = entry.getValue();
                if (value != null) {
                    String key = entry.getKey();
                    FieldCommentConverter fieldCommentConverter = a2.get(key.toLowerCase());
                    if (fieldCommentConverter != null) {
                        String obj = value.toString();
                        String converterToTxt = i == 1 ? fieldCommentConverter.converterToTxt(obj) : fieldCommentConverter.converterToVal(obj);
                        a(fieldCommentConverter, map, i);
                        a(fieldCommentConverter, hashMap, obj);
                        map.put(key, converterToTxt);
                    }
                }
            }
            for (String str : hashMap.keySet()) {
                map.put(str, hashMap.get(str));
            }
        }
    }

    private static void a(FieldCommentConverter fieldCommentConverter, Map<String, Object> map, int i) {
        String[] split;
        Map<String, String> config = fieldCommentConverter.getConfig();
        if (config != null) {
            String str = config.get("linkField");
            if (oConvertUtils.isNotEmpty(str)) {
                for (String str2 : str.split(org.jeecg.modules.online.cgform.d.b.sB)) {
                    Object obj = map.get(str2);
                    if (obj != null) {
                        String obj2 = obj.toString();
                        map.put(str2, i == 1 ? fieldCommentConverter.converterToTxt(obj2) : fieldCommentConverter.converterToVal(obj2));
                    }
                }
            }
        }
    }

    private static void a(FieldCommentConverter fieldCommentConverter, Map<String, Object> map, String str) {
        Map<String, String> config = fieldCommentConverter.getConfig();
        if (config != null) {
            String str2 = config.get("treeText");
            if (oConvertUtils.isNotEmpty(str2)) {
                map.put(str2, str);
            }
        }
    }
}