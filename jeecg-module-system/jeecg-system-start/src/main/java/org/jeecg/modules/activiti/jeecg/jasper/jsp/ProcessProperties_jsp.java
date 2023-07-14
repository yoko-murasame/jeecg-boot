package org.jeecg.modules.activiti.jeecg.jasper.jsp;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import org.apache.jasper.el.JspValueExpression;
import org.apache.jasper.runtime.*;
import org.apache.taglibs.standard.tag.rt.core.ForEachTag;
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
import javax.servlet.jsp.tagext.JspTag;
import javax.servlet.jsp.tagext.Tag;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class ProcessProperties_jsp extends HttpJspBase implements JspSourceDependent, JspSourceImports {
    private static final JspFactory a = JspFactory.getDefaultFactory();
    private static Map<String, Long> b = new HashMap(1);
    private static final Set<String> c;
    private static final Set<String> d;
    private TagHandlerPool e;
    private TagHandlerPool f;
    private TagHandlerPool g;
    private volatile ExpressionFactory h;
    private volatile InstanceManager i;

    public ProcessProperties_jsp() {
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
        if (this.h == null) {
            synchronized(this) {
                if (this.h == null) {
                    this.h = a.getJspApplicationContext(this.getServletConfig().getServletContext()).getExpressionFactory();
                }
            }
        }

        return this.h;
    }

    public InstanceManager b() {
        if (this.i == null) {
            synchronized(this) {
                if (this.i == null) {
                    this.i = InstanceManagerFactory.getInstanceManager(this.getServletConfig());
                }
            }
        }

        return this.i;
    }

    public void _jspInit() {
        this.e = TagHandlerPool.getTagHandlerPool(this.getServletConfig());
        this.f = TagHandlerPool.getTagHandlerPool(this.getServletConfig());
        this.g = TagHandlerPool.getTagHandlerPool(this.getServletConfig());
    }

    public void _jspDestroy() {
        this.e.release();
        this.f.release();
        this.g.release();
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

                var8.write("\r\n\r\n<script type=\"text/javascript\">\r\n//流程对象\r\nvar process = workflow.process;\r\n//属性表格定义\r\nrows = [\r\n         { \"name\": \"ID\", \"group\": \"流程\", \"value\": process.id,\"field\": \"id\", \"editor\": \"text\" },\r\n         { \"name\": \"名称\", \"group\": \"流程\", \"value\": process.name, \"field\": \"name\", \"editor\": \"text\" },\r\n         { \"name\": \"命名空间\", \"group\": \"流程\", \"value\": process.category, \"field\": \"category\", \"editor\": \"text\" },\r\n         { \"name\": \"描述\", \"group\": \"流程\", \"value\": process.documentation, \"field\": \"documentation\", \"editor\": \"text\" }       \r\n       ];\r\n //保存属性\r\nfunction saveProcessProperties(){\r\n\tprocess.id=rows[0].value;\r\n\tprocess.name=rows[1].value;\r\n\tprocess.category=rows[2].value;\r\n\tprocess.documentation=rows[3].value;\r\n}\r\n //构建属性表格数据\r\nfunction populateProcessProperites(){\r\n\trows[0].value=process.id;\r\n\trows[1].value=process.name;\r\n\trows[2].value=process.category;\r\n\trows[3].value=process.documentation;\r\n\tpropertygrid();\r\n} \r\n //加载属性表格数据\r\nfunction propertygrid(){\r\n\t$('#general-properties').propertygrid('loadData', rows);\r\n\t}\r\n");
                var8.write("$(function(){\r\n//创建属性表格\r\n$('#general-properties').propertygrid({\r\n  width: 'auto',\r\n  height: 'auto',\r\n  showGroup: false,\r\n  scrollbarSize: 0,\r\n  border:0,\r\n  columns: [[\r\n          { field: 'name', title: '属性名', width: 30, resizable: false },\r\n          { field: 'value', title: '属性值', width: 100, resizable: false }\r\n  ]],\r\n  onAfterEdit:function(){  \r\n  \tsaveProcessProperties();//自动保存\r\n   }\r\n});\r\npropertygrid();\r\n});\r\n</script>\r\n\r\n");
                var8.write("\r\n<script type=\"text/javascript\">\r\n<!--\r\n\t//获取监听器id\r\n\tfunction getOldListenerIds(){\r\n\t\tvar listeners=process.listeners;\r\n\t\t  var listenersIds=new Array();\r\n\t\t  for(var i=0;i<listeners.getSize();i++){\r\n\t\t\tvar listener = listeners.get(i);\r\n\t\t\tlistenersIds.push(listener.getId());\r\n\t\t  }\r\n\t\treturn listenersIds.join(\",\");\r\n\t}\r\n\t//添加监听器\r\n\tfunction addListener(id,event,serviceType,value){\r\n\t\tvar ls=process.getListener(id);\r\n\t\tif(!ls){\r\n\t\t\tvar listener = new draw2d.Process.Listener();\r\n\t\t\tlistener.id=id;\r\n\t\t\tlistener.event = event;\r\n\t\t\tlistener.serviceType=serviceType;\r\n\t\t\tlistener.serviceClass = value;\r\n\t\t\tlistener.serviceExpression = value;\r\n\t\t\tprocess.addListener(listener);\r\n\t\t}\r\n\t}\r\n\t//删除监听器\r\n\tfunction removeListener(id){\r\n\t\tprocess.deleteListener(id);\r\n\t}\r\n\r\n//-->\r\n</script>\r\n<div id=\"process-properties-layout\" class=\"easyui-layout\" fit=\"true\">\r\n <div id=\"process-properties-panel\" region=\"center\" border=\"true\">\r\n  <div id=\"task-properties-accordion\" class=\"easyui-accordion\" fit=\"true\" border=\"false\">\r\n   <div id=\"general\" style=\"padding:0px;border:0px\" title=\"流程属性面板\" class=\"properties-menu\">\r\n");
                var8.write("    <div id=\"task-properties-toolbar-panel\" region=\"north\" border=\"false\" style=\"padding: 3px; height: 25px; background: #E1F0F2;\">\r\n    \t流程类型:\r\n     <select id=\"typeid\" style=\"width: 150px; padding: 1px\">\r\n       <option value=\"\"></option>\r\n       ");
                if (this.a(var4)) {
                    return;
                }

                var8.write("\r\n     </select>\r\n    </div>\r\n    <table id=\"general-properties\">\r\n    </table>\r\n   </div>\r\n  <div id=\"eventlisteners\" title=\"执行监听\" style=\"overflow: hidden; padding: 1px;\">\r\n    ");
                var8.write("\r\n\t<div id=\"tb\">\r\n  \t\t");
                var8.write("\r\n\t\t<a href=\"#\" class=\"easyui-linkbutton\" data-options=\"iconCls:'icon-add',plain:true\" onclick=\"selectProcessListener();\">添加</a>\r\n\t</div>\r\n\t<table id=\"listenerList\"></table>\r\n\t");
                var8.write("\r\n   </div>\r\n  </div>\r\n </div>\r\n</div>\r\n<script type=\"text/javascript\">\r\n//初始化流程类型（存H5缓存中，防止丢失）\r\nvar typeVal = $(\"#typeid\").val();\r\nsessionStorage.setItem('processType', typeVal);\r\n\r\n//获取流程图中的监听IDS\r\nvar ids = getOldListenerIds();\r\n$('#listenerList').datagrid({\r\n    url:'designer/api/getListenersByIds',\r\n    fit: true,\r\n    queryParams: {'ids': ids,'token':workflow.process.token},\r\n    fitColumns:true,\r\n    toolbar: '#tb',\r\n    columns:[[\r\n    \t{field:'id',title:'名字',hidden:true},\r\n\t\t{field:'listenername',title:'名字',width:30},\r\n\t\t{field:'listenereven',title:'事件',width:30},\r\n\t\t{field:'listenertype',title:'类型',width:30},\r\n\t\t{field:'listenervalue',title:'执行内容',width:70},\r\n\t\t{field:'opt',title:'操作',width:30,align:'right',\r\n\t\t\t formatter:function(value,row,index){\r\n\t            var d = '<a href=\"#\" onclick=\"delRow('+index+')\">删除</a>';\r\n\t            return d;\r\n\t         }\r\n\t\t}\r\n    ]]\r\n});\r\n\r\n//选择流程监听\r\nfunction selectProcessListener(){\r\n\t   var url = '/main/act/designer/goListeners?typeid=1&token='+workflow.process.token;\r\n\t   $.dialog({content: 'url:'+url,\r\n");
                var8.write("\t\t   title: '执行监听列表',\r\n\t\t   lock : true,\r\n\t\t   width :'500px',\r\n\t\t   height :'450px',\r\n\t\t   left :'85%',\r\n\t\t   top :'65%',\r\n\t\t   opacity : 0.4,\r\n\t\t   button : [ {name : '确定',\r\n\t\t\t   callback : saveProcessListener,focus : true}, \r\n\t\t\t   {name : '取消',callback : function() {}} ]});\r\n}\r\n\t   \r\n//保存监听\r\nfunction saveProcessListener() {\r\n\tvar iframe = this.iframe.contentWindow;\r\n\tvar rows = iframe.getSelectProcessListenerList();\t\r\n\tfor(var i=0;i<rows.length;i++){ \r\n\t\tvar v_id = rows[i]['id'];\r\n\t\tvar v_listenername = rows[i]['listenername'];\r\n\t\tvar v_listenereven = rows[i]['listenereven'];\r\n\t\tvar v_listenertype = rows[i]['listenertype'];\r\n\t\tvar v_listenervalue = rows[i]['listenervalue'];\r\n\t\t\r\n\t\tvar ls = process.getListener(v_id);\r\n\t\taddListener(v_id,v_listenereven,v_listenertype,v_listenervalue);\r\n\t\tif(!ls){\r\n\t\t\t$('#listenerList').datagrid('appendRow',{\r\n\t\t\t\tid:v_id,\r\n\t\t\t\tlistenername:v_listenername,\r\n\t\t\t\tlistenereven:v_listenereven,\r\n\t\t\t\tlistenertype:v_listenertype,\r\n\t\t\t\tlistenervalue:v_listenervalue\r\n\t\t\t});\r\n\t\t}\r\n\t}\r\n");
                var8.write("}\r\n\r\n /* function setProcessListener(index){\r\n\t  var row = $('#eventlistenerList').datagrid('getRows')[index];\r\n\t  $.ajax({\r\n\t   url : \"processController.do?setProcessListener\",\r\n\t   type : 'POST',\r\n\t   data : {\r\n\t    id :row.id\r\n\t   },\r\n\t   dataType : 'json',\r\n\t   success : function(data) {\r\n\t    if (data.success) {\r\n\t     var listener = new draw2d.Process.Listener();\r\n\t     listener.event=row.TPListerer_listenereven;\r\n\t     listener.id=row.id;\r\n\t     listener.serviceType = row.TPListerer_listenertype;\r\n\t     if(row.TPListerer_listenertype==\"javaClass\")\r\n\t     {\r\n\t      listener.serviceClass= row.TPListerer_listenervalue;\r\n\t     }\r\n\t     else\r\n\t     {\r\n\t       listener.serviceExpression=row.TPListerer_listenervalue;\r\n\t     }\r\n\t     \r\n\t      process.listeners.add(listener);\r\n\t    }\r\n\t    else\r\n\t    {\r\n\t      process.deleteListener(row.id);\r\n\t    }\r\n\t    reloadeventlistenerList();\r\n\t   }\r\n\t  });\r\n } */\r\n \r\n\t//删除流程监听\r\n\tfunction delRow(id){\r\n\t  var rows=$('#listenerList').datagrid('getSelections');\r\n\t  for(var i=0;i<rows.length;i++){\r\n");
                var8.write("\t\t  var row=rows[i];\r\n\t\t  var index=$('#listenerList').datagrid('getRowIndex',row);\r\n   \t\t  $('#listenerList').datagrid('deleteRow',index);\r\n   \t  \t  removeListener(row.id);\r\n\t  }\r\n \t}\r\n \r\n\t//切换流程类型，将流程类型保存到H5缓存中（防止丢失）\r\n\t$(\"#typeid\").change(function() {\r\n\t\tvar typeVal = $(\"#typeid\").val();\r\n\t\tconsole.log('typeVal: ' + typeVal);\r\n\t\tsessionStorage.setItem('processType', typeVal);\r\n\t});\r\n</script>\r\n");
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
        ForEachTag var4 = (ForEachTag)this.f.get(ForEachTag.class);
        boolean var5 = false;

        try {
            var4.setPageContext(var1);
            var4.setParent((Tag)null);
            var4.setItems((new JspValueExpression("/designer/processProperties.jsp(94,7) '${proTypeList}'", this.a().createValueExpression(var1.getELContext(), "${proTypeList}", Object.class))).getValue(var1.getELContext()));
            var4.setVar("type");
            int[] var6 = new int[]{0};

            try {
                int var7 = var4.doStartTag();
                int var8;
                if (var7 != 0) {
                    do {
                        var3.write("\r\n       <option value=\"");
                        var3.write((String)PageContextImpl.proprietaryEvaluate("${type.id}", String.class, var1, (ProtectedFunctionMapper)null));
                        var3.write(34);
                        var3.write(32);
                        if (this.a(var4, var1, var6)) {
                            var8 = 1;
                            return true;
                        }

                        var3.write(">\r\n        ");
                        var3.write((String)PageContextImpl.proprietaryEvaluate("${type.typename}", String.class, var1, (ProtectedFunctionMapper)null));
                        var3.write("\r\n       </option>\r\n      ");
                        var8 = var4.doAfterBody();
                    } while(var8 == 2);
                }

                if (var4.doEndTag() == 5) {
                    var8 = 1;
                    return true;
                }
            } catch (Throwable var17) {
                while(true) {
                    int var10003 = var6[0];
                    int var10000 = var6[0];
                    var6[0] = var10003 - 1;
                    if (var10000 <= 0) {
                        var4.doCatch(var17);
                        break;
                    }

                    var3 = var1.popBody();
                }
            } finally {
                var4.doFinally();
            }

            this.f.reuse(var4);
            var5 = true;
            return false;
        } finally {
            JspRuntimeLibrary.releaseTag(var4, this.b(), var5);
        }
    }

    private boolean a(JspTag var1, PageContext var2, int[] var3) throws Throwable {
        JspWriter var5 = var2.getOut();
        IfTag var6 = (IfTag)this.g.get(IfTag.class);
        boolean var7 = false;

        try {
            var6.setPageContext(var2);
            var6.setParent((Tag)var1);
            var6.setTest((Boolean)PageContextImpl.proprietaryEvaluate("${type.id==typeId}", Boolean.TYPE, var2, (ProtectedFunctionMapper)null));
            int var8 = var6.doStartTag();
            int var9;
            if (var8 != 0) {
                do {
                    var5.write("selected=\"selected\"");
                    var9 = var6.doAfterBody();
                } while(var9 == 2);
            }

            if (var6.doEndTag() == 5) {
                boolean var13 = true;
                return var13;
            }

            this.g.reuse(var6);
            var7 = true;
        } finally {
            JspRuntimeLibrary.releaseTag(var6, this.b(), var7);
        }

        return false;
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
