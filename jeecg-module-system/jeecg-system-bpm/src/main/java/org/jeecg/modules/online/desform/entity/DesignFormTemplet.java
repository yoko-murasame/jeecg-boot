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

@TableName("design_form_templet")
@ApiModel(
        value = "design_form_templet对象",
        description = "表单设计器模板表"
)
public class DesignFormTemplet {
    @TableId(
            type = IdType.ASSIGN_ID
    )
    @ApiModelProperty("ID")
    private String id;
    @Excel(
            name = "模板编码",
            width = 15.0
    )
    @ApiModelProperty("模板编码")
    private String templetCode;
    @Excel(
            name = "模板名称",
            width = 15.0
    )
    @ApiModelProperty("模板名称")
    private String templetName;
    @Excel(
            name = "模板JSON",
            width = 15.0
    )
    @ApiModelProperty("模板JSON")
    private String templetJson;
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

    public DesignFormTemplet() {
    }

    public String getId() {
        return this.id;
    }

    public String getTempletCode() {
        return this.templetCode;
    }

    public String getTempletName() {
        return this.templetName;
    }

    public String getTempletJson() {
        return this.templetJson;
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

    public DesignFormTemplet setId(String id) {
        this.id = id;
        return this;
    }

    public DesignFormTemplet setTempletCode(String templetCode) {
        this.templetCode = templetCode;
        return this;
    }

    public DesignFormTemplet setTempletName(String templetName) {
        this.templetName = templetName;
        return this;
    }

    public DesignFormTemplet setTempletJson(String templetJson) {
        this.templetJson = templetJson;
        return this;
    }

    public DesignFormTemplet setCreateBy(String createBy) {
        this.createBy = createBy;
        return this;
    }

    @JsonFormat(
            timezone = "GMT+8",
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    public DesignFormTemplet setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public DesignFormTemplet setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
        return this;
    }

    @JsonFormat(
            timezone = "GMT+8",
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    public DesignFormTemplet setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public String toString() {
        return "DesignFormTemplet(id=" + this.getId() + ", templetCode=" + this.getTempletCode() + ", templetName=" + this.getTempletName() + ", templetJson=" + this.getTempletJson() + ", createBy=" + this.getCreateBy() + ", createTime=" + this.getCreateTime() + ", updateBy=" + this.getUpdateBy() + ", updateTime=" + this.getUpdateTime() + ")";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof DesignFormTemplet)) {
            return false;
        } else {
            DesignFormTemplet var2 = (DesignFormTemplet)o;
            if (!var2.canEqual(this)) {
                return false;
            } else {
                label107: {
                    String var3 = this.getId();
                    String var4 = var2.getId();
                    if (var3 == null) {
                        if (var4 == null) {
                            break label107;
                        }
                    } else if (var3.equals(var4)) {
                        break label107;
                    }

                    return false;
                }

                String var5 = this.getTempletCode();
                String var6 = var2.getTempletCode();
                if (var5 == null) {
                    if (var6 != null) {
                        return false;
                    }
                } else if (!var5.equals(var6)) {
                    return false;
                }

                String var7 = this.getTempletName();
                String var8 = var2.getTempletName();
                if (var7 == null) {
                    if (var8 != null) {
                        return false;
                    }
                } else if (!var7.equals(var8)) {
                    return false;
                }

                label86: {
                    String var9 = this.getTempletJson();
                    String var10 = var2.getTempletJson();
                    if (var9 == null) {
                        if (var10 == null) {
                            break label86;
                        }
                    } else if (var9.equals(var10)) {
                        break label86;
                    }

                    return false;
                }

                label79: {
                    String var11 = this.getCreateBy();
                    String var12 = var2.getCreateBy();
                    if (var11 == null) {
                        if (var12 == null) {
                            break label79;
                        }
                    } else if (var11.equals(var12)) {
                        break label79;
                    }

                    return false;
                }

                label72: {
                    Date var13 = this.getCreateTime();
                    Date var14 = var2.getCreateTime();
                    if (var13 == null) {
                        if (var14 == null) {
                            break label72;
                        }
                    } else if (var13.equals(var14)) {
                        break label72;
                    }

                    return false;
                }

                String var15 = this.getUpdateBy();
                String var16 = var2.getUpdateBy();
                if (var15 == null) {
                    if (var16 != null) {
                        return false;
                    }
                } else if (!var15.equals(var16)) {
                    return false;
                }

                Date var17 = this.getUpdateTime();
                Date var18 = var2.getUpdateTime();
                if (var17 == null) {
                    if (var18 != null) {
                        return false;
                    }
                } else if (!var17.equals(var18)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof DesignFormTemplet;
    }

    public int hashCode() {
        boolean var1 = true;
        int var2 = 1;
        String var3 = this.getId();
        var2 = var2 * 59 + (var3 == null ? 43 : var3.hashCode());
        String var4 = this.getTempletCode();
        var2 = var2 * 59 + (var4 == null ? 43 : var4.hashCode());
        String var5 = this.getTempletName();
        var2 = var2 * 59 + (var5 == null ? 43 : var5.hashCode());
        String var6 = this.getTempletJson();
        var2 = var2 * 59 + (var6 == null ? 43 : var6.hashCode());
        String var7 = this.getCreateBy();
        var2 = var2 * 59 + (var7 == null ? 43 : var7.hashCode());
        Date var8 = this.getCreateTime();
        var2 = var2 * 59 + (var8 == null ? 43 : var8.hashCode());
        String var9 = this.getUpdateBy();
        var2 = var2 * 59 + (var9 == null ? 43 : var9.hashCode());
        Date var10 = this.getUpdateTime();
        var2 = var2 * 59 + (var10 == null ? 43 : var10.hashCode());
        return var2;
    }
}
