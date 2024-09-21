package org.jeecg.modules.activiti.jeecg.jexfm.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.util.*;
import org.jeecg.modules.activiti.jeecg.commons.lang.MyStringUtil;
import org.jeecg.modules.online.desform.b.k;
import org.jeecg.modules.online.desform.entity.DesignForm;
import org.jeecg.modules.online.desform.entity.DesignFormData;
import org.jeecg.modules.online.desform.entity.DesignFormRoute;
import org.jeecg.modules.online.desform.entity.DesignFormTemplet;
import org.jeecg.modules.online.desform.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController("designFormApiController")
@RequestMapping({"/desform"})
public class JexfmAA {
    private static final Logger a = LoggerFactory.getLogger(JexfmAA.class);
    @Autowired
    private IDesignFormService designFormService;
    @Autowired
    private IDesignFormTempletService designFormTempletService;
    @Autowired
    private ISysBaseAPI sysBaseAPI;
    @Autowired
    @Lazy
    private RedisUtil redisUtil;
    @Autowired
    private IDesignFormUrlAuthService designFormUrlAuthService;
    @Autowired
    private IDesignFormAuthService designFormAuthService;
    @Autowired
    private IDesignFormRouteService designFormRouteService;
    @Autowired
    private IDesignFormDataService designFormDataService;
    @Value("${jeecg.desform.theme-color:#1890ff}")
    private String desformThemeColor;
    @Value("${jeecg.desform.upload-type:system}")
    private String desformUploadType;

    public JexfmAA() {
    }

    @GetMapping({"/getQiniuUploadToken"})
    public Result<?> getQiniuUploadToken() {
        return Result.ok(k.getUploadToken());
    }

    private String a(HttpServletRequest var1) {
        String var2 = var1.getHeader("X_GATEWAY_BASE_PATH");
        if (oConvertUtils.isNotEmpty(var2)) {
            a.info("x_gateway_base_path = " + var2);
            return var2;
        } else {
            String var3 = var1.getScheme();
            String var4 = var1.getServerName();
            int var5 = var1.getServerPort(); //8989
            String var6 = var1.getContextPath();
            // return var3 + "://" + var4 + ":" + var5 + var6;
            return var6;
        }
    }

    public ModelAndView a(ModelAndView var1, HttpServletRequest var2) {
        try {
            TokenUtils.verifyToken(var2, this.sysBaseAPI, this.redisUtil);
            var1.addObject("baseURL", this.a(var2));
            // var1.addObject("qiniuConfig", k.getConfig().toJSONString());
            var1.addObject("themeColor", this.desformThemeColor);
            var1.addObject("uploadType", this.desformUploadType);
            var1.setViewName("desform/designForm");
        } catch (Exception var4) {
            var4.printStackTrace();
            var1.addObject("message", var4.getMessage());
            var1.setViewName("desform/error");
        }

        return var1;
    }

    @RequestMapping({"/index"})
    public ModelAndView b(ModelAndView var1, HttpServletRequest var2) {
        return this.a(var1, var2);
    }

    @RequestMapping({"/index/{id}"})
    public ModelAndView a(@PathVariable("id") String var1, @RequestParam("isTemplet") Boolean var2, ModelAndView var3, HttpServletRequest var4) {
        if (!MyStringUtil.isEmpty(var1)) {
            if (var2 != null && var2) {
                DesignFormTemplet var6 = (DesignFormTemplet)this.designFormTempletService.getById(var1);
                if (var6 != null) {
                    var3.addObject("designJson", var6.getTempletJson());
                }
            } else {
                DesignForm var5 = (DesignForm)this.designFormService.getById(var1);
                if (var5 != null) {
                    var3.addObject("desform", var5);
                    var3.addObject("desformName", var5.getDesformName());
                    var3.addObject("designJson", var5.getDesformDesignJson());
                }
            }
        }

        return this.a(var3, var4);
    }

