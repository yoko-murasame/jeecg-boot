package org.jeecg.modules.activiti.jeecg.jexfm;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.modules.online.desform.b.k;
import org.jeecg.modules.online.desform.entity.DesignFormData;
import org.jeecg.modules.online.desform.service.IDesignFormDataService;
import org.jeecg.modules.online.desform.service.IDesignFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController("designFormExtController")
@RequestMapping({"/desform/ext"})
public class JexfmAE {
    @Autowired
    @Lazy
    private IDesignFormService designFormService;
    @Autowired
    @Lazy
    private IDesignFormDataService designFormDataService;
    @Value("${jeecg.desform.theme-color}")
    private String desformThemeColor;
    @Value("${jeecg.desform.upload-type}")
    private String desformUploadType;

    public JexfmAE() {
    }

    @GetMapping({"/{desformCode}"})
    public ModelAndView a(@PathVariable("desformCode") String var1, @RequestParam(name = "reuseId",required = false) String var2, ModelAndView var3, HttpServletRequest var4) {
        String var5 = "add";
        if (JexfmBA.b(var2)) {
            var5 = "reuse";
        } else {
            var2 = "add";
        }

        return this.a((ModelAndView)var3, (String)var5, (String)var1, (String)var2, (String)null, (HttpServletRequest)var4);
    }

    @GetMapping({"/{desformCode}/{dataId}"})
    public ModelAndView b(@PathVariable("desformCode") String var1, @PathVariable("dataId") String var2, ModelAndView var3, HttpServletRequest var4) {
        String var5 = "edit";
        return this.a((ModelAndView)var3, (String)var5, (String)var1, (String)var2, (String)null, (HttpServletRequest)var4);
    }

    @PostMapping({"/{desformCode}"})
    public Result a(@PathVariable("desformCode") String var1, @RequestBody DesignFormData var2) {
        var2.setDesformCode(var1);
        return this.designFormDataService.addOne(var2);
    }

    @PutMapping({"/{desformCode}/{dataId}"})
    public Result a(@PathVariable("desformCode") String var1, @PathVariable("dataId") String var2, @RequestBody DesignFormData var3) {
        var3.setDesformCode(var1);
        return this.designFormDataService.editOne(var3);
    }

    @GetMapping({"/success"})
    public ModelAndView c(@RequestParam("desformCode") String var1, @RequestParam("dataId") String var2, ModelAndView var3, HttpServletRequest var4) {
        ModelAndView var5 = new ModelAndView();
        var5 = this.designFormService.queryFormViewByCode(var1, var2, (String)null, var5);
        Map var6 = var5.getModel();
        JSONObject var7 = JSON.parseObject(JSON.parseObject(JSON.toJSONString(var6.get("designFormData"))).getString("desformDataJson"));
        JSONObject var8 = JSON.parseObject(JSON.toJSONString(var6.get("designForm")));
        String var9 = var8.getString("desformDesignJson");
        JSONObject var10 = JSON.parseObject(var9);
        JSONArray var11 = var10.getJSONArray("list");
        ArrayList var12 = new ArrayList();
        List var13 = Arrays.asList("button", "buttons", "grid", "card", "tabs", "text", "sub-table-design", "divider");
        org.jeecg.modules.online.desform.b.e.a(var11, (var3x, var4x, var5x) -> {
            if (var3x == null || !var13.contains(var3x.getType())) {
                JSONObject var66 = new JSONObject();
                String var7x = var4x.getString("model");
                String var88 = var7.getString(var7x);
                if (JexfmBA.b(var88)) {
                    var66.put("title", var4x.getString("name"));
                    var66.put("modal", var7x);
                    var66.put("value", var88);
                    var12.add(var66);
                }
            }

        });
        var3.addObject("widgets", JSON.toJSONString(var12));
        var3.setViewName("desform/result/success");
        return var3;
    }

    private String a(HttpServletRequest var1) {
        String var2 = var1.getScheme();
        String var3 = var1.getServerName();
        int var4 = var1.getServerPort(); //8989
        String var5 = var1.getContextPath();
        // return var2 + "://" + var3 + ":" + var4 + var5;
        return var5;
    }

    private ModelAndView a(ModelAndView var1, String var2, String var3, String var4, String var5, HttpServletRequest var6) {
        try {
            var1 = this.designFormService.queryFormViewByCode(var3, var4, var5, true, var1);
            var1.addObject("action", "reuse".equals(var2) ? "add" : var2);
            var1.addObject("themeColor", this.desformThemeColor);
            var1.addObject("uploadType", this.desformUploadType);
            var1.addObject("baseURL", this.a(var6));
            var1.addObject("qiniuConfig", k.getConfig().toJSONString());
            var1.addObject("mockToken", org.jeecg.modules.online.desform.b.e.getMockToken());
            var1.setViewName("desform/formGenerateExternal");
            return var1;
        } catch (JeecgBootException var8) {
            var1.addObject("message", var8.getMessage());
        } catch (Exception var9) {
            var9.printStackTrace();
            var1.addObject("message", var9.getMessage());
        }

        var1.setViewName("desform/error");
        return var1;
    }
}
