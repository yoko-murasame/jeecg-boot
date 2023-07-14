package org.jeecg.modules.workflow.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 通用编辑权限申请流程实体
 *
 * @author Yoko
 * @date 2022/5/10 16:23
 */
@Data
@TableName("deal_service_edit_apply")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "deal_service_gsdj对象", description = "业务办理-估算登记")
public class EditApply implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String CAN_EDIT = "1";
    public static final String CAN_NOT_EDIT = "0";
    public static final String ON_PROCESS = "2";
    public static final String PROCESS_NOT_START = "1";
    public static final String PROCESS_ING = "2";
    public static final String PROCESS_FINISH = "3";
    public static final String PROCESS_KEY = "EditApply";
    public static final String BPM_STATUS = "bpm_status";
    /**
     * 授权通过后，权限的时限（小时）
     */
    public static final Integer EXPIRED_HOUR = 24;

    @TableId(type = IdType.INPUT)
    @ApiModelProperty(value = "业务id，也是主键")
    private String id;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "创建日期")
    private Date createTime;

    @ApiModelProperty(value = "更新人")
    private String updateBy;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "更新日期")
    private Date updateTime;

    @ApiModelProperty(value = "所属部门")
    private String sysOrgCode;

    @ApiModelProperty(value = "流程状态")
    @Dict(dicCode = "bpm_status")
    private String bpmStatus;

    @ApiModelProperty(value = "流程标题")
    private String bpmTitle;

    @ApiModelProperty(value = "编辑状态")
    private String canEdit;

    @ApiModelProperty(value = "流程业务编码")
    private String bpmFlowCode;

    @ApiModelProperty(value = "流程表单地址")
    private String bpmFormUrl;

    @ApiModelProperty(value = "流程表单数据接口地址")
    private String bpmFormApi;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "过期时间")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Date expireTime;

}
