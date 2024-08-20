package org.jeecg.modules.online.graphreport.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.apache.poi.ss.usermodel.Workbook;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.util.BrowserUtils;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.online.graphreport.entity.OnlGraphreportHead;
import org.jeecg.modules.online.graphreport.entity.OnlGraphreportItem;
import org.jeecg.modules.online.graphreport.service.IOnlGraphreportApiService;
import org.jeecg.modules.online.graphreport.service.IOnlGraphreportHeadService;
import org.jeecg.modules.online.graphreport.util.GraphreportUtil;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.params.ExcelExportEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: OnlGraphreportApiController.java */
@RequestMapping({"/online/graphreport/api"})
@RestController("onlGraphreportApiController")
/* renamed from: org.jeecg.modules.online.graphreport.a.a */
/* loaded from: hibernate-common-ol-5.4.74.jar:org/jeecg/modules/online/graphreport/a/a.class */
public class OnlGraphreportApiController {

    /* renamed from: a */
    private static final Logger f0a = LoggerFactory.getLogger(OnlGraphreportApiController.class);
    @Autowired
    private IOnlGraphreportHeadService headService;
    @Autowired
    private IOnlGraphreportApiService apiService;

    @GetMapping({"/getTempletAllData"})
    /* renamed from: a */
    public Result<?> m27a(@RequestParam(name = "id", required = true) String str) {
        return this.apiService.getTempletChartsData(str);
    }

    @GetMapping({"/getChartsData"})
    /* renamed from: a */
    public Result<?> m26a(@RequestParam(name = "id", required = true) String str, @RequestParam("params") String str2) {
        OnlGraphreportHead onlGraphreportHead = (OnlGraphreportHead) this.headService.getById(str);
        if (onlGraphreportHead == null) {
            return Result.error("实体不存在");
        }
        return Result.ok(this.apiService.queryChartDataSource(onlGraphreportHead, str2));
    }

    @GetMapping({"/getChartsDataByCode"})
    /* renamed from: b */
    public Result<?> m24b(@RequestParam(name = "code", required = true) String str, @RequestParam("params") String str2) {
        OnlGraphreportHead queryByCode = this.headService.queryByCode(str);
        if (queryByCode == null) {
            return Result.error("实体不存在");
        }
        return Result.ok(this.apiService.queryChartDataSource(queryByCode, str2));
    }

    @RequestMapping({"/exportXlsById"})
    /* renamed from: a */
    public void m25a(@RequestParam("id") String str, @RequestParam(value = "name", required = false) String str2, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        List list;
        OnlGraphreportHead onlGraphreportHead = (OnlGraphreportHead) this.headService.getById(str);
        if (onlGraphreportHead == null) {
            throw new JeecgBootException("实体不存在");
        }
        if (oConvertUtils.isEmpty(str2)) {
            str2 = "ExportExcel";
        }
        Map<String, Object> queryChartDataSource = this.apiService.queryChartDataSource(onlGraphreportHead, "");
        if ("json".equals(onlGraphreportHead.getDataType())) {
            list = (List) JSON.parseArray(onlGraphreportHead.getCgrSql()).toJavaObject(new TypeReference<List<Map<String, Object>>>() { // from class: org.jeecg.modules.online.graphreport.a.a.1
            }.getType());
        } else {
            list = (List) queryChartDataSource.get("data");
        }
        List<OnlGraphreportItem> list2 = (List) queryChartDataSource.get("items");
        Map<String, Object> map = (Map) queryChartDataSource.get("dictOptions");
        ArrayList arrayList = new ArrayList();
        HashMap<String, Object> hashMap = new HashMap();
        arrayList.add(new ExcelExportEntity("#", "table_left_num_index", 30));
        for (OnlGraphreportItem onlGraphreportItem : list2) {
            if ("Y".equals(onlGraphreportItem.getIsShow())) {
                String fieldName = onlGraphreportItem.getFieldName();
                arrayList.add(new ExcelExportEntity(onlGraphreportItem.getFieldTxt(), fieldName, 30));
                if ("Y".equals(onlGraphreportItem.getIsTotal())) {
                    hashMap.put(fieldName, Double.valueOf(0.0d));
                }
            }
        }
        for (int i = 0; i < list.size(); i++) {
            Map map2 = (Map) list.get(i);
            map2.put("table_left_num_index", Integer.valueOf(i + 1));
            if (hashMap.size() > 0) {
                for (String str3 : hashMap.keySet()) {
                    try {
                        hashMap.put(str3, Double.valueOf(((Double) hashMap.get(str3)).doubleValue() + Double.parseDouble(String.valueOf(map2.get(str3)))));
                    } catch (Exception e) {
                        f0a.error("导出计算总计时出错：", e);
                        hashMap.remove(str3);
                    }
                }
            }
            if (map.keySet().size() > 0) {
                for (String str4 : map.keySet()) {
                    map2.put(str4, GraphreportUtil.m4c((List) map.get(str4), String.valueOf(map2.get(str4))));
                }
            }
        }
        if (hashMap.size() > 0) {
            HashMap hashMap2 = new HashMap();
            hashMap2.put("table_left_num_index", "总计");
            for (String str5 : hashMap.keySet()) {
                hashMap2.put(str5, hashMap.get(str5));
            }
            list.add(hashMap2);
        }
        OutputStream outputStream = null;
        try {
            Workbook exportExcel = ExcelExportUtil.exportExcel(new ExportParams(str2, str2), arrayList, list);
            if ("MSIE".equalsIgnoreCase(BrowserUtils.checkBrowse(httpServletRequest).substring(0, 4))) {
                httpServletResponse.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(str2, "UTF-8") + ".xls");
            } else {
                httpServletResponse.setHeader("content-disposition", "attachment;filename=" + new String(str2.getBytes(StandardCharsets.UTF_8), "ISO8859-1") + ".xls");
            }
            outputStream = httpServletResponse.getOutputStream();
            exportExcel.write(outputStream);
            if (outputStream != null) {
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (Exception e2) {
                }
            }
        } catch (Exception e3) {
            if (outputStream != null) {
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (Exception e4) {
                }
            }
        } catch (Throwable th) {
            if (outputStream != null) {
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (Exception e5) {
                    throw th;
                }
            }
            throw th;
        }
    }
}
