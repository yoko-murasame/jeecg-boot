package org.jeecg.modules.technical.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecg.common.system.base.entity.JeecgEntity;
import org.jeecg.config.mybatis.ListTypeHandler;
import org.jeecg.modules.system.entity.SysUpload;
import org.jeecg.modules.technical.entity.enums.Current;
import org.jeecg.modules.technical.entity.enums.Enabled;
import org.jeecg.modules.technical.entity.enums.Suffix;
import org.jeecg.modules.technical.entity.enums.Type;

import java.util.List;

// import com.bimface.file.bean.FileBean;

/**
 * @author Yoko
 * @date 2021.01.28
 * 通用文件类型，图纸、模型
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "technical_file", autoResultMap = true)
public class File extends JeecgEntity {

    private String name; // 文件名，相同时，版本号+1
    private Integer version; // 版本号
    @TableField(value = "folder_id")
    private String folderId; // 关联目录
    @TableField(value = "file_order")
    private Integer sortOrder; // 文件顺序，扩展字段，暂时不需要考虑

    @TableField(value = "project_id")
    private String projectId; // 关联项目id
    @TableField(value = "project_name")
    private String projectName; // 关联项目名

    @TableField(value = "oss_file", typeHandler = FastjsonTypeHandler.class)
    private SysUpload ossFile; // 关联aliyun oss文件
    // @TableField(value = "bimface_file",typeHandler = JacksonTypeHandler.class)
    // private FileBean bimfaceFile; //关联bimface file

    @TableField(value = "current_show")
    private Current current; // 表示当前选中版本
    private Type type; // 类型 int
    private Suffix suffix; // 后缀 String
    private Enabled enabled; // 逻辑删除 int

    private String thumbnail; // 缩略图
    private String size; // 文件大小
    // 这里我换成了自己写的 ListTypeHandler 其实用默认的JSON处理也可以
    @TableField(typeHandler = ListTypeHandler.class)
    private List<String> changes; // 变更
    @TableField(typeHandler = ListTypeHandler.class)
    private List<String> comments; // 批注
    @TableField(typeHandler = ListTypeHandler.class)
    private List<String> problems; // 问题
    @TableField(value = "upload_by")
    private String uploadBy; // 上传人，使用realname作值

    @TableField(value = "business_id")
    private String businessId;
    @TableField(value = "business_name")
    private String businessName;
}
