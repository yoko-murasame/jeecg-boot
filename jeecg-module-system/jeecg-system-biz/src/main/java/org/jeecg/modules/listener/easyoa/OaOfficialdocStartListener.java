package org.jeecg.modules.listener.easyoa;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.modules.extbpm.process.common.WorkFlowGlobals;
import org.jeecg.modules.extbpm.process.service.IExtActProcessService;

import java.util.Map;

/**
 * 公文表单监听
 */
public class OaOfficialdocStartListener  implements ExecutionListener {

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
        String currentActivityName = execution.getCurrentActivityName();
        if("oa_officialdoc_issued".equals(businessKey)){
            if("办公室核稿".equals(currentActivityName)){
                Object reviewUserOpinion = dataForm.get("review_user_opinion");
                if(null==reviewUserOpinion){
                    throw new RuntimeException("请在业务信息处填写办公室核稿意见！");
                }
            }else if("办公室领导核稿".equals(currentActivityName)){
                Object leadersOption = dataForm.get("office_leaders_option");
                if(null==leadersOption){
                    throw new RuntimeException("请在业务信息处填写办公室领导核稿意见！");
                }
            }else if("主办单位审核".equals(currentActivityName)){
                Object sponsorReview = dataForm.get("sponsor_review");
                if(null==sponsorReview){
                    throw new RuntimeException("请在业务信息处填写主办单位审核意见！");
                }
            }else if("部门会签".equals(currentActivityName)){
                Object jointlySigns = dataForm.get("jointly_signs");
                if(null==jointlySigns){
                    throw new RuntimeException("请在表业务信息填写部门会签意见！");
                }
            }else if("领导会签".equals(currentActivityName)){
                Object leaderJointlySigns = dataForm.get("leader_jointly_signs");
                if(null==leaderJointlySigns){
                    throw new RuntimeException("请在业务信息处填写领导会签意见！");
                }
            }else if("秘书审核".equals(currentActivityName)){
                Object secretaryReview = dataForm.get("secretary_review");
                if(null==secretaryReview){
                    throw new RuntimeException("请在业务信息处填写秘书审核意见！");
                }
            }else if("校对".equals(currentActivityName)){
                Object proofreadUser = dataForm.get("proofread_user");
                if(null==proofreadUser){
                    throw new RuntimeException("请在业务信息处填写校对意见！");
                }
            }
        }else if("oa_officialdoc_received".equals(businessKey)){
            if("办公室领导审核".equals(currentActivityName)){
                Object officeComments = dataForm.get("office_comments");
                if(null==officeComments){
                    throw new RuntimeException("请在业务信息处填写办公室领导审核意见！");
                }
            } else if("领导审核".equals(currentActivityName)){
                Object leaderInstructions = dataForm.get("leader_instructions");
                if(null==leaderInstructions){
                    throw new RuntimeException("请在业务信息处填写领导审核意见！");
                }
            } else if("主办部门".equals(currentActivityName)){
                Object sponsorComments = dataForm.get("sponsor_comments");
                if(null==sponsorComments){
                    throw new RuntimeException("请在业务信息处填写主办部门意见！");
                }
            }else if("协办部门".equals(currentActivityName)){
                Object coSponsorComments = dataForm.get("co_sponsor_comments");
                if(null==coSponsorComments){
                    throw new RuntimeException("请在业务信息处填写协办部门意见！");
                }
            }
        }
    }
}
