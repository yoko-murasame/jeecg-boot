package org.jeecg.modules.activiti.jeecg.jasper.bpm.controller;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.bpmn.behavior.ParallelGatewayActivityBehavior;
import org.activiti.engine.impl.bpmn.behavior.ParallelMultiInstanceBehavior;
import org.activiti.engine.impl.bpmn.behavior.SequentialMultiInstanceBehavior;
import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.DelegationState;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.lang.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.DateUtils;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.activiti.jeecg.commons.lang.MyStreamUtils;
import org.jeecg.modules.activiti.jeecg.jasper.extbpm.ExtbpmA;
import org.jeecg.modules.bpm.d.a.j;
import org.jeecg.modules.bpm.d.a.k;
import org.jeecg.modules.bpm.dto.ActHiActinstDTO;
import org.jeecg.modules.bpm.dto.ProcessHisDTO;
import org.jeecg.modules.bpm.dto.TaskDTO;
import org.jeecg.modules.bpm.service.ActivitiService;
import org.jeecg.modules.bpm.util.c;
import org.jeecg.modules.extbpm.process.entity.ExtActBpmFile;
import org.jeecg.modules.extbpm.process.entity.ExtActBpmLog;
import org.jeecg.modules.extbpm.process.entity.ExtActFlowData;
import org.jeecg.modules.extbpm.process.entity.ExtActTaskCc;
import org.jeecg.modules.extbpm.process.exception.BpmException;
import org.jeecg.modules.extbpm.process.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@RestController("actTaskController")
@RequestMapping({"/act/task"})
public class ActTaskController {
    private static final Logger a = LoggerFactory.getLogger(ActTaskController.class);
    @Autowired
    protected RepositoryService repositoryService;
    @Autowired
    protected RuntimeService runtimeService;
    @Autowired
    protected TaskService taskService;
    @Autowired
    protected IdentityService identityService;
    @Autowired
    private ActivitiService activitiService;
    @Autowired
    private IExtActBpmLogService extActBpmLogService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private IExtActTaskCcService extActTaskCcService;
    @Autowired
    private IExtActBpmFileService extActBpmFileService;
    @Autowired
    private ISysBaseAPI sysBaseAPI;
    @Autowired
    private IExtActFlowDataService extActFlowDataService;
    @Autowired
    private IExtActProcessService extActProcessService;

    public ActTaskController() {
    }

    @GetMapping({"/list"})
    public Result<IPage<TaskDTO>> a(HttpServletRequest var1) {
        a.debug("------------进入list---------------");
        long var2 = System.currentTimeMillis();
        Result var4 = new Result();
        Page var5 = new Page();
        String var6 = JwtUtil.getUserNameByToken(var1);
        List var7 = this.activitiService.findPriTodoTasks(var6, var1);
        long var8 = System.currentTimeMillis();
        a.info("获取我的任务分页列表 耗时：" + (var8 - var2) + "ms");
        Long var10 = this.activitiService.countPriTodaoTask(var6, var1);
        a.info("taskTotalSize：" + var10);
        a.info("获取我的任务总数  耗时：" + (System.currentTimeMillis() - var8) + "ms");
        var5.setRecords(var7);
        var5.setTotal(var10);
        var4.setSuccess(true);
        var4.setResult(var5);
        a.debug("-----\t-------退出list---------------");
        return var4;
    }

    @GetMapping({"/taskGroupList"})
    public Result<IPage<TaskDTO>> b(HttpServletRequest var1) {
        Result var2 = new Result();
        Page var3 = new Page();
        String var4 = JwtUtil.getUserNameByToken(var1);
        List var5 = this.sysBaseAPI.getRolesByUsername(var4);
        List var6 = this.activitiService.findGroupTodoTasks(var5, var1);
        Long var7 = this.activitiService.countGroupTodoTasks(var5, var1);
        a.info("taskSize：" + var7);
        var3.setRecords(var6);
        var3.setTotal(var7);
        var2.setSuccess(true);
        var2.setResult(var3);
        return var2;
    }

    @GetMapping({"/taskHistoryList"})
    public Result<IPage<TaskDTO>> a(HttpServletRequest var1, @RequestParam(name = "pageNo",defaultValue = "1") Integer var2, @RequestParam(name = "pageSize",defaultValue = "10") Integer var3) {
        Result var4 = new Result();
        Page var5 = new Page((long)var2, (long)var3);
        String var6 = JwtUtil.getUserNameByToken(var1);
        var5 = this.activitiService.findHistoryTasks(var5, var6, var1);
        var4.setSuccess(true);
        var4.setResult(var5);
        return var4;
    }

