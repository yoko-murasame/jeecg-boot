package org.jeecg.modules.technical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.technical.entity.FolderUserPermission;
import org.jeecg.modules.technical.vo.FolderUserPermissionRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FolderUserPermissionService extends IService<FolderUserPermission> {


    @Transactional(rollbackFor = Exception.class)
    void savePersonalPermission(List<String> folderIds, String username);

    @Transactional(rollbackFor = Exception.class)
    void removePermission(List<String> folderIds);

    void savePermission(FolderUserPermissionRequest params);

    List<FolderUserPermission> queryPermission(FolderUserPermissionRequest params);
}
