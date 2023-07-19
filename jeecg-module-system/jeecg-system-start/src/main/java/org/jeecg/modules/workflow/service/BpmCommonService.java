package org.jeecg.modules.workflow.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.NativeTaskQuery;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.io.IOUtils;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.CommonUtils;
import org.jeecg.common.util.DateUtils;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.activiti.jeecg.jasper.extbpm.service.ExtActProcessService;
import org.jeecg.modules.bpm.d.a.k;
import org.jeecg.modules.bpm.dto.ActHiActinstDTO;
import org.jeecg.modules.bpm.dto.ProcessHisDTO;
import org.jeecg.modules.bpm.service.ActivitiService;
import org.jeecg.modules.extbpm.process.entity.*;
import org.jeecg.modules.extbpm.process.exception.BpmException;
import org.jeecg.modules.extbpm.process.mapper.ExtActProcessMapper;
import org.jeecg.modules.extbpm.process.mapper.ExtActTaskNotificationMapper;
import org.jeecg.modules.extbpm.process.service.IExtActBpmFileService;
import org.jeecg.modules.extbpm.process.service.IExtActBpmLogService;
import org.jeecg.modules.extbpm.process.service.IExtActFlowDataService;
import org.jeecg.modules.extbpm.process.service.IExtActProcessFormService;
import org.jeecg.modules.online.desform.entity.DesignFormData;
import org.jeecg.modules.online.desform.service.IDesignFormDataService;
import org.jeecg.modules.workflow.entity.TaskDTO;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 自定义流程功能通用实现
 *
 * @author Yoko
 * @since 2022/7/21 11:04
 */
@Slf4j
@Service
public class BpmCommonService {

    private static final String APPLY_USER_ID = "applyUserId";

    @Resource
    private ActivitiService activitiService;
    @Resource
    private ISysBaseAPI sysBaseAPI;
    @Resource
    private HistoryService historyService;
    @Resource
    private IExtActBpmFileService extActBpmFileService;
    @Resource
    private IExtActBpmLogService extActBpmLogService;
    @Resource
    private IExtActFlowDataService extActFlowDataService;
    @Resource
    protected RepositoryService repositoryService;
    @Resource
    private RuntimeService runtimeService;
    @Resource
    private TaskService taskService;
    @Resource
    private ExtActTaskNotificationMapper extActTaskNotificationMapper;
    @Resource
    private IExtActProcessFormService extActProcessFormService;
    @Resource
    private ExtActProcessService extActProcessService;
    @Resource
    private IDesignFormDataService designFormDataService;

    /**
     * 获取流程定义id
     *
     * @author Yoko
     * @since 2023/7/19 11:16
     * @param param
     * 建议传入两个参数
     * 1.relationCode
     * 2.formTableName
     * @return org.jeecg.common.api.vo.Result<?>
     */
    public Result<ExtActProcess> getExtActProcess(ExtActProcessForm param) {
        QueryWrapper<ExtActProcessForm> qw = QueryGenerator.initQueryWrapper(param, new HashMap<>());
        List<ExtActProcessForm> list = extActProcessFormService.list(qw);
        if (list.size() > 1) {
            throw new RuntimeException("存在多个匹配结果，请缩小范围！");
        }
        ExtActProcessForm extActProcessForm = list.get(0);
        String processId = extActProcessForm.getProcessId();
        ExtActProcess process = extActProcessService.getById(processId);
        return Result.OK(process);
    }

    /**
     * 发起流程接口
     *
     * @author Yoko
     * @since 2023/6/3 19:04
     * @param flowCode 流程code
     * @param id 表单id
     * @param formUrl 表单地址
     * @param formUrlMobile 移动表单地址
     * @param username 用户名
     * @return org.jeecg.common.api.vo.Result<java.lang.String>
     */
    public Result<String> startMutilProcess(String flowCode, String id, String formUrl, String formUrlMobile, String username) {

        Result<String> result = new Result<String>();
        result.setMessage("流程发起成功！");

        ActivitiException activitiException;
        Throwable throwable;
        try {
            if (oConvertUtils.isEmpty(flowCode)) {
                throw new BpmException("flowCode参数不能为空");
            }

            if (oConvertUtils.isEmpty(id)) {
                throw new BpmException("id参数不能为空");
            }

            if (oConvertUtils.isEmpty(formUrl)) {
                throw new BpmException("formUrl参数不能为空");
            }

            activitiException = null;

            ExtActProcessForm extActProcessForm;
            Map<String, Object> variables;
            try {
                extActProcessForm = this.extActProcessFormService.getOne(Wrappers.lambdaQuery(ExtActProcessForm.class).eq(ExtActProcessForm::getRelationCode, flowCode));
                String formTableName = extActProcessForm.getFormTableName();
                // 流程变量 所有表中的数据
                variables = this.extActProcessService.getDataById(formTableName, id);
                // 流程变量 BPM_DATA_ID
                variables.put(org.jeecg.modules.extbpm.process.common.a.l, id);
                // 流程变量 BPM_FORM_CONTENT_URL
                variables.put(org.jeecg.modules.extbpm.process.common.a.o, formUrl);
                // 流程变量 BPM_FORM_CONTENT_URL_MOBILE
                if (oConvertUtils.isNotEmpty(formUrlMobile)) {
                    variables.put(org.jeecg.modules.extbpm.process.common.a.p, formUrlMobile);
                } else {
                    variables.put(org.jeecg.modules.extbpm.process.common.a.p, formUrl);
                }
                //  BPM_DES_DATA_ID
                // 先找是否存在设计器表单数据，找到直接注入
                DesignFormData designFormData = designFormDataService.getOne(Wrappers.lambdaQuery(DesignFormData.class).eq(DesignFormData::getOnlineFormDataId, id));
                if (null != designFormData) {
                    // 流程变量 BPM_DES_DATA_ID
                    variables.put(org.jeecg.modules.extbpm.process.common.a.n, designFormData.getId());
                    // 流程变量 BPM_DES_FORM_CODE
                    variables.put(org.jeecg.modules.extbpm.process.common.a.m, designFormData.getDesformCode());
                }

                // 流程变量 BPM_FORM_KEY
                variables.put(org.jeecg.modules.extbpm.process.common.a.h, formTableName);
            } catch (Exception var14) {
                var14.printStackTrace();
                throw new BpmException("获取流程信息异常");
            }

            ProcessInstance processInstance = this.extActProcessService.startMutilProcess(username, id, variables, extActProcessForm);
            log.info("启动成功流程实例 ProcessInstance: {}", processInstance);
            result.setResult(processInstance.getProcessInstanceId());
        } catch (ActivitiException exception) {
            activitiException = exception;
            String msg = "启动流程失败";
            if (exception.getMessage().contains("no processes deployed with key")) {
                msg = "没有部署流程!,请在流程配置中部署流程!";
            } else if (exception.getMessage().contains("Error while evaluating expression")) {
                msg = "启动流程失败,流程表达式异常!";

                try {
                    msg = msg + activitiException.getCause().getCause().getMessage();
                } catch (Exception ignored) {
                }
            } else {
                msg = "启动流程失败!请确认流程和表单是否关联!";
            }
            result.error500(msg);
            throwable = exception.getCause();
            if (throwable != null) {
                if (((Throwable)throwable).getCause() != null && ((Throwable)throwable).getCause() instanceof BpmException) {
                    result.error500("启动流程失败:" + ((Throwable)throwable).getCause().getMessage());
                }
            }
            exception.printStackTrace();
        } catch (BpmException var16) {
            result.error500("启动流程失败:" + var16.getMessage());
            var16.printStackTrace();
        } catch (Exception var17) {
            result.error500("启动流程失败!,请确认流程和表单是否关联!");
            var17.printStackTrace();
            Throwable var9 = var17.getCause();
            if (var9 != null) {
                throwable = (Throwable)var9;
                if (throwable.getCause() != null && throwable.getCause() instanceof BpmException) {
                    result.error500("启动流程失败:" + throwable.getCause().getMessage());
                }
            }
        }
        return result;
    }

