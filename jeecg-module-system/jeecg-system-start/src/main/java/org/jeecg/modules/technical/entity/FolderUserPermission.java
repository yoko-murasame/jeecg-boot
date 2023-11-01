package org.jeecg.modules.technical.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.jeecg.modules.technical.entity.enums.PermissionType;

/**
 * 目录-用户权限表
 */
@Data
@TableName(value = "technical_folder_user_permission")
public class FolderUserPermission {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 目录id
     */
    @TableField(value = "folder_id")
    private String folderId;

    /**
     * 用户名
     */
    @TableField(value = "username")
    private String username;

    /**
     * 数据权限类型
     */
    @TableField(value = "data_permission_type")
    private PermissionType dataPermissionType;
}
