package org.jeecg.modules.online.cgform.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;
import org.jeecg.modules.online.cgform.entity.OnlCgformHead;
import org.jeecg.modules.online.cgform.mapper.OnlCgformFieldMapper;
import org.jeecg.modules.online.cgform.service.IOnlCgformHeadService;
import org.jeecg.modules.online.cgform.service.IOnlCgformSqlService;
import org.jeecg.modules.online.config.exception.BusinessException;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/* compiled from: OnlCgformSqlServiceImpl.java */
@Service("onlCgformSqlServiceImpl")
/* loaded from: hibernate-common-ol-5.4.74(2).jar:org/jeecg/modules/online/cgform/service/impl/g.class */
public class g implements IOnlCgformSqlService {
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;
    @Autowired
    private IOnlCgformHeadService onlCgformHeadService;

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformSqlService
    public void saveBatchOnlineTable(OnlCgformHead head, List<OnlCgformField> fieldList, List<Map<String, Object>> dataList) throws BusinessException {
        SqlSession sqlSession = null;
        try {
            try {
                org.jeecg.modules.online.cgform.converter.b.a(2, dataList, fieldList);
                sqlSession = this.sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
                OnlCgformFieldMapper onlCgformFieldMapper = (OnlCgformFieldMapper) sqlSession.getMapper(OnlCgformFieldMapper.class);
                if (1000 >= dataList.size()) {
                    for (int i = 0; i < dataList.size(); i++) {
                        a(JSON.toJSONString(dataList.get(i)), head, fieldList, onlCgformFieldMapper);
                    }
                } else {
                    for (int i2 = 0; i2 < dataList.size(); i2++) {
                        a(JSON.toJSONString(dataList.get(i2)), head, fieldList, onlCgformFieldMapper);
                        if (i2 % 1000 == 0) {
                            sqlSession.commit();
                            sqlSession.clearCache();
                        }
                    }
                }
                sqlSession.commit();
                sqlSession.close();
            } catch (Exception e) {
                throw new BusinessException(e.getMessage());
            }
        } catch (Throwable th) {
            sqlSession.close();
            throw th;
        }
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformSqlService
    public void saveOrUpdateSubData(String subDataJsonStr, OnlCgformHead head, List<OnlCgformField> subFiledList) throws BusinessException {
        a(subDataJsonStr, head, subFiledList, (OnlCgformFieldMapper) SpringContextUtils.getBean(OnlCgformFieldMapper.class));
    }

    private void a(String str, OnlCgformHead onlCgformHead, List<OnlCgformField> list, OnlCgformFieldMapper onlCgformFieldMapper) throws BusinessException {
        JSONObject parseObject = JSONObject.parseObject(str);
        int executeEnhanceJava = this.onlCgformHeadService.executeEnhanceJava(org.jeecg.modules.online.cgform.d.b.af, org.jeecg.modules.online.cgform.d.b.al, onlCgformHead, parseObject);
        String tableName = onlCgformHead.getTableName();
        if (1 == executeEnhanceJava) {
            onlCgformFieldMapper.executeInsertSQL(org.jeecg.modules.online.cgform.d.b.a(tableName, list, parseObject));
        } else if (2 == executeEnhanceJava) {
            onlCgformFieldMapper.executeUpdatetSQL(org.jeecg.modules.online.cgform.d.b.b(tableName, list, parseObject));
        } else {
            if (0 == executeEnhanceJava) {
            }
        }
    }
}