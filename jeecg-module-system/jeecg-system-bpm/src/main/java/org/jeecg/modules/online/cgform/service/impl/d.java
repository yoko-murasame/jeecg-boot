package org.jeecg.modules.online.cgform.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.aspect.DictAspect;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.system.query.QueryRuleEnum;
import org.jeecg.common.system.util.JeecgDataAutorUtils;
import org.jeecg.common.system.vo.DictModel;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.system.vo.SysPermissionDataRuleModel;
import org.jeecg.common.util.CommonUtils;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.common.util.SqlInjectionUtil;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.online.auth.mapper.OnlAuthDataMapper;
import org.jeecg.modules.online.auth.service.IOnlAuthPageService;
import org.jeecg.modules.online.cgform.d.c;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;
import org.jeecg.modules.online.cgform.entity.OnlCgformHead;
import org.jeecg.modules.online.cgform.mapper.OnlCgformFieldMapper;
import org.jeecg.modules.online.cgform.mapper.OnlCgformHeadMapper;
import org.jeecg.modules.online.cgform.model.TreeModel;
import org.jeecg.modules.online.cgform.service.IOnlCgformFieldService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/* compiled from: OnlCgformFieldServiceImpl.java */
@Service("onlCgformFieldServiceImpl")
/* loaded from: hibernate-common-ol-5.4.74(2).jar:org/jeecg/modules/online/cgform/service/impl/d.class */
public class d extends ServiceImpl<OnlCgformFieldMapper, OnlCgformField> implements IOnlCgformFieldService {
    private static final Logger a = LoggerFactory.getLogger(d.class);
    @Autowired
    private OnlCgformFieldMapper onlCgformFieldMapper;
    @Autowired
    private OnlCgformHeadMapper cgformHeadMapper;
    @Autowired
    private OnlAuthDataMapper onlAuthDataMapper;
    @Autowired
    private IOnlAuthPageService onlAuthPageService;
    @Autowired
    private ISysBaseAPI sysBaseAPI;
    private static final String b = "0";

    @Value("${mybatis-plus.global-config.db-config.logic-delete-field:del_flag}")
    private String MYBATIS_LOGIC_DELETE_FIELD;

    @Value("${mybatis-plus.global-config.db-config.logic-delete-value:1}")
    private String MYBATIS_LOGIC_DELETE_FIELD_VAL;

    @Value("${mybatis-plus.global-config.db-config.logic-not-delete-value:0}")
    private String MYBATIS_LOGIC_NOT_DELETE_FIELD_VAL;

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformFieldService
    public Map<String, Object> queryAutolistPage(String tableName, String code, Map<String, Object> params, List<String> needList, String dataRulePerms, String queryAllColumn) {
        Map<String, Object> resultMap = new HashMap<>();
        LambdaQueryWrapper<OnlCgformField> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OnlCgformField::getCgformHeadId, code);
        lambdaQueryWrapper.orderByAsc(OnlCgformField::getOrderNum);
        List<OnlCgformField> allFields = list(lambdaQueryWrapper);

        StringBuffer stringBuffer = new StringBuffer();

        // 所有列
        List<OnlCgformField> queryAvailableFields = allFields;
        // 如果未传入查询所有列，查找列表显示字段+权限控制字段
        if (StringUtils.isBlank(queryAllColumn)) {
            queryAvailableFields = queryAvailableFields(code, tableName, true, allFields, needList);
        }
        // 组装SELECT
        org.jeecg.modules.online.cgform.d.b.assembleSelect(tableName, queryAvailableFields, stringBuffer);

        // 数据权限规则
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        List<SysPermissionDataRuleModel> queryOwnerAuth = this.onlAuthDataMapper.queryOwnerAuth(loginUser.getId(), code);
        // 数据权限规则-全局perms
        if (StringUtils.isNotBlank(dataRulePerms)) {
            List<SysPermissionDataRuleModel> dataRules = this.sysBaseAPI.queryPermissionDataRuleByPerms(dataRulePerms, loginUser.getUsername(), QueryRuleEnum.RIGHT_LIKE);
            if (dataRules != null && !dataRules.isEmpty()) {
                queryOwnerAuth.addAll(dataRules);
            }
        }
        // 注入请求上下文的当前用户
        if (!CollectionUtils.isEmpty(queryOwnerAuth)) {
            JeecgDataAutorUtils.installUserInfo(this.sysBaseAPI.getCacheUser(loginUser.getUsername()));
        }

        // 检查sql注入
        String[] arr1 = new String[]{};
        arr1 = params.toString().split(",");
        SqlInjectionUtil.filterContent(arr1);

        // 组装WHERE
        String whereCondition = org.jeecg.modules.online.cgform.d.b.assembleQuery(allFields, params, needList, queryOwnerAuth) + org.jeecg.modules.online.cgform.d.b.assembleSuperQuery(params);

