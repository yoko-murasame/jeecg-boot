package org.jeecg.config.init;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.jeecg.config.JeecgDubboCondition;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * Dubbo提供者配置
 *
 * @author Yoko
 * @since 2024/4/13 5:49
 */
@Configuration
@EnableDubbo(scanBasePackages = {"${dubbo.scan.basePackages:org.jeecg.modules}"})
@Conditional(JeecgDubboCondition.class)
public class DubboProviderConfig {
    // 配置Dubbo的其他相关配置，例如注册中心、协议、端口等
}
