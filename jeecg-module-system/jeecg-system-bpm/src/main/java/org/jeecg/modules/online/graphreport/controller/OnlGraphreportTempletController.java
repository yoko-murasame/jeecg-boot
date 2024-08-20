package org.jeecg.modules.online.graphreport.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.online.graphreport.entity.OnlGraphreportTemplet;
import org.jeecg.modules.online.graphreport.entity.OnlGraphreportTempletItem;
import org.jeecg.modules.online.graphreport.page.OnlGraphreportTempletPage;
import org.jeecg.modules.online.graphreport.service.IOnlGraphreportTempletItemService;
import org.jeecg.modules.online.graphreport.service.IOnlGraphreportTempletService;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

/* compiled from: OnlGraphreportTempletController.java */
@RequestMapping({"/online/graphreport/templet"})
@RestController("onlGraphreportTempletController")
/* renamed from: org.jeecg.modules.online.graphreport.a.c */
/* loaded from: hibernate-common-ol-5.4.74.jar:org/jeecg/modules/online/graphreport/a/c.class */
public class OnlGraphreportTempletController {

    /* renamed from: a */
    private static final Logger f3a = LoggerFactory.getLogger(OnlGraphreportTempletController.class);
    @Autowired
    private IOnlGraphreportTempletService onlGraphreportTempletService;
    @Autowired
    private IOnlGraphreportTempletItemService onlGraphreportTempletItemService;

    @GetMapping({"/list"})
    /* renamed from: a */
    public Result<IPage<OnlGraphreportTemplet>> m12a(OnlGraphreportTemplet onlGraphreportTemplet, @RequestParam(name = "pageNo", defaultValue = "1") Integer num, @RequestParam(name = "pageSize", defaultValue = "10") Integer num2, HttpServletRequest httpServletRequest) {
        Result<IPage<OnlGraphreportTemplet>> result = new Result<>();
        Wrapper initQueryWrapper = QueryGenerator.initQueryWrapper(onlGraphreportTemplet, httpServletRequest.getParameterMap());
        IPage page = this.onlGraphreportTempletService.page(new Page((long) num.intValue(), (long) num2.intValue()), initQueryWrapper);
        result.setSuccess(true);
        result.setResult(page);
        return result;
    }

    @PostMapping({"/add"})
    /* renamed from: a */
    public Result<OnlGraphreportTemplet> m11a(@RequestBody OnlGraphreportTempletPage onlGraphreportTempletPage) {
        Result<OnlGraphreportTemplet> result = new Result<>();
        try {
            OnlGraphreportTemplet onlGraphreportTemplet = new OnlGraphreportTemplet();
            BeanUtils.copyProperties(onlGraphreportTempletPage, onlGraphreportTemplet);
            this.onlGraphreportTempletService.saveMain(onlGraphreportTemplet, onlGraphreportTempletPage.getOnlGraphreportTempletItemList());
            result.success("添加成功！");
        } catch (Exception e) {
            f3a.error(e.getMessage(), e);
            result.error500("操作失败");
        }
        return result;
    }

    @PutMapping({"/edit"})
    /* renamed from: b */
    public Result<OnlGraphreportTemplet> m8b(@RequestBody OnlGraphreportTempletPage onlGraphreportTempletPage) {
        Result<OnlGraphreportTemplet> result = new Result<>();
        OnlGraphreportTemplet onlGraphreportTemplet = new OnlGraphreportTemplet();
        BeanUtils.copyProperties(onlGraphreportTempletPage, onlGraphreportTemplet);
        if (((OnlGraphreportTemplet) this.onlGraphreportTempletService.getById(onlGraphreportTemplet.getId())) == null) {
            result.error500("未找到对应实体");
        } else {
            this.onlGraphreportTempletService.updateById(onlGraphreportTemplet);
            this.onlGraphreportTempletService.updateMain(onlGraphreportTemplet, onlGraphreportTempletPage.getOnlGraphreportTempletItemList());
            result.success("修改成功!");
        }
        return result;
    }

    @DeleteMapping({"/delete"})
    /* renamed from: a */
    public Result<OnlGraphreportTemplet> m14a(@RequestParam(name = "id", required = true) String str) {
        Result<OnlGraphreportTemplet> result = new Result<>();
        if (((OnlGraphreportTemplet) this.onlGraphreportTempletService.getById(str)) == null) {
            result.error500("未找到对应实体");
        } else {
            this.onlGraphreportTempletService.delMain(str);
            result.success("删除成功!");
        }
        return result;
    }

