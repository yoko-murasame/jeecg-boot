package org.jeecg.modules.online.cgform.controller;


import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.aspect.annotation.OnlineAuth;
import org.jeecg.common.aspect.annotation.PermissionData;
import org.jeecg.common.constant.enums.ModuleType;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.system.vo.DictModel;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.BrowserUtils;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.common.util.TokenUtils;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.extbpm.process.entity.ExtActProcessForm;
import org.jeecg.modules.extbpm.process.service.IExtActProcessFormService;
import org.jeecg.modules.online.cgform.CgformDB;
import org.jeecg.modules.online.cgform.CgformDC;
import org.jeecg.modules.online.cgform.d.e;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;
import org.jeecg.modules.online.cgform.entity.OnlCgformHead;
import org.jeecg.modules.online.cgform.model.OnlComplexModel;
import org.jeecg.modules.online.cgform.model.OnlGenerateModel;
import org.jeecg.modules.online.cgform.model.TreeModel;
import org.jeecg.modules.online.cgform.service.IOnlCgformFieldService;
import org.jeecg.modules.online.cgform.service.IOnlCgformHeadService;
import org.jeecg.modules.online.cgform.service.IOnlCgformSqlService;
import org.jeecg.modules.online.cgform.service.IOnlineService;
import org.jeecg.modules.online.config.b.d;
import org.jeecg.modules.online.config.exception.BusinessException;
import org.jeecg.modules.online.config.exception.DBException;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.params.ExcelExportEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.support.incrementer.OracleSequenceMaxValueIncrementer;
import org.springframework.jdbc.support.incrementer.PostgreSQLSequenceMaxValueIncrementer;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

@RestController("onlCgformApiController")
@RequestMapping({"/online/cgform/api"})
public class OnlCgformApiController {
    private static final Logger a = LoggerFactory.getLogger(OnlCgformApiController.class);
    @Autowired
    private IOnlCgformHeadService onlCgformHeadService;
    @Autowired
    private IOnlCgformFieldService onlCgformFieldService;
    @Autowired
    private IOnlCgformSqlService onlCgformSqlService;
    @Autowired
    private ISysBaseAPI sysBaseAPI;
    @Autowired
    private IOnlineService onlineService;
    @Autowired
    private IExtActProcessFormService extActProcessFormService;


    @Value("${jeecg.path.upload}")
    private String upLoadPath;
    @Value("${jeecg.uploadType}")
    private String uploadType;

    public OnlCgformApiController() {
    }

    @PostMapping({"/addAll"})
    public Result<?> a(@RequestBody org.jeecg.modules.online.cgform.model.a var1) {
        try {
            String var2 = var1.getHead().getTableName();
            return d.a(var2) ? Result.error("数据库表[" + var2 + "]已存在,请从数据库导入表单") : this.onlCgformHeadService.addAll(var1);
        } catch (Exception var3) {
            a.error("OnlCgformApiController.addAll()发生异常：" + var3.getMessage(), var3);
            return Result.error("操作失败");
        }
    }

    @PutMapping({"/editAll"})
    public Result<?> b(@RequestBody org.jeecg.modules.online.cgform.model.a var1) {
        try {
            return this.onlCgformHeadService.editAll(var1);
        } catch (Exception var3) {
            a.error("OnlCgformApiController.editAll()发生异常：" + var3.getMessage(), var3);
            return Result.error("操作失败");
        }
    }

    @AutoLog(
            operateType = 1,
            value = "online列表加载",
            module = ModuleType.ONLINE
    )
    @OnlineAuth("getColumns")
    @GetMapping({"/getColumns/{code}"})
    public Result<OnlComplexModel> a(@PathVariable("code") String var1) {
        Result<OnlComplexModel> result = new Result<>();
        OnlCgformHead onlCgformHead = (OnlCgformHead)this.onlCgformHeadService.getById(var1);
        if (onlCgformHead == null) {
            result.error500("实体不存在");
            return result;
        } else {
            LoginUser loginUser = (LoginUser)SecurityUtils.getSubject().getPrincipal();
            OnlComplexModel onlComplexModel = this.onlineService.queryOnlineConfig(onlCgformHead, loginUser.getUsername());
            onlComplexModel.setIsDesForm(onlCgformHead.getIsDesForm());
            onlComplexModel.setDesFormCode(onlCgformHead.getDesFormCode());
            //查询流程配置
            ExtActProcessForm extActProcessForm= extActProcessFormService.getOne(new LambdaQueryWrapper<ExtActProcessForm>()
                    .eq(ExtActProcessForm::getFormTableName,onlCgformHead.getTableName()));
            if(extActProcessForm!=null){
                onlComplexModel.setBpmCirculate(extActProcessForm.getCirculate());
            } else {
                onlComplexModel.setBpmCirculate(false);
            }
            // 数据初始化JS增强
            onlComplexModel.setOnlineInitQueryParamGetter(onlCgformHead.getOnlineInitQueryParamGetter());
            // Vue2监听器JS增强
            onlComplexModel.setOnlineVueWatchJsStr(onlCgformHead.getOnlineVueWatchJsStr());
            // 是否是视图表
            onlComplexModel.setViewTable(onlCgformHead.getViewTable());
            // 是否隐藏action按钮
            onlComplexModel.setHideActionButton(onlCgformHead.getHideActionButton());

            result.setResult(onlComplexModel);
            result.setOnlTable(onlCgformHead.getTableName());
            return result;
        }
    }

