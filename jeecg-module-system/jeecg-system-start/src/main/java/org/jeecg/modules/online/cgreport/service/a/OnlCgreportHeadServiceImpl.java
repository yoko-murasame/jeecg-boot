package org.jeecg.modules.online.cgreport.service.a;

import cn.hutool.core.util.ReUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.DictModel;
import org.jeecg.common.system.vo.DynamicDataSourceModel;
import org.jeecg.common.util.dynamic.db.DataSourceCachePool;
import org.jeecg.common.util.dynamic.db.DynamicDBUtil;
import org.jeecg.common.util.dynamic.db.SqlUtils;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.online.cgform.d.j;
import org.jeecg.modules.online.cgform.enums.DataBaseEnum;
import org.jeecg.modules.online.cgreport.entity.OnlCgreportHead;
import org.jeecg.modules.online.cgreport.entity.OnlCgreportItem;
import org.jeecg.modules.online.cgreport.entity.OnlCgreportParam;
import org.jeecg.modules.online.cgreport.mapper.OnlCgreportHeadMapper;
import org.jeecg.modules.online.cgreport.model.OnlCgreportModel;
import org.jeecg.modules.online.cgreport.service.IOnlCgreportHeadService;
import org.jeecg.modules.online.cgreport.service.IOnlCgreportItemService;
import org.jeecg.modules.online.cgreport.service.IOnlCgreportParamService;
import org.jeecg.modules.online.cgreport.util.SqlUtil;
import org.jeecg.modules.online.config.exception.DBException;
import org.jeecg.modules.online.graphreport.util.GraphreportUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* compiled from: OnlCgreportHeadServiceImpl.java */
@Service("onlCgreportHeadServiceImpl")
/* loaded from: hibernate-common-ol-5.4.74(2).jar:org/jeecg/modules/online/cgreport/service/a/b.class */
public class OnlCgreportHeadServiceImpl extends ServiceImpl<OnlCgreportHeadMapper, OnlCgreportHead> implements IOnlCgreportHeadService {
    private static final Logger a = LoggerFactory.getLogger(OnlCgreportHeadServiceImpl.class);
    @Autowired
    private IOnlCgreportParamService onlCgreportParamService;
    @Autowired
    private IOnlCgreportItemService onlCgreportItemService;
    @Autowired
    private OnlCgreportHeadMapper mapper;
    @Autowired
    private ISysBaseAPI sysBaseAPI;