    @GetMapping({"/getProcessTaskTransInfo"})
    public Result<Map<String, Object>> a(@RequestParam(name = "taskId",required = true) String var1) {
        Result var2 = new Result();
        Task var3 = this.activitiService.getTask(var1);
        HashMap var4 = new HashMap();
        List var5 = this.activitiService.getOutTransitions(var1);
        if (var5.size() == 1) {
            Iterator var6 = var5.iterator();

            while(var6.hasNext()) {
                Map var7 = (Map)var6.next();
                var7.put("Transition", "确认提交");
            }
        }

        var4.put("transitionList", var5);
        var4.put("nextCodeCount", var5 == null ? 0 : var5.size());
        List var16 = this.activitiService.getHistTaskNodeList(var3.getProcessInstanceId());
        var4.put("histListNode", var16);
        var4.put("histListSize", var16.size());
        String var17 = this.a(var3, var16);
        a.info("turnbackTaskId======>" + var17);
        var4.put("turnbackTaskId", var17);
        var4.put("taskId", var1);
        var4.put("taskName", var3.getName());
        String var8 = "";
        String var9 = "";
        if (var3 != null && ExtbpmA.b(var3.getAssignee())) {
            LoginUser var10 = this.sysBaseAPI.getUserByName(var3.getAssignee());
            if (var10 != null) {
                var8 = var10.getRealname();
            }

            var9 = var3.getCreateTime() == null ? "" : DateUtils.formatDate(var3.getCreateTime(), "YYYY-MM-dd HH:mm:ss");
        }

        var4.put("taskAssigneeName", var8);
        var4.put("taskNameStartTime", var9);
        new ArrayList();
        LambdaQueryWrapper<ExtActBpmLog> var11 = new LambdaQueryWrapper<>();
        var11.eq(ExtActBpmLog::getProcInstId, var3.getProcessInstanceId());
        var11.ne(ExtActBpmLog::getOpUserId, "System Job");
        var11.orderByAsc(ExtActBpmLog::getOpTime);
        List var18 = this.extActBpmLogService.list(var11);
        List var12 = null;
        Iterator var13 = var18.iterator();

        while(var13.hasNext()) {
            ExtActBpmLog var14 = (ExtActBpmLog)var13.next();
            LambdaQueryWrapper<ExtActBpmFile> var15 = new LambdaQueryWrapper<>();
            var15.eq(ExtActBpmFile::getBpmLogId, var14.getId());
            var12 = this.extActBpmFileService.list(var15);
            var14.setBpmFiles(var12);
        }

        boolean var19 = false;
        boolean var21 = false;
        int var20;
        int var22;
        if (var18.size() - 3 > 0) {
            var20 = var18.size() - 3;
            var22 = var18.size();
        } else {
            var20 = 0;
            var22 = var18.size();
        }

        List var23 = var18.subList(var20, var22);
        var4.put("bpmLogList", var18);
        var4.put("bpmLogListCount", var18.size());
        var4.put("bpmLogStepList", var23);
        var4.put("bpmLogStepListCount", var23.size());
        var2.setResult(var4);
        var2.setSuccess(true);
        return var2;
    }

    @GetMapping({"/getHisProcessTaskTransInfo"})
    public Result<Map<String, Object>> b(@RequestParam(name = "procInstId",required = true) String var1) {
        Result var2 = new Result();
        HashMap var3 = new HashMap();
        List var4 = this.activitiService.getActHiActinstStartAndEnd(var1);
        boolean var5 = false;
        ExtActBpmLog var6 = new ExtActBpmLog();
        Iterator var7 = var4.iterator();

        LoginUser var12;
        while(var7.hasNext()) {
            ActHiActinstDTO var8 = (ActHiActinstDTO)var7.next();
            if ("endEvent".equals(var8.getActType())) {
                var5 = true;
                var6.setTaskName("结束");
                var6.setOpTime(var8.getEndTime());
                List var9 = this.activitiService.getProcessEndUserId(var1);
                if (var9 != null) {
                    Map var10 = (Map)var9.get(0);
                    String var11 = oConvertUtils.getString(var10.get("ASSIGNEE_"));
                    if (oConvertUtils.isNotEmpty(var11)) {
                        var12 = this.sysBaseAPI.getUserByName(var11);
                        var6.setOpUserName(var12 != null ? var12.getRealname() : var11);
                    }
                }
                break;
            }
        }

        String var16 = null;
        String var17 = null;
        String var18 = null;
        List var19;
        if (!var5) {
            var19 = ((HistoricTaskInstanceQuery)this.historyService.createHistoricTaskInstanceQuery().processInstanceId(var1)).list();
            if (var19 != null && var19.size() > 0) {
                HistoricTaskInstance var20 = (HistoricTaskInstance)var19.get(var19.size() - 1);
                var16 = var20.getName();
                if (oConvertUtils.isNotEmpty(var20.getAssignee())) {
                    var12 = this.sysBaseAPI.getUserByName(var20.getAssignee());
                    var17 = var12.getRealname();
                }

                var18 = var20.getStartTime() == null ? "" : DateUtils.formatDate(var20.getStartTime(), "YYYY-MM-dd HH:mm:ss");
            }
        }

        var3.put("currTaskName", var16);
        var3.put("currTaskNameAssignee", var17);
        var3.put("currTaskNameStartTime", var18);
        new ArrayList();
        LambdaQueryWrapper<ExtActBpmLog> var21 = new LambdaQueryWrapper<>();
        var21.eq(ExtActBpmLog::getProcInstId, var1);
        var21.ne(ExtActBpmLog::getOpUserId, "System Job");
        var21.orderByAsc(ExtActBpmLog::getOpTime);
        var19 = this.extActBpmLogService.list(var21);
        var12 = null;
        Iterator var13 = var19.iterator();

        while(var13.hasNext()) {
            ExtActBpmLog var14 = (ExtActBpmLog)var13.next();
            LambdaQueryWrapper<ExtActBpmFile> var15 = new LambdaQueryWrapper<>();
            var15.eq(ExtActBpmFile::getBpmLogId, var14.getId());
            List var22 = this.extActBpmFileService.list(var15);
            var14.setBpmFiles(var22);
        }

        if (var5) {
            var19.add(var6);
        }

        int var23 = var19.size() - 3 < 0 ? 0 : var19.size() - 3;
        List var24 = (List)var19.stream().skip((long)var23).collect(Collectors.toList());
        a.info("bpmLogStepList size : " + var24.size());
        var3.put("bpmLogList", var19);
        var3.put("bpmLogListCount", var19.size());
        var3.put("bpmLogStepList", var24);
        var3.put("bpmLogStepListCount", var24.size());
        var2.setResult(var3);
        var2.setSuccess(true);
        return var2;
    }

