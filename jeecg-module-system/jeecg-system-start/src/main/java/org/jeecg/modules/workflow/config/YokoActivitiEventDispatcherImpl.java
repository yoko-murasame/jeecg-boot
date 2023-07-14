package org.jeecg.modules.workflow.config;

import org.activiti.engine.delegate.event.impl.ActivitiEventDispatcherImpl;

/**
 * 自定义事件转发器
 *
 * @author Yoko
 * @since 2022/6/9 19:45
 */
public class YokoActivitiEventDispatcherImpl extends ActivitiEventDispatcherImpl {

    public YokoActivitiEventDispatcherImpl() {
        eventSupport = new YokoActivitiEventSupport();
    }
}
