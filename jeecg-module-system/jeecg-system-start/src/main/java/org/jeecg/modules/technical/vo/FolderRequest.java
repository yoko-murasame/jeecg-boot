package org.jeecg.modules.technical.vo;

import com.alibaba.fastjson.JSONArray;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecg.modules.technical.entity.enums.Type;

import java.util.List;

@Data
@ApiModel(description = "技术管理-目录请求")
public class FolderRequest {

    @ApiModelProperty(value = "关联项目id")
    private String projectId;
    @ApiModelProperty(value = "关联项目名", hidden = false)
    private String projectName;

    @ApiModelProperty(value = "业务id", hidden = false)
    private String businessId;
    @ApiModelProperty(value = "业务名", hidden = false)
    private String businessName;

    @ApiModelProperty(value = "类型", hidden = false)
    private Type type;

    @ApiModelProperty(value = "初始化目录，已废弃", hidden = false)
    @Deprecated
    private List<String> subFolders;
    @ApiModelProperty(value = "初始化目录，JSON配置数组", hidden = false)
    private JSONArray initialFolders;

}
