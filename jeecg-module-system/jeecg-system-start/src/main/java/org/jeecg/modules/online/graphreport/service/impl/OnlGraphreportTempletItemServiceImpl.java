package org.jeecg.modules.online.graphreport.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.online.graphreport.entity.OnlGraphreportTempletItem;
import org.jeecg.modules.online.graphreport.mapper.OnlGraphreportTempletItemMapper;
import org.jeecg.modules.online.graphreport.service.IOnlGraphreportTempletItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/* compiled from: OnlGraphreportTempletItemServiceImpl.java */
@Service("onlGraphreportTempletItemServiceImpl")
/* renamed from: org.jeecg.modules.online.graphreport.service.a.d */
/* loaded from: hibernate-common-ol-5.4.74.jar:org/jeecg/modules/online/graphreport/service/a/d.class */
public class OnlGraphreportTempletItemServiceImpl extends ServiceImpl<OnlGraphreportTempletItemMapper, OnlGraphreportTempletItem> implements IOnlGraphreportTempletItemService {
    @Autowired
    private OnlGraphreportTempletItemMapper onlGraphreportTempletItemMapper;

    @Override // org.jeecg.modules.online.graphreport.service.IOnlGraphreportTempletItemService
    public List<OnlGraphreportTempletItem> selectByMainId(String mainId) {
        QueryWrapper<OnlGraphreportTempletItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("graphreport_templet_id", mainId);
        queryWrapper.orderByAsc("group_num");
        queryWrapper.orderByAsc("order_num");
        return this.onlGraphreportTempletItemMapper.selectList(queryWrapper);
    }
}
