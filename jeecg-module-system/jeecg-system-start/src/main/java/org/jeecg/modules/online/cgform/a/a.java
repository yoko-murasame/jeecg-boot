package org.jeecg.modules.online.cgform.a;

import org.jeecg.modules.online.cgform.d.b;

/* compiled from: LinkDown.java */
/* loaded from: hibernate-common-ol-5.4.74(2).jar:org/jeecg/modules/online/cgform/a/a.class */
public class a {
    private String sa;
    private String sb;
    private String sc;
    private String sd;
    private String se;
    private String sf;
    private String sg;
    private String sh;

    public void setTable(String table) {
        this.sa = table;
    }

    public void setTxt(String txt) {
        this.sb = txt;
    }

    public void setKey(String key) {
        this.sc = key;
    }

    public void setLinkField(String linkField) {
        this.sd = linkField;
    }

    public void setIdField(String idField) {
        this.se = idField;
    }

    public void setPidField(String pidField) {
        this.sf = pidField;
    }

    public void setPidValue(String pidValue) {
        this.sg = pidValue;
    }

    public void setCondition(String condition) {
        this.sh = condition;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof a) {
            a aVar = (a) o;
            if (aVar.a(this)) {
                String table = getTable();
                String table2 = aVar.getTable();
                if (table == null) {
                    if (table2 != null) {
                        return false;
                    }
                } else if (!table.equals(table2)) {
                    return false;
                }
                String txt = getTxt();
                String txt2 = aVar.getTxt();
                if (txt == null) {
                    if (txt2 != null) {
                        return false;
                    }
                } else if (!txt.equals(txt2)) {
                    return false;
                }
                String key = getKey();
                String key2 = aVar.getKey();
                if (key == null) {
                    if (key2 != null) {
                        return false;
                    }
                } else if (!key.equals(key2)) {
                    return false;
                }
                String linkField = getLinkField();
                String linkField2 = aVar.getLinkField();
                if (linkField == null) {
                    if (linkField2 != null) {
                        return false;
                    }
                } else if (!linkField.equals(linkField2)) {
                    return false;
                }
                String idField = getIdField();
                String idField2 = aVar.getIdField();
                if (idField == null) {
                    if (idField2 != null) {
                        return false;
                    }
                } else if (!idField.equals(idField2)) {
                    return false;
                }
                String pidField = getPidField();
                String pidField2 = aVar.getPidField();
                if (pidField == null) {
                    if (pidField2 != null) {
                        return false;
                    }
                } else if (!pidField.equals(pidField2)) {
                    return false;
                }
                String pidValue = getPidValue();
                String pidValue2 = aVar.getPidValue();
                if (pidValue == null) {
                    if (pidValue2 != null) {
                        return false;
                    }
                } else if (!pidValue.equals(pidValue2)) {
                    return false;
                }
                String condition = getCondition();
                String condition2 = aVar.getCondition();
                return condition == null ? condition2 == null : condition.equals(condition2);
            }
            return false;
        }
        return false;
    }

    protected boolean a(Object obj) {
        return obj instanceof a;
    }

    public int hashCode() {
        String table = getTable();
        int hashCode = (1 * 59) + (table == null ? 43 : table.hashCode());
        String txt = getTxt();
        int hashCode2 = (hashCode * 59) + (txt == null ? 43 : txt.hashCode());
        String key = getKey();
        int hashCode3 = (hashCode2 * 59) + (key == null ? 43 : key.hashCode());
        String linkField = getLinkField();
        int hashCode4 = (hashCode3 * 59) + (linkField == null ? 43 : linkField.hashCode());
        String idField = getIdField();
        int hashCode5 = (hashCode4 * 59) + (idField == null ? 43 : idField.hashCode());
        String pidField = getPidField();
        int hashCode6 = (hashCode5 * 59) + (pidField == null ? 43 : pidField.hashCode());
        String pidValue = getPidValue();
        int hashCode7 = (hashCode6 * 59) + (pidValue == null ? 43 : pidValue.hashCode());
        String condition = getCondition();
        return (hashCode7 * 59) + (condition == null ? 43 : condition.hashCode());
    }

    public String toString() {
        return "LinkDown(table=" + getTable() + ", txt=" + getTxt() + ", key=" + getKey() + ", linkField=" + getLinkField() + ", idField=" + getIdField() + ", pidField=" + getPidField() + ", pidValue=" + getPidValue() + ", condition=" + getCondition() + ")";
    }

    public String getTable() {
        return this.sa;
    }

    public String getTxt() {
        return this.sb;
    }

    public String getKey() {
        return this.sc;
    }

    public String getLinkField() {
        return this.sd;
    }

    public String getIdField() {
        return this.se;
    }

    public String getPidField() {
        return this.sf;
    }

    public String getPidValue() {
        return this.sg;
    }

    public String getCondition() {
        return this.sh;
    }

    private String getQuerySql() {
        new StringBuffer().append(b.sa);
        return null;
    }
}