    @AutoLog(
            operateType = 1,
            value = "online列表数据查询",
            module = ModuleType.ONLINE
    )
    @PermissionData
    @OnlineAuth("getData")
    @GetMapping({"/getData/{code}"})
    public Result<Map<String, Object>> a(@PathVariable("code") String code, HttpServletRequest request) {
        Result<Map<String, Object>> res = new Result<>();
        OnlCgformHead cgformHead = this.onlCgformHeadService.getById(code);
        if (cgformHead == null) {
            res.error500("实体不存在");
            return res;
        } else {
            try {
                String tableName = cgformHead.getTableName();
                String dataRulePerms = cgformHead.getDataRulePerms();
                Map<String, Object> params = CgformDB.getRequestParams(request);
                List<String> needLists = null;
                String needList = request.getParameter("needList");
                if (StringUtils.hasText(needList)) {
                    String[] arr = needList.split(",");
                    needLists = Arrays.asList(arr);
                }
                // 查询所有列
                String queryAllColumn = request.getParameter("queryAllColumn");
                Map<String, Object> pageMap = this.onlCgformFieldService.queryAutolistPage(tableName, code, params, needLists, dataRulePerms, queryAllColumn);
                this.enhanceList(cgformHead, pageMap);
                res.setResult(pageMap);
            } catch (Exception var8) {
                a.error(var8.getMessage(), var8);
                res.error500("数据库查询失败，" + var8.getMessage());
            }
            res.setOnlTable(cgformHead.getTableName());
            return res;
        }
    }

    @AutoLog(
            operateType = 1,
            value = "online表单加载",
            module = ModuleType.ONLINE
    )
    @OnlineAuth("getFormItem")
    @GetMapping({"/getFormItem/{code}"})
    public Result<?> b(@PathVariable("code") String var1, HttpServletRequest var2) {
        OnlCgformHead var3 = (OnlCgformHead)this.onlCgformHeadService.getById(var1);
        if (var3 == null) {
            Result.error("表不存在");
        }

        Result var4 = new Result();
        LoginUser var5 = (LoginUser)SecurityUtils.getSubject().getPrincipal();
        JSONObject var6 = this.onlineService.queryOnlineFormItem(var3, var5.getUsername());
        var4.setResult(var6);
        var4.setOnlTable(var3.getTableName());
        return var4;
    }

    @AutoLog(
            operateType = 1,
            value = "online根据表名加载表单",
            module = ModuleType.ONLINE
    )
    @GetMapping({"/getFormItemBytbname/{table}"})
    public Result<?> a(@PathVariable("table") String var1, @RequestParam(name = "taskId",required = false) String var2) {
        Result var3 = new Result();
        LambdaQueryWrapper<OnlCgformHead> var4 = new LambdaQueryWrapper();
        var4.eq(OnlCgformHead::getTableName, var1);
        OnlCgformHead var5 = (OnlCgformHead)this.onlCgformHeadService.getOne(var4);
        if (var5 == null) {
            Result.error("表不存在");
        }

        LoginUser var6 = (LoginUser)SecurityUtils.getSubject().getPrincipal();
        JSONObject var7 = this.onlineService.queryFlowOnlineFormItem(var5, var6.getUsername(), var2);
        var3.setResult(var7);
        var3.setOnlTable(var1);
        return var3;
    }

    @OnlineAuth("getEnhanceJs")
    @GetMapping({"/getEnhanceJs/{code}"})
    public Result<?> c(@PathVariable("code") String var1, HttpServletRequest var2) {
        String var3 = this.onlineService.queryEnahcneJsString(var1, "form");
        return Result.ok(var3);
    }

    @AutoLog(
            operateType = 1,
            value = "online表单数据查询"
    )
    @GetMapping({"/form/{code}/{id}"})
    public Result<?> b(@PathVariable("code") String var1, @PathVariable("id") String var2) {
        try {
            Map var3 = this.onlCgformHeadService.queryManyFormData(var1, var2);
            return Result.ok(CgformDB.b(var3));
        } catch (Exception var4) {
            a.error("Online表单查询异常：" + var4.getMessage(), var4);
            return Result.error("查询失败，" + var4.getMessage());
        }
    }

    @GetMapping({"/subform/{table}/{mainId}"})
    public Result<?> c(@PathVariable("table") String var1, @PathVariable("mainId") String var2) {
        try {
            Map var3 = this.onlCgformHeadService.querySubFormData(var1, var2);
            return Result.ok(CgformDB.b(var3));
        } catch (Exception var4) {
            a.error("Online表单查询异常：" + var4.getMessage(), var4);
            return Result.error("查询失败，" + var4.getMessage());
        }
    }

    @GetMapping({"/subform/list/{table}/{mainId}"})
    public Result<?> d(@PathVariable("table") String var1, @PathVariable("mainId") String var2) {
        try {
            return Result.ok(this.onlCgformHeadService.queryManySubFormData(var1, var2));
        } catch (Exception var4) {
            a.error("Online表单查询异常：" + var4.getMessage(), var4);
            return Result.error("查询失败，" + var4.getMessage());
        }
    }

    @AutoLog(
            operateType = 1,
            value = "online根据表名查询表单数据",
            module = ModuleType.ONLINE
    )
    @GetMapping({"/form/table_name/{tableName}/{dataId}"})
    public Result<?> e(@PathVariable("tableName") String var1, @PathVariable("dataId") String var2) {
        try {
            LambdaQueryWrapper<OnlCgformHead> var3 = new LambdaQueryWrapper();
            var3.eq(OnlCgformHead::getTableName, var1);
            OnlCgformHead var4 = (OnlCgformHead)this.onlCgformHeadService.getOne(var3);
            if (var4 == null) {
                throw new Exception("OnlCgform tableName: " + var1 + " 不存在！");
            } else {
                Result var5 = this.b(var4.getId(), var2);
                var5.setOnlTable(var1);
                return var5;
            }
        } catch (Exception var6) {
            a.error("Online表单查询异常，" + var6.getMessage(), var6);
            return Result.error("查询失败，" + var6.getMessage());
        }
    }

