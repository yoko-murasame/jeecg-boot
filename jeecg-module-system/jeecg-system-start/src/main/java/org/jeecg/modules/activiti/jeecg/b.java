package org.jeecg.modules.activiti.jeecg;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jeecg.designer.commons.d;
import org.jeecg.designer.util.DesUtils;
import org.jeecg.designer.vo.AjaxJson;
import org.jeecg.modules.activiti.jeecg.util.JMathUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

@RestController("AloneMockAPIController")
@RequestMapping({"/alone/act/designer/api"})
public class b {
    private static final Log a = LogFactory.getLog(b.class);

    public b() {
    }

    @RequestMapping({"/getProcessXml"})
    @ResponseBody
    public void a(HttpServletRequest var1, HttpServletResponse var2) {
        var2.setContentType("text/xml;charset=UTF-8");
        String var3 = org.jeecg.designer.util.a.c(var1.getParameter("processId"));
        String var4 = org.jeecg.designer.util.a.c(var1.getParameter("token"));
        a.info(" getProcessXml 登录令牌token： " + var4);
        String var5 = null;

        try {
            InputStream var6 = this.getClass().getClassLoader().getResourceAsStream("org/jeecg/designer/mock/demo.bpmn_sub.xml".replace("classpath:", ""));
            var5 = d.a(var6);
            var2.getWriter().write(var5);
        } catch (Exception var7) {
            var7.printStackTrace();
        }

    }

    @RequestMapping(
            value = {"/getExpressions"},
            method = {RequestMethod.POST, RequestMethod.GET}
    )
    public String a(HttpServletRequest var1) {
        String var2 = org.jeecg.designer.util.a.c(var1.getParameter("token"));
        a.info(" getExpressions 登录令牌token： " + var2);
        String var3 = "classpath:org/jeecg/designer/mock/getExpressions.json";
        return JMathUtil.U(var3);
    }

    @RequestMapping(
            value = {"/getTypes"},
            method = {RequestMethod.POST, RequestMethod.GET}
    )
    public String b(HttpServletRequest var1) {
        String var2 = org.jeecg.designer.util.a.c(var1.getParameter("token"));
        a.info(" getExpressions 登录令牌token： " + var2);
        String var3 = "classpath:org/jeecg/designer/mock/mock/getTypes.json";
        return JMathUtil.U(var3);
    }

    @RequestMapping(
            value = {"/getDefaultListener"},
            method = {RequestMethod.POST, RequestMethod.GET}
    )
    public String c(HttpServletRequest var1) {
        String var2 = org.jeecg.designer.util.a.c(var1.getParameter("token"));
        a.info(" getDefaultListener 登录令牌token： " + var2);
        String var3 = "classpath:org/jeecg/designer/mock/getDefaultListener.json";
        return JMathUtil.U(var3);
    }

    @RequestMapping(
            value = {"/getGroups"},
            method = {RequestMethod.POST, RequestMethod.GET}
    )
    public String d(HttpServletRequest var1) {
        String var2 = org.jeecg.designer.util.a.c(var1.getParameter("token"));
        a.info(" getGroups 登录令牌token： " + var2);
        String var3 = "classpath:org/jeecg/designer/mock/getGroups.json";
        return JMathUtil.U(var3);
    }

    @RequestMapping(
            value = {"/getUsers"},
            method = {RequestMethod.POST, RequestMethod.GET}
    )
    public String e(HttpServletRequest var1) {
        String var2 = org.jeecg.designer.util.a.c(var1.getParameter("token"));
        a.info(" getUsers 登录令牌token： " + var2);
        String var3 = var1.getParameter("page");
        String var4 = var1.getParameter("rows");
        String var5 = var1.getParameter("account");
        String var6 = var1.getParameter("name");
        a.info("pageNumber: " + org.jeecg.designer.util.a.a(var3, 1));
        a.info("pageSize: " + org.jeecg.designer.util.a.a(var4, 5));
        a.info("account: " + var5);
        a.info("name: " + var6);
        String var7 = "classpath:org/jeecg/designer/mock/getUsers.json";
        return JMathUtil.U(var7);
    }

