//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jeecg.modules.extbpm.process.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.bpm.service.ActivitiService;
import org.jeecg.modules.extbpm.process.entity.ExtActFlowData;
import org.jeecg.modules.extbpm.process.entity.ExtActProcessNode;
import org.jeecg.modules.extbpm.process.entity.ExtActProcessNodeDeployment;
import org.jeecg.modules.extbpm.process.entity.ExtActProcessNodePermission;
import org.jeecg.modules.extbpm.process.pojo.BizTaskDTO;
import org.jeecg.modules.extbpm.process.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController("extActProcessNodeController")
@RequestMapping({"/act/process/extActProcessNode"})
public class ExtActProcessNodeController {
    private static final Logger a = LoggerFactory.getLogger(ExtActProcessNodeController.class);
    @Autowired
    private IExtActProcessNodeService extActProcessNodeService;
    @Autowired
    private IExtActProcessFormService extActProcessFormService;
    @Autowired
    private ActivitiService activitiService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    protected RepositoryService repositoryService;
    @Autowired
    private IExtActProcessNodeDeploymentService extActProcessNodeDeploymentService;
    @Autowired
    private IExtActProcessNodePermissionService extActProcessNodePermissionService;
    @Autowired
    private IExtActFlowDataService extActFlowDataService;

    @GetMapping({"/list"})
    public Result<IPage<ExtActProcessNode>> a(ExtActProcessNode var1, @RequestParam(name = "pageNo",defaultValue = "1") Integer var2, @RequestParam(name = "pageSize",defaultValue = "10") Integer var3, HttpServletRequest var4) {
        Result var5 = new Result();
        QueryWrapper var6 = new QueryWrapper(var1);
        Page var7 = new Page((long)var2, (long)var3);
        var6.orderByAsc("process_node_code");
        IPage var8 = this.extActProcessNodeService.page(var7, var6);
        var5.setSuccess(true);
        var5.setResult(var8);
        return var5;
    }

    @PostMapping({"/add"})
    public Result<ExtActProcessNode> a(@RequestBody ExtActProcessNode var1) {
        Result var2 = new Result();

        try {
            this.extActProcessNodeService.save(var1);
            var2.success("添加成功！");
        } catch (Exception var4) {
            a.error(var4.getMessage(), var4);
            var2.error500("操作失败");
        }

        return var2;
    }

    @PutMapping({"/edit"})
    public Result<?> b(@RequestBody ExtActProcessNode var1) {
        new Result();
        this.extActProcessNodeService.updateById(var1);
        return Result.ok("编辑成功");
    }

    @DeleteMapping({"/delete"})
    public Result<ExtActProcessNode> a(@RequestParam(name = "id",required = true) String var1) {
        Result var2 = new Result();
        ExtActProcessNode var3 = (ExtActProcessNode)this.extActProcessNodeService.getById(var1);
        if (var3 == null) {
            var2.error500("未找到对应实体");
        } else {
            boolean var4 = this.extActProcessNodeService.removeById(var1);
            if (var4) {
                var2.success("删除成功!");
            }
        }

        return var2;
    }

    @DeleteMapping({"/deleteBatch"})
    public Result<ExtActProcessNode> b(@RequestParam(name = "ids",required = true) String var1) {
        Result var2 = new Result();
        if (var1 != null && !"".equals(var1.trim())) {
            this.extActProcessNodeService.removeByIds(Arrays.asList(var1.split(",")));
            var2.success("删除成功!");
        } else {
            var2.error500("参数不识别！");
        }

        return var2;
    }

    @GetMapping({"/queryById"})
    public Result<ExtActProcessNode> c(@RequestParam(name = "id",required = true) String var1) {
        Result var2 = new Result();
        ExtActProcessNode var3 = (ExtActProcessNode)this.extActProcessNodeService.getById(var1);
        if (var3 == null) {
            var2.error500("未找到对应实体");
        } else {
            var2.setResult(var3);
            var2.setSuccess(true);
        }

        return var2;
    }

