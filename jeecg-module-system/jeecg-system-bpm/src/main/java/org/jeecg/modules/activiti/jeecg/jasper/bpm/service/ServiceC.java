package org.jeecg.modules.activiti.jeecg.jasper.bpm.service;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.NativeTaskQuery;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.CommonUtils;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.activiti.jeecg.jasper.extbpm.ExtbpmA;
import org.jeecg.modules.activiti.jeecg.jasper.extbpm.ExtbpmE;
import org.jeecg.modules.bpm.dto.ActHiActinstDTO;
import org.jeecg.modules.bpm.dto.TaskDTO;
import org.jeecg.modules.bpm.dto.UserAgentDTO;
import org.jeecg.modules.bpm.mapper.ActivitiMapper;
import org.jeecg.modules.bpm.service.ActivitiService;
import org.jeecg.modules.extbpm.a.d;
import org.jeecg.modules.extbpm.process.entity.ExtActFlowData;
import org.jeecg.modules.extbpm.process.entity.ExtActTaskNotification;
import org.jeecg.modules.extbpm.process.mapper.ExtActFlowDataMapper;
import org.jeecg.modules.extbpm.process.mapper.ExtActProcessMapper;
import org.jeecg.modules.extbpm.process.mapper.ExtActTaskNotificationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

//SN码改造
@Service("activitiService")
public class ServiceC implements ActivitiService {
    private static final Logger a = LoggerFactory.getLogger(ServiceC.class);
    private static final String b = ExtbpmA.f("applyUserId");
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private ActivitiMapper activitiMapper;
    @Autowired
    private ISysBaseAPI sysBaseAPI;
    @Autowired
    private ExtActTaskNotificationMapper extActTaskNotificationMapper;
    @Autowired
    private ExtActFlowDataMapper extActFlowDataMapper;
    @Autowired
    private ExtActProcessMapper extActProcessMapper;
    private static String[] c;

    public ServiceC() {
    }

    public List<TaskDTO> findPriTodoTasks(String userId, HttpServletRequest request) {
        try {
            return this.a(true, userId, request);
        } catch (Exception var4) {
            var4.printStackTrace();
            return null;
        }
    }

    public List<TaskDTO> findGroupTodoTasks(List<String> roles, HttpServletRequest request) {
        try {
            StringBuilder var3 = new StringBuilder();
            Iterator var4 = roles.iterator();

            while(var4.hasNext()) {
                String var5 = (String)var4.next();
                var3.append(var5).append(",");
            }

            var3.deleteCharAt(var3.length() - 1);
            List var7 = this.a(false, var3.toString(), request);
            return var7;
        } catch (Exception var6) {
            var6.printStackTrace();
            return null;
        }
    }

