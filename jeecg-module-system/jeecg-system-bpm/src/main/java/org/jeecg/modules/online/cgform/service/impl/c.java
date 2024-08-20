package org.jeecg.modules.online.cgform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.online.cgform.entity.OnlCgformEnhanceJava;
import org.jeecg.modules.online.cgform.entity.OnlCgformEnhanceSql;
import org.jeecg.modules.online.cgform.mapper.OnlCgformEnhanceJavaMapper;
import org.jeecg.modules.online.cgform.mapper.OnlCgformEnhanceSqlMapper;
import org.jeecg.modules.online.cgform.service.IOnlCgformEnhanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/* compiled from: OnlCgformEnhanceServiceImpl.java */
@Service("onlCgformEnhanceService")
/* loaded from: hibernate-common-ol-5.4.74(2).jar:org/jeecg/modules/online/cgform/service/impl/c.class */
public class c implements IOnlCgformEnhanceService {
    @Autowired
    private OnlCgformEnhanceJavaMapper onlCgformEnhanceJavaMapper;
    @Autowired
    private OnlCgformEnhanceSqlMapper onlCgformEnhanceSqlMapper;

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformEnhanceService
    public List<OnlCgformEnhanceJava> queryEnhanceJavaList(String cgformId) {
        return this.onlCgformEnhanceJavaMapper.selectList( new LambdaQueryWrapper<OnlCgformEnhanceJava>()
                .eq(OnlCgformEnhanceJava::getCgformHeadId, cgformId));
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformEnhanceService
    public void saveEnhanceJava(OnlCgformEnhanceJava onlCgformEnhanceJava) {
        this.onlCgformEnhanceJavaMapper.insert(onlCgformEnhanceJava);
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformEnhanceService
    public void updateEnhanceJava(OnlCgformEnhanceJava onlCgformEnhanceJava) {
        this.onlCgformEnhanceJavaMapper.updateById(onlCgformEnhanceJava);
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformEnhanceService
    public void deleteEnhanceJava(String id) {
        this.onlCgformEnhanceJavaMapper.deleteById(id);
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformEnhanceService
    public void deleteBatchEnhanceJava(List<String> idList) {
        this.onlCgformEnhanceJavaMapper.deleteBatchIds(idList);
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformEnhanceService
    public boolean checkOnlyEnhance(OnlCgformEnhanceJava onlCgformEnhanceJava) {
        LambdaQueryWrapper<OnlCgformEnhanceJava> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(OnlCgformEnhanceJava::getButtonCode, onlCgformEnhanceJava.getButtonCode());
        lambdaQueryWrapper.eq(OnlCgformEnhanceJava::getCgformHeadId, onlCgformEnhanceJava.getCgformHeadId());
        lambdaQueryWrapper.eq(OnlCgformEnhanceJava::getEvent, onlCgformEnhanceJava.getEvent());
        Long selectCount = this.onlCgformEnhanceJavaMapper.selectCount(lambdaQueryWrapper);
        if (selectCount != null) {
            if ((selectCount.intValue() == 1 && oConvertUtils.isEmpty(onlCgformEnhanceJava.getId())) || selectCount.intValue() == 2) {
                return false;
            }
            return true;
        }
        return true;
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformEnhanceService
    public boolean checkOnlyEnhance(OnlCgformEnhanceSql onlCgformEnhanceSql) {
        LambdaQueryWrapper<OnlCgformEnhanceSql> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(OnlCgformEnhanceSql::getButtonCode, onlCgformEnhanceSql.getButtonCode());
        lambdaQueryWrapper.eq(OnlCgformEnhanceSql::getCgformHeadId, onlCgformEnhanceSql.getCgformHeadId());
        Long selectCount = this.onlCgformEnhanceSqlMapper.selectCount(lambdaQueryWrapper);
        if (selectCount != null) {
            if ((selectCount.intValue() == 1 && oConvertUtils.isEmpty(onlCgformEnhanceSql.getId())) || selectCount.intValue() > 1) {
                return false;
            }
            return true;
        }
        return true;
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformEnhanceService
    public List<OnlCgformEnhanceSql> queryEnhanceSqlList(String cgformId) {
        return this.onlCgformEnhanceSqlMapper.selectList( new LambdaQueryWrapper<OnlCgformEnhanceSql>().eq(OnlCgformEnhanceSql::getCgformHeadId, cgformId));
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformEnhanceService
    public void saveEnhanceSql(OnlCgformEnhanceSql onlCgformEnhanceSql) {
        this.onlCgformEnhanceSqlMapper.insert(onlCgformEnhanceSql);
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformEnhanceService
    public void updateEnhanceSql(OnlCgformEnhanceSql onlCgformEnhanceSql) {
        this.onlCgformEnhanceSqlMapper.updateById(onlCgformEnhanceSql);
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformEnhanceService
    public void deleteEnhanceSql(String id) {
        this.onlCgformEnhanceSqlMapper.deleteById(id);
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformEnhanceService
    public void deleteBatchEnhanceSql(List<String> idList) {
        this.onlCgformEnhanceSqlMapper.deleteBatchIds(idList);
    }
}