    @GetMapping({"/getProcessNodeInfo"})
    public Result<Map<String, Object>> d(@RequestParam(name = "taskId",required = true) String taskId) {
        Result<Map<String, Object>> result = new Result<>();
        Task task = this.activitiService.getTask(taskId);
        String processInstanceId = task.getProcessInstanceId();
        Map<String, Object> variables = this.runtimeService.getVariables(processInstanceId);
        String BPM_FORM_KEY = (String)variables.get(org.jeecg.modules.extbpm.process.common.a.h);
        String BPM_DATA_ID = (String)variables.get(org.jeecg.modules.extbpm.process.common.a.l);
        if (oConvertUtils.isEmpty(BPM_DATA_ID)) {
            BPM_DATA_ID = (String)variables.get("id");
            this.runtimeService.setVariable(processInstanceId, org.jeecg.modules.extbpm.process.common.a.l, BPM_DATA_ID);
        }

        String processDefinitionId = task.getProcessDefinitionId();
        ProcessDefinition processDefinition = (ProcessDefinition)this.repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
        variables.put("BPM_TASK_KEY", task.getTaskDefinitionKey());
        variables.put("BPM_TASK_ID", taskId);
        variables.put("BPM_INST_ID", processInstanceId);
        variables.put("BPM_PROC_DEF_ID", processDefinitionId);
        String BPM_FORM_CONTENT_URL = (String)variables.get(org.jeecg.modules.extbpm.process.common.a.o);
        String BPM_FORM_CONTENT_URL_MOBILE = (String)variables.get(org.jeecg.modules.extbpm.process.common.a.p);
        String BPM_FORM_TYPE = (String)variables.get(org.jeecg.modules.extbpm.process.common.a.t);
        // 如果组件类型是KForm，默认给KForm的组件
        if (Objects.equals(BPM_FORM_TYPE, CommonConstant.DESIGN_FORM_TYPE_SUB + "")) {
            BPM_FORM_CONTENT_URL = CommonConstant.KFORM_DESIGN_MODULE;
        }

        LambdaQueryWrapper<ExtActProcessNodeDeployment> deploymentLambdaQueryWrapper;
        ExtActProcessNodeDeployment deployment;
        if (oConvertUtils.isEmpty(BPM_FORM_CONTENT_URL) || oConvertUtils.isEmpty(BPM_FORM_CONTENT_URL_MOBILE)) {
            deploymentLambdaQueryWrapper = new LambdaQueryWrapper<>();
            deploymentLambdaQueryWrapper.eq(ExtActProcessNodeDeployment::getProcessNodeCode, org.jeecg.modules.extbpm.process.common.a.i);
            deploymentLambdaQueryWrapper.eq(ExtActProcessNodeDeployment::getDeploymentId, processDefinition.getDeploymentId());
            deployment = (ExtActProcessNodeDeployment)this.extActProcessNodeDeploymentService.getOne(deploymentLambdaQueryWrapper);
            if (deployment != null && oConvertUtils.isNotEmpty(deployment.getModelAndView())) {
                BPM_FORM_CONTENT_URL = org.jeecg.modules.bpm.util.a.a(variables, deployment.getModelAndView(), BPM_DATA_ID);
                this.runtimeService.setVariable(processInstanceId, org.jeecg.modules.extbpm.process.common.a.o, BPM_FORM_CONTENT_URL);
            }

            if (deployment != null && oConvertUtils.isNotEmpty(deployment.getModelAndViewMobile())) {
                BPM_FORM_CONTENT_URL_MOBILE = org.jeecg.modules.bpm.util.a.a(variables, deployment.getModelAndViewMobile(), BPM_DATA_ID);
                this.runtimeService.setVariable(processInstanceId, org.jeecg.modules.extbpm.process.common.a.p, BPM_FORM_CONTENT_URL_MOBILE);
            }
        }

        deploymentLambdaQueryWrapper = new LambdaQueryWrapper<>();
        deploymentLambdaQueryWrapper.eq(ExtActProcessNodeDeployment::getProcessNodeCode, task.getTaskDefinitionKey());
        deploymentLambdaQueryWrapper.eq(ExtActProcessNodeDeployment::getDeploymentId, processDefinition.getDeploymentId());
        deployment = (ExtActProcessNodeDeployment)this.extActProcessNodeDeploymentService.getOne(deploymentLambdaQueryWrapper);
        if (deployment != null && oConvertUtils.isNotEmpty(deployment.getModelAndView())) {
            BPM_FORM_CONTENT_URL = org.jeecg.modules.bpm.util.a.a(variables, deployment.getModelAndView(), BPM_DATA_ID);
        }

        if (deployment != null && oConvertUtils.isNotEmpty(deployment.getModelAndViewMobile())) {
            BPM_FORM_CONTENT_URL_MOBILE = org.jeecg.modules.bpm.util.a.a(variables, deployment.getModelAndViewMobile(), BPM_DATA_ID);
        }

        ArrayList<Map<String, String>> permissionList = new ArrayList<>();
        if (deployment != null) {
            LambdaQueryWrapper<ExtActProcessNodePermission> permissionLambdaQueryWrapper = new LambdaQueryWrapper<>();
            permissionLambdaQueryWrapper.eq(ExtActProcessNodePermission::getProcessId, deployment.getProcessId());
            permissionLambdaQueryWrapper.eq(ExtActProcessNodePermission::getProcessNodeCode, deployment.getProcessNodeCode());
            permissionLambdaQueryWrapper.eq(ExtActProcessNodePermission::getStatus, "1");
            permissionLambdaQueryWrapper.eq(ExtActProcessNodePermission::getFormType, org.jeecg.modules.extbpm.process.common.a.w);
            if (oConvertUtils.isNotEmpty(BPM_FORM_KEY)) {
                permissionLambdaQueryWrapper.eq(ExtActProcessNodePermission::getFormBizCode, BPM_FORM_KEY);
            }

            List<ExtActProcessNodePermission> extActProcessNodePermissions = this.extActProcessNodePermissionService.list(permissionLambdaQueryWrapper);
            for (ExtActProcessNodePermission extActProcessNodePermission : extActProcessNodePermissions) {
                Map<String, String> perm = new HashMap<>();
                perm.put("action", extActProcessNodePermission.getRuleCode());
                perm.put("type", extActProcessNodePermission.getRuleType());
                permissionList.add(perm);
            }
        }

        HashMap<String, Object> resMap = new HashMap<>();
        resMap.put("formUrl", BPM_FORM_CONTENT_URL);
        resMap.put("formUrlMobile", BPM_FORM_CONTENT_URL_MOBILE);
        resMap.put("taskId", taskId);
        resMap.put("taskDefKey", task.getTaskDefinitionKey());
        resMap.put("dataId", BPM_DATA_ID);
        resMap.put("tableName", BPM_FORM_KEY);
        resMap.put("permissionList", permissionList);
        resMap.put("records", variables);
        // 设置online相关扩展
        if (deployment != null) {
            resMap.put("modelAndViewType", deployment.getModelAndViewType());
            resMap.put("showTask", deployment.getShowTask());
            resMap.put("showProcess", deployment.getShowProcess());
            resMap.put("onlineCode", deployment.getOnlineCode());
            try {
                resMap.put("onlineFormConfig", JSON.parse(deployment.getOnlineFormConfig()));
            } catch (Exception e) {
                resMap.put("onlineFormConfig", new HashMap<String, Object>());
                a.warn("onlineFormConfig解析JSON失败: {}", deployment.getOnlineFormConfig());
            }
            resMap.put("onlineInitQueryParamGetter", deployment.getOnlineInitQueryParamGetter());
            resMap.put("showReject", deployment.getShowReject());
            resMap.put("customTaskModule", deployment.getCustomTaskModule());
            resMap.put("showMessageHandle", deployment.getShowMessageHandle());
        }

        result.setResult(resMap);
        result.setSuccess(true);
        return result;
    }