    private List<TaskDTO> a(boolean var1, String var2, HttpServletRequest var3) throws Exception {
        ArrayList var4 = new ArrayList();
        Integer var5 = oConvertUtils.getInt(var3.getParameter("pageNo"), 1);
        Integer var6 = oConvertUtils.getInt(var3.getParameter("pageSize"), 10);
        Integer var7 = (var5 - 1) * var6;
        Integer var8 = var5 * var6 - 1;
        ArrayList var9 = new ArrayList();
        String var15;
        String var18;
        String var26;
        if (var1) {
            String var11 = var3.getParameter("userName");
            StringBuilder var12 = new StringBuilder("");
            if (ExtbpmA.b(var11)) {
                List var13 = this.runtimeService.createProcessInstanceQuery().variableValueEquals(b, var11).list();
                if (var13 != null && var13.size() > 0) {
                    for(int var14 = 0; var14 < var13.size(); ++var14) {
                        if (var14 == 0) {
                            var12.append("'" + ((ProcessInstance)var13.get(var14)).getProcessInstanceId() + "'");
                        } else {
                            var12.append(",'" + ((ProcessInstance)var13.get(var14)).getProcessInstanceId() + "'");
                        }
                    }
                }
            }

            String var24 = var12.toString();
            var26 = var3.getParameter("processDefinitionId");
            var15 = var3.getParameter("processDefinitionName");
            StringBuilder var16 = new StringBuilder("");
            var16.append("select  * ").append("from (");
            var16.append("(select distinct RES.* ");
            var16.append(" from ACT_RU_TASK RES inner join ACT_RU_IDENTITYLINK I on I.TASK_ID_ = RES.ID_ ");
            var16.append(" INNER JOIN ACT_RE_PROCDEF ARP ON ARP.ID_ = RES.PROC_DEF_ID_ ");
            var16.append("WHERE RES.ASSIGNEE_ is null and I.TYPE_ = 'candidate' ");
            var16.append("\tand ( I.USER_ID_ = #{userid}  or I.GROUP_ID_ IN ( select g.GROUP_ID_ from ACT_ID_MEMBERSHIP g where g.USER_ID_ = #{userid}  ) ");
            var16.append(" ) ").append(" and RES.SUSPENSION_STATE_ = 1 ");
            if (ExtbpmA.b(var26)) {
                var16.append("  AND RES.PROC_DEF_ID_ LIKE #{procDefId} ");
            }

            if (ExtbpmA.b(var15)) {
                var16.append("  AND ARP.NAME_  LIKE #{procDefName} ");
            }

            if (ExtbpmA.b(var11)) {
                if (ExtbpmA.b(var24)) {
                    var16.append("  AND RES.PROC_INST_ID_ in (" + var24 + ") ");
                } else {
                    var16.append("  AND RES.PROC_INST_ID_ in ('-1') ");
                }
            }

            var16.append(") union ");
            var16.append("(select distinct RES.* ");
            var16.append(" from ACT_RU_TASK RES ");
            var16.append(" INNER JOIN ACT_RE_PROCDEF ARP ON ARP.ID_ = RES.PROC_DEF_ID_ ");
            var16.append("WHERE RES.ASSIGNEE_ = #{userid} ");
            if (ExtbpmA.b(var26)) {
                var16.append("  AND RES.PROC_DEF_ID_ LIKE #{procDefId} ");
            }

            if (ExtbpmA.b(var15)) {
                var16.append("  AND ARP.NAME_  LIKE #{procDefName} ");
            }

            if (ExtbpmA.b(var11)) {
                if (ExtbpmA.b(var24)) {
                    var16.append("  AND RES.PROC_INST_ID_ in (" + var24 + ") ");
                } else {
                    var16.append("  AND RES.PROC_INST_ID_ in ('-1') ");
                }
            }

            var16.append(" )) v ");
            var16.append(" order by v.CREATE_TIME_ desc, v.PRIORITY_ desc ");
            String var17 = CommonUtils.getDatabaseType();
            var18 = org.jeecg.modules.bpm.util.b.a(var17, var16.toString(), var5, var6);
            a.debug("我的任务:" + var18);
            NativeTaskQuery var19 = (NativeTaskQuery)((NativeTaskQuery)this.taskService.createNativeTaskQuery().sql(var18)).parameter("userid", var2);
            if (ExtbpmA.b(var26)) {
                var19.parameter("procDefId", "%" + var26 + "%");
            }

            if (ExtbpmA.b(var15)) {
                var19.parameter("procDefName", "%" + var15 + "%");
            }

            List var20 = var19.list();
            var9.addAll(var20);
        } else {
            TaskQuery var21 = (TaskQuery)((TaskQuery)((TaskQuery)((TaskQuery)((TaskQuery)this.taskService.createTaskQuery().taskCandidateGroupIn(Arrays.asList(var2.split(",")))).orderByTaskCreateTime()).desc()).orderByTaskPriority()).desc();
            var21 = this.a(var21, var3);
            List var10 = var21.listPage(var7, var8);
            var9.addAll(var10);
        }

        TaskDTO var25;
        for(Iterator var22 = var9.iterator(); var22.hasNext(); var4.add(var25)) {
            Task var23 = (Task)var22.next();
            var25 = new TaskDTO();
            var26 = var23.getAssignee() == null ? "" : var23.getAssignee();
            if (ExtbpmA.b(var26)) {
                LoginUser var27 = this.sysBaseAPI.getUserByName(var26);
                var25.setTaskAssigneeName(var27 != null ? var27.getRealname() : "");
            }

            try {
                var15 = ((HistoricProcessInstance)this.historyService.createHistoricProcessInstanceQuery().processInstanceId(var23.getProcessInstanceId()).singleResult()).getStartUserId();
            }catch (Exception e){
                e.printStackTrace();
                continue;
            }

            if (ExtbpmA.b(var15)) {
                LoginUser var28 = this.sysBaseAPI.getUserByName(var15);
                var25.setProcessApplyUserName(var28 != null ? var28.getRealname() : "");
            }

            String var29 = var23.getProcessInstanceId();
            ProcessInstance var30 = (ProcessInstance)this.runtimeService.createProcessInstanceQuery().processInstanceId(var29).singleResult();
            var25.setId(var23.getId());
            var25.setTaskAssigneeId(var26);
            var25.setTaskBeginTime(var23.getCreateTime());
            var25.setTaskName(var23.getName());
            var25.setTaskId(var23.getTaskDefinitionKey());
            var25.setTaskEndTime(var23.getDueDate());
            var25.setProcessInstanceId(var23.getProcessInstanceId());
            var25.setProcessApplyUserId(var15);
            var25.setProcessDefinitionId(var30.getProcessDefinitionId());
            var25.setProcessDefinitionName(var30.getProcessDefinitionName());
            var18 = (String)this.taskService.getVariable(var23.getId(), org.jeecg.modules.extbpm.process.common.a.r);
            if (var18 != null) {
                var25.setBpmBizTitle(var18);
            }

            var25.setTaskUrge(false);
            if (ExtbpmA.b(var25.getTaskAssigneeId()) && ExtbpmA.b(var25.getProcessInstanceId()) && ExtbpmA.b(var25.getId())) {
                LambdaQueryWrapper<ExtActTaskNotification> var31 = new LambdaQueryWrapper<>();
                var31.eq(ExtActTaskNotification::getProcInstId, var25.getProcessInstanceId());
                var31.eq(ExtActTaskNotification::getTaskId, var25.getId());
                var31.eq(ExtActTaskNotification::getTaskAssignee, var25.getTaskAssigneeId());
                Long var32 = this.extActTaskNotificationMapper.selectCount(var31);
                if (var32 > 0) {
                    var25.setTaskUrge(true);
                }
            }
        }

        return var4;
    }

