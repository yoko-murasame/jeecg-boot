FROM kuluseky/alpine-java-zh:8_server-jre_unlimited

# 配置变量-启动jar包名称
ENV APP_NAME=jeecg-cloud-xxljob-3.4.3.jar
# 配置变量-指定加载配置
ENV APP_PROFILE_ACTIVE="pg"
# 配置变量-指定端口
ENV APP_PORT=9080

# 复制jar包
ADD ./target/$APP_NAME ./

# 提示需暴露端口
EXPOSE $APP_PORT

# 数据库配置
ENV DATABASE_HOST 172.17.0.1
ENV DATABASE_PORT 54321
ENV DATABASE_NAME 'xxl_job'
ENV DATABASE_USER 'postgres'
ENV DATABASE_PWD '123456'

# xxl_job配置(用户密码配置在这没啥用，以数据库为准)
ENV XXL_USERNAME admin
ENV XXL_PASSWORD 123456
