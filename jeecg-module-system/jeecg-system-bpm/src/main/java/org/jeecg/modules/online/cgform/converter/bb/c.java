package org.jeecg.modules.online.cgform.converter.bb;

import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.system.vo.DictModel;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;

import java.util.ArrayList;
import java.util.List;

/* compiled from: DictEasyConverter.java */
/* loaded from: hibernate-common-ol-5.4.74(2).jar:org/jeecg/modules/online/cgform/converter/b/c.class */
public class c extends org.jeecg.modules.online.cgform.converter.aa.b {
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v16, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r0v20, types: [java.util.List] */
    public c(OnlCgformField onlCgformField) {
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
}