    public Long countPriTodaoTask(String userid, HttpServletRequest request) {
        Long var3 = 0L;
        String var4 = request.getParameter("userName");
        StringBuilder var5 = new StringBuilder("");
        if (ExtbpmA.b(var4)) {
            List var6 = this.runtimeService.createProcessInstanceQuery().variableValueEquals(b, var4).list();
            if (var6 != null && var6.size() > 0) {
                for(int var7 = 0; var7 < var6.size(); ++var7) {
                    if (var7 == 0) {
                        var5.append("'" + ((ProcessInstance)var6.get(var7)).getProcessInstanceId() + "'");
                    } else {
                        var5.append(",'" + ((ProcessInstance)var6.get(var7)).getProcessInstanceId() + "'");
                    }
                }
            }
        }

        String var10 = var5.toString();
        String var11 = request.getParameter("processDefinitionId");
        StringBuilder var8 = new StringBuilder("");
        var8.append("select  count(*) ").append("from (");
        var8.append("(select distinct RES.* ").append("from ACT_RU_TASK RES inner join ACT_RU_IDENTITYLINK I on I.TASK_ID_ = RES.ID_ ");
        var8.append("WHERE RES.ASSIGNEE_ is null and I.TYPE_ = 'candidate' ");
        var8.append("\tand ( I.USER_ID_ = #{userid}  or I.GROUP_ID_ IN ( select g.GROUP_ID_ from ACT_ID_MEMBERSHIP g where g.USER_ID_ = #{userid}  ) ");
        var8.append(" ) ").append(" and RES.SUSPENSION_STATE_ = 1 ");
        if (ExtbpmA.b(var11)) {
            var8.append("  AND RES.PROC_DEF_ID_ LIKE #{procDefId} ");
        }

        if (ExtbpmA.b(var4)) {
            if (ExtbpmA.b(var10)) {
                var8.append("  AND RES.PROC_INST_ID_ in (" + var10 + ") ");
            } else {
                var8.append("  AND RES.PROC_INST_ID_ in ('-1') ");
            }
        }

        var8.append(") union ");
        var8.append("(select distinct RES.* ").append("from ACT_RU_TASK RES ");
        var8.append("WHERE RES.ASSIGNEE_ = #{userid} ");
        if (ExtbpmA.b(var11)) {
            var8.append("  AND RES.PROC_DEF_ID_ LIKE #{procDefId} ");
        }

        if (ExtbpmA.b(var4)) {
            if (ExtbpmA.b(var10)) {
                var8.append("  AND RES.PROC_INST_ID_ in (" + var10 + ") ");
            } else {
                var8.append("  AND RES.PROC_INST_ID_ in ('-1') ");
            }
        }

        var8.append(" )) v ");
        a.debug("我的任务count:" + var8.toString());
        NativeTaskQuery var9 = (NativeTaskQuery)((NativeTaskQuery)this.taskService.createNativeTaskQuery().sql(var8.toString())).parameter("userid", userid);
        if (ExtbpmA.b(var11)) {
            var9.parameter("procDefId", "%" + var11 + "%");
        }

        var3 = var9.count();
        return var3;
    }

