package org.jeecg.common.system.config;

import org.apache.dubbo.config.annotation.DubboReference;
import org.jeecg.common.system.api.ISysBpmAPI;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Configuration;

/**
 * Dubbo 消费者配置
 *
 * @author Yoko
 */
@Configuration
@ConditionalOnMissingClass("org.jeecg.modules.online.cgform.service.impl.e")
public class ConsumerBpmConfig {

    @DubboReference(interfaceClass = ISysBpmAPI.class)
    private ISysBpmAPI sysBpmAPI;

}
