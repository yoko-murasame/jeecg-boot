package org.jeecg.modules.workflow.listener;

import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.jeecg.common.api.dto.message.MessageDTO;
import org.jeecg.modules.workflow.anno.YokoGlobalListener;
import org.jeecg.modules.workflow.entity.EditApply;
import org.jeecg.modules.workflow.service.EditApplyService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.List;

/**
 * 通用编辑权限申请流程-监听处理器
 *
 * @author Yoko
 * @date 2022/5/10 16:43
 */
@YokoGlobalListener(processDefinitionKey = "EditApply", processDefinitionName = "编辑权限申请流程")
public class EditApplyListener extends YokoGlobalAbstractListener {

    @Autowired
    private EditApplyService editApplyService;

    @Override
    protected void customTaskCreatedHandler(TaskEntity taskEntity) {

        List<String> userList = this.getUsernameList(taskEntity);
        String applyUserId = (String) super.variables.get("applyUserId");
        MessageDTO msg = new MessageDTO(userList.get(0), applyUserId, "", "");
        switch (taskEntity.getName()) {
            case "授权":
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.HOUR, EditApply.EXPIRED_HOUR);
                // 授权 EditApply.EXPIRED_HOUR 小时内编辑权限
                editApplyService.edit(new EditApply().setId(super.businessKey).setCanEdit(EditApply.CAN_EDIT).setExpireTime(calendar.getTime()));
                super.completeTask(taskEntity);
                msg.setTitle("编辑权限申请成功");
                msg.setContent(String.format("您的业务《%s》，编辑权限申请成功，有效期限%d小时！", super.bpm_biz_title, EditApply.EXPIRED_HOUR));
                super.sendMessage(msg);
                // oa
                super.sendTodoAndSaveAsync("编辑权限申请成功", taskEntity, "");
                break;
            case "不授权":
                EditApply editApply = new EditApply().setId(super.businessKey).setCanEdit(EditApply.CAN_NOT_EDIT).setExpireTime(null);
                editApplyService.edit(editApply);
                // 不授权时，需要在这里手动删除历史流程（注意这个流程还没完，需要异步执行，等待久一点没事）
                super.executeAsync(e -> editApplyService.deleteProcess(editApply), 3000);
                msg.setTitle("编辑权限申请失败");
                msg.setContent(String.format("您的业务《%s》，编辑权限申请失败，具体原因请联系主管！", super.bpm_biz_title));
                super.sendMessage(msg);
                super.completeTask(taskEntity);
                // oa
                super.sendTodoAndSaveAsync("编辑权限申请成功", taskEntity, "");
                break;
            default:
                // 站内消息
                super.sendMessage(taskEntity);
                // oa推送
                super.sendTodoAndSaveAsync(taskEntity);
                break;
        }
    }

    @Override
    protected void customTaskCompletedHandler(TaskEntity taskEntity) {
        // oa待办完成
        super.finishTodoAsync(taskEntity);
    }
}
