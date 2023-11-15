package org.jeecg.modules.workflow.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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

    @TableField("proc_def_name")
    @ApiModelProperty(value = "流程定义Name")
    private String procDefName;

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

    @TableField("form_data_id")
    @ApiModelProperty(value = "业务主键")
    private String formDataId;

    @TableField("oa_todo_id")
    @ApiModelProperty(value = "oa代办id")
    private String oaDodoId;

    @TableField("bpm_biz_title")
    @ApiModelProperty(value = "业务标题")
    private String bpmBizTitle;

    // v2 列表接口新增字段==============BEGIN==================
    @TableField("business_id")
    private String businessId;
    @TableField("task_assignee_name")
    private String taskAssigneeName;
    @TableField("task_assignee_id")
    private String taskAssigneeId;
    @TableField("task_due_time")
    private Long taskDueTime;
    @TableField("process_apply_user_id")
    private String processApplyUserId;
    @TableField("bpm_status")
    private String bpmStatus;
    @TableField("task_timeout_warn")
    private Boolean taskTimeoutWarn;
    @TableField("task_urge")
    private Boolean taskUrge;
    @TableField("task_urge_remark")
    private String taskUrgeRemark;
    @TableField("remarks")
    private String remarks;

    @TableField("process_apply_user_name")
    @ApiModelProperty(value = "发起人")
    private String processApplyUserName;
    @ApiModelProperty(value = "发起人")
    @TableField("user_name")
    private String userName;

    @TableField("task_begin_time")
    @ApiModelProperty(value = "开始时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String taskBeginTime;

    @TableField("task_end_time")
    @ApiModelProperty(value = "开始时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date taskEndTime;

    @TableField("task_name")
    @ApiModelProperty(value = "当前环节")
    private String taskName;

    @TableField("process_definition_id")
    @ApiModelProperty(value = "流程编号")
    private String processDefinitionId;

    @TableField("task_id")
    @ApiModelProperty(value = "任务ID(这里是TaskDefinitionKey)")
    private String taskId;

    @TableField("process_instance_id")
    @ApiModelProperty(value = "流程实例")
    private String processInstanceId;

    @TableField("process_definition_name")
    @ApiModelProperty(value = "流程定义Name")
    private String processDefinitionName;

    public String dealTimeFromNum(long time) {
        StringBuilder var3 = new StringBuilder();
        long var4 = time / 1000L;
        long var6 = 86400L;
        long var8 = 3600L;
        long var10 = 60L;
        int var12 = 0;
        int var13 = 0;
        int var14 = 0;
        int var15 = 0;
        if (var4 >= 86400L) {
            var12 = (int) (var4 / 86400L);
            var4 -= (long) var12 * 86400L;
        }

        if (var4 >= 3600L) {
            var13 = (int) (var4 / 3600L);
            var4 -= 3600L * (long) var13;
        }

        if (var4 >= 60L) {
            var14 = (int) (var4 / 60L);
            var4 -= (long) var14 * 60L;
        }

        if (var4 > 0L) {
            var15 = (int) var4;
        }

        var3.setLength(0);
        if (var12 > 0) {
            var3.append(var12);
            var3.append("天");
        }

        if (var13 > 0) {
            var3.append(var13);
            var3.append("小时");
        }

        if (var14 > 0) {
            var3.append(var14);
            var3.append("分");
        }

        if (var15 > 0) {
            var3.append(var15);
            var3.append("秒");
        }

        return var3.toString();
    }

    public String getDurationStr() {
        try {
            return this.taskDueTime == null ? null : this.dealTimeFromNum(this.taskDueTime);
        } catch (Exception var2) {
            var2.printStackTrace();
            return null;
        }
    }
    // v2 列表接口新增字段==============END==================

}
