package org.jeecg.modules.online.cgform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;
import org.jeecg.modules.online.cgform.entity.OnlCgformHead;
import org.jeecg.modules.online.cgform.mapper.OnlCgformFieldMapper;
import org.jeecg.modules.online.cgform.mapper.OnlCgformHeadMapper;
import org.jeecg.modules.online.cgform.service.IOnlineBaseAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

/* compiled from: OnlineBaseAPIServiceImpl.java */
@Service("onlineBaseAPI")
/* loaded from: hibernate-common-ol-5.4.74(2).jar:org/jeecg/modules/online/cgform/service/impl/h.class */
public class h implements IOnlineBaseAPI {
    @Autowired
    private OnlCgformHeadMapper onlCgformHeadMapper;
    @Autowired
    private OnlCgformFieldMapper onlCgformFieldMapper;

    @Override // org.jeecg.modules.online.cgform.service.IOnlineBaseAPI
    public String getOnlineErpCode(String code, String tableType) {
        if ("3".equals(tableType)) {
            String substring = code.substring(1);
            OnlCgformHead onlCgformHead = (OnlCgformHead) this.onlCgformHeadMapper.selectById(substring);
            if (onlCgformHead != null && onlCgformHead.getTableType().intValue() == 3) {
                List selectList = this.onlCgformFieldMapper.selectList(new LambdaQueryWrapper<OnlCgformField>()
                        .eq(OnlCgformField::getCgformHeadId, substring));
                if (selectList != null && selectList.size() > 0) {
                    String str = null;
                    Iterator it = selectList.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        OnlCgformField onlCgformField = (OnlCgformField) it.next();
                        if (oConvertUtils.isNotEmpty(onlCgformField.getMainTable())) {
                            str = onlCgformField.getMainTable();
                            break;
                        }
                    }
                    OnlCgformHead onlCgformHead2 = (OnlCgformHead) this.onlCgformHeadMapper.selectOne( new LambdaQueryWrapper<OnlCgformHead>()
                            .eq(OnlCgformHead::getTableName, str));
                    String themeTemplate = onlCgformHead2.getThemeTemplate();
                    if (onlCgformHead2 != null && ("innerTable".equals(themeTemplate) || org.jeecg.modules.online.cgform.d.b.ERP.equals(themeTemplate))) {
                        code = "/" + onlCgformHead2.getId();
                    }
                }
            }
        }
        return code;
    }
}
