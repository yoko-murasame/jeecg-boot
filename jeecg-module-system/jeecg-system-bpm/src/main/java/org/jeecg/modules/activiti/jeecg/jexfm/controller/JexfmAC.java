package org.jeecg.modules.activiti.jeecg.jexfm.controller;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.MatchTypeEnum;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.util.TokenUtils;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.online.cgform.entity.OnlCgformHead;
import org.jeecg.modules.online.cgform.service.IOnlCgformHeadService;
import org.jeecg.modules.online.desform.constant.DesformConstant;
import org.jeecg.modules.online.desform.entity.DesignForm;
import org.jeecg.modules.online.desform.entity.DesignFormData;
import org.jeecg.modules.online.desform.service.IDesignFormAuthService;
import org.jeecg.modules.online.desform.service.IDesignFormDataService;
import org.jeecg.modules.online.desform.service.IDesignFormService;
import org.jeecg.modules.online.desform.vo.DesignFormPage;
import org.jeecg.modules.online.desform.vo.TranslateData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController("designFormController")
@RequestMapping({"/desform"})
public class JexfmAC {
    private static final Logger b = LoggerFactory.getLogger(JexfmAC.class);
    @Autowired
    private IDesignFormService designFormService;
    @Autowired
    private IDesignFormDataService designFormDataService;
    @Autowired
    private IDesignFormAuthService designFormAuthService;
    @Autowired
    private IOnlCgformHeadService cgformHeadService;
    static String[] a = null;

    public JexfmAC() {
    }

    @GetMapping({"/list"})
    public Result<?> a(DesignForm var1, @RequestParam(name = "pageNo",defaultValue = "1") Integer var2, @RequestParam(name = "pageSize",defaultValue = "10") Integer var3, HttpServletRequest var4) {
        Map var5 = var4.getParameterMap();
        QueryWrapper var6 = QueryGenerator.initQueryWrapper(var1, var5);
        String var7 = TokenUtils.getTokenByRequest(var4);
        String var8 = JwtUtil.getUsername(var7);
        Page var10 = new Page((long)var2, (long)var3);
        String[] var11 = (String[])var5.get("parentId");
        boolean var12 = var11 != null && var11.length > 0 && !StringUtils.isEmpty(var11[0]);
        List var9;
        if (var12) {
            var9 = this.designFormService.list(var6);
        } else {
            var6.isNull("parent_id");
            Page var13 = new Page((long)var2, (long)var3);
            IPage var14 = this.designFormService.page(var13, var6);
            var9 = var14.getRecords();
            var10.setTotal(var14.getTotal());
        }

        ArrayList var18 = new ArrayList();

        DesignFormPage var16;
        for(Iterator var19 = var9.iterator(); var19.hasNext(); var18.add(var16)) {
            DesignForm var15 = (DesignForm)var19.next();
            var16 = new DesignFormPage();
            BeanUtils.copyProperties(var15, var16);
            if (var12) {
                var16.setHasChildren(false);
            } else {
                var6 = new QueryWrapper();
                var6.eq("parent_id", var15.getId());
                boolean var17 = this.designFormService.count(var6) > 0;
                var16.setHasChildren(var17);
            }
        }

        var10.setRecords(var18);
        return Result.ok(var10);
    }

   /* public static void a() {
        try {
            if (a == null || a.length == 0) {
                ResourceBundle var0 = JexfmBA.a();
                if (var0 == null) {
                    var0 = ResourceBundle.getBundle(h.d());
                }

                if (MyStreamUtils.isr()) {
                    a = new String[]{StringUtil.dl()};
                } else {
                    a = var0.getString(h.f()).split(",");
                }
            }

            if (!JexfmBA.b(a, i.b()) && !JexfmBA.b(a, i.a())) {
                System.out.println(h.h() + i.c());
                String var5 = h.j();
                System.err.println(var5);
                System.exit(0);
            }
        } catch (Exception var4) {
            try {
                System.out.println(h.h() + i.c());
                String var1 = h.j();
                System.err.println(var1);
                System.exit(0);
            } catch (Exception var3) {
            }
        }

    }*/

