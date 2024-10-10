package org.jeecg.modules.api.controller;


import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.system.vo.SysOnlListDataModel;
import org.jeecg.common.system.vo.SysOnlListQueryModel;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 服务化 bpm、online模块 对外接口请求类
 * @author: Yoko
 */
@Slf4j
@RestController
@RequestMapping("/sys/bpm")
public class SysBpmApiController {

    @Resource
    private org.jeecg.modules.online.cgform.service.impl.e onlCgformHeadService;

    /**
     * 获取Online列表数据
     *
     * @author Yoko
     * @since 2024/9/20 12:25
     * @param code 表单编码
     * @param queryParam 查询参数，需要分页请传入：pageSize、pageNo
     * @return org.jeecg.common.system.vo.SysOnlListDataModel
     */
    @PostMapping("/getDataByCode")
    SysOnlListDataModel getDataByCode(@RequestParam("code") String code, @RequestBody Map<String, Object> queryParam) {
        return onlCgformHeadService.getDataByCode(code, queryParam);
    }

    /**
     * 获取Online列表数据
     *
     * @author Yoko
     * @since 2024/9/20 12:25
     * @param onlListQueryModel 查询实体
     * @return org.jeecg.common.system.vo.SysOnlListDataModel
     */
    @PostMapping("/getData")
    SysOnlListDataModel getData(@RequestBody SysOnlListQueryModel onlListQueryModel) {
        return onlCgformHeadService.getData(onlListQueryModel);
    }

    /**
     * 新增Online表单数据
     *
     * @author Yoko
     * @param code 表单编码
     * @param formData 表单数据
     * @return java.lang.String 数据库表名
     */
    @PostMapping("/saveManyFormData")
    String saveManyFormData(@RequestParam String code, @RequestBody JSONObject formData) {
        try {
            return onlCgformHeadService.saveManyFormData(code, formData);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 编辑Online表单数据
     *
     * @author Yoko
     * @param code 表单编码
     * @param formData 表单数据
     * @return java.lang.String 数据库表名
     */
    @PostMapping("/editManyFormData")
    String editManyFormData(@RequestParam String code, @RequestBody JSONObject formData){
        try {
            return onlCgformHeadService.editManyFormData(code, formData);
        } catch (Exception e) {
            return "";
        }
    }

}
