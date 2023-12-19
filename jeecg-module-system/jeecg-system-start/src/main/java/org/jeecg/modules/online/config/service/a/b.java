package org.jeecg.modules.online.config.service.a;

import org.apache.commons.lang.StringUtils;
import org.jeecg.modules.online.cgform.d.i;
import org.jeecg.modules.online.config.service.DbTableHandleI;

/* compiled from: DbTableMysqlHandleImpl.java */
/* loaded from: hibernate-common-ol-5.4.74(2).jar:org/jeecg/modules/online/config/service/a/b.class */
public class b implements DbTableHandleI {
    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String getAddColumnSql(org.jeecg.modules.online.config.b.a columnMeta) {
        return " ADD COLUMN " + a(columnMeta) + ";";
    }

    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String getReNameFieldName(org.jeecg.modules.online.config.b.a columnMeta) {
        return "CHANGE COLUMN " + columnMeta.getOldColumnName() + " " + b(columnMeta) + " ;";
    }

    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String getUpdateColumnSql(org.jeecg.modules.online.config.b.a cgformcolumnMeta, org.jeecg.modules.online.config.b.a datacolumnMeta) {
        return " MODIFY COLUMN " + b(cgformcolumnMeta, datacolumnMeta) + ";";
    }

    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String getMatchClassTypeByDataType(String dataType, int digits) {
        String str = "";
        if (dataType.equalsIgnoreCase(org.jeecg.modules.online.config.b.b.l)) {
            str = org.jeecg.modules.online.config.b.b.i;
        } else if (dataType.equalsIgnoreCase("double")) {
            str = "double";
        } else if (dataType.equalsIgnoreCase("int")) {
            str = "int";
        } else if (dataType.equalsIgnoreCase("Date")) {
            str = i.DATE;
        } else if (dataType.equalsIgnoreCase("Datetime")) {
            str = i.DATE;
        } else if (dataType.equalsIgnoreCase(org.jeecg.modules.online.config.b.b.e)) {
            str = "bigdecimal";
        } else if (dataType.equalsIgnoreCase("text")) {
            str = "text";
        } else if (dataType.equalsIgnoreCase("blob")) {
            str = "blob";
        }
        return str;
    }

    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String dropTableSQL(String tableName) {
        return " DROP TABLE IF EXISTS " + tableName + " ;";
    }

    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String getDropColumnSql(String fieldName) {
        return " DROP COLUMN " + fieldName + ";";
    }

