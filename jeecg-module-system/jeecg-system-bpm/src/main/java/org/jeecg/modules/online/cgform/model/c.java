/*    */ package org.jeecg.modules.online.cgform.model;
/*    */ 
/*    */ public class c
/*    */ {
/*    */   private String a;
/*    */ 
/*    */   public c()
/*    */   {
/*    */   }
/*    */ 
/*    */   public c(String paramString)
/*    */   {
/* 13 */     this.a = paramString;
/*    */   }
/*    */ 
/*    */   public String getCustomRender()
/*    */   {
/*  8 */     return this.a;
/*    */   }
/*    */ 
/*    */   public void setCustomRender(String customRender)
/*    */   {
/*  5 */     this.a = customRender; } 
/*  5 */   public boolean equals(Object o) { if (o == this) return true; if (!(o instanceof c)) return false; c localc = (c)o; if (!localc.a(this)) return false; String str1 = getCustomRender(); String str2 = localc.getCustomRender(); return str1 == null ? str2 == null : str1.equals(str2); } 
/*  5 */   protected boolean a(Object paramObject) { return paramObject instanceof c; } 
/*  5 */   public int hashCode() { int i = 59; int j = 1; String str = getCustomRender(); j = j * 59 + (str == null ? 43 : str.hashCode()); return j; } 
/*  5 */   public String toString() { return "ScopedSlots(customRender=" + getCustomRender() + ")"; }
/*    */ 
/*    */ }

/* Location:           C:\Users\tx\Desktop\hibernate-common-ol-5.4.74(2).jar
 * Qualified Name:     org.jeecg.modules.online.cgform.model.c
 * JD-Core Version:    0.6.2
 */