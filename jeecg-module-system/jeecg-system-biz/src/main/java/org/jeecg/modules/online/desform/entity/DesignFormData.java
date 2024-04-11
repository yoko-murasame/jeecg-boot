//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jeecg.modules.online.desform.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@TableName("design_form_data")
public class DesignFormData implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(
            type = IdType.ASSIGN_ID
    )
    private String id;
    private String desformId;
    @Excel(
            name = "表单设计编码",
            width = 15.0
    )
    private String desformCode;
    @Excel(
            name = "表单设计名称",
            width = 15.0
    )
    private String desformName;
    @Excel(
            name = "表单数据JSON",
            width = 15.0
    )
    private String desformDataJson;
    @Excel(
            name = "Online表单的Code",
            width = 15.0
    )
    private String onlineFormCode;
    @Excel(
            name = "Online数据表中的id，用于修改",
            width = 15.0
    )
    private String onlineFormDataId;
    @Excel(
            name = "创建人",
            width = 15.0
    )
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
    private Date createTime;
    @Excel(
            name = "修改人",
            width = 15.0
    )
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
    private Date updateTime;

    public DesignFormData() {
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

    public String getDesformName() {
        return this.desformName;
    }

    public String getDesformDataJson() {
        return this.desformDataJson;
    }

    public String getOnlineFormCode() {
        return this.onlineFormCode;
    }

    public String getOnlineFormDataId() {
        return this.onlineFormDataId;
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

    public void setId(String id) {
        this.id = id;
    }

    public void setDesformId(String desformId) {
        this.desformId = desformId;
    }

    public void setDesformCode(String desformCode) {
        this.desformCode = desformCode;
    }

    public void setDesformName(String desformName) {
        this.desformName = desformName;
    }

    public void setDesformDataJson(String desformDataJson) {
        this.desformDataJson = desformDataJson;
    }

    public void setOnlineFormCode(String onlineFormCode) {
        this.onlineFormCode = onlineFormCode;
    }

    public void setOnlineFormDataId(String onlineFormDataId) {
        this.onlineFormDataId = onlineFormDataId;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    @JsonFormat(
            timezone = "GMT+8",
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    @JsonFormat(
            timezone = "GMT+8",
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof DesignFormData)) {
            return false;
        } else {
            DesignFormData var2 = (DesignFormData)o;
            if (!var2.canEqual(this)) {
                return false;
            } else {
                label143: {
                    String var3 = this.getId();
                    String var4 = var2.getId();
                    if (var3 == null) {
                        if (var4 == null) {
                            break label143;
                        }
                    } else if (var3.equals(var4)) {
                        break label143;
                    }

                    return false;
                }

                String var5 = this.getDesformId();
                String var6 = var2.getDesformId();
                if (var5 == null) {
                    if (var6 != null) {
                        return false;
                    }
                } else if (!var5.equals(var6)) {
                    return false;
                }

                String var7 = this.getDesformCode();
                String var8 = var2.getDesformCode();
                if (var7 == null) {
                    if (var8 != null) {
                        return false;
                    }
                } else if (!var7.equals(var8)) {
                    return false;
                }

                label122: {
                    String var9 = this.getDesformName();
                    String var10 = var2.getDesformName();
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
                    String var11 = this.getDesformDataJson();
                    String var12 = var2.getDesformDataJson();
                    if (var11 == null) {
                        if (var12 == null) {
                            break label115;
                        }
                    } else if (var11.equals(var12)) {
                        break label115;
                    }

                    return false;
                }

                String var13 = this.getOnlineFormCode();
                String var14 = var2.getOnlineFormCode();
                if (var13 == null) {
                    if (var14 != null) {
                        return false;
                    }
                } else if (!var13.equals(var14)) {
                    return false;
                }

                String var15 = this.getOnlineFormDataId();
                String var16 = var2.getOnlineFormDataId();
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
        return other instanceof DesignFormData;
    }

    public int hashCode() {
        boolean var1 = true;
        int var2 = 1;
        String var3 = this.getId();
        var2 = var2 * 59 + (var3 == null ? 43 : var3.hashCode());
        String var4 = this.getDesformId();
        var2 = var2 * 59 + (var4 == null ? 43 : var4.hashCode());
        String var5 = this.getDesformCode();
        var2 = var2 * 59 + (var5 == null ? 43 : var5.hashCode());
        String var6 = this.getDesformName();
        var2 = var2 * 59 + (var6 == null ? 43 : var6.hashCode());
        String var7 = this.getDesformDataJson();
        var2 = var2 * 59 + (var7 == null ? 43 : var7.hashCode());
        String var8 = this.getOnlineFormCode();
        var2 = var2 * 59 + (var8 == null ? 43 : var8.hashCode());
        String var9 = this.getOnlineFormDataId();
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

    public String toString() {
        return "DesignFormData(id=" + this.getId() + ", desformId=" + this.getDesformId() + ", desformCode=" + this.getDesformCode() + ", desformName=" + this.getDesformName() + ", desformDataJson=" + this.getDesformDataJson() + ", onlineFormCode=" + this.getOnlineFormCode() + ", onlineFormDataId=" + this.getOnlineFormDataId() + ", createBy=" + this.getCreateBy() + ", createTime=" + this.getCreateTime() + ", updateBy=" + this.getUpdateBy() + ", updateTime=" + this.getUpdateTime() + ")";
    }
}
