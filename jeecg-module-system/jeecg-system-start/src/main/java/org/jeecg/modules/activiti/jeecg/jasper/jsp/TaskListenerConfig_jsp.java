package org.jeecg.modules.activiti.jeecg.jasper.jsp;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import org.apache.jasper.runtime.*;
import org.apache.taglibs.standard.tag.rt.core.SetTag;
import org.apache.tomcat.InstanceManager;
import org.jeecg.modules.activiti.jeecg.util.JMathUtil;

import javax.el.ExpressionFactory;
import javax.servlet.DispatcherType;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.SkipPageException;
import javax.servlet.jsp.tagext.Tag;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class TaskListenerConfig_jsp extends HttpJspBase implements JspSourceDependent, JspSourceImports {
    private static final JspFactory a = JspFactory.getDefaultFactory();
    private static Map<String, Long> b = new HashMap(1);
    private static final Set<String> c;
    private static final Set<String> d;
    private TagHandlerPool e;
    private volatile ExpressionFactory f;
    private volatile InstanceManager g;

    public TaskListenerConfig_jsp() {
    }

    public Map<String, Long> getDependants() {
        return b;
    }

    public Set<String> getPackageImports() {
        return c;
    }

    public Set<String> getClassImports() {
        return d;
    }

    public ExpressionFactory a() {
        if (this.f == null) {
            synchronized(this) {
                if (this.f == null) {
                    this.f = a.getJspApplicationContext(this.getServletConfig().getServletContext()).getExpressionFactory();
                }
            }
        }

        return this.f;
    }

    public InstanceManager b() {
        if (this.g == null) {
            synchronized(this) {
                if (this.g == null) {
                    this.g = InstanceManagerFactory.getInstanceManager(this.getServletConfig());
                }
            }
        }

        return this.g;
    }

    public void _jspInit() {
        this.e = TagHandlerPool.getTagHandlerPool(this.getServletConfig());
    }

    public void _jspDestroy() {
        this.e.release();
    }

    public void _jspService(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String var3 = request.getMethod();
        if (!"GET".equals(var3) && !"POST".equals(var3) && !"HEAD".equals(var3) && !DispatcherType.ERROR.equals(request.getDispatcherType())) {
            response.sendError(405, "JSPs only permit GET POST or HEAD");
        } else {
            HttpSession var5 = null;
            JspWriter var8 = null;
            Object var10 = null;
            PageContext var11 = null;

            try {
                try {
                    response.setContentType("text/html; charset=UTF-8");
                    PageContext var4 = a.getPageContext(this, request, response, (String)null, true, 8192, true);
                    var11 = var4;
                    ServletContext var6 = var4.getServletContext();
                    ServletConfig var7 = var4.getServletConfig();
                    var5 = var4.getSession();
                    var8 = var4.getOut();
                    var8.write(13);
                    var8.write(10);
                    var8.write("\r\n\r\n\r\n\r\n");
                    String var12 = request.getContextPath();
//                    String var13 = request.getScheme() + "://" + request.getServerName() + ":8989"  + var12;
    //                 String var13 = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()  + var12;
                                String var13 = var12;
                    String var14 = JMathUtil.defaultString(".jsp");
                    String var15 = request.getHeader("X_GATEWAY_BASE_PATH");
                    if (var15 != null && !var15.equals("")) {
                        var13 = var15;
                    }

                    var8.write(13);
                    var8.write(10);
                    SetTag var16 = (SetTag)this.e.get(SetTag.class);
                    boolean var17 = false;

                    try {
                        var16.setPageContext(var11);
                        var16.setParent((Tag)null);
                        var16.setVar("webRoot");
                        var16.setValue(var13);
                        int var18 = var16.doStartTag();
                        if (var16.doEndTag() == 5) {
                            return;
                        }

                        this.e.reuse(var16);
                        var17 = true;
                    } finally {
                        JspRuntimeLibrary.releaseTag(var16, this.b(), var17);
                    }

                    var8.write("\r\n<!DOCTYPE html>\r\n<html>\r\n <head>\r\n  <title>添加流程变量</title>\r\n  <t:base type=\"jquery,easyui,tools\" cssTheme=\"default\"></t:base>\r\n  <script type=\"text/javascript\">\r\n\tvar processNodeId = '");
                    var8.write((String)PageContextImpl.proprietaryEvaluate("${id}", String.class, var4, (ProtectedFunctionMapper)null));
                    var8.write("';\r\n\tvar processId = '");
                    var8.write((String)PageContextImpl.proprietaryEvaluate("${processId}", String.class, var4, (ProtectedFunctionMapper)null));
                    var8.write("';\r\n\tfunction xuanze() {\r\n\t\tvar ids = getlistenerListSelections(\"listenerid\") + \"\";\r\n\t\tvar events = getlistenerListSelections(\"listenereven\") + \"\";\r\n\t\tvar types = getlistenerListSelections(\"listenertype\") + \"\";\r\n\t\tvar values = getlistenerListSelections(\"listenervalue\") + \"\";\r\n\t\tvar len = (ids.split(\",\")).length;\r\n\t\tvar eventstmp = events.split(\",\");\r\n\t\tvar typestmp = types.split(\",\");\r\n\t\tvar valuestmp = values.split(\",\");\r\n\t\tfor ( var i = 0; i < len; i++) {\r\n\t\t\tvar listener = new draw2d.Task.Listener();\r\n\t\t\tlistener.event = eventstmp[i];\r\n\t\t\tlistener.serviceType = typestmp[i];\r\n\t\t\tif (typestmp[i] == 'javaClass') {\r\n\t\t\t\tlistener.serviceClass = valuestmp[i];\r\n\t\t\t} else if (typestmp[i] == 'expression') {\r\n\t\t\t\tlistener.serviceExpression = valuestmp[i];\r\n\t\t\t}\r\n\t\t\ttask.listeners.add(listener);\r\n\t\t}\r\n\t\tloadTaskListeners();\r\n\t\t_listener_win.window('close');\r\n\t}\r\n</script>\r\n </head>\r\n <body style=\"overflow-y: hidden\" scroll=\"no\">\r\n  <t:formvalid formid=\"formobj\" layout=\"div\" dialog=\"false\" action=\"userController.do?save\">\r\n");
                    var8.write("    <fieldset class=\"step\">\r\n     <legend>\r\n    \t  基本信息\r\n     </legend>\r\n     <div class=\"form\">\r\n      <label class=\"form\">\r\n     \t  监听\r\n      </label>\r\n      <input name=\"listenerid\" type=\"hidden\" value=\"");
                    var8.write((String)PageContextImpl.proprietaryEvaluate("${listenerid}", String.class, var4, (ProtectedFunctionMapper)null));
                    var8.write("\" id=\"listenerid\">\r\n      <input name=\"listenereven\" value=\"");
                    var8.write((String)PageContextImpl.proprietaryEvaluate("${listenereven }", String.class, var4, (ProtectedFunctionMapper)null));
                    var8.write("\" id=\"listenereven\" readonly=\"readonly\" />\r\n      <t:choose hiddenName=\"listenerid\" hiddenid=\"id\" url=\"processController.do?chooseListener\" name=\"listenerList\" icon=\"icon-choose\" title=\"监听列表\" textname=\"listenereven\"></t:choose>\r\n   </div>\r\n    </fieldset>\r\n  </t:formvalid>\r\n  <!--  <t:datagrid checkbox=\"true\" name=\"listenerList\" actionUrl=\"processController.do?getlisteners\"  idField=\"listenerid\" pagination=\"false\">\t \t\r\n\t\t<t:dgCol title=\"listenerid\" hidden=\"true\" field=\"listenerid\"></t:dgCol>\r\n\t\t<t:dgCol title=\"事件类型\" field=\"listenereven\" width=\"40\"></t:dgCol>\r\n\t\t<t:dgCol title=\"监听类型\" field=\"listenertype\" width=\"40\"></t:dgCol>\r\n\t\t<t:dgCol title=\"值\" field=\"listenervalue\" width=\"40\"></t:dgCol>\r\n\t</t:datagrid>\t\r\n\t-->\r\n </body>\r\n</html>\r\n");
                } catch (Throwable var31) {
                    if (!(var31 instanceof SkipPageException)) {
                        var8 = (JspWriter)var10;
                        if (var10 != null && ((JspWriter)var10).getBufferSize() != 0) {
                            try {
                                if (response.isCommitted()) {
                                    var8.flush();
                                } else {
                                    var8.clearBuffer();
                                }
                            } catch (IOException var29) {
                            }
                        }

                        if (var11 == null) {
                            throw new ServletException(var31);
                        }

                        var11.handlePageException(var31);
                        return;
                    }
                }

            } finally {
                a.releasePageContext(var11);
            }
        }
    }

    static {
        b.put("/context/mytags.jsp", 1634671342300L);
        c = new HashSet();
        c.add("javax.servlet");
        c.add("java.util");
        c.add("org.jeecg.designer.commons");
        c.add("javax.servlet.http");
        c.add("javax.servlet.jsp");
        d = null;
    }
}