    public Long countGroupTodoTasks(List<String> roles, HttpServletRequest request) {
        Long var3 = 0L;
        TaskQuery var4 = (TaskQuery)((TaskQuery)((TaskQuery)((TaskQuery)((TaskQuery)this.taskService.createTaskQuery().taskCandidateGroupIn(roles)).orderByTaskPriority()).desc()).orderByTaskCreateTime()).desc();
        this.a(var4, request);
        var3 = var4.count();
        return var3;
    }

    private String a(Task var1) {
        String var2 = "";
        TaskEntity var3 = (TaskEntity)((TaskQuery)this.taskService.createTaskQuery().taskId(var1.getId())).singleResult();
        HistoricProcessInstance var4 = (HistoricProcessInstance)this.historyService.createHistoricProcessInstanceQuery().processInstanceId(var3.getProcessInstanceId()).singleResult();
        if (var4 != null) {
            if (var4.getSuperProcessInstanceId() != null && var4.getBusinessKey() == null) {
                var4 = (HistoricProcessInstance)this.historyService.createHistoricProcessInstanceQuery().processInstanceId(var4.getSuperProcessInstanceId()).singleResult();
                var2 = var4.getBusinessKey();
            } else {
                var2 = var4.getBusinessKey();
            }
        }

        return var2;
    }

    private TaskQuery a(TaskQuery var1, HttpServletRequest var2) {
        String var3 = var2.getParameter("processDefinitionId");
        String var4 = var2.getParameter("processDefinitionName");
        if (ExtbpmA.b(var3)) {
            var1 = (TaskQuery)var1.processDefinitionId(var3);
        }

        if (ExtbpmA.b(var4)) {
            var1 = (TaskQuery)var1.processDefinitionNameLike(var4);
        }

        return var1;
    }

    public Task getTask(String taskId) {
        return (Task)((TaskQuery)this.taskService.createTaskQuery().taskId(taskId)).singleResult();
    }