    @AutoLog(
            operateType = 2,
            value = "online新增数据",
            module = ModuleType.ONLINE
    )
    @OnlineAuth("form")
    @PostMapping({"/form/{code}"})
    public Result<String> a(@PathVariable("code") String code, @RequestBody JSONObject var2, HttpServletRequest var3) {
        Result var4 = new Result();

        try {
            String var5 = CgformDB.a();
            var2.put("id", var5);
            String var6 = TokenUtils.getTokenByRequest(var3);
            String var7 = this.onlCgformHeadService.saveManyFormData(code, var2, var6);
            var4.setSuccess(true);
            var4.setResult(var5);
            var4.setOnlTable(var7);
        } catch (Exception var8) {
            a.error("OnlCgformApiController.formAdd()发生异常：", var8);
            var4.setSuccess(false);
            var4.setMessage("保存失败，" + CgformDB.a(var8));
        }

        return var4;
    }

    @AutoLog(
            operateType = 3,
            value = "online修改数据",
            module = ModuleType.ONLINE
    )
    @OnlineAuth("form")
    @PutMapping({"/form/{code}"})
    public Result<?> a(@PathVariable("code") String var1, @RequestBody JSONObject var2) {
        try {
            String var3 = this.onlCgformHeadService.editManyFormData(var1, var2);
            Result var4 = Result.ok("修改成功！");
            var4.setOnlTable(var3);
            String id = var2.getString("id");
            var4.setResult(id);
            return var4;
        } catch (Exception var5) {
            a.error("OnlCgformApiController.formEdit()发生异常：" + var5.getMessage(), var5);
            return Result.error("修改失败，" + CgformDB.a(var5));
        }
    }

    @AutoLog(
            operateType = 4,
            value = "online删除数据",
            module = ModuleType.ONLINE
    )
    @OnlineAuth("form")
    @DeleteMapping({"/form/{code}/{id}"})
    public Result<?> f(@PathVariable("code") String var1, @PathVariable("id") String var2) {
        OnlCgformHead var3 = (OnlCgformHead)this.onlCgformHeadService.getById(var1);
        if (var3 == null) {
            return Result.error("实体不存在");
        } else {
            try {
                String var4 = "";
                if ("Y".equals(var3.getIsTree())) {
                    var2 = this.onlCgformFieldService.queryTreeChildIds(var3, var2);
                    var4 = this.onlCgformFieldService.queryTreePids(var3, var2);
                }

                if (var2.indexOf(",") > 0) {
                    String var5;
                    if (var3.getTableType() == 2) {
                        this.onlCgformFieldService.deleteAutoListMainAndSub(var3, var2);
                    } else {
                        var5 = var3.getTableName();
                        this.onlCgformFieldService.deleteAutoListById(var5, var2);
                    }

                    if ("Y".equals(var3.getIsTree())) {
                        var5 = var3.getTableName();
                        String var6 = var3.getTreeIdField();
                        String[] var7 = var4.split(",");
                        String[] var8 = var7;
                        int var9 = var7.length;

                        for(int var10 = 0; var10 < var9; ++var10) {
                            String var11 = var8[var10];
                            this.onlCgformFieldService.updateTreeNodeNoChild(var5, var6, var11);
                        }
                    }
                } else {
                    this.onlCgformHeadService.deleteOneTableInfo(var1, var2);
                }

                if (oConvertUtils.isNotEmpty(var3.getIsDesForm()) && "1".equals(var3.getIsDesForm())) {
                    this.onlCgformFieldService.deleteAutoList("design_form_data", "online_form_data_id", var2);
                }
            } catch (Exception var12) {
                a.error("OnlCgformApiController.formEdit()发生异常：" + var12.getMessage(), var12);
                return Result.error("删除失败");
            }

            Result var13 = Result.ok("删除成功!");
            var13.setOnlTable(var3.getTableName());
            return var13;
        }
    }

    @AutoLog(
            operateType = 4,
            value = "online删除数据",
            module = ModuleType.ONLINE
    )
    @DeleteMapping({"/formByCode/{code}/{id}"})
    public Result<?> g(@PathVariable("code") String var1, @PathVariable("id") String var2) {
        OnlCgformHead var3 = (OnlCgformHead)this.onlCgformHeadService.getOne((Wrapper)(new LambdaQueryWrapper<OnlCgformHead>()).eq(OnlCgformHead::getTableName, var1));
        if (var3 == null) {
            return Result.error("实体不存在");
        } else {
            try {
                if (var2.indexOf(",") > 0) {
                    String var4 = var3.getTableName();
                    this.onlCgformFieldService.deleteAutoListById(var4, var2);
                } else {
                    this.onlCgformHeadService.deleteOneTableInfo(var3.getId(), var2);
                }
            } catch (Exception var5) {
                a.error("OnlCgformApiController.formEdit()发生异常：" + var5.getMessage(), var5);
                return Result.error("删除失败");
            }

            Result var6 = Result.ok("删除成功!");
            var6.setOnlTable(var3.getTableName());
            return var6;
        }
    }

    @OnlineAuth("getQueryInfo")
    @GetMapping({"/getQueryInfo/{code}"})
    public Result<?> getQueryInfo(@PathVariable("code") String code) {
        try {
            List<Map<String, String>> res = this.onlCgformFieldService.getAutoListQueryInfo(code);
            return Result.ok(res);
        } catch (Exception var3) {
            a.error("OnlCgformApiController.getQueryInfo()发生异常：" + var3.getMessage(), var3);
            return Result.error("查询失败");
        }
    }

    @PostMapping({"/doDbSynch/{code}/{synMethod}"})
    public Result<?> h(@PathVariable("code") String var1, @PathVariable("synMethod") String var2) {
        try {
            long var3 = System.currentTimeMillis();
            this.onlCgformHeadService.doDbSynch(var1, var2);
            a.info("==同步数据库消耗时间" + (System.currentTimeMillis() - var3) + "毫秒");
        } catch (Exception var5) {
            a.error(var5.getMessage(), var5);
            return Result.error("同步数据库失败，" + CgformDB.a(var5));
        }

        return Result.ok("同步数据库成功!");
    }

