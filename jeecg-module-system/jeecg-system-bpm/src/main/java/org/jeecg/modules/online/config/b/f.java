package org.jeecg.modules.online.config.b;

import org.jeecg.modules.online.cgform.entity.OnlCgformField;

import java.util.Comparator;

/* compiled from: FieldNumComparator.java */
/* loaded from: hibernate-common-ol-5.4.74(2).jar:org/jeecg/modules/online/config/b/f.class */
public class f implements Comparator<OnlCgformField> {
    @Override // java.util.Comparator
    /* renamed from: a */
    public int compare(OnlCgformField onlCgformField, OnlCgformField onlCgformField2) {
        if (onlCgformField == null || onlCgformField.getOrderNum() == null || onlCgformField2 == null || onlCgformField2.getOrderNum() == null) {
            return -1;
        }
        Integer orderNum = onlCgformField.getOrderNum();
        Integer orderNum2 = onlCgformField2.getOrderNum();
        if (orderNum.intValue() < orderNum2.intValue()) {
            return -1;
        }
        return orderNum == orderNum2 ? 0 : 1;
    }
}
