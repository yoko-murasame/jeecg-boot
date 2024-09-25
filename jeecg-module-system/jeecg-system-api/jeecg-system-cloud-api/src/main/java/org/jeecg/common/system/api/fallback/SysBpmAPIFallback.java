package org.jeecg.common.system.api.fallback;

import com.alibaba.fastjson.JSONObject;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.system.api.ISysBpmAPI;
import org.jeecg.common.system.vo.*;

import java.util.Map;

/**
 * 进入fallback的方法 检查是否token未设置
 */
@Slf4j
public class SysBpmAPIFallback implements ISysBpmAPI {

    @Setter
    private Throwable cause;

    @Override
    public SysOnlListDataModel getDataByCode(String code, Map<String, Object> queryParam) {
        log.error("Online列表数据查询失败 {}", cause);
        return null;
    }

    @Override
    public SysOnlListDataModel getData(SysOnlListQueryModel onlListQueryModel) {
        log.error("Online列表数据查询失败 {}", cause);
        return null;
    }

    @Override
    public String saveManyFormData(String code, JSONObject formData) throws Exception {
        log.error("Online表单数据新增失败 {}", cause);
        return "";
    }

    @Override
    public String editManyFormData(String code, JSONObject formData) throws Exception {
        log.error("Online表单数据编辑失败 {}", cause);
        return "";
    }
}
