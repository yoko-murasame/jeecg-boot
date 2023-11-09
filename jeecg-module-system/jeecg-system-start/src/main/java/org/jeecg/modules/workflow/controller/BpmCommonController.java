package org.jeecg.modules.workflow.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.modules.bpm.dto.ProcessHisDTO;
import org.jeecg.modules.extbpm.process.entity.ExtActFlowData;
import org.jeecg.modules.extbpm.process.entity.ExtActProcess;
import org.jeecg.modules.extbpm.process.entity.ExtActProcessForm;
import org.jeecg.modules.extbpm.process.mapper.ExtActProcessMapper;
import org.jeecg.modules.extbpm.process.service.IExtActFlowDataService;
import org.jeecg.modules.workflow.entity.TaskDTO;
import org.jeecg.modules.workflow.entity.TaskEntity;
import org.jeecg.modules.workflow.service.BpmCommonService;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

/**
 * 自定义流程功能通用实现
 *
 * @author Yoko
 * @since 2022/7/21 10:55
 */
@Api(tags = "流程通用-自定义流程功能通用实现")
@RestController
@RequestMapping("/workflow/common")
@Slf4j
public class BpmCommonController {

    @Resource
    private BpmCommonService bpmCommonService;

    @ApiOperation("获取Biz流程节点信息")
    @GetMapping({"/getBizHisProcessNodeInfo"})
    public Result<Map<String, Object>> getBizHisProcessNodeInfo(@RequestParam(name = "flowCode") String flowCode, @RequestParam(name = "dataId") String dataId) {
        Map<String, Object> res = bpmCommonService.getBizHisProcessNodeInfo(flowCode, dataId);
        return Result.OK(res);
    }

    @ApiOperation("获取图片资源(最新版本的)-通过processKey")
    @GetMapping({"/process/resource"})
    public void getBpmResourceByProcess(@RequestParam("processKey") String processKey, @ApiIgnore HttpServletResponse response) {
        this.bpmCommonService.getBpmResourceByProcess(processKey, response);
    }

    @ApiOperation("获取图片资源-通过deployId")
    @GetMapping({"/deploy/resource"})
    public void getBpmResourceByDeployment(@RequestParam String deploymentId,
                                           @RequestParam String resourceName,
                                           @ApiIgnore HttpServletResponse response) {
        this.bpmCommonService.getBpmResourceByDeployment(deploymentId, resourceName, response);
    }

    @ApiOperation("获取流程审批记录打印数据")
    @GetMapping({"/report/queryReportById"})
    public JSONObject queryReportById(@RequestParam("id") String id) {
        return this.bpmCommonService.queryReportById(id);
    }

    @ApiOperation("审批进度-流程跟踪(图像悬浮数据) 接管接口")
    @GetMapping({"getNodePositionInfo"})
    public Result<Map<String, Object>> getNodePositionInfo(@RequestParam("processInstanceId") String processInstanceId) {
        Map<String, Object> result = this.bpmCommonService.getNodePositionInfo(processInstanceId);
        return Result.OK(result);
    }

    @ApiOperation("审批进度-流程历史跟踪(列表数据) 接管接口")
    @GetMapping({"/processHistoryList"})
    public Result<IPage<ProcessHisDTO>> processHistoryList(@RequestParam("processInstanceId") String processInstId) {
        Page<ProcessHisDTO> page = new Page<>();
        List<ProcessHisDTO> res = this.bpmCommonService.processHistoryList(processInstId);
        page.setRecords(res);
        page.setTotal(res.size());
        return Result.OK(page);
    }

    @ApiOperation("查询审批记录接口")
    @GetMapping({"/getHisProcessTaskTransInfo"})
    public Result<Map<String, Object>> getHisProcessTaskTransInfo(@RequestParam(name = "procInstId") String procInstId) {
        HashMap<String, Object> hashMap = this.bpmCommonService.getHisProcessTaskTransInfo(procInstId);
        return Result.OK(hashMap);
    }


    @ApiOperation("取回流程")
    @GetMapping(value = "/callBackProcess")
    public Result<?> callBackProcess(@RequestParam(required = false) String id,
                                     @RequestParam(required = false) String processInstanceId) {
        this.bpmCommonService.callBackProcess(id, processInstanceId);
        return Result.OK();
    }

    @ApiOperation("取回Activity流程实例接口")
    @GetMapping(value = "/callBackProcessInstance")
    public Result<?> callBackProcess(@RequestParam(required = false) String processInstanceId) {
        this.bpmCommonService.callBackProcessInstance(processInstanceId);
        return Result.OK();
    }


    @ApiOperation("直接完成流程")
    @GetMapping(value = "/finishProcess/{id}")
    public Result<?> finishProcess(@PathVariable("id") String id) {
        this.bpmCommonService.finishProcess(id);
        return Result.OK();
    }

    @ApiOperation("获取我的待办列表")
    @GetMapping({"/myTaskList"})
    public Result<IPage<TaskDTO>> myTaskList(HttpServletRequest request) {
        log.debug("------------进入myTaskList---------------");
        long start = System.currentTimeMillis();
        Result<IPage<TaskDTO>> result = new Result<>();
        Page<TaskDTO> page = new Page<>();
        String username = JwtUtil.getUserNameByToken(request);
        // 先请求总数，为0时直接放行
        Long taskSize = this.bpmCommonService.countPriTodaoTask(username, request);
        long end = System.currentTimeMillis();
        log.debug(" 获取我的任务总数: " + taskSize + ", 耗时: " + (end - start) + "ms");
        List<TaskDTO> todoTasks = new ArrayList<>();
        // 总数大于0再查列表，可减少sql查询
        if (taskSize > 0) {
            todoTasks = this.bpmCommonService.findPriTodoTasks(username, request);
            log.debug("获取我的任务分页列表 耗时：" + (System.currentTimeMillis() - end) + "ms");
        }
        page.setRecords(todoTasks);
        page.setTotal(taskSize);
        result.setSuccess(true);
        result.setResult(page);
        log.debug("------------退出myTaskList---------------");
        return result;
    }

