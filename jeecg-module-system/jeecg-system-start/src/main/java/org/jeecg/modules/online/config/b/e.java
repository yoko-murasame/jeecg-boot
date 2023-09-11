package org.jeecg.modules.online.config.b;

import java.util.HashMap;
import java.util.Map;

/* compiled from: ExtendJsonConvert.java */
/* loaded from: hibernate-common-ol-5.4.74(2).jar:org/jeecg/modules/online/config/b/e.class */
public class e {
    protected static Map<String, String> a = new HashMap();

    static {
        a.put("class", "clazz");
    }

    private static String a(String str, int i) {
        String str2 = str;
        for (String str3 : a.keySet()) {
            String valueOf = String.valueOf(str3);
            String valueOf2 = String.valueOf(a.get(valueOf));
            if (i == 1) {
                str2 = str.replaceAll(valueOf, valueOf2);
            } else if (i == 2) {
                str2 = str.replaceAll(valueOf2, valueOf);
            }
        }
        return str2;
    }
}