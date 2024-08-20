package org.jeecg.modules.activiti.jeecg;

/*     */
/*     */

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jeecg.designer.util.DesUtils;
import org.jeecg.modules.activiti.jeecg.util.JMathUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/*     */
/*     */ @Controller("AloneDesignerController")
/*     */ @RequestMapping({"/alone/act/designer"})
/*     */ public class a {
    /*  26 */   private static final Log a = LogFactory.getLog(a.class);
    /*     */
    /*  27 */   private static String b = null;
    /*     */
    /*  28 */   private static String c = null;
    /*     */
    /*  29 */   private static String d = null;
    /*     */
    /*     */   @RequestMapping
    /*     */   public ModelAndView a(HttpServletRequest paramHttpServletRequest) {
        /*  38 */     String str1 = org.jeecg.designer.util.a.a(paramHttpServletRequest.getParameter("id"), "0");
        /*  39 */     String str2 = org.jeecg.designer.util.a.c(paramHttpServletRequest.getParameter("token"));
        /*  40 */     paramHttpServletRequest.setAttribute("processid", str1);
        /*  41 */     paramHttpServletRequest.setAttribute("token", str2);
        /*  42 */     a.info(" index 登录令牌token： " + str2);
        /*  43 */     return new ModelAndView(b);
        /*     */   }
    /*     */
    /*     */   @RequestMapping({"processProperties"})
    /*     */   public ModelAndView b(HttpServletRequest paramHttpServletRequest) {
        /*  53 */     String str1 = org.jeecg.designer.util.a.c(paramHttpServletRequest.getParameter("turn"));
        /*  54 */     String str2 = org.jeecg.designer.util.a.c(paramHttpServletRequest.getParameter("id"));
        /*  55 */     String str3 = org.jeecg.designer.util.a.c(paramHttpServletRequest.getParameter("checkbox"));
        /*  56 */     String str4 = org.jeecg.designer.util.a.c(paramHttpServletRequest.getParameter("processId"));
        /*  58 */     a.info(" 流程设计属性页面 turn： " + str1 + ",节点ID: " + str2 + ",checkbox: " + str3 + ",processId: " + str4);
        /*  60 */     if (!"0".equals(str4))
            /*  61 */       paramHttpServletRequest.setAttribute("typeId", "2");
        /*  63 */     paramHttpServletRequest.setAttribute("checkbox", str3);
        /*  64 */     paramHttpServletRequest.setAttribute("id", str2);
        /*  66 */     paramHttpServletRequest.setAttribute("proTypeList", DesUtils.getMockDataProcessTypes());
        /*  67 */     paramHttpServletRequest.setAttribute("processId", str4);
        /*  68 */     return new ModelAndView("designer/" + str1 + "");
        /*     */   }
    /*     */
    /*     */   @RequestMapping({"goExpression"})
    /*     */   public ModelAndView c(HttpServletRequest paramHttpServletRequest) {
        /*  78 */     String str1 = org.jeecg.designer.util.a.c(paramHttpServletRequest.getParameter("id"));
        /*  79 */     String str2 = org.jeecg.designer.util.a.c(paramHttpServletRequest.getParameter("checkbox"));
        /*  80 */     String str3 = org.jeecg.designer.util.a.c(paramHttpServletRequest.getParameter("processId"));
        /*  81 */     paramHttpServletRequest.setAttribute("checkbox", str2);
        /*  82 */     paramHttpServletRequest.setAttribute("id", str1);
        /*  83 */     paramHttpServletRequest.setAttribute("processId", str3);
        /*  85 */     String str4 = org.jeecg.designer.util.a.c(paramHttpServletRequest.getParameter("token"));
        /*  86 */     a.info(" index 登录令牌token： " + str4);
        /*  87 */     paramHttpServletRequest.setAttribute("token", str4);
        /*  88 */     return new ModelAndView("designer/selectExpressionList");
        /*     */   }
    /*     */
    /*     */   @RequestMapping({"goSearchSelectUser"})
    /*     */   public ModelAndView d(HttpServletRequest paramHttpServletRequest) {
        /*  98 */     String str1 = org.jeecg.designer.util.a.c(paramHttpServletRequest.getParameter("id"));
        /*  99 */     String str2 = org.jeecg.designer.util.a.c(paramHttpServletRequest.getParameter("checkbox"));
        /* 100 */     String str3 = org.jeecg.designer.util.a.c(paramHttpServletRequest.getParameter("processId"));
        /* 101 */     paramHttpServletRequest.setAttribute("checkbox", str2);
        /* 102 */     paramHttpServletRequest.setAttribute("id", str1);
        /* 103 */     paramHttpServletRequest.setAttribute("processId", str3);
        /* 105 */     String str4 = org.jeecg.designer.util.a.c(paramHttpServletRequest.getParameter("token"));
        /* 106 */     a.info(" index 登录令牌token： " + str4);
        /* 107 */     paramHttpServletRequest.setAttribute("token", str4);
        /* 108 */     return new ModelAndView("designer/searchSelectUserList");
        /*     */   }
    /*     */
    /*     */   @RequestMapping({"goSubParameterList"})
    /*     */   public ModelAndView e(HttpServletRequest paramHttpServletRequest) {
        /* 119 */     String str1 = org.jeecg.designer.util.a.c(paramHttpServletRequest.getParameter("id"));
        /* 120 */     String str2 = org.jeecg.designer.util.a.c(paramHttpServletRequest.getParameter("checkbox"));
        /* 121 */     String str3 = org.jeecg.designer.util.a.c(paramHttpServletRequest.getParameter("processId"));
        /* 122 */     paramHttpServletRequest.setAttribute("checkbox", str2);
        /* 123 */     paramHttpServletRequest.setAttribute("id", str1);
        /* 124 */     paramHttpServletRequest.setAttribute("processId", str3);
        /* 125 */     return new ModelAndView("designer/subParameterList");
        /*     */   }
    /*     */
    /*     */   @RequestMapping({"goListeners"})
    /*     */   public ModelAndView f(HttpServletRequest paramHttpServletRequest) {
        /* 135 */     String str1 = org.jeecg.designer.util.a.c(paramHttpServletRequest.getParameter("token"));
        /* 136 */     a.info(" index 登录令牌token： " + str1);
        /* 137 */     paramHttpServletRequest.setAttribute("token", str1);
        /* 140 */     String str2 = org.jeecg.designer.util.a.c(paramHttpServletRequest.getParameter("typeid"));
        /* 141 */     a.info(" 流程监听类型 typeid： " + str2);
        /* 142 */     paramHttpServletRequest.setAttribute("typeid", str2);
        /* 143 */     return new ModelAndView("designer/selectProcessListenerList");
        /*     */   }
    /*     */
    /*     */   static {
        /* 147 */    // if (c.a(b))
            /* 148 */       b = JMathUtil.defaultString("designer/index");
        /* 150 */    // if (c.a(c))
            /* 151 */       c = JMathUtil.defaultString("designer/selectProcessListenerList");
        /* 153 */    // if (c.a(d))
            /* 154 */       d = JMathUtil.defaultString("designer/selectExpressionList");
        /* 156 */     // JMathUtil.b();
        /*     */   }
    /*     */ }


