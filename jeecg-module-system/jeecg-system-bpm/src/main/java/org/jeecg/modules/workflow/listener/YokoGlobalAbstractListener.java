package org.jeecg.modules.workflow.listener;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.*;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.impl.ActivitiEntityEventImpl;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.IdentityLink;
import org.jeecg.common.api.CommonAPI;
import org.jeecg.common.api.dto.message.MessageDTO;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.system.vo.SysUserModel;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.modules.extbpm.listener.execution.ProcessEndListener;
import org.jeecg.modules.extbpm.process.common.WorkFlowGlobals;
import org.jeecg.modules.extbpm.process.entity.ExtActBpmLog;
import org.jeecg.modules.extbpm.process.entity.ExtActFlowData;
import org.jeecg.modules.extbpm.process.mapper.ExtActDesignFlowDataMapper;
import org.jeecg.modules.extbpm.process.mapper.ExtActFlowDataMapper;
import org.jeecg.modules.extbpm.process.mapper.ExtActProcessMapper;
import org.jeecg.modules.extbpm.process.pojo.DesignFormDataDTO;
import org.jeecg.modules.extbpm.process.service.IExtActBpmLogService;
import org.jeecg.modules.extbpm.process.service.IExtActProcessService;
import org.jeecg.modules.workflow.service.IOaTodoService;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * 抽象自定义的全局监听器
 * 1.抽象出处理方法到子类实现
 * 2.实现一些基本方法（如系统内消息推送、OA消息推送）
 *
 * @author Yoko
 * @date 2022/3/24 14:26
 */
@Slf4j
public abstract class YokoGlobalAbstractListener implements ActivitiEventListener, ExecutionListener {

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 当前绑定的流程定义名称，使用注解 @YokoGlobalListener("支付款") 可自动注入
     */
    @Getter
    @Setter
    private String bindProcessDefinitionName;

    /**
     * 当前绑定的流程编码，使用注解 @YokoGlobalListener("支付款") 可自动注入
     */
    @Getter
    @Setter
    private String bindProcessDefinitionKey;

    /**
     * 流程节点没有配置角色信息时，是否自动注入发起人
     * 当设置成 false 时，节点处理人为空将自动抛异常
     */
    @Getter
    @Setter
    private boolean autoFillApplyUserId = true;

    /**
     * 流程节点注入忽略任务
     * 仅当 autoFillApplyUserId = false 时生效
     */
    @Getter
    @Setter
    private List<String> autoFillIgnoreTaskNames;

    /**
     * 指定会签节点名称，自动处理最后一个会签节点返回给会签发起人
     */
    @Getter
    @Setter
    private List<String> signTaskNames;

    /**
     * 是否开启自动注入目标节点历史处理人（无配置固定处理人时）<br/>
     * 此项配置后，选项 autoFillHistoricRejectApplyUserId 失效
     */
    @Getter
    @Setter
    private boolean autoFillHistoricApplyUserId;

    /**
     * 是否开启自动注入驳回目标节点历史处理人（无配置固定处理人时）
     */
    @Getter
    @Setter
    private boolean autoFillHistoricRejectApplyUserId;

    /**
     * 是否用前端选择后的处理人，覆盖默认配置的处理人
     * 此配置必须配置忽略的会签节点，否则会签节点创建多个会签子任务时，会导致每次request域的前端选择处理人变成任务候选人进行分配任务
     */
    @Getter
    @Setter
    private boolean overrideNodeDefaultUserByCustomSelect;

    /**
     * 对接OA服务
     */
    @Resource
    private IOaTodoService oaTodoInterface;

    public CommonAPI getBaseApi() {
        if (null == baseApi) {
            baseApi = SpringContextUtils.getBean(CommonAPI.class);
        }
        return baseApi;
    }

    public TaskService getTaskService() {
        if (null == taskService) {
            taskService = SpringContextUtils.getBean(TaskService.class);
        }
        return taskService;
    }

    public RuntimeService getRuntimeService() {
        if (null == runtimeService) {
            runtimeService = SpringContextUtils.getBean(RuntimeService.class);
        }
        return runtimeService;
    }

    public RepositoryService getRepositoryService() {
        if (null == repositoryService) {
            repositoryService = SpringContextUtils.getBean(RepositoryService.class);
        }
        return repositoryService;
    }

    private CommonAPI baseApi;

    private TaskService taskService;

    private RuntimeService runtimeService;

    private RepositoryService repositoryService;
    /**所有的第三方Service，必须采用懒加载方式-------END--------**/

    /**流程中可能会需要使用到的变量数据-----BEGIN-----**/
    /**
     * 业务数据对应ID（冗余字段，方便理解）
     */
    protected String businessKey;
    /**
     * 业务数据对应ID
     */
    protected String BPM_DATA_ID;
    /**
     * BPM 流程对应的表单KEY（流程配置中，定义的字段：表名/自定义表单CODE，大部分情况下，就是关联的业务表名）
     */
    protected String BPM_FORM_KEY;
    /**
     * BPM 流程对应的流程业务KEY（即Activity中绑定的业务Key，同：BPM_DATA_ID（即业务id）。
     */
    protected String BPM_FORM_BUSINESSKEY;
    /**
     * BPM 业务标题表达式(全局)（流程配置中，定义的字段：业务标题表达式）
     */
    protected String bpm_biz_title;
    /**
     * BPM 节点对应的表单URL(全局)（流程发起中，定义的formUrl）
     */
    protected String BPM_FORM_CONTENT_URL;
    /**
     * 当前任务的流程变量
     */
    protected Map<String, Object> variables;

    /**
     * 我的代办跳转按钮html
     */
    public final static String A_BUTTON_MY_TODO_HTML = "<br/>请点击“<a href=\"/bpm/task/MyTaskList\">我的待办</a>”处理任务。";

