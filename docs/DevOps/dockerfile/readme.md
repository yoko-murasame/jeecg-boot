# JDK8中文环境基础容器

1. 基于 anapsix/alpine-java:8_server-jre_unlimited
2. 安装tzdata修正容器时区问题
3. 安装中文字体黑体
4. 添加/LD_LIBRARY动态链接库（子镜像往该目录添加文件即可）
5. 支持单体jar、分离jar启动

子镜像Dockerfile示例：

```dockerfile
FROM kuluseky/alpine-java-zh:8_server-jre_unlimited

# 动态链接库
# COPY ./so/* /LD_LIBRARY/

# 配置变量-启动jar包名称
ENV APP_NAME=app-name.jar
# 配置变量-指定加载配置：prod、dev、test
ENV APP_PROFILE_ACTIVE=""
# 配置变量-指定端口
ENV APP_PORT=8080

# （可选）配置变量-依赖文件夹，实际目录都不为空时，将以分离式jar启动
# ENV APP_LIB_PATH=lib APP_CONFIG_PATH=config
# （可选）配置变量-附加启动JVM参数（以下为默认参数）
# ENV APP_JVM_OPTION="-Dfile.encoding=utf-8 -Djava.security.egd=file:/dev/./urandom -Xms1024m -Xmx2048m"

# （可选）分离式jar启动-挂载整个app目录（/app）
# VOLUME $APP
# （可选）分离式jar启动-挂载外部lib（/app/lib）
# VOLUME $APP/$APP_LIB_PATH
# （可选）分离式jar启动-挂载外部config（/app/config）
# VOLUME $APP/$APP_CONFIG_PATH
# （可选）挂载附件目录
# VOLUME /opt/upFiles

# 复制jar包
ADD ./target/$APP_NAME ./

# 提示需暴露端口
EXPOSE $APP_PORT

```
