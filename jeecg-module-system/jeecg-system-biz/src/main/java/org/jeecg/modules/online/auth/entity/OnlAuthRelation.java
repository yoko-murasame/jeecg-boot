//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jeecg.modules.online.auth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.io.Serializable;

@TableName("onl_auth_relation")
@ApiModel(
        value = "onl_auth_relation对象",
        description = "onl_auth_relation"
)
@Data
@Accessors(chain = true)
public class OnlAuthRelation implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(
            type = IdType.ASSIGN_ID
    )
    @ApiModelProperty("id")
    private String id;
    @Excel(
            name = "角色id",
            width = 15.0
    )
    @ApiModelProperty("角色id")
    private String roleId;
    @Excel(
            name = "权限id",
            width = 15.0
    )
    @ApiModelProperty("权限id")
    private String authId;
    @Excel(
            name = "1字段 2按钮 3数据权限",
            width = 15.0
    )
    @ApiModelProperty("1字段 2按钮 3数据权限")
    private Integer type;
    private String cgformId;

    @ApiModelProperty("鉴权模式")
    private String authMode;

}
