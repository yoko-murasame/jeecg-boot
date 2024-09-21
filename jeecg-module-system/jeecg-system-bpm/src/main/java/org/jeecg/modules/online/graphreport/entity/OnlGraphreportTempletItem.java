package org.jeecg.modules.online.graphreport.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@TableName("onl_graphreport_templet_item")
/* loaded from: hibernate-common-ol-5.4.74.jar:org/jeecg/modules/online/graphreport/entity/OnlGraphreportTempletItem.class */
public class OnlGraphreportTempletItem implements Serializable {
    private static final long serialVersionUID = 1;
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    private String graphreportTempletId;
    @Excel(name = "图表编码", width = 15.0d)
    private String graphreportCode;
    @Excel(name = "图表类型（饼状图、曲线图、柱状图、数据列表等）", width = 15.0d)
    private String graphreportType;
    @Excel(name = "组合数字，默认值0 非必填", width = 15.0d)
    private Integer groupNum;
    @Excel(name = "组合展示风格（1 卡片，2 tab）非必填", width = 15.0d)
    private String groupStyle;
    @Excel(name = "分组描述", width = 15.0d)
    private String groupTxt;
    @Excel(name = "排序", width = 15.0d)
    private Integer orderNum;
    @Excel(name = "是否显示 1显示 0不显示，默认1", width = 15.0d)
    private String isShow;
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

    public void setId(String id) {
        this.id = id;
    }

    public void setGraphreportTempletId(String graphreportTempletId) {
        this.graphreportTempletId = graphreportTempletId;
    }

    public void setGraphreportCode(String graphreportCode) {
        this.graphreportCode = graphreportCode;
    }

    public void setGraphreportType(String graphreportType) {
        this.graphreportType = graphreportType;
    }

    public void setGroupNum(Integer groupNum) {
        this.groupNum = groupNum;
    }

    public void setGroupStyle(String groupStyle) {
        this.groupStyle = groupStyle;
    }

    public void setGroupTxt(String groupTxt) {
        this.groupTxt = groupTxt;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
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
        if (!(o instanceof OnlGraphreportTempletItem)) {
            return false;
        }
        OnlGraphreportTempletItem onlGraphreportTempletItem = (OnlGraphreportTempletItem) o;
        if (!onlGraphreportTempletItem.canEqual(this)) {
            return false;
        }
        Integer groupNum = getGroupNum();
        Integer groupNum2 = onlGraphreportTempletItem.getGroupNum();
        if (groupNum == null) {
            if (groupNum2 != null) {
                return false;
            }
        } else if (!groupNum.equals(groupNum2)) {
            return false;
        }
        Integer orderNum = getOrderNum();
        Integer orderNum2 = onlGraphreportTempletItem.getOrderNum();
        if (orderNum == null) {
            if (orderNum2 != null) {
                return false;
            }
        } else if (!orderNum.equals(orderNum2)) {
            return false;
        }
        String id = getId();
        String id2 = onlGraphreportTempletItem.getId();
        if (id == null) {
            if (id2 != null) {
                return false;
            }
        } else if (!id.equals(id2)) {
            return false;
        }
        String graphreportTempletId = getGraphreportTempletId();
        String graphreportTempletId2 = onlGraphreportTempletItem.getGraphreportTempletId();
        if (graphreportTempletId == null) {
            if (graphreportTempletId2 != null) {
                return false;
            }
        } else if (!graphreportTempletId.equals(graphreportTempletId2)) {
            return false;
        }
        String graphreportCode = getGraphreportCode();
        String graphreportCode2 = onlGraphreportTempletItem.getGraphreportCode();
        if (graphreportCode == null) {
            if (graphreportCode2 != null) {
                return false;
            }
        } else if (!graphreportCode.equals(graphreportCode2)) {
            return false;
        }
        String graphreportType = getGraphreportType();
        String graphreportType2 = onlGraphreportTempletItem.getGraphreportType();
        if (graphreportType == null) {
            if (graphreportType2 != null) {
                return false;
            }
        } else if (!graphreportType.equals(graphreportType2)) {
            return false;
        }
        String groupStyle = getGroupStyle();
        String groupStyle2 = onlGraphreportTempletItem.getGroupStyle();
        if (groupStyle == null) {
            if (groupStyle2 != null) {
                return false;
            }
        } else if (!groupStyle.equals(groupStyle2)) {
            return false;
        }
        String groupTxt = getGroupTxt();
        String groupTxt2 = onlGraphreportTempletItem.getGroupTxt();
        if (groupTxt == null) {
            if (groupTxt2 != null) {
                return false;
            }
        } else if (!groupTxt.equals(groupTxt2)) {
            return false;
        }
        String isShow = getIsShow();
        String isShow2 = onlGraphreportTempletItem.getIsShow();
        if (isShow == null) {
            if (isShow2 != null) {
                return false;
            }
        } else if (!isShow.equals(isShow2)) {
            return false;
        }
        Date createTime = getCreateTime();
        Date createTime2 = onlGraphreportTempletItem.getCreateTime();
        if (createTime == null) {
            if (createTime2 != null) {
                return false;
            }
        } else if (!createTime.equals(createTime2)) {
            return false;
        }
        String createBy = getCreateBy();
        String createBy2 = onlGraphreportTempletItem.getCreateBy();
        if (createBy == null) {
            if (createBy2 != null) {
                return false;
            }
        } else if (!createBy.equals(createBy2)) {
            return false;
        }
        Date updateTime = getUpdateTime();
        Date updateTime2 = onlGraphreportTempletItem.getUpdateTime();
        if (updateTime == null) {
            if (updateTime2 != null) {
                return false;
            }
        } else if (!updateTime.equals(updateTime2)) {
            return false;
        }
        String updateBy = getUpdateBy();
        String updateBy2 = onlGraphreportTempletItem.getUpdateBy();
        return updateBy == null ? updateBy2 == null : updateBy.equals(updateBy2);
    }

