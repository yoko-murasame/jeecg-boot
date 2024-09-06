package org.jeecg.modules.wz.business.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: -Circle
 * @Date: 2021/7/6 10:35
 * @Description:
 */
@Data
@TableName("cb_role_layer")
@ApiModel(value = "RoleLayer对象", description = "RoleLayer对象")
public class RoleLayer implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 图层id
     */
    @ApiModelProperty(value = "图层id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long resourceLayerId;

    /**
     * 权限id
     */
    @ApiModelProperty(value = "权限id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long appRoleId;
}
