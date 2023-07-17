//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jeecg.modules.online.desform.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@TableName("design_form_route")
@ApiModel(
        value = "design_form_route对象",
        description = "表单设计器路由跳转配置表"
)
public class DesignFormRoute {
    @TableId(
            type = IdType.ASSIGN_ID
    )
    @ApiModelProperty("ID")
    private String id;
    @Excel(
            name = "表单设计ID",
            width = 15.0
    )
    @ApiModelProperty("表单设计ID")
    private String desformId;
    @Excel(
            name = "表单设计编码",
            width = 15.0
    )
    @ApiModelProperty("表单设计编码")
    private String desformCode;
    @Excel(
            name = "路由名称",
            width = 15.0
    )
    @ApiModelProperty("路由名称")
    private String routeName;
    @Dict(
            dicCode = "desform_route_type"
    )
    @Excel(
            name = "路由跳转类型",
            width = 15.0,
            dicCode = "desform_route_type"
    )
    @ApiModelProperty("路由跳转类型")
    private String routeType;
    @Excel(
            name = "下一步路由地址",
            width = 15.0
    )
    @ApiModelProperty("下一步路由地址")
    private String routePath;
    @Excel(
            name = "状态",
            width = 15.0
    )
    @ApiModelProperty("状态")
    private Integer status;
    @Excel(
            name = "创建人",
            width = 15.0
    )
    @ApiModelProperty("创建人")
    private String createBy;
    @Excel(
            name = "创建时间",
            width = 20.0,
            format = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            timezone = "GMT+8",
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @ApiModelProperty("创建时间")
    private Date createTime;
    @Excel(
            name = "修改人",
            width = 15.0
    )
    @ApiModelProperty("修改人")
    private String updateBy;
    @Excel(
            name = "修改时间",
            width = 20.0,
            format = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            timezone = "GMT+8",
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @ApiModelProperty("修改时间")
    private Date updateTime;

    public DesignFormRoute() {
    }

    public String getId() {
        return this.id;
    }

    public String getDesformId() {
        return this.desformId;
    }

    public String getDesformCode() {
        return this.desformCode;
    }

    public String getRouteName() {
        return this.routeName;
    }

    public String getRouteType() {
        return this.routeType;
    }

    public String getRoutePath() {
        return this.routePath;
    }

    public Integer getStatus() {
        return this.status;
    }

    public String getCreateBy() {
        return this.createBy;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public String getUpdateBy() {
        return this.updateBy;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public DesignFormRoute setId(String id) {
        this.id = id;
        return this;
    }

    public DesignFormRoute setDesformId(String desformId) {
        this.desformId = desformId;
        return this;
    }

    public DesignFormRoute setDesformCode(String desformCode) {
        this.desformCode = desformCode;
        return this;
    }

    public DesignFormRoute setRouteName(String routeName) {
        this.routeName = routeName;
        return this;
    }

    public DesignFormRoute setRouteType(String routeType) {
        this.routeType = routeType;
        return this;
    }

    public DesignFormRoute setRoutePath(String routePath) {
        this.routePath = routePath;
        return this;
    }

    public DesignFormRoute setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public DesignFormRoute setCreateBy(String createBy) {
        this.createBy = createBy;
        return this;
    }

    @JsonFormat(
            timezone = "GMT+8",
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    public DesignFormRoute setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public DesignFormRoute setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
        return this;
    }

    @JsonFormat(
            timezone = "GMT+8",
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    public DesignFormRoute setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public String toString() {
        return "DesignFormRoute(id=" + this.getId() + ", desformId=" + this.getDesformId() + ", desformCode=" + this.getDesformCode() + ", routeName=" + this.getRouteName() + ", routeType=" + this.getRouteType() + ", routePath=" + this.getRoutePath() + ", status=" + this.getStatus() + ", createBy=" + this.getCreateBy() + ", createTime=" + this.getCreateTime() + ", updateBy=" + this.getUpdateBy() + ", updateTime=" + this.getUpdateTime() + ")";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof DesignFormRoute)) {
            return false;
        } else {
            DesignFormRoute var2 = (DesignFormRoute)o;
            if (!var2.canEqual(this)) {
                return false;
            } else {
                label143: {
                    Integer var3 = this.getStatus();
                    Integer var4 = var2.getStatus();
                    if (var3 == null) {
                        if (var4 == null) {
                            break label143;
                        }
                    } else if (var3.equals(var4)) {
                        break label143;
                    }

                    return false;
                }

                String var5 = this.getId();
                String var6 = var2.getId();
                if (var5 == null) {
                    if (var6 != null) {
                        return false;
                    }
                } else if (!var5.equals(var6)) {
                    return false;
                }

                String var7 = this.getDesformId();
                String var8 = var2.getDesformId();
                if (var7 == null) {
                    if (var8 != null) {
                        return false;
                    }
                } else if (!var7.equals(var8)) {
                    return false;
                }

                label122: {
                    String var9 = this.getDesformCode();
                    String var10 = var2.getDesformCode();
                    if (var9 == null) {
                        if (var10 == null) {
                            break label122;
                        }
                    } else if (var9.equals(var10)) {
                        break label122;
                    }

                    return false;
                }

                label115: {
                    String var11 = this.getRouteName();
                    String var12 = var2.getRouteName();
                    if (var11 == null) {
                        if (var12 == null) {
                            break label115;
                        }
                    } else if (var11.equals(var12)) {
                        break label115;
                    }

                    return false;
                }

                String var13 = this.getRouteType();
                String var14 = var2.getRouteType();
                if (var13 == null) {
                    if (var14 != null) {
                        return false;
                    }
                } else if (!var13.equals(var14)) {
                    return false;
                }

                String var15 = this.getRoutePath();
                String var16 = var2.getRoutePath();
                if (var15 == null) {
                    if (var16 != null) {
                        return false;
                    }
                } else if (!var15.equals(var16)) {
                    return false;
                }

                label94: {
                    String var17 = this.getCreateBy();
                    String var18 = var2.getCreateBy();
                    if (var17 == null) {
                        if (var18 == null) {
                            break label94;
                        }
                    } else if (var17.equals(var18)) {
                        break label94;
                    }

                    return false;
                }

                label87: {
                    Date var19 = this.getCreateTime();
                    Date var20 = var2.getCreateTime();
                    if (var19 == null) {
                        if (var20 == null) {
                            break label87;
                        }
                    } else if (var19.equals(var20)) {
                        break label87;
                    }

                    return false;
                }

                String var21 = this.getUpdateBy();
                String var22 = var2.getUpdateBy();
                if (var21 == null) {
                    if (var22 != null) {
                        return false;
                    }
                } else if (!var21.equals(var22)) {
                    return false;
                }

                Date var23 = this.getUpdateTime();
                Date var24 = var2.getUpdateTime();
                if (var23 == null) {
                    if (var24 != null) {
                        return false;
                    }
                } else if (!var23.equals(var24)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof DesignFormRoute;
    }

    public int hashCode() {
        boolean var1 = true;
        int var2 = 1;
        Integer var3 = this.getStatus();
        var2 = var2 * 59 + (var3 == null ? 43 : var3.hashCode());
        String var4 = this.getId();
        var2 = var2 * 59 + (var4 == null ? 43 : var4.hashCode());
        String var5 = this.getDesformId();
        var2 = var2 * 59 + (var5 == null ? 43 : var5.hashCode());
        String var6 = this.getDesformCode();
        var2 = var2 * 59 + (var6 == null ? 43 : var6.hashCode());
        String var7 = this.getRouteName();
        var2 = var2 * 59 + (var7 == null ? 43 : var7.hashCode());
        String var8 = this.getRouteType();
        var2 = var2 * 59 + (var8 == null ? 43 : var8.hashCode());
        String var9 = this.getRoutePath();
        var2 = var2 * 59 + (var9 == null ? 43 : var9.hashCode());
        String var10 = this.getCreateBy();
        var2 = var2 * 59 + (var10 == null ? 43 : var10.hashCode());
        Date var11 = this.getCreateTime();
        var2 = var2 * 59 + (var11 == null ? 43 : var11.hashCode());
        String var12 = this.getUpdateBy();
        var2 = var2 * 59 + (var12 == null ? 43 : var12.hashCode());
        Date var13 = this.getUpdateTime();
        var2 = var2 * 59 + (var13 == null ? 43 : var13.hashCode());
        return var2;
    }
}
