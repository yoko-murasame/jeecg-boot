package org.jeecg.modules.online.graphreport.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@TableName("onl_graphreport_item")
/* loaded from: hibernate-common-ol-5.4.74.jar:org/jeecg/modules/online/graphreport/entity/OnlGraphreportItem.class */
public class OnlGraphreportItem implements Serializable {
    private static final long serialVersionUID = 1;
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    private String graphreportHeadId;
    @Excel(name = "字段名", width = 15.0d)
    private String fieldName;
    @Excel(name = "字段文本", width = 15.0d)
    private String fieldTxt;
    @Excel(name = "是否列表显示", width = 15.0d)
    private String isShow;
    @Excel(name = "是否计算总计", width = 15.0d)
    private String isTotal;
    @Excel(name = "是否查询", width = 15.0d)
    private String searchFlag;
    @Excel(name = "查询模式", width = 15.0d)
    private String searchMode;
    @Excel(name = "字典Code", width = 15.0d)
    private String dictCode;
    @Excel(name = "字段href", width = 15.0d)
    private String fieldHref;
    @Excel(name = "字段类型", width = 15.0d)
    private String fieldType;
    @Excel(name = "排序", width = 15.0d)
    private Integer orderNum;
    @Excel(name = "取值表达式", width = 15.0d)
    private String replaceVal;
    @Excel(name = "创建人", width = 15.0d)
    private String createBy;
    @Excel(name = "创建时间", width = 20.0d, format = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @Excel(name = "修改人", width = 15.0d)
    private String updateBy;
    @Excel(name = "修改时间", width = 20.0d, format = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    public void setId(String id) {
        this.id = id;
    }

    public void setGraphreportHeadId(String graphreportHeadId) {
        this.graphreportHeadId = graphreportHeadId;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public void setFieldTxt(String fieldTxt) {
        this.fieldTxt = fieldTxt;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    public void setIsTotal(String isTotal) {
        this.isTotal = isTotal;
    }

    public void setSearchFlag(String searchFlag) {
        this.searchFlag = searchFlag;
    }

    public void setSearchMode(String searchMode) {
        this.searchMode = searchMode;
    }

    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
    }

    public void setFieldHref(String fieldHref) {
        this.fieldHref = fieldHref;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public void setReplaceVal(String replaceVal) {
        this.replaceVal = replaceVal;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override // java.lang.Object
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof OnlGraphreportItem)) {
            return false;
        }
        OnlGraphreportItem onlGraphreportItem = (OnlGraphreportItem) o;
        if (!onlGraphreportItem.canEqual(this)) {
            return false;
        }
        Integer orderNum = getOrderNum();
        Integer orderNum2 = onlGraphreportItem.getOrderNum();
        if (orderNum == null) {
            if (orderNum2 != null) {
                return false;
            }
        } else if (!orderNum.equals(orderNum2)) {
            return false;
        }
        String id = getId();
        String id2 = onlGraphreportItem.getId();
        if (id == null) {
            if (id2 != null) {
                return false;
            }
        } else if (!id.equals(id2)) {
            return false;
        }
        String graphreportHeadId = getGraphreportHeadId();
        String graphreportHeadId2 = onlGraphreportItem.getGraphreportHeadId();
        if (graphreportHeadId == null) {
            if (graphreportHeadId2 != null) {
                return false;
            }
        } else if (!graphreportHeadId.equals(graphreportHeadId2)) {
            return false;
        }
        String fieldName = getFieldName();
        String fieldName2 = onlGraphreportItem.getFieldName();
        if (fieldName == null) {
            if (fieldName2 != null) {
                return false;
            }
        } else if (!fieldName.equals(fieldName2)) {
            return false;
        }
        String fieldTxt = getFieldTxt();
        String fieldTxt2 = onlGraphreportItem.getFieldTxt();
        if (fieldTxt == null) {
            if (fieldTxt2 != null) {
                return false;
            }
        } else if (!fieldTxt.equals(fieldTxt2)) {
            return false;
        }
        String isShow = getIsShow();
        String isShow2 = onlGraphreportItem.getIsShow();
        if (isShow == null) {
            if (isShow2 != null) {
                return false;
            }
        } else if (!isShow.equals(isShow2)) {
            return false;
        }
        String isTotal = getIsTotal();
        String isTotal2 = onlGraphreportItem.getIsTotal();
        if (isTotal == null) {
            if (isTotal2 != null) {
                return false;
            }
        } else if (!isTotal.equals(isTotal2)) {
            return false;
        }
        String searchFlag = getSearchFlag();
        String searchFlag2 = onlGraphreportItem.getSearchFlag();
        if (searchFlag == null) {
            if (searchFlag2 != null) {
                return false;
            }
        } else if (!searchFlag.equals(searchFlag2)) {
            return false;
        }
        String searchMode = getSearchMode();
        String searchMode2 = onlGraphreportItem.getSearchMode();
        if (searchMode == null) {
            if (searchMode2 != null) {
                return false;
            }
        } else if (!searchMode.equals(searchMode2)) {
            return false;
        }
        String dictCode = getDictCode();
        String dictCode2 = onlGraphreportItem.getDictCode();
        if (dictCode == null) {
            if (dictCode2 != null) {
                return false;
            }
        } else if (!dictCode.equals(dictCode2)) {
            return false;
        }
        String fieldHref = getFieldHref();
        String fieldHref2 = onlGraphreportItem.getFieldHref();
        if (fieldHref == null) {
            if (fieldHref2 != null) {
                return false;
            }
        } else if (!fieldHref.equals(fieldHref2)) {
            return false;
        }
        String fieldType = getFieldType();
        String fieldType2 = onlGraphreportItem.getFieldType();
        if (fieldType == null) {
            if (fieldType2 != null) {
                return false;
            }
        } else if (!fieldType.equals(fieldType2)) {
            return false;
        }
        String replaceVal = getReplaceVal();
        String replaceVal2 = onlGraphreportItem.getReplaceVal();
        if (replaceVal == null) {
            if (replaceVal2 != null) {
                return false;
            }
        } else if (!replaceVal.equals(replaceVal2)) {
            return false;
        }
        String createBy = getCreateBy();
        String createBy2 = onlGraphreportItem.getCreateBy();
        if (createBy == null) {
            if (createBy2 != null) {
                return false;
            }
        } else if (!createBy.equals(createBy2)) {
            return false;
        }
        Date createTime = getCreateTime();
        Date createTime2 = onlGraphreportItem.getCreateTime();
        if (createTime == null) {
            if (createTime2 != null) {
                return false;
            }
        } else if (!createTime.equals(createTime2)) {
            return false;
        }
        String updateBy = getUpdateBy();
        String updateBy2 = onlGraphreportItem.getUpdateBy();
        if (updateBy == null) {
            if (updateBy2 != null) {
                return false;
            }
        } else if (!updateBy.equals(updateBy2)) {
            return false;
        }
        Date updateTime = getUpdateTime();
        Date updateTime2 = onlGraphreportItem.getUpdateTime();
        return updateTime == null ? updateTime2 == null : updateTime.equals(updateTime2);
    }

    protected boolean canEqual(Object other) {
        return other instanceof OnlGraphreportItem;
    }

    @Override // java.lang.Object
    public int hashCode() {
        Integer orderNum = getOrderNum();
        int hashCode = (1 * 59) + (orderNum == null ? 43 : orderNum.hashCode());
        String id = getId();
        int hashCode2 = (hashCode * 59) + (id == null ? 43 : id.hashCode());
        String graphreportHeadId = getGraphreportHeadId();
        int hashCode3 = (hashCode2 * 59) + (graphreportHeadId == null ? 43 : graphreportHeadId.hashCode());
        String fieldName = getFieldName();
        int hashCode4 = (hashCode3 * 59) + (fieldName == null ? 43 : fieldName.hashCode());
        String fieldTxt = getFieldTxt();
        int hashCode5 = (hashCode4 * 59) + (fieldTxt == null ? 43 : fieldTxt.hashCode());
        String isShow = getIsShow();
        int hashCode6 = (hashCode5 * 59) + (isShow == null ? 43 : isShow.hashCode());
        String isTotal = getIsTotal();
        int hashCode7 = (hashCode6 * 59) + (isTotal == null ? 43 : isTotal.hashCode());
        String searchFlag = getSearchFlag();
        int hashCode8 = (hashCode7 * 59) + (searchFlag == null ? 43 : searchFlag.hashCode());
        String searchMode = getSearchMode();
        int hashCode9 = (hashCode8 * 59) + (searchMode == null ? 43 : searchMode.hashCode());
        String dictCode = getDictCode();
        int hashCode10 = (hashCode9 * 59) + (dictCode == null ? 43 : dictCode.hashCode());
        String fieldHref = getFieldHref();
        int hashCode11 = (hashCode10 * 59) + (fieldHref == null ? 43 : fieldHref.hashCode());
        String fieldType = getFieldType();
        int hashCode12 = (hashCode11 * 59) + (fieldType == null ? 43 : fieldType.hashCode());
        String replaceVal = getReplaceVal();
        int hashCode13 = (hashCode12 * 59) + (replaceVal == null ? 43 : replaceVal.hashCode());
        String createBy = getCreateBy();
        int hashCode14 = (hashCode13 * 59) + (createBy == null ? 43 : createBy.hashCode());
        Date createTime = getCreateTime();
        int hashCode15 = (hashCode14 * 59) + (createTime == null ? 43 : createTime.hashCode());
        String updateBy = getUpdateBy();
        int hashCode16 = (hashCode15 * 59) + (updateBy == null ? 43 : updateBy.hashCode());
        Date updateTime = getUpdateTime();
        return (hashCode16 * 59) + (updateTime == null ? 43 : updateTime.hashCode());
    }

    @Override // java.lang.Object
    public String toString() {
        return "OnlGraphreportItem(id=" + getId() + ", graphreportHeadId=" + getGraphreportHeadId() + ", fieldName=" + getFieldName() + ", fieldTxt=" + getFieldTxt() + ", isShow=" + getIsShow() + ", isTotal=" + getIsTotal() + ", searchFlag=" + getSearchFlag() + ", searchMode=" + getSearchMode() + ", dictCode=" + getDictCode() + ", fieldHref=" + getFieldHref() + ", fieldType=" + getFieldType() + ", orderNum=" + getOrderNum() + ", replaceVal=" + getReplaceVal() + ", createBy=" + getCreateBy() + ", createTime=" + getCreateTime() + ", updateBy=" + getUpdateBy() + ", updateTime=" + getUpdateTime() + ")";
    }

    public String getId() {
        return this.id;
    }

    public String getGraphreportHeadId() {
        return this.graphreportHeadId;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public String getFieldTxt() {
        return this.fieldTxt;
    }

    public String getIsShow() {
        return this.isShow;
    }

    public String getIsTotal() {
        return this.isTotal;
    }

    public String getSearchFlag() {
        return this.searchFlag;
    }

    public String getSearchMode() {
        return this.searchMode;
    }

    public String getDictCode() {
        return this.dictCode;
    }

    public String getFieldHref() {
        return this.fieldHref;
    }

    public String getFieldType() {
        return this.fieldType;
    }

    public Integer getOrderNum() {
        return this.orderNum;
    }

    public String getReplaceVal() {
        return this.replaceVal;
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
}
