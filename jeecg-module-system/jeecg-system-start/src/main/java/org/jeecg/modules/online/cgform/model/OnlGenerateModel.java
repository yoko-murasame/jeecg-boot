 package org.jeecg.modules.online.cgform.model;
 
 import java.io.Serializable;
 import java.util.ArrayList;
 import java.util.List;
 
 public class OnlGenerateModel
   implements Serializable
 {
   private static final long b = 684098897071177558L;
   private String c;
   private String d;
   private String e;
   private String f;
   private String g;
   private String h;
   private String i;
   private String j;
   private String k;
/*  28 */   List<OnlGenerateModel> a = new ArrayList();
 
   public String getProjectPath() {
/*  31 */     return this.d;
   }
 
   public void setProjectPath(String projectPath) {
/*  35 */     this.d = projectPath;
   }
 
   public String getPackageStyle() {
/*  39 */     return this.e;
   }
 
   public void setPackageStyle(String packageStyle) {
/*  43 */     this.e = packageStyle;
   }
 
   public String getFtlDescription() {
/*  47 */     return this.f;
   }
 
   public void setFtlDescription(String ftlDescription) {
/*  51 */     this.f = ftlDescription;
   }
 
   public String getJformType() {
/*  55 */     return this.g;
   }
 
   public void setJformType(String jformType) {
/*  59 */     this.g = jformType;
   }
 
   public String getTableName() {
/*  63 */     return this.h;
   }
 
   public void setTableName(String tableName) {
/*  67 */     this.h = tableName;
   }
 
   public String getEntityPackage() {
/*  71 */     return this.i;
   }
 
   public void setEntityPackage(String entityPackage) {
/*  75 */     this.i = entityPackage;
   }
 
   public String getEntityName() {
/*  79 */     return this.j;
   }
 
   public void setEntityName(String entityName) {
/*  83 */     this.j = entityName;
   }
 
   public String getJspMode() {
/*  87 */     return this.k;
   }
 
   public void setJspMode(String jspMode) {
/*  91 */     this.k = jspMode;
   }
 
   public String getCode() {
/*  95 */     return this.c;
   }
 
   public void setCode(String code) {
/*  99 */     this.c = code;
   }
 
   public List<OnlGenerateModel> getSubList() {
/* 103 */     return this.a;
   }
 
   public void setSubList(List<OnlGenerateModel> subList) {
/* 107 */     this.a = subList;
   }
 }

/* Location:           C:\Users\tx\Desktop\hibernate-common-ol-5.4.74(2).jar
 * Qualified Name:     org.jeecg.modules.online.cgform.model.OnlGenerateModel
 * JD-Core Version:    0.6.2
 */