    @Override // org.jeecg.modules.online.cgreport.service.IOnlCgreportHeadService
    public Map<String, Object> executeSelectSql(String sql, String onlCgreportHeadId, Map<String, Object> params) throws SQLException {
        Object obj =null;
        IPage<Map<String, Object>> selectPageBySql;
        String str = null;
        try {
            str = org.jeecg.modules.online.config.b.d.getDatabaseType();
        } catch (DBException e) {
            e.printStackTrace();
        }
        LambdaQueryWrapper<OnlCgreportParam> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(OnlCgreportParam::getCgrheadId, onlCgreportHeadId);
        List<OnlCgreportParam> list = this.onlCgreportParamService.list(lambdaQueryWrapper);
        if (list != null && list.size() > 0) {
            for (OnlCgreportParam onlCgreportParam : list) {
                Object obj2 = params.get("self_" + onlCgreportParam.getParamName());
                String str2 = "";
                if (obj2 != null) {
                    str2 = obj2.toString();
                } else if (obj2 == null && oConvertUtils.isNotEmpty(onlCgreportParam.getParamValue())) {
                    str2 = onlCgreportParam.getParamValue();
                }
                String str3 = "${" + onlCgreportParam.getParamName() + "}";
                if (sql.indexOf(str3) > 0) {
                    if (str2 != null && str2.startsWith(org.jeecg.modules.online.cgform.d.b.sz) && str2.endsWith(org.jeecg.modules.online.cgform.d.b.sz)) {
                        str2 = str2.substring(1, str2.length() - 1);
                    }
                    sql = sql.replace(str3, str2);
                } else if (oConvertUtils.isNotEmpty(str2)) {
                    params.put(org.jeecg.modules.online.cgreport.b.a.D + onlCgreportParam.getParamName(), str2);
                }
            }
        }
        HashMap hashMap = new HashMap();
        Page<Map<String, Object>> page = new Page<>(Integer.valueOf(oConvertUtils.getInt(params.get("pageNo"), 1)).intValue(), Integer.valueOf(oConvertUtils.getInt(params.get("pageSize"), 10)).intValue());
        LambdaQueryWrapper<OnlCgreportItem> lambdaQueryWrapper2 = new LambdaQueryWrapper();
        lambdaQueryWrapper2.eq(OnlCgreportItem::getCgrheadId, onlCgreportHeadId);
        lambdaQueryWrapper2.eq(OnlCgreportItem::getIsSearch, 1);
        String a2 = org.jeecg.modules.online.cgreport.util.a.a(this.onlCgreportItemService.list(lambdaQueryWrapper2), params, "jeecg_rp_temp.", (String) null);
        if (ReUtil.contains(" order\\s+by ", sql.toLowerCase()) && "SQLSERVER".equalsIgnoreCase(str)) {
            throw new JeecgBootException("SqlServer不支持SQL内排序!");
        }
        String b = SqlUtil.b("select * from (" + sql + ") jeecg_rp_temp  where 1=1 " + a2);
        if (params.get("column") != null) {
            b = b + " order by jeecg_rp_temp." + obj.toString() + " " + params.get("order").toString();
        }
        a.info("报表查询sql=>\r\n" + b);
        if (Boolean.valueOf(String.valueOf(params.get("getAll"))).booleanValue()) {
            List<Map<String, Object>> executeSelect = this.mapper.executeSelect(b);
            selectPageBySql = new Page<>();
            selectPageBySql.setRecords(executeSelect);
            selectPageBySql.setTotal(executeSelect.size());
        } else {
            selectPageBySql = this.mapper.selectPageBySql(page, b);
        }
        hashMap.put("total", Long.valueOf(selectPageBySql.getTotal()));
        hashMap.put("records", org.jeecg.modules.online.cgform.d.b.d(selectPageBySql.getRecords()));
        return hashMap;
    }

    @Override // org.jeecg.modules.online.cgreport.service.IOnlCgreportHeadService
    public Map<String, Object> executeSelectSqlDynamic(String dbKey, String sql, Map<String, Object> params, String onlCgreportHeadId) {
        Object obj = null;
        DynamicDataSourceModel cacheDynamicDataSourceModel = DataSourceCachePool.getCacheDynamicDataSourceModel(dbKey);
        String str = (String) params.get("order");
        String str2 = (String) params.get("column");
        int i = oConvertUtils.getInt(params.get("pageNo"), 1);
        int i2 = oConvertUtils.getInt(params.get("pageSize"), 10);
        a.info("【Online多数据源逻辑】报表查询参数params: " + JSON.toJSONString(params));
        LambdaQueryWrapper<OnlCgreportParam> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(OnlCgreportParam::getCgrheadId, onlCgreportHeadId);
        List<OnlCgreportParam> list = this.onlCgreportParamService.list(lambdaQueryWrapper);
        if (list != null && list.size() > 0) {
            for (OnlCgreportParam onlCgreportParam : list) {
                Object obj2 = params.get("self_" + onlCgreportParam.getParamName());
                String str3 = "";
                if (obj2 != null) {
                    str3 = obj2.toString();
                } else if (obj2 == null && oConvertUtils.isNotEmpty(onlCgreportParam.getParamValue())) {
                    str3 = onlCgreportParam.getParamValue();
                }
                sql = sql.replace("${" + onlCgreportParam.getParamName() + "}", str3);
            }
        }
        LambdaQueryWrapper<OnlCgreportItem> lambdaQueryWrapper2 = new LambdaQueryWrapper();
        lambdaQueryWrapper2.eq(OnlCgreportItem::getCgrheadId, onlCgreportHeadId);
        lambdaQueryWrapper2.eq(OnlCgreportItem::getIsSearch, 1);
        List list2 = this.onlCgreportItemService.list(lambdaQueryWrapper2);
        if (ReUtil.contains(" order\\s+by ", sql.toLowerCase()) && "3".equalsIgnoreCase(cacheDynamicDataSourceModel.getDbType())) {
            throw new JeecgBootException("SqlServer不支持SQL内排序!");
        }
        String b = SqlUtil.b("select * from (" + sql + ") jeecg_rp_temp  where 1=1 " + org.jeecg.modules.online.cgreport.util.a.a(list2, params, "jeecg_rp_temp.", DataBaseEnum.getDataBaseNameByValue(cacheDynamicDataSourceModel.getDbType())));
        String countSql = SqlUtils.getCountSql(b);
        if (params.get("column") != null) {
            b = b + " order by jeecg_rp_temp." + obj.toString() + " " + params.get("order").toString();
        }
        String str4 = b;
        if (!Boolean.valueOf(String.valueOf(params.get("getAll"))).booleanValue()) {
            str4 = SqlUtils.createPageSqlByDBType(cacheDynamicDataSourceModel.getDbType(), b, i, i2);
        }
        a.info("多数据源 报表查询sql=>querySql: " + b);
        a.info("多数据源 报表查询sql=>pageSQL: " + str4);
        a.info("多数据源 报表查询sql=>countSql: " + countSql);
        HashMap hashMap = new HashMap();
        hashMap.put("total", ((Map) DynamicDBUtil.findOne(dbKey, countSql, new Object[0])).get("total"));
        hashMap.put("records", org.jeecg.modules.online.cgform.d.b.d(DynamicDBUtil.findList(dbKey, str4, new Object[0])));
        return hashMap;
    }

