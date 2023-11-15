package org.jeecg.modules.workflow.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

/**
 * 我发起的流程列表
 * 流程历史轨迹
 *
 * @author Yoko
 * @since 2023/11/15 15:53
 */
@Data
public class ProcessHisDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private String processInstanceId;
    private String processDefinitionId;
    private String processDefinitionName;
    private String createTime;
    private String startTime;
    private Long taskDueTime;
    private String endTime;
    private String assignee;
    private String assigneeName;
    private String deleteReason;
    private String startUserId;
    private String startUserName;
    @TableField(exist = false)
    private String spendTimes;
    private String bpmBizTitle;
    private String bpmStatus;
    private String businessId;
    @TableField(exist = false)
    private String finishedStateQuery;

    public static final String isUnfinished = "isUnfinished";
    public static final String isFinished = "isFinished";

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

    public String getSpendTimes() {
        try {
            return this.taskDueTime == null ? null : this.dealTimeFromNum(this.taskDueTime);
        } catch (Exception var2) {
            var2.printStackTrace();
            return null;
        }
    }

}
