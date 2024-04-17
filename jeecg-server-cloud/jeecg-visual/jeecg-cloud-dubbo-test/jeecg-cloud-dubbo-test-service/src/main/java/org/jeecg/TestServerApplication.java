package org.jeecg;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 启动类-Test
 * FIXME 仅供参考，实际项目中各微服务保留一个即可
 */
@SpringBootApplication(scanBasePackages = "org.jeecg")
@EnableSwagger2
@EnableDubbo(scanBasePackages = {"${dubbo.scan.basePackages:org.jeecg.modules.provider.rpc}"})
@EnableDiscoveryClient
@EnableCaching
@EnableScheduling
public class TestServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestServerApplication.class, args);
    }
}
