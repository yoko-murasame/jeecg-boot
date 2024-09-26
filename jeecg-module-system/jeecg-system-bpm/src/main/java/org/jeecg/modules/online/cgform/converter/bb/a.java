package org.jeecg.modules.online.cgform.converter.bb;

import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;

import java.util.HashMap;
import java.util.Map;

/* compiled from: CatTreeConverter.java */
/* loaded from: hibernate-common-ol-5.4.74(2).jar:org/jeecg/modules/online/cgform/converter/b/a.class */
public class a extends org.jeecg.modules.online.cgform.converter.aa.a {
    private String treeText;

    public String getTreeText() {
        return this.treeText;
    }

    public void setTreeText(String treeText) {
        this.treeText = treeText;
    }

    public a(OnlCgformField onlCgformField) {
        super(org.jeecg.modules.online.cgform.d.b.sW, "ID", org.jeecg.modules.online.cgform.d.b.sX);
        this.treeText = onlCgformField.getDictText();
        this.field = onlCgformField.getDbFieldName();
    }

    @Override // org.jeecg.modules.online.cgform.converter.a.a, org.jeecg.modules.online.cgform.converter.FieldCommentConverter
    public Map<String, String> getConfig() {
        if (oConvertUtils.isEmpty(this.treeText)) {
            return null;
        }
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("treeText", this.treeText);
        hashMap.put("field", this.field);
        return hashMap;
    }
}
