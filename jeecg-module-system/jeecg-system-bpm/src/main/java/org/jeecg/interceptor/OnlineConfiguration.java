package org.jeecg.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class OnlineConfiguration implements WebMvcConfigurer {
    public OnlineConfiguration() {
    }

    @Bean
    public OnlineInterceptor onlineInterceptor() {
        return new OnlineInterceptor();
    }

    public void addInterceptors(InterceptorRegistry registry) {
        String[] var2 = new String[]{"/*.html", "/html/**", "/js/**", "/css/**", "/images/**"};
        registry.addInterceptor(this.onlineInterceptor()).excludePathPatterns(var2).addPathPatterns(new String[]{"/online/cgform/api/**"});
    }
}
