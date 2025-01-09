FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER jeecgos@163.com

# 通过清华镜像源安装tzdata修正容器时区问题
RUN sed -i 's/dl-cdn.alpinelinux.org/mirrors.tuna.tsinghua.edu.cn/g' /etc/apk/repositories && apk add --no-cache tzdata
# 时区（无论是否安装tzdata，JDK时区设置下面软链接后，都会更正，但是tzdata不安装会让容器时区不生效）
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

WORKDIR /jeecg-boot

# 端口
ENV PORT 8080
# 激活配置
ENV PROFILE prod
# 暴露端口
EXPOSE $PORT/tcp
# 应用-本地文件上传目录（建议）
VOLUME /opt/upFiles
# 应用-本地webapp目录（可选）
# VOLUME /opt/webapp

# 数据库配置
ENV DATABASE_HOST 172.17.0.1
ENV DATABASE_PORT 54321
# ENV DATABASE_NAME 'postgres'
# ENV DATABASE_USER 'postgres'
# ENV DATABASE_PWD '123456'

# Redis配置
ENV REDIS_HOST 172.17.0.1
ENV REDIS_PORT 63791
# ENV REDIS_DB 11
# ENV REDIS_PWD ''

#ADD ./src/main/resources/jeecg ./config/jeecg
COPY ./target/lib ./lib
COPY ./target/config ./config
ADD ./target/main.jar ./

CMD sleep 1;java -Dserver.port=$PORT -Dspring.profiles.active=$PROFILE -Dloader.path=lib,config -Djava.security.egd=file:/dev/./urandom -jar main.jar
