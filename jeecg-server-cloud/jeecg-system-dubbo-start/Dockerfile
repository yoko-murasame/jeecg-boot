FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER jeecgos@163.com

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

RUN mkdir -p /opt/upFiles && mkdir -p /jeecg-system-dubbo

WORKDIR /jeecg-system-dubbo

ENV SERVER_PORT 8001

ENV DUBBO_PORT 20880

EXPOSE ${SERVER_PORT}

EXPOSE ${DUBBO_PORRT}

ADD ./target/jeecg-system-dubbo-start-3.4.3.jar ./

CMD sleep 1;pwd;ls -al;echo $PATH;java -version;java -Dfile.encoding=utf-8 -Djava.security.egd=file:/dev/./urandom -jar jeecg-system-dubbo-start-3.4.3.jar
