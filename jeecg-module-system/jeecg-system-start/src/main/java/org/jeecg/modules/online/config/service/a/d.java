package org.jeecg.modules.online.config.service.a;

import org.apache.commons.lang.StringUtils;
import org.jeecg.modules.online.cgform.d.i;
import org.jeecg.modules.online.config.exception.DBException;
import org.jeecg.modules.online.config.service.DbTableHandleI;

/* compiled from: DbTablePostgresHandleImpl.java */
/* loaded from: hibernate-common-ol-5.4.74(2).jar:org/jeecg/modules/online/config/service/a/d.class */
public class d implements DbTableHandleI {
    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String getAddColumnSql(org.jeecg.modules.online.config.b.a columnMeta) {
        return " ADD COLUMN " + a(columnMeta) + ";";
    }

    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String getReNameFieldName(org.jeecg.modules.online.config.b.a columnMeta) {
        return " RENAME  COLUMN  " + columnMeta.getOldColumnName() + " to " + columnMeta.getColumnName() + ";";
    }

    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String getUpdateColumnSql(org.jeecg.modules.online.config.b.a cgformcolumnMeta, org.jeecg.modules.online.config.b.a datacolumnMeta) throws DBException {
        return "  ALTER  COLUMN   " + a(cgformcolumnMeta, datacolumnMeta) + ";";
    }

    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String getSpecialHandle(org.jeecg.modules.online.config.b.a cgformcolumnMeta, org.jeecg.modules.online.config.b.a datacolumnMeta) {
        return "  ALTER  COLUMN   " + b(cgformcolumnMeta, datacolumnMeta) + ";";
    }

    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String getMatchClassTypeByDataType(String dataType, int digits) {
        String str = "";
        if (dataType.equalsIgnoreCase(org.jeecg.modules.online.config.b.b.l)) {
            str = org.jeecg.modules.online.config.b.b.i;
        } else if (dataType.equalsIgnoreCase("double")) {
            str = "double";
        } else if (dataType.contains("int")) {
            str = "int";
        } else if (dataType.equalsIgnoreCase("Date")) {
            str = i.d;
        } else if (dataType.equalsIgnoreCase("timestamp")) {
            str = i.d;
        } else if (dataType.equalsIgnoreCase("bytea")) {
            str = "blob";
        } else if (dataType.equalsIgnoreCase("text")) {
            str = "text";
        } else if (dataType.equalsIgnoreCase(org.jeecg.modules.online.config.b.b.e)) {
            str = "bigdecimal";
        } else if (dataType.equalsIgnoreCase("numeric")) {
            str = "bigdecimal";
        }
        return str;
    }

    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String dropTableSQL(String tableName) {
        return " DROP TABLE  " + tableName + " ;";
    }

    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String getDropColumnSql(String fieldName) {
        return " DROP COLUMN " + fieldName + ";";
    }

    private String a(org.jeecg.modules.online.config.b.a aVar, org.jeecg.modules.online.config.b.a aVar2) throws DBException {
        String str = "";
        if (aVar.getColunmType().equalsIgnoreCase(org.jeecg.modules.online.config.b.b.i)) {
            str = aVar.getColumnName() + "  type character varying(" + aVar.getColumnSize() + ") ";
        } else if (aVar.getColunmType().equalsIgnoreCase(i.d)) {
            str = aVar.getColumnName() + "  type timestamp ";
        } else if (aVar.getColunmType().equalsIgnoreCase("int")) {
            str = aVar.getColumnName() + " type int4 ";
        } else if (aVar.getColunmType().equalsIgnoreCase("double")) {
            str = aVar.getColumnName() + " type  numeric(" + aVar.getColumnSize() + org.jeecg.modules.online.cgform.d.b.sB + aVar.getDecimalDigits() + ") ";
        } else if (aVar.getColunmType().equalsIgnoreCase("BigDecimal")) {
            str = aVar.getColumnName() + " type  decimal(" + aVar.getColumnSize() + org.jeecg.modules.online.cgform.d.b.sB + aVar.getDecimalDigits() + ") ";
        } else if (aVar.getColunmType().equalsIgnoreCase("text")) {
            str = aVar.getColumnName() + " text ";
        } else if (aVar.getColunmType().equalsIgnoreCase("blob")) {
            throw new DBException("blob类型不可修改");
        }
        return str;
    }