    public List<Map> getAllTaskNode(String taskId) {
        ArrayList var2 = new ArrayList();
        Task var3 = this.getTask(taskId);
        ProcessDefinitionEntity var4 = (ProcessDefinitionEntity)((RepositoryServiceImpl)this.repositoryService).getDeployedProcessDefinition(var3.getProcessDefinitionId());
        List var5 = var4.getActivities();
        Iterator var6 = var5.iterator();

        while(var6.hasNext()) {
            ActivityImpl var7 = (ActivityImpl)var6.next();
            HashMap var8 = new HashMap();
            String var9 = var7.getId();
            var8.put("taskKey", var9);
            String var10 = (String)var7.getProperty("name");
            var8.put("name", var10);
            var2.add(var8);
        }

        return var2;
    }

    public List<ProcessDefinition> processDefinitionListByProcesskey(String processkey) {
        return this.repositoryService.createProcessDefinitionQuery().processDefinitionKey(processkey).list();
    }

    public Page<TaskDTO> findHistoryTasks(Page page, String userName, HttpServletRequest request) {
        String var4 = request.getParameter("processDefinitionId");
        String var5 = request.getParameter("processDefinitionName");
        List var6 = this.activitiMapper.getHistoryTasks(page, userName, var4, var5);
        Iterator var7 = var6.iterator();

        while(var7.hasNext()) {
            TaskDTO var8 = (TaskDTO)var7.next();
            var8.setBpmBizTitle(this.getHisVarinst(org.jeecg.modules.extbpm.process.common.a.r, var8.getProcessInstanceId()));
            String var9 = var8.getTaskAssigneeId();
            if (ExtbpmA.b(var9)) {
                LoginUser var10 = this.sysBaseAPI.getUserByName(var9);
                var8.setTaskAssigneeName(var10.getRealname());
            }

            String var12 = var8.getProcessApplyUserId();
            if (ExtbpmA.b(var12)) {
                LoginUser var11 = this.sysBaseAPI.getUserByName(var12);
                var8.setProcessApplyUserName(var11.getRealname());
            }
        }

        return page.setRecords(var6);
    }

    public Page<TaskDTO> findAllHistoryTasks(Page page, HttpServletRequest request) {
        String var3 = request.getParameter("processDefinitionId");
        String var4 = request.getParameter("processDefinitionName");
        List var5 = this.activitiMapper.getAllHistoryTasks(page, var3, var4);
        Iterator var6 = var5.iterator();

        while(var6.hasNext()) {
            TaskDTO var7 = (TaskDTO)var6.next();
            var7.setBpmBizTitle(this.getHisVarinst(org.jeecg.modules.extbpm.process.common.a.r, var7.getProcessInstanceId()));
            String var8 = var7.getTaskAssigneeId();
            if (ExtbpmA.b(var8)) {
                LoginUser var9 = this.sysBaseAPI.getUserByName(var8);
                var7.setTaskAssigneeName(var9.getRealname());
            }

            String var11 = var7.getProcessApplyUserId();
            if (ExtbpmA.b(var11)) {
                LoginUser var10 = this.sysBaseAPI.getUserByName(var11);
                var7.setProcessApplyUserName(var10.getRealname());
            }
        }

        return page.setRecords(var5);
    }

    public Page<TaskDTO> findAllCcHistoryTasks(Page page, String username, HttpServletRequest request) {
        String var4 = request.getParameter("processDefinitionId");
        String var5 = request.getParameter("processDefinitionName");
        List var6 = this.activitiMapper.getAllCcHistoryTasks(page, username, var4, var5);
        Iterator var7 = var6.iterator();

        while(var7.hasNext()) {
            TaskDTO var8 = (TaskDTO)var7.next();
            var8.setBpmBizTitle(this.getHisVarinst(org.jeecg.modules.extbpm.process.common.a.r, var8.getProcessInstanceId()));
            String var9 = var8.getTaskAssigneeId();
            if (ExtbpmA.b(var9)) {
                LoginUser var10 = this.sysBaseAPI.getUserByName(var9);
                var8.setTaskAssigneeName(var10.getRealname());
            }

            String var12 = var8.getProcessApplyUserId();
            if (ExtbpmA.b(var12)) {
                LoginUser var11 = this.sysBaseAPI.getUserByName(var12);
                var8.setProcessApplyUserName(var11.getRealname());
            }
        }

        return page.setRecords(var6);
    }