    protected boolean canEqual(Object other) {
        return other instanceof OnlGraphreportTempletItem;
    }

    @Override // java.lang.Object
    public int hashCode() {
        Integer groupNum = getGroupNum();
        int hashCode = (1 * 59) + (groupNum == null ? 43 : groupNum.hashCode());
        Integer orderNum = getOrderNum();
        int hashCode2 = (hashCode * 59) + (orderNum == null ? 43 : orderNum.hashCode());
        String id = getId();
        int hashCode3 = (hashCode2 * 59) + (id == null ? 43 : id.hashCode());
        String graphreportTempletId = getGraphreportTempletId();
        int hashCode4 = (hashCode3 * 59) + (graphreportTempletId == null ? 43 : graphreportTempletId.hashCode());
        String graphreportCode = getGraphreportCode();
        int hashCode5 = (hashCode4 * 59) + (graphreportCode == null ? 43 : graphreportCode.hashCode());
        String graphreportType = getGraphreportType();
        int hashCode6 = (hashCode5 * 59) + (graphreportType == null ? 43 : graphreportType.hashCode());
        String groupStyle = getGroupStyle();
        int hashCode7 = (hashCode6 * 59) + (groupStyle == null ? 43 : groupStyle.hashCode());
        String groupTxt = getGroupTxt();
        int hashCode8 = (hashCode7 * 59) + (groupTxt == null ? 43 : groupTxt.hashCode());
        String isShow = getIsShow();
        int hashCode9 = (hashCode8 * 59) + (isShow == null ? 43 : isShow.hashCode());
        Date createTime = getCreateTime();
        int hashCode10 = (hashCode9 * 59) + (createTime == null ? 43 : createTime.hashCode());
        String createBy = getCreateBy();
        int hashCode11 = (hashCode10 * 59) + (createBy == null ? 43 : createBy.hashCode());
        Date updateTime = getUpdateTime();
        int hashCode12 = (hashCode11 * 59) + (updateTime == null ? 43 : updateTime.hashCode());
        String updateBy = getUpdateBy();
        return (hashCode12 * 59) + (updateBy == null ? 43 : updateBy.hashCode());
    }

    @Override // java.lang.Object
    public String toString() {
        return "OnlGraphreportTempletItem(id=" + getId() + ", graphreportTempletId=" + getGraphreportTempletId() + ", graphreportCode=" + getGraphreportCode() + ", graphreportType=" + getGraphreportType() + ", groupNum=" + getGroupNum() + ", groupStyle=" + getGroupStyle() + ", groupTxt=" + getGroupTxt() + ", orderNum=" + getOrderNum() + ", isShow=" + getIsShow() + ", createTime=" + getCreateTime() + ", createBy=" + getCreateBy() + ", updateTime=" + getUpdateTime() + ", updateBy=" + getUpdateBy() + ")";
    }

    public String getId() {
        return this.id;
    }

    public String getGraphreportTempletId() {
        return this.graphreportTempletId;
    }

    public String getGraphreportCode() {
        return this.graphreportCode;
    }

    public String getGraphreportType() {
        return this.graphreportType;
    }

    public Integer getGroupNum() {
        return this.groupNum;
    }

    public String getGroupStyle() {
        return this.groupStyle;
    }

    public String getGroupTxt() {
        return this.groupTxt;
    }

    public Integer getOrderNum() {
        return this.orderNum;
    }

    public String getIsShow() {
        return this.isShow;
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
