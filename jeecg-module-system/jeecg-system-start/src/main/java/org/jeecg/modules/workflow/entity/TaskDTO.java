package org.jeecg.modules.workflow.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 我的流程列表（改造jeecg源码）
 *
 * @author Yoko
 * @since 2022/7/21 11:11
 */
@Data
public class TaskDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;
    /**
     * 业务表单id
     * 需要扩展的仅此字段
     */
    private String businessId;
    private String taskName;
    private String taskId;
    private String taskAssigneeName;
    private String taskAssigneeId;
    @JsonFormat(
            timezone = "GMT+8",
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private Date taskBeginTime;
    @JsonFormat(
            timezone = "GMT+8",
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private Date taskEndTime;
    private Long taskDueTime;
    private String processApplyUserName;
    private String processApplyUserId;
    private String processDefinitionId;
    private String processDefinitionName;
    private String processInstanceId;
    private String bpmBizTitle;
    private String bpmStatus;
    private Boolean taskTimeoutWarn;
    private Boolean taskUrge;
    private String taskUrgeRemark;
    private String remarks;

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
}
