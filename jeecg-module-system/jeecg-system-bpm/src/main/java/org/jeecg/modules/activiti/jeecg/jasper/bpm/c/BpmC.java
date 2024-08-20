package org.jeecg.modules.activiti.jeecg.jasper.bpm.c;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.DelegationState;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.DateUtils;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.activiti.jeecg.jasper.bpm.service.ServiceD;
import org.jeecg.modules.activiti.jeecg.jasper.extbpm.ExtbpmA;
import org.jeecg.modules.bpm.dto.BatchSuspendInfo;
import org.jeecg.modules.bpm.dto.ProcessInstHisDTO;
import org.jeecg.modules.bpm.dto.SuspendInfo;
import org.jeecg.modules.bpm.service.ActivitiService;
import org.jeecg.modules.extbpm.process.entity.ExtActFlowData;
import org.jeecg.modules.extbpm.process.service.IExtActFlowDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.*;

@RestController("actProcessInstanceController")
@RequestMapping({"/act/processInstance"})
public class BpmC {
    private static final Logger a = LoggerFactory.getLogger(BpmC.class);
    private final String b = ExtbpmA.f("startUserId");
    private final String c = ExtbpmA.f("processInstanceId");
    @Autowired
    protected RepositoryService repositoryService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private ActivitiService activitiService;
    @Autowired
    private ServiceD superTaskService;
    @Autowired
    private IExtActFlowDataService extActFlowDataService;
    @Autowired
    private ISysBaseAPI sysBaseAPI;

    public BpmC() {
    }

