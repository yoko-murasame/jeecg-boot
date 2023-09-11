/*    */ package org.jeecg.modules.online.cgform.model;
/*    */ 
/*    */ public class TreeModel
/*    */ {
/*    */   private String label;
/*    */   private String store;
/*    */   private String id;
/*    */   private String pid;
/*    */ 
/*    */   public String getLabel()
/*    */   {
/*  8 */     return this.label;
/*    */   }
/*    */ 
/*    */   public String getStore()
/*    */   {
/* 13 */     return this.store;
/*    */   }
/* 15 */   public String getId() { return this.id; } 
/*    */   public String getPid() {
/* 17 */     return this.pid;
/*    */   }
/*    */ 
/*    */   public void setLabel(String label)
/*    */   {
/*  5 */     this.label = label; } 
/*  5 */   public void setStore(String store) { this.store = store; } 
/*  5 */   public void setId(String id) { this.id = id; } 
/*  5 */   public void setPid(String pid) { this.pid = pid; } 
/*  5 */   public boolean equals(Object o) { if (o == this) return true; if (!(o instanceof TreeModel)) return false; TreeModel localTreeModel = (TreeModel)o; if (!localTreeModel.canEqual(this)) return false; String str1 = getLabel(); String str2 = localTreeModel.getLabel(); if (str1 == null ? str2 != null : !str1.equals(str2)) return false; String str3 = getStore(); String str4 = localTreeModel.getStore(); if (str3 == null ? str4 != null : !str3.equals(str4)) return false; String str5 = getId(); String str6 = localTreeModel.getId(); if (str5 == null ? str6 != null : !str5.equals(str6)) return false; String str7 = getPid(); String str8 = localTreeModel.getPid(); return str7 == null ? str8 == null : str7.equals(str8); } 
/*  5 */   protected boolean canEqual(Object other) { return other instanceof TreeModel; } 
/*  5 */   public int hashCode() { int i = 59; int j = 1; String str1 = getLabel(); j = j * 59 + (str1 == null ? 43 : str1.hashCode()); String str2 = getStore(); j = j * 59 + (str2 == null ? 43 : str2.hashCode()); String str3 = getId(); j = j * 59 + (str3 == null ? 43 : str3.hashCode()); String str4 = getPid(); j = j * 59 + (str4 == null ? 43 : str4.hashCode()); return j; } 
/*  5 */   public String toString() { return "TreeModel(label=" + getLabel() + ", store=" + getStore() + ", id=" + getId() + ", pid=" + getPid() + ")"; }
/*    */ 
/*    */ }

/* Location:           C:\Users\tx\Desktop\hibernate-common-ol-5.4.74(2).jar
 * Qualified Name:     org.jeecg.modules.online.cgform.model.TreeModel
 * JD-Core Version:    0.6.2
 */