package org.jeecg.modules.extbpm.process.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("ext_act_process_node_auth")
public class ExtActProcessNodePermission implements Serializable {
    private static final long serialVersionUID = 7551093886668881378L;
    @TableId(
            type = IdType.ASSIGN_ID
    )
    private String id;
    @Excel(
            name = "流程ID",
            width = 15.0
    )
    private String processId;
    @Excel(
            name = "节点编码",
            width = 15.0
    )
    private String processNodeCode;
    @Excel(
            name = "规则编码",
            width = 15.0
    )
    private String ruleCode;
    @Excel(
            name = "规则名称",
            width = 15.0
    )
    private String ruleName;
    @Excel(
            name = "策略",
            width = 15.0
    )
    private String ruleType;
    @Excel(
            name = "状态(0无效1有效)",
            width = 15.0
    )
    private String status;
    @Excel(
            name = "创建人登录名称",
            width = 15.0
    )
    private String createBy;
    @Excel(
            name = "创建日期",
            width = 20.0,
            format = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            timezone = "GMT+8",
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private Date createTime;
    @Excel(
            name = "更新人登录名称",
            width = 15.0
    )
    private String updateBy;
    @Excel(
            name = "更新日期",
            width = 20.0,
            format = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            timezone = "GMT+8",
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private Date updateTime;
    private String formType;
    private String formBizCode;
    private String desformComKey;

}
