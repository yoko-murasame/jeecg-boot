FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER jeecgos@163.com

# 通过清华镜像源安装tzdata解决时区问题
RUN sed -i 's/dl-cdn.alpinelinux.org/mirrors.tuna.tsinghua.edu.cn/g' /etc/apk/repositories && apk add --no-cache tzdata
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

RUN mkdir -p /jeecg-cloud-gateway

WORKDIR /jeecg-cloud-gateway

EXPOSE 9999

ADD ./target/jeecg-cloud-gateway-3.4.3.jar ./

CMD sleep 100;java -Dfile.encoding=utf-8 -Djava.security.egd=file:/dev/./urandom -jar jeecg-cloud-gateway-3.4.3.jar