    private String a(Task var1, List<Map<String, Object>> var2) {
        String var3 = "";
        ArrayList var4 = new ArrayList();
        List var5 = this.a(var1);
        if (var2 != null && var2.size() > 0) {
            for(int var6 = var2.size(); var6 > 0; --var6) {
                Map var7 = (Map)var2.get(var6 - 1);
                if (var5 != null && var5.size() > 0) {
                    Iterator var8 = var5.iterator();

                    while(var8.hasNext()) {
                        ActivityImpl var9 = (ActivityImpl)var8.next();
                        if (var9.getId().equals((String)var7.get("task_def_key_"))) {
                            var4.add(var9.getId());
                            break;
                        }
                    }
                }
            }
        }

        if (var4.size() > 0) {
            var3 = (String)var4.get(0);
        }

        return var3;
    }

    private List<ActivityImpl> a(Task var1) {
        ArrayList var2 = new ArrayList();
        ProcessDefinitionEntity var3 = (ProcessDefinitionEntity)((RepositoryServiceImpl)this.repositoryService).getDeployedProcessDefinition(var1.getProcessDefinitionId());
        ActivityImpl var4 = var3.findActivity(var1.getTaskDefinitionKey());
        if (!(var4.getActivityBehavior() instanceof SequentialMultiInstanceBehavior) && !(var4.getActivityBehavior() instanceof ParallelMultiInstanceBehavior)) {
            var2.addAll(this.a(var4));
        }

        return var2;
    }

    private List<ActivityImpl> a(ActivityImpl var1) {
        ArrayList var2 = new ArrayList();
        List var3 = var1.getIncomingTransitions();
        Iterator var4 = var3.iterator();

        while(var4.hasNext()) {
            PvmTransition var5 = (PvmTransition)var4.next();
            ActivityImpl var6 = (ActivityImpl)var5.getSource();
            if (var6.getActivityBehavior() instanceof UserTaskActivityBehavior) {
                var2.add(var6);
            } else {
                if (var6.getActivityBehavior() instanceof ParallelGatewayActivityBehavior) {
                    break;
                }

                var2.addAll(this.a(var6));
            }
        }

        return var2;
    }

    @GetMapping({"traceImage"})
    public void a(@RequestParam("processInstanceId") String var1, HttpServletResponse var2) throws Exception {
        InputStream var3 = null;

        try {
            j var4 = new j(var1);
            ProcessEngine var5 = ProcessEngines.getDefaultProcessEngine();
            var3 = (InputStream)var5.getManagementService().executeCommand(var4);
            boolean var6 = false;
            byte[] var7 = new byte[1024];

            int var18;
            while((var18 = var3.read(var7, 0, 1024)) != -1) {
                var2.getOutputStream().write(var7, 0, var18);
            }

            var2.getOutputStream().flush();
            var2.getOutputStream().close();
        } catch (Exception var16) {
            var16.printStackTrace();
        } finally {
            try {
                if (var3 != null) {
                    var3.close();
                }
            } catch (Exception var15) {
            }

        }

    }

    @GetMapping({"getNodePositionInfo"})
    public Result<Map<String, Object>> b(@RequestParam("processInstanceId") String var1, HttpServletResponse var2) throws Exception {
        Result var3 = new Result();
        HashMap var4 = new HashMap();
        List var5 = ((HistoricTaskInstanceQuery)this.historyService.createHistoricTaskInstanceQuery().processInstanceId(var1)).list();
        ArrayList var6 = new ArrayList();
        TaskDTO var7 = null;
        ExtActBpmLog var8 = null;

        List var13;
        for(Iterator var9 = var5.iterator(); var9.hasNext(); var6.add(var7)) {
            HistoricTaskInstance var10 = (HistoricTaskInstance)var9.next();
            var7 = new TaskDTO();
            var7.setId(var10.getId());
            var7.setTaskId(var10.getTaskDefinitionKey());
            var7.setTaskBeginTime(var10.getStartTime());
            var7.setTaskEndTime(var10.getEndTime());
            var7.setTaskAssigneeId(var10.getAssignee());
            var7.setTaskDueTime(var10.getDurationInMillis());
            var7.setTaskName(var10.getName());
            LoginUser var11 = this.sysBaseAPI.getUserByName(var10.getAssignee());
            if (var11 != null) {
                var7.setTaskAssigneeName(var11.getRealname());
            }

            LambdaQueryWrapper<ExtActBpmLog> var12 = new LambdaQueryWrapper<>();
            var12.eq(ExtActBpmLog::getTaskId, var10.getId());
            var13 = this.extActBpmLogService.list(var12);
            if (var13 != null && var13.size() > 0) {
                var8 = (ExtActBpmLog)var13.get(0);
                var7.setRemarks(var8.getRemarks());
            }
        }

        var4.put("hisTasks", var6);
        k var17 = new k(var1);
        ProcessEngine var18 = ProcessEngines.getDefaultProcessEngine();
        List var19 = (List)var18.getManagementService().executeCommand(var17);
        ArrayList var20 = new ArrayList();
        var13 = null;
        Iterator var14 = var19.iterator();

        while(var14.hasNext()) {
            ActivityImpl var15 = (ActivityImpl)var14.next();
            HashMap var21 = new HashMap();
            var21.put("x", var15.getX());
            var21.put("y", var15.getY());
            var21.put("width", var15.getWidth());
            var21.put("height", var15.getHeight());
            var21.put("id", var15.getId());
            String var16 = var15.getX() + "," + var15.getY() + "," + (var15.getX() + var15.getWidth()) + "," + (var15.getY() + var15.getHeight());
            var21.put("coords", var16);
            var20.add(var21);
        }

        var4.put("positionList", var20);
        var3.setResult(var4);
        var3.setSuccess(true);
        return var3;
    }

