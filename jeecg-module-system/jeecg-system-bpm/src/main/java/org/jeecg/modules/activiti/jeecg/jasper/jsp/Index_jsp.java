package org.jeecg.modules.activiti.jeecg.jasper.jsp;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import lombok.extern.slf4j.Slf4j;
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

/**
 * 流程图设计页面
 */
@Slf4j
public final class Index_jsp extends HttpJspBase implements JspSourceDependent, JspSourceImports {
    private static final JspFactory a = JspFactory.getDefaultFactory();
    private static Map<String, Long> b = new HashMap(1);
    private static final Set<String> c;
    private static final Set<String> d;
    private TagHandlerPool e;
    private volatile ExpressionFactory f;
    private volatile InstanceManager g;

    public Index_jsp() {
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
                    log.info("getContextPath:" + var12);
//                    String var13 = request.getScheme() + "://" + request.getServerName() + ":8989"  + var12;
    //                 String var13 = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + var12;
                                String var13 = var12;
                    String var14 = JMathUtil.defaultString(".jsp");
                    String var15 = request.getHeader("X_GATEWAY_BASE_PATH");
                    log.info("X_GATEWAY_BASE_PATH:" + var15);
                    if (var15 != null && !var15.equals("")) {
                        var13 = var15;
                    }
                    log.info("BASE_PATH:" + var13);

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

                    var8.write("\r\n<!DOCTYPE html>\r\n<html>\r\n <head>\r\n  <title>在线流程设计器_JEECG版权所有</title>\r\n  \t<script type=\"text/javascript\" src=\"");
                    var8.print(var13);
                    var8.write("/plug-in/designer/page-loading.js\"></script>\r\n  \t<link id=\"easyuiTheme\" rel=\"stylesheet\" href=\"");
                    var8.print(var13);
                    var8.write("/plug-in/designer/easyui/easyui.css\" type=\"text/css\"></link>\r\n\t<link rel=\"stylesheet\" href=\"");
                    var8.print(var13);
                    var8.write("/plug-in/designer/easyui/icon.css\" type=\"text/css\"></link>\r\n\t<link href=\"");
                    var8.print(var13);
                    var8.write("/plug-in/designer/designer.css\" type=\"text/css\" rel=\"stylesheet\" />\r\n\t<script type=\"text/javascript\" src=\"");
                    var8.print(var13);
                    var8.write("/plug-in/designer/easyui/jquery-1.7.2.min.js\"></script>\r\n\t<script type=\"text/javascript\" src=\"");
                    var8.print(var13);
                    var8.write("/plug-in/designer/easyui/jquery.easyui.min.1.3.0.js\"></script>\r\n\t<script src=\"");
                    var8.print(var13);
                    var8.write("/plug-in/designer/draw2d/wz_jsgraphics.js\"></script>\r\n\t<script src=\"");
                    var8.print(var13);
                    var8.write("/plug-in/designer/draw2d/mootools.js\"></script>\r\n\t<script src=\"");
                    var8.print(var13);
                    var8.write("/plug-in/designer/draw2d/moocanvas.js\"></script>\r\n\t<script src=\"");
                    var8.print(var13);
                    var8.write("/plug-in/designer/draw2d/draw2d.js\"></script>\r\n\t<script src=\"");
                    var8.print(var13);
                    var8.write("/plug-in/designer/MyCanvas.js\"></script>\r\n\t<script src=\"");
                    var8.print(var13);
                    var8.write("/plug-in/designer/ResizeImage.js\"></script>\r\n\t<script src=\"");
                    var8.print(var13);
                    var8.write("/plug-in/designer/event/Start.js\"></script>\r\n\t<script src=\"");
                    var8.print(var13);
                    var8.write("/plug-in/designer/event/End.js\"></script>\r\n\t<script src=\"");
                    var8.print(var13);
                    var8.write("/plug-in/designer/connection/MyInputPort.js\"></script>\r\n\t<script src=\"");
                    var8.print(var13);
                    var8.write("/plug-in/designer/connection/MyOutputPort.js\"></script>\r\n\t<script src=\"");
                    var8.print(var13);
                    var8.write("/plug-in/designer/connection/DecoratedConnection.js\"></script>\r\n\t<script src=\"");
                    var8.print(var13);
                    var8.write("/plug-in/designer/task/Task.js\"></script>\r\n\t<script src=\"");
                    var8.print(var13);
                    var8.write("/plug-in/designer/task/UserTask.js\"></script>\r\n\t<script src=\"");
                    var8.print(var13);
                    var8.write("/plug-in/designer/task/ManualTask.js\"></script>\r\n\t<script src=\"");
                    var8.print(var13);
                    var8.write("/plug-in/designer/task/ServiceTask.js\"></script>\r\n\t<script src=\"");
                    var8.print(var13);
                    var8.write("/plug-in/designer/gateway/ExclusiveGateway.js\"></script>\r\n\t<script src=\"");
                    var8.print(var13);
                    var8.write("/plug-in/designer/gateway/ParallelGateway.js\"></script>\r\n\t<script src=\"");
                    var8.print(var13);
                    var8.write("/plug-in/designer/gateway/InclusiveGateway.js\"></script>\r\n\t<script src=\"");
                    var8.print(var13);
                    var8.write("/plug-in/designer/boundaryevent/TimerBoundary.js\"></script>\r\n\t<script src=\"");
                    var8.print(var13);
                    var8.write("/plug-in/designer/boundaryevent/ErrorBoundary.js\"></script>\r\n\t<script src=\"");
                    var8.print(var13);
                    var8.write("/plug-in/designer/subprocess/CallActivity.js\"></script>\r\n\t<script src=\"");
                    var8.print(var13);
                    var8.write("/plug-in/designer/task/ScriptTask.js\"></script>\r\n\t<script src=\"");
                    var8.print(var13);
                    var8.write("/plug-in/designer/task/MailTask.js\"></script>\r\n\t<script src=\"");
                    var8.print(var13);
                    var8.write("/plug-in/designer/task/ReceiveTask.js\"></script>\r\n\t<script src=\"");
                    var8.print(var13);
                    var8.write("/plug-in/designer/task/BusinessRuleTask.js\"></script>\r\n\t<script src=\"");
                    var8.print(var13);
                    var8.write("/plug-in/designer/designer.js\"></script>\r\n\t<script src=\"");
                    var8.print(var13);
                    var8.write("/plug-in/designer/mydesigner.js?v=1.0\"></script>\r\n\t<script src=\"");
                    var8.print(var13);
                    var8.write("/plug-in/designer/lhgDialog/lhgdialog.min.js\"></script>\r\n\r\n\t<script type=\"text/javascript\">\r\n\t\t$(function() {\r\n\t\t\ttry {\r\n\t\t\t\t_task_obj = $('#task');\r\n\t\t\t\t_task_context_menu = $('#task-context-menu').menu({});\r\n\t\t\t\t$('.easyui-linkbutton').draggable({\r\n\t\t\t\t\tproxy : function(source) {\r\n\t\t\t\t\t\tvar n = $('<div class=\"draggable-model-proxy\"></div>');\r\n\t\t\t\t\t\tn.html($(source).html()).appendTo('body');\r\n\t\t\t\t\t\treturn n;\r\n\t\t\t\t\t},\r\n\t\t\t\t\tdeltaX : 0,\r\n\t\t\t\t\tdeltaY : 0,\r\n\t\t\t\t\trevert : true,\r\n\t\t\t\t\tcursor : 'auto',\r\n\t\t\t\t\tonStartDrag : function() {\r\n\t\t\t\t\t\t$(this).draggable('options').cursor = 'not-allowed';\r\n\t\t\t\t\t},\r\n\t\t\t\t\tonStopDrag : function() {\r\n\t\t\t\t\t\t$(this).draggable('options').cursor = 'auto';\r\n\t\t\t\t\t}\r\n\t\t\t\t});\r\n\t\t\t\t$('#paintarea').droppable({\r\n\t\t\t\t\taccept : '.easyui-linkbutton',\r\n\t\t\t\t\tonDragEnter : function(e, source) {\r\n\t\t\t\t\t\t$(source).draggable('options').cursor = 'auto';\r\n\t\t\t\t\t},\r\n\t\t\t\t\tonDragLeave : function(e, source) {\r\n\t\t\t\t\t\t$(source).draggable('options').cursor = 'not-allowed';\r\n\t\t\t\t\t},\r\n\t\t\t\t\tonDrop : function(e, source) {\r\n");
                    var8.write("\t\t\t\t\t\t$(this).removeClass('over');\r\n\t\t\t\t\t\tvar wfModel = $(source).attr('wfModel');\r\n\t\t\t\t\t\tvar shape = $(source).attr('shape');\r\n\t\t\t\t\t\tif (wfModel) {\r\n\t\t\t\t\t\t\tvar x = $(source).draggable('proxy').offset().left;\r\n\t\t\t\t\t\t\tvar y = $(source).draggable('proxy').offset().top;\r\n\t\t\t\t\t\t\tvar xOffset = workflow.getAbsoluteX();\r\n\t\t\t\t\t\t\tvar yOffset = workflow.getAbsoluteY();\r\n\t\t\t\t\t\t\tvar scrollLeft = workflow.getScrollLeft();\r\n\t\t\t\t\t\t\tvar scrollTop = workflow.getScrollTop();\r\n\t\t\t\t\t\t\taddModel(wfModel, x - xOffset + scrollLeft, y - yOffset + scrollTop, shape);\r\n\t\t\t\t\t\t}\r\n\t\t\t\t\t}\r\n\t\t\t\t});\r\n\t\t\t\t//$('#paintarea').bind('contextmenu',function(e){\r\n\t\t\t\t//alert(e.target.tagName);\r\n\t\t\t\t//});\r\n\t\r\n\t\t\t} catch (e) {\r\n\t\r\n\t\t\t}\r\n\t\t});\r\n\t//-->\r\n\t</script>\r\n </head>\r\n <body id=\"designer\" class=\"easyui-layout\" style=\"position: static;\">\r\n  <div region=\"west\" split=\"true\" iconCls=\"palette-icon\" title=\"流程元素\" style=\"width: 110px;\">\r\n   <div class=\"easyui-accordion\" fit=\"true\" border=\"false\">\r\n    <div id=\"event\" title=\"事件\" iconCls=\"palette-menu-icon\" class=\"palette-menu\">\r\n");
                    var8.write("     <a href=\"#\" class=\"easyui-linkbutton\" plain=\"true\" iconCls=\"start-event-icon\" wfModel=\"Start\">开始</a>\r\n     <br>\r\n     <a href=\"#\" class=\"easyui-linkbutton\" plain=\"true\" iconCls=\"end-event-icon\" wfModel=\"End\">结束</a>\r\n     <br>\r\n    </div>\r\n    <div id=\"task\" title=\"任务\" iconCls=\"palette-menu-icon\" selected=\"true\" class=\"palette-menu\">\r\n     <a href=\"#\" class=\"easyui-linkbutton\" plain=\"true\" iconCls=\"user-task-icon\" wfModel=\"UserTask\">用户任务</a>\r\n     <br>\r\n     <a href=\"#\" class=\"easyui-linkbutton\" plain=\"true\" iconCls=\"manual-task-icon\" wfModel=\"ManualTask\">手工任务</a>\r\n     <br>\r\n     <a href=\"#\" class=\"easyui-linkbutton\" plain=\"true\" iconCls=\"service-task-icon\" wfModel=\"ServiceTask\">服务任务</a>\r\n     <br>\r\n     <a href=\"#\" class=\"easyui-linkbutton\" plain=\"true\" iconCls=\"script-task-icon\" wfModel=\"ScriptTask\">脚本任务</a>\r\n     <br>\r\n     <a href=\"#\" class=\"easyui-linkbutton\" plain=\"true\" iconCls=\"mail-task-icon\" wfModel=\"MailTask\">邮件任务</a>\r\n     <br>\r\n     <a href=\"#\" class=\"easyui-linkbutton\" plain=\"true\" iconCls=\"receive-task-icon\" wfModel=\"ReceiveTask\">接受任务</a>\r\n");
                    var8.write("     <br>\r\n     <a href=\"#\" class=\"easyui-linkbutton\" plain=\"true\" iconCls=\"business-rule-task-icon\" wfModel=\"BusinessRuleTask\">业务规则</a>\r\n     <br>\r\n     <a href=\"#\" class=\"easyui-linkbutton\" plain=\"true\" iconCls=\"subprocess-icon\" wfModel=\"CallActivity\">子流程</a>\r\n     <br>\r\n    </div>\r\n    <div id=\"gateway\" title=\"网关\" iconCls=\"palette-menu-icon\" class=\"palette-menu\">\r\n     <a href=\"#\" class=\"easyui-linkbutton\" plain=\"true\" iconCls=\"parallel-gateway-icon\" wfModel=\"ParallelGateway\">同步</a>\r\n     <br>\r\n     <a href=\"#\" class=\"easyui-linkbutton\" plain=\"true\" iconCls=\"exclusive-gateway-icon\" wfModel=\"ExclusiveGateway\">分支</a>\r\n     <br>\r\n     <a href=\"#\" class=\"easyui-linkbutton\" plain=\"true\" iconCls=\"inclusive-gateway-icon\" wfModel=\"InclusiveGateway\">包含</a>\r\n     <br>\r\n    </div>\r\n    <div id=\"boundary-event\" title=\"边界事件\" iconCls=\"palette-menu-icon\" class=\"palette-menu\">\r\n     <a href=\"#\" class=\"easyui-linkbutton\" plain=\"true\" iconCls=\"timer-boundary-event-icon\" wfModel=\"TimerBoundary\">时间边界</a>\r\n     <br>\r\n     <a href=\"#\" class=\"easyui-linkbutton\" plain=\"true\" iconCls=\"error-boundary-event-icon\" wfModel=\"ErrorBoundary\">错误边界</a>\r\n");
                    var8.write("     <br>\r\n    </div>\r\n   </div>\r\n  </div>\r\n  <div id=\"process-panel\" region=\"center\" style=\"padding: 1px\" split=\"true\" iconCls=\"process-icon\" title=\"流程\">\r\n   <div id=\"process-definition-tab\">\r\n    <div id=\"designer-area\" title=\"设计\" style=\"POSITION: absolute; width: 100%; height: 100%; padding: 0; border: none; overflow: auto;\">\r\n     <div id=\"paintarea\" style=\"POSITION: absolute; WIDTH: 2000px; HEIGHT: 2000px\"></div>\r\n    </div>\r\n    <div id=\"xml-area\" title=\"源码\" style=\"width: 100%; height: 100%; overflow: hidden; overflow-x: hidden; overflow-y: hidden;\">\r\n     <textarea id=\"descriptorarea\" rows=\"38\" style=\"width: 100%; height: 100%; padding: 0; border: none; font-size: 12px\" readonly=\"readonly\"></textarea>\r\n    </div>\r\n   </div>\r\n  </div>\r\n  <!-- toolbar -->\r\n  <div id=\"toolbar-panel\" region=\"north\" border=\"false\" style=\"height: 55px; background: #d8e4fe;\" title=\"工具栏\">\r\n   <input type=\"hidden\" name=\"processId\" id=\"processId\" value=\"");
                    var8.write((String)PageContextImpl.proprietaryEvaluate("${processid}", String.class, var4, (ProtectedFunctionMapper)null));
                    var8.write("\">\r\n   <input type=\"hidden\" name=\"token\" id=\"token\" value=\"");
                    var8.write((String)PageContextImpl.proprietaryEvaluate("${token}", String.class, var4, (ProtectedFunctionMapper)null));
                    var8.write("\">\r\n   <img width=\"20\" height=\"18\" title=\"保存流程\" src=\"");
                    var8.print(var13);
                    var8.write("/plug-in/designer/img/save.png\" onclick=\"saveProcessDef()\" class=\"buttonStyle\" />\r\n   <img width=\"20\" height=\"18\" title=\"上一步\" src=\"");
                    var8.print(var13);
                    var8.write("/plug-in/designer/img/back.png\" onclick=\"undo()\" class=\"buttonStyle\" />\r\n   <img width=\"20\" height=\"18\" title=\"下一步\" src=\"");
                    var8.print(var13);
                    var8.write("/plug-in/designer/img/next.png\" onclick=\"redo()\" class=\"buttonStyle\" />\r\n   <img width=\"20\" height=\"18\" title=\"导出\" src=\"");
                    var8.print(var13);
                    var8.write("/plug-in/designer/img/printer.png\" onclick=\"exportProcessDef(this)\" class=\"buttonStyle\" />\r\n   <img width=\"20\" height=\"18\" title=\"关闭设计\" src=\"");
                    var8.print(var13);
                    var8.write("/plug-in/designer/img/close.png\" onclick=\"closeW(this)\" class=\"buttonStyle\" />\r\n  </div>\r\n  <!-- update-begin--Author:scott  Date:20170703 for：重构变更control请求地址 -->\r\n  <div region=\"east\" id=\"properties-panel\" href=\"designer/processProperties?turn=processProperties&processId=");
                    var8.write((String)PageContextImpl.proprietaryEvaluate("${processid }", String.class, var4, (ProtectedFunctionMapper)null));
                    var8.write("\" split=\"true\" iconCls=\"properties-icon\" title=\"流程属性\"  style=\"padding:1px;width: 280px;\">\r\n  </div>\r\n  <!-- task context menu -->\r\n  <div id=\"task-context-menu\" class=\"easyui-menu\" style=\"width: 120px;\">\r\n   <div id=\"properties-task-context-menu\" iconCls=\"properties-icon\">\r\n    属性\r\n   </div>\r\n   <div id=\"delete-task-context-menu\" iconCls=\"icon-remove\">\r\n    删除\r\n   </div>\r\n  </div>\r\n  <!-- form configuration window -->\r\n  <div id=\"form-win\" title=\"表单配置\" style=\"width: 720px; height: 300px;\">\r\n  </div>\r\n  <!-- form configuration window -->\r\n  <div id=\"variable-win\" title=\"变量配置\" style=\"width: 720px; height: 300px;\">\r\n  </div>\r\n  <!-- listener configuration window -->\r\n  <div id=\"listener-win\" title=\"监听配置\" style=\"width: 720px; height: 300px;\">\r\n  </div>\r\n  <!-- candidate configuration window -->\r\n  <div id=\"task-candidate-win\" title=\"任务配置\" style=\"width: 720px; height: 300px;\">\r\n  </div>\r\n <script type=\"text/javascript\">\r\n\t$('#process-definition-tab').tabs({\r\n\t\tfit : true,\r\n\t\tonSelect : function(title) {\r\n\t\t\tif (title == '设计') {\r\n");
                    var8.write("\t\t\t\t\r\n\t\t\t} else if (title == '源码' || title == 'sourcecode') {\r\n\t\t\t\t$('#descriptorarea').val(workflow.toXML());\r\n\r\n\t\t\t}\r\n\t\t}\r\n\t});\r\n</script>\r\n\r\n");
                    String var33 = JMathUtil.defaultString(".jsp");
                    var8.write("\r\n\r\n <script type=\"text/javascript\">\r\ncreateCanvas('");
                    var8.write((String)PageContextImpl.proprietaryEvaluate("${processid}", String.class, var4, (ProtectedFunctionMapper)null));
                    var8.write("',false,'");
                    var8.write((String)PageContextImpl.proprietaryEvaluate("${token}", String.class, var4, (ProtectedFunctionMapper)null));
                    var8.write("');\r\nvar process;\r\ngetDefaultListener('");
                    var8.write((String)PageContextImpl.proprietaryEvaluate("${processid}", String.class, var4, (ProtectedFunctionMapper)null));
                    var8.write(39);
                    var8.write(44);
                    var8.write(39);
                    var8.write((String)PageContextImpl.proprietaryEvaluate("${token}", String.class, var4, (ProtectedFunctionMapper)null));
                    var8.write("');\r\nfunction getDefaultListener(processId,token){\r\n\tif(processId!=''&&processId!='0'){\r\n\t\treturn;\r\n\t}\r\n\t$.ajax({\r\n\t\turl : \"designer/api/getDefaultListener\",\r\n\t\ttype : 'GET',\r\n\t\tdata : {\r\n\t\t\tprocessId : processId,\r\n\t\t\ttoken: token\r\n\t\t},\r\n\t\tdataType : 'json',\r\n\t\terror : function(data) {\r\n\t\t\ttip(data.msg);\r\n\t\t},\r\n\t\tsuccess : function(data) {\r\n\t\t\tif (data.success) {\r\n\t\t\t\tvar listenerid = data.obj.id;\r\n\t\t\t\tvar listenereven = data.obj.listenereven;\r\n\t\t\t\tvar listenertype = data.obj.listenertype;\r\n\t\t\t\tvar listenervalue = data.obj.listenervalue;\r\n\t\t\t\tvar listenername = data.obj.listenername;\r\n\t\t\t\t/* alert(\"listenerid:\"+listenerid\r\n\t\t\t\t\t\t+\",listenereven:\"+listenereven\r\n\t\t\t\t\t\t+\",listenertype:\"+listenertype\r\n\t\t\t\t\t\t+\",listenervalue\"+listenervalue\r\n\t\t\t\t\t\t+\",listenername\"+listenername);  */\r\n\t\t\t\tif ($('#listenername').length >= 1) {\r\n\t\t\t\t\t$('#listenername').val(listenername);\r\n\t\t\t\t\t$('#listenername').blur();\r\n\t\t\t\t}\r\n\t\t\t\tif ($(\"input[name='listenername']\").length >= 1) {\r\n\t\t\t\t\t$(\"input[name='listenername']\").val(listenername);\r\n");
                   var8.write("\t\t\t\t\t$(\"input[name='listenername']\").blur();\r\n\t\t\t\t}\r\n\t\t\t\tif ($('#listenereven').length >= 1) {\r\n\t\t\t\t\t$('#listenereven').val(listenereven);\r\n\t\t\t\t\t$('#listenereven').blur();\r\n\t\t\t\t}\r\n\t\t\t\tif ($(\"input[name='listenereven']\").length >= 1) {\r\n\t\t\t\t\t$(\"input[name='listenereven']\").val(listenereven);\r\n\t\t\t\t\t$(\"input[name='listenereven']\").blur();\r\n\t\t\t\t}\r\n\t\t\t\tif ($('#listenertype').length >= 1) {\r\n\t\t\t\t\t$('#listenertype').val(listenertype);\r\n\t\t\t\t\t$('#listenertype').blur();\r\n\t\t\t\t}\r\n\t\t\t\tif ($(\"input[name='listenertype']\").length >= 1) {\r\n\t\t\t\t\t$(\"input[name='listenertype']\").val(listenertype);\r\n\t\t\t\t\t$(\"input[name='listenertype']\").blur();\r\n\t\t\t\t}\r\n\t\t\t\tif ($('#listenervalue').length >= 1) {\r\n\t\t\t\t\t$('#listenervalue').val(listenervalue);\r\n\t\t\t\t\t$('#listenervalue').blur();\r\n\t\t\t\t}\r\n\t\t\t\tif ($(\"input[name='listenervalue']\").length >= 1) {\r\n\t\t\t\t\t$(\"input[name='listenervalue']\").val(listenervalue);\r\n\t\t\t\t\t$(\"input[name='listenervalue']\").blur();\r\n\t\t\t\t}\r\n\t\t\t\tif (listenerid !== undefined && listenerid != \"\") {\r\n\t\t\t\t\t$('#listenerid').val(listenerid);\r\n");
                  var8.write("\t\t\t\t}\r\n\t\t\t\tsaveProcessListener(listenerid,listenereven,listenertype,listenervalue,listenername);\r\n\t\t\t}\r\n\t\t}\r\n\t});\r\n}\r\n\r\n//保存监听\r\nfunction saveProcessListener(listenerid,listenereven,listenertype,listenervalue,listenername) {\r\n\tvar listenerids = listenerid.split(\",\");\r\n\tvar listenerevens = listenereven.split(\",\");\r\n\tvar listenertypes = listenertype.split(\",\");\r\n\tvar listenervalues = listenervalue.split(\",\");\r\n\tvar listenernames = listenername.split(\",\");\r\n\tprocess = workflow.process;\r\n\t//var count = process.listeners.getSize();\r\n\t//if(count>0){\r\n\t//\treturn;\r\n\t//}\r\n\t//alert(count);\r\n\tfor(var i=0;i<listenerids.length;i++){\r\n\t\tvar ls = process.getListener(listenerids[i]);\r\n\t\taddListener(listenerids[i],listenerevens[i],listenertypes[i],listenervalues[i]);\r\n\t\tif(!ls){\r\n\t\t\t$('#listenerList').datagrid('appendRow',{\r\n\t\t\t\tid:listenerids[i],\r\n\t\t\t\tlistenername:listenernames[i],\r\n\t\t\t\tlistenereven:listenerevens[i],\r\n\t\t\t\tlistenertype:listenertypes[i],\r\n\t\t\t\tlistenervalue:listenervalues[i]\r\n\t\t\t});\r\n\t\t}\r\n\t}\r\n}\r\n\r\n//添加监听器\r\nfunction addListener(id,event,serviceType,value){\r\n");

                   var8.write("\tvar ls=process.getListener(id);\r\n\t" +
                           "if(!ls){\r\n\t\t" +
                           "var listener = new draw2d.Process.Listener();" +
                           "\r\n\t\tlistener.id=id;\r\n\t\tlistener.event = event;\r\n\t\tlistener.serviceType=serviceType;" +
                           "\r\n\t\tlistener.serviceClass = value;" +
                           "\r\n\t\tlistener.serviceExpression = value;" +
                           "\r\n\t\tprocess.addListener(listener);" +
                           "\r\n\t}" +
                           "\r\n}\r\n//信息提示\r\nfunction tip(msg){\r\n\t//$.messager.alert(\"操作提示\",msg,\"info\");\r\n\t$.messager.show({\r\n\t\ttitle:'提示',\r\n\t\tmsg:msg,\r\n\t\tstyle: { left: 500, top: 100 }\r\n\t});\r\n}\r\n//错误提示\r\nfunction tipError(msg){\r\n\t$.messager.alert(\"错误提醒！！\",msg,\"error\");\r\n}\r\n</script>\r\n </body>\r\n</html>\r\n");
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
