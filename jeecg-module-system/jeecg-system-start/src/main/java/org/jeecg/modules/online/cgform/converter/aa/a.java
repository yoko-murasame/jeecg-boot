package org.jeecg.modules.online.cgform.converter.aa;

import java.util.List;
import java.util.Map;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.system.vo.DictModel;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.online.cgform.converter.FieldCommentConverter;

/* compiled from: ConfigConvert.java */
/* loaded from: hibernate-common-ol-5.4.74(2).jar:org/jeecg/modules/online/cgform/converter/a/a.class */
public class a implements FieldCommentConverter {
    protected ISysBaseAPI a;
    protected String b;
    protected String c;
    protected String d;
    protected String e;

    public a() {
        this.a = (ISysBaseAPI) SpringContextUtils.getBean(ISysBaseAPI.class);
    }

    public a(String str, String str2, String str3) {
        this();
        this.c = str;
        this.d = str2;
        this.e = str3;
    }

    public String getField() {
        return this.b;
    }

    public void setField(String field) {
        this.b = field;
    }

    public String getTable() {
        return this.c;
    }

    public void setTable(String table) {
        this.c = table;
    }

    public String getCode() {
        return this.d;
    }

    public void setCode(String code) {
        this.d = code;
    }

    public String getText() {
        return this.e;
    }

    public void setText(String text) {
        this.e = text;
    }

    @Override // org.jeecg.modules.online.cgform.converter.FieldCommentConverter
    public String converterToVal(String txt) {
        String str;
        if (oConvertUtils.isNotEmpty(txt)) {
            String str2 = this.e + "= '" + txt + org.jeecg.modules.online.cgform.d.b.sz;
            int indexOf = this.c.indexOf("where");
            if (indexOf > 0) {
                str = this.c.substring(0, indexOf).trim();
                str2 = str2 + " and " + this.c.substring(indexOf + 5);
            } else {
                str = this.c;
            }
            List queryFilterTableDictInfo = this.a.queryFilterTableDictInfo(str, this.e, this.d, str2);
            if (queryFilterTableDictInfo != null && queryFilterTableDictInfo.size() > 0) {
                return ((DictModel) queryFilterTableDictInfo.get(0)).getValue();
            }
            return null;
        }
        return null;
    }

    @Override // org.jeecg.modules.online.cgform.converter.FieldCommentConverter
    public String converterToTxt(String val) {
        String str;
        if (oConvertUtils.isNotEmpty(val)) {
            String str2 = this.d + "= '" + val + org.jeecg.modules.online.cgform.d.b.sz;
            int indexOf = this.c.indexOf("where");
            if (indexOf > 0) {
                str = this.c.substring(0, indexOf).trim();
                str2 = str2 + " and " + this.c.substring(indexOf + 5);
            } else {
                str = this.c;
            }
            List queryFilterTableDictInfo = this.a.queryFilterTableDictInfo(str, this.e, this.d, str2);
            if (queryFilterTableDictInfo != null && queryFilterTableDictInfo.size() > 0) {
                return ((DictModel) queryFilterTableDictInfo.get(0)).getText();
            }
            return null;
        }
        return null;
    }

    @Override // org.jeecg.modules.online.cgform.converter.FieldCommentConverter
    public Map<String, String> getConfig() {
        return null;
    }
}