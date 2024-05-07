package cn.com.hyit.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * 数据源配置
 */
@Configuration
@MapperScan(value={"cn.com.hyit.**.dao*"})
public class MybatisPlusConfig {

}