    private ModelAndView a(ModelAndView var1, String var2, String var3, String var4, String var5, HttpServletRequest var6) {
        try {
            TokenUtils.verifyToken(var6, this.sysBaseAPI, this.redisUtil);
            if (this.designFormUrlAuthService.urlIsPassed(var3, var2)) {
                var1.addObject("action", var2);
                var1.addObject("themeColor", this.desformThemeColor);
                var1.addObject("uploadType", this.desformUploadType);
                var1.addObject("baseURL", this.a(var6));
                // var1.addObject("qiniuConfig", k.getConfig().toJSONString());
                if ("view".equals(var2)) {
                    var1 = this.designFormService.queryFormViewById(var3, var4, var5, var1);
                } else {
                    boolean var7 = BrowserUtils.isMobile(var6);
                    if (var7) {
                        var1 = this.designFormService.queryFormAutoViewByCode(var3, var4, var5, var1);
                    } else {
                        var1 = this.designFormService.queryFormViewByCode(var3, var4, var5, var1);
                    }
                }

                String var17 = var6.getParameter("token");
                String var8 = JwtUtil.getUsername(var17);
                JSONObject var9 = this.designFormAuthService.getUserInfoByUsername(var8);
                var1.addObject("authUserInfoJson", var9.toJSONString());
                String var10 = var6.getParameter("taskId");
                List var11;
                if (MyStringUtil.isEmpty(var10)) {
                    var11 = this.designFormAuthService.queryByCode(var3);
                } else {
                    var11 = this.designFormAuthService.queryByTaskId(var3, var10);
                }

                var1.addObject("authInfoJson", JSON.toJSONString(var11));
                DesignFormRoute var12 = this.designFormRouteService.getEffectiveRoute(var3);
                if (var12 != null) {
                    var1.addObject("nextRouteConfig", JSON.toJSONString(var12));
                }

                String var13 = var6.getParameter("routeDataId");
                if (MyStringUtil.isNotBlank(var13)) {
                    DesignFormData var14 = (DesignFormData)this.designFormDataService.getById(var13);
                    if (var14 != null) {
                        var1.addObject("routeData", var14.getDesformDataJson());
                    }
                }

                var1.setViewName("desform/formGenerate");
                return var1;
            }

            var1.addObject("message", "无打开此链接的权限");
        } catch (JeecgBootException var15) {
            var1.addObject("message", var15.getMessage());
        } catch (Exception var16) {
            var16.printStackTrace();
            var1.addObject("message", var16.getMessage());
        }

        var1.setViewName("desform/error");
        return var1;
    }

    @RequestMapping({"/view/{desformId}/{dataId}"})
    public ModelAndView a(@PathVariable("desformId") String var1, @PathVariable("dataId") String var2, ModelAndView var3, HttpServletRequest var4) {
        String var5 = "view";
        return this.a(var3, var5, var1, var2, (String)null, var4);
    }

    @RequestMapping({"/add/{desformCode}"})
    public ModelAndView a(@PathVariable("desformCode") String var1, ModelAndView var2, HttpServletRequest var3) {
        String var4 = "add";
        return this.a(var2, var4, var1, "add", (String)null, var3);
    }

    @RequestMapping({"/edit/{desformCode}/{dataId}"})
    public ModelAndView b(@PathVariable("desformCode") String var1, @PathVariable("dataId") String var2, ModelAndView var3, HttpServletRequest var4) {
        String var5 = "edit";
        return this.a(var3, var5, var1, var2, (String)null, var4);
    }

    @RequestMapping({"/edit/online/{desformCode}/{onlineDataId}"})
    public ModelAndView c(@PathVariable("desformCode") String var1, @PathVariable("onlineDataId") String var2, ModelAndView var3, HttpServletRequest var4) {
        String var5 = "edit";
        return this.a(var3, var5, var1, (String)null, var2, var4);
    }

    @RequestMapping({"/detail/{desformCode}/{dataId}"})
    public ModelAndView d(@PathVariable("desformCode") String var1, @PathVariable("dataId") String var2, ModelAndView var3, HttpServletRequest var4) {
        var3.addObject("isReadOnly", true);
        String var5 = "detail";
        return this.a(var3, var5, var1, var2, (String)null, var4);
    }

    @RequestMapping({"/detail/online/{desformCode}/{onlineDataId}"})
    public ModelAndView e(@PathVariable("desformCode") String var1, @PathVariable("onlineDataId") String var2, ModelAndView var3, HttpServletRequest var4) {
        var3.addObject("isReadOnly", true);
        String var5 = "detail";
        return this.a(var3, var5, var1, (String)null, var2, var4);
    }

    @RequestMapping({"/customUrlTest"})
    public Result a(@RequestBody JSONObject var1, HttpServletRequest var2) {
        boolean var3 = HttpMethod.POST.matches(var2.getMethod());
        if (var3) {
            String var4 = TokenUtils.getTokenByRequest(var2);
            JSONObject var5 = var1.getJSONObject("desformDataJson");
            JSONObject var6 = new JSONObject();
            var6.put("name", var5.getString("name"));
            var6.put("sex", var5.getString("sex"));
            var6.put("age", var5.getString("age"));
            return RestDesformUtil.addOne("extract_test_staff", var6, var4);
        } else {
            return Result.ok();
        }
    }
}
