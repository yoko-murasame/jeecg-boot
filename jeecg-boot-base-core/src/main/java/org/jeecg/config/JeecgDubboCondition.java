package org.jeecg.config;

import org.jeecg.common.constant.CommonConstant;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * Dubbo环境加载条件
 */
public class JeecgDubboCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Object object = context.getEnvironment().getProperty(CommonConstant.DUBBO_SERVER_KEY);
        //如果没有dubbo注册发现的配置 说明是单体应用
        if(object==null){
            return false;
        }
        return true;
    }
}