    @Override // org.jeecg.modules.online.cgreport.service.IOnlCgreportHeadService
    @Transactional(rollbackFor = {Exception.class})
    public Result<?> editAll(OnlCgreportModel values) {
        OnlCgreportHead head = values.getHead();
        if (((OnlCgreportHead) super.getById(head.getId())) == null) {
            return Result.error("未找到对应实体");
        }
        super.updateById(head);
        LambdaQueryWrapper<OnlCgreportItem> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(OnlCgreportItem::getCgrheadId, head.getId());
        this.onlCgreportItemService.remove(lambdaQueryWrapper);
        LambdaQueryWrapper<OnlCgreportParam> lambdaQueryWrapper2 = new LambdaQueryWrapper();
        lambdaQueryWrapper2.eq(OnlCgreportParam::getCgrheadId, head.getId());
        this.onlCgreportParamService.remove(lambdaQueryWrapper2);
        for (OnlCgreportParam onlCgreportParam : values.getParams()) {
            onlCgreportParam.setCgrheadId(head.getId());
        }
        for (OnlCgreportItem onlCgreportItem : values.getItems()) {
            onlCgreportItem.setFieldName(onlCgreportItem.getFieldName().trim().toLowerCase());
            onlCgreportItem.setCgrheadId(head.getId());
        }
        this.onlCgreportItemService.saveBatch(values.getItems());
        this.onlCgreportParamService.saveBatch(values.getParams());
        return Result.ok("全部修改成功");
    }

    @Override // org.jeecg.modules.online.cgreport.service.IOnlCgreportHeadService
    @Transactional(rollbackFor = {Exception.class})
    public Result<?> delete(String id) {
        if (super.removeById(id)) {
            LambdaQueryWrapper<OnlCgreportItem> lambdaQueryWrapper = new LambdaQueryWrapper();
            lambdaQueryWrapper.eq(OnlCgreportItem::getCgrheadId, id);
            this.onlCgreportItemService.remove(lambdaQueryWrapper);
            LambdaQueryWrapper<OnlCgreportParam> lambdaQueryWrapper2 = new LambdaQueryWrapper();
            lambdaQueryWrapper2.eq(OnlCgreportParam::getCgrheadId, id);
            this.onlCgreportParamService.remove(lambdaQueryWrapper2);
        }
        return Result.ok("删除成功");
    }