    @GetMapping({"/processHistoryList"})
    public Result<IPage<ProcessHisDTO>> a(@RequestParam("processInstanceId") String var1, HttpServletRequest var2) {
        Result var3 = new Result();
        Page var4 = new Page();
        ArrayList var5 = new ArrayList();
        List var6 = this.activitiService.getActHiActinstStartAndEnd(var1);
        List var7 = ((HistoricTaskInstanceQuery)this.historyService.createHistoricTaskInstanceQuery().processInstanceId(var1)).list();
        String var8 = this.activitiService.getProcessStartUserId(var1);
        String var9 = "";
        List var10 = this.activitiService.getProcessEndUserId(var1);
        Map var11;
        if (var10 != null) {
            var11 = (Map)var10.get(0);
            if (var11 != null) {
                var9 = var11.get("ASSIGNEE_") == null ? "" : (String)var11.get("ASSIGNEE_");
            }
        }

        var11 = null;
        Iterator var12 = var6.iterator();

        ActHiActinstDTO var13;
        String var14;
        String var15;
        LoginUser var16;
        ProcessHisDTO var18;
        while(var12.hasNext()) {
            var13 = (ActHiActinstDTO)var12.next();
            if ("startEvent".equals(var13.getActType())) {
                var14 = var13.getStartTime() == null ? "" : DateUtils.formatDate(var13.getStartTime(), "yyyy-MM-dd HH:mm:ss");
                var15 = var13.getEndTime() == null ? "" : DateUtils.formatDate(var13.getEndTime(), "yyyy-MM-dd HH:mm:ss");
                var18 = new ProcessHisDTO();
                var18.setId(org.jeecg.modules.extbpm.process.common.a.i);
                var18.setName(var13.getActId());
                var18.setProcessInstanceId(var13.getExecutionId());
                var18.setStartTime(var14);
                var18.setEndTime(var15);
                var18.setAssignee(var8);
                if (oConvertUtils.isNotEmpty(var18.getAssignee())) {
                    var16 = this.sysBaseAPI.getUserByName(var18.getAssignee());
                    var18.setAssigneeName(var16 != null ? var16.getRealname() : var18.getAssignee());
                }

                var18.setDeleteReason("已完成");
                var5.add(var18);
            }
        }

        var12 = var7.iterator();

        while(var12.hasNext()) {
            HistoricTaskInstance var19 = (HistoricTaskInstance)var12.next();
            var14 = "";
            if ("completed".equals(var19.getDeleteReason())) {
                var14 = "已完成";
                if (var19.getDescription() != null && var19.getDescription().indexOf("委托") != -1) {
                    var14 = var19.getDescription() + "【" + var14 + "】";
                }
            } else {
                var14 = var19.getDeleteReason();
            }

            var15 = var19.getStartTime() == null ? "" : DateUtils.formatDate(var19.getStartTime(), "yyyy-MM-dd HH:mm:ss");
            String var20 = var19.getEndTime() == null ? "" : DateUtils.formatDate(var19.getEndTime(), "yyyy-MM-dd HH:mm:ss");
            var18 = new ProcessHisDTO();
            var18.setId(var19.getId());
            var18.setName(var19.getName());
            var18.setProcessInstanceId(var19.getProcessInstanceId());
            var18.setStartTime(var15);
            var18.setEndTime(var20);
            var18.setAssignee(var19.getAssignee());
            if (oConvertUtils.isNotEmpty(var18.getAssignee())) {
                LoginUser var17 = this.sysBaseAPI.getUserByName(var18.getAssignee());
                var18.setAssigneeName(var17 != null ? var17.getRealname() : var18.getAssignee());
            }

            var18.setDeleteReason(var14);
            var5.add(var18);
        }

        var12 = var6.iterator();

        while(var12.hasNext()) {
            var13 = (ActHiActinstDTO)var12.next();
            if ("endEvent".equals(var13.getActType())) {
                var14 = var13.getStartTime() == null ? "" : DateUtils.formatDate(var13.getStartTime(), "yyyy-MM-dd HH:mm:ss");
                var15 = var13.getEndTime() == null ? "" : DateUtils.formatDate(var13.getEndTime(), "yyyy-MM-dd HH:mm:ss");
                var18 = new ProcessHisDTO();
                var18.setId(org.jeecg.modules.extbpm.process.common.a.i);
                var18.setName(var13.getActId());
                var18.setProcessInstanceId(var13.getExecutionId());
                var18.setStartTime(var14);
                var18.setEndTime(var15);
                var18.setAssignee(var9);
                if (oConvertUtils.isNotEmpty(var18.getAssignee())) {
                    var16 = this.sysBaseAPI.getUserByName(var18.getAssignee());
                    var18.setAssigneeName(var16 != null ? var16.getRealname() : var18.getAssignee());
                }

                var18.setDeleteReason("已完成");
                var5.add(var18);
            }
        }

        a.info("taskSize：" + var5.size());
        var4.setRecords(var5);
        var4.setTotal((long)var5.size());
        var3.setSuccess(true);
        var3.setResult(var4);
        return var3;
    }

