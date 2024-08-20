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

public final class TaskProperties_jsp extends HttpJspBase implements JspSourceDependent, JspSourceImports {
    private static final JspFactory a = JspFactory.getDefaultFactory();
    private static Map<String, Long> b = new HashMap(1);
    private static final Set<String> c;
    private static final Set<String> d;
    private TagHandlerPool e;
    private volatile ExpressionFactory f;
    private volatile InstanceManager g;

    public TaskProperties_jsp() {
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
            synchronized (this) {
                if (this.f == null) {
                    this.f = a.getJspApplicationContext(this.getServletConfig().getServletContext()).getExpressionFactory();
                }
            }
        }

        return this.f;
    }

    public InstanceManager b() {
        if (this.g == null) {
            synchronized (this) {
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
                    PageContext var4 = a.getPageContext(this, request, response, (String) null, true, 8192, true);
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
                    //                    String var13 = request.getScheme() + "://" + request.getServerName() + ":8989"  + var12;
                    String var14 = JMathUtil.defaultString(".jsp");
                    String var15 = request.getHeader("X_GATEWAY_BASE_PATH");
                    if (var15 != null && !var15.equals("")) {
                        var13 = var15;
                    }

                    var8.write(13);
                    var8.write(10);
                    SetTag var16 = (SetTag) this.e.get(SetTag.class);
                    boolean var17 = false;

                    try {
                        var16.setPageContext(var11);
                        var16.setParent((Tag) null);
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

                    var8.write("\r\n<script src=\"");
                    var8.print(var13);
                    var8.write("/plug-in/js/jquery.div.resize.js\"></script>\r\n\r\n<script type=\"text/javascript\">\r\n<!--\r\nvar task = workflow" +
                            ".getFigure(nodeid);//当前节点对象\r\n//属性表格定义\r\nrows = [\r\n    \r\n            { \"name\": \"ID\", \"group\": \"名称\", \"value\": " +
                            "task.taskId,\"field\": \"taskId\", \"editor\": \"text\" },\r\n            { \"name\": \"描述\", \"group\": \"任务属性\", \"value\": " +
                            "task.taskName, \"field\": \"taskName\", \"editor\": \"text\" },\r\n            { \"name\": \"任务属性\", \"group\": \"任务属性\", " +
                            "\"value\": task.documentation, \"field\": \"documentation\", \"editor\": \"text\" },\r\n            { \"name\": \"表单key\", " +
                            "\"group\": \"表单属性\", \"value\": task.formKey, \"field\": \"formKey\", \"editor\": \"text\" }\r\n           \r\n          \r\n   " +
                            "     ];\r\n$(function(){\r\n\t$('#task_extend').val(task.task_extend);\r\n\t$('#isSequential').val(task.isSequential);\r\n\t$" +
                            "('#loopCardinality').val(task.loopCardinality);\r\n\t$('#collection').val(task.collection);\r\n\t$('#elementVariable').val(task" +
                            ".elementVariable);\r\n\t$('#completionCondition').val(task.completionCondition);\r\n\t$('#performerType').combobox" +
                            "({\r\n\t\t\teditable:false,\r\n\t\t\tonChange:function(newValue, oldValue){\r\n");
                    var8.write("\t\t\t\t$('#expression').val('');\r\n\t\t\t\tswitchTaskCandidatesList(newValue);\r\n\t\t\t}\r\n\t\t});" +
                            "\r\n\r\n\t\r\n\ttask_candidate_panel=$('#task-candidate-panel').panel({\r\n\t\tborder:false\r\n\t\t//minimized:true,\r\n\t});" +
                            "\r\n\t\r\n\tvar ptypeOld=$('#performerType').combobox('getValue')\r\n\tvar ptype='';\r\n\tif($('#performerType').combobox" +
                            "('getValue')!=''){\r\n\t\tptype= task.performerType || $('#performerType').combobox('getValue');\r\n\t}\r\n\t\r\n\t//console" +
                            ".info('ptype: ',ptype)\r\n\t//console.info('ptypeOld: ',ptypeOld)\r\n\t\r\n\t$('#performerType').combobox('setValue',ptype);" +
                            "\r\n\t\r\n\tif(ptype == ptypeOld){\r\n\t\t//console.info('performerType: ',ptypeOld)\r\n\t\tswitchTaskCandidatesList(ptype);" +
                            "\r\n\t}\r\n});\r\nfunction switchTaskCandidatesList(performerType){\r\n\t//var panelWidth = $('#main-config').width() - 20;" +
                            "\r\n\t//task_candidate_panel.panel('resize', {width: panelWidth});\r\n\tif(performerType == 'candidateUsers')" +
                            "{\r\n\t\ttask_candidate_panel.panel(\"refresh\",\"designer/processProperties?turn=candidateUsersConfig&checkbox=true\");" +
                            "\r\n\t}else if(performerType == 'candidateGroups'){\r\n\t\ttask_candidate_panel.panel(\"refresh\"," +
                            "\"designer/processProperties?turn=candidateGroupsConfig&checkbox=true\");\r\n");
                    var8.write("\t}else if(performerType == 'assignee'){\r\n\t\ttask_candidate_panel.panel(\"refresh\"," +
                            "\"designer/processProperties?turn=candidateUsersConfig&checkbox=false\");\r\n\t}\r\n}\r\n\r\n//保存\r\nfunction saveTaskProperties" +
                            "(){\r\n\ttask.taskId=$.trim(rows[0].value);\r\n\ttask.taskName=rows[1].value;\r\n\ttask.formKey=rows[3].value;\r\n\ttask" +
                            ".documentation=rows[2].value;\r\n\ttask.setId($.trim(rows[0].value));\r\n\ttask.setContent($.trim(rows[1].value));\r\n\ttask" +
                            ".performerType=$('#performerType').combobox('getValue');\r\n\ttask.expression=$.trim($('#expression').val());\r\n\ttask" +
                            ".isUseExpression=true;\r\n\ttask.task_extend=$.trim($('#task_extend').val());\r\n\ttask.loopCardinality=$.trim($" +
                            "('#loopCardinality').val());\r\n\ttask.isSequential=$.trim($('#isSequential').val());\r\n\ttask.loopCardinality=$.trim($" +
                            "('#loopCardinality').val());\r\n\ttask.collection=$.trim($('#collection').val());\r\n\ttask.elementVariable=$.trim($" +
                            "('#elementVariable').val());\r\n\ttask.completionCondition=$.trim($('#completionCondition').val());\r\n}\r\n\r\nfunction " +
                            "onSaveTaskProperties(){\r\n\t//update--begin---author:scott   date:20191219    for:用户支持弹出popup多选，增加保存校验\r\n");
                    var8.write("\tvar tempExpression = $.trim($('#expression').val())\r\n\tvar tempPerformerType=$('#performerType').combobox('getValue');" +
                            "\r\n\t//校验处理人情况下，用户只能选中一个\r\n\tif(tempPerformerType && tempPerformerType=='assignee'){\r\n\t\tif((tempExpression.indexOf(\"$\") " +
                            "== -1 && tempExpression.indexOf(\"(\") == -1) && tempExpression.indexOf(\",\") != -1 ){\r\n\t\t\ttip(\"人员类型为处理人情况下，用户只能选择一个!\");" +
                            "\r\n\t\t\treturn;\r\n\t\t}\r\n\t}\r\n\t//update--end---author:scott   date:20191219    " +
                            "for:用户支持弹出popup多选，增加保存校验\r\n\t\r\n\tsaveTaskProperties()\r\n\ttip(\"保存成功!\");\r\n}\r\n//加载变量\r\nfunction populateTaskProperites" +
                            "(){\r\n\t$('#performerType').combobox('setValue',task.performerType);\r\n\t$('#expression').val(task.expression);\r\n\trows[0]" +
                            ".value=task.taskId;\r\n\trows[1].value=task.taskName;\r\n\trows[2].value=task.documentation;\r\n\trows[3].value=task.formKey;" +
                            "\r\n\t\r\n}\r\n\r\n//加载属性表格数据\r\nfunction propertygrid(){\r\n\t$('#task-propertygrid').propertygrid('loadData', rows);" +
                            "\r\n\tpopulateTaskProperites();\r\n\t}\r\n//创建属性表格\r\n$('#task-propertygrid').propertygrid({\r\n  width: 'auto',\r\n  height: " +
                            "'auto',\r\n  showGroup: true,\r\n  scrollbarSize: 0,\r\n");
                    var8.write("  border:0,\r\n  columns: [[\r\n          { field: 'name', title: '属性名', width: 30, resizable: false },\r\n          { field:" +
                            " 'value', title: '属性值', width: 100, resizable: false }\r\n  ]],\r\n  onAfterEdit:function(){  \r\n  \tsaveTaskProperties();" +
                            "//自动保存\r\n   }\r\n});\r\npropertygrid();\r\n\r\n//-->\r\n</script>\r\n\r\n\r\n<script " +
                            "type=\"text/javascript\">\r\n<!--\r\n\t//获取执行监听器id\r\n\tfunction getOldListenerIds(){\r\n\t\tvar listeners=task.listeners;" +
                            "\r\n\t\t  var listenersIds=new Array();\r\n\t\t  for(var i=0;i<listeners.getSize();i++){\r\n\t\t\tvar listener = listeners.get" +
                            "(i);\r\n\t\t\tlistenersIds.push(listener.getId());\r\n\t\t  }\r\n\t\treturn listenersIds.join(\",\");" +
                            "\r\n\t}\r\n\t//添加执行监听器\r\n\tfunction addListener(id,event,serviceType,value){\r\n\t\tvar ls=task.getListener(id);\r\n\t\tif(!ls)" +
                            "{\r\n\t\t\tvar listener = new draw2d.Task.Listener();\r\n\t\t\tlistener.id = id;\r\n\t\t\tlistener.event = event;" +
                            "\r\n\t\t\tlistener.serviceType = serviceType;\r\n\t\t\tlistener.serviceClass = value;\r\n\t\t\tlistener.serviceExpression = " +
                            "value;\r\n\t\t\ttask.addListener(listener);\r\n\t\t}\r\n\t}\r\n\t//删除执行监听器\r\n\tfunction removeListener(id){\r\n\t\ttask" +
                            ".deleteListener(id);\r\n");
                    var8.write("\t}\t\r\n\r\n//-->\r\n</script>\r\n<div id=\"task-properties-layout\" class=\"easyui-layout\" fit=\"true\">\r\n <div " +
                            "id=\"task-properties-toolbar-panel\" region=\"north\" border=\"false\" style=\"height:30px; background: #E1F0F2;\">\r\n  <a " +
                            "href=\"##\" id=\"sb2\" class=\"easyui-linkbutton\" plain=\"true\" iconCls=\"icon-save\" onclick=\"onSaveTaskProperties()" +
                            "\">保存</a>\r\n </div>\r\n <div id=\"task-properties-panel\" region=\"center\" border=\"true\">\r\n  <div " +
                            "id=\"task-properties-accordion\" class=\"easyui-accordion\" fit=\"true\" border=\"false\">\r\n   <div id=\"task\" title=\"任务属性\"" +
                            " selected=\"true\" class=\"properties-menu\">\r\n    <table id=\"task-propertygrid\">\r\n    </table>\r\n   </div>\r\n   <div " +
                            "id=\"main-config\" title=\"人员配置\" class=\"properties-menu\">\r\n    <div class=\"datagrid-toolbar\" style=\"height:auto\">\r\n  " +
                            "   <table id=\"main-properties\">\r\n      <tr>\r\n       <td align=\"right\">\r\n       \t类型:\r\n       </td>\r\n       " +
                            "<td>\r\n        <select id=\"performerType\" name=\"performerType\" style=\"width:80px;\">\r\n\t         <option " +
                            "value=\"assignee\">处理人</option>\r\n\t         <option value=\"candidateUsers\">备选人员</option>\r\n");
                    var8.write("\t         <option value=\"candidateGroups\">备选角色</option>\r\n        </select>\r\n        <a href=\"#\" " +
                            "class=\"easyui-linkbutton\" plain=\"true\" iconCls=\"icon-search\" onclick=\"xuanze();\">选择</a>\r\n        <a href=\"#\" " +
                            "class=\"easyui-linkbutton\" plain=\"true\"  onclick=\"searchSelectUser();\">查询</a>\r\n       </td>\r\n      </tr>\r\n      " +
                            "<tr>\r\n       <td align=\"right\">\r\n   \t\t   表达式:\r\n       </td>\r\n       <td>\r\n        <input type=\"text\" " +
                            "id=\"expression\" name=\"expression\" style=\"width:130px\" />\r\n        <a href=\"#\" class=\"easyui-linkbutton\" " +
                            "plain=\"true\" iconCls=\"icon-cut\" onclick=\"selectExpression();\">更多</a>\r\n       </td>\r\n      </tr>\r\n     </table>\r\n  " +
                            "  </div>\r\n    <div id=\"task-candidate-panel\" class=\"\" style=\"overflow: hidden; width: 280px; height: 450px; padding:1px;" +
                            "\">\r\n    </div>\r\n   </div>\r\n \r\n   <div id=\"taskHuiQianProperties\" title=\"会签属性\" selected=\"true\">\r\n    <table " +
                            "id=\"main-properties\">\r\n      <tr>\r\n\t       <td align=\"right\">\r\n\t       \t 状态:\r\n\t       </td>\r\n\t       " +
                            "<td>\r\n\t\t        <select id=\"isSequential\" name=\"isSequential\" style=\"width:160px;\">\r\n");
                    var8.write("\t\t         <option value=\"\">不启动多实例</option>\r\n\t\t         <option value=\"true\">顺序</option>\r\n\t\t         <option " +
                            "value=\"false\">并行</option>\r\n\t\t        </select>\r\n\t       </td>\r\n      </tr>\r\n\t  <tr>\r\n\t       <td " +
                            "align=\"right\">\r\n\t   \t\t    循环数量:\r\n\t       </td>\r\n\t       <td>\r\n\t        <input type=\"text\" " +
                            "id=\"loopCardinality\" name=\"loopCardinality\" style=\"width:160px\" />\r\n\t       </td>\r\n      </tr>\r\n      <tr>\r\n\t   " +
                            "    <td align=\"right\">\r\n\t   \t        循环集合:\r\n\t       </td>\r\n\t       <td>\r\n\t        <input type=\"text\" " +
                            "id=\"collection\" name=\"collection\" style=\"width:160px\" />\r\n\t       </td>\r\n      </tr>\r\n      <tr>\r\n\t       <td " +
                            "align=\"right\">\r\n\t   \t\t  元素名:\r\n\t       </td>\r\n\t       <td>\r\n\t        <input type=\"text\" id=\"elementVariable\" " +
                            "name=\"elementVariable\" style=\"width:160px\" />\r\n\t       </td>\r\n      </tr>\r\n       <tr>\r\n\t       <td " +
                            "align=\"right\">\r\n\t   \t\t  结束条件:\r\n\t       </td>\r\n\t       <td>\r\n\t        <input type=\"text\" " +
                            "id=\"completionCondition\" name=\"completionCondition\" style=\"width:160px\" />\r\n\t       </td>\r\n      </tr>\r\n     " +
                            "</table>\r\n");
                    var8.write("    <fieldset style=\"line-height: 21px;\">\r\n\t\t<legend>说明</legend>\r\n\t\t<div>1.${flowUtil.stringToList" +
                            "(assigneeUserIdList)}，将字符串转换成集合，暴露的SpringBean方法</div>\r\n\t\t<div>2" +
                            ".多实例任务Activiti默认会创建3个流程变量，nrOfInstances:实例总数，nrOfActiveInstances:当前活跃的，也就是当前还未完成的，对于顺序的多实例，此值总是1," +
                            "nrOfCompletedInstances:已完成的实例个数。</div>\r\n\t\t<div>3.状态:不启动多实例,则只会创建一个任务，默认不启动，不启动多实例，一下配置都无效，true:顺序执行，fasle:并行," +
                            "同时执行。</div>\r\n\t\t<div>4.循环数量:指定创建多任务的数量。可使用表达式从流程变量获取。</div>\r\n\t\t<div>5" +
                            ".循环集合:流程变量中一个集合类型变量的变量名称。根据集合元素多少创建任务数量。可使用表达式。例:流程变量：assigneeUserIdList=[user1,user2]，可用assigneeUserIdList。</div>\r\n\t\t<div>6" +
                            ".集合元素:集合中每个元素的变量名称，可在每个任务中获取,可使用表达式。例：集合为当定义集合元素名称为:assigneeUserId,可在任务直接指派人员用表达式${assigneeUserId}获取，用于动态会签。</div>\r\n\t\t<div>7" +
                            ".结束条件:多实例活动结束的条件，默认为完成多全部实例，当表达式返回true时结束多实例活动。例：${nrOfCompletedInstances/nrOfInstances>=0.6} " +
                            "说明当有60%的任务完成时，会完成此多实例，删除其他未完成的，继续下面的流程。</div>\r\n\t</fieldset>\r\n   </div>\r\n   \r\n   <div id=\"listeners\" title=\"任务监听\" " +
                            "style=\"overflow: hidden;padding:1px;\">\r\n  ");
                    var8.write("\r\n    <div id=\"tb\">\r\n\t\t<a href=\"#\" class=\"easyui-linkbutton\" data-options=\"iconCls:'icon-add',plain:true\" " +
                            "onclick=\"selectProcessListener();\">添加</a>\r\n\t</div>\r\n\t<table id=\"listenerList\"></table>\r\n\t");
                    var8.write(" \r\n   </div>\r\n  </div>\r\n </div>\r\n</div>\r\n<script type=\"text/javascript\">\r\n//获取流程图中的监听IDS\r\nvar ids = " +
                            "getOldListenerIds();\r\n$('#listenerList').datagrid({\r\n    url:'designer/api/getListenersByIds',\r\n    fit: true,\r\n    " +
                            "queryParams: {'ids': ids,'token': workflow.process.token},\r\n    fitColumns:true,\r\n    toolbar: '#tb',\r\n    columns:[[\r\n " +
                            "   \t{field:'id',title:'名字',hidden:true},\r\n\t\t{field:'listenername',title:'名字',width:30},\r\n\t\t{field:'listenereven'," +
                            "title:'事件',width:30},\r\n\t\t{field:'listenertype',title:'类型',width:30},\r\n\t\t{field:'listenervalue',title:'执行内容',width:70}," +
                            "\r\n\t\t{field:'opt',title:'操作',width:30,align:'right',\r\n\t\t\t formatter:function(value,row,index){\r\n\t            var d = " +
                            "'<a href=\"#\" onclick=\"delRow('+index+')\">删除</a>';\r\n\t            return d;\r\n\t         }\r\n\t\t}\r\n    ]]\r\n});" +
                            "\r\n\r\n    var windowapi;\r\n    try{\r\n    \twindowapi = frameElement.api, \r\n    \tW = windowapi.opener;\r\n    }catch(e)" +
                            "{}\r\n    \r\n    //选择流程监听\r\n   function selectProcessListener(){\r\n\t   var url = " +
                            "'/act/designer/goListeners?typeid=2&token='+workflow.process.token;\r\n");
                    var8.write("\t   $.dialog({content: 'url:'+url,\r\n\t\t   title: '任务监听列表',\r\n\t\t   lock : true,\r\n\t\t   parent:windowapi,\r\n\t\t   " +
                            "width :'500px',\r\n\t\t   height :'450px',\r\n\t\t   left :'85%',\r\n\t\t   top :'65%',\r\n\t\t   opacity : 0.4,\r\n\t\t   " +
                            "button : [ {name : '确定',\r\n\t\t\t   callback : saveProcessListener,focus : true}, \r\n\t\t\t   {name : '取消',callback : function" +
                            "() {}} ]});\r\n\t   }\r\n    \r\n   //update--begin---author:scott   date:20191219    for:用户支持弹出popup多选\r\n   //popup高级选择用户\r\n " +
                            "  function searchSelectUser(){\r\n\t   var selectPerformerType = $('#performerType').combobox('getValue');\r\n\t   if" +
                            "(!selectPerformerType){\r\n\t\t   tip(\"请先选择类型!\");\r\n\t\t   return\r\n\t   }else if(selectPerformerType=='candidateGroups')" +
                            "{\r\n\t\t   tip(\"备选角色类型，不能查询用户!\");\r\n\t\t   return\r\n\t   }\r\n\t\t\r\n\t   var url = " +
                            "'/act/designer/goSearchSelectUser?token='+workflow.process.token;\r\n\t   console.log(\"userid \",$('#expression').val());\r\n\t   $" +
                            ".dialog({content: 'url:'+url,\r\n\t\t   title: '选择用户列表',\r\n\t\t   lock : true,\r\n\t\t   parent:windowapi,\r\n\t\t   width " +
                            ":'700px',\r\n\t\t   height :'450px',\r\n\t\t   data:{userids:$('#expression').val()},\r\n");
                    var8.write("\t\t   left :'85%',\r\n\t\t   top :'65%',\r\n\t\t   opacity : 0.4,\r\n\t\t   button : [ {name : '确定',\r\n\t\t\t   callback : " +
                            "clickcallbackUser,focus : true}, \r\n\t\t\t   {name : '取消',callback : function() {}} ]});\r\n\t}\r\n\t\r\n   function " +
                            "clickcallbackUser(){\r\n\t  var iframe = this.iframe.contentWindow;\r\n\t   var userIds=iframe.getselectExpressionListSelections" +
                            "('id');\t\r\n\t   if($('#expression').length>=1){\r\n\t\t   $('#expression').val(userIds);$('#expression').blur();" +
                            "\r\n\t\t}\r\n\t}\r\n   //update--end---author:scott   date:20191219    for:用户支持弹出popup多选，增加保存校验\r\n   \r\n \t//选择表达式\t\r\n   " +
                            "function selectExpression(){\r\n\t   var url = '/act/designer/goExpression?token='+workflow.process.token;\r\n\t   $" +
                            ".dialog({content: 'url:'+url,\r\n\t\t   title: '表达式列表',\r\n\t\t   lock : true,\r\n\t\t   parent:windowapi,\r\n\t\t   width " +
                            ":'500px',\r\n\t\t   height :'450px',\r\n\t\t   left :'85%',\r\n\t\t   top :'65%',\r\n\t\t   opacity : 0.4,\r\n\t\t   button : [ " +
                            "{name : '确定',\r\n\t\t\t   callback : clickcallback,focus : true}, \r\n\t\t\t   {name : '取消',callback : function() {}} ]});\r\n\t" +
                            "   }\r\n   \r\n   function clickcallback(){\r\n\t  var iframe = this.iframe.contentWindow;\r\n");
                    var8.write("\t   var expression=iframe.getselectExpressionListSelections('expression');\t\r\n\t   if($('#expression').length>=1){\r\n\t\t" +
                            "   $('#expression').val(expression);$('#expression').blur();\r\n\t\t}\r\n\t}\r\n   \r\n    //保存监听(回调)\r\n\tfunction " +
                            "saveProcessListener() {\r\n\t\tvar iframe = this.iframe.contentWindow;\r\n\t\tvar rows = iframe.getSelectProcessListenerList();" +
                            "\r\n\t\tfor(var i=0;i<rows.length;i++){ \r\n\t\t\tvar v_id = rows[i]['id'];\r\n\t\t\tvar v_listenername = " +
                            "rows[i]['listenername'];\r\n\t\t\tvar v_listenereven = rows[i]['listenereven'];\r\n\t\t\tvar v_listenertype = " +
                            "rows[i]['listenertype'];\r\n\t\t\tvar v_listenervalue = rows[i]['listenervalue'];\r\n\t\t\t\r\n\t\t\tvar ls=task.getListener" +
                            "(v_id);\r\n\t\t\taddListener(v_id,v_listenereven,v_listenertype,v_listenervalue);\r\n\t\t\t//addListener(listenerids[i]," +
                            "listenerevens[i],listenertypes[i],listenervalues[i]);\r\n\t\t\tif(!ls){\r\n\t\t\t\t$('#listenerList').datagrid('appendRow'," +
                            "{\r\n\t\t\t\t\tid:v_id,\r\n\t\t\t\t\tlistenername:v_listenername,\r\n\t\t\t\t\tlistenereven:v_listenereven," +
                            "\r\n\t\t\t\t\tlistenertype:v_listenertype,\r\n\t\t\t\t\tlistenervalue:v_listenervalue\r\n\t\t\t\t});\r\n");
                    var8.write("\t\t\t}\r\n\t\t}\r\n\t}\r\n\t\r\n\t/* function setProcessListener(index){\r\n\t\tvar row = $('#listenerList').datagrid" +
                            "('getRows')[index];\r\n\t\t$.ajax({\r\n\t\t\turl : \"designer/setProcessListener\",\r\n\t\t\ttype : 'POST',\r\n\t\t\tdata : " +
                            "{\r\n\t\t\t\tid :row.id\r\n\t\t\t},\r\n\t\t\tdataType : 'json',\r\n\t\t\tsuccess : function(data) {\r\n\t\t\t\tif (data.success)" +
                            " {\r\n\t\t\t\t\tvar listener = new draw2d.Task.Listener();\r\n\t\t\t\t\tlistener.event=row.TPListerer_listenereven;" +
                            "\r\n\t\t\t\t\tlistener.id=row.id;\r\n\t\t\t\t\tlistener.serviceType = row.TPListerer_listenertype;\r\n\t\t\t\t\tif(row" +
                            ".TPListerer_listenertype==\"javaClass\")\r\n\t\t\t\t\t{\r\n\t\t\t\t\t\tlistener.serviceClass= row.TPListerer_listenervalue;" +
                            "\r\n\t\t\t\t\t}\r\n\t\t\t\t\telse\r\n\t\t\t\t\t{\r\n\t\t\t\t\t \tlistener.serviceExpression=row.TPListerer_listenervalue;" +
                            "\r\n\t\t\t\t\t}\r\n\t\t\t\t\ttask.listeners.add(listener);\r\n\t\t\t\t}\r\n\t\t\t\telse\r\n\t\t\t\t{\r\n\t\t\t\t\ttask" +
                            ".deleteListener(row.id);\r\n\t\t\t\t}\r\n\t\t\t\treloadlistenerList();\r\n\t\t\t}\r\n\t\t});\r\n\t} " +
                            "*/\r\n\t\r\n\t//删除流程监听\r\n\tfunction delRow(id){\r\n  \t  var rows=$('#listenerList').datagrid('getSelections');\r\n  \t  for" +
                            "(var i=0;i<rows.length;i++){\r\n  \t\t  var row=rows[i];\r\n  \t\t  var index=$('#listenerList').datagrid('getRowIndex',row);" +
                            "\r\n");
                    var8.write("      \t  $('#listenerList').datagrid('deleteRow',index);\r\n      \t  removeListener(row.id);\r\n  \t  }\r\n    " +
                            "}\r\n\t\r\n\t//监听div大小变化事件,让用户列表自适应\r\n\t$(\"#main-config\").resize(function(){\r\n\t    console.log(\"触发了....\");\r\n\t    var " +
                            "panelWidth = $('#main-config').width();\r\n\t\ttask_candidate_panel.panel('resize', {width: panelWidth});\r\n\t});" +
                            "\r\n\t\r\n</script>\r\n");
                } catch (Throwable var31) {
                    if (!(var31 instanceof SkipPageException)) {
                        var8 = (JspWriter) var10;
                        if (var10 != null && ((JspWriter) var10).getBufferSize() != 0) {
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
