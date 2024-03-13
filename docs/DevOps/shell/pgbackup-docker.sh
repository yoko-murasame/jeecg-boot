#!/bin/bash

####################################### 配置项 BEGIN #######################################
# 所有数据库备份存放目录
BACKUP_DIR="/root/opt/pgbackup"
# 每个数据库保留的备份文件数量
KEEP_COUNT=7
# 用户密码
username=postgres
password=123456
# HOST，由于是容器内的，因此端口和HOST基本不用改
HOSTNAME=127.0.0.1
PORT=5432
# 数据库名称，可以指定多个数据库
DB_NAMES=("postgres" "db2" "db3")
# PostgreSQL容器名称
PG_CONTAINER_NAME=postgre-13
# 定时任务Crony表达式-每天凌晨1点执行一次
CRON_SCHEDULE="0 1 * * *"
####################################### 配置项 END #########################################

####################################### 定时任务 BEGIN ######################################
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

####################################### 数据库备份 BEGIN #####################################
# pg_dump 命令
PG_DUMP="PGPASSWORD=${password} pg_dump"
# 日期格式，用于命名备份文件
DATE=$(date +%Y%m%d)

# 遍历所有数据库
for DB_NAME in "${DB_NAMES[@]}"
do
    # 备份文件名
    BACKUP_FILE="${DB_NAME}_${DATE}.dump"

    # 导出数据库
    docker exec ${PG_CONTAINER_NAME} bash -c "${PG_DUMP} -h ${HOSTNAME} -p ${PORT} -U ${username} -Fc -f /${BACKUP_FILE} ${DB_NAME}"
    # 导出备份
    mkdir -p ${BACKUP_DIR}
    docker cp ${PG_CONTAINER_NAME}:/${BACKUP_FILE} ${BACKUP_DIR}/${BACKUP_FILE}
    # 删除容器内备份
    docker exec ${PG_CONTAINER_NAME} rm /${BACKUP_FILE}

    # 检查备份文件是否存在
    if [ -f "${BACKUP_DIR}/${BACKUP_FILE}" ]; then
        echo "备份成功: ${BACKUP_DIR}/${BACKUP_FILE}" >> ${BACKUP_DIR}/backup.log
    else
        echo "备份失败: ${BACKUP_DIR}/${BACKUP_FILE}"  >> ${BACKUP_DIR}/backup.log
    fi

    # 清理的文件名前缀
    BACKUP_PREFIX="${DB_NAME}_"

    # 遍历备份目录下所有以 BACKUP_PREFIX 开头的文件，并按修改时间排序
    files=($(find "$BACKUP_DIR" -maxdepth 1 -name "${BACKUP_PREFIX}*.dump" -printf "%T@ %p\n" | sort -n | cut -d' ' -f2-))

    # 如果备份文件数量大于 KEEP_COUNT，则删除除了最新的 KEEP_COUNT 个文件之外的所有文件
    count=${#files[@]}
    if [[ $count -gt $KEEP_COUNT ]]; then
        echo "保存最近 $KEEP_COUNT 个备份" >> ${BACKUP_DIR}/backup-clean.log
        for ((i=0; i<count-KEEP_COUNT; i++))
        do
            echo "删除备份 ${files[i]}" >> ${BACKUP_DIR}/backup-clean.log
            rm "${files[i]}"
        done
    else
        echo "备份文件数量 (${#files[@]}) 小于等于 $KEEP_COUNT, 不执行清理操作." >> ${BACKUP_DIR}/backup-clean.log
    fi
done
####################################### 数据库备份 END #######################################
