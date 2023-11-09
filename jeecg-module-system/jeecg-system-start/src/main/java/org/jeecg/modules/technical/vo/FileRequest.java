package org.jeecg.modules.technical.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(description = "技术管理-文件请求")
public class FileRequest {

    @ApiModelProperty(value = "文件id")
    private String id;

    @ApiModelProperty(value = "多个文件id")
    private List<String> ids;

    @ApiModelProperty(value = "文件名称")
    private String name;

    @ApiModelProperty(value = "多个文件名称")
    private List<String> names;

    @ApiModelProperty(value = "关联目录")
    private String folderId;

}
