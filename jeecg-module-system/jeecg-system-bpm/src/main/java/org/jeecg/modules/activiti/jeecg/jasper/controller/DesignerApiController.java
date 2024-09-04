package org.jeecg.modules.activiti.jeecg.jasper.controller;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.io.IOUtils;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.common.util.TokenUtils;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.activiti.jeecg.commons.lang.MyStreamUtils;
import org.jeecg.modules.extbpm.process.entity.ExtActListener;
import org.jeecg.modules.extbpm.process.entity.ExtActProcess;
import org.jeecg.modules.extbpm.process.pojo.UserInfo;
import org.jeecg.modules.extbpm.process.service.IExtActExpressionService;
import org.jeecg.modules.extbpm.process.service.IExtActListenerService;
import org.jeecg.modules.extbpm.process.service.IExtActProcessService;
import org.jeecgframework.designer.vo.AjaxJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.List;

@RestController("designerApiController")
@RequestMapping({"/act/designer/api"})
public class DesignerApiController {
    private static final Logger a = LoggerFactory.getLogger(DesignerApiController.class);
    @Autowired
    private IExtActProcessService extActProcessService;
    @Autowired
    private IExtActListenerService extActListenerService;
    @Autowired
    private IExtActExpressionService extActExpressionService;
    @Autowired
    private ISysBaseAPI sysBaseAPI;
    @Autowired
    @Lazy
    private RedisUtil redisUtil;

    public DesignerApiController() {
    }

    @RequestMapping({"/getProcessXml"})
    @ResponseBody
    public void a(HttpServletRequest var1, HttpServletResponse var2) {
        var2.setContentType("text/xml;charset=UTF-8");
        String var3 = oConvertUtils.getString(var1.getParameter("processId"));
        String var4 = oConvertUtils.getString(var1.getParameter("token"));
        a.info(" getProcessXml 登录令牌token： " + var4);
        TokenUtils.verifyToken(var1, this.sysBaseAPI, this.redisUtil);
        ExtActProcess var5 = (ExtActProcess)this.extActProcessService.getById(var3);
        String var6 = null;

        try {
            var6 = MyStreamUtils.InputStreamTOString(MyStreamUtils.byteTOInputStream(var5.getProcessXml()));
            var2.getWriter().write(var6);
        } catch (Exception var8) {
            var8.printStackTrace();
        }

    }

    @RequestMapping(
            value = {"/getExpressions"},
            method = {RequestMethod.POST, RequestMethod.GET}
    )
    public String a(HttpServletRequest var1) {
        String var2 = oConvertUtils.getString(var1.getParameter("token"));
        a.debug(" getExpressions 登录令牌token： " + var2);
        List var3 = this.extActExpressionService.list();
        HashMap var4 = new HashMap();
        var4.put("total", var3.size());
        var4.put("rows", var3);
        JSONObject var5 = new JSONObject(var4);
        a.info(var5.toJSONString());
        return var5.toJSONString();
    }

    @RequestMapping(
            value = {"/getDefaultListener"},
            method = {RequestMethod.POST, RequestMethod.GET}
    )
    public String b(HttpServletRequest var1) {
        String var2 = oConvertUtils.getString(var1.getParameter("token"));
        a.debug(" getDefaultListener 登录令牌token： " + var2);
        HashMap var3 = new HashMap();
        HashMap var4 = new HashMap();
        ExtActListener var5 = (ExtActListener)this.extActListenerService.getById("402880e54803a496014805e5d9190012");
        if (var5 != null) {
            var3.put("success", true);
            var3.put("msg", "成功获取默认监听器！");
            var4.put("id", var5.getId());
            var4.put("listenername", var5.getListenerName());
            var4.put("listenereven", var5.getListenerEvent());
            var4.put("listenertype", var5.getListenerValueType());
            var4.put("listenervalue", var5.getListenerValue());
        } else {
            var3.put("success", false);
            var3.put("msg", "获取默认监听器失败");
        }

        var3.put("obj", var4);
        JSONObject var6 = new JSONObject(var3);
        a.info(var6.toJSONString());
        return var6.toJSONString();
    }

    @RequestMapping(
            value = {"/getGroups"},
            method = {RequestMethod.POST, RequestMethod.GET}
    )
    public String c(HttpServletRequest var1) {
        String var2 = oConvertUtils.getString(var1.getParameter("token"));
        a.debug(" getGroups 登录令牌token： " + var2);
        List var3 = this.extActProcessService.getBpmRoles();
        HashMap var4 = new HashMap();
        var4.put("total", var3.size());
        var4.put("rows", var3);
        JSONObject var5 = new JSONObject(var4);
        a.info(var5.toJSONString());
        return var5.toJSONString();
    }