    /**
     * 初始化基础流程变量数据<br/>
     * 预处理方法在这里统一调用
     */
    protected void installVariables(TaskEntity taskEntity, ActivitiEntityEventImpl eventImpl) {
        this.BPM_DATA_ID = (String) taskEntity.getVariable(WorkFlowGlobals.BPM_DATA_ID);
        this.businessKey = this.BPM_DATA_ID;
        this.BPM_FORM_KEY = (String) taskEntity.getVariable(WorkFlowGlobals.BPM_FORM_KEY);
        this.BPM_FORM_BUSINESSKEY = (String) taskEntity.getVariable(WorkFlowGlobals.BPM_FORM_BUSINESSKEY);
        this.bpm_biz_title = (String) taskEntity.getVariable(WorkFlowGlobals.BPM_BIZ_TITLE);
        this.BPM_FORM_CONTENT_URL = (String) taskEntity.getVariable(WorkFlowGlobals.BPM_FORM_CONTENT_URL);
        this.variables = taskEntity.getVariables();
        // 节点完成时，往request注入：1.会签发起人 2.任务历史处理人
        this.installNextUserIdAfterSign(taskEntity, eventImpl);
        this.installNextUserIdAfterHistoric(taskEntity, eventImpl);
        // 节点创建时，从request域读取并注入处理人
        this.installNextUserIdByDefault(taskEntity, eventImpl);
    }

    /**
     * 在任务创建生命周期做预处理<br/>
     * 初始化默认处理人，如果assignee不存在，默认指定为流程发起人，即：applyUserId
     */
    protected void installNextUserIdByDefault(TaskEntity taskEntity, ActivitiEntityEventImpl eventImpl) {
        if (eventImpl.getType() != ActivitiEventType.TASK_CREATED) {
            return;
        }
        /**
         * 值得注意的是，在这个全局监听器中，优先级是比较高的，因此来自业务表单的数据：nextUserId还没有被自动注入
         * 因此这里直接通过截取请求上下文，获取业务表单的数据
         * 实现功能：
         * 1.优先设置下一节点处理人（当且仅当没有assignee、candidates时）
         * 2.针对会签任务创建时，activity会为每个处理人，各自创建一个任务，
         *   比如选了2个人：admin、yoko，那么创建两个task，每个task自动分配assignee，
         *   因此只需要判断assignee存在时，不再注入nextUserId即可
         * 3.后续的自定义全局监听中就能够正常发送消息了
         * 4.注入会签发起人
         * PS：如果是会签节点，会多出一个变量：String assigneeUserId = data.get("assigneeUserIdList");
         */
        Optional.ofNullable(SpringContextUtils.getHttpServletRequest()).flatMap(request -> Optional.ofNullable((Map<String, String>) request.getAttribute("data"))).ifPresent(data -> {
            String nextUserId = data.get("nextUserId");
            // 注入会签人发起人
            if (StringUtils.isEmpty(nextUserId)) {
                nextUserId = data.get(taskEntity.getTaskDefinitionKey() + "_sign");
            }
            // 注入目标节点的历史处理人
            if (StringUtils.isEmpty(nextUserId)) {
                nextUserId = data.get(taskEntity.getTaskDefinitionKey() + "_historic");
            }
            // 是否用前端选择后的处理人，覆盖默认配置的处理人
            if (StringUtils.hasText(nextUserId) && overrideNodeDefaultUserByCustomSelect) {
                String[] userIds = nextUserId.split(",");
                // 1个人
                if (userIds.length == 1) {
                    taskEntity.setAssignee(userIds[0]);
                }
                // 多个人转化成候选人
                if (userIds.length > 1) {
                    // 需要判断是否是会签，会签需要一个个指派人，然后剔除后更新request域
                    if (this.isSignNode(taskEntity.getName())) {
                        taskEntity.setAssignee(userIds[0]);
                        String restUserIds = Arrays.stream(userIds).skip(1).collect(Collectors.joining(","));
                        data.put("nextUserId", restUserIds);
                        SpringContextUtils.getHttpServletRequest().setAttribute("data", data);
                    } else {
                        taskEntity.setAssignee(null);
                        taskEntity.getCandidates().clear();
                        for (String userId : userIds) {
                            taskEntity.addCandidateUser(userId);
                        }
                    }
                }
            }
            // 最终的默认人判断
            String assignee = taskEntity.getAssignee();
            Set<IdentityLink> candidates = taskEntity.getCandidates();
            if (StringUtils.hasText(nextUserId) && StringUtils.isEmpty(assignee) && candidates.size() == 0) {
                String[] userIds = nextUserId.split(",");
                taskEntity.setAssignee(userIds[0]);
            }
        });
        /**
         * 流程图中，若任务节点没有配置变量：${applyUserId}
         * assignee将是空值，任务不会派发给指定人，因此需要做预处理（一般用于流程发起时的节点自动指派人员）
         * 1.驳回节点时，不抛异常，直接返回
         * 2.禁用自动填充空处理人时，抛出错误提示
         * 3.属于忽略节点，保持不变
         * 4.其余情况默认填充${applyUserId}
         */
        String assignee = taskEntity.getAssignee();
        Set<IdentityLink> candidates = taskEntity.getCandidates();
        if (StringUtils.isEmpty(assignee) && candidates.size() == 0) {
            if (!this.autoFillApplyUserId && !this.isAutoFillIgnoreNode(taskEntity.getName())) {
                throw new ActivitiException(" 下一节点名称：" + taskEntity.getName() + "，没有配置默认处理人，请选则处理人！");
            } else if (this.isAutoFillIgnoreNode(taskEntity.getName())) {
                taskEntity.setAssignee(assignee);
            } else {
                String applyUserId = (String) this.variables.get("applyUserId");
                taskEntity.setAssignee(applyUserId);
            }
        }
    }

