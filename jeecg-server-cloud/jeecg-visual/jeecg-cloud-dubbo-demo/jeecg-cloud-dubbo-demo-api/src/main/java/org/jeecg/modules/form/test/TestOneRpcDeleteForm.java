package org.jeecg.modules.form.test;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="TestOneRpcDeleteForm", description="测试模块-rpc删除表单")
public class TestOneRpcDeleteForm implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键", name = "id", notes = "主键", required = true)
    private String id;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人", name = "createBy", notes = "创建人")
    @Size(max = 200, message = "创建人长度不能超过200")
    private String createBy;

    /**
     * 创建日期
     */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "创建日期", name = "createTime", notes = "创建日期")
    private Date createTime;

    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人", name = "updateBy", notes = "更新人")
    @Size(max = 200, message = "更新人长度不能超过200")
    private String updateBy;

    /**
     * 更新日期
     */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "更新日期", name = "updateTime", notes = "更新日期")
    private Date updateTime;

    /**
     * 所属部门
     */
    @ApiModelProperty(value = "所属部门", name = "sysOrgCode", notes = "所属部门")
    @Size(max = 200, message = "所属部门长度不能超过200")
    private String sysOrgCode;

    /**
     * 名称
     */
    @Excel(name = "名称", width = 15)
    @ApiModelProperty(value = "名称", name = "name", notes = "名称", required = true)
    @Size(max = 200, message = "名称长度不能超过200")
    @NotEmpty(message = "名称不能为空")
    private String name;

    /**
     * 性别
     */
    @Excel(name = "性别", width = 15, dicCode = "sex")
    @Dict(dicCode = "sex")
    @ApiModelProperty(value = "性别", name = "sex", notes = "性别")
    @Size(max = 200, message = "性别长度不能超过200")
    private String sex;

    /**
     * 时间2
     */
    @Excel(name = "时间2", width = 15, format = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "时间2", name = "sj", notes = "时间2")
    private Date sj;

    /**
     * 流程状态2
     */
    @Excel(name = "流程状态2", width = 15, dicCode = "bpm_status")
    @Dict(dicCode = "bpm_status")
    @ApiModelProperty(value = "流程状态2", name = "bpmStatus", notes = "流程状态2")
    @Size(max = 200, message = "流程状态2长度不能超过200")
    private String bpmStatus;

    /**
     * 辣辣2
     */
    @Excel(name = "辣辣2", width = 15)
    @ApiModelProperty(value = "辣辣2", name = "lala", notes = "辣辣2")
    @Size(max = 32, message = "辣辣2长度不能超过32")
    private String lala;

    /**
     * 文件2
     */
    @Excel(name = "文件2", width = 15)
    @ApiModelProperty(value = "文件2", name = "file", notes = "文件2")
    @Size(max = 300, message = "文件2长度不能超过300")
    private String file;

    /**
     * 图片2
     */
    @Excel(name = "图片2", width = 15)
    @ApiModelProperty(value = "图片2", name = "image", notes = "图片2")
    @Size(max = 300, message = "图片2长度不能超过300")
    private String image;

    /**
     * 测试
     */
    @Excel(name = "测试", width = 15)
    @ApiModelProperty(value = "测试", name = "newOne", notes = "测试")
    @Size(max = 32, message = "测试长度不能超过32")
    private String newOne;

}
