package org.jeecg.modules.online.cgform;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.system.query.MatchTypeEnum;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.query.QueryRuleEnum;
import org.jeecg.common.system.vo.DictModel;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.system.vo.SysPermissionDataRuleModel;
import org.jeecg.common.util.*;
import org.jeecg.common.util.jsonschema.BaseColumn;
import org.jeecg.common.util.jsonschema.CommonProperty;
import org.jeecg.common.util.jsonschema.JsonSchemaDescrip;
import org.jeecg.common.util.jsonschema.JsonschemaUtil;
import org.jeecg.common.util.jsonschema.validate.*;
import org.jeecg.modules.online.cgform.entity.*;
import org.jeecg.modules.online.cgform.enums.CgformValidPatternEnum;
import org.jeecg.modules.online.cgform.mapper.OnlCgformHeadMapper;
import org.jeecg.modules.online.cgform.service.IOnlCgformFieldService;
import org.jeecg.modules.online.config.exception.DBException;
import org.jeecgframework.poi.excel.entity.params.ExcelExportEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.jeecg.common.system.query.QueryGenerator.getSqlRuleValue;

@Service
public class CgformDB {
    private static final Logger ay = LoggerFactory.getLogger(CgformDB.class);
    public static final String a = "SELECT ";
    public static final String b = " FROM ";
    public static final String c = " AND ";
    public static final String d = " like ";
    public static final String e = " COUNT(*) ";
    public static final String WHERE_1_1 = " where 1=1  ";
    public static final String g = " where  ";
    public static final String h = " ORDER BY ";
    public static final String i = "asc";
    public static final String j = "desc";
    public static final String k = "=";
    public static final String l = "!=";
    public static final String m = ">=";
    public static final String n = ">";
    public static final String o = "<=";
    public static final String p = "<";
    public static final String q = " or ";
    public static final String r = "Y";
    public static final String s = "$";
    public static final String t = "CREATE_TIME";
    public static final String u = "CREATE_BY";
    public static final String v = "UPDATE_TIME";
    public static final String w = "UPDATE_BY";
    public static final String x = "SYS_ORG_CODE";
    public static final int y = 2;
    public static final String z = "'";
    public static final String A = "N";
    public static final String B = ",";
    public static final String C = "single";
    public static final String D = "id";
    public static final String E = "bpm_status";
    public static final String F = "1";
    public static final String G = "force";
    public static final String H = "normal";
    public static final String I = "switch";
    public static final String J = "popup";
    public static final String K = "sel_search";
    public static final String L = "image";
    public static final String M = "file";
    public static final String N = "sel_tree";
    public static final String O = "cat_tree";
    public static final String P = "link_down";
    public static final String Q = "SYS_USER";
    public static final String R = "REALNAME";
    public static final String S = "USERNAME";
    public static final String T = "SYS_DEPART";
    public static final String U = "DEPART_NAME";
    public static final String V = "ID";
    public static final String W = "SYS_CATEGORY";
    public static final String X = "NAME";
    public static final String Y = "CODE";
    public static final String Z = "ID";
    public static final String aa = "PID";
    public static final String ab = "HAS_CHILD";
    public static final String ac = "popupMulti";
    public static final String ad = "sel_search";
    public static final String ae = "sub-table-design_";
    public static final String af = "import";
    public static final String ag = "export";
    public static final String ah = "query";
    public static final String ai = "form";
    public static final String aj = "list";
    public static final String ak = "1";
    public static final String al = "start";
    public static final String am = "erp";
    public static final String an = "exportSingleOnly";
    public static final String ao = "isSingleTableImport";
    public static final String ap = "foreignKeys";
    public static final int aq = 1;
    public static final int ar = 2;
    public static final int as = 0;
    public static final int at = 1;
    public static final String au = "1";
    public static final String av = "id";
    public static final String aw = "center";
    public static final String ax = "showLength";
    private static final String az = "beforeAdd,beforeEdit,afterAdd,afterEdit,beforeDelete,afterDelete,mounted,created";
    private static String aA;

    private static String MYBATIS_LOGIC_DELETE_FIELD= "del_flag";

    private static Integer MYBATIS_LOGIC_DELETE_FIELD_VAL = 1;

    private static Integer MYBATIS_LOGIC_NOT_DELETE_FIELD_VAL = 0;

    public CgformDB() {
    }

    public static void a(String var0, List<OnlCgformField> var1, StringBuffer var2) {
        if (var1 != null && var1.size() != 0) {
            var2.append("SELECT ");
            int var3 = var1.size();
            boolean var4 = false;

            for(int var5 = 0; var5 < var3; ++var5) {
                OnlCgformField var6 = (OnlCgformField)var1.get(var5);
                if ("id".equals(var6.getDbFieldName())) {
                    var4 = true;
                }

                if ("cat_tree".equals(var6.getFieldShowType()) && oConvertUtils.isNotEmpty(var6.getDictText())) {
                    var2.append(var6.getDictText() + ",");
                }

                if (var5 == var3 - 1) {
                    var2.append(var6.getDbFieldName() + " ");
                } else {
                    var2.append(var6.getDbFieldName() + ",");
                }
            }

            if (!var4) {
                var2.append(",id");
            }
        } else {
            var2.append("SELECT id");
        }

        var2.append(" FROM " + getTableName(var0));
    }

    public static String toDatetime(String var0) {
        return " to_date('" + var0 + "','yyyy-MM-dd HH24:mi:ss')";
    }

    public static String toDate(String var0) {
        return " to_date('" + var0 + "','yyyy-MM-dd')";
    }

    public static boolean c(String var0) {
        if ("list".equals(var0)) {
            return true;
        } else if ("radio".equals(var0)) {
            return true;
        } else if ("checkbox".equals(var0)) {
            return true;
        } else {
            return "list_multi".equals(var0);
        }
    }

    public static String getSqlCondition(List<OnlCgformField> cgformFields, Map<String, Object> queryParam, List<String> var2) {
        return getSqlCondition(cgformFields, queryParam, var2, null);
    }

