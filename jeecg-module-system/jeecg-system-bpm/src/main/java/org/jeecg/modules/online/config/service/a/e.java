package org.jeecg.modules.online.config.service.a;

import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.online.cgform.d.i;
import org.jeecg.modules.online.config.service.DbTableHandleI;

/* compiled from: DbTableSQLServerHandleImpl.java */
/* loaded from: hibernate-common-ol-5.4.74(2).jar:org/jeecg/modules/online/config/service/a/e.class */
public class e implements DbTableHandleI {
    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String getAddColumnSql(org.jeecg.modules.online.config.b.a columnMeta) {
        return " ADD  " + a(columnMeta) + ";";
    }

    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String getReNameFieldName(org.jeecg.modules.online.config.b.a columnMeta) {
        return "  sp_rename '" + columnMeta.getTableName() + "." + columnMeta.getOldColumnName() + "', '" + columnMeta.getColumnName() + "', 'COLUMN';";
    }

    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String getUpdateColumnSql(org.jeecg.modules.online.config.b.a cgformcolumnMeta, org.jeecg.modules.online.config.b.a datacolumnMeta) {
        return " ALTER COLUMN  " + a(cgformcolumnMeta, datacolumnMeta) + ";";
    }

    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String getMatchClassTypeByDataType(String dataType, int digits) {
        String str = "";
        if (dataType.equalsIgnoreCase(org.jeecg.modules.online.config.b.b.l) || dataType.equalsIgnoreCase("nvarchar")) {
            str = org.jeecg.modules.online.config.b.b.i;
        } else if (dataType.equalsIgnoreCase("float")) {
            str = "double";
        } else if (dataType.equalsIgnoreCase("int")) {
            str = "int";
        } else if (dataType.equalsIgnoreCase("Date")) {
            str = i.DATE;
        } else if (dataType.equalsIgnoreCase("Datetime")) {
            str = i.DATE;
        } else if (dataType.equalsIgnoreCase("numeric")) {
            str = "bigdecimal";
        } else if (dataType.equalsIgnoreCase("varbinary") || dataType.equalsIgnoreCase("image")) {
            str = "blob";
        } else if (dataType.equalsIgnoreCase("text") || dataType.equalsIgnoreCase("ntext")) {
            str = "text";
        }
        return str;
    }

    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String dropTableSQL(String tableName) {
        return " DROP TABLE " + tableName + " ;";
    }

    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String getDropColumnSql(String fieldName) {
        return " DROP COLUMN " + fieldName + ";";
    }

    private String a(org.jeecg.modules.online.config.b.a aVar, org.jeecg.modules.online.config.b.a aVar2) {
        String str = "";
        if (aVar.getColunmType().equalsIgnoreCase(org.jeecg.modules.online.config.b.b.i)) {
            str = aVar.getColumnName() + " nvarchar(" + aVar.getColumnSize() + ") " + ("Y".equals(aVar.getIsNullable()) ? "NULL" : "NOT NULL");
        } else if (aVar.getColunmType().equalsIgnoreCase(i.DATE)) {
            str = aVar.getColumnName() + " datetime " + ("Y".equals(aVar.getIsNullable()) ? "NULL" : "NOT NULL");
        } else if (aVar.getColunmType().equalsIgnoreCase("int")) {
            str = aVar.getColumnName() + " int " + ("Y".equals(aVar.getIsNullable()) ? "NULL" : "NOT NULL");
        } else if (aVar.getColunmType().equalsIgnoreCase("double")) {
            str = aVar.getColumnName() + " float " + ("Y".equals(aVar.getIsNullable()) ? "NULL" : "NOT NULL");
        } else if (aVar.getColunmType().equalsIgnoreCase("bigdecimal")) {
            str = aVar.getColumnName() + " numeric(" + aVar.getColumnSize() + org.jeecg.modules.online.cgform.d.b.DOT_STRING + aVar.getDecimalDigits() + ") " + ("Y".equals(aVar.getIsNullable()) ? "NULL" : "NOT NULL");
        } else if (aVar.getColunmType().equalsIgnoreCase("text")) {
            str = aVar.getColumnName() + " ntext " + ("Y".equals(aVar.getIsNullable()) ? "NULL" : "NOT NULL");
        } else if (aVar.getColunmType().equalsIgnoreCase("blob")) {
            str = aVar.getColumnName() + " image";
        }
        return str;
    }