    /**
     * 导出有点问题
     * @param code
     * @param request
     * @param response
     */
    @OnlineAuth("exportXls")
    @PermissionData
    @GetMapping({"/exportXls/{code}"})
    public void a(@PathVariable("code") String code, HttpServletRequest request, HttpServletResponse response) {
        OnlCgformHead cgformHead = (OnlCgformHead)this.onlCgformHeadService.getById(code);
        if (cgformHead != null) {
            String tableTxt = cgformHead.getTableTxt();
            String paramsStr = request.getParameter("paramsStr");
            Map pageResult = new HashMap<>();
            Object var8 = null;
            if (oConvertUtils.isNotEmpty(paramsStr)) {
                pageResult = JSONObject.parseObject(paramsStr, Map.class);
            }

            pageResult.put("pageSize", -521);
            // 查询所有列
            String queryAllColumn = request.getParameter("queryAllColumn");
            Map<String, Object> autolistPage = this.onlCgformFieldService.queryAutolistPage(cgformHead.getTableName(), cgformHead.getId(), pageResult, null, cgformHead.getDataRulePerms(), queryAllColumn);
            List<OnlCgformField> fieldList = (List<OnlCgformField>)autolistPage.get("fieldList");
            List<Map<String, Object>> records = (List<Map<String, Object>>)autolistPage.get("records");
            List exportRecords = new ArrayList();
            String selections = (pageResult).get("selections") == null ? null : (pageResult).get("selections").toString();
            if (CgformDC.b(selections)) {
                List<String> finalVar1 = Arrays.asList(selections.split(","));
                exportRecords = (records).stream().filter((var1x) -> finalVar1.contains(var1x.get("id"))).collect(Collectors.toList());
            } else {
                if (records == null) {
                    records = new ArrayList();
                }
                exportRecords.addAll(records);
            }

            org.jeecg.modules.online.cgform.converter.b.a(1, exportRecords, fieldList);

            try {
                this.onlCgformHeadService.executeEnhanceExport(cgformHead, exportRecords);
            } catch (BusinessException var31) {
                a.error("导出java增强处理出错", var31.getMessage());
            }

            List<ExcelExportEntity> exportEntities = this.getExcelExportEntities(fieldList, "id");
            if (cgformHead.getTableType() == 2 && oConvertUtils.isEmpty(pageResult.get("exportSingleOnly"))) {
                String var15 = cgformHead.getSubTableStr();
                if (oConvertUtils.isNotEmpty(var15)) {
                    String[] var16 = var15.split(",");
                    String[] var17 = var16;
                    int var18 = var16.length;

                    for(int var19 = 0; var19 < var18; ++var19) {
                        String var20 = var17[var19];
                        this.a(var20, pageResult, exportRecords, exportEntities);
                    }
                }
            }

            Workbook var33 = ExcelExportUtil.exportExcel(new ExportParams((String)null, tableTxt), exportEntities, (Collection)exportRecords);
            ServletOutputStream var34 = null;

            try {
                response.setContentType("application/x-msdownload;charset=utf-8");
                String var35 = BrowserUtils.checkBrowse(request);
                String var36 = cgformHead.getTableTxt() + "-v" + cgformHead.getTableVersion();
                if ("MSIE".equalsIgnoreCase(var35.substring(0, 4))) {
                    response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(var36, "UTF-8") + ".xls");
                } else {
                    String var37 = new String(var36.getBytes("UTF-8"), "ISO8859-1");
                    response.setHeader("content-disposition", "attachment;filename=" + var37 + ".xls");
                }

                var34 = response.getOutputStream();
                var33.write(var34);
                response.flushBuffer();
            } catch (Exception var30) {
                a.error("--通过流的方式获取文件异常--" + var30.getMessage(), var30);
            } finally {
                if (var34 != null) {
                    try {
                        var34.close();
                    } catch (IOException var29) {
                        a.error(var29.getMessage(), var29);
                    }
                }

            }

        }
    }

    @OnlineAuth("importXls")
    @PostMapping({"/importXls/{code}"})
    public Result<?> b(@PathVariable("code") String var1, HttpServletRequest var2, HttpServletResponse var3) {
        long var4 = System.currentTimeMillis();
        Result var6 = new Result();
        String var7 = "";

        try {
            OnlCgformHead var8 = (OnlCgformHead)this.onlCgformHeadService.getById(var1);
            if (var8 == null) {
                return Result.error("数据库不存在该表记录");
            }

            LambdaQueryWrapper<OnlCgformField> var9 = new LambdaQueryWrapper();
            var9.eq(OnlCgformField::getCgformHeadId, var1);
            List var10 = this.onlCgformFieldService.list(var9);
            String var11 = var2.getParameter("isSingleTableImport");
            List var12 = CgformDB.e(var10);
            if (oConvertUtils.isEmpty(var11) && var8.getTableType() == 2 && oConvertUtils.isNotEmpty(var8.getSubTableStr())) {
                String[] var13 = var8.getSubTableStr().split(",");
                int var14 = var13.length;

                for(int var15 = 0; var15 < var14; ++var15) {
                    String var16 = var13[var15];
                    OnlCgformHead var17 = (OnlCgformHead)this.onlCgformHeadService.getOne((Wrapper)(new LambdaQueryWrapper<OnlCgformHead>()).eq(OnlCgformHead::getTableName, var16));
                    if (var17 != null) {
                        List var18 = this.onlCgformFieldService.list((Wrapper)(new LambdaQueryWrapper<OnlCgformField>()).eq(OnlCgformField::getCgformHeadId, var17.getId()));
                        List var19 = CgformDB.b(var18, var17.getTableTxt());
                        if (var19.size() > 0) {
                            var12.addAll(var19);
                        }
                    }
                }
            }

            JSONObject var47 = null;
            String var48 = var2.getParameter("foreignKeys");
            if (oConvertUtils.isNotEmpty(var48)) {
                var47 = JSONObject.parseObject(var48);
            }

            MultipartHttpServletRequest var49 = (MultipartHttpServletRequest)var2;
            Map var50 = var49.getFileMap();
            DataSource var51 = (DataSource)SpringContextUtils.getApplicationContext().getBean(DataSource.class);
            String var52 = d.a(var51);
            Iterator var53 = var50.entrySet().iterator();

            while(true) {
                while(var53.hasNext()) {
                    Entry var20 = (Entry)var53.next();
                    MultipartFile var21 = (MultipartFile)var20.getValue();
                    ImportParams var22 = new ImportParams();
                    var22.setImageList(var12);
                    var22.setDataHanlder(new org.jeecg.modules.online.cgform.d.a(var10, this.upLoadPath, this.uploadType));
                    List var23 = ExcelImportUtil.importExcel(var21.getInputStream(), Map.class, var22);
                    if (var23 != null) {
                        Object var24 = "";
                        ArrayList var25 = new ArrayList();

                        Map var27;
                        for(Iterator var26 = var23.iterator(); var26.hasNext(); var27.put("$mainTable$id", var24)) {
                            var27 = (Map)var26.next();
                            boolean var28 = false;
                            Set var29 = var27.keySet();
                            HashMap var30 = new HashMap();
                            Iterator var31 = var29.iterator();

                            String var32;
                            while(var31.hasNext()) {
                                var32 = (String)var31.next();
                                if (var32.indexOf("$subTable$") == -1) {
                                    if (var32.indexOf("$mainTable$") != -1 && oConvertUtils.isNotEmpty(var27.get(var32).toString())) {
                                        var28 = true;
                                        var24 = this.a(var8, var51, var52);
                                    }

                                    var30.put(var32.replace("$mainTable$", ""), var27.get(var32));
                                }
                            }

                            if (var28) {
                                var30.put("id", var24);
                                var25.add(var30);
                                var24 = var30.get("id");
                            }

                            if (var47 != null) {
                                var31 = var47.keySet().iterator();

                                while(var31.hasNext()) {
                                    var32 = (String)var31.next();
                                    System.out.println(var32 + "=" + var47.getString(var32));
                                    var30.put(var32, var47.getString(var32));
                                }
                            }
                        }

                        if (var25 == null || var25.size() == 0) {
                            var6.setSuccess(false);
                            var6.setMessage("导入失败，匹配的数据条数为零!");
                            return var6;
                        }

                        this.onlCgformSqlService.saveBatchOnlineTable(var8, var10, var25);
                        if (oConvertUtils.isEmpty(var11) && var8.getTableType() == 2 && oConvertUtils.isNotEmpty(var8.getSubTableStr())) {
                            String[] var54 = var8.getSubTableStr().split(",");
                            int var55 = var54.length;

                            for(int var56 = 0; var56 < var55; ++var56) {
                                String var57 = var54[var56];
                                OnlCgformHead var58 = (OnlCgformHead)this.onlCgformHeadService.getOne((Wrapper)(new LambdaQueryWrapper<OnlCgformHead>()).eq(OnlCgformHead::getTableName, var57));
                                if (var58 != null) {
                                    LambdaQueryWrapper<OnlCgformField> var59 = new LambdaQueryWrapper();
                                    var59.eq(OnlCgformField::getCgformHeadId, var58.getId());
                                    List var60 = this.onlCgformFieldService.list(var59);
                                    ArrayList var33 = new ArrayList();
                                    String var34 = var58.getTableTxt();
                                    Iterator var35 = var23.iterator();

                                    while(var35.hasNext()) {
                                        Map var36 = (Map)var35.next();
                                        boolean var37 = false;
                                        HashMap var38 = new HashMap();
                                        Iterator var39 = var60.iterator();

                                        while(var39.hasNext()) {
                                            OnlCgformField var40 = (OnlCgformField)var39.next();
                                            String var41 = var40.getMainTable();
                                            String var42 = var40.getMainField();
                                            boolean var43 = var8.getTableName().equals(var41) && oConvertUtils.isNotEmpty(var42);
                                            String var44 = var34 + "_" + var40.getDbFieldTxt();
                                            if (var43) {
                                                var38.put(var40.getDbFieldName(), var36.get("$mainTable$" + var42));
                                            }

                                            Object var45 = var36.get("$subTable$" + var44);
                                            if (null != var45 && oConvertUtils.isNotEmpty(var45.toString())) {
                                                var37 = true;
                                                var38.put(var40.getDbFieldName(), var45);
                                            }
                                        }

                                        if (var37) {
                                            var38.put("id", this.a(var58, var51, var52));
                                            var33.add(var38);
                                        }
                                    }

                                    if (var33.size() > 0) {
                                        this.onlCgformSqlService.saveBatchOnlineTable(var58, var60, var33);
                                    }
                                }
                            }
                        }

                        var7 = "文件导入成功！";
                    } else {
                        var7 = "识别模版数据错误";
                        a.error(var7);
                    }
                }

                var6.setSuccess(true);
                var6.setMessage("导入成功!");
                break;
            }
        } catch (Exception var46) {
            var6.setSuccess(false);
            var6.setMessage(var46.getMessage());
            a.error(var46.getMessage(), var46);
        }

        a.info("=====online导入数据完成,耗时:" + (System.currentTimeMillis() - var4) + "毫秒=====");
        return var6;
    }

    @PostMapping({"/doButton"})
    public Result<?> a(@RequestBody JSONObject var1) {
        String var2 = var1.getString("formId");
        String var3 = var1.getString("dataId");
        String var4 = var1.getString("buttonCode");
        JSONObject var5 = var1.getJSONObject("uiFormData");

        try {
            this.onlCgformHeadService.executeCustomerButton(var4, var2, var3);
        } catch (Exception var7) {
            a.error(var7.getMessage(), var7);
            return Result.error("执行失败!");
        }

        return Result.ok("执行成功!");
    }

    public Object a(OnlCgformHead var1, DataSource var2, String var3) throws SQLException, DBException {
        Object var4 = null;
        String var5 = var1.getIdType();
        String var6 = var1.getIdSequence();
        if (oConvertUtils.isNotEmpty(var5) && "UUID".equalsIgnoreCase(var5)) {
            var4 = CgformDB.a();
        } else {
            PostgreSQLSequenceMaxValueIncrementer var7;
            OracleSequenceMaxValueIncrementer var13;
            if (oConvertUtils.isNotEmpty(var5) && "NATIVE".equalsIgnoreCase(var5)) {
                if (oConvertUtils.isNotEmpty(var3) && "oracle".equalsIgnoreCase(var3)) {
                    var13 = new OracleSequenceMaxValueIncrementer(var2, "HIBERNATE_SEQUENCE");

                    try {
                        var4 = var13.nextLongValue();
                    } catch (Exception var12) {
                        a.error(var12.getMessage(), var12);
                    }
                } else if (oConvertUtils.isNotEmpty(var3) && "postgres".equalsIgnoreCase(var3)) {
                    var7 = new PostgreSQLSequenceMaxValueIncrementer(var2, "HIBERNATE_SEQUENCE");

                    try {
                        var4 = var7.nextLongValue();
                    } catch (Exception var11) {
                        a.error(var11.getMessage(), var11);
                    }
                } else {
                    var4 = null;
                }
            } else if (oConvertUtils.isNotEmpty(var5) && "SEQUENCE".equalsIgnoreCase(var5)) {
                if (oConvertUtils.isNotEmpty(var3) && "oracle".equalsIgnoreCase(var3)) {
                    var13 = new OracleSequenceMaxValueIncrementer(var2, var6);

                    try {
                        var4 = var13.nextLongValue();
                    } catch (Exception var10) {
                        a.error(var10.getMessage(), var10);
                    }
                } else if (oConvertUtils.isNotEmpty(var3) && "postgres".equalsIgnoreCase(var3)) {
                    var7 = new PostgreSQLSequenceMaxValueIncrementer(var2, var6);

                    try {
                        var4 = var7.nextLongValue();
                    } catch (Exception var9) {
                        a.error(var9.getMessage(), var9);
                    }
                } else {
                    var4 = null;
                }
            } else {
                var4 = CgformDB.a();
            }
        }

        return var4;
    }

    private void a(Map var1, List<OnlCgformField> var2) {
        Iterator var3 = var2.iterator();

        while(true) {
            OnlCgformField var4;
            String var5;
            String var6;
            String var7;
            do {
                do {
                    if (!var3.hasNext()) {
                        return;
                    }

                    var4 = (OnlCgformField)var3.next();
                    var5 = var4.getDictTable();
                    var6 = var4.getDictField();
                    var7 = var4.getDictText();
                } while(oConvertUtils.isEmpty(var5) && oConvertUtils.isEmpty(var6));
            } while("popup".equals(var4.getFieldShowType()));

            String var9 = String.valueOf(var1.get(var4.getDbFieldName()));
            List var8;
            if (oConvertUtils.isEmpty(var5)) {
                var8 = this.sysBaseAPI.queryDictItemsByCode(var6);
            } else {
                var8 = this.sysBaseAPI.queryTableDictItemsByCode(var5, var7, var6);
            }

            Iterator var10 = var8.iterator();

            while(var10.hasNext()) {
                DictModel var11 = (DictModel)var10.next();
                if (var9.equals(var11.getText())) {
                    var1.put(var4.getDbFieldName(), var11.getValue());
                }
            }
        }
    }

    private List<ExcelExportEntity> getExcelExportEntities(List<OnlCgformField> cgformFields, String idDbFieldName) {
        ArrayList<ExcelExportEntity> result = new ArrayList<>();

        for(int idx = 0; idx < cgformFields.size(); ++idx) {
            if ((null == idDbFieldName || !idDbFieldName.equals(((OnlCgformField)cgformFields.get(idx)).getDbFieldName())) && ((OnlCgformField)cgformFields.get(idx)).getIsShowList() == 1) {
                String var5 = ((OnlCgformField)cgformFields.get(idx)).getDbFieldName();
                ExcelExportEntity excelExportEntity = new ExcelExportEntity(((OnlCgformField)cgformFields.get(idx)).getDbFieldTxt(), var5);
                if ("image".equals(((OnlCgformField)cgformFields.get(idx)).getFieldShowType())) {
                    excelExportEntity.setType(2);
                    excelExportEntity.setExportImageType(3);
                    excelExportEntity.setImageBasePath(this.upLoadPath);
                    excelExportEntity.setHeight(50.0D);
                    excelExportEntity.setWidth(60.0D);
                } else {
                    int var7 = ((OnlCgformField)cgformFields.get(idx)).getDbLength() == 0 ? 12 : (((OnlCgformField)cgformFields.get(idx)).getDbLength() > 30 ? 30 : ((OnlCgformField)cgformFields.get(idx)).getDbLength());
                    if (((OnlCgformField)cgformFields.get(idx)).getFieldShowType().equals("date")) {
                        excelExportEntity.setFormat("yyyy-MM-dd");
                    } else if (((OnlCgformField)cgformFields.get(idx)).getFieldShowType().equals("datetime")) {
                        excelExportEntity.setFormat("yyyy-MM-dd HH:mm:ss");
                    }

                    if (var7 < 10) {
                        var7 = 10;
                    }

                    excelExportEntity.setWidth((double)var7);
                }

                result.add(excelExportEntity);
            }
        }

        return result;
    }

    private void a(String dbFieldName, Map<String, Object> pageResult, List<Map<String, Object>> exportRecords, List<ExcelExportEntity> excelExportEntities) {
        OnlCgformHead cgformHead = this.onlCgformHeadService.getOne((new LambdaQueryWrapper<OnlCgformHead>()).eq(OnlCgformHead::getTableName, dbFieldName));
        LambdaQueryWrapper<OnlCgformField> qw = new LambdaQueryWrapper<>();
        qw.eq(OnlCgformField::getCgformHeadId, cgformHead.getId());
        qw.orderByAsc(OnlCgformField::getOrderNum);
        List<OnlCgformField> onlCgformFields = this.onlCgformFieldService.list(qw);
        String mainField = "";
        String mainFieldDbName = "";

        for (OnlCgformField cgformField : onlCgformFields) {
            if (oConvertUtils.isNotEmpty(cgformField.getMainField())) {
                mainField = cgformField.getMainField();
                mainFieldDbName = cgformField.getDbFieldName();
                break;
            }
        }

        ExcelExportEntity excelExportEntity = new ExcelExportEntity(cgformHead.getTableTxt(), dbFieldName);
        excelExportEntity.setList(this.getExcelExportEntities(onlCgformFields, "id"));
        excelExportEntities.add(excelExportEntity);

        for (Map<String, Object> subRecord : exportRecords) {
            pageResult.put(mainFieldDbName, subRecord.get(mainField));
            String subSql = CgformDB.generateSql(cgformHead.getTableName(), onlCgformFields, pageResult);
            a.info("-----------动态列表查询子表sql》》" + subSql);
            List<Map<String, Object>> subList = this.onlCgformHeadService.queryListData(subSql);
            org.jeecg.modules.online.cgform.converter.b.a(1, subList, onlCgformFields);
            subRecord.put(dbFieldName, CgformDB.d(subList));
        }

    }

    @GetMapping({"/checkOnlyTable"})
    public Result<?> i(@RequestParam("tbname") String var1, @RequestParam("id") String var2) {
        OnlCgformHead var3;
        if (oConvertUtils.isEmpty(var2)) {
            if (d.a(var1)) {
                return Result.ok(-1);
            }

            var3 = (OnlCgformHead)this.onlCgformHeadService.getOne((new LambdaQueryWrapper<OnlCgformHead>()).eq(OnlCgformHead::getTableName, var1));
            if (oConvertUtils.isNotEmpty(var3)) {
                return Result.ok(-1);
            }
        } else {
            var3 = (OnlCgformHead)this.onlCgformHeadService.getById(var2);
            if (!var1.equals(var3.getTableName()) && d.a(var1)) {
                return Result.ok(-1);
            }
        }

        return Result.ok(1);
    }

    @PostMapping({"/codeGenerate"})
    public Result<?> b(@RequestBody JSONObject var1) {
        OnlGenerateModel var2 = (OnlGenerateModel)JSONObject.parseObject(var1.toJSONString(), OnlGenerateModel.class);
        List var3 = null;

        try {
            if ("1".equals(var2.getJformType())) {
                var3 = this.onlCgformHeadService.generateCode(var2);
            } else {
                var3 = this.onlCgformHeadService.generateOneToMany(var2);
            }

            return Result.ok(var3);
        } catch (Exception var5) {
            var5.printStackTrace();
            return Result.error(var5.getMessage());
        }
    }

    @GetMapping({"/downGenerateCode"})
    public void a(@RequestParam("fileList") List<String> var1, HttpServletRequest var2, HttpServletResponse var3) {
        List var4 = (List)var1.stream().filter((var0) -> {
            return var0.indexOf("src/main/java") == -1 && var0.indexOf("src%5Cmain%5Cjava") == -1;
        }).collect(Collectors.toList());
        if (var1 != null && (var4 == null || var4.size() <= 0)) {
            String var5 = "生成代码_" + System.currentTimeMillis() + ".zip";
            final String var6 = "/opt/temp/codegenerate/" + var5;
            File var7 = e.a(var1, var6);
            if (var7.exists()) {
                var3.setContentType("application/force-download");
                var3.addHeader("Content-Disposition", "attachment;fileName=" + var5);
                byte[] var8 = new byte[1024];
                FileInputStream var9 = null;
                BufferedInputStream var10 = null;

                try {
                    var9 = new FileInputStream(var7);
                    var10 = new BufferedInputStream(var9);
                    ServletOutputStream var11 = var3.getOutputStream();

                    for(int var12 = var10.read(var8); var12 != -1; var12 = var10.read(var8)) {
                        var11.write(var8, 0, var12);
                    }
                } catch (Exception var25) {
                    var25.printStackTrace();
                } finally {
                    if (var10 != null) {
                        try {
                            var10.close();
                        } catch (IOException var24) {
                            var24.printStackTrace();
                        }
                    }

                    if (var9 != null) {
                        try {
                            var9.close();
                        } catch (IOException var23) {
                            var23.printStackTrace();
                        }
                    }

                    class NamelessClass_1 extends Thread {
                        NamelessClass_1() {
                        }

                        public void run() {
                            try {
                                Thread.sleep(10000L);
                                FileUtil.del(var6);
                            } catch (InterruptedException var2) {
                                var2.printStackTrace();
                            }

                        }
                    }

                    (new NamelessClass_1()).start();
                }
            }

        } else {
            a.error(" fileList 不合法！！！", var1);
        }
    }

    @AutoLog(
            operateType = 1,
            value = "online列表数据查询",
            module = ModuleType.ONLINE
    )
    @GetMapping({"/getTreeData/{code}"})
    @PermissionData
    public Result<Map<String, Object>> d(@PathVariable("code") String var1, HttpServletRequest var2) {
        Result var3 = new Result();
        OnlCgformHead var4 = (OnlCgformHead)this.onlCgformHeadService.getById(var1);
        if (var4 == null) {
            var3.error500("实体不存在");
            return var3;
        } else {
            try {
                String var5 = var4.getTableName();
                String var6 = var4.getTreeIdField();
                String var7 = var4.getTreeParentIdField();
                ArrayList var8 = Lists.newArrayList(new String[]{var6, var7});
                Map var9 = CgformDB.getRequestParams(var2);
                String var10 = null;
                if (var9.get(var6) != null) {
                    var10 = var9.get(var6).toString();
                }

                if (var9.get("hasQuery") != null && "false".equals(var9.get("hasQuery")) && var9.get(var7) == null) {
                    var9.put(var7, "0");
                } else {
                    var9.put("pageSize", -521);
                    var9.put(var7, var9.get(var7));
                }

                var9.put(var6, (Object)null);
                Map var11 = this.onlCgformFieldService.queryAutoTreeNoPage(var5, var1, var9, var8, var7);
                this.enhanceList(var4, var11);
                var3.setResult(var11);
            } catch (Exception var12) {
                a.error(var12.getMessage(), var12);
                var3.error500("数据库查询失败" + var12.getMessage());
            }

            var3.setOnlTable(var4.getTableName());
            return var3;
        }
    }

    private void enhanceList(OnlCgformHead onlCgformHead, Map<String, Object> pageMap) throws BusinessException {
        List<Map<String, Object>> records = (List<Map<String, Object>>)pageMap.get("records");
        this.onlCgformHeadService.executeEnhanceList(onlCgformHead, "query", records);
    }

    @PostMapping({"/crazyForm/{name}"})
    public Result<?> b(@PathVariable("name") String var1, @RequestBody JSONObject var2) {
        Result var3 = new Result();

        try {
            String var4 = CgformDB.a();
            var2.put("id", var4);
            this.onlCgformHeadService.addCrazyFormData(var1, var2);
            var3.setResult(var4);
            var3.setMessage("保存成功");
            return var3;
        } catch (Exception var5) {
            a.error("OnlCgformApiController.formAddForDesigner()发生异常：" + var5.getMessage(), var5);
            return Result.error("保存失败");
        }
    }

    @PutMapping({"/crazyForm/{name}"})
    public Result<?> c(@PathVariable("name") String var1, @RequestBody JSONObject var2) {
        try {
            var2.remove("create_by");
            var2.remove("create_time");
            var2.remove("update_by");
            var2.remove("update_time");
            this.onlCgformHeadService.editCrazyFormData(var1, var2);
        } catch (Exception var4) {
            a.error("OnlCgformApiController.formEditForDesigner()发生异常：" + var4.getMessage(), var4);
            return Result.error("保存失败");
        }

        return Result.ok("保存成功!");
    }

    @AutoLog(
            operateType = 1,
            value = "online列表加载",
            module = ModuleType.ONLINE
    )
    @GetMapping({"/getErpColumns/{code}"})
    public Result<Map<String, Object>> c(@PathVariable("code") String var1) {
        Result var2 = new Result();
        OnlCgformHead var3 = (OnlCgformHead)this.onlCgformHeadService.getById(var1);
        if (var3 == null) {
            var2.error500("实体不存在");
            return var2;
        } else {
            HashMap var4 = new HashMap();
            LoginUser var5 = (LoginUser)SecurityUtils.getSubject().getPrincipal();
            OnlComplexModel var6 = this.onlineService.queryOnlineConfig(var3, var5.getUsername());
            var4.put("main", var6);
            if ("erp".equals(var3.getThemeTemplate()) && var3.getTableType() == 2) {
                String var7 = var3.getSubTableStr();
                if (oConvertUtils.isNotEmpty(var7)) {
                    ArrayList var8 = new ArrayList();
                    String[] var9 = var7.split(",");
                    int var10 = var9.length;

                    for(int var11 = 0; var11 < var10; ++var11) {
                        String var12 = var9[var11];
                        OnlCgformHead var13 = (OnlCgformHead)this.onlCgformHeadService.getOne((Wrapper)(new LambdaQueryWrapper<OnlCgformHead>()).eq(OnlCgformHead::getTableName, var12));
                        if (var13 != null) {
                            var8.add(this.onlineService.queryOnlineConfig(var13, var5.getUsername()));
                        }
                    }

                    if (var8.size() > 0) {
                        var4.put("subList", var8);
                    }
                }
            }

            var2.setOnlTable(var3.getTableName());
            var2.setResult(var4);
            var2.setSuccess(true);
            return var2;
        }
    }

    @AutoLog(
            operateType = 1,
            value = "online表单加载",
            module = ModuleType.ONLINE
    )
    @GetMapping({"/getErpFormItem/{code}"})
    public Result<?> e(@PathVariable("code") String var1, HttpServletRequest var2) {
        OnlCgformHead var3 = (OnlCgformHead)this.onlCgformHeadService.getById(var1);
        if (var3 == null) {
            Result.error("表不存在");
        }

        Result var4 = new Result();
        LoginUser var5 = (LoginUser)SecurityUtils.getSubject().getPrincipal();
        JSONObject var6 = this.onlineService.queryOnlineFormObj(var3, var5.getUsername());
        var4.setResult(var6);
        var4.setOnlTable(var3.getTableName());
        return var4;
    }

    @GetMapping({"/querySelectOptions"})
    public Result<List<TreeModel>> a(@ModelAttribute org.jeecg.modules.online.cgform.a.a var1) {
        Result var2 = new Result();
        List var3 = this.onlCgformFieldService.queryDataListByLinkDown(var1);
        var2.setResult(var3);
        var2.setSuccess(true);
        return var2;
    }
}
