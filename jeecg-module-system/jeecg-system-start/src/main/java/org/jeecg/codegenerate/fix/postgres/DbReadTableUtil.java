package org.jeecg.codegenerate.fix.postgres;

import org.apache.commons.lang.StringUtils;
import org.jeecgframework.codegenerate.generate.pojo.ColumnVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class DbReadTableUtil {
    private static final Logger a = LoggerFactory.getLogger(org.jeecg.codegenerate.fix.postgres.DbReadTableUtil.class);
    private static Connection b;
    private static Statement c;
    private static int d = 0;
    private static boolean e = false;
    private static boolean f = false;
    private static boolean g = false;
    private static boolean h = false;
    private static boolean i = false;
    private static boolean j = false;

    public DbReadTableUtil() {
    }

    public static List<String> a() throws SQLException {
        return readAllTableNames();
    }

    public static List<String> readAllTableNames() throws SQLException {
        String var1 = null;
        ArrayList var2 = new ArrayList(0);

        try {
            Class.forName(org.jeecgframework.codegenerate.a.a.b);
            b = DriverManager.getConnection(org.jeecgframework.codegenerate.a.a.c, org.jeecgframework.codegenerate.a.a.d, org.jeecgframework.codegenerate.a.a.e);
            c = b.createStatement(1005, 1007);
            String var3 = b.getCatalog();
            a.info(" connect databaseName : " + var3);
            if (org.jeecgframework.codegenerate.database.a.a(org.jeecgframework.codegenerate.a.a.c)) {
                var1 = MessageFormat.format("select distinct table_name from information_schema.columns where table_schema = {0}", org.jeecgframework.codegenerate.generate.util.f.c(var3));
            }

            if (org.jeecgframework.codegenerate.database.a.b(org.jeecgframework.codegenerate.a.a.c)) {
                var1 = " select distinct colstable.table_name as  table_name from user_tab_cols colstable order by colstable.table_name";
            }

            if (org.jeecgframework.codegenerate.database.a.d(org.jeecgframework.codegenerate.a.a.c)) {
                if (org.jeecgframework.codegenerate.a.a.a.indexOf(",") == -1) {
                    var1 = MessageFormat.format("select tablename from pg_tables where schemaname in( {0} )", org.jeecgframework.codegenerate.generate.util.f.c(org.jeecgframework.codegenerate.a.a.a));
                } else {
                    StringBuffer var4 = new StringBuffer();
                    String[] var5 = org.jeecgframework.codegenerate.a.a.a.split(",");
                    String[] var6 = var5;
                    int var7 = var5.length;

                    for(int var8 = 0; var8 < var7; ++var8) {
                        String var9 = var6[var8];
                        var4.append(org.jeecgframework.codegenerate.generate.util.f.c(var9) + ",");
                    }

                    var1 = MessageFormat.format("select tablename from pg_tables where schemaname in( {0} )", var4.toString().substring(0, var4.toString().length() - 1));
                }
            }

            if (org.jeecgframework.codegenerate.database.a.c(org.jeecgframework.codegenerate.a.a.c)) {
                var1 = "select distinct c.name as  table_name from sys.objects c where c.type = 'U' ";
            }

            a.debug("--------------sql-------------" + var1);
            ResultSet var0 = c.executeQuery(var1);

            while(var0.next()) {
                String var20 = var0.getString(1);
                var2.add(var20);
            }
        } catch (Exception var18) {
            var18.printStackTrace();
        } finally {
            try {
                if (c != null) {
                    c.close();
                    c = null;
                    System.gc();
                }

                if (b != null) {
                    b.close();
                    b = null;
                    System.gc();
                }
            } catch (SQLException var17) {
                throw var17;
            }

        }

        return var2;
    }

    public static List<ColumnVo> a(String var0) throws Exception {
        String var2 = null;
        ArrayList var3 = new ArrayList();

        int var5;
        try {
            Class.forName(org.jeecgframework.codegenerate.a.a.b);
            b = DriverManager.getConnection(org.jeecgframework.codegenerate.a.a.c, org.jeecgframework.codegenerate.a.a.d, org.jeecgframework.codegenerate.a.a.e);
            c = b.createStatement(1005, 1007);
            String var4 = b.getCatalog();
            a.info(" connect databaseName : " + var4);
            if (org.jeecgframework.codegenerate.database.a.a(org.jeecgframework.codegenerate.a.a.c)) {
                var2 = MessageFormat.format("select column_name,data_type,column_comment,numeric_precision,numeric_scale,character_maximum_length,is_nullable nullable from information_schema.columns where table_name = {0} and table_schema = {1} order by ORDINAL_POSITION", org.jeecgframework.codegenerate.generate.util.f.c(var0), org.jeecgframework.codegenerate.generate.util.f.c(var4));
            }

            if (org.jeecgframework.codegenerate.database.a.b(org.jeecgframework.codegenerate.a.a.c)) {
                var2 = MessageFormat.format(" select colstable.column_name column_name, colstable.data_type data_type, commentstable.comments column_comment, colstable.Data_Precision column_precision, colstable.Data_Scale column_scale,colstable.Char_Length,colstable.nullable from user_tab_cols colstable  inner join user_col_comments commentstable  on colstable.column_name = commentstable.column_name  where colstable.table_name = commentstable.table_name  and colstable.table_name = {0}", org.jeecgframework.codegenerate.generate.util.f.c(var0.toUpperCase()));
            }
            // 修复 postgresql 读取字段信息的 sql 语句
            if (org.jeecgframework.codegenerate.database.a.d(org.jeecgframework.codegenerate.a.a.c)) {
                // numeric_precision_radix 有问题，实际精度对不上 -> numeric_precision
                // var2 = MessageFormat.format("select icm.column_name as field,icm.udt_name as type,fieldtxt.descript as comment, icm.numeric_precision_radix as column_precision ,icm.numeric_scale as column_scale ,icm.character_maximum_length as Char_Length,icm.is_nullable as attnotnull  from information_schema.columns icm, (SELECT A.attnum,( SELECT description FROM pg_catalog.pg_description WHERE objoid = A.attrelid AND objsubid = A.attnum ) AS descript,A.attname \tFROM pg_catalog.pg_attribute A WHERE A.attrelid = ( SELECT oid FROM pg_class WHERE relname = {0} ) AND A.attnum > 0 AND NOT A.attisdropped  ORDER BY\tA.attnum ) fieldtxt where icm.table_name={1} and fieldtxt.attname = icm.column_name", org.jeecgframework.codegenerate.generate.util.f.c(var0), org.jeecgframework.codegenerate.generate.util.f.c(var0));
                var2 = MessageFormat.format("select icm.column_name as field,icm.udt_name as type,fieldtxt.descript as comment, icm.numeric_precision as column_precision ,icm.numeric_scale as column_scale ,icm.character_maximum_length as Char_Length,icm.is_nullable as attnotnull  from information_schema.columns icm, (SELECT A.attnum,( SELECT description FROM pg_catalog.pg_description WHERE objoid = A.attrelid AND objsubid = A.attnum ) AS descript,A.attname \tFROM pg_catalog.pg_attribute A WHERE A.attrelid = ( SELECT oid FROM pg_class WHERE relname = {0} ) AND A.attnum > 0 AND NOT A.attisdropped  ORDER BY\tA.attnum ) fieldtxt where icm.table_name={1} and fieldtxt.attname = icm.column_name", org.jeecgframework.codegenerate.generate.util.f.c(var0), org.jeecgframework.codegenerate.generate.util.f.c(var0));
            }

            if (org.jeecgframework.codegenerate.database.a.c(org.jeecgframework.codegenerate.a.a.c)) {
                var2 = MessageFormat.format("select distinct cast(a.name as varchar(50)) column_name,  cast(b.name as varchar(50)) data_type,  cast(e.value as NVARCHAR(200)) comment,  cast(ColumnProperty(a.object_id,a.Name,'''Precision''') as int) num_precision,  cast(ColumnProperty(a.object_id,a.Name,'''Scale''') as int) num_scale,  a.max_length,  (case when a.is_nullable=1 then '''y''' else '''n''' end) nullable,column_id   from sys.columns a left join sys.types b on a.user_type_id=b.user_type_id left join (select top 1 * from sys.objects where type = '''U''' and name ={0}  order by name) c on a.object_id=c.object_id left join sys.extended_properties e on e.major_id=c.object_id and e.minor_id=a.column_id and e.class=1 where c.name={0} order by a.column_id", org.jeecgframework.codegenerate.generate.util.f.c(var0));
            }

            a.debug("--------------sql-------------" + var2);
            ResultSet var1 = c.executeQuery(var2);
            var1.last();
            var5 = var1.getRow();
            if (var5 <= 0) {
                throw new Exception("该表不存在或者表中没有字段");
            }

            ColumnVo var7 = new ColumnVo();
            if (org.jeecgframework.codegenerate.a.a.k) {
                var7.setFieldName(org.jeecgframework.codegenerate.generate.util.f.d(var1.getString(1).toLowerCase()));
            } else {
                var7.setFieldName(var1.getString(1).toLowerCase());
            }

            var7.setFieldDbName(var1.getString(1).toUpperCase());
            var7.setFieldType(org.jeecgframework.codegenerate.generate.util.f.d(var1.getString(2).toLowerCase()));
            var7.setFieldDbType(org.jeecgframework.codegenerate.generate.util.f.d(var1.getString(2).toLowerCase()));
            var7.setPrecision(var1.getString(4));
            var7.setScale(var1.getString(5));
            var7.setCharmaxLength(var1.getString(6));
            var7.setNullable(org.jeecgframework.codegenerate.generate.util.f.a(var1.getString(7)));
            org.jeecgframework.codegenerate.generate.util.f.a(var7);
            var7.setFiledComment(StringUtils.isBlank(var1.getString(3)) ? var7.getFieldName() : var1.getString(3));
            a.debug("columnt.getFieldName() -------------" + var7.getFieldName());
            String[] var8 = new String[0];
            if (org.jeecgframework.codegenerate.a.a.o != null) {
                var8 = org.jeecgframework.codegenerate.a.a.o.toLowerCase().split(",");
            }

            if (!org.jeecgframework.codegenerate.a.a.l.equals(var7.getFieldName()) && !org.jeecgframework.codegenerate.database.util.a.a(var7.getFieldDbName().toLowerCase(), var8)) {
                var3.add(var7);
            }

            while(var1.previous()) {
                ColumnVo var9 = new ColumnVo();
                if (org.jeecgframework.codegenerate.a.a.k) {
                    var9.setFieldName(org.jeecgframework.codegenerate.generate.util.f.d(var1.getString(1).toLowerCase()));
                } else {
                    var9.setFieldName(var1.getString(1).toLowerCase());
                }

                var9.setFieldDbName(var1.getString(1).toUpperCase());
                a.debug("columnt.getFieldName() -------------" + var9.getFieldName());
                if (!org.jeecgframework.codegenerate.a.a.l.equals(var9.getFieldName()) && !org.jeecgframework.codegenerate.database.util.a.a(var9.getFieldDbName().toLowerCase(), var8)) {
                    var9.setFieldType(org.jeecgframework.codegenerate.generate.util.f.d(var1.getString(2).toLowerCase()));
                    var9.setFieldDbType(org.jeecgframework.codegenerate.generate.util.f.d(var1.getString(2).toLowerCase()));
                    a.debug("-----po.setFieldType------------" + var9.getFieldType());
                    var9.setPrecision(var1.getString(4));
                    var9.setScale(var1.getString(5));
                    var9.setCharmaxLength(var1.getString(6));
                    var9.setNullable(org.jeecgframework.codegenerate.generate.util.f.a(var1.getString(7)));
                    org.jeecgframework.codegenerate.generate.util.f.a(var9);
                    var9.setFiledComment(StringUtils.isBlank(var1.getString(3)) ? var9.getFieldName() : var1.getString(3));
                    var3.add(var9);
                }
            }

            a.debug("读取表成功");
        } catch (ClassNotFoundException var18) {
            throw var18;
        } catch (SQLException var19) {
            throw var19;
        } finally {
            try {
                if (c != null) {
                    c.close();
                    c = null;
                    System.gc();
                }

                if (b != null) {
                    b.close();
                    b = null;
                    System.gc();
                }
            } catch (SQLException var17) {
                throw var17;
            }

        }

        ArrayList var21 = new ArrayList();

        for(var5 = var3.size() - 1; var5 >= 0; --var5) {
            ColumnVo var6 = (ColumnVo)var3.get(var5);
            var21.add(var6);
        }

        return var21;
    }

    public static String getProjectPath() {
        return org.jeecgframework.codegenerate.a.a.f;
    }

    public static List<ColumnVo> b(String tableName) throws Exception {
        return readOriginalTableColumn(tableName);
    }

    public static List<ColumnVo> readOriginalTableColumn(String tableName) throws Exception {
        ResultSet var1 = null;
        String var2 = null;
        ArrayList var3 = new ArrayList();

        int var5;
        try {
            Class.forName(org.jeecgframework.codegenerate.a.a.b);
            b = DriverManager.getConnection(org.jeecgframework.codegenerate.a.a.c, org.jeecgframework.codegenerate.a.a.d, org.jeecgframework.codegenerate.a.a.e);
            c = b.createStatement(1005, 1007);
            String var4 = b.getCatalog();
            a.info(" connect databaseName : " + var4);
            if (org.jeecgframework.codegenerate.database.a.a(org.jeecgframework.codegenerate.a.a.c)) {
                var2 = MessageFormat.format("select column_name,data_type,column_comment,numeric_precision,numeric_scale,character_maximum_length,is_nullable nullable from information_schema.columns where table_name = {0} and table_schema = {1} order by ORDINAL_POSITION", org.jeecgframework.codegenerate.generate.util.f.c(tableName), org.jeecgframework.codegenerate.generate.util.f.c(var4));
            }

            if (org.jeecgframework.codegenerate.database.a.b(org.jeecgframework.codegenerate.a.a.c)) {
                var2 = MessageFormat.format(" select colstable.column_name column_name, colstable.data_type data_type, commentstable.comments column_comment, colstable.Data_Precision column_precision, colstable.Data_Scale column_scale,colstable.Char_Length,colstable.nullable from user_tab_cols colstable  inner join user_col_comments commentstable  on colstable.column_name = commentstable.column_name  where colstable.table_name = commentstable.table_name  and colstable.table_name = {0}", org.jeecgframework.codegenerate.generate.util.f.c(tableName.toUpperCase()));
            }

            if (org.jeecgframework.codegenerate.database.a.d(org.jeecgframework.codegenerate.a.a.c)) {
                // var2 = MessageFormat.format("select icm.column_name as field,icm.udt_name as type,fieldtxt.descript as comment, icm.numeric_precision_radix as column_precision ,icm.numeric_scale as column_scale ,icm.character_maximum_length as Char_Length,icm.is_nullable as attnotnull  from information_schema.columns icm, (SELECT A.attnum,( SELECT description FROM pg_catalog.pg_description WHERE objoid = A.attrelid AND objsubid = A.attnum ) AS descript,A.attname \tFROM pg_catalog.pg_attribute A WHERE A.attrelid = ( SELECT oid FROM pg_class WHERE relname = {0} ) AND A.attnum > 0 AND NOT A.attisdropped  ORDER BY\tA.attnum ) fieldtxt where icm.table_name={1} and fieldtxt.attname = icm.column_name", org.jeecgframework.codegenerate.generate.util.f.c(tableName), org.jeecgframework.codegenerate.generate.util.f.c(tableName));
                var2 = MessageFormat.format("select icm.column_name as field,icm.udt_name as type,fieldtxt.descript as comment, icm.numeric_precision as column_precision ,icm.numeric_scale as column_scale ,icm.character_maximum_length as Char_Length,icm.is_nullable as attnotnull  from information_schema.columns icm, (SELECT A.attnum,( SELECT description FROM pg_catalog.pg_description WHERE objoid = A.attrelid AND objsubid = A.attnum ) AS descript,A.attname \tFROM pg_catalog.pg_attribute A WHERE A.attrelid = ( SELECT oid FROM pg_class WHERE relname = {0} ) AND A.attnum > 0 AND NOT A.attisdropped  ORDER BY\tA.attnum ) fieldtxt where icm.table_name={1} and fieldtxt.attname = icm.column_name", org.jeecgframework.codegenerate.generate.util.f.c(tableName), org.jeecgframework.codegenerate.generate.util.f.c(tableName));
            }

            if (org.jeecgframework.codegenerate.database.a.c(org.jeecgframework.codegenerate.a.a.c)) {
                var2 = MessageFormat.format("select distinct cast(a.name as varchar(50)) column_name,  cast(b.name as varchar(50)) data_type,  cast(e.value as NVARCHAR(200)) comment,  cast(ColumnProperty(a.object_id,a.Name,'''Precision''') as int) num_precision,  cast(ColumnProperty(a.object_id,a.Name,'''Scale''') as int) num_scale,  a.max_length,  (case when a.is_nullable=1 then '''y''' else '''n''' end) nullable,column_id   from sys.columns a left join sys.types b on a.user_type_id=b.user_type_id left join (select top 1 * from sys.objects where type = '''U''' and name ={0}  order by name) c on a.object_id=c.object_id left join sys.extended_properties e on e.major_id=c.object_id and e.minor_id=a.column_id and e.class=1 where c.name={0} order by a.column_id", org.jeecgframework.codegenerate.generate.util.f.c(tableName));
            }

            var1 = c.executeQuery(var2);
            var1.last();
            var5 = var1.getRow();
            if (var5 <= 0) {
                throw new Exception("该表不存在或者表中没有字段");
            }

            ColumnVo var7 = new ColumnVo();
            if (org.jeecgframework.codegenerate.a.a.k) {
                var7.setFieldName(org.jeecgframework.codegenerate.generate.util.f.d(var1.getString(1).toLowerCase()));
            } else {
                var7.setFieldName(var1.getString(1).toLowerCase());
            }

            var7.setFieldDbName(var1.getString(1).toUpperCase());
            var7.setPrecision(org.jeecgframework.codegenerate.generate.util.f.b(var1.getString(4)));
            var7.setScale(org.jeecgframework.codegenerate.generate.util.f.b(var1.getString(5)));
            var7.setCharmaxLength(org.jeecgframework.codegenerate.generate.util.f.b(var1.getString(6)));
            var7.setNullable(org.jeecgframework.codegenerate.generate.util.f.a(var1.getString(7)));
            var7.setFieldType(org.jeecgframework.codegenerate.generate.util.f.a(var1.getString(2).toLowerCase(), var7.getPrecision(), var7.getScale()));
            var7.setFieldDbType(org.jeecgframework.codegenerate.generate.util.f.d(var1.getString(2).toLowerCase()));
            org.jeecgframework.codegenerate.generate.util.f.a(var7);
            var7.setFiledComment(StringUtils.isBlank(var1.getString(3)) ? var7.getFieldName() : var1.getString(3));
            a.debug("columnt.getFieldName() -------------" + var7.getFieldName());
            var3.add(var7);

            while(true) {
                if (!var1.previous()) {
                    a.debug("读取表成功");
                    break;
                }

                ColumnVo var8 = new ColumnVo();
                if (org.jeecgframework.codegenerate.a.a.k) {
                    var8.setFieldName(org.jeecgframework.codegenerate.generate.util.f.d(var1.getString(1).toLowerCase()));
                } else {
                    var8.setFieldName(var1.getString(1).toLowerCase());
                }

                var8.setFieldDbName(var1.getString(1).toUpperCase());
                var8.setPrecision(org.jeecgframework.codegenerate.generate.util.f.b(var1.getString(4)));
                var8.setScale(org.jeecgframework.codegenerate.generate.util.f.b(var1.getString(5)));
                var8.setCharmaxLength(org.jeecgframework.codegenerate.generate.util.f.b(var1.getString(6)));
                var8.setNullable(org.jeecgframework.codegenerate.generate.util.f.a(var1.getString(7)));
                var8.setFieldType(org.jeecgframework.codegenerate.generate.util.f.a(var1.getString(2).toLowerCase(), var8.getPrecision(), var8.getScale()));
                var8.setFieldDbType(org.jeecgframework.codegenerate.generate.util.f.d(var1.getString(2).toLowerCase()));
                org.jeecgframework.codegenerate.generate.util.f.a(var8);
                var8.setFiledComment(StringUtils.isBlank(var1.getString(3)) ? var8.getFieldName() : var1.getString(3));
                var3.add(var8);
            }
        } catch (ClassNotFoundException var17) {
            throw var17;
        } catch (SQLException var18) {
            throw var18;
        } finally {
            try {
                if (c != null) {
                    c.close();
                    c = null;
                    System.gc();
                }

                if (b != null) {
                    b.close();
                    b = null;
                    System.gc();
                }
            } catch (SQLException var16) {
                throw var16;
            }

        }

        ArrayList var20 = new ArrayList();

        for(var5 = var3.size() - 1; var5 >= 0; --var5) {
            ColumnVo var6 = (ColumnVo)var3.get(var5);
            var20.add(var6);
        }

        return var20;
    }

    public static boolean c(String var0) {
        String var2 = null;

        try {
            a.debug("数据库驱动: " + org.jeecgframework.codegenerate.a.a.b);
            Class.forName(org.jeecgframework.codegenerate.a.a.b);
            b = DriverManager.getConnection(org.jeecgframework.codegenerate.a.a.c, org.jeecgframework.codegenerate.a.a.d, org.jeecgframework.codegenerate.a.a.e);
            c = b.createStatement(1005, 1007);
            String var3 = b.getCatalog();
            a.info(" connect databaseName : " + var3);
            if (org.jeecgframework.codegenerate.database.a.a(org.jeecgframework.codegenerate.a.a.c)) {
                var2 = "select column_name,data_type,column_comment,0,0 from information_schema.columns where table_name = '" + var0 + "' and table_schema = '" + var3 + "'";
            }

            if (org.jeecgframework.codegenerate.database.a.b(org.jeecgframework.codegenerate.a.a.c)) {
                var2 = "select colstable.column_name column_name, colstable.data_type data_type, commentstable.comments column_comment from user_tab_cols colstable  inner join user_col_comments commentstable  on colstable.column_name = commentstable.column_name  where colstable.table_name = commentstable.table_name  and colstable.table_name = '" + var0.toUpperCase() + "'";
            }

            if (org.jeecgframework.codegenerate.database.a.d(org.jeecgframework.codegenerate.a.a.c)) {
                // var2 = MessageFormat.format("select icm.column_name as field,icm.udt_name as type,fieldtxt.descript as comment, icm.numeric_precision_radix as column_precision ,icm.numeric_scale as column_scale ,icm.character_maximum_length as Char_Length,icm.is_nullable as attnotnull  from information_schema.columns icm, (SELECT A.attnum,( SELECT description FROM pg_catalog.pg_description WHERE objoid = A.attrelid AND objsubid = A.attnum ) AS descript,A.attname \tFROM pg_catalog.pg_attribute A WHERE A.attrelid = ( SELECT oid FROM pg_class WHERE relname = {0} ) AND A.attnum > 0 AND NOT A.attisdropped  ORDER BY\tA.attnum ) fieldtxt where icm.table_name={1} and fieldtxt.attname = icm.column_name", org.jeecgframework.codegenerate.generate.util.f.c(var0), org.jeecgframework.codegenerate.generate.util.f.c(var0));
                var2 = MessageFormat.format("select icm.column_name as field,icm.udt_name as type,fieldtxt.descript as comment, icm.numeric_precision as column_precision ,icm.numeric_scale as column_scale ,icm.character_maximum_length as Char_Length,icm.is_nullable as attnotnull  from information_schema.columns icm, (SELECT A.attnum,( SELECT description FROM pg_catalog.pg_description WHERE objoid = A.attrelid AND objsubid = A.attnum ) AS descript,A.attname \tFROM pg_catalog.pg_attribute A WHERE A.attrelid = ( SELECT oid FROM pg_class WHERE relname = {0} ) AND A.attnum > 0 AND NOT A.attisdropped  ORDER BY\tA.attnum ) fieldtxt where icm.table_name={1} and fieldtxt.attname = icm.column_name", org.jeecgframework.codegenerate.generate.util.f.c(var0), org.jeecgframework.codegenerate.generate.util.f.c(var0));
            }

            if (org.jeecgframework.codegenerate.database.a.c(org.jeecgframework.codegenerate.a.a.c)) {
                var2 = MessageFormat.format("select distinct cast(a.name as varchar(50)) column_name,  cast(b.name as varchar(50)) data_type,  cast(e.value as NVARCHAR(200)) comment,  cast(ColumnProperty(a.object_id,a.Name,'''Precision''') as int) num_precision,  cast(ColumnProperty(a.object_id,a.Name,'''Scale''') as int) num_scale,  a.max_length,  (case when a.is_nullable=1 then '''y''' else '''n''' end) nullable,column_id   from sys.columns a left join sys.types b on a.user_type_id=b.user_type_id left join (select top 1 * from sys.objects where type = '''U''' and name ={0}  order by name) c on a.object_id=c.object_id left join sys.extended_properties e on e.major_id=c.object_id and e.minor_id=a.column_id and e.class=1 where c.name={0} order by a.column_id", org.jeecgframework.codegenerate.generate.util.f.c(var0));
            }

            ResultSet var1 = c.executeQuery(var2);
            var1.last();
            int var4 = var1.getRow();
            return var4 > 0;
        } catch (Exception var5) {
            var5.printStackTrace();
            return false;
        }
    }

    public static String d(String var0) {
        String[] var1 = var0.split("_");
        var0 = "";
        int var2 = 0;

        for(int var3 = var1.length; var2 < var3; ++var2) {
            if (var2 > 0) {
                String var4 = var1[var2].toLowerCase();
                var4 = var4.substring(0, 1).toUpperCase() + var4.substring(1, var4.length());
                var0 = var0 + var4;
            } else {
                var0 = var0 + var1[var2].toLowerCase();
            }
        }

        var0 = var0.substring(0, 1).toUpperCase() + var0.substring(1);
        return var0;
    }

    // static {
    //     try {
    //         e = null != Class.forName("org.apache.jsp.designer.index_jsp");
    //         if (e) {
    //             ++d;
    //         }
    //     } catch (Throwable var9) {
    //         e = false;
    //     }
    //
    //     try {
    //         f = null != Class.forName("org.apache.jsp.designer.candidateUsersConfig_jsp");
    //         if (f) {
    //             ++d;
    //         }
    //     } catch (Throwable var8) {
    //         f = false;
    //     }
    //
    //     try {
    //         g = null != Class.forName("org.jeecg.modules.online.desform.entity.DesignForm");
    //         if (g) {
    //             ++d;
    //         }
    //     } catch (Throwable var7) {
    //         g = false;
    //     }
    //
    //     try {
    //         h = null != Class.forName("org.jeecg.modules.online.desform.service.IDesignFormAuthService");
    //         if (h) {
    //             ++d;
    //         }
    //     } catch (Throwable var6) {
    //         h = false;
    //     }
    //
    //     try {
    //         i = null != Class.forName("org.jeecg.modules.aspect.SysUserAspect");
    //         if (i) {
    //             ++d;
    //         }
    //     } catch (Throwable var5) {
    //         i = false;
    //     }
    //
    //     try {
    //         j = null != Class.forName("org.jeecg.modules.extbpm.listener.execution.ProcessEndListener");
    //         if (j) {
    //             ++d;
    //         }
    //     } catch (Throwable var4) {
    //         j = false;
    //     }
    //
    //     long var0 = 1728000000L;
    //     Runnable var2 = new Runnable() {
    //         public void run() {
    //             while(true) {
    //                 try {
    //                     Thread.sleep(1728000000L);
    //                     if (DbReadTableUtil.d <= 2) {
    //                         Thread.sleep(787968000000L);
    //                         return;
    //                     }
    //
    //                     String var1 = "";
    //                     Object var2 = null;
    //
    //                     try {
    //                         String var4 = System.getProperty("user.dir") + File.separator + "config" + File.separator + org.jeecgframework.codegenerate.generate.util.d.e();
    //                         BufferedInputStream var3 = new BufferedInputStream(new FileInputStream(var4));
    //                         var2 = new PropertyResourceBundle(var3);
    //                         var3.close();
    //                     } catch (IOException var6) {
    //                     }
    //
    //                     if (var2 == null) {
    //                         var2 = ResourceBundle.getBundle(org.jeecgframework.codegenerate.generate.util.d.d());
    //                     }
    //
    //                     String var8 = ((ResourceBundle)var2).getString(org.jeecgframework.codegenerate.generate.util.d.g());
    //                     byte[] var9 = org.jeecgframework.codegenerate.generate.util.d.a(org.jeecgframework.codegenerate.generate.util.d.i(), var8);
    //                     var8 = new String(var9, "UTF-8");
    //                     String[] var5 = var8.split("\\|");
    //                     if (var8.contains("--")) {
    //                         Thread.sleep(787968000000L);
    //                         return;
    //                     }
    //
    //                     if (!var5[1].equals(org.jeecgframework.codegenerate.generate.util.e.b())) {
    //                         System.out.println(org.jeecgframework.codegenerate.generate.util.d.h() + org.jeecgframework.codegenerate.generate.util.e.b());
    //                         System.err.println(org.jeecgframework.codegenerate.generate.util.c.d("9RUvZRL/eoRJhWiHinvL3IFhdT4m8hwt7o9OXN5JPAPcpelJxtgYL0/JESq9cif96ihcHzCZ5d7V6meXp1InTNjyffi6mPzwXLlrdruW38M=", "jm072"));
    //                         System.exit(0);
    //                     }
    //                 } catch (Exception var7) {
    //                     System.err.println(org.jeecgframework.codegenerate.generate.util.d.h() + org.jeecgframework.codegenerate.generate.util.e.b());
    //                     System.err.println(org.jeecgframework.codegenerate.generate.util.c.d("RXPUfpgyxAmQAY+315PkFvzSFm7dkFSwselDafKC8PVxQOWwkRbJSXVlhZ3NyxTGfJJO9ES9iOmfXtI+mgMNTg==", "jm0156"));
    //                     System.exit(0);
    //                 }
    //             }
    //         }
    //     };
    //     Thread var3 = new Thread(var2);
    //     var3.start();
    // }
}
