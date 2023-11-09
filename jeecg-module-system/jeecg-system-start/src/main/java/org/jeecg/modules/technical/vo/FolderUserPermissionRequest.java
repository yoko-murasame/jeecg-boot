package org.jeecg.modules.technical.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel(description ="目录-用户权限请求实体")
@Data
public class FolderUserPermissionRequest {

    @ApiModelProperty(value = "目录id")
    private String folderId;

    @ApiModelProperty(value = "普通用户用户名")
    private List<String> usernames;

    @ApiModelProperty(value = "超级用户用户名")
    private List<String> superUsernames;

}
