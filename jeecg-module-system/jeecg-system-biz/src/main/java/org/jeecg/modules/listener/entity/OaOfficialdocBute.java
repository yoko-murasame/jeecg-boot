package org.jeecg.modules.listener.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: oa_officialdoc_distribute
 * @Author: jeecg-boot
 * @Date:   2020-07-02
 * @Version: V1.0
 */
@Data
@TableName("oa_officialdoc_distribute")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="公文分发", description="oa_officialdoc_distribute")
public class OaOfficialdocBute implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;
	/**departId*/
	@Excel(name = "部门", width = 15)
    @ApiModelProperty(value = "部门")
    private String departId;
	/**公文发文id*/
	@Excel(name = "公文发文id", width = 15)
    @ApiModelProperty(value = "公文发文id")
    private String issuedId;
	/**状态（0未处理 1已处理）*/
	@Excel(name = "状态（0未处理 1已处理）", width = 15)
    @ApiModelProperty(value = "状态（0未处理 1已处理）")
    private String status;
    /**
     * 分发日期
     */
    @Excel(name = "分发日期", width = 15,format = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "分发日期")
    private Date distributeDate;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private String createBy;
	/**创建时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
	/**修改人*/
    @ApiModelProperty(value = "修改人")
    private String updateBy;
	/**修改时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;
	/**组织机构编码*/
    @ApiModelProperty(value = "组织机构编码")
    private String sysOrgCode;

    /**收文方式 1 公文发文 2 公文收文*/
    @ApiModelProperty(value = "收文方式")
    private String type;
    /**用户id*/
    @ApiModelProperty(value = "用户id")
    private String userId;


}
