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

public final class ServiceTaskProperties_jsp extends HttpJspBase implements JspSourceDependent, JspSourceImports {
    private static final JspFactory a = JspFactory.getDefaultFactory();
    private static Map<String, Long> b = new HashMap(1);
    private static final Set<String> c;
    private static final Set<String> d;
    private TagHandlerPool e;
    private volatile ExpressionFactory f;
    private volatile InstanceManager g;

    public ServiceTaskProperties_jsp() {
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

                    var8.write("\r\n<script type=\"text/javascript\">\r\nvar tid = '");
                    var8.write((String)PageContextImpl.proprietaryEvaluate("${id}", String.class, var4, (ProtectedFunctionMapper)null));
                    var8.write("';\r\nvar task = workflow.getFigure(tid);\r\npopulateTaskProperites();\r\nfunction saveTaskProperties(){\r\n\ttask.taskId=$('#id').val();\r\n\ttask.taskName=$('#name').val();\r\n\ttask.expression=$('#expression').val();\r\n\ttask.documentation=$('#documentation').val();\r\n\ttask.serviceType=$('#serviceType').val();\r\n\ttask.resultVariable=$('#resultVariable').val();\r\n\ttask.setContent($('#name').val());\r\n\ttip(\"保存成功 !\");\r\n}\r\nfunction populateTaskProperites(){\r\n\t$('#id').val(task.taskId);\r\n\t$('#name').val(task.taskName);\r\n\t$('#expression').val(task.expression);\r\n\t$('#documentation').val(task.documentation);\r\n\t$('#serviceType').val(task.serviceType);\r\n\t$('#resultVariable').val(task.resultVariable);\r\n}\r\n</script>\r\n<div id=\"task-properties-layout\" class=\"easyui-layout\" fit=\"true\">\r\n\t<div id=\"task-properties-toolbar-panel\" region=\"north\" border=\"false\" style=\"height:30px;background:#E1F0F2;\">\r\n\t\t<a href=\"##\" id=\"sb2\" class=\"easyui-linkbutton\" plain=\"true\" iconCls=\"icon-save\" onclick=\"saveTaskProperties()\">保存</a>\r\n\t</div>\r\n\t<div id=\"task-properties-panel\" region=\"center\" border=\"true\">\r\n");
                    var8.write("\t\t<div id=\"task-properties-accordion\" class=\"easyui-accordion\" fit=\"true\" border=\"false\">\r\n\t\t\t<div id=\"general\" title=\"主属性\" selected=\"true\" class=\"properties-menu\">\r\n\t\t\t\t<table id=\"general-properties\">\r\n\t\t\t\t\t<tr>\r\n\t\t\t\t\t\t<td align=\"right\">Id:</td>\r\n\t\t\t\t\t\t<td><input type=\"text\" id=\"id\" name=\"id\"  value=\"\"/></td>\r\n\t\t\t\t\t</tr>\r\n\t\t\t\t\t<tr>\r\n\t\t\t\t\t\t<td align=\"right\">名称:</td>\r\n\t\t\t\t\t\t<td><input type=\"text\" id=\"name\" name=\"name\"  value=\"\"/></td>\r\n\t\t\t\t\t</tr>\r\n\t\t\t\t\t<tr>\r\n\t\t\t\t\t\t<td align=\"right\">描述:</td>\r\n\t\t\t\t\t\t<td><textarea id=\"documentation\" name=\"documentation\" cols=\"15\" rows=\"4\"></textarea></td>\r\n\t\t\t\t\t</tr>\r\n\t\t\t\t\t<tr>\r\n\t\t\t\t\t\t<td align=\"right\">类型:</td>\r\n\t\t\t\t\t\t<td><select id=\"serviceType\" >\r\n\t\t\t\t\t\t\t\t<option value=\"javaClass\">javaClass</option>\r\n\t\t\t\t\t\t\t\t<option value=\"expression\" >expression</option>\r\n\t\t\t\t\t\t\t\t<option value=\"delegateExpression\" >delegateExpression</option>\r\n\t\t\t\t\t\t\t</select>\r\n\t\t\t\t\t\t</td>\r\n\t\t\t\t\t</tr>\r\n\t\t\t\t\t<tr>\r\n\t\t\t\t\t\t<td align=\"right\">脚本:</td>\r\n\t\t\t\t\t\t<td>\r\n\t\t\t\t\t\t<textarea id=\"expression\" name=\"expression\" cols=\"25\" rows=\"5\"></textarea>\r\n");
                    var8.write("\t\t\t\t\t\t</td>\r\n\t\t\t\t\t</tr>\r\n\t\t\t\t\t<tr>\r\n\t\t\t\t\t\t<td align=\"right\">返回变量:</td>\r\n\t\t\t\t\t\t<td><input type=\"text\" id=\"resultVariable\" name=\"resultVariable\"  value=\"\"/></td>\r\n\t\t\t\t\t</tr>\r\n\t\t\t\t</table>\r\n\t\t\t\t<fieldset style=\"line-height: 21px;\">\r\n\t\t\t\t\t\t<legend>说明</legend>\r\n\t\t\t\t\t\t<div>1.服务任务，当流程执行到服务任务时，执行相应的服务内容。</div>\r\n\t\t\t\t\t\t<div>2.类型:<br/>&nbsp;&nbsp;&nbsp;&nbsp;javaClass,执行java类;<br/>&nbsp;&nbsp;&nbsp;&nbsp;expression：表达式，<br/>&nbsp;&nbsp;&nbsp;&nbsp;delegateExpression：Spring容器中bean。</div>\r\n\t\t\t\t\t\t<div>3.执行内容：具体的类全名，或表达式。</div>\r\n\t\t\t\t\t\t<div>4.返回变量：任务返回处理结果保存到流程变量中的变量名称。</div>\r\n\t\t\t\t</fieldset>\r\n\t\t\t</div>\r\n\t\t</div>\r\n\t</div>\r\n</div>");
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
        c.add("org.jeecg.designer.commons");
        c.add("javax.servlet.http");
        c.add("javax.servlet.jsp");
        d = null;
    }
}
