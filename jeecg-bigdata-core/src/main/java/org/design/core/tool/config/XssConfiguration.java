package org.design.core.tool.config;

import javax.servlet.DispatcherType;
import org.design.core.tool.support.xss.XssFilter;
import org.design.core.tool.support.xss.XssProperties;
import org.design.core.tool.support.xss.XssUrlProperties;
import org.design.core.tool.utils.StringPool;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties({XssProperties.class, XssUrlProperties.class})
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(value = {"blade.xss.enabled"}, havingValue = StringPool.TRUE)
/* loaded from: bigdata-core-tool-3.1.0.jar:org/design/core/tool/config/XssConfiguration.class */
public class XssConfiguration {
    private final XssProperties xssProperties;
    private final XssUrlProperties xssUrlProperties;

    public XssConfiguration(XssProperties xssProperties, XssUrlProperties xssUrlProperties) {
        this.xssProperties = xssProperties;
        this.xssUrlProperties = xssUrlProperties;
    }

    @Bean
    public FilterRegistrationBean<XssFilter> xssFilterRegistration() {
        FilterRegistrationBean<XssFilter> registration = new FilterRegistrationBean<>();
        registration.setDispatcherTypes(DispatcherType.REQUEST, new DispatcherType[0]);
        registration.setFilter(new XssFilter(this.xssProperties, this.xssUrlProperties));
        registration.addUrlPatterns(new String[]{"/*"});
        registration.setName("xssFilter");
        registration.setOrder(Integer.MAX_VALUE);
        return registration;
    }
}
