package org.jeecg.modules.online.cgform.d;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.CommonAPI;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.system.query.MatchTypeEnum;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.query.QueryRuleEnum;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.system.vo.DictModel;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.system.vo.SysPermissionDataRuleModel;
import org.jeecg.common.util.*;
import org.jeecg.common.util.jsonschema.BaseColumn;
import org.jeecg.common.util.jsonschema.CommonProperty;
import org.jeecg.common.util.jsonschema.JsonSchemaDescrip;
import org.jeecg.common.util.jsonschema.JsonschemaUtil;
import org.jeecg.common.util.jsonschema.validate.*;
import org.jeecg.modules.online.cgform.CgformDB;
import org.jeecg.modules.online.cgform.entity.*;
import org.jeecg.modules.online.cgform.enums.CgformConstant;
import org.jeecg.modules.online.cgform.enums.CgformValidPatternEnum;
import org.jeecg.modules.online.cgform.mapper.OnlCgformHeadMapper;
import org.jeecg.modules.online.cgform.service.IOnlCgformFieldService;
import org.jeecg.modules.online.config.exception.DBException;
import org.jeecgframework.poi.excel.entity.params.ExcelExportEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/* compiled from: CgformUtil.java */
/* loaded from: hibernate-common-ol-5.4.74(2).jar:org/jeecg/modules/online/cgform/d/b.class */
public class b {
    private static final Logger ay = LoggerFactory.getLogger(b.class);
    public static final String SELECT = "SELECT ";
    public static final String FROM = " FROM ";
    public static final String AND = " AND ";
    public static final String LIKE = " like ";
    public static final String COUNT = " COUNT(*) ";
    public static final String WHERE_1_1 = " where 1=1  ";
    public static final String WHERE = " where  ";
    public static final String ORDERBY = " ORDER BY ";
    public static final String ASC = "asc";
    public static final String DESC = "desc";
    public static final String EQ = "=";
    public static final String NEQ = "!=";
    public static final String GE = ">=";
    public static final String GT = ">";
    public static final String LE = "<=";
    public static final String sp = "<";
    public static final String OR = " or ";
    public static final String sr = "Y";
    public static final String ss = "$";
    public static final String st = "CREATE_TIME";
    public static final String su = "CREATE_BY";
    public static final String sv = "UPDATE_TIME";
    public static final String sw = "UPDATE_BY";
    public static final String sx = "SYS_ORG_CODE";
    public static final int sy = 2;
    public static final String sz = "'";
    public static final String sA = "N";
    public static final String DOT_STRING = ",";
    public static final String single = "single";
    public static final String sD = "id";
    public static final String sE = "bpm_status";
    public static final String sF = "1";
    public static final String sG = "force";
    public static final String sH = "normal";
    public static final String sI = "switch";
    public static final String POPUP = "popup";
    public static final String sK = "sel_search";
    public static final String sL = "image";
    public static final String sM = "file";
    public static final String sN = "sel_tree";
    public static final String CAT_TREE = "cat_tree";
    public static final String sP = "link_down";
    public static final String sQ = "SYS_USER";
    public static final String sR = "REALNAME";
    public static final String S = "USERNAME";
    public static final String sT = "SYS_DEPART";
    public static final String sU = "DEPART_NAME";
    public static final String sV = "ID";
    public static final String sW = "SYS_CATEGORY";
    public static final String sX = "NAME";
    public static final String sY = "CODE";
    public static final String sZ = "ID";
    public static final String aa = "PID";
    public static final String ab = "HAS_CHILD";
    public static final String ac = "popupMulti";
    public static final String ad = "sel_search";
    public static final String ae = "sub-table-design_";
    public static final String af = "import";
    public static final String ag = "export";
    public static final String ah = "query";
    public static final String form = "form";
    public static final String aj = "list";
    public static final String ak = "1";
    public static final String al = "start";
    public static final String am = "erp";
    public static final String an = "exportSingleOnly";
    public static final String ao = "isSingleTableImport";
    public static final String foreignKeys = "foreignKeys";
    public static final int aq = 1;
    public static final int ar = 2;
    public static final int as = 0;
    public static final int at = 1;
    public static final String au = "1";
    public static final String av = "id";
    public static final String aw = "center";
    public static final String ax = "showLength";
    public static final String orderRule = "orderRule";
    private static final String az = "beforeAdd,beforeEdit,afterAdd,afterEdit,beforeDelete,afterDelete,mounted,created";
    private static String aA;

    public static void assembleSelect(String str, List<OnlCgformField> list, StringBuffer stringBuffer) {
        if (list == null || list.size() == 0) {
            stringBuffer.append("SELECT id");
        } else {
            stringBuffer.append(SELECT);
            int size = list.size();
            boolean hasIdField = false;
            for (int i2 = 0; i2 < size; i2++) {
                OnlCgformField onlCgformField = list.get(i2);
                if ("id".equals(onlCgformField.getDbFieldName())) {
                    hasIdField = true;
                }
                if (CAT_TREE.equals(onlCgformField.getFieldShowType()) && oConvertUtils.isNotEmpty(onlCgformField.getDictText())) {
                    stringBuffer.append(onlCgformField.getDictText() + DOT_STRING);
                }
                if (i2 == size - 1) {
                    stringBuffer.append(onlCgformField.getDbFieldName() + " ");
                } else {
                    stringBuffer.append(onlCgformField.getDbFieldName() + DOT_STRING);
                }
            }
            if (!hasIdField) {
                stringBuffer.append(",id");
            }
        }
        stringBuffer.append(FROM + f(str));
    }

    public static String a(String str) {
        return " to_date('" + str + "','yyyy-MM-dd HH24:mi:ss')";
    }

    public static String b(String str) {
        return " to_date('" + str + "','yyyy-MM-dd')";
    }

    public static boolean c(String str) {
        if (aj.equals(str) || "radio".equals(str) || "checkbox".equals(str) || "list_multi".equals(str)) {
            return true;
        }
        return false;
    }

    public static String a(List<OnlCgformField> list, Map<String, Object> map, List<String> list2) {
        return assembleQuery(list, map, list2, (List<SysPermissionDataRuleModel>) null);
    }

    // 组装查询条件
    public static String assembleQuery(List<OnlCgformField> list, Map<String, Object> map, List<String> needList, List<SysPermissionDataRuleModel> ruleModels) {
        String str;
        StringBuffer finalSqlCondition = new StringBuffer();
        String databaseType = "";
        try {
            databaseType = org.jeecg.modules.online.config.b.d.getDatabaseType();
        } catch (SQLException e2) {
            e2.printStackTrace();
        } catch (DBException e3) {
            e3.printStackTrace();
        }
        Map<String, List<SysPermissionDataRuleModel>> rulesMap = QueryGenerator.getRulesMap(ruleModels);

        // 处理自定义sql片段
        CgformDB.handleDataRulesForCustomSql(rulesMap, finalSqlCondition);

        // 组装查询条件
        for (OnlCgformField onlCgformField : list) {
            String dbFieldName = onlCgformField.getDbFieldName();
            String dbType = onlCgformField.getDbType();
            // 统一调用：为一个字段应用多个数据规则，相同字段的规则会拼接OR关系
            String camelDbFieldName = oConvertUtils.camelNames(dbFieldName);
            if (rulesMap.containsKey(dbFieldName)) {
                CgformDB.handleDataRulesForOneField(databaseType, rulesMap.get(dbFieldName), dbFieldName, dbType, finalSqlCondition);
            } else if (rulesMap.containsKey(camelDbFieldName)) {
                CgformDB.handleDataRulesForOneField(databaseType, rulesMap.get(camelDbFieldName), dbFieldName, dbType, finalSqlCondition);
            }
            if (needList != null && needList.contains(dbFieldName)) {
                onlCgformField.setIsQuery(1);
                onlCgformField.setQueryMode(single);
            }
            if (oConvertUtils.isNotEmpty(onlCgformField.getMainField()) && oConvertUtils.isNotEmpty(onlCgformField.getMainTable())) {
                onlCgformField.setIsQuery(1);
                onlCgformField.setQueryMode(single);
            }
            if (1 == onlCgformField.getIsQuery().intValue()) {
                if (single.equals(onlCgformField.getQueryMode())) {
                    Object obj = map.get(dbFieldName);
                    if (obj != null) {
                        if ("list_multi".equals(onlCgformField.getFieldShowType())) {
                            String[] split = obj.toString().split(DOT_STRING);
                            String str4 = "";
                            for (int i2 = 0; i2 < split.length; i2++) {
                                if (oConvertUtils.isNotEmpty(str4)) {
                                    str = str4 + OR + dbFieldName + LIKE + "'%" + split[i2] + ",%'" + OR + dbFieldName + LIKE + "'%," + split[i2] + "%'";
                                } else {
                                    str = dbFieldName + LIKE + "'%" + split[i2] + ",%'" + OR + dbFieldName + LIKE + "'%," + split[i2] + "%'";
                                }
                                str4 = str;
                            }
                            finalSqlCondition.append(" AND (" + str4 + ")");
                        }
                        if (POPUP.equals(onlCgformField.getFieldShowType())) {
                            finalSqlCondition.append(" AND (" + b(dbFieldName, obj.toString()) + ")");
                        } else if ("ORACLE".equals(databaseType) && dbType.toLowerCase().indexOf(i.DATE) >= 0) {
                            finalSqlCondition.append(AND + dbFieldName + EQ + a(obj.toString()));
                        } else {
                            // FIXME 主要是这里调用了默认的构造入口
                            finalSqlCondition.append(AND + QueryGenerator.getSingleQueryConditionSql(dbFieldName, "", obj, !k.isNumber(dbType)));
                        }
                    }
                } else {
                    Object obj2 = map.get(dbFieldName + "_begin");
                    if (obj2 != null) {
                        finalSqlCondition.append(AND + dbFieldName + GE);
                        if (k.isNumber(dbType)) {
                            finalSqlCondition.append(obj2.toString());
                        } else if ("ORACLE".equals(databaseType) && dbType.toLowerCase().indexOf(i.DATE) >= 0) {
                            finalSqlCondition.append(a(obj2.toString()));
                        } else {
                            finalSqlCondition.append(sz + obj2.toString() + sz);
                        }
                    }
                    Object obj3 = map.get(dbFieldName + "_end");
                    if (obj3 != null) {
                        finalSqlCondition.append(AND + dbFieldName + LE);
                        if (k.isNumber(dbType)) {
                            finalSqlCondition.append(obj3.toString());
                        } else if ("ORACLE".equals(databaseType) && dbType.toLowerCase().indexOf(i.DATE) >= 0) {
                            finalSqlCondition.append(a(obj3.toString()));
                        } else {
                            finalSqlCondition.append(sz + obj3.toString() + sz);
                        }
                    }
                }
            }
        }
        return finalSqlCondition.toString();
    }