    /**
     * 在任务完成生命周期做预处理<br/>
     * 会签任务完成时，自动回归给会签发起人（如果节点没有指定默认处理人时）<br/>
     * 注：此方法往request域中注入了对应会签发起人节点的历史处理人，key为taskDefinitionKey+"_sign"<br/>
     * 注：实际的注入在 {@see installNextUserIdByDefault} 中
     */
    protected void installNextUserIdAfterSign(TaskEntity taskEntity, ActivitiEntityEventImpl eventImpl) {
        if (eventImpl.getType() != ActivitiEventType.TASK_COMPLETED) {
            return;
        }
        // 先确保有request域，然后确保是正常提交操作
        Optional.ofNullable(SpringContextUtils.getHttpServletRequest()).flatMap(request -> Optional.ofNullable((Map<String, String>) request.getAttribute("data"))).ifPresent(data -> {
            if (!"1".equals(data.get("processModel"))) {
                return;
            }
            // 在会签节点结束后，自动判断下一步是否有处理人，没有就注入最早的节点创建人
            List<PvmTransition> outgoingTransitions = taskEntity.getExecution().getActivity().getOutgoingTransitions();
            outgoingTransitions.forEach(transition -> {
                PvmActivity source = transition.getSource();
                String sourceName = (String) source.getProperty("name");
                // 根据注解配置，仅在源节点是配置中的会签节点处理
                if (this.isSignNode(sourceName)) {
                    Optional.ofNullable(transition.getDestination()).flatMap(destination -> Optional.ofNullable((TaskDefinition) destination.getProperty(
                            "taskDefinition"))).ifPresent(taskDefinition -> {
                        HistoryService historyService = SpringContextUtils.getBean(HistoryService.class);
                        List<HistoricTaskInstance> history = historyService.createHistoricTaskInstanceQuery()
                                .processInstanceId(taskEntity.getProcessInstanceId())
                                .taskDefinitionKey(taskDefinition.getKey()).orderByTaskCreateTime().desc().list();
                        if (history.size() > 0) {
                            // 注入到request域
                            data.put(taskDefinition.getKey() + "_sign", history.get(0).getAssignee());
                            SpringContextUtils.getHttpServletRequest().setAttribute("data", data);
                        }
                    });
                }
            });
        });
    }

    /**
     * 在任务完成生命周期做预处理<br/>
     * （存在历史任务时|任务驳回时），自动回归给目标节点的历史处理人（如果节点没有指定处理人时）<br/>
     * 注：此方法往request域中注入了驳回节点处理人，key为taskDefinitionKey+"_historic"<br/>
     * 注：实际的注入在 {@see installNextUserIdByDefault} 中
     */
    protected void installNextUserIdAfterHistoric(TaskEntity taskEntity, ActivitiEntityEventImpl eventImpl) {
        if (eventImpl.getType() != ActivitiEventType.TASK_COMPLETED) {
            return;
        }
        // 先确保有request域，然后确保是驳回操作
        Optional.ofNullable(SpringContextUtils.getHttpServletRequest()).flatMap(request -> Optional.ofNullable((Map<String, String>) request.getAttribute("data"))).ifPresent(data -> {
            // 全局开关优先，然后依据驳回开关和全局的逻辑或结果，仅驳回开启时过滤驳回节点
            boolean flag = this.autoFillHistoricApplyUserId || this.autoFillHistoricRejectApplyUserId;
            if (this.autoFillHistoricApplyUserId) {
            } else if (!flag) {
                return;
            } else if (StringUtils.isEmpty(data.get("rejectModelNode")) && !"3".equals(data.get("processModel"))) {
                return;
            }
            List<PvmTransition> outgoingTransitions = taskEntity.getExecution().getActivity().getOutgoingTransitions();
            outgoingTransitions.forEach(transition -> {
                Optional.ofNullable(transition.getDestination()).flatMap(destination -> Optional.ofNullable((TaskDefinition) destination.getProperty("taskDefinition"))).ifPresent(taskDefinition -> {
                    HistoryService historyService = SpringContextUtils.getBean(HistoryService.class);
                    List<HistoricTaskInstance> history = historyService.createHistoricTaskInstanceQuery()
                            .processInstanceId(taskEntity.getProcessInstanceId())
                            .taskDefinitionKey(taskDefinition.getKey()).orderByTaskCreateTime().desc().list();
                    if (history.size() > 0) {
                        // 注入到request域
                        data.put(taskDefinition.getKey() + "_historic", history.get(0).getAssignee());
                        SpringContextUtils.getHttpServletRequest().setAttribute("data", data);
                    }
                });
            });
        });
    }

    /**
     * 获取当前业务数据
     */
    protected Map<String, Object> getFormData() {
        return SpringContextUtils.getBean(IExtActProcessService.class).getDataById(BPM_FORM_KEY, BPM_DATA_ID);
    }

    /**流程中可能会需要使用到的变量数据-----END-----**/

    /**
     * ExecutionListener的实现
     *
     * @deprecated 替换使用ActivitiEventListener的实现
     */
    @Override
    public void notify(DelegateExecution execution) throws Exception {
        log.info("监听事件：{}, 注：基于实现ExecutionListener接口的监听器，非全局任务监听器。" +
                "以监听类型`start`为例，仅在流程开始时触发一次！此方式无法在旧版本流程配置中无法满足需求！" +
                "如果需要实现全局监听发送消息，使用ActivitiEventListener + ProcessEngineConfigurationConfigurer。", execution.getEventName());
    }

    @Override
    public void onEvent(ActivitiEvent event) {
        this.taskCreatedHandler(event);
        this.taskCompletedHandler(event);
        this.processCompletedHandler(event);
        this.processOrActivityCancelledHandler(event);
    }

    @Override
    public boolean isFailOnException() {
        return true;
    }

