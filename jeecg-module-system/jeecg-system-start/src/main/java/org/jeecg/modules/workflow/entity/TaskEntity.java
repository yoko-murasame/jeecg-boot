package org.jeecg.modules.workflow.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class TaskEntity {
    @TableId("id_")
    @ApiModelProperty(value = "ID")
    private String id;

    @TableField("rev_")
    @ApiModelProperty(value = "版本")
    private Integer rev;

    @TableField("execution_id_")
    @ApiModelProperty(value = "执行ID")
    private String executionId;

    @TableField("proc_inst_id_")
    @ApiModelProperty(value = "流程实例ID")
    private String procInstId;

    @TableField("proc_def_id_")
    @ApiModelProperty(value = "流程定义ID")
    private String procDefId;

    @TableField("name_")
    @ApiModelProperty(value = "名称")
    private String name;

    @TableField("parent_task_id_")
    @ApiModelProperty(value = "父任务ID")
    private String parentTaskId;

    @TableField("description_")
    @ApiModelProperty(value = "描述")
    private String description;

    @TableField("task_def_key_")
    @ApiModelProperty(value = "任务定义键")
    private String taskDefKey;

    @TableField("owner_")
    @ApiModelProperty(value = "拥有者")
    private String owner;

    @TableField("assignee_")
    @ApiModelProperty(value = "受理人")
    private String assignee;

    @TableField("delegation_")
    @ApiModelProperty(value = "委派")
    private String delegation;

    @TableField("priority_")
    @ApiModelProperty(value = "优先级")
    private Integer priority;

    @TableField("create_time_")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @TableField("due_date_")
    @ApiModelProperty(value = "截止日期")
    private Date dueDate;

    @TableField("category_")
    @ApiModelProperty(value = "分类")
    private String category;

    @TableField("suspension_state_")
    @ApiModelProperty(value = "挂起状态")
    private Integer suspensionState;

    @TableField("tenant_id_")
    @ApiModelProperty(value = "租户ID")
    private String tenantId;

    @TableField("form_key_")
    @ApiModelProperty(value = "表单键")
    private String formKey;

}
