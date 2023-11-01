package org.jeecg.modules.technical.controller;

import io.swagger.annotations.ApiOperation;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.technical.entity.FolderUserPermission;
import org.jeecg.modules.technical.service.FolderUserPermissionService;
import org.jeecg.modules.technical.vo.FolderUserPermissionRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 目录-用户权限表(technical_folder_user_permission)表控制层
 *
 */
@RestController
@RequestMapping("/technical/folder/user/permission")
public class FolderUserPermissionController {

    @Resource
    private FolderUserPermissionService permissionService;


    @ApiOperation("保存权限数据")
    @RequestMapping(value = "savePermission", method = {RequestMethod.POST})
    public Result<?> savePermission(@RequestBody FolderUserPermissionRequest params) {
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
