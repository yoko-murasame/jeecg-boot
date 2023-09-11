package org.jeecg.modules.online.cgform.converter.bb;

import org.jeecg.modules.online.cgform.entity.OnlCgformField;

/* compiled from: TreeSelectConverter.java */
/* loaded from: hibernate-common-ol-5.4.74(2).jar:org/jeecg/modules/online/cgform/converter/b/i.class */
public class i extends org.jeecg.modules.online.cgform.converter.aa.a {
    public i(OnlCgformField onlCgformField) {
        String var2 = onlCgformField.getDictText();
        String[] var3 = var2.split(",");
        this.setTable(onlCgformField.getDictTable());
        this.setCode(var3[0]);
        this.setText(var3[2]);
    }
}
