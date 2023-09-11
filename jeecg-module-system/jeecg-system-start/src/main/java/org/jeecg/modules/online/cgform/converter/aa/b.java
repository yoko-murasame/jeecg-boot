package org.jeecg.modules.online.cgform.converter.aa;

import java.util.List;
import java.util.Map;
import org.jeecg.common.system.vo.DictModel;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.online.cgform.converter.FieldCommentConverter;

/* compiled from: ForeseeConvert.java */
/* loaded from: hibernate-common-ol-5.4.74(2).jar:org/jeecg/modules/online/cgform/converter/a/b.class */
public class b implements FieldCommentConverter {
    protected String a;
    protected List<DictModel> b;

    public String getFiled() {
        return this.a;
    }

    public void setFiled(String filed) {
        this.a = filed;
    }

    public List<DictModel> getDictList() {
        return this.b;
    }

    public void setDictList(List<DictModel> dictList) {
        this.b = dictList;
    }

    @Override // org.jeecg.modules.online.cgform.converter.FieldCommentConverter
    public String converterToVal(String txt) {
        if (oConvertUtils.isNotEmpty(txt)) {
            for (DictModel dictModel : this.b) {
                if (dictModel.getText().equals(txt)) {
                    return dictModel.getValue();
                }
            }
            return null;
        }
        return null;
    }

    @Override // org.jeecg.modules.online.cgform.converter.FieldCommentConverter
    public String converterToTxt(String val) {
        if (oConvertUtils.isNotEmpty(val)) {
            for (DictModel dictModel : this.b) {
                if (dictModel.getValue().equals(val)) {
                    return dictModel.getText();
                }
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