    /**
     * 获取我的待办列表
     *
     * @param userId
     * @param request
     * @return java.util.List<org.jeecg.modules.workflow.entity.TaskDTO>
     * @author Yoko
     * @since 2023/1/4 10:15
     */
    public List<TaskDTO> findPriTodoTasks(String userId, HttpServletRequest request) {
        try {
            return this.taskDTOList(true, userId, request);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 扩展我的任务待办列表，带上业务表单id
     *
     * @param var1
     * @param var2
     * @param var3
     * @return java.util.List<org.jeecg.modules.workflow.entity.TaskDTO>
     * @author Yoko
     * @since 2022/7/21 11:22
     */
    private List<TaskDTO> taskDTOList(boolean var1, String var2, HttpServletRequest var3) throws Exception {
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
            if (StringUtils.hasText(var11)) {
                List var13 = this.runtimeService.createProcessInstanceQuery().variableValueEquals(APPLY_USER_ID,
                        var11).list();
                if (var13 != null && var13.size() > 0) {
                    for (int var14 = 0; var14 < var13.size(); ++var14) {
                        if (var14 == 0) {
                            var12.append("'" + ((ProcessInstance) var13.get(var14)).getProcessInstanceId() + "'");
                        } else {
                            var12.append(",'" + ((ProcessInstance) var13.get(var14)).getProcessInstanceId() + "'");
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
            var16.append("\tand ( I.USER_ID_ = #{userid}  or I.GROUP_ID_ IN ( select g.GROUP_ID_ from " +
                    "ACT_ID_MEMBERSHIP g where g.USER_ID_ = #{userid}  ) ");
            var16.append(" ) ").append(" and RES.SUSPENSION_STATE_ = 1 ");
            if (StringUtils.hasText(var26)) {
                var16.append("  AND RES.PROC_DEF_ID_ LIKE #{procDefId} ");
            }

            if (StringUtils.hasText(var15)) {
                var16.append("  AND ARP.NAME_  LIKE #{procDefName} ");
            }

            if (StringUtils.hasText(var11)) {
                if (StringUtils.hasText(var24)) {
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
            if (StringUtils.hasText(var26)) {
                var16.append("  AND RES.PROC_DEF_ID_ LIKE #{procDefId} ");
            }

            if (StringUtils.hasText(var15)) {
                var16.append("  AND ARP.NAME_  LIKE #{procDefName} ");
            }

            if (StringUtils.hasText(var11)) {
                if (StringUtils.hasText(var24)) {
                    var16.append("  AND RES.PROC_INST_ID_ in (" + var24 + ") ");
                } else {
                    var16.append("  AND RES.PROC_INST_ID_ in ('-1') ");
                }
            }

            var16.append(" )) v ");
            var16.append(" order by v.CREATE_TIME_ desc, v.PRIORITY_ desc ");
            String var17 = CommonUtils.getDatabaseType();
            var18 = org.jeecg.modules.bpm.util.b.a(var17, var16.toString(), var5, var6);
            log.debug("我的任务:" + var18);
            NativeTaskQuery var19 =
                    (NativeTaskQuery) ((NativeTaskQuery) this.taskService.createNativeTaskQuery().sql(var18)).parameter("userid", var2);
            if (StringUtils.hasText(var26)) {
                var19.parameter("procDefId", "%" + var26 + "%");
            }

            if (StringUtils.hasText(var15)) {
                var19.parameter("procDefName", "%" + var15 + "%");
            }

            List var20 = var19.list();
            var9.addAll(var20);
        } else {
            TaskQuery var21 =
                    (TaskQuery) ((TaskQuery) ((TaskQuery) ((TaskQuery) ((TaskQuery) this.taskService.createTaskQuery().taskCandidateGroupIn(Arrays.asList(var2.split(",")))).orderByTaskCreateTime()).desc()).orderByTaskPriority()).desc();
            var21 = this.queryTask(var21, var3);
            List var10 = var21.listPage(var7, var8);
            var9.addAll(var10);
        }

        TaskDTO taskDTO;
        for (Iterator var22 = var9.iterator(); var22.hasNext(); var4.add(taskDTO)) {
            Task var23 = (Task) var22.next();
            taskDTO = new TaskDTO();
            var26 = var23.getAssignee() == null ? "" : var23.getAssignee();
            if (StringUtils.hasText(var26)) {
                LoginUser var27 = this.sysBaseAPI.getUserByName(var26);
                taskDTO.setTaskAssigneeName(var27 != null ? var27.getRealname() : "");
            }

            var15 = ((HistoricProcessInstance) this.historyService.createHistoricProcessInstanceQuery().processInstanceId(var23.getProcessInstanceId()).singleResult()).getStartUserId();
            if (StringUtils.hasText(var15)) {
                LoginUser var28 = this.sysBaseAPI.getUserByName(var15);
                taskDTO.setProcessApplyUserName(var28 != null ? var28.getRealname() : "");
            }

            String var29 = var23.getProcessInstanceId();
            ProcessInstance var30 =
                    (ProcessInstance) this.runtimeService.createProcessInstanceQuery().processInstanceId(var29).singleResult();
            taskDTO.setId(var23.getId());
            taskDTO.setTaskAssigneeId(var26);
            taskDTO.setTaskBeginTime(var23.getCreateTime());
            taskDTO.setTaskName(var23.getName());
            taskDTO.setTaskId(var23.getTaskDefinitionKey());
            taskDTO.setTaskEndTime(var23.getDueDate());
            taskDTO.setProcessInstanceId(var23.getProcessInstanceId());
            taskDTO.setProcessApplyUserId(var15);
            taskDTO.setProcessDefinitionId(var30.getProcessDefinitionId());
            taskDTO.setProcessDefinitionName(var30.getProcessDefinitionName());
            // 扩展实体返回数据，返回表单项id
            Map<String, Object> variables = this.taskService.getVariables(var23.getId());
            var18 = (String) variables.get("bpm_biz_title");
            String businessId = (String) this.taskService.getVariable(var23.getId(), "BPM_DATA_ID");
            taskDTO.setBusinessId(businessId);
            if (var18 != null) {
                taskDTO.setBpmBizTitle(var18);
            }
            taskDTO.setTaskUrge(false);
            if (StringUtils.hasText(taskDTO.getTaskAssigneeId()) && StringUtils.hasText(taskDTO.getProcessInstanceId()) && StringUtils.hasText(taskDTO.getId())) {
                LambdaQueryWrapper<ExtActTaskNotification> var31 = new LambdaQueryWrapper();
                var31.eq(ExtActTaskNotification::getProcInstId, taskDTO.getProcessInstanceId());
                var31.eq(ExtActTaskNotification::getTaskId, taskDTO.getId());
                var31.eq(ExtActTaskNotification::getTaskAssignee, taskDTO.getTaskAssigneeId());
                Long var32 = this.extActTaskNotificationMapper.selectCount(var31);
                if (var32 > 0) {
                    taskDTO.setTaskUrge(true);
                }
            }
        }

        return var4;
    }

    private TaskQuery queryTask(TaskQuery taskQuery, HttpServletRequest request) {
        String processDefinitionId = request.getParameter("processDefinitionId");
        String processDefinitionName = request.getParameter("processDefinitionName");
        if (StringUtils.hasText(processDefinitionId)) {
            taskQuery = (TaskQuery) taskQuery.processDefinitionId(processDefinitionId);
        }

        if (StringUtils.hasText(processDefinitionName)) {
            taskQuery = (TaskQuery) taskQuery.processDefinitionNameLike(processDefinitionName);
        }

        return taskQuery;
    }

    /**
     * 获取我的待办数量
     *
     * @param userid
     * @param request
     * @return java.lang.Long
     * @author Yoko
     * @since 2023/1/4 10:14
     */
    public Long countPriTodaoTask(String userid, HttpServletRequest request) {
        Long var3 = 0L;
        String var4 = request.getParameter("userName");
        StringBuilder var5 = new StringBuilder("");
        if (StringUtils.hasText(var4)) {
            List var6 =
                    this.runtimeService.createProcessInstanceQuery().variableValueEquals(APPLY_USER_ID, var4).list();
            if (var6 != null && var6.size() > 0) {
                for (int var7 = 0; var7 < var6.size(); ++var7) {
                    if (var7 == 0) {
                        var5.append("'" + ((ProcessInstance) var6.get(var7)).getProcessInstanceId() + "'");
                    } else {
                        var5.append(",'" + ((ProcessInstance) var6.get(var7)).getProcessInstanceId() + "'");
                    }
                }
            }
        }

        String var10 = var5.toString();
        String var11 = request.getParameter("processDefinitionId");
        StringBuilder var8 = new StringBuilder("");
        var8.append("select  count(*) ").append("from (");
        var8.append("(select distinct RES.* ").append("from ACT_RU_TASK RES inner join ACT_RU_IDENTITYLINK I on I" +
                ".TASK_ID_ = RES.ID_ ");
        var8.append("WHERE RES.ASSIGNEE_ is null and I.TYPE_ = 'candidate' ");
        var8.append("\tand ( I.USER_ID_ = #{userid}  or I.GROUP_ID_ IN ( select g.GROUP_ID_ from ACT_ID_MEMBERSHIP g " +
                "where g.USER_ID_ = #{userid}  ) ");
        var8.append(" ) ").append(" and RES.SUSPENSION_STATE_ = 1 ");
        if (StringUtils.hasText(var11)) {
            var8.append("  AND RES.PROC_DEF_ID_ LIKE #{procDefId} ");
        }

        if (StringUtils.hasText(var4)) {
            if (StringUtils.hasText(var10)) {
                var8.append("  AND RES.PROC_INST_ID_ in (" + var10 + ") ");
            } else {
                var8.append("  AND RES.PROC_INST_ID_ in ('-1') ");
            }
        }

        var8.append(") union ");
        var8.append("(select distinct RES.* ").append("from ACT_RU_TASK RES ");
        var8.append("WHERE RES.ASSIGNEE_ = #{userid} ");
        if (StringUtils.hasText(var11)) {
            var8.append("  AND RES.PROC_DEF_ID_ LIKE #{procDefId} ");
        }

        if (StringUtils.hasText(var4)) {
            if (StringUtils.hasText(var10)) {
                var8.append("  AND RES.PROC_INST_ID_ in (" + var10 + ") ");
            } else {
                var8.append("  AND RES.PROC_INST_ID_ in ('-1') ");
            }
        }

        var8.append(" )) v ");
        log.debug("我的任务count:" + var8.toString());
        NativeTaskQuery var9 =
                (NativeTaskQuery) ((NativeTaskQuery) this.taskService.createNativeTaskQuery().sql(var8.toString())).parameter("userid", userid);
        if (StringUtils.hasText(var11)) {
            var9.parameter("procDefId", "%" + var11 + "%");
        }

        var3 = var9.count();
        return var3;
    }

    /**
     * 直接完成流程
     *
     * @param id
     * @return void
     * @author Yoko
     * @since 2023/1/4 10:12
     */
    public void finishProcess(String id) {
        IExtActFlowDataService extActFlowDataService = SpringContextUtils.getBean(IExtActFlowDataService.class);
        ExtActProcessMapper extActProcessMapper = SpringContextUtils.getBean(ExtActProcessMapper.class);
        List<ExtActFlowData> extActFlowData =
                extActFlowDataService.list(new LambdaQueryWrapper<ExtActFlowData>().eq(ExtActFlowData::getFormDataId,
                        id));

        try {
            if (extActFlowData.size() > 0) {
                for (ExtActFlowData flowData : extActFlowData) {
                    String processInstId = flowData.getProcessInstId();
                    RuntimeService runtimeService = SpringContextUtils.getBean(RuntimeService.class);
                    ProcessInstance processInstance =
                            (ProcessInstance) runtimeService.createProcessInstanceQuery().processInstanceId(processInstId).singleResult();
                    if (null != processInstance) {
                        runtimeService.setVariable(processInstance.getProcessInstanceId(),
                                org.jeecg.modules.extbpm.process.common.a.q,
                                org.jeecg.modules.extbpm.process.common.a.e);
                        runtimeService.deleteProcessInstance(processInstId, "管理员-直接完成流程");
                        // 清除OA待办消息
                        // SpringContextUtils.getBean(RsaUtils.class).finishTodo(processInstId);
                    }
                }
            }
        } catch (Exception e) {}

        // 再完成jeecg里的流转数据
        if (extActFlowData.size() > 0) {
            // 新的逻辑：更新流转数据bpm状态值，然后更新业务数据表
            ExtActFlowData flowData = extActFlowData.get(0);
            flowData.setBpmStatus("3");
            extActFlowDataService.updateById(flowData);
            // 业务表
            String formTableName = flowData.getFormTableName();
            String bpmStatusField = flowData.getBpmStatusField();
            if (StringUtils.isEmpty(bpmStatusField)) {
                bpmStatusField = "bpm_status";
            }
            extActProcessMapper.updateBpmStatusById(formTableName, id, bpmStatusField, "3");
        }
    }

    /**
     * 取回流程
     *
     * @param id
     * @param processInstanceId
     * @return org.jeecg.common.api.vo.Result<?>
     * @author Yoko
     */
    public void callBackProcess(String id, String processInstanceId) {
        /**
         * 直接传过来的processInstanceId因为字典翻译缓存问题，导致重复，这里直接查，也可以避免多个结果的问题
         */
        LambdaQueryWrapper<ExtActFlowData> lqr =
                new LambdaQueryWrapper<ExtActFlowData>()
                        .eq(StringUtils.hasText(id), ExtActFlowData::getFormDataId, id)
                        .eq(StringUtils.hasText(processInstanceId), ExtActFlowData::getProcessInstId, processInstanceId);
        IExtActFlowDataService extActFlowDataService = SpringContextUtils.getBean(IExtActFlowDataService.class);
        ExtActProcessMapper extActProcessMapper = SpringContextUtils.getBean(ExtActProcessMapper.class);
        List<ExtActFlowData> extActFlowData = extActFlowDataService.list(lqr);
        if (extActFlowData.size() > 0) {
            for (ExtActFlowData flowData : extActFlowData) {
                String processInstId = flowData.getProcessInstId();
                RuntimeService runtimeService = SpringContextUtils.getBean(RuntimeService.class);
                ProcessInstance processInstance =
                        (ProcessInstance) runtimeService.createProcessInstanceQuery().processInstanceId(processInstId).singleResult();
                if (null != processInstance) {
                    runtimeService.setVariable(processInstance.getProcessInstanceId(),
                            org.jeecg.modules.extbpm.process.common.a.q, org.jeecg.modules.extbpm.process.common.a.z);
                    try {
                        runtimeService.deleteProcessInstance(processInstId, "发起人流程追回");
                    } catch (Exception e) {
                        log.error(e.getMessage());
                    }
                }
                // 追回流转数据、重置业务表数据
                flowData.setBpmStatus("1");
                extActFlowDataService.updateById(flowData);
                String formTableName = flowData.getFormTableName();
                String bpmStatusField = flowData.getBpmStatusField();
                if (StringUtils.isEmpty(bpmStatusField)) {
                    bpmStatusField = "bpm_status";
                }
                extActProcessMapper.updateBpmStatusById(formTableName, id, bpmStatusField, "1");
            }
            extActFlowDataService.remove(lqr);
        }
    }

    public void callBackProcess(String id) {
        this.callBackProcess(id, null);
    }

    /**
     * 查询审批记录接口
     * 1.覆盖默认接口 '/act/task/getHisProcessTaskTransInfo' jasper-ck-6.2.09.jar -> org.jeecg.modules.bpm.c.d
     * 2.代替的前端页面中原接口：
     * /src/views/modules/bpm/task/form/HisTaskModule.vue -> url.getHisProcessTaskTransInfo
     * /src/views/modules/bpmbiz/common/BizHisTaskModule.vue -> url.getHisProcessTaskTransInfo
     * 3.为什么要替代？在并行网关节点中，两个分支任务同时创建，此时无法确定任务先后完成，这时查询历史记录会有重复节点bug数据，这是因为原先代码
     * 只取了历史任务队尾节点作为当前节点，而未判断任务是否未完成！
     *
     * @param procInstId
     * @author Yoko
     */
    public HashMap<String, Object> getHisProcessTaskTransInfo(String procInstId) {
        HashMap<String, Object> hashMap = new HashMap<>();
        List<ActHiActinstDTO> hiActinstStartAndEnd = this.activitiService.getActHiActinstStartAndEnd(procInstId);
        boolean flag = false;
        ExtActBpmLog extActBpmLog = new ExtActBpmLog();
        Iterator<ActHiActinstDTO> hiActinstDTOIterator = hiActinstStartAndEnd.iterator();

        LoginUser loginUser;
        while (hiActinstDTOIterator.hasNext()) {
            ActHiActinstDTO actHiActinstDTO = hiActinstDTOIterator.next();
            if ("endEvent".equals(actHiActinstDTO.getActType())) {
                flag = true;
                extActBpmLog.setTaskName("结束");
                extActBpmLog.setOpTime(actHiActinstDTO.getEndTime());
                List<Map<String, Object>> processEndUserId = this.activitiService.getProcessEndUserId(procInstId);
                if (processEndUserId != null) {
                    Map<String, Object> endUser = processEndUserId.get(0);
                    String assignee = oConvertUtils.getString(endUser.get("ASSIGNEE_"));
                    if (oConvertUtils.isNotEmpty(assignee)) {
                        loginUser = this.sysBaseAPI.getUserByName(assignee);
                        extActBpmLog.setOpUserName(loginUser != null ? loginUser.getRealname() : assignee);
                    }
                }
                break;
            }
        }

        String currTaskName = null;
        String realname = null;
        String startTime = null;
        List<HistoricTaskInstance> historicTaskInstances;
        if (!flag) {
            historicTaskInstances =
                    (this.historyService.createHistoricTaskInstanceQuery().processInstanceId(procInstId)).list();
            if (historicTaskInstances != null && historicTaskInstances.size() > 0) {
                // 这里会有bug，需要先筛选出没有完成的任务
                historicTaskInstances =
                        historicTaskInstances.stream().filter(e -> null == e.getEndTime()).collect(Collectors.toList());
                HistoricTaskInstance historicTaskInstance = historicTaskInstances.get(historicTaskInstances.size() - 1);
                currTaskName = historicTaskInstance.getName();
                if (oConvertUtils.isNotEmpty(historicTaskInstance.getAssignee())) {
                    loginUser = this.sysBaseAPI.getUserByName(historicTaskInstance.getAssignee());
                    realname = loginUser.getRealname();
                }

                startTime = historicTaskInstance.getStartTime() == null ? "" :
                        DateUtils.formatDate(historicTaskInstance.getStartTime(), "YYYY-MM-dd " +
                                "HH:mm:ss");
            }
        }

        hashMap.put("currTaskName", currTaskName);
        hashMap.put("currTaskNameAssignee", realname);
        hashMap.put("currTaskNameStartTime", startTime);
        LambdaQueryWrapper<ExtActBpmLog> extActBpmLogLambdaQueryWrapper = new LambdaQueryWrapper<>();
        extActBpmLogLambdaQueryWrapper.eq(ExtActBpmLog::getProcInstId, procInstId);
        extActBpmLogLambdaQueryWrapper.ne(ExtActBpmLog::getOpUserId, "System Job");
        extActBpmLogLambdaQueryWrapper.orderByAsc(ExtActBpmLog::getOpTime);
        List<ExtActBpmLog> extActBpmLogs = this.extActBpmLogService.list(extActBpmLogLambdaQueryWrapper);

        for (ExtActBpmLog actBpmLog : extActBpmLogs) {
            LambdaQueryWrapper<ExtActBpmFile> extActBpmFileLambdaQueryWrapper = new LambdaQueryWrapper<>();
            extActBpmFileLambdaQueryWrapper.eq(ExtActBpmFile::getBpmLogId, actBpmLog.getId());
            List<ExtActBpmFile> extActBpmFiles = this.extActBpmFileService.list(extActBpmFileLambdaQueryWrapper);
            actBpmLog.setBpmFiles(extActBpmFiles);
        }

        if (flag) {
            extActBpmLogs.add(extActBpmLog);
        }

        int size = Math.max(extActBpmLogs.size() - 3, 0);
        List<ExtActBpmLog> bpmLogs = extActBpmLogs.stream().skip(size).collect(Collectors.toList());
        log.info("bpmLogStepList size : " + bpmLogs.size());
        hashMap.put("bpmLogList", extActBpmLogs);
        hashMap.put("bpmLogListCount", extActBpmLogs.size());
        hashMap.put("bpmLogStepList", bpmLogs);
        hashMap.put("bpmLogStepListCount", bpmLogs.size());
        return hashMap;
    }

    /**
     * 审批进度 -> 流程历史跟踪(列表数据) 接管接口
     * 原接口：/act/task/processHistoryList
     * 新接口：/workflow/common/processHistoryList
     * 对应前端类：chengfa_epc_vue/src/views/modules/bpmbiz/BizProcessModule.vue::processHistoryList
     *
     * @author Yoko
     * @since 2022/11/21 20:21
     */
    public List<ProcessHisDTO> processHistoryList(String processInstId) {

        List<ProcessHisDTO> processHisDTOS = new ArrayList<>();
        List var6 = this.activitiService.getActHiActinstStartAndEnd(processInstId);
        List var7 =
                ((HistoricTaskInstanceQuery) this.historyService.createHistoricTaskInstanceQuery().processInstanceId(processInstId)).list();
        String var8 = this.activitiService.getProcessStartUserId(processInstId);
        String var9 = "";
        List var10 = this.activitiService.getProcessEndUserId(processInstId);
        Map var11;
        if (var10 != null) {
            var11 = (Map) var10.get(0);
            if (var11 != null) {
                var9 = var11.get("ASSIGNEE_") == null ? "" : (String) var11.get("ASSIGNEE_");
            }
        }

        var11 = null;
        Iterator var12 = var6.iterator();

        ActHiActinstDTO var13;
        String var14;
        String var15;
        LoginUser var16;
        ProcessHisDTO var18;
        while (var12.hasNext()) {
            var13 = (ActHiActinstDTO) var12.next();
            if ("startEvent".equals(var13.getActType())) {
                var14 = var13.getStartTime() == null ? "" : DateUtils.formatDate(var13.getStartTime(), "yyyy-MM-dd " +
                        "HH:mm:ss");
                var15 = var13.getEndTime() == null ? "" : DateUtils.formatDate(var13.getEndTime(), "yyyy-MM-dd " +
                        "HH:mm:ss");
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
                processHisDTOS.add(var18);
            }
        }

        var12 = var7.iterator();

        while (var12.hasNext()) {
            HistoricTaskInstance var19 = (HistoricTaskInstance) var12.next();
            var14 = "";
            if ("completed".equals(var19.getDeleteReason())) {
                var14 = "已完成";
                if (var19.getDescription() != null && var19.getDescription().indexOf("委托") != -1) {
                    var14 = var19.getDescription() + "【" + var14 + "】";
                }
            } else {
                var14 = var19.getDeleteReason();
            }

            var15 = var19.getStartTime() == null ? "" : DateUtils.formatDate(var19.getStartTime(), "yyyy-MM-dd " +
                    "HH:mm:ss");
            String var20 = var19.getEndTime() == null ? "" : DateUtils.formatDate(var19.getEndTime(), "yyyy-MM-dd " +
                    "HH:mm:ss");
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
            processHisDTOS.add(var18);
        }

        var12 = var6.iterator();

        while (var12.hasNext()) {
            var13 = (ActHiActinstDTO) var12.next();
            if ("endEvent".equals(var13.getActType())) {
                var14 = var13.getStartTime() == null ? "" : DateUtils.formatDate(var13.getStartTime(), "yyyy-MM-dd " +
                        "HH:mm:ss");
                var15 = var13.getEndTime() == null ? "" : DateUtils.formatDate(var13.getEndTime(), "yyyy-MM-dd " +
                        "HH:mm:ss");
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
                processHisDTOS.add(var18);
            }
        }

        log.info("taskSize：" + processHisDTOS.size());
        return processHisDTOS;
    }

    /**
     * 审批进度 -> 流程跟踪(图像悬浮数据) 接管接口
     * 原接口：/act/task/getNodePositionInfo
     * 新接口：/workflow/common/getNodePositionInfo
     * 对应前端类：chengfa_epc_vue/src/views/modules/bpmbiz/BizProcessModule.vue::getNodePositionInfo
     * 1.节点无处理人时，会产生用户查询失败异常
     *
     * @author Yoko
     * @since 2022/11/21 20:16
     */
    public Map<String, Object> getNodePositionInfo(String processInstanceId) {

        Map<String, Object> result = new HashMap<>();
        List historicTaskInstances =
                ((HistoricTaskInstanceQuery) this.historyService.createHistoricTaskInstanceQuery().processInstanceId(processInstanceId)).list();
        ArrayList var6 = new ArrayList();
        org.jeecg.modules.bpm.dto.TaskDTO var7 = null;
        ExtActBpmLog var8 = null;

        List var13;
        for (Iterator var9 = historicTaskInstances.iterator(); var9.hasNext(); var6.add(var7)) {
            HistoricTaskInstance var10 = (HistoricTaskInstance) var9.next();
            var7 = new org.jeecg.modules.bpm.dto.TaskDTO();
            var7.setId(var10.getId());
            var7.setTaskId(var10.getTaskDefinitionKey());
            var7.setTaskBeginTime(var10.getStartTime());
            var7.setTaskEndTime(var10.getEndTime());
            var7.setTaskAssigneeId(var10.getAssignee());
            var7.setTaskDueTime(var10.getDurationInMillis());
            var7.setTaskName(var10.getName());
            if (StringUtils.hasText(var10.getAssignee())) {
                LoginUser var11 = this.sysBaseAPI.getUserByName(var10.getAssignee());
                if (var11 != null) {
                    var7.setTaskAssigneeName(var11.getRealname());
                }
            }

            LambdaQueryWrapper<ExtActBpmLog> var12 = new LambdaQueryWrapper<>();
            var12.eq(ExtActBpmLog::getTaskId, var10.getId());
            var13 = this.extActBpmLogService.list(var12);
            if (var13 != null && var13.size() > 0) {
                var8 = (ExtActBpmLog) var13.get(0);
                var7.setRemarks(var8.getRemarks());
            }
        }

        result.put("hisTasks", var6);
        k var17 = new k(processInstanceId);
        ProcessEngine var18 = ProcessEngines.getDefaultProcessEngine();
        List var19 = (List) var18.getManagementService().executeCommand(var17);
        ArrayList var20 = new ArrayList();
        var13 = null;
        Iterator var14 = var19.iterator();

        while (var14.hasNext()) {
            ActivityImpl var15 = (ActivityImpl) var14.next();
            HashMap var21 = new HashMap();
            var21.put("x", var15.getX());
            var21.put("y", var15.getY());
            var21.put("width", var15.getWidth());
            var21.put("height", var15.getHeight());
            var21.put("id", var15.getId());
            String var16 =
                    var15.getX() + "," + var15.getY() + "," + (var15.getX() + var15.getWidth()) + "," + (var15.getY() + var15.getHeight());
            var21.put("coords", var16);
            var20.add(var21);
        }

        result.put("positionList", var20);
        return result;
    }

    /**
     * 获取流程审批记录打印数据
     * 基于接口：/processHistoryList 修改
     *
     * @param id 业务表单id
     * @author Yoko
     */
    public JSONObject queryReportById(String id) {
        if (StringUtils.isEmpty(id)) {
            JSONObject result = new JSONObject();
            List<ProcessHisDTO> historyList = new ArrayList<>();
            historyList.add(new ProcessHisDTO());
            result.put("data", historyList);
            return result;
        }

        long start = System.currentTimeMillis();
        // 先找到关联的流程实例id
        String processInstanceId = new LambdaQueryChainWrapper<>(extActFlowDataService.getBaseMapper())
                .eq(ExtActFlowData::getFormDataId, id)
                .oneOpt().get().getProcessInstId();

        List<ProcessHisDTO> historyList = new ArrayList<ProcessHisDTO>();

        // 获取流程历史数据
        List<ActHiActinstDTO> actHiActinstStartAndEnd = this.activitiService.getActHiActinstStartAndEnd(processInstanceId);
        List<HistoricTaskInstance> historicTaskInstances =
                ((HistoricTaskInstanceQuery) this.historyService.createHistoricTaskInstanceQuery().processInstanceId(processInstanceId)).list();

        // 处理起始节点
        Iterator<ActHiActinstDTO> beginIterator = actHiActinstStartAndEnd.iterator();
        while (beginIterator.hasNext()) {
            ActHiActinstDTO actHiActinstDTO = beginIterator.next();
            if ("startEvent".equals(actHiActinstDTO.getActType())) {
                String startTime = actHiActinstDTO.getStartTime() == null ? "" : DateUtils.formatDate(actHiActinstDTO.getStartTime(), "yyyy-MM-dd " +
                        "HH:mm:ss");
                String endTime = actHiActinstDTO.getEndTime() == null ? "" : DateUtils.formatDate(actHiActinstDTO.getEndTime(), "yyyy-MM-dd " +
                        "HH:mm:ss");
                ProcessHisDTO processHisDTO = new ProcessHisDTO();
                processHisDTO.setId(org.jeecg.modules.extbpm.process.common.a.i);
                processHisDTO.setName(actHiActinstDTO.getActId());
                processHisDTO.setName("开始");
                processHisDTO.setProcessInstanceId(actHiActinstDTO.getExecutionId());
                processHisDTO.setStartTime(startTime);
                processHisDTO.setEndTime(endTime);
                String processStartUserId = this.activitiService.getProcessStartUserId(processInstanceId);
                processHisDTO.setAssignee(processStartUserId);
                if (oConvertUtils.isNotEmpty(processHisDTO.getAssignee())) {
                    LoginUser loginUser = this.sysBaseAPI.getUserByName(processHisDTO.getAssignee());
                    processHisDTO.setAssigneeName(loginUser != null ? loginUser.getRealname() : processHisDTO.getAssignee());
                }
                processHisDTO.setDeleteReason("已完成");
                historyList.add(processHisDTO);
            }
        }

        // 处理中间节点
        Iterator<HistoricTaskInstance> historicTaskInstanceIterator = historicTaskInstances.iterator();
        while (historicTaskInstanceIterator.hasNext()) {
            HistoricTaskInstance historicTaskInstance = historicTaskInstanceIterator.next();
            String deleteReason = "";
            if ("completed".equals(historicTaskInstance.getDeleteReason())) {
                deleteReason = "已完成";
                if (historicTaskInstance.getDescription() != null && historicTaskInstance.getDescription().contains("委托")) {
                    deleteReason = historicTaskInstance.getDescription() + "【" + deleteReason + "】";
                }
            } else {
                deleteReason = historicTaskInstance.getDeleteReason();
            }
            String startTime = historicTaskInstance.getStartTime() == null ? "" : DateUtils.formatDate(historicTaskInstance.getStartTime(), "yyyy-MM-dd " +
                    "HH:mm:ss");
            String endTime = historicTaskInstance.getEndTime() == null ? "" : DateUtils.formatDate(historicTaskInstance.getEndTime(), "yyyy-MM-dd " +
                    "HH:mm:ss");
            ProcessHisDTO processHisDTO = new ProcessHisDTO();
            processHisDTO.setId(historicTaskInstance.getId());
            processHisDTO.setName(historicTaskInstance.getName());
            processHisDTO.setProcessInstanceId(historicTaskInstance.getProcessInstanceId());
            processHisDTO.setStartTime(startTime);
            processHisDTO.setEndTime(endTime);
            processHisDTO.setAssignee(historicTaskInstance.getAssignee());
            if (oConvertUtils.isNotEmpty(processHisDTO.getAssignee())) {
                LoginUser loginUser = this.sysBaseAPI.getUserByName(processHisDTO.getAssignee());
                processHisDTO.setAssigneeName(loginUser != null ? loginUser.getRealname() : processHisDTO.getAssignee());
            }
            processHisDTO.setDeleteReason(deleteReason);
            // 填充流程审批历史意见
            new LambdaQueryChainWrapper<>(extActBpmLogService.getBaseMapper())
                    .eq(ExtActBpmLog::getTaskId, historicTaskInstance.getId()).oneOpt()
                    .ifPresent(extActBpmLog -> processHisDTO.setDeleteReason(extActBpmLog.getRemarks()));
            historyList.add(processHisDTO);
        }

        // 处理末尾节点
        Iterator<ActHiActinstDTO> endIterator = actHiActinstStartAndEnd.iterator();
        while (endIterator.hasNext()) {
            ActHiActinstDTO actHiActinstDTO = (ActHiActinstDTO) endIterator.next();
            if ("endEvent".equals(actHiActinstDTO.getActType())) {
                String startTime = actHiActinstDTO.getStartTime() == null ? "" : DateUtils.formatDate(actHiActinstDTO.getStartTime(), "yyyy-MM-dd " +
                        "HH:mm:ss");
                String endTime = actHiActinstDTO.getEndTime() == null ? "" : DateUtils.formatDate(actHiActinstDTO.getEndTime(), "yyyy-MM-dd " +
                        "HH:mm:ss");
                ProcessHisDTO processHisDTO = new ProcessHisDTO();
                processHisDTO.setId(org.jeecg.modules.extbpm.process.common.a.i);
                processHisDTO.setName(actHiActinstDTO.getActId());
                processHisDTO.setName("结束");
                processHisDTO.setProcessInstanceId(actHiActinstDTO.getExecutionId());
                processHisDTO.setStartTime(startTime);
                processHisDTO.setEndTime(endTime);
                // 函数式处理
                String processEndUserId = Optional.ofNullable(this.activitiService.getProcessEndUserId(processInstanceId))
                        .map(listMap -> Optional.ofNullable(listMap.get(0)).orElse(new HashMap<String, Object>()))
                        .map(map -> (String) Optional.ofNullable(map.get("ASSIGNEE_")).orElse(""))
                        .orElse("");
                processHisDTO.setAssignee(processEndUserId);
                if (oConvertUtils.isNotEmpty(processHisDTO.getAssignee())) {
                    LoginUser loginUser = this.sysBaseAPI.getUserByName(processHisDTO.getAssignee());
                    processHisDTO.setAssigneeName(loginUser != null ? loginUser.getRealname() : processHisDTO.getAssignee());
                }
                processHisDTO.setDeleteReason("已完成");
                historyList.add(processHisDTO);
            }
        }

        // 转换成报表API格式
        JSONObject result = new JSONObject();
        result.put("data", historyList);
        long end = System.currentTimeMillis();
        log.debug("通过id查询报表数据 耗时: " + (end - start) + "ms");
        return result;
    }

    /**
     * 获取图片资源-通过deployId
     *
     * @param deploymentId
     * @param resourceName
     * @param response
     * @return void
     * @author Yoko
     * @since 2023/1/4 10:55
     */
    public void getBpmResourceByDeployment(String deploymentId, String resourceName, HttpServletResponse response) {
        InputStream is = this.repositoryService.getResourceAsStream(deploymentId, resourceName);
        try {
            IOUtils.copy(is, response.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取图片资源(最新版本的)
     * jasper-ck-6.2.09.jar!\org\jeecg\modules\extbpm\process\controller\g
     * @param processKey 流程定义id
     * @return void
     * @author Yoko
     * @since 2022/12/16 16:52
     */
    public void getBpmResourceByProcess(String processKey, HttpServletResponse response) {
        List<ProcessDefinition> processDefinitions = this.activitiService.processDefinitionListByProcesskey(processKey);
        processDefinitions.sort((a, b) -> b.getVersion() - a.getVersion());
        ProcessDefinition latest = processDefinitions.get(0);

        InputStream is = this.repositoryService.getResourceAsStream(latest.getDeploymentId(), latest.getDiagramResourceName());
        try {
            IOUtils.copy(is, response.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取Biz流程节点信息
     * org.jeecg.modules.extbpm.process.controller.i#b(java.lang.String, java.lang.String, javax.servlet.http.HttpServletRequest)
     * 原: /act/process/extActProcessNode/getBizHisProcessNodeInfo
     * 新: /workflow/common/getBizHisProcessNodeInfo
     * @author Yoko
     * @since 2023/6/13 16:57
     * @param flowCode 流程code
     * @param dataId 表单id
     * @return java.util.HashMap<java.lang.String, java.lang.Object>
     */
    public Map<String, Object> getBizHisProcessNodeInfo(String flowCode, String dataId) {
        Map<String, Object> result = null;
        try {
            LambdaQueryWrapper<ExtActFlowData> wrapper = new LambdaQueryWrapper<ExtActFlowData>();
            wrapper.eq(ExtActFlowData::getRelationCode, flowCode);
            wrapper.eq(ExtActFlowData::getFormDataId, dataId);
            ExtActFlowData extActFlowData = (ExtActFlowData)this.extActFlowDataService.getOne(wrapper);
            String hisVarinst = this.activitiService.getHisVarinst("BPM_FORM_CONTENT_URL", extActFlowData.getProcessInstId());
            String formTableName = extActFlowData.getFormTableName();
            Map<String, Object> records = new HashMap<String, Object>();
            records.put("BPM_DATA_ID", dataId);
            records.put("BPM_FORM_KEY", formTableName);
            result = new HashMap<String, Object>();
            result.put("formUrl", hisVarinst);
            result.put("taskId", "");
            result.put("taskDefKey", "");
            result.put("dataId", dataId);
            result.put("procInsId", extActFlowData.getProcessInstId());
            result.put("tableName", formTableName);
            result.put("permissionList", new ArrayList());
            result.put("bizTaskList", new ArrayList());
            result.put("records", records);
        } catch (TooManyResultsException e) {
            throw new RuntimeException("流程异常, 存在多个重复实例, 请取回流程后重试!");
        } catch (Exception e) {
            throw new RuntimeException("流程异常, 错误信息: " + e.getMessage());
        }
        return result;
    }

}
