package org.jeecg.modules.technical.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.jeecg.modules.technical.entity.enums.Enabled;
import org.jeecg.modules.technical.entity.enums.Level;
import org.jeecg.modules.technical.entity.enums.Type;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@ApiModel(description = "技术管理-目录Vo实体")
public class FolderVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value="名称")
    private String name;
    @ApiModelProperty(value = "层级", hidden = true)
    private Level level;
    @ApiModelProperty(value = "顺序", hidden = true)
    private Integer order;
    @ApiModelProperty(value = "父层级")
    private String parentId;
    @ApiModelProperty(value = "项目id", hidden = true)
    private String projectId;
    @ApiModelProperty(value = "项目名", hidden = true)
    private String projectName;
    @ApiModelProperty(value = "业务id", hidden = true)
    private String businessId;
    @ApiModelProperty(value = "业务名", hidden = true)
    private String businessName;
    @ApiModelProperty(value = "子目录数", hidden = true)
    private Integer childFolderSize;
    @ApiModelProperty(value = "子文件数", hidden = true)
    private Integer childFileSize;
    @ApiModelProperty(value = "类型", hidden = true)
    private Type type;
    @ApiModelProperty(value = "启用状态", hidden = true)
    private Enabled enabled;

    @ApiModelProperty(value = "创建时间", hidden = true)
    private String createTime;

    @ApiModelProperty(value = "更新时间", hidden = true)
    private String updateTime;

    @ApiModelProperty(value = "标签")
    private String tags;
}
