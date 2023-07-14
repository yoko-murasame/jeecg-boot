package org.jeecg.modules.listener.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.io.Serializable;

/**
 * @Description: 部门对应用户
 * @Author: jeecg-boot
 * @Date:   2020-07-06
 * @Version: V1.0
 */
@Data
@TableName("oa_officialdoc_depart_user")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="oa_officialdoc_depart_user对象", description="部门对应用户")
public class OaOfficialdocDeUser implements Serializable {
    private static final long serialVersionUID = 1L;

    /**id*/
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;
    /**部门*/
    @Excel(name = "部门", width = 15,dictTable = "sys_depart",dicCode = "id",dicText = "depart_name")
    @ApiModelProperty(value = "部门")
    @Dict(dictTable = "sys_depart",dicCode = "id",dicText = "depart_name")
    private String departId;
    /**用户*/
    @Excel(name = "用户", width = 15)
    @ApiModelProperty(value = "用户")
    @Dict(dictTable = "sys_user",dicText = "realname",dicCode = "username")
    private String userId;
}
