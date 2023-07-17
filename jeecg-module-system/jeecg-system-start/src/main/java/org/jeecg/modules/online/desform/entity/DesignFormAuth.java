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
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@TableName("design_form_auth")
@ApiModel(
        value = "design_form_auth对象",
        description = "表单设计器字段权限表"
)
public class DesignFormAuth {
    @TableId(
            type = IdType.ASSIGN_ID
    )
    @ApiModelProperty("主键ID")
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
            name = "权限类型",
            width = 20.0
    )
    @ApiModelProperty("权限类型")
    private String permissionType;
    @Excel(
            name = "组件id json中的key",
            width = 15.0
    )
    @ApiModelProperty("组件id json中的key")
    private String authComKey;
    @Excel(
            name = "权限名称",
            width = 15.0
    )
    @ApiModelProperty("权限名称")
    private String authTitle;
    @Excel(
            name = "权限字段",
            width = 15.0
    )
    @ApiModelProperty("权限字段")
    private String authField;
    @Excel(
            name = "授权类型",
            width = 15.0
    )
    @ApiModelProperty("授权类型")
    private String authType;
    @Excel(
            name = "授权规则值",
            width = 15.0
    )
    @ApiModelProperty("授权规则值")
    private String authValue;
    @Excel(
            name = "授权范围【Y 所有人 ,N 不是所有人】默认所有人",
            width = 15.0
    )
    @ApiModelProperty("授权范围【Y 所有人 ,N 不是所有人】默认所有人")
    private String authScopeIsAll;
    @Excel(
            name = "授权范围值，保存user登录账户",
            width = 15.0
    )
    @ApiModelProperty("授权范围值，保存user登录账户")
    private String authScopeUsersVal;
    @Excel(
            name = "授权范围值，保存授权角色编码",
            width = 15.0
    )
    @ApiModelProperty("授权范围值，保存授权角色编码")
    private String authScopeRolesVal;
    @Excel(
            name = "授权范围值，保存部门编码",
            width = 15.0
    )
    @ApiModelProperty("授权范围值，保存部门编码")
    private String authScopeDepartsVal;
    @Excel(
            name = "是否是子表内权限",
            width = 15.0
    )
    @ApiModelProperty("是否是子表内权限")
    private Boolean subTable;
    @Excel(
            name = "子表的标题",
            width = 15.0
    )
    @ApiModelProperty("子表的标题")
    private String subTitle;
    @Excel(
            name = "子表的Key",
            width = 15.0
    )
    @ApiModelProperty("子表的Key")
    private String subKey;
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

    public DesignFormAuth() {
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

    public String getPermissionType() {
        return this.permissionType;
    }

    public String getAuthComKey() {
        return this.authComKey;
    }

    public String getAuthTitle() {
        return this.authTitle;
    }

    public String getAuthField() {
        return this.authField;
    }

    public String getAuthType() {
        return this.authType;
    }

    public String getAuthValue() {
        return this.authValue;
    }

    public String getAuthScopeIsAll() {
        return this.authScopeIsAll;
    }

    public String getAuthScopeUsersVal() {
        return this.authScopeUsersVal;
    }

    public String getAuthScopeRolesVal() {
        return this.authScopeRolesVal;
    }

    public String getAuthScopeDepartsVal() {
        return this.authScopeDepartsVal;
    }

    public Boolean getSubTable() {
        return this.subTable;
    }

    public String getSubTitle() {
        return this.subTitle;
    }

    public String getSubKey() {
        return this.subKey;
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

    public DesignFormAuth setId(String id) {
        this.id = id;
        return this;
    }

    public DesignFormAuth setDesformId(String desformId) {
        this.desformId = desformId;
        return this;
    }

    public DesignFormAuth setDesformCode(String desformCode) {
        this.desformCode = desformCode;
        return this;
    }

    public DesignFormAuth setPermissionType(String permissionType) {
        this.permissionType = permissionType;
        return this;
    }

    public DesignFormAuth setAuthComKey(String authComKey) {
        this.authComKey = authComKey;
        return this;
    }

    public DesignFormAuth setAuthTitle(String authTitle) {
        this.authTitle = authTitle;
        return this;
    }

    public DesignFormAuth setAuthField(String authField) {
        this.authField = authField;
        return this;
    }

    public DesignFormAuth setAuthType(String authType) {
        this.authType = authType;
        return this;
    }

    public DesignFormAuth setAuthValue(String authValue) {
        this.authValue = authValue;
        return this;
    }

    public DesignFormAuth setAuthScopeIsAll(String authScopeIsAll) {
        this.authScopeIsAll = authScopeIsAll;
        return this;
    }

    public DesignFormAuth setAuthScopeUsersVal(String authScopeUsersVal) {
        this.authScopeUsersVal = authScopeUsersVal;
        return this;
    }

    public DesignFormAuth setAuthScopeRolesVal(String authScopeRolesVal) {
        this.authScopeRolesVal = authScopeRolesVal;
        return this;
    }

    public DesignFormAuth setAuthScopeDepartsVal(String authScopeDepartsVal) {
        this.authScopeDepartsVal = authScopeDepartsVal;
        return this;
    }

    public DesignFormAuth setSubTable(Boolean subTable) {
        this.subTable = subTable;
        return this;
    }

    public DesignFormAuth setSubTitle(String subTitle) {
        this.subTitle = subTitle;
        return this;
    }

    public DesignFormAuth setSubKey(String subKey) {
        this.subKey = subKey;
        return this;
    }

    public DesignFormAuth setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public DesignFormAuth setCreateBy(String createBy) {
        this.createBy = createBy;
        return this;
    }

    @JsonFormat(
            timezone = "GMT+8",
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    public DesignFormAuth setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public DesignFormAuth setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
        return this;
    }

    @JsonFormat(
            timezone = "GMT+8",
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    public DesignFormAuth setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public String toString() {
        return "DesignFormAuth(id=" + this.getId() + ", desformId=" + this.getDesformId() + ", desformCode=" + this.getDesformCode() + ", permissionType=" + this.getPermissionType() + ", authComKey=" + this.getAuthComKey() + ", authTitle=" + this.getAuthTitle() + ", authField=" + this.getAuthField() + ", authType=" + this.getAuthType() + ", authValue=" + this.getAuthValue() + ", authScopeIsAll=" + this.getAuthScopeIsAll() + ", authScopeUsersVal=" + this.getAuthScopeUsersVal() + ", authScopeRolesVal=" + this.getAuthScopeRolesVal() + ", authScopeDepartsVal=" + this.getAuthScopeDepartsVal() + ", subTable=" + this.getSubTable() + ", subTitle=" + this.getSubTitle() + ", subKey=" + this.getSubKey() + ", status=" + this.getStatus() + ", createBy=" + this.getCreateBy() + ", createTime=" + this.getCreateTime() + ", updateBy=" + this.getUpdateBy() + ", updateTime=" + this.getUpdateTime() + ")";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof DesignFormAuth)) {
            return false;
        } else {
            DesignFormAuth var2 = (DesignFormAuth)o;
            if (!var2.canEqual(this)) {
                return false;
            } else {
                label263: {
                    Boolean var3 = this.getSubTable();
                    Boolean var4 = var2.getSubTable();
                    if (var3 == null) {
                        if (var4 == null) {
                            break label263;
                        }
                    } else if (var3.equals(var4)) {
                        break label263;
                    }

                    return false;
                }

                Integer var5 = this.getStatus();
                Integer var6 = var2.getStatus();
                if (var5 == null) {
                    if (var6 != null) {
                        return false;
                    }
                } else if (!var5.equals(var6)) {
                    return false;
                }

                label249: {
                    String var7 = this.getId();
                    String var8 = var2.getId();
                    if (var7 == null) {
                        if (var8 == null) {
                            break label249;
                        }
                    } else if (var7.equals(var8)) {
                        break label249;
                    }

                    return false;
                }

                String var9 = this.getDesformId();
                String var10 = var2.getDesformId();
                if (var9 == null) {
                    if (var10 != null) {
                        return false;
                    }
                } else if (!var9.equals(var10)) {
                    return false;
                }

                label235: {
                    String var11 = this.getDesformCode();
                    String var12 = var2.getDesformCode();
                    if (var11 == null) {
                        if (var12 == null) {
                            break label235;
                        }
                    } else if (var11.equals(var12)) {
                        break label235;
                    }

                    return false;
                }

                String var13 = this.getPermissionType();
                String var14 = var2.getPermissionType();
                if (var13 == null) {
                    if (var14 != null) {
                        return false;
                    }
                } else if (!var13.equals(var14)) {
                    return false;
                }

                label221: {
                    String var15 = this.getAuthComKey();
                    String var16 = var2.getAuthComKey();
                    if (var15 == null) {
                        if (var16 == null) {
                            break label221;
                        }
                    } else if (var15.equals(var16)) {
                        break label221;
                    }

                    return false;
                }

                label214: {
                    String var17 = this.getAuthTitle();
                    String var18 = var2.getAuthTitle();
                    if (var17 == null) {
                        if (var18 == null) {
                            break label214;
                        }
                    } else if (var17.equals(var18)) {
                        break label214;
                    }

                    return false;
                }

                String var19 = this.getAuthField();
                String var20 = var2.getAuthField();
                if (var19 == null) {
                    if (var20 != null) {
                        return false;
                    }
                } else if (!var19.equals(var20)) {
                    return false;
                }

                label200: {
                    String var21 = this.getAuthType();
                    String var22 = var2.getAuthType();
                    if (var21 == null) {
                        if (var22 == null) {
                            break label200;
                        }
                    } else if (var21.equals(var22)) {
                        break label200;
                    }

                    return false;
                }

                label193: {
                    String var23 = this.getAuthValue();
                    String var24 = var2.getAuthValue();
                    if (var23 == null) {
                        if (var24 == null) {
                            break label193;
                        }
                    } else if (var23.equals(var24)) {
                        break label193;
                    }

                    return false;
                }

                String var25 = this.getAuthScopeIsAll();
                String var26 = var2.getAuthScopeIsAll();
                if (var25 == null) {
                    if (var26 != null) {
                        return false;
                    }
                } else if (!var25.equals(var26)) {
                    return false;
                }

                String var27 = this.getAuthScopeUsersVal();
                String var28 = var2.getAuthScopeUsersVal();
                if (var27 == null) {
                    if (var28 != null) {
                        return false;
                    }
                } else if (!var27.equals(var28)) {
                    return false;
                }

                label172: {
                    String var29 = this.getAuthScopeRolesVal();
                    String var30 = var2.getAuthScopeRolesVal();
                    if (var29 == null) {
                        if (var30 == null) {
                            break label172;
                        }
                    } else if (var29.equals(var30)) {
                        break label172;
                    }

                    return false;
                }

                String var31 = this.getAuthScopeDepartsVal();
                String var32 = var2.getAuthScopeDepartsVal();
                if (var31 == null) {
                    if (var32 != null) {
                        return false;
                    }
                } else if (!var31.equals(var32)) {
                    return false;
                }

                String var33 = this.getSubTitle();
                String var34 = var2.getSubTitle();
                if (var33 == null) {
                    if (var34 != null) {
                        return false;
                    }
                } else if (!var33.equals(var34)) {
                    return false;
                }

                label151: {
                    String var35 = this.getSubKey();
                    String var36 = var2.getSubKey();
                    if (var35 == null) {
                        if (var36 == null) {
                            break label151;
                        }
                    } else if (var35.equals(var36)) {
                        break label151;
                    }

                    return false;
                }

                String var37 = this.getCreateBy();
                String var38 = var2.getCreateBy();
                if (var37 == null) {
                    if (var38 != null) {
                        return false;
                    }
                } else if (!var37.equals(var38)) {
                    return false;
                }

                label137: {
                    Date var39 = this.getCreateTime();
                    Date var40 = var2.getCreateTime();
                    if (var39 == null) {
                        if (var40 == null) {
                            break label137;
                        }
                    } else if (var39.equals(var40)) {
                        break label137;
                    }

                    return false;
                }

                String var41 = this.getUpdateBy();
                String var42 = var2.getUpdateBy();
                if (var41 == null) {
                    if (var42 != null) {
                        return false;
                    }
                } else if (!var41.equals(var42)) {
                    return false;
                }

                Date var43 = this.getUpdateTime();
                Date var44 = var2.getUpdateTime();
                if (var43 == null) {
                    if (var44 == null) {
                        return true;
                    }
                } else if (var43.equals(var44)) {
                    return true;
                }

                return false;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof DesignFormAuth;
    }

    public int hashCode() {
        boolean var1 = true;
        int var2 = 1;
        Boolean var3 = this.getSubTable();
        var2 = var2 * 59 + (var3 == null ? 43 : var3.hashCode());
        Integer var4 = this.getStatus();
        var2 = var2 * 59 + (var4 == null ? 43 : var4.hashCode());
        String var5 = this.getId();
        var2 = var2 * 59 + (var5 == null ? 43 : var5.hashCode());
        String var6 = this.getDesformId();
        var2 = var2 * 59 + (var6 == null ? 43 : var6.hashCode());
        String var7 = this.getDesformCode();
        var2 = var2 * 59 + (var7 == null ? 43 : var7.hashCode());
        String var8 = this.getPermissionType();
        var2 = var2 * 59 + (var8 == null ? 43 : var8.hashCode());
        String var9 = this.getAuthComKey();
        var2 = var2 * 59 + (var9 == null ? 43 : var9.hashCode());
        String var10 = this.getAuthTitle();
        var2 = var2 * 59 + (var10 == null ? 43 : var10.hashCode());
        String var11 = this.getAuthField();
        var2 = var2 * 59 + (var11 == null ? 43 : var11.hashCode());
        String var12 = this.getAuthType();
        var2 = var2 * 59 + (var12 == null ? 43 : var12.hashCode());
        String var13 = this.getAuthValue();
        var2 = var2 * 59 + (var13 == null ? 43 : var13.hashCode());
        String var14 = this.getAuthScopeIsAll();
        var2 = var2 * 59 + (var14 == null ? 43 : var14.hashCode());
        String var15 = this.getAuthScopeUsersVal();
        var2 = var2 * 59 + (var15 == null ? 43 : var15.hashCode());
        String var16 = this.getAuthScopeRolesVal();
        var2 = var2 * 59 + (var16 == null ? 43 : var16.hashCode());
        String var17 = this.getAuthScopeDepartsVal();
        var2 = var2 * 59 + (var17 == null ? 43 : var17.hashCode());
        String var18 = this.getSubTitle();
        var2 = var2 * 59 + (var18 == null ? 43 : var18.hashCode());
        String var19 = this.getSubKey();
        var2 = var2 * 59 + (var19 == null ? 43 : var19.hashCode());
        String var20 = this.getCreateBy();
        var2 = var2 * 59 + (var20 == null ? 43 : var20.hashCode());
        Date var21 = this.getCreateTime();
        var2 = var2 * 59 + (var21 == null ? 43 : var21.hashCode());
        String var22 = this.getUpdateBy();
        var2 = var2 * 59 + (var22 == null ? 43 : var22.hashCode());
        Date var23 = this.getUpdateTime();
        var2 = var2 * 59 + (var23 == null ? 43 : var23.hashCode());
        return var2;
    }
}