    @Override // org.jeecg.modules.online.cgreport.service.IOnlCgreportHeadService
    @Transactional(rollbackFor = {Exception.class})
    public Result<?> bathDelete(String[] ids) {
        for (String str : ids) {
            if (super.removeById(str)) {
                LambdaQueryWrapper<OnlCgreportItem> lambdaQueryWrapper = new LambdaQueryWrapper();
                lambdaQueryWrapper.eq(OnlCgreportItem::getCgrheadId, str);
                this.onlCgreportItemService.remove(lambdaQueryWrapper);
                LambdaQueryWrapper<OnlCgreportParam> lambdaQueryWrapper2 = new LambdaQueryWrapper();
                lambdaQueryWrapper2.eq(OnlCgreportParam::getCgrheadId, str);
                this.onlCgreportParamService.remove(lambdaQueryWrapper2);
            }
        }
        return Result.ok("删除成功");
    }

    @Override // org.jeecg.modules.online.cgreport.service.IOnlCgreportHeadService
    public List<String> getSqlFields(String sql, String dbKey) throws SQLException {
        List<String> a2;
        if (StringUtils.isNotBlank(dbKey)) {
            a2 = a(sql, dbKey);
        } else {
            a2 = a(sql, null);
        }
        return a2;
    }

    @Override // org.jeecg.modules.online.cgreport.service.IOnlCgreportHeadService
    public List<String> getSqlParams(String sql) {
        if (oConvertUtils.isEmpty(sql)) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        Matcher matcher = Pattern.compile("\\$\\{\\w+\\}").matcher(sql);
        while (matcher.find()) {
            String group = matcher.group();
            arrayList.add(group.substring(group.indexOf("{") + 1, group.indexOf("}")));
        }
        return arrayList;
    }

    private List<String> a(String str, String str2) throws SQLException {
        Set keySet;
        if (oConvertUtils.isEmpty(str)) {
            return null;
        }
        String trim = str.replace(org.jeecg.modules.online.cgform.d.b.sk, org.jeecg.modules.online.cgreport.b.a.u).trim();
        if (trim.endsWith(";")) {
            trim = trim.substring(0, trim.length() - 1);
        }
        String a2 = SqlUtil.a(QueryGenerator.convertSystemVariables(trim));
        if (StringUtils.isNotBlank(str2)) {
            a.info("parse sql : " + a2);
            DynamicDataSourceModel cacheDynamicDataSourceModel = DataSourceCachePool.getCacheDynamicDataSourceModel(str2);
            if (ReUtil.contains(" order\\s+by ", a2.toLowerCase()) && "3".equalsIgnoreCase(cacheDynamicDataSourceModel.getDbType())) {
                throw new JeecgBootException("SqlServer不支持SQL内排序!");
            }
            if ("1".equals(cacheDynamicDataSourceModel.getDbType())) {
                a2 = GraphreportUtil.f4ay + a2 + ") temp LIMIT 1";
            } else if ("2".equals(cacheDynamicDataSourceModel.getDbType())) {
                a2 = GraphreportUtil.f4ay + a2 + ") temp WHERE ROWNUM <= 1";
            } else if ("3".equals(cacheDynamicDataSourceModel.getDbType())) {
                a2 = "SELECT TOP 1 * FROM (" + a2 + ") temp";
            }
            a.info("parse sql with page : " + a2);
            Map map = (Map) DynamicDBUtil.findOne(str2, a2, new Object[0]);
            if (map == null) {
                throw new JeecgBootException("该报表sql没有数据");
            }
            keySet = map.keySet();
        } else {
            a.info("parse sql: " + a2);
            String str3 = null;
            try {
                str3 = org.jeecg.modules.online.config.b.d.getDatabaseType();
            } catch (DBException e) {
                e.printStackTrace();
            }
            if (ReUtil.contains(" order\\s+by ", a2.toLowerCase()) && "SQLSERVER".equalsIgnoreCase(str3)) {
                throw new JeecgBootException("SqlServer不支持SQL内排序!");
            }
            List records = this.mapper.selectPageBySql(new Page<>(1L, 1L), a2).getRecords();
            if (records.size() < 1) {
                throw new JeecgBootException("该报表sql没有数据");
            }
            keySet = ((Map) records.get(0)).keySet();
        }
        if (keySet != null) {
            keySet.remove("ROW_ID");
        }
        return new ArrayList(keySet);
    }

