package org.jeecg.modules.online.cgform.converter.bb;

import org.jeecg.common.constant.ProvinceCityArea;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;

/* compiled from: PcaConverter.java */
/* loaded from: hibernate-common-ol-5.4.74(2).jar:org/jeecg/modules/online/cgform/converter/b/g.class */
public class g extends org.jeecg.modules.online.cgform.converter.aa.b {
    ProvinceCityArea c;

    public g(OnlCgformField onlCgformField) {
        this.a = onlCgformField.getDbFieldName();
        this.c = (ProvinceCityArea) SpringContextUtils.getBean(ProvinceCityArea.class);
    }

    @Override // org.jeecg.modules.online.cgform.converter.a.b, org.jeecg.modules.online.cgform.converter.FieldCommentConverter
    public String converterToVal(String txt) {
        if (oConvertUtils.isEmpty(txt)) {
            return null;
        }
        return this.c.getCode(txt);
    }

    @Override // org.jeecg.modules.online.cgform.converter.a.b, org.jeecg.modules.online.cgform.converter.FieldCommentConverter
    public String converterToTxt(String val) {
        if (oConvertUtils.isEmpty(val)) {
            return null;
        }
        return this.c.getText(val);
    }
}