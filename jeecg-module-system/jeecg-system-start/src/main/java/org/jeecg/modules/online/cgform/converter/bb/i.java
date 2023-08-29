package org.jeecg.modules.online.cgform.converter.bb;

import org.jeecg.modules.online.cgform.entity.OnlCgformField;

/* compiled from: TreeSelectConverter.java */
/* loaded from: hibernate-common-ol-5.4.74(2).jar:org/jeecg/modules/online/cgform/converter/b/i.class */
public class i extends org.jeecg.modules.online.cgform.converter.aa.a {
    public i(OnlCgformField onlCgformField) {
        String[] split = onlCgformField.getDictText().split(org.jeecg.modules.online.cgform.d.b.sB);
        setTable(onlCgformField.getDictTable());
        setCode(split[0]);
        setText(split[2]);
    }
}