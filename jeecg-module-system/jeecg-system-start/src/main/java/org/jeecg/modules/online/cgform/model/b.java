/*    */ package org.jeecg.modules.online.cgform.model;
/*    */ 
/*    */ public class b
/*    */ {
/*    */   private String a;
/*    */   private String b;
/*    */   private String c;
/*    */ 
/*    */   public b()
/*    */   {
/*    */   }
/*    */ 
/*    */   public b(String paramString1, String paramString2)
/*    */   {
/* 28 */     this.c = paramString2;
/* 29 */     this.a = paramString1;
/*    */   }
/*    */ 
/*    */   public String getField()
/*    */   {
/* 11 */     return this.a;
/*    */   }
/*    */ 
/*    */   public String getTable()
/*    */   {
/* 17 */     return this.b;
/*    */   }
/*    */ 
/*    */   public String getKey()
/*    */   {
/* 22 */     return this.c;
/*    */   }
/*    */ 
/*    */   public void setField(String field)
/*    */   {
/*  5 */     this.a = field; } 
/*  5 */   public void setTable(String table) { this.b = table; } 
/*  5 */   public void setKey(String key) { this.c = key; } 
/*  5 */   public boolean equals(Object o) { if (o == this) return true; if (!(o instanceof b)) return false; b localb = (b)o; if (!localb.a(this)) return false; String str1 = getField(); String str2 = localb.getField(); if (str1 == null ? str2 != null : !str1.equals(str2)) return false; String str3 = getTable(); String str4 = localb.getTable(); if (str3 == null ? str4 != null : !str3.equals(str4)) return false; String str5 = getKey(); String str6 = localb.getKey(); return str5 == null ? str6 == null : str5.equals(str6); } 
/*  5 */   protected boolean a(Object paramObject) { return paramObject instanceof b; } 
/*  5 */   public int hashCode() { int i = 59; int j = 1; String str1 = getField(); j = j * 59 + (str1 == null ? 43 : str1.hashCode()); String str2 = getTable(); j = j * 59 + (str2 == null ? 43 : str2.hashCode()); String str3 = getKey(); j = j * 59 + (str3 == null ? 43 : str3.hashCode()); return j; } 
/*  5 */   public String toString() { return "OnlForeignKey(field=" + getField() + ", table=" + getTable() + ", key=" + getKey() + ")"; }
/*    */ 
/*    */ }

/* Location:           C:\Users\tx\Desktop\hibernate-common-ol-5.4.74(2).jar
 * Qualified Name:     org.jeecg.modules.online.cgform.model.b
 * JD-Core Version:    0.6.2
 */