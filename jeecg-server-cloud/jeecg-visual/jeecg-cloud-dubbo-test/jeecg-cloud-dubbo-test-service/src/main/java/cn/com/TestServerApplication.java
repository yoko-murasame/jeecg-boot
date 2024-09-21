package cn.com;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 启动类-Test
 * FIXME 仅供参考，实际项目中各微服务保留一个即可
 */
@SpringBootApplication
@ComponentScan(
        basePackages = {"org.jeecg", "cn.com"},
        excludeFilters = @ComponentScan.Filter(classes = {
                org.jeecg.config.mybatis.MybatisPlusSaasConfig.class,
                org.jeecg.config.Swagger2Config.class
        }, type = FilterType.ASSIGNABLE_TYPE)
)
@EnableDubbo(scanBasePackages = {"${dubbo.scan.basePackages:cn.com.**.provider.rpc}"})
@EnableDiscoveryClient
@EnableCaching
@EnableScheduling
public class TestServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestServerApplication.class, args);
    }
}
