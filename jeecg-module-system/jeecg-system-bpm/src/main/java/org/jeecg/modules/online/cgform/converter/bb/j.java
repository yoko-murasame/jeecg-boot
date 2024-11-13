package org.jeecg.modules.online.cgform.converter.bb;

import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;

import java.util.ArrayList;

/* compiled from: UserSelectConverter.java */
/* loaded from: hibernate-common-ol-5.4.74(2).jar:org/jeecg/modules/online/cgform/converter/b/j.class */
public class j extends org.jeecg.modules.online.cgform.converter.aa.b {
    public j(OnlCgformField onlCgformField) {
        this.b = ((ISysBaseAPI) SpringContextUtils.getBean(ISysBaseAPI.class)).queryTableDictItemsByCode(org.jeecg.modules.online.cgform.d.b.SYS_USER, org.jeecg.modules.online.cgform.d.b.REALNAME, org.jeecg.modules.online.cgform.d.b.USERNAME);
        this.a = onlCgformField.getDbFieldName();
    }

    @Override // org.jeecg.modules.online.cgform.converter.a.b, org.jeecg.modules.online.cgform.converter.FieldCommentConverter
    public String converterToVal(String txt) {
        if (oConvertUtils.isEmpty(txt)) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (String str : txt.split(org.jeecg.modules.online.cgform.d.b.DOT_STRING)) {
            String converterToVal = super.converterToVal(str);
            if (converterToVal != null) {
                arrayList.add(converterToVal);
            }
        }
        return String.join(org.jeecg.modules.online.cgform.d.b.DOT_STRING, arrayList);
    }

    @Override // org.jeecg.modules.online.cgform.converter.a.b, org.jeecg.modules.online.cgform.converter.FieldCommentConverter
    public String converterToTxt(String val) {
        if (oConvertUtils.isEmpty(val)) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (String str : val.split(org.jeecg.modules.online.cgform.d.b.DOT_STRING)) {
            String converterToTxt = super.converterToTxt(str);
            if (converterToTxt != null) {
                arrayList.add(converterToTxt);
            }
        }
        return String.join(org.jeecg.modules.online.cgform.d.b.DOT_STRING, arrayList);
    }
}