    /**
     * @param event
     * @return void
     * @author Yoko
     * @date 2022/3/25 14:31
     * @description 任务创建触发
     */
    protected void taskCreatedHandler(ActivitiEvent event) {
        String name = this.getBindProcessDefinitionName();
        String key = this.getBindProcessDefinitionKey();
        if (ActivitiEventType.TASK_CREATED == event.getType()) {
            ActivitiEntityEventImpl eventImpl = (ActivitiEntityEventImpl) event;
            TaskEntity taskEntity = (TaskEntity) eventImpl.getEntity();
            String processDefinitionName = taskEntity.getProcessInstance().getProcessDefinition().getName();
            String processDefinitionKey = taskEntity.getProcessInstance().getProcessDefinition().getKey();
            if (StringUtils.hasText(name) && processDefinitionName.contains(name)) {
                log.info("流程名称：{}，生命周期:任务创建，开始时间：{}", name, LocalDateTime.now().format(formatter));
                this.installVariables(taskEntity, eventImpl);
                this.customTaskCreatedHandler(taskEntity);
                log.info("流程名称：{}，生命周期:任务创建，结束时间：{}", name, LocalDateTime.now().format(formatter));
            } else if (StringUtils.hasText(key) && processDefinitionKey.contains(key)) {
                log.info("流程名称：{}，生命周期:任务创建，开始时间：{}", name, LocalDateTime.now().format(formatter));
                this.installVariables(taskEntity, eventImpl);
                this.customTaskCreatedHandler(taskEntity);
                log.info("流程名称：{}，生命周期:任务创建，结束时间：{}", name, LocalDateTime.now().format(formatter));
            }
        }
    }

    /**
     * @param event
     * @return void
     * @author Yoko
     * @date 2022/3/25 14:31
     * @description 任务完成触发
     */
    protected void taskCompletedHandler(ActivitiEvent event) {
        String name = this.getBindProcessDefinitionName();
        String key = this.getBindProcessDefinitionKey();
        if (ActivitiEventType.TASK_COMPLETED == event.getType()) {
            ActivitiEntityEventImpl eventImpl = (ActivitiEntityEventImpl) event;
            TaskEntity taskEntity = (TaskEntity) eventImpl.getEntity();
            String processDefinitionName = taskEntity.getProcessInstance().getProcessDefinition().getName();
            String processDefinitionKey = taskEntity.getProcessInstance().getProcessDefinition().getKey();
            if (StringUtils.hasText(name) && processDefinitionName.contains(name)) {
                log.info("流程名称：{}，生命周期:任务完成，开始时间：{}", name, LocalDateTime.now().format(formatter));
                this.installVariables(taskEntity, eventImpl);
                this.customTaskCompletedHandler(taskEntity);
                log.info("流程名称：{}，生命周期:任务完成，结束时间：{}", name, LocalDateTime.now().format(formatter));
            } else if (StringUtils.hasText(key) && processDefinitionKey.contains(key)) {
                log.info("流程名称：{}，生命周期:任务完成，开始时间：{}", name, LocalDateTime.now().format(formatter));
                this.installVariables(taskEntity, eventImpl);
                this.customTaskCompletedHandler(taskEntity);
                log.info("流程名称：{}，生命周期:任务完成，结束时间：{}", name, LocalDateTime.now().format(formatter));
            }
        }
    }

    /**
     * @param event
     * @return void
     * @author Yoko
     * @date 2022/3/25 14:32
     * @description 流程完成触发
     */
    protected void processCompletedHandler(ActivitiEvent event) {
        String name = this.getBindProcessDefinitionName();
        String key = this.getBindProcessDefinitionKey();
        if (ActivitiEventType.PROCESS_COMPLETED == event.getType()) {
            ActivitiEntityEventImpl eventImpl = (ActivitiEntityEventImpl) event;
            ExecutionEntity executionEntity = (ExecutionEntity) eventImpl.getEntity();
            String processDefinitionName = executionEntity.getProcessDefinition().getName();
            String processDefinitionKey = executionEntity.getProcessDefinition().getKey();
            if (StringUtils.hasText(name) && processDefinitionName.contains(name)) {
                log.info("流程名称：{}，生命周期:流程完成，开始时间：{}", name, LocalDateTime.now().format(formatter));
                this.customProcessCompletedHandler(executionEntity);
                this.doUpdateStatus(executionEntity);
                log.info("流程名称：{}，生命周期:流程完成，结束时间：{}", name, LocalDateTime.now().format(formatter));
            } else if (StringUtils.hasText(key) && processDefinitionKey.contains(key)) {
                log.info("流程名称：{}，生命周期:流程完成，开始时间：{}", name, LocalDateTime.now().format(formatter));
                this.customProcessCompletedHandler(executionEntity);
                this.doUpdateStatus(executionEntity);
                log.info("流程名称：{}，生命周期:流程完成，结束时间：{}", name, LocalDateTime.now().format(formatter));
            }
        }
    }

    /**
     * @param event
     * @return void
     * @author Yoko
     * @date 2022/3/25 14:32
     * @description 流程取消（流程取回、流程作废）触发
     */
    protected void processOrActivityCancelledHandler(ActivitiEvent event) {
        String name = this.getBindProcessDefinitionName();
        String key = this.getBindProcessDefinitionKey();
        ProcessDefinition processDefinition =
                this.getRepositoryService().getProcessDefinition(event.getProcessDefinitionId());
        if (name.contains(processDefinition.getName()) || key.contains(processDefinition.getKey())) {
            if (ActivitiEventType.PROCESS_CANCELLED == event.getType()) {
                log.info("流程名称：{}，流程编码：{}，生命周期:Process流程取消，开始时间：{}", name, key, LocalDateTime.now().format(formatter));
                this.customProcessCancelledHandler(event);
                log.info("流程名称：{}，流程编码：{}，生命周期:Process流程取消，结束时间：{}", name, key, LocalDateTime.now().format(formatter));

            }
            if (ActivitiEventType.ACTIVITY_CANCELLED == event.getType()) {
                log.info("流程名称：{}，流程编码：{}，生命周期:Activity流程取消，开始时间：{}", name, key, LocalDateTime.now().format(formatter));
                this.customProcessCancelledHandler(event);
                log.info("流程名称：{}，流程编码：{}，生命周期:Activity流程取消，结束时间：{}", name, key, LocalDateTime.now().format(formatter));
            }
        }
    }

