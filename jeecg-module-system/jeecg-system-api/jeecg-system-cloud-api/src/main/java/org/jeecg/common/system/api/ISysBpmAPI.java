package org.jeecg.common.system.api;

import com.alibaba.fastjson.JSONObject;
import org.jeecg.common.api.BpmAPI;
import org.jeecg.common.constant.ServiceNameConstants;
import org.jeecg.common.system.api.fallback.SysBpmAPIFallback;
import org.jeecg.common.system.vo.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 流程、Online相关API
 */
@Component
@FeignClient(contextId = "sysBpmRemoteApi", value = ServiceNameConstants.SERVICE_SYSTEM, fallbackFactory = SysBpmAPIFallback.class)
@ConditionalOnMissingClass("org.jeecg.modules.online.cgform.service.impl.e")
public interface ISysBpmAPI extends BpmAPI {

    /**
     * 获取Online列表数据
     *
     * @author Yoko
     * @since 2024/9/20 12:25
     * @param code 表单编码
     * @param queryParam 查询参数，需要分页请传入：pageSize、pageNo
     * @return org.jeecg.common.system.vo.SysOnlListDataModel
     */
    @PostMapping("/sys/bpm/getDataByCode")
    SysOnlListDataModel getDataByCode(@RequestParam("code") String code, @RequestBody Map<String, Object> queryParam);

    /**
     * 获取Online列表数据
     *
     * @author Yoko
     * @since 2024/9/20 12:25
     * @param onlListQueryModel 查询实体
     * @return org.jeecg.common.system.vo.SysOnlListDataModel
     */
    @PostMapping("/sys/bpm/getData")
    SysOnlListDataModel getData(@RequestBody SysOnlListQueryModel onlListQueryModel);

    /**
     * 新增Online表单数据
     *
     * @author Yoko
     * @param code 表单编码
     * @param formData 表单数据
     * @return java.lang.String 数据库表名
     */
    @PostMapping("/sys/bpm/saveManyFormData")
    String saveManyFormData(@RequestParam String code, @RequestBody JSONObject formData) throws Exception;

    /**
     * 编辑Online表单数据
     *
     * @author Yoko
     * @param code 表单编码
     * @param formData 表单数据
     * @return java.lang.String 数据库表名
     */
    @PostMapping("/sys/bpm/editManyFormData")
    String editManyFormData(@RequestParam String code, @RequestBody JSONObject formData) throws Exception;

}
