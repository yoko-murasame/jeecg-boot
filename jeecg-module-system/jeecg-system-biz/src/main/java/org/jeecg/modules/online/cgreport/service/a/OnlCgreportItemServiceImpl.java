package org.jeecg.modules.online.cgreport.service.a;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.online.cgreport.entity.OnlCgreportItem;
import org.jeecg.modules.online.cgreport.mapper.OnlCgreportItemMapper;
import org.jeecg.modules.online.cgreport.service.IOnlCgreportItemService;
import org.jeecg.modules.online.cgreport.util.SqlUtil;
import org.springframework.stereotype.Service;

/* compiled from: OnlCgreportItemServiceImpl.java */
@Service("onlCgreportItemServiceImpl")
/* loaded from: hibernate-common-ol-5.4.74(2).jar:org/jeecg/modules/online/cgreport/service/a/c.class */
public class OnlCgreportItemServiceImpl extends ServiceImpl<OnlCgreportItemMapper, OnlCgreportItem> implements IOnlCgreportItemService {

    @Override // org.jeecg.modules.online.cgreport.service.IOnlCgreportItemService
    public List<Map<String, String>> getAutoListQueryInfo(String cgrheadId) {
        LambdaQueryWrapper<OnlCgreportItem> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(OnlCgreportItem::getCgrheadId, cgrheadId);
        lambdaQueryWrapper.eq(OnlCgreportItem::getIsSearch, 1);
        List<OnlCgreportItem> list = list(lambdaQueryWrapper);
        ArrayList arrayList = new ArrayList();
        int i = 0;
        for (OnlCgreportItem onlCgreportItem : list) {
            HashMap hashMap = new HashMap();
            hashMap.put("label", onlCgreportItem.getFieldTxt());
            String dictCode = onlCgreportItem.getDictCode();
            if (oConvertUtils.isNotEmpty(dictCode)) {
                if (SqlUtil.c(dictCode)) {
                    hashMap.put("view", "search");
                    hashMap.put("sql", dictCode);
                } else {
                    hashMap.put("view", org.jeecg.modules.online.cgform.d.b.aj);
                }
            } else {
                hashMap.put("view", onlCgreportItem.getFieldType().toLowerCase());
            }
            hashMap.put("mode", oConvertUtils.isEmpty(onlCgreportItem.getSearchMode()) ? org.jeecg.modules.online.cgform.d.b.single : onlCgreportItem.getSearchMode());
            hashMap.put("field", onlCgreportItem.getFieldName());
            i++;
            if (i > 2) {
                hashMap.put("hidden", "1");
            }
            arrayList.add(hashMap);
        }
        return arrayList;
    }
}