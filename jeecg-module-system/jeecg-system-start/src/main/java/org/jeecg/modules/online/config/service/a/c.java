package org.jeecg.modules.online.config.service.a;

import org.apache.commons.lang.StringUtils;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.online.cgform.d.i;
import org.jeecg.modules.online.config.service.DbTableHandleI;

/* compiled from: DbTableOracleHandleImpl.java */
/* loaded from: hibernate-common-ol-5.4.74(2).jar:org/jeecg/modules/online/config/service/a/c.class */
public class c implements DbTableHandleI {
    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String getAddColumnSql(org.jeecg.modules.online.config.b.a columnMeta) {
        return " ADD  " + a(columnMeta) + "";
    }

    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String getReNameFieldName(org.jeecg.modules.online.config.b.a columnMeta) {
        return "RENAME COLUMN  " + columnMeta.getOldColumnName() + " TO " + columnMeta.getColumnName() + "";
    }

    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String getUpdateColumnSql(org.jeecg.modules.online.config.b.a cgformcolumnMeta, org.jeecg.modules.online.config.b.a datacolumnMeta) {
        return " MODIFY   " + a(cgformcolumnMeta, datacolumnMeta) + "";
    }

    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String getMatchClassTypeByDataType(String dataType, int digits) {
        String str = "";
        if (dataType.equalsIgnoreCase("varchar2")) {
            str = org.jeecg.modules.online.config.b.b.i;
        }
        if (dataType.equalsIgnoreCase("nvarchar2")) {
            str = org.jeecg.modules.online.config.b.b.i;
        } else if (dataType.equalsIgnoreCase("double")) {
            str = "double";
        } else if (dataType.equalsIgnoreCase(org.jeecg.modules.online.config.b.b.h) && digits == 0) {
            str = "int";
        } else if (dataType.equalsIgnoreCase(org.jeecg.modules.online.config.b.b.h) && digits != 0) {
            str = "double";
        } else if (dataType.equalsIgnoreCase("int")) {
            str = "int";
        } else if (dataType.equalsIgnoreCase("Date")) {
            str = i.DATE;
        } else if (dataType.equalsIgnoreCase("Datetime")) {
            str = i.DATE;
        } else if (dataType.equalsIgnoreCase("blob")) {
            str = "blob";
        } else if (dataType.equalsIgnoreCase("clob")) {
            str = "text";
        }
        return str;
    }

    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String dropTableSQL(String tableName) {
        return " DROP TABLE  " + tableName.toLowerCase() + " ";
    }

    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String getDropColumnSql(String fieldName) {
        return " DROP COLUMN " + fieldName.toUpperCase() + "";
    }

    private String a(org.jeecg.modules.online.config.b.a aVar) {
        String str = "";
        if (aVar.getColunmType().equalsIgnoreCase(org.jeecg.modules.online.config.b.b.i)) {
            str = aVar.getColumnName() + " varchar2(" + aVar.getColumnSize() + ")";
        } else if (aVar.getColunmType().equalsIgnoreCase(i.DATE)) {
            str = aVar.getColumnName() + " date";
        } else if (aVar.getColunmType().equalsIgnoreCase("int")) {
            str = aVar.getColumnName() + " NUMBER(" + aVar.getColumnSize() + ")";
        } else if (aVar.getColunmType().equalsIgnoreCase("double")) {
            str = aVar.getColumnName() + " NUMBER(" + aVar.getColumnSize() + org.jeecg.modules.online.cgform.d.b.DOT_STRING + aVar.getDecimalDigits() + ")";
        } else if (aVar.getColunmType().equalsIgnoreCase("bigdecimal")) {
            str = aVar.getColumnName() + " NUMBER(" + aVar.getColumnSize() + org.jeecg.modules.online.cgform.d.b.DOT_STRING + aVar.getDecimalDigits() + ")";
        } else if (aVar.getColunmType().equalsIgnoreCase("text")) {
            str = aVar.getColumnName() + " CLOB ";
        } else if (aVar.getColunmType().equalsIgnoreCase("blob")) {
            str = aVar.getColumnName() + " BLOB ";
        }
        return (str + (StringUtils.isNotEmpty(aVar.getFieldDefault()) ? " DEFAULT " + aVar.getFieldDefault() : " ")) + ("Y".equals(aVar.getIsNullable()) ? " NULL" : " NOT NULL");
    }

    private String a(org.jeecg.modules.online.config.b.a aVar, org.jeecg.modules.online.config.b.a aVar2) {
        String str = "";
        String str2 = "";
        String realDbType = aVar2.getRealDbType();
        if (!aVar2.getIsNullable().equals(aVar.getIsNullable())) {
            str2 = aVar.getIsNullable().equals("Y") ? "NULL" : "NOT NULL";
        }
        if (aVar.getColunmType().equalsIgnoreCase(org.jeecg.modules.online.config.b.b.i)) {
            str = aVar.getColumnName() + " " + ((oConvertUtils.isEmpty(realDbType) || realDbType.toLowerCase().indexOf(org.jeecg.modules.online.config.b.b.l) < 0) ? "varchar2" : "varchar2") + "(" + aVar.getColumnSize() + ")" + str2;
        } else if (aVar.getColunmType().equalsIgnoreCase(i.DATE)) {
            str = aVar.getColumnName() + " date " + str2;
        } else if (aVar.getColunmType().equalsIgnoreCase("int")) {
            str = aVar.getColumnName() + " NUMBER(" + aVar.getColumnSize() + ") " + str2;
        } else if (aVar.getColunmType().equalsIgnoreCase("double")) {
            str = aVar.getColumnName() + " NUMBER(" + aVar.getColumnSize() + org.jeecg.modules.online.cgform.d.b.DOT_STRING + aVar.getDecimalDigits() + ") " + str2;
        } else if (aVar.getColunmType().equalsIgnoreCase("bigdecimal")) {
            str = aVar.getColumnName() + " NUMBER(" + aVar.getColumnSize() + org.jeecg.modules.online.cgform.d.b.DOT_STRING + aVar.getDecimalDigits() + ") " + str2;
        } else if (aVar.getColunmType().equalsIgnoreCase("blob")) {
            str = aVar.getColumnName() + " BLOB " + str2;
        } else if (aVar.getColunmType().equalsIgnoreCase("text")) {
            str = aVar.getColumnName() + " CLOB " + str2;
        }
        return (str + (StringUtils.isNotEmpty(aVar.getFieldDefault()) ? " DEFAULT " + aVar.getFieldDefault() : " ")) + str2;
    }

    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String getCommentSql(org.jeecg.modules.online.config.b.a columnMeta) {
        return "COMMENT ON COLUMN " + columnMeta.getTableName() + "." + columnMeta.getColumnName() + " IS '" + columnMeta.getComment() + org.jeecg.modules.online.cgform.d.b.sz;
    }

    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String getSpecialHandle(org.jeecg.modules.online.config.b.a cgformcolumnMeta, org.jeecg.modules.online.config.b.a datacolumnMeta) {
        return null;
    }

    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String dropIndexs(String indexName, String tableName) {
        return "DROP INDEX " + indexName;
    }

    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String countIndex(String indexName, String tableName) {
        return "select count(*) from user_ind_columns where index_name=upper('" + indexName + "')";
    }
}
