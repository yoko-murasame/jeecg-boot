package org.design.core.tool.config;

import org.design.core.tool.utils.SpringUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration(proxyBeanMethods = false)
@Order(Integer.MIN_VALUE)
/* loaded from: bigdata-core-tool-3.1.0.jar:org/design/core/tool/config/ToolConfiguration.class */
public class ToolConfiguration implements WebMvcConfigurer {
    @Bean
    public SpringUtil springUtil() {
        return new SpringUtil();
    }
}
