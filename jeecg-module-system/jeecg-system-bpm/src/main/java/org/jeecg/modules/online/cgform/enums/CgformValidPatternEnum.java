/*    */ package org.jeecg.modules.online.cgform.enums;
/*    */ 
/*    */ public enum CgformValidPatternEnum
/*    */ {
/*  9 */   ONLY("only", "only", ""), 
/*    */ 
/* 11 */   NUM6_16("n6-16", "^\\d{6,16}$", "请输入6-16位的数字"), 
/*    */ 
/* 13 */   STRING6_16("*6-16", "^.{6,16}$", "请输入6-16位任意字符"), 
/*    */ 
/* 15 */   LETTER6_18("s6-18", "^[a-z|A-Z]{6,18}$", "请输入6-18位字母"), 
/*    */ 
/* 17 */   URL("url", "^((ht|f)tps?):\\/\\/[\\w\\-]+(\\.[\\w\\-]+)+([\\w\\-.,@?^=%&:\\/~+#]*[\\w\\-@?^=%&\\/~+#])?$", "请输入正规的网址"), 
/*    */ 
/* 19 */   MOBILE("m", "^1[3456789]\\d{9}$", "请输入正规的手机号码"), 
/*    */ 
/* 21 */   POSTAL("p", "^[1-9]\\d{5}$", "请输入正规的邮政编码"), 
/*    */ 
/* 23 */   LETTER("s", "[A-Z|a-z]+$", "请输入字母"), 
/*    */ 
/* 25 */   NUMBER("n", "^-?\\d+(\\.?\\d+|\\d?)$", "请输入数字"), 
/*    */ 
/* 27 */   INTEGER("z", "z", "请输入整数"), 
/*    */ 
/* 29 */   NOTNULL("*", "^.+$", "该字段不能为空"), 
/*    */ 
/* 31 */   EMAIL("e", "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$", "请输入正确格式的邮箱地址"), 
/*    */ 
/* 33 */   MONEY("money", "^(([1-9][0-9]*)|([0]\\.\\d{0,2}|[1-9][0-9]*\\.\\d{0,5}))$", "请输入正确的金额");
/*    */ 
/*    */   String type;
/*    */   String pattern;
/*    */   String msg;
/*    */ 
/*    */   private CgformValidPatternEnum(String type, String pattern, String msg) {
/* 42 */     this.pattern = pattern;
/* 43 */     this.msg = msg;
/* 44 */     this.type = type;
/*    */   }
/*    */ 
/*    */   public String getType() {
/* 48 */     return this.type;
/*    */   }
/*    */   public void setType(String type) {
/* 51 */     this.type = type;
/*    */   }
/*    */   public String getPattern() {
/* 54 */     return this.pattern;
/*    */   }
/*    */   public void setPattern(String pattern) {
/* 57 */     this.pattern = pattern;
/*    */   }
/*    */   public String getMsg() {
/* 60 */     return this.msg;
/*    */   }
/*    */   public void setMsg(String msg) {
/* 63 */     this.msg = msg;
/*    */   }
/*    */ 
/*    */   public static CgformValidPatternEnum getPatternInfoByType(String type) {
/* 67 */     for (CgformValidPatternEnum localCgformValidPatternEnum : values()) {
/* 68 */       if (localCgformValidPatternEnum.type.equals(type)) {
/* 69 */         return localCgformValidPatternEnum;
/*    */       }
/*    */     }
/* 72 */     return null;
/*    */   }
/*    */ }

/* Location:           C:\Users\tx\Desktop\hibernate-common-ol-5.4.74(2).jar
 * Qualified Name:     org.jeecg.modules.online.cgform.enums.CgformValidPatternEnum
 * JD-Core Version:    0.6.2
 */