    private String b(org.jeecg.modules.online.config.b.a aVar, org.jeecg.modules.online.config.b.a aVar2) {
        String str = "";
        if (!aVar.a(aVar2)) {
            if (aVar.getColunmType().equalsIgnoreCase(org.jeecg.modules.online.config.b.b.i)) {
                str = aVar.getColumnName() + (StringUtils.isNotEmpty(aVar.getFieldDefault()) ? " SET DEFAULT " + aVar.getFieldDefault() : " DROP DEFAULT");
            } else if (aVar.getColunmType().equalsIgnoreCase(i.d)) {
                str = aVar.getColumnName() + (StringUtils.isNotEmpty(aVar.getFieldDefault()) ? " SET DEFAULT " + aVar.getFieldDefault() : " DROP DEFAULT");
            } else if (aVar.getColunmType().equalsIgnoreCase("int")) {
                str = aVar.getColumnName() + (StringUtils.isNotEmpty(aVar.getFieldDefault()) ? " SET DEFAULT " + aVar.getFieldDefault() : " DROP DEFAULT");
            } else if (aVar.getColunmType().equalsIgnoreCase("double")) {
                str = aVar.getColumnName() + (StringUtils.isNotEmpty(aVar.getFieldDefault()) ? " SET DEFAULT " + aVar.getFieldDefault() : " DROP DEFAULT");
            } else if (aVar.getColunmType().equalsIgnoreCase("bigdecimal")) {
                str = aVar.getColumnName() + (StringUtils.isNotEmpty(aVar.getFieldDefault()) ? " SET DEFAULT " + aVar.getFieldDefault() : " DROP DEFAULT");
            } else if (aVar.getColunmType().equalsIgnoreCase("text")) {
                str = aVar.getColumnName() + (StringUtils.isNotEmpty(aVar.getFieldDefault()) ? " SET DEFAULT " + aVar.getFieldDefault() : " DROP DEFAULT");
            }
        }
        return str;
    }

    private String a(org.jeecg.modules.online.config.b.a aVar) {
        String str = "";
        if (aVar.getColunmType().equalsIgnoreCase(org.jeecg.modules.online.config.b.b.i)) {
            str = aVar.getColumnName() + " character varying(" + aVar.getColumnSize() + ") ";
        } else if (aVar.getColunmType().equalsIgnoreCase(i.d)) {
            str = aVar.getColumnName() + " timestamp ";
        } else if (aVar.getColunmType().equalsIgnoreCase("int")) {
            str = aVar.getColumnName() + " int4";
        } else if (aVar.getColunmType().equalsIgnoreCase("double")) {
            str = aVar.getColumnName() + " numeric(" + aVar.getColumnSize() + org.jeecg.modules.online.cgform.d.b.sB + aVar.getDecimalDigits() + ") ";
        } else if (aVar.getColunmType().equalsIgnoreCase("bigdecimal")) {
            str = aVar.getColumnName() + " decimal(" + aVar.getColumnSize() + org.jeecg.modules.online.cgform.d.b.sB + aVar.getDecimalDigits() + ") ";
        } else if (aVar.getColunmType().equalsIgnoreCase("blob")) {
            str = aVar.getColumnName() + " bytea(" + aVar.getColumnSize() + ") ";
        } else if (aVar.getColunmType().equalsIgnoreCase("text")) {
            str = aVar.getColumnName() + " text ";
        }
        return str + (StringUtils.isNotEmpty(aVar.getFieldDefault()) ? " DEFAULT " + aVar.getFieldDefault() : " ");
    }

    private String b(org.jeecg.modules.online.config.b.a aVar) {
        String str = "";
        if (aVar.getColunmType().equalsIgnoreCase(org.jeecg.modules.online.config.b.b.i)) {
            str = aVar.getColumnName() + " character varying(" + aVar.getColumnSize() + ") ";
        } else if (aVar.getColunmType().equalsIgnoreCase(i.d)) {
            str = aVar.getColumnName() + " datetime ";
        } else if (aVar.getColunmType().equalsIgnoreCase("int")) {
            str = aVar.getColumnName() + " int(" + aVar.getColumnSize() + ") ";
        } else if (aVar.getColunmType().equalsIgnoreCase("double")) {
            str = aVar.getColumnName() + " numeric(" + aVar.getColumnSize() + org.jeecg.modules.online.cgform.d.b.sB + aVar.getDecimalDigits() + ") ";
        }
        return str;
    }

    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String getCommentSql(org.jeecg.modules.online.config.b.a columnMeta) {
        return "COMMENT ON COLUMN " + columnMeta.getTableName() + "." + columnMeta.getColumnName() + " IS '" + columnMeta.getComment() + org.jeecg.modules.online.cgform.d.b.sz;
    }

    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String dropIndexs(String indexName, String tableName) {
        return "DROP INDEX " + indexName;
    }

    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String countIndex(String indexName, String tableName) {
        return "SELECT count(*) FROM pg_indexes WHERE indexname = '" + indexName + "' and tablename = '" + tableName + org.jeecg.modules.online.cgform.d.b.sz;
    }
}