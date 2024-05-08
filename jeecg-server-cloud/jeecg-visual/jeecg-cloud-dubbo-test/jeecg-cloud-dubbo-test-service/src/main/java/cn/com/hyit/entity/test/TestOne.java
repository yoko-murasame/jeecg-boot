package cn.com.hyit.entity.test;

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
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@ApiModel(value="TestOne-实体", description="生成测试表-实体")
@Accessors(chain = true)
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("test_one")
public class TestOne implements Serializable, Cloneable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键", name = "id", notes = "主键")
    private String id;

    /**
     * 创建人
     */
    @TableField(value = "create_by")
    @ApiModelProperty(value = "创建人", name = "createBy", notes = "创建人")
    @Size(max = 50, message = "创建人长度不能超过50")
    private String createBy;

    /**
     * 创建日期
     */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @TableField(value = "create_time")
    @ApiModelProperty(value = "创建日期", name = "createTime", notes = "创建日期")
    private Date createTime;

    /**
     * 更新人
     */
    @TableField(value = "update_by")
    @ApiModelProperty(value = "更新人", name = "updateBy", notes = "更新人")
    @Size(max = 50, message = "更新人长度不能超过50")
    private String updateBy;

    /**
     * 更新日期
     */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @TableField(value = "update_time")
    @ApiModelProperty(value = "更新日期", name = "updateTime", notes = "更新日期")
    private Date updateTime;

    /**
     * 所属部门
     */
    @TableField(value = "sys_org_code")
    @ApiModelProperty(value = "所属部门", name = "sysOrgCode", notes = "所属部门")
    @Size(max = 64, message = "所属部门长度不能超过64")
    private String sysOrgCode;

    /**
     * 名称
     */
    @TableField(value = "name")
    @ApiModelProperty(value = "名称", name = "name", notes = "名称")
    @Size(max = 32, message = "名称长度不能超过32")
    private String name;

    /**
     * 性别
     */
    @TableField(value = "sex")
    @ApiModelProperty(value = "性别", name = "sex", notes = "性别")
    @Size(max = 32, message = "性别长度不能超过32")
    private String sex;

    /**
     * 时间
     */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @TableField(value = "sj")
    @ApiModelProperty(value = "时间", name = "sj", notes = "时间")
    private Date sj;

    /**
     * 流程状态
     */
    @TableField(value = "bpm_status")
    @ApiModelProperty(value = "流程状态", name = "bpmStatus", notes = "流程状态", required = true)
    @Size(max = 32, message = "流程状态长度不能超过32")
    @NotEmpty(message = "流程状态不能为空")
    private String bpmStatus;

    /**
     * 辣辣
     */
    @TableField(value = "lala")
    @ApiModelProperty(value = "辣辣", name = "lala", notes = "辣辣")
    @Size(max = 32, message = "辣辣长度不能超过32")
    private String lala;

    /**
     * 文件
     */
    @TableField(value = "file")
    @ApiModelProperty(value = "文件", name = "file", notes = "文件")
    private String file;

    /**
     * 图片
     */
    @TableField(value = "image")
    @ApiModelProperty(value = "图片", name = "image", notes = "图片")
    private String image;

    /**
     * 字段a
     */
    @TableField(value = "new_one")
    @ApiModelProperty(value = "字段a", name = "newOne", notes = "字段a", required = true)
    @Size(max = 32, message = "字段a长度不能超过32")
    @NotEmpty(message = "字段a不能为空")
    private String newOne;

    /**
     * 数值a
     */
    @TableField(value = "num_one")
    @ApiModelProperty(value = "数值a", name = "numOne", notes = "数值a")
    private BigDecimal numOne;

}
