/*    */ package org.jeecg.modules.online.cgform.model;
/*    */ 
/*    */ public class OnlColumn
/*    */ {
/*    */   private String title;
/*    */   private String dataIndex;
/*    */   private String align;
/*    */   private String customRender;
/*    */   private c scopedSlots;
/*    */   private String hrefSlotName;
/*    */   private int showLength;
/* 30 */   private boolean sorter = false;
/*    */ 
/*    */   public OnlColumn()
/*    */   {
/*    */   }
/*    */ 
/*    */   public OnlColumn(String title, String dataIndex)
/*    */   {
/* 40 */     this.align = "center";
/* 41 */     this.title = title;
/* 42 */     this.dataIndex = dataIndex;
/*    */   }
/*    */ 
/*    */   public String getTitle()
/*    */   {
/*  9 */     return this.title;
/*    */   }
/* 11 */   public String getDataIndex() { return this.dataIndex; } 
/*    */   public String getAlign() {
/* 13 */     return this.align;
/*    */   }
/*    */ 
/*    */   public String getCustomRender()
/*    */   {
/* 18 */     return this.customRender;
/*    */   }
/* 20 */   public c getScopedSlots() { return this.scopedSlots; }
/*    */ 
/*    */   public String getHrefSlotName() {
/* 23 */     return this.hrefSlotName;
/*    */   }
/*    */ 
/*    */   public int getShowLength()
/*    */   {
/* 28 */     return this.showLength;
/*    */   }
/* 30 */   public boolean isSorter() { return this.sorter; }
/*    */ 
/*    */ 
/*    */   public void setTitle(String title)
/*    */   {
/*  6 */     this.title = title; } 
/*  6 */   public void setDataIndex(String dataIndex) { this.dataIndex = dataIndex; } 
/*  6 */   public void setAlign(String align) { this.align = align; } 
/*  6 */   public void setCustomRender(String customRender) { this.customRender = customRender; } 
/*  6 */   public void setScopedSlots(c scopedSlots) { this.scopedSlots = scopedSlots; } 
/*  6 */   public void setHrefSlotName(String hrefSlotName) { this.hrefSlotName = hrefSlotName; } 
/*  6 */   public void setShowLength(int showLength) { this.showLength = showLength; } 
/*  6 */   public void setSorter(boolean sorter) { this.sorter = sorter; } 
/*  6 */   public boolean equals(Object o) { if (o == this) return true; if (!(o instanceof OnlColumn)) return false; OnlColumn localOnlColumn = (OnlColumn)o; if (!localOnlColumn.canEqual(this)) return false; if (getShowLength() != localOnlColumn.getShowLength()) return false; if (isSorter() != localOnlColumn.isSorter()) return false; String str1 = getTitle(); String str2 = localOnlColumn.getTitle(); if (str1 == null ? str2 != null : !str1.equals(str2)) return false; String str3 = getDataIndex(); String str4 = localOnlColumn.getDataIndex(); if (str3 == null ? str4 != null : !str3.equals(str4)) return false; String str5 = getAlign(); String str6 = localOnlColumn.getAlign(); if (str5 == null ? str6 != null : !str5.equals(str6)) return false; String str7 = getCustomRender(); String str8 = localOnlColumn.getCustomRender(); if (str7 == null ? str8 != null : !str7.equals(str8)) return false; c localc1 = getScopedSlots(); c localc2 = localOnlColumn.getScopedSlots(); if (localc1 == null ? localc2 != null : !localc1.equals(localc2)) return false; String str9 = getHrefSlotName(); String str10 = localOnlColumn.getHrefSlotName(); return str9 == null ? str10 == null : str9.equals(str10); } 
/*  6 */   protected boolean canEqual(Object other) { return other instanceof OnlColumn; } 
/*  6 */   public int hashCode() { int i = 59; int j = 1; j = j * 59 + getShowLength(); j = j * 59 + (isSorter() ? 79 : 97); String str1 = getTitle(); j = j * 59 + (str1 == null ? 43 : str1.hashCode()); String str2 = getDataIndex(); j = j * 59 + (str2 == null ? 43 : str2.hashCode()); String str3 = getAlign(); j = j * 59 + (str3 == null ? 43 : str3.hashCode()); String str4 = getCustomRender(); j = j * 59 + (str4 == null ? 43 : str4.hashCode()); c localc = getScopedSlots(); j = j * 59 + (localc == null ? 43 : localc.hashCode()); String str5 = getHrefSlotName(); j = j * 59 + (str5 == null ? 43 : str5.hashCode()); return j; } 
/*  6 */   public String toString() { return "OnlColumn(title=" + getTitle() + ", dataIndex=" + getDataIndex() + ", align=" + getAlign() + ", customRender=" + getCustomRender() + ", scopedSlots=" + getScopedSlots() + ", hrefSlotName=" + getHrefSlotName() + ", showLength=" + getShowLength() + ", sorter=" + isSorter() + ")"; }
/*    */ 
/*    */ }

/* Location:           C:\Users\tx\Desktop\hibernate-common-ol-5.4.74(2).jar
 * Qualified Name:     org.jeecg.modules.online.cgform.model.OnlColumn
 * JD-Core Version:    0.6.2
 */