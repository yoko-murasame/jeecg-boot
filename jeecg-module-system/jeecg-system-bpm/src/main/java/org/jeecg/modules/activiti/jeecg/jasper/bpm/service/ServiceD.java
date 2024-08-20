package org.jeecg.modules.activiti.jeecg.jasper.bpm.service;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.bpm.mapper.ActivitiMapper;
import org.jeecg.modules.bpm.util.c;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service("superTaskService")
public class ServiceD {
    private static final Logger a = LoggerFactory.getLogger(ServiceD.class);
    @Autowired
    protected RepositoryService repositoryService;
    @Autowired
    protected RuntimeService runtimeService;
    @Autowired
    protected TaskService taskService;
    @Autowired
    protected HistoryService historyService;
    @Autowired
    protected ActivitiMapper activitiMapper;

    public ServiceD() {
    }

    public synchronized void a(String var1, String var2, Map<String, Object> var3) throws Exception {
        List var4 = this.f(this.d(var1).getId(), this.e(var1).getTaskDefinitionKey());
        Iterator var5 = var4.iterator();

        while(var5.hasNext()) {
            Task var6 = (Task)var5.next();
            if (var1.equals(var6.getId())) {
                this.a(var6.getId(), var3, var2);
            }
        }

    }

    public void a(String var1, String var2) throws Exception {
        if (StringUtils.isEmpty(var2)) {
            throw new Exception("目标节点ID为空！");
        } else {
            List var3 = this.f(this.d(var1).getId(), this.e(var1).getTaskDefinitionKey());
            Iterator var4 = var3.iterator();

            while(var4.hasNext()) {
                Task var5 = (Task)var4.next();
                this.a(var5.getId(), (Map)null, (String)var2);
            }

        }
    }

