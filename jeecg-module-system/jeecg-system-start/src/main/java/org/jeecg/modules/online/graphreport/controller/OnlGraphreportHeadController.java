package org.jeecg.modules.online.graphreport.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang.StringUtil;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.online.graphreport.entity.OnlGraphreportHead;
import org.jeecg.modules.online.graphreport.entity.OnlGraphreportItem;
import org.jeecg.modules.online.graphreport.page.OnlGraphreportHeadPage;
import org.jeecg.modules.online.graphreport.service.IOnlGraphreportHeadService;
import org.jeecg.modules.online.graphreport.service.IOnlGraphreportItemService;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

/* compiled from: OnlGraphreportHeadController.java */
@RequestMapping({"/online/graphreport/head"})
@RestController("onlGraphreportHeadController")
/* renamed from: org.jeecg.modules.online.graphreport.a.b */
/* loaded from: hibernate-common-ol-5.4.74.jar:org/jeecg/modules/online/graphreport/a/b.class */
public class OnlGraphreportHeadController {

    /* renamed from: a */
    private static final Logger f2a = LoggerFactory.getLogger(OnlGraphreportHeadController.class);
    @Autowired
    private IOnlGraphreportHeadService onlGraphreportHeadService;
    @Autowired
    private IOnlGraphreportItemService onlGraphreportItemService;

    @GetMapping({"/list"})
    /* renamed from: a */
    public Result<IPage<OnlGraphreportHead>> m21a(OnlGraphreportHead onlGraphreportHead, @RequestParam(name = "pageNo", defaultValue = "1") Integer num, @RequestParam(name = "pageSize", defaultValue = "10") Integer num2, HttpServletRequest httpServletRequest) {
        Result<IPage<OnlGraphreportHead>> result = new Result<>();
        Wrapper initQueryWrapper = QueryGenerator.initQueryWrapper(onlGraphreportHead, httpServletRequest.getParameterMap());
        IPage page = this.onlGraphreportHeadService.page(new Page((long) num.intValue(), (long) num2.intValue()), initQueryWrapper);
        result.setSuccess(true);
        result.setResult(page);
        return result;
    }

    @PostMapping({"/add"})
    /* renamed from: a */
    public Result<OnlGraphreportHead> m20a(@RequestBody OnlGraphreportHeadPage onlGraphreportHeadPage) {
        Result<OnlGraphreportHead> result = new Result<>();
        try {
            OnlGraphreportHead onlGraphreportHead = new OnlGraphreportHead();
            BeanUtils.copyProperties(onlGraphreportHeadPage, onlGraphreportHead);
            f2a.info("onlGraphreportHeadPage:" + onlGraphreportHeadPage);
            this.onlGraphreportHeadService.saveMain(onlGraphreportHead, onlGraphreportHeadPage.getOnlGraphreportItemList());
            result.success("添加成功！");
        } catch (DuplicateKeyException e) {
            result.error500("触发唯一键约束");
        } catch (Exception e2) {
            f2a.error(e2.getMessage(), e2);
            result.error500("操作失败");
        }
        return result;
    }

    @PutMapping({"/edit"})
    /* renamed from: b */
    public Result<OnlGraphreportHead> m17b(@RequestBody OnlGraphreportHeadPage onlGraphreportHeadPage) {
        Result<OnlGraphreportHead> result = new Result<>();
        OnlGraphreportHead onlGraphreportHead = new OnlGraphreportHead();
        BeanUtils.copyProperties(onlGraphreportHeadPage, onlGraphreportHead);
        if (((OnlGraphreportHead) this.onlGraphreportHeadService.getById(onlGraphreportHead.getId())) == null) {
            result.error500("未找到对应实体");
        } else {
            this.onlGraphreportHeadService.updateById(onlGraphreportHead);
            this.onlGraphreportHeadService.updateMain(onlGraphreportHead, onlGraphreportHeadPage.getOnlGraphreportItemList());
            result.success("修改成功!");
        }
        return result;
    }

    @DeleteMapping({"/delete"})
    /* renamed from: a */
    public Result<OnlGraphreportHead> m23a(@RequestParam(name = "id", required = true) String str) {
        Result<OnlGraphreportHead> result = new Result<>();
        if (((OnlGraphreportHead) this.onlGraphreportHeadService.getById(str)) == null) {
            result.error500("未找到对应实体");
        } else {
            this.onlGraphreportHeadService.delMain(str);
            result.success("删除成功!");
        }
        return result;
    }

