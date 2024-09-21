package org.jeecg.modules.online.cgform.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OnlGenerateModel implements Serializable {
    private static final long serialVersionUID = 684098897071177558L;
    private String code;
    private String projectPath;
    private String packageStyle;
    private String ftlDescription;
    private String jformType;
    private String tableName;
    private String entityPackage;
    private String entityName;
    private String jspMode;
    List<OnlGenerateModel> subList = new ArrayList<>();

    public String getProjectPath() {
        return this.projectPath;
    }

    public void setProjectPath(String projectPath) {
        this.projectPath = projectPath;
    }

    public String getPackageStyle() {
        return this.packageStyle;
    }

    public void setPackageStyle(String packageStyle) {
        this.packageStyle = packageStyle;
    }

    public String getFtlDescription() {
        return this.ftlDescription;
    }

    public void setFtlDescription(String ftlDescription) {
        this.ftlDescription = ftlDescription;
    }

    public String getJformType() {
        return this.jformType;
    }

    public void setJformType(String jformType) {
        this.jformType = jformType;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getEntityPackage() {
        return this.entityPackage;
    }

    public void setEntityPackage(String entityPackage) {
        this.entityPackage = entityPackage;
    }

    public String getEntityName() {
        return this.entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getJspMode() {
        return this.jspMode;
    }

    public void setJspMode(String jspMode) {
        this.jspMode = jspMode;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<OnlGenerateModel> getSubList() {
        return this.subList;
    }

    public void setSubList(List<OnlGenerateModel> subList) {
        this.subList = subList;
    }
}
