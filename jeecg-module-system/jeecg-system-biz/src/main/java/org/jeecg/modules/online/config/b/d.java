package org.jeecg.modules.online.config.b;

import lombok.SneakyThrows;
import org.hibernate.Session;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.online.config.exception.DBException;
import org.jeecg.modules.online.config.service.DbTableHandleI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: DbTableUtil.java */
/* loaded from: hibernate-common-ol-5.4.74(2).jar:org/jeecg/modules/online/config/b/d.class */
public class d {
    private static final Logger b = LoggerFactory.getLogger(d.class);
    public static String a = "";

    public static DbTableHandleI getTableHandle() throws SQLException, DBException {
        DbTableHandleI bVar = null;
        String databaseType = getDatabaseType();
        boolean z = true;
        switch (databaseType.hashCode()) {
            case -1955532418:
                if (databaseType.equals("ORACLE")) {
                    z = true;
//                    bVar = new org.jeecg.modules.online.config.service.a.b();
                    bVar = new org.jeecg.modules.online.config.service.a.c();
                    break;
                }
                break;
            case -1620389036:
                if (databaseType.equals("POSTGRESQL")) {
                    z = true;
                    bVar = new org.jeecg.modules.online.config.service.a.d();
                    break;
                }
                break;
            case 2185:
                if (databaseType.equals("DM")) {
                    z = true;
                    bVar = new org.jeecg.modules.online.config.service.a.a();
                    break;
                }
                break;
            case 73844866:
                if (databaseType.equals("MYSQL")) {
                    z = false;
                    bVar = new org.jeecg.modules.online.config.service.a.b();
                    break;
                }
                break;
            case 912124529:
                if (databaseType.equals("SQLSERVER")) {
                    z = true;
                    bVar = new org.jeecg.modules.online.config.service.a.e();
                    break;
                }
                break;
        }
        return bVar;
    }

    public static Connection getConnection() throws SQLException {
        return ((DataSource) SpringContextUtils.getApplicationContext().getBean(DataSource.class)).getConnection();
    }

    public static String getDatabaseType() throws SQLException, DBException {
        if (oConvertUtils.isNotEmpty(a)) {
            return a;
        }
        return a((DataSource) SpringContextUtils.getApplicationContext().getBean(DataSource.class));
    }

