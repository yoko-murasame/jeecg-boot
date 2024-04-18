package cn.com.hyit.form.test;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableField;
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
@ApiModel(value="TestOneRpcQueryForm", description="生成测试表-rpc查询表单")
public class TestOneRpcQueryForm implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键", name = "id", notes = "主键")
    private String id;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人", name = "createBy", notes = "创建人")
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
    private String sysOrgCode;

    /**
     * 名称
     */
    @Excel(name = "名称", width = 15)
    @ApiModelProperty(value = "名称", name = "name", notes = "名称")
    private String name;

    /**
     * 性别
     */
    @Excel(name = "性别", width = 15)
    @ApiModelProperty(value = "性别", name = "sex", notes = "性别")
    private String sex;

    /**
     * 时间
     */
    @Excel(name = "时间", width = 15, format = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "时间", name = "sj", notes = "时间")
    private Date sj;

    /**
     * 流程状态
     */
    @Excel(name = "流程状态", width = 15)
    @ApiModelProperty(value = "流程状态", name = "bpmStatus", notes = "流程状态")
    private String bpmStatus;

    /**
     * 辣辣
     */
    @Excel(name = "辣辣", width = 15)
    @ApiModelProperty(value = "辣辣", name = "lala", notes = "辣辣")
    private String lala;

    /**
     * 文件
     */
    @Excel(name = "文件", width = 15)
    @ApiModelProperty(value = "文件", name = "file", notes = "文件")
    private String file;

    /**
     * 图片
     */
    @Excel(name = "图片", width = 15)
    @ApiModelProperty(value = "图片", name = "image", notes = "图片")
    private String image;

    /**
     * 字段a
     */
    @Excel(name = "字段a", width = 15)
    @ApiModelProperty(value = "字段a", name = "newOne", notes = "字段a")
    private String newOne;

    /**
     * 数值a
     */
    @Excel(name = "数值a", width = 15)
    @ApiModelProperty(value = "数值a", name = "numOne", notes = "数值a")
    private BigDecimal numOne;

    @ApiModelProperty(value = "每页数量", name = "pageSize", notes = "每页数量,默认20")
    @TableField(exist = false)
    private Integer pageSize = 20;

    @ApiModelProperty(value = "页码", name = "pageNum", notes = "页码,默认1")
    @TableField(exist = false)
    private Integer pageNum = 1;

}
