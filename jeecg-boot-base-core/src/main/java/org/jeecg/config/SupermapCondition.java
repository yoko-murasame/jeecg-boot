package org.jeecg.config;

import org.jeecg.common.constant.CommonConstant;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * 超图环境加载条件
 */
public class SupermapCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Object object = context.getEnvironment().getProperty(CommonConstant.SUPERMAP_KEY);
        //如果没有服务注册发现的配置 说明是单体应用
        if (object == null || object.equals("false")) {
            return false;
        }
        return true;
    }
}
