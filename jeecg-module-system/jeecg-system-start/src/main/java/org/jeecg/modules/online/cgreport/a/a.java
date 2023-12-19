package org.jeecg.modules.online.cgreport.a;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.PermissionData;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.system.vo.DictModel;
import org.jeecg.common.system.vo.DynamicDataSourceModel;
import org.jeecg.common.util.BrowserUtils;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.online.cgreport.entity.OnlCgreportHead;
import org.jeecg.modules.online.cgreport.entity.OnlCgreportItem;
import org.jeecg.modules.online.cgreport.entity.OnlCgreportParam;
import org.jeecg.modules.online.cgreport.mapper.OnlCgreportHeadMapper;
import org.jeecg.modules.online.cgreport.service.IOnlCgreportItemService;
import org.jeecg.modules.online.cgreport.service.IOnlCgreportParamService;
import org.jeecg.modules.online.cgreport.util.SqlUtil;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.params.ExcelExportEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: OnlCgreportAPI.java */
@RequestMapping({"/online/cgreport/api"})
@RestController("onlCgreportAPI")
/* loaded from: hibernate-common-ol-5.4.74(2).jar:org/jeecg/modules/online/cgreport/a/a.class */
public class a {
    private static final Logger a = LoggerFactory.getLogger(a.class);
    @Autowired
    private org.jeecg.modules.online.cgreport.service.IOnlCgreportHeadService onlCgreportHeadService;
    @Autowired
    private IOnlCgreportItemService onlCgreportItemService;
    @Autowired
    private ISysBaseAPI sysBaseAPI;
    @Autowired
    private IOnlCgreportParamService onlCgreportParamService;

    @GetMapping({"/getColumnsAndData/{code}"})
    @PermissionData
    public Result<?> a(@PathVariable("code") String str, HttpServletRequest httpServletRequest) {
        OnlCgreportHead onlCgreportHead = (OnlCgreportHead) this.onlCgreportHeadService.getById(str);
        if (onlCgreportHead == null) {
            return Result.error("实体不存在");
        }
        Result<?> b = b(str, httpServletRequest);
        if (b.getCode().equals(200)) {
            JSONArray jSONArray = JSON.parseObject(JSONObject.toJSONString(b.getResult())).getJSONArray("records");
            Map<String, Object> queryColumnInfo = this.onlCgreportHeadService.queryColumnInfo(str, false);
            JSONArray jSONArray2 = (JSONArray) queryColumnInfo.get("columns");
            HashMap hashMap = new HashMap();
            if (jSONArray2 != null) {
                for (int i = 0; i < jSONArray2.size(); i++) {
                    JSONObject jSONObject = jSONArray2.getJSONObject(i);
                    Object obj = jSONObject.get("dictCode");
                    if (obj != null) {
                        String obj2 = obj.toString();
                        String string = jSONArray2.getJSONObject(i).getString("dataIndex");
                        List<DictModel> queryColumnDict = this.onlCgreportHeadService.queryColumnDict(obj2, jSONArray, string);
                        if (queryColumnDict != null) {
                            hashMap.put(string, queryColumnDict);
                            jSONObject.put("customRender", string);
                        }
                    }
                }
            }
            queryColumnInfo.put("cgreportHeadName", onlCgreportHead.getName());
            queryColumnInfo.put("data", b.getResult());
            queryColumnInfo.put("dictOptions", hashMap);
            return Result.ok(queryColumnInfo);
        }
        return b;
    }