    @PostMapping({"processComplete"})
    public Result<Object> processComplete(@RequestBody HashMap<String, String> params, HttpServletRequest request) {
        Result var3 = new Result();

        Throwable throwable;
        Throwable throwable1;
        try {
            request.setAttribute("data", params);
            String taskId = oConvertUtils.getString((String)params.get("taskId"));
            String nextnode = oConvertUtils.getString((String)params.get("nextnode"));
            int nextCodeCount = oConvertUtils.getInt((String)params.get("nextCodeCount"));
            String processModel = oConvertUtils.getString((String)params.get("processModel"));
            String nextUserId = oConvertUtils.getString((String)params.get("nextUserId"));
            String rejectModelNode = oConvertUtils.getString((String)params.get("rejectModelNode"));
            HashMap<String, Object> variables = new HashMap<>();
            Task task = this.activitiService.getTask(taskId);
            String processInstanceId = task.getProcessInstanceId();
            this.runtimeService.setVariable(processInstanceId, org.jeecg.modules.extbpm.process.common.a.q, org.jeecg.modules.extbpm.process.common.a.d);
            boolean isUserTask = this.b(taskId, nextnode);
            if (isUserTask) {
                params.put(org.jeecg.modules.extbpm.process.common.a.y, nextUserId);
                this.runtimeService.setVariable(processInstanceId, org.jeecg.modules.extbpm.process.common.a.y, nextUserId);
            }

            if (StringUtils.isNotBlank(task.getOwner())) {
                DelegationState delegationState = task.getDelegationState();
                switch(delegationState) {
                    case PENDING:
                        this.taskService.resolveTask(taskId);
                    case RESOLVED:
                }
            }

            String var27 = "";
            String var15;
            int var18;
            String var19;
            String var32;
            if ("1".equals(processModel)) {
                boolean var28 = this.c(task.getProcessDefinitionId(), nextnode);
                if ("end".equals(nextnode)) {
                    if (nextCodeCount != 1 && !var28) {
                        this.activitiService.goProcessTaskNode(taskId, nextnode, variables);
                    } else {
                        this.taskService.complete(taskId, variables);
                    }
                } else {
                    if (nextCodeCount != 1 && !var28) {
                        this.activitiService.goProcessTaskNode(taskId, nextnode, variables);
                    } else {
                        this.taskService.complete(taskId, variables);
                    }

                    if (!isUserTask) {
                        String var30 = this.activitiService.getTaskIdByProins(processInstanceId, nextnode);
                        if (oConvertUtils.isNotEmpty(nextUserId) && oConvertUtils.isNotEmpty(var30)) {
                            if (nextUserId.indexOf(",") < 0) {
                                this.taskService.setAssignee(var30, nextUserId);
                            } else {
                                this.taskService.setAssignee(var30, (String)null);
                                String[] var33 = nextUserId.split(",");
                                var18 = var33.length;

                                for(int var36 = 0; var36 < var18; ++var36) {
                                    String var20 = var33[var36];
                                    if (oConvertUtils.isNotEmpty(var20)) {
                                        this.taskService.addCandidateUser(var30, var20);
                                    }
                                }
                            }
                        }
                    }
                }
            } else if ("2".equals(processModel)) {
                this.taskService.complete(taskId, variables);
            } else {
                this.runtimeService.setVariable(processInstanceId, org.jeecg.modules.extbpm.process.common.a.q, org.jeecg.modules.extbpm.process.common.a.B);
                this.activitiService.goProcessTaskNode(taskId, rejectModelNode, variables);
                var15 = this.activitiService.getTaskIdByProins(processInstanceId, rejectModelNode);
                if (oConvertUtils.isNotEmpty(nextUserId)) {
                    if (nextUserId.indexOf(",") < 0) {
                        this.taskService.setAssignee(var15, nextUserId);
                    } else {
                        this.taskService.setAssignee(var15, (String)null);
                        String[] var16 = nextUserId.split(",");
                        int var17 = var16.length;

                        for(var18 = 0; var18 < var17; ++var18) {
                            var19 = var16[var18];
                            if (oConvertUtils.isNotEmpty(var19)) {
                                this.taskService.addCandidateUser(var15, var19);
                            }
                        }
                    }
                }

                Task var29 = this.activitiService.getTask(var15);
                var32 = task.getAssignee();
                LoginUser var34 = this.sysBaseAPI.getUserByName(task.getAssignee());
                if (var34 != null) {
                    var32 = var34.getRealname();
                }

                var27 = var32 + "驳回任务 〔" + task.getName() + "〕 →〔" + var29.getName() + "〕 ";
            }

            var15 = JwtUtil.getUserNameByToken(request);
            LoginUser var31 = this.sysBaseAPI.getUserByName(var15);
            var32 = oConvertUtils.getString((String)params.get("ccUserIds"));
            this.a(var32, task, var15);
            ProcessInstance var35 = (ProcessInstance)this.runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
            var19 = oConvertUtils.getString((String)params.get("reason"));
            ExtActBpmLog var37 = new ExtActBpmLog();
            if (var35 != null) {
                var37.setBusinessKey(var35.getBusinessKey());
                var37.setProcName(var35.getName());
            }

            var37.setOpTime(new Date());
            var37.setOpUserId(var15);
            if (var31 != null) {
                var37.setOpUserName(var31.getRealname());
            }

            var37.setProcInstId(processInstanceId);
            if (oConvertUtils.isNotEmpty(var27)) {
                var19 = var19 + "       『 " + var27 + " 』";
            }

            var37.setRemarks(var19);
            var37.setTaskDefKey(task.getTaskDefinitionKey());
            var37.setTaskId(taskId);
            var37.setTaskName(task.getName());
            this.extActBpmLogService.save(var37);
            String var21 = oConvertUtils.getString((String)params.get("fileList"));
            this.a(var37.getId(), var21);
        } catch (BpmException var22) {
            var3.error500("任务执行失败:" + var22.getMessage());
            var22.printStackTrace();
        } catch (ActivitiException var23) {
            var3.error500("任务执行失败:" + var23.getMessage());
            var23.printStackTrace();
            throwable = var23.getCause();
            if (throwable != null) {
                throwable1 = (Throwable)throwable;
                if (throwable1.getCause() != null && throwable1.getCause() instanceof BpmException) {
                    var3.error500("任务执行失败:" + throwable1.getCause().getMessage());
                }
            }
        } catch (Exception var24) {
            var3.error500("任务执行失败:" + var24.getMessage());
            var24.printStackTrace();
            throwable = var24.getCause();
            if (throwable != null) {
                throwable1 = (Throwable)throwable;
                if (throwable1.getCause() != null && throwable1.getCause() instanceof BpmException) {
                    var3.error500("任务执行失败:" + throwable1.getCause().getMessage());
                }
            }
        }

        return var3;
    }

