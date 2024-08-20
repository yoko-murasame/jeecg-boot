package org.jeecg.modules.online.cgform.enhance.a;

import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.system.vo.SysCategoryModel;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.online.cgform.enhance.CgformEnhanceJavaListInter;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;
import org.jeecg.modules.online.cgform.service.IOnlCgformFieldService;
import org.jeecg.modules.online.config.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/* compiled from: CgformEnhanceExportDemo.java */
@Component("cgformEnhanceExportDemo")
/* loaded from: hibernate-common-ol-5.4.74(2).jar:org/jeecg/modules/online/cgform/enhance/a/b.class */
public class b implements CgformEnhanceJavaListInter {
    @Autowired
    ISysBaseAPI sysBaseAPI;
    @Autowired
    IOnlCgformFieldService onlCgformFieldService;

    @Override // org.jeecg.modules.online.cgform.enhance.CgformEnhanceJavaListInter
    public void execute(String tableName, List<Map<String, Object>> data) throws BusinessException {
        OnlCgformField queryFormFieldByTableNameAndField;
        List queryTableDictByKeys;
        List<SysCategoryModel> queryAllDSysCategory = this.sysBaseAPI.queryAllDSysCategory();
        for (Map<String, Object> map : data) {
            String string = oConvertUtils.getString(map.get("fen_tree"));
            if (!oConvertUtils.isEmpty(string)) {
                List list = (List) queryAllDSysCategory.stream().filter(sysCategoryModel -> {
                    return sysCategoryModel.getId().equals(string);
                }).collect(Collectors.toList());
                if (list != null && list.size() != 0) {
                    map.put("fen_tree", ((SysCategoryModel) list.get(0)).getName());
                }
                String string2 = oConvertUtils.getString(map.get("sel_search"));
                if (!oConvertUtils.isEmpty(string2) && (queryFormFieldByTableNameAndField = this.onlCgformFieldService.queryFormFieldByTableNameAndField(tableName, "sel_search")) != null && !oConvertUtils.isEmpty(queryFormFieldByTableNameAndField.getDictTable()) && (queryTableDictByKeys = this.sysBaseAPI.queryTableDictByKeys(queryFormFieldByTableNameAndField.getDictTable(), queryFormFieldByTableNameAndField.getDictText(), queryFormFieldByTableNameAndField.getDictField(), new String[]{string2})) != null && queryTableDictByKeys.size() > 0) {
                    map.put("sel_search", queryTableDictByKeys.get(0));
                }
            }
        }
    }
}