    private String a(org.jeecg.modules.online.config.b.a aVar, org.jeecg.modules.online.config.b.a aVar2) {
        String str = "";
        if (aVar.getColunmType().equalsIgnoreCase(org.jeecg.modules.online.config.b.b.i)) {
            str = aVar.getColumnName() + " varchar(" + aVar.getColumnSize() + ") " ;
        } else if (aVar.getColunmType().equalsIgnoreCase(i.DATE)) {
            str = aVar.getColumnName() + " datetime " ;
        } else if (aVar.getColunmType().equalsIgnoreCase("int")) {
            str = aVar.getColumnName() + " int(" + aVar.getColumnSize() + ") " ;
        } else if (aVar.getColunmType().equalsIgnoreCase("double")) {
            str = aVar.getColumnName() + " double(" + aVar.getColumnSize() + org.jeecg.modules.online.cgform.d.b.DOT_STRING + aVar.getDecimalDigits() + ") " + ("Y".equals(aVar.getIsNullable()) ? "NULL" : "NOT NULL");
        } else if (aVar.getColunmType().equalsIgnoreCase("bigdecimal")) {
            str = aVar.getColumnName() + " decimal(" + aVar.getColumnSize() + org.jeecg.modules.online.cgform.d.b.DOT_STRING + aVar.getDecimalDigits() + ") " + ("Y".equals(aVar.getIsNullable()) ? "NULL" : "NOT NULL");
        } else if (aVar.getColunmType().equalsIgnoreCase("text")) {
            str = aVar.getColumnName() + " text " ;
        } else if (aVar.getColunmType().equalsIgnoreCase("blob")) {
            str = aVar.getColumnName() + " blob " ;
        }
//        if (aVar.getColunmType().equalsIgnoreCase(org.jeecg.modules.online.config.b.b.i)) {
//            str = aVar.getColumnName() + " varchar(" + aVar.getColumnSize() + ") " + ("Y".equals(aVar.getIsNullable()) ? "NULL" : "NOT NULL");
//        } else if (aVar.getColunmType().equalsIgnoreCase(i.d)) {
//            str = aVar.getColumnName() + " datetime " + ("Y".equals(aVar.getIsNullable()) ? "NULL" : "NOT NULL");
//        } else if (aVar.getColunmType().equalsIgnoreCase("int")) {
//            str = aVar.getColumnName() + " int(" + aVar.getColumnSize() + ") " + ("Y".equals(aVar.getIsNullable()) ? "NULL" : "NOT NULL");
//        } else if (aVar.getColunmType().equalsIgnoreCase("double")) {
//            str = aVar.getColumnName() + " double(" + aVar.getColumnSize() + org.jeecg.modules.online.cgform.d.b.DOT_STRING + aVar.getDecimalDigits() + ") " + ("Y".equals(aVar.getIsNullable()) ? "NULL" : "NOT NULL");
//        } else if (aVar.getColunmType().equalsIgnoreCase("bigdecimal")) {
//            str = aVar.getColumnName() + " decimal(" + aVar.getColumnSize() + org.jeecg.modules.online.cgform.d.b.DOT_STRING + aVar.getDecimalDigits() + ") " + ("Y".equals(aVar.getIsNullable()) ? "NULL" : "NOT NULL");
//        } else if (aVar.getColunmType().equalsIgnoreCase("text")) {
//            str = aVar.getColumnName() + " text " + ("Y".equals(aVar.getIsNullable()) ? "NULL" : "NOT NULL");
//        } else if (aVar.getColunmType().equalsIgnoreCase("blob")) {
//            str = aVar.getColumnName() + " blob " + ("Y".equals(aVar.getIsNullable()) ? "NULL" : "NOT NULL");
//        }
//        String str2 = (str + (StringUtils.isNotEmpty(aVar.getComment()) ? " COMMENT '" + aVar.getComment() + org.jeecg.modules.online.cgform.d.b.sz : " ")) + (StringUtils.isNotEmpty(aVar.getFieldDefault()) ? " DEFAULT " + aVar.getFieldDefault() : " ");
        String str2 = (str + (" ")) + (StringUtils.isNotEmpty(aVar.getFieldDefault()) ? " DEFAULT " + aVar.getFieldDefault() : " ");
        String pkType = aVar.getPkType();
        if ("id".equalsIgnoreCase(aVar.getColumnName()) && pkType != null && ("SEQUENCE".equalsIgnoreCase(pkType) || "NATIVE".equalsIgnoreCase(pkType))) {
            str2 = str2 + " AUTO_INCREMENT ";
        }
        return str2;
    }

    private String b(org.jeecg.modules.online.config.b.a aVar, org.jeecg.modules.online.config.b.a aVar2) {
        return a(aVar, aVar2);
    }

    private String a(org.jeecg.modules.online.config.b.a aVar) {
        return a(aVar, null);
    }

    private String b(org.jeecg.modules.online.config.b.a aVar) {
        return a(aVar, null);
    }

    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String getCommentSql(org.jeecg.modules.online.config.b.a columnMeta) {
        return "";
    }

    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String getSpecialHandle(org.jeecg.modules.online.config.b.a cgformcolumnMeta, org.jeecg.modules.online.config.b.a datacolumnMeta) {
        return null;
    }

    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String dropIndexs(String indexName, String tableName) {
        return "DROP INDEX " + indexName + " ON " + tableName;
    }

    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String countIndex(String indexName, String tableName) {
        return "select COUNT(*) from information_schema.statistics where table_name = '" + tableName + "'  AND index_name = '" + indexName + org.jeecg.modules.online.cgform.d.b.sz;
    }
}