    private void a(String var1, String var2) {
        if (oConvertUtils.isNotEmpty(var2)) {
            JSONArray var3 = JSONArray.parseArray(var2);
            if (var3 != null && var3.size() > 0) {
                for(int var4 = 0; var4 < var3.size(); ++var4) {
                    ExtActBpmFile var5 = new ExtActBpmFile();
                    JSONObject var6 = var3.getJSONObject(var4);
                    var5.setBpmLogId(var1);
                    var5.setFileName(var6.getString("fileName"));
                    var5.setFilePath(var6.getString("filePath"));
                    var5.setFileSize(var6.getString("fileSize"));
                    this.extActBpmFileService.save(var5);
                }
            }
        }

    }

    private void a(String var1, Task var2, String var3) {
        if (!oConvertUtils.isEmpty(var1)) {
            String[] var4 = var1.split(",");
            ExtActTaskCc var5 = null;
            String[] var6 = var4;
            int var7 = var4.length;

            for(int var8 = 0; var8 < var7; ++var8) {
                String var9 = var6[var8];
                var5 = new ExtActTaskCc();
                var5.setProcDefId(var2.getProcessDefinitionId());
                var5.setProcInstId(var2.getProcessInstanceId());
                var5.setExecutionId(var2.getExecutionId());
                var5.setTaskDefKey(var2.getTaskDefinitionKey());
                var5.setTaskId(var2.getId());
                var5.setTaskName(var2.getName());
                var5.setFromUserName(var3);
                var5.setCcUserName(var9);
                this.extActTaskCcService.save(var5);
            }

        }
    }

    private boolean b(String var1, String var2) {
        Task var3 = this.activitiService.getTask(var1);
        String var4 = var3.getProcessDefinitionId();
        ProcessDefinition var5 = this.repositoryService.getProcessDefinition(var4);
        InputStream var6 = this.repositoryService.getResourceAsStream(var5.getDeploymentId(), var5.getResourceName());
        String var7 = MyStreamUtils.InputStreamTOString(var6);
        return c.a(var7, var2);
    }

    private boolean c(String var1, String var2) {
        ProcessDefinition var3 = this.repositoryService.getProcessDefinition(var1);
        InputStream var4 = this.repositoryService.getResourceAsStream(var3.getDeploymentId(), var3.getResourceName());
        String var5 = MyStreamUtils.InputStreamTOString(var4);
        return c.b(var5, var2);
    }

    @PutMapping({"/claim"})
    public Result<Map<String, Object>> b(@RequestBody HashMap<String, String> var1, HttpServletRequest var2) {
        Result var3 = new Result();

        try {
            String var4 = JwtUtil.getUserNameByToken(var2);
            String var5 = oConvertUtils.getString((String)var1.get("taskId"));
            this.taskService.claim(var5, var4);
            var3.success("签收成功");
        } catch (Exception var6) {
            var6.printStackTrace();
            var3.error500("签收失败，或者已被他人签收");
        }

        return var3;
    }

    @PutMapping({"/taskEntrust"})
    public Result<Map<String, Object>> c(@RequestBody HashMap<String, String> var1, HttpServletRequest var2) {
        Result var3 = new Result();

        try {
            String var4 = oConvertUtils.getString((String)var1.get("taskId"));
            String var5 = oConvertUtils.getString((String)var1.get("taskAssignee"));
            Task var6 = (Task)((TaskQuery)this.taskService.createTaskQuery().taskId(var4)).active().singleResult();
            if (oConvertUtils.isEmpty(var6.getAssignee())) {
                String var7 = JwtUtil.getUserNameByToken(var2);
                this.taskService.claim(var4, var7);
            }

            this.taskService.delegateTask(var6.getId(), var5);
            var3.success("委托成功");
        } catch (Exception var8) {
            var8.printStackTrace();
            var3.error500("委托失败");
        }

        return var3;
    }

