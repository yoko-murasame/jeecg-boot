package org.jeecg.modules.online.graphreport.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

@TableName("onl_graphreport_templet")
/* loaded from: hibernate-common-ol-5.4.74.jar:org/jeecg/modules/online/graphreport/entity/OnlGraphreportTemplet.class */
public class OnlGraphreportTemplet implements Serializable {
    private static final long serialVersionUID = 1;
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    private String templetCode;
    private String templetName;
    private String templetStyle;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private String createBy;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    private String updateBy;

    public void setId(String id) {
        this.id = id;
    }

    public void setTempletCode(String templetCode) {
        this.templetCode = templetCode;
    }

    public void setTempletName(String templetName) {
        this.templetName = templetName;
    }

    public void setTempletStyle(String templetStyle) {
        this.templetStyle = templetStyle;
    }

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    @Override // java.lang.Object
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof OnlGraphreportTemplet)) {
            return false;
        }
        OnlGraphreportTemplet onlGraphreportTemplet = (OnlGraphreportTemplet) o;
        if (!onlGraphreportTemplet.canEqual(this)) {
            return false;
        }
        String id = getId();
        String id2 = onlGraphreportTemplet.getId();
        if (id == null) {
            if (id2 != null) {
                return false;
            }
        } else if (!id.equals(id2)) {
            return false;
        }
        String templetCode = getTempletCode();
        String templetCode2 = onlGraphreportTemplet.getTempletCode();
        if (templetCode == null) {
            if (templetCode2 != null) {
                return false;
            }
        } else if (!templetCode.equals(templetCode2)) {
            return false;
        }
        String templetName = getTempletName();
        String templetName2 = onlGraphreportTemplet.getTempletName();
        if (templetName == null) {
            if (templetName2 != null) {
                return false;
            }
        } else if (!templetName.equals(templetName2)) {
            return false;
        }
        String templetStyle = getTempletStyle();
        String templetStyle2 = onlGraphreportTemplet.getTempletStyle();
        if (templetStyle == null) {
            if (templetStyle2 != null) {
                return false;
            }
        } else if (!templetStyle.equals(templetStyle2)) {
            return false;
        }
        Date createTime = getCreateTime();
        Date createTime2 = onlGraphreportTemplet.getCreateTime();
        if (createTime == null) {
            if (createTime2 != null) {
                return false;
            }
        } else if (!createTime.equals(createTime2)) {
            return false;
        }
        String createBy = getCreateBy();
        String createBy2 = onlGraphreportTemplet.getCreateBy();
        if (createBy == null) {
            if (createBy2 != null) {
                return false;
            }
        } else if (!createBy.equals(createBy2)) {
            return false;
        }
        Date updateTime = getUpdateTime();
        Date updateTime2 = onlGraphreportTemplet.getUpdateTime();
        if (updateTime == null) {
            if (updateTime2 != null) {
                return false;
            }
        } else if (!updateTime.equals(updateTime2)) {
            return false;
        }
        String updateBy = getUpdateBy();
        String updateBy2 = onlGraphreportTemplet.getUpdateBy();
        return updateBy == null ? updateBy2 == null : updateBy.equals(updateBy2);
    }

    protected boolean canEqual(Object other) {
        return other instanceof OnlGraphreportTemplet;
    }

    @Override // java.lang.Object
    public int hashCode() {
        String id = getId();
        int hashCode = (1 * 59) + (id == null ? 43 : id.hashCode());
        String templetCode = getTempletCode();
        int hashCode2 = (hashCode * 59) + (templetCode == null ? 43 : templetCode.hashCode());
        String templetName = getTempletName();
        int hashCode3 = (hashCode2 * 59) + (templetName == null ? 43 : templetName.hashCode());
        String templetStyle = getTempletStyle();
        int hashCode4 = (hashCode3 * 59) + (templetStyle == null ? 43 : templetStyle.hashCode());
        Date createTime = getCreateTime();
        int hashCode5 = (hashCode4 * 59) + (createTime == null ? 43 : createTime.hashCode());
        String createBy = getCreateBy();
        int hashCode6 = (hashCode5 * 59) + (createBy == null ? 43 : createBy.hashCode());
        Date updateTime = getUpdateTime();
        int hashCode7 = (hashCode6 * 59) + (updateTime == null ? 43 : updateTime.hashCode());
        String updateBy = getUpdateBy();
        return (hashCode7 * 59) + (updateBy == null ? 43 : updateBy.hashCode());
    }

    @Override // java.lang.Object
    public String toString() {
        return "OnlGraphreportTemplet(id=" + getId() + ", templetCode=" + getTempletCode() + ", templetName=" + getTempletName() + ", templetStyle=" + getTempletStyle() + ", createTime=" + getCreateTime() + ", createBy=" + getCreateBy() + ", updateTime=" + getUpdateTime() + ", updateBy=" + getUpdateBy() + ")";
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

    public String getTempletStyle() {
        return this.templetStyle;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public String getCreateBy() {
        return this.createBy;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public String getUpdateBy() {
        return this.updateBy;
    }
}
