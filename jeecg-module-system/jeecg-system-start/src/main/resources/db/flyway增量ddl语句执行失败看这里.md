# 如果flyway执行ddl更新语句失败，需要先关闭wall，执行完框架升级语句后再开启wall

## 先注释spring.datasource.dynamic.druid.filters配置

```yaml
spring:
  datasource:
    druid:
      stat-view-servlet:
        enabled: true
        loginUsername: admin
        loginPassword: 123456@123456
        allow:
      web-stat-filter:
        enabled: true
    dynamic:
      druid: # 全局druid参数，绝大部分值和默认保持一致。(现已支持的参数如下,不清楚含义不要乱设置)
        # 连接池的配置信息
        # 初始化大小，最小，最大
        initial-size: 5
        min-idle: 5
        maxActive: 1000
        # 配置获取连接等待超时的时间
        maxWait: 60000
        # 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
        timeBetweenEvictionRunsMillis: 60000
        # 配置一个连接在池中最小生存的时间，单位是毫秒
        minEvictableIdleTimeMillis: 300000
        validationQuery: SELECT 1
        testWhileIdle: true
        testOnBorrow: false
        testOnReturn: false
        # 打开PSCache，并且指定每个连接上PSCache的大小
        poolPreparedStatements: true
        maxPoolPreparedStatementPerConnectionSize: 20
        # 配置监控统计拦截的filters，去掉后监控界面sql无法统计，'wall'用于防火墙
        # FIXME 如果flyway执行ddl更新语句失败，先关闭wall，执行完框架升级语句后建议开启wall
        # 防火墙规则配置：https://github.com/alibaba/druid/wiki/%E9%85%8D%E7%BD%AE-wallfilter
        # TODO 先注释，执行后再开起来！
        # filters: stat,wall,slf4j # stat,slf4j,wall
```