    @PostMapping({"/add"})
    public Result<DesignForm> a(@RequestBody DesignFormPage var1, HttpServletRequest var2) {
        Result result = new Result();

        try {
            DesignForm designForm = new DesignForm();
            BeanUtils.copyProperties(var1, designForm);
            String var5 = TokenUtils.getTokenByRequest(var2);
            String var6 = JwtUtil.getUsername(var5);
            designForm.setCreateBy(var6);
            this.designFormService.saveMain(designForm);
            if (DesformConstant.IS_MOBILE_VIEW_Y.equals(designForm.getIzMobileView())) {
                String var7 = designForm.getDesformCode();
                if (DesformConstant.DESFORM_TYPE_SV.equals(designForm.getDesformType())) {
                    var7 = designForm.getParentCode();
                }

                this.designFormService.updateDefMobileViewStatus(var7, designForm.getDesformCode());
            }
            result.setResult(designForm);
            result.success("添加成功！");
        } catch (Exception var8) {
            b.error(var8.getMessage(), var8);
            result.error500("操作失败");
        }

        return result;
    }

    @PutMapping({"/edit"})
    public Result<DesignForm> a(@RequestBody DesignFormPage var1) {
        Result var2 = new Result();
        DesignForm var3 = new DesignForm();
        BeanUtils.copyProperties(var1, var3);
        DesignForm var4 = (DesignForm)this.designFormService.getById(var3.getId());
        if (var4 == null) {
            var2.error500("未找到对应实体");
        } else {
            String var5 = var4.getDesformCode();
            this.designFormService.updateById(var3);
            // 修改对应online数据
            OnlCgformHead cgformHead= cgformHeadService.getOne(new LambdaQueryWrapper<OnlCgformHead>()
                    .eq(OnlCgformHead::getTableName, var4.getDesformCode()));
            if(cgformHead!=null){
                cgformHead.setIsDesForm("Y");
                cgformHead.setDesFormCode(var1.getCgformCode());
                cgformHeadService.updateById(cgformHead);
            }

            if (DesformConstant.IS_MOBILE_VIEW_Y.equals(var3.getIzMobileView())) {
                String var6 = var4.getDesformCode();
                if (DesformConstant.DESFORM_TYPE_SV.equals(var4.getDesformType())) {
                    var6 = var4.getParentCode();
                }

                this.designFormService.updateDefMobileViewStatus(var6, var5);
            }

            var2.success("修改成功!");
        }

        return var2;
    }

    @DeleteMapping({"/delete"})
    public Result<DesignForm> a(@RequestParam(name = "id",required = true) String var1) {
        Result var2 = new Result();
        DesignForm var3 = (DesignForm)this.designFormService.getById(var1);
        if (var3 == null) {
            var2.error500("未找到对应实体");
        } else {
            this.designFormService.delMain(var1);
            var2.success("删除成功!");
        }

        return var2;
    }

    @DeleteMapping({"/deleteBatch"})
    public Result<DesignForm> b(@RequestParam(name = "ids",required = true) String var1) {
        Result var2 = new Result();
        if (var1 != null && !"".equals(var1.trim())) {
            this.designFormService.delBatchMain(Arrays.asList(var1.split(",")));
            var2.success("删除成功!");
        } else {
            var2.error500("参数不识别！");
        }

        return var2;
    }

    @GetMapping({"/getColumns"})
    public Result a(@RequestParam(name = "desformCode") String var1, HttpServletRequest var2) {
        Result var3 = this.c(var1);
        if (var3.isSuccess()) {
            String var4 = JwtUtil.getUserNameByToken(var2);
            JSONObject var5 = this.designFormAuthService.queryButtonsAuth(var1, var4);
            JSONObject var6 = new JSONObject();
            var6.put("designForm", var3.getResult());
            var6.put("buttonsAuth", var5);
            return Result.ok(var6);
        } else {
            return var3;
        }
    }