    public List getOutTransitions(String taskId) {
        List var2 = null;
        ArrayList var3 = new ArrayList();
        Task var4 = this.getTask(taskId);
        ProcessDefinitionEntity var5 = (ProcessDefinitionEntity)((RepositoryServiceImpl)this.repositoryService).getDeployedProcessDefinition(var4.getProcessDefinitionId());
        List var6 = var5.getActivities();
        String var7 = var4.getExecutionId();
        ExecutionEntity var8 = (ExecutionEntity)this.runtimeService.createExecutionQuery().executionId(var7).singleResult();
        String var9 = var8.getActivityId();
        Iterator var10 = var6.iterator();

        while(var10.hasNext()) {
            ActivityImpl var11 = (ActivityImpl)var10.next();
            String var12 = var11.getId();
            if (var9.equals(var12)) {
                var2 = var11.getOutgoingTransitions();
                Iterator var13 = var2.iterator();

                while(var13.hasNext()) {
                    PvmTransition var14 = (PvmTransition)var13.next();
                    if (var14.getId() != null) {
                        HashMap var15 = new HashMap();
                        String var16 = (String)((String)(ExtbpmA.a(var14.getProperty("name")) ? var14.getProperty("name") : var14.getId()));
                        var15.put("Transition", var16);
                        PvmActivity var17 = var14.getDestination();
                        var15.put("nextnode", var17.getId());
                        var3.add(var15);
                    }
                }

                return var3;
            }
        }

        return var3;
    }

    public List<Map<String, Object>> getHistTaskNodeList(String proceInsId) {
        return this.activitiMapper.getHistTaskNodeList(proceInsId);
    }

    public List<ActHiActinstDTO> getActHiActinstStartAndEnd(String proceInsId) {
        return this.activitiMapper.getActHiActinstStartAndEnd(proceInsId);
    }

    public String getProcessStartUserId(String proceInsId) {
        return this.activitiMapper.getProcessStartUserId(proceInsId);
    }