    public static String getSqlCondition(List<OnlCgformField> cgformFields, Map<String, Object> queryParam, List<String> var2, List<SysPermissionDataRuleModel> permissionDataRuleModels) {
        StringBuffer finalSqlCondition = new StringBuffer();
        String databaseType = "";

        try {
            databaseType = org.jeecg.modules.online.config.b.d.getDatabaseType();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (DBException e1) {
            e1.printStackTrace();
        }

        Map<String, List<SysPermissionDataRuleModel>> rulesMap = QueryGenerator.getRulesMap(permissionDataRuleModels);

        // 处理自定义sql片段
        handleDataRulesForCustomSql(rulesMap, finalSqlCondition);

        Iterator<OnlCgformField> cgformFieldIterator = cgformFields.iterator();

        while(true) {
            while(true) {
                String dbFieldName;
                String dbType;
                Object var11;
                OnlCgformField onlCgformField;
                do {
                    while(true) {
                        do {
                            if (!cgformFieldIterator.hasNext()) {
                                return finalSqlCondition.toString();
                            }

                            onlCgformField = cgformFieldIterator.next();
                            dbFieldName = onlCgformField.getDbFieldName();
                            dbType = onlCgformField.getDbType();
                            String camelDbFieldName = oConvertUtils.camelNames(dbFieldName);
                            if (rulesMap.containsKey(dbFieldName)) {
                                handleDataRulesForOneField(databaseType, rulesMap.get(dbFieldName), dbFieldName, dbType, finalSqlCondition);
                            } else if (rulesMap.containsKey(camelDbFieldName)) {
                                handleDataRulesForOneField(databaseType, rulesMap.get(camelDbFieldName), dbFieldName, dbType, finalSqlCondition);
                            }

                            if (var2 != null && var2.contains(dbFieldName)) {
                                onlCgformField.setIsQuery(1);
                                onlCgformField.setQueryMode("single");
                            }

                            if (oConvertUtils.isNotEmpty(onlCgformField.getMainField()) && oConvertUtils.isNotEmpty(onlCgformField.getMainTable())) {
                                onlCgformField.setIsQuery(1);
                                onlCgformField.setQueryMode("single");
                            }

                            // 添加逻辑 可以筛选id
                            if ("id".equals(onlCgformField.getDbFieldName())) {
                                onlCgformField.setIsQuery(1);
                                onlCgformField.setQueryMode("single");
                            }
                        } while(1 != onlCgformField.getIsQuery());

                        if ("single".equals(onlCgformField.getQueryMode())) {
                            var11 = queryParam.get(dbFieldName);
                            break;
                        }

                        var11 = queryParam.get(dbFieldName + "_begin");
                        if (var11 != null) {
                            finalSqlCondition.append(" AND " + dbFieldName + ">=");
                            if (org.jeecg.modules.online.cgform.d.k.isNumber(dbType)) {
                                finalSqlCondition.append(var11.toString());
                            } else if ("ORACLE".equals(databaseType) && dbType.toLowerCase().indexOf("date") >= 0) {
                                finalSqlCondition.append(toDatetime(var11.toString()));
                            } else {
                                finalSqlCondition.append("'" + var11.toString() + "'");
                            }
                        }

                        Object var12 = queryParam.get(dbFieldName + "_end");
                        if (var12 != null) {
                            finalSqlCondition.append(" AND " + dbFieldName + "<=");
                            if (org.jeecg.modules.online.cgform.d.k.isNumber(dbType)) {
                                finalSqlCondition.append(var12.toString());
                            } else if ("ORACLE".equals(databaseType) && dbType.toLowerCase().indexOf("date") >= 0) {
                                finalSqlCondition.append(toDatetime(var12.toString()));
                            } else {
                                finalSqlCondition.append("'" + var12.toString() + "'");
                            }
                        }
                    }
                } while(var11 == null);

                String var13;
                if ("list_multi".equals(onlCgformField.getFieldShowType())) {
                    String[] var18 = var11.toString().split(",");
                    var13 = "";

                    for(int var14 = 0; var14 < var18.length; ++var14) {
                        if (oConvertUtils.isNotEmpty(var13)) {
                            var13 = var13 + " or " + dbFieldName + " like " + "'%" + var18[var14] + ",%'" + " or " + dbFieldName + " like " + "'%," + var18[var14] + "%'";
                        } else {
                            var13 = dbFieldName + " like " + "'%" + var18[var14] + ",%'" + " or " + dbFieldName + " like " + "'%," + var18[var14] + "%'";
                        }
                    }

                    finalSqlCondition.append(" AND (" + var13 + ")");
                }

                if ("popup".equals(onlCgformField.getFieldShowType())) {
                    finalSqlCondition.append(" AND (" + b(dbFieldName, var11.toString()) + ")");
                } else if ("ORACLE".equals(databaseType) && dbType.toLowerCase().indexOf("date") >= 0) {
                    finalSqlCondition.append(" AND " + dbFieldName + "=" + toDatetime(var11.toString()));
                } else {
                    boolean var19 = !org.jeecg.modules.online.cgform.d.k.isNumber(dbType);
                    var13 = QueryGenerator.getSingleQueryConditionSql(dbFieldName, "", var11, var19);
                    finalSqlCondition.append(" AND " + var13);
                }
            }
        }
    }

    // 处理自定义sql片段
    public static void handleDataRulesForCustomSql(Map<String, List<SysPermissionDataRuleModel>> rulesMap, StringBuffer finalSqlCondition) {
        List<String> sqlRulesKey = rulesMap.keySet().stream()
                .filter(e -> oConvertUtils.isNotEmpty(e) && e.startsWith("SQL_RULES_COLUMN"))
                .collect(Collectors.toList());
        if (!sqlRulesKey.isEmpty()) {
            // online表单里的自定义sql数据权限，和编码方式有所区别，sqlRulesKey其实是唯一的，这里以列表形式展开防止特殊情况
            finalSqlCondition.append(org.jeecg.modules.online.cgform.d.b.AND);
            finalSqlCondition.append("(");
            for (int idx = 0; idx < sqlRulesKey.size(); idx++) {
                String sqlRuleKey = sqlRulesKey.get(idx);
                // 每个自定义sql片段一定是唯一的
                for (int i1 = 0; i1 < rulesMap.get(sqlRuleKey).size(); i1++) {
                    SysPermissionDataRuleModel sqlRule = rulesMap.get(sqlRuleKey).get(i1);
                    if (i1 == 0) {
                        finalSqlCondition.append("(").append(getSqlRuleValue(sqlRule.getRuleValue())).append(")");
                    } else {
                        finalSqlCondition.append(org.jeecg.modules.online.cgform.d.b.OR).append("(").append(getSqlRuleValue(sqlRule.getRuleValue())).append(")");
                    }
                }
                if (sqlRulesKey.size() > 1 && idx < sqlRulesKey.size() - 1) {
                    finalSqlCondition.append(org.jeecg.modules.online.cgform.d.b.OR);
                }
            }
            finalSqlCondition.append(")");
        }
    }

    public static String a(Map<String, Object> var0) {
        Object var1 = var0.get("superQueryParams");
        if (var1 != null && !StringUtils.isBlank(var1.toString())) {
            IOnlCgformFieldService var2 = (IOnlCgformFieldService)SpringContextUtils.getBean(IOnlCgformFieldService.class);
            String var3 = null;

            try {
                var3 = URLDecoder.decode(var1.toString(), "UTF-8");
            } catch (UnsupportedEncodingException var19) {
                var19.printStackTrace();
                return "";
            }

            JSONArray var4 = JSONArray.parseArray(var3);
            Object var5 = var0.get("superQueryMatchType");
            MatchTypeEnum var6 = MatchTypeEnum.getByValue(var5);
            if (var6 == null) {
                var6 = MatchTypeEnum.AND;
            }

            HashMap var7 = new HashMap();
            StringBuilder var8 = (new StringBuilder(" AND ")).append("(");

            for(int var9 = 0; var9 < var4.size(); ++var9) {
                JSONObject var10 = var4.getJSONObject(var9);
                String var11 = var10.getString("field");
                String[] var12 = var11.split(",");
                if (var12.length == 1) {
                    a(var8, var11, var10, var6, (JSONObject)null, var9 == 0);
                } else if (var12.length == 2) {
                    String var13 = var12[0];
                    String var14 = var12[1];
                    JSONObject var15 = (JSONObject)var7.get(var13);
                    if (var15 == null) {
                        List var16 = var2.queryFormFieldsByTableName(var13);
                        var15 = new JSONObject(3);
                        Iterator var17 = var16.iterator();

                        while(var17.hasNext()) {
                            OnlCgformField var18 = (OnlCgformField)var17.next();
                            if (StringUtils.isNotBlank(var18.getMainTable())) {
                                var15.put("subTableName", var13);
                                var15.put("subField", var18.getDbFieldName());
                                var15.put("mainTable", var18.getMainTable());
                                var15.put("mainField", var18.getMainField());
                            }
                        }

                        var7.put(var13, var15);
                    }

                    a(var8, var14, var10, var6, var15, var9 == 0);
                }
            }

            return var8.append(")").toString();
        } else {
            return "";
        }
    }

    private static void a(StringBuilder var0, String var1, JSONObject var2, MatchTypeEnum var3, JSONObject var4, boolean var5) {
        if (!var5) {
            var0.append(" ").append(var3.getValue()).append(" ");
        }

        String var6 = var2.getString("type");
        String var7 = var2.getString("val");
        String var8 = a(var6, var7);
        QueryRuleEnum var9 = QueryRuleEnum.getByValue(var2.getString("rule"));
        if (var9 == null) {
            var9 = QueryRuleEnum.EQ;
        }

        if (var4 != null) {
            String var10 = var4.getString("subTableName");
            String var11 = var4.getString("subField");
            String var12 = var4.getString("mainTable");
            String var13 = var4.getString("mainField");
            var0.append("(").append(var13).append(" IN (SELECT ").append(var11).append(" FROM ").append(var10).append(" WHERE ");
            if ("popup".equals(var6)) {
                var0.append(b(var1, var7));
            } else {
                var0.append(var1);
                a(var0, var9, var7, var8, var6);
            }

            var0.append("))");
        } else if ("popup".equals(var6)) {
            var0.append(b(var1, var7));
        } else {
            var0.append(var1);
            a(var0, var9, var7, var8, var6);
        }

    }

    private static void a(StringBuilder var0, QueryRuleEnum var1, String var2, String var3, String var4) {
        if ("date".equals(var4) && "ORACLE".equalsIgnoreCase(getDatabseType())) {
            var3 = var3.replace("'", "");
            if (var3.length() == 10) {
                var3 = toDate(var3);
            } else {
                var3 = toDatetime(var3);
            }
        }

        switch(var1) {
            case GT:
                var0.append(">").append(var3);
                break;
            case GE:
                var0.append(">=").append(var3);
                break;
            case LT:
                var0.append("<").append(var3);
                break;
            case LE:
                var0.append("<=").append(var3);
                break;
            case NE:
                var0.append("!=").append(var3);
                break;
            case IN:
                var0.append(" IN (");
                String[] var5 = var2.split(",");

                for(int var6 = 0; var6 < var5.length; ++var6) {
                    String var7 = var5[var6];
                    if (StringUtils.isNotBlank(var7)) {
                        String var8 = a(var4, var7);
                        var0.append(var8);
                        if (var6 < var5.length - 1) {
                            var0.append(",");
                        }
                    }
                }

                var0.append(")");
                break;
            case LIKE:
                var0.append(" like ").append("N").append("'").append("%").append(var2).append("%").append("'");
                break;
            case LEFT_LIKE:
                var0.append(" like ").append("N").append("'").append("%").append(var2).append("'");
                break;
            case RIGHT_LIKE:
                var0.append(" like ").append("N").append("'").append(var2).append("%").append("'");
                break;
            case EQ:
            default:
                var0.append("=").append(var3);
        }

    }

    private static String a(String var0, String var1) {
        if (!"int".equals(var0) && !"number".equals(var0)) {
            if ("date".equals(var0)) {
                return "'" + var1 + "'";
            } else {
                return "SQLSERVER".equals(getDatabseType()) ? "N'" + var1 + "'" : "'" + var1 + "'";
            }
        } else {
            return var1;
        }
    }

    public static Map<String, Object> getRequestParams(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        HashMap<String, Object> params = new HashMap<>();
        Iterator<Entry<String, String[]>> iterator = parameterMap.entrySet().iterator();
        String key = "";
        String value = "";

        for(Object tempVal; iterator.hasNext(); params.put(key, value)) {
            Entry<String, String[]> entry = iterator.next();
            key = entry.getKey();
            tempVal = entry.getValue();
            if (!"_t".equals(key) && null != tempVal) {
                String[] vals = (String[]) tempVal;
                for (String string : vals) {
                    value = string + ",";
                }
                value = value.substring(0, value.length() - 1);
            } else {
                value = "";
            }
        }
        return params;
    }

    public static boolean a(String var0, List<OnlCgformField> var1) {
        Iterator var2 = var1.iterator();

        OnlCgformField var3;
        do {
            if (!var2.hasNext()) {
                return false;
            }

            var3 = (OnlCgformField)var2.next();
        } while(!var0.equals(var3.getDbFieldName()));

        return true;
    }

    public static JSONObject a(List<OnlCgformField> var0, List<String> var1, org.jeecg.modules.online.cgform.model.d var2) {
        new JSONObject();
        ArrayList var4 = new ArrayList();
        ArrayList var5 = new ArrayList();
        ISysBaseAPI var6 = (ISysBaseAPI)SpringContextUtils.getBean(ISysBaseAPI.class);
        OnlCgformHeadMapper var7 = (OnlCgformHeadMapper)SpringContextUtils.getBean(OnlCgformHeadMapper.class);
        ArrayList var8 = new ArrayList();
        Iterator var9 = var0.iterator();

        while(true) {
            OnlCgformField var10;
            String var11;
            do {
                do {
                    if (!var9.hasNext()) {
                        JSONObject var3;
                        JsonSchemaDescrip var23;
                        if (var4.size() > 0) {
                            var23 = new JsonSchemaDescrip(var4);
                            var3 = JsonschemaUtil.getJsonSchema(var23, var5);
                        } else {
                            var23 = new JsonSchemaDescrip();
                            var3 = JsonschemaUtil.getJsonSchema(var23, var5);
                        }

                        return var3;
                    }

                    var10 = (OnlCgformField)var9.next();
                    var11 = var10.getDbFieldName();
                } while("id".equals(var11));
            } while(var8.contains(var11));

            String var12 = var10.getDbFieldTxt();
            if ("1".equals(var10.getFieldMustInput())) {
                var4.add(var11);
            }

            String var13 = var10.getFieldShowType();
            Object var14 = null;
            if ("switch".equals(var13)) {
                var14 = new SwitchProperty(var11, var12, var10.getFieldExtendJson());
            } else if (c(var13)) {
                Object var30 = new ArrayList();
                if (oConvertUtils.isNotEmpty(var10.getDictTable())) {
                    var30 = var6.queryTableDictItemsByCode(var10.getDictTable(), var10.getDictText(), var10.getDictField());
                } else if (oConvertUtils.isNotEmpty(var10.getDictField())) {
                    var30 = var6.queryDictItemsByCode(var10.getDictField());
                }

                var14 = new StringProperty(var11, var12, var13, var10.getDbLength(), (List)var30);
                if (org.jeecg.modules.online.cgform.d.k.isNumber(var10.getDbType())) {
                    ((CommonProperty)var14).setType("number");
                }
            } else if (org.jeecg.modules.online.cgform.d.k.isNumber(var10.getDbType())) {
                NumberProperty var29 = new NumberProperty(var11, var12, "number");
                if (CgformValidPatternEnum.INTEGER.getType().equals(var10.getFieldValidType())) {
                    var29.setPattern(CgformValidPatternEnum.INTEGER.getPattern());
                }

                var14 = var29;
            } else {
                String var16;
                String var31;
                if (!"popup".equals(var13)) {
                    if ("sel_search".equals(var13)) {
                        var14 = new DictProperty(var11, var12, var10.getDictTable(), var10.getDictField(), var10.getDictText());
                    } else if ("link_down".equals(var13)) {
                        LinkDownProperty var27 = new LinkDownProperty(var11, var12, var10.getDictTable());
                        a((LinkDownProperty)var27, (List)var0, (List)var8);
                        var14 = var27;
                    } else {
                        String var25;
                        if ("sel_tree".equals(var13)) {
                            var25 = var10.getDictText();
                            String[] var32 = var25.split(",");
                            var31 = var10.getDictTable() + "," + var32[2] + "," + var32[0];
                            TreeSelectProperty var35 = new TreeSelectProperty(var11, var12, var31, var32[1], var10.getDictField());
                            if (var32.length > 3) {
                                var35.setHasChildField(var32[3]);
                            }

                            var14 = var35;
                        } else if ("cat_tree".equals(var13)) {
                            var25 = var10.getDictText();
                            var16 = var10.getDictField();
                            var31 = "0";
                            if (oConvertUtils.isNotEmpty(var16) && !"0".equals(var16)) {
                                var31 = var7.queryCategoryIdByCode(var16);
                            }

                            if (oConvertUtils.isEmpty(var25)) {
                                var14 = new TreeSelectProperty(var11, var12, var31);
                            } else {
                                var14 = new TreeSelectProperty(var11, var12, var31, var25);
                                HiddenProperty var34 = new HiddenProperty(var25, var25);
                                var5.add(var34);
                            }
                        } else if (var2 != null && var11.equals(var2.getFieldName())) {
                            var25 = var2.getTableName() + "," + var2.getTextField() + "," + var2.getCodeField();
                            TreeSelectProperty var28 = new TreeSelectProperty(var11, var12, var25, var2.getPidField(), var2.getPidValue());
                            var28.setHasChildField(var2.getHsaChildField());
                            var28.setPidComponent(1);
                            var14 = var28;
                        } else {
                            StringProperty var24 = new StringProperty(var11, var12, var13, var10.getDbLength());
                            if (oConvertUtils.isNotEmpty(var10.getFieldValidType())) {
                                CgformValidPatternEnum var26 = CgformValidPatternEnum.getPatternInfoByType(var10.getFieldValidType());
                                if (var26 != null) {
                                    if (CgformValidPatternEnum.NOTNULL == var26) {
                                        var4.add(var11);
                                    } else {
                                        var24.setPattern(var26.getPattern());
                                        var24.setErrorInfo(var26.getMsg());
                                    }
                                } else {
                                    var24.setPattern(var10.getFieldValidType());
                                    var24.setErrorInfo("输入的值不合法");
                                }
                            }

                            var14 = var24;
                        }
                    }
                } else {
                    PopupProperty var15 = new PopupProperty(var11, var12, var10.getDictTable(), var10.getDictText(), var10.getDictField());
                    var16 = var10.getDictText();
                    if (var16 != null && !var16.equals("")) {
                        String[] var17 = var16.split(",");
                        String[] var18 = var17;
                        int var19 = var17.length;

                        for(int var20 = 0; var20 < var19; ++var20) {
                            String var21 = var18[var20];
                            if (!a(var21, var0)) {
                                HiddenProperty var22 = new HiddenProperty(var21, var21);
                                var22.setOrder(var10.getOrderNum());
                                var5.add(var22);
                            }
                        }
                    }

                    var31 = var10.getFieldExtendJson();
                    if (var31 != null && !var31.equals("")) {
                        JSONObject var33 = JSONObject.parseObject(var31);
                        if (var33.containsKey("popupMulti")) {
                            //没有这个方法，排查一下
                            //var15.setPopupMulti(var33.getBoolean("popupMulti"));
                        }
                    }

                    var14 = var15;
                }
            }

            if (var10.getIsReadOnly() == 1 || var1 != null && var1.indexOf(var11) >= 0) {
                ((CommonProperty)var14).setDisabled(true);
            }

            ((CommonProperty)var14).setOrder(var10.getOrderNum());
            ((CommonProperty)var14).setDefVal(var10.getFieldDefaultValue());
            ((CommonProperty)var14).setFieldExtendJson(var10.getFieldExtendJson());
            ((CommonProperty)var14).setDbPointLength(var10.getDbPointLength());
            var5.add(var14);
        }
    }

    public static JSONObject b(String var0, List<OnlCgformField> var1) {
        new JSONObject();
        ArrayList var3 = new ArrayList();
        ArrayList var4 = new ArrayList();
        ISysBaseAPI var5 = (ISysBaseAPI)SpringContextUtils.getBean(ISysBaseAPI.class);
        Iterator var6 = var1.iterator();

        while(var6.hasNext()) {
            OnlCgformField var7 = (OnlCgformField)var6.next();
            String var8 = var7.getDbFieldName();
            if (!"id".equals(var8)) {
                String var9 = var7.getDbFieldTxt();
                if ("1".equals(var7.getFieldMustInput())) {
                    var3.add(var8);
                }

                String var10 = var7.getFieldShowType();
                String var11 = var7.getDictField();
                Object var12 = null;
                if (org.jeecg.modules.online.cgform.d.k.isNumber(var7.getDbType())) {
                    var12 = new NumberProperty(var8, var9, "number");
                } else if (c(var10)) {
                    List var13 = var5.queryDictItemsByCode(var11);
                    var12 = new StringProperty(var8, var9, var10, var7.getDbLength(), var13);
                } else {
                    var12 = new StringProperty(var8, var9, var10, var7.getDbLength());
                }

                ((CommonProperty)var12).setOrder(var7.getOrderNum());
                var4.add(var12);
            }
        }

        JSONObject var2 = JsonschemaUtil.getSubJsonSchema(var0, var3, var4);
        return var2;
    }

    public static Set<String> a(List<OnlCgformField> var0) {
        HashSet var1 = new HashSet();
        Iterator var2 = var0.iterator();

        OnlCgformField var3;
        String var4;
        while(var2.hasNext()) {
            var3 = (OnlCgformField)var2.next();
            if ("popup".equals(var3.getFieldShowType())) {
                var4 = var3.getDictText();
                if (var4 != null && !var4.equals("")) {
                    var1.addAll((Collection)Arrays.stream(var4.split(",")).collect(Collectors.toSet()));
                }
            }

            if ("cat_tree".equals(var3.getFieldShowType())) {
                var4 = var3.getDictText();
                if (oConvertUtils.isNotEmpty(var4)) {
                    var1.add(var4);
                }
            }
        }

        var2 = var0.iterator();

        while(var2.hasNext()) {
            var3 = (OnlCgformField)var2.next();
            var4 = var3.getDbFieldName();
            if (var3.getIsShowForm() == 1 && var1.contains(var4)) {
                var1.remove(var4);
            }
        }

        return var1;
    }

    public static Map<String, Object> a(String var0, List<OnlCgformField> var1, JSONObject var2) {
        StringBuffer var3 = new StringBuffer();
        StringBuffer var4 = new StringBuffer();
        String var5 = "";

        try {
            var5 = org.jeecg.modules.online.config.b.d.getDatabaseType();
        } catch (SQLException var15) {
            var15.printStackTrace();
        } catch (DBException var16) {
            var16.printStackTrace();
        }

        HashMap var6 = new HashMap();
        boolean var7 = false;
        String var8 = null;
        LoginUser var9 = (LoginUser)SecurityUtils.getSubject().getPrincipal();
        if (var9 == null) {
            throw new JeecgBootException("online保存表单数据异常:系统未找到当前登陆用户信息");
        } else {
            Set var10 = a(var1);
            Iterator var11 = var1.iterator();

            while(true) {
                while(var11.hasNext()) {
                    OnlCgformField var12 = (OnlCgformField)var11.next();
                    String var13 = var12.getDbFieldName();
                    if (null == var13) {
                        ay.info("--------online保存表单数据遇见空名称的字段------->>" + var12.getId());
                    } else if ("id".equals(var13.toLowerCase())) {
                        var7 = true;
                        var8 = var2.getString(var13);
                    } else {
                        a(var12, var9, var2, "CREATE_BY", "CREATE_TIME", "SYS_ORG_CODE");
                        if ("bpm_status".equals(var13.toLowerCase())) {
                            var3.append("," + var13);
                            var4.append(",'1'");
                        } else {
                            String var14;
                            if (var10.contains(var13)) {
                                var3.append("," + var13);
                                var14 = org.jeecg.modules.online.cgform.d.k.a(var5, var12, var2, var6);
                                var4.append("," + var14);
                            } else if (var12.getIsShowForm() == 1 || !oConvertUtils.isEmpty(var12.getMainField()) || !oConvertUtils.isEmpty(var12.getDbDefaultVal())) {
                                if (var2.get(var13) == null) {
                                    if (oConvertUtils.isEmpty(var12.getDbDefaultVal())) {
                                        continue;
                                    }

                                    var2.put(var13, var12.getDbDefaultVal());
                                }

                                if ("".equals(var2.get(var13))) {
                                    var14 = var12.getDbType();
                                    if (org.jeecg.modules.online.cgform.d.k.isNumber(var14) || org.jeecg.modules.online.cgform.d.k.b(var14)) {
                                        continue;
                                    }
                                }

                                var3.append("," + var13);
                                var14 = org.jeecg.modules.online.cgform.d.k.a(var5, var12, var2, var6);
                                var4.append("," + var14);
                            }
                        }
                    }
                }

                if (var7) {
                    if (oConvertUtils.isEmpty(var8)) {
                        var8 = a();
                    }
                } else {
                    var8 = a();
                }

                String var17 = "insert into " + getTableName(var0) + "(" + "id" + var3.toString() + ") values(" + "'" + var8 + "'" + var4.toString() + ")";
                var6.put("execute_sql_string", var17);
                ay.debug("--动态表单保存sql-->" + var17);
                return var6;
            }
        }
    }

    public static Map<String, Object> b(String var0, List<OnlCgformField> var1, JSONObject var2) {
        StringBuffer var3 = new StringBuffer();
        HashMap var4 = new HashMap();
        String var5 = "";

        try {
            var5 = org.jeecg.modules.online.config.b.d.getDatabaseType();
        } catch (SQLException var12) {
            var12.printStackTrace();
        } catch (DBException var13) {
            var13.printStackTrace();
        }

        LoginUser var6 = (LoginUser)SecurityUtils.getSubject().getPrincipal();
        if (var6 == null) {
            throw new JeecgBootException("online修改表单数据异常:系统未找到当前登陆用户信息");
        } else {
            Set var7 = a(var1);
            Iterator var8 = var1.iterator();

            while(true) {
                while(var8.hasNext()) {
                    OnlCgformField var9 = (OnlCgformField)var8.next();
                    String var10 = var9.getDbFieldName();
                    if (null == var10) {
                        ay.info("--------online修改表单数据遇见空名称的字段------->>" + var9.getId());
                    } else {
                        a(var9, var6, var2, "UPDATE_BY", "UPDATE_TIME");
                        String var11;
                        if (var7.contains(var10) && var2.get(var10) != null && !"".equals(var2.getString(var10))) {
                            var11 = org.jeecg.modules.online.cgform.d.k.a(var5, var9, var2, var4);
                            var3.append(var10 + "=" + var11 + ",");
                        } else if (var9.getIsShowForm() == 1 && !"id".equals(var10)) {
                            if ("".equals(var2.get(var10))) {
                                var11 = var9.getDbType();
                                if (org.jeecg.modules.online.cgform.d.k.isNumber(var11) || org.jeecg.modules.online.cgform.d.k.b(var11)) {
                                    continue;
                                }
                            }

                            if (!oConvertUtils.isNotEmpty(var9.getMainTable()) || !oConvertUtils.isNotEmpty(var9.getMainField())) {
                                var11 = org.jeecg.modules.online.cgform.d.k.a(var5, var9, var2, var4);
                                var3.append(var10 + "=" + var11 + ",");
                            }
                        }
                    }
                }

                String var14 = var3.toString();
                if (var14.endsWith(",")) {
                    var14 = var14.substring(0, var14.length() - 1);
                }

                String var15 = "update " + getTableName(var0) + " set " + var14 + " where  " + "id" + "=" + "'" + var2.getString("id") + "'";
                ay.debug("--动态表单编辑sql-->" + var15);
                var4.put("execute_sql_string", var15);
                return var4;
            }
        }
    }

    public static String a(String var0, List<OnlCgformField> var1, String var2) {
        return a(var0, var1, "id", var2);
    }

    public static String a(String var0, List<OnlCgformField> var1, String var2, String var3) {
        StringBuffer var4 = new StringBuffer();
        var4.append("SELECT ");
        int var5 = var1.size();
        boolean var6 = false;

        for(int var7 = 0; var7 < var5; ++var7) {
            String var8 = ((OnlCgformField)var1.get(var7)).getDbFieldName();
            if ("id".equals(var8)) {
                var6 = true;
            }

            var4.append(var8);
            if (var5 > var7 + 1) {
                var4.append(",");
            }
        }

        if (!var6) {
            var4.append(",id");
        }
        // 去除 WHERE 1=1
        if (StringUtils.isNotBlank(var3)) {
            var4.append(" FROM " + getTableName(var0) + " where " + var2 + "=" + "'" + var3 + "'");
        } else {
            var4.append(" FROM " + getTableName(var0));
        }
        // var4.append(" FROM " + f(var0) + " where 1=1  " + " AND " + var2 + "=" + "'" + var3 + "'");
        return var4.toString();
    }

    public static void a(OnlCgformField var0, LoginUser var1, JSONObject var2, String... var3) {
        String var4 = var0.getDbFieldName();
        boolean var5 = false;
        String[] var6 = var3;
        int var7 = var3.length;

        for(int var8 = 0; var8 < var7; ++var8) {
            String var9 = var6[var8];
            if (var4.toUpperCase().equals(var9)) {
                if (var0.getIsShowForm() == 1) {
                    if (var2.get(var4) == null) {
                        var5 = true;
                    }
                } else {
                    var0.setIsShowForm(1);
                    var5 = true;
                }

                if (var5) {
                    byte var11 = -1;
                    switch(var9.hashCode()) {
                        case -909973894:
                            if (var9.equals("CREATE_BY")) {
                                var11 = 0;
                            }
                            break;
                        case -99751974:
                            if (var9.equals("SYS_ORG_CODE")) {
                                var11 = 4;
                            }
                            break;
                        case 837427085:
                            if (var9.equals("UPDATE_BY")) {
                                var11 = 2;
                            }
                            break;
                        case 1609067651:
                            if (var9.equals("UPDATE_TIME")) {
                                var11 = 3;
                            }
                            break;
                        case 1688939568:
                            if (var9.equals("CREATE_TIME")) {
                                var11 = 1;
                            }
                    }

                    switch(var11) {
                        case 0:
                            var2.put(var4, var1.getUsername());
                            return;
                        case 1:
                            var0.setFieldShowType("datetime");
                            var2.put(var4, DateUtils.formatDateTime());
                            return;
                        case 2:
                            var2.put(var4, var1.getUsername());
                            return;
                        case 3:
                            var0.setFieldShowType("datetime");
                            var2.put(var4, DateUtils.formatDateTime());
                            return;
                        case 4:
                            var2.put(var4, var1.getOrgCode());
                    }
                }
                break;
            }
        }

    }

    public static boolean a(Object var0, Object var1) {
        if (oConvertUtils.isEmpty(var0) && oConvertUtils.isEmpty(var1)) {
            return true;
        } else {
            return oConvertUtils.isNotEmpty(var0) && var0.equals(var1);
        }
    }

    public static boolean a(OnlCgformField var0, OnlCgformField var1) {
        return !a((Object)var0.getDbFieldName(), (Object)var1.getDbFieldName()) || !a((Object)var0.getDbFieldTxt(), (Object)var1.getDbFieldTxt()) || !a((Object)var0.getDbLength(), (Object)var1.getDbLength()) || !a((Object)var0.getDbPointLength(), (Object)var1.getDbPointLength()) || !a((Object)var0.getDbType(), (Object)var1.getDbType()) || !a((Object)var0.getDbIsNull(), (Object)var1.getDbIsNull()) || !a((Object)var0.getDbIsKey(), (Object)var1.getDbIsKey()) || !a((Object)var0.getDbDefaultVal(), (Object)var1.getDbDefaultVal());
    }

    public static boolean a(OnlCgformIndex var0, OnlCgformIndex var1) {
        return !a((Object)var0.getIndexName(), (Object)var1.getIndexName()) || !a((Object)var0.getIndexField(), (Object)var1.getIndexField()) || !a((Object)var0.getIndexType(), (Object)var1.getIndexType());
    }

    public static boolean a(OnlCgformHead var0, OnlCgformHead var1) {
        return !a((Object)var0.getTableName(), (Object)var1.getTableName()) || !a((Object)var0.getTableTxt(), (Object)var1.getTableTxt());
    }

    public static String generateSql(String tableName, List<OnlCgformField> onlCgformFields, Map<String, Object> record) {
        StringBuffer conditionSb = new StringBuffer();
        StringBuffer fieldsNameSb = new StringBuffer();

        for (OnlCgformField cgformField : onlCgformFields) {
            String dbFieldName = cgformField.getDbFieldName();
            String dbType = cgformField.getDbType();
            if (cgformField.getIsShowList() == 1) {
                fieldsNameSb.append("," + dbFieldName);
            }

            boolean isString;
            String conditionStr;
            if (oConvertUtils.isNotEmpty(cgformField.getMainField())) {
                isString = !org.jeecg.modules.online.cgform.d.k.isNumber(dbType);
                conditionStr = QueryGenerator.getSingleQueryConditionSql(dbFieldName, "", record.get(dbFieldName), isString);
                if (!"".equals(conditionStr)) {
                    conditionSb.append(" AND " + conditionStr);
                }
            }

            if (cgformField.getDbFieldName().toUpperCase().equals(MYBATIS_LOGIC_DELETE_FIELD.toUpperCase())) {
                //规定的删除字段
                isString = !org.jeecg.modules.online.cgform.d.k.isNumber(dbType);
                conditionStr = QueryGenerator.getSingleQueryConditionSql(dbFieldName, "", MYBATIS_LOGIC_NOT_DELETE_FIELD_VAL, isString);
                conditionSb.append(" AND " + conditionStr);
            }

            if (cgformField.getIsQuery() == 1) {
                if ("single".equals(cgformField.getQueryMode())) {
                    if (record.get(dbFieldName) != null) {
                        isString = !org.jeecg.modules.online.cgform.d.k.isNumber(dbType);
                        conditionStr = QueryGenerator.getSingleQueryConditionSql(dbFieldName, "", record.get(dbFieldName), isString);
                        if (!"".equals(conditionStr)) {
                            conditionSb.append(" AND " + conditionStr);
                        }
                    }
                } else {
                    Object beginField = record.get(dbFieldName + "_begin");
                    if (beginField != null) {
                        conditionSb.append(" AND " + dbFieldName + ">=");
                        if (org.jeecg.modules.online.cgform.d.k.isNumber(dbType)) {
                            conditionSb.append(beginField);
                        } else {
                            conditionSb.append("'" + beginField + "'");
                        }
                    }

                    Object endField = record.get(dbFieldName + "_end");
                    if (endField != null) {
                        conditionSb.append(" AND " + dbFieldName + "<=");
                        if (org.jeecg.modules.online.cgform.d.k.isNumber(dbType)) {
                            conditionSb.append(endField);
                        } else {
                            conditionSb.append("'" + endField + "'");
                        }
                    }
                }
            }
        }

        String whereCondition = conditionSb.toString();
        if (whereCondition.startsWith(org.jeecg.modules.online.cgform.d.b.AND)) {
            whereCondition = whereCondition.replaceFirst(org.jeecg.modules.online.cgform.d.b.AND, "");
        }
        // return "SELECT id" + var4.toString() + " FROM " + f(var0) + " where 1=1  " + var3.toString();
        // 去除 WHERE 1=1
        if (StringUtils.isNotBlank(whereCondition)) {
            return "SELECT id" + fieldsNameSb + " FROM " + getTableName(tableName) + " where " + whereCondition;
        } else {
            return "SELECT id" + fieldsNameSb + " FROM " + getTableName(tableName);
        }
    }

    public static List<ExcelExportEntity> a(List<OnlCgformField> var0, String var1) {
        ArrayList var2 = new ArrayList();

        for(int var3 = 0; var3 < var0.size(); ++var3) {
            if ((null == var1 || !var1.equals(((OnlCgformField)var0.get(var3)).getDbFieldName())) && ((OnlCgformField)var0.get(var3)).getIsShowList() == 1) {
                ExcelExportEntity var4 = new ExcelExportEntity(((OnlCgformField)var0.get(var3)).getDbFieldTxt(), ((OnlCgformField)var0.get(var3)).getDbFieldName());
                int var5 = ((OnlCgformField)var0.get(var3)).getDbLength() == 0 ? 12 : (((OnlCgformField)var0.get(var3)).getDbLength() > 30 ? 30 : ((OnlCgformField)var0.get(var3)).getDbLength());
                if (((OnlCgformField)var0.get(var3)).getFieldShowType().equals("date")) {
                    var4.setFormat("yyyy-MM-dd");
                } else if (((OnlCgformField)var0.get(var3)).getFieldShowType().equals("datetime")) {
                    var4.setFormat("yyyy-MM-dd HH:mm:ss");
                }

                if (var5 < 10) {
                    var5 = 10;
                }

                var4.setWidth((double)var5);
                var2.add(var4);
            }
        }

        return var2;
    }

    public static boolean a(OnlCgformEnhanceJava var0) {
        String var1 = var0.getCgJavaType();
        String var2 = var0.getCgJavaValue();
        if (oConvertUtils.isNotEmpty(var2)) {
            try {
                if ("class".equals(var1)) {
                    Class var3 = Class.forName(var2);
                    if (var3 == null || var3.newInstance() == null) {
                        return false;
                    }
                }

                if ("spring".equals(var1)) {
                    Object var5 = SpringContextUtils.getBean(var2);
                    if (var5 == null) {
                        return false;
                    }
                }
            } catch (Exception var4) {
                ay.error(var4.getMessage(), var4);
                return false;
            }
        }

        return true;
    }

    public static void b(List<String> var0) {
        Collections.sort(var0, new Comparator<String>() {
            public int compare(String var1, String var2) {
                if (var1 != null && var2 != null) {
                    if (var1.compareTo(var2) > 0) {
                        return 1;
                    } else if (var1.compareTo(var2) < 0) {
                        return -1;
                    } else {
                        return var1.compareTo(var2) == 0 ? 0 : 0;
                    }
                } else {
                    return -1;
                }
            }
        });
    }

    public static void c(List<String> var0) {
        Collections.sort(var0, new Comparator<String>() {
            public int compare(String var1, String var2) {
                if (var1 != null && var2 != null) {
                    if (var1.length() > var2.length()) {
                        return 1;
                    } else if (var1.length() < var2.length()) {
                        return -1;
                    } else if (var1.compareTo(var2) > 0) {
                        return 1;
                    } else if (var1.compareTo(var2) < 0) {
                        return -1;
                    } else {
                        return var1.compareTo(var2) == 0 ? 0 : 0;
                    }
                } else {
                    return -1;
                }
            }
        });
    }

    private static String handleRuleValue(String var0, boolean isString, QueryRuleEnum ruleEnum) {
        if (ruleEnum == QueryRuleEnum.IN) {
            return handleRuleValue(var0, isString);
        } else {
            return isString ? "'" + QueryGenerator.converRuleValue(var0) + "'" : QueryGenerator.converRuleValue(var0);
        }
    }

    private static String handleRuleValue(String var0, boolean var1) {
        if (var0 != null && var0.length() != 0) {
            var0 = QueryGenerator.converRuleValue(var0);
            String[] var2 = var0.split(",");
            ArrayList var3 = new ArrayList();
            String[] var4 = var2;
            int var5 = var2.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                String var7 = var4[var6];
                if (var7 != null && var7.length() != 0) {
                    if (var1) {
                        var3.add("'" + var7 + "'");
                    } else {
                        var3.add(var7);
                    }
                }
            }

            return "(" + StringUtils.join(var3, ",") + ")";
        } else {
            return "()";
        }
    }

