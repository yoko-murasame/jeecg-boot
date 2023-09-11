package org.jeecg.modules.online.cgform.converter.bb;

import java.util.HashMap;
import java.util.Map;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;

/* compiled from: CatTreeConverter.java */
/* loaded from: hibernate-common-ol-5.4.74(2).jar:org/jeecg/modules/online/cgform/converter/b/a.class */
public class a extends org.jeecg.modules.online.cgform.converter.aa.a {
    private String f;

    public String getTreeText() {
        return this.f;
    }

    public void setTreeText(String treeText) {
        this.f = treeText;
    }

    public a(OnlCgformField onlCgformField) {
        super(org.jeecg.modules.online.cgform.d.b.sW, "ID", org.jeecg.modules.online.cgform.d.b.sX);
        this.f = onlCgformField.getDictText();
        this.b = onlCgformField.getDbFieldName();
    }

    @Override // org.jeecg.modules.online.cgform.converter.a.a, org.jeecg.modules.online.cgform.converter.FieldCommentConverter
    public Map<String, String> getConfig() {
        if (oConvertUtils.isEmpty(this.f)) {
            return null;
        }
        HashMap hashMap = new HashMap();
        hashMap.put("treeText", this.f);
        hashMap.put("field", this.b);
        return hashMap;
    }
}