package org.jeecg.modules.workflow.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.*;
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
import org.jeecg.common.api.dto.message.BusTemplateMessageDTO;
import org.jeecg.common.api.dto.message.TemplateDTO;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.*;
import org.jeecg.modules.bpm.d.a.k;
import org.jeecg.modules.bpm.dto.ActHiActinstDTO;
import org.jeecg.modules.bpm.dto.ProcessHisDTO;
import org.jeecg.modules.bpm.service.ActivitiService;
import org.jeecg.modules.extbpm.process.common.WorkFlowGlobals;
import org.jeecg.modules.extbpm.process.entity.*;
import org.jeecg.modules.extbpm.process.exception.BpmException;
import org.jeecg.modules.extbpm.process.mapper.ExtActProcessMapper;
import org.jeecg.modules.extbpm.process.mapper.ExtActTaskNotificationMapper;
import org.jeecg.modules.extbpm.process.service.*;
import org.jeecg.modules.online.desform.entity.DesignFormData;
import org.jeecg.modules.online.desform.service.IDesignFormDataService;
import org.jeecg.modules.workflow.entity.TaskDTO;
import org.jeecg.modules.workflow.entity.TaskEntity;
import org.jeecg.modules.workflow.entity.TaskQueryVO;
import org.jeecg.modules.workflow.mapper.BpmCommonMapper;
import org.jeecg.modules.workflow.mapper.TaskEntityMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
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
    private IExtActProcessService extActProcessService;
    @Resource
    private IDesignFormDataService designFormDataService;
    @Resource
    private BpmCommonMapper bpmCommonMapper;
    @Resource
    private TaskEntityMapper taskEntityMapper;
    @Value("${spring.mail.username}")
    private String emailFrom;

    /**
     * 获取我的代办详细信息
     * @deprecated {@link BpmCommonService#myTaskListV2}
     */
    @Deprecated
    public <E extends IPage<TaskEntity>> E taskPage(E page, Wrapper<TaskEntity> queryWrapper) {
        return taskEntityMapper.taskPage(page, queryWrapper);
    }
    @Deprecated
    public List<TaskEntity> taskList(Wrapper<TaskEntity> queryWrapper) {
        return taskEntityMapper.taskList(queryWrapper);
    }

    /**
     * 获取我的待办列表-全条件版本
     */
    public <E extends IPage<TaskEntity>> E myTaskListV2(E page, Wrapper<TaskEntity> queryWrapper, String username) {
        return taskEntityMapper.myTaskListV2(page, queryWrapper, username);
    }
    public List<TaskEntity> myTaskListV2(Wrapper<TaskEntity> queryWrapper, String username) {
        return taskEntityMapper.myTaskListV2(queryWrapper, username);
    }

    /**
     * 任务催办
     *
     * @param extActTaskNotification
     * @return void
     * @author Yoko
     * @since 2023/11/15 10:30
     */
    public void taskNotification(ExtActTaskNotification extActTaskNotification) {
        List<Task> var2 = this.taskService.createTaskQuery().active().processInstanceId(extActTaskNotification.getProcInstId()).list();
        ProcessInstance var3 = this.runtimeService.createProcessInstanceQuery().processInstanceId(extActTaskNotification.getProcInstId()).singleResult();
        extActTaskNotification.setProcName(var3.getProcessDefinitionName());

        for (Task var5 : var2) {
            if (oConvertUtils.isNotEmpty(var5.getAssignee())) {
                log.info("--------发消息----------");
                extActTaskNotification.setId(UUIDGenerator.generate());
                extActTaskNotification.setTaskId(var5.getId());
                extActTaskNotification.setTaskName(var5.getName());
                extActTaskNotification.setTaskAssignee(var5.getAssignee());
                extActTaskNotification.setOpTime(new Date());
                extActTaskNotification.setTaskAssignee(var5.getAssignee());
                this.extActTaskNotificationMapper.insert(extActTaskNotification);
                String[] var6 = extActTaskNotification.getNotifyType().split(",");
                if (var6.length > 0) {
                    List<String> var7 = Arrays.asList(var6);
                    if (var7.contains("2")) {
                        log.info("--------邮件通知----------");
                        LoginUser var8 = this.sysBaseAPI.getUserByName(var5.getAssignee());
                        if (var8 != null && var8.getEmail() != null) {
                            try {
                                JavaMailSender var9 = (JavaMailSender) SpringContextUtils.getBean("mailSender");
                                MimeMessage var10 = var9.createMimeMessage();
                                MimeMessageHelper var11 = new MimeMessageHelper(var10, true);
                                var11.setFrom(this.emailFrom);
                                var11.setTo(var8.getEmail());
                                var11.setSubject("流程催办提醒");
                                HashMap<String, String> var12 = new HashMap<>();
                                var12.put("bpm_name", extActTaskNotification.getProcName());
                                var12.put("bpm_task", extActTaskNotification.getTaskName());
                                var12.put("remark", extActTaskNotification.getRemarks());
                                var12.put("datetime", DateUtils.formatDateTime());
                                TemplateDTO var13 = new TemplateDTO("bpm_cuiban", var12);
                                String var14 = this.sysBaseAPI.parseTemplateByCode(var13);
                                var11.setText(var14, true);
                                var9.send(var10);
                            } catch (Exception var15) {
                                log.error("--------邮件通知异常----------{}", var15.getMessage());
                            }
                        }
                    }

                    if (var7.contains("1")) {
                        log.info("--------页面通知----------");
                        HashMap<String, String> var16 = new HashMap<>();
                        var16.put("bpm_name", extActTaskNotification.getProcName());
                        var16.put("bpm_task", extActTaskNotification.getTaskName());
                        var16.put("remark", extActTaskNotification.getRemarks());
                        var16.put("datetime", DateUtils.formatDateTime());
                        BusTemplateMessageDTO var17 = new BusTemplateMessageDTO(extActTaskNotification.getCreateBy(), extActTaskNotification.getTaskAssignee(), "流程催办提醒", var16, "bpm_cuiban", SysAnnmentTypeEnum.BPM.getType(), var3.getId());
                        this.sysBaseAPI.sendBusTemplateAnnouncement(var17);
                    }
                }
            }
        }
    }

    /**
     * 获取历史代办-全条件版本
     *
     * @param page
     * @param queryWrapper
     * @param userName
     * @return com.baomidou.mybatisplus.extension.plugins.pagination.Page<org.jeecg.modules.workflow.entity.TaskEntity>
     * @author Yoko
     * @since 2023/11/15 14:47
     */
    public Page<TaskEntity> taskHistoryListV2(Page<TaskEntity> page, QueryWrapper<TaskEntity> queryWrapper, String userName) {
        return taskEntityMapper.taskHistoryListV2(page, queryWrapper, userName);
    }


    /**
     * 我发起的流程列表
     */
    public Page<org.jeecg.modules.workflow.entity.ProcessHisDTO> myApplyProcessListV2(Page<org.jeecg.modules.workflow.entity.ProcessHisDTO> page, QueryWrapper<org.jeecg.modules.workflow.entity.ProcessHisDTO> queryWrapper) {
        return taskEntityMapper.myApplyProcessListV2(page, queryWrapper);
    }

    /**
     * 我发起的流程列表
     */
    public Page<org.jeecg.modules.workflow.entity.ProcessHisDTO> myApplyProcessListV2(org.jeecg.modules.workflow.entity.ProcessHisDTO dto,
                                                                                      Integer pageNo,
                                                                                      Integer pageSize,
                                                                                      HttpServletRequest request) {
        dto.setStartUserId(JwtUtil.getUserNameByToken(request));
        String finishedStateQuery = dto.getFinishedStateQuery();
        dto.setFinishedStateQuery(null);
        QueryWrapper<org.jeecg.modules.workflow.entity.ProcessHisDTO> queryWrapper = QueryGenerator.initQueryWrapper(dto, request.getParameterMap());
        // 是否完成
        if (StringUtils.hasText(finishedStateQuery)) {
            if (org.jeecg.modules.workflow.entity.ProcessHisDTO.isFinished.equals(finishedStateQuery)) {
                queryWrapper.isNotNull("end_time");
            } else if (org.jeecg.modules.workflow.entity.ProcessHisDTO.isUnfinished.equals(finishedStateQuery)) {
                queryWrapper.isNull("end_time");
            }
        }
        Page<org.jeecg.modules.workflow.entity.ProcessHisDTO> page = new Page<org.jeecg.modules.workflow.entity.ProcessHisDTO>(pageNo, pageSize);
        return this.myApplyProcessListV2(page, queryWrapper);
    }

    /**
     * 我的抄送流程列表
     */
    public Page<TaskEntity> taskAllCcHistoryListV2(Page<TaskEntity> page, QueryWrapper<TaskEntity> queryWrapper, String userName) {
        return taskEntityMapper.taskAllCcHistoryListV2(page, queryWrapper, userName);
    }


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
            throw new RuntimeException("存在多个匹配结果，请检查流程配置并缩小范围！");
        }
        ExtActProcess process = null;
        try {
            ExtActProcessForm extActProcessForm = list.get(0);
            String processId = extActProcessForm.getProcessId();
            process = extActProcessService.getById(processId);
        } catch (Exception e) {
            throw new RuntimeException("请检查流程是否正确配置，online表单的请以 onl_ 开头，具体格式：onl_表名；kform设计器的表单以 desform_ 开头，具体格式：desform_表名！");
        }
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
     * @param bpmVariables 传进来的流程变量
     * @return org.jeecg.common.api.vo.Result<java.lang.String>
     */
    public Result<String> startMutilProcess(String flowCode, String id, String formUrl, String formUrlMobile, String username, Map<String, Object> bpmVariables) {

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

            // 预先校验一下ext_act_flow_data表中是否存在运行中的流程
            List<ExtActFlowData> extActFlowDataList = extActFlowDataService.list(Wrappers.lambdaQuery(ExtActFlowData.class)
                    .eq(ExtActFlowData::getFormDataId, id)
                    .eq(ExtActFlowData::getBpmStatus, "2"));
            if (!extActFlowDataList.isEmpty()) {
                throw new BpmException("该表单已存在运行中的流程，请勿重复发起！");
            }

            activitiException = null;

            ExtActProcessForm extActProcessForm;
            Map<String, Object> processVariables;
            try {
                extActProcessForm = this.extActProcessFormService.getOne(Wrappers.lambdaQuery(ExtActProcessForm.class).eq(ExtActProcessForm::getRelationCode, flowCode));
                String formTableName = extActProcessForm.getFormTableName();
                // 流程变量 所有表中的数据
                processVariables = this.extActProcessService.getDataById(formTableName, id);
                // 流程变量 BPM_DATA_ID
                processVariables.put(org.jeecg.modules.extbpm.process.common.a.l, id);
                // 流程变量 BPM_FORM_CONTENT_URL
                processVariables.put(org.jeecg.modules.extbpm.process.common.a.o, formUrl);
                // 流程变量 BPM_FORM_CONTENT_URL_MOBILE
                if (oConvertUtils.isNotEmpty(formUrlMobile)) {
                    processVariables.put(org.jeecg.modules.extbpm.process.common.a.p, formUrlMobile);
                } else {
                    processVariables.put(org.jeecg.modules.extbpm.process.common.a.p, formUrl);
                }
                //  BPM_DES_DATA_ID
                // 先找是否存在设计器表单数据，找到直接注入
                DesignFormData designFormData = designFormDataService.getOne(Wrappers.lambdaQuery(DesignFormData.class).eq(DesignFormData::getOnlineFormDataId, id));
                if (null != designFormData) {
                    // 流程变量 BPM_DES_DATA_ID
                    processVariables.put(org.jeecg.modules.extbpm.process.common.a.n, designFormData.getId());
                    // 流程变量 BPM_DES_FORM_CODE
                    processVariables.put(org.jeecg.modules.extbpm.process.common.a.m, designFormData.getDesformCode());
                }

                // 是否是设计器表单
                if (flowCode.startsWith(CommonConstant.FLOW_CODE_PREFIX_DESFORM)) {
                    // KForm设计器表单，修改几个变量
                    processVariables.put(org.jeecg.modules.extbpm.process.common.a.o, CommonConstant.KFORM_DESIGN_MODULE);
                    processVariables.put(org.jeecg.modules.extbpm.process.common.a.t, WorkFlowGlobals.BPM_FORM_TYPE_2);
                }

                // 流程变量 BPM_FORM_KEY
                processVariables.put(org.jeecg.modules.extbpm.process.common.a.h, formTableName);

                // 注入用户传进来的流程变量
                if (bpmVariables != null) {
                    processVariables.putAll(bpmVariables);
                }
            } catch (Exception var14) {
                var14.printStackTrace();
                throw new BpmException("获取流程信息异常");
            }

            ProcessInstance processInstance = this.extActProcessService.startMutilProcess(username, id, processVariables, extActProcessForm);
            log.info("启动成功流程实例 ProcessInstance: {}", processInstance);
            result.setResult(processInstance.getProcessInstanceId());
        } catch (ActivitiException exception) {
            activitiException = exception;
            String msg = "启动流程失败！";
            if (exception.getMessage().contains("no processes deployed with key")) {
                msg = "没有部署流程!,请在流程配置中部署流程!";
            } else if (exception.getMessage().contains("Error while evaluating expression")) {
                msg = "启动流程失败,流程表达式异常!";

                try {
                    msg = msg + activitiException.getCause().getCause().getMessage();
                } catch (Exception ignored) {
                }
            } else if (StringUtils.hasText(exception.getMessage())) {
                msg = msg + exception.getMessage();
            } else {
                msg = "启动流程失败!请确认流程和表单是否关联!";
            }
            result.error500(msg);
            throwable = exception.getCause();
            if (throwable != null) {
                if (throwable.getCause() != null && throwable.getCause() instanceof BpmException) {
                    result.error500("启动流程失败:" + throwable.getCause().getMessage());
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
     * @deprecated {@link BpmCommonService#myTaskListV2}
     */
    @Deprecated
    public List<TaskDTO> findPriTodoTasks(String userId, HttpServletRequest request) {
        try {
            return this.taskDTOList(true, userId, request);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Deprecated
    public List<TaskEntity> taskDTOAllList(TaskQueryVO taskQueryVO) {
        List<TaskEntity> tasks = bpmCommonMapper.taskDTOAllList(taskQueryVO);
        return tasks;
    }

    /**
     * 扩展我的任务待办列表，带上业务表单id
     *
     * @param nativeQuery
     * @param usernames
     * @param request
     * @return java.util.List<org.jeecg.modules.workflow.entity.TaskDTO>
     * @author Yoko
     * @since 2022/7/21 11:22
     */
    private List<TaskDTO> taskDTOList(boolean nativeQuery, String usernames, HttpServletRequest request) throws Exception {
        List<TaskDTO> result = new ArrayList<>();
        int pageNo = oConvertUtils.getInt(request.getParameter("pageNo"), 1);
        int pageSize = oConvertUtils.getInt(request.getParameter("pageSize"), 10);
        int pageStart = (pageNo - 1) * pageSize;
        int pageEnd = pageNo * pageSize - 1;
        List<Task> tasks = new ArrayList<>();
        if (nativeQuery) {
            String userName = request.getParameter("userName");
            StringBuilder stringBuilder = new StringBuilder();
            if (StringUtils.hasText(userName)) {
                List<ProcessInstance> processInstanceList = this.runtimeService.createProcessInstanceQuery().variableValueEquals(APPLY_USER_ID,
                        userName).list();
                if (processInstanceList != null && !processInstanceList.isEmpty()) {
                    for (int i = 0; i < processInstanceList.size(); ++i) {
                        if (i == 0) {
                            stringBuilder.append("'").append(processInstanceList.get(i).getProcessInstanceId()).append("'");
                        } else {
                            stringBuilder.append(",'").append(processInstanceList.get(i).getProcessInstanceId()).append("'");
                        }
                    }
                }
            }
            String procInstIds = stringBuilder.toString();
            String processDefinitionId = request.getParameter("processDefinitionId");
            String processDefinitionName = request.getParameter("processDefinitionName");
            StringBuilder sqlBuilder = new StringBuilder();
            sqlBuilder.append("select  * ").append("from (");
            sqlBuilder.append("(select distinct RES.* ");
            sqlBuilder.append(" from ACT_RU_TASK RES inner join ACT_RU_IDENTITYLINK I on I.TASK_ID_ = RES.ID_ ");
            sqlBuilder.append(" INNER JOIN ACT_RE_PROCDEF ARP ON ARP.ID_ = RES.PROC_DEF_ID_ ");
            sqlBuilder.append("WHERE RES.ASSIGNEE_ is null and I.TYPE_ = 'candidate' ");
            sqlBuilder.append("\tand ( I.USER_ID_ = #{userid}  or I.GROUP_ID_ IN ( select g.GROUP_ID_ from " +
                    "ACT_ID_MEMBERSHIP g where g.USER_ID_ = #{userid}  ) ");
            sqlBuilder.append(" ) ").append(" and RES.SUSPENSION_STATE_ = 1 ");
            if (StringUtils.hasText(processDefinitionId)) {
                sqlBuilder.append("  AND RES.PROC_DEF_ID_ LIKE #{procDefId} ");
            }

            if (StringUtils.hasText(processDefinitionName)) {
                sqlBuilder.append("  AND ARP.NAME_  LIKE #{procDefName} ");
            }

            if (StringUtils.hasText(userName)) {
                if (StringUtils.hasText(procInstIds)) {
                    sqlBuilder.append("  AND RES.PROC_INST_ID_ in (" + procInstIds + ") ");
                } else {
                    sqlBuilder.append("  AND RES.PROC_INST_ID_ in ('-1') ");
                }
            }

            sqlBuilder.append(") union ");
            sqlBuilder.append("(select distinct RES.* ");
            sqlBuilder.append(" from ACT_RU_TASK RES ");
            sqlBuilder.append(" INNER JOIN ACT_RE_PROCDEF ARP ON ARP.ID_ = RES.PROC_DEF_ID_ ");
            sqlBuilder.append("WHERE RES.ASSIGNEE_ = #{userid} ");
            if (StringUtils.hasText(processDefinitionId)) {
                sqlBuilder.append("  AND RES.PROC_DEF_ID_ LIKE #{procDefId} ");
            }

            if (StringUtils.hasText(processDefinitionName)) {
                sqlBuilder.append("  AND ARP.NAME_  LIKE #{procDefName} ");
            }

            if (StringUtils.hasText(userName)) {
                if (StringUtils.hasText(procInstIds)) {
                    sqlBuilder.append("  AND RES.PROC_INST_ID_ in (" + procInstIds + ") ");
                } else {
                    sqlBuilder.append("  AND RES.PROC_INST_ID_ in ('-1') ");
                }
            }

            sqlBuilder.append(" )) v ");
            sqlBuilder.append(" order by v.CREATE_TIME_ desc, v.PRIORITY_ desc ");
            String databaseType = CommonUtils.getDatabaseType();
            String finalPageSql = org.jeecg.modules.bpm.util.b.a(databaseType, sqlBuilder.toString(), pageNo, pageSize);
            log.debug("我的任务:" + finalPageSql);
            NativeTaskQuery nativeTaskQuery =
                    this.taskService.createNativeTaskQuery().sql(finalPageSql).parameter("userid", usernames);
            if (StringUtils.hasText(processDefinitionId)) {
                nativeTaskQuery.parameter("procDefId", "%" + processDefinitionId + "%");
            }

            if (StringUtils.hasText(processDefinitionName)) {
                nativeTaskQuery.parameter("procDefName", "%" + processDefinitionName + "%");
            }
            List<Task> list = nativeTaskQuery.list();
            tasks.addAll(list);
        } else {
            TaskQuery taskQuery =
                    this.taskService.createTaskQuery().orderByTaskCreateTime().desc().orderByTaskPriority().desc();
            // 将用户名查询分离
            if (StringUtils.hasText(usernames)) {
                taskQuery = taskQuery.taskCandidateGroupIn(Arrays.asList(usernames.split(",")));
            }
            taskQuery = this.queryTask(taskQuery, request);
            List<Task> list = taskQuery.listPage(pageStart, pageEnd);
            tasks.addAll(list);
        }

        // 填充业务信息
        for (Task task : tasks) {
            TaskDTO taskDTO = new TaskDTO();
            String assignee = task.getAssignee() == null ? "" : task.getAssignee();
            if (StringUtils.hasText(assignee)) {
                LoginUser var27 = this.sysBaseAPI.getUserByName(assignee);
                taskDTO.setTaskAssigneeName(var27 != null ? var27.getRealname() : "");
            }

            String startUserId = this.historyService.createHistoricProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult().getStartUserId();
            if (StringUtils.hasText(startUserId)) {
                LoginUser loginUser = this.sysBaseAPI.getUserByName(startUserId);
                taskDTO.setProcessApplyUserName(loginUser != null ? loginUser.getRealname() : "");
            }

            String processInstanceId = task.getProcessInstanceId();
            ProcessInstance processInstance =
                    this.runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
            taskDTO.setId(task.getId());
            taskDTO.setTaskAssigneeId(assignee);
            taskDTO.setTaskBeginTime(task.getCreateTime());
            taskDTO.setTaskName(task.getName());
            taskDTO.setTaskId(task.getTaskDefinitionKey());
            taskDTO.setTaskEndTime(task.getDueDate());
            taskDTO.setProcessInstanceId(task.getProcessInstanceId());
            taskDTO.setProcessApplyUserId(startUserId);
            taskDTO.setProcessDefinitionId(processInstance.getProcessDefinitionId());
            taskDTO.setProcessDefinitionName(processInstance.getProcessDefinitionName());
            // 扩展实体返回数据，返回表单项id
            Map<String, Object> variables = this.taskService.getVariables(task.getId());
            String bpmBizTitle = (String) variables.get("bpm_biz_title");
            String businessId = (String) this.taskService.getVariable(task.getId(), "BPM_DATA_ID");
            taskDTO.setBusinessId(businessId);
            if (bpmBizTitle != null) {
                taskDTO.setBpmBizTitle(bpmBizTitle);
            }
            taskDTO.setTaskUrge(false);
            if (StringUtils.hasText(taskDTO.getTaskAssigneeId()) && StringUtils.hasText(taskDTO.getProcessInstanceId()) && StringUtils.hasText(taskDTO.getId())) {
                LambdaQueryWrapper<ExtActTaskNotification> lqw = Wrappers.lambdaQuery(ExtActTaskNotification.class);
                lqw.eq(ExtActTaskNotification::getProcInstId, taskDTO.getProcessInstanceId());
                lqw.eq(ExtActTaskNotification::getTaskId, taskDTO.getId());
                lqw.eq(ExtActTaskNotification::getTaskAssignee, taskDTO.getTaskAssigneeId());
                Long urgeCount = this.extActTaskNotificationMapper.selectCount(lqw);
                if (urgeCount > 0) {
                    taskDTO.setTaskUrge(true);
                }
            }
            result.add(taskDTO);
        }

        return result;
    }

    private TaskQuery queryTask(TaskQuery taskQuery, HttpServletRequest request) {
        String processDefinitionId = request.getParameter("processDefinitionId");
        String processDefinitionName = request.getParameter("processDefinitionName");
        if (StringUtils.hasText(processDefinitionId)) {
            taskQuery = taskQuery.processDefinitionId(processDefinitionId);
        }

        if (StringUtils.hasText(processDefinitionName)) {
            taskQuery = taskQuery.processDefinitionNameLike(processDefinitionName);
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
     * @deprecated {@link BpmCommonService#myTaskListV2}
     */
    @Deprecated
    public Long countPriTodaoTask(String userid, HttpServletRequest request) {
        Long var3 = 0L;
        String var4 = request.getParameter("userName");
        StringBuilder var5 = new StringBuilder();
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
        StringBuilder var8 = new StringBuilder();
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
        log.debug("我的任务count:" + var8);
        NativeTaskQuery var9 =
                this.taskService.createNativeTaskQuery().sql(var8.toString()).parameter("userid", userid);
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

        if (!extActFlowData.isEmpty()) {
            for (ExtActFlowData flowData : extActFlowData) {
                // 先删流程示例
                String processInstId = flowData.getProcessInstId();
                RuntimeService runtimeService = SpringContextUtils.getBean(RuntimeService.class);
                ProcessInstance processInstance =
                        runtimeService.createProcessInstanceQuery().processInstanceId(processInstId).singleResult();
                if (null != processInstance) {
                    runtimeService.setVariable(processInstance.getProcessInstanceId(),
                            org.jeecg.modules.extbpm.process.common.a.q,
                            org.jeecg.modules.extbpm.process.common.a.e);
                    // 流程示例删除失败，异常就抛出去
                    runtimeService.deleteProcessInstance(processInstId, CommonConstant.BPM_REASON_QUICK_FINISH);
                    // 清除OA待办消息
                    // SpringContextUtils.getBean(RsaUtils.class).finishTodo(processInstId);
                }
            }
        }
        // 再完成jeecg里的流转数据
        if (!extActFlowData.isEmpty()) {
            // 新的逻辑：更新流转数据bpm状态值，然后更新业务数据表
            ExtActFlowData flowData = extActFlowData.get(0);
            flowData.setBpmStatus("3");
            extActFlowDataService.updateById(flowData);
            // 业务表
            String formTableName = flowData.getFormTableName();
            String bpmStatusField = flowData.getBpmStatusField();
            if (!StringUtils.hasText(bpmStatusField)) {
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
        if (!extActFlowData.isEmpty()) {
            for (ExtActFlowData flowData : extActFlowData) {
                String processInstId = flowData.getProcessInstId();
                RuntimeService runtimeService = SpringContextUtils.getBean(RuntimeService.class);
                ProcessInstance processInstance =
                        (ProcessInstance) runtimeService.createProcessInstanceQuery().processInstanceId(processInstId).singleResult();
                if (null != processInstance) {
                    runtimeService.setVariable(processInstance.getProcessInstanceId(),
                            org.jeecg.modules.extbpm.process.common.a.q, org.jeecg.modules.extbpm.process.common.a.z);
                    try {
                        runtimeService.deleteProcessInstance(processInstId, CommonConstant.BPM_REASON_CALLBACK);
                    } catch (Exception e) {
                        log.error(e.getMessage());
                    }
                }
                // 追回流转数据、重置业务表数据
                flowData.setBpmStatus("1");
                extActFlowDataService.updateById(flowData);
                String formTableName = flowData.getFormTableName();
                String bpmStatusField = flowData.getBpmStatusField();
                if (!StringUtils.hasText(bpmStatusField)) {
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
            if (historicTaskInstances != null && !historicTaskInstances.isEmpty()) {
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
        List<ActHiActinstDTO> actHiActinstStartAndEnd = this.activitiService.getActHiActinstStartAndEnd(processInstId);
        List<HistoricTaskInstance> historicTaskInstances = this.historyService.createHistoricTaskInstanceQuery().processInstanceId(processInstId).list();
        String processStartUserId = this.activitiService.getProcessStartUserId(processInstId);
        String assignee = "";
        List<Map<String, Object>> processEndUserId = this.activitiService.getProcessEndUserId(processInstId);
        if (processEndUserId != null) {
            Map<String, Object> endUserMap = processEndUserId.get(0);
            if (endUserMap != null) {
                assignee = endUserMap.get("ASSIGNEE_") == null ? "" : (String) endUserMap.get("ASSIGNEE_");
            }
        }

        for (ActHiActinstDTO actHiActinstDTO : actHiActinstStartAndEnd) {
            if ("startEvent".equals(actHiActinstDTO.getActType())) {
                String startTime = actHiActinstDTO.getStartTime() == null ? "" : DateUtils.formatDate(actHiActinstDTO.getStartTime(), "yyyy-MM-dd " +
                        "HH:mm:ss");
                String endTime = actHiActinstDTO.getEndTime() == null ? "" : DateUtils.formatDate(actHiActinstDTO.getEndTime(), "yyyy-MM-dd " +
                        "HH:mm:ss");
                ProcessHisDTO processHisDTO = new ProcessHisDTO();
                processHisDTO.setId(org.jeecg.modules.extbpm.process.common.a.i);
                processHisDTO.setName(actHiActinstDTO.getActId());
                processHisDTO.setProcessInstanceId(actHiActinstDTO.getExecutionId());
                processHisDTO.setStartTime(startTime);
                processHisDTO.setEndTime(endTime);
                processHisDTO.setAssignee(processStartUserId);
                if (oConvertUtils.isNotEmpty(processHisDTO.getAssignee())) {
                    LoginUser loginUser = this.sysBaseAPI.getUserByName(processHisDTO.getAssignee());
                    processHisDTO.setAssigneeName(loginUser != null ? loginUser.getRealname() : processHisDTO.getAssignee());
                }
                processHisDTO.setDeleteReason("已完成");
                processHisDTOS.add(processHisDTO);
            }
        }

        for (HistoricTaskInstance historicTaskInstance : historicTaskInstances) {
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
            processHisDTOS.add(processHisDTO);
        }

        for (ActHiActinstDTO actHiActinstDTO : actHiActinstStartAndEnd) {
            if ("endEvent".equals(actHiActinstDTO.getActType())) {
                String startTime = actHiActinstDTO.getStartTime() == null ? "" : DateUtils.formatDate(actHiActinstDTO.getStartTime(), "yyyy-MM-dd " +
                        "HH:mm:ss");
                String endTime = actHiActinstDTO.getEndTime() == null ? "" : DateUtils.formatDate(actHiActinstDTO.getEndTime(), "yyyy-MM-dd " +
                        "HH:mm:ss");
                ProcessHisDTO processHisDTO = new ProcessHisDTO();
                // processHisDTO.setId(org.jeecg.modules.extbpm.process.common.a.i);
                processHisDTO.setId("end");
                processHisDTO.setName(actHiActinstDTO.getActId());
                processHisDTO.setProcessInstanceId(actHiActinstDTO.getExecutionId());
                processHisDTO.setStartTime(startTime);
                processHisDTO.setEndTime(endTime);
                processHisDTO.setAssignee(assignee);
                if (oConvertUtils.isNotEmpty(processHisDTO.getAssignee())) {
                    LoginUser loginUser = this.sysBaseAPI.getUserByName(processHisDTO.getAssignee());
                    processHisDTO.setAssigneeName(loginUser != null ? loginUser.getRealname() : processHisDTO.getAssignee());
                }
                processHisDTO.setDeleteReason("已完成");
                processHisDTOS.add(processHisDTO);
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
        List<HistoricTaskInstance> historicTaskInstances = this.historyService.createHistoricTaskInstanceQuery().processInstanceId(processInstanceId).list();
        List<org.jeecg.modules.bpm.dto.TaskDTO> taskDTOS = new ArrayList<>();
        org.jeecg.modules.bpm.dto.TaskDTO taskDTO;

        for (Iterator<HistoricTaskInstance> iterator = historicTaskInstances.iterator(); iterator.hasNext(); taskDTOS.add(taskDTO)) {
            HistoricTaskInstance historicTaskInstance = iterator.next();
            taskDTO = new org.jeecg.modules.bpm.dto.TaskDTO();
            taskDTO.setId(historicTaskInstance.getId());
            taskDTO.setTaskId(historicTaskInstance.getTaskDefinitionKey());
            taskDTO.setTaskBeginTime(historicTaskInstance.getStartTime());
            taskDTO.setTaskEndTime(historicTaskInstance.getEndTime());
            taskDTO.setTaskAssigneeId(historicTaskInstance.getAssignee());
            taskDTO.setTaskDueTime(historicTaskInstance.getDurationInMillis());
            taskDTO.setTaskName(historicTaskInstance.getName());
            if (StringUtils.hasText(historicTaskInstance.getAssignee())) {
                LoginUser loginUser = this.sysBaseAPI.getUserByName(historicTaskInstance.getAssignee());
                if (loginUser != null) {
                    taskDTO.setTaskAssigneeName(loginUser.getRealname());
                }
            }
            LambdaQueryWrapper<ExtActBpmLog> queryWrapper = new LambdaQueryWrapper<ExtActBpmLog>().eq(ExtActBpmLog::getTaskId, historicTaskInstance.getId());
            List<ExtActBpmLog> extActBpmLogs = this.extActBpmLogService.list(queryWrapper);
            if (extActBpmLogs != null && !extActBpmLogs.isEmpty()) {
                taskDTO.setRemarks(extActBpmLogs.get(0).getRemarks());
            }
        }

        result.put("hisTasks", taskDTOS);
        k nodes = new k(processInstanceId);
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        List<ActivityImpl> activityList = (List<ActivityImpl>) defaultProcessEngine.getManagementService().executeCommand(nodes);
        List<Map<String, Object>> positionList = new ArrayList<>();

        for (ActivityImpl activity : activityList) {
            Map<String, Object> hashMap = new HashMap<>();
            hashMap.put("x", activity.getX());
            hashMap.put("y", activity.getY());
            hashMap.put("width", activity.getWidth());
            hashMap.put("height", activity.getHeight());
            hashMap.put("id", activity.getId());
            String coords = activity.getX() + "," + activity.getY() + "," + (activity.getX() + activity.getWidth()) + "," + (activity.getY() + activity.getHeight());
            hashMap.put("coords", coords);
            positionList.add(hashMap);
        }

        result.put("positionList", positionList);
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
                .oneOpt().map(ExtActFlowData::getProcessInstId).orElseThrow(() -> new JeecgBootException("未找到关联流程实例id，业务id：" + id));

        List<ProcessHisDTO> historyList = new ArrayList<ProcessHisDTO>();

        // 获取流程历史数据
        List<ActHiActinstDTO> actHiActinstStartAndEnd = this.activitiService.getActHiActinstStartAndEnd(processInstanceId);
        List<HistoricTaskInstance> historicTaskInstances =
                ((HistoricTaskInstanceQuery) this.historyService.createHistoricTaskInstanceQuery().processInstanceId(processInstanceId)).list();

        // 处理起始节点
        for (ActHiActinstDTO actHiActinstDTO : actHiActinstStartAndEnd) {
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
        for (HistoricTaskInstance historicTaskInstance : historicTaskInstances) {
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
        for (ActHiActinstDTO actHiActinstDTO : actHiActinstStartAndEnd) {
            if ("endEvent".equals(actHiActinstDTO.getActType())) {
                String startTime = actHiActinstDTO.getStartTime() == null ? "" : DateUtils.formatDate(actHiActinstDTO.getStartTime(), "yyyy-MM-dd " +
                        "HH:mm:ss");
                String endTime = actHiActinstDTO.getEndTime() == null ? "" : DateUtils.formatDate(actHiActinstDTO.getEndTime(), "yyyy-MM-dd " +
                        "HH:mm:ss");
                ProcessHisDTO processHisDTO = new ProcessHisDTO();
                // processHisDTO.setId(org.jeecg.modules.extbpm.process.common.a.i);
                processHisDTO.setId("end");
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
    public Map<String, Object> getBizHisProcessNodeInfo(String flowCode, String dataId, Boolean throwEx) {
        Map<String, Object> result = null;
        try {
            LambdaQueryWrapper<ExtActFlowData> wrapper = new LambdaQueryWrapper<ExtActFlowData>();
            wrapper.eq(ExtActFlowData::getRelationCode, flowCode);
            wrapper.eq(ExtActFlowData::getFormDataId, dataId);
            wrapper.orderByDesc(ExtActFlowData::getCreateTime);
            ExtActFlowData extActFlowData = this.extActFlowDataService.getOne(wrapper, throwEx);
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

    public void callBackProcessInstance(String processInstId) {
        RuntimeService runtimeService = SpringContextUtils.getBean(RuntimeService.class);
        ProcessInstance processInstance =
                (ProcessInstance) runtimeService.createProcessInstanceQuery().processInstanceId(processInstId).singleResult();
        if (null != processInstance) {
            runtimeService.setVariable(processInstance.getProcessInstanceId(),
                    org.jeecg.modules.extbpm.process.common.a.q, org.jeecg.modules.extbpm.process.common.a.z);
            try {
                runtimeService.deleteProcessInstance(processInstId, "流程取回");
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }

    /**
     * 委派节点
     *
     * @author Yoko
     * @since 2023/11/7 16:19
     * @param taskId 任务id
     * @param taskAssignee 指定处理人
     */
    public void taskEntrust(String taskId, String taskAssignee, HttpServletRequest request) {
        Task task = (this.taskService.createTaskQuery().taskId(taskId)).active().singleResult();
        // 签收状态需要签收一下
        if (!StringUtils.hasText(task.getAssignee())) {
            String username = JwtUtil.getUserNameByToken(request);
            taskService.claim(taskId, username);
        }
        // 委托
        taskService.delegateTask(task.getId(), taskAssignee);
    }
}
