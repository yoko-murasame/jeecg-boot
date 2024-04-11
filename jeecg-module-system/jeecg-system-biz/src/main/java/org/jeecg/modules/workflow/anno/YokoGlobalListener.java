package org.jeecg.modules.workflow.anno;

import org.activiti.engine.delegate.event.ActivitiEventType;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Yoko
 * @date 2022/3/24 17:27
 * @description 自定义全局监听处理器发现注解，自动注入监听器
 */
@Target(value = {ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface YokoGlobalListener {

    @AliasFor(annotation = Component.class)
    String value() default "";

    /**
     * 关联流程定义的名称
     */
    String processDefinitionName() default "";

    /**
     * 关联流程编码
     */
    String processDefinitionKey() default "";

    /**
     * 流程节点没有配置角色信息时，是否自动注入发起人<br/>
     * 当设置成 false 时，节点处理人为空将自动抛异常
     */
    boolean autoFillApplyUserId() default false;

    /**
     * 流程节点注入忽略任务<br/>
     * 仅当 autoFillApplyUserId = false 时生效
     * 支持正则匹配
     */
    String[] autoFillIgnoreTaskNames() default {".*?自动完成.*?"};

    /**
     * 指定会签节点名称，自动处理最后一个会签节点返回给会签发起人
     * 支持正则匹配
     */
    String[] signTaskNames() default {".*?会签.*?"};

    /**
     * 是否开启自动注入目标节点历史处理人（无配置固定处理人时）<br/>
     * 此项配置为 true 时，选项 autoFillHistoricRejectApplyUserId 失效
     */
    boolean autoFillHistoricApplyUserId() default true;

    /**
     * 是否开启自动注入驳回目标节点历史处理人（无配置固定处理人时）
     */
    boolean autoFillHistoricRejectApplyUserId() default true;

    /**
     * 关联流程的生命周期<br/>
     * 默认提供：TASK_CREATED、TASK_COMPLETED、PROCESS_COMPLETED<br/>
     * 目前实现：TASK_CREATED、TASK_COMPLETED、PROCESS_COMPLETED、PROCESS_CANCELLED、ACTIVITY_CANCELLED
     */
    ActivitiEventType[] eventTypes() default {};

    /**
     * 是否用前端选择后的处理人，覆盖默认配置的处理人
     * 此配置必须配置忽略的会签节点，否则会签节点创建多个会签子任务时，会导致每次request域的前端选择处理人变成任务候选人进行分配任务
     */
    boolean overrideNodeDefaultUserByCustomSelect() default true;
}
