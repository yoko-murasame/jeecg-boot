## Jeecg微服务架构改造-添加Dubbo支持

折腾了一个通宵，为了解决下面问题：

1. 引入Dubbo的RPC调用功能（有别于Feign的HTTP调用）
2. 实现Jeecg框架的"core包->CommonAPI"和"biz包->API实现类"解耦
2. 兼容Spring Cloud、单体、Dubbo三种启动方式

解决方案如下：（各种POM依赖、特殊异常就不描述了，总之过程异常艰辛）

1. 新增jeecg-system-dubbo-start启动模块，依赖biz模块
2. 在该start模块中编写Dubbo配置，封装相关类用于判断启动环境为Dubbo
3. 在biz模块中实现Dubbo的生产者相关逻辑（大量尝试..）
4. 调试Dubbo生产者模块启动过程中的各种参数、解决报错（这里要注意每个Dubbo服务提供者模块的端口配置dubbo.protocol.port不能冲突）
5. 细化划分dubbo-system-api模块，并在这里实现CommonAPI的消费者相关逻辑
6. 这里解决了个天坑：CommonAPI在Core包中无法被RPC调用，具体表现为Shiro相关逻辑里调用API抛出ClassNotFound
7. 实现、测试实际业务微服务，需要注意：每个业务微服务既是生产者，也是消费者（依赖jeecg-system-dubbo）
8. 至此，Dubbo集成结束，并完美兼容Spring Cloud、单体、Dubbo三种启动方式
