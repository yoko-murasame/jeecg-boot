package org.jeecg.modules.system.controller;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统ID生成接口
 */
@Api(tags = "系统ID生成接口")
@Slf4j
@RestController
@RequestMapping("/sys/id")
public class IdController {

    @ApiOperation(value = "获取新的雪花ID", notes = "获取新的雪花ID")
    @GetMapping(value = "/snowId")
    public Result<String> snowId() {
        return Result.OK(IdWorker.getIdStr());
    }

}
