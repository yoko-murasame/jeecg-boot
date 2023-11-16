[返回](../)

# APIJSON

腾讯开源框架，零代码接口和文档，JSON 协议 与 ORM 库。

官网：http://apijson.cn/

Github：https://github.com/Tencent/APIJSON

## 快速开始

这里的演示Demo集成了后端+官网的测试前端工具：

* 后端：https://github.com/APIJSON/APIJSON-Demo/tree/master/APIJSON-Java-Server
* 前端测试工具（已集成进Spring Boot）：https://github.com/APIJSON/APIAuto

### 1. 创建数据库

新建数据库，执行 `jeecg-module-extra/APIJSONBoot/src/main/resources/PostgreSQL/postgres_sys_ddl.sql` 文件，创建数据库表。
也可以执行 `single` 目录下的脚本，含测试数据。

SQL脚本是从官网获取的：https://github.com/APIJSON/APIJSON-Demo/tree/master/PostgreSQL

### 2. 修改配置

1）修改 `src/main/java/apijson/demo/DemoSQLConfig.java` 数据库连接信息。

2）修改 `src/main/java/apijson/demo/DemoApplication.java` 的static块中添加数据库驱动。

3）运行 DemoApplication，访问：http://localhost:8080，Demo的API地址：http://localhost:8080/demo

### 3. 相关使用方式见官方文档

https://github.com/Tencent/APIJSON/blob/master/Document.md

# END
