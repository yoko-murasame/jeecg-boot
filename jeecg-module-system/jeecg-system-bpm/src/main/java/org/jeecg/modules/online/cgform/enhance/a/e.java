package org.jeecg.modules.online.cgform.enhance.a;

import org.jeecg.modules.online.cgform.enhance.CgformEnhanceJavaListInter;
import org.jeecg.modules.online.config.exception.BusinessException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* compiled from: CgformEnhanceQueryDemo.java */
@Component("cgformEnhanceQueryDemo")
/* loaded from: hibernate-common-ol-5.4.74(2).jar:org/jeecg/modules/online/cgform/enhance/a/e.class */
public class e implements CgformEnhanceJavaListInter {
    @Override // org.jeecg.modules.online.cgform.enhance.CgformEnhanceJavaListInter
    public void execute(String tableName, List<Map<String, Object>> data) throws BusinessException {
        List<a> a2 = a();
        for (Map<String, Object> map : data) {
            Object obj = map.get("province");
            if (obj != null) {
                map.put("province", (String) a2.stream().filter(aVar -> {
                    return obj.toString().equals(aVar.a());
                }).map((v0) -> {
                    return v0.b();
                }).findAny().orElse(""));
            }
        }
    }

    private List<a> a() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new a("bj", "北京"));
        arrayList.add(new a("sd", "山东"));
        arrayList.add(new a("ah", "安徽"));
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: CgformEnhanceQueryDemo.java */
    /* loaded from: hibernate-common-ol-5.4.74(2).jar:org/jeecg/modules/online/cgform/enhance/a/e$a.class */
    public class a {
        String a;
        String b;

        public a(String str, String str2) {
            this.a = str;
            this.b = str2;
        }

        public String a() {
            return this.a;
        }

        public String b() {
            return this.b;
        }
    }
}