    /**
     * @param taskEntity
     * @return void
     * @author Yoko
     * @date 2022/3/24 15:01
     * @description 生命周期：任务创建，自定义实现相关任务的业务处理
     */
    protected abstract void customTaskCreatedHandler(TaskEntity taskEntity);

    /**
     * @param taskEntity
     * @return void
     * @author Yoko
     * @date 2022/3/24 15:02
     * @description 生命周期：任务完成，自定义实现相关任务的业务处理
     */
    protected abstract void customTaskCompletedHandler(TaskEntity taskEntity);

    /**
     * @param executionEntity
     * @return void
     * @author Yoko
     * @date 2022/3/25 11:22
     * @description 生命周期：流程完成，自定义实现相关任务的业务处理
     */
    protected void customProcessCompletedHandler(ExecutionEntity executionEntity) {

    }

    /**
     * @param activitiEvent
     * @return void
     * @author Yoko
     * @date 2022/3/25 11:51
     * @description 生命周期：流程完成，自定义实现相关任务的业务处理
     */
    protected void customProcessCancelledHandler(ActivitiEvent activitiEvent) {

    }

    /**
     * @param userList
     * @param taskName
     * @return void
     * @author Yoko
     * @description 发送站内消息
     */
    protected void sendMessage(List<String> userList, String taskName) {
        MessageDTO msg = new MessageDTO("system", "", "系统提示", " 您有新的任务需要审批!");
        String title = String.format("您有新的任务待审批，流程名称：%s，任务名称：%s，当前环节：%s。", this.getBindProcessDefinitionName(), this.bpm_biz_title, taskName);
        msg.setTitle(title);
        msg.setContent(title + "<br/>请在“我的待办”模块处理任务。");
        for (String username : userList) {
            msg.setToUser(username);
            getBaseApi().sendSysAnnouncement(msg);
            // todo 发送短信等等
        }
        log.info("节点：{} 消息通知发送成功", taskName);
    }

    /**
     * @param taskEntity
     * @return void
     * @author Yoko
     * @date 2022/3/24 15:43
     * @description 发送站内消息
     */
    protected void sendMessage(TaskEntity taskEntity) {
        String title = String.format("您有新的任务待审批，流程名称：%s，任务名称：%s，当前环节：%s。", this.getBindProcessDefinitionName(), this.bpm_biz_title, taskEntity.getName());
        String content = title + A_BUTTON_MY_TODO_HTML;
        sendMessage(taskEntity, title, content);
    }

    protected void sendMessage(TaskEntity taskEntity, String title, String content) {
        List<String> userList = this.getUsernameList(taskEntity);
        String taskName = taskEntity.getName();
        MessageDTO msg = new MessageDTO("system", "", "系统提示", " 您有新的任务需要审批!");
        msg.setTitle(title);
        msg.setContent(content);
        for (String username : userList) {
            msg.setToUser(username);
            getBaseApi().sendSysAnnouncement(msg);
            // todo 发送短信等等
        }
        log.info("节点：{} 消息通知发送成功", taskName);
    }

    protected void sendMessage(MessageDTO msg) {
        getBaseApi().sendSysAnnouncement(msg);
    }

    /**
     * @param title
     * @param taskEntity
     * @return void
     * @author Yoko
     * @date 2022/3/24 15:41
     * @description 发送待办消息给OA
     */
    protected void sendTodoAndSave(String title, TaskEntity taskEntity, String orgCode) {
        List<String> usernameList = this.getUsernameList(taskEntity, orgCode);
        usernameList = CollectionUtil.distinct(usernameList);
        List<String> usernames = new ArrayList<>(usernameList.size());
        List<String> phones = new ArrayList<>(usernameList.size());
        for (String username : usernameList) {
            LoginUser sysUser = getBaseApi().getUserByName(username);
            String realname = sysUser.getRealname();
            String phone = sysUser.getPhone();
            if (StringUtils.hasText(realname) && StringUtils.hasText(phone)) {
                usernames.add(realname);
                phones.add(phone);
            }
        }
        // return;
        oaTodoInterface.sendTodoAndSave(title, usernames, phones, taskEntity.getProcessInstanceId());
    }

    protected void sendTodoAndSave(TaskEntity taskEntity, String orgCode) {
        String title = String.format("您有新的任务待审批，流程名称：%s，任务名称：%s，当前环节：%s。", this.getBindProcessDefinitionName(), this.bpm_biz_title, taskEntity.getName());
        this.sendTodoAndSave(title, taskEntity, orgCode);
    }

    /**
     * @param taskEntity
     * @return void
     * @author Yoko
     * @date 2022/3/24 16:41
     * @description 需要提供异步方法，防止发送消息阻塞线程
     */
    protected void sendTodoAndSaveAsync(TaskEntity taskEntity) {
        this.executeAsync(() -> this.sendTodoAndSave(taskEntity, ""));
    }

    protected void sendTodoAndSaveAsync(TaskEntity taskEntity, String orgCode) {
        this.executeAsync(() -> this.sendTodoAndSave(taskEntity, orgCode));
    }

    protected void sendTodoAndSaveAsync(String title, TaskEntity taskEntity, String orgCode) {
        this.executeAsync(() -> this.sendTodoAndSave(title, taskEntity, orgCode));
    }

