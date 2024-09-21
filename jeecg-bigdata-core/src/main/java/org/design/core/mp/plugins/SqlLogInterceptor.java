package org.design.core.mp.plugins;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.core.toolkit.SystemClock;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.ResultHandler;
import org.design.core.tool.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Intercepts({@Signature(type = StatementHandler.class, method = "query", args = {Statement.class, ResultHandler.class}), @Signature(type = StatementHandler.class, method = "update", args = {Statement.class}), @Signature(type = StatementHandler.class, method = "batch", args = {Statement.class})})
/* loaded from: bigdata-core-mybatis-3.1.0.jar:org/design/core/mp/plugins/SqlLogInterceptor.class */
public class SqlLogInterceptor implements Interceptor {
    private static final Logger log = LoggerFactory.getLogger(SqlLogInterceptor.class);
    private static final String DRUID_POOLED_PREPARED_STATEMENT = "com.alibaba.druid.pool.DruidPooledPreparedStatement";
    private static final String T4C_PREPARED_STATEMENT = "oracle.jdbc.driver.T4CPreparedStatement";
    private static final String ORACLE_PREPARED_STATEMENT_WRAPPER = "oracle.jdbc.driver.OraclePreparedStatementWrapper";
    private Method oracleGetOriginalSqlMethod;
    private Method druidGetSqlMethod;

    public Object intercept(Invocation invocation) throws Throwable {
        Statement statement;
        Object firstArg = invocation.getArgs()[0];
        if (Proxy.isProxyClass(firstArg.getClass())) {
            statement = (Statement) SystemMetaObject.forObject(firstArg).getValue("h.statement");
        } else {
            statement = (Statement) firstArg;
        }
        MetaObject stmtMetaObj = SystemMetaObject.forObject(statement);
        try {
            statement = (Statement) stmtMetaObj.getValue("stmt.statement");
        } catch (Exception e) {
        }
        if (stmtMetaObj.hasGetter("delegate")) {
            try {
                statement = (Statement) stmtMetaObj.getValue("delegate");
            } catch (Exception e2) {
            }
        }
        String originalSql = null;
        String stmtClassName = statement.getClass().getName();
        if (DRUID_POOLED_PREPARED_STATEMENT.equals(stmtClassName)) {
            try {
                if (this.druidGetSqlMethod == null) {
                    this.druidGetSqlMethod = Class.forName(DRUID_POOLED_PREPARED_STATEMENT).getMethod("getSql", new Class[0]);
                }
                Object stmtSql = this.druidGetSqlMethod.invoke(statement, new Object[0]);
                if (stmtSql instanceof String) {
                    originalSql = (String) stmtSql;
                }
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        } else if (T4C_PREPARED_STATEMENT.equals(stmtClassName) || ORACLE_PREPARED_STATEMENT_WRAPPER.equals(stmtClassName)) {
            try {
                if (this.oracleGetOriginalSqlMethod != null) {
                    Object stmtSql2 = this.oracleGetOriginalSqlMethod.invoke(statement, new Object[0]);
                    if (stmtSql2 instanceof String) {
                        originalSql = (String) stmtSql2;
                    }
                } else {
                    this.oracleGetOriginalSqlMethod = getMethodRegular(Class.forName(stmtClassName), "getOriginalSql");
                    if (this.oracleGetOriginalSqlMethod != null) {
                        this.oracleGetOriginalSqlMethod.setAccessible(true);
                        if (null != this.oracleGetOriginalSqlMethod) {
                            Object stmtSql3 = this.oracleGetOriginalSqlMethod.invoke(statement, new Object[0]);
                            if (stmtSql3 instanceof String) {
                                originalSql = (String) stmtSql3;
                            }
                        }
                    }
                }
            } catch (Exception e4) {
            }
        }
        if (originalSql == null) {
            originalSql = statement.toString();
        }
        String originalSql2 = originalSql.replaceAll("[\\s]+", " ");
        int index = indexOfSqlStart(originalSql2);
        if (index > 0) {
            originalSql2 = originalSql2.substring(index);
        }
        long start = SystemClock.now();
        Object result = invocation.proceed();
        System.err.println(StringUtil.format("\n==============  Sql Start  ==============\nExecute ID  ：{}\nExecute SQL ：{}\nExecute Time：{} ms\n==============  Sql  End   ==============\n", new Object[]{((MappedStatement) SystemMetaObject.forObject(PluginUtils.realTarget(invocation.getTarget())).getValue("delegate.mappedStatement")).getId(), originalSql2, Long.valueOf(SystemClock.now() - start)}));
        return result;
    }

    public Object plugin(Object target) {
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    private Method getMethodRegular(Class<?> clazz, String methodName) {
        if (Object.class.equals(clazz)) {
            return null;
        }
        Method[] declaredMethods = clazz.getDeclaredMethods();
        for (Method method : declaredMethods) {
            if (method.getName().equals(methodName)) {
                return method;
            }
        }
        return getMethodRegular(clazz.getSuperclass(), methodName);
    }

    private int indexOfSqlStart(String sql) {
        String upperCaseSql = sql.toUpperCase();
        Set<Integer> set = new HashSet<>();
        set.add(Integer.valueOf(upperCaseSql.indexOf("SELECT ")));
        set.add(Integer.valueOf(upperCaseSql.indexOf("UPDATE ")));
        set.add(Integer.valueOf(upperCaseSql.indexOf("INSERT ")));
        set.add(Integer.valueOf(upperCaseSql.indexOf("DELETE ")));
        set.remove(-1);
        if (CollectionUtils.isEmpty(set)) {
            return -1;
        }
        List<Integer> list = new ArrayList<>(set);
        list.sort(Comparator.naturalOrder());
        return list.get(0).intValue();
    }
}