    // 为一个字段应用多个数据规则，相同字段的规则会拼接OR关系
    public static void handleDataRulesForOneField(String databaseType, List<SysPermissionDataRuleModel> ruleModels, String dbFieldName, String dbType, StringBuffer buffer) {
        if (!ruleModels.isEmpty()) {
            buffer.append(org.jeecg.modules.online.cgform.d.b.AND);
            buffer.append("(");
            for (int roleModelIndex = 0; roleModelIndex < ruleModels.size(); roleModelIndex++) {
                SysPermissionDataRuleModel ruleModel = ruleModels.get(roleModelIndex);
                handleDataRuleToPerSql(databaseType, dbFieldName, dbType, buffer, ruleModel, false);
                if (ruleModels.size() > 1 && roleModelIndex != ruleModels.size() - 1) {
                    buffer.append(org.jeecg.modules.online.cgform.d.b.OR);
                }
            }
            buffer.append(")");
        }
    }

    // 拼接查询条件
    public static void handleDataRuleToPerSql(String databaseType, String dbFieldName, String dbType, StringBuffer buffer, SysPermissionDataRuleModel ruleModel, boolean needPreAnd) {
        QueryRuleEnum ruleEnum = QueryRuleEnum.getByValue(ruleModel.getRuleConditions());
        boolean isString = !org.jeecg.modules.online.cgform.d.k.isNumber(dbType);
        String ruleValue = handleRuleValue(ruleModel.getRuleValue(), isString, ruleEnum);
        if (ruleValue != null && ruleEnum != null) {
            if ("ORACLE".equalsIgnoreCase(databaseType) && "Date".equals(dbType)) {
                ruleValue = ruleValue.replace("'", "");
                if (ruleValue.length() == 10) {
                    ruleValue = toDate(ruleValue);
                } else {
                    ruleValue = toDatetime(ruleValue);
                }
            }
            if (needPreAnd) {
                buffer.append(org.jeecg.modules.online.cgform.d.b.AND);
            }
            switch(ruleEnum) {
                case GT:
                    buffer.append(dbFieldName).append(">").append(ruleValue);
                    break;
                case GE:
                    buffer.append(dbFieldName).append(">=").append(ruleValue);
                    break;
                case LT:
                    buffer.append(dbFieldName).append("<").append(ruleValue);
                    break;
                case LE:
                    buffer.append(dbFieldName).append("<=").append(ruleValue);
                    break;
                case NE:
                    buffer.append(dbFieldName).append(" <> ").append(ruleValue);
                    break;
                case IN:
                    buffer.append(dbFieldName).append(" IN ").append(ruleValue);
                    break;
                case LIKE:
                    buffer.append(dbFieldName).append(" LIKE '%").append(QueryGenerator.trimSingleQuote(ruleValue)).append("%'");
                    break;
                case LEFT_LIKE:
                    buffer.append(dbFieldName).append(" LIKE '%").append(QueryGenerator.trimSingleQuote(ruleValue)).append("'");
                    break;
                case RIGHT_LIKE:
                    buffer.append(dbFieldName).append(" LIKE '").append(QueryGenerator.trimSingleQuote(ruleValue)).append("%'");
                    break;
                case EQ:
                    buffer.append(dbFieldName).append("=").append(ruleValue);
                    break;
                default:
                    ay.info("--查询规则未匹配到---");
            }
        }
    }

