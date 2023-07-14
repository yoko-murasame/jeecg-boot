package org.jeecg.modules.listener.easyoa;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.modules.extbpm.process.common.WorkFlowGlobals;
import org.jeecg.modules.extbpm.process.service.IExtActProcessService;
import org.jeecg.modules.listener.entity.OaOfficialdocDeUser;
import org.jeecg.modules.listener.service.IOaOfficialdocDeUserService;

import java.util.Map;

/**
 * 公文收文分发监听
 */
@Slf4j
public class ReveicedStartListener implements ExecutionListener {
    private static IExtActProcessService extActProcessService;
    private static final long serialVersionUID = 1L;
    static {
        extActProcessService = SpringContextUtils.getBean(IExtActProcessService.class);
    }

    @Override
    public void notify(DelegateExecution execution) throws Exception {
        String bpmDataId = (String) execution.getVariable(WorkFlowGlobals.BPM_DATA_ID);
        String businessKey = (String) execution.getVariable(WorkFlowGlobals.BPM_FORM_KEY);
        Map<String, Object> dataForm = extActProcessService.getDataById(businessKey, bpmDataId);
        Object circulationDepts = dataForm.get("circulation_depts");
        IOaOfficialdocDeUserService deUserService = SpringContextUtils.getBean(IOaOfficialdocDeUserService.class);
        ISysBaseAPI iSysBaseAPI = SpringContextUtils.getBean(ISysBaseAPI.class);
        String[] depts = circulationDepts.toString().split(",");
            String ids="";
            for (String departId:
                    depts) {
                OaOfficialdocDeUser oaOfficialdocDeUser = deUserService.selectByDepartId(departId);
                if(null!=oaOfficialdocDeUser){
                    String userId = oaOfficialdocDeUser.getUserId();
                    //---author:liusq---data:2021-01-07----for:userId为多个逗号分隔的情况----begin---
                    String[] userIds = userId.split(",");
                    for (String id : userIds) {
                        //通过用户id查询用户账号
                        LoginUser userById = iSysBaseAPI.getUserById(id);
                        String username = userById.getUsername();
                        if(!ids.contains(username)){
                            ids=ids+username+",";
                        }
                    }
                    //---author:liusq---data:2021-01-07----for:userId为多个逗号分隔的情况---end---
                } else{
                    throw new RuntimeException("请选择负责部门！");
                }
            }
            if(!"".equals(ids)){
                execution.setVariable("assigneeUserIdList", ids.substring(0,ids.length()-1));
            }

    }

}
