package org.jeecg.modules.technical.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecg.common.system.base.entity.JeecgEntity;
import org.jeecg.modules.technical.entity.enums.Enabled;
import org.jeecg.modules.technical.entity.enums.Level;
import org.jeecg.modules.technical.entity.enums.PermissionType;
import org.jeecg.modules.technical.entity.enums.Type;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("technical_folder")
public class Folder extends JeecgEntity {

    @ApiModelProperty(value = "id")
    private String id;
    private String name;
    private Level level;
    @TableField(value = "folder_order")// oder是数据库关键字，需要改
    private Integer folderOrder;
    @TableField(value = "parent_id")
    private String parentId;
    @TableField(value = "project_id")
    private String projectId;
    @TableField(value = "project_name")
    private String projectName;
    @TableField(value = "business_id")
    private String businessId;
    @TableField(value = "business_name")
    private String businessName;
    @TableField(value = "child_folder_size")
    private Integer childFolderSize;
    @TableField(value = "child_file_size")
    private Integer childFileSize;
    @TableField(value = "tags")
    private String tags;
    private Type type;
    private Enabled enabled = Enabled.ENABLED;

    @TableField(exist = false)
    private String username;
    @TableField(exist = false)
    private PermissionType dataPermissionType;

    @TableField(exist = false)
    private String folderTreeNames;

    // 是否初始化文件夹树名称
    @TableField(exist = false)
    private Boolean initialFolderTreeNamesIfNotExist = false;

    public static Folder of(String id, String projectId, String projectName, String businessId, String businessName) {
        return new Folder().setId(id).setProjectId(projectId).setProjectName(projectName).setBusinessId(businessId).setBusinessName(businessName);
    }

    public static Folder of(String id, String projectId, String businessId) {
        return new Folder().setId(id).setProjectId(projectId).setBusinessId(businessId);
    }

    public static Folder of(String projectId, String businessId) {
        return new Folder().setProjectId(projectId).setBusinessId(businessId);
    }

    public static Folder of(String id) {
        return new Folder().setId(id);
    }

    public static Folder of() {
        return new Folder();
    }

    public Folder ofName(String name) {
        return this.setName(name);
    }

    public Folder ofType(Type type) {
        return this.setType(type);
    }

    public Folder ofLevel(Level level) {
        return this.setLevel(level);
    }

    public Folder ofProject(String projectId, String projectName) {
        return this.setProjectId(projectId).setProjectName(projectName);
    }

    public Folder ofBusiness(String businessId, String businessName) {
        return this.setBusinessId(businessId).setBusinessName(businessName);
    }

    public Folder ofParent(String parentId) {
        return this.setParentId(parentId);
    }

}
