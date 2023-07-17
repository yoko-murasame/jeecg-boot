//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jeecg.modules.online.desform.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@TableName("design_form")
public class DesignForm implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(
            type = IdType.ASSIGN_ID
    )
    private String id;
    private String desformCode;
    private String desformName;
    private String desformIcon;
    private String desformDesignJson;
    private String cgformCode;
    private String parentId;
    private String parentCode;
    private Integer desformType;
    private Boolean izOaShow;
    private Integer izMobileView;
    private String createBy;
    @JsonFormat(
            timezone = "GMT+8",
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private Date createTime;
    private String updateBy;
    @JsonFormat(
            timezone = "GMT+8",
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private Date updateTime;

    public DesignForm() {
    }

    public String getId() {
        return this.id;
    }

    public String getDesformCode() {
        return this.desformCode;
    }

    public String getDesformName() {
        return this.desformName;
    }

    public String getDesformIcon() {
        return this.desformIcon;
    }

    public String getDesformDesignJson() {
        return this.desformDesignJson;
    }

    public String getCgformCode() {
        return this.cgformCode;
    }

    public String getParentId() {
        return this.parentId;
    }

    public String getParentCode() {
        return this.parentCode;
    }

    public Integer getDesformType() {
        return this.desformType;
    }

    public Boolean getIzOaShow() {
        return this.izOaShow;
    }

    public Integer getIzMobileView() {
        return this.izMobileView;
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

    public void setDesformCode(String desformCode) {
        this.desformCode = desformCode;
    }

    public void setDesformName(String desformName) {
        this.desformName = desformName;
    }

    public void setDesformIcon(String desformIcon) {
        this.desformIcon = desformIcon;
    }

    public void setDesformDesignJson(String desformDesignJson) {
        this.desformDesignJson = desformDesignJson;
    }

    public void setCgformCode(String cgformCode) {
        this.cgformCode = cgformCode;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public void setDesformType(Integer desformType) {
        this.desformType = desformType;
    }

    public void setIzOaShow(Boolean izOaShow) {
        this.izOaShow = izOaShow;
    }

    public void setIzMobileView(Integer izMobileView) {
        this.izMobileView = izMobileView;
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
        } else if (!(o instanceof DesignForm)) {
            return false;
        } else {
            DesignForm var2 = (DesignForm)o;
            if (!var2.canEqual(this)) {
                return false;
            } else {
                label191: {
                    Integer var3 = this.getDesformType();
                    Integer var4 = var2.getDesformType();
                    if (var3 == null) {
                        if (var4 == null) {
                            break label191;
                        }
                    } else if (var3.equals(var4)) {
                        break label191;
                    }

                    return false;
                }

                Boolean var5 = this.getIzOaShow();
                Boolean var6 = var2.getIzOaShow();
                if (var5 == null) {
                    if (var6 != null) {
                        return false;
                    }
                } else if (!var5.equals(var6)) {
                    return false;
                }

                Integer var7 = this.getIzMobileView();
                Integer var8 = var2.getIzMobileView();
                if (var7 == null) {
                    if (var8 != null) {
                        return false;
                    }
                } else if (!var7.equals(var8)) {
                    return false;
                }

                label170: {
                    String var9 = this.getId();
                    String var10 = var2.getId();
                    if (var9 == null) {
                        if (var10 == null) {
                            break label170;
                        }
                    } else if (var9.equals(var10)) {
                        break label170;
                    }

                    return false;
                }

                label163: {
                    String var11 = this.getDesformCode();
                    String var12 = var2.getDesformCode();
                    if (var11 == null) {
                        if (var12 == null) {
                            break label163;
                        }
                    } else if (var11.equals(var12)) {
                        break label163;
                    }

                    return false;
                }

                String var13 = this.getDesformName();
                String var14 = var2.getDesformName();
                if (var13 == null) {
                    if (var14 != null) {
                        return false;
                    }
                } else if (!var13.equals(var14)) {
                    return false;
                }

                String var15 = this.getDesformIcon();
                String var16 = var2.getDesformIcon();
                if (var15 == null) {
                    if (var16 != null) {
                        return false;
                    }
                } else if (!var15.equals(var16)) {
                    return false;
                }

                label142: {
                    String var17 = this.getDesformDesignJson();
                    String var18 = var2.getDesformDesignJson();
                    if (var17 == null) {
                        if (var18 == null) {
                            break label142;
                        }
                    } else if (var17.equals(var18)) {
                        break label142;
                    }

                    return false;
                }

                label135: {
                    String var19 = this.getCgformCode();
                    String var20 = var2.getCgformCode();
                    if (var19 == null) {
                        if (var20 == null) {
                            break label135;
                        }
                    } else if (var19.equals(var20)) {
                        break label135;
                    }

                    return false;
                }

                String var21 = this.getParentId();
                String var22 = var2.getParentId();
                if (var21 == null) {
                    if (var22 != null) {
                        return false;
                    }
                } else if (!var21.equals(var22)) {
                    return false;
                }

                label121: {
                    String var23 = this.getParentCode();
                    String var24 = var2.getParentCode();
                    if (var23 == null) {
                        if (var24 == null) {
                            break label121;
                        }
                    } else if (var23.equals(var24)) {
                        break label121;
                    }

                    return false;
                }

                String var25 = this.getCreateBy();
                String var26 = var2.getCreateBy();
                if (var25 == null) {
                    if (var26 != null) {
                        return false;
                    }
                } else if (!var25.equals(var26)) {
                    return false;
                }

                label107: {
                    Date var27 = this.getCreateTime();
                    Date var28 = var2.getCreateTime();
                    if (var27 == null) {
                        if (var28 == null) {
                            break label107;
                        }
                    } else if (var27.equals(var28)) {
                        break label107;
                    }

                    return false;
                }

                String var29 = this.getUpdateBy();
                String var30 = var2.getUpdateBy();
                if (var29 == null) {
                    if (var30 != null) {
                        return false;
                    }
                } else if (!var29.equals(var30)) {
                    return false;
                }

                Date var31 = this.getUpdateTime();
                Date var32 = var2.getUpdateTime();
                if (var31 == null) {
                    if (var32 != null) {
                        return false;
                    }
                } else if (!var31.equals(var32)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof DesignForm;
    }

    public int hashCode() {
        boolean var1 = true;
        int var2 = 1;
        Integer var3 = this.getDesformType();
        var2 = var2 * 59 + (var3 == null ? 43 : var3.hashCode());
        Boolean var4 = this.getIzOaShow();
        var2 = var2 * 59 + (var4 == null ? 43 : var4.hashCode());
        Integer var5 = this.getIzMobileView();
        var2 = var2 * 59 + (var5 == null ? 43 : var5.hashCode());
        String var6 = this.getId();
        var2 = var2 * 59 + (var6 == null ? 43 : var6.hashCode());
        String var7 = this.getDesformCode();
        var2 = var2 * 59 + (var7 == null ? 43 : var7.hashCode());
        String var8 = this.getDesformName();
        var2 = var2 * 59 + (var8 == null ? 43 : var8.hashCode());
        String var9 = this.getDesformIcon();
        var2 = var2 * 59 + (var9 == null ? 43 : var9.hashCode());
        String var10 = this.getDesformDesignJson();
        var2 = var2 * 59 + (var10 == null ? 43 : var10.hashCode());
        String var11 = this.getCgformCode();
        var2 = var2 * 59 + (var11 == null ? 43 : var11.hashCode());
        String var12 = this.getParentId();
        var2 = var2 * 59 + (var12 == null ? 43 : var12.hashCode());
        String var13 = this.getParentCode();
        var2 = var2 * 59 + (var13 == null ? 43 : var13.hashCode());
        String var14 = this.getCreateBy();
        var2 = var2 * 59 + (var14 == null ? 43 : var14.hashCode());
        Date var15 = this.getCreateTime();
        var2 = var2 * 59 + (var15 == null ? 43 : var15.hashCode());
        String var16 = this.getUpdateBy();
        var2 = var2 * 59 + (var16 == null ? 43 : var16.hashCode());
        Date var17 = this.getUpdateTime();
        var2 = var2 * 59 + (var17 == null ? 43 : var17.hashCode());
        return var2;
    }

    public String toString() {
        return "DesignForm(id=" + this.getId() + ", desformCode=" + this.getDesformCode() + ", desformName=" + this.getDesformName() + ", desformIcon=" + this.getDesformIcon() + ", desformDesignJson=" + this.getDesformDesignJson() + ", cgformCode=" + this.getCgformCode() + ", parentId=" + this.getParentId() + ", parentCode=" + this.getParentCode() + ", desformType=" + this.getDesformType() + ", izOaShow=" + this.getIzOaShow() + ", izMobileView=" + this.getIzMobileView() + ", createBy=" + this.getCreateBy() + ", createTime=" + this.getCreateTime() + ", updateBy=" + this.getUpdateBy() + ", updateTime=" + this.getUpdateTime() + ")";
    }
}
