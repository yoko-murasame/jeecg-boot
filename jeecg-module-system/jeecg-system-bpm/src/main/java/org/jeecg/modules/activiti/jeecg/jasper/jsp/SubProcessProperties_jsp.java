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

public final class SubProcessProperties_jsp extends HttpJspBase implements JspSourceDependent, JspSourceImports {
    private static final JspFactory a = JspFactory.getDefaultFactory();
    private static Map<String, Long> b = new HashMap(5);
    private static final Set<String> c;
    private static final Set<String> d;
    private TagHandlerPool e;
    private volatile ExpressionFactory f;
    private volatile InstanceManager g;

    public SubProcessProperties_jsp() {
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
                    //                 String var13 = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + var12;
                    String contextPath = var12;
                    //                    String var13 = request.getScheme() + "://" + request.getServerName() + ":8989"  + var12;
                    String var14 = JMathUtil.defaultString(".jsp");
                    String var15 = request.getHeader("X_GATEWAY_BASE_PATH");
                    if (var15 != null && !var15.equals("")) {
                        contextPath = var15;
                    }

                    var8.write(13);
                    var8.write(10);
                    SetTag var16 = (SetTag) this.e.get(SetTag.class);
                    boolean var17 = false;

                    try {
                        var16.setPageContext(var11);
                        var16.setParent((Tag) null);
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

                    var8.write("\r\n<script type=\"text/javascript\">\r\nvar subProcess= workflow.getFigure(nodeid);\r\n\r\nvar windowapi;" +
                            "\r\ntry{\r\n\twindowapi = frameElement.api, \r\n\tW = windowapi.opener;\r\n}catch(e){}\r\n\r\n//属性表格定义\r\n rows = [\r\n    \r\n " +
                            "           { \"name\": \"ID\", \"group\": \"调用活动\", \"value\": subProcess.subProcessId,\"field\":\"subProcessId\", \"editor\": " +
                            "\"text\" },\r\n            { \"name\": \"名字\", \"group\": \"调用活动\", \"value\": subProcess.name, \"field\": \"name\", \"editor\":" +
                            " \"text\" },\r\n            { \"name\": \"名字\", \"group\": \"调用子流程\", \"value\": subProcess.callSubProcess, \"field\": " +
                            "\"callSubProcess\", \"editor\": \"text\" },\r\n/*             { \"name\": \"源变量\", \"group\": \"传入变量(主流程->子流程)\", \"value\": " +
                            "subProcess.insource,\"field\": \"insource\", \"editor\": \"text\" },\r\n            { \"name\": \"目标变量\", \"group\": \"传入变量" +
                            "(主流程->子流程)\", \"value\": subProcess.intarget, \"field\": \"intarget\", \"editor\": \"text\" },\r\n            { \"name\": " +
                            "\"源变量\", \"group\": \"传回变量(子流程->主流程)\", \"value\": subProcess.outsource, \"field\": \"outsource\", \"editor\": \"text\" },\r\n  " +
                            "          { \"name\": \"目标变量\", \"group\": \"传回变量(子流程->主流程)\", \"value\": subProcess.outtarget, \"field\": \"outtarget\", " +
                            "\"editor\": \"text\" } */\r\n");
                    var8.write("        ];\r\n //保存属性\r\nfunction saveSubProProperties(){\r\n\tsubProcess.subProcessId=rows[0].value;\r\n\tsubProcess" +
                            ".name=rows[1].value;\r\n\tsubProcess.callSubProcess=rows[2].value;\r\n/* \tsubProcess.insource=rows[3].value;\r\n\tsubProcess" +
                            ".intarget=rows[4].value;\r\n\tsubProcess.outsource=rows[5].value;\r\n\tsubProcess.outtarget=rows[6].value; */\r\n}\r\n " +
                            "//构建属性表格数据\r\nfunction populateSubProProperites(){\r\n\trows[0].value=subProcess.subProcessId;\r\n\trows[1].value=subProcess" +
                            ".name;\r\n\trows[2].value=subProcess.callSubProcess;\r\n/* \trows[3].value=subProcess.insource;\r\n\trows[4].value=subProcess" +
                            ".intarget;\r\n\trows[5].value=subProcess.outsource;\r\n\trows[6].value=subProcess.outtarget; */\r\n} \r\n //加载属性表格数据\r\nfunction" +
                            " subPropropertygrid(){\r\n\t$('#subpro-propertygrid').propertygrid('loadData',rows);\r\n\t}\r\n$(function(){\r\n//创建属性表格\r\n$" +
                            "('#subpro-propertygrid').propertygrid({\r\n  width: 'auto',\r\n  height: 'auto',\r\n  showGroup: true,\r\n  scrollbarSize: 0," +
                            "\r\n  border:0,\r\n  columns: [[\r\n          { field: 'name', title: '属性名', width: 30, resizable: false },\r\n          { " +
                            "field: 'value', title: '属性值', width: 100, resizable: false }\r\n");
                    var8.write("  ]],\r\n  onAfterEdit:function(){  \r\n  \tsaveSubProProperties();//自动保存\r\n   }\r\n});\r\nsubPropropertygrid();\r\n});" +
                            "\r\n\r\n$(function(){\r\n\t//初始化会签\r\n\t$('#isSequential').val(subProcess.isSequential);\r\n\t$('#loopCardinality').val" +
                            "(subProcess.loopCardinality);\r\n\t$('#collection').val(subProcess.activiti_collection);\r\n\t$('#elementVariable').val" +
                            "(subProcess.activiti_elementVariable);\r\n\t$('#completionCondition').val(subProcess.completionCondition);\r\n\t\r\n\tconsole" +
                            ".log(\"解析XML subProcess.insourceArray\",subProcess.insourceArray);\r\n\tconsole.log(\"解析XML subProcess.outsourceArray\"," +
                            "subProcess.outsourceArray);\r\n});\r\n//保存会签\r\nfunction onSaveTaskSubpro(){\r\n\tsubProcess.isSequential=$.trim($" +
                            "('#isSequential').val());\r\n\tsubProcess.loopCardinality=$.trim($('#loopCardinality').val());\r\n\tsubProcess" +
                            ".activiti_collection=$.trim($('#collection').val());\r\n\tsubProcess.activiti_elementVariable=$.trim($('#elementVariable').val()" +
                            ");\r\n\tsubProcess.completionCondition=$.trim($('#completionCondition').val());\r\n\ttip(\"保存成功!\");" +
                            "\r\n}\r\n\r\n//update--begin---author:scott   date:20191219    for:子流程支持多个参数配置----\r\n");
                    var8.write("//配置主子流程传递参数\r\nfunction configSubParams(){\r\n   var url = " + contextPathPre + " '/act/designer/goSubParameterList';\r\n   $.dialog({content:" +
                            " 'url:'+url,\r\n\t   title: '子流程参数配置页面',\r\n\t   lock : true,\r\n\t   parent:windowapi,\r\n\t   width :'600px',\r\n\t   height " +
                            ":'450px',\r\n\t   left :'85%',\r\n\t   top :'65%',\r\n\t   data:{subParams: subProcess.insourceArray?subProcess.insourceArray" +
                            ".concat(subProcess.outsourceArray):null},\r\n\t   opacity : 0.4,\r\n\t   button : [ {name : '确定',callback : clickcallback,focus " +
                            ": true}, \r\n\t\t          {name : '取消',callback : function() {}} ]});\r\n}\r\n//回调函数\r\nfunction clickcallback(){\r\n\tvar " +
                            "iframe = this.iframe.contentWindow;\r\n\tvar allSubParamsRows=iframe.getAllSubParams();\r\n\tconsole.log(\"配置参数 clickcallback\"," +
                            "allSubParamsRows);\r\n   \r\n\tsubProcess.insourceArray = allSubParamsRows.filter(getIn);\r\n\tsubProcess.outsourceArray =  " +
                            "allSubParamsRows.filter(getOut);\r\n\tconsole.log(\"更新subProcess.insourceArray\",subProcess.insourceArray);\r\n\tconsole.log" +
                            "(\"更新subProcess.outsourceArray\",subProcess.outsourceArray);\r\n\t//点击确认直接触发保存\r\n\tonSaveTaskSubpro();\r\n}\r\nfunction getIn" +
                            "(obj){\r\n");
                    var8.write("\t return obj.transfer == 'in';\r\n}\r\nfunction getOut(obj){\r\n\t return obj.transfer == 'out';" +
                            "\r\n}\r\n//update--begin---author:scott   date:20191219    for:子流程支持多个参数配置\r\n\r\n</script>\r\n<div " +
                            "id=\"subpro-properties-layout\" class=\"easyui-layout\" fit=\"true\">\r\n\t <div id=\"subpro-properties-toolbar-panel\" " +
                            "region=\"north\" border=\"false\" style=\"background: #E1F0F2;\">\r\n\t \t<a href=\"##\" id=\"sb2\" class=\"easyui-linkbutton\" " +
                            "plain=\"true\" iconCls=\"icon-save\" onclick=\"onSaveTaskSubpro()\">保存</a>\r\n\t \t<a href=\"##\" id=\"sb2\" " +
                            "class=\"easyui-linkbutton\" plain=\"true\" iconCls=\"icon-sum\" onclick=\"configSubParams()\">参数配置</a>\r\n\t </div>\r\n\t<div " +
                            "id=\"subpro-properties-panel\" region=\"center\" border=\"true\">\r\n\t  <div id=\"subpro-properties-accordion\" " +
                            "class=\"easyui-accordion\" fit=\"true\" border=\"false\">\r\n\t   <div id=\"subpro\" title=\"子流程属性\" selected=\"true\" " +
                            "class=\"properties-menu\">\r\n\t    <table id=\"subpro-propertygrid\">\r\n\t    </table>\r\n\t   </div>\r\n\t   \r\n\t   <div " +
                            "id=\"taskHuiQianProperties\" title=\"会签属性\" selected=\"true\">\r\n\t    <table id=\"main-properties\">\r\n\t      <tr>\r\n");
                    var8.write("\t\t       <td align=\"right\">\r\n\t\t       \t 状态:\r\n\t\t       </td>\r\n\t\t       <td>\r\n\t\t\t        <select " +
                            "id=\"isSequential\" name=\"isSequential\" style=\"width:160px;\">\r\n\t\t\t         <option value=\"\">不启动多实例</option>\r\n\t\t\t" +
                            "         <option value=\"true\">顺序</option>\r\n\t\t\t         <option value=\"false\">并行</option>\r\n\t\t\t        " +
                            "</select>\r\n\t\t       </td>\r\n\t      </tr>\r\n\t\t  <tr>\r\n\t\t       <td align=\"right\">\r\n\t\t   \t\t    循环数量:\r\n\t\t " +
                            "      </td>\r\n\t\t       <td>\r\n\t\t        <input type=\"text\" id=\"loopCardinality\" name=\"loopCardinality\" " +
                            "style=\"width:160px\" />\r\n\t\t       </td>\r\n\t      </tr>\r\n\t      <tr>\r\n\t\t       <td align=\"right\">\r\n\t\t   \t   " +
                            "     循环集合:\r\n\t\t       </td>\r\n\t\t       <td>\r\n\t\t        <input type=\"text\" id=\"collection\" name=\"collection\" " +
                            "style=\"width:160px\" />\r\n\t\t       </td>\r\n\t      </tr>\r\n\t      <tr>\r\n\t\t       <td align=\"right\">\r\n\t\t   \t\t " +
                            " 元素名:\r\n\t\t       </td>\r\n\t\t       <td>\r\n\t\t        <input type=\"text\" id=\"elementVariable\" name=\"elementVariable\"" +
                            " style=\"width:160px\" />\r\n\t\t       </td>\r\n\t      </tr>\r\n\t       <tr>\r\n\t\t       <td align=\"right\">\r\n");
                    var8.write("\t\t   \t\t  结束条件:\r\n\t\t       </td>\r\n\t\t       <td>\r\n\t\t        <input type=\"text\" id=\"completionCondition\" " +
                            "name=\"completionCondition\" style=\"width:160px\" />\r\n\t\t       </td>\r\n\t      </tr>\r\n\t     </table>\r\n\t    <fieldset" +
                            " style=\"line-height: 21px;\">\r\n\t\t\t<legend>说明</legend>\r\n\t\t\t<div>1.${flowUtil.stringToList(assigneeUserIdList)" +
                            "}，将字符串转换成集合，暴露的SpringBean方法</div>\r\n\t\t\t<div>2" +
                            ".多实例任务Activiti默认会创建3个流程变量，nrOfInstances:实例总数，nrOfActiveInstances:当前活跃的，也就是当前还未完成的，对于顺序的多实例，此值总是1," +
                            "nrOfCompletedInstances:已完成的实例个数。</div>\r\n\t\t\t<div>3.状态:不启动多实例,则只会创建一个任务，默认不启动，不启动多实例，一下配置都无效，true:顺序执行，fasle:并行," +
                            "同时执行。</div>\r\n\t\t\t<div>4.循环数量:指定创建多任务的数量。可使用表达式从流程变量获取。</div>\r\n\t\t\t<div>5" +
                            ".循环集合:流程变量中一个集合类型变量的变量名称。根据集合元素多少创建任务数量。可使用表达式。例:流程变量：assigneeUserIdList=[user1," +
                            "user2]，可用assigneeUserIdList。</div>\r\n\t\t\t<div>6.集合元素:集合中每个元素的变量名称，可在每个任务中获取,可使用表达式。例：集合为当定义集合元素名称为:assigneeUserId," +
                            "可在任务直接指派人员用表达式${assigneeUserId}获取，用于动态会签。</div>\r\n\t\t\t<div>7" +
                            ".结束条件:多实例活动结束的条件，默认为完成多全部实例，当表达式返回true时结束多实例活动。例：${nrOfCompletedInstances/nrOfInstances>=0.6} " +
                            "说明当有60%的任务完成时，会完成此多实例，删除其他未完成的，继续下面的流程。</div>\r\n");
                    var8.write("\t\t</fieldset>\r\n\t   </div>\r\n\t   \r\n\t  </div>\r\n\t</div>\r\n</div>");
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
