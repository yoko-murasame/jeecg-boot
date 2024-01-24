#!/bin/bash

################################# 配置项 BEGIN ##################################
# 备份目录
BACKUP_DIR="/root/opt/pgbackup"
# 保留的备份文件数量
KEEP_COUNT=7
# 用户密码
username=postgres
password=123456
# HOST
HOSTNAME=127.0.0.1
PORT=5432
# 数据库名称
DB_NAME=postgres
# PostgreSQL容器名称
PG_CONTAINER_NAME=postgre-13
################################# 配置项 END ####################################
# 设置权限
# chmod u+x pgbackup-docker.sh
# 设置定时任务
# crontab -e
# PostgreSQL备份脚本-每天凌晨1点执行一次
# 0 1 * * * /path/to/pgbackup-docker.sh
################################################################################

# pg_dump 命令
PG_DUMP="docker exec -it ${PG_CONTAINER_NAME} pg_dump"
# 日期格式，用于命名备份文件
DATE=$(date +%Y%m%d)
# 备份文件名
BACKUP_FILE="${DB_NAME}_${DATE}.dump"

# 导出数据库
PGPASSWORD=${password} ${PG_DUMP} -h ${HOSTNAME} -p ${PORT} -U ${username} -Fc -f /${BACKUP_FILE} ${DB_NAME}
# 导出备份
mkdir -p ${BACKUP_DIR}
docker cp ${PG_CONTAINER_NAME}:/${BACKUP_FILE} ${BACKUP_DIR}/${BACKUP_FILE}
# 删除容器内备份
docker exec -it ${PG_CONTAINER_NAME} rm /${BACKUP_FILE}

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