    @GetMapping({"/list"})
    public Result<IPage<ProcessInstHisDTO>> a(HttpServletRequest var1) {
        Result var2 = new Result();
        Page var3 = new Page();
        HistoricProcessInstanceQuery var4 = this.historyService.createHistoricProcessInstanceQuery();
        if (ExtbpmA.b(var1.getParameter(this.b))) {
            var4 = var4.startedBy(var1.getParameter(this.b));
        }

        if (ExtbpmA.b(var1.getParameter(this.c))) {
            var4 = var4.processInstanceId(var1.getParameter(this.c));
        }

        String var5 = var1.getParameter("startTime_begin");
        String var6 = var1.getParameter("startTime_end");
        if (ExtbpmA.b(var5)) {
            try {
                var4.startedAfter(DateUtils.parseDate(var5, "yyyy-MM-dd"));
            } catch (ParseException var49) {
                var49.printStackTrace();
            }
        }

        if (ExtbpmA.b(var6)) {
            try {
                var4.startedBefore(DateUtils.parseDate(var6, "yyyy-MM-dd"));
            } catch (ParseException var48) {
                var48.printStackTrace();
            }
        }

        String var7 = var1.getParameter("endTime_begin");
        String var8 = var1.getParameter("endTime_end");
        if (ExtbpmA.b(var7)) {
            try {
                var4.finishedAfter(DateUtils.parseDate(var7, "yyyy-MM-dd"));
            } catch (ParseException var47) {
                var47.printStackTrace();
            }
        }

        if (ExtbpmA.b(var8)) {
            try {
                var4.finishedBefore(DateUtils.parseDate(var8, "yyyy-MM-dd"));
            } catch (ParseException var46) {
                var46.printStackTrace();
            }
        }

        Integer var9 = oConvertUtils.getInt(var1.getParameter("pageNo"), 1);
        Integer var10 = oConvertUtils.getInt(var1.getParameter("pageSize"), 10);
        Integer var11 = (var9 - 1) * var10;
        List var12 = ((HistoricProcessInstanceQuery)var4.unfinished().orderByProcessInstanceStartTime().desc()).listPage(var11, var10);
        long var13 = var4.unfinished().count();
        ArrayList var15 = new ArrayList();
        ProcessInstHisDTO var16 = null;
        Iterator var17 = var12.iterator();

        while(true) {
            while(true) {
                HistoricProcessInstance var18;
                String var19;
                String var20;
                String var31;
                ProcessDefinition var32;
                String var33;
                String var34;
                String var35;
                String var36;
                do {
                    if (!var17.hasNext()) {
                        var3.setRecords(var15);
                        a.debug(" count  : " + var13);
                        var3.setTotal(var13);
                        var2.setSuccess(true);
                        var2.setResult(var3);
                        return var2;
                    }

                    var18 = (HistoricProcessInstance)var17.next();
                    var19 = DateFormatUtils.format(var18.getStartTime(), "yyyy-MM-dd HH:mm:ss");
                    var20 = var18.getEndTime() == null ? "" : DateFormatUtils.format(var18.getEndTime(), "yyyy-MM-dd HH:mm:ss");
                    long var21 = var18.getEndTime() == null ? Calendar.getInstance().getTimeInMillis() - var18.getStartTime().getTime() : var18.getEndTime().getTime() - var18.getStartTime().getTime();
                    long var23 = var21 / 86400000L;
                    long var25 = var21 % 86400000L;
                    long var27 = var25 / 3600000L;
                    var25 %= 3600000L;
                    long var29 = var25 / 60000L;
                    var31 = var23 + "天" + var27 + "小时" + var29 + "分";
                    var32 = (ProcessDefinition)this.repositoryService.createProcessDefinitionQuery().processDefinitionId(var18.getProcessDefinitionId()).singleResult();
                    a.debug("spendTimes：" + var31);
                    var33 = "finished";
                    var34 = "";
                    var35 = "";
                    var36 = "";
                } while(var18.getEndTime() != null);

                String var37 = this.activitiService.getHisVarinst(org.jeecg.modules.extbpm.process.common.a.r, var18.getId());
                var37 = oConvertUtils.isEmpty(var37) ? var32.getName() : var37;
                ProcessInstance var38 = (ProcessInstance)this.runtimeService.createProcessInstanceQuery().processInstanceId(var18.getId()).singleResult();
                List var39 = ((TaskQuery)this.taskService.createTaskQuery().processInstanceId(var18.getId())).list();
                LoginUser var42;
                if (var39.size() > 1) {
                    var33 = "" + var38.isSuspended();
                    var34 = ExtbpmA.h(((Task)var39.get(0)).getName());
                    var35 = ExtbpmA.h(((Task)var39.get(0)).getAssignee());
                    var36 = oConvertUtils.getString(((Task)var39.get(0)).getId());
                    var16 = new ProcessInstHisDTO();
                    var16.setId(var18.getId());
                    var16.setBpmBizTitle(var37);
                    var16.setParentId("");
                    var16.setTaskId(var36);
                    var16.setName(var34);
                    var16.setAssignee("");
                    var16.setPrcocessDefinitionName(var32.getName());
                    var16.setStartUserId(var18.getStartUserId());
                    if (oConvertUtils.isNotEmpty(var16.getStartUserId())) {
                        LoginUser var51 = this.sysBaseAPI.getUserByName(var16.getStartUserId());
                        var16.setStartUserName(var51.getRealname());
                    }

                    var16.setStartTime(var19);
                    var16.setEndTime(var20);
                    var16.setSpendTimes(var31);
                    var16.setIsSuspended(var33);
                    var16.setProcessDefinitionId(var18.getProcessDefinitionId());
                    var16.setProcessInstanceId(var18.getId());
                    int var52 = 1;
                    ArrayList var53 = new ArrayList();
                    var42 = null;
                    Iterator var43 = var39.iterator();

                    while(var43.hasNext()) {
                        Task var44 = (Task)var43.next();
                        var33 = "" + var38.isSuspended();
                        var34 = ExtbpmA.h(var44.getName());
                        var35 = ExtbpmA.h(var44.getAssignee());
                        var36 = oConvertUtils.getString(var44.getId());
                        ProcessInstHisDTO var50 = new ProcessInstHisDTO();
                        var50.setId(var18.getId() + ":" + var52++);
                        var50.setBpmBizTitle(var37);
                        var50.setParentId(var18.getId());
                        var50.setTaskId(var36);
                        var50.setName(var34);
                        var50.setAssignee(var35);
                        LoginUser var45;
                        if (oConvertUtils.isNotEmpty(var50.getAssignee())) {
                            var45 = this.sysBaseAPI.getUserByName(var50.getAssignee());
                            var50.setAssigneeName(var45 != null ? var45.getRealname() : var50.getAssignee());
                        }

                        var50.setPrcocessDefinitionName(var32.getName());
                        var50.setStartUserId(var18.getStartUserId());
                        if (oConvertUtils.isNotEmpty(var50.getStartUserId())) {
                            var45 = this.sysBaseAPI.getUserByName(var50.getStartUserId());
                            var50.setStartUserName(var45.getRealname());
                        }

                        var50.setStartTime(var19);
                        var50.setEndTime(var20);
                        var50.setSpendTimes(var31);
                        var50.setIsSuspended(var33);
                        var50.setProcessDefinitionId(var18.getProcessDefinitionId());
                        var50.setProcessInstanceId(var18.getId());
                        var53.add(var50);
                    }

                    var16.setChildren(var53);
                    var15.add(var16);
                } else {
                    Iterator var40 = var39.iterator();

                    while(var40.hasNext()) {
                        Task var41 = (Task)var40.next();
                        var33 = "" + var38.isSuspended();
                        var34 = ExtbpmA.h(var41.getName());
                        var35 = ExtbpmA.h(var41.getAssignee());
                        var36 = oConvertUtils.getString(var41.getId());
                        var16 = new ProcessInstHisDTO();
                        var16.setId(var18.getId());
                        var16.setBpmBizTitle(var37);
                        var16.setParentId("");
                        var16.setTaskId(var36);
                        var16.setName(var34);
                        var16.setAssignee(var35);
                        if (oConvertUtils.isNotEmpty(var16.getAssignee())) {
                            var42 = this.sysBaseAPI.getUserByName(var16.getAssignee());
                            var16.setAssigneeName(var42 != null ? var42.getRealname() : var16.getAssignee());
                        }

                        var16.setPrcocessDefinitionName(var32.getName());
                        var16.setStartUserId(var18.getStartUserId());
                        if (oConvertUtils.isNotEmpty(var16.getStartUserId())) {
                            var42 = this.sysBaseAPI.getUserByName(var16.getStartUserId());
                            var16.setStartUserName(var42 != null ? var42.getRealname() : var16.getStartUserId());
                        }

                        var16.setStartTime(var19);
                        var16.setEndTime(var20);
                        var16.setSpendTimes(var31);
                        var16.setIsSuspended(var33);
                        var16.setProcessDefinitionId(var18.getProcessDefinitionId());
                        var16.setProcessInstanceId(var18.getId());
                        var15.add(var16);
                    }
                }
            }
        }
    }