    @GetMapping({"/myApplyProcessList"})
    public Result<IPage<ProcessHisDTO>> c(HttpServletRequest var1) {
        Result var2 = new Result();
        Page var3 = new Page();
        ArrayList var4 = new ArrayList();
        String var5 = var1.getParameter("processDefinitionId");
        String var6 = var1.getParameter("processName");
        String var7 = var1.getParameter("finishedStateQuery");
        String var8 = JwtUtil.getUserNameByToken(var1);
        HistoricProcessInstanceQuery var9 = this.historyService.createHistoricProcessInstanceQuery().startedBy(var8);
        if (oConvertUtils.isNotEmpty(var5)) {
            var9.processDefinitionId(var5);
        }

        if (oConvertUtils.isNotEmpty(var6)) {
            List var10 = this.extActProcessService.getProcessKeysByProcessName(var6);
            var9.processDefinitionKeyIn(var10);
            if (var10 == null || var10.size() == 0) {
                var3.setRecords(var4);
                var3.setTotal(0L);
                var2.setSuccess(true);
                var2.setResult(var3);
                return var2;
            }
        }

        Integer var33 = oConvertUtils.getInt(var1.getParameter("pageNo"), 1);
        Integer var11 = oConvertUtils.getInt(var1.getParameter("pageSize"), 10);
        a.info("pageNo:" + var33 + " ,pageSize:" + var11);
        Integer var12 = (var33 - 1) * var11;
        ProcessHisDTO var13 = null;
        List var14 = null;
        if ("isUnfinished".equals(var7)) {
            var14 = ((HistoricProcessInstanceQuery)var9.unfinished().orderByProcessInstanceStartTime().desc()).listPage(var12, var11);
        } else if ("isFinished".equals(var7)) {
            var14 = ((HistoricProcessInstanceQuery)var9.finished().orderByProcessInstanceStartTime().desc()).listPage(var12, var11);
        } else {
            var14 = ((HistoricProcessInstanceQuery)var9.orderByProcessInstanceStartTime().desc()).listPage(var12, var11);
        }

        Iterator var15 = var14.iterator();

        while(var15.hasNext()) {
            HistoricProcessInstance var16 = (HistoricProcessInstance)var15.next();
            String var17 = DateUtils.formatDate(var16.getStartTime(), "yyyy-MM-dd HH:mm:ss");
            String var18 = var16.getEndTime() == null ? "" : DateUtils.formatDate(var16.getEndTime(), "yyyy-MM-dd HH:mm:ss");
            long var19 = var16.getEndTime() == null ? Calendar.getInstance().getTimeInMillis() - var16.getStartTime().getTime() : var16.getEndTime().getTime() - var16.getStartTime().getTime();
            long var21 = var19 / 86400000L;
            long var23 = var19 % 86400000L;
            long var25 = var23 / 3600000L;
            var23 %= 3600000L;
            long var27 = var23 / 60000L;
            String var29 = var21 + "天" + var25 + "小时" + var27 + "分";
            var13 = new ProcessHisDTO();
            var13.setId(var16.getId());
            var13.setPrcocessDefinitionName(var16.getProcessDefinitionName());
            var13.setStartUserId(var16.getStartUserId());
            if (oConvertUtils.isNotEmpty(var13.getStartUserId())) {
                LoginUser var30 = this.sysBaseAPI.getUserByName(var13.getStartUserId());
                var13.setStartUserName(var30.getRealname());
            }

            var13.setStartTime(var17);
            var13.setEndTime(var18);
            var13.setSpendTimes(var29);
            var13.setProcessDefinitionId(var16.getProcessDefinitionId());
            var13.setProcessInstanceId(var16.getId());
            String var32 = this.activitiService.getHisVarinst(org.jeecg.modules.extbpm.process.common.a.r, var16.getId());
            String var31 = this.activitiService.getHisVarinst(org.jeecg.modules.extbpm.process.common.a.q, var16.getId());
            var31 = oConvertUtils.getString(var31, var16.getDeleteReason());
            if (var31 != null) {
                var31 = var31.replace("发起人", "");
            } else if (oConvertUtils.isNotEmpty(var18)) {
                var31 = "已完成";
            }

            var13.setBpmBizTitle(var32);
            var13.setBpmStatus(var31);
            var4.add(var13);
        }

        Long var34 = var9.count();
        a.info("size：" + var34);
        var3.setRecords(var4);
        var3.setTotal(var34);
        var2.setSuccess(true);
        var2.setResult(var3);
        return var2;
    }

    @GetMapping({"/historyProcessList"})
    public Result<IPage<ProcessHisDTO>> d(HttpServletRequest var1) {
        Result var2 = new Result();
        Page var3 = new Page();
        String var4 = var1.getParameter("processDefinitionId");
        String var5 = var1.getParameter("processName");
        Integer var6 = oConvertUtils.getInt(var1.getParameter("pageNo"), 1);
        Integer var7 = oConvertUtils.getInt(var1.getParameter("pageSize"), 10);
        Integer var8 = (var6 - 1) * var7;
        ArrayList var9 = new ArrayList();
        ProcessHisDTO var10 = null;
        HistoricProcessInstanceQuery var11 = this.historyService.createHistoricProcessInstanceQuery();
        if (oConvertUtils.isNotEmpty(var4)) {
            var11.processDefinitionId(var4);
        }

        if (oConvertUtils.isNotEmpty(var5)) {
            var11.processDefinitionName(var5);
        }

        List var12 = ((HistoricProcessInstanceQuery)var11.orderByProcessInstanceStartTime().desc()).listPage(var8, var7);
        Iterator var13 = var12.iterator();

        while(var13.hasNext()) {
            HistoricProcessInstance var14 = (HistoricProcessInstance)var13.next();
            String var15 = DateUtils.formatDate(var14.getStartTime(), "yyyy-MM-dd HH:mm:ss");
            String var16 = var14.getEndTime() == null ? "" : DateUtils.formatDate(var14.getEndTime(), "yyyy-MM-dd HH:mm:ss");
            long var17 = var14.getEndTime() == null ? Calendar.getInstance().getTimeInMillis() - var14.getStartTime().getTime() : var14.getEndTime().getTime() - var14.getStartTime().getTime();
            long var19 = var17 / 86400000L;
            long var21 = var17 % 86400000L;
            long var23 = var21 / 3600000L;
            var21 %= 3600000L;
            long var25 = var21 / 60000L;
            String var27 = var19 + "天" + var23 + "小时" + var25 + "分";
            var10 = new ProcessHisDTO();
            var10.setId(var14.getId());
            var10.setPrcocessDefinitionName(var14.getProcessDefinitionName());
            var10.setStartUserId(var14.getStartUserId());
            if (oConvertUtils.isNotEmpty(var10.getStartUserId())) {
                LoginUser var28 = this.sysBaseAPI.getUserByName(var10.getStartUserId());
                var10.setStartUserName(var28.getRealname());
            }

            var10.setStartTime(var15);
            var10.setEndTime(var16);
            var10.setSpendTimes(var27);
            var10.setProcessDefinitionId(var14.getProcessDefinitionId());
            var10.setProcessInstanceId(var14.getId());
            String var30 = this.activitiService.getHisVarinst(org.jeecg.modules.extbpm.process.common.a.r, var14.getId());
            String var29 = this.activitiService.getHisVarinst(org.jeecg.modules.extbpm.process.common.a.q, var14.getId());
            var29 = oConvertUtils.getString(var29, var14.getDeleteReason());
            if (var29 != null) {
                var29 = var29.replace("发起人", "");
            }

            var10.setBpmStatus(var29);
            var10.setBpmBizTitle(var30);
            var9.add(var10);
        }

        Long var31 = var11.count();
        a.info("size：" + var31);
        var3.setRecords(var9);
        var3.setTotal(var31);
        var2.setSuccess(true);
        var2.setResult(var3);
        return var2;
    }

