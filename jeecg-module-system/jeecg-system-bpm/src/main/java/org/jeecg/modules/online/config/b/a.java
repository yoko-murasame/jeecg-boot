package org.jeecg.modules.online.config.b;

import org.apache.commons.lang.StringUtils;
import org.jeecg.modules.online.cgform.d.i;


/* compiled from: ColumnMeta.java */
/* loaded from: hibernate-common-ol-5.4.74(2).jar:org/jeecg/modules/online/config/b/a.class */
public class a {
    private String tableName;
    private String columnId;
    private String columnName;
    private int columnSize;
    private String columnType;
    private String comment;
    private String fieldDefault;
    private int decimalDigits;
    private String isNullable;
    private String pkType;
    private String oldColumnName;
    private String realDbType;

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof a)) {
            return false;
        }
        a aVar = (a) obj;
        // return (this.columnType.contains(i.DATE) || this.columnType.contains("blob") || this.columnType.contains("text")) ? this.columnName.equals(aVar.getColumnName()) && this.isNullable.equals(aVar.isNullable) && judgeEqual(this.comment, aVar.getComment()) && judgeEqual(this.fieldDefault, aVar.getFieldDefault()) : this.columnType.equals(aVar.getColunmType()) && this.isNullable.equals(aVar.isNullable) && this.columnSize == aVar.getColumnSize() && judgeEqual(this.comment, aVar.getComment()) && judgeEqual(this.fieldDefault, aVar.getFieldDefault());
        if (this.columnType.contains(i.DATE) || this.columnType.contains("blob") || this.columnType.contains("text")) {
            return this.columnName.equals(aVar.getColumnName()) && this.isNullable.equals(aVar.isNullable) && judgeEqual(this.comment, aVar.getComment()) && judgeEqual(this.fieldDefault, aVar.getFieldDefault());
        } else {
            return this.columnType.equals(aVar.getColunmType()) && this.isNullable.equals(aVar.isNullable) && this.columnSize == aVar.getColumnSize() && judgeEqual(this.comment, aVar.getComment()) && judgeEqual(this.fieldDefault, aVar.getFieldDefault());
        }

    }

    public boolean equals(Object obj, String dbTypeName) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof a)) {
            return false;
        }
        a aVar = (a) obj;
        // return "SQLSERVER".equals(dbTypeName) ? (this.se.contains(i.DATE) || this.se.contains("blob") || this.se.contains("text")) ? this.sc.equals(aVar.getColumnName()) && this.si.equals(aVar.si) : this.se.equals(aVar.getColunmType()) && this.si.equals(aVar.si) && this.sd == aVar.getColumnSize() && a(this.sg, aVar.getFieldDefault()) : "POSTGRESQL".equals(dbTypeName) ? (this.se.contains(i.DATE) || this.se.contains("blob") || this.se.contains("text")) ? this.sc.equals(aVar.getColumnName()) && this.si.equals(aVar.si) : this.se.equals(aVar.getColunmType()) && this.si.equals(aVar.si) && this.sd == aVar.getColumnSize() && a(this.sg, aVar.getFieldDefault()) : ("ORACLE".equals(dbTypeName) || "DM".equals(dbTypeName)) ? (this.se.contains(i.DATE) || this.se.contains("blob") || this.se.contains("text")) ? this.sc.equals(aVar.getColumnName()) && this.si.equals(aVar.si) : this.se.equals(aVar.getColunmType()) && this.si.equals(aVar.si) && this.sd == aVar.getColumnSize() && a(this.sg, aVar.getFieldDefault()) : (this.se.contains(i.DATE) || this.se.contains("blob") || this.se.contains("text")) ? this.se.equals(aVar.getColunmType()) && this.sc.equals(aVar.getColumnName()) && this.si.equals(aVar.si) && a(this.sf, aVar.getComment()) && a(this.sg, aVar.getFieldDefault()) : this.se.equals(aVar.getColunmType()) && this.si.equals(aVar.si) && this.sd == aVar.getColumnSize() && a(this.sf, aVar.getComment()) && a(this.sg, aVar.getFieldDefault());
        if ("SQLSERVER".equals(dbTypeName)) {
            if (this.columnType.contains(i.DATE) || this.columnType.contains("blob") || this.columnType.contains("text")) {
                return this.columnName.equals(aVar.getColumnName()) && this.isNullable.equals(aVar.isNullable);
            } else {
                return this.columnType.equals(aVar.getColunmType()) && this.isNullable.equals(aVar.isNullable) && this.columnSize == aVar.getColumnSize() && judgeEqual(this.fieldDefault, aVar.getFieldDefault());
            }
        } else if ("POSTGRESQL".equals(dbTypeName)) {
            if (this.columnType.contains(i.DATE) || this.columnType.contains("blob") || this.columnType.contains("text")) {
                return this.columnName.equals(aVar.getColumnName()) && this.isNullable.equals(aVar.isNullable);
            } else if (this.columnType.contains("double") || this.columnType.contains("float") || this.columnType.contains("int")) {
                // 针对普通数值类型的精确判断（不需要校验实际位数）
                return this.columnType.equals(aVar.getColunmType()) && this.isNullable.equals(aVar.isNullable) && judgeEqual(this.fieldDefault, aVar.getFieldDefault());
            }else {
                return this.columnType.equals(aVar.getColunmType()) && this.isNullable.equals(aVar.isNullable) && this.columnSize == aVar.getColumnSize() && judgeEqual(this.fieldDefault, aVar.getFieldDefault());
            }
        } else if ("ORACLE".equals(dbTypeName) || "DM".equals(dbTypeName)) {
            if (this.columnType.contains(i.DATE) || this.columnType.contains("blob") || this.columnType.contains("text")) {
                return this.columnName.equals(aVar.getColumnName()) && this.isNullable.equals(aVar.isNullable);
            } else {
                return this.columnType.equals(aVar.getColunmType()) && this.isNullable.equals(aVar.isNullable) && this.columnSize == aVar.getColumnSize() && judgeEqual(this.fieldDefault, aVar.getFieldDefault());
            }
        } else {
            if (this.columnType.contains(i.DATE) || this.columnType.contains("blob") || this.columnType.contains("text")) {
                return this.columnType.equals(aVar.getColunmType()) && this.columnName.equals(aVar.getColumnName()) && this.isNullable.equals(aVar.isNullable) && judgeEqual(this.comment, aVar.getComment()) && judgeEqual(this.fieldDefault, aVar.getFieldDefault());
            } else {
                return this.columnType.equals(aVar.getColunmType()) && this.isNullable.equals(aVar.isNullable) && this.columnSize == aVar.getColumnSize() && judgeEqual(this.comment, aVar.getComment()) && judgeEqual(this.fieldDefault, aVar.getFieldDefault());
            }
        }
    }

    public boolean judgeEqualA(a aVar) {
        if (aVar == this) {
            return true;
        }
        return judgeEqual(this.comment, aVar.getComment());
    }

    public boolean judgeEqualB(a aVar) {
        if (aVar == this) {
            return true;
        }
        return judgeEqual(this.comment, aVar.getComment());
    }

    private boolean judgeEqual(String str, String str2) {
        boolean isNotEmpty = StringUtils.isNotEmpty(str);
        if (isNotEmpty != StringUtils.isNotEmpty(str2)) {
            return false;
        }
        if (isNotEmpty) {
            return str.equals(str2);
        }
        return true;
    }

    public String getColumnName() {
        return this.columnName;
    }

    public int getColumnSize() {
        return this.columnSize;
    }

    public String getColunmType() {
        return this.columnType;
    }

    public String getComment() {
        return this.comment;
    }

    public int getDecimalDigits() {
        return this.decimalDigits;
    }

    public String getIsNullable() {
        return this.isNullable;
    }

    public String getOldColumnName() {
        return this.oldColumnName;
    }

    public int hashCode() {
        return this.columnSize + (this.columnType.hashCode() * this.columnName.hashCode());
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public void setColumnSize(int columnSize) {
        this.columnSize = columnSize;
    }

    public void setColunmType(String colunmType) {
        this.columnType = colunmType;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setDecimalDigits(int decimalDigits) {
        this.decimalDigits = decimalDigits;
    }

    public void setIsNullable(String isNullable) {
        this.isNullable = isNullable;
    }

    public void setOldColumnName(String oldColumnName) {
        this.oldColumnName = oldColumnName;
    }

    public String toString() {
        return this.columnName + org.jeecg.modules.online.cgform.d.b.DOT_STRING + this.columnType + org.jeecg.modules.online.cgform.d.b.DOT_STRING + this.isNullable + org.jeecg.modules.online.cgform.d.b.DOT_STRING + this.columnSize;
    }

    public String getColumnId() {
        return this.columnId;
    }

    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getFieldDefault() {
        return this.fieldDefault;
    }

    public void setFieldDefault(String fieldDefault) {
        this.fieldDefault = fieldDefault;
    }

    public String getPkType() {
        return this.pkType;
    }

    public void setPkType(String pkType) {
        this.pkType = pkType;
    }

    public String getRealDbType() {
        return this.realDbType;
    }

    public void setRealDbType(String realDbType) {
        this.realDbType = realDbType;
    }
}
