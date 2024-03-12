package org.jeecg.modules.online.cgreport.a;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.SqlInjectionUtil;
import org.jeecg.modules.base.service.BaseCommonService;
import org.jeecg.modules.online.cgreport.entity.OnlCgreportHead;
import org.jeecg.modules.online.cgreport.entity.OnlCgreportItem;
import org.jeecg.modules.online.cgreport.entity.OnlCgreportParam;
import org.jeecg.modules.online.cgreport.model.OnlCgreportModel;
import org.jeecg.modules.online.cgreport.service.IOnlCgreportHeadService;
import org.jeecg.modules.online.cgreport.service.IOnlCgreportItemService;
import org.jeecg.modules.online.cgreport.service.IOnlCgreportParamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* compiled from: OnlCgreportHeadController.java */
@RequestMapping({"/online/cgreport/head"})
@RestController("onlCgreportHeadController")
/* loaded from: hibernate-common-ol-5.4.74(2).jar:org/jeecg/modules/online/cgreport/a/b.class */
public class b {
    private static final Logger a = LoggerFactory.getLogger(b.class);
    @Autowired
    private ISysBaseAPI sysBaseAPI;
    @Autowired
    private IOnlCgreportHeadService onlCgreportHeadService;
    @Autowired
    private IOnlCgreportParamService onlCgreportParamService;
    @Autowired
    private IOnlCgreportItemService onlCgreportItemService;
    @Autowired
    private BaseCommonService baseCommonService;

    @GetMapping({"/parseSql"})
    public Result<?> a(@RequestParam(name = "sql") String str, @RequestParam(name = "dbKey", required = false) String str2) {
        String str3;
        if (StringUtils.isNotBlank(str2) && this.sysBaseAPI.getDynamicDbSourceByCode(str2) == null) {
            return Result.error("数据源不存在");
        }
        HashMap hashMap = new HashMap();
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        try {
            a.info("Online报表，sql解析：" + str);
            this.baseCommonService.addLog("Online报表，sql解析：" + str, 2, 2);
            SqlInjectionUtil.specialFilterContentForOnlineReport(str);
            List<String> sqlFields = this.onlCgreportHeadService.getSqlFields(str, str2);
            List<String> sqlParams = this.onlCgreportHeadService.getSqlParams(str);
            int i = 1;
            for (String str4 : sqlFields) {
                OnlCgreportItem onlCgreportItem = new OnlCgreportItem();
                onlCgreportItem.setFieldName(str4.toLowerCase());
                onlCgreportItem.setFieldTxt(str4);
                onlCgreportItem.setIsShow(1);
                onlCgreportItem.setOrderNum(Integer.valueOf(i));
                onlCgreportItem.setId(org.jeecg.modules.online.cgform.d.b.a());
                onlCgreportItem.setFieldType(org.jeecg.modules.online.cgreport.b.a.z);
                arrayList.add(onlCgreportItem);
                i++;
            }
            for (String str5 : sqlParams) {
                OnlCgreportParam onlCgreportParam = new OnlCgreportParam();
                onlCgreportParam.setParamName(str5);
                onlCgreportParam.setParamTxt(str5);
                arrayList2.add(onlCgreportParam);
            }
            hashMap.put("fields", arrayList);
            hashMap.put(org.jeecg.modules.online.cgreport.b.a.c, arrayList2);
            return Result.ok(hashMap);
        } catch (Exception e) {
            a.error(e.getMessage(), e);
            if (e.getMessage().indexOf("Connection refused: connect") != -1) {
                str3 = "解析失败，数据源连接失败.";
            } else {
                str3 = e.getMessage().indexOf("值可能存在SQL注入风险") != -1 ? "解析失败，SQL可能存在SQL注入风险." : e.getMessage().indexOf("该报表sql没有数据") != -1 ? "解析失败，报表sql查询数据为空，无法解析字段." : e.getMessage().indexOf("SqlServer不支持SQL内排序") != -1 ? "解析失败，SqlServer不支持SQL内排序." : "解析失败，SQL语法错误.";
            }
            return Result.error(str3);
        }
    }

    @GetMapping({"/list"})
    public Result<IPage<OnlCgreportHead>> a(OnlCgreportHead onlCgreportHead, @RequestParam(name = "pageNo", defaultValue = "1") Integer num, @RequestParam(name = "pageSize", defaultValue = "10") Integer num2, HttpServletRequest httpServletRequest) {
        Result<IPage<OnlCgreportHead>> result = new Result<>();
        Wrapper initQueryWrapper = QueryGenerator.initQueryWrapper(onlCgreportHead, httpServletRequest.getParameterMap());
        IPage page = this.onlCgreportHeadService.page(new Page(num.intValue(), num2.intValue()), initQueryWrapper);
        result.setSuccess(true);
        result.setResult(page);
        return result;
    }

    @PostMapping({"/add"})
    public Result<?> a(@RequestBody OnlCgreportModel onlCgreportModel) {
        Result<?> result = new Result<>();
        try {
            String a2 = org.jeecg.modules.online.cgform.d.b.a();
            OnlCgreportHead head = onlCgreportModel.getHead();
            List<OnlCgreportParam> params = onlCgreportModel.getParams();
            List<OnlCgreportItem> items = onlCgreportModel.getItems();
            head.setId(a2);
            for (OnlCgreportParam onlCgreportParam : params) {
                onlCgreportParam.setId(null);
                onlCgreportParam.setCgrheadId(a2);
            }
            for (OnlCgreportItem onlCgreportItem : items) {
                onlCgreportItem.setId(null);
                onlCgreportItem.setFieldName(onlCgreportItem.getFieldName().trim().toLowerCase());
                onlCgreportItem.setCgrheadId(a2);
            }
            this.onlCgreportHeadService.save(head);
            this.onlCgreportParamService.saveBatch(params);
            this.onlCgreportItemService.saveBatch(items);
            result.success("添加成功！");
        } catch (Exception e) {
            a.error(e.getMessage(), e);
            result.error500("操作失败");
        }
        return result;
    }

    @PutMapping({"/editAll"})
    public Result<?> b(@RequestBody OnlCgreportModel onlCgreportModel) {
        try {
            return this.onlCgreportHeadService.editAll(onlCgreportModel);
        } catch (Exception e) {
            a.error(e.getMessage(), e);
            return Result.error("操作失败");
        }
    }

    @DeleteMapping({"/delete"})
    public Result<?> a(@RequestParam(name = "id", required = true) String str) {
        return this.onlCgreportHeadService.delete(str);
    }

    @DeleteMapping({"/deleteBatch"})
    public Result<?> b(@RequestParam(name = "ids", required = true) String str) {
        return this.onlCgreportHeadService.bathDelete(str.split(org.jeecg.modules.online.cgform.d.b.DOT_STRING));
    }

    @GetMapping({"/queryById"})
    public Result<OnlCgreportHead> c(@RequestParam(name = "id", required = true) String str) {
        Result<OnlCgreportHead> result = new Result<>();
        result.setResult((OnlCgreportHead) this.onlCgreportHeadService.getById(str));
        return result;
    }
}
