FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER jeecgos@163.com

# 通过镜像源安装tzdata修正容器时区问题
RUN sed -i 's/dl-cdn.alpinelinux.org/mirrors.aliyun.com/g' /etc/apk/repositories && apk add --no-cache tzdata
# 时区（无论是否安装tzdata，JDK时区设置下面软链接后，都会更正，但是tzdata不安装会让容器时区不生效）
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

# 字体(解决流程图部署乱码,请设置好字体目录位置)
RUN sed -i 's/dl-cdn.alpinelinux.org/mirrors.aliyun.com/g' /etc/apk/repositories && apk update && apk --no-cache --purge -U upgrade
RUN apk add --no-cache --purge -uU ttf-dejavu fontconfig && rm -rf /var/cache/apk/* && mkdir -p /usr/share/fonts/new/
# 字体直接打入包中
COPY fonts/*.ttf /usr/share/fonts/new/
RUN fc-cache
# 或者字体挂载出来
# VOLUME /usr/share/fonts/new/

# 文件目录
RUN mkdir -p /opt/upFiles && mkdir -p /opt/webapp

RUN mkdir -p /jeecg-cloud-dubbo-test

WORKDIR /jeecg-cloud-dubbo-test

ENV DUBBO_PORT 20883

EXPOSE 8003

EXPOSE ${DUBBO_PORRT}

ADD ./target/jeecg-cloud-dubbo-test-service-3.4.3.jar ./

CMD sleep 1;pwd;ls -al;echo $PATH;java -version;java -Dfile.encoding=utf-8 -Djava.security.egd=file:/dev/./urandom -jar jeecg-cloud-dubbo-test-service-3.4.3.jar