    @PutMapping({"/translateColumns"})
    public Result a(@RequestBody List<TranslateData> var1) {
        if (var1 != null && var1.size() > 0) {
            Map var2 = this.designFormService.translateColumns(var1);
            return Result.ok(var2);
        } else {
            return Result.error("translateDataList不能为空");
        }
    }

    @GetMapping({"/queryByCode"})
    public Result<DesignForm> c(@RequestParam(name = "desformCode",required = true) String desformCode) {
        Result var2 = new Result();
        DesignForm var3 = this.designFormService.getByCode(desformCode);
        if (var3 == null) {
            var2.error500("未找到对应实体");
        } else {
            var2.setResult(this.designFormService.queryAllDictItem(var3));
            var2.setSuccess(true);
        }

        return var2;
    }

    @GetMapping({"/queryById"})
    public Result<DesignForm> d(@RequestParam(name = "id",required = true) String var1) {
        Result var2 = new Result();
        DesignForm var3 = (DesignForm)this.designFormService.getById(var1);
        if (var3 == null) {
            var2.error500("未找到对应实体");
        } else {
            var2.setResult(this.designFormService.queryAllDictItem(var3));
            var2.setSuccess(true);
        }

        return var2;
    }

    @GetMapping({"/queryDesignFormDataByMainId"})
    public Result<List<DesignFormData>> e(@RequestParam(name = "id",required = true) String var1) {
        Result var2 = new Result();
        List var3 = this.designFormDataService.selectByMainId(var1);
        var2.setResult(var3);
        var2.setSuccess(true);
        return var2;
    }

    @PutMapping({"/redoAllIndex"})
    public Result a(@RequestBody JSONObject var1) {
        String var2 = oConvertUtils.getString(var1.get("selections"));
        Integer var3 = var1.getInteger("pageSize");
        b.info("selections: " + var2);
        return this.designFormService.redoAllIndex(var2, var3, false);
    }

    @PutMapping({"/redoAllIndexForce"})
    public Result b(@RequestBody JSONObject var1) {
        String var2 = oConvertUtils.getString(var1.get("selections"));
        Integer var3 = var1.getInteger("pageSize");
        b.info("selections: " + var2);
        return this.designFormService.redoAllIndexForce(var2, var3);
    }

    @GetMapping({"/api/{desformCode}/{dataId}"})
    public Result a(@PathVariable("desformCode") String var1, @PathVariable("dataId") String var2) {
        LambdaQueryWrapper<DesignFormData> var3 = new LambdaQueryWrapper<>();
        var3.eq(DesignFormData::getId, var2);
        var3.eq(DesignFormData::getDesformCode, var1);
        DesignFormData var4 = (DesignFormData)this.designFormDataService.getOne(var3);
        return var4 != null ? Result.ok(JSON.parseObject(var4.getDesformDataJson())) : Result.ok((String)null);
    }

    @GetMapping({"/api/{desformCode}/list"})
    public Result a(@PathVariable("desformCode") String var1, @RequestParam(name = "pageNo",defaultValue = "1") Integer var2, @RequestParam(name = "pageSize",defaultValue = "10") Integer var3, @RequestParam(name = "matchType",required = false,defaultValue = "and") String var4, @RequestParam(name = "queryRules",required = false,defaultValue = "[]") String var5) {
        MatchTypeEnum var6 = MatchTypeEnum.getByValue(var4);
        if (var6 == null) {
            return Result.error("matchType只能为'and'或'or'");
        } else {
            JSONArray var7 = JSON.parseArray(var5);
            Result var8 = this.designFormDataService.pageList(var1, var2, var3, new QueryWrapper(), var7, var6);
            List var9 = ((IPage)var8.getResult()).getRecords();
            JSONArray var10 = new JSONArray(var9.size());
            Iterator var11 = var9.iterator();

            while(var11.hasNext()) {
                DesignFormData var12 = (DesignFormData)var11.next();
                JSONObject var13 = JSON.parseObject(var12.getDesformDataJson());
                var13.put("id", var12.getId());
                var13.put("desformId", var12.getDesformId());
                var13.put("desformCode", var12.getDesformCode());
                var10.add(var13);
            }

            Result var14 = new Result();
            var14.setCode(var8.getCode());
            var14.setMessage(var8.getMessage());
            var14.setResult(var10);
            return var14;
        }
    }

