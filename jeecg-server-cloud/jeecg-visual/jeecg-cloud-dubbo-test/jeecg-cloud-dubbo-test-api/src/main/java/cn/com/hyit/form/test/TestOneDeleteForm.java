package cn.com.hyit.form.test;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="TestOneDeleteForm", description="生成测试表-删除表单")
public class TestOneDeleteForm implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键", name = "id", notes = "主键", required = true)
    @NotEmpty(message = "主键不能为空")
    private String id;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人", name = "createBy", notes = "创建人")
    private String createBy;

    /**
     * 创建日期
     */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
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
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
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
    @ApiModelProperty(value = "名称", name = "name", notes = "名称")
    private String name;

    /**
     * 性别
     */
    @ApiModelProperty(value = "性别", name = "sex", notes = "性别")
    private String sex;

    /**
     * 时间
     */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "时间", name = "sj", notes = "时间")
    private Date sj;

    /**
     * 流程状态
     */
    @ApiModelProperty(value = "流程状态", name = "bpmStatus", notes = "流程状态")
    private String bpmStatus;

    /**
     * 辣辣
     */
    @ApiModelProperty(value = "辣辣", name = "lala", notes = "辣辣")
    private String lala;

    /**
     * 文件
     */
    @ApiModelProperty(value = "文件", name = "file", notes = "文件")
    private String file;

    /**
     * 图片
     */
    @ApiModelProperty(value = "图片", name = "image", notes = "图片")
    private String image;

    /**
     * 字段a
     */
    @ApiModelProperty(value = "字段a", name = "newOne", notes = "字段a")
    private String newOne;

    /**
     * 数值a
     */
    @ApiModelProperty(value = "数值a", name = "numOne", notes = "数值a")
    private BigDecimal numOne;

}
