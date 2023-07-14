package org.jeecg.modules.listener.easyoa;


import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.modules.extbpm.process.common.WorkFlowGlobals;
import org.jeecg.modules.extbpm.process.service.IExtActProcessService;
import org.jeecg.modules.listener.entity.OaOfficialdocBute;
import org.jeecg.modules.listener.entity.OaOfficialdocDeUser;
import org.jeecg.modules.listener.service.IOaOfficialdocButeService;
import org.jeecg.modules.listener.service.IOaOfficialdocDeUserService;
import java.util.Date;
import java.util.Map;

/**
 * 公文发文分发监听事件
 */
@Slf4j
public class DistributeIssuedEndListener implements TaskListener {
    private static IExtActProcessService extActProcessService;
    private static final long serialVersionUID = 1L;
    static {
        extActProcessService = SpringContextUtils.getBean(IExtActProcessService.class);
    }

    @Override
    public void notify(DelegateTask delegateTask) {
        //获取主流程实例
        String processInstanceId = delegateTask.getProcessInstanceId();
        DelegateExecution execution = delegateTask.getExecution();
        String bpmDataId = (String) execution.getVariable(WorkFlowGlobals.BPM_DATA_ID);
        String businessKey = (String) execution.getVariable(WorkFlowGlobals.BPM_FORM_KEY);
        Map<String, Object> dataForm = extActProcessService.getDataById(businessKey, bpmDataId);
        Object id = dataForm.get("id");
        Object mainDelivery = dataForm.get("main_delivery");
        Object ccDelivery = dataForm.get("cc_delivery");
        String newDepartId=mainDelivery.toString();
        if(null!=ccDelivery){
            if(!newDepartId.equals(ccDelivery.toString())){
                newDepartId=newDepartId+","+ccDelivery.toString();
            }
        }
        //查询部门下的用户
        IOaOfficialdocDeUserService deUserService = SpringContextUtils.getBean(IOaOfficialdocDeUserService.class);
        for (String departId:
        newDepartId.split(",")) {
            OaOfficialdocDeUser oaOfficialdocDeUser = deUserService.selectByDepartId(departId);
            String userId = oaOfficialdocDeUser.getUserId();
            if(org.apache.commons.lang.StringUtil.isNotBlank(userId)){
                String[] userIds = userId.split(",");
                for (String uId: userIds) {
                    OaOfficialdocBute distribute = new OaOfficialdocBute();
                    distribute.setStatus("0");
                    distribute.setDepartId(departId);
                    distribute.setIssuedId(String.valueOf(id));
                    distribute.setUserId(uId);
                    distribute.setDistributeDate(new Date());
                    distribute.setType("1");
                    IOaOfficialdocButeService distributeService = SpringContextUtils.getBean(IOaOfficialdocButeService.class);
                    distributeService.save(distribute);
                }
            }
        }
    }
}
