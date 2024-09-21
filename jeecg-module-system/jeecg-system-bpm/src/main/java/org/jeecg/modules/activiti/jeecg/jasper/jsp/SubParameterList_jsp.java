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

public final class SubParameterList_jsp extends HttpJspBase implements JspSourceDependent, JspSourceImports {
    private static final JspFactory a = JspFactory.getDefaultFactory();
    private static Map<String, Long> b = new HashMap(5);
    private static final Set<String> c;
    private static final Set<String> d;
    private TagHandlerPool e;
    private volatile ExpressionFactory f;
    private volatile InstanceManager g;

    public SubParameterList_jsp() {
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

                    var8.write("\r\n<!DOCTYPE html >\r\n<html>\r\n<head>\r\n<title>子流程参数配置页面</title>\r\n<link id=\"easyuiTheme\" rel=\"stylesheet\" href=\"");
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
                    var8.write("/plug-in/designer/easyui/easyui-lang-zh_CN.js\"></script>\r\n</head>\r\n<body style=\"overflow-y: hidden\" scroll=\"no\">\r\n<a href=\"#\" class=\"easyui-linkbutton\" plain=\"true\" onclick=\"addRemark()\"><i class=\"fa fa-plus-square\" aria-hidden=\"true\"></i> 添加</a>\r\n<a href=\"#\" class=\"easyui-linkbutton\" plain=\"true\" onclick=\"removeit()\"><i class=\"fa fa-minus-square\" aria-hidden=\"true\"></i> 删除</a>\r\n<a href=\"#\" class=\"easyui-linkbutton\" plain=\"true\" onclick=\"save()\"><i class=\"fa fa-check-square\" aria-hidden=\"true\"></i> 保存</a>\r\n<table id=\"remark_dg\"></table>\r\n<script type=\"text/javascript\">\r\n/* $('#selectExpressionList').datagrid({\r\n    url:'../designer/api/getExpressions',\r\n    fit: true,\r\n    fitColumns:true,\r\n    rownumbers:true,\r\n    columns:[[\r\n    \t{field:'id',title:'ID',hidden:true},\r\n\t\t{field:'name',title:'表达式名称',width:30},\r\n\t\t{field:'expression',title:'表达式',width:30}\r\n    ]]\r\n}); */\r\n\r\nfunction getAllSubParams() {\r\n    var rows = $('#remark_dg').datagrid(\"getRows\");\r\n    console.log(rows)\r\n    if(!checkIsNotNull(rows)){\r\n\t   $.messager.alert('提示', '参数不允许存在空值！', 'error', function() {});  \r\n");
                    var8.write("\t   return;\r\n    }\r\n    return rows\r\n};\r\n</script>\r\n\r\n<script type=\"text/javascript\">\r\nvar typeJson = [{\"value\":\"in\",\"text\":\"传进\"},{\"value\":\"out\",\"text\":\"传出\"}];\r\nvar subParamData = [\r\n\t{\"id\":\"1\",\"source\":\"applyUserId\", \"target\":\"applyUserId\", \"transfer\":\"in\"},\r\n\t{\"id\":\"2\",\"source\":\"JG_LOCAL_PROCESS_ID\", \"target\":\"JG_SUB_MAIN_PROCESS_ID\", \"transfer\":\"in\"},\r\n];\r\n\r\nvar api = frameElement.api;\r\nconsole.log('传入参数 api.data ',api.data);\r\nif(api.data && api.data.subParams){\r\n\tif(api.data.subParams && api.data.subParams.length!=0){\r\n\t\tsubParamData = api.data.subParams\r\n\t}\r\n}\r\n$obj = $(\"#remark_dg\");\r\nvar indexs;\r\n   $obj.datagrid({\r\n\t    url:null,\r\n        columns: [[ //显示的列\r\n        \t  {\r\n                  field: 'id',\r\n                  title: '编号',\r\n                  width: 100,\r\n                  sortable: true,\r\n                  checkbox: true\r\n              },\r\n\t\t   {\r\n\t\t       field: 'source',\r\n\t\t       title: '源头变量',\r\n\t\t       width: 100,\r\n\t\t       editor: {\r\n\t\t           type: 'validatebox',\r\n\t\t           options: {\r\n");
                    var8.write("\t\t\r\n\t\t           }\r\n\t\t       }\r\n\t\t   },\r\n\t\t   {\r\n\t\t       field: 'target',\r\n\t\t       title: '目标变量',\r\n\t\t       width: 100,\r\n\t\t       editor: {\r\n\t\t           type: 'validatebox',\r\n\t\t           options: {\r\n\t\t\r\n\t\t           }\r\n\t\t       }\r\n\t\t   },\r\n\t\t   {\r\n\t\t       field: 'transfer',\r\n\t\t       title: '方向(传进/传出)',\r\n\t\t       sortable: true,\r\n\t\t       width: 100,\r\n\t\t       editor: {\r\n\t\t           type: 'combobox',\r\n\t\t           options: {\r\n\t\t        \t   editable: false,\r\n\t\t        \t   valueField:'value',\r\n\t\t               textField:'text',\r\n\t\t               data: typeJson,\r\n\t\t               //required:true\r\n\t\t           },\r\n\t\t       }\r\n\t\t   }]],\r\n       toolbar:'#tb',  //表格菜单\r\n       fit:true,\r\n       fitColumns:true,\r\n       loadMsg:'加载中...', //加载提示\r\n       rownumbers:true, //显示行号列\r\n       onClickCell: onClickCell,\r\n       queryParams:{   //在请求数据是发送的额外参数，如果没有则不用写\r\n       },\r\n       onLoadSuccess:function(data){\r\n       },\r\n       rowStyler:function(index,row){\r\n       }\r\n   });\r\n    //可编辑行\r\n   var editIndex = undefined;\r\n");
                    var8.write("   function endEditing(){\r\n       if (editIndex == undefined){return true}\r\n       if ($('#remark_dg').datagrid('validateRow', editIndex)){\r\n           $('#remark_dg').datagrid('endEdit', editIndex);\r\n           editIndex = undefined;\r\n           return true;\r\n       } else {\r\n           return false;\r\n       }\r\n   }\r\n  //修改的方式是直接点击单元格，所以table要加上onClickCell属性，然后重写onClickCell方法\r\n   function onClickCell(index, field){\r\n       if (editIndex != index){\r\n           if (endEditing()){\r\n               $('#remark_dg').datagrid('selectRow', index).datagrid('beginEdit', index);\r\n               var ed = $('#remark_dg').datagrid('getEditor', {index:index,field:field});\r\n               ($(ed.target).data('textbox') ? $(ed.target).textbox('textbox') : $(ed.target)).focus();\r\n               editIndex = index;\r\n           } else {\r\n               $('#remark_dg').datagrid('selectRow', editIndex);\r\n           }\r\n       }\r\n   }\r\n // 添加\r\n function addRemark(){\r\n       if (endEditing()){\r\n           $('#remark_dg').datagrid('appendRow',{id:generateUUID()});\r\n");
                    var8.write("           editIndex = $('#remark_dg').datagrid('getRows').length-1;\r\n           $('#remark_dg').datagrid('selectRow', editIndex).datagrid('beginEdit', editIndex);\r\n       }\r\n   }\r\n   // 删除\r\n    function removeit(){  \r\n       $.messager.confirm('确认','确认删除?',function(r){  \r\n           if (r){\r\n        \t   var rows = $('#remark_dg').datagrid('getSelections');\r\n        \t   console.log(\"del rows\",rows)\r\n        \t   subParamData = subParamData.filter(function (item) {\r\n        \t\t   var b;\r\n        \t\t   try {\r\n        \t\t\t  b = !rows.includes(item)\r\n        \t\t   }catch(err) {\r\n\t\t\t\t\t  //IE11不支持includes方法\t\t\t   \r\n        \t\t\t  b = !(rows.indexOf(item)!=-1)\r\n        \t\t   }\r\n                   return b;\r\n               })\r\n               $(\"#remark_dg\").datagrid('loadData',subParamData);\r\n              /*  if (rows) {\r\n                   for(var i=0; i<rows.length;i++){\r\n                       var rowIndex = $('#remark_dg').datagrid('getRowIndex', rows[i]);\r\n                       $('#remark_dg').datagrid('deleteRow', rowIndex);  \r\n");
                    var8.write("                   }\r\n                } */\r\n           }  \r\n       });  \r\n\r\n   }\r\n   // 保存方法（添加修改用了一个方法accept()）\r\n   function save(){\r\n\t   var rows = $(\"#remark_dg\").datagrid(\"getRows\"); //这段代码是获取当前页的所有行。\r\n\t   console.log(\"save rows 子流程参数\",rows)\r\n\t   //设置为非编辑模式\r\n\t   $('#remark_dg').datagrid('acceptChanges');\r\n\t   \r\n\t   if(!checkIsNotNull(rows)){\r\n\t\t   $.messager.alert('提示', '参数不允许存在空值！', 'error', function() {});  \r\n\t\t   return;\r\n\t   }\r\n\t   //设置为非编辑模式\r\n\t   $('#remark_dg').datagrid('acceptChanges');\r\n   }\r\n   \r\n  function generateUUID() {\r\n    var d = new Date().getTime();\r\n    if (window.performance && typeof window.performance.now === \"function\") {\r\n        d += performance.now(); //use high-precision timer if available\r\n    }\r\n    var uuid = 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {\r\n        var r = (d + Math.random() * 16) % 16 | 0;\r\n        d = Math.floor(d / 16);\r\n        return (c == 'x' ? r : (r & 0x3 | 0x8)).toString(16);\r\n    });\r\n    return uuid;\r\n}\r\n  \r\n  function checkIsNotNull(rows) {\r\n");
                    var8.write("\t  for(var i=0;i<rows.length;i++)\r\n\t  {\r\n\t\t if(!rows[i]){\r\n\t\t\t return false;\r\n\t\t }\r\n\t\t var source = rows[i].source;\r\n\t\t var target = rows[i].target;\r\n\t\t var transfer = rows[i].transfer;\r\n\t\t if(!source || !target || !transfer){\r\n\t\t\t return false;\r\n\t\t }\r\n\t  }\r\n\t  return true;\r\n  }\r\n  //初始化数据\r\n  $(\"#remark_dg\").datagrid('loadData',subParamData);\r\n</script>\r\n</body>\r\n</html>\r\n\r\n");
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
        b.put("jar:file:/H:/spacelic/jeecg-boot-module-desiger-2021/WebRoot/WEB-INF/lib/jstl-1.2.jar!/META-INF/c.tld", 1153356282000L);
        b.put("jar:file:/H:/spacelic/jeecg-boot-module-desiger-2021/WebRoot/WEB-INF/lib/jstl-1.2.jar!/META-INF/fmt-1_0-rt.tld", 1153356282000L);
        b.put("/context/mytags.jsp", 1634671342300L);
        b.put("/WEB-INF/lib/jstl-1.2.jar", 1552489370216L);
        b.put("jar:file:/H:/spacelic/jeecg-boot-module-desiger-2021/WebRoot/WEB-INF/lib/jstl-1.2.jar!/META-INF/fn.tld", 1153356282000L);
        c = new HashSet();
        c.add("javax.servlet");
        c.add("org.jeecg.designer.commons");
        c.add("javax.servlet.http");
        c.add("javax.servlet.jsp");
        d = null;
    }
}
