package org.jeecg.modules.online.graphreport.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import org.jeecg.modules.online.graphreport.entity.OnlGraphreportTemplet;
import org.jeecg.modules.online.graphreport.entity.OnlGraphreportTempletItem;
import org.jeecg.modules.online.graphreport.mapper.OnlGraphreportTempletItemMapper;
import org.jeecg.modules.online.graphreport.mapper.OnlGraphreportTempletMapper;
import org.jeecg.modules.online.graphreport.service.IOnlGraphreportTempletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/* compiled from: OnlGraphreportTempletServiceImpl.java */
@Service("onlGraphreportTempletServiceImpl")
/* renamed from: org.jeecg.modules.online.graphreport.service.a.e */
/* loaded from: hibernate-common-ol-5.4.74.jar:org/jeecg/modules/online/graphreport/service/a/e.class */
public class OnlGraphreportTempletServiceImpl extends ServiceImpl<OnlGraphreportTempletMapper, OnlGraphreportTemplet> implements IOnlGraphreportTempletService {
    @Autowired
    private OnlGraphreportTempletMapper onlGraphreportTempletMapper;
    @Autowired
    private OnlGraphreportTempletItemMapper onlGraphreportTempletItemMapper;

    @Override // org.jeecg.modules.online.graphreport.service.IOnlGraphreportTempletService
    @Transactional
    public void saveMain(OnlGraphreportTemplet onlGraphreportTemplet, List<OnlGraphreportTempletItem> onlGraphreportTempletItemList) {
        this.onlGraphreportTempletMapper.insert(onlGraphreportTemplet);
        for (OnlGraphreportTempletItem onlGraphreportTempletItem : onlGraphreportTempletItemList) {
            onlGraphreportTempletItem.setGraphreportTempletId(onlGraphreportTemplet.getId());
            this.onlGraphreportTempletItemMapper.insert(onlGraphreportTempletItem);
        }
    }

    @Override // org.jeecg.modules.online.graphreport.service.IOnlGraphreportTempletService
    @Transactional
    public void updateMain(OnlGraphreportTemplet onlGraphreportTemplet, List<OnlGraphreportTempletItem> onlGraphreportTempletItemList) {
        this.onlGraphreportTempletMapper.updateById(onlGraphreportTemplet);
        this.onlGraphreportTempletItemMapper.deleteByMainId(onlGraphreportTemplet.getId());
        for (OnlGraphreportTempletItem onlGraphreportTempletItem : onlGraphreportTempletItemList) {
            onlGraphreportTempletItem.setGraphreportTempletId(onlGraphreportTemplet.getId());
            this.onlGraphreportTempletItemMapper.insert(onlGraphreportTempletItem);
        }
    }

    @Override // org.jeecg.modules.online.graphreport.service.IOnlGraphreportTempletService
    @Transactional
    public void delMain(String id) {
        this.onlGraphreportTempletMapper.deleteById(id);
        this.onlGraphreportTempletItemMapper.deleteByMainId(id);
    }

    @Override // org.jeecg.modules.online.graphreport.service.IOnlGraphreportTempletService
    @Transactional
    public void delBatchMain(Collection<? extends Serializable> idList) {
        for (Serializable serializable : idList) {
            this.onlGraphreportTempletMapper.deleteById(serializable);
            this.onlGraphreportTempletItemMapper.deleteByMainId(serializable.toString());
        }
    }
}