    public static String a(String var0, JSONObject var1) {
        if (var1 == null) {
            return var0;
        } else {
            var0 = var0.replace("#{UUID}", UUIDGenerator.generate());
            Set var2 = QueryGenerator.getSqlRuleParams(var0);
            Iterator var3 = var2.iterator();

            while(true) {
                while(var3.hasNext()) {
                    String var4 = (String)var3.next();
                    String var5;
                    if (var1.get(var4.toUpperCase()) == null && var1.get(var4.toLowerCase()) == null) {
                        var5 = QueryGenerator.converRuleValue(var4);
                        var0 = var0.replace("#{" + var4 + "}", var5);
                    } else {
                        var5 = null;
                        if (var1.containsKey(var4.toLowerCase())) {
                            var5 = var1.getString(var4.toLowerCase());
                        } else if (var1.containsKey(var4.toUpperCase())) {
                            var5 = var1.getString(var4.toUpperCase());
                        }

                        var0 = var0.replace("#{" + var4 + "}", var5);
                    }
                }

                return var0;
            }
        }
    }

    public static String c(String var0, List<OnlCgformButton> var1) {
        var0 = d(var0, var1);
        String[] var2 = "beforeAdd,beforeEdit,afterAdd,afterEdit,beforeDelete,afterDelete,mounted,created".split(",");
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            String var5 = var2[var4];
            Pattern var6;
            Matcher var7;
            if ("beforeAdd,afterAdd,mounted,created".indexOf(var5) >= 0) {
                var6 = Pattern.compile("(" + var5 + "\\s*\\(\\)\\s*\\{)");
                var7 = var6.matcher(var0);
                if (var7.find()) {
                    var0 = var0.replace(var7.group(0), var5 + "(that){const getAction=this._getAction,postAction=this._postAction,deleteAction=this._deleteAction;");
                }
            } else {
                var6 = Pattern.compile("(" + var5 + "\\s*\\(row\\)\\s*\\{)");
                var7 = var6.matcher(var0);
                if (var7.find()) {
                    var0 = var0.replace(var7.group(0), var5 + "(that,row){const getAction=this._getAction,postAction=this._postAction,deleteAction=this._deleteAction;");
                } else {
                    Pattern var8 = Pattern.compile("(" + var5 + "\\s*\\(\\)\\s*\\{)");
                    Matcher var9 = var8.matcher(var0);
                    if (var9.find()) {
                        var0 = var0.replace(var9.group(0), var5 + "(that){const getAction=this._getAction,postAction=this._postAction,deleteAction=this._deleteAction;");
                    }
                }
            }
        }

