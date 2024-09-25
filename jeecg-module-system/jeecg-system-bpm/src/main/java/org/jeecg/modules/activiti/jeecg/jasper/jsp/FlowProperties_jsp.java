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

public final class FlowProperties_jsp extends HttpJspBase implements JspSourceDependent, JspSourceImports {
    private static final JspFactory a = JspFactory.getDefaultFactory();
    private static Map<String, Long> b = new HashMap(5);
    private static final Set<String> c;
    private static final Set<String> d;
    private TagHandlerPool e;
    private volatile ExpressionFactory f;
    private volatile InstanceManager g;

    public FlowProperties_jsp() {
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
                    String contextPath = var12;
                    String var14 = JMathUtil.defaultString(".jsp");
                    String var15 = request.getHeader("X_GATEWAY_BASE_PATH");
                    if (var15 != null && !var15.equals("")) {
                        contextPath = var15;
                    }

                    var8.write(13);
                    var8.write(10);
                    SetTag var16 = (SetTag)this.e.get(SetTag.class);
                    boolean var17 = false;

                    try {
                        var16.setPageContext(var11);
                        var16.setParent((Tag)null);
                        var16.setVar("webRoot");
                        var16.setValue(contextPath);
                        int var18 = var16.doStartTag();
                        if (var16.doEndTag() == 5) {
                            return;
                        }

                        this.e.reuse(var16);
                        var17 = true;
                    } finally {
                        JspRuntimeLibrary.releaseTag(var16, this.b(), var17);
                    }

                    String contextPathPre = "'" + contextPath + "' + ";

                    var8.write("\r\n<script type=\"text/javascript\">\r\n//流程对象\r\nvar line = workflow.getLine(nodeid);\r\nif(line.condition!=null&&line.condition!=\"\"){\r\n\tline.condition = line.condition.replace(/(^\\s*)|(\\s*$)/g,\"\");\r\n}\r\n\r\n//属性表格定义\r\nrows = [\r\n         { \"name\": \"ID\", \"group\": \"节点属性\", \"value\": line.lineId,\"field\": \"id\", \"editor\": \"text\" },\r\n         { \"name\": \"Lable\", \"group\": \"节点属性\", \"value\": line.lineName, \"field\": \"name\", \"editor\": \"text\" },\r\n         { \"name\": \"表达式\", \"group\": \"分支条件\", \"value\": line.condition, \"field\": \"condition\", \"editor\": \"text\" }\r\n         \r\n       ];\r\n //保存属性\r\nfunction saveFlowProperties(){\r\n\tline.lineId=rows[0].value;\r\n\tline.lineName=rows[1].value;\r\n\tline.condition=rows[2].value;\r\n\tline.setLabel(rows[1].value);\r\n}\r\n //构建属性表格数据\r\nfunction populateFlowProperites(){\r\n\trows[0].value=line.lineId;\r\n\trows[1].value=line.lineName;\r\n\trows[2].value=line.condition;\r\n\tflowpropertygrid();\r\n} \r\n //加载属性表格数据\r\nfunction flowpropertygrid(){\r\n\t$('#flow-properties').propertygrid('loadData', rows);\r\n\t}\r\n$(function(){\r\n//创建属性表格\r\n$('#flow-properties').propertygrid({\r\n");
                    var8.write("  width: 'auto',\r\n  height: 'auto',\r\n  showGroup: true,\r\n  scrollbarSize: 0,\r\n  border:0,\r\n  columns: [[\r\n          { field: 'name', title: '属性名', width: 30, resizable: false },\r\n          { field: 'value', title: '属性值', width: 100, resizable: false }\r\n  ]],\r\n  onAfterEdit:function(){  \r\n  \tsaveFlowProperties();//自动保存\r\n   }\r\n});\r\nflowpropertygrid();\r\n});\r\n</script>\r\n<script type=\"text/javascript\">\r\n<!--\r\n\t//获取执行监听器id\r\n\tfunction getOldListenerIds(){\r\n\t\tvar listeners=line.listeners;\r\n\t\t  var listenersIds=new Array();\r\n\t\t  for(var i=0;i<listeners.getSize();i++){\r\n\t\t\tvar listener = listeners.get(i);\r\n\t\t\tlistenersIds.push(listener.getId());\r\n\t\t  }\r\n\t\treturn listenersIds.join(\",\");\r\n\t}\r\n\t//添加执行监听器\r\n\tfunction addListener(id,event,serviceType,value){\r\n\t\tvar ls=line.getListener(id);\r\n\t\tif(!ls){\r\n\t\t\tvar listener = new draw2d.DecoratedConnection.Listener();\r\n\t\t\tlistener.id = id;\r\n\t\t\tlistener.event = event;\r\n\t\t\tlistener.serviceType = serviceType;\r\n\t\t\t if(serviceType==\"javaClass\"){\r\n\t\t\t\t listener.serviceClass = value;\r\n\t\t     }\r\n");
                    var8.write("\t\t     else{\r\n\t\t    \t listener.serviceExpression = value;\r\n\t\t     }\r\n\t\t\t line.listeners.add(listener);\r\n\t\t}\r\n\t}\r\n\t//删除执行监听器\r\n\tfunction removeListener(id){\r\n\t\tline.deleteListener(id);\r\n\t}\t\r\n\r\n//-->\r\n</script>\r\n<div id=\"flow-properties-layout\" class=\"easyui-layout\" fit=\"true\">\r\n <div id=\"flow-properties-panel\" region=\"center\" border=\"true\">\r\n  <div id=\"flow-properties-accordion\" class=\"easyui-accordion\" fit=\"true\" border=\"false\">\r\n   <div id=\"flow\" style=\"padding:0px;border:0px\" title=\"流程属性面板\" class=\"properties-menu\">\r\n    <table id=\"flow-properties\">\r\n    </table>\r\n   </div>\r\n   <div id=\"flowlisteners\" title=\"执行监听器\" style=\"overflow: hidden; padding: 1px;\">\r\n   ");
                    var8.write("\r\n     <div id=\"tb\">\r\n\t\t<a href=\"#\" class=\"easyui-linkbutton\" data-options=\"iconCls:'icon-add',plain:true\" onclick=\"selectProcessListener();\">添加</a>\r\n\t</div>\r\n\t<table id=\"flowlistenerList\"></table>\r\n\t");
                    var8.write("\r\n   </div>\r\n  </div>\r\n </div>\r\n</div>\r\n<script type=\"text/javascript\">\r\n//获取流程图中的监听IDS\r\nvar ids = getOldListenerIds();\r\nconsole.log('ids: '+ids);\r\n$('#flowlistenerList').datagrid({\r\n    url:'designer/api/getListenersByIds?token='+workflow.process.token,\r\n    fit: true,\r\n    fitColumns:true,\r\n    queryParams: {'ids': ids},\r\n    toolbar: '#tb',\r\n    columns:[[\r\n    \t{field:'id',title:'ID',hidden:true},\r\n\t\t{field:'listenername',title:'名字',width:30},\r\n\t\t{field:'listenereven',title:'事件',width:30},\r\n\t\t{field:'listenertype',title:'类型',width:30},\r\n\t\t{field:'listenervalue',title:'执行内容',width:70},\r\n\t\t{field:'opt',title:'操作',width:30,align:'right',\r\n\t\t\t formatter:function(value,row,index){\r\n\t\t\t\t var d = '<a href=\"#\" onclick=\"delRow('+index+')\">删除</a>';\r\n\t\t         return d;\r\n\t         }\r\n\t\t}\r\n    ]]\r\n});\r\n\r\n\r\n\t//选择流程监听\r\n\tfunction selectProcessListener(){\r\n\t   var url = " + contextPathPre + " '/act/designer/goListeners?typeid=1&token='+workflow.process.token;\r\n\t   $.dialog({content: 'url:'+url,\r\n\t\t   title: '执行监听列表',\r\n\t\t   lock : true,\r\n\t\t   width :'500px',\r\n");
                    var8.write("\t\t   height :'450px',\r\n\t\t   left :'85%',\r\n\t\t   top :'65%',\r\n\t\t   opacity : 0.4,\r\n\t\t   button : [ {name : '确定',\r\n\t\t\t   callback : saveFlowListener,focus : true}, \r\n\t\t\t   {name : '取消',callback : function() {}} ]});\r\n\t   }\r\n\t   \r\n\r\n/*  //保存监听\r\n function saveFlowListener() {\r\n  var listenerid = $('#listenerid').val();\r\n  $.ajax({\r\n   url : \"designer/saveFlowListener\",\r\n   type : 'POST',\r\n   data : {\r\n  \ttype:1,\r\n    processNode : '");
                    var8.write((String)PageContextImpl.proprietaryEvaluate("${id}", String.class, var4, (ProtectedFunctionMapper)null));
                    var8.write("',\r\n    processkey : '");
                    var8.write((String)PageContextImpl.proprietaryEvaluate("${processId}", String.class, var4, (ProtectedFunctionMapper)null));
                    var8.write("',\r\n    listenerid : listenerid\r\n   },\r\n   dataType : 'json',\r\n   success : function(data) {\r\n    if (data.success) {\r\n     $('#flowlistenerList').datagrid('reload');\r\n    }\r\n   }\r\n  });\r\n } */\r\n \r\n//保存监听(回调)\r\n function saveFlowListener() {\r\n \tvar iframe = this.iframe.contentWindow;\r\n \tvar rows = iframe.getSelectProcessListenerList();\t\r\n \tfor(var i=0;i<rows.length;i++){ \r\n \t\tvar v_id = rows[i]['id'];\r\n \t\tvar v_listenername = rows[i]['listenername'];\r\n \t\tvar v_listenereven = rows[i]['listenereven'];\r\n \t\tvar v_listenertype = rows[i]['listenertype'];\r\n \t\tvar v_listenervalue = rows[i]['listenervalue'];\r\n \t\tvar v_status = rows[i]['status'];\r\n \t\t\r\n \t\tvar ls=line.getListener(v_id);\r\n \t\taddListener(v_id,v_listenereven,v_listenertype,v_listenervalue);\r\n \t\t//addListener(listenerids[i],listenerevens[i],listenertypes[i],listenervalues[i]);\r\n \t\tif(!ls){\r\n \t\t\t$('#flowlistenerList').datagrid('appendRow',{\r\n \t\t\t\tid:v_id,\r\n \t\t\t\tlistenername:v_listenername,\r\n \t\t\t\tlistenereven:v_listenereven,\r\n \t\t\t\tlistenertype:v_listenertype,\r\n");
                    var8.write(" \t\t\t\tlistenervalue:v_listenervalue,\r\n \t\t\t\tstatus:v_status\r\n \t\t\t});\r\n \t\t}\r\n \t}\r\n }\r\n \r\n\t//删除流程监听\r\n\tfunction delRow(id){\r\n\t  var rows=$('#flowlistenerList').datagrid('getSelections');\r\n\t  for(var i=0;i<rows.length;i++){\r\n\t\t  var row=rows[i];\r\n\t\t  var index=$('#flowlistenerList').datagrid('getRowIndex',row);\r\n   \t  \t  $('#flowlistenerList').datagrid('deleteRow',index);\r\n   \t  \t  removeListener(row.id);\r\n\t  }\r\n \t}\r\n \r\n\r\n /* 设置流程监听状态（禁用/启用）\r\n function setFlowListener(index)\r\n {\r\n  var row = $('#flowlistenerList').datagrid('getRows')[index];\r\n  $.ajax({\r\n   url : \"designer/setFlowListener\",\r\n   type : 'POST',\r\n   data : {\r\n    id :row.id\r\n   },\r\n   dataType : 'json',\r\n   success : function(data) {\r\n    if (data.success) {\r\n     var listener =  new draw2d.DecoratedConnection.Listener();\r\n     listener.id=row.id;\r\n     listener.serviceType = row.TPListerer_listenertype;\r\n     if(row.TPListerer_listenertype==\"javaClass\")\r\n     {\r\n      listener.serviceClass= row.TPListerer_listenervalue;\r\n     }\r\n     else\r\n     {\r\n       listener.serviceExpression=row.TPListerer_listenervalue;\r\n");
                    var8.write("     }\r\n     \r\n     line.listeners.add(listener);\r\n    }\r\n    else\r\n    {\r\n    \tline.deleteListener(row.id);\r\n    }\r\n    reloadflowlistenerList();\r\n   }\r\n  });\r\n } */\r\n \r\n</script>\r\n");
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