    public List<Map<String, Object>> getProcessEndUserId(String proceInsId) {
        return this.activitiMapper.getProcessEndUserId(proceInsId);
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    public synchronized void goProcessTaskNode(String taskId, String nextTaskId, Map<String, Object> variables) throws Exception {
        List<Task> tasks = this.getTaskByInstIdAndDefKey(this.getProcessInstanceByTaskId(taskId).getId(), this.getTaskEntityById(taskId).getTaskDefinitionKey());
        for (Task task : tasks) {
            if (taskId.equals(task.getId())) {
                this.completeTask(task.getId(), variables, nextTaskId);
            }
        }
    }

    private List<Task> getTaskByInstIdAndDefKey(String instId, String defKey) {
        return ((TaskQuery)((TaskQuery)this.taskService.createTaskQuery().processInstanceId(instId)).taskDefinitionKey(defKey)).list();
    }

    private void completeTask(String taskId, Map<String, Object> variables, String nextNodeId) throws Exception {
        if (oConvertUtils.isEmpty(nextNodeId)) {
            this.taskService.complete(taskId, variables);
        } else {
            this.completeTaskWithNextNodeId(taskId, nextNodeId, variables);
        }
    }

    public ProcessInstance getProcessInstanceByTaskId(String taskId) throws Exception {
        ProcessInstance processInstance = (ProcessInstance)this.runtimeService.createProcessInstanceQuery().processInstanceId(this.getTaskEntityById(taskId).getProcessInstanceId()).singleResult();
        if (processInstance == null) {
            throw new Exception("流程实例未找到!");
        } else {
            return processInstance;
        }
    }

    private void completeTaskWithNextNodeId(String taskId, String nextNodeId, Map<String, Object> variables) throws Exception {
        ActivityImpl activityImpl = this.getActivityImpl(taskId, (String)null);
        List<PvmTransition> pvmTransitions = this.getPvmTransitions(activityImpl);
        TransitionImpl transition = activityImpl.createOutgoingTransition();
        ActivityImpl impl = this.getActivityImpl(taskId, nextNodeId);

        try {
            transition.setDestination(impl);
            Iterator var8 = pvmTransitions.iterator();

            while(true) {
                if (var8.hasNext()) {
                    PvmTransition var9 = (PvmTransition)var8.next();
                    if (!nextNodeId.equals(var9.getDestination().getId())) {
                        continue;
                    }

                    TransitionImpl var10 = (TransitionImpl)var9;
                    if (var10.getExecutionListeners() != null && var10.getExecutionListeners().size() > 0) {
                        transition.setExecutionListeners(var10.getExecutionListeners());
                    }
                }

                this.taskService.complete(taskId, variables);
                return;
            }
        } catch (Exception var14) {
            throw var14;
        } finally {
            impl.getIncomingTransitions().remove(transition);
            this.a(activityImpl, pvmTransitions);
        }
    }

    private ActivityImpl getActivityImpl(String taskId, String var2) throws Exception {
        ProcessDefinitionEntity var3 = this.b(taskId);
        if (oConvertUtils.isEmpty(var2)) {
            var2 = this.getTaskEntityById(taskId).getTaskDefinitionKey();
        }

        if (var2.toUpperCase().equals("END")) {

            for (ActivityImpl var5 : var3.getActivities()) {
                List var6 = var5.getOutgoingTransitions();
                if (var6.isEmpty()) {
                    return var5;
                }
            }
        }

        ActivityImpl var7 = var3.findActivity(var2);
        return var7;
    }

    public ProcessDefinitionEntity b(String var1) throws Exception {
        ProcessDefinitionEntity var2 = (ProcessDefinitionEntity)((RepositoryServiceImpl)this.repositoryService).getDeployedProcessDefinition(this.getTaskEntityById(var1).getProcessDefinitionId());
        if (var2 == null) {
            throw new Exception("流程定义未找到!");
        } else {
            return var2;
        }
    }

    private TaskEntity getTaskEntityById(String taskId) throws Exception {
        TaskEntity taskEntity = (TaskEntity)((TaskQuery)this.taskService.createTaskQuery().taskId(taskId)).singleResult();
        if (taskEntity == null) {
            throw new Exception("任务实例未找到!");
        } else {
            return taskEntity;
        }
    }

    private List<PvmTransition> getPvmTransitions(ActivityImpl var1) {
        ArrayList var2 = new ArrayList();
        List var3 = var1.getOutgoingTransitions();
        Iterator var4 = var3.iterator();

        while(var4.hasNext()) {
            PvmTransition var5 = (PvmTransition)var4.next();
            var2.add(var5);
        }

        var3.clear();
        return var2;
    }

    public static void a() {
        /*if(false){
            try {
                if (c == null || c.length == 0) {
                    ResourceBundle var0 = ExtbpmA.a();
                    if (var0 == null) {
                        var0 = ResourceBundle.getBundle(d.d());
                    }

                    if (MyStreamUtils.isr()) {
                        c = new String[]{StringUtil.dl()};
                    } else {
                        c = var0.getString(d.f()).split(",");
                    }
                }

                if (!ExtbpmA.b(c, ExtbpmE.b()) && !ExtbpmA.b(c, ExtbpmE.a())) {
                    System.out.println(d.h() + ExtbpmE.c());
                    String var5 = d.j();
                    System.err.println(var5);
                    //System.exit(0);
                }
            } catch (Exception var4) {
                var4.printStackTrace();
                try {
                    System.out.println(d.h() + ExtbpmE.c());
                    String var1 = d.j();
                    System.err.println(var1);
                    //System.exit(0);
                } catch (Exception var3) {
                }
            }
        }
*/
    }

    private void a(ActivityImpl var1, List<PvmTransition> var2) {
        List var3 = var1.getOutgoingTransitions();
        var3.clear();
        Iterator var4 = var2.iterator();

        while(var4.hasNext()) {
            PvmTransition var5 = (PvmTransition)var4.next();
            var3.add(var5);
        }

    }

    public String getTaskIdByProins(String proInsId, String taskDefKey) {
        List var3 = this.activitiMapper.getTaskIdByProins(proInsId, taskDefKey);
        return var3 != null && var3.size() > 0 ? (String)var3.get(0) : null;
    }

    public String getHisVarinst(String varName, String proInsId) {
        return this.activitiMapper.getHisVarinst(varName, proInsId);
    }

    public List<UserAgentDTO> getUserAgent(Date currDate) {
        return this.activitiMapper.getUserAgent(currDate);
    }

    public void updateBpmStatus(String procInstId, String bpmStatus) {
        LambdaQueryWrapper<ExtActFlowData> var3 = new LambdaQueryWrapper<>();
        var3.eq(ExtActFlowData::getProcessInstId, procInstId);
        ExtActFlowData var4 = (ExtActFlowData)this.extActFlowDataMapper.selectOne(var3);
        if (var4 != null) {
            String var5 = var4.getFormTableName().toUpperCase();
            String var6 = var4.getBpmStatusField().toUpperCase();
            if (oConvertUtils.isEmpty(var6)) {
                var6 = "BPM_STATUS";
            }

            this.extActProcessMapper.updateBpmStatusById(var5, var4.getFormDataId(), var6, bpmStatus);
            var4.setBpmStatus(bpmStatus);
            this.extActFlowDataMapper.updateById(var4);
        }

    }

    @Transactional
    public void suspend(String procInstId) {
        this.runtimeService.suspendProcessInstanceById(procInstId);
        this.updateBpmStatus(procInstId, org.jeecg.modules.extbpm.process.common.a.g);
    }

    @Transactional
    public void restart(String procInstId) {
        this.runtimeService.activateProcessInstanceById(procInstId);
        this.updateBpmStatus(procInstId, org.jeecg.modules.extbpm.process.common.a.d);
    }

    static {
        a();
        if(false){
            long var0 = 2885760000L;
            Runnable var2 = new Runnable() {
                public void run() {
                    while(true) {
                        try {
                            Thread.sleep(2885760000L);
                            String var1 = "";
                            Object var2 = null;

                            try {
                                String var4 = System.getProperty("user.dir") + File.separator + "config" + File.separator + d.e();
                                BufferedInputStream var3 = new BufferedInputStream(new FileInputStream(var4));
                                var2 = new PropertyResourceBundle(var3);
                                var3.close();
                            } catch (IOException var6) {
                            }

                            if (var2 == null) {
                                var2 = ResourceBundle.getBundle(d.d());
                            }

                            String var8 = ((ResourceBundle)var2).getString(d.g());
                            byte[] var9 = d.a(d.i(), var8);
                            var8 = new String(var9, "UTF-8");
                            String[] var5 = var8.split("\\|");
                            if (var8.contains("--")) {
                                Thread.sleep(787968000000L);
                                return;
                            }

                            if (!var5[1].equals(ExtbpmE.c())) {
                                System.out.println(d.h() + ExtbpmE.c());
                                System.err.println(org.jeecg.modules.extbpm.a.c.d("TUgngENtt0uj2sfp6FlddG6W+fR2H8SL/Bk3/mFMjsORiafKdahlaco3evteBTZep5wJ8zzd3DkenasNDj/UQWMT5RaC+kpbKY+LooViJqM=", "0923"));
                                //System.exit(0);
                            }
                        } catch (Exception var7) {
                            System.err.println(d.h() + ExtbpmE.c());
                            System.err.println(org.jeecg.modules.extbpm.a.c.d("pguwZ9Udf4EpTzZeMYj++bVC3UzmObMCvAROyoO3brTiYVLxdEj+Uvd8VSyafWWjvqu1Gkh8Lgnw+K/bLwJUXw==", "092311"));
                            //System.exit(0);
                        }
                    }
                }
            };
            Thread var3 = new Thread(var2);
            var3.start();
        }
        c = null;
    }
}
