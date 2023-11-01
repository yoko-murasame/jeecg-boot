package org.jeecg.modules.technical.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.technical.entity.FolderUserPermission;
import org.jeecg.modules.technical.entity.enums.PermissionType;
import org.jeecg.modules.technical.mapper.FolderUserPermissionMapper;
import org.jeecg.modules.technical.service.FolderUserPermissionService;
import org.jeecg.modules.technical.vo.FolderUserPermissionRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FolderUserPermissionServiceImpl extends ServiceImpl<FolderUserPermissionMapper, FolderUserPermission> implements FolderUserPermissionService {

    @Resource
    private FolderUserPermissionMapper permissionMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void savePermission(FolderUserPermissionRequest params) {
        String folderId = params.getFolderId();
        Assert.hasText(folderId, "目录id不能为空");
        List<String> usernames = params.getUsernames();
        List<String> superUsernames = params.getSuperUsernames();
        // 保存普通用户目录权限
        if (usernames != null && !usernames.isEmpty()) {
            // 删除存在的数据
            LambdaQueryWrapper<FolderUserPermission> wp = Wrappers.lambdaQuery(FolderUserPermission.class)
                    .eq(FolderUserPermission::getFolderId, folderId)
                    .in(FolderUserPermission::getUsername, usernames);
            this.remove(wp);
            // 保存新的数据
            List<FolderUserPermission> newPermission = usernames.stream().map(username -> {
                FolderUserPermission permission = new FolderUserPermission();
                permission.setFolderId(folderId);
                permission.setUsername(username);
                permission.setDataPermissionType(PermissionType.PERSONAL);
                return permission;
            }).collect(Collectors.toList());
            this.saveBatch(newPermission);
        }
        // 保存超级用户目录权限
        if (superUsernames != null && !superUsernames.isEmpty()) {
            // 删除存在的数据
            LambdaQueryWrapper<FolderUserPermission> wp = Wrappers.lambdaQuery(FolderUserPermission.class)
                    .eq(FolderUserPermission::getFolderId, folderId)
                    .in(FolderUserPermission::getUsername, superUsernames);
            this.remove(wp);
            // 保存新的数据
            List<FolderUserPermission> newPermission = superUsernames.stream().map(username -> {
                FolderUserPermission permission = new FolderUserPermission();
                permission.setFolderId(folderId);
                permission.setUsername(username);
                permission.setDataPermissionType(PermissionType.FULL);
                return permission;
            }).collect(Collectors.toList());
            this.saveBatch(newPermission);
        }
    }

    @Override
    public List<FolderUserPermission> queryPermission(FolderUserPermissionRequest params) {
        String folderId = params.getFolderId();
        LambdaQueryWrapper<FolderUserPermission> wp = Wrappers.lambdaQuery(FolderUserPermission.class);
        if (StringUtils.hasText(folderId)) {
            wp.in(FolderUserPermission::getFolderId, Arrays.asList(folderId.split(",")));
        }
        List<String> usernames = params.getUsernames();
        if (usernames != null && !usernames.isEmpty()) {
            wp.in(FolderUserPermission::getUsername, usernames);
        }
        List<String> superUsernames = params.getSuperUsernames();
        if (superUsernames != null && !superUsernames.isEmpty()) {
            wp.in(FolderUserPermission::getUsername, superUsernames);
        }
        return this.list(wp);
    }

}