        return d(var0);
    }

    public static void a(OnlCgformEnhanceJs var0, String var1, List<OnlCgformField> var2) {
        if (var0 != null && !oConvertUtils.isEmpty(var0.getCgJs())) {
            String var3 = var0.getCgJs();
            String var4 = "onlChange";
            Pattern var5 = Pattern.compile("(" + var1 + "_" + var4 + "\\s*\\(\\)\\s*\\{)");
            Matcher var6 = var5.matcher(var3);
            if (var6.find()) {
                var3 = var3.replace(var6.group(0), var1 + "_" + var4 + "(){const getAction=this._getAction,postAction=this._postAction,deleteAction=this._deleteAction;");
                Iterator var7 = var2.iterator();

                while(var7.hasNext()) {
                    OnlCgformField var8 = (OnlCgformField)var7.next();
                    Pattern var9 = Pattern.compile("(" + var8.getDbFieldName() + "\\s*\\(\\))");
                    Matcher var10 = var9.matcher(var3);
                    if (var10.find()) {
                        var3 = var3.replace(var10.group(0), var8.getDbFieldName() + "(that,event)");
                    }
                }
            }

            var0.setCgJs(var3);
        }
    }

    public static void a(OnlCgformEnhanceJs var0, String var1, List<OnlCgformField> var2, boolean var3) {
        if (var0 != null && !oConvertUtils.isEmpty(var0.getCgJs())) {
            String var4 = var0.getCgJs();
            String var5 = "onlChange";
            Pattern var6 = Pattern.compile("([^_]" + var5 + "\\s*\\(\\)\\s*\\{)");
            Matcher var7 = var6.matcher(var4);
            if (var7.find()) {
                var4 = var4.replace(var7.group(0), var5 + "(){const getAction=this._getAction,postAction=this._postAction,deleteAction=this._deleteAction;");
                Iterator var8 = var2.iterator();

                while(var8.hasNext()) {
                    OnlCgformField var9 = (OnlCgformField)var8.next();
                    Pattern var10 = Pattern.compile("(" + var9.getDbFieldName() + "\\s*\\(\\))");
                    Matcher var11 = var10.matcher(var4);
                    if (var11.find()) {
                        var4 = var4.replace(var11.group(0), var9.getDbFieldName() + "(that,event)");
                    }
                }
            }

            var0.setCgJs(var4);
            a(var0);
            a(var0, var1, var2);
        }
    }

    public static void a(OnlCgformEnhanceJs var0) {
        String var1 = var0.getCgJs();
        String var2 = "show";
        Pattern var3 = Pattern.compile("(" + var2 + "\\s*\\(\\)\\s*\\{)");
        Matcher var4 = var3.matcher(var1);
        if (var4.find()) {
            var1 = var1.replace(var4.group(0), var2 + "(that){const getAction=this._getAction,postAction=this._postAction,deleteAction=this._deleteAction;");
        }

        var0.setCgJs(var1);
    }

    public static String d(String var0) {
        ay.debug("最终的增强JS", var0);
        return "class OnlineEnhanceJs{constructor(getAction,postAction,deleteAction){this._getAction=getAction;this._postAction=postAction;this._deleteAction=deleteAction;}" + var0 + "}";
    }

    public static String d(String var0, List<OnlCgformButton> var1) {
        if (var1 != null) {
            Iterator var2 = var1.iterator();

            while(true) {
                while(var2.hasNext()) {
                    OnlCgformButton var3 = (OnlCgformButton)var2.next();
                    String var4 = var3.getButtonCode();
                    Pattern var5;
                    Matcher var6;
                    if ("link".equals(var3.getButtonStyle()) || "link-pre".equals(var3.getButtonStyle()) || "link-after".equals(var3.getButtonStyle())) {
                        var5 = Pattern.compile("(" + var4 + "\\s*\\(row\\)\\s*\\{)");
                        var6 = var5.matcher(var0);
                        if (var6.find()) {
                            var0 = var0.replace(var6.group(0), var4 + "(that,row){const getAction=this._getAction,postAction=this._postAction,deleteAction=this._deleteAction;");
                        } else {
                            Pattern var7 = Pattern.compile("(" + var4 + "\\s*\\(\\)\\s*\\{)");
                            Matcher var8 = var7.matcher(var0);
                            if (var8.find()) {
                                var0 = var0.replace(var8.group(0), var4 + "(that){const getAction=this._getAction,postAction=this._postAction,deleteAction=this._deleteAction;");
                            }
                        }
                    } else if ("button".equals(var3.getButtonStyle()) || "form".equals(var3.getButtonStyle())) {
                        var5 = Pattern.compile("(" + var4 + "\\s*\\(\\)\\s*\\{)");
                        var6 = var5.matcher(var0);
                        if (var6.find()) {
                            var0 = var0.replace(var6.group(0), var4 + "(that){const getAction=this._getAction,postAction=this._postAction,deleteAction=this._deleteAction;");
                        }
                    }
                }

                return var0;
            }
        } else {
            return var0;
        }
    }

    public static JSONArray a(List<OnlCgformField> var0, List<String> var1) {
        JSONArray var2 = new JSONArray();
        ISysBaseAPI var3 = (ISysBaseAPI)SpringContextUtils.getBean(ISysBaseAPI.class);
        Iterator var4 = var0.iterator();

        while(true) {
            OnlCgformField var5;
            String var6;
            do {
                if (!var4.hasNext()) {
                    return var2;
                }

                var5 = (OnlCgformField)var4.next();
                var6 = var5.getDbFieldName();
            } while("id".equals(var6));

            JSONObject var7 = new JSONObject();
            if (var1.indexOf(var6) >= 0) {
                var7.put("disabled", true);
            }

            if (var5.getIsReadOnly() != null && 1 == var5.getIsReadOnly()) {
                var7.put("disabled", true);
            }

            var7.put("title", var5.getDbFieldTxt());
            var7.put("key", var6);
            String var8 = c(var5);
            var7.put("type", var8);
            if (var5.getFieldLength() == null) {
                var7.put("width", "186px");
            } else if (!"sel_depart".equals(var8) && !"sel_user".equals(var8)) {
                var7.put("width", var5.getFieldLength() + "px");
            } else {
                var7.put("width", "");
            }

            if (var8.equals("file") || var8.equals("image")) {
                var7.put("responseName", "message");
                var7.put("token", true);
            }

            if (var8.equals("switch")) {
                var7.put("type", "checkbox");
                JSONArray var9 = new JSONArray();
                if (oConvertUtils.isEmpty(var5.getFieldExtendJson())) {
                    var9.add("Y");
                    var9.add("N");
                } else {
                    var9 = JSONArray.parseArray(var5.getFieldExtendJson());
                }

                var7.put("customValue", var9);
            }

            if (var8.equals("popup")) {
                var7.put("popupCode", var5.getDictTable());
                var7.put("orgFields", var5.getDictField());
                var7.put("destFields", var5.getDictText());
                String var17 = var5.getDictText();
                if (var17 != null && !var17.equals("")) {
                    ArrayList var10 = new ArrayList();
                    String[] var11 = var17.split(",");
                    String[] var12 = var11;
                    int var13 = var11.length;

                    for(int var14 = 0; var14 < var13; ++var14) {
                        String var15 = var12[var14];
                        if (!a(var15, var0)) {
                            var10.add(var15);
                            JSONObject var16 = new JSONObject();
                            var16.put("title", var15);
                            var16.put("key", var15);
                            var16.put("type", "hidden");
                            var2.add(var16);
                        }
                    }
                }
            }

            var7.put("defaultValue", var5.getDbDefaultVal());
            var7.put("fieldDefaultValue", var5.getFieldDefaultValue());
            var7.put("scopedSlots", var5.getScopedSlots());
            var7.put("scopedSlotsRenderCode", var5.getScopedSlotsRenderCode());
            var7.put("placeholder", "请输入" + var5.getDbFieldTxt());
            var7.put("validateRules", b(var5));
            if ("list".equals(var5.getFieldShowType()) || "radio".equals(var5.getFieldShowType()) || "checkbox_meta".equals(var5.getFieldShowType()) || "list_multi".equals(var5.getFieldShowType()) || "sel_search".equals(var5.getFieldShowType())) {
                Object var18 = new ArrayList();
                if (oConvertUtils.isNotEmpty(var5.getDictTable())) {
                    var18 = var3.queryTableDictItemsByCode(var5.getDictTable(), var5.getDictText(), var5.getDictField());
                } else if (oConvertUtils.isNotEmpty(var5.getDictField())) {
                    var18 = var3.queryDictItemsByCode(var5.getDictField());
                }

                var7.put("options", var18);
                if ("list_multi".equals(var5.getFieldShowType())) {
                    var7.put("width", "230px");
                }
            }

            var7.put("fieldExtendJson", var5.getFieldExtendJson());
            var2.add(var7);
        }
    }

    private static JSONArray b(OnlCgformField var0) {
        JSONArray var1 = new JSONArray();
        JSONObject var2;
        if (var0.getDbIsNull() == 0 || "1".equals(var0.getFieldMustInput())) {
            var2 = new JSONObject();
            var2.put("required", true);
            var2.put("message", var0.getDbFieldTxt() + "不能为空!");
            var1.add(var2);
        }

        if (oConvertUtils.isNotEmpty(var0.getFieldValidType())) {
            var2 = new JSONObject();
            if ("only".equals(var0.getFieldValidType())) {
                var2.put("unique", true);
                var2.put("message", var0.getDbFieldTxt() + "不能重复");
            } else {
                var2.put("pattern", var0.getFieldValidType());
                var2.put("message", var0.getDbFieldTxt() + "格式不正确");
            }

            var1.add(var2);
        }

        return var1;
    }

    public static Map<String, Object> b(Map<String, Object> var0) {
        HashMap var1 = new HashMap();
        if (var0 != null && !var0.isEmpty()) {
            Set var2 = var0.keySet();

            Object var5;
            String var9;
            for(Iterator var3 = var2.iterator(); var3.hasNext(); var1.put(var9, var5 == null ? "" : var5)) {
                String var4 = (String)var3.next();
                var5 = var0.get(var4);
                if (var5 instanceof Clob) {
                    var5 = a((Clob)var5);
                } else if (var5 instanceof byte[]) {
                    var5 = new String((byte[])((byte[])var5));
                } else if (var5 instanceof Blob) {
                    try {
                        if (var5 != null) {
                            Blob var6 = (Blob)var5;
                            var5 = new String(var6.getBytes(1L, (int)var6.length()), "UTF-8");
                        }
                    } catch (Exception var8) {
                        var8.printStackTrace();
                    }
                }

                var9 = var4.toLowerCase();
                if (var5 != null && var5 instanceof String) {
                    String var7 = var5.toString();
                    if (var7.startsWith("[") && var7.endsWith("]")) {
                        // todo 这里图片上传后 多个列表url解析出现问题 数据格式
                        //  [{type=img, name=echarts.png, status=done, uid=vc-upload-1693186298043-5, url=http://cdn.kcz66.com/%E5%A4%B4%E5%83%8F.jpg}]
                        var5 = JSONArray.parseArray(var7);
                    }
                }
            }

            return var1;
        } else {
            return var1;
        }
    }

    public static JSONObject a(JSONObject var0) {
        if (!org.jeecg.modules.online.config.b.d.a()) {
            return var0;
        } else {
            JSONObject var1 = new JSONObject();
            if (var0 != null && !var0.isEmpty()) {
                Set var2 = var0.keySet();
                Iterator var3 = var2.iterator();

                while(var3.hasNext()) {
                    String var4 = (String)var3.next();
                    String var5 = var4.toLowerCase();
                    var1.put(var5, var0.get(var4));
                }

                return var1;
            } else {
                return var1;
            }
        }
    }

    public static List<Map<String, Object>> d(List<Map<String, Object>> var0) {
        ArrayList var1 = new ArrayList();
        Iterator var2 = var0.iterator();

        while(var2.hasNext()) {
            Map var3 = (Map)var2.next();
            HashMap var4 = new HashMap();
            Set var5 = var3.keySet();

            Object var8;
            String var12;
            for(Iterator var6 = var5.iterator(); var6.hasNext(); var4.put(var12, var8 == null ? "" : var8)) {
                String var7 = (String)var6.next();
                var8 = var3.get(var7);
                if (var8 instanceof Clob) {
                    var8 = a((Clob)var8);
                } else if (var8 instanceof byte[]) {
                    var8 = new String((byte[])((byte[])var8));
                } else if (var8 instanceof Blob) {
                    try {
                        if (var8 != null) {
                            Blob var9 = (Blob)var8;
                            var8 = new String(var9.getBytes(1L, (int)var9.length()), "UTF-8");
                        }
                    } catch (Exception var11) {
                        var11.printStackTrace();
                    }
                }

                var12 = var7.toLowerCase();
                if (var8 != null && var8 instanceof String) {
                    String var10 = var8.toString();
                    if (var10.startsWith("[") && var10.endsWith("]")) {
                        var8 = JSONArray.parseArray(var10);
                    }
                }
            }

            var1.add(var4);
        }

        return var1;
    }

    public static String a(Clob var0) {
        String var1 = "";

        try {
            Reader var2 = var0.getCharacterStream();
            char[] var3 = new char[(int)var0.length()];
            var2.read(var3);
            var1 = new String(var3);
            var2.close();
        } catch (IOException var4) {
            var4.printStackTrace();
        } catch (SQLException var5) {
            var5.printStackTrace();
        }

        return var1;
    }

    public static Map<String, Object> c(String var0, List<OnlCgformField> var1, JSONObject var2) {
        StringBuffer var3 = new StringBuffer();
        StringBuffer var4 = new StringBuffer();
        String var5 = "";

        try {
            var5 = org.jeecg.modules.online.config.b.d.getDatabaseType();
        } catch (SQLException var14) {
            var14.printStackTrace();
        } catch (DBException var15) {
            var15.printStackTrace();
        }

        HashMap var6 = new HashMap();
        boolean var7 = false;
        String var8 = null;
        LoginUser var9 = (LoginUser)SecurityUtils.getSubject().getPrincipal();
        if (var9 == null) {
            throw new JeecgBootException("online保存表单数据异常:系统未找到当前登陆用户信息");
        } else {
            Iterator var10 = var1.iterator();

            while(true) {
                while(var10.hasNext()) {
                    OnlCgformField var11 = (OnlCgformField)var10.next();
                    String var12 = var11.getDbFieldName();
                    if (null == var12) {
                        ay.info("--------online保存表单数据遇见空名称的字段------->>" + var11.getId());
                    } else if (var2.get(var12) != null || "CREATE_BY".equalsIgnoreCase(var12) || "CREATE_TIME".equalsIgnoreCase(var12) || "SYS_ORG_CODE".equalsIgnoreCase(var12)) {
                        a(var11, var9, var2, "CREATE_BY", "CREATE_TIME", "SYS_ORG_CODE");
                        String var13;
                        if ("".equals(var2.get(var12))) {
                            var13 = var11.getDbType();
                            if (org.jeecg.modules.online.cgform.d.k.isNumber(var13) || org.jeecg.modules.online.cgform.d.k.b(var13)) {
                                continue;
                            }
                        }

                        if ("id".equals(var12.toLowerCase())) {
                            var7 = true;
                            var8 = var2.getString(var12);
                        } else {
                            var3.append("," + var12);
                            var13 = org.jeecg.modules.online.cgform.d.k.a(var5, var11, var2, var6);
                            var4.append("," + var13);
                        }
                    }
                }

                if (!var7 || oConvertUtils.isEmpty(var8)) {
                    var8 = a();
                }

                String var16 = "insert into " + getTableName(var0) + "(" + "id" + var3.toString() + ") values(" + "'" + var8 + "'" + var4.toString() + ")";
                var6.put("execute_sql_string", var16);
                ay.debug("--表单设计器表单保存sql-->" + var16);
                return var6;
            }
        }
    }

    public static Map<String, Object> d(String var0, List<OnlCgformField> var1, JSONObject var2) {
        StringBuffer var3 = new StringBuffer();
        HashMap var4 = new HashMap();
        String var5 = "";

        try {
            var5 = org.jeecg.modules.online.config.b.d.getDatabaseType();
        } catch (SQLException var11) {
            var11.printStackTrace();
        } catch (DBException var12) {
            var12.printStackTrace();
        }

        LoginUser var6 = (LoginUser)SecurityUtils.getSubject().getPrincipal();
        if (var6 == null) {
            throw new JeecgBootException("online保存表单数据异常:系统未找到当前登陆用户信息");
        } else {
            Iterator var7 = var1.iterator();

            while(true) {
                while(var7.hasNext()) {
                    OnlCgformField var8 = (OnlCgformField)var7.next();
                    String var9 = var8.getDbFieldName();
                    if (null == var9) {
                        ay.info("--------online修改表单数据遇见空名称的字段------->>" + var8.getId());
                    } else if (!"id".equals(var9) && (var2.get(var9) != null || "UPDATE_BY".equalsIgnoreCase(var9) || "UPDATE_TIME".equalsIgnoreCase(var9) || "SYS_ORG_CODE".equalsIgnoreCase(var9))) {
                        a(var8, var6, var2, "UPDATE_BY", "UPDATE_TIME", "SYS_ORG_CODE");
                        String var10;
                        if ("".equals(var2.get(var9))) {
                            var10 = var8.getDbType();
                            if (org.jeecg.modules.online.cgform.d.k.isNumber(var10) || org.jeecg.modules.online.cgform.d.k.b(var10)) {
                                continue;
                            }
                        }

                        var10 = org.jeecg.modules.online.cgform.d.k.a(var5, var8, var2, var4);
                        var3.append(var9 + "=" + var10 + ",");
                    }
                }

                String var13 = var3.toString();
                if (var13.endsWith(",")) {
                    var13 = var13.substring(0, var13.length() - 1);
                }

                String var14 = "update " + getTableName(var0) + " set " + var13 + " where  " + "id" + "=" + "'" + var2.getString("id") + "'";
                ay.debug("--表单设计器表单编辑sql-->" + var14);
                var4.put("execute_sql_string", var14);
                return var4;
            }
        }
    }

    public static Map<String, Object> a(String var0, String var1, String var2) {
        HashMap var3 = new HashMap();
        String var4 = "update " + getTableName(var0) + " set " + var1 + "=" + "'" + 0 + "'" + " where  " + "id" + "=" + "'" + var2 + "'";
        ay.info("--修改树节点状态：为无子节点sql-->" + var4);
        var3.put("execute_sql_string", var4);
        return var3;
    }

    public static String e(String var0) {
        return var0 != null && !"".equals(var0) && !"0".equals(var0) ? "CODE like '" + var0 + "%" + "'" : "";
    }

    public static String getTableName(String var0) {
        return Pattern.matches("^[a-zA-z].*\\$\\d+$", var0) ? var0.substring(0, var0.lastIndexOf("$")) : var0;
    }

    public static void a(LinkDownProperty var0, List<OnlCgformField> var1, List<String> var2) {
        String var3 = var0.getDictTable();
        JSONObject var4 = JSONObject.parseObject(var3);
        String var5 = var4.getString("linkField");
        ArrayList var6 = new ArrayList();
        if (oConvertUtils.isNotEmpty(var5)) {
            String[] var7 = var5.split(",");
            Iterator var8 = var1.iterator();

            label26:
            while(true) {
                while(true) {
                    if (!var8.hasNext()) {
                        break label26;
                    }

                    OnlCgformField var9 = (OnlCgformField)var8.next();
                    String var10 = var9.getDbFieldName();
                    String[] var11 = var7;
                    int var12 = var7.length;

                    for(int var13 = 0; var13 < var12; ++var13) {
                        String var14 = var11[var13];
                        if (var14.equals(var10)) {
                            var2.add(var10);
                            var6.add(new BaseColumn(var9.getDbFieldTxt(), var10));
                            break;
                        }
                    }
                }
            }
        }

        var0.setOtherColumns(var6);
    }

    public static String a(byte[] var0, String var1, String var2, String var3) {
        return CommonUtils.uploadOnlineImage(var0, var1, var2, var3);
    }

    public static List<String> e(List<OnlCgformField> var0) {
        ArrayList var1 = new ArrayList();
        Iterator var2 = var0.iterator();

        while(var2.hasNext()) {
            OnlCgformField var3 = (OnlCgformField)var2.next();
            if ("image".equals(var3.getFieldShowType())) {
                var1.add(var3.getDbFieldTxt());
            }
        }

        return var1;
    }

    public static List<String> b(List<OnlCgformField> var0, String var1) {
        ArrayList var2 = new ArrayList();
        Iterator var3 = var0.iterator();

        while(var3.hasNext()) {
            OnlCgformField var4 = (OnlCgformField)var3.next();
            if ("image".equals(var4.getFieldShowType())) {
                var2.add(var1 + "_" + var4.getDbFieldTxt());
            }
        }

        return var2;
    }

    public static String a() {
        long var0 = IdWorker.getId();
        return String.valueOf(var0);
    }

    public static String a(Exception var0) {
        String var1 = var0.getCause() != null ? var0.getCause().getMessage() : var0.getMessage();
        if (var1.indexOf("ORA-01452") != -1) {
            var1 = "ORA-01452: 无法 CREATE UNIQUE INDEX; 找到重复的关键字";
        } else if (var1.indexOf("duplicate key") != -1) {
            var1 = "无法 CREATE UNIQUE INDEX; 找到重复的关键字";
        }

        return var1;
    }

    public static List<DictModel> a(OnlCgformField var0) {
        ArrayList var1 = new ArrayList();
        String var2 = var0.getFieldExtendJson();
        String var3 = "是";
        String var4 = "否";
        JSONArray var5 = JSONArray.parseArray("[\"Y\",\"N\"]");
        if (oConvertUtils.isNotEmpty(var2)) {
            var5 = JSONArray.parseArray(var2);
        }

        DictModel var6 = new DictModel(var5.getString(0), var3);
        DictModel var7 = new DictModel(var5.getString(1), var4);
        var1.add(var6);
        var1.add(var7);
        return var1;
    }

    private static String c(OnlCgformField var0) {
        if ("checkbox".equals(var0.getFieldShowType())) {
            return "checkbox";
        } else if ("list".equals(var0.getFieldShowType())) {
            return "select";
        } else if ("switch".equals(var0.getFieldShowType())) {
            return "switch";
        } else if ("sel_user".equals(var0.getFieldShowType())) {
            return "sel_user";
        } else if ("sel_depart".equals(var0.getFieldShowType())) {
            return "sel_depart";
        } else if (!"image".equals(var0.getFieldShowType()) && !"file".equals(var0.getFieldShowType()) && !"radio".equals(var0.getFieldShowType()) && !"popup".equals(var0.getFieldShowType()) && !"list_multi".equals(var0.getFieldShowType()) && !"sel_search".equals(var0.getFieldShowType())) {
            if ("datetime".equals(var0.getFieldShowType())) {
                return "datetime";
            } else if ("date".equals(var0.getFieldShowType())) {
                return "date";
            } else if ("int".equals(var0.getDbType())) {
                return "inputNumber";
            } else {
                return !"double".equals(var0.getDbType()) && !"BigDecimal".equals(var0.getDbType()) ? "input" : "inputNumber";
            }
        } else {
            return var0.getFieldShowType();
        }
    }

    private static String getDatabseType() {
        if (oConvertUtils.isNotEmpty(aA)) {
            return aA;
        } else {
            try {
                aA = org.jeecg.modules.online.config.b.d.getDatabaseType();
                return aA;
            } catch (Exception var1) {
                var1.printStackTrace();
                return aA;
            }
        }
    }

    public static List<String> f(List<String> var0) {
        ArrayList var1 = new ArrayList();
        Iterator var2 = var0.iterator();

        while(var2.hasNext()) {
            String var3 = (String)var2.next();
            var1.add(var3.toLowerCase());
        }

        return var1;
    }

    private static String b(String var0, String var1) {
        String var2 = "";
        if (var1 != null && !"".equals(var1)) {
            String[] var3 = var1.split(",");

            for(int var4 = 0; var4 < var3.length; ++var4) {
                if (var4 > 0) {
                    var2 = var2 + " AND ";
                }

                var2 = var2 + var0 + " like ";
                if ("SQLSERVER".equals(getDatabseType())) {
                    var2 = var2 + "N";
                }

                var2 = var2 + "'%" + var3[var4] + "%" + "'";
            }

            ay.info(" POPUP fieldSql: " + var2);
            return var2;
        } else {
            return var2;
        }
    }

}