    @GetMapping({"reassign"})
    public Result a(@RequestParam("taskId") String var1, @RequestParam("userName") String var2, HttpServletRequest var3) {
        Task var4 = this.activitiService.getTask(var1);
        if (oConvertUtils.isNotEmpty(var2) && var2.endsWith(",")) {
            var2 = var2.substring(0, var2.length() - 1);
        }

        LoginUser var5 = (LoginUser)SecurityUtils.getSubject().getPrincipal();
        this.taskService.setOwner(var4.getId(), var5.getUsername());
        this.taskService.setAssignee(var4.getId(), var2);
        return Result.ok("委派成功!");
    }

    @GetMapping({"suspend"})
    public Result a(@RequestParam("processInstanceId") String var1, HttpServletRequest var2) {
        this.activitiService.suspend(var1);
        return Result.ok("挂起成功!");
    }

    @GetMapping({"restart"})
    public Result b(@RequestParam("processInstanceId") String var1, HttpServletRequest var2) {
        this.activitiService.restart(var1);
        return Result.ok("启动成功!");
    }

    @PostMapping({"batchSuspend"})
    public Result<BatchSuspendInfo> a(@RequestBody BatchSuspendInfo var1, HttpServletRequest var2) {
        Result var3 = new Result();
        List var4 = var1.getData();
        if (var4 != null && var4.size() > 0) {
            int var5 = 0;
            Iterator var6 = var4.iterator();

            while(var6.hasNext()) {
                SuspendInfo var7 = (SuspendInfo)var6.next();

                try {
                    LambdaQueryWrapper<ExtActFlowData> var8 = new LambdaQueryWrapper<>();
                    var8.eq(ExtActFlowData::getFormDataId, var7.getDataId());
                    var8.eq(ExtActFlowData::getRelationCode, var7.getFlowCode());
                    ExtActFlowData var9 = (ExtActFlowData)this.extActFlowDataService.getOne(var8);
                    if (var9 != null) {
                        var7.setProcInstId(var9.getProcessInstId());
                        this.activitiService.suspend(var9.getProcessInstId());
                        var7.setStatus("1");
                        ++var5;
                        continue;
                    }
                } catch (Exception var10) {
                    a.error(var10.getMessage(), var10);
                }

                var7.setStatus("0");
            }

            var1.setData(var4);
            var1.setSucessCount(var5);
            var3.setResult(var1);
            var3.success("处理完成！");
            return var3;
        } else {
            var3.error500("请选择数据");
            return var3;
        }
    }