    @Override // org.jeecg.modules.online.cgreport.service.IOnlCgreportHeadService
    public Map<String, Object> queryCgReportConfig(String reportId) {
        HashMap hashMap = new HashMap(0);
        Map<String, Object> queryCgReportMainConfig = this.mapper.queryCgReportMainConfig(reportId);
        List<Map<String, Object>> queryCgReportItems = this.mapper.queryCgReportItems(reportId);
        List<OnlCgreportParam> queryCgReportParams = this.mapper.queryCgReportParams(reportId);
        if (org.jeecg.modules.online.config.b.d.a()) {
            hashMap.put(org.jeecg.modules.online.cgreport.b.a.a, org.jeecg.modules.online.cgform.d.b.b(queryCgReportMainConfig));
            hashMap.put(org.jeecg.modules.online.cgreport.b.a.b, org.jeecg.modules.online.cgform.d.b.d(queryCgReportItems));
        } else {
            hashMap.put(org.jeecg.modules.online.cgreport.b.a.a, queryCgReportMainConfig);
            hashMap.put(org.jeecg.modules.online.cgreport.b.a.b, queryCgReportItems);
        }
        hashMap.put(org.jeecg.modules.online.cgreport.b.a.c, queryCgReportParams);
        return hashMap;
    }

    @Override // org.jeecg.modules.online.cgreport.service.IOnlCgreportHeadService
    public List<Map<?, ?>> queryByCgReportSql(String sql, Map params, Map paramData, int pageNo, int pageSize) {
        String a2 = SqlUtil.a(sql, params);
        List<Map<?, ?>> list = null;
        if (paramData == null || paramData.size() == 0) {
        }
        if (pageNo == -1 && pageSize == -1) {
            list = this.mapper.executeSelete(a2);
        } else {
            IPage<Map<String, Object>> selectPageBySql = this.mapper.selectPageBySql(new Page<>(pageNo, pageSize), a2);
            if (selectPageBySql.getRecords() != null && selectPageBySql.getRecords().size() > 0) {
                list.addAll(selectPageBySql.getRecords());
            }
        }
        return list;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v27, types: [java.util.List] */
    @Override // org.jeecg.modules.online.cgreport.service.IOnlCgreportHeadService
    public List<DictModel> queryDictSelectData(String sql, String keyword) {
        ArrayList<DictModel> arrayList = new ArrayList();
        Page<Map<String, Object>> page = new Page<>();
        page.setSearchCount(false);
        page.setCurrent(1L);
        page.setSize(10L);
        String sql2 = sql.trim();
        int lastIndexOf = sql2.lastIndexOf(";");
        if (lastIndexOf == sql2.length() - 1) {
            sql2 = sql2.substring(0, lastIndexOf);
        }
        if (keyword != null && !"".equals(keyword)) {
            String str = " like '%" + keyword + "%'";
            sql2 = "select * from (" + sql2 + ") t where t.value " + str + org.jeecg.modules.online.cgform.d.b.sq + "t.text" + str;
        }
        List records = ((OnlCgreportHeadMapper) this.baseMapper).selectPageBySql(page, sql2).getRecords();
        if (records != null && records.size() != 0) {
            arrayList = (ArrayList<DictModel>) JSON.parseArray(JSON.toJSONString(records), DictModel.class);
        }
        return arrayList;
    }

    @Override // org.jeecg.modules.online.cgreport.service.IOnlCgreportHeadService
    public Map<String, Object> queryColumnInfo(String code, boolean queryDict) {
        HashMap hashMap = new HashMap();
        LambdaQueryWrapper<OnlCgreportItem> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OnlCgreportItem::getCgrheadId, code)
                .eq(OnlCgreportItem::getIsShow, 1)
                .orderByAsc(OnlCgreportItem::getOrderNum);
        List<OnlCgreportItem> list = this.onlCgreportItemService.list(queryWrapper);
        JSONArray jSONArray = new JSONArray();
        JSONArray jSONArray2 = new JSONArray();
        HashMap hashMap2 = new HashMap();
        boolean z = false;
        for (OnlCgreportItem onlCgreportItem : list) {
            JSONObject jSONObject = new JSONObject(4);
            jSONObject.put("title", onlCgreportItem.getFieldTxt());
            jSONObject.put("dataIndex", onlCgreportItem.getFieldName());
            jSONObject.put("fieldType", onlCgreportItem.getFieldType());
            jSONObject.put("align", org.jeecg.modules.online.cgform.d.b.aw);
            jSONObject.put("sorter", "true");
            jSONObject.put("isTotal", onlCgreportItem.getIsTotal());
            jSONObject.put("groupTitle", onlCgreportItem.getGroupTitle());
            if (oConvertUtils.isNotEmpty(onlCgreportItem.getGroupTitle())) {
                z = true;
            }
            String fieldType = onlCgreportItem.getFieldType();
            if ("Integer".equals(fieldType) || "Date".equals(fieldType) || "Long".equals(fieldType)) {
                jSONObject.put("sorter", "true");
            }
            if (StringUtils.isNotBlank(onlCgreportItem.getFieldHref())) {
                String str = j.e + onlCgreportItem.getFieldName();
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("customRender", str);
                jSONObject.put("scopedSlots", jSONObject2);
                JSONObject jSONObject3 = new JSONObject();
                jSONObject3.put("slotName", str);
                jSONObject3.put("href", onlCgreportItem.getFieldHref());
                jSONArray.add(jSONObject3);
            }
            String dictCode = onlCgreportItem.getDictCode();
            if (dictCode != null && !"".equals(dictCode)) {
                if (queryDict) {
                    hashMap2.put(onlCgreportItem.getFieldName(), queryColumnDict(onlCgreportItem.getDictCode(), null, null));
                    jSONObject.put("customRender", onlCgreportItem.getFieldName());
                } else {
                    jSONObject.put("dictCode", dictCode);
                }
            }
            jSONArray2.add(jSONObject);
        }
        if (queryDict) {
            hashMap.put("dictOptions", hashMap2);
        }
        hashMap.put("columns", jSONArray2);
        hashMap.put("fieldHrefSlots", jSONArray);
        hashMap.put("isGroupTitle", Boolean.valueOf(z));
        return hashMap;
    }