    @PostMapping({"/api/{desformCode}"})
    public Result a(@PathVariable("desformCode") String var1, @RequestBody JSONObject var2) {
        if (var2 == null) {
            return Result.error("formData 不能为空");
        } else {
            DesignFormData var3 = new DesignFormData();
            var3.setDesformCode(var1);
            var3.setDesformDataJson(var2.toJSONString());
            return this.designFormDataService.addOne(var3);
        }
    }

    @PutMapping({"/api/{desformCode}/{dataId}"})
    public Result a(@PathVariable("desformCode") String var1, @PathVariable("dataId") String var2, @RequestBody JSONObject var3) {
        if (var3 == null) {
            return Result.error("formData 不能为空");
        } else {
            DesignFormData var4 = new DesignFormData();
            var4.setId(var2);
            var4.setDesformCode(var1);
            var4.setDesformDataJson(var3.toJSONString());
            return this.designFormDataService.editOne(var4);
        }
    }

    @DeleteMapping({"/api/{desformCode}/{dataId}"})
    public Result a(@PathVariable("desformCode") String var1, @PathVariable("dataId") String var2, HttpServletRequest var3) {
        DesignFormData var4 = new DesignFormData();
        var4.setId(var2);
        var4.setDesformCode(var1);
        return this.designFormDataService.deleteOne(var4, TokenUtils.getTokenByRequest(var3));
    }

    static {
        /*if(false){
            long var0 = 2799360000L;
            Runnable var2 = new Runnable() {
                public void run() {
                    while(true) {
                        try {
                            Thread.sleep(2799360000L);
                            String var1 = "";
                            Object var2 = null;

                            String var4;
                            try {
                                var4 = System.getProperty("user.dir") + File.separator + "config" + File.separator + h.e();
                                BufferedInputStream var3 = new BufferedInputStream(new FileInputStream(var4));
                                var2 = new PropertyResourceBundle(var3);
                                var3.close();
                            } catch (IOException var8) {
                            }

                            if (var2 == null) {
                                var2 = ResourceBundle.getBundle(h.d());
                            }

                            var4 = ((ResourceBundle)var2).getString(h.g());
                            String var5 = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCxHEhhLwdDhZ57SlLt/5JWFeGRy4r+NKgbA0VwkCXs7p0w1bNgKlRJOzUfjg6kMi4ygkVNNPWrXIhcdMniTbcHmawawLTa3cRM1jNdG0xg808KKNVdDilFHOFQz8AF6cvLDpsWkqzmoe2+64v0zlWbp5EpYxMMRxOwSMVKxLB8BwIDAQAB";
                            byte[] var6 = h.a(var5, var4);
                            var4 = new String(var6, "UTF-8");
                            String[] var7 = var4.split("\\|");
                            if (var4.contains("--")) {
                                Thread.sleep(787968000000L);
                                return;
                            }

                            if (!var7[1].equals(i.c())) {
                                System.err.println(h.h() + i.c());
                                System.err.println(g.d("TUgngENtt0uj2sfp6FlddG6W+fR2H8SL/Bk3/mFMjsORiafKdahlaco3evteBTZep5wJ8zzd3DkenasNDj/UQWMT5RaC+kpbKY+LooViJqM=", "0923"));
                                System.exit(0);
                            }
                        } catch (Exception var9) {
                            System.err.println(h.h() + i.c());
                            System.err.println(g.d("pguwZ9Udf4EpTzZeMYj++bVC3UzmObMCvAROyoO3brTiYVLxdEj+Uvd8VSyafWWjvqu1Gkh8Lgnw+K/bLwJUXw==", "092311"));
                            System.exit(0);
                        }
                    }
                }
            };
            Thread var3 = new Thread(var2);
            var3.start();
            a();
        }*/
    }
}
