package org.jeecg.modules.workflow.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.workflow.entity.EditApply;
import org.jeecg.modules.workflow.service.EditApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 通用编辑权限申请流程
 *
 * @author Yoko
 * @date 2022/5/10 16:39
 */
@Api(tags = "流程通用-编辑权限申请流程")
@RestController
@RequestMapping("/workflow/editapply")
@Slf4j
public class EditApplyController {

    @Autowired
    private EditApplyService editApplyService;

    @ApiOperation(value = "获取和初始化", notes = "获取和初始化")
    @PostMapping(value = "/getAndInit")
    public Result<?> getAndInit(@RequestBody EditApply editApply) {

        EditApply res = editApplyService.getAndInit(editApply);
        return Result.OK("操作成功！", res);
    }

    @ApiOperation(value = "编辑", notes = "编辑")
    @PostMapping(value = "/edit")
    public Result<?> edit(@RequestBody EditApply editApply) {

        editApplyService.edit(editApply);
        return Result.OK("操作成功！");
    }
}
