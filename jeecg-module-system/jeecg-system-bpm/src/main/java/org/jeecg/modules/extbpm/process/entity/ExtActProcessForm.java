package org.jeecg.modules.extbpm.process.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("ext_act_process_form")
public class ExtActProcessForm implements Serializable {
    private static final long serialVersionUID = -14940371239459038L;
    @TableId(
            type = IdType.ASSIGN_ID
    )
    private String id;
    private String relationCode;
    private String bizName;
    private String processId;
    private String formTableName;
    private String formType;
    private String titleExp;
    private String formDealStyle;
    private String flowStatusCol;
    // 是否循环发起流程
    private Boolean circulate;

}
