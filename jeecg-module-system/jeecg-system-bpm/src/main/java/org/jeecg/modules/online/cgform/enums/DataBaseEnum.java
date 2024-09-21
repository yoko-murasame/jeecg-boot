/*    */ package org.jeecg.modules.online.cgform.enums;
/*    */ 
/*    */ public enum DataBaseEnum
/*    */ {
/*  8 */   MYSQL("MYSQL", "1"), 
/*  9 */   ORACLE("ORACLE", "2"), 
/* 10 */   SQLSERVER("SQLSERVER", "3"), 
/* 11 */   POSTGRESQL("POSTGRESQL", "4");
/*    */ 
/*    */   private String name;
/*    */   private String value;
/*    */ 
/* 14 */   private DataBaseEnum(String name, String value) { this.name = name;
/* 15 */     this.value = value;
/*    */   }
/*    */ 
/*    */   public static String getDataBaseNameByValue(String value)
/*    */   {
/* 28 */     for (DataBaseEnum localDataBaseEnum : values()) {
/* 29 */       if (localDataBaseEnum.value.equals(value)) {
/* 30 */         return localDataBaseEnum.name;
/*    */       }
/*    */     }
/* 33 */     return MYSQL.name;
/*    */   }
/*    */ 
/*    */   public String getName()
/*    */   {
/* 38 */     return this.name;
/*    */   }
/*    */ 
/*    */   public void setName(String name) {
/* 42 */     this.name = name;
/*    */   }
/*    */ 
/*    */   public String getValue() {
/* 46 */     return this.value;
/*    */   }
/*    */ 
/*    */   public void setValue(String value) {
/* 50 */     this.value = value;
/*    */   }
/*    */ }

/* Location:           C:\Users\tx\Desktop\hibernate-common-ol-5.4.74(2).jar
 * Qualified Name:     org.jeecg.modules.online.cgform.enums.DataBaseEnum
 * JD-Core Version:    0.6.2
 */