        //判断字段中是否包含逻辑删除字段
        String logicDelflagSql = " AND del_flag=" + MYBATIS_LOGIC_NOT_DELETE_FIELD_VAL + " ";
        Optional<OnlCgformField> delFlagOptional = allFields.stream().filter(item -> ((OnlCgformField) item).getDbFieldName().equals(MYBATIS_LOGIC_DELETE_FIELD)).findFirst();
        if(delFlagOptional.isPresent()){
            whereCondition = whereCondition + logicDelflagSql;
        }
        if (StringUtils.isNotBlank(whereCondition)) {
            // stringBuffer.append(org.jeecg.modules.online.cgform.d.b.WHERE_1_1 + whereCondition);
            // 去除第一个and
            if (whereCondition.startsWith(org.jeecg.modules.online.cgform.d.b.AND)) {
                whereCondition = whereCondition.replaceFirst(org.jeecg.modules.online.cgform.d.b.AND, "");
            }
            stringBuffer.append(org.jeecg.modules.online.cgform.d.b.WHERE).append(whereCondition);
        }
        // 组装ORDER BY
        Object column = params.get("column");
        if (column != null) {
            String columnString = column.toString();
            String order = params.get("order").toString();
            if (hasDbField(columnString, allFields)) {
                stringBuffer.append(org.jeecg.modules.online.cgform.d.b.ORDERBY).append(oConvertUtils.camelToUnderline(columnString));
                if (org.jeecg.modules.online.cgform.d.b.ASC.equals(order)) {
                    stringBuffer.append(" asc");
                } else {
                    stringBuffer.append(" desc");
                }
            }
        }
        // 检查sql注入（这里会影响online列表getData的查询）
        // SqlInjectionUtil.filterContent(stringBuffer.toString());
        int valueOf = params.get("pageSize") == null ? 10 : Integer.parseInt(params.get("pageSize").toString());
        // System.out.println(stringBuffer);
        // jeecg作者自己协定的不分页值
        if (valueOf == -521) {
            List<Map<String, Object>> queryListBySql = this.onlCgformFieldMapper.queryListBySql(stringBuffer.toString());
            a.debug("---Online查询sql 不分页 :>>" + stringBuffer);
            if (queryListBySql == null || queryListBySql.isEmpty()) {
                resultMap.put("total", 0);
                resultMap.put("fieldList", queryAvailableFields);
            } else {
                resultMap.put("total", queryListBySql.size());
                resultMap.put("fieldList", queryAvailableFields);
                resultMap.put("records", org.jeecg.modules.online.cgform.d.b.d(queryListBySql));
            }
        } else {
            Page<Map<String, Object>> page = new Page<>(params.get("pageNo") == null ? 1 : Integer.parseInt(params.get("pageNo").toString()), valueOf);
            a.debug("---Online查询sql:>>" + stringBuffer);
            IPage<Map<String, Object>> selectPageBySql = this.onlCgformFieldMapper.selectPageBySql(page, stringBuffer.toString());
            resultMap.put("total", selectPageBySql.getTotal());
            resultMap.put("records", org.jeecg.modules.online.cgform.d.b.d(selectPageBySql.getRecords()));
        }
        // 翻译懒加载字典（主要是表字典的配置，以结果值为准）
        Map<String, List<DictModel>> dictOptions = this.transferLazyDictOptions((List<Map<String, Object>>) resultMap.get("records"), queryAvailableFields);
        // 去重
        for (String key : dictOptions.keySet()) {
            dictOptions.computeIfPresent(key, (k, dictModels) -> dictModels.stream().distinct().collect(Collectors.toList()));
        }
        resultMap.put("dictOptions", dictOptions);
        return resultMap;
    }

    /**
     * 翻译懒加载字典
     *
     * @author Yoko
     * @since 2024/9/5 15:19
     * @param records 待翻译结果列表
     * @param onlCgformFields 表单字段列表
     * @return 字典数组
     */
    public Map<String, List<DictModel>> transferLazyDictOptions(List<Map<String, Object>> records, List<OnlCgformField> onlCgformFields) {
        Map<String, List<String>> dataListMap = new HashMap<>(5);
        // 组装字典 {字典code:值列表}
        for (OnlCgformField onlCgformField : onlCgformFields) {
            String fieldShowType = onlCgformField.getFieldShowType();
            String dbFieldName = onlCgformField.getDbFieldName();
            // 没有设置懒加载的跳过、还有popup组件跳过
            if (!Objects.equals(1, onlCgformField.getDictLazyLoad()) || org.jeecg.modules.online.cgform.d.b.POPUP.equals(fieldShowType)) {
                continue;
            }
            // 懒加载字典
            List<String> dicVals = records.stream().map(e -> Optional.ofNullable(e.get(dbFieldName)).orElse("").toString()).filter(StringUtils::isNotEmpty).collect(Collectors.toList());
            // 最终字典key
            String dictCode = getFinalDictCode(onlCgformField);
            if (StringUtils.isNotEmpty(dictCode)) {
                List<String> nowValues = dataListMap.computeIfAbsent(dictCode, k -> new ArrayList<>());
                nowValues.addAll(dicVals);
            }
        }
        // 统一翻译
        DictAspect dictAspect = SpringContextUtils.getBean(DictAspect.class);
        Map<String, List<DictModel>> dictOptions = dictAspect.translateAllDict(dataListMap);

        // 翻译结果注入到列表
        for (Map<String, Object> record : records) {
            for (OnlCgformField onlCgformField : onlCgformFields) {
                // 最终字典key
                String dictCode = getFinalDictCode(onlCgformField);
                if (StringUtils.isEmpty(dictCode)) {
                    continue;
                }
                String value = (String) record.get(onlCgformField.getDbFieldName());
                if (oConvertUtils.isNotEmpty(value)) {
                    List<DictModel> dictModels = dictOptions.get(dictCode);
                    if (dictModels == null || dictModels.isEmpty()) {
                        continue;
                    }
                    String textValue = dictAspect.translDictText(dictModels, value);
                    record.put(onlCgformField.getDbFieldName() + CommonConstant.DICT_TEXT_SUFFIX, textValue);
                }
            }
        }

        return dictOptions;
    }

    private String getFinalDictCode(OnlCgformField onlCgformField) {
        String dictField = onlCgformField.getDictField();
        String dictTable = onlCgformField.getDictTable();
        String dictText = onlCgformField.getDictText();
        String dictCode = "";
        if (oConvertUtils.isNotEmpty(dictTable)) {
            // 表字典，去除 dictTable 的条件
            dictCode = String.format("%s,%s,%s", CommonUtils.getTableNameByTableSql(dictTable), dictText, dictField);
        } else if (oConvertUtils.isNotEmpty(dictField)) {
            String[] dics = dictField.split(",");
            if (dics.length == 1) {
                // 系统字典
                dictCode = dictField;
            } else if (dics.length == 3 || dics.length == 4) {
                // 表字典，去除 dictTable 的条件
                dictCode = String.format("%s,%s,%s", dics[0], dics[1], dics[2]);
            } else {
                throw new RuntimeException("字典配置错误，格式为：表名,显示字段,值字段,可选条件");
            }
        }
        return dictCode;
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformFieldService
    public Map<String, Object> queryAutoExportlist(String tbname, String headId, Map<String, Object> params, List<String> needList) {
        HashMap hashMap = new HashMap();
        LambdaQueryWrapper<OnlCgformField> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(OnlCgformField::getCgformHeadId, headId);
        lambdaQueryWrapper.orderByAsc(OnlCgformField::getOrderNum);
        List list = list(lambdaQueryWrapper);
        //老版本是根据列表或表单字段去调出
        //新版本根据字段配置获取到字段列表
        List<OnlCgformField> queryAvailableFields = queryAvailableExportFields(headId, tbname, true, list, needList);
        StringBuffer stringBuffer = new StringBuffer();
        // 组装SELECT
        org.jeecg.modules.online.cgform.d.b.assembleSelect(tbname, queryAvailableFields, stringBuffer);
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        List<SysPermissionDataRuleModel> queryOwnerAuth = this.onlAuthDataMapper.queryOwnerAuth(loginUser.getId(), headId);
        if (queryOwnerAuth != null && queryOwnerAuth.size() > 0) {
            JeecgDataAutorUtils.installUserInfo(this.sysBaseAPI.getCacheUser(loginUser.getUsername()));
        }
        // 检查sql注入
        String[] arr1 = new String[]{};
        arr1 = params.toString().split(",");
        SqlInjectionUtil.filterContent(arr1);


        // 组装WHERE
        String whereCondition = org.jeecg.modules.online.cgform.d.b.assembleQuery(list, params, needList, queryOwnerAuth) + org.jeecg.modules.online.cgform.d.b.assembleSuperQuery(params);

        //判断字段中是否包含逻辑删除字段
        String logicDelflagSql = " AND del_flag=" + MYBATIS_LOGIC_NOT_DELETE_FIELD_VAL + " ";
        Optional<OnlCgformField> delFlagOptional = list.stream().filter(item -> ((OnlCgformField) item).getDbFieldName().equals(MYBATIS_LOGIC_DELETE_FIELD)).findFirst();
        if(delFlagOptional.isPresent()){
            whereCondition = whereCondition + logicDelflagSql;
        }
        if (StringUtils.isNotBlank(whereCondition)) {
            // stringBuffer.append(org.jeecg.modules.online.cgform.d.b.WHERE_1_1 + whereCondition);
            // 去除第一个and
            if (whereCondition.startsWith(org.jeecg.modules.online.cgform.d.b.AND)) {
                whereCondition = whereCondition.replaceFirst(org.jeecg.modules.online.cgform.d.b.AND, "");
            }
            stringBuffer.append(org.jeecg.modules.online.cgform.d.b.WHERE + whereCondition);
        }
        // 组装ORDER BY
        Object obj = params.get("column");
        if (obj != null) {
            String obj2 = obj.toString();
            String obj3 = params.get("order").toString();
            if (hasDbField(obj2, list)) {
                stringBuffer.append(org.jeecg.modules.online.cgform.d.b.ORDERBY + oConvertUtils.camelToUnderline(obj2));
                if (org.jeecg.modules.online.cgform.d.b.ASC.equals(obj3)) {
                    stringBuffer.append(" asc");
                } else {
                    stringBuffer.append(" desc");
                }
            }
        }
        // 检查sql注入（这里会影响online列表getData的查询）
        // SqlInjectionUtil.filterContent(stringBuffer.toString());
        Integer valueOf = Integer.valueOf(params.get("pageSize") == null ? 10 : Integer.parseInt(params.get("pageSize").toString()));
        if (valueOf.intValue() == -521) {
            List<Map<String, Object>> queryListBySql = this.onlCgformFieldMapper.queryListBySql(stringBuffer.toString());
            a.debug("---Online查询sql 不分页 :>>" + stringBuffer.toString());
            if (queryListBySql == null || queryListBySql.size() == 0) {
                hashMap.put("total", 0);
                hashMap.put("fieldList", queryAvailableFields);
            } else {
                hashMap.put("total", Integer.valueOf(queryListBySql.size()));
                hashMap.put("fieldList", queryAvailableFields);
                hashMap.put("records", org.jeecg.modules.online.cgform.d.b.d(queryListBySql));
            }
        } else {
            Page<Map<String, Object>> page = new Page<>(Integer.valueOf(params.get("pageNo") == null ? 1 : Integer.parseInt(params.get("pageNo").toString())).intValue(), valueOf.intValue());
            a.debug("---Online查询sql:>>" + stringBuffer.toString());
            IPage<Map<String, Object>> selectPageBySql = this.onlCgformFieldMapper.selectPageBySql(page, stringBuffer.toString());
            hashMap.put("total", Long.valueOf(selectPageBySql.getTotal()));
            hashMap.put("records", org.jeecg.modules.online.cgform.d.b.d(selectPageBySql.getRecords()));
        }
        return hashMap;
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformFieldService
    public Map<String, Object> queryAutoTreeNoPage(String tbname, String headId, Map<String, Object> params, List<String> needList, String pidField) {
        HashMap hashMap = new HashMap();
        LambdaQueryWrapper<OnlCgformField> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(OnlCgformField::getCgformHeadId, headId);
        lambdaQueryWrapper.orderByAsc(OnlCgformField::getOrderNum);
        List list = list(lambdaQueryWrapper);
        List<OnlCgformField> queryAvailableFields = queryAvailableFields(headId, tbname, true, list, needList);
        StringBuffer stringBuffer = new StringBuffer();
        org.jeecg.modules.online.cgform.d.b.assembleSelect(tbname, queryAvailableFields, stringBuffer);
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        List<SysPermissionDataRuleModel> queryOwnerAuth = this.onlAuthDataMapper.queryOwnerAuth(loginUser.getId(), headId);
        if (queryOwnerAuth != null && queryOwnerAuth.size() > 0) {
            JeecgDataAutorUtils.installUserInfo(this.sysBaseAPI.getCacheUser(loginUser.getUsername()));
        }
        stringBuffer.append(org.jeecg.modules.online.cgform.d.b.WHERE_1_1 + org.jeecg.modules.online.cgform.d.b.assembleQuery(list, params, needList, queryOwnerAuth) + org.jeecg.modules.online.cgform.d.b.assembleSuperQuery(params));
        Object obj = params.get("column");
        if (obj != null) {
            String obj2 = obj.toString();
            String obj3 = params.get("order").toString();
            if (hasDbField(obj2, list)) {
                stringBuffer.append(org.jeecg.modules.online.cgform.d.b.ORDERBY + oConvertUtils.camelToUnderline(obj2));
                if (org.jeecg.modules.online.cgform.d.b.ASC.equals(obj3)) {
                    stringBuffer.append(" asc");
                } else {
                    stringBuffer.append(" desc");
                }
            }
        }
        Integer valueOf = Integer.valueOf(params.get("pageSize") == null ? 10 : Integer.parseInt(params.get("pageSize").toString()));
        if (valueOf.intValue() == -521) {
            ArrayList<Map<String, Object>> queryListBySql = (ArrayList) this.onlCgformFieldMapper.queryListBySql(stringBuffer.toString());
            if ("true".equals(params.get("hasQuery"))) {
                ArrayList arrayList = new ArrayList();
                for (Map<String, Object> map : queryListBySql) {
                    String obj4 = map.get(pidField).toString();
                    if (obj4 != null && !b.equals(obj4)) {
                        Map<String, Object> a2 = a(obj4, tbname, headId, needList, pidField);
                        if (a2 != null && a2.size() > 0 && !arrayList.contains(a2)) {
                            arrayList.add(a2);
                        }
                    } else if (!arrayList.contains(map)) {
                        arrayList.add(map);
                    }
                }
                queryListBySql = arrayList;
            }
            a.debug("---Online查询sql 不分页 :>>" + stringBuffer.toString());
            if (queryListBySql == null || queryListBySql.size() == 0) {
                hashMap.put("total", 0);
                hashMap.put("fieldList", queryAvailableFields);
            } else {
                hashMap.put("total", Integer.valueOf(queryListBySql.size()));
                hashMap.put("fieldList", queryAvailableFields);
                hashMap.put("records", org.jeecg.modules.online.cgform.d.b.d(queryListBySql));
            }
        } else {
            Page<Map<String, Object>> page = new Page<>(Integer.valueOf(params.get("pageNo") == null ? 1 : Integer.parseInt(params.get("pageNo").toString())).intValue(), valueOf.intValue());
            a.debug("---Online查询sql:>>" + stringBuffer.toString());
            IPage<Map<String, Object>> selectPageBySql = this.onlCgformFieldMapper.selectPageBySql(page, stringBuffer.toString());
            hashMap.put("total", Long.valueOf(selectPageBySql.getTotal()));
            hashMap.put("records", org.jeecg.modules.online.cgform.d.b.d(selectPageBySql.getRecords()));
        }
        return hashMap;
    }

    private Map<String, Object> a(String str, String str2, String str3, List<String> list, String str4) {
        HashMap hashMap = new HashMap();
        hashMap.put("id", str);
        LambdaQueryWrapper<OnlCgformField> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(OnlCgformField::getCgformHeadId, str3);
        lambdaQueryWrapper.orderByAsc(OnlCgformField::getOrderNum);
        List list2 = list(lambdaQueryWrapper);
        List<OnlCgformField> queryAvailableFields = queryAvailableFields(str3, str2, true, list2, list);
        StringBuffer stringBuffer = new StringBuffer();
        org.jeecg.modules.online.cgform.d.b.assembleSelect(str2, queryAvailableFields, stringBuffer);
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        List<SysPermissionDataRuleModel> queryOwnerAuth = this.onlAuthDataMapper.queryOwnerAuth(loginUser.getId(), str3);
        if (queryOwnerAuth != null && queryOwnerAuth.size() > 0) {
            JeecgDataAutorUtils.installUserInfo(this.sysBaseAPI.getCacheUser(loginUser.getUsername()));
        }
        stringBuffer.append(org.jeecg.modules.online.cgform.d.b.WHERE_1_1 + org.jeecg.modules.online.cgform.d.b.assembleQuery(list2, hashMap, list, queryOwnerAuth) + "and id='" + str + org.jeecg.modules.online.cgform.d.b.sz);
        List<Map<String, Object>> queryListBySql = this.onlCgformFieldMapper.queryListBySql(stringBuffer.toString());
        if (queryListBySql != null && queryListBySql.size() > 0) {
            Map<String, Object> map = queryListBySql.get(0);
            if (map != null && map.get(str4) != null && !b.equals(map.get(str4))) {
                return a(map.get(str4).toString(), str2, str3, list, str4);
            }
            return map;
        }
        return null;
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformFieldService
    public void saveFormData(String code, String tbname, JSONObject json, boolean isCrazy) {
        LambdaQueryWrapper<OnlCgformField> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(OnlCgformField::getCgformHeadId, code);
        List list = list(lambdaQueryWrapper);
        if (isCrazy) {
            ((OnlCgformFieldMapper) this.baseMapper).executeInsertSQL(org.jeecg.modules.online.cgform.d.b.c(tbname, list, json));
        } else {
            ((OnlCgformFieldMapper) this.baseMapper).executeInsertSQL(org.jeecg.modules.online.cgform.d.b.a(tbname, list, json));
        }
    }

    /**
     * 去掉用户相关操作
     */
    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformFieldService
    public void saveFormDataForCyclePlan(String code, String tbname, JSONObject json, boolean isCrazy) {
        // LambdaQueryWrapper<OnlCgformField> lambdaQueryWrapper = new LambdaQueryWrapper();
        // lambdaQueryWrapper.eq(OnlCgformField::getCgformHeadId, code);
        // List list = list(lambdaQueryWrapper);
        // if (isCrazy) {
        //     ((OnlCgformFieldMapper) this.baseMapper).executeInsertSQL(org.jeecg.modules.online.cgform.d.b.getCrazyFormDataForCyclePlan(tbname, list, json));
        // } else {
        //     ((OnlCgformFieldMapper) this.baseMapper).executeInsertSQL(org.jeecg.modules.online.cgform.d.b.getFormDataForCyclePlan(tbname, list, json));
        // }
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformFieldService
    public void saveTreeFormData(String code, String tbname, JSONObject json, String hasChildField, String pidField) {
        LambdaQueryWrapper<OnlCgformField> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(OnlCgformField::getCgformHeadId, code);
        List<OnlCgformField> list = list(lambdaQueryWrapper);
        for (OnlCgformField onlCgformField : list) {
            if (hasChildField.equals(onlCgformField.getDbFieldName()) && onlCgformField.getIsShowForm().intValue() != 1) {
                onlCgformField.setIsShowForm(1);
                json.put(hasChildField, b);
            } else if (pidField.equals(onlCgformField.getDbFieldName()) && oConvertUtils.isEmpty(json.get(pidField))) {
                onlCgformField.setIsShowForm(1);
                json.put(pidField, b);
            }
        }
        ((OnlCgformFieldMapper) this.baseMapper).executeInsertSQL(org.jeecg.modules.online.cgform.d.b.a(tbname, list, json));
        if (!b.equals(json.getString(pidField))) {
            ((OnlCgformFieldMapper) this.baseMapper).editFormData("update " + tbname + " set " + hasChildField + " = '1' where id = '" + json.getString(pidField) + org.jeecg.modules.online.cgform.d.b.sz);
        }
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformFieldService
    public void saveFormData(List<OnlCgformField> fieldList, String tbname, JSONObject json) {

        // 优先删除同主键的数据，防止特殊情况的一对多报错（一个字段存了多个外键）
        String id = json.getString("id");
        if (StringUtils.isNotEmpty(id)) {
            // 如果是删除重复id的逻辑，每次主表保存后，这个子数据关联的其他字段还是会不见，因此如果存在这个数据 拒绝保存
            if (this.baseMapper.queryCountBySql("select count(*) from " + tbname + " where id = '" + id + "'") > 0) {
                // 找到外键字段 如果存在多个外键怎么办？已支持
                List<OnlCgformField> exists = fieldList.stream().filter(e -> StringUtils.isNotEmpty(e.getMainTable()) && StringUtils.isNotEmpty(e.getMainField())).collect(Collectors.toList());
                if (!exists.isEmpty()) {
                    Map<String, Object> sourceData = this.baseMapper.queryFormData("select * from " + tbname + " where id = '" + id + "'");

                    // 处理所有外键字段
                    for (OnlCgformField exist : exists) {
                        // 先获取原先的数据
                        String sourceValue = (String) sourceData.get(exist.getDbFieldName());

                        // 当前的外键数据
                        String currentValue = json.getString(exist.getDbFieldName());

                        if (StringUtils.isNotEmpty(sourceValue)) {
                            // 判断关联的主表id是否有多个逗号
                            List<String> arr = Arrays.asList(sourceValue.split(","));
                            if (arr.size() > 1) {
                                if (!arr.contains(currentValue)) {
                                    arr.add(currentValue);
                                }
                                json.put(exist.getDbFieldName(), String.join(",", arr));
                            }
                        }
                    }

                    // 删除原先的数据
                    this.baseMapper.deleteAutoList("delete from " + tbname + " where id = '" + id + "'");
                    a.warn("重复id的数据，存在外键，已扩展外键字段，表名：{}，id={}，JSON={}", tbname, id, json.toJSONString());
                } else {
                    // 找不到外键字段，就跳过这个数据的保存
                    a.warn("重复id的数据，不存在外键，不允许保存，表名：{}，id={}，JSON={}", tbname, id, json.toJSONString());
                    return;
                }
            }
        }

        // 保存数据
        Map<String, Object> sql = org.jeecg.modules.online.cgform.d.b.a(tbname, fieldList, json);
        ((OnlCgformFieldMapper) this.baseMapper).executeInsertSQL(sql);
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformFieldService
    public void editFormData(String code, String tbname, JSONObject json, boolean isCrazy) {
        LambdaQueryWrapper<OnlCgformField> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(OnlCgformField::getCgformHeadId, code);
        List list = list(lambdaQueryWrapper);
        if (isCrazy) {
            ((OnlCgformFieldMapper) this.baseMapper).executeUpdatetSQL(org.jeecg.modules.online.cgform.d.b.d(tbname, list, json));
        } else {
            ((OnlCgformFieldMapper) this.baseMapper).executeUpdatetSQL(org.jeecg.modules.online.cgform.d.b.b(tbname, list, json));
        }
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformFieldService
    public void editTreeFormData(String code, String tbname, JSONObject json, String hasChildField, String pidField) {
        String f = org.jeecg.modules.online.cgform.d.b.f(tbname);
        String obj = org.jeecg.modules.online.cgform.d.b.b(((OnlCgformFieldMapper) this.baseMapper).queryFormData("select * from " + f + " where id = '" + json.getString("id") + org.jeecg.modules.online.cgform.d.b.sz)).get(pidField).toString();
        LambdaQueryWrapper<OnlCgformField> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(OnlCgformField::getCgformHeadId, code);
        List<OnlCgformField> list = list(lambdaQueryWrapper);
        for (OnlCgformField onlCgformField : list) {
            if (pidField.equals(onlCgformField.getDbFieldName()) && oConvertUtils.isEmpty(json.get(pidField))) {
                onlCgformField.setIsShowForm(1);
                json.put(pidField, b);
            }
        }
        ((OnlCgformFieldMapper) this.baseMapper).executeUpdatetSQL(org.jeecg.modules.online.cgform.d.b.b(tbname, list, json));
        if (!obj.equals(json.getString(pidField))) {
            if (!b.equals(obj)) {
                Integer queryCountBySql = ((OnlCgformFieldMapper) this.baseMapper).queryCountBySql("select count(*) from " + f + " where " + pidField + " = '" + obj + org.jeecg.modules.online.cgform.d.b.sz);
                if (queryCountBySql == null || queryCountBySql.intValue() == 0) {
                    ((OnlCgformFieldMapper) this.baseMapper).editFormData("update " + f + " set " + hasChildField + " = '0' where id = '" + obj + org.jeecg.modules.online.cgform.d.b.sz);
                }
            }
            if (!b.equals(json.getString(pidField))) {
                ((OnlCgformFieldMapper) this.baseMapper).editFormData("update " + f + " set " + hasChildField + " = '1' where id = '" + json.getString(pidField) + org.jeecg.modules.online.cgform.d.b.sz);
            }
        }
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformFieldService
    public Map<String, Object> queryFormData(String code, String tbname, String id) {
        LambdaQueryWrapper<OnlCgformField> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(OnlCgformField::getCgformHeadId, code);
        lambdaQueryWrapper.eq(OnlCgformField::getIsShowForm, 1);
        return this.onlCgformFieldMapper.queryFormData(org.jeecg.modules.online.cgform.d.b.a(tbname, list(lambdaQueryWrapper), id));
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformFieldService
    @Transactional(rollbackFor = {Exception.class})
    public void deleteAutoListMainAndSub(OnlCgformHead head, String ids) {
        String[] split;
        List list = new ArrayList();
        if (head.getTableType().intValue() == 2) {
            String id = head.getId();
            String tableName = head.getTableName();
            ArrayList<Map> arrayList = new ArrayList();
            if (c.b(head.getSubTableStr())) {
                for (String str : head.getSubTableStr().split(org.jeecg.modules.online.cgform.d.b.DOT_STRING)) {
                    OnlCgformHead onlCgformHead = (OnlCgformHead) this.cgformHeadMapper.selectOne((Wrapper) new LambdaQueryWrapper<OnlCgformHead>()
                            .eq(OnlCgformHead::getTableName, str));
                    if (onlCgformHead != null &&
                            (list = list(new LambdaQueryWrapper<OnlCgformField>()
                                    .eq(OnlCgformField::getCgformHeadId, onlCgformHead.getId())
                                    .eq(OnlCgformField::getMainTable, head.getTableName()))) != null &&
                            list.size() != 0) {
                        OnlCgformField onlCgformField = (OnlCgformField) list.get(0);
                        HashMap hashMap = new HashMap();
                        hashMap.put("linkField", onlCgformField.getDbFieldName());
                        hashMap.put("mainField", onlCgformField.getMainField());
                        hashMap.put("tableName", str);
                        hashMap.put("linkValueStr", "");
                        arrayList.add(hashMap);
                    }
                }
                LambdaQueryWrapper<OnlCgformField> lambdaQueryWrapper = new LambdaQueryWrapper();
                lambdaQueryWrapper.eq(OnlCgformField::getCgformHeadId, id);
                List list2 = list(lambdaQueryWrapper);
                for (String str2 : ids.split(org.jeecg.modules.online.cgform.d.b.DOT_STRING)) {
                    Map<String, Object> queryFormData = this.onlCgformFieldMapper.queryFormData(org.jeecg.modules.online.cgform.d.b.a(tableName, list2, str2));
                    new ArrayList();
                    for (Map map : arrayList) {
                        Object obj = queryFormData.get(((String) map.get("mainField")).toLowerCase());
                        if (obj == null) {
                            obj = queryFormData.get(((String) map.get("mainField")).toUpperCase());
                        }
                        if (obj != null) {
                            map.put("linkValueStr", ((String) map.get("linkValueStr")) + String.valueOf(obj) + org.jeecg.modules.online.cgform.d.b.DOT_STRING);
                        }
                    }
                }
                for (Map map2 : arrayList) {
                    deleteAutoList((String) map2.get("tableName"), (String) map2.get("linkField"), (String) map2.get("linkValueStr"));
                }
            }
            deleteAutoListById(head.getTableName(), ids);

        }
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformFieldService
    public void deleteAutoListById(String tbname, String ids) {
        deleteAutoList(tbname, "id", ids);
    }


    public void logicDeleteAutoListById(String tbname, String ids) {
        logicDeleteAutoList(tbname, "id", ids);
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformFieldService
    public void deleteAutoList(String tbname, String linkField, String linkValue) {
        if (linkValue != null && !"".equals(linkValue)) {
            OnlCgformHead onlCgformHead = (OnlCgformHead) this.cgformHeadMapper.selectOne((Wrapper) new LambdaQueryWrapper<OnlCgformHead>()
                    .eq(OnlCgformHead::getTableName, tbname));
            List<OnlCgformField> fields = this.onlCgformFieldMapper.selectList(new LambdaQueryWrapper<OnlCgformField>()
                    .eq(OnlCgformField::getCgformHeadId, onlCgformHead.getId()));
            Optional<OnlCgformField> delFlagOptional = fields.stream().filter(item -> ((OnlCgformField) item).getDbFieldName().equals(MYBATIS_LOGIC_DELETE_FIELD)).findFirst();
            String[] split = linkValue.split(org.jeecg.modules.online.cgform.d.b.DOT_STRING);
            StringBuffer stringBuffer = new StringBuffer();
            for (String str : split) {
                if (str != null && !"".equals(str)) {
                    stringBuffer.append(org.jeecg.modules.online.cgform.d.b.sz + str + "',");
                }
            }
            String stringBuffer2 = stringBuffer.toString();
            String str2 = "";
            if (delFlagOptional.isPresent()) {
                str2 = "UPDATE " + org.jeecg.modules.online.cgform.d.b.f(tbname) + " set " + MYBATIS_LOGIC_DELETE_FIELD + " = " + MYBATIS_LOGIC_DELETE_FIELD_VAL + " where " + linkField + " in(" + stringBuffer2.substring(0, stringBuffer2.length() - 1) + ")";
            } else {
                str2 = "DELETE FROM " + org.jeecg.modules.online.cgform.d.b.f(tbname) + " where " + linkField + " in(" + stringBuffer2.substring(0, stringBuffer2.length() - 1) + ")";
            }

            // str2 += " OR " + linkField + " like '%" + stringBuffer2.substring(0, stringBuffer2.length() - 1) + "%'";
            a.debug("--删除sql-->" + str2);
            this.onlCgformFieldMapper.deleteAutoList(str2);
        }
    }


    public void logicDeleteAutoList(String tbname, String linkField, String linkValue) {
        if (linkValue != null && !"".equals(linkValue)) {
            String[] split = linkValue.split(org.jeecg.modules.online.cgform.d.b.DOT_STRING);
            StringBuffer stringBuffer = new StringBuffer();
            for (String str : split) {
                if (str != null && !"".equals(str)) {
                    stringBuffer.append(org.jeecg.modules.online.cgform.d.b.sz + str + "',");
                }
            }
            String stringBuffer2 = stringBuffer.toString();
            String str2 = "UPDATE " + org.jeecg.modules.online.cgform.d.b.f(tbname) + " set " + MYBATIS_LOGIC_DELETE_FIELD + " = " + MYBATIS_LOGIC_DELETE_FIELD_VAL + " where " + linkField + " in(" + stringBuffer2.substring(0, stringBuffer2.length() - 1) + ")";
            // str2 += " OR " + linkField + " like '%" + stringBuffer2.substring(0, stringBuffer2.length() - 1) + "%'";
            a.debug("--删除sql-->" + str2);
            this.onlCgformFieldMapper.deleteAutoList(str2);
        }
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformFieldService
    public List<Map<String, String>> getAutoListQueryInfo(String code) {
        LambdaQueryWrapper<OnlCgformField> lambdaQueryWrapper = new LambdaQueryWrapper<OnlCgformField>();
        lambdaQueryWrapper.eq(OnlCgformField::getCgformHeadId, code);
        lambdaQueryWrapper.eq(OnlCgformField::getIsQuery, 1);
        lambdaQueryWrapper.orderByAsc(OnlCgformField::getOrderNum);
        List<OnlCgformField> list = list(lambdaQueryWrapper);
        ArrayList arrayList = new ArrayList();
        int i = 0;
        for (OnlCgformField onlCgformField : list) {
            HashMap<String, Object> hashMap = new HashMap();
            hashMap.put("label", onlCgformField.getDbFieldTxt());
            hashMap.put("field", onlCgformField.getDbFieldName());
            hashMap.put("mode", onlCgformField.getQueryMode());
            if ("1".equals(onlCgformField.getQueryConfigFlag())) {
                hashMap.put("config", "1");
                hashMap.put("view", onlCgformField.getQueryShowType());
                hashMap.put("defValue", onlCgformField.getQueryDefVal());
                if (org.jeecg.modules.online.cgform.d.b.CAT_TREE.equals(onlCgformField.getFieldShowType())) {
                    hashMap.put("pcode", onlCgformField.getQueryDictField());
                } else if (org.jeecg.modules.online.cgform.d.b.sN.equals(onlCgformField.getFieldShowType())) {
                    String[] split = onlCgformField.getQueryDictText().split(org.jeecg.modules.online.cgform.d.b.DOT_STRING);
                    hashMap.put("dict", onlCgformField.getQueryDictTable() + org.jeecg.modules.online.cgform.d.b.DOT_STRING + split[2] + org.jeecg.modules.online.cgform.d.b.DOT_STRING + split[0]);
                    hashMap.put("pidField", split[1]);
                    hashMap.put("hasChildField", split[3]);
                    hashMap.put("pidValue", onlCgformField.getQueryDictField());
                } else {
                    hashMap.put("dictTable", onlCgformField.getQueryDictTable());
                    hashMap.put("dictCode", onlCgformField.getQueryDictField());
                    hashMap.put("dictText", onlCgformField.getQueryDictText());
                }
            } else {
                hashMap.put("view", onlCgformField.getFieldShowType());
                if (org.jeecg.modules.online.cgform.d.b.CAT_TREE.equals(onlCgformField.getFieldShowType())) {
                    hashMap.put("pcode", onlCgformField.getDictField());
                } else if (org.jeecg.modules.online.cgform.d.b.sN.equals(onlCgformField.getFieldShowType())) {
                    String[] split2 = onlCgformField.getDictText().split(org.jeecg.modules.online.cgform.d.b.DOT_STRING);
                    hashMap.put("dict", onlCgformField.getDictTable() + org.jeecg.modules.online.cgform.d.b.DOT_STRING + split2[2] + org.jeecg.modules.online.cgform.d.b.DOT_STRING + split2[0]);
                    hashMap.put("pidField", split2[1]);
                    hashMap.put("hasChildField", split2[3]);
                    hashMap.put("pidValue", onlCgformField.getDictField());
                } else if (org.jeecg.modules.online.cgform.d.b.POPUP.equals(onlCgformField.getFieldShowType())) {
                    hashMap.put("dictTable", onlCgformField.getDictTable());
                    hashMap.put("dictCode", onlCgformField.getDictField());
                    hashMap.put("dictText", onlCgformField.getDictText());
                } else if ("sel_search".equals(onlCgformField.getFieldShowType())) {
                    hashMap.put("dictTable", onlCgformField.getDictTable());
                    hashMap.put("dictCode", onlCgformField.getDictField());
                    hashMap.put("dictText", onlCgformField.getDictText());
                }
                hashMap.put("mode", onlCgformField.getQueryMode());
            }
            i++;
            if (i > 2) {
                hashMap.put("hidden", "1");
            }
            arrayList.add(hashMap);
        }
        return arrayList;
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformFieldService
    public List<OnlCgformField> queryFormFields(String code, boolean isform) {
        LambdaQueryWrapper<OnlCgformField> lambdaQueryWrapper = new LambdaQueryWrapper<OnlCgformField>()
                .eq(OnlCgformField::getCgformHeadId, code);
        if (isform) {
            lambdaQueryWrapper.eq(OnlCgformField::getIsShowForm, 1);
        }
        return list(lambdaQueryWrapper);
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformFieldService
    public List<OnlCgformField> queryFormFieldsByTableName(String tableName) {
        OnlCgformHead onlCgformHead = this.cgformHeadMapper.selectOne(new LambdaQueryWrapper<OnlCgformHead>()
                .eq(OnlCgformHead::getTableName, tableName));

        if (onlCgformHead != null) {
            LambdaQueryWrapper lambdaQueryWrapper = new LambdaQueryWrapper<OnlCgformField>()
                    .eq(OnlCgformField::getCgformHeadId, onlCgformHead.getId());
            return list(lambdaQueryWrapper);
        }
        return null;
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformFieldService
    public OnlCgformField queryFormFieldByTableNameAndField(String tableName, String fieldName) {
        OnlCgformHead onlCgformHead = this.cgformHeadMapper.selectOne(new LambdaQueryWrapper<OnlCgformHead>()
                .eq(OnlCgformHead::getTableName, tableName));
        if (onlCgformHead != null) {
            LambdaQueryWrapper lambdaQueryWrapper = new LambdaQueryWrapper<OnlCgformField>()
                    .eq(OnlCgformField::getCgformHeadId, onlCgformHead.getId())
                    .eq(OnlCgformField::getDbFieldName, fieldName);
            if (list(lambdaQueryWrapper) != null && list(lambdaQueryWrapper).size() > 0) {
                return (OnlCgformField) list(lambdaQueryWrapper).get(0);
            }
            return null;
        }
        return null;
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformFieldService
    public Map<String, Object> queryFormData(List<OnlCgformField> fieldList, String tbname, String id) {
        return this.onlCgformFieldMapper.queryFormData(org.jeecg.modules.online.cgform.d.b.a(tbname, fieldList, id));
    }

    @Override
    public List<Map<String, Object>> querySubFormData(List<OnlCgformField> fieldList, String tbname, String linkField, String value) {
        return this.onlCgformFieldMapper.queryListData(org.jeecg.modules.online.cgform.d.b.a(tbname, fieldList, linkField, value));
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformFieldService
    public IPage<Map<String, Object>> selectPageBySql(Page<Map<String, Object>> page, String sql) {
        return ((OnlCgformFieldMapper) this.baseMapper).selectPageBySql(page, sql);
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformFieldService
    public List<String> selectOnlineHideColumns(String tbname) {
        return a(((OnlCgformFieldMapper) this.baseMapper).selectOnlineHideColumns(((LoginUser) SecurityUtils.getSubject().getPrincipal()).getId(), "online:" + tbname + ":%"));
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformFieldService
    public List<OnlCgformField> queryAvailableFields(String cgFormId, String tbname, String taskId, boolean isList) {
        List<String> selectFlowAuthColumns;
        List<OnlCgformField> list = list(new LambdaQueryWrapper<OnlCgformField>()
                .eq(OnlCgformField::getCgformHeadId, cgFormId)
                .eq(OnlCgformField::getIsShowList, 1)
                .eq(OnlCgformField::getIsShowForm, 1)
                .orderByAsc(OnlCgformField::getOrderNum));
        String str = "online:" + tbname + "%";
        String id = ((LoginUser) SecurityUtils.getSubject().getPrincipal()).getId();
        List<String> arrayList = new ArrayList<>();
        if (oConvertUtils.isEmpty(taskId)) {
            List<String> queryHideCode = this.onlAuthPageService.queryHideCode(id, cgFormId, isList);
            if (queryHideCode != null && queryHideCode.size() != 0 && queryHideCode.get(0) != null) {
                arrayList.addAll(queryHideCode);
            }
        } else if (c.b(taskId) && (selectFlowAuthColumns = ((OnlCgformFieldMapper) this.baseMapper).selectFlowAuthColumns(tbname, taskId, "1")) != null && selectFlowAuthColumns.size() > 0 && selectFlowAuthColumns.get(0) != null) {
            arrayList.addAll(selectFlowAuthColumns);
        }
        if (arrayList.size() == 0) {
            return list;
        }
        List<OnlCgformField> arrayList2 = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            OnlCgformField onlCgformField = list.get(i);
            if (b(onlCgformField.getDbFieldName(), arrayList)) {
                arrayList2.add(onlCgformField);
            }
        }
        return arrayList2;
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformFieldService
    public List<String> queryDisabledFields(String tbname) {
        return a(((OnlCgformFieldMapper) this.baseMapper).selectOnlineDisabledColumns(((LoginUser) SecurityUtils.getSubject().getPrincipal()).getId(), "online:" + tbname + "%"));
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformFieldService
    public List<String> queryDisabledFields(String tbname, String taskId) {
        if (oConvertUtils.isEmpty(taskId)) {
            return null;
        }
        return a(((OnlCgformFieldMapper) this.baseMapper).selectFlowAuthColumns(tbname, taskId, "2"));
    }

    private List<String> a(List<String> list) {
        ArrayList arrayList = new ArrayList();
        if (list == null || list.size() == 0 || list.get(0) == null) {
            return arrayList;
        }
        for (String str : list) {
            if (!oConvertUtils.isEmpty(str)) {
                String substring = str.substring(str.lastIndexOf(":") + 1);
                if (!oConvertUtils.isEmpty(substring)) {
                    arrayList.add(substring);
                }
            }
        }
        return arrayList;
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformFieldService
    public List<OnlCgformField> queryAvailableFields(String tbname, boolean isList, List<OnlCgformField> List, List<String> needList) {
        return getFieldList(((OnlCgformFieldMapper) this.baseMapper).selectOnlineHideColumns(((LoginUser) SecurityUtils.getSubject().getPrincipal()).getId(), "online:" + tbname + "%"), isList, List, needList);
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformFieldService
    public List<OnlCgformField> queryAvailableFields(String cgformId, String tbname, boolean isList, List<OnlCgformField> fieldList, List<String> needList) {
        java.util.List<String> listHideColumn = this.onlAuthPageService.queryListHideColumn(((LoginUser) SecurityUtils.getSubject().getPrincipal()).getId(), cgformId);
        return getFieldList(listHideColumn, isList, fieldList, needList);
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformFieldService
    public List<OnlCgformField> queryAvailableExportFields(String cgformId, String tbname, boolean isList, List<OnlCgformField> List, List<String> needList) {
        return aExport(this.onlAuthPageService.queryListHideColumn(((LoginUser) SecurityUtils.getSubject().getPrincipal()).getId(), cgformId), isList, List, needList);
    }

    private List<OnlCgformField> getFieldList(List<String> listHideColumn, boolean isList, List<OnlCgformField> fieldList, List<String> needList) {
        List<OnlCgformField> res = new ArrayList<>();
        boolean var6 = true;
        if (listHideColumn == null || listHideColumn.isEmpty() || listHideColumn.get(0) == null) {
            var6 = false;
        }

        Iterator var7 = fieldList.iterator();

        while (true) {
            while (var7.hasNext()) {
                OnlCgformField var8 = (OnlCgformField) var7.next();
                String var9 = var8.getDbFieldName();
                if (needList != null && needList.contains(var9)) {
                    var8.setIsQuery(1);
                    res.add(var8);
                } else {

                    if (isList) {
                        if (var8.getIsShowList() != 1) {
                            if (c.b(var8.getMainTable()) && c.b(var8.getMainField())) {
                                res.add(var8);
                            }
                            continue;
                        }
                    } else if (var8.getIsShowForm() != 1) {
                        continue;
                    }

                    if (var6) {
                        if (this.b(var9, listHideColumn)) {
                            res.add(var8);
                        }
                    } else {
                        res.add(var8);
                    }
                }
            }

            return res;
        }
    }


    //根据配置的数据获取可导出的字段
    private List<OnlCgformField> aExport(List<String> var1, boolean var2, List<OnlCgformField> var3, List<String> var4) {
        ArrayList var5 = new ArrayList();
        boolean var6 = true;
        if (var1 == null || var1.size() == 0 || var1.get(0) == null) {
            var6 = false;
        }

        Iterator var7 = var3.iterator();

        while (true) {
            while (var7.hasNext()) {
                OnlCgformField var8 = (OnlCgformField) var7.next();
                // fixme 后续引入此功能时，再判断是否为限制导出列
                var5.add(var8);
//                 if (var8.getIsExport() == 1) {
// //                        if (org.jeecg.modules.online.cgform.d.c.b(var8.getMainTable()) && c.b(var8.getMainField())) {
//                     var5.add(var8);
// //                        }
//                     continue;
//                 }
            }
            return var5;
        }
    }

    private boolean b(String str, List<String> list) {
        boolean z = true;
        for (int i = 0; i < list.size(); i++) {
            String str2 = list.get(i);
            if (!oConvertUtils.isEmpty(str2)) {
                String substring = str2.substring(str2.lastIndexOf(":") + 1);
                if (!oConvertUtils.isEmpty(substring) && substring.equals(str)) {
                    z = false;
                }
            }
        }
        return z;
    }

    public boolean hasDbField(String str, List<OnlCgformField> list) {
        boolean z = false;
        Iterator<OnlCgformField> it = list.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            if (oConvertUtils.camelToUnderline(str).equals(it.next().getDbFieldName())) {
                z = true;
                break;
            }
        }
        return z;
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformFieldService
    public void executeInsertSQL(Map<String, Object> params) {
        ((OnlCgformFieldMapper) this.baseMapper).executeInsertSQL(params);
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformFieldService
    public void executeUpdatetSQL(Map<String, Object> params) {
        ((OnlCgformFieldMapper) this.baseMapper).executeUpdatetSQL(params);
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformFieldService
    public List<TreeModel> queryDataListByLinkDown(org.jeecg.modules.online.cgform.a.a linkDown) {
        return ((OnlCgformFieldMapper) this.baseMapper).queryDataListByLinkDown(linkDown);
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformFieldService
    public void updateTreeNodeNoChild(String tableName, String filed, String id) {
        ((OnlCgformFieldMapper) this.baseMapper).executeUpdatetSQL(org.jeecg.modules.online.cgform.d.b.a(tableName, filed, id));
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformFieldService
    public String queryTreeChildIds(OnlCgformHead head, String ids) {
        String treeParentIdField = head.getTreeParentIdField();
        String tableName = head.getTableName();
        String[] split = ids.split(org.jeecg.modules.online.cgform.d.b.DOT_STRING);
        StringBuffer stringBuffer = new StringBuffer();
        for (String str : split) {
            if (str != null && !stringBuffer.toString().contains(str)) {
                if (stringBuffer.toString().length() > 0) {
                    stringBuffer.append(org.jeecg.modules.online.cgform.d.b.DOT_STRING);
                }
                stringBuffer.append(str);
                a(str, treeParentIdField, tableName, stringBuffer);
            }
        }
        return stringBuffer.toString();
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformFieldService
    public String queryTreePids(OnlCgformHead head, String ids) {
        String treeParentIdField = head.getTreeParentIdField();
        String tableName = head.getTableName();
        StringBuffer stringBuffer = new StringBuffer();
        String[] split = ids.split(org.jeecg.modules.online.cgform.d.b.DOT_STRING);
        for (String str : split) {
            if (str != null) {
                String obj = org.jeecg.modules.online.cgform.d.b.b(((OnlCgformFieldMapper) this.baseMapper).queryFormData("select * from " + org.jeecg.modules.online.cgform.d.b.f(tableName) + " where id = '" + str + org.jeecg.modules.online.cgform.d.b.sz)).get(treeParentIdField).toString();
                List<Map<String, Object>> queryListBySql = this.onlCgformFieldMapper.queryListBySql("select * from " + org.jeecg.modules.online.cgform.d.b.f(tableName) + " where " + treeParentIdField + "= '" + obj + "' and id not in(" + ids + ")");
                if ((queryListBySql == null || queryListBySql.size() == 0) && !Arrays.asList(split).contains(obj) && !stringBuffer.toString().contains(obj)) {
                    stringBuffer.append(obj).append(org.jeecg.modules.online.cgform.d.b.DOT_STRING);
                }
            }
        }
        return stringBuffer.toString();
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformFieldService
    public String queryForeignKey(String cgFormId, String mainTable) {

        List list = list(new LambdaQueryWrapper<OnlCgformField>()
                .eq(OnlCgformField::getCgformHeadId, cgFormId)
                .eq(OnlCgformField::getMainTable, mainTable));
        if (list != null && list.size() > 0) {
            return ((OnlCgformField) list.get(0)).getMainField();
        }
        return null;
    }

    private StringBuffer a(String str, String str2, String str3, StringBuffer stringBuffer) {
        List<Map<String, Object>> queryListBySql = this.onlCgformFieldMapper.queryListBySql("select * from " + org.jeecg.modules.online.cgform.d.b.f(str3) + " where " + str2 + "= '" + str + org.jeecg.modules.online.cgform.d.b.sz);
        if (queryListBySql != null && queryListBySql.size() > 0) {
            for (Map<String, Object> map : queryListBySql) {
                Map<String, Object> b2 = org.jeecg.modules.online.cgform.d.b.b(map);
                if (!stringBuffer.toString().contains(b2.get("id").toString())) {
                    stringBuffer.append(org.jeecg.modules.online.cgform.d.b.DOT_STRING).append(b2.get("id"));
                }
                a(b2.get("id").toString(), str2, str3, stringBuffer);
            }
        }
        return stringBuffer;
    }
}