    public static String assembleSuperQuery(Map<String, Object> map) {
        Object obj = map.get("superQueryParams");
        if (obj == null || StringUtils.isBlank(obj.toString())) {
            return "";
        }
        IOnlCgformFieldService iOnlCgformFieldService = (IOnlCgformFieldService) SpringContextUtils.getBean(IOnlCgformFieldService.class);
        try {
            JSONArray parseArray = JSONArray.parseArray(URLDecoder.decode(obj.toString(), "UTF-8"));
            MatchTypeEnum byValue = MatchTypeEnum.getByValue(map.get("superQueryMatchType"));
            if (byValue == null) {
                byValue = MatchTypeEnum.AND;
            }
            HashMap hashMap = new HashMap();
            StringBuilder append = new StringBuilder(AND).append("(");
            int i2 = 0;
            while (i2 < parseArray.size()) {
                JSONObject jSONObject = parseArray.getJSONObject(i2);
                String string = jSONObject.getString("field");
                String[] split = string.split(DOT_STRING);
                if (split.length == 1) {
                    a(append, string, jSONObject, byValue, null, i2 == 0);
                } else if (split.length == 2) {
                    String str = split[0];
                    String str2 = split[1];
                    JSONObject jSONObject2 = (JSONObject) hashMap.get(str);
                    if (jSONObject2 == null) {
                        List<OnlCgformField> queryFormFieldsByTableName = iOnlCgformFieldService.queryFormFieldsByTableName(str);
                        jSONObject2 = new JSONObject(3);
                        for (OnlCgformField onlCgformField : queryFormFieldsByTableName) {
                            if (StringUtils.isNotBlank(onlCgformField.getMainTable())) {
                                jSONObject2.put("subTableName", str);
                                jSONObject2.put("subField", onlCgformField.getDbFieldName());
                                jSONObject2.put("mainTable", onlCgformField.getMainTable());
                                jSONObject2.put("mainField", onlCgformField.getMainField());
                            }
                        }
                        hashMap.put(str, jSONObject2);
                    }
                    a(append, str2, jSONObject, byValue, jSONObject2, i2 == 0);
                }
                i2++;
            }
            return append.append(")").toString();
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
            return "";
        }
    }

    private static void a(StringBuilder sb, String str, JSONObject jSONObject, MatchTypeEnum matchTypeEnum, JSONObject jSONObject2, boolean z2) {
        if (!z2) {
            sb.append(" ").append(matchTypeEnum.getValue()).append(" ");
        }
        String string = jSONObject.getString("type");
        String string2 = jSONObject.getString("val");
        String a2 = a(string, string2);
        QueryRuleEnum byValue = QueryRuleEnum.getByValue(jSONObject.getString("rule"));
        if (byValue == null) {
            byValue = QueryRuleEnum.EQ;
        }
        if (jSONObject2 != null) {
            String string3 = jSONObject2.getString("subTableName");
            String string4 = jSONObject2.getString("subField");
            jSONObject2.getString("mainTable");
            sb.append("(").append(jSONObject2.getString("mainField")).append(" IN (SELECT ").append(string4).append(sb).append(string3).append(" WHERE ");
            if (POPUP.equals(string)) {
                sb.append(b(str, string2));
            } else {
                sb.append(str);
                a(sb, byValue, string2, a2, string);
            }
            sb.append("))");
        } else if (POPUP.equals(string)) {
            sb.append(b(str, string2));
        } else {
            sb.append(str);
            a(sb, byValue, string2, a2, string);
        }
    }