    @Override // org.jeecg.modules.online.cgreport.service.IOnlCgreportHeadService
    public List<DictModel> queryColumnDict(String dictCode, JSONArray records, String fieldName) {
        List<DictModel> list = null;
        if (oConvertUtils.isNotEmpty(dictCode)) {
            if (dictCode.trim().toLowerCase().indexOf("select ") == 0 && (fieldName == null || records.size() > 0)) {
                String dictCode2 = dictCode.trim();
                int lastIndexOf = dictCode2.lastIndexOf(";");
                if (lastIndexOf == dictCode2.length() - 1) {
                    dictCode2 = dictCode2.substring(0, lastIndexOf);
                }
                String str = GraphreportUtil.f4ay + dictCode2 + ") temp ";
                if (records != null) {
                    ArrayList arrayList = new ArrayList();
                    for (int i = 0; i < records.size(); i++) {
                        String string = records.getJSONObject(i).getString(fieldName);
                        if (StringUtils.isNotBlank(string)) {
                            arrayList.add(string);
                        }
                    }
                    str = str + "WHERE temp.value IN (" + (org.jeecg.modules.online.cgform.d.b.sz + StringUtils.join(arrayList, "','") + org.jeecg.modules.online.cgform.d.b.sz) + ")";
                }
                List<Map<?, ?>> executeSelete = ((OnlCgreportHeadMapper) getBaseMapper()).executeSelete(str);
                if (executeSelete != null && executeSelete.size() != 0) {
                    list = JSON.parseArray(JSON.toJSONString(executeSelete), DictModel.class);
                }
            } else {
                list = this.sysBaseAPI.queryDictItemsByCode(dictCode);
            }
        }
        return list;
    }
}
