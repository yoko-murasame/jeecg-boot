#!/bin/bash

# 请注意脚本中行分隔符的印象，Linux是LF，Windows是CRLF！拿VS Code或者IDEA等工具可以直接修改！

#启动 sh start.sh start
#停止 sh start.sh stop
#重启 sh start.sh restart
#查看状态 sh start.sh status

#入口jar 这里可替换为你自己的执行程序，其他代码无需更改
APP_NAME=main.jar
#配置文件
APP_PROFILE=prod
#端口
APP_PORT=8123
#额外jvm参数，这里手动指定load路径，加载分离后的依赖
EXTERNAL_JVM="-Dloader.path=lib,config"

#使用说明，用来提示输入参数
usage() {
    echo "Usage: sh demo.sh [start|stop|restart|status]"
    exit 1
}

#检查程序是否在运行
is_exist() {
    pid=`ps -ef | grep $APP_NAME | grep -v grep | awk '{print $2}' `
    #如果不存在返回1，存在返回0
    if [ -z "${pid}" ]; then
      return 1
    else
      return 0
    fi
}

#启动方法
start() {
   is_exist
   if [ $? -eq "0" ]; then
     echo "${APP_NAME} is already running. pid=${pid} ."
   else
     nohup java -jar ${EXTERNAL_JVM} -Dspring.profiles.active=${APP_PROFILE} -Dserver.port=${APP_PORT} ./${APP_NAME} > log.log 2>&1 &
   fi
}

#停止方法
stop() {
   is_exist
   if [ $? -eq "0" ]; then
     kill -9 $pid
   else
     echo "${APP_NAME} is not running"
   fi
}

#输出运行状态
status() {
   is_exist
   if [ $? -eq "0" ]; then
     echo "${APP_NAME} is running. Pid is ${pid}"
   else
     echo "${APP_NAME} is not running."
   fi
}

#重启
restart() {
   stop
   start
}

#根据输入参数，选择执行对应方法，不输入则执行使用说明
case "$1" in
   "start")
     start
     ;;
   "stop")
     stop
     ;;
   "status")
     status
     ;;
   "restart")
     restart
     ;;
   *)
     usage
     ;;
esac
