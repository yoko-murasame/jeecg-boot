package org.jeecg.common.api;

import org.jeecg.common.system.vo.*;

import java.util.Map;

/**
 * 流程、Online相关API
 */
public interface BpmAPI {

    /**
     * 获取Online列表数据
     *
     * @author Yoko
     * @since 2024/9/20 12:25
     * @param code 表单编码
     * @param queryParam 查询参数，需要分页请传入：pageSize、pageNo
     * @return org.jeecg.common.system.vo.SysOnlListDataModel
     */
    SysOnlListDataModel getDataByCode(String code, Map<String, Object> queryParam);

    /**
     * 获取Online列表数据
     *
     * @author Yoko
     * @since 2024/9/20 12:25
     * @param onlListQueryModel 查询实体
     * @return org.jeecg.common.system.vo.SysOnlListDataModel
     */
    SysOnlListDataModel getData(SysOnlListQueryModel onlListQueryModel);

}
