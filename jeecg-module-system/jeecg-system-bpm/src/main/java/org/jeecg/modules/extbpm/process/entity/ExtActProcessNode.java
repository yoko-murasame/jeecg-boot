package org.jeecg.modules.extbpm.process.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("ext_act_process_node")
public class ExtActProcessNode implements Serializable {
    private static final long serialVersionUID = -5984043943248970006L;
    @TableId(
            type = IdType.ASSIGN_ID
    )
    private String id;
    private String formId;
    private String processId;
    private String processNodeCode;
    private String processNodeName;
    private String modelAndView;
    private String modelAndViewMobile;
    private Integer nodeTimeout;
    private String nodeBpmStatus;

    // 表单类型 1 Online表单 2 kform设计器 3 自定义开发 4 online列表
    private String modelAndViewType;
    // 是否显示任务处理模块（和ExtActProcessForm取逻辑与）
    private Boolean showTask;
    // 是否显示流程图模块（和ExtActProcessForm取逻辑与）
    private Boolean showProcess;
    // online列表编码
    private String onlineCode;
    // online表单、列表配置
    private String onlineFormConfig;
    // Online查询筛选参数获取器（JS增强支持await）
    private String onlineInitQueryParamGetter;

}
