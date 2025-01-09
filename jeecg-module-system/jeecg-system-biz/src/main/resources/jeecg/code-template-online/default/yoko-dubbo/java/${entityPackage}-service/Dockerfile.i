FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER jeecgos@163.com

# 通过清华镜像源安装tzdata修正容器时区问题
RUN sed -i 's/dl-cdn.alpinelinux.org/mirrors.tuna.tsinghua.edu.cn/g' /etc/apk/repositories && apk add --no-cache tzdata
# 时区（无论是否安装tzdata，JDK时区设置下面软链接后，都会更正，但是tzdata不安装会让容器时区不生效）
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

RUN mkdir -p /opt/upFiles && mkdir -p /${entityPackage}-dubbo

WORKDIR /${entityPackage}-dubbo

ENV DUBBO_PORT 20880

EXPOSE 8001

EXPOSE <#noparse>${DUBBO_PORRT}</#noparse>

ADD ./target/${entityPackage}-dubbo-service-3.4.3.jar ./

CMD sleep 1;pwd;ls -al;echo <#noparse>$PATH</#noparse>;java -version;java -Dfile.encoding=utf-8 -Djava.security.egd=file:/dev/./urandom -jar ${entityPackage}-dubbo-service-3.4.3.jar