    @Deprecated
    @ApiOperation(value = "完成流程通用方法", notes = "完成流程通用方法")
    @PostMapping(value = "/finishHistoryActivity")
    public Result<?> finishHistoryActivity(@RequestParam(required = false) String dateBegin, String dateEnd) {
        RuntimeService runtimeService = SpringContextUtils.getBean(RuntimeService.class);
        IExtActFlowDataService extActFlowDataService = SpringContextUtils.getBean(IExtActFlowDataService.class);
        ExtActProcessMapper extActProcessMapper = SpringContextUtils.getBean(ExtActProcessMapper.class);
        TaskService taskService = SpringContextUtils.getBean(TaskService.class);
        // List<ProcessInstance> list = runtimeService.createNativeProcessInstanceQuery().sql("SELECT * FROM
        // act_ru_task WHERE create_time_ < '2022-05-06 20:00'").list();

        Assert.hasText(dateEnd, "form参数：dateEnd不能为空（dateBegin可选）！格式：yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        List<Task> oldTasks = null;
        try {
            LocalDateTime endDateTime = LocalDateTime.parse(dateEnd, dateTimeFormatter);
            Date end = Date.from(endDateTime.atZone(ZoneId.systemDefault()).toInstant());
            TaskQuery taskQuery = taskService.createTaskQuery().taskCreatedBefore(end);
            if (StringUtils.hasText(dateBegin)) {
                LocalDateTime beginDateTime = LocalDateTime.parse(dateBegin, dateTimeFormatter);
                Date begin = Date.from(beginDateTime.atZone(ZoneId.systemDefault()).toInstant());
                taskQuery = taskQuery.taskCreatedAfter(begin);
            }
            oldTasks = taskQuery.list();
            oldTasks.forEach(task -> {
                try {
                    // 流程引擎中完成
                    runtimeService.setVariable(task.getProcessInstanceId(), "bpm_status", "3");
                    runtimeService.deleteProcessInstance(task.getProcessInstanceId(), "admin自动完成");

                    List<ExtActFlowData> extActFlowData =
                            extActFlowDataService.list(new LambdaQueryWrapper<ExtActFlowData>().eq(ExtActFlowData::getProcessInstId,
                                    task.getProcessInstanceId()));
                    // 再完成jeecg里的流转数据
                    if (extActFlowData.size() > 0) {
                        ExtActFlowData flowData = extActFlowData.get(0);
                        flowData.setBpmStatus("3");
                        extActFlowDataService.updateById(flowData);
                        // 业务表
                        String formTableName = flowData.getFormTableName();
                        String bpmStatusField = flowData.getBpmStatusField();
                        if (StringUtils.isEmpty(bpmStatusField)) {
                            bpmStatusField = "bpm_status";
                        }
                        String formDataId = flowData.getFormDataId();
                        extActProcessMapper.updateBpmStatusById(formTableName, formDataId, bpmStatusField, "3");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (DateTimeParseException e1) {
            return Result.error("form参数：dateEnd不能为空（dateBegin可选）！格式：yyyy-MM-dd HH:mm:ss");
        } catch (Exception e) {
            return Result.error("未知异常：" + e.getMessage());
        }

        return Result.OK();
    }

    @ApiOperation(value = "发起流程", notes = "发起流程")
    @PostMapping({"startMutilProcess"})
    public Result<String> startMutilProcess(@RequestBody HashMap<String, String> param, HttpServletRequest request) {
        String flowCode = (String)param.get("flowCode");
        String id = (String)param.get("id");
        String formUrl = (String)param.get("formUrl");
        String formUrlMobile = (String)param.get("formUrlMobile");
        String username = JwtUtil.getUserNameByToken(request);
        return this.bpmCommonService.startMutilProcess(flowCode, id, formUrl, formUrlMobile, username);
    }

    @ApiOperation(value = "任务委派", notes = "任务委派")
    @RequestMapping(value = "/taskEntrust", method = {RequestMethod.POST, RequestMethod.PUT})
    public Result<?> taskEntrust(@RequestBody JSONObject param, HttpServletRequest request) {
        String taskId = param.getString("taskId");
        String taskAssignee = param.getString("taskAssignee");
        this.bpmCommonService.taskEntrust(taskId, taskAssignee, request);
        return Result.OK();
    }

    @ApiOperation(value = "获取ExtActProcess", notes = "获取ExtActProcess")
    @GetMapping({"getExtActProcess"})
    public Result<ExtActProcess> getExtActProcess(ExtActProcessForm param) {
        Result<ExtActProcess> extActProcess = this.bpmCommonService.getExtActProcess(param);
        extActProcess.getResult().setProcessXml(null);
        return extActProcess;
    }

    @ApiOperation("获取系统代办完整数据接口")
    @RequestMapping(value = "taskPage", method = RequestMethod.GET)
    public Result<?> taskPage(TaskEntity taskEntity,
                              @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                              @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                              HttpServletRequest request) {
        QueryWrapper<TaskEntity> queryWrapper = QueryGenerator.initQueryWrapper(taskEntity, request.getParameterMap());
        Page<TaskEntity> page = new Page<TaskEntity>(pageNo, pageSize);
        Page<TaskEntity> taskEntityPage = this.bpmCommonService.taskPage(page, queryWrapper);
        return Result.OK(taskEntityPage);
    }

}