    @DeleteMapping({"/deleteBatch"})
    /* renamed from: b */
    public Result<OnlGraphreportHead> m19b(@RequestParam(name = "ids", required = true) String str) {
        Result<OnlGraphreportHead> result = new Result<>();
        if (str == null || "".equals(str.trim())) {
            result.error500("参数不识别！");
        } else {
            this.onlGraphreportHeadService.delBatchMain(Arrays.asList(str.split(",")));
            result.success("删除成功!");
        }
        return result;
    }

    @GetMapping({"/queryById"})
    /* renamed from: c */
    public Result<OnlGraphreportHead> m16c(@RequestParam(name = "id", required = true) String str) {
        Result<OnlGraphreportHead> result = new Result<>();
        OnlGraphreportHead onlGraphreportHead = (OnlGraphreportHead) this.onlGraphreportHeadService.getById(str);
        if (onlGraphreportHead == null) {
            result.error500("未找到对应实体");
        } else {
            result.setResult(onlGraphreportHead);
            result.setSuccess(true);
        }
        return result;
    }

    @GetMapping({"/queryOnlGraphreportItemByMainId"})
    /* renamed from: d */
    public Result<List<OnlGraphreportItem>> m15d(@RequestParam(name = "id", required = true) String str) {
        Result<List<OnlGraphreportItem>> result = new Result<>();
        result.setResult(this.onlGraphreportItemService.selectByMainId(str));
        result.setSuccess(true);
        return result;
    }

    @RequestMapping({"/exportXls"})
    /* renamed from: a */
    public ModelAndView m22a(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Wrapper<OnlGraphreportHead> wrapper = null;
        try {
            String parameter = httpServletRequest.getParameter("paramsStr");
            if (StringUtil.isNotEmpty(parameter)) {
                wrapper = QueryGenerator.initQueryWrapper((OnlGraphreportHead) JSON.parseObject(URLDecoder.decode(parameter, "UTF-8"), OnlGraphreportHead.class), httpServletRequest.getParameterMap());
            }
        } catch (UnsupportedEncodingException e) {
            f2a.error(e.getMessage(), e);
        }
        ModelAndView modelAndView = new ModelAndView(new JeecgEntityExcelView());
        ArrayList arrayList = new ArrayList();
        for (OnlGraphreportHead onlGraphreportHead : this.onlGraphreportHeadService.list(wrapper)) {
            OnlGraphreportHeadPage onlGraphreportHeadPage = new OnlGraphreportHeadPage();
            BeanUtils.copyProperties(onlGraphreportHead, onlGraphreportHeadPage);
            onlGraphreportHeadPage.setOnlGraphreportItemList(this.onlGraphreportItemService.selectByMainId(onlGraphreportHead.getId()));
            arrayList.add(onlGraphreportHeadPage);
        }
        modelAndView.addObject("fileName", "图表报告列表");
        modelAndView.addObject("entity", OnlGraphreportHeadPage.class);
        modelAndView.addObject("params", new ExportParams("图表报告列表数据", "导出人:Jeecg", "导出信息"));
        modelAndView.addObject("data", arrayList);
        return modelAndView;
    }

    @RequestMapping(value = {"/importExcel"}, method = {RequestMethod.POST})
    /* renamed from: b */
    public Result<?> m18b(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Iterator it = ((MultipartHttpServletRequest) httpServletRequest).getFileMap().entrySet().iterator();
        if (!it.hasNext()) {
            return Result.error("文件导入失败！");
        }
        MultipartFile multipartFile = (MultipartFile) ((Map.Entry) it.next()).getValue();
        ImportParams importParams = new ImportParams();
        importParams.setTitleRows(2);
        importParams.setHeadRows(1);
        importParams.setNeedSave(true);
        try {
            try {
                List<OnlGraphreportHeadPage> importExcel = ExcelImportUtil.importExcel(multipartFile.getInputStream(), OnlGraphreportHeadPage.class, importParams);
                for (OnlGraphreportHeadPage onlGraphreportHeadPage : importExcel) {
                    OnlGraphreportHead onlGraphreportHead = new OnlGraphreportHead();
                    BeanUtils.copyProperties(onlGraphreportHeadPage, onlGraphreportHead);
                    this.onlGraphreportHeadService.saveMain(onlGraphreportHead, onlGraphreportHeadPage.getOnlGraphreportItemList());
                }
                Result<?> ok = Result.ok("文件导入成功！数据行数：" + importExcel.size());
                try {
                    multipartFile.getInputStream().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return ok;
            } catch (Exception e2) {
                f2a.error(e2.getMessage(), e2);
                Result<?> error = Result.error("文件导入失败！");
                try {
                    multipartFile.getInputStream().close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
                return error;
            }
        } catch (Throwable th) {
            try {
                multipartFile.getInputStream().close();
            } catch (IOException e4) {
                e4.printStackTrace();
            }
            throw th;
        }
    }
}
