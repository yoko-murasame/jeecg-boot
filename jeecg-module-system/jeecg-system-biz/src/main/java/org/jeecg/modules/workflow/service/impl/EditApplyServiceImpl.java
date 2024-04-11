package org.jeecg.modules.workflow.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.modules.extbpm.process.entity.ExtActFlowData;
import org.jeecg.modules.extbpm.process.service.IExtActFlowDataService;
import org.jeecg.modules.workflow.entity.EditApply;
import org.jeecg.modules.workflow.mapper.EditApplyMapper;
import org.jeecg.modules.workflow.service.IEditApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.jeecg.modules.workflow.entity.EditApply.*;

/**
 * 通用编辑权限申请流程
 *
 * @author Yoko
 */
@Slf4j
@Service
@CacheConfig(cacheNames = "EditApplyService")
public class EditApplyServiceImpl extends ServiceImpl<EditApplyMapper, EditApply> implements IEditApplyService {

    @Autowired
    private EditApplyMapper editApplyMapper;

    @Override
    @Cacheable(key = "#editApply?.id")
    public EditApply getAndInit(EditApply editApply) {
        Assert.hasText(editApply.getId(), "业务主键不能为空");
        EditApply exist = this.getById(editApply.getId());
        if (null != exist) {
            /**
             * 处理过期时间
             * 截止时间和编辑状态具有原子性：
             * 1.无截止时间 -> 一定不可编辑
             * 2.存在截止时间需要判断过期，已过期 -> 重置状态
             * 3.截止时间非过期 -> 一定可编辑
             */
            Date expireTime = exist.getExpireTime();
            if (null != expireTime) {
                boolean isExpired = expireTime.before(new Date());
                // 已过期 -> 重置状态
                if (isExpired) {
                    exist.setCanEdit(CAN_NOT_EDIT).setExpireTime(null).setBpmStatus(PROCESS_NOT_START);
                    this.edit(exist);
                    // 删除流程记录
                    this.deleteProcess(exist);
                }
                // 截止时间非过期 -> 一定可编辑
                else if (!CAN_EDIT.equals(exist.getCanEdit())) {
                    exist.setCanEdit(CAN_EDIT);
                    this.edit(exist);
                }
            } else {
                String bpmStatus = exist.getBpmStatus();
                String canEdit = exist.getCanEdit();
                /**
                 * 没有截止时间时，存在以下情况
                 * 1.正在申请流程中
                 * 2.不在流程中，且状态不可编辑
                 * 第二点被视为错误状态，需要手动纠错
                 */
                boolean wrongState = !PROCESS_ING.equals(bpmStatus) && !CAN_NOT_EDIT.equals(canEdit);
                if (wrongState) {
                    // 重置数据
                    exist.setCanEdit(CAN_NOT_EDIT).setExpireTime(null).setBpmStatus(PROCESS_NOT_START);
                    this.edit(exist);
                    // 清除错误流程
                    this.deleteProcess(exist);
                }
            }
        }
        if (null == exist) {
            editApply.setCanEdit(CAN_NOT_EDIT).setExpireTime(null).setBpmStatus(PROCESS_NOT_START);
            this.save(editApply);
            exist = editApply;
        }
        return exist;
    }

    @CacheEvict(key = "#record?.id")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteProcess(EditApply record) {
        IExtActFlowDataService flowDataService = SpringContextUtils.getBean(IExtActFlowDataService.class);
        LambdaQueryWrapper<ExtActFlowData> flowDataLambdaQueryWrapper = new LambdaQueryWrapper<ExtActFlowData>()
                .eq(ExtActFlowData::getFormDataId, record.getId())
                .eq(ExtActFlowData::getProcessKey, PROCESS_KEY);
        // 清除错误流程
        List<ExtActFlowData> flowDatas = flowDataService.list(flowDataLambdaQueryWrapper);
        // 删除流程记录
        flowDataService.remove(flowDataLambdaQueryWrapper);
        try {
            if (flowDatas.size() > 0) {
                for (ExtActFlowData flowData : flowDatas) {
                    String processInstId = flowData.getProcessInstId();
                    RuntimeService runtimeService = SpringContextUtils.getBean(RuntimeService.class);
                    ProcessInstance processInstance = (ProcessInstance) runtimeService.createProcessInstanceQuery().processInstanceId(processInstId).singleResult();
                    Optional.ofNullable(processInstance).ifPresent(pi -> {
                        runtimeService.setVariable(processInstId, BPM_STATUS, PROCESS_FINISH);
                        runtimeService.deleteProcessInstance(processInstId, "自动结束错误流程");
                        log.info("getAndInit，{}，流程ID：{}", "自动结束错误流程", processInstId);
                    });
                }
            }
        } catch (Exception e) {
        }
    }

    @CacheEvict(key = "#editApply?.id")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void edit(EditApply editApply) {
        editApplyMapper.updateById(editApply);
    }

}
