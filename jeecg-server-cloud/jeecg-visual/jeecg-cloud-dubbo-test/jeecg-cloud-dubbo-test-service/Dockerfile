FROM kuluseky/alpine-java-zh:8_server-jre_unlimited

# 配置变量-启动jar包名称
ENV APP_NAME=jeecg-cloud-dubbo-test-service-3.4.3.jar
# 配置变量-指定加载配置
ENV APP_PROFILE_ACTIVE=""
# 配置变量-指定端口
ENV APP_PORT=8003
ENV DUBBO_PORT 20883

# 复制jar包
ADD ./target/$APP_NAME ./

# 提示需暴露端口
EXPOSE $APP_PORT
EXPOSE ${DUBBO_PORRT}
