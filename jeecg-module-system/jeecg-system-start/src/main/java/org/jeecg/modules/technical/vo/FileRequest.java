package org.jeecg.modules.technical.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "技术管理-文件请求")
public class FileRequest {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "关联目录")
    private String folderId;

}