    /**
     * @param taskEntity
     * @return void
     * @author Yoko
     * @date 2022/3/24 16:48
     * @description 完成OA代办消息
     */
    protected void finishTodo(TaskEntity taskEntity) {
        oaTodoInterface.finishTodo(taskEntity.getProcessInstanceId());
    }

    protected void finishTodoAsync(TaskEntity taskEntity) {
        this.executeAsync(() -> this.finishTodo(taskEntity));
    }

    /**
     * 根据角色信息设置候选人
     *
     * @param taskEntity 任务实体
     * @param role 角色编码
     * @param orgCode 过滤到指定的部门
     * @return List<String> 候选人列表
     * @author Yoko
     * @since 2022/6/6 10:15
     */
    protected List<String> addCandidateUserByRole(TaskEntity taskEntity, String role, String orgCode) {
        // 先清除原先的候选人、角色配置
        taskEntity.getCandidates().clear();
        TaskService taskService = SpringContextUtils.getBean(TaskService.class);
        taskService.deleteCandidateGroup(taskEntity.getId(), role);
        taskEntity.deleteCandidateGroup(role);

        // 清除无配置角色时的默认发起人
        taskEntity.setAssignee(null);

        // 这一步再重新复制角色信息
        taskEntity.addCandidateGroup(role);

        // 从系统里找出
        List<String> usernameList = this.getUsernameList(taskEntity, orgCode);
        for (String username : usernameList) {
            taskService.addCandidateUser(taskEntity.getId(), username);
            taskEntity.addCandidateUser(username);
        }

        // 移除用户组的影响
        taskEntity.deleteCandidateGroup(role);

        return usernameList;
    }

    /**
     * 根据角色信息设置处理人
     *
     * @param taskEntity
     * @param role
     * @param orgCode
     * @return java.util.List<java.lang.String>
     * @author Yoko
     * @since 2022/6/6 10:42
     */
    protected List<String> addCandidateUserAndClaimByRole(TaskEntity taskEntity, String role, String orgCode) {
        // 先清除原先的候选人、角色配置
        taskEntity.getCandidates().clear();
        TaskService taskService = SpringContextUtils.getBean(TaskService.class);
        taskService.deleteCandidateGroup(taskEntity.getId(), role);
        taskEntity.deleteCandidateGroup(role);

        // 清除无配置角色时的默认发起人
        taskEntity.setAssignee(null);

        // 这一步再重新复制角色信息
        taskEntity.addCandidateGroup(role);

        // 从系统里找出
        List<String> usernameList = this.getUsernameList(taskEntity, orgCode);
        for (String username : usernameList) {
            taskService.addCandidateUser(taskEntity.getId(), username);
            taskEntity.addCandidateUser(username);
        }

        // 移除用户组的影响
        taskEntity.deleteCandidateGroup(role);

        // 让第一个人直接签收
        taskService.claim(taskEntity.getId(), usernameList.get(0));
        return usernameList;
    }

    /**
     * 获取用户账号
     *
     * @param taskEntity
     * @return
     */
    protected List<String> getUsernameList(TaskEntity taskEntity, String orgCode) {
        /**
         * 优先获取选中的人进行发送消息
         * 1.installVariables中，对于流程图配置了默认处理人的节点，如果选择了其他人，也不会改变默认配置人
         * 2.但是Jeecg的后续监听器中，有地方改变了taskEntity的处理人
         * 3.因此，在这里优先判断返回前端选择的其他人
         * Todo 添加配置，使用前端选择的人去覆盖默认配置处理人
         */
        // String nextUserId =
        //         Optional.ofNullable(SpringContextUtils.getHttpServletRequest()).flatMap(request -> Optional.ofNullable((Map<String, String>) request
        //         .getAttribute(
        //                 "data"))).map(e -> e.get("nextUserId")).orElse("");
        // if (StringUtils.hasText(nextUserId)) {
        //     return Arrays.asList(nextUserId.split(","));
        // }
        // 前端没有优先选人时，走taskEntity内部各种流程图内的配置和判断
        List<String> list = new ArrayList<>();
        String username = taskEntity.getAssignee();
        if (username == null) {
            Set<IdentityLink> set = taskEntity.getCandidates();
            for (IdentityLink identityLink : set) {
                String partUsername = identityLink.getUserId();
                if (partUsername == null) {
                    // 备选角色
                    String roleCode = identityLink.getGroupId();

                    // 根据角色编码查询用户账号集合 -如没有 需自定义
                    List<SysUserModel> sysUserModels = getBaseApi().getUserModelByRoleCodes(roleCode);
                    List<String> usernames = sysUserModels.stream()
                            // 过滤到指定部门，如传入：A01，仅会找到 A01A02、A01A03
                            .filter(sysUser -> {
                                if (StringUtils.hasText(orgCode)) {
                                    if (StringUtils.hasText(sysUser.getOrgCode())) {
                                        return sysUser.getOrgCode().indexOf(orgCode) == 0;
                                    } else if (StringUtils.hasText(sysUser.getDepartIds())) {
                                        long count = getBaseApi().getAllSysDepart(sysUser.getDepartIds(), CommonConstant.DEL_FLAG_0 + "")
                                                .stream()
                                                .filter(sysDepart -> sysDepart.getOrgCode().indexOf(orgCode) == 0)
                                                .count();
                                        return count > 0;
                                    } else {
                                        return false;
                                    }
                                }
                                return true;
                            })
                            .map(SysUserModel::getUsername).collect(Collectors.toList());
                    list.addAll(usernames);
                } else {
                    // 备选人员
                    list.add(partUsername);
                }
            }
        } else {
            // 处理人 直接添加
            list.add(username);
        }
        return list;
    }

    protected List<String> getUsernameList(TaskEntity taskEntity) {
        return this.getUsernameList(taskEntity, "");
    }

