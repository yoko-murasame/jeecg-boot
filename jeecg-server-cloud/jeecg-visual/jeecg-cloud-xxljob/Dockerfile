FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER jeecgos@163.com

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

RUN mkdir -p /jeecg-cloud-xxljob

WORKDIR /jeecg-cloud-xxljob

# 端口
ENV PORT 9080
# 激活配置
ENV PROFILE pg
# 暴露端口
EXPOSE $PORT/tcp

# 数据库配置
ENV DATABASE_HOST 172.17.0.1
ENV DATABASE_PORT 54321
ENV DATABASE_NAME 'xxl_job'
ENV DATABASE_USER 'postgres'
ENV DATABASE_PWD '123456'

# xxl_job配置(用户密码配置在这没啥用，以数据库为准)
ENV XXL_USERNAME admin
ENV XXL_PASSWORD 123456

ADD ./target/jeecg-cloud-xxljob-3.4.3.jar ./

CMD sleep 1;java -Dserver.port=$PORT -Dspring.profiles.active=$PROFILE -Dfile.encoding=utf-8 -Djava.security.egd=file:/dev/./urandom -jar jeecg-cloud-xxljob-3.4.3.jar
