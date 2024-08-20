package org.jeecg.modules.activiti.jeecg.jexfm.controller;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.poi.ss.usermodel.Workbook;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.system.vo.DictModel;
import org.jeecg.common.util.BrowserUtils;
import org.jeecg.common.util.TokenUtils;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.activiti.jeecg.commons.lang.MyStringUtil;
import org.jeecg.modules.activiti.jeecg.jexfm.JexfmBA;
import org.jeecg.modules.online.desform.entity.DesignForm;
import org.jeecg.modules.online.desform.entity.DesignFormData;
import org.jeecg.modules.online.desform.service.IDesignFormDataService;
import org.jeecg.modules.online.desform.service.IDesignFormService;
import org.jeecg.modules.online.desform.vo.excel.DesformWidgetList;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.params.ExcelExportEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.Map.Entry;

@RestController("designFormDataController")
@RequestMapping({"/desform/data"})
public class JexfmAD {
    private static final Logger b = LoggerFactory.getLogger(JexfmAD.class);
    private ISysBaseAPI sysBaseAPI;
    private IDesignFormDataService designFormDataService;
    private IDesignFormService designFormService;
    @Value("${jeecg.path.upload}")
    private String upLoadPath;
    @Value("${jeecg.uploadType}")
    private String uploadType;

    @Autowired
    public JexfmAD(ISysBaseAPI var1, IDesignFormDataService var2, IDesignFormService var3) {
        this.sysBaseAPI = var1;
        this.designFormDataService = var2;
        this.designFormService = var3;
    }

    @GetMapping({"/list"})
    public Result<?> a(DesignFormData var1, @RequestParam(name = "pageNo",defaultValue = "1") Integer var2, @RequestParam(name = "pageSize",defaultValue = "10") Integer var3, @RequestParam(name = "desformCode") String var4, @RequestParam(name = "superQuery",required = false,defaultValue = "{}") String var5, HttpServletRequest var6) throws UnsupportedEncodingException {
        String var7 = JwtUtil.getUserNameByToken(var6);
        QueryWrapper var8 = QueryGenerator.initQueryWrapper(var1, var6.getParameterMap());
        var8.orderByDesc("create_time");
        return this.designFormDataService.queryPage(var4, var2, var3, var8, var5, var7);
    }

    @PostMapping({"/add"})
    public Result a(@RequestBody DesignFormData var1) {
        return this.designFormDataService.addOne(var1);
    }

    @PutMapping({"/edit"})
    public Result b(@RequestBody DesignFormData var1) {
        return this.designFormDataService.editOne(var1);
    }

    @DeleteMapping({"/delete"})
    public Result a(@RequestParam(name = "id") String var1, HttpServletRequest var2) {
        return this.designFormDataService.deleteOne(var1, TokenUtils.getTokenByRequest(var2));
    }

    @DeleteMapping({"/deleteBatch"})
    public Result<DesignFormData> b(@RequestParam(name = "ids") String var1, HttpServletRequest var2) {
        Result var3 = new Result();
        if (var1 != null && !"".equals(var1.trim())) {
            this.designFormDataService.deleteBatchMain(Arrays.asList(var1.split(",")), TokenUtils.getTokenByRequest(var2));
            var3.success("删除成功!");
        } else {
            var3.error500("参数不识别！");
        }

        return var3;
    }

    @GetMapping({"/queryById"})
    public Result<DesignFormData> a(@RequestParam(name = "id",required = true) String var1) {
        Result var2 = new Result();
        DesignFormData var3 = (DesignFormData)this.designFormDataService.getById(var1);
        if (var3 == null) {
            var2.error500("未找到对应数据");
        } else {
            var2.setResult(var3);
            var2.setSuccess(true);
        }

        return var2;
    }

    @GetMapping({"/queryDesignFormDataByMainId"})
    public Result<List<DesignFormData>> b(@RequestParam(name = "id",required = true) String var1) {
        Result var2 = new Result();
        List var3 = this.designFormDataService.selectByMainId(var1);
        var2.setResult(var3);
        var2.setSuccess(true);
        return var2;
    }

