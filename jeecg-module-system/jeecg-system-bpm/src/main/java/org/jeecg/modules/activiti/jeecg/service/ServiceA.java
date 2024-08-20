package org.jeecg.modules.activiti.jeecg.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jeecg.designer.util.DesUtils;
import org.jeecg.designer.vo.ProcessTypeVo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Configuration("newJeecgDesignerFlowService")
public class ServiceA {
    private static final Log a = LogFactory.getLog(ServiceA.class);
    private static String b = null;
    private static String c = null;
    private static String dd = null;

    public ServiceA() {
    }

    public ModelAndView a(HttpServletRequest var1) {
        String var2 = org.jeecg.designer.util.a.a(var1.getParameter("id"), "0");
        String var3 = org.jeecg.designer.util.a.c(var1.getParameter("token"));
        var1.setAttribute("processid", var2);
        var1.setAttribute("token", var3);
        a.info(" index 登录令牌token： " + var3);
        return new ModelAndView(b);
    }

    public ModelAndView b(HttpServletRequest var1) {
        String var2 = org.jeecg.designer.util.a.c(var1.getParameter("turn"));
        String var3 = org.jeecg.designer.util.a.c(var1.getParameter("id"));
        String var4 = org.jeecg.designer.util.a.c(var1.getParameter("checkbox"));
        String var5 = org.jeecg.designer.util.a.c(var1.getParameter("processId"));
        a.info(" 流程设计属性页面 turn： " + var2 + ",节点ID: " + var3 + ",checkbox: " + var4 + ",processId: " + var5);
        if (!"0".equals(var5)) {
            var1.setAttribute("typeId", "2");
        }

        var1.setAttribute("checkbox", var4);
        var1.setAttribute("id", var3);
        var1.setAttribute("proTypeList", DesUtils.getMockDataProcessTypes());
        var1.setAttribute("processId", var5);
        return new ModelAndView("designer/" + var2 + "");
    }

    public ModelAndView c(HttpServletRequest var1) {
        String var2 = org.jeecg.designer.util.a.c(var1.getParameter("id"));
        String var3 = org.jeecg.designer.util.a.c(var1.getParameter("checkbox"));
        String var4 = org.jeecg.designer.util.a.c(var1.getParameter("processId"));
        var1.setAttribute("checkbox", var3);
        var1.setAttribute("id", var2);
        var1.setAttribute("processId", var4);
        String var5 = org.jeecg.designer.util.a.c(var1.getParameter("token"));
        a.info(" index 登录令牌token： " + var5);
        var1.setAttribute("token", var5);
        return new ModelAndView("designer/selectExpressionList");
    }

    public ModelAndView d(HttpServletRequest var1) {
        String var2 = org.jeecg.designer.util.a.c(var1.getParameter("id"));
        String var3 = org.jeecg.designer.util.a.c(var1.getParameter("checkbox"));
        String var4 = org.jeecg.designer.util.a.c(var1.getParameter("processId"));
        var1.setAttribute("checkbox", var3);
        var1.setAttribute("id", var2);
        var1.setAttribute("processId", var4);
        String var5 = org.jeecg.designer.util.a.c(var1.getParameter("token"));
        a.info(" index 登录令牌token： " + var5);
        var1.setAttribute("token", var5);
        return new ModelAndView("designer/searchSelectUserList");
    }

    @Bean(
            name = {"desginerSearchSelectUser"}
    )
    public ProcessTypeVo a() {
       /* long var1 = 2626560000L;
        1 var3 = new 1(this);
        Thread var4 = new Thread(var3);
        var4.start();*/

        return new ProcessTypeVo();
    }

/*
    private String b() {
        String var1 = "ERROR";

        try {
            ResourceBundle var2 = org.jeecg.designer.commons.c.g();
            if (var2 == null) {
                var2 = ResourceBundle.getBundle(CommonD.d());
            }

            var1 = var2.getString(CommonD.g());
            String var3 = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCxHEhhLwdDhZ57SlLt/5JWFeGRy4r+NKgbA0VwkCXs7p0w1bNgKlRJOzUfjg6kMi4ygkVNNPWrXIhcdMniTbcHmawawLTa3cRM1jNdG0xg808KKNVdDilFHOFQz8AF6cvLDpsWkqzmoe2+64v0zlWbp5EpYxMMRxOwSMVKxLB8BwIDAQAB";
            byte[] var4 = CommonD.a(var3, var1);
            var1 = new String(var4, "UTF-8");
            String[] var5 = var1.split("\\|");
            if (!var5[1].equals(org.jeecg.designer.commons.c.c())) {
                System.out.println(CommonD.h() + org.jeecg.designer.commons.c.c());
                System.err.println(org.jeecg.designer.commons.a.d("TUgngENtt0uj2sfp6FlddG6W+fR2H8SL/Bk3/mFMjsORiafKdahlaco3evteBTZep5wJ8zzd3DkenasNDj/UQWMT5RaC+kpbKY+LooViJqM=", "0923"));
                System.exit(0);
            }
        } catch (Exception var6) {
            System.out.println(CommonD.h() + org.jeecg.designer.commons.c.c());
            System.err.println(org.jeecg.designer.commons.a.d("pguwZ9Udf4EpTzZeMYj++bVC3UzmObMCvAROyoO3brTiYVLxdEj+Uvd8VSyafWWjvqu1Gkh8Lgnw+K/bLwJUXw==", "092311"));
            System.exit(0);
        }

        return var1;
    }
*/

    public ModelAndView e(HttpServletRequest var1) {
        String var2 = org.jeecg.designer.util.a.c(var1.getParameter("id"));
        String var3 = org.jeecg.designer.util.a.c(var1.getParameter("checkbox"));
        String var4 = org.jeecg.designer.util.a.c(var1.getParameter("processId"));
        var1.setAttribute("checkbox", var3);
        var1.setAttribute("id", var2);
        var1.setAttribute("processId", var4);
        return new ModelAndView("designer/subParameterList");
    }

    public ModelAndView f(HttpServletRequest var1) {
        String var2 = org.jeecg.designer.util.a.c(var1.getParameter("token"));
        a.info(" index 登录令牌token： " + var2);
        var1.setAttribute("token", var2);
        String var3 = org.jeecg.designer.util.a.c(var1.getParameter("typeid"));
        a.info(" 流程监听类型 typeid： " + var3);
        var1.setAttribute("typeid", var3);
        return new ModelAndView("designer/selectProcessListenerList");
    }
}
