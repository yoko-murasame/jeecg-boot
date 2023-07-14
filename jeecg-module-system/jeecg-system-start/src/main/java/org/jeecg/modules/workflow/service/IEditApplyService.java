package org.jeecg.modules.workflow.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.workflow.entity.EditApply;

/**
 * @author Yoko
 */
public interface IEditApplyService extends IService<EditApply> {
    EditApply getAndInit(EditApply editApply);

    void deleteProcess(EditApply record);

    void edit(EditApply editApply);
}
