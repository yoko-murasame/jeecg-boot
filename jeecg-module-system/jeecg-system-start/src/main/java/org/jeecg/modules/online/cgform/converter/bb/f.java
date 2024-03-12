package org.jeecg.modules.online.cgform.converter.bb;

import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.system.vo.DictModel;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;

import java.util.ArrayList;
import java.util.List;

/* compiled from: MultiSelectConverter.java */
/* loaded from: hibernate-common-ol-5.4.74(2).jar:org/jeecg/modules/online/cgform/converter/b/f.class */
public class f extends org.jeecg.modules.online.cgform.converter.aa.b {
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v16, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r0v20, types: [java.util.List] */
    public f(OnlCgformField onlCgformField) {
        ISysBaseAPI iSysBaseAPI = (ISysBaseAPI) SpringContextUtils.getBean(ISysBaseAPI.class);
        String dictTable = onlCgformField.getDictTable();
        String dictText = onlCgformField.getDictText();
        String dictField = onlCgformField.getDictField();
        List<DictModel> arrayList = new ArrayList();
        if (oConvertUtils.isNotEmpty(dictTable)) {
            arrayList = iSysBaseAPI.queryTableDictItemsByCode(dictTable, dictText, dictField);
        } else if (oConvertUtils.isNotEmpty(dictField)) {
            arrayList = iSysBaseAPI.queryDictItemsByCode(dictField);
        }
        this.b = arrayList;
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
