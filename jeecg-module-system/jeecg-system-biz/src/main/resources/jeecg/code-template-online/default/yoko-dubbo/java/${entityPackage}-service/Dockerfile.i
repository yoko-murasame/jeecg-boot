FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER jeecgos@163.com

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

RUN mkdir -p /opt/upFiles && mkdir -p /${entityPackage}-dubbo

WORKDIR /${entityPackage}-dubbo

ENV DUBBO_PORT 20880

EXPOSE 8001

EXPOSE <#noparse>${DUBBO_PORRT}</#noparse>

ADD ./target/${entityPackage}-dubbo-service-3.4.3.jar ./

CMD sleep 1;pwd;ls -al;echo <#noparse>$PATH</#noparse>;java -version;java -Dfile.encoding=utf-8 -Djava.security.egd=file:/dev/./urandom -jar ${entityPackage}-dubbo-service-3.4.3.jar
