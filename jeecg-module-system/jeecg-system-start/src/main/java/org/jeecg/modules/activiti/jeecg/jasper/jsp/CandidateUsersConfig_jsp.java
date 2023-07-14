package org.jeecg.modules.activiti.jeecg.jasper.jsp;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import org.apache.jasper.runtime.*;
import org.apache.taglibs.standard.tag.rt.core.IfTag;
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

public final class CandidateUsersConfig_jsp extends HttpJspBase implements JspSourceDependent, JspSourceImports {
    private static final JspFactory a = JspFactory.getDefaultFactory();
    private static Map<String, Long> b = new HashMap(1);
    private static final Set<String> c;
    private static final Set<String> d;
    private TagHandlerPool e;
    private TagHandlerPool f;
    private volatile ExpressionFactory g;
    private volatile InstanceManager h;

    public CandidateUsersConfig_jsp() {
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
        if (this.g == null) {
            synchronized(this) {
                if (this.g == null) {
                    this.g = a.getJspApplicationContext(this.getServletConfig().getServletContext()).getExpressionFactory();
                }
            }
        }

        return this.g;
    }

    public InstanceManager b() {
        if (this.h == null) {
            synchronized(this) {
                if (this.h == null) {
                    this.h = InstanceManagerFactory.getInstanceManager(this.getServletConfig());
                }
            }
        }

        return this.h;
    }

    public void _jspInit() {
        this.e = TagHandlerPool.getTagHandlerPool(this.getServletConfig());
        this.f = TagHandlerPool.getTagHandlerPool(this.getServletConfig());
    }

    public void _jspDestroy() {
        this.e.release();
        this.f.release();
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
//                String var13 = request.getScheme() + "://" + request.getServerName() + ":8989"  + var12;
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

                var8.write("\r\n<table id=\"userList\"></table>\r\n<script type=\"text/javascript\">\r\n$('#userList').datagrid({\r\n    url:'designer/api/getUsers?token='+workflow.process.token,\r\n    fit: true,\r\n    fitColumns:true,\r\n    rownumbers:true,\r\n    singleSelect:!");
                var8.write((String)PageContextImpl.proprietaryEvaluate("${checkbox}", String.class, var4, (ProtectedFunctionMapper)null));
                var8.write(",\r\n    columns:[[\r\n   \t\t");
                if (this.a(var4)) {
                    return;
                }

                var8.write("\r\n\t\t{field:'firstName',title:'用户昵称',width:40},\r\n\t\t{field:'id',title:'用户账号',width:40}\r\n    ]]\r\n});\r\n\r\nfunction xuanze(){\r\n\tvar ids=getListSelections();\r\n\t$('#expression').val(ids);\r\n}\r\n\r\n/**\r\n * 获取列表中选中行的数据（多行）\r\n */\r\nfunction getListSelections(){\r\n\tvar ids = '';\r\n\tvar rows = $(\"#userList\").datagrid(\"getSelections\");\r\n\tfor(var i=0;i<rows.length;i++){\r\n\t\tids+=rows[i].id;\r\n\t\tids+=',';\r\n\t}\r\n\tids = ids.substring(0,ids.length-1);\r\n\treturn ids;\r\n}\t\r\n\r\n\r\n</script>\r\n");
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
                }
            } finally {
                a.releasePageContext(var11);
            }

        }
    }

    private boolean a(PageContext var1) throws Throwable {
        JspWriter var3 = var1.getOut();
        IfTag var4 = (IfTag)this.f.get(IfTag.class);
        boolean var5 = false;

        boolean var11;
        try {
            var4.setPageContext(var1);
            var4.setParent((Tag)null);
            var4.setTest((Boolean)PageContextImpl.proprietaryEvaluate("${checkbox}", Boolean.TYPE, var1, (ProtectedFunctionMapper)null));
            int var6 = var4.doStartTag();
            int var7;
            if (var6 != 0) {
                do {
                    var3.write("\r\n   \t\t{field:'ck',checkbox:true},\r\n   \t\t");
                    var7 = var4.doAfterBody();
                } while(var7 == 2);
            }

            if (var4.doEndTag() != 5) {
                this.f.reuse(var4);
                var5 = true;
                return false;
            }

            var11 = true;
        } finally {
            JspRuntimeLibrary.releaseTag(var4, this.b(), var5);
        }

        return var11;
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
