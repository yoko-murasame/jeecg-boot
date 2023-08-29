/*    */ package org.jeecg.modules.online.cgform.model;
/*    */ 
/*    */ public class HrefSlots
/*    */ {
/*    */   private String slotName;
/*    */   private String href;
/*    */ 
/*    */   public HrefSlots()
/*    */   {
/*    */   }
/*    */ 
/*    */   public HrefSlots(String slotName, String href)
/*    */   {
/* 15 */     this.slotName = slotName;
/* 16 */     this.href = href;
/*    */   }
/*    */ 
/*    */   public String getSlotName()
/*    */   {
/*  8 */     return this.slotName;
/*    */   }
/* 10 */   public String getHref() { return this.href; }
/*    */ 
/*    */ 
/*    */   public void setSlotName(String slotName)
/*    */   {
/*  5 */     this.slotName = slotName; } 
/*  5 */   public void setHref(String href) { this.href = href; } 
/*  5 */   public boolean equals(Object o) { if (o == this) return true; if (!(o instanceof HrefSlots)) return false; HrefSlots localHrefSlots = (HrefSlots)o; if (!localHrefSlots.canEqual(this)) return false; String str1 = getSlotName(); String str2 = localHrefSlots.getSlotName(); if (str1 == null ? str2 != null : !str1.equals(str2)) return false; String str3 = getHref(); String str4 = localHrefSlots.getHref(); return str3 == null ? str4 == null : str3.equals(str4); } 
/*  5 */   protected boolean canEqual(Object other) { return other instanceof HrefSlots; } 
/*  5 */   public int hashCode() { int i = 59; int j = 1; String str1 = getSlotName(); j = j * 59 + (str1 == null ? 43 : str1.hashCode()); String str2 = getHref(); j = j * 59 + (str2 == null ? 43 : str2.hashCode()); return j; } 
/*  5 */   public String toString() { return "HrefSlots(slotName=" + getSlotName() + ", href=" + getHref() + ")"; }
/*    */ 
/*    */ }

/* Location:           C:\Users\tx\Desktop\hibernate-common-ol-5.4.74(2).jar
 * Qualified Name:     org.jeecg.modules.online.cgform.model.HrefSlots
 * JD-Core Version:    0.6.2
 */