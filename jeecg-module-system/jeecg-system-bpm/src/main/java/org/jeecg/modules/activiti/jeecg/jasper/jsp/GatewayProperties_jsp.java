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

public final class GatewayProperties_jsp extends HttpJspBase implements JspSourceDependent, JspSourceImports {
    private static final JspFactory a = JspFactory.getDefaultFactory();
    private static Map<String, Long> b = new HashMap(1);
    private static final Set<String> c;
    private static final Set<String> d;
    private TagHandlerPool e;
    private volatile ExpressionFactory f;
    private volatile InstanceManager g;

    public GatewayProperties_jsp() {
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
    //                 String var13 = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + var12;
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

                    var8.write("\r\n<script type=\"text/javascript\">\r\nvar gateway = workflow.getFigure(nodeid);\r\n//属性表格定义\r\n rows = [\r\n    \r\n            { \"name\": \"ID\", \"group\": \"网关属性\", \"value\": gateway.gatewayId,\"field\": \"gatewayId\", \"editor\": \"text\" },\r\n            { \"name\": \"名字\", \"group\": \"网关属性\", \"value\": gateway.gatewayName, \"field\": \"gatewayName\", \"editor\": \"text\" }        \r\n];\r\n //保存属性\r\nfunction saveGatewayProperties(){\r\n\tgateway.gatewayId=rows[0].value;\r\n\tgateway.gatewayName=rows[1].value;\r\n}\r\n //构建属性表格数据\r\nfunction populateGatewayProperites(){\r\n\trows[0].value=gateway.gatewayId;\r\n\trows[1].value=gateway.gatewayName;\r\n\tgatewaypropertygrid();\r\n} \r\n //加载属性表格数据\r\nfunction gatewaypropertygrid(){\r\n\t$('#gateway-properties').propertygrid('loadData',rows);\r\n\t}\r\n$(function(){\r\n//创建属性表格\r\n$('#gateway-properties').propertygrid({\r\n  width: 'auto',\r\n  height: 'auto',\r\n  showGroup: true,\r\n  scrollbarSize: 0,\r\n  border:0,\r\n  columns: [[\r\n          { field: 'name', title: '属性名', width: 30, resizable: false },\r\n          { field: 'value', title: '属性值', width: 100, resizable: false }\r\n");
                    var8.write("  ]],\r\n  onAfterEdit:function(){  \r\n  \tsaveGatewayProperties();//自动保存\r\n   }\r\n});\r\ngatewaypropertygrid();\r\n});\r\n</script>\r\n<div id=\"gateway-properties-layout\" class=\"easyui-layout\" fit=\"true\">\r\n <div id=\"gateway-properties-panel\"  region=\"center\" border=\"true\">\r\n   <table id=\"gateway-properties\">\r\n   </table>\r\n </div>\r\n</div>");
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