    public static boolean a() {
        try {
            return "ORACLE".equals(getDatabaseType());
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (DBException e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public static String a(DataSource dataSource) throws SQLException, DBException {
        if ("".equals(a)) {
            Connection connection = dataSource.getConnection();
            try {
                try {
                    String lowerCase = connection.getMetaData().getDatabaseProductName().toLowerCase();
                    if (lowerCase.indexOf("mysql") >= 0) {
                        a = "MYSQL";
                    } else if (lowerCase.indexOf("oracle") >= 0) {
                        a = "ORACLE";
                    } else if (lowerCase.indexOf("dm") >= 0) {
                        a = "DM";
                    } else if (lowerCase.indexOf("sqlserver") >= 0 || lowerCase.indexOf("sql server") >= 0) {
                        a = "SQLSERVER";
                    } else if (lowerCase.indexOf("postgresql") >= 0) {
                        a = "POSTGRESQL";
                    } else {
                        throw new DBException("数据库类型:[" + lowerCase + "]不识别!");
                    }
                    connection.close();
                } catch (Exception e) {
                    b.error(e.getMessage(), e);
                    connection.close();
                }
            } catch (Throwable th) {
                connection.close();
                throw th;
            }
        }
        return a;
    }

    public static String a(Connection connection) throws SQLException, DBException {
        if ("".equals(a)) {
            String lowerCase = connection.getMetaData().getDatabaseProductName().toLowerCase();
            if (lowerCase.indexOf("mysql") >= 0) {
                a = "MYSQL";
            } else if (lowerCase.indexOf("oracle") >= 0) {
                a = "ORACLE";
            } else if (lowerCase.indexOf("sqlserver") >= 0 || lowerCase.indexOf("sql server") >= 0) {
                a = "SQLSERVER";
            } else if (lowerCase.indexOf("postgresql") >= 0) {
                a = "POSTGRESQL";
            } else {
                throw new DBException("数据库类型:[" + lowerCase + "]不识别!");
            }
        }
        return a;
    }

    public static String a(Session session) throws SQLException, DBException {
        return getDatabaseType();
    }

    public static String a(String str, String str2) {
        boolean z = true;
        switch (str2.hashCode()) {
            case -1955532418:
                if (str2.equals("ORACLE")) {
                    z = false;
                    return str.toUpperCase();
                }
                break;
            case -1620389036:
                if (str2.equals("POSTGRESQL")) {
                    z = true;
                    return str.toLowerCase();
                }
                break;
        }
        return str;
    }

    @SneakyThrows
    public static Boolean a(String str) {
        ResultSet tables;
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            try {
                String[] strArr = {"TABLE"};
                Connection connection2 = getConnection();
                DatabaseMetaData metaData = connection2.getMetaData();
                String str2 = null;
                try {
                    str2 = getDatabaseType();
                } catch (DBException e) {
                    e.printStackTrace();
                }
                String a2 = a(str, str2);
                String username = ((org.jeecg.modules.online.config.a.b) SpringContextUtils.getBean(org.jeecg.modules.online.config.a.b.class)).getUsername();
                if ("ORACLE".equals(str2) || "DM".equals(str2)) {
                    username = username != null ? username.toUpperCase() : null;
                }
                // metaData.getTables(connection2.getCatalog(), username, a2, strArr);
                if ("SQLSERVER".equals(str2) || "POSTGRESQL".equals(str2)) {
                    tables = metaData.getTables(connection2.getCatalog(), null, a2, strArr);
                } else {
                    tables = metaData.getTables(connection2.getCatalog(), username, a2, strArr);
                }
                if (tables.next()) {
                    b.info("数据库表：【" + str + "】已存在");
                    if (tables != null) {
                        try {
                            tables.close();
                        } catch (SQLException e2) {
                            b.error(e2.getMessage(), e2);
                        }
                    }
                    if (connection2 != null) {
                        connection2.close();
                    }
                    return true;
                }
                if (tables != null) {
                    try {
                        tables.close();
                    } catch (SQLException e3) {
                        b.error(e3.getMessage(), e3);
                    }
                }
                if (connection2 != null) {
                    connection2.close();
                }
                return false;
            } catch (SQLException e4) {
                throw new RuntimeException();
            }
        } catch (Throwable th) {
            if (0 != 0) {
                try {
                    resultSet.close();
                } catch (SQLException e5) {
                    b.error(e5.getMessage(), e5);
                    throw th;
                }
            }
            if (0 != 0) {
                connection.close();
            }
            throw th;
        }
    }

    public static Map<String, Object> a(List<Map<String, Object>> list) {
        HashMap hashMap = new HashMap();
        for (int i = 0; i < list.size(); i++) {
            hashMap.put(list.get(i).get("column_name").toString(), list.get(i));
        }
        return hashMap;
    }

    public static String getDialect() throws SQLException, DBException {
        return b(getDatabaseType());
    }

    public static String b(String str) throws SQLException, DBException {
        String str2 = "org.hibernate.dialect.MySQL5InnoDBDialect";
        switch (str) {
            case "ORACLE":
                str2 = "org.hibernate.dialect.OracleDialect";
                break;
            case "POSTGRESQL":
                str2 = "org.hibernate.dialect.PostgreSQLDialect";
                break;
            case "DM":
                str2 = "org.hibernate.dialect.DmDialect";
                break;
            case "SQLSERVER":
                str2 = "org.hibernate.dialect.SQLServerDialect";
                break;
        }
        return str2;
    }

    public static String c(String str) {
        return str;
    }
}
