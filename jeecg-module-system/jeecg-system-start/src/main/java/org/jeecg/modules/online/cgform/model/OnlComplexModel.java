 package org.jeecg.modules.online.cgform.model;
 
 import java.io.Serializable;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import org.jeecg.common.system.vo.DictModel;
 import org.jeecg.modules.online.cgform.entity.OnlCgformButton;
 
 public class OnlComplexModel
   implements Serializable
 {
   private static final long b = 1L;
   private String c;
   private String d;
   private String e;
   private String f;
   private Integer g;
   private String h;
   private String i;
   private Integer j;
   private List<OnlColumn> k;
   private List<String> l;
/*  65 */   private Map<String, List<DictModel>> m = new HashMap();
   private List<OnlCgformButton> n;
   List<HrefSlots> a;
   private String o;
   private List<b> p;
   private String q;
   private String r;
   private String s;
   private String t;
   private String u;
   private Integer v;
 
   public String getCode()
   {
/*  22 */     return this.c;
   }
   public String getFormTemplate() {
/*  25 */     return this.d;
   }
   public String getDescription() {
/*  28 */     return this.e;
   }
   public String getCurrentTableName() {
/*  31 */     return this.f;
   }
   public Integer getTableType() {
/*  34 */     return this.g;
   }
 
   public String getPaginationFlag()
   {
/*  39 */     return this.h;
   }
 
   public String getCheckboxFlag()
   {
/*  44 */     return this.i;
   }
 
   public Integer getScrollFlag()
   {
/*  50 */     return this.j;
   }
 
   public List<OnlColumn> getColumns()
   {
/*  55 */     return this.k;
   }
 
   public List<String> getHideColumns()
   {
/*  60 */     return this.l;
   }
 
   public Map<String, List<DictModel>> getDictOptions()
   {
/*  65 */     return this.m;
   }
 
   public List<OnlCgformButton> getCgButtonList()
   {
/*  70 */     return this.n;
   }
 
   public List<HrefSlots> getFieldHrefSlots()
   {
/*  75 */     return this.a;
   }
 
   public String getEnhanceJs()
   {
/*  80 */     return this.o;
   }
 
   public List<b> getForeignKeys()
   {
/*  85 */     return this.p;
   }
 
   public String getPidField()
   {
/*  91 */     return this.q;
   }
 
   public String getHasChildrenField()
   {
/*  97 */     return this.r;
   }
 
   public String getTextField()
   {
/* 103 */     return this.s;
   }
   public String getIsDesForm() {
/* 106 */     return this.t;
   }
/* 108 */   public String getDesFormCode() { return this.u; }
 
   public Integer getRelationType() {
/* 111 */     return this.v;
   }
 
   public void setCode(String code)
   {
/*  15 */     this.c = code; } 
/*  15 */   public void setFormTemplate(String formTemplate) { this.d = formTemplate; } 
/*  15 */   public void setDescription(String description) { this.e = description; } 
/*  15 */   public void setCurrentTableName(String currentTableName) { this.f = currentTableName; } 
/*  15 */   public void setTableType(Integer tableType) { this.g = tableType; } 
/*  15 */   public void setPaginationFlag(String paginationFlag) { this.h = paginationFlag; } 
/*  15 */   public void setCheckboxFlag(String checkboxFlag) { this.i = checkboxFlag; } 
/*  15 */   public void setScrollFlag(Integer scrollFlag) { this.j = scrollFlag; } 
/*  15 */   public void setColumns(List<OnlColumn> columns) { this.k = columns; } 
/*  15 */   public void setHideColumns(List<String> hideColumns) { this.l = hideColumns; } 
/*  15 */   public void setDictOptions(Map<String, List<DictModel>> dictOptions) { this.m = dictOptions; } 
/*  15 */   public void setCgButtonList(List<OnlCgformButton> cgButtonList) { this.n = cgButtonList; } 
/*  15 */   public void setFieldHrefSlots(List<HrefSlots> fieldHrefSlots) { this.a = fieldHrefSlots; } 
/*  15 */   public void setEnhanceJs(String enhanceJs) { this.o = enhanceJs; } 
/*  15 */   public void setForeignKeys(List<b> foreignKeys) { this.p = foreignKeys; } 
/*  15 */   public void setPidField(String pidField) { this.q = pidField; } 
/*  15 */   public void setHasChildrenField(String hasChildrenField) { this.r = hasChildrenField; } 
/*  15 */   public void setTextField(String textField) { this.s = textField; } 
/*  15 */   public void setIsDesForm(String isDesForm) { this.t = isDesForm; } 
/*  15 */   public void setDesFormCode(String desFormCode) { this.u = desFormCode; } 
/*  15 */   public void setRelationType(Integer relationType) { this.v = relationType; } 
/*  15 */   public boolean equals(Object o) { if (o == this) return true; if (!(o instanceof OnlComplexModel)) return false; OnlComplexModel localOnlComplexModel = (OnlComplexModel)o; if (!localOnlComplexModel.a(this)) return false; Integer localInteger1 = getTableType(); Integer localInteger2 = localOnlComplexModel.getTableType(); if (localInteger1 == null ? localInteger2 != null : !localInteger1.equals(localInteger2)) return false; Integer localInteger3 = getScrollFlag(); Integer localInteger4 = localOnlComplexModel.getScrollFlag(); if (localInteger3 == null ? localInteger4 != null : !localInteger3.equals(localInteger4)) return false; Integer localInteger5 = getRelationType(); Integer localInteger6 = localOnlComplexModel.getRelationType(); if (localInteger5 == null ? localInteger6 != null : !localInteger5.equals(localInteger6)) return false; String str1 = getCode(); String str2 = localOnlComplexModel.getCode(); if (str1 == null ? str2 != null : !str1.equals(str2)) return false; String str3 = getFormTemplate(); String str4 = localOnlComplexModel.getFormTemplate(); if (str3 == null ? str4 != null : !str3.equals(str4)) return false; String str5 = getDescription(); String str6 = localOnlComplexModel.getDescription(); if (str5 == null ? str6 != null : !str5.equals(str6)) return false; String str7 = getCurrentTableName(); String str8 = localOnlComplexModel.getCurrentTableName(); if (str7 == null ? str8 != null : !str7.equals(str8)) return false; String str9 = getPaginationFlag(); String str10 = localOnlComplexModel.getPaginationFlag(); if (str9 == null ? str10 != null : !str9.equals(str10)) return false; String str11 = getCheckboxFlag(); String str12 = localOnlComplexModel.getCheckboxFlag(); if (str11 == null ? str12 != null : !str11.equals(str12)) return false; List localList1 = getColumns(); List localList2 = localOnlComplexModel.getColumns(); if (localList1 == null ? localList2 != null : !localList1.equals(localList2)) return false; List localList3 = getHideColumns(); List localList4 = localOnlComplexModel.getHideColumns(); if (localList3 == null ? localList4 != null : !localList3.equals(localList4)) return false; Map localMap1 = getDictOptions(); Map localMap2 = localOnlComplexModel.getDictOptions(); if (localMap1 == null ? localMap2 != null : !localMap1.equals(localMap2)) return false; List localList5 = getCgButtonList(); List localList6 = localOnlComplexModel.getCgButtonList(); if (localList5 == null ? localList6 != null : !localList5.equals(localList6)) return false; List localList7 = getFieldHrefSlots(); List localList8 = localOnlComplexModel.getFieldHrefSlots(); if (localList7 == null ? localList8 != null : !localList7.equals(localList8)) return false; String str13 = getEnhanceJs(); String str14 = localOnlComplexModel.getEnhanceJs(); if (str13 == null ? str14 != null : !str13.equals(str14)) return false; List localList9 = getForeignKeys(); List localList10 = localOnlComplexModel.getForeignKeys(); if (localList9 == null ? localList10 != null : !localList9.equals(localList10)) return false; String str15 = getPidField(); String str16 = localOnlComplexModel.getPidField(); if (str15 == null ? str16 != null : !str15.equals(str16)) return false; String str17 = getHasChildrenField(); String str18 = localOnlComplexModel.getHasChildrenField(); if (str17 == null ? str18 != null : !str17.equals(str18)) return false; String str19 = getTextField(); String str20 = localOnlComplexModel.getTextField(); if (str19 == null ? str20 != null : !str19.equals(str20)) return false; String str21 = getIsDesForm(); String str22 = localOnlComplexModel.getIsDesForm(); if (str21 == null ? str22 != null : !str21.equals(str22)) return false; String str23 = getDesFormCode(); String str24 = localOnlComplexModel.getDesFormCode(); return str23 == null ? str24 == null : str23.equals(str24); } 
/*  15 */   protected boolean a(Object paramObject) { return paramObject instanceof OnlComplexModel; } 
/*  15 */   public int hashCode() { int i1 = 59; int i2 = 1; Integer localInteger1 = getTableType(); i2 = i2 * 59 + (localInteger1 == null ? 43 : localInteger1.hashCode()); Integer localInteger2 = getScrollFlag(); i2 = i2 * 59 + (localInteger2 == null ? 43 : localInteger2.hashCode()); Integer localInteger3 = getRelationType(); i2 = i2 * 59 + (localInteger3 == null ? 43 : localInteger3.hashCode()); String str1 = getCode(); i2 = i2 * 59 + (str1 == null ? 43 : str1.hashCode()); String str2 = getFormTemplate(); i2 = i2 * 59 + (str2 == null ? 43 : str2.hashCode()); String str3 = getDescription(); i2 = i2 * 59 + (str3 == null ? 43 : str3.hashCode()); String str4 = getCurrentTableName(); i2 = i2 * 59 + (str4 == null ? 43 : str4.hashCode()); String str5 = getPaginationFlag(); i2 = i2 * 59 + (str5 == null ? 43 : str5.hashCode()); String str6 = getCheckboxFlag(); i2 = i2 * 59 + (str6 == null ? 43 : str6.hashCode()); List localList1 = getColumns(); i2 = i2 * 59 + (localList1 == null ? 43 : localList1.hashCode()); List localList2 = getHideColumns(); i2 = i2 * 59 + (localList2 == null ? 43 : localList2.hashCode()); Map localMap = getDictOptions(); i2 = i2 * 59 + (localMap == null ? 43 : localMap.hashCode()); List localList3 = getCgButtonList(); i2 = i2 * 59 + (localList3 == null ? 43 : localList3.hashCode()); List localList4 = getFieldHrefSlots(); i2 = i2 * 59 + (localList4 == null ? 43 : localList4.hashCode()); String str7 = getEnhanceJs(); i2 = i2 * 59 + (str7 == null ? 43 : str7.hashCode()); List localList5 = getForeignKeys(); i2 = i2 * 59 + (localList5 == null ? 43 : localList5.hashCode()); String str8 = getPidField(); i2 = i2 * 59 + (str8 == null ? 43 : str8.hashCode()); String str9 = getHasChildrenField(); i2 = i2 * 59 + (str9 == null ? 43 : str9.hashCode()); String str10 = getTextField(); i2 = i2 * 59 + (str10 == null ? 43 : str10.hashCode()); String str11 = getIsDesForm(); i2 = i2 * 59 + (str11 == null ? 43 : str11.hashCode()); String str12 = getDesFormCode(); i2 = i2 * 59 + (str12 == null ? 43 : str12.hashCode()); return i2; } 
/*  15 */   public String toString() { return "OnlComplexModel(code=" + getCode() + ", formTemplate=" + getFormTemplate() + ", description=" + getDescription() + ", currentTableName=" + getCurrentTableName() + ", tableType=" + getTableType() + ", paginationFlag=" + getPaginationFlag() + ", checkboxFlag=" + getCheckboxFlag() + ", scrollFlag=" + getScrollFlag() + ", columns=" + getColumns() + ", hideColumns=" + getHideColumns() + ", dictOptions=" + getDictOptions() + ", cgButtonList=" + getCgButtonList() + ", fieldHrefSlots=" + getFieldHrefSlots() + ", enhanceJs=" + getEnhanceJs() + ", foreignKeys=" + getForeignKeys() + ", pidField=" + getPidField() + ", hasChildrenField=" + getHasChildrenField() + ", textField=" + getTextField() + ", isDesForm=" + getIsDesForm() + ", desFormCode=" + getDesFormCode() + ", relationType=" + getRelationType() + ")"; }
 
 }

/* Location:           C:\Users\tx\Desktop\hibernate-common-ol-5.4.74(2).jar
 * Qualified Name:     org.jeecg.modules.online.cgform.model.OnlComplexModel
 * JD-Core Version:    0.6.2
 */