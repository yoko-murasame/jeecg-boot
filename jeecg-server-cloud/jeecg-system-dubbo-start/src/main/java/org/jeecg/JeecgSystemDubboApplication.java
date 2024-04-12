package org.jeecg;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(scanBasePackages = "org.jeecg")
@EnableSwagger2
@EnableDubbo(scanBasePackages = {"${dubbo.scan.basePackages:org.jeecg.common}"})
@EnableCaching
@EnableScheduling
public class JeecgSystemDubboApplication {
}
