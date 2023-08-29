package org.jeecg.modules.online.config.b;

import com.alibaba.druid.filter.config.ConfigTools;
import freemarker.template.TemplateException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;
import org.jeecg.modules.online.config.exception.DBException;
import org.jeecg.modules.online.config.service.DbTableHandleI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class c {
    private static final Logger a = LoggerFactory.getLogger(c.class);
    private static final String b = "org/jeecg/modules/online/config/engine/tableTemplate.ftl";
    private static DbTableHandleI dbTableHandleI;

    public c() throws SQLException, DBException {
        dbTableHandleI = d.getTableHandle();
    }

    public static void a(org.jeecg.modules.online.config.a.a aVar) throws IOException, TemplateException, HibernateException, SQLException, DBException {
        String message;
        String databaseType = d.getDatabaseType();
        if ("ORACLE".equals(databaseType)) {
            ArrayList arrayList = new ArrayList();
            for (OnlCgformField onlCgformField : aVar.getColumns()) {
                if ("int".equals(onlCgformField.getDbType())) {
                    onlCgformField.setDbType("double");
                    onlCgformField.setDbPointLength(0);
                }
                arrayList.add(onlCgformField);
            }
            aVar.setColumns(arrayList);
        }
        String a2 = g.a(b, a(aVar, databaseType));
        a.info(a2);
        HashMap hashMap = new HashMap();
        org.jeecg.modules.online.config.a.b dbConfig = aVar.getDbConfig();
        hashMap.put("hibernate.connection.driver_class", dbConfig.getDriverClassName());
        hashMap.put("hibernate.connection.url", dbConfig.getUrl());
        hashMap.put("hibernate.connection.username", dbConfig.getUsername());
        if (dbConfig.getDruid() != null && oConvertUtils.isNotEmpty(dbConfig.getDruid().getPublicKey())) {
            a.info(" dbconfig.getDruid().getPublicKey() = " + dbConfig.getDruid().getPublicKey());
            try {
                String decrypt = ConfigTools.decrypt(dbConfig.getDruid().getPublicKey(), dbConfig.getPassword());
                a.info(" 解密密码 decryptPassword = " + decrypt);
                hashMap.put("hibernate.connection.password", decrypt);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            hashMap.put("hibernate.connection.password", dbConfig.getPassword());
        }
        hashMap.put("hibernate.show_sql", true);
        hashMap.put("hibernate.format_sql", true);
        hashMap.put("hibernate.temp.use_jdbc_metadata_defaults", false);
        hashMap.put("hibernate.dialect", d.b(databaseType));
        hashMap.put("hibernate.hbm2ddl.auto", "create");
        hashMap.put("hibernate.connection.autocommit", false);
        hashMap.put("hibernate.current_session_context_class", "thread");
        MetadataSources metadataSources = new MetadataSources(new StandardServiceRegistryBuilder().applySettings(hashMap).build());
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(a2.getBytes("utf-8"));
        metadataSources.addInputStream(byteArrayInputStream);
        Metadata buildMetadata = metadataSources.buildMetadata();
        SchemaExport schemaExport = new SchemaExport();
        schemaExport.create(EnumSet.of(TargetType.DATABASE), buildMetadata);
        byteArrayInputStream.close();
        for (Object object : schemaExport.getExceptions()) {
            Exception exc= (Exception)object;
            if ("java.sql.SQLSyntaxErrorException".equals(exc.getCause().getClass().getName())) {
                SQLSyntaxErrorException sQLSyntaxErrorException = (SQLSyntaxErrorException) exc.getCause();
                if ("42000".equals(sQLSyntaxErrorException.getSQLState())) {
                    if (1064 == sQLSyntaxErrorException.getErrorCode() || 903 == sQLSyntaxErrorException.getErrorCode()) {
                        throw new DBException("请确认表名是否为关键字。");
                    }
                } else {
                    throw new DBException(exc.getMessage());
                }
            } else if ("com.microsoft.sqlserver.jdbc.SQLServerException".equals(exc.getCause().getClass().getName())) {
                if (exc.getCause().toString().indexOf("Incorrect syntax near the keyword") != -1) {
                    exc.printStackTrace();
                    throw new DBException(exc.getCause().getMessage());
                }
                a.error(exc.getMessage());
            } else {
                if ("DM".equals(databaseType) && (message = exc.getMessage()) != null && message.indexOf("Error executing DDL \"drop table") >= 0) {
                    a.error(message);
                }
                throw new DBException(exc.getMessage());
            }
        }
    }

    public List<String> b(org.jeecg.modules.online.config.a.a aVar) throws DBException, SQLException {
        String databaseType = d.getDatabaseType();
        String a2 = d.a(aVar.getTableName(), databaseType);
        String str = "alter table  " + a2 + " ";
        ArrayList arrayList = new ArrayList();
        try {
            Map<String, a> c2 = c(null, a2);
            Map<String, a> c3 = c(aVar);
            Map<String, String> a3 = a(aVar.getColumns());
            for (String str2 : c3.keySet()) {
                if (!c2.containsKey(str2)) {
                    a aVar2 = c3.get(str2);
                    String str3 = a3.get(str2);
                    if (a3.containsKey(str2) && c2.containsKey(str3)) {
                        a aVar3 = c2.get(str3);
                        String reNameFieldName = dbTableHandleI.getReNameFieldName(aVar2);
                        if ("SQLSERVER".equals(databaseType)) {
                            arrayList.add(reNameFieldName);
                        } else {
                            arrayList.add(str + reNameFieldName);
                        }
                        arrayList.add(d(str2, aVar2.getColumnId()));
                        if (!aVar3.equals(aVar2)) {
                            arrayList.add(str + a(aVar2, aVar3));
                            if ("POSTGRESQL".equals(databaseType)) {
                                arrayList.add(str + b(aVar2, aVar3));
                            }
                        }
                        if (!"SQLSERVER".equals(databaseType) && !aVar3.b(aVar2)) {
                            arrayList.add(c(aVar2));
                        }
                    } else {
                        arrayList.add(str + b(aVar2));
                        if (!"SQLSERVER".equals(databaseType) && StringUtils.isNotEmpty(aVar2.getComment())) {
                            arrayList.add(c(aVar2));
                        }
                    }
                } else {
                    a aVar4 = c2.get(str2);
                    a aVar5 = c3.get(str2);
                    if (!aVar4.a(aVar5, databaseType)) {
                        arrayList.add(str + a(aVar5, aVar4));
                    }
                    if (!"SQLSERVER".equals(databaseType) && !"ORACLE".equals(databaseType) && !aVar4.b(aVar5)) {
                        arrayList.add(c(aVar5));
                    }
                }
            }
            for (String str4 : c2.keySet()) {
                if (!c3.containsKey(str4.toLowerCase()) && !a3.containsValue(str4.toLowerCase())) {
                    arrayList.add(str + b(str4));
                }
            }
            a.info(" db update sql : " + arrayList.toString());
            return arrayList;
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    private static Map<String, Object> a(org.jeecg.modules.online.config.a.a aVar, String str) {
        HashMap hashMap = new HashMap();
        for (OnlCgformField onlCgformField : aVar.getColumns()) {
            onlCgformField.setDbDefaultVal(c(onlCgformField.getDbDefaultVal()));
        }
        hashMap.put("entity", aVar);
        hashMap.put("dataType", str);
        return hashMap;
    }

    private Map<String, a> c(String str, String str2) throws SQLException {
        ResultSet columns;
        HashMap hashMap = new HashMap();
        Connection connection = null;
        try {
            connection = d.getConnection();
        } catch (Exception e) {
            a.error(e.getMessage(), e);
        }
        DatabaseMetaData metaData = connection.getMetaData();
        org.jeecg.modules.online.config.a.b bVar = (org.jeecg.modules.online.config.a.b) SpringContextUtils.getBean(org.jeecg.modules.online.config.a.b.class);
        String str3 = null;
        try {
            str3 = d.getDatabaseType();
        } catch (DBException e2) {
            e2.printStackTrace();
        }
        String username = bVar.getUsername();
        if ("ORACLE".equals(str3)) {
            username = username.toUpperCase();
        }
        if ("SQLSERVER".equals(str3)) {
            columns = metaData.getColumns(connection.getCatalog(), null, str2, "%");
        } else {
            columns = metaData.getColumns(connection.getCatalog(), username, str2, "%");
        }

        while (columns.next()) {
            a aVar = new a();
            aVar.setTableName(str2);
            String lowerCase = columns.getString("COLUMN_NAME").toLowerCase();
            aVar.setColumnName(lowerCase);
            String string = columns.getString("TYPE_NAME");
            int i = columns.getInt("DECIMAL_DIGITS");
            aVar.setColunmType(dbTableHandleI.getMatchClassTypeByDataType(string, i));
            aVar.setRealDbType(string);
            int i2 = columns.getInt("COLUMN_SIZE");
            aVar.setColumnSize(i2);
            aVar.setDecimalDigits(i);
            aVar.setIsNullable(columns.getInt("NULLABLE") == 1 ? "Y" : "N");
            aVar.setComment(columns.getString("REMARKS"));
            String string2 = columns.getString("COLUMN_DEF");
            aVar.setFieldDefault(c(string2) == null ? "" : c(string2));
            a.info("getColumnMetadataFormDataBase --->COLUMN_NAME:" + lowerCase.toUpperCase() + " TYPE_NAME :" + string + " DECIMAL_DIGITS:" + i + " COLUMN_SIZE:" + i2);
            hashMap.put(lowerCase, aVar);
        }
        return hashMap;
    }

    private Map<String, a> c(org.jeecg.modules.online.config.a.a aVar) {
        HashMap hashMap = new HashMap();
        for (OnlCgformField onlCgformField : aVar.getColumns()) {
            a aVar2 = new a();
            aVar2.setTableName(aVar.getTableName().toLowerCase());
            aVar2.setColumnId(onlCgformField.getId());
            aVar2.setColumnName(onlCgformField.getDbFieldName().toLowerCase());
            aVar2.setColumnSize(onlCgformField.getDbLength().intValue());
            aVar2.setColunmType(onlCgformField.getDbType().toLowerCase());
            aVar2.setIsNullable(onlCgformField.getDbIsNull().intValue() == 1 ? "Y" : "N");
            aVar2.setComment(onlCgformField.getDbFieldTxt());
            aVar2.setDecimalDigits(onlCgformField.getDbPointLength().intValue());
            aVar2.setFieldDefault(c(onlCgformField.getDbDefaultVal()));
            aVar2.setPkType(aVar.getJformPkType() == null ? "UUID" : aVar.getJformPkType());
            aVar2.setOldColumnName(onlCgformField.getDbFieldNameOld() != null ? onlCgformField.getDbFieldNameOld().toLowerCase() : null);
            a.info("getColumnMetadataFormCgForm ----> DbFieldName: " + onlCgformField.getDbFieldName().toLowerCase() + " | DbType: " + onlCgformField.getDbType().toLowerCase() + " | DbPointLength:" + onlCgformField.getDbPointLength() + " | DbLength:" + onlCgformField.getDbLength());
            hashMap.put(onlCgformField.getDbFieldName().toLowerCase(), aVar2);
        }
        return hashMap;
    }

    private Map<String, String> a(List<OnlCgformField> list) {
        HashMap hashMap = new HashMap();
        for (OnlCgformField onlCgformField : list) {
            hashMap.put(onlCgformField.getDbFieldName(), onlCgformField.getDbFieldNameOld());
        }
        return hashMap;
    }

    private String b(String str) {
        return dbTableHandleI.getDropColumnSql(str);
    }

    private String a(a aVar, a aVar2) throws DBException {
        return dbTableHandleI.getUpdateColumnSql(aVar, aVar2);
    }

    private String b(a aVar, a aVar2) {
        return dbTableHandleI.getSpecialHandle(aVar, aVar2);
    }

    private String a(a aVar) {
        return dbTableHandleI.getReNameFieldName(aVar);
    }

    private String b(a aVar) {
        return dbTableHandleI.getAddColumnSql(aVar);
    }

    private String c(a aVar) {
        return dbTableHandleI.getCommentSql(aVar);
    }

    private String d(String str, String str2) {
        return "update onl_cgform_field set DB_FIELD_NAME_OLD = '" + str + "' where ID ='" + str2 + org.jeecg.modules.online.cgform.d.b.sz;
    }

    private int a(String str, String str2, Session session) {
        return session.createSQLQuery("update onl_cgform_field set DB_FIELD_NAME_OLD= '" + str + "' where ID ='" + str2 + org.jeecg.modules.online.cgform.d.b.sz).executeUpdate();
    }

    private static String c(String str) {
        if (StringUtils.isNotEmpty(str)) {
            try {
                Double.valueOf(str);
            } catch (Exception e) {
                if (!str.startsWith(org.jeecg.modules.online.cgform.d.b.sz) || !str.endsWith(org.jeecg.modules.online.cgform.d.b.sz)) {
                    str = org.jeecg.modules.online.cgform.d.b.sz + str + org.jeecg.modules.online.cgform.d.b.sz;
                }
            }
        }
        return str;
    }

    public String a(String str, String str2) {
        return dbTableHandleI.dropIndexs(str, str2);
    }

    public String b(String str, String str2) {
        return dbTableHandleI.countIndex(str, str2);
    }

    public static List<String> a(String str) throws SQLException {
        Connection connection = null;
        ArrayList arrayList = new ArrayList();
        try {
            try {
                connection = d.getConnection();
                ResultSet indexInfo = connection.getMetaData().getIndexInfo(null, null, str, false, false);
                indexInfo.getMetaData();
                while (indexInfo.next()) {
                    String string = indexInfo.getString("INDEX_NAME");
                    if (oConvertUtils.isEmpty(string)) {
                        string = indexInfo.getString("index_name");
                    }
                    if (oConvertUtils.isNotEmpty(string)) {
                        arrayList.add(string);
                    }
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                a.error(e.getMessage(), e);
                if (connection != null) {
                    connection.close();
                }
            }
            return arrayList;
        } catch (Throwable th) {
            if (connection != null) {
                connection.close();
            }
            throw th;
        }
    }
}