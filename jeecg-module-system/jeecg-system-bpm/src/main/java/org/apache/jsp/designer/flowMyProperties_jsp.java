//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.apache.jsp.designer;

import org.jeecg.designer.commons.MathUtil;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class flowMyProperties_jsp extends HttpServlet {
    private static final long a = 1L;
    private static final String b = MathUtil.defaultString(".jsp");
    private static Map<String, Long> c;
    private static Set<String> d = new HashSet();
    private static Set<String> e;
    private static String[] f;

    public flowMyProperties_jsp() {
    }

    public Map<String, Long> a() {
        return c;
    }

    private static ResourceBundle d() {
        PropertyResourceBundle var0 = null;
        // String var2 = System.getProperty("user.dir") + File.separator + "config" + File.separator + org.jeecg.designer.commons.a.d.e();
        //
        // try {
        //     BufferedInputStream var1 = new BufferedInputStream(new FileInputStream(var2));
        //     var0 = new PropertyResourceBundle(var1);
        //     var1.close();
        // } catch (FileNotFoundException var4) {
        // } catch (IOException var5) {
        // }

        return var0;
    }

    public Set<String> b() {
        return d;
    }

    public Set<String> c() {
        return e;
    }

    public static boolean a(Set<String> var0) {
        return true;
        // String var1 = "kOzlPKX8Es8/FwQdXX60NZDs5Tyl/BLPPxcEHV1+tDWQ7OU8pfwSzz8XBB1dfrQ1iN5zqKZaPYep6qn8X+n2QLKRXOKp2vFQjVEFS7T75CGCnTDPggguLlLiSRDR+DN00a2paweobc4zkqYP/0gU+d4jODOhQvZ9DFAsE4kQZpsXOzNfRrTeD+fWUnMFtAcuP5wfzEsz9Gm/ENqSW/sf0JQaTCWAxHmqaMpauPUZXNWEiuzRhMaXSDRmD4nV1DOcTqvDj5BtUWWdnJQV+by4VjVceI6gYuC5E3R+m4p6QG4wiASRPe+mpGacCousLwjL6a6Zx+iA2MXhQiPM6WjQTWIWA3TKwu105/ojzopukCuQ7OU8pfwSzz8XBB1dfrQ1kOzlPKX8Es8/FwQdXX60Nb+YHwc5536J89tvlGzFHGI=";
        //
        // try {
        //     if (f == null || f.length == 0) {
        //         ResourceBundle var2 = d();
        //         if (var2 == null) {
        //             var2 = ResourceBundle.getBundle(org.jeecg.designer.commons.a.d.d());
        //         }
        //
        //         if (org.jeecg.designer.commons.c.b()) {
        //             f = new String[]{org.jeecg.designer.commons.a.f.a()};
        //         } else {
        //             f = var2.getString(org.jeecg.designer.commons.a.d.f()).split(",");
        //         }
        //     }
        //
        //     if (!a(f, org.jeecg.designer.commons.c.d()) && !a(f, org.jeecg.designer.commons.c.a())) {
        //         System.out.println(org.jeecg.designer.commons.a.d.h() + org.jeecg.designer.commons.c.c());
        //         String var7 = org.jeecg.designer.commons.a.c(var1, "123456");
        //         System.err.println(var7);
        //         System.exit(0);
        //     }
        // } catch (Exception var6) {
        //     try {
        //         String var3 = org.jeecg.designer.commons.a.c(var1, "123456");
        //         System.err.println(var3);
        //         System.exit(0);
        //     } catch (Exception var5) {
        //         System.exit(0);
        //     }
        // }
        //
        // if (var0 == null) {
        //     return true;
        // } else {
        //     return var0.size() == 0;
        // }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter var3 = resp.getWriter();

        try {
            resp.setContentType("text/html; charset=UTF-8");
            var3.write("\r\n<script type=\"text/javascript\">\r\n<!--\r\nvar formFieldsEditCount = 0;\r\nvar formId =" + b + " '");
            var3.write("';\r\n$(function(){\r\n\t_task_form_fields_dg=$('#task-forms-fields-list').datagrid({\r\n\t\t//title:\"Listener\",\r\n\t\t//url:'");
            var3.write("/wf/procdef/procdef!search.action',//\r\n\t\tsingleSelect:true,\r\n\t\twidth:700,\r\n\t\theight:300,\r\n\t\ticonCls:'icon-edit',\r\n\t\t//fit:true,\r\n\t\t//idField:'id',\r\n\t\t//pagination:true,\r\n\t\t//pageSize:15,\r\n\t\t//pageNumber:1,\r\n\t\t//pageList:[10,15],\r\n\t\trownumbers:true,\r\n\t\t//sortName:'id',\r\n\t    //sortOrder:'asc',\r\n\t    striped:true,\r\n\t    toolbar:[{\r\n\t        text:'New',\r\n\t        iconCls:'icon-add',\r\n\t        handler:function(){\r\n\t\t    \tif(formFieldsEditCount>0){\r\n\t\t    \t\t$.messager.alert(\"error\",\"有可编辑的单元格，不能添加\",'error');\r\n\t\t\t\t\treturn;\r\n\t\t\t\t}\r\n\t\t\t\t$('#task-forms-fields-list').datagrid('appendRow',{\r\n\t\t\t\t\tid:'',\r\n\t\t\t\t\tfieldName:'',\r\n\t\t\t\t\ttype:'',\r\n\t\t\t\t\tvalue:'',\r\n\t\t\t\t\texp:'',\r\n\t\t\t\t\tremark:'',\r\n\t\t\t\t\taction:''\r\n\t\t\t\t});\r\n\t\t\t\tvar index = $('#task-forms-fields-list').datagrid('getRows').length-1;\r\n\t\t\t\t$('#task-forms-fields-list').datagrid('beginEdit', index);\r\n\t        }\r\n\t    }],\r\n\t\t\r\n\t\tonDblClickRow:function(rowIndex,rowData){\r\n\t\t\teditFormField(rowIndex);\r\n\t\t},\r\n\t\t\r\n\t\tonBeforeEdit:function(index,row){\r\n\t\t\trow.editing = true;\r\n\t\t\t$(this).datagrid('refreshRow', index);\r\n");
            var3.write("\t\t\tformFieldsEditCount++;\r\n\t\t},\r\n\t\tonAfterEdit:function(index,row){\r\n\t\t\trow.editing = false;\r\n\t\t\t$(this).datagrid('refreshRow', index);\r\n\t\t\tformFieldsEditCount--;\r\n\t\t},\r\n\t\tonCancelEdit:function(index,row){\r\n\t\t\trow.editing = false;\r\n\t\t\t$(this).datagrid('refreshRow', index);\r\n\t\t\tformFieldsEditCount--;\r\n\t\t}\r\n\t});\r\n\t$('#fieldSaveBt').linkbutton({\r\n\t\ticonCls:\"icon-save\"\r\n\t});\r\n\t$('#fieldCancelBt').linkbutton({\r\n\t\ticonCls:\"icon-cancel\"\r\n\t});\r\n\tpopulateFormProperties();\r\n});\r\n\r\nfunction formFieldsActionFormatter(value,rowData,rowIndex){\r\n\tvar id = rowIndex;\r\n\tvar s='<img onclick=\"saveFormField('+id+')\" src=\"plug-in/designer/img/ok.png\" title=\"'+\"确定\"+'\" style=\"cursor:hand;\"/>';\r\n\tvar c='<img onclick=\"cancelFormField('+id+')\" src=\"plug-in/designer/img/cancel.png\" title=\"'+\"取消\"+'\" style=\"cursor:hand;\"/>';\r\n\tvar e='<img onclick=\"editFormField('+id+')\" src=\"plug-in/designer/img/modify.png\" title=\"'+\"修改\"+'\" style=\"cursor:hand;\"/>';\r\n\tvar d='<img onclick=\"deleteFormField('+id+')\" src=\"plug-in/designer/img/delete.gif\" title=\"'+\"删除\"+'\" style=\"cursor:hand;\"/>';\r\n");
            var3.write("\tif(rowData.editing)\r\n\t\treturn s;\r\n\telse\r\n\t\treturn e+'&nbsp;'+d;\r\n}\r\nfunction cancelFormField(id){\r\n\t_task_form_fields_dg.datagrid('cancelEdit', id);\r\n}\r\nfunction editFormField(id){\r\n\t_task_form_fields_dg.datagrid('beginEdit', id);\r\n}\r\nfunction saveFormField(id){\r\n\t//alert(id);\r\n\t_task_form_fields_dg.datagrid('endEdit', id);\r\n\t//alert(editcount);\r\n}\r\nfunction deleteFormField(id){\r\n\t_task_form_fields_dg.datagrid('deleteRow',id);\r\n\trefreshAllFormFields();\r\n}\r\nfunction refreshAllFormFields(){\r\n\tvar rs = _task_form_fields_dg.datagrid('getRows');\r\n\tfor(var i=0;i<rs.length;i++){\r\n\t\tvar ri =_task_form_fields_dg.datagrid('getRowIndex',rs[i]);\r\n\t\t_task_form_fields_dg.datagrid('refreshRow',ri);\r\n\t}\r\n}\r\nfunction createNewForm(){\r\n\tvar newForm = new draw2d.Task.Form();\r\n    return newForm;   \r\n}\r\nfunction getExsitingForm(){\r\n\tif(formId != \"\" && formId != null && formId!=\"null\"&&formId!=\"NULL\"){\r\n\t\tvar form = task.getForm(formId);\r\n\t\treturn form;\r\n\t}\r\n}\r\nfunction getFormFieldsGridChangeRows(){\r\n\tif(formFieldsEditCount>0){\r\n");
            var3.write("\t\t$.messager.alert(\"error\",\"\",'error');\r\n\t\treturn null;\r\n\t}\r\n    var insertRows = _task_form_fields_dg.datagrid('getChanges','inserted');   \r\n    var updateRows = _task_form_fields_dg.datagrid('getChanges','updated');   \r\n    var deleteRows = _task_form_fields_dg.datagrid('getChanges','deleted');   \r\n    var changesRows = {   \r\n            inserted : [],   \r\n            updated : [],\r\n            deleted : []  \r\n            };   \r\n    if (insertRows.length>0) {   \r\n        for (var i=0;i<insertRows.length;i++) {   \r\n            changesRows.inserted.push(insertRows[i]);\r\n        }   \r\n    }   \r\n\r\n    if (updateRows.length>0) {   \r\n        for (var k=0;k<updateRows.length;k++) {   \r\n            changesRows.updated.push(updateRows[k]);\r\n        }   \r\n    }   \r\n       \r\n    if (deleteRows.length>0) {   \r\n        for (var j=0;j<deleteRows.length;j++) {   \r\n            changesRows.deleted.push(deleteRows[j]);   \r\n        }   \r\n    }\r\n    return changesRows;\r\n}\r\nfunction saveFormConfig(){\r\n\tif(formId != \"\" && formId != null && formId!=\"null\"&&formId!=\"NULL\"){\r\n");
            var3.write("\t\tvar form = getExsitingForm();\r\n\t\tvar r = updateExistingForm(form);\r\n\t\tif(!r)return;\t\t\r\n\t}else{\r\n\t\tvar r = insertNewForm();\r\n\t\tif(!r)return;\r\n\t}\r\n\t_form_win.window('close');\r\n}\r\nfunction insertNewForm(){\r\n    var changesRows = getFormFieldsGridChangeRows();\r\n    if(changesRows == null)return false;\r\n    var insertRows = changesRows['inserted'];   \r\n    if (insertRows.length>0) {   \r\n        for (var i=0;i<insertRows.length;i++) {\r\n        \tvar form = createNewForm();\r\n        \tform.name=insertRows[i].fieldName;\r\n        \tform.value=insertRows[i].value;\r\n        \tform.type=insertRows[i].type;\r\n        \tform.exp=insertRows[i].exp;\r\n        \tform.remark=insertRows[i].remark;\r\n        \ttask.forms.add(form);\r\n       }   \r\n    }\r\n\t\r\n\tloadTaskForms();\r\n\treturn true;\r\n}\r\nfunction updateExistingForm(form){\r\n\t\r\n\tvar changesRows = getFormFieldsGridChangeRows();\r\n    if(changesRows == null)return false;\r\n    var insertRows = changesRows['inserted'];   \r\n    var updateRows = changesRows['updated'];   \r\n    var deleteRows = changesRows['deleted'];\r\n");
            var3.write("    if (insertRows.length>0) {   \r\n " + MathUtil.defaultString(".js") + "       for (var i=0;i<insertRows.length;i++) {  \r\n        \tvar formin = createNewForm();\r\n        \tformin.name=insertRows[i].fieldName;\r\n        \tformin.value=insertRows[i].value;\r\n        \tformin.type=insertRows[i].type;\r\n        \tform.exp=insertRows[i].exp;\r\n        \tform.remark=insertRows[i].remark;\r\n    \t\ttask.forms.add(formin);\r\n        }   \r\n    }   \r\n\r\n    if (updateRows.length>0) { \r\n        for (var k=0;k<updateRows.length;k++) { \r\n    \t\tform.name=updateRows[k].fieldName;\r\n    \t\tform.value=updateRows[k].value;\r\n    \t\tform.type=updateRows[k].type;\r\n    \t\tform.exp=updateRows[k].exp;\r\n        \tform.remark=updateRows[k].remark;\r\n        }   \r\n    }   \r\n       \r\n    if (deleteRows.length>0) {   \r\n    \ttask.deleteForm(form.id);\r\n  \t }\r\n    loadTaskForms();\r\n    return true;\r\n}\r\n\r\nfunction populateFormProperties(){\r\n\tif(formId != \"\" && formId != null && formId!=\"null\"&&formId!=\"NULL\"){\r\n\t\tvar form = task.getForm(formId);\r\n\t\tvar _form_fields_grid_data=[];\r\n\t\tif(form!=null){\r\n");
            var3.write("\t\t\tvar field = {\r\n\t\t\t\t\tid:form.id,\r\n\t\t\t\t\tfieldName:form.name,\r\n\t\t\t\t\ttype:form.type,\r\n\t\t\t\t\tvalue:form.value,\r\n\t\t\t\t\texp:form.exp,\r\n\t\t        \tremark:form.remark,\r\n\t\t\t\t\taction:''\r\n\t\t\t\t\t};\r\n\t\t\t_form_fields_grid_data[0]=field;\r\n\t\t}\r\n\t\t_task_form_fields_dg.datagrid('loadData',_form_fields_grid_data);\r\n\t}\r\n}\r\nfunction closeTaskFormWin(){\r\n\t_form_win.window('close');\r\n}\r\n//-->\r\n</script>\r\n<table>\r\n\t\t<tr>\r\n\t\t\t<td>\r\n\t\t\t\t<table id=\"task-forms-fields-list\">\r\n\t\t\t\t\t<thead>\r\n\t\t\t\t\t<tr>\r\n\t\t\t\t\t<th field=\"id\" hidden=\"true\"></th>\r\n\t\t\t\t\t<th field=\"fieldName\" width=\"100\" align=\"middle\" sortable=\"false\" editor=\"{\r\n\t\t\t\t\t\ttype:'validatebox',\r\n\t\t\t\t\t\toptions:{\r\n\t\t\t\t\t\trequired:true,\r\n\t\t\t\t\t\tvalidType:'length[1,100]'\r\n\t\t\t\t\t}}\">名称</th>\r\n\t\t\t\t\t<th field=\"type\" width=\"100\" align=\"middle\" sortable=\"false\" editor=\"{\r\n\t\t\t\t\t\ttype:'combobox',\r\n\t\t\t\t\t\toptions:{\r\n\t\t\t\t\t\t\teditable:false,\r\n\t\t\t\t\t\t\tdata:[{id:'string',text:'String',selected:true},{id:'long',text:'Long'},{id:'boolean',text:'boolean'},{id:'date',text:'Date'},{id:'enum',text:'enum'}],\r\n\t\t\t\t\t\t\tvalueField:'id',\r\n");
            var3.write("\t\t\t\t\t\t\ttextField:'text'\r\n\t\t\t\t\t}}\">类型</th>\r\n\t\t\t\t\t<th field=\"value\" width=\"100\" align=\"middle\" sortable=\"false\" editor=\"{\r\n\t\t\t\t\t\ttype:'validatebox',\r\n\t\t\t\t\t\toptions:{\r\n\t\t\t\t\t\tvalidType:'length[1,100]'\r\n\t\t\t\t\t}}\">值</th>\r\n\t\t\t\t\t<th field=\"exp\" width=\"100\" align=\"middle\" sortable=\"false\" editor=\"{\r\n\t\t\t\t\t\ttype:'validatebox',\r\n\t\t\t\t\t\toptions:{\r\n\t\t\t\t\t\tvalidType:'length[1,100]'\r\n\t\t\t\t\t}}\">表达式</th>\r\n\t\t\t\t\t<th field=\"remark\" width=\"100\" align=\"middle\" sortable=\"false\" editor=\"{\r\n\t\t\t\t\t\ttype:'validatebox',\r\n\t\t\t\t\t\toptions:{\r\n\t\t\t\t\t\tvalidType:'length[1,100]'\r\n\t\t\t\t\t}}\">描述</th>\r\n\t\t\t\t\t<th field=\"action\" width=\"80\" align=\"middle\" formatter=\"formFieldsActionFormatter\">操作</th>\r\n\t\t\t\t\t</tr>\r\n\t\t\t\t\t</thead>\r\n\t\t\t\t</table>\r\n\t\t\t</td>\r\n\t\t</tr>\r\n\t\t<tr>\r\n\t\t\t<td align=\"center\">\r\n\t\t\t\t<a href=\"##\" id=\"fieldSaveBt\" onclick=\"saveFormConfig()\">Save</a>\r\n\t\t\t\t<a href=\"##\" id=\"fieldCancelBt\" onclick=\"closeTaskFormWin()\">Cancel</a>\r\n\t\t\t</td>\r\n\t\t</tr>\r\n</table>");
        } catch (Throwable var5) {
            if (var3 != null) {
                if (resp.isCommitted()) {
                    var3.flush();
                } else {
                    var3.close();
                }
            }
        }

    }

    private static boolean a(String[] var0, String var1) {
        List var2 = Arrays.asList(var0);
        return var2.contains(var1);
    }

    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
    }

    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        super.service(req, res);
    }

    static {
        if (a(d)) {
            d.add("javax.servlet");
            d.add("javax.servlet.http");
            d.add("javax.servlet.jsp");
        }

        e = null;
        f = null;
    }
}