    @GetMapping({"/getHisProcessNodeInfo"})
    public Result<Map<String, Object>> e(@RequestParam(name = "procInstId",required = true) String var1) {
        Result var2 = new Result();
        String var3 = this.activitiService.getHisVarinst(org.jeecg.modules.extbpm.process.common.a.o, var1);
        String var4 = this.activitiService.getHisVarinst(org.jeecg.modules.extbpm.process.common.a.p, var1);
        String var5 = this.activitiService.getHisVarinst(org.jeecg.modules.extbpm.process.common.a.l, var1);
        String var6 = this.activitiService.getHisVarinst(org.jeecg.modules.extbpm.process.common.a.h, var1);
        HashMap var7 = new HashMap();
        var7.put(org.jeecg.modules.extbpm.process.common.a.l, var5);
        var7.put(org.jeecg.modules.extbpm.process.common.a.h, var6);
        HashMap var8 = new HashMap();
        var8.put("formUrl", var3);
        var8.put("formUrlMobile", var4);
        var8.put("taskId", "");
        var8.put("taskDefKey", "");
        var8.put("dataId", var5);
        var8.put("tableName", var6);
        var8.put("permissionList", new ArrayList());
        var8.put("records", var7);
        var2.setResult(var8);
        var2.setSuccess(true);
        return var2;
    }