    @DeleteMapping({"/deleteBatch"})
    /* renamed from: b */
    public Result<OnlGraphreportTemplet> m10b(@RequestParam(name = "ids", required = true) String str) {
        Result<OnlGraphreportTemplet> result = new Result<>();
        if (str == null || "".equals(str.trim())) {
            result.error500("参数不识别！");
        } else {
            this.onlGraphreportTempletService.delBatchMain(Arrays.asList(str.split(",")));
            result.success("删除成功!");
        }
        return result;
    }

    @GetMapping({"/queryById"})
    /* renamed from: c */
    public Result<OnlGraphreportTemplet> m7c(@RequestParam(name = "id", required = true) String str) {
        Result<OnlGraphreportTemplet> result = new Result<>();
        OnlGraphreportTemplet onlGraphreportTemplet = (OnlGraphreportTemplet) this.onlGraphreportTempletService.getById(str);
        if (onlGraphreportTemplet == null) {
            result.error500("未找到对应实体");
        } else {
            result.setResult(onlGraphreportTemplet);
            result.setSuccess(true);
        }
        return result;
    }

    @GetMapping({"/queryTempletItemByMainId"})
    /* renamed from: d */
    public Result<List<OnlGraphreportTempletItem>> m6d(@RequestParam(name = "id", required = true) String str) {
        Result<List<OnlGraphreportTempletItem>> result = new Result<>();
        result.setResult(this.onlGraphreportTempletItemService.selectByMainId(str));
        result.setSuccess(true);
        return result;
    }

    @RequestMapping({"/exportXls"})
    /* renamed from: a */
    public ModelAndView m13a(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Wrapper<OnlGraphreportTemplet> wrapper = null;
        try {
            String parameter = httpServletRequest.getParameter("paramsStr");
            if (oConvertUtils.isNotEmpty(parameter)) {
                wrapper = QueryGenerator.initQueryWrapper((OnlGraphreportTemplet) JSON.parseObject(URLDecoder.decode(parameter, "UTF-8"), OnlGraphreportTemplet.class), httpServletRequest.getParameterMap());
            }
        } catch (UnsupportedEncodingException e) {
            f3a.error(e.getMessage(), e);
        }
        ModelAndView modelAndView = new ModelAndView(new JeecgEntityExcelView());
        ArrayList arrayList = new ArrayList();
        for (OnlGraphreportTemplet onlGraphreportTemplet : this.onlGraphreportTempletService.list(wrapper)) {
            OnlGraphreportTempletPage onlGraphreportTempletPage = new OnlGraphreportTempletPage();
            BeanUtils.copyProperties(onlGraphreportTemplet, onlGraphreportTempletPage);
            onlGraphreportTempletPage.setOnlGraphreportTempletItemList(this.onlGraphreportTempletItemService.selectByMainId(onlGraphreportTemplet.getId()));
            arrayList.add(onlGraphreportTempletPage);
        }
        modelAndView.addObject("fileName", "Online报表多数据源主表列表");
        modelAndView.addObject("entity", OnlGraphreportTempletPage.class);
        modelAndView.addObject("params", new ExportParams("Online报表多数据源主表列表数据", "导出人:Jeecg", "导出信息"));
        modelAndView.addObject("data", arrayList);
        return modelAndView;
    }

    @RequestMapping(value = {"/importExcel"}, method = {RequestMethod.POST})
    /* renamed from: b */
    public Result<?> m9b(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
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
                List<OnlGraphreportTempletPage> importExcel = ExcelImportUtil.importExcel(multipartFile.getInputStream(), OnlGraphreportTempletPage.class, importParams);
                for (OnlGraphreportTempletPage onlGraphreportTempletPage : importExcel) {
                    OnlGraphreportTemplet onlGraphreportTemplet = new OnlGraphreportTemplet();
                    BeanUtils.copyProperties(onlGraphreportTempletPage, onlGraphreportTemplet);
                    this.onlGraphreportTempletService.saveMain(onlGraphreportTemplet, onlGraphreportTempletPage.getOnlGraphreportTempletItemList());
                }
                Result<?> ok = Result.ok("文件导入成功！数据行数：" + importExcel.size());
                try {
                    multipartFile.getInputStream().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return ok;
            } catch (Exception e2) {
                f3a.error(e2.getMessage(), e2);
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
