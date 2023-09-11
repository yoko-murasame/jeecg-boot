package org.jeecg.modules.online.graphreport.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

@TableName("onl_graphreport_head")
/* loaded from: hibernate-common-ol-5.4.74.jar:org/jeecg/modules/online/graphreport/entity/OnlGraphreportHead.class */
public class OnlGraphreportHead implements Serializable {
    private static final long serialVersionUID = 1;
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    private String name;
    private String code;
    private String cgrSql;
    private String xaxisField;
    private String yaxisField;
    private String yaxisText;
    private String content;
    private String extendJs;
    private String graphType;
    private String isCombination;
    private String displayTemplate;
    private String dataType;
    private String dbSource;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setCgrSql(String cgrSql) {
        this.cgrSql = cgrSql;
    }

    public void setXaxisField(String xaxisField) {
        this.xaxisField = xaxisField;
    }

    public void setYaxisField(String yaxisField) {
        this.yaxisField = yaxisField;
    }

    public void setYaxisText(String yaxisText) {
        this.yaxisText = yaxisText;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setExtendJs(String extendJs) {
        this.extendJs = extendJs;
    }

    public void setGraphType(String graphType) {
        this.graphType = graphType;
    }

    public void setIsCombination(String isCombination) {
        this.isCombination = isCombination;
    }

    public void setDisplayTemplate(String displayTemplate) {
        this.displayTemplate = displayTemplate;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public void setDbSource(String dbSource) {
        this.dbSource = dbSource;
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
        if (!(o instanceof OnlGraphreportHead)) {
            return false;
        }
        OnlGraphreportHead onlGraphreportHead = (OnlGraphreportHead) o;
        if (!onlGraphreportHead.canEqual(this)) {
            return false;
        }
        String id = getId();
        String id2 = onlGraphreportHead.getId();
        if (id == null) {
            if (id2 != null) {
                return false;
            }
        } else if (!id.equals(id2)) {
            return false;
        }
        String name = getName();
        String name2 = onlGraphreportHead.getName();
        if (name == null) {
            if (name2 != null) {
                return false;
            }
        } else if (!name.equals(name2)) {
            return false;
        }
        String code = getCode();
        String code2 = onlGraphreportHead.getCode();
        if (code == null) {
            if (code2 != null) {
                return false;
            }
        } else if (!code.equals(code2)) {
            return false;
        }
        String cgrSql = getCgrSql();
        String cgrSql2 = onlGraphreportHead.getCgrSql();
        if (cgrSql == null) {
            if (cgrSql2 != null) {
                return false;
            }
        } else if (!cgrSql.equals(cgrSql2)) {
            return false;
        }
        String xaxisField = getXaxisField();
        String xaxisField2 = onlGraphreportHead.getXaxisField();
        if (xaxisField == null) {
            if (xaxisField2 != null) {
                return false;
            }
        } else if (!xaxisField.equals(xaxisField2)) {
            return false;
        }
        String yaxisField = getYaxisField();
        String yaxisField2 = onlGraphreportHead.getYaxisField();
        if (yaxisField == null) {
            if (yaxisField2 != null) {
                return false;
            }
        } else if (!yaxisField.equals(yaxisField2)) {
            return false;
        }
        String yaxisText = getYaxisText();
        String yaxisText2 = onlGraphreportHead.getYaxisText();
        if (yaxisText == null) {
            if (yaxisText2 != null) {
                return false;
            }
        } else if (!yaxisText.equals(yaxisText2)) {
            return false;
        }
        String content = getContent();
        String content2 = onlGraphreportHead.getContent();
        if (content == null) {
            if (content2 != null) {
                return false;
            }
        } else if (!content.equals(content2)) {
            return false;
        }
        String extendJs = getExtendJs();
        String extendJs2 = onlGraphreportHead.getExtendJs();
        if (extendJs == null) {
            if (extendJs2 != null) {
                return false;
            }
        } else if (!extendJs.equals(extendJs2)) {
            return false;
        }
        String graphType = getGraphType();
        String graphType2 = onlGraphreportHead.getGraphType();
        if (graphType == null) {
            if (graphType2 != null) {
                return false;
            }
        } else if (!graphType.equals(graphType2)) {
            return false;
        }
        String isCombination = getIsCombination();
        String isCombination2 = onlGraphreportHead.getIsCombination();
        if (isCombination == null) {
            if (isCombination2 != null) {
                return false;
            }
        } else if (!isCombination.equals(isCombination2)) {
            return false;
        }
        String displayTemplate = getDisplayTemplate();
        String displayTemplate2 = onlGraphreportHead.getDisplayTemplate();
        if (displayTemplate == null) {
            if (displayTemplate2 != null) {
                return false;
            }
        } else if (!displayTemplate.equals(displayTemplate2)) {
            return false;
        }
        String dataType = getDataType();
        String dataType2 = onlGraphreportHead.getDataType();
        if (dataType == null) {
            if (dataType2 != null) {
                return false;
            }
        } else if (!dataType.equals(dataType2)) {
            return false;
        }
        String dbSource = getDbSource();
        String dbSource2 = onlGraphreportHead.getDbSource();
        if (dbSource == null) {
            if (dbSource2 != null) {
                return false;
            }
        } else if (!dbSource.equals(dbSource2)) {
            return false;
        }
        Date createTime = getCreateTime();
        Date createTime2 = onlGraphreportHead.getCreateTime();
        if (createTime == null) {
            if (createTime2 != null) {
                return false;
            }
        } else if (!createTime.equals(createTime2)) {
            return false;
        }
        String createBy = getCreateBy();
        String createBy2 = onlGraphreportHead.getCreateBy();
        if (createBy == null) {
            if (createBy2 != null) {
                return false;
            }
        } else if (!createBy.equals(createBy2)) {
            return false;
        }
        Date updateTime = getUpdateTime();
        Date updateTime2 = onlGraphreportHead.getUpdateTime();
        if (updateTime == null) {
            if (updateTime2 != null) {
                return false;
            }
        } else if (!updateTime.equals(updateTime2)) {
            return false;
        }
        String updateBy = getUpdateBy();
        String updateBy2 = onlGraphreportHead.getUpdateBy();
        return updateBy == null ? updateBy2 == null : updateBy.equals(updateBy2);
    }

    protected boolean canEqual(Object other) {
        return other instanceof OnlGraphreportHead;
    }

    @Override // java.lang.Object
    public int hashCode() {
        String id = getId();
        int hashCode = (1 * 59) + (id == null ? 43 : id.hashCode());
        String name = getName();
        int hashCode2 = (hashCode * 59) + (name == null ? 43 : name.hashCode());
        String code = getCode();
        int hashCode3 = (hashCode2 * 59) + (code == null ? 43 : code.hashCode());
        String cgrSql = getCgrSql();
        int hashCode4 = (hashCode3 * 59) + (cgrSql == null ? 43 : cgrSql.hashCode());
        String xaxisField = getXaxisField();
        int hashCode5 = (hashCode4 * 59) + (xaxisField == null ? 43 : xaxisField.hashCode());
        String yaxisField = getYaxisField();
        int hashCode6 = (hashCode5 * 59) + (yaxisField == null ? 43 : yaxisField.hashCode());
        String yaxisText = getYaxisText();
        int hashCode7 = (hashCode6 * 59) + (yaxisText == null ? 43 : yaxisText.hashCode());
        String content = getContent();
        int hashCode8 = (hashCode7 * 59) + (content == null ? 43 : content.hashCode());
        String extendJs = getExtendJs();
        int hashCode9 = (hashCode8 * 59) + (extendJs == null ? 43 : extendJs.hashCode());
        String graphType = getGraphType();
        int hashCode10 = (hashCode9 * 59) + (graphType == null ? 43 : graphType.hashCode());
        String isCombination = getIsCombination();
        int hashCode11 = (hashCode10 * 59) + (isCombination == null ? 43 : isCombination.hashCode());
        String displayTemplate = getDisplayTemplate();
        int hashCode12 = (hashCode11 * 59) + (displayTemplate == null ? 43 : displayTemplate.hashCode());
        String dataType = getDataType();
        int hashCode13 = (hashCode12 * 59) + (dataType == null ? 43 : dataType.hashCode());
        String dbSource = getDbSource();
        int hashCode14 = (hashCode13 * 59) + (dbSource == null ? 43 : dbSource.hashCode());
        Date createTime = getCreateTime();
        int hashCode15 = (hashCode14 * 59) + (createTime == null ? 43 : createTime.hashCode());
        String createBy = getCreateBy();
        int hashCode16 = (hashCode15 * 59) + (createBy == null ? 43 : createBy.hashCode());
        Date updateTime = getUpdateTime();
        int hashCode17 = (hashCode16 * 59) + (updateTime == null ? 43 : updateTime.hashCode());
        String updateBy = getUpdateBy();
        return (hashCode17 * 59) + (updateBy == null ? 43 : updateBy.hashCode());
    }

    @Override // java.lang.Object
    public String toString() {
        return "OnlGraphreportHead(id=" + getId() + ", name=" + getName() + ", code=" + getCode() + ", cgrSql=" + getCgrSql() + ", xaxisField=" + getXaxisField() + ", yaxisField=" + getYaxisField() + ", yaxisText=" + getYaxisText() + ", content=" + getContent() + ", extendJs=" + getExtendJs() + ", graphType=" + getGraphType() + ", isCombination=" + getIsCombination() + ", displayTemplate=" + getDisplayTemplate() + ", dataType=" + getDataType() + ", dbSource=" + getDbSource() + ", createTime=" + getCreateTime() + ", createBy=" + getCreateBy() + ", updateTime=" + getUpdateTime() + ", updateBy=" + getUpdateBy() + ")";
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getCode() {
        return this.code;
    }

    public String getCgrSql() {
        return this.cgrSql;
    }

    public String getXaxisField() {
        return this.xaxisField;
    }

    public String getYaxisField() {
        return this.yaxisField;
    }

    public String getYaxisText() {
        return this.yaxisText;
    }

    public String getContent() {
        return this.content;
    }

    public String getExtendJs() {
        return this.extendJs;
    }

    public String getGraphType() {
        return this.graphType;
    }

    public String getIsCombination() {
        return this.isCombination;
    }

    public String getDisplayTemplate() {
        return this.displayTemplate;
    }

    public String getDataType() {
        return this.dataType;
    }

    public String getDbSource() {
        return this.dbSource;
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