    @GetMapping({"/getColumns/{code}"})
    @Deprecated
    public Result<?> a(@PathVariable("code") String str) {
        OnlCgreportHead onlCgreportHead = (OnlCgreportHead) this.onlCgreportHeadService.getById(str);
        if (onlCgreportHead == null) {
            return Result.error("实体不存在");
        }
        LambdaQueryWrapper queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq("cgrhead_id", str);
        queryWrapper.eq("is_show", 1);
        queryWrapper.orderByAsc("order_num");
        List<OnlCgreportItem> list = this.onlCgreportItemService.list(queryWrapper);
        ArrayList arrayList = new ArrayList();
        HashMap hashMap = new HashMap();
        for (OnlCgreportItem onlCgreportItem : list) {
            HashMap hashMap2 = new HashMap(3);
            hashMap2.put("title", onlCgreportItem.getFieldTxt());
            hashMap2.put("dataIndex", onlCgreportItem.getFieldName());
            hashMap2.put("align", org.jeecg.modules.online.cgform.d.b.aw);
            hashMap2.put("sorter", "true");
            arrayList.add(hashMap2);
            String dictCode = onlCgreportItem.getDictCode();
            if (oConvertUtils.isNotEmpty(dictCode)) {
                List list2 = null;
                if (dictCode.toLowerCase().indexOf("select ") == 0) {
                    List<Map<?, ?>> executeSelete = ((OnlCgreportHeadMapper) this.onlCgreportHeadService.getBaseMapper()).executeSelete(dictCode);
                    if (executeSelete != null && executeSelete.size() != 0) {
                        list2 = JSON.parseArray(JSON.toJSONString(executeSelete), DictModel.class);
                    }
                } else {
                    list2 = this.sysBaseAPI.queryDictItemsByCode(dictCode);
                }
                if (list2 != null) {
                    hashMap.put(onlCgreportItem.getFieldName(), list2);
                    hashMap2.put("customRender", onlCgreportItem.getFieldName());
                }
            }
        }
        HashMap hashMap3 = new HashMap(1);
        hashMap3.put("columns", arrayList);
        hashMap3.put("dictOptions", hashMap);
        hashMap3.put("cgreportHeadName", onlCgreportHead.getName());
        return Result.ok(hashMap3);
    }

    @GetMapping({"/getData/{code}"})
    @PermissionData
    public Result<?> b(@PathVariable("code") String str, HttpServletRequest httpServletRequest) {
        Map<String, Object> executeSelectSql;
        OnlCgreportHead onlCgreportHead = (OnlCgreportHead) this.onlCgreportHeadService.getById(str);
        if (onlCgreportHead == null) {
            return Result.error("实体不存在");
        }
        String trim = onlCgreportHead.getCgrSql().trim();
        String dbSource = onlCgreportHead.getDbSource();
        try {
            Map<String, Object> a2 = SqlUtil.a(httpServletRequest);
            a2.put("getAll", httpServletRequest.getAttribute("getAll"));
            if (StringUtils.isNotBlank(dbSource)) {
                a.debug("Online报表: 走了多数据源逻辑");
                executeSelectSql = this.onlCgreportHeadService.executeSelectSqlDynamic(dbSource, trim, a2, onlCgreportHead.getId());
            } else {
                a.debug("Online报表: 走了稳定逻辑");
                executeSelectSql = this.onlCgreportHeadService.executeSelectSql(trim, onlCgreportHead.getId(), a2);
            }
            return Result.ok(executeSelectSql);
        } catch (Exception e) {
            a.error(e.getMessage(), e);
            return Result.error("SQL执行失败：" + e.getMessage());
        }
    }

    @GetMapping({"/getQueryInfo/{code}"})
    public Result<?> b(@PathVariable("code") String str) {
        try {
            return Result.ok(this.onlCgreportItemService.getAutoListQueryInfo(str));
        } catch (Exception e) {
            a.info(e.getMessage(), e);
            return Result.error("查询失败");
        }
    }

