package org.jeecg.modules.technical.vo;

// import com.bimface.file.bean.FileBean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.jeecg.modules.system.entity.SysUpload;
import org.jeecg.modules.technical.entity.enums.Current;
import org.jeecg.modules.technical.entity.enums.Suffix;
import org.jeecg.modules.technical.entity.enums.Type;

import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
@ApiModel(description = "技术管理-文件Vo实体")
public class FileVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "文件名，相同时，版本号+1")
    private String name;
    @ApiModelProperty(value = "版本号", hidden = true)
    private Integer version;
    @ApiModelProperty(value = "关联目录id")
    private String folderId;
    @ApiModelProperty(value = "文件顺序，扩展字段，暂时不需要考虑", hidden = true)
    private Integer order;

    @ApiModelProperty(value = "关联项目id")
    private String projectId;
    @ApiModelProperty(value = "关联项目名", hidden = true)
    private String projectName;

    @ApiModelProperty(value = "业务id", hidden = true)
    private String businessId;
    @ApiModelProperty(value = "业务名", hidden = true)
    private String businessName;

    @ApiModelProperty(value = "关联aliyun oss文件", hidden = true)
    private SysUpload ossFile;
    // @ApiModelProperty(value = "关联bimface file", hidden = true)
    // private FileBean bimfaceFile;

    @ApiModelProperty(value = "类型", hidden = true)
    private Type type;
    @ApiModelProperty(value = "后缀", hidden = true)
    private Suffix suffix;
    @ApiModelProperty(value = "当前选中版本", hidden = true)
    private Current current;

    @ApiModelProperty(value = "缩略图", hidden = true)
    private String thumbnail;
    @ApiModelProperty(value = "文件大小", hidden = true)
    private String size;
    @ApiModelProperty(value = "变更", hidden = true)
    private List<String> changes;
    @ApiModelProperty(value = "批注", hidden = true)
    private List<String> comments;
    @ApiModelProperty(value = "问题", hidden = true)
    private List<String> problems;

    @ApiModelProperty(value = "创建时间", hidden = true)
    private String createTime;
    @ApiModelProperty(value = "创建人", hidden = true)
    private String createBy;
    @ApiModelProperty(value = "上传人", hidden = true)
    private String uploadBy;

    @ApiModelProperty(value = "更新时间", hidden = true)
    private String updateTime;
}