    @PostMapping({"batchRestart"})
    public Result<BatchSuspendInfo> b(@RequestBody BatchSuspendInfo var1, HttpServletRequest var2) {
        Result var3 = new Result();
        List var4 = var1.getData();
        if (var4 != null && var4.size() > 0) {
            int var5 = 0;
            Iterator var6 = var4.iterator();

            while(var6.hasNext()) {
                SuspendInfo var7 = (SuspendInfo)var6.next();

                try {
                    LambdaQueryWrapper<ExtActFlowData> var8 = new LambdaQueryWrapper<>();
                    var8.eq(ExtActFlowData::getFormDataId, var7.getDataId());
                    var8.eq(ExtActFlowData::getRelationCode, var7.getFlowCode());
                    ExtActFlowData var9 = (ExtActFlowData)this.extActFlowDataService.getOne(var8);
                    if (var9 != null) {
                        var7.setProcInstId(var9.getProcessInstId());
                        this.activitiService.restart(var9.getProcessInstId());
                        var7.setStatus("1");
                        ++var5;
                        continue;
                    }
                } catch (Exception var10) {
                    a.error(var10.getMessage(), var10);
                }

                var7.setStatus("0");
            }

            var1.setData(var4);
            var1.setSucessCount(var5);
            var3.setResult(var1);
            var3.success("处理完成！");
            return var3;
        } else {
            var3.error500("请选择数据");
            return var3;
        }
    }

    @GetMapping({"close"})
    public Result c(@RequestParam("processInstanceId") String var1, HttpServletRequest var2) {
        this.runtimeService.deleteProcessInstance(var1, "主动作废流程");
        return Result.ok("作废成功!");
    }

    @GetMapping({"skipNode"})
    public Result b(@RequestParam("taskId") String var1, @RequestParam("skipTaskNode") String var2, HttpServletRequest var3) throws Exception {
        Task var4 = (Task)((TaskQuery)this.taskService.createTaskQuery().taskId(var1)).active().singleResult();
        if (StringUtils.isNotBlank(var4.getOwner())) {
            DelegationState var5 = var4.getDelegationState();
            switch(var5) {
                case PENDING:
                    this.taskService.resolveTask(var1);
                case RESOLVED:
            }
        }

        if ("end".equals(var2)) {
            this.superTaskService.a(var1);
        } else {
            this.superTaskService.a(var1, var2);
        }

        return Result.ok("跳转成功!");
    }

    @GetMapping({"getAllTask"})
    public Result d(@RequestParam("taskId") String var1, HttpServletRequest var2) throws Exception {
        List var3 = this.activitiService.getAllTaskNode(var1);
        ArrayList var4 = new ArrayList();
        Iterator var5 = var3.iterator();

        while(var5.hasNext()) {
            Map var6 = (Map)var5.next();
            String var7 = (String)var6.get("taskKey");
            if (var7 != null && !var7.startsWith("exclusiveGateway") && !var7.startsWith("parallelGateway") && !var7.startsWith("callSubProcess") && !var7.startsWith("end") && !var7.startsWith("start1")) {
                var4.add(var6);
            }
        }

        return Result.ok(var4);
    }
}
