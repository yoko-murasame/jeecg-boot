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

@TableName("design_form_url_auth")
@ApiModel(
        value = "design_form_url_auth对象",
        description = "表单设计器url授权表"
)
public class DesignFormUrlAuth {
    @Excel(
            name = "id",
            width = 15.0
    )
    @ApiModelProperty("id")
    @TableId(
            type = IdType.ASSIGN_ID
    )
    private String id;
    @Excel(
            name = "表单ID",
            width = 15.0
    )
    @ApiModelProperty("表单ID")
    private String desformId;
    @Excel(
            name = "表单CODE",
            width = 15.0
    )
    @ApiModelProperty("表单CODE")
    private String desformCode;
    @Excel(
            name = "链接类型",
            width = 15.0
    )
    @ApiModelProperty("链接类型")
    private String urlType;
    @Excel(
            name = "链接状态（1=有效，2=无效）",
            width = 15.0
    )
    @ApiModelProperty("链接状态（1=有效，2=无效）")
    private Integer urlStatus;
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

    public DesignFormUrlAuth() {
    }

    public DesignFormUrlAuth(String desformCode) {
        this.desformCode = desformCode;
    }

    public DesignFormUrlAuth(String desformId, String desformCode, String urlType) {
        this.desformId = desformId;
        this.desformCode = desformCode;
        this.urlType = urlType;
        this.urlStatus = 1;
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

    public String getUrlType() {
        return this.urlType;
    }

    public Integer getUrlStatus() {
        return this.urlStatus;
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

    public DesignFormUrlAuth setId(String id) {
        this.id = id;
        return this;
    }

    public DesignFormUrlAuth setDesformId(String desformId) {
        this.desformId = desformId;
        return this;
    }

    public DesignFormUrlAuth setDesformCode(String desformCode) {
        this.desformCode = desformCode;
        return this;
    }

    public DesignFormUrlAuth setUrlType(String urlType) {
        this.urlType = urlType;
        return this;
    }

    public DesignFormUrlAuth setUrlStatus(Integer urlStatus) {
        this.urlStatus = urlStatus;
        return this;
    }

    public DesignFormUrlAuth setCreateBy(String createBy) {
        this.createBy = createBy;
        return this;
    }

    @JsonFormat(
            timezone = "GMT+8",
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    public DesignFormUrlAuth setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public DesignFormUrlAuth setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
        return this;
    }

    @JsonFormat(
            timezone = "GMT+8",
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    public DesignFormUrlAuth setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public String toString() {
        return "DesignFormUrlAuth(id=" + this.getId() + ", desformId=" + this.getDesformId() + ", desformCode=" + this.getDesformCode() + ", urlType=" + this.getUrlType() + ", urlStatus=" + this.getUrlStatus() + ", createBy=" + this.getCreateBy() + ", createTime=" + this.getCreateTime() + ", updateBy=" + this.getUpdateBy() + ", updateTime=" + this.getUpdateTime() + ")";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof DesignFormUrlAuth)) {
            return false;
        } else {
            DesignFormUrlAuth var2 = (DesignFormUrlAuth)o;
            if (!var2.canEqual(this)) {
                return false;
            } else {
                label119: {
                    Integer var3 = this.getUrlStatus();
                    Integer var4 = var2.getUrlStatus();
                    if (var3 == null) {
                        if (var4 == null) {
                            break label119;
                        }
                    } else if (var3.equals(var4)) {
                        break label119;
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

                label105: {
                    String var7 = this.getDesformId();
                    String var8 = var2.getDesformId();
                    if (var7 == null) {
                        if (var8 == null) {
                            break label105;
                        }
                    } else if (var7.equals(var8)) {
                        break label105;
                    }

                    return false;
                }

                String var9 = this.getDesformCode();
                String var10 = var2.getDesformCode();
                if (var9 == null) {
                    if (var10 != null) {
                        return false;
                    }
                } else if (!var9.equals(var10)) {
                    return false;
                }

                label91: {
                    String var11 = this.getUrlType();
                    String var12 = var2.getUrlType();
                    if (var11 == null) {
                        if (var12 == null) {
                            break label91;
                        }
                    } else if (var11.equals(var12)) {
                        break label91;
                    }

                    return false;
                }

                String var13 = this.getCreateBy();
                String var14 = var2.getCreateBy();
                if (var13 == null) {
                    if (var14 != null) {
                        return false;
                    }
                } else if (!var13.equals(var14)) {
                    return false;
                }

                label77: {
                    Date var15 = this.getCreateTime();
                    Date var16 = var2.getCreateTime();
                    if (var15 == null) {
                        if (var16 == null) {
                            break label77;
                        }
                    } else if (var15.equals(var16)) {
                        break label77;
                    }

                    return false;
                }

                label70: {
                    String var17 = this.getUpdateBy();
                    String var18 = var2.getUpdateBy();
                    if (var17 == null) {
                        if (var18 == null) {
                            break label70;
                        }
                    } else if (var17.equals(var18)) {
                        break label70;
                    }

                    return false;
                }

                Date var19 = this.getUpdateTime();
                Date var20 = var2.getUpdateTime();
                if (var19 == null) {
                    if (var20 != null) {
                        return false;
                    }
                } else if (!var19.equals(var20)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof DesignFormUrlAuth;
    }

    public int hashCode() {
        boolean var1 = true;
        int var2 = 1;
        Integer var3 = this.getUrlStatus();
        var2 = var2 * 59 + (var3 == null ? 43 : var3.hashCode());
        String var4 = this.getId();
        var2 = var2 * 59 + (var4 == null ? 43 : var4.hashCode());
        String var5 = this.getDesformId();
        var2 = var2 * 59 + (var5 == null ? 43 : var5.hashCode());
        String var6 = this.getDesformCode();
        var2 = var2 * 59 + (var6 == null ? 43 : var6.hashCode());
        String var7 = this.getUrlType();
        var2 = var2 * 59 + (var7 == null ? 43 : var7.hashCode());
        String var8 = this.getCreateBy();
        var2 = var2 * 59 + (var8 == null ? 43 : var8.hashCode());
        Date var9 = this.getCreateTime();
        var2 = var2 * 59 + (var9 == null ? 43 : var9.hashCode());
        String var10 = this.getUpdateBy();
        var2 = var2 * 59 + (var10 == null ? 43 : var10.hashCode());
        Date var11 = this.getUpdateTime();
        var2 = var2 * 59 + (var11 == null ? 43 : var11.hashCode());
        return var2;
    }
}
