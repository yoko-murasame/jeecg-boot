package org.jeecg.modules.workflow.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "流程任务查询VO", description = "流程任务查询VO")
@Data
public class TaskQueryVO {
    @ApiModelProperty(value = "流程定义id")
    private String procDefId;
    @ApiModelProperty(value = "流程定义name")
    private String procDefName;
    @ApiModelProperty(value = "用户名(手机号)")
    private String userid;
}
