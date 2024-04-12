package org.jeecg.modules.technical.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.modules.technical.entity.FolderUserPermission;
import org.jeecg.modules.technical.service.FolderUserPermissionService;
import org.jeecg.modules.technical.vo.FolderUserPermissionRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * 目录-用户权限表(technical_folder_user_permission)表控制层
 *
 */
@Api(tags = "新版知识库")
@RestController
@RequestMapping("/technical/folder/user/permission")
public class FolderUserPermissionController {

    @Resource
    private FolderUserPermissionService permissionService;

    @AutoLog(value = "目录-用户权限表-保存个人目录权限")
    @ApiOperation("保存权限数据")
    @RequestMapping(value = "savePermission", method = {RequestMethod.POST})
    public Result<?> savePermission(@RequestBody FolderUserPermissionRequest params) {
        permissionService.removePermission(Collections.singletonList(params.getFolderId()));
        permissionService.savePermission(params);
        return Result.OK();
    }

    @ApiOperation("查询权限列表")
    @RequestMapping(value = "queryPermission", method = {RequestMethod.POST})
    public Result<?> queryPermission(@RequestBody FolderUserPermissionRequest params) {
        List<FolderUserPermission> res = permissionService.queryPermission(params);
        return Result.OK(res);
    }

}
