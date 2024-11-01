#!/bin/sh

# docker日志定时清理脚本
# docker安装目录（默认docker的存储路径为/data/docker0）
docker_home="/home/docker"
# 定时任务Crony表达式-每天凌晨2点执行一次
CRON_SCHEDULE="0 2 * * *"

# 检查是否有root权限
if [ "$EUID" -ne 0 ]
    then echo "请使用root权限运行此脚本"
    exit
fi

# 来自命令行参数的 Docker 日志目录
if [ "$#" -eq 1 ]; then
    echo "参数1(指定docker安装路径): $1"
    docker_home=$1
elif [ "$#" -eq 2 ]; then
    if [ "$2" != "enable" ]; then
        echo "参数2(注册定时任务)，请固定输入：enable"
        exit
    fi
    ####################################### 定时任务 BEGIN ######################################
    echo "注册定时任务..."
    # 设置权限
    SCRIPT_PATH="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)/$(basename "${BASH_SOURCE[0]}")"
    chmod u+x $SCRIPT_PATH
    # 设置定时任务
    CRON_CMD="${CRON_SCHEDULE} ${SCRIPT_PATH}"
    # 使用 sed 命令将 * 和 ? 转义
    ESCAPED_CMD=$(echo "$CRON_CMD" | sed 's/\*/\\*/g; s/\?/\\?/g')
    # 使用 grep 命令查找是否已经存在我们想要添加的定时任务
    if echo "$(crontab -l)" | grep -q "$ESCAPED_CMD"; then
        echo "定时任务已存在($CRON_CMD)。"
    else
        echo "删除脚本($SCRIPT_PATH)关联的定时任务..."
        (crontab -l | grep -v "$(basename "${BASH_SOURCE[0]}")"; ) | crontab -
        echo "正在添加..."
        (crontab -l; echo "$CRON_CMD") | crontab -
        echo "定时任务已添加。"
    fi
    ####################################### 定时任务 END ########################################
fi

echo "======== 开始清理docker容器日志 ========"
logs=$(find $docker_home/containers/ -name *-json.log)
for log in $logs
    do
        echo "清空日志：$log"
        cat /dev/null > $log
    done
echo "======== 日志清理完成 ========"
