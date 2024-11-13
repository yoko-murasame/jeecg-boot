package org.jeecg.modules.online.cgform.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.modules.online.cgform.entity.OnlCgformIndex;
import org.jeecg.modules.online.cgform.mapper.OnlCgformHeadMapper;
import org.jeecg.modules.online.cgform.mapper.OnlCgformIndexMapper;
import org.jeecg.modules.online.cgform.service.IOnlCgformIndexService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/* compiled from: OnlCgformIndexServiceImpl.java */
@Service("onlCgformIndexServiceImpl")
/* loaded from: hibernate-common-ol-5.4.74(2).jar:org/jeecg/modules/online/cgform/service/impl/f.class */
public class f extends ServiceImpl<OnlCgformIndexMapper, OnlCgformIndex> implements IOnlCgformIndexService {
    private static final Logger a = LoggerFactory.getLogger(f.class);
    @Autowired
    private OnlCgformHeadMapper onlCgformHeadMapper;

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformIndexService
    public void createIndex(String code, String databaseType, String tbname) {
        String str="";
        LambdaQueryWrapper<OnlCgformIndex> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(OnlCgformIndex::getCgformHeadId, code);
        List<OnlCgformIndex> list = list(lambdaQueryWrapper);
        if (list != null && list.size() > 0) {
            for (OnlCgformIndex onlCgformIndex : list) {
                if (!CommonConstant.DEL_FLAG_1.equals(onlCgformIndex.getDelFlag()) && "N".equals(onlCgformIndex.getIsDbSynch())) {
                    String indexName = onlCgformIndex.getIndexName();
                    String indexField = onlCgformIndex.getIndexField();
                    String str2 = org.jeecg.modules.online.cgform.d.b.NORMAL.equals(onlCgformIndex.getIndexType()) ? " index " : onlCgformIndex.getIndexType() + " index ";
                    boolean z = true;
                    switch (databaseType.hashCode()) {
                        case -1955532418:
                            if (databaseType.equals("ORACLE")) {
                                z = true;
                                break;
                            }else {

                                str = "create " + str2 + indexName + " on " + tbname + "(" + indexField + ")";
                            }
                            break;
                        case -1620389036:
                            if (databaseType.equals("POSTGRESQL")) {
                                z = true;
                                str = "create " + str2 + indexName + " on " + tbname + "(" + indexField + ")";
                                break;
                            }
                            break;
                        case 73844866:
                            if (databaseType.equals("MYSQL")) {
                                z = false;
                                break;
                            }else {

                                str = "create " + str2 + indexName + " on " + tbname + "(" + indexField + ")";
                            }
                            break;
                        case 912124529:
                            if (databaseType.equals("SQLSERVER")) {
                                z = true;

                                str = "create " + str2 + indexName + " on " + tbname + "(" + indexField + ")";
                                break;
                            }
                            break;
                        default:
                            str = "create " + str2 + indexName + " on " + tbname + "(" + indexField + ")";
                            break;
                    }
                    String str3 = str;
                    a.info(" 创建索引 executeDDL ：" + str3);
                    this.onlCgformHeadMapper.executeDDL(str3);
                    onlCgformIndex.setIsDbSynch("Y");
                    updateById(onlCgformIndex);
                }
            }
        }
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformIndexService
    public boolean isExistIndex(String countSql) {
        if (countSql == null) {
            return true;
        }
        Integer valueOf = Integer.valueOf(((OnlCgformIndexMapper) this.baseMapper).queryIndexCount(countSql));
        if (valueOf != null && valueOf.intValue() > 0) {
            return true;
        }
        return false;
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformIndexService
    public List<OnlCgformIndex> getCgformIndexsByCgformId(String cgformId) {
        return ((OnlCgformIndexMapper) this.baseMapper).selectList((Wrapper) new LambdaQueryWrapper<OnlCgformIndex>().in(OnlCgformIndex::getCgformHeadId, new Object[]{cgformId}));
    }
}
