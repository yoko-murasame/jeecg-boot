package org.jeecg.common.system.config;

import org.apache.dubbo.config.annotation.DubboReference;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Configuration;

/**
 * Dubbo 消费者配置
 *
 * @author Yoko
 * @since 2024/4/13 5:28
 */
@Configuration
@ConditionalOnMissingClass("org.jeecg.modules.system.service.impl.SysBaseApiImpl")
public class ConsumerConfig {

    /*
     * Jeecg微服务架构改造-添加Dubbo支持
     * 折腾了一个通宵，为了解决这个问题：让Dubbo架构下的"core包->CommonAPI"和"biz包->API实现类"解耦
     * 解决方案如下：（各种POM依赖、特殊异常就不描述了，总之过程异常艰辛）
     * 1.新增jeecg-system-dubbo-start启动模块，依赖biz模块
     * 2.在该start模块中编写Dubbo配置，封装相关类用于判断启动环境为Dubbo
     * 3.在biz模块中实现Dubbo的生产者相关逻辑（大量尝试..）
     * 4.调试Dubbo生产者模块启动过程中的各种参数、解决报错（这里要注意每个Dubbo服务提供者模块的端口配置dubbo.protocol.port不能冲突）
     * 5.细化划分dubbo-system-api模块，并在这里实现CommonAPI的消费者相关逻辑
     * 6.这里解决了个天坑：CommonAPI在Core包中无法被RPC调用，具体表现为Shiro相关逻辑里调用API抛出ClassNotFound
     * 7.实现、测试实际业务微服务，需要注意：每个业务微服务既是生产者，也是消费者（依赖jeecg-system-dubbo）
     * 8.至此，Dubbo集成结束，并完美兼容Spring Cloud、单体、Dubbo三种启动方式
     * 本来是添加一个新的实现类SysBaseApiImplConsumer.java，在里面进行Dubbo注入，发现ShiroRealm中依赖时上下文包含两个接口实现
     * 因此最终放这里即可，通过@DubboReference进行rpc注入，生成一个代理对象注入Spring上下文即可满足
     */
    @DubboReference(interfaceClass = ISysBaseAPI.class)
    private ISysBaseAPI sysBaseApi;

    // @Bean
    // @DubboReference(interfaceClass = ISysBaseAPI.class)
    // public ReferenceBean<ISysBaseAPI> iSysBaseApi() {
    //     return new ReferenceBean<ISysBaseAPI>();
    // }

}
