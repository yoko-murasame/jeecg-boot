package org.jeecg.modules.workflow.config;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.delegate.event.impl.ActivitiEventSupport;

/**
 * 自定义事件支持器
 *
 * @author Yoko
 * @since 2022/6/9 19:40
 */
public class YokoActivitiEventSupport extends ActivitiEventSupport {

    @Override
    protected void dispatchEvent(ActivitiEvent event, ActivitiEventListener listener) {
        try {
            listener.onEvent(event);
        } catch (Throwable t) {
            if (listener.isFailOnException()) {
                throw new ActivitiException(t.getMessage());
            }
        }
    }
}
