package org.jeecg.modules.online.cgform.converter.bb;

import com.alibaba.fastjson.JSONArray;
import org.jeecg.common.system.vo.DictModel;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;

import java.util.ArrayList;

/* compiled from: SwitchConverter.java */
/* loaded from: hibernate-common-ol-5.4.74(2).jar:org/jeecg/modules/online/cgform/converter/b/h.class */
public class h extends org.jeecg.modules.online.cgform.converter.aa.b {
    public h(OnlCgformField onlCgformField) {
        JSONArray parseArray;
        String fieldExtendJson = onlCgformField.getFieldExtendJson();
        String str = "Y";
        String str2 = "N";
        if (fieldExtendJson != null && !"".equals(fieldExtendJson) && (parseArray = JSONArray.parseArray(fieldExtendJson)) != null && parseArray.size() == 2) {
            str = parseArray.get(0).toString();
            str2 = parseArray.get(1).toString();
        }
        ArrayList arrayList = new ArrayList();
        DictModel dictModel = new DictModel(str, "是");
        DictModel dictModel2 = new DictModel(str2, "否");
        arrayList.add(dictModel);
        arrayList.add(dictModel2);
        this.b = arrayList;
        this.a = onlCgformField.getDbFieldName();
    }
}
