package org.jeecg.modules.online.graphreport.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.util.RestUtil;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.common.util.TokenUtils;
import org.jeecg.common.util.dynamic.db.DataSourceCachePool;
import org.jeecg.common.util.dynamic.db.DynamicDBUtil;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.online.cgform.d.b;
import org.jeecg.modules.online.graphreport.entity.OnlGraphreportHead;
import org.jeecg.modules.online.graphreport.entity.OnlGraphreportTemplet;
import org.jeecg.modules.online.graphreport.entity.OnlGraphreportTempletItem;
import org.jeecg.modules.online.graphreport.mapper.OnlGraphreportHeadMapper;
import org.jeecg.modules.online.graphreport.service.IOnlGraphreportApiService;
import org.jeecg.modules.online.graphreport.service.IOnlGraphreportHeadService;
import org.jeecg.modules.online.graphreport.service.IOnlGraphreportTempletItemService;
import org.jeecg.modules.online.graphreport.service.IOnlGraphreportTempletService;
import org.jeecg.modules.online.graphreport.util.GraphreportUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/* compiled from: OnlGraphreportApiServiceImpl.java */
@Service("onlGraphreportApiServiceImpl")
/* renamed from: org.jeecg.modules.online.graphreport.service.a.a */
/* loaded from: hibernate-common-ol-5.4.74.jar:org/jeecg/modules/online/graphreport/service/a/a.class */
public class OnlGraphreportApiServiceImpl extends ServiceImpl<OnlGraphreportHeadMapper, OnlGraphreportHead> implements IOnlGraphreportApiService {

    /* renamed from: a */
    private static final Logger f10a = LoggerFactory.getLogger(OnlGraphreportApiServiceImpl.class);
    @Autowired
    private IOnlGraphreportHeadService headService;
    @Autowired
    private IOnlGraphreportTempletService templetService;
    @Autowired
    private IOnlGraphreportTempletItemService templetItemService;

    @Override // org.jeecg.modules.online.graphreport.service.IOnlGraphreportApiService
    public Result<?> getTempletChartsData(String id) {
        HashMap hashMap = new HashMap();
        OnlGraphreportTemplet onlGraphreportTemplet = (OnlGraphreportTemplet) this.templetService.getById(id);
        if (onlGraphreportTemplet == null) {
            return Result.error("未找到对应实体");
        }
        hashMap.put("templet", onlGraphreportTemplet);
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = null;
        Integer num = null;
        String str = null;
        String str2 = null;
        for (OnlGraphreportTempletItem onlGraphreportTempletItem : this.templetItemService.selectByMainId(id).stream().filter(onlGraphreportTempletItem2 -> {
            return onlGraphreportTempletItem2.getIsShow().equals("1");
        }).collect(Collectors.toList())) {
            if (num == null || !onlGraphreportTempletItem.getGroupNum().equals(num)) {
                if (arrayList2 != null) {
                    arrayList.add(m1a(num.intValue(), str, str2, arrayList2));
                }
                arrayList2 = new ArrayList();
                num = onlGraphreportTempletItem.getGroupNum();
                str = onlGraphreportTempletItem.getGroupStyle();
                str2 = onlGraphreportTempletItem.getGroupTxt();
            }
            OnlGraphreportHead queryByCode = this.headService.queryByCode(onlGraphreportTempletItem.getGraphreportCode());
            String graphreportType = onlGraphreportTempletItem.getGraphreportType();
            if (!"normal".equals(graphreportType)) {
                queryByCode.setGraphType(graphreportType);
            }
            arrayList2.add(queryChartDataSource(queryByCode, null));
        }
        if (arrayList2 != null && arrayList2.size() > 0) {
            arrayList.add(m1a(num.intValue(), str, str2, arrayList2));
        }
        hashMap.put("groups", arrayList);
        return Result.ok(hashMap);
    }

    /* renamed from: a */
    private Map<String, Object> m1a(int i, String str, String str2, List<?> list) {
        HashMap hashMap = new HashMap();
        hashMap.put("groupNum", Integer.valueOf(i));
        hashMap.put("groupStyle", str);
        hashMap.put("groupTxt", str2);
        hashMap.put("charts", list);
        return hashMap;
    }

    @Override // org.jeecg.modules.online.graphreport.service.IOnlGraphreportApiService
    public Map<String, Object> queryChartDataSource(OnlGraphreportHead head, String paramString) {
        List<Map<String, Object>> list;
        Map<String, Object> queryChartConfig = this.headService.queryChartConfig(head);
        if ("sql".equals(head.getDataType())) {
            String cgrSql = head.getCgrSql();
            String dbSource = head.getDbSource();
            String a = GraphreportUtil.m5a(cgrSql, paramString);
            if (oConvertUtils.isEmpty(dbSource)) {
                f10a.info("Online图表: 走了稳定逻辑");
                list = this.headService.executeSelete(a);
            } else {
                f10a.info("Online图表: 走了多数据源逻辑");
                DataSourceCachePool.getCacheDynamicDataSourceModel(dbSource);
                list = DynamicDBUtil.findList(dbSource, a, new Object[0]);
            }
            queryChartConfig.put("data", b.d(list));
        } else if ("api".equals(head.getDataType())) {
            String trim = head.getCgrSql().trim();
            if (oConvertUtils.isEmpty(trim)) {
                queryChartConfig.put("data", new ArrayList());
                return queryChartConfig;
            }
            if (!trim.startsWith("http")) {
                if (trim.startsWith("/")) {
                    trim = RestUtil.getBaseUrl() + trim;
                } else {
                    trim = RestUtil.getBaseUrl() + "/" + trim;
                }
            }
            HttpServletRequest httpServletRequest = SpringContextUtils.getHttpServletRequest();
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("X-Access-Token", TokenUtils.getTokenByRequest(httpServletRequest));
            queryChartConfig.put("data", b.d((List) JSONObject.parseObject((String) RestUtil.request(trim, HttpMethod.GET, httpHeaders, (JSONObject) null, (Object) null, String.class).getBody()).get("data")));
        }
        return queryChartConfig;
    }
}