    @GetMapping({"/getBizProcessNodeInfo"})
    public Result<Map<String, Object>> a(@RequestParam(name = "flowCode",required = true) String var1, @RequestParam(name = "dataId",required = true) String var2, HttpServletRequest var3) {
        Result var4 = new Result();
        LambdaQueryWrapper<ExtActFlowData> var5 = new LambdaQueryWrapper();
        var5.eq(ExtActFlowData::getRelationCode, var1);
        var5.eq(ExtActFlowData::getFormDataId, var2);
        ExtActFlowData var6 = (ExtActFlowData)this.extActFlowDataService.getOne(var5);
        if (var6 == null) {
            var4.error500("未找到对应实体");
        } else {
            String var7 = var6.getProcessInstId();
            Map var8 = this.runtimeService.getVariables(var7);
            String var9 = var6.getFormDataId();
            ProcessInstance var10 = (ProcessInstance)this.runtimeService.createProcessInstanceQuery().processInstanceId(var7).singleResult();
            String var11 = JwtUtil.getUserNameByToken(var3);
            String var12 = "";
            List var13 = ((TaskQuery)((TaskQuery)this.taskService.createTaskQuery().processInstanceId(var6.getProcessInstId())).taskAssignee(var11)).list();
            List var14 = ((TaskQuery)((TaskQuery)this.taskService.createTaskQuery().processInstanceId(var6.getProcessInstId())).taskCandidateUser(var11)).list();
            List var15 = this.a(var13, var14, var10);
            BizTaskDTO var16 = (BizTaskDTO)var15.get(0);
            var12 = var16.getTaskId();
            String var17 = var10.getProcessDefinitionId();
            var8.put("BPM_TASK_ID", var12);
            var8.put("BPM_INST_ID", var12);
            var8.put("BPM_PROC_DEF_ID", var17);
            String var18 = (String)var8.get(org.jeecg.modules.extbpm.process.common.a.o);
            if (oConvertUtils.isEmpty(var18)) {
                LambdaQueryWrapper<ExtActProcessNodeDeployment> var19 = new LambdaQueryWrapper();
                var19.eq(ExtActProcessNodeDeployment::getProcessNodeCode, org.jeecg.modules.extbpm.process.common.a.i);
                var19.eq(ExtActProcessNodeDeployment::getDeploymentId, var10.getDeploymentId());
                ExtActProcessNodeDeployment var20 = (ExtActProcessNodeDeployment)this.extActProcessNodeDeploymentService.getOne(var19);
                if (var20 != null && oConvertUtils.isNotEmpty(var20.getModelAndView())) {
                    String var21 = org.jeecg.modules.bpm.util.a.a(var8, var20.getModelAndView(), var9);
                    var18 = var21;
                    this.runtimeService.setVariable(var7, org.jeecg.modules.extbpm.process.common.a.o, var21);
                }
            }

            String var30 = "";
            LambdaQueryWrapper<ExtActProcessNodeDeployment> var31 = new LambdaQueryWrapper();
            var31.eq(ExtActProcessNodeDeployment::getProcessNodeCode, var16.getTaskId());
            var31.eq(ExtActProcessNodeDeployment::getDeploymentId, var10.getDeploymentId());
            ExtActProcessNodeDeployment var28 = (ExtActProcessNodeDeployment)this.extActProcessNodeDeploymentService.getOne(var31);
            if (var28 != null && oConvertUtils.isNotEmpty(var28.getModelAndView())) {
                var30 = var28.getModelAndView();
                var30 = org.jeecg.modules.bpm.util.a.a(var8, var30, var9);
                var18 = var30;
            }

            ArrayList var22 = new ArrayList();
            if (var28 != null) {
                LambdaQueryWrapper<ExtActProcessNodePermission> var23 = new LambdaQueryWrapper();
                var23.eq(ExtActProcessNodePermission::getProcessId, var28.getProcessId());
                var23.eq(ExtActProcessNodePermission::getProcessNodeCode, var28.getProcessNodeCode());
                var23.eq(ExtActProcessNodePermission::getStatus, "1");
                List var24 = this.extActProcessNodePermissionService.list(var23);
                HashMap var25 = null;
                Iterator var26 = var24.iterator();

                while(var26.hasNext()) {
                    ExtActProcessNodePermission var27 = (ExtActProcessNodePermission)var26.next();
                    var25 = new HashMap();
                    var25.put("action", var27.getRuleCode());
                    var25.put("type", var27.getRuleType());
                    var22.add(var25);
                }
            }

            HashMap var29 = new HashMap();
            var29.put("formUrl", var18);
            var29.put("taskId", var16.getId());
            var29.put("taskDefKey", var16.getTaskId());
            var29.put("dataId", var9);
            var29.put("procInsId", var6.getProcessInstId());
            var29.put("tableName", var6.getFormTableName());
            var29.put("permissionList", var22);
            var29.put("bizTaskList", var15);
            var29.put("records", var8);
            var4.setResult(var29);
            var4.setSuccess(true);
        }

        return var4;
    }

