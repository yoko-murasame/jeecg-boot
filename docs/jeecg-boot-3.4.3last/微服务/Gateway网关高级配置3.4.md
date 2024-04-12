# Gateway网关高级配置3.4

https://www.kancloud.cn/zhangdaiscott/jeecg-boot/3041089

- - [Gateway路由网关配置](https://www.kancloud.cn/zhangdaiscott/jeecg-boot/3041089#Gateway_1)

  - - [路由加载模式说明](https://www.kancloud.cn/zhangdaiscott/jeecg-boot/3041089#_4)

    - [以下介绍三种模式的配置方式](https://www.kancloud.cn/zhangdaiscott/jeecg-boot/3041089#_12)

    - - [模式一： 数据库配置方式](https://www.kancloud.cn/zhangdaiscott/jeecg-boot/3041089#__17)
      - [模式二：本地yml配置方式](https://www.kancloud.cn/zhangdaiscott/jeecg-boot/3041089#yml_24)
      - [模式三： nacos配置方式](https://www.kancloud.cn/zhangdaiscott/jeecg-boot/3041089#_nacos_51)

  - [GateWay路由条件配置](https://www.kancloud.cn/zhangdaiscott/jeecg-boot/3041089#GateWay_59)

  - [GateWay过滤器限流配置](https://www.kancloud.cn/zhangdaiscott/jeecg-boot/3041089#GateWay_184)

  - - - [限流测试](https://www.kancloud.cn/zhangdaiscott/jeecg-boot/3041089#_221)

## Gateway路由网关配置

jeecg 路由加载支持三种模式： `database数据库` ，`yml本地配置` 、 `nacos配置` ，其中nacos和数据库方式支持动态刷新路由！默认用的是`database数据库`模式。

### 路由加载模式说明

| 配置模式 | 配置加载方式                        |
| :------- | :---------------------------------- |
| database | 走系统的路由管理DB                  |
| yml      | 本地配置文件 application.yml        |
| nacos    | 走nacos的 jeecg-gateway-router.json |

### 以下介绍三种模式的配置方式

切换路由需修改nacos中的 `jeecg-gateway-dev.yml`中路由配置如下图
![img](https://img.kancloud.cn/3c/51/3c51de22de9b7d4c40b93dda1d4d507e_809x889.png)

#### 模式一： 数据库配置方式

修改 `jeecg-gateway-dev.yml`中的 jeecg.route.config.data-type=database
再通过系统管理下的路由配置菜单进行路由维护，路由配置表为sys_gateway_route
这样gateway 路由加载就从数据库中读取。
界面可视化配置参考如下
![img](https://img.kancloud.cn/66/f1/66f1de9ecb22ec987bb3fecb81412df8_863x967.png)

#### 模式二：本地yml配置方式

修改 `jeecg-gateway-dev.yml`中的 jeecg.route.config.data-type=yml，并在里面添加如下路由配置

```
spring:
  cloud:
    gateway:
      routes:
        - id: jeecg-demo
          uri: lb://jeecg-demo
          predicates:
            - Path=/mock/**,/test/**,/bigscreen/template1/**,/bigscreen/template2/**
        - id: jeecg-system
          uri: lb://jeecg-system
          predicates:
            - Path=/sys/**,/eoa/plan/**,/email/**,/oa/im/**,/metting/**,/filemanage/**,/officialdoc/**,/sign/**,/oa/im/**,/cms/**,/chat/eoaCmsMenu/**,/filedisk/**,/im/**,/joa/**,/online/**,/bigscreen/**,/jmreport/**,/design/report/**,/desform/**,/process/**,/act/**,/plug-in/**
        - id: jeecg-system-websocket
          uri: lb:ws://jeecg-system
          predicates:
            - Path=/websocket/**,/eoaSocket/**
        - id: jeecg-demo-websocket
          uri: lb:ws://jeecg-demo
          predicates:
            - Path=/vxeSocket/**
```

#### 模式三： nacos配置方式

修改 `jeecg-gateway-dev.yml`中的 jeecg.route.config.data-type=nacos
再在nacos中新建路由配置文件[ jeecg-gateway-router.json](https://gitee.com/jeecg/jeecg-boot/blob/master/jeecg-boot/jeecg-cloud-module/jeecg-cloud-nacos/docs/config/jeecg-gateway-router.json)

![img](https://img.kancloud.cn/24/75/2475d1c0146fd6b1d95ae06eccfd526a_1857x716.png)

------

## GateWay路由条件配置

![img](https://img.kancloud.cn/0d/3b/0d3b63cf31384b8dfc7785e159fde150_858x441.png)
1、时间点后匹配
yml方式配置

```
spring:
 cloud:
  gateway:
   routes:
    - id: after_route
      uri: https://example.org
      predicates:
       - After=2022-02-20T17:42:47.789-07:00[America/Denver]
```

2、时间点前匹配

```
spring:
 cloud:
  gateway:
   routes:
    - id: before_route
      uri: https://example.org
      predicates:
       - Before=2022-02-20T17:42:47.789-07:00[America/Denver]
```

3、时间区间匹配

```
spring:
 cloud:
  gateway:
   routes:
    - id: between_route
      uri: https://example.org
      predicates:
       - Between=2022-01-20T17:42:47.789-07:00[America/Denver],
2022-01-21T17:42:47.789-07:00[America/Denver]
```

4、指定Cookie正则匹配指定值

```
spring:
 cloud:
  gateway:
   routes:
    - id: cookie_route
      uri: https://example.org
      predicates:
       - Cookie=cookie,china
```

5、指定Header正则匹配指定值

```
spring:
 cloud:
  gateway:
   routes:
    - id: header_route
      uri: https://example.org
      predicates:
       - Header=X-Request-Id
```

6、请求Host匹配指定值

```
spring:
 cloud:
  gateway:
   routes:
    - id: host_route
      uri: https://example.org
      predicates:
       - Host=**.somehost.org,**.anotherhost.org
```

7、请求Method匹配指定请求方式

```
spring:
 cloud:
  gateway:
   routes:
    - id: method_route
      uri: https://example.org
      predicates:
       - Method=GET,POST
```

8、请求路径正则匹配

```
spring:
 cloud:
  gateway:
   routes:
    - id: path_route
      uri: https://example.org
      predicates:
       - Path=/red/{segment},/blue/{segment}
```

9、请求包含某参数

```
spring:
 cloud:
  gateway:
   routes:
    - id: query_route
      uri: https://example.org
      predicates:
       - Query=green
```

10、请求包含某参数并且参数值匹配正则表达式

```
spring:
 cloud:
  gateway:
   routes:
    - id: query_route
      uri: https://example.org
      predicates:
       - Query=red, gree.
```

11、远程地址匹配

```
spring:
 cloud:
  gateway:
  routes:
   - id: remoteaddr_route
     uri: https://example.org
     predicates:
      - RemoteAddr=192.168.1.1/24
```

## GateWay过滤器限流配置

限流方式支持IP/用户接口等方式
ip限流：key-resolver: '#{@ipKeyResolver}'

用户限流：key-resolver: '#{@userKeyResolver}'

接口限流：key-resolver: '#{@apiKeyResolver}'

配置方式一：可视化界面配置
![img](https://img.kancloud.cn/ff/1a/ff1a1393408dce6263cc9f5fab3ca2ac_775x1238.png)
配置方式二：json路由配置

```
"filters": [{
    "name": "RequestRateLimiter",
    "args": {
        "key-resolver": "#{@ipKeyResolver}",
        "redis-rate-limiter.replenishRate": 1,
        "redis-rate-limiter.burstCapacity": 20
    }
}]
```

xml路由配置

```
spring:
  redis:
    host: localhost
filters:
#redis限流, filter名称必须是RequestRateLimiter
- name: RequestRateLimiter
  args:
    # 使用SpEL名称引用Bean，与上面新建的RateLimiterConfig类中的bean的name相同
    key-resolver: '#{@ipKeyResolver}'
    # 每秒最大访问次数
    redis-rate-limiter.replenishRate: 20
    # 令牌桶最大容量
    redis-rate-limiter.burstCapacity: 20
```

#### 限流测试

模拟100个并发
![img](https://img.kancloud.cn/52/7f/527f143c239827162b22a91f907ca157_1138x644.png)
测试结果
![img](https://img.kancloud.cn/33/a1/33a1edccd53c91b4eb3deb48e8a53a73_1472x853.png)