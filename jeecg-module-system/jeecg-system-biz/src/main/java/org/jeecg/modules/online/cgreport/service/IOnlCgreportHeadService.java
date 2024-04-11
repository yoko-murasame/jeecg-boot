package org.jeecg.modules.online.cgreport.service;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.extension.service.IService;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.vo.DictModel;
import org.jeecg.modules.online.cgreport.entity.OnlCgreportHead;
import org.jeecg.modules.online.cgreport.model.OnlCgreportModel;

/* loaded from: hibernate-common-ol-5.4.74(2).jar:org/jeecg/modules/online/cgreport/service/IOnlCgreportHeadService.class */
public interface IOnlCgreportHeadService extends IService<OnlCgreportHead> {
    Result<?> editAll(OnlCgreportModel onlCgreportModel);

    Result<?> delete(String str);

    Result<?> bathDelete(String[] strArr);

    Map<String, Object> executeSelectSql(String str, String str2, Map<String, Object> map) throws SQLException;

    Map<String, Object> executeSelectSqlDynamic(String str, String str2, Map<String, Object> map, String str3);

    List<String> getSqlFields(String str, String str2) throws SQLException;

    List<String> getSqlParams(String str);

    Map<String, Object> queryCgReportConfig(String str);

    List<Map<?, ?>> queryByCgReportSql(String str, Map map, Map map2, int i, int i2);

    List<DictModel> queryDictSelectData(String str, String str2);

    Map<String, Object> queryColumnInfo(String str, boolean z);

    List<DictModel> queryColumnDict(String str, JSONArray jSONArray, String str2);
}