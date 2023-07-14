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

public final class TaskVariableConfig_jsp extends HttpJspBase implements JspSourceDependent, JspSourceImports {
    private static final JspFactory a = JspFactory.getDefaultFactory();
    private static Map<String, Long> b = new HashMap(1);
    private static final Set<String> c;
    private static final Set<String> d;
    private TagHandlerPool e;
    private volatile ExpressionFactory f;
    private volatile InstanceManager g;

    public TaskVariableConfig_jsp() {
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
//                    String var13 = request.getScheme() + "://" + request.getServerName() + ":8989"  + var12;
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

                    var8.write("\r\n<t:base type=\"jquery,easyui,tools\" cssTheme=\"default\"></t:base> \r\n<script type=\"text/javascript\">\r\n<!--\r\nvar variableFieldsEditCount = 0;\r\nvar variableId = '");
                    var8.write((String)PageContextImpl.proprietaryEvaluate("${id}", String.class, var4, (ProtectedFunctionMapper)null));
                    var8.write("';\r\nvar processNode='");
                    var8.write((String)PageContextImpl.proprietaryEvaluate("${processNode}", String.class, var4, (ProtectedFunctionMapper)null));
                    var8.write("';\r\nvar processId='");
                    var8.write((String)PageContextImpl.proprietaryEvaluate("${processId}", String.class, var4, (ProtectedFunctionMapper)null));
                    var8.write("';\r\nvar processDefinitionId='");
                    var8.write((String)PageContextImpl.proprietaryEvaluate("${processDefinitionId}", String.class, var4, (ProtectedFunctionMapper)null));
                    var8.write("';\r\nvar files='processproid,processprokey,processprotype,processproval,processproexp,processproname,processprodatatype';\r\n$(function(){\r\n\t_task_variable_fields_dg=$('#task-variables-fields-list').datagrid({\r\n\t\t//title:\"Listener\",\r\n\t\turl:'activitiController.do?getVariable&processNode='+processNode+'&processId='+processId+'&field='+files+'&variableId='+variableId,//\r\n\t\tsingleSelect:true,\r\n\t\twidth:700,\r\n\t\theight:200,\r\n\t\ticonCls:'icon-edit',\r\n\t\t//fit:true,\r\n\t\t//idField:'id',\r\n\t\t//pagination:true,\r\n\t\t//pageSize:15,\r\n\t\t//pageNumber:1,\r\n\t\t//pageList:[10,15],\r\n\t\trownumbers:true,\r\n\t\t//sortName:'id',\r\n\t    //sortOrder:'asc',\r\n\t    striped:true,\r\n\t    toolbar:[{\r\n\t        text:'New',\r\n\t        iconCls:'icon-add',\r\n\t        handler:function(){\r\n\t\t    \tif(variableFieldsEditCount>0){\r\n\t\t    \t\t$.messager.alert(\"error\",\"有可编辑的单元格，不能添加\",'error');\r\n\t\t\t\t\treturn;\r\n\t\t\t\t}\r\n\t\t\t\t$('#task-variables-fields-list').datagrid('appendRow',{\r\n\t\t\t\t\tprocessproid:'',\r\n\t\t\t\t\tprocessprokey:'',\r\n\t\t\t\t\tprocessprotype:'',\r\n\t\t\t\t\tprocessproval:'',\r\n\t\t\t\t\tprocessproexp:'',\r\n");
                    var8.write("\t\t\t\t\tprocessproname:'',\r\n\t\t\t\t\tprocessprodatatype:'',\r\n\t\t\t\t\taction:''\r\n\t\t\t\t});\r\n\t\t\t\tvar index = $('#task-variables-fields-list').datagrid('getRows').length-1;\r\n\t\t\t\t$('#task-variables-fields-list').datagrid('beginEdit', index);\r\n\t        }\r\n\t    }],\r\n\t\t\r\n\t\tonDblClickRow:function(rowIndex,rowData){\r\n\t\t\teditVariableField(rowIndex);\r\n\t\t},\r\n\t\t\r\n\t\tonBeforeEdit:function(index,row){\r\n\t\t\trow.editing = true;\r\n\t\t\t$(this).datagrid('refreshRow', index);\r\n\t\t\tvariableFieldsEditCount++;\r\n\t\t},\r\n\t\tonAfterEdit:function(index,row){\r\n\t\t\trow.editing = false;\r\n\t\t\t$(this).datagrid('refreshRow', index);\r\n\t\t\tvariableFieldsEditCount--;\r\n\t\t},\r\n\t\tonCancelEdit:function(index,row){\r\n\t\t\trow.editing = false;\r\n\t\t\t$(this).datagrid('refreshRow', index);\r\n\t\t\tvariableFieldsEditCount--;\r\n\t\t}\r\n\t});\r\n\t$('#fieldSaveBt').linkbutton({\r\n\t\ticonCls:\"icon-save\"\r\n\t});\r\n\t$('#fieldCancelBt').linkbutton({\r\n\t\ticonCls:\"icon-cancel\"\r\n\t});\r\n});\r\n\r\nfunction variableFieldsActionFormatter(value,rowData,rowIndex){\r\n\tvar id = rowIndex;\r\n\tvar s='<img onclick=\"saveVariableField('+id+')\" src=\"plug-in/designer/img/ok.png\" title=\"'+\"确定\"+'\" style=\"cursor:hand;\"/>';\r\n");
                    var8.write("\tvar c='<img onclick=\"cancelVariableField('+id+')\" src=\"plug-in/designer/img/cancel.png\" title=\"'+\"取消\"+'\" style=\"cursor:hand;\"/>';\r\n\tvar e='<img onclick=\"editVariableField('+id+')\" src=\"plug-in/designer/img/modify.png\" title=\"'+\"修改\"+'\" style=\"cursor:hand;\"/>';\r\n\tif(rowData.editing)\r\n\t\treturn s;\r\n\telse\r\n\t\treturn e;\r\n}\r\nfunction cancelVariableField(id){\r\n\t_task_variable_fields_dg.datagrid('cancelEdit', id);\r\n}\r\nfunction editVariableField(id){\r\n\t_task_variable_fields_dg.datagrid('beginEdit', id);\r\n}\r\nfunction saveVariableField(id){\r\n\t//alert(id);\r\n\t_task_variable_fields_dg.datagrid('endEdit', id);\r\n\t//alert(editcount);\r\n}\r\n\r\nfunction refreshAllVariableFields(){\r\n\tvar rs = _task_variable_fields_dg.datagrid('getRows');\r\n\tfor(var i=0;i<rs.length;i++){\r\n\t\tvar ri =_task_variable_fields_dg.datagrid('getRowIndex',rs[i]);\r\n\t\t_task_variable_fields_dg.datagrid('refreshRow',ri);\r\n\t}\r\n}\r\nfunction createNewVariable(){\r\n\t\r\n}\r\nfunction getExsitingForm(){\r\n\t\r\n}\r\nfunction getVariableFieldsGridChangeRows(){\r\n\tif(variableFieldsEditCount>0){\r\n");
                    var8.write("\t\t$.messager.alert(\"error\",\"\",'error');\r\n\t\treturn null;\r\n\t}\r\n    var insertRows = _task_variable_fields_dg.datagrid('getChanges','inserted');   \r\n    var updateRows = _task_variable_fields_dg.datagrid('getChanges','updated');   \r\n    var deleteRows = _task_variable_fields_dg.datagrid('getChanges','deleted');   \r\n    var changesRows = {   \r\n            inserted : [],   \r\n            updated : [],\r\n            deleted : []  \r\n            };   \r\n    if (insertRows.length>0) {   \r\n        for (var i=0;i<insertRows.length;i++) {   \r\n            changesRows.inserted.push(insertRows[i]);\r\n        }   \r\n    }   \r\n\r\n    if (updateRows.length>0) {   \r\n        for (var k=0;k<updateRows.length;k++) {   \r\n            changesRows.updated.push(updateRows[k]);\r\n        }   \r\n    }   \r\n       \r\n    if (deleteRows.length>0) {   \r\n        for (var j=0;j<deleteRows.length;j++) {   \r\n            changesRows.deleted.push(deleteRows[j]);   \r\n        }   \r\n    }\r\n    return changesRows;\r\n}\r\nfunction saveVariableConfig(){\r\n\tif(variableId != \"\" && variableId != null && variableId!=\"null\"&&variableId!=\"NULL\"){\r\n");
                    var8.write("\t\tvar r = updateExistingVariable();\r\n\t\tif(!r)return;\t\t\r\n\t}else{\r\n\t\tvar r = insertNewVariable();\r\n\t\tif(!r)return;\r\n\t}\r\n\t_variable_win.window('close');\r\n\t\r\n}\r\nfunction insertNewVariable(){\r\n    var changesRows = getVariableFieldsGridChangeRows();\r\n    var params=\"\";\r\n    if(changesRows == null)return false;\r\n    var insertRows = changesRows['inserted'];   \r\n    if (insertRows.length>0) {   \r\n        for (var i=0;i<insertRows.length;i++) {\r\n        \tvar id=insertRows[i].processproid;\r\n        \tvar name=insertRows[i].processprokey;\r\n        \tvar value=insertRows[i].processproval;\r\n        \tvar type=insertRows[i].processprotype;\r\n        \tvar exp=insertRows[i].processproexp;\r\n        \tvar remark=insertRows[i].processproname;\r\n        \tvar source=insertRows[i].processprodatatype;\r\n        \tparams=params+\"processId=\"+processId+\"###tid=\"+tid+\"###name=\"+name+\"###type=\"+type+\"###value=\"+value+\"###exp=\"+exp+\"###remark=\"+remark+\"###source=\"+source+\"###varibleid=\"+id+\"@@@\";\r\n       }   \r\n    }\r\n    \r\n    $.ajax({\r\n\t\turl : \"activitiController.do?saveProcessDescriptor\",\r\n");
                    var8.write("\t\ttype : 'POST',\r\n\t\tdata : {\r\n\t\t\tprocessDescriptor : '',\r\n\t\t\tprocessName : '',\r\n\t\t\tprocessId : processId,\r\n\t\t\tparams:params,\r\n\t\t\tnodes:'',\r\n\t\t\tprocessDefinitionId:processDefinitionId\r\n\t\t},\r\n\t\tdataType : 'json',\r\n\t\terror : function() {\r\n\t\t\treturn \"\";\r\n\t\t},\r\n\t\tsuccess : function(data) {\r\n\t\t\tif (data.success) {\r\n\t\t\t\t$.messager.alert('Info', '保存成功!', 'info');\r\n\t\t\t\t$('#task-variable-properties-list').datagrid('reload');\r\n\t\t\t} \r\n\t\t}\r\n\t});\r\n\treturn true;\r\n}\r\nfunction updateExistingVariable(){\r\n\tvar params=\"\";\r\n\tvar changesRows = getVariableFieldsGridChangeRows();\r\n    if(changesRows == null)return false;\r\n    var insertRows = changesRows['inserted'];   \r\n    var updateRows = changesRows['updated'];   \r\n    if (insertRows.length>0) {   \r\n        for (var i=0;i<insertRows.length;i++) {  \r\n        \tvar id=insertRows[i].processproid;\r\n        \tvar name=insertRows[i].processprokey;\r\n        \tvar value=insertRows[i].processproval;\r\n        \tvar type=insertRows[i].processprotype;\r\n        \tvar exp=insertRows[i].processproexp;\r\n");
                    var8.write("        \tvar remark=insertRows[i].processproname;\r\n        \tvar source=insertRows[i].processprodatatype;\r\n        \tparams=params+\"processId=\"+processId+\"###tid=\"+tid+\"###name=\"+name+\"###type=\"+type+\"###value=\"+value+\"###exp=\"+exp+\"###remark=\"+remark+\"###source=\"+source+\"###varibleid=\"+id+\"@@@\";\r\n        }   \r\n    }   \r\n\r\n    if (updateRows.length>0) { \r\n        for (var k=0;k<updateRows.length;k++) { \r\n        \tvar id=updateRows[k].processproid;\r\n        \tvar name=updateRows[k].processprokey;\r\n        \tvar value=updateRows[k].processproval;\r\n        \tvar type=updateRows[k].processprotype;\r\n        \tvar exp=updateRows[k].processproexp;\r\n        \tvar remark=updateRows[k].processproname;\r\n        \tvar source=updateRows[k].processprodatatype;\r\n        \tparams=params+\"processId=\"+processId+\"###tid=\"+tid+\"###name=\"+name+\"###type=\"+type+\"###value=\"+value+\"###exp=\"+exp+\"###remark=\"+remark+\"###source=\"+source+\"###varibleid=\"+id+\"@@@\";\r\n        }   \r\n    }  \r\n    $.ajax({\r\n\t\turl : \"activitiController.do?saveProcessDescriptor\",\r\n");
                    var8.write("\t\ttype : 'POST',\r\n\t\tdata : {\r\n\t\t\tprocessDescriptor : '',\r\n\t\t\tprocessName : '',\r\n\t\t\tprocessId : processId,\r\n\t\t\tparams:params,\r\n\t\t\tnodes:'',\r\n\t\t\tprocessDefinitionId:processDefinitionId\r\n\t\t},\r\n\t\tdataType : 'json',\r\n\t\terror : function() {\r\n\t\t\treturn \"\";\r\n\t\t},\r\n\t\tsuccess : function(data) {\r\n\t\t\tif (data.success) {\r\n\t\t\t\t$.messager.alert('Info', '保存成功!', 'info');\r\n\t\t\t\t$('#task-variable-properties-list').datagrid('reload');\r\n\t\t\t} \r\n\t\t}\r\n\t});   \r\n    return true;\r\n}\r\n\r\n\r\nfunction closeTaskVariableWin(){\r\n\t_variable_win.window('close');\r\n}\r\n//-->\r\n</script>\r\n<table>\r\n\t\t<tr>\r\n\t\t\t<td>\r\n\t\t\t\t<table id=\"task-variables-fields-list\">\r\n\t\t\t\t\t<thead>\r\n\t\t\t\t\t<tr>\r\n\t\t\t\t\t<th field=\"processproid\" hidden=\"true\"></th>\r\n\t\t\t\t\t<th field=\"processprokey\" width=\"100\" align=\"middle\" sortable=\"false\" editor=\"{\r\n\t\t\t\t\t\ttype:'validatebox',\r\n\t\t\t\t\t\toptions:{\r\n\t\t\t\t\t\trequired:true,\r\n\t\t\t\t\t\tvalidType:'length[1,100]'\r\n\t\t\t\t\t}}\">名称</th>\r\n\t\t\t\t\t<th field=\"processprotype\" width=\"100\" align=\"middle\" sortable=\"false\" editor=\"{\r\n\t\t\t\t\t\ttype:'combobox',\r\n\t\t\t\t\t\toptions:{\r\n");
                    var8.write("\t\t\t\t\t\t\teditable:false,\r\n\t\t\t\t\t\t\tdata:[{id:'S',text:'字符',selected:true},{id:'I',text:'整型'},{id:'B',text:'布尔型'},{id:'F',text:'单精度浮点数'},{id:'L',text:'长整型'},{id:'D',text:'日期'},{id:'SD',text:'sql Date类型'},{id:'N',text:'双精度浮点数'}],\r\n\t\t\t\t\t\t\tvalueField:'id',\r\n\t\t\t\t\t\t\ttextField:'text'\r\n\t\t\t\t\t}}\">类型</th>\r\n\t\t\t\t\t<th field=\"processproval\" width=\"100\" align=\"middle\" sortable=\"false\" editor=\"{\r\n\t\t\t\t\t\ttype:'validatebox',\r\n\t\t\t\t\t\toptions:{\r\n\t\t\t\t\t\tvalidType:'length[1,100]'\r\n\t\t\t\t\t}}\">值</th>\r\n\t\t\t\t\t<th field=\"processproexp\" width=\"100\" align=\"middle\" sortable=\"false\" editor=\"{\r\n\t\t\t\t\t\ttype:'validatebox',\r\n\t\t\t\t\t\toptions:{\r\n\t\t\t\t\t\tvalidType:'length[1,100]'\r\n\t\t\t\t\t}}\">表达式</th>\r\n\t\t\t\t\t<th field=\"processproname\" width=\"100\" align=\"middle\" sortable=\"false\" editor=\"{\r\n\t\t\t\t\t\ttype:'validatebox',\r\n\t\t\t\t\t\toptions:{\r\n\t\t\t\t\t\tvalidType:'length[1,100]'\r\n\t\t\t\t\t}}\">描述</th>\r\n\t\t\t\t\t<th field=\"processprodatatype\" width=\"100\" align=\"middle\" sortable=\"false\" editor=\"{\r\n\t\t\t\t\t\ttype:'combobox',\r\n\t\t\t\t\t\toptions:{\r\n\t\t\t\t\t\t\teditable:false,\r\n\t\t\t\t\t\t\tdata:[{id:'database',text:'数据库'},{id:'page',text:'页面'}],\r\n");
                    var8.write("\t\t\t\t\t\t\tvalueField:'id',\r\n\t\t\t\t\t\t\ttextField:'text'\r\n\t\t\t\t\t}}\">来源</th>\r\n\t\t\t\t\t<th field=\"action\" width=\"80\" align=\"middle\" formatter=\"variableFieldsActionFormatter\">操作</th>\r\n\t\t\t\t\t</tr>\r\n\t\t\t\t\t</thead>\r\n\t\t\t\t</table>\r\n\t\t\t</td>\r\n\t\t</tr>\r\n\t\t<tr>\r\n\t\t\t<td align=\"center\">\r\n\t\t\t\t<a href=\"##\" id=\"fieldSaveBt\" onclick=\"saveVariableConfig()\">Save</a>\r\n\t\t\t\t<a href=\"##\" id=\"fieldCancelBt\" onclick=\"closeTaskVariableWin()\">Cancel</a>\r\n\t\t\t</td>\r\n\t\t</tr>\r\n</table>");
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