    /**
     * @param preTaskName
     * @param taskEntity
     * @return void
     * @author Yoko
     * @date 2022/1/11 12:33
     * @description 指定人物处理人（基于Jeecg的流程历史表：ext_act_bpm_log）
     */
    protected void assignTaskToPre(String preTaskName, TaskEntity taskEntity, Consumer<TaskEntity> callback) {
        // 解决：任务委托后，委托人处理完后整改回复不能自动继承委托人身份的Bug（多线程异步+TaskService）
        this.executeAsync(() -> {
            if (StringUtils.hasText(preTaskName)) {
                log.info("自动指派历史节点 {} 处理人为当前节点 {} 签收人--BEGIN", preTaskName, taskEntity.getName());
                this.sleep();
                // Jeecg的历史还没有创建，因此第一次进入为空，代替使用Activity自带的HistoryService（异步操作下此情况不发生）
                ExtActBpmLog history = SpringContextUtils.getBean(IExtActBpmLogService.class)
                        .getOne(new LambdaQueryWrapper<ExtActBpmLog>()
                                .orderByDesc(ExtActBpmLog::getOpTime)
                                .eq(ExtActBpmLog::getBusinessKey, taskEntity.getExecution().getBusinessKey())
                                .eq(ExtActBpmLog::getTaskName, preTaskName)
                                .last("limit 1"));
                if (history != null) {
                    taskEntity.setAssignee(history.getOpUserId());
                    SpringContextUtils.getBean(TaskService.class)
                            .setAssignee(taskEntity.getId(), taskEntity.getAssignee());
                }
                log.info("自动指派历史节点 {} 处理人为当前节点 {} 签收人--END", preTaskName, taskEntity.getName());
            }
            this.sendMessage(taskEntity);
            callback.accept(taskEntity);
        });
    }

    protected void assignTaskToPre(String preTaskName, TaskEntity taskEntity) {
        this.assignTaskToPre(preTaskName, taskEntity, taskEntity1 -> {
        });
    }

    /**
     * @param taskEntity
     * @param remarks
     * @return void
     * @author Yoko
     * @date 2022/1/11 12:26
     * @description 保存流程记录
     */
    protected void saveToHistory(TaskEntity taskEntity, Object remarks) {
        this.executeAsync(() -> {
            log.info("保存流程记录--BEGIN--{}", taskEntity.getName());
            this.sleep();
            IExtActBpmLogService logService = SpringContextUtils.getBean(IExtActBpmLogService.class);
            ExtActBpmLog actBpmLog = logService.getOne(
                    new LambdaQueryWrapper<ExtActBpmLog>()
                            .eq(ExtActBpmLog::getBusinessKey, taskEntity.getExecution().getBusinessKey())
                            .eq(ExtActBpmLog::getTaskName, taskEntity.getName())
                            .orderByDesc(ExtActBpmLog::getOpTime)
                            .last("limit 1")
            );
            if (null != actBpmLog) {
                actBpmLog.setRemarks(String.valueOf(remarks));
                logService.updateById(actBpmLog);
                // todo 保存历史数据到业务表，这个后期再做
            }
            log.info("保存流程记录--END--{}", taskEntity.getName());
        });
    }

