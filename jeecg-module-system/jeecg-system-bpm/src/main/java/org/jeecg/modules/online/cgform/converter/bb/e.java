package org.jeecg.modules.online.cgform.converter.bb;

import com.alibaba.fastjson.JSONObject;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;

import java.util.HashMap;
import java.util.Map;

/* compiled from: LinkDownConverter.java */
/* loaded from: hibernate-common-ol-5.4.74(2).jar:org/jeecg/modules/online/cgform/converter/b/e.class */
public class e extends org.jeecg.modules.online.cgform.converter.aa.a {
    private String f;

    public String getLinkField() {
        return this.f;
    }

    public void setLinkField(String linkField) {
        this.f = linkField;
    }

    public e(OnlCgformField onlCgformField) {
        org.jeecg.modules.online.cgform.a.a aVar = (org.jeecg.modules.online.cgform.a.a) JSONObject.parseObject(onlCgformField.getDictTable(), org.jeecg.modules.online.cgform.a.a.class);
        setTable(aVar.getTable());
        setCode(aVar.getKey());
        setText(aVar.getTxt());
        this.f = aVar.getLinkField();
    }

    @Override // org.jeecg.modules.online.cgform.converter.a.a, org.jeecg.modules.online.cgform.converter.FieldCommentConverter
    public Map<String, String> getConfig() {
        HashMap hashMap = new HashMap();
        hashMap.put("linkField", this.f);
        return hashMap;
    }
}
