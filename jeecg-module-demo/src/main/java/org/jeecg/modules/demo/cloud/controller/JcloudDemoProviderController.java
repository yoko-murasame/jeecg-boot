package org.jeecg.modules.demo.cloud.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.BpmAPI;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.vo.SysOnlListDataModel;
import org.jeecg.modules.demo.cloud.service.JcloudDemoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 服务端提供方——feign接口
 * 【提供给system-start调用测试，看feign是否畅通】
 * @author: jeecg-boot
 */
@Slf4j
@RestController
@RequestMapping("/test")
@Api(tags = "测试服务端提供方——feign接口")
public class JcloudDemoProviderController {

    @Resource
    private JcloudDemoService jcloudDemoService;

    @Resource
    private BpmAPI bpmAPI;

    @GetMapping("/getMessage")
    public String getMessage(@RequestParam String name) {
        String msg = jcloudDemoService.getMessage(name);
        log.info(" 微服务被调用：{} ",msg);
        return msg;
    }

    @ApiOperation(value = "查询Online列表数据", notes = "查询Online列表数据")
    @PostMapping("/sys/bpm/getDataByCode")
    public SysOnlListDataModel getDataByCode(@RequestParam("code") String code, @RequestBody Map<String, Object> queryParam) {
        return bpmAPI.getDataByCode(code, queryParam);
    }

}