    private List<PvmTransition> a(ActivityImpl var1) {
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

    private void a(String var1, Map<String, Object> var2, String var3) throws Exception {
        if (oConvertUtils.isEmpty(var3)) {
            this.taskService.complete(var1, var2);
        } else {
            this.b(var1, var3, var2);
        }

    }

    public void a(String var1) throws Exception {
        ActivityImpl var2 = this.e(var1, "end");
        this.a(var1, (Map)null, (String)var2.getId());
    }

    private ActivityImpl a(ProcessInstance var1, List<ActivityImpl> var2) {
        while(true) {
            if (var2.size() > 0) {
                ActivityImpl var3 = (ActivityImpl)var2.get(0);
                HistoricActivityInstance var4 = this.a(var1, var3.getId());
                if (var4 == null) {
                    var2.remove(var3);
                    continue;
                }

                if (var2.size() > 1) {
                    ActivityImpl var5 = (ActivityImpl)var2.get(1);
                    HistoricActivityInstance var6 = this.a(var1, var5.getId());
                    if (var6 == null) {
                        var2.remove(var5);
                        continue;
                    }

                    if (var4.getEndTime().before(var6.getEndTime())) {
                        var2.remove(var3);
                        continue;
                    }

                    var2.remove(var5);
                    continue;
                }
            }

            if (var2.size() > 0) {
                return (ActivityImpl)var2.get(0);
            }

            return null;
        }
    }

    private ActivityImpl e(String var1, String var2) throws Exception {
        ProcessDefinitionEntity var3 = this.c(var1);
        if (StringUtils.isEmpty(var2)) {
            var2 = this.e(var1).getTaskDefinitionKey();
        }

        if (var2.toUpperCase().equals("END")) {
            Iterator var4 = var3.getActivities().iterator();

            while(var4.hasNext()) {
                ActivityImpl var5 = (ActivityImpl)var4.next();
                List var6 = var5.getOutgoingTransitions();
                if (var6.isEmpty()) {
                    return var5;
                }
            }
        }

        ActivityImpl var7 = var3.findActivity(var2);
        return var7;
    }

    public List<ActivityImpl> b(String var1) throws Exception {
        List var2 = this.a(var1, this.e(var1, (String)null), new ArrayList(), new ArrayList());
        TaskEntity var3 = this.e(var1);
        ProcessDefinitionEntity var4 = (ProcessDefinitionEntity)this.repositoryService.getProcessDefinition(var3.getProcessDefinitionId());
        List var5 = var4.getActivities();

        ActivityImpl var7;
        for(Iterator var6 = var5.iterator(); var6.hasNext(); var7 = (ActivityImpl)var6.next()) {
        }

        a.info("size====" + var2.size());
        return this.a(var2);
    }

    private HistoricActivityInstance a(ProcessInstance var1, String var2) {
        HistoricActivityInstance var3 = null;
        List var4 = ((HistoricActivityInstanceQuery)this.historyService.createHistoricActivityInstanceQuery().activityType("userTask").processInstanceId(var1.getId()).activityId(var2).finished().orderByHistoricActivityInstanceEndTime().desc()).list();
        if (var4.size() > 0) {
            var3 = (HistoricActivityInstance)var4.get(0);
        }

        return var3;
    }

    private String b(ActivityImpl var1) {
        List var2 = var1.getOutgoingTransitions();
        Iterator var3 = var2.iterator();

        while(var3.hasNext()) {
            PvmTransition var4 = (PvmTransition)var3.next();
            TransitionImpl var5 = (TransitionImpl)var4;
            var1 = var5.getDestination();
            String var6 = (String)var1.getProperty("type");
            if ("parallelGateway".equals(var6)) {
                String var7 = var1.getId();
                String var8 = var7.substring(var7.lastIndexOf("_") + 1);
                if ("END".equals(var8.toUpperCase())) {
                    return var7.substring(0, var7.lastIndexOf("_")) + "_start";
                }
            }
        }

        return null;
    }

    public ProcessDefinitionEntity c(String var1) throws Exception {
        ProcessDefinitionEntity var2 = (ProcessDefinitionEntity)((RepositoryServiceImpl)this.repositoryService).getDeployedProcessDefinition(this.e(var1).getProcessDefinitionId());
        if (var2 == null) {
            throw new Exception("流程定义未找到!");
        } else {
            return var2;
        }
    }

    public ProcessInstance d(String var1) throws Exception {
        ProcessInstance var2 = (ProcessInstance)this.runtimeService.createProcessInstanceQuery().processInstanceId(this.e(var1).getProcessInstanceId()).singleResult();
        if (var2 == null) {
            throw new Exception("流程实例未找到!");
        } else {
            return var2;
        }
    }

    private TaskEntity e(String var1) throws Exception {
        TaskEntity var2 = (TaskEntity)((TaskQuery)this.taskService.createTaskQuery().taskId(var1)).singleResult();
        if (var2 == null) {
            throw new Exception("任务实例未找到!");
        } else {
            return var2;
        }
    }

    private List<Task> f(String var1, String var2) {
        return ((TaskQuery)((TaskQuery)this.taskService.createTaskQuery().processInstanceId(var1)).taskDefinitionKey(var2)).list();
    }

    private List<ActivityImpl> a(String var1, ActivityImpl var2, List<ActivityImpl> var3, List<ActivityImpl> var4) throws Exception {
        ProcessInstance var5 = this.d(var1);
        List var6 = var2.getIncomingTransitions();
        ArrayList var7 = new ArrayList();
        ArrayList var8 = new ArrayList();
        Iterator var9 = var6.iterator();

        while(var9.hasNext()) {
            PvmTransition var10 = (PvmTransition)var9.next();
            TransitionImpl var11 = (TransitionImpl)var10;
            ActivityImpl var12 = var11.getSource();
            String var13 = (String)var12.getProperty("type");
            if ("parallelGateway".equals(var13)) {
                String var14 = var12.getId();
                String var15 = var14.substring(var14.lastIndexOf("_") + 1);
                if ("START".equals(var15.toUpperCase())) {
                    return var3;
                }

                var8.add(var12);
            } else {
                if ("startEvent".equals(var13)) {
                    return var3;
                }

                if ("userTask".equals(var13)) {
                    var4.add(var12);
                } else if ("exclusiveGateway".equals(var13)) {
                    var2 = var11.getSource();
                    var7.add(var2);
                }
            }
        }

        var9 = var7.iterator();

        ActivityImpl var17;
        while(var9.hasNext()) {
            var17 = (ActivityImpl)var9.next();
            this.a(var1, var17, var3, var4);
        }

        var9 = var8.iterator();

        while(var9.hasNext()) {
            var17 = (ActivityImpl)var9.next();
            this.a(var1, var17, var3, var4);
        }

        var2 = this.a(var5, var4);
        if (var2 != null) {
            String var16 = this.b(var2);
            if (StringUtils.isEmpty(var16)) {
                var3.add(var2);
            } else {
                var2 = this.e(var1, var16);
            }

            var4.clear();
            this.a(var1, var2, var3, var4);
        }

        return var3;
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

    private List<ActivityImpl> a(List<ActivityImpl> var1) {
        ArrayList var2 = new ArrayList();

        for(int var3 = var1.size(); var3 > 0; --var3) {
            if (!var2.contains(var1.get(var3 - 1))) {
                var2.add(var1.get(var3 - 1));
            }
        }

        return var2;
    }

    public void b(String var1, String var2) {
        this.taskService.setAssignee(var1, var2);
    }

    private void b(String var1, String var2, Map<String, Object> var3) throws Exception {
        ActivityImpl var4 = this.e(var1, (String)null);
        List var5 = this.a(var4);
        TransitionImpl var6 = var4.createOutgoingTransition();
        ActivityImpl var7 = this.e(var1, var2);

        try {
            var6.setDestination(var7);
            Iterator var8 = var5.iterator();

            while(true) {
                if (var8.hasNext()) {
                    PvmTransition var9 = (PvmTransition)var8.next();
                    if (!var2.equals(var9.getDestination().getId())) {
                        continue;
                    }

                    TransitionImpl var10 = (TransitionImpl)var9;
                    if (var10.getExecutionListeners() != null && var10.getExecutionListeners().size() > 0) {
                        var6.setExecutionListeners(var10.getExecutionListeners());
                    }
                }

                this.taskService.complete(var1, var3);
                return;
            }
        } catch (Exception var14) {
            throw var14;
        } finally {
            var7.getIncomingTransitions().remove(var6);
            this.a(var4, var5);
        }
    }

    public String c(String var1, String var2) {
        List var3 = this.activitiMapper.getTaskIdByProins(var1, var2);
        return var3 != null && var3.size() > 0 ? (String)var3.get(0) : null;
    }

    public boolean d(String var1, String var2) {
        Task var3 = (Task)((TaskQuery)this.taskService.createTaskQuery().taskId(var1)).singleResult();
        String var4 = var3.getProcessDefinitionId();
        ProcessDefinition var5 = this.repositoryService.getProcessDefinition(var4);
        InputStream var6 = this.repositoryService.getResourceAsStream(var5.getDeploymentId(), var5.getResourceName());
        String var7 = null;

        try {
            var7 = IOUtils.toString(var6);
        } catch (IOException var9) {
            var9.printStackTrace();
        }

        return c.a(var7, var2);
    }

    static {
        //StringUtil.lastIndexOf();
    }
}