    @RequestMapping(
            value = {"/getPageUsers"},
            method = {RequestMethod.POST, RequestMethod.GET}
    )
    public String f(HttpServletRequest var1) {
        String var2 = org.jeecg.designer.util.a.c(var1.getParameter("token"));
        a.info(" getPageUsers 登录令牌token： " + var2);
        String var3 = var1.getParameter("page");
        String var4 = var1.getParameter("rows");
        String var5 = var1.getParameter("account");
        String var6 = var1.getParameter("name");
        a.info("pageNumber: " + org.jeecg.designer.util.a.a(var3, 1));
        a.info("pageSize: " + org.jeecg.designer.util.a.a(var4, 5));
        a.info("account: " + var5);
        a.info("name: " + var6);
        String var7 = "classpath:org/jeecg/designer/mock/getUsers.json";
        return JMathUtil.U(var7);
    }

    @RequestMapping({"/getListenersByIds"})
    public String b(HttpServletRequest var1, HttpServletResponse var2) {
        String var3 = org.jeecg.designer.util.a.c(var1.getParameter("token"));
        a.info(" getListenersByIds 登录令牌token： " + var3);
        String var4 = org.jeecg.designer.util.a.c(var1.getParameter("ids"));
        return DesUtils.getMockDataRealListeners(var4);
    }

    @RequestMapping({"/getListenersByType"})
    public String c(HttpServletRequest var1, HttpServletResponse var2) {
        String var3 = org.jeecg.designer.util.a.c(var1.getParameter("token"));
        a.info(" getListenersByType 登录令牌token： " + var3);
        String var4 = org.jeecg.designer.util.a.c(var1.getParameter("typeid"));
        a.info(" 流程监听类型 typeid： " + var4);
        String var5 = "classpath:org/jeecg/designer/mock/listenersByType.json";
        return JMathUtil.U(var5);
    }

    @RequestMapping({"/exportProcessDef"})
    @ResponseBody
    public AjaxJson d(HttpServletRequest var1, HttpServletResponse var2) {
        String var3 = org.jeecg.designer.util.a.c(var1.getParameter("token"));
        a.info(" exportProcessDef 登录令牌token： " + var3);
        AjaxJson var4 = new AjaxJson();
        String var5 = org.jeecg.designer.util.a.c(var1.getParameter("procdefId"));
        String var6 = org.jeecg.designer.util.a.c(var1.getParameter("processName"));

        try {
            InputStream var7 = this.getClass().getClassLoader().getResourceAsStream("org/jeecg/designer/mock/demo.bpmn_sub.xml".replace("classpath:", ""));
            IOUtils.copy(var7, var2.getOutputStream());
            String var8 = var5 + ".bpmn20.xml";
            var2.setHeader("Content-Disposition", "attachment;filename=" + var8);
            var2.flushBuffer();
            var4.setMsg("导出成功!");
            var7.close();
        } catch (Exception var9) {
            var9.printStackTrace();
        }

        return null;
    }

    @RequestMapping({"/saveProcess"})
    @ResponseBody
    public AjaxJson g(HttpServletRequest var1) {
        AjaxJson var2 = new AjaxJson();

        try {
            String var3 = org.jeecg.designer.util.a.c(var1.getParameter("processDefinitionId"));
            String var4 = org.jeecg.designer.util.a.c(var1.getParameter("processDescriptor"));
            String var5 = org.jeecg.designer.util.a.c(var1.getParameter("processName"));
            String var6 = org.jeecg.designer.util.a.c(var1.getParameter("processkey"));
            String var7 = org.jeecg.designer.util.a.c(var1.getParameter("params"));
            String var8 = org.jeecg.designer.util.a.c(var1.getParameter("nodes"));
            String var9 = org.jeecg.designer.util.a.c(var1.getParameter("typeid"));
            DesUtils.checkNodeDuplicate(var8);
            String var10 = org.jeecg.designer.util.a.c(var1.getParameter("token"));
            a.info(" processDefinitionId ：" + var3);
            a.info(" processkey ：" + var6);
            a.info(" processName ：" + var5);
            a.info(" processDescriptor ：" + var4);
            a.info(" nodes ：" + var8);
            a.info(" params ：" + var7);
            a.info(" typeid ：" + var9);
            a.info(" saveProcess 登录令牌token： " + var10);
        } catch (Exception var11) {
            var2.setMsg(var11.getMessage());
        }

        return var2;
    }

    static {
        JMathUtil.b();
    }
}
