package org.jeecg.config;

import org.springframework.context.annotation.Bean;

/**
 * 临时token feign配置
 *
 * @author Yoko
 */
public class TempTokenFeignConfig {

    @Bean
    public TempTokenRequestInterceptor tempTokenRequestInterceptor() {
        return new TempTokenRequestInterceptor();
    }

}
