package org.jeecg.modules.technical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.technical.entity.FolderUserPermission;
import org.jeecg.modules.technical.vo.FolderUserPermissionRequest;

import java.util.List;

public interface FolderUserPermissionService extends IService<FolderUserPermission> {


    void savePermission(FolderUserPermissionRequest params);

    List<FolderUserPermission> queryPermission(FolderUserPermissionRequest params);
}
