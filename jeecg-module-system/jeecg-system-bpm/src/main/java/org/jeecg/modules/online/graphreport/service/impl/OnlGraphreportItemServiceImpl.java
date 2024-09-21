package org.jeecg.modules.online.graphreport.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.online.graphreport.entity.OnlGraphreportItem;
import org.jeecg.modules.online.graphreport.mapper.OnlGraphreportItemMapper;
import org.jeecg.modules.online.graphreport.service.IOnlGraphreportItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/* compiled from: OnlGraphreportItemServiceImpl.java */
@Service("onlGraphreportItemServiceImpl")
/* renamed from: org.jeecg.modules.online.graphreport.service.a.c */
/* loaded from: hibernate-common-ol-5.4.74.jar:org/jeecg/modules/online/graphreport/service/a/c.class */
public class OnlGraphreportItemServiceImpl extends ServiceImpl<OnlGraphreportItemMapper, OnlGraphreportItem> implements IOnlGraphreportItemService {
    @Autowired
    private OnlGraphreportItemMapper onlGraphreportItemMapper;

    @Override // org.jeecg.modules.online.graphreport.service.IOnlGraphreportItemService
    public List<OnlGraphreportItem> selectByMainId(String mainId) {
        return this.onlGraphreportItemMapper.selectByMainId(mainId);
    }
}
