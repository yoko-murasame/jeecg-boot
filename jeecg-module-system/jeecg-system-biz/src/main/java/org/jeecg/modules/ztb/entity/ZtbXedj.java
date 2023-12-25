package org.jeecg.modules.ztb.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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

/**
 * @Description: 招投标-小额登记
 */
@Data
@TableName("ztb_xedj")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "ztb_xedj对象", description = "招投标-小额登记")
public class ZtbXedj implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private String createBy;
    /**
     * 创建日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    private String updateBy;
    /**
     * 更新日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;
    /**
     * 所属部门
     */
    @ApiModelProperty(value = "所属部门")
    private String sysOrgCode;
    /**
     * 工程项目名称
     */
    @Excel(name = "工程项目名称", width = 30)
    @ApiModelProperty(value = "工程项目名称")
    private String gcxmmc;
    /**
     * 工程合同金额
     */
    @Excel(name = "工程合同金额", width = 15)
    @ApiModelProperty(value = "工程合同金额")
    private java.math.BigDecimal gchtje;
    /**
     * 工程项目负责人
     */
    @Excel(name = "工程项目负责人", width = 15)
    @ApiModelProperty(value = "工程项目负责人")
    private String gcxmfzr;
    /**
     * 发包方式
     */
    @Excel(name = "发包方式", width = 15, dicCode = "ztb_xedj_fbfs")
    @ApiModelProperty(value = "发包方式")
    @Dict(dicCode = "ztb_xedj_fbfs")
    private String fbfs;
    /**
     * 施工单位
     */
    @Excel(name = "施工单位", width = 15)
    @ApiModelProperty(value = "施工单位")
    private String sgdw;
    /**
     * 实际开工时间
     */
    @Excel(name = "实际开工时间", width = 15, format = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "实际开工时间")
    private java.util.Date sjkgsj;
    /**
     * 工程内容
     */
    @Excel(name = "工程内容", width = 30)
    @ApiModelProperty(value = "工程内容")
    private String gcnr;
    /**
     * 合同完成进度
     */
    @Excel(name = "合同完成进度", width = 15)
    @ApiModelProperty(value = "合同完成进度")
    private String htwcjd;
    /**
     * 目前形象进度
     */
    @Excel(name = "目前形象进度", width = 15)
    @ApiModelProperty(value = "目前形象进度")
    private String mqxxjd;
    /**
     * 目前现场照片
     */
    // @Excel(name = "目前现场照片", width = 15)
    @ApiModelProperty(value = "目前现场照片")
    private String mqxczp;
    /**
     * 项目存在问题
     */
    @Excel(name = "项目存在问题", width = 30)
    @ApiModelProperty(value = "项目存在问题")
    private String xmczwt;
    /**
     * 工程额支付进度
     */
    @Excel(name = "工程额支付进度", width = 15)
    @ApiModelProperty(value = "工程额支付进度")
    private String gcezfjd;
    /**
     * 验收时间
     */
    @Excel(name = "验收时间", width = 15, format = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "验收时间")
    private java.util.Date yssj;
    /**
     * 完工验收材料
     */
    // @Excel(name = "完工验收材料", width = 15)
    @ApiModelProperty(value = "完工验收材料")
    private String wgyscl;
    /**
     * 完工照片
     */
    // @Excel(name = "完工照片", width = 15)
    @ApiModelProperty(value = "完工照片")
    private String wgzp;
    /**
     * 结算资料
     */
    // @Excel(name = "结算资料", width = 15)
    @ApiModelProperty(value = "结算资料")
    private String jszl;
    /**
     * 其他附件
     */
    // @Excel(name = "其他附件", width = 15)
    @ApiModelProperty(value = "其他附件")
    private String qtfj;
    /**
     * 当前阶段
     */
    @Excel(name = "当前阶段", width = 15, dicCode = "ztb_xedj_state")
    @ApiModelProperty(value = "当前阶段")
    @Dict(dicCode = "ztb_xedj_state")
    private String currentState;

    /**
     * 根据登记的验收时间，超过验收时间3个月不传结算材料，就做提醒
     */
    @ApiModelProperty(value = "结算材料填写超时提醒")
    @TableField(exist = false)
    private String overdue;

}
