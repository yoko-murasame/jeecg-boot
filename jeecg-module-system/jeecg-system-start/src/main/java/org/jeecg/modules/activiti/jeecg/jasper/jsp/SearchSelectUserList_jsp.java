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

public final class SearchSelectUserList_jsp extends HttpJspBase implements JspSourceDependent, JspSourceImports {
    private static final JspFactory a = JspFactory.getDefaultFactory();
    private static Map<String, Long> b = new HashMap(1);
    private static final Set<String> c;
    private static final Set<String> d;
    private TagHandlerPool e;
    private volatile ExpressionFactory f;
    private volatile InstanceManager g;

    public SearchSelectUserList_jsp() {
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

                    var8.write("\r\n<!DOCTYPE html>\r\n<html>\r\n<head>\r\n<title>用户选择列表</title>\r\n<link id=\"easyuiTheme\" rel=\"stylesheet\" href=\"");
                    var8.print(var13);
                    var8.write("/plug-in/designer/easyui/easyui.css\" type=\"text/css\"></link>\r\n<link rel=\"stylesheet\" href=\"");
                    var8.print(var13);
                    var8.write("/plug-in/designer/easyui/icon.css\" type=\"text/css\"></link>\r\n<link rel=\"stylesheet\" href=\"");
                    var8.print(var13);
                    var8.write("/plug-in/font-awesome-4.7.0/css/font-awesome.min.css\"></link>\r\n<script type=\"text/javascript\" src=\"");
                    var8.print(var13);
                    var8.write("/plug-in/designer/easyui/jquery-1.7.2.min.js\"></script>\r\n<script type=\"text/javascript\" src=\"");
                    var8.print(var13);
                    var8.write("/plug-in/designer/easyui/jquery.easyui.min.1.3.0.js\"></script>\r\n<script type=\"text/javascript\" src=\"");
                    var8.print(var13);
                    var8.write("/plug-in/designer/easyui/easyui-lang-zh_CN.js\"></script>\r\n\r\n<style>\r\ntable.table-list {\r\n \twidth: 99.6%;\r\n \tfont-size: 12px;\r\n \tmargin:0px auto;\r\n \tborder-collapse: collapse;\r\n}\r\ntable.table-list thead {\r\n}\r\n\r\ntable.table-list thead > tr > td{\r\n\ttext-align: left;\r\n\tpadding-left: 5px;\r\n\t\r\n}\r\ntable.table-list  tr {\r\n\theight: 25px;\r\n}\r\ntable.table-list thead th {\r\n\tbackground: #FDFDFF;\r\n\theight: 25px;\r\n\ttext-align: center;\r\n\tbackground-color: #e5e5e5;\r\n\tborder-bottom: 1px solid #ccc\r\n}\r\ntable.table-list tr > th {\r\n\tpadding: 0px 5px;\r\n\ttext-align: center;\r\n\tcolor: #282831;\r\n\tborder:1px solid #e5e5e5;\r\n\t\r\n\tborder-right: 1px dotted #ccc;\r\n    font-size: 12px;\r\n    font-weight: normal;\r\n    background: #fafafa url(../../plug-in/designer/easyui/images/datagrid_header_bg.gif) repeat-x left bottom;\r\n    border-bottom: 1px dotted #ccc;\r\n    border-top: 0px dotted #fff;\r\n}\r\ntable.table-list td {\r\n\tfont-weight: normal;\r\n\tpadding: 2px 2px;\r\n\ttext-align: center;\r\n\tborder:1px solid #e5e5e5;\r\n}\r\n\r\n.panel-body {\r\n    border: 1px solid #dde8f7\r\n");
                    var8.write("}\r\na {color:#444}\r\na:link { text-decoration: none; } /*正常情况下无下划线*/\r\na:visited { text-decoration: none; } /*访问过的链接无下划线*/\r\na:hover { text-decoration: underline; } /*鼠标指向时显示下划线*/\r\na:active { text-decoration: underline; } /*鼠标按下时也显示*/\r\n</style>\r\n</head>\r\n<body style=\"overflow-y: hidden\" scroll=\"no\">\r\n<div class=\"easyui-layout\" data-options=\"fit:true\">\r\n\t<div data-options=\"region:'east'\" style=\"width:200px;\">\r\n       \t<table class=\"table-list\" cellspacing=\"0\" id=\"checkedTable1\">\r\n\t\t\t<thead>\r\n\t\t\t\t<tr class=\"datagrid-header\">\r\n\t\t\t\t\t<th>用户账号</th>\r\n\t\t\t\t\t<th>操作 <a class=\"list-group-item\" href=\"#\" onclick=\"deleteAll()\"><i class=\"fa fa-times\" aria-hidden=\"true\"></i></a></th>\r\n\t\t\t\t</tr>\r\n\t\t\t</thead>\r\n\t\t\t<tbody>\r\n\t\t\t</tbody>\r\n\t\t</table>\r\n    </div>\r\n    <div data-options=\"region:'center'\" >\r\n\t\t<table class=\"table-list\" id=\"searchSelectUser\" toolbar=\"#tb\"></table>\r\n\t</div>\r\n\t<div id=\"tb\" style=\"padding:3px\">\r\n\t\t<span>账号:</span>\r\n\t\t<input id=\"account\" style=\"line-height:17px;border:1px solid #ccc;width: 150px;\"/>\r\n\t\t<span>名字:</span>\r\n");
                    var8.write("\t\t<input id=\"name\" style=\"line-height:17px;border:1px solid #ccc;width: 150px;\"/>\r\n\t\t<a href=\"#\" class=\"easyui-linkbutton\" plain=\"true\" onclick=\"doSearch()\"><i class=\"fa fa-search\" aria-hidden=\"true\"></i> 查询</a>\r\n\t\t<a href=\"#\" class=\"easyui-linkbutton\" plain=\"true\" onclick=\"doClear()\"><i class=\"fa fa-refresh\" aria-hidden=\"true\"></i> 清空</a>\r\n\t</div>\r\n</div>\r\n\r\n<script type=\"text/javascript\">\r\n//默认第一页，\r\nvar pageNumber = 1;\r\n//每页显示5行\r\nvar pageSize = 10;\r\nvar selectIds = [];\r\ninitSelectUser();\r\n\r\n$('#searchSelectUser').datagrid({\r\n    url:'../designer/api/getPageUsers?token=");
                    var8.write((String)PageContextImpl.proprietaryEvaluate("${token}", String.class, var4, (ProtectedFunctionMapper)null));
                    var8.write("',\r\n    fit: true,\r\n    fitColumns:true,\r\n    rownumbers:true,\r\n    pagination: true, //在DataGrid控件底部显示分页工具栏。\r\n    pageNumber: pageNumber,\r\n    rownumbers: true,\r\n    pageSize: pageSize,\r\n    pageList: [5,10,15,20],\r\n    columns:[[\r\n    \t{field:'ck',checkbox:true},\r\n\t\t{field:'id',title:'用户账号',width:60},\r\n    \t{field:'firstName',title:'用户名字',width:60},\r\n    ]],\r\n    onSelect: function (index, row) {\r\n    \tcheckSelect();\r\n    },\r\n});\r\n\r\nfunction getselectExpressionListSelections(field) {\r\n//    var ids = [];\r\n//    var rows = $('#searchSelectUser').datagrid('getSelections');\r\n//    for (var i = 0; i < rows.length; i++) {\r\n//        ids.push(rows[i][field]);\r\n//    }\r\n//    ids.join(',');\r\n    return getValuesById('id');\r\n};\r\n\r\nfunction initSelectUser(){\r\n\tvar api = frameElement.api;\r\n\tconsole.log('api.data ',api.data);\r\n\tif(api.data && api.data.userids){\r\n\t\tvar userids = api.data.userids;\r\n\t\tvar useridsArray = userids.split(',');\r\n\t\tfor(var i =0; i<useridsArray.length;i++) {\r\n\t\t\tvar rowsId = useridsArray[i];\r\n\t\t\tvar rowsName ;\r\n");
                    var8.write("    \t\tif(hasRepeart(rowsId)) {\r\n\t\t\t\tvar newRow = '<tr><td><input type=\"hidden\" value='+rowsId+' id=\"id\"><span>'+rowsId+'</span></td><td><a href=\"#\" class=\"ace_button\" onclick=\"deleteRow(this)\"><i class=\"fa fa-trash-o\"></i> 删除</a></td></tr>';\r\n\t\t\t\t$(\"#checkedTable1\").append(newRow);\r\n\t\t  \t}\r\n\t\t}\r\n\t}\r\n}\r\nfunction doSearch(){\r\n\t$('#searchSelectUser').datagrid('load',{\r\n\t\taccount: $('#account').val(),\r\n\t\tname: $('#name').val()\r\n\t});\r\n}\r\nfunction doClear(){\r\n\t$('#account').val(\"\")\r\n\t$('#name').val(\"\")\r\n\t$('#searchSelectUser').datagrid('load',{});\r\n}\r\nfunction checkSelect() {\r\n\tvar rows = $(\"#searchSelectUser\").datagrid(\"getChecked\");\r\n\tif(rows.length>=1) {\r\n\t\tfor(var i =0; i<rows.length;i++) {\r\n\t\t\tvar rowsName = rows[i]['id'];\r\n\t\t\tvar userName = rows[i]['last'];\r\n    \t\tvar rowsId = rows[i]['id'];\r\n    \t\tif(hasRepeart(rowsId)) {\r\n\t\t\t\tvar newRow = '<tr><td><input type=\"hidden\" value='+rowsId+' id=\"id\"><input type=\"hidden\" value='+userName+' id=\"userName\"><span>'+rowsName+'</span></td><td><a href=\"#\" class=\"ace_button\" onclick=\"deleteRow(this)\"><i class=\"fa fa-trash-o\"></i> 删除</a></td></tr>';\r\n");
                    var8.write("\t\t\t\t$(\"#checkedTable1\").append(newRow);\r\n\t\t  \t}\r\n\t\t}\r\n\t}\r\n}\r\nfunction hasRepeart(col) {\r\n\tvar flag = true;\r\n\t$(\"#checkedTable1\").find(\"tbody\").find(\"tr\").each(function() {\r\n\t\tif ($(this).find(\"input\").val() == col) {\r\n\t\t\tflag = false;\r\n\t\t\treturn false;\r\n\t\t}\r\n\t});\r\n\treturn flag;\r\n}\r\nfunction deleteRow(row) {\r\n\tvar tr = row.parentNode.parentNode;\r\n\tvar tbody = tr.parentNode;\r\n\ttbody.removeChild(tr);\r\n}\r\nfunction deleteAll() {\r\n\t$(\"#checkedTable1 tbody\").html(\"\");\r\n}\r\nfunction getValuesById(id){\r\n\tvar str='';\r\n\t$(\"#checkedTable1\").find(\"tbody tr\").each(function() {\r\n\t\tstr += $(this).find(\"input[id=\"+id+\"]\").val()+\",\";\r\n\t});\r\n\tstr = str.substring(0,str.length-1);\r\n\treturn str;\r\n}\r\n//回车键事件\r\n$(document).keyup(function(){\r\n\tif(event.keyCode==13){\r\n\t\t//执行函数\r\n\t\tdoSearch();\r\n\t}\r\n});\r\n</script>\r\n</body>\r\n</html>\r\n\r\n");
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
