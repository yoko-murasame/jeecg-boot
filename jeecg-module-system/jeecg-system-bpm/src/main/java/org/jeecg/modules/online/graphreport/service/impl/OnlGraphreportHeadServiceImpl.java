package org.jeecg.modules.online.graphreport.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.system.vo.DictModel;
import org.jeecg.modules.online.graphreport.entity.OnlGraphreportHead;
import org.jeecg.modules.online.graphreport.entity.OnlGraphreportItem;
import org.jeecg.modules.online.graphreport.mapper.OnlGraphreportHeadMapper;
import org.jeecg.modules.online.graphreport.mapper.OnlGraphreportItemMapper;
import org.jeecg.modules.online.graphreport.service.IOnlGraphreportHeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: OnlGraphreportHeadServiceImpl.java */
@Service("onlGraphreportHeadServiceImpl")
/* renamed from: org.jeecg.modules.online.graphreport.service.a.b */
/* loaded from: hibernate-common-ol-5.4.74.jar:org/jeecg/modules/online/graphreport/service/a/b.class */
public class OnlGraphreportHeadServiceImpl extends ServiceImpl<OnlGraphreportHeadMapper, OnlGraphreportHead> implements IOnlGraphreportHeadService {
    @Autowired
    private OnlGraphreportHeadMapper onlGraphreportHeadMapper;
    @Autowired
    private OnlGraphreportItemMapper onlGraphreportItemMapper;
    @Autowired
    private ISysBaseAPI sysBaseAPI;

    @Override // org.jeecg.modules.online.graphreport.service.IOnlGraphreportHeadService
    public List<Map<String, Object>> executeSelete(String sql) {
        return this.onlGraphreportHeadMapper.executeSelete(sql);
    }

    @Override // org.jeecg.modules.online.graphreport.service.IOnlGraphreportHeadService
    @Transactional(rollbackFor = {Exception.class})
    public void saveMain(OnlGraphreportHead onlGraphreportHead, List<OnlGraphreportItem> onlGraphreportItemList) {
        if (onlGraphreportHead.getYaxisText() == null) {
            onlGraphreportHead.setYaxisText("yaxis_text");
        }
        this.onlGraphreportHeadMapper.insert(onlGraphreportHead);
        for (OnlGraphreportItem onlGraphreportItem : onlGraphreportItemList) {
            onlGraphreportItem.setGraphreportHeadId(onlGraphreportHead.getId());
            this.onlGraphreportItemMapper.insert(onlGraphreportItem);
        }
    }

    @Override // org.jeecg.modules.online.graphreport.service.IOnlGraphreportHeadService
    @Transactional(rollbackFor = {Exception.class})
    public void updateMain(OnlGraphreportHead onlGraphreportHead, List<OnlGraphreportItem> onlGraphreportItemList) {
        this.onlGraphreportHeadMapper.updateById(onlGraphreportHead);
        this.onlGraphreportItemMapper.deleteByMainId(onlGraphreportHead.getId());
        for (OnlGraphreportItem onlGraphreportItem : onlGraphreportItemList) {
            onlGraphreportItem.setGraphreportHeadId(onlGraphreportHead.getId());
            this.onlGraphreportItemMapper.insert(onlGraphreportItem);
        }
    }

    @Override // org.jeecg.modules.online.graphreport.service.IOnlGraphreportHeadService
    @Transactional(rollbackFor = {Exception.class})
    public void delMain(String id) {
        this.onlGraphreportHeadMapper.deleteById(id);
        this.onlGraphreportItemMapper.deleteByMainId(id);
    }

    @Override // org.jeecg.modules.online.graphreport.service.IOnlGraphreportHeadService
    @Transactional(rollbackFor = {Exception.class})
    public void delBatchMain(Collection<? extends Serializable> idList) {
        for (Serializable serializable : idList) {
            this.onlGraphreportHeadMapper.deleteById(serializable);
            this.onlGraphreportItemMapper.deleteByMainId(serializable.toString());
        }
    }

    @Override // org.jeecg.modules.online.graphreport.service.IOnlGraphreportHeadService
    public OnlGraphreportHead queryByCode(String code) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("code", code);
        return (OnlGraphreportHead) getOne(queryWrapper);
    }

    @Override // org.jeecg.modules.online.graphreport.service.IOnlGraphreportHeadService
    public Map<String, Object> queryChartConfig(OnlGraphreportHead head) {
        HashMap hashMap = new HashMap();
        hashMap.put("head", head);
        if (head.getYaxisField() != null) {
            head.setYaxisField(head.getYaxisField().toLowerCase());
        }
        if (head.getXaxisField() != null) {
            head.setXaxisField(head.getXaxisField().toLowerCase());
        }
        List<OnlGraphreportItem> selectByMainId = this.onlGraphreportItemMapper.selectByMainId(head.getId());
        for (OnlGraphreportItem onlGraphreportItem : selectByMainId) {
            if (onlGraphreportItem.getFieldName() != null) {
                onlGraphreportItem.setFieldName(onlGraphreportItem.getFieldName().toLowerCase());
            }
        }
        HashMap hashMap2 = new HashMap(0);
        for (OnlGraphreportItem onlGraphreportItem2 : selectByMainId) {
            String dictCode = onlGraphreportItem2.getDictCode();
            if (!StringUtils.isEmpty(dictCode)) {
                List list = null;
                if (dictCode.toLowerCase().trim().indexOf("select ") == 0) {
                    List<Map<String, Object>> executeSelete = executeSelete(dictCode);
                    if (!(executeSelete == null || executeSelete.size() == 0)) {
                        list = JSON.parseArray(JSON.toJSONString(executeSelete), DictModel.class);
                    }
                } else {
                    list = this.sysBaseAPI.queryDictItemsByCode(dictCode);
                }
                hashMap2.put(onlGraphreportItem2.getFieldName(), list);
            }
        }
        hashMap.put("items", selectByMainId);
        hashMap.put("dictOptions", hashMap2);
        return hashMap;
    }
}
