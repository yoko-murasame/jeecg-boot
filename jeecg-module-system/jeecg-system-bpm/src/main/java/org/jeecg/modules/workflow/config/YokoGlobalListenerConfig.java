package org.jeecg.modules.workflow.config;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.jeecg.modules.workflow.anno.YokoGlobalListener;
import org.jeecg.modules.workflow.listener.YokoGlobalAbstractListener;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

/**
 * 全局监听器发现注册类
 *
 * @author Yoko
 * @see YokoGlobalListener
 */
@DependsOn(value = {
        "springProcessEngineConfiguration"
})
@Component
@Slf4j
public class YokoGlobalListenerConfig implements ApplicationListener, ApplicationContextAware {

    @Autowired
    private SpringProcessEngineConfiguration configuration;

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    public void init() {
        Map<String, Object> listeners = applicationContext.getBeansWithAnnotation(YokoGlobalListener.class);
        Set<Map.Entry<String, Object>> entries = listeners.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            YokoGlobalAbstractListener listener = (YokoGlobalAbstractListener) entry.getValue();
            Class<?> clazz = listener.getClass();
            YokoGlobalListener yokoGlobalListener = AnnotationUtils.findAnnotation(clazz, YokoGlobalListener.class);
            if (null == yokoGlobalListener) {
                continue;
            }
            if (!StringUtils.hasText(yokoGlobalListener.processDefinitionKey()) && !StringUtils.hasText(yokoGlobalListener.processDefinitionName())) {
                log.error("监听器注册失败：{}，关联流程为空，请检查注解@YokoGlobalListener配置！", clazz.getName());
                System.exit(0);
            }
            listener.setBindProcessDefinitionKey(yokoGlobalListener.processDefinitionKey());
            listener.setBindProcessDefinitionName(yokoGlobalListener.processDefinitionName());
            listener.setAutoFillApplyUserId(yokoGlobalListener.autoFillApplyUserId());
            listener.setAutoFillIgnoreTaskNames(Arrays.asList(yokoGlobalListener.autoFillIgnoreTaskNames()));
            listener.setSignTaskNames(Arrays.asList(yokoGlobalListener.signTaskNames()));
            listener.setAutoFillHistoricApplyUserId(yokoGlobalListener.autoFillHistoricApplyUserId());
            listener.setAutoFillHistoricRejectApplyUserId(yokoGlobalListener.autoFillHistoricRejectApplyUserId());
            listener.setOverrideNodeDefaultUserByCustomSelect(yokoGlobalListener.overrideNodeDefaultUserByCustomSelect());
            listener.setUpdateVariablesAfterTaskComplete(yokoGlobalListener.updateVariablesAfterTaskComplete());
            // 直接通过dispatch注册到configuration
            if (null == configuration.getEventDispatcher() || !configuration.getEventDispatcher().getClass().getName().contains("YokoActivitiEventDispatcherImpl")) {
                configuration.setEventDispatcher(new YokoActivitiEventDispatcherImpl());
                configuration.setEnableEventDispatcher(true);
            }
            /*
             * 监听类型注册
             * 默认提供：TASK_CREATED、TASK_COMPLETED、PROCESS_COMPLETED
             * 目前实现：TASK_CREATED、TASK_COMPLETED、PROCESS_COMPLETED、PROCESS_CANCELLED、ACTIVITY_CANCELLED
             */
            ActivitiEventType[] activitiEventTypes = yokoGlobalListener.eventTypes();
            if (activitiEventTypes.length == 0) {
                activitiEventTypes = new ActivitiEventType[]{ActivitiEventType.TASK_CREATED,
                        ActivitiEventType.TASK_COMPLETED, ActivitiEventType.PROCESS_COMPLETED,
                        ActivitiEventType.PROCESS_CANCELLED};
            }
            configuration.getEventDispatcher().addEventListener(listener, activitiEventTypes);
            log.info("监听器注册成功：{}，关联流程名称：{}", clazz.getName(), yokoGlobalListener.processDefinitionName());
        }
    }

    /**
     * 实现接口：ApplicationListener，会触发多次加载，不推荐使用
     */
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
    }

}
