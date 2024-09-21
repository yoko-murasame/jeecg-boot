package org.jeecg.modules.online.graphreport.page;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.jeecg.modules.online.graphreport.entity.OnlGraphreportTempletItem;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/* renamed from: org.jeecg.modules.online.graphreport.vo.OnlGraphreportTempletPage */
/* loaded from: hibernate-common-ol-5.4.74.jar:org/jeecg/modules/online/graphreport/vo/OnlGraphreportTempletPage.class */
public class OnlGraphreportTempletPage {
    private String id;
    @Excel(name = "templetCode", width = 15.0d)
    private String templetCode;
    @Excel(name = "报表名称", width = 15.0d)
    private String templetName;
    @Excel(name = "报表风格模板（单排、双排、Tab模式、分组模式-根据配置动态展示、可自定义...）", width = 15.0d)
    private String templetStyle;
    @Excel(name = "createTime", width = 20.0d, format = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @Excel(name = "createBy", width = 15.0d)
    private String createBy;
    @Excel(name = "updateTime", width = 20.0d, format = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    @Excel(name = "updateBy", width = 15.0d)
    private String updateBy;
    @ExcelCollection(name = "Online报表多数据源次表")
    private List<OnlGraphreportTempletItem> onlGraphreportTempletItemList;

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

    public void setOnlGraphreportTempletItemList(List<OnlGraphreportTempletItem> onlGraphreportTempletItemList) {
        this.onlGraphreportTempletItemList = onlGraphreportTempletItemList;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof OnlGraphreportTempletPage)) {
            return false;
        }
        OnlGraphreportTempletPage onlGraphreportTempletPage = (OnlGraphreportTempletPage) o;
        if (!onlGraphreportTempletPage.canEqual(this)) {
            return false;
        }
        String id = getId();
        String id2 = onlGraphreportTempletPage.getId();
        if (id == null) {
            if (id2 != null) {
                return false;
            }
        } else if (!id.equals(id2)) {
            return false;
        }
        String templetCode = getTempletCode();
        String templetCode2 = onlGraphreportTempletPage.getTempletCode();
        if (templetCode == null) {
            if (templetCode2 != null) {
                return false;
            }
        } else if (!templetCode.equals(templetCode2)) {
            return false;
        }
        String templetName = getTempletName();
        String templetName2 = onlGraphreportTempletPage.getTempletName();
        if (templetName == null) {
            if (templetName2 != null) {
                return false;
            }
        } else if (!templetName.equals(templetName2)) {
            return false;
        }
        String templetStyle = getTempletStyle();
        String templetStyle2 = onlGraphreportTempletPage.getTempletStyle();
        if (templetStyle == null) {
            if (templetStyle2 != null) {
                return false;
            }
        } else if (!templetStyle.equals(templetStyle2)) {
            return false;
        }
        Date createTime = getCreateTime();
        Date createTime2 = onlGraphreportTempletPage.getCreateTime();
        if (createTime == null) {
            if (createTime2 != null) {
                return false;
            }
        } else if (!createTime.equals(createTime2)) {
            return false;
        }
        String createBy = getCreateBy();
        String createBy2 = onlGraphreportTempletPage.getCreateBy();
        if (createBy == null) {
            if (createBy2 != null) {
                return false;
            }
        } else if (!createBy.equals(createBy2)) {
            return false;
        }
        Date updateTime = getUpdateTime();
        Date updateTime2 = onlGraphreportTempletPage.getUpdateTime();
        if (updateTime == null) {
            if (updateTime2 != null) {
                return false;
            }
        } else if (!updateTime.equals(updateTime2)) {
            return false;
        }
        String updateBy = getUpdateBy();
        String updateBy2 = onlGraphreportTempletPage.getUpdateBy();
        if (updateBy == null) {
            if (updateBy2 != null) {
                return false;
            }
        } else if (!updateBy.equals(updateBy2)) {
            return false;
        }
        List<OnlGraphreportTempletItem> onlGraphreportTempletItemList = getOnlGraphreportTempletItemList();
        List<OnlGraphreportTempletItem> onlGraphreportTempletItemList2 = onlGraphreportTempletPage.getOnlGraphreportTempletItemList();
        return onlGraphreportTempletItemList == null ? onlGraphreportTempletItemList2 == null : onlGraphreportTempletItemList.equals(onlGraphreportTempletItemList2);
    }

    protected boolean canEqual(Object other) {
        return other instanceof OnlGraphreportTempletPage;
    }

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
        int hashCode8 = (hashCode7 * 59) + (updateBy == null ? 43 : updateBy.hashCode());
        List<OnlGraphreportTempletItem> onlGraphreportTempletItemList = getOnlGraphreportTempletItemList();
        return (hashCode8 * 59) + (onlGraphreportTempletItemList == null ? 43 : onlGraphreportTempletItemList.hashCode());
    }

    public String toString() {
        return "OnlGraphreportTempletPage(id=" + getId() + ", templetCode=" + getTempletCode() + ", templetName=" + getTempletName() + ", templetStyle=" + getTempletStyle() + ", createTime=" + getCreateTime() + ", createBy=" + getCreateBy() + ", updateTime=" + getUpdateTime() + ", updateBy=" + getUpdateBy() + ", onlGraphreportTempletItemList=" + getOnlGraphreportTempletItemList() + ")";
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

    public List<OnlGraphreportTempletItem> getOnlGraphreportTempletItemList() {
        return this.onlGraphreportTempletItemList;
    }
}