    private static void a(StringBuilder sb, QueryRuleEnum queryRuleEnum, String str, String str2, String str3) {
        if (i.DATE.equals(str3) && "ORACLE".equalsIgnoreCase(getDatabseType())) {
            String replace = str2.replace(sz, "");
            if (replace.length() == 10) {
                str2 = b(replace);
            } else {
                str2 = a(replace);
            }
        }
        switch (AnonymousClass4.a[queryRuleEnum.ordinal()]) {
            case 1:
                sb.append(GT).append(str2);
                return;
            case 2:
                sb.append(GE).append(str2);
                return;
            case 3:
                sb.append(sp).append(str2);
                return;
            case 4:
                sb.append(LE).append(str2);
                return;
            case 5:
                sb.append(NEQ).append(str2);
                return;
            case 6:
                sb.append(" IN (");
                String[] split = str.split(DOT_STRING);
                for (int i2 = 0; i2 < split.length; i2++) {
                    String str4 = split[i2];
                    if (StringUtils.isNotBlank(str4)) {
                        sb.append(a(str3, str4));
                        if (i2 < split.length - 1) {
                            sb.append(DOT_STRING);
                        }
                    }
                }
                sb.append(")");
                return;
            case 7:
                sb.append(LIKE).append("N").append(sz).append("%").append(str).append("%").append(sz);
                return;
            case 8:
                sb.append(LIKE).append("N").append(sz).append("%").append(str).append(sz);
                return;
            case 9:
                sb.append(LIKE).append("N").append(sz).append(str).append("%").append(sz);
                return;
            case 10:
            default:
                sb.append(EQ).append(str2);
                return;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: CgformUtil.java */
    /* renamed from: org.jeecg.modules.online.cgform.d.b$4  reason: invalid class name */
    /* loaded from: hibernate-common-ol-5.4.74(2).jar:org/jeecg/modules/online/cgform/d/b$4.class */
    public static /* synthetic */ class AnonymousClass4 {
        static final /* synthetic */ int[] a = new int[QueryRuleEnum.values().length];

        static {
            try {
                a[QueryRuleEnum.GT.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[QueryRuleEnum.GE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                a[QueryRuleEnum.LT.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                a[QueryRuleEnum.LE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                a[QueryRuleEnum.NE.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                a[QueryRuleEnum.IN.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                a[QueryRuleEnum.LIKE.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                a[QueryRuleEnum.LEFT_LIKE.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                a[QueryRuleEnum.RIGHT_LIKE.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                a[QueryRuleEnum.EQ.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
        }
    }

    private static String a(String str, String str2) {
        if ("int".equals(str) || org.jeecg.modules.online.config.b.b.h.equals(str)) {
            return str2;
        }
        if (i.DATE.equals(str)) {
            return sz + str2 + sz;
        }
        if ("SQLSERVER".equals(getDatabseType())) {
            return "N'" + str2 + sz;
        }
        return sz + str2 + sz;
    }

    public static Map<String, Object> a(HttpServletRequest httpServletRequest) {
        String str;
        String[] strArr = new String[httpServletRequest.getParameterMap().size()];
        Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();
        HashMap hashMap = new HashMap();
        String str2 = "";
        for (Map.Entry entry : parameterMap.entrySet()) {
            String str3 = (String) entry.getKey();
            Object value = entry.getValue();
            if ("_t".equals(str3) || null == value) {
                str = "";
            } else if (value instanceof String[]) {
                for (int i2 = 0; i2 < ((String[]) value).length; i2++) {
                    str2 = strArr[i2] + DOT_STRING;
                }
                str = str2.substring(0, str2.length() - 1);
            } else {
                str = value.toString();
            }
            str2 = str;
            hashMap.put(str3, str2);
        }
        return hashMap;
    }

    public static boolean a(String str, List<OnlCgformField> list) {
        for (OnlCgformField onlCgformField : list) {
            if (str.equals(onlCgformField.getDbFieldName())) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v170, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r0v177, types: [java.util.List] */
    public static JSONObject a(List<OnlCgformField> list, List<String> list2, org.jeecg.modules.online.cgform.model.d dVar) {
        JSONObject jsonSchema;
        CommonProperty stringProperty;
        String[] split;
        new JSONObject();
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ISysBaseAPI iSysBaseAPI = (ISysBaseAPI) SpringContextUtils.getBean(ISysBaseAPI.class);
        OnlCgformHeadMapper onlCgformHeadMapper = (OnlCgformHeadMapper) SpringContextUtils.getBean(OnlCgformHeadMapper.class);
        ArrayList arrayList3 = new ArrayList();
        for (OnlCgformField onlCgformField : list) {
            String dbFieldName = onlCgformField.getDbFieldName();
            if (!"id".equals(dbFieldName) && !arrayList3.contains(dbFieldName)) {
                String dbFieldTxt = onlCgformField.getDbFieldTxt();
                if ("1".equals(onlCgformField.getFieldMustInput())) {
                    arrayList.add(dbFieldName);
                }
                String fieldShowType = onlCgformField.getFieldShowType();
                if (sI.equals(fieldShowType)) {
                    stringProperty = (new SwitchProperty(dbFieldName, dbFieldTxt, onlCgformField.getFieldExtendJson()));
                } else if (c(fieldShowType)) {
                    ArrayList arrayList4 = new ArrayList();
                    if (oConvertUtils.isNotEmpty(onlCgformField.getDictTable())) {
                        arrayList4 = (ArrayList) iSysBaseAPI.queryTableDictItemsByCode(onlCgformField.getDictTable(), onlCgformField.getDictText(), onlCgformField.getDictField());
                    } else if (oConvertUtils.isNotEmpty(onlCgformField.getDictField())) {
                        arrayList4 = (ArrayList) iSysBaseAPI.queryDictItemsByCode(onlCgformField.getDictField());
                    }
                    stringProperty = new StringProperty(dbFieldName, dbFieldTxt, fieldShowType, onlCgformField.getDbLength(), arrayList4);
                    if (k.isNumber(onlCgformField.getDbType())) {
                        stringProperty.setType(org.jeecg.modules.online.config.b.b.h);
                    }
                } else if (k.isNumber(onlCgformField.getDbType())) {
                    NumberProperty numberProperty = new NumberProperty(dbFieldName, dbFieldTxt, org.jeecg.modules.online.config.b.b.h);
                    if (CgformValidPatternEnum.INTEGER.getType().equals(onlCgformField.getFieldValidType())) {
                        numberProperty.setPattern(CgformValidPatternEnum.INTEGER.getPattern());
                    }
                    stringProperty = numberProperty;
                } else if (POPUP.equals(fieldShowType)) {
                    PopupProperty popupProperty = new PopupProperty(dbFieldName, dbFieldTxt, onlCgformField.getDictTable(), onlCgformField.getDictText(), onlCgformField.getDictField());
                    String dictText = onlCgformField.getDictText();
                    if (dictText != null && !dictText.equals("")) {
                        for (String str : dictText.split(DOT_STRING)) {
                            if (!a(str, list)) {
                                HiddenProperty hiddenProperty = new HiddenProperty(str, str);
                                hiddenProperty.setOrder(onlCgformField.getOrderNum());
                                arrayList2.add(hiddenProperty);
                            }
                        }
                    }
                    String fieldExtendJson = onlCgformField.getFieldExtendJson();
                    if (fieldExtendJson != null && !fieldExtendJson.equals("")) {
                        JSONObject parseObject = JSONObject.parseObject(fieldExtendJson);
                        if (parseObject.containsKey(ac)) {
                            popupProperty.setPopupMulti(parseObject.getBoolean(ac));
                        }
                    }
                    stringProperty = popupProperty;
                } else if ("sel_search".equals(fieldShowType)) {
                    stringProperty = new DictProperty(dbFieldName, dbFieldTxt, onlCgformField.getDictTable(), onlCgformField.getDictField(), onlCgformField.getDictText());
                } else if (sP.equals(fieldShowType)) {
                    LinkDownProperty linkDownProperty = new LinkDownProperty(dbFieldName, dbFieldTxt, onlCgformField.getDictTable());
                    a((LinkDownProperty) linkDownProperty, list, (List<String>) arrayList3);
                    stringProperty = linkDownProperty;
                } else if (sN.equals(fieldShowType)) {
                    String[] split2 = onlCgformField.getDictText().split(DOT_STRING);
                    TreeSelectProperty treeSelectProperty = new TreeSelectProperty(dbFieldName, dbFieldTxt, onlCgformField.getDictTable() + DOT_STRING + split2[2] + DOT_STRING + split2[0], split2[1], onlCgformField.getDictField());
                    if (split2.length > 3) {
                        treeSelectProperty.setHasChildField(split2[3]);
                    }
                    stringProperty = treeSelectProperty;
                } else if (CAT_TREE.equals(fieldShowType)) {
                    String dictText2 = onlCgformField.getDictText();
                    String dictField = onlCgformField.getDictField();
                    String str2 = "0";
                    if (oConvertUtils.isNotEmpty(dictField) && !"0".equals(dictField)) {
                        str2 = onlCgformHeadMapper.queryCategoryIdByCode(dictField);
                    }
                    if (oConvertUtils.isEmpty(dictText2)) {
                        stringProperty = new TreeSelectProperty(dbFieldName, dbFieldTxt, str2);
                    } else {
                        stringProperty = new TreeSelectProperty(dbFieldName, dbFieldTxt, str2, dictText2);
                        arrayList2.add(new HiddenProperty(dictText2, dictText2));
                    }
                } else if (dVar != null && dbFieldName.equals(dVar.getFieldName())) {
                    TreeSelectProperty treeSelectProperty2 = new TreeSelectProperty(dbFieldName, dbFieldTxt, dVar.getTableName() + DOT_STRING + dVar.getTextField() + DOT_STRING + dVar.getCodeField(), dVar.getPidField(), dVar.getPidValue());
                    treeSelectProperty2.setHasChildField(dVar.getHsaChildField());
                    treeSelectProperty2.setPidComponent(1);
                    stringProperty = treeSelectProperty2;
                } else {
                    StringProperty stringProperty2 = new StringProperty(dbFieldName, dbFieldTxt, fieldShowType, onlCgformField.getDbLength());
                    if (oConvertUtils.isNotEmpty(onlCgformField.getFieldValidType())) {
                        CgformValidPatternEnum patternInfoByType = CgformValidPatternEnum.getPatternInfoByType(onlCgformField.getFieldValidType());
                        if (patternInfoByType != null) {
                            if (CgformValidPatternEnum.NOTNULL == patternInfoByType) {
                                arrayList.add(dbFieldName);
                            } else {
                                stringProperty2.setPattern(patternInfoByType.getPattern());
                                stringProperty2.setErrorInfo(patternInfoByType.getMsg());
                            }
                        } else {
                            stringProperty2.setPattern(onlCgformField.getFieldValidType());
                            stringProperty2.setErrorInfo("输入的值不合法");
                        }
                    }
                    stringProperty = stringProperty2;
                }
                if (onlCgformField.getIsReadOnly().intValue() == 1 || (list2 != null && list2.indexOf(dbFieldName) >= 0)) {
                    stringProperty.setDisabled(true);
                }
                stringProperty.setOrder(onlCgformField.getOrderNum());
                stringProperty.setDefVal(onlCgformField.getFieldDefaultValue());
                stringProperty.setFieldExtendJson(onlCgformField.getFieldExtendJson());
                stringProperty.setDbPointLength(onlCgformField.getDbPointLength());
                arrayList2.add(stringProperty);
            }
        }
        if (arrayList.size() > 0) {
            jsonSchema = JsonschemaUtil.getJsonSchema(new JsonSchemaDescrip(arrayList), arrayList2);
        } else {
            jsonSchema = JsonschemaUtil.getJsonSchema(new JsonSchemaDescrip(), arrayList2);
        }
        return jsonSchema;
    }

    public static JSONObject b(String str, List<OnlCgformField> list) {
        CommonProperty stringProperty;
        new JSONObject();
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ISysBaseAPI iSysBaseAPI = (ISysBaseAPI) SpringContextUtils.getBean(ISysBaseAPI.class);
        for (OnlCgformField onlCgformField : list) {
            String dbFieldName = onlCgformField.getDbFieldName();
            if (!"id".equals(dbFieldName)) {
                String dbFieldTxt = onlCgformField.getDbFieldTxt();
                if ("1".equals(onlCgformField.getFieldMustInput())) {
                    arrayList.add(dbFieldName);
                }
                String fieldShowType = onlCgformField.getFieldShowType();
                String dictField = onlCgformField.getDictField();
                if (k.isNumber(onlCgformField.getDbType())) {
                    stringProperty = new NumberProperty(dbFieldName, dbFieldTxt, org.jeecg.modules.online.config.b.b.h);
                } else if (c(fieldShowType)) {
                    stringProperty = new StringProperty(dbFieldName, dbFieldTxt, fieldShowType, onlCgformField.getDbLength(), iSysBaseAPI.queryDictItemsByCode(dictField));
                } else {
                    stringProperty = new StringProperty(dbFieldName, dbFieldTxt, fieldShowType, onlCgformField.getDbLength());
                }
                CommonProperty stringProperty2 = stringProperty;
                stringProperty2.setOrder(onlCgformField.getOrderNum());
                arrayList2.add(stringProperty2);
            }
        }
        return JsonschemaUtil.getSubJsonSchema(str, arrayList, arrayList2);
    }

    public static Set<String> a(List<OnlCgformField> list) {
        String dictText;
        HashSet hashSet = new HashSet();
        for (OnlCgformField onlCgformField : list) {
            if (POPUP.equals(onlCgformField.getFieldShowType()) && (dictText = onlCgformField.getDictText()) != null && !dictText.equals("")) {
                hashSet.addAll((Collection) Arrays.stream(dictText.split(DOT_STRING)).collect(Collectors.toSet()));
            }
            if (CAT_TREE.equals(onlCgformField.getFieldShowType())) {
                String dictText2 = onlCgformField.getDictText();
                if (oConvertUtils.isNotEmpty(dictText2)) {
                    hashSet.add(dictText2);
                }
            }
        }
        for (OnlCgformField onlCgformField2 : list) {
            String dbFieldName = onlCgformField2.getDbFieldName();
            if (onlCgformField2.getIsShowForm().intValue() == 1 && hashSet.contains(dbFieldName)) {
                hashSet.remove(dbFieldName);
            }
        }
        return hashSet;
    }

    public static Map<String, Object> a(String tableName, List<OnlCgformField> onlCgformFields, JSONObject formData) {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        String str2 = "";
        try {
            str2 = org.jeecg.modules.online.config.b.d.getDatabaseType();
        } catch (SQLException e2) {
            e2.printStackTrace();
        } catch (DBException e3) {
            e3.printStackTrace();
        }
        HashMap<String, Object> hashMap = new HashMap<>();
        boolean hasId = false;
        String idValue = null;
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        // 来自流程服务中的调用时，不存在请求上下文
        if (loginUser == null) {
            String token = TokenUtils.getTokenByRequest();
            if (StringUtils.isNotEmpty(token)) {
                String username = JwtUtil.getUsername(token);
                loginUser = SpringContextUtils.getBean(CommonAPI.class).getUserByName(username);
            }
        }
        if (loginUser == null) {
            throw new JeecgBootException("online保存表单数据异常:系统未找到当前登陆用户信息");
        }
        Set<String> a2 = a(onlCgformFields);
        for (OnlCgformField onlCgformField : onlCgformFields) {
            String dbFieldName = onlCgformField.getDbFieldName();
            if (null == dbFieldName) {
                ay.info("--------online保存表单数据遇见空名称的字段------->>" + onlCgformField.getId());
            } else if ("id".equals(dbFieldName.toLowerCase())) {
                hasId = true;
                idValue = formData.getString(dbFieldName);
            } else {
                a(onlCgformField, loginUser, formData, su, st, sx);
                if (sE.equals(dbFieldName.toLowerCase())) {
                    stringBuffer.append(DOT_STRING + dbFieldName);
                    stringBuffer2.append(",'1'");
                } else if (a2.contains(dbFieldName)) {
                    stringBuffer.append(DOT_STRING + dbFieldName);
                    stringBuffer2.append(DOT_STRING + k.a(str2, onlCgformField, formData, hashMap));
                } else if (onlCgformField.getIsShowForm().intValue() == 1 || !oConvertUtils.isEmpty(onlCgformField.getMainField()) || !oConvertUtils.isEmpty(onlCgformField.getDbDefaultVal())) {
                    if (formData.get(dbFieldName) == null) {
                        if (!oConvertUtils.isEmpty(onlCgformField.getDbDefaultVal())) {
                            formData.put(dbFieldName, onlCgformField.getDbDefaultVal());
                        }
                    }
                    if ("".equals(formData.get(dbFieldName))) {
                        String dbType = onlCgformField.getDbType();
                        if (!k.isNumber(dbType) && !k.b(dbType)) {
                        }
                    }
                    stringBuffer.append(DOT_STRING + dbFieldName);
                    stringBuffer2.append(DOT_STRING + k.a(str2, onlCgformField, formData, hashMap));
                }
            }
        }
        if (hasId) {
            if (oConvertUtils.isEmpty(idValue)) {
                idValue = a();
            }
        } else {
            idValue = a();
        }
        String str4 = "insert into " + f(tableName) + "(id" + stringBuffer.toString() + ") values(" + sz + idValue + sz + stringBuffer2.toString() + ")";
        hashMap.put("execute_sql_string", str4);
        ay.info("--动态表单保存sql-->" + str4);
        // 将新的id带回去
        formData.put("id", idValue);
        return hashMap;
    }

    public static Map<String, Object> b(String tableName, List<OnlCgformField> onlCgformFields, JSONObject formData) {
        StringBuffer stringBuffer = new StringBuffer();
        HashMap<String, Object> hashMap = new HashMap<>();
        String str2 = "";
        try {
            str2 = org.jeecg.modules.online.config.b.d.getDatabaseType();
        } catch (SQLException e2) {
            e2.printStackTrace();
        } catch (DBException e3) {
            e3.printStackTrace();
        }
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        // 来自流程服务中的调用时，不存在请求上下文
        if (loginUser == null) {
            String token = TokenUtils.getTokenByRequest();
            if (StringUtils.isNotEmpty(token)) {
                String username = JwtUtil.getUsername(token);
                loginUser = SpringContextUtils.getBean(CommonAPI.class).getUserByName(username);
            }
        }
        if (loginUser == null) {
            throw new JeecgBootException("online修改表单数据异常:系统未找到当前登陆用户信息");
        }
        Set<String> a2 = a(onlCgformFields);
        for (OnlCgformField onlCgformField : onlCgformFields) {
            String dbFieldName = onlCgformField.getDbFieldName();
            if (null == dbFieldName) {
                ay.info("--------online修改表单数据遇见空名称的字段------->>" + onlCgformField.getId());
            } else {
                // 直接处理空字符串到null
                if (!org.springframework.util.StringUtils.hasText(formData.getString(dbFieldName))) {
                    formData.put(dbFieldName, null);
                }
                // update_by update_time sys_org_code跳过
                if (st.equalsIgnoreCase(dbFieldName) || su.equalsIgnoreCase(dbFieldName) || sx.equalsIgnoreCase(dbFieldName)) {
                    // a(onlCgformField, loginUser, jSONObject, su, st, sx);
                    String orgCode = formData.getString(sx);
                    if (!org.springframework.util.StringUtils.hasText(orgCode)) {
                        a(onlCgformField, loginUser, formData, sx);
                    } else {
                        continue;
                    }
                }
                a(onlCgformField, loginUser, formData, sw, sv);
                if (a2.contains(dbFieldName) && formData.get(dbFieldName) != null && !"".equals(formData.getString(dbFieldName))) {
                    stringBuffer.append(dbFieldName + EQ + k.a(str2, onlCgformField, formData, hashMap) + DOT_STRING);
                } else if (onlCgformField.getIsShowForm().intValue() == 1 && !"id".equals(dbFieldName)) {
                    if ("".equals(formData.get(dbFieldName))) {
                        String dbType = onlCgformField.getDbType();
                        if (!k.isNumber(dbType) && !k.b(dbType)) {
                        }
                    }
                    if (!oConvertUtils.isNotEmpty(onlCgformField.getMainTable()) || !oConvertUtils.isNotEmpty(onlCgformField.getMainField())) {
                        stringBuffer.append(dbFieldName + EQ + k.a(str2, onlCgformField, formData, hashMap) + DOT_STRING);
                    } else {
                        // 无论是不是外键，都更新字段
                        stringBuffer.append(dbFieldName + EQ + k.a(str2, onlCgformField, formData, hashMap) + DOT_STRING);
                    }
                } else {
                    // 无论是不是表单显示字段，都更新
                    stringBuffer.append(dbFieldName + EQ + k.a(str2, onlCgformField, formData, hashMap) + DOT_STRING);
                }
            }
        }
        String stringBuffer2 = stringBuffer.toString();
        if (stringBuffer2.endsWith(DOT_STRING)) {
            stringBuffer2 = stringBuffer2.substring(0, stringBuffer2.length() - 1);
        }
        String str3 = "update " + f(tableName) + " set " + stringBuffer2 + WHERE + "id" + EQ + sz + formData.getString("id") + sz;
        ay.info("--动态表单编辑sql-->" + str3);
        hashMap.put("execute_sql_string", str3);
        return hashMap;
    }

    public static String a(String str, List<OnlCgformField> list, String str2) {
        return a(str, list, "id", str2);
    }

    public static String a(String str, List<OnlCgformField> list, String str2, String str3) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(SELECT);
        StringBuffer orderStr=new StringBuffer();
        int size = list.size();
        boolean z2 = false;
        for (int i2 = 0; i2 < size; i2++) {
            String dbFieldName = list.get(i2).getDbFieldName();
            if ("id".equals(dbFieldName)) {
                z2 = true;
            }
            stringBuffer.append(dbFieldName);
            if (size > i2 + 1) {
                stringBuffer.append(DOT_STRING);
            }
            // 处理子表排序
            JSONObject parseObject;
            String fieldExtendJson=list.get(i2).getFieldExtendJson();
            if(StringUtils.isNotEmpty(fieldExtendJson)){
                if(fieldExtendJson.indexOf(b.orderRule) > 0 && (parseObject = JSON.parseObject(fieldExtendJson)) != null && parseObject.get(b.orderRule) != null){
                   String orderTyep= parseObject.get("orderRule").toString();
                   if(orderStr.indexOf(ORDERBY)==-1) {
                       orderStr.append(ORDERBY);
                   }
                    orderStr.append(dbFieldName);
                    orderStr.append(" ");
                    orderStr.append(orderTyep);
                }
            }
        }
        if (!z2) {
            stringBuffer.append(",id");
        }
        // 原版本时依赖主键查询，如果想要多对多（一个字段存储了多个附表id），就必须换成模糊查询
        // stringBuffer.append(sb + f(str) + sf + sc + str2 + sk + sz + str3 + sz);
        // stringBuffer.append(FROM + f(str) + WHERE_1_1 + AND + str2 + " like " + sz + "%" + str3 + "%" + sz);
        // 去除 WHERE 1=1
        if (StringUtils.isNotBlank(str3)) {
            stringBuffer.append(FROM + f(str) + WHERE + str2 + " like " + sz + "%" + str3 + "%" + sz);
        } else {
            stringBuffer.append(FROM + f(str));
        }
        stringBuffer.append(" ");
        stringBuffer.append(orderStr);


        return stringBuffer.toString();
    }

    public static void a(OnlCgformField onlCgformField, LoginUser loginUser, JSONObject jSONObject, String... strArr) {
        String dbFieldName = onlCgformField.getDbFieldName();
        boolean z2 = false;
        for (String str : strArr) {
            if (dbFieldName.toUpperCase().equals(str)) {
                if (onlCgformField.getIsShowForm().intValue() == 1) {
                    if (jSONObject.get(dbFieldName) == null) {
                        z2 = true;
                    }
                } else {
                    onlCgformField.setIsShowForm(1);
                    z2 = true;
                }
                if (z2) {
                    boolean z3 = true;
                    switch (str.hashCode()) {
                        case -909973894:
                            if (str.equals(su)) {
                                z3 = false;
                                jSONObject.put(dbFieldName, loginUser.getUsername());
                                break;
                            }
                            break;
                        case -99751974:
                            if (str.equals(sx)) {
                                z3 = true;
                                jSONObject.put(dbFieldName, loginUser.getOrgCode());
                                break;
                            }
                            break;
                        case 837427085:
                            if (str.equals(sw)) {
                                z3 = true;
                                jSONObject.put(dbFieldName, loginUser.getUsername());
                                break;
                            }
                            break;
                        case 1609067651:
                            if (str.equals(sv)) {
                                z3 = true;
                                onlCgformField.setFieldShowType("datetime");
                                jSONObject.put(dbFieldName, DateUtils.formatDateTime());
                                break;
                            }
                            break;
                        case 1688939568:
                            if (str.equals(st)) {
                                z3 = true;
                                onlCgformField.setFieldShowType("datetime");
                                jSONObject.put(dbFieldName, DateUtils.formatDateTime());
                                break;
                            }
                            break;
                    }
                }
                return;
            }
        }
    }


    public static boolean a(Object obj, Object obj2) {
        if (oConvertUtils.isEmpty(obj) && oConvertUtils.isEmpty(obj2)) {
            return true;
        }
        if (oConvertUtils.isNotEmpty(obj) && obj.equals(obj2)) {
            return true;
        }
        return false;
    }

    public static boolean a(OnlCgformField onlCgformField, OnlCgformField onlCgformField2) {
        if (!a((Object) onlCgformField.getDbFieldName(), (Object) onlCgformField2.getDbFieldName()) || !a((Object) onlCgformField.getDbFieldTxt(), (Object) onlCgformField2.getDbFieldTxt()) || !a(onlCgformField.getDbLength(), onlCgformField2.getDbLength()) || !a(onlCgformField.getDbPointLength(), onlCgformField2.getDbPointLength()) || !a((Object) onlCgformField.getDbType(), (Object) onlCgformField2.getDbType()) || !a(onlCgformField.getDbIsNull(), onlCgformField2.getDbIsNull()) || !a(onlCgformField.getDbIsKey(), onlCgformField2.getDbIsKey()) || !a((Object) onlCgformField.getDbDefaultVal(), (Object) onlCgformField2.getDbDefaultVal())) {
            return true;
        }
        return false;
    }

    public static boolean a(OnlCgformIndex onlCgformIndex, OnlCgformIndex onlCgformIndex2) {
        if (!a((Object) onlCgformIndex.getIndexName(), (Object) onlCgformIndex2.getIndexName()) || !a((Object) onlCgformIndex.getIndexField(), (Object) onlCgformIndex2.getIndexField()) || !a((Object) onlCgformIndex.getIndexType(), (Object) onlCgformIndex2.getIndexType())) {
            return true;
        }
        return false;
    }

    public static boolean a(OnlCgformHead onlCgformHead, OnlCgformHead onlCgformHead2) {
        if (!a((Object) onlCgformHead.getTableName(), (Object) onlCgformHead2.getTableName()) || !a((Object) onlCgformHead.getTableTxt(), (Object) onlCgformHead2.getTableTxt())) {
            return true;
        }
        return false;
    }

    public static String a(String str, List<OnlCgformField> list, Map<String, Object> map) {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        for (OnlCgformField onlCgformField : list) {
            String dbFieldName = onlCgformField.getDbFieldName();
            String dbType = onlCgformField.getDbType();
            if (onlCgformField.getIsShowList().intValue() == 1) {
                stringBuffer2.append(DOT_STRING + dbFieldName);
            }
            if (oConvertUtils.isNotEmpty(onlCgformField.getMainField())) {
                String singleQueryConditionSql = QueryGenerator.getSingleQueryConditionSql(dbFieldName, "", map.get(dbFieldName), !k.isNumber(dbType));
                if (!"".equals(singleQueryConditionSql)) {
                    stringBuffer.append(AND + singleQueryConditionSql);
                }
            }
            if (onlCgformField.getIsQuery().intValue() == 1) {
                if (single.equals(onlCgformField.getQueryMode())) {
                    if (map.get(dbFieldName) != null) {
                        String singleQueryConditionSql2 = QueryGenerator.getSingleQueryConditionSql(dbFieldName, "", map.get(dbFieldName), !k.isNumber(dbType));
                        if (!"".equals(singleQueryConditionSql2)) {
                            stringBuffer.append(AND + singleQueryConditionSql2);
                        }
                    }
                } else {
                    Object obj = map.get(dbFieldName + "_begin");
                    if (obj != null) {
                        stringBuffer.append(AND + dbFieldName + GE);
                        if (k.isNumber(dbType)) {
                            stringBuffer.append(obj.toString());
                        } else {
                            stringBuffer.append(sz + obj.toString() + sz);
                        }
                    }
                    Object obj2 = map.get(dbFieldName + "_end");
                    if (obj2 != null) {
                        stringBuffer.append(AND + dbFieldName + LE);
                        if (k.isNumber(dbType)) {
                            stringBuffer.append(obj2.toString());
                        } else {
                            stringBuffer.append(sz + obj2.toString() + sz);
                        }
                    }
                }
            }
        }
        // return "SELECT id" + stringBuffer2.toString() + FROM + f(str) + WHERE_1_1 + stringBuffer.toString();

        // 去除 WHERE 1=1
        if (StringUtils.isNotBlank(stringBuffer.toString())) {
            return "SELECT id" + stringBuffer2.toString() + FROM + f(str) + WHERE + stringBuffer.toString();
        } else {
            return "SELECT id" + stringBuffer2.toString() + FROM + f(str);
        }
    }

    public static List<ExcelExportEntity> a(List<OnlCgformField> list, String str) {
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < list.size(); i2++) {
            if ((null == str || !str.equals(list.get(i2).getDbFieldName())) && list.get(i2).getIsShowList().intValue() == 1) {
                ExcelExportEntity excelExportEntity = new ExcelExportEntity(list.get(i2).getDbFieldTxt(), list.get(i2).getDbFieldName());
                int intValue = list.get(i2).getDbLength().intValue() == 0 ? 12 : list.get(i2).getDbLength().intValue() > 30 ? 30 : list.get(i2).getDbLength().intValue();
                if (list.get(i2).getFieldShowType().equals(i.DATE)) {
                    excelExportEntity.setFormat("yyyy-MM-dd");
                } else if (list.get(i2).getFieldShowType().equals("datetime")) {
                    excelExportEntity.setFormat("yyyy-MM-dd HH:mm:ss");
                }
                if (intValue < 10) {
                    intValue = 10;
                }
                excelExportEntity.setWidth(intValue);
                arrayList.add(excelExportEntity);
            }
        }
        return arrayList;
    }

    public static boolean a(OnlCgformEnhanceJava onlCgformEnhanceJava) {
        Class<?> cls;
        String cgJavaType = onlCgformEnhanceJava.getCgJavaType();
        String cgJavaValue = onlCgformEnhanceJava.getCgJavaValue();
        if (oConvertUtils.isNotEmpty(cgJavaValue)) {
            try {
                if ("class".equals(cgJavaType) && ((cls = Class.forName(cgJavaValue)) == null || cls.newInstance() == null)) {
                    return false;
                }
                if ("spring".equals(cgJavaType)) {
                    if (SpringContextUtils.getBean(cgJavaValue) == null) {
                        return false;
                    }
                    return true;
                }
                return true;
            } catch (Exception e2) {
                ay.error(e2.getMessage(), e2);
                return false;
            }
        }
        return true;
    }

    public static void b(List<String> list) {
        Collections.sort(list, new Comparator<String>() { // from class: org.jeecg.modules.online.cgform.d.b.1
            @Override // java.util.Comparator
            /* renamed from: a */
            public int compare(String str, String str2) {
                if (str == null || str2 == null) {
                    return -1;
                }
                if (str.compareTo(str2) > 0) {
                    return 1;
                }
                if (str.compareTo(str2) < 0) {
                    return -1;
                }
                if (str.compareTo(str2) == 0) {
                    return 0;
                }
                return 0;
            }
        });
    }

    public static void c(List<String> list) {
        Collections.sort(list, new Comparator<String>() { // from class: org.jeecg.modules.online.cgform.d.b.2
            @Override // java.util.Comparator
            /* renamed from: a */
            public int compare(String str, String str2) {
                if (str == null || str2 == null) {
                    return -1;
                }
                if (str.length() > str2.length()) {
                    return 1;
                }
                if (str.length() < str2.length()) {
                    return -1;
                }
                if (str.compareTo(str2) > 0) {
                    return 1;
                }
                if (str.compareTo(str2) < 0) {
                    return -1;
                }
                if (str.compareTo(str2) == 0) {
                    return 0;
                }
                return 0;
            }
        });
    }

    private static String a(String str, boolean z2, QueryRuleEnum queryRuleEnum) {
        if (queryRuleEnum == QueryRuleEnum.IN) {
            return a(str, z2);
        }
        if (z2) {
            return sz + QueryGenerator.converRuleValue(str) + sz;
        }
        return QueryGenerator.converRuleValue(str);
    }

    private static String a(String str, boolean z2) {
        if (str == null || str.length() == 0) {
            return "()";
        }
        String[] split = QueryGenerator.converRuleValue(str).split(DOT_STRING);
        ArrayList arrayList = new ArrayList();
        for (String str2 : split) {
            if (str2 != null && str2.length() != 0) {
                if (z2) {
                    arrayList.add(sz + str2 + sz);
                } else {
                    arrayList.add(str2);
                }
            }
        }
        return "(" + StringUtils.join(arrayList, DOT_STRING) + ")";
    }

    public static String a(String str, JSONObject jSONObject) {
        if (jSONObject == null) {
            return str;
        }
        String replace = str.replace("#{UUID}", UUIDGenerator.generate());
        for (String str2 : QueryGenerator.getSqlRuleParams(replace)) {
            if (jSONObject.get(str2.toUpperCase()) == null && jSONObject.get(str2.toLowerCase()) == null) {
                replace = replace.replace("#{" + str2 + "}", QueryGenerator.converRuleValue(str2));
            } else {
                String str3 = null;
                if (jSONObject.containsKey(str2.toLowerCase())) {
                    str3 = jSONObject.getString(str2.toLowerCase());
                } else if (jSONObject.containsKey(str2.toUpperCase())) {
                    str3 = jSONObject.getString(str2.toUpperCase());
                }
                replace = replace.replace("#{" + str2 + "}", str3);
            }
        }
        return replace;
    }

    public static String c(String str, List<OnlCgformButton> list) {
        String[] split;
        String d2 = d(str, list);
        for (String str2 : az.split(DOT_STRING)) {
            if ("beforeAdd,afterAdd,mounted,created".indexOf(str2) >= 0) {
                Matcher matcher = Pattern.compile("(" + str2 + "\\s*\\(\\)\\s*\\{)").matcher(d2);
                if (matcher.find()) {
                    d2 = d2.replace(matcher.group(0), str2 + "(that){const getAction=this._getAction,postAction=this._postAction,deleteAction=this._deleteAction;");
                }
            } else {
                Matcher matcher2 = Pattern.compile("(" + str2 + "\\s*\\(row\\)\\s*\\{)").matcher(d2);
                if (matcher2.find()) {
                    d2 = d2.replace(matcher2.group(0), str2 + "(that,row){const getAction=this._getAction,postAction=this._postAction,deleteAction=this._deleteAction;");
                } else {
                    Matcher matcher3 = Pattern.compile("(" + str2 + "\\s*\\(\\)\\s*\\{)").matcher(d2);
                    if (matcher3.find()) {
                        d2 = d2.replace(matcher3.group(0), str2 + "(that){const getAction=this._getAction,postAction=this._postAction,deleteAction=this._deleteAction;");
                    }
                }
            }
        }
        return d(d2);
    }

    public static void a(OnlCgformEnhanceJs onlCgformEnhanceJs, String str, List<OnlCgformField> list) {
        if (onlCgformEnhanceJs == null || oConvertUtils.isEmpty(onlCgformEnhanceJs.getCgJs())) {
            return;
        }
        String cgJs = onlCgformEnhanceJs.getCgJs();
        Matcher matcher = Pattern.compile("(" + str + "_" + CgformConstant.ONLINE_JS_CHANGE_FUNCTION_NAME + "\\s*\\(\\)\\s*\\{)").matcher(cgJs);
        if (matcher.find()) {
            cgJs = cgJs.replace(matcher.group(0), str + "_" + CgformConstant.ONLINE_JS_CHANGE_FUNCTION_NAME + "(){const getAction=this._getAction,postAction=this._postAction,deleteAction=this._deleteAction;");
            for (OnlCgformField onlCgformField : list) {
                Matcher matcher2 = Pattern.compile("(" + onlCgformField.getDbFieldName() + "\\s*\\(\\))").matcher(cgJs);
                if (matcher2.find()) {
                    cgJs = cgJs.replace(matcher2.group(0), onlCgformField.getDbFieldName() + "(that,event)");
                }
            }
        }
        onlCgformEnhanceJs.setCgJs(cgJs);
    }

    public static void a(OnlCgformEnhanceJs onlCgformEnhanceJs, String str, List<OnlCgformField> list, boolean z2) {
        if (onlCgformEnhanceJs == null || oConvertUtils.isEmpty(onlCgformEnhanceJs.getCgJs())) {
            return;
        }
        String cgJs = onlCgformEnhanceJs.getCgJs();
        Matcher matcher = Pattern.compile("([^_]" + CgformConstant.ONLINE_JS_CHANGE_FUNCTION_NAME + "\\s*\\(\\)\\s*\\{)").matcher(cgJs);
        if (matcher.find()) {
            cgJs = cgJs.replace(matcher.group(0), CgformConstant.ONLINE_JS_CHANGE_FUNCTION_NAME + "(){const getAction=this._getAction,postAction=this._postAction,deleteAction=this._deleteAction;");
            for (OnlCgformField onlCgformField : list) {
                Matcher matcher2 = Pattern.compile("(" + onlCgformField.getDbFieldName() + "\\s*\\(\\))").matcher(cgJs);
                if (matcher2.find()) {
                    cgJs = cgJs.replace(matcher2.group(0), onlCgformField.getDbFieldName() + "(that,event)");
                }
            }
        }
        onlCgformEnhanceJs.setCgJs(cgJs);
        a(onlCgformEnhanceJs);
        a(onlCgformEnhanceJs, str, list);
    }

    public static void a(OnlCgformEnhanceJs onlCgformEnhanceJs) {
        String cgJs = onlCgformEnhanceJs.getCgJs();
        Matcher matcher = Pattern.compile("(show\\s*\\(\\)\\s*\\{)").matcher(cgJs);
        if (matcher.find()) {
            cgJs = cgJs.replace(matcher.group(0), "show(that){const getAction=this._getAction,postAction=this._postAction,deleteAction=this._deleteAction;");
        }
        onlCgformEnhanceJs.setCgJs(cgJs);
    }

    public static String d(String str) {
        ay.info("最终的增强JS", str);
        return "class OnlineEnhanceJs{constructor(getAction,postAction,deleteAction){this._getAction=getAction;this._postAction=postAction;this._deleteAction=deleteAction;}" + str + "}";
    }

    public static String d(String str, List<OnlCgformButton> list) {
        if (list != null) {
            for (OnlCgformButton onlCgformButton : list) {
                String buttonCode = onlCgformButton.getButtonCode();
                if ("link".equals(onlCgformButton.getButtonStyle())) {
                    Matcher matcher = Pattern.compile("(" + buttonCode + "\\s*\\(row\\)\\s*\\{)").matcher(str);
                    if (matcher.find()) {
                        str = str.replace(matcher.group(0), buttonCode + "(that,row){const getAction=this._getAction,postAction=this._postAction,deleteAction=this._deleteAction;");
                    } else {
                        Matcher matcher2 = Pattern.compile("(" + buttonCode + "\\s*\\(\\)\\s*\\{)").matcher(str);
                        if (matcher2.find()) {
                            str = str.replace(matcher2.group(0), buttonCode + "(that){const getAction=this._getAction,postAction=this._postAction,deleteAction=this._deleteAction;");
                        }
                    }
                } else if ("button".equals(onlCgformButton.getButtonStyle()) || form.equals(onlCgformButton.getButtonStyle())) {
                    Matcher matcher3 = Pattern.compile("(" + buttonCode + "\\s*\\(\\)\\s*\\{)").matcher(str);
                    if (matcher3.find()) {
                        str = str.replace(matcher3.group(0), buttonCode + "(that){const getAction=this._getAction,postAction=this._postAction,deleteAction=this._deleteAction;");
                    }
                }
            }
        }
        return str;
    }

    public static JSONArray a(List<OnlCgformField> list, List<String> list2) {
        String[] split;
        JSONArray jSONArray = new JSONArray();
        ISysBaseAPI iSysBaseAPI = (ISysBaseAPI) SpringContextUtils.getBean(ISysBaseAPI.class);
        for (OnlCgformField onlCgformField : list) {
            String dbFieldName = onlCgformField.getDbFieldName();
            if (!"id".equals(dbFieldName)) {
                JSONObject jSONObject = new JSONObject();
                if (list2.indexOf(dbFieldName) >= 0) {
                    jSONObject.put("disabled", true);
                }
                if (onlCgformField.getIsReadOnly() != null && 1 == onlCgformField.getIsReadOnly().intValue()) {
                    jSONObject.put("disabled", true);
                }
                jSONObject.put("title", onlCgformField.getDbFieldTxt());
                jSONObject.put("key", dbFieldName);
                String c2 = c(onlCgformField);
                jSONObject.put("type", c2);
                if (onlCgformField.getFieldLength() == null) {
                    jSONObject.put("width", "186px");
                } else if ("sel_depart".equals(c2) || "sel_user".equals(c2)) {
                    jSONObject.put("width", "");
                } else {
                    jSONObject.put("width", onlCgformField.getFieldLength() + "px");
                }
                if (c2.equals("file") || c2.equals("image")) {
                    jSONObject.put("responseName", "message");
                    jSONObject.put("token", true);
                }
                if (c2.equals(sI)) {
                    jSONObject.put("type", "checkbox");
                    JSONArray jSONArray2 = new JSONArray();
                    if (oConvertUtils.isEmpty(onlCgformField.getFieldExtendJson())) {
                        jSONArray2.add("Y");
                        jSONArray2.add("N");
                    } else {
                        jSONArray2 = JSONArray.parseArray(onlCgformField.getFieldExtendJson());
                    }
                    jSONObject.put("customValue", jSONArray2);
                }
                if (c2.equals(POPUP)) {
                    jSONObject.put("popupCode", onlCgformField.getDictTable());
                    jSONObject.put("orgFields", onlCgformField.getDictField());
                    jSONObject.put("destFields", onlCgformField.getDictText());
                    String dictText = onlCgformField.getDictText();
                    if (dictText != null && !dictText.equals("")) {
                        ArrayList arrayList = new ArrayList();
                        for (String str : dictText.split(DOT_STRING)) {
                            if (!a(str, list)) {
                                arrayList.add(str);
                                JSONObject jSONObject2 = new JSONObject();
                                jSONObject2.put("title", str);
                                jSONObject2.put("key", str);
                                jSONObject2.put("type", "hidden");
                                jSONArray.add(jSONObject2);
                            }
                        }
                    }
                }
                jSONObject.put("defaultValue", onlCgformField.getDbDefaultVal());
                jSONObject.put("fieldDefaultValue", onlCgformField.getFieldDefaultValue());
                jSONObject.put("scopedSlots", onlCgformField.getScopedSlots());
                jSONObject.put("scopedSlotsRenderCode", onlCgformField.getScopedSlotsRenderCode());
                jSONObject.put("placeholder", "请输入" + onlCgformField.getDbFieldTxt());
                jSONObject.put("validateRules", b(onlCgformField));
                if (aj.equals(onlCgformField.getFieldShowType()) || "radio".equals(onlCgformField.getFieldShowType()) || "checkbox_meta".equals(onlCgformField.getFieldShowType()) || "list_multi".equals(onlCgformField.getFieldShowType()) || "sel_search".equals(onlCgformField.getFieldShowType())) {
                    Collection arrayList2 = new ArrayList();
                    if (oConvertUtils.isNotEmpty(onlCgformField.getDictTable())) {
                        arrayList2 = iSysBaseAPI.queryTableDictItemsByCode(onlCgformField.getDictTable(), onlCgformField.getDictText(), onlCgformField.getDictField());
                    } else if (oConvertUtils.isNotEmpty(onlCgformField.getDictField())) {
                        arrayList2 = iSysBaseAPI.queryDictItemsByCode(onlCgformField.getDictField());
                    }
                    jSONObject.put("options", arrayList2);
                    if ("list_multi".equals(onlCgformField.getFieldShowType())) {
                        jSONObject.put("width", "230px");
                    }
                }
                jSONObject.put("fieldExtendJson", onlCgformField.getFieldExtendJson());
                jSONArray.add(jSONObject);
            }
        }
        return jSONArray;
    }

    private static JSONArray b(OnlCgformField onlCgformField) {
        JSONArray jSONArray = new JSONArray();
        if (onlCgformField.getDbIsNull().intValue() == 0 || "1".equals(onlCgformField.getFieldMustInput())) {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("required", true);
            jSONObject.put("message", onlCgformField.getDbFieldTxt() + "不能为空!");
            jSONArray.add(jSONObject);
        }
        if (oConvertUtils.isNotEmpty(onlCgformField.getFieldValidType())) {
            JSONObject jSONObject2 = new JSONObject();
            if ("only".equals(onlCgformField.getFieldValidType())) {
                jSONObject2.put("unique", true);
                jSONObject2.put("message", onlCgformField.getDbFieldTxt() + "不能重复");
            } else {
                jSONObject2.put("pattern", onlCgformField.getFieldValidType());
                jSONObject2.put("message", onlCgformField.getDbFieldTxt() + "格式不正确");
            }
            jSONArray.add(jSONObject2);
        }
        return jSONArray;
    }

    public static Map<String, Object> b(Map<String, Object> map) {
        HashMap hashMap = new HashMap();
        if (map == null || map.isEmpty()) {
            return hashMap;
        }
        for (String str : map.keySet()) {
            Object str2 = map.get(str);
            if (str2 instanceof Clob) {
                str2 = a((Clob) str2);
            } else if (str2 instanceof byte[]) {
                str2 = new String((byte[]) str2);
            } else if (str2 instanceof Blob) {
                if (str2 != null) {
                    try {
                        Blob blob = (Blob) str2;
                        str2 = new String(blob.getBytes(1L, (int) blob.length()), "UTF-8");
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            }
            String lowerCase = str.toLowerCase();
            if (str2 != null && (str2 instanceof String)) {
                String obj = str2.toString();
                if (obj.startsWith("[") && obj.endsWith("]")) {
                    str2 = JSONArray.parseArray(obj);
                }
            }
            hashMap.put(lowerCase, str2 == null ? "" : str2);
        }
        return hashMap;
    }

    public static JSONObject a(JSONObject jSONObject) {
        if (org.jeecg.modules.online.config.b.d.a()) {
            JSONObject jSONObject2 = new JSONObject();
            if (jSONObject == null || jSONObject.isEmpty()) {
                return jSONObject2;
            }
            for (String str : jSONObject.keySet()) {
                jSONObject2.put(str.toLowerCase(), jSONObject.get(str));
            }
            return jSONObject2;
        }
        return jSONObject;
    }

    public static List<Map<String, Object>> d(List<Map<String, Object>> list) {
        ArrayList arrayList = new ArrayList();
        for (Map<String, Object> map : list) {
            HashMap hashMap = new HashMap();
            for (String str : map.keySet()) {
                Object str2 = map.get(str);
                if (str2 instanceof Clob) {
                    str2 = a((Clob) str2);
                } else if (str2 instanceof byte[]) {
                    str2 = new String((byte[]) str2);
                } else if (str2 instanceof Blob) {
                    if (str2 != null) {
                        try {
                            Blob blob = (Blob) str2;
                            str2 = new String(blob.getBytes(1L, (int) blob.length()), "UTF-8");
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }
                }
                String lowerCase = str.toLowerCase();
                if (str2 != null && (str2 instanceof String)) {
                    String obj = str2.toString();
                    if (obj.startsWith("[") && obj.endsWith("]")) {
                        str2 = JSONArray.parseArray(obj);
                    }
                }
                hashMap.put(lowerCase, str2 == null ? "" : str2);
            }
            arrayList.add(hashMap);
        }
        return arrayList;
    }

    public static String a(Clob clob) {
        String str = "";
        try {
            Reader characterStream = clob.getCharacterStream();
            char[] cArr = new char[(int) clob.length()];
            characterStream.read(cArr);
            str = new String(cArr);
            characterStream.close();
        } catch (IOException e2) {
            e2.printStackTrace();
        } catch (SQLException e3) {
            e3.printStackTrace();
        }
        return str;
    }

    public static Map<String, Object> c(String tableName, List<OnlCgformField> onlCgformFields, JSONObject formData) {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        String str2 = "";
        try {
            str2 = org.jeecg.modules.online.config.b.d.getDatabaseType();
        } catch (SQLException e2) {
            e2.printStackTrace();
        } catch (DBException e3) {
            e3.printStackTrace();
        }
        HashMap<String, Object> hashMap = new HashMap<>();
        boolean hasId = false;
        String idValue = null;
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        // 来自流程服务中的调用时，不存在请求上下文
        if (loginUser == null) {
            String token = TokenUtils.getTokenByRequest();
            if (StringUtils.isNotEmpty(token)) {
                String username = JwtUtil.getUsername(token);
                loginUser = SpringContextUtils.getBean(CommonAPI.class).getUserByName(username);
            }
        }
        if (loginUser == null) {
            throw new JeecgBootException("online保存表单数据异常:系统未找到当前登陆用户信息");
        }
        for (OnlCgformField onlCgformField : onlCgformFields) {
            String dbFieldName = onlCgformField.getDbFieldName();
            if (null == dbFieldName) {
                ay.info("--------online保存表单数据遇见空名称的字段------->>" + onlCgformField.getId());
            } else if (formData.get(dbFieldName) != null || su.equalsIgnoreCase(dbFieldName) || st.equalsIgnoreCase(dbFieldName) || sx.equalsIgnoreCase(dbFieldName)) {
                a(onlCgformField, loginUser, formData, su, st, sx);
                if ("".equals(formData.get(dbFieldName))) {
                    String dbType = onlCgformField.getDbType();
                    if (!k.isNumber(dbType) && !k.b(dbType)) {
                    }
                }
                if ("id".equals(dbFieldName.toLowerCase())) {
                    hasId = true;
                    idValue = formData.getString(dbFieldName);
                } else {
                    stringBuffer.append(DOT_STRING + dbFieldName);
                    stringBuffer2.append(DOT_STRING + k.a(str2, onlCgformField, formData, hashMap));
                }
            }
        }
        if (!hasId || oConvertUtils.isEmpty(idValue)) {
            idValue = a();
        }
        String insertSql = "insert into " + f(tableName) + "(id" + stringBuffer.toString() + ") values(" + sz + idValue + sz + stringBuffer2.toString() + ")";
        hashMap.put("execute_sql_string", insertSql);
        ay.info("--表单设计器表单保存sql-->" + insertSql);
        // 将新的id带回去
        formData.put("id", idValue);
        return hashMap;
    }

    public static Map<String, Object> d(String tableName, List<OnlCgformField> onlCgformFields, JSONObject formData) {
        StringBuffer stringBuffer = new StringBuffer();
        HashMap hashMap = new HashMap();
        String str2 = "";
        try {
            str2 = org.jeecg.modules.online.config.b.d.getDatabaseType();
        } catch (SQLException e2) {
            e2.printStackTrace();
        } catch (DBException e3) {
            e3.printStackTrace();
        }
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        // 来自流程服务中的调用时，不存在请求上下文
        if (loginUser == null) {
            String token = TokenUtils.getTokenByRequest();
            if (StringUtils.isNotEmpty(token)) {
                String username = JwtUtil.getUsername(token);
                loginUser = SpringContextUtils.getBean(CommonAPI.class).getUserByName(username);
            }
        }
        if (loginUser == null) {
            throw new JeecgBootException("online保存表单数据异常:系统未找到当前登陆用户信息");
        }
        for (OnlCgformField onlCgformField : onlCgformFields) {
            String dbFieldName = onlCgformField.getDbFieldName();
            if (null == dbFieldName) {
                ay.info("--------online修改表单数据遇见空名称的字段------->>" + onlCgformField.getId());
            } else if (!"id".equals(dbFieldName) && (formData.get(dbFieldName) != null || sw.equalsIgnoreCase(dbFieldName) || sv.equalsIgnoreCase(dbFieldName) || sx.equalsIgnoreCase(dbFieldName))) {
                a(onlCgformField, loginUser, formData, sw, sv, sx);
                if ("".equals(formData.get(dbFieldName))) {
                    String dbType = onlCgformField.getDbType();
                    if (!k.isNumber(dbType) && !k.b(dbType)) {
                    }
                }
                stringBuffer.append(dbFieldName + EQ + k.a(str2, onlCgformField, formData, hashMap) + DOT_STRING);
            }
        }
        String stringBuffer2 = stringBuffer.toString();
        if (stringBuffer2.endsWith(DOT_STRING)) {
            stringBuffer2 = stringBuffer2.substring(0, stringBuffer2.length() - 1);
        }
        String str3 = "update " + f(tableName) + " set " + stringBuffer2 + WHERE + "id" + EQ + sz + formData.getString("id") + sz;
        ay.info("--表单设计器表单编辑sql-->" + str3);
        hashMap.put("execute_sql_string", str3);
        return hashMap;
    }

    public static Map<String, Object> a(String str, String str2, String str3) {
        HashMap hashMap = new HashMap();
        String str4 = "update " + f(str) + " set " + str2 + EQ + sz + 0 + sz + WHERE + "id" + EQ + sz + str3 + sz;
        ay.info("--修改树节点状态：为无子节点sql-->" + str4);
        hashMap.put("execute_sql_string", str4);
        return hashMap;
    }

    public static String e(String str) {
        if (str == null || "".equals(str) || "0".equals(str)) {
            return "";
        }
        return "CODE like '" + str + "%" + sz;
    }

    public static String f(String str) {
        if (Pattern.matches("^[a-zA-z].*\\$\\d+$", str)) {
            return str.substring(0, str.lastIndexOf(ss));
        }
        return str;
    }

    public static void a(LinkDownProperty linkDownProperty, List<OnlCgformField> list, List<String> list2) {
        String string = JSONObject.parseObject(linkDownProperty.getDictTable()).getString("linkField");
        ArrayList arrayList = new ArrayList();
        if (oConvertUtils.isNotEmpty(string)) {
            String[] split = string.split(DOT_STRING);
            for (OnlCgformField onlCgformField : list) {
                String dbFieldName = onlCgformField.getDbFieldName();
                int length = split.length;
                int i2 = 0;
                while (true) {
                    if (i2 < length) {
                        if (!split[i2].equals(dbFieldName)) {
                            i2++;
                        } else {
                            list2.add(dbFieldName);
                            arrayList.add(new BaseColumn(onlCgformField.getDbFieldTxt(), dbFieldName));
                            break;
                        }
                    } else {
                        break;
                    }
                }
            }
        }
        linkDownProperty.setOtherColumns(arrayList);
    }

    public static String a(byte[] bArr, String str, String str2, String str3) {
        return CommonUtils.uploadOnlineImage(bArr, str, str2, str3);
    }

    public static List<String> e(List<OnlCgformField> list) {
        ArrayList arrayList = new ArrayList();
        for (OnlCgformField onlCgformField : list) {
            if ("image".equals(onlCgformField.getFieldShowType())) {
                arrayList.add(onlCgformField.getDbFieldTxt());
            }
        }
        return arrayList;
    }

    public static List<String> b(List<OnlCgformField> list, String str) {
        ArrayList arrayList = new ArrayList();
        for (OnlCgformField onlCgformField : list) {
            if ("image".equals(onlCgformField.getFieldShowType())) {
                arrayList.add(str + "_" + onlCgformField.getDbFieldTxt());
            }
        }
        return arrayList;
    }

    public static String a() {
        return String.valueOf(IdWorker.getId());
    }

    public static String a(Exception exc) {
        String message = exc.getCause() != null ? exc.getCause().getMessage() : exc.getMessage();
        if (message.indexOf("ORA-01452") != -1) {
            message = "ORA-01452: 无法 CREATE UNIQUE INDEX; 找到重复的关键字";
        } else if (message.indexOf("duplicate key") != -1) {
            message = "无法 CREATE UNIQUE INDEX; 找到重复的关键字";
        }
        return message;
    }

    public static List<DictModel> a(OnlCgformField onlCgformField) {
        ArrayList arrayList = new ArrayList();
        String fieldExtendJson = onlCgformField.getFieldExtendJson();
        JSONArray parseArray = JSONArray.parseArray("[\"Y\",\"N\"]");
        if (oConvertUtils.isNotEmpty(fieldExtendJson)) {
            parseArray = JSONArray.parseArray(fieldExtendJson);
        }
        DictModel dictModel = new DictModel(parseArray.getString(0), "是");
        DictModel dictModel2 = new DictModel(parseArray.getString(1), "否");
        arrayList.add(dictModel);
        arrayList.add(dictModel2);
        return arrayList;
    }

    private static String c(OnlCgformField onlCgformField) {
        if ("checkbox".equals(onlCgformField.getFieldShowType())) {
            return "checkbox";
        }
        if (aj.equals(onlCgformField.getFieldShowType())) {
            return "select";
        }
        if (sI.equals(onlCgformField.getFieldShowType())) {
            return sI;
        }
        if ("sel_user".equals(onlCgformField.getFieldShowType())) {
            return "sel_user";
        }
        if ("sel_depart".equals(onlCgformField.getFieldShowType())) {
            return "sel_depart";
        }
        if ("image".equals(onlCgformField.getFieldShowType()) || "file".equals(onlCgformField.getFieldShowType()) || "radio".equals(onlCgformField.getFieldShowType()) || POPUP.equals(onlCgformField.getFieldShowType()) || "list_multi".equals(onlCgformField.getFieldShowType()) || "sel_search".equals(onlCgformField.getFieldShowType())) {
            return onlCgformField.getFieldShowType();
        }
        if ("datetime".equals(onlCgformField.getFieldShowType())) {
            return "datetime";
        }
        if (i.DATE.equals(onlCgformField.getFieldShowType())) {
            return i.DATE;
        }
        if ("int".equals(onlCgformField.getDbType()) || "double".equals(onlCgformField.getDbType()) || "BigDecimal".equals(onlCgformField.getDbType())) {
            return "inputNumber";
        }
        return "input";
    }

    private static String getDatabseType() {
        if (oConvertUtils.isNotEmpty(aA)) {
            return aA;
        }
        try {
            aA = org.jeecg.modules.online.config.b.d.getDatabaseType();
            return aA;
        } catch (Exception e2) {
            e2.printStackTrace();
            return aA;
        }
    }

    public static List<String> f(List<String> list) {
        ArrayList arrayList = new ArrayList();
        for (String str : list) {
            arrayList.add(str.toLowerCase());
        }
        return arrayList;
    }

    private static String b(String str, String str2) {
        String str3 = "";
        if (str2 == null || "".equals(str2)) {
            return str3;
        }
        String[] split = str2.split(DOT_STRING);
        for (int i2 = 0; i2 < split.length; i2++) {
            if (i2 > 0) {
                str3 = str3 + AND;
            }
            String str4 = str3 + str + LIKE;
            if ("SQLSERVER".equals(getDatabseType())) {
                str4 = str4 + "N";
            }
            str3 = str4 + "'%" + split[i2] + "%" + sz;
        }
        ay.info(" POPUP fieldSql: " + str3);
        return str3;
    }
}
