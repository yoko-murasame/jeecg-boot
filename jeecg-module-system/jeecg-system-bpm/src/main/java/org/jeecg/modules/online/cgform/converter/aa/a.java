package org.jeecg.modules.online.cgform.converter.aa;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.system.vo.DictModel;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.online.cgform.converter.FieldCommentConverter;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/* compiled from: ConfigConvert.java */
/* loaded from: hibernate-common-ol-5.4.74(2).jar:org/jeecg/modules/online/cgform/converter/a/a.class */
@Data
public class a implements FieldCommentConverter {
    protected ISysBaseAPI sysBaseAPI;
    protected String field;
    protected String table;
    protected String code;
    protected String text;

    public a() {
        this.sysBaseAPI = SpringContextUtils.getBean(ISysBaseAPI.class);
    }

    public a(String str, String str2, String str3) {
        this();
        this.table = str;
        this.code = str2;
        this.text = str3;
    }

    @Override // org.jeecg.modules.online.cgform.converter.FieldCommentConverter
    public String converterToVal(String txt) {
        String str;
        if (oConvertUtils.isNotEmpty(txt)) {
            String str2 = this.text + "= '" + txt + org.jeecg.modules.online.cgform.d.b.sz;
            int indexOf = this.table.indexOf("where");
            if (indexOf > 0) {
                str = this.table.substring(0, indexOf).trim();
                str2 = str2 + " and " + this.table.substring(indexOf + 5);
            } else {
                str = this.table;
            }
            List<DictModel> queryFilterTableDictInfo = this.sysBaseAPI.queryFilterTableDictInfo(str, this.text, this.code, str2);
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
            String str2 = this.code + "= '" + val + org.jeecg.modules.online.cgform.d.b.sz;
            int indexOf = this.table.indexOf("where");
            if (indexOf > 0) {
                str = this.table.substring(0, indexOf).trim();
                str2 = str2 + " and " + this.table.substring(indexOf + 5);
            } else {
                str = this.table;
            }
            List<DictModel> queryFilterTableDictInfo = null;
            // 表字典格式
            if (StringUtils.hasText(this.table)) {
                queryFilterTableDictInfo = this.sysBaseAPI.queryFilterTableDictInfo(str, this.text, this.code, str2);
            } else {
                // 增强方式的表字典
                queryFilterTableDictInfo = this.sysBaseAPI.loadDictItemByKeyword(this.code, val, 1);
            }
            if (queryFilterTableDictInfo != null && !queryFilterTableDictInfo.isEmpty()) {
                return queryFilterTableDictInfo.get(0).getText();
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