    @GetMapping({"/getParamsInfo/{code}"})
    public Result<?> c(@PathVariable("code") String str) {
        try {
            LambdaQueryWrapper<OnlCgreportParam> lambdaQueryWrapper = new LambdaQueryWrapper();
            lambdaQueryWrapper.eq(OnlCgreportParam::getCgrheadId, str);
            return Result.ok(this.onlCgreportParamService.list(lambdaQueryWrapper));
        } catch (Exception e) {
            a.info(e.getMessage(), e);
            return Result.error("查询失败");
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v157, types: [java.util.List] */
    @RequestMapping({"/exportXls/{reportId}"})
    @PermissionData
    public void a(@PathVariable("reportId") String str, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        if (oConvertUtils.isNotEmpty(str)) {
            try {
                List list = (List) this.onlCgreportHeadService.queryCgReportConfig(str).get(org.jeecg.modules.online.cgreport.b.a.b);
                httpServletRequest.setAttribute("getAll", true);
                Result<?> b = b(str, httpServletRequest);
                List<Map> list2 = null;
                if (b.getCode().equals(200)) {
                    list2 = (List) ((Map) b.getResult()).get("records");
                }
                ArrayList<String> arrayList = new ArrayList();
                HashMap hashMap = new HashMap();
                HashMap<String,Object> hashMap2 = new HashMap();
                ArrayList<ExcelExportEntity> arrayList2 = new ArrayList();
                for (int i = 0; i < list.size(); i++) {
                    if ("1".equals(oConvertUtils.getString(((Map) list.get(i)).get("is_show")))) {
                        String obj = ((Map) list.get(i)).get(org.jeecg.modules.online.cgreport.b.a.o).toString();
                        ExcelExportEntity excelExportEntity = new ExcelExportEntity(((Map) list.get(i)).get("field_txt").toString(), obj, 15);
                        List<DictModel> queryColumnDict = this.onlCgreportHeadService.queryColumnDict(oConvertUtils.getString(((Map) list.get(i)).get(org.jeecg.modules.online.cgreport.b.a.m)), JSONObject.parseArray(JSONObject.toJSONString(list2)), obj);
                        if (queryColumnDict != null && queryColumnDict.size() > 0) {
                            ArrayList arrayList3 = new ArrayList();
                            for (DictModel dictModel : queryColumnDict) {
                                arrayList3.add(dictModel.getText() + "_" + dictModel.getValue());
                            }
                            excelExportEntity.setReplace((String[]) arrayList3.toArray(new String[arrayList3.size()]));
                        }
                        Object obj2 = ((Map) list.get(i)).get("replace_val");
                        if (oConvertUtils.isNotEmpty(obj2)) {
                            excelExportEntity.setReplace(obj2.toString().split(org.jeecg.modules.online.cgform.d.b.DOT_STRING));
                        }
                        if (oConvertUtils.isNotEmpty(((Map) list.get(i)).get("group_title"))) {
                            String obj3 = ((Map) list.get(i)).get("group_title").toString();
                            List arrayList4 = new ArrayList();
                            if (hashMap2.containsKey(obj3)) {
                                arrayList4 = (List) hashMap2.get(obj3);
                                arrayList4.add(obj);
                            } else {
                                arrayList2.add(new ExcelExportEntity(obj3, obj3, true));
                                arrayList4.add(obj);
                            }
                            hashMap2.put(obj3, arrayList4);
                            excelExportEntity.setColspan(true);
                        }
                        arrayList2.add(excelExportEntity);
                    }
                    if ("1".equals(oConvertUtils.getString(((Map) list.get(i)).get("is_total")))) {
                        arrayList.add(((Map) list.get(i)).get(org.jeecg.modules.online.cgreport.b.a.o).toString());
                    }
                }
                for (Map.Entry entry : hashMap2.entrySet()) {
                    String str2 = (String) entry.getKey();
                    List list3 = (List) entry.getValue();
                    for (ExcelExportEntity excelExportEntity2 : arrayList2) {
                        if (str2.equals(excelExportEntity2.getName()) && excelExportEntity2.isColspan()) {
                            excelExportEntity2.setSubColumnList(list3);
                        }
                    }
                }
                if (arrayList != null && arrayList.size() > 0) {
                    BigDecimal bigDecimal = new BigDecimal(0.0d);
                    for (String str3 : arrayList) {
                        for (Map map : list2) {
                            String obj4 = map.get(str3).toString();
                            if (obj4.matches("\\d+(.\\d+)?")) {
                                bigDecimal = bigDecimal.add(new BigDecimal(obj4));
                            }
                        }
                        hashMap.put(str3, bigDecimal);
                    }
                    list2.add(hashMap);
                }
                httpServletResponse.setContentType("application/vnd.ms-excel");
                OutputStream outputStream = null;
                try {
                    if ("MSIE".equalsIgnoreCase(BrowserUtils.checkBrowse(httpServletRequest).substring(0, 4))) {
                        httpServletResponse.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode("报表", "UTF-8") + ".xls");
                    } else {
                        httpServletResponse.setHeader("content-disposition", "attachment;filename=" + new String("报表".getBytes("UTF-8"), "ISO8859-1") + ".xls");
                    }
                    Workbook exportExcel = ExcelExportUtil.exportExcel(new ExportParams((String) null, "导出信息"), (List<ExcelExportEntity>)arrayList2, (List)list2);
                    outputStream = httpServletResponse.getOutputStream();
                    exportExcel.write(outputStream);
                    try {
                        outputStream.flush();
                        outputStream.close();
                        return;
                    } catch (Exception e) {
                        return;
                    }
                } catch (Exception e2) {
                    try {
                        outputStream.flush();
                        outputStream.close();
                        return;
                    } catch (Exception e3) {
                        return;
                    }
                } catch (Throwable th) {
                    try {
                        outputStream.flush();
                        outputStream.close();
                    } catch (Exception e4) {
                    }
                    throw th;
                }
            } catch (Exception e5) {
                throw new JeecgBootException("动态报表配置不存在!");
            }
        }
        throw new JeecgBootException("参数错误");
    }

    @GetMapping({"/getRpColumns/{code}"})
    public Result<?> d(@PathVariable("code") String str) {
        LambdaQueryWrapper<OnlCgreportHead> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(OnlCgreportHead::getCode, str);
        OnlCgreportHead onlCgreportHead = (OnlCgreportHead) this.onlCgreportHeadService.getOne(lambdaQueryWrapper);
        if (onlCgreportHead == null) {
            return Result.error("实体不存在");
        }
        Map<String, Object> queryColumnInfo = this.onlCgreportHeadService.queryColumnInfo(onlCgreportHead.getId(), true);
        queryColumnInfo.put("cgRpConfigId", onlCgreportHead.getId());
        queryColumnInfo.put("cgRpConfigName", onlCgreportHead.getName());
        return Result.ok(queryColumnInfo);
    }

    @PostMapping({"/testConnection"})
    public Result a(@RequestBody DynamicDataSourceModel dynamicDataSourceModel) {
        Connection connection = null;
        try {
            try {
                Class.forName(dynamicDataSourceModel.getDbDriver());
                Connection connection2 = DriverManager.getConnection(dynamicDataSourceModel.getDbUrl(), dynamicDataSourceModel.getDbUsername(), dynamicDataSourceModel.getDbPassword());
                if (connection2 != null) {
                    Result ok = Result.ok("数据库连接成功");
                    if (connection2 != null) {
                        try {
                            if (!connection2.isClosed()) {
                                connection2.close();
                            }
                        } catch (SQLException e) {
                            a.error(e.toString());
                        }
                    }
                    return ok;
                }
                Result ok2 = Result.ok("数据库连接失败：错误未知");
                if (connection2 != null) {
                    try {
                        if (!connection2.isClosed()) {
                            connection2.close();
                        }
                    } catch (SQLException e2) {
                        a.error(e2.toString());
                    }
                }
                return ok2;
            } catch (ClassNotFoundException e3) {
                a.error(e3.toString());
                Result error = Result.error("数据库连接失败：驱动类不存在");
                if (0 != 0) {
                    try {
                        if (!connection.isClosed()) {
                            connection.close();
                        }
                    } catch (SQLException e4) {
                        a.error(e4.toString());
                        return error;
                    }
                }
                return error;
            } catch (Exception e5) {
                a.error(e5.toString());
                Result error2 = Result.error("数据库连接失败：" + e5.getMessage());
                if (0 != 0) {
                    try {
                        if (!connection.isClosed()) {
                            connection.close();
                        }
                    } catch (SQLException e6) {
                        a.error(e6.toString());
                        return error2;
                    }
                }
                return error2;
            }
        } catch (Throwable th) {
            if (0 != 0) {
                try {
                    if (!connection.isClosed()) {
                        connection.close();
                    }
                } catch (SQLException e7) {
                    a.error(e7.toString());
                    throw th;
                }
            }
            throw th;
        }
    }

    @GetMapping({"/getReportDictList"})
    public Result<?> a(@RequestParam("sql") String str, @RequestParam(name = "keyword", required = false) String str2) {
        return Result.ok(this.onlCgreportHeadService.queryDictSelectData(str, str2));
    }
}