    private String a(org.jeecg.modules.online.config.b.a aVar) {
        String str = "";
        if (aVar.getColunmType().equalsIgnoreCase(org.jeecg.modules.online.config.b.b.i)) {
            str = aVar.getColumnName() + " nvarchar(" + aVar.getColumnSize() + ") " + ("Y".equals(aVar.getIsNullable()) ? "NULL" : "NOT NULL");
        } else if (aVar.getColunmType().equalsIgnoreCase(i.DATE)) {
            str = aVar.getColumnName() + " datetime " + ("Y".equals(aVar.getIsNullable()) ? "NULL" : "NOT NULL");
        } else if (aVar.getColunmType().equalsIgnoreCase("int")) {
            str = aVar.getColumnName() + " int " + ("Y".equals(aVar.getIsNullable()) ? "NULL" : "NOT NULL");
        } else if (aVar.getColunmType().equalsIgnoreCase("double")) {
            str = aVar.getColumnName() + " float " + ("Y".equals(aVar.getIsNullable()) ? "NULL" : "NOT NULL");
        } else if (aVar.getColunmType().equalsIgnoreCase("bigdecimal")) {
            str = aVar.getColumnName() + " numeric(" + aVar.getColumnSize() + org.jeecg.modules.online.cgform.d.b.DOT_STRING + aVar.getDecimalDigits() + ") " + ("Y".equals(aVar.getIsNullable()) ? "NULL" : "NOT NULL");
        } else if (aVar.getColunmType().equalsIgnoreCase("text")) {
            str = aVar.getColumnName() + " ntext " + ("Y".equals(aVar.getIsNullable()) ? "NULL" : "NOT NULL");
        } else if (aVar.getColunmType().equalsIgnoreCase("blob")) {
            str = aVar.getColumnName() + " image";
        }
        return str;
    }

    private String b(org.jeecg.modules.online.config.b.a aVar) {
        String str = "";
        if (aVar.getColunmType().equalsIgnoreCase(org.jeecg.modules.online.config.b.b.i)) {
            str = aVar.getColumnName() + " nvarchar(" + aVar.getColumnSize() + ") " + ("Y".equals(aVar.getIsNullable()) ? "NULL" : "NOT NULL");
        } else if (aVar.getColunmType().equalsIgnoreCase(i.DATE)) {
            str = aVar.getColumnName() + " datetime " + ("Y".equals(aVar.getIsNullable()) ? "NULL" : "NOT NULL");
        } else if (aVar.getColunmType().equalsIgnoreCase("int")) {
            str = aVar.getColumnName() + " int " + ("Y".equals(aVar.getIsNullable()) ? "NULL" : "NOT NULL");
        } else if (aVar.getColunmType().equalsIgnoreCase("double")) {
            str = aVar.getColumnName() + " float " + ("Y".equals(aVar.getIsNullable()) ? "NULL" : "NOT NULL");
        }
        return str;
    }

    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String getCommentSql(org.jeecg.modules.online.config.b.a columnMeta) {
        StringBuffer stringBuffer = new StringBuffer("EXECUTE ");
        if (oConvertUtils.isEmpty(columnMeta.getOldColumnName())) {
            stringBuffer.append("sp_addextendedproperty");
        } else {
            stringBuffer.append("sp_updateextendedproperty");
        }
        stringBuffer.append(" N'MS_Description', '");
        stringBuffer.append(columnMeta.getComment());
        stringBuffer.append("', N'SCHEMA', N'dbo', N'TABLE', N'");
        stringBuffer.append(columnMeta.getTableName());
        stringBuffer.append("', N'COLUMN', N'");
        stringBuffer.append(columnMeta.getColumnName() + org.jeecg.modules.online.cgform.d.b.SINGLE_QUOTE);
        return stringBuffer.toString();
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
        return "SELECT count(*) FROM sys.indexes WHERE object_id=OBJECT_ID('" + tableName + "') and NAME= '" + indexName + org.jeecg.modules.online.cgform.d.b.SINGLE_QUOTE;
    }
}
