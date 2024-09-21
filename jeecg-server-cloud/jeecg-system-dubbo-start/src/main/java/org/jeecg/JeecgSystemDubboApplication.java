package org.jeecg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@SpringBootApplication
@EnableSwagger2WebMvc
@EnableCaching
@EnableScheduling
@EnableDiscoveryClient
public class JeecgSystemDubboApplication {
    public static void main(String[] args) {
        SpringApplication.run(JeecgSystemDubboApplication.class, args);
    }
}