    /**
     * 更新业务表单的流程状态
     * 源码参考反编译的Jeecg流程结束监听
     *
     * @see ProcessEndListener
     */
    private void doUpdateStatus(ExecutionEntity executionEntity) {
        String businessKey = executionEntity.getBusinessKey();
        if (StringUtils.hasText(businessKey)) {
            ExtActProcessMapper extActProcessMapper = SpringContextUtils.getBean(ExtActProcessMapper.class);
            ExtActFlowDataMapper extActFlowDataMapper = SpringContextUtils.getBean(ExtActFlowDataMapper.class);
            ExtActDesignFlowDataMapper extActDesignFlowDataMapper = SpringContextUtils.getBean(ExtActDesignFlowDataMapper.class);
            String bpmStatus = (String) variables.get("bpm_status");
            LambdaQueryWrapper<ExtActFlowData> lqw;
            ExtActFlowData extActFlowData;
            String formTableName;
            String bpmStatusField;
            /**
             * 1.取回流程，这里结束监听里是走不到此分支的
             * 2.作废流程，结束监听同理取不到
             * 3.其它状态（1、2、3、4、5），从这里可以知道，源码中，发起取回或作废时，bpmStatus被赋值成了String常量值，因此其他值的流程状态走到这里，默认走向完成流程
             */
            if (WorkFlowGlobals.PROCESS_CALLBACKPROCESS_STATUS.equals(bpmStatus)) {
                lqw = new LambdaQueryWrapper();
                lqw.eq(ExtActFlowData::getFormDataId, businessKey);
                lqw.eq(ExtActFlowData::getProcessInstId, executionEntity.getProcessInstanceId());
                extActFlowData = extActFlowDataMapper.selectOne(lqw);
                if (extActFlowData != null) {
                    formTableName = extActFlowData.getFormTableName().toUpperCase();
                    bpmStatusField = extActFlowData.getBpmStatusField().toUpperCase();
                    if (StringUtils.isEmpty(bpmStatusField)) {
                        bpmStatusField = "BPM_STATUS";
                    }
                    extActProcessMapper.updateBpmStatusById(formTableName, businessKey, bpmStatusField, WorkFlowGlobals.BPM_BUS_STATUS_1);
                    extActFlowDataMapper.deleteById(extActFlowData.getId());
                }
            } else if (WorkFlowGlobals.PROCESS_INVALIDPROCESS_STATUS.equals(bpmStatus)) {
                lqw = new LambdaQueryWrapper();
                lqw.eq(ExtActFlowData::getFormDataId, businessKey);
                lqw.eq(ExtActFlowData::getProcessInstId, executionEntity.getProcessInstanceId());
                extActFlowData = extActFlowDataMapper.selectOne(lqw);
                if (extActFlowData != null) {
                    formTableName = extActFlowData.getFormTableName().toUpperCase();
                    log.debug("-------tableName--------" + formTableName);
                    bpmStatusField = extActFlowData.getBpmStatusField().toUpperCase();
                    if (StringUtils.isEmpty(bpmStatusField)) {
                        bpmStatusField = "BPM_STATUS";
                    }

                    extActProcessMapper.updateBpmStatusById(formTableName, businessKey, bpmStatusField, WorkFlowGlobals.BPM_BUS_STATUS_4);
                    extActFlowData.setBpmStatus(WorkFlowGlobals.BPM_BUS_STATUS_4);
                    extActFlowDataMapper.updateById(extActFlowData);
                }
            } else {
                lqw = new LambdaQueryWrapper();
                lqw.eq(ExtActFlowData::getFormDataId, businessKey);
                lqw.eq(ExtActFlowData::getProcessInstId, executionEntity.getProcessInstanceId());
                extActFlowData = extActFlowDataMapper.selectOne(lqw);
                if (extActFlowData != null) {
                    formTableName = extActFlowData.getFormTableName().toUpperCase();
                    bpmStatusField = extActFlowData.getBpmStatusField().toUpperCase();
                    if (StringUtils.isEmpty(bpmStatusField)) {
                        bpmStatusField = "BPM_STATUS";
                    }

                    extActProcessMapper.updateBpmStatusById(formTableName, businessKey, bpmStatusField, WorkFlowGlobals.BPM_BUS_STATUS_3);
                    extActFlowData.setBpmStatus(WorkFlowGlobals.BPM_BUS_STATUS_3);
                    extActFlowDataMapper.updateById(extActFlowData);
                    // Todo 下面的是表单设计器的状态变量修改，可以注释掉
                    String bpmDesFormCode = this.getString(executionEntity.getVariable(WorkFlowGlobals.BPM_DES_FORM_CODE));
                    String bpmDesDataId = this.getString(executionEntity.getVariable(WorkFlowGlobals.BPM_DES_DATA_ID));
                    String designFormRelationTableName = extActDesignFlowDataMapper.getDesignFormRelationTableName(bpmDesFormCode);
                    DesignFormDataDTO designFormDataDTO = extActDesignFlowDataMapper.getDesignFormDataById(bpmDesDataId);
                    log.debug("----  流程结束监听，修改表单设计器的数据  ---");
                    log.debug("--- dbTableName： " + designFormRelationTableName);
                    log.debug("--- bpmDesDataId： " + bpmDesDataId);
                    log.debug("--- col： " + bpmStatusField);
                    if (StringUtils.hasText(designFormRelationTableName) && designFormDataDTO != null && StringUtils.hasText(designFormDataDTO.getOnlineFormDataId())) {
                        log.debug("--- designFormDataDto.getOnlineFormDataId()： " + designFormDataDTO.getOnlineFormDataId());
                        extActProcessMapper.updateBpmStatusById(designFormRelationTableName, designFormDataDTO.getOnlineFormDataId(), bpmStatusField, WorkFlowGlobals.BPM_BUS_STATUS_3);
                    }
                }
            }

        }
    }

    /**
     * @param taskId
     * @return void
     * @author Yoko
     * @date 2022/5/13 10:03
     * @description 完成任务
     */
    protected void completeTask(String taskId) {
        this.getTaskService().complete(taskId);
    }

    protected void completeTask(TaskEntity taskEntity) {
        this.getTaskService().complete(taskEntity.getId());
    }

    /**
     * @param processInstanceId
     * @param reason
     * @return void
     * @author Yoko
     * @date 2022/5/13 10:03
     * @description 完成流程
     */
    protected void completeProcess(String processInstanceId, String reason) {
        this.getRuntimeService().deleteProcessInstance(processInstanceId, reason);
    }

    protected void executeAsync(Runnable task) {
        SpringContextUtils.getBean(ThreadPoolTaskExecutor.class).execute(task);
    }

    protected void executeAsync(Consumer<? super YokoGlobalAbstractListener> consumer, long startTimeout) {
        this.executeAsync(() -> {
            this.sleep(startTimeout);
            consumer.accept(this);
        });
    }

    protected String joinStrs(CharSequence charSequence, String... strs) {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (String str : strs) {
            if (StringUtils.hasText(str)) {
                if (first) {
                    first = false;
                } else {
                    sb.append(charSequence);
                }
                sb.append(str);
            }
        }
        return sb.toString();
    }

    protected String getString(Object object) {
        if (StringUtils.isEmpty(object)) {
            return "";
        }
        return (object.toString().trim());
    }

    protected void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
    }

    protected void sleep(long timemillis) {
        try {
            Thread.sleep(timemillis);
        } catch (InterruptedException ignored) {
        }
    }

    /**
     * 判断是否是会签节点，支持正则
     *
     * @param nodeName
     * @return boolean
     * @author Yoko
     * @since 2022/9/7 15:42
     */
    protected boolean isSignNode(String nodeName) {
        boolean flag = false;
        for (String signTaskName : this.signTaskNames) {
            if (signTaskName.equals(nodeName)) {
                flag = true;
                break;
            }
            if (nodeName.contains(signTaskName)) {
                flag = true;
                break;
            }
            if (nodeName.matches(signTaskName)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    protected boolean isAutoFillIgnoreNode(String nodeName) {
        boolean flag = false;
        for (String ignoreTaskName : this.autoFillIgnoreTaskNames) {
            if (ignoreTaskName.equals(nodeName)) {
                flag = true;
                break;
            }
            if (nodeName.contains(ignoreTaskName)) {
                flag = true;
                break;
            }
            if (nodeName.matches(ignoreTaskName)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

}