    private List<BizTaskDTO> a(List<Task> var1, List<Task> var2, ProcessInstance var3) {
        ArrayList var4 = new ArrayList();
        BizTaskDTO var5 = null;
        Iterator var6 = var1.iterator();

        Task var7;
        while(var6.hasNext()) {
            var7 = (Task)var6.next();
            var5 = new BizTaskDTO();
            var5.setId(var7.getId());
            var5.setTaskId(var7.getTaskDefinitionKey());
            var5.setTaskName(var7.getName());
            var5.setTaskClaimFlag(false);
            var5.setProcDefId(var7.getProcessDefinitionId());
            var5.setSuspendFlag(var3.isSuspended());
            var5.setProcInstId(var3.getProcessInstanceId());
            var4.add(var5);
        }

        var6 = var2.iterator();

        while(var6.hasNext()) {
            var7 = (Task)var6.next();
            var5 = new BizTaskDTO();
            var5.setId(var7.getId());
            var5.setTaskId(var7.getTaskDefinitionKey());
            var5.setTaskName(var7.getName());
            if (oConvertUtils.isEmpty(var7.getAssignee())) {
                var5.setTaskClaimFlag(true);
            } else {
                var5.setTaskClaimFlag(false);
            }

            var5.setProcDefId(var7.getProcessDefinitionId());
            var5.setSuspendFlag(var3.isSuspended());
            var5.setProcInstId(var3.getProcessInstanceId());
            var4.add(var5);
        }

        return var4;
    }

    @GetMapping({"/getBizHisProcessNodeInfo"})
    public Result<Map<String, Object>> b(@RequestParam(name = "flowCode",required = true) String var1, @RequestParam(name = "dataId",required = true) String var2, HttpServletRequest var3) {
        Result var4 = new Result();
        LambdaQueryWrapper<ExtActFlowData> var5 = new LambdaQueryWrapper();
        var5.eq(ExtActFlowData::getRelationCode, var1);
        var5.eq(ExtActFlowData::getFormDataId, var2);
        ExtActFlowData var6 = (ExtActFlowData)this.extActFlowDataService.getOne(var5);
        String var7 = this.activitiService.getHisVarinst(org.jeecg.modules.extbpm.process.common.a.o, var6.getProcessInstId());
        String var9 = var6.getFormTableName();
        HashMap var10 = new HashMap();
        var10.put(org.jeecg.modules.extbpm.process.common.a.l, var2);
        var10.put(org.jeecg.modules.extbpm.process.common.a.h, var9);
        HashMap var11 = new HashMap();
        var11.put("formUrl", var7);
        var11.put("taskId", "");
        var11.put("taskDefKey", "");
        var11.put("dataId", var2);
        var11.put("procInsId", var6.getProcessInstId());
        var11.put("tableName", var9);
        var11.put("permissionList", new ArrayList());
        var11.put("bizTaskList", new ArrayList());
        var11.put("records", var10);
        var4.setResult(var11);
        var4.setSuccess(true);
        return var4;
    }
}
