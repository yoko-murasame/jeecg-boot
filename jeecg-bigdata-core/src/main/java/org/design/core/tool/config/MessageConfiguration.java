package org.design.core.tool.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.design.core.tool.utils.Charsets;
import org.springframework.http.converter.*;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

// @Configuration(proxyBeanMethods = false)
// @Order(Integer.MIN_VALUE)
/* loaded from: bigdata-core-tool-3.1.0.jar:org/design/core/tool/config/MessageConfiguration.class */
public class MessageConfiguration implements WebMvcConfigurer {
    private final ObjectMapper objectMapper;

    public MessageConfiguration(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.removeIf(x -> {
            return (x instanceof StringHttpMessageConverter) || (x instanceof AbstractJackson2HttpMessageConverter);
        });
        converters.add(new StringHttpMessageConverter(Charsets.UTF_8));
        converters.add(new ByteArrayHttpMessageConverter());
        converters.add(new ResourceHttpMessageConverter());
        converters.add(new ResourceRegionHttpMessageConverter());
    }
}
