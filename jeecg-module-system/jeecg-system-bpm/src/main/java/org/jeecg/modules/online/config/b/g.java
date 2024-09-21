package org.jeecg.modules.online.config.b;

import freemarker.template.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringWriter;
import java.util.Map;

/* compiled from: FreemarkerHelper.java */
/* loaded from: hibernate-common-ol-5.4.74(2).jar:org/jeecg/modules/online/config/b/g.class */
public class g {
    private static final Logger a = LoggerFactory.getLogger(g.class);
    private static Configuration b = new Configuration(Configuration.VERSION_2_3_28);

    static {
        b.setNumberFormat("0.#####################");
        b.setClassForTemplateLoading(g.class, "/");
    }

    public static String a(String str, String str2, Map<String, Object> map) {
        try {
            StringWriter stringWriter = new StringWriter();
            b.getTemplate(str, str2).process(map, stringWriter);
            return stringWriter.toString();
        } catch (Exception e) {
            a.error(e.getMessage(), e);
            return e.toString();
        }
    }

    public static String a(String str, Map<String, Object> map) {
        return a(str, "utf-8", map);
    }
}
