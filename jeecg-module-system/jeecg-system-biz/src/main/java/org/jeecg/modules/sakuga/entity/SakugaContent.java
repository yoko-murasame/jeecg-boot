package org.jeecg.modules.sakuga.entity;

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
 * @Description: 作画内容管理
 * @Author: jeecg-boot
 * @Date:   2023-05-06
 * @Version: V1.0
 */
@Data
@TableName("sakuga_content")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="sakuga_content对象", description="作画内容管理")
public class SakugaContent implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private java.lang.String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private java.lang.String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;
	/**所属部门*/
    @ApiModelProperty(value = "所属部门")
    private java.lang.String sysOrgCode;
	/**片段标题*/
	@Excel(name = "片段标题", width = 15)
    @ApiModelProperty(value = "片段标题")
    private java.lang.String title;
	/**动画名称*/
	@Excel(name = "动画名称", width = 15)
    @ApiModelProperty(value = "动画名称")
    private java.lang.String animeName;
	/**作监*/
	@Excel(name = "作监", width = 15)
    @ApiModelProperty(value = "作监")
    private java.lang.String author;
	/**动画公司*/
	@Excel(name = "动画公司", width = 15)
    @ApiModelProperty(value = "动画公司")
    private java.lang.String company;
	/**内容描述*/
	@Excel(name = "内容描述", width = 15)
    @ApiModelProperty(value = "内容描述")
    private java.lang.String content;
	/**标签*/
	@Excel(name = "标签", width = 15, dicCode = "sakuga_content_tags")
	@Dict(dicCode = "sakuga_content_tags")
    @ApiModelProperty(value = "标签")
    private java.lang.String tags;
	/**图片*/
	@Excel(name = "图片", width = 15)
    @ApiModelProperty(value = "图片")
    private java.lang.String picture;
	/**视频*/
	@Excel(name = "视频", width = 15)
    @ApiModelProperty(value = "视频")
    private java.lang.String video;
    /**附件*/
    @Excel(name = "附件", width = 15)
    @ApiModelProperty(value = "附件")
    private java.lang.String file;

    /**内容高亮*/
    @Excel(name = "内容高亮", width = 15)
    @ApiModelProperty(value = "内容高亮")
    @TableField(exist = false)
    private java.lang.String contentHighlight;
}
