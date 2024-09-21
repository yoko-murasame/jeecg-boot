package org.jeecg.modules.online.cgform.converter;

import org.jeecg.common.util.MyClassLoader;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.online.cgform.converter.bb.c;
import org.jeecg.modules.online.cgform.converter.bb.*;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: ConvertFactory.java */
/* loaded from: hibernate-common-ol-5.4.74(2).jar:org/jeecg/modules/online/cgform/converter/a.class */
public class a {
    private static final Logger a = LoggerFactory.getLogger(a.class);
    private static final String b = "list";
    private static final String c = "radio";
    private static final String d = "checkbox";
    private static final String e = "list_multi";
    private static final String f = "sel_search";
    private static final String g = "sel_tree";
    private static final String h = "cat_tree";
    private static final String i = "link_down";
    private static final String j = "sel_depart";
    private static final String k = "sel_user";
    private static final String l = "pca";
    private static final String m = "switch";

    public static FieldCommentConverter a(OnlCgformField onlCgformField) {
        // FieldCommentConverter fieldCommentConverter = null;
        // String fieldShowType = onlCgformField.getFieldShowType();
        // boolean z = true;
        // switch (fieldShowType.hashCode()) {
        //     case -1624761913:
        //         if (fieldShowType.equals("link_down")) {
        //             z = true;
        //             break;
        //         }
        //         fieldCommentConverter = new org.jeecg.modules.online.cgform.converter.bb.c(onlCgformField);
        //         break;
        //     case -889473228:
        //         if (fieldShowType.equals("switch")) {
        //             z = true;
        //             fieldCommentConverter = new org.jeecg.modules.online.cgform.converter.bb.c(onlCgformField);
        //             break;
        //         }
        //         break;
        //     case 110798:
        //         if (fieldShowType.equals("pca")) {
        //             z = true;
        //             fieldCommentConverter = new f(onlCgformField);
        //             break;
        //         }
        //         break;
        //     case 3322014:
        //         if (fieldShowType.equals("list")) {
        //             z = false;
        //             break;
        //         }
        //         fieldCommentConverter = new f(onlCgformField);
        //         break;
        //     case 45359719:
        //         if (fieldShowType.equals("cat_tree")) {
        //             z = true;
        //             fieldCommentConverter = new d(onlCgformField);
        //             break;
        //         }
        //         break;
        //     case 108270587:
        //         if (fieldShowType.equals("radio")) {
        //             z = true;
        //             fieldCommentConverter = new i(onlCgformField);
        //             break;
        //         }
        //         break;
        //     case 702184024:
        //         if (fieldShowType.equals("list_multi")) {
        //             z = true;
        //             fieldCommentConverter = new org.jeecg.modules.online.cgform.converter.bb.a(onlCgformField);
        //             break;
        //         }
        //         break;
        //     case 1186535523:
        //         if (fieldShowType.equals("sel_tree")) {
        //             z = true;
        //             fieldCommentConverter = new e(onlCgformField);
        //             break;
        //         }
        //         break;
        //     case 1186566288:
        //         if (fieldShowType.equals(k)) {
        //             z = true;
        //             fieldCommentConverter = new org.jeecg.modules.online.cgform.converter.bb.b(onlCgformField);
        //             break;
        //         }
        //         break;
        //     case 1536891843:
        //         if (fieldShowType.equals(d)) {
        //             z = true;
        //             fieldCommentConverter = new j(onlCgformField);
        //             break;
        //         }
        //         break;
        //     case 1624559481:
        //         if (fieldShowType.equals(j)) {
        //             z = true;
        //             fieldCommentConverter = new g(onlCgformField);
        //             break;
        //         }
        //         break;
        //     case 2053565741:
        //         if (fieldShowType.equals("sel_search")) {
        //             z = true;
        //             fieldCommentConverter = new h(onlCgformField);
        //             break;
        //         }
        //         break;
        //     default:
        //         fieldCommentConverter = null;
        //         break;
        // }
        // return fieldCommentConverter;

        OnlCgformField var0 = onlCgformField;
        String var1 = onlCgformField.getFieldShowType();
        FieldCommentConverter var2 = null;
        switch (var1) {
            case "list":
            case "radio":
                var2 = new c(var0);
                break;
            case "list_multi":
            case "checkbox":
                var2 = new f(var0);
                break;
            case "sel_search":
                var2 = new d(var0);
                break;
            case "sel_tree":
                var2 = new i(var0);
                break;
            case "cat_tree":
                var2 = new org.jeecg.modules.online.cgform.converter.bb.a(var0);
                break;
            case "link_down":
                var2 = new e(var0);
                break;
            case "sel_depart":
                var2 = new org.jeecg.modules.online.cgform.converter.bb.b(var0);
                break;
            case "sel_user":
                var2 = new j(var0);
                break;
            case "pca":
                var2 = new g(var0);
                break;
            case "switch":
                var2 = new h(var0);
                break;
            default:
                var2 = null;
        }
        return var2;
    }

    public static Map<String, FieldCommentConverter> a(List<OnlCgformField> list) {
        FieldCommentConverter a2;
        HashMap hashMap = new HashMap();
        for (OnlCgformField onlCgformField : list) {
            if (oConvertUtils.isNotEmpty(onlCgformField.getConverter())) {
                a2 = a(onlCgformField.getConverter().trim());
            } else {
                a2 = a(onlCgformField);
            }
            if (a2 != null) {
                hashMap.put(onlCgformField.getDbFieldName().toLowerCase(), a2);
            }
        }
        return hashMap;
    }

    private static FieldCommentConverter a(String str) {
        FieldCommentConverter fieldCommentConverter = null;
        if (str.indexOf(".") > 0) {
            try {
                fieldCommentConverter = (FieldCommentConverter) MyClassLoader.getClassByScn(str).newInstance();
            } catch (IllegalAccessException e2) {
                a.error(e2.getMessage(), e2);
            } catch (InstantiationException e3) {
                a.error(e3.getMessage(), e3);
            }
        } else {
            fieldCommentConverter = (FieldCommentConverter) SpringContextUtils.getBean(str);
        }
        if (fieldCommentConverter != null && (fieldCommentConverter instanceof FieldCommentConverter)) {
            return fieldCommentConverter;
        }
        return null;
    }
}