    @PutMapping({"/invalidProcess"})
    public Result<Map<String, Object>> d(@RequestBody HashMap<String, String> var1, HttpServletRequest var2) {
        Result var3 = new Result();

        try {
            String var4 = oConvertUtils.getString((String)var1.get("processInstanceId"));
            ProcessInstance var5 = (ProcessInstance)this.runtimeService.createProcessInstanceQuery().processInstanceId(var4).singleResult();
            this.runtimeService.setVariable(var5.getProcessInstanceId(), org.jeecg.modules.extbpm.process.common.a.q, org.jeecg.modules.extbpm.process.common.a.A);
            this.runtimeService.deleteProcessInstance(var4, "发起人流程作废");
            var3.success("作废成功");
        } catch (Exception var6) {
            var6.printStackTrace();
            var3.error500("作废失败");
        }

        return var3;
    }

    @PutMapping({"/invalidBizProcess"})
    public Result<Map<String, Object>> e(@RequestBody HashMap<String, String> var1, HttpServletRequest var2) {
        Result var3 = new Result();

        try {
            String var4 = oConvertUtils.getString((String)var1.get("flowCode"));
            String var5 = oConvertUtils.getString((String)var1.get("dataId"));
            LambdaQueryWrapper<ExtActFlowData> var6 = new LambdaQueryWrapper();
            var6.eq(ExtActFlowData::getRelationCode, var4);
            var6.eq(ExtActFlowData::getFormDataId, var5);
            ExtActFlowData var7 = (ExtActFlowData)this.extActFlowDataService.getOne(var6);
            String var8 = var7.getProcessInstId();
            ProcessInstance var9 = (ProcessInstance)this.runtimeService.createProcessInstanceQuery().processInstanceId(var8).singleResult();
            this.runtimeService.setVariable(var9.getProcessInstanceId(), org.jeecg.modules.extbpm.process.common.a.q, org.jeecg.modules.extbpm.process.common.a.A);
            this.runtimeService.deleteProcessInstance(var8, "发起人流程作废");
            var3.success("作废成功");
        } catch (Exception var10) {
            var10.printStackTrace();
            var3.error500("作废失败");
        }

        return var3;
    }

    @PutMapping({"/callBackProcess"})
    public Result<Map<String, Object>> f(@RequestBody HashMap<String, String> var1, HttpServletRequest var2) {
        Result var3 = new Result();

        try {
            String var4 = oConvertUtils.getString((String)var1.get("processInstanceId"));
            ProcessInstance var5 = (ProcessInstance)this.runtimeService.createProcessInstanceQuery().processInstanceId(var4).singleResult();
            this.runtimeService.setVariable(var5.getProcessInstanceId(), org.jeecg.modules.extbpm.process.common.a.q, org.jeecg.modules.extbpm.process.common.a.z);
            this.runtimeService.deleteProcessInstance(var4, CommonConstant.BPM_REASON_CALLBACK);
            var3.success("追回成功");
        } catch (Exception var6) {
            var6.printStackTrace();
            var3.error500("追回失败");
        }

        return var3;
    }

    @PutMapping({"/callBackBizProcess"})
    public Result<Map<String, Object>> g(@RequestBody HashMap<String, String> var1, HttpServletRequest var2) {
        Result var3 = new Result();

        try {
            String var4 = oConvertUtils.getString((String)var1.get("flowCode"));
            String var5 = oConvertUtils.getString((String)var1.get("dataId"));
            LambdaQueryWrapper<ExtActFlowData> var6 = new LambdaQueryWrapper<>();
            var6.eq(ExtActFlowData::getRelationCode, var4);
            var6.eq(ExtActFlowData::getFormDataId, var5);
            ExtActFlowData var7 = (ExtActFlowData)this.extActFlowDataService.getOne(var6);
            String var8 = var7.getProcessInstId();
            ProcessInstance var9 = (ProcessInstance)this.runtimeService.createProcessInstanceQuery().processInstanceId(var8).singleResult();
            this.runtimeService.setVariable(var9.getProcessInstanceId(), org.jeecg.modules.extbpm.process.common.a.q, org.jeecg.modules.extbpm.process.common.a.z);
            this.runtimeService.deleteProcessInstance(var8, CommonConstant.BPM_REASON_CALLBACK);
            var3.success("追回成功");
        } catch (Exception var10) {
            var10.printStackTrace();
            var3.error500("追回失败");
        }

        return var3;
    }

    @GetMapping({"/taskAllHistoryList"})
    public Result<IPage<TaskDTO>> b(HttpServletRequest var1, @RequestParam(name = "pageNo",defaultValue = "1") Integer var2, @RequestParam(name = "pageSize",defaultValue = "10") Integer var3) {
        Result var4 = new Result();
        Page var5 = new Page((long)var2, (long)var3);
        var5 = this.activitiService.findAllHistoryTasks(var5, var1);
        var4.setSuccess(true);
        var4.setResult(var5);
        return var4;
    }

    @GetMapping({"/taskAllCcHistoryList"})
    public Result<IPage<TaskDTO>> c(HttpServletRequest var1, @RequestParam(name = "pageNo",defaultValue = "1") Integer var2, @RequestParam(name = "pageSize",defaultValue = "10") Integer var3) {
        Result var4 = new Result();
        Page var5 = new Page((long)var2, (long)var3);
        String var6 = JwtUtil.getUserNameByToken(var1);
        var5 = this.activitiService.findAllCcHistoryTasks(var5, var6, var1);
        var4.setSuccess(true);
        var4.setResult(var5);
        return var4;
    }
}