    @RequestMapping(
            value = {"/getUsers"},
            method = {RequestMethod.POST, RequestMethod.GET}
    )
    public String getUsers(HttpServletRequest request) {
        String token = oConvertUtils.getString(request.getParameter("token"));
        a.debug(" getUsers 登录令牌token： " + token);
        HashMap<String, Object> result = new HashMap<>();
        // 查完整分页太大了，只查100条
        // List<UserInfo> users = this.extActProcessService.getBpmUsers();
        // result.put("total", users.size());
        // result.put("rows", users);
        int pageNo = StringUtils.hasText(request.getParameter("pageNo")) ? Integer.parseInt(request.getParameter("pageNo")) : 1;
        int pageSize = StringUtils.hasText(request.getParameter("pageSize")) ? Integer.parseInt(request.getParameter("pageSize")) : 100;
        Page<UserInfo> page = new Page<>(pageNo, pageSize);
        Page<UserInfo> pageBpmUsers = this.extActProcessService.getPageBpmUsers("", "", page);
        result.put("total", pageBpmUsers.getTotal());
        result.put("rows", pageBpmUsers.getRecords());
        result.put("current", pageBpmUsers.getCurrent());
        result.put("size", pageBpmUsers.getSize());
        result.put("pages", pageBpmUsers.getPages());
        // JSONObject var5 = new JSONObject(result);
        // a.info(var5.toJSONString());
        return JSONObject.toJSONString(result);
    }

    @RequestMapping(
            value = {"/getPageUsers"},
            method = {RequestMethod.POST, RequestMethod.GET}
    )
    public String e(HttpServletRequest var1) {
        String var2 = oConvertUtils.getString(var1.getParameter("token"));
        a.debug(" getPageUsers 登录令牌token： " + var2);
        String var3 = var1.getParameter("page");
        String var4 = var1.getParameter("rows");
        String var5 = var1.getParameter("account");
        String var6 = var1.getParameter("name");
        a.debug("pageNumber: " + oConvertUtils.getInt(var3, 1));
        a.debug("pageSize: " + oConvertUtils.getInt(var4, 10));
        a.debug("account: " + var5);
        a.debug("name: " + var6);
        Page var7 = new Page((long)oConvertUtils.getInt(var3, 1), (long)oConvertUtils.getInt(var4, 10));
        Page var8 = this.extActProcessService.getPageBpmUsers(var5, var6, var7);
        HashMap var9 = new HashMap();
        var9.put("total", var8.getTotal());
        var9.put("rows", var8.getRecords());
        JSONObject var10 = new JSONObject(var9);
        a.info(var10.toJSONString());
        return var10.toJSONString();
    }

    @RequestMapping({"/getListenersByIds"})
    public String b(HttpServletRequest var1, HttpServletResponse var2) {
        String var3 = oConvertUtils.getString(var1.getParameter("token"));
        a.debug(" getRealListenerGrid 登录令牌token： " + var3);
        String var4 = oConvertUtils.getString(var1.getParameter("ids"));
        String[] var5 = var4.split(",");
        List var6 = this.extActListenerService.getListenerInfoByIds(var5);
        HashMap var7 = new HashMap();
        var7.put("total", var6.size());
        var7.put("rows", var6);
        JSONObject var8 = new JSONObject(var7);
        a.info(var8.toJSONString());
        return var8.toJSONString();
    }

    @RequestMapping({"/getListenersByType"})
    public String c(HttpServletRequest var1, HttpServletResponse var2) {
        String var3 = oConvertUtils.getString(var1.getParameter("token"));
        a.debug(" getListenersByType 登录令牌token： " + var3);
        String var4 = oConvertUtils.getString(var1.getParameter("typeid"));
        a.info(" 流程监听类型 typeid： " + var4);
        List var5 = this.extActListenerService.getListenerInfoByType(var4);
        HashMap var6 = new HashMap();
        var6.put("total", var5.size());
        var6.put("rows", var5);
        JSONObject var7 = new JSONObject(var6);
        a.info(var7.toJSONString());
        return var7.toJSONString();
    }

    @RequestMapping({"/exportProcessDef"})
    @ResponseBody
    public AjaxJson d(HttpServletRequest var1, HttpServletResponse var2) {
        String var3 = oConvertUtils.getString(var1.getParameter("token"));
        a.debug(" exportProcessDef 登录令牌token： " + var3);
        AjaxJson var4 = new AjaxJson();
        String var5 = oConvertUtils.getString(var1.getParameter("procdefId"));
        String var6 = oConvertUtils.getString(var1.getParameter("processName"));
        LambdaQueryWrapper var7 = new LambdaQueryWrapper();
        //var7.eq(ExtActProcess::getProcessKey, var5);
        ExtActProcess var8 = (ExtActProcess)this.extActProcessService.getOne(var7);

        try {
            ByteArrayInputStream var9 = new ByteArrayInputStream(var8.getProcessXml());
            String var10 = var5 + ".bpmn20.xml";
            var2.setContentType("application/octet-stream");
            var2.setHeader("Content-Disposition", "attachment;filename=" + var10);
            IOUtils.copy(var9, var2.getOutputStream());
            var2.flushBuffer();
            var4.setMsg("导出成功!");
            var9.close();
        } catch (Exception var11) {
            var11.printStackTrace();
        }

        return null;
    }

    @RequestMapping({"/saveProcess"})
    @ResponseBody
    public AjaxJson f(HttpServletRequest var1) {
        String var2 = oConvertUtils.getString(var1.getParameter("token"));
        a.debug(" exportProcessDef 登录令牌token： " + var2);
        AjaxJson var3 = new AjaxJson();

        try {
            var3 = this.extActProcessService.saveProcess(var1);
        } catch (Exception var5) {
            var5.printStackTrace();
            var3.setMsg(var5.getMessage());
            var3.setSuccess(false);
        }

        return var3;
    }
}
