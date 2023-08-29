package org.jeecg.modules.online.config.b;

import org.apache.commons.lang.StringUtils;
import org.jeecg.modules.online.cgform.d.i;


/* compiled from: ColumnMeta.java */
/* loaded from: hibernate-common-ol-5.4.74(2).jar:org/jeecg/modules/online/config/b/a.class */
public class a {
    private String sa;
    private String sb;
    private String sc;
    private int sd;
    private String se;
    private String sf;
    private String sg;
    private int sh;
    private String si;
    private String sj;
    private String sk;
    private String sl;

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof a)) {
            return false;
        }
        a aVar = (a) obj;
        return (this.se.contains(i.d) || this.se.contains("blob") || this.se.contains("text")) ? this.sc.equals(aVar.getColumnName()) && this.si.equals(aVar.si) && a(this.sf, aVar.getComment()) && a(this.sg, aVar.getFieldDefault()) : this.se.equals(aVar.getColunmType()) && this.si.equals(aVar.si) && this.sd == aVar.getColumnSize() && a(this.sf, aVar.getComment()) && a(this.sg, aVar.getFieldDefault());
    }

    public boolean a(Object obj, String str) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof a)) {
            return false;
        }
        a aVar = (a) obj;
        return "SQLSERVER".equals(str) ? (this.se.contains(i.d) || this.se.contains("blob") || this.se.contains("text")) ? this.sc.equals(aVar.getColumnName()) && this.si.equals(aVar.si) : this.se.equals(aVar.getColunmType()) && this.si.equals(aVar.si) && this.sd == aVar.getColumnSize() && a(this.sg, aVar.getFieldDefault()) : "POSTGRESQL".equals(str) ? (this.se.contains(i.d) || this.se.contains("blob") || this.se.contains("text")) ? this.sc.equals(aVar.getColumnName()) && this.si.equals(aVar.si) : this.se.equals(aVar.getColunmType()) && this.si.equals(aVar.si) && this.sd == aVar.getColumnSize() && a(this.sg, aVar.getFieldDefault()) : ("ORACLE".equals(str) || "DM".equals(str)) ? (this.se.contains(i.d) || this.se.contains("blob") || this.se.contains("text")) ? this.sc.equals(aVar.getColumnName()) && this.si.equals(aVar.si) : this.se.equals(aVar.getColunmType()) && this.si.equals(aVar.si) && this.sd == aVar.getColumnSize() && a(this.sg, aVar.getFieldDefault()) : (this.se.contains(i.d) || this.se.contains("blob") || this.se.contains("text")) ? this.se.equals(aVar.getColunmType()) && this.sc.equals(aVar.getColumnName()) && this.si.equals(aVar.si) && a(this.sf, aVar.getComment()) && a(this.sg, aVar.getFieldDefault()) : this.se.equals(aVar.getColunmType()) && this.si.equals(aVar.si) && this.sd == aVar.getColumnSize() && a(this.sf, aVar.getComment()) && a(this.sg, aVar.getFieldDefault());
    }

    public boolean a(a aVar) {
        if (aVar == this) {
            return true;
        }
        return a(this.sf, aVar.getComment());
    }

    public boolean b(a aVar) {
        if (aVar == this) {
            return true;
        }
        return a(this.sf, aVar.getComment());
    }

    private boolean a(String str, String str2) {
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
        return this.sc;
    }

    public int getColumnSize() {
        return this.sd;
    }

    public String getColunmType() {
        return this.se;
    }

    public String getComment() {
        return this.sf;
    }

    public int getDecimalDigits() {
        return this.sh;
    }

    public String getIsNullable() {
        return this.si;
    }

    public String getOldColumnName() {
        return this.sk;
    }

    public int hashCode() {
        return this.sd + (this.se.hashCode() * this.sc.hashCode());
    }

    public void setColumnName(String columnName) {
        this.sc = columnName;
    }

    public void setColumnSize(int columnSize) {
        this.sd = columnSize;
    }

    public void setColunmType(String colunmType) {
        this.se = colunmType;
    }

    public void setComment(String comment) {
        this.sf = comment;
    }

    public void setDecimalDigits(int decimalDigits) {
        this.sh = decimalDigits;
    }

    public void setIsNullable(String isNullable) {
        this.si = isNullable;
    }

    public void setOldColumnName(String oldColumnName) {
        this.sk = oldColumnName;
    }

    public String toString() {
        return this.sc + org.jeecg.modules.online.cgform.d.b.sB + this.se + org.jeecg.modules.online.cgform.d.b.sB + this.si + org.jeecg.modules.online.cgform.d.b.sB + this.sd;
    }

    public String getColumnId() {
        return this.sb;
    }

    public void setColumnId(String columnId) {
        this.sb = columnId;
    }

    public String getTableName() {
        return this.sa;
    }

    public void setTableName(String tableName) {
        this.sa = tableName;
    }

    public String getFieldDefault() {
        return this.sg;
    }

    public void setFieldDefault(String fieldDefault) {
        this.sg = fieldDefault;
    }

    public String getPkType() {
        return this.sj;
    }

    public void setPkType(String pkType) {
        this.sj = pkType;
    }

    public String getRealDbType() {
        return this.sl;
    }

    public void setRealDbType(String realDbType) {
        this.sl = realDbType;
    }
}