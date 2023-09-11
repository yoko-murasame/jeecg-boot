package org.jeecg.modules.online.cgform.converter;

import java.util.Map;
import org.springframework.stereotype.Component;

/* compiled from: CustomDemoConverter.java */
@Component("customDemoConverter")
/* loaded from: hibernate-common-ol-5.4.74(2).jar:org/jeecg/modules/online/cgform/converter/c.class */
public class c implements FieldCommentConverter {
    @Override // org.jeecg.modules.online.cgform.converter.FieldCommentConverter
    public String converterToVal(String txt) {
        if (txt != null && txt.equals("管理员1")) {
            return "admin";
        }
        return txt;
    }

    @Override // org.jeecg.modules.online.cgform.converter.FieldCommentConverter
    public String converterToTxt(String val) {
        if (val != null) {
            if (val.equals("admin")) {
                return "管理员1";
            }
            if (val.equals("scott")) {
                return "管理员2";
            }
        }
        return val;
    }

    @Override // org.jeecg.modules.online.cgform.converter.FieldCommentConverter
    public Map<String, String> getConfig() {
        return null;
    }
}