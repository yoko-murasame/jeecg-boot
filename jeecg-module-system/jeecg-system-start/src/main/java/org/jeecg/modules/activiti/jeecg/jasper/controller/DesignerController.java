package org.jeecg.modules.activiti.jeecg.jasper.controller;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.common.util.TokenUtils;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.extbpm.process.entity.ExtActProcess;
import org.jeecg.modules.extbpm.process.mapper.ExtActProcessMapper;
import org.jeecg.modules.extbpm.process.service.IExtActProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller("designerController")
@RequestMapping({"/act/designer"})
public class DesignerController {
    private static final Log a = LogFactory.getLog(DesignerController.class);
    @Autowired
    private IExtActProcessService extActProcessService;
    @Autowired
    private ExtActProcessMapper extActProcessMapper;
    @Autowired
    private ISysBaseAPI sysBaseAPI;
    @Autowired
    @Lazy
    private RedisUtil redisUtil;

    public DesignerController() {
    }

    @RequestMapping
    public void a(HttpServletRequest var1, HttpServletResponse var2) {
        String var3 = oConvertUtils.getString(var1.getParameter("id"), "0");
        String var4 = oConvertUtils.getString(var1.getParameter("token"));
        a.info(" index 登录令牌token： " + var4);
        TokenUtils.verifyToken(var1, this.sysBaseAPI, this.redisUtil);
        var1.setAttribute("processid", var3);
        var1.setAttribute("token", var4);

        try {
            var1.getRequestDispatcher("/designer/index.jsp").forward(var1, var2);
        } catch (Exception var6) {
            var6.printStackTrace();
        }

    }

    @RequestMapping({"processProperties"})
    public void b(HttpServletRequest var1, HttpServletResponse var2) throws ServletException, IOException {
        String var3 = oConvertUtils.getString(var1.getParameter("turn"));
        String var4 = oConvertUtils.getString(var1.getParameter("id"));
        String var5 = oConvertUtils.getString(var1.getParameter("checkbox"));
        String var6 = oConvertUtils.getString(var1.getParameter("processId"));
        String var7 = oConvertUtils.getString(var1.getParameter("processDefinitionId"));
        a.info(" 流程设计属性页面 turn： " + var3 + ",节点ID: " + var4 + ",checkbox: " + var5 + ",processId: " + var6);
        ExtActProcess var8 = (ExtActProcess)this.extActProcessService.getById(var6);
        if (var8 != null) {
            var1.setAttribute("typeId", var8.getProcessType());
        } else {
            var8 = (ExtActProcess)this.extActProcessService.getById(var7);
            if (var8 != null) {
                var1.setAttribute("typeId", var8.getProcessType());
            } else {
                var1.setAttribute("typeId", "test");
            }
        }

        var1.setAttribute("checkbox", var5);
        var1.setAttribute("id", var4);
        var1.setAttribute("proTypeList", this.extActProcessMapper.getDictItems("bpm_process_type"));
        var1.setAttribute("processId", var6);
        var1.getRequestDispatcher("/designer/" + var3 + ".jsp").forward(var1, var2);
    }

    @RequestMapping({"goExpression"})
    public void c(HttpServletRequest var1, HttpServletResponse var2) throws ServletException, IOException {
        String var3 = oConvertUtils.getString(var1.getParameter("id"));
        String var4 = oConvertUtils.getString(var1.getParameter("checkbox"));
        String var5 = oConvertUtils.getString(var1.getParameter("processId"));
        var1.setAttribute("checkbox", var4);
        var1.setAttribute("id", var3);
        var1.setAttribute("processId", var5);
        String var6 = oConvertUtils.getString(var1.getParameter("token"));
        a.debug(" index 登录令牌token： " + var6);
        var1.setAttribute("token", var6);
        var1.getRequestDispatcher("/designer/selectExpressionList.jsp").forward(var1, var2);
    }

    @RequestMapping({"goSearchSelectUser"})
    public void d(HttpServletRequest var1, HttpServletResponse var2) throws ServletException, IOException {
        String var3 = oConvertUtils.getString(var1.getParameter("id"));
        String var4 = oConvertUtils.getString(var1.getParameter("checkbox"));
        String var5 = oConvertUtils.getString(var1.getParameter("processId"));
        var1.setAttribute("checkbox", var4);
        var1.setAttribute("id", var3);
        var1.setAttribute("processId", var5);
        String var6 = oConvertUtils.getString(var1.getParameter("token"));
        a.debug(" index 登录令牌token： " + var6);
        var1.setAttribute("token", var6);
        var1.getRequestDispatcher("/designer/searchSelectUserList.jsp").forward(var1, var2);
    }

    @RequestMapping({"goSubParameterList"})
    public void e(HttpServletRequest var1, HttpServletResponse var2) throws ServletException, IOException {
        String var3 = oConvertUtils.getString(var1.getParameter("id"));
        String var4 = oConvertUtils.getString(var1.getParameter("checkbox"));
        String var5 = oConvertUtils.getString(var1.getParameter("processId"));
        var1.setAttribute("checkbox", var4);
        var1.setAttribute("id", var3);
        var1.setAttribute("processId", var5);
        var1.getRequestDispatcher("/designer/subParameterList.jsp").forward(var1, var2);
    }

    @RequestMapping({"goListeners"})
    public void f(HttpServletRequest var1, HttpServletResponse var2) throws ServletException, IOException {
        String var3 = oConvertUtils.getString(var1.getParameter("typeid"));
        a.info(" 流程监听类型 typeid： " + var3);
        var1.setAttribute("typeid", var3);
        String var4 = oConvertUtils.getString(var1.getParameter("token"));
        a.debug(" index 登录令牌token： " + var4);
        var1.setAttribute("token", var4);
        var1.getRequestDispatcher("/designer/selectProcessListenerList.jsp").forward(var1, var2);
    }
}
