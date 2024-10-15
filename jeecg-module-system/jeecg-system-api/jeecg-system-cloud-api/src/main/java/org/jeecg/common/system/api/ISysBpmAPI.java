package org.jeecg.common.system.api;

import com.alibaba.fastjson.JSONObject;
import org.jeecg.common.api.BpmAPI;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.constant.ServiceNameConstants;
import org.jeecg.common.system.api.fallback.SysBpmAPIFallback;
import org.jeecg.common.system.vo.SysOnlListDataModel;
import org.jeecg.common.system.vo.SysOnlListQueryModel;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

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
    @PostMapping("/sys/bpm/getDataByCode")
    SysOnlListDataModel getDataByCode(@RequestParam("code") String code, @RequestBody Map<String, Object> queryParam, @RequestHeader(CommonConstant.X_ACCESS_TOKEN) String token);

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
    @PostMapping("/sys/bpm/getData")
    SysOnlListDataModel getData(@RequestBody SysOnlListQueryModel onlListQueryModel, @RequestHeader(CommonConstant.X_ACCESS_TOKEN) String token);

    /**
     * 新增Online表单数据
     *
     * @author Yoko
     * @param code 表单编码
     * @param javaBean JavaBean实体
     * @return java.lang.String 数据库表名
     */
    @PostMapping("/sys/bpm/saveManyFormDataByJavaBean")
    String saveManyFormDataByJavaBean(@RequestParam String code, @RequestBody Object javaBean) throws Exception;
    @PostMapping("/sys/bpm/saveManyFormDataByJavaBean")
    String saveManyFormDataByJavaBean(@RequestParam String code, @RequestBody Object javaBean, @RequestHeader(CommonConstant.X_ACCESS_TOKEN) String token) throws Exception;

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
    @PostMapping("/sys/bpm/saveManyFormData")
    String saveManyFormData(@RequestParam String code, @RequestBody JSONObject formData, @RequestHeader(CommonConstant.X_ACCESS_TOKEN) String token) throws Exception;

    /**
     * 编辑Online表单数据
     *
     * @author Yoko
     * @param code 表单编码
     * @param javaBean JavaBean实体
     * @return java.lang.String 数据库表名
     */
    @PostMapping("/sys/bpm/editManyFormDataByJavaBean")
    String editManyFormDataByJavaBean(@RequestParam String code, @RequestBody Object javaBean) throws Exception;
    @PostMapping("/sys/bpm/editManyFormDataByJavaBean")
    String editManyFormDataByJavaBean(@RequestParam String code, @RequestBody Object javaBean, @RequestHeader(CommonConstant.X_ACCESS_TOKEN) String token) throws Exception;

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
    @PostMapping("/sys/bpm/editManyFormData")
    String editManyFormData(@RequestParam String code, @RequestBody JSONObject formData, @RequestHeader(CommonConstant.X_ACCESS_TOKEN) String token) throws Exception;

}