    @GetMapping({"/exportXls/{desformCode}"})
    public void a(DesignFormData var1, @PathVariable("desformCode") String var2, @RequestParam(name = "pageNo",defaultValue = "1") Integer var3, @RequestParam(name = "selectionIds",required = false) String var4, @RequestParam(name = "pageSize",defaultValue = "10") Integer var5, @RequestParam(name = "superQuery",required = false,defaultValue = "{}") String var6, HttpServletRequest var7, HttpServletResponse var8) throws Exception {
        DesignForm var9 = this.designFormService.getByCode(var2);
        if (var9 != null) {
            String var10 = var9.getDesformName();
            DesformWidgetList var11 = org.jeecg.modules.online.desform.b.e.e(var9);

            assert var11 != null;

            QueryWrapper var12 = QueryGenerator.initQueryWrapper(var1, var7.getParameterMap());
            if (JexfmBA.b(var4)) {
                var12.in("id", Arrays.asList(var4.split(",")));
            }

            String var13 = JwtUtil.getUserNameByToken(var7);
            var5 = -521;
            Result var14 = this.designFormDataService.queryPage(var2, var3, var5, var12, var6, var13);
            Object var15 = new ArrayList();
            if (var14.getResult() != null) {
                var15 = ((IPage)var14.getResult()).getRecords();
            }

            ArrayList var16 = new ArrayList(((List)var15).size());
            Iterator var17 = ((List)var15).iterator();

            String var19;
            while(var17.hasNext()) {
                DesignFormData var18 = (DesignFormData)var17.next();
                var19 = var18.getDesformDataJson();
                if (MyStringUtil.isNotBlank(var19)) {
                    var16.add(JSON.parseObject(var19).getInnerMap());
                }
            }

            org.jeecg.modules.online.desform.excel.converter.b.a(1, var16, var11.main);
            List var37 = org.jeecg.modules.online.desform.b.e.a(var11.main);
            Iterator var38 = var11.sub.keySet().iterator();

            while(var38.hasNext()) {
                var19 = (String)var38.next();
                List var20 = (List)var11.sub.get(var19);
                Iterator var21 = var16.iterator();

                while(var21.hasNext()) {
                    Map var22 = (Map)var21.next();
                    Object var23 = var22.get(var19);
                    ArrayList var24 = new ArrayList();
                    if (!oConvertUtils.isEmpty(var23)) {
                        JSONArray var25 = JSON.parseArray(URLDecoder.decode(var23.toString(), "UTF-8"));

                        for(int var26 = 0; var26 < var25.size(); ++var26) {
                            var24.add(var25.getJSONObject(var26).getInnerMap());
                        }
                    }

                    org.jeecg.modules.online.desform.excel.converter.b.a(1, var24, var20);
                    var22.put(var19, var24);
                }

                List var42 = org.jeecg.modules.online.desform.b.e.a(var20);
                ExcelExportEntity var44 = new ExcelExportEntity((String)var11.subNames.get(var19), var19);
                var44.setList(var42);
                var37.add(var44);
            }

            Workbook var39 = ExcelExportUtil.exportExcel(new ExportParams((String)null, var10), var37, var16);
            ServletOutputStream var40 = null;

            try {
                var8.setContentType("application/x-msdownload;charset=utf-8");
                String var41 = BrowserUtils.checkBrowse(var7);
                if ("MSIE".equalsIgnoreCase(var41.substring(0, 4))) {
                    var8.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(var10, "UTF-8") + ".xls");
                } else {
                    String var43 = new String(var10.getBytes(StandardCharsets.UTF_8), "ISO8859-1");
                    var8.setHeader("content-disposition", "attachment;filename=" + var43 + ".xls");
                }

                var40 = var8.getOutputStream();
                var39.write(var40);
                var8.flushBuffer();
            } catch (Exception var35) {
                b.error("--通过流的方式获取文件异常--" + var35.getMessage(), var35);
            } finally {
                if (var40 != null) {
                    try {
                        var40.close();
                    } catch (IOException var34) {
                        b.error(var34.getMessage(), var34);
                    }
                }

            }

        }
    }

    @PostMapping({"/importXls/{desformCode}"})
    public Result c(@PathVariable("desformCode") String var1, HttpServletRequest var2) {
        String var3 = TokenUtils.getTokenByRequest(var2);
        long var4 = System.currentTimeMillis();

        try {
            DesignForm var6 = this.designFormService.getByCode(var1);
            if (var6 == null) {
                return Result.error("无法导入不存在的表单");
            } else {
                String var7 = var6.getDesformName();
                JSONArray var8 = org.jeecg.modules.online.desform.b.e.b(var6);
                Map var9 = org.jeecg.modules.online.desform.b.e.a(var8);
                DesformWidgetList var10 = org.jeecg.modules.online.desform.b.e.b(var8);

                assert var10 != null;

                MultipartHttpServletRequest var11 = (MultipartHttpServletRequest)var2;
                Map var12 = var11.getFileMap();
                Iterator var13 = var12.entrySet().iterator();

                while(var13.hasNext()) {
                    Entry var14 = (Entry)var13.next();
                    MultipartFile var15 = (MultipartFile)var14.getValue();
                    ImportParams var16 = new ImportParams();
                    List var17 = ExcelImportUtil.importExcel(var15.getInputStream(), Map.class, var16);
                    b.info("[表单设计器导入] 原始数据：" + JSON.toJSONString(var17));
                    if (var17 != null) {
                        org.jeecg.modules.online.desform.b.d var18 = new org.jeecg.modules.online.desform.b.d(var10, var17, var9);
                        b.info("[表单设计器导入] 实际数据：" + JSON.toJSONString(var18.b));
                        this.designFormDataService.saveBatchByImport(var6, var18.b, var3);
                    } else {
                        b.error("识别模版数据错误");
                    }
                }

                b.info("= = = = = 表单设计器（" + var7 + "）导入数据完成，耗时:" + (System.currentTimeMillis() - var4) + "ms = = = = =");
                return Result.ok("导入成功");
            }
        } catch (Exception var19) {
            b.error(var19.getMessage(), var19);
            return Result.error(var19.getMessage());
        }
    }

    private void a(JSONObject var1, JSONArray var2) {
        HashMap var3 = new HashMap();
        org.jeecg.modules.online.desform.b.e.a(var2, (var3x, var4, var5) -> {
            if (!org.jeecg.modules.online.desform.b.e.a(var3x)) {
                String var6 = var4.getString("model");
                Object var7 = var1.get(var6);
                String var8;
                if (var7 != null && MyStringUtil.isNotBlank(var8 = var7.toString())) {
                    JSONObject var9 = var4.getJSONObject("options");
                    String var10 = var9.getString("dictCode");
                    if (MyStringUtil.isNotBlank(var10)) {
                        List var11 = (List)var3.get(var10);
                        if (var11 == null) {
                            var11 = this.sysBaseAPI.queryDictItemsByCode(var10);
                            var3.put(var10, var11);
                        }

                        Iterator var12 = var11.iterator();

                        while(var12.hasNext()) {
                            DictModel var13 = (DictModel)var12.next();
                            if (var8.equals(var13.getText())) {
                                var1.put(var6, var13.getValue());
                            }
                        }
                    }
                }
            }

        });
    }
}
