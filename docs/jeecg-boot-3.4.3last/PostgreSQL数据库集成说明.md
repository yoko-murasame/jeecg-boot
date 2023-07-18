[返回](../)

# PostgreSQL数据库集成说明

* 数据源默认改成PostgreSQL
* 默认集成了PostGIS、zhparser分词
* 分词版本Docker安装方式参考: https://github.com/yoko-murasame/docker-postgres-12-zhparser-postgis

组件路径: 
* [PG初始化脚本](https://github.com/yoko-murasame/jeecg-boot/blob/yoko-3.4.3last/db/PostgreSQL)

**PostgreSQL分词版本安装说明(基于Docker)**
```shell
# 先去把上面的仓库clone下来
git clone https://github.com/yoko-murasame/docker-postgres-12-zhparser-postgis.git
# 修改pg版本号 找到 ARG pg_version=12 改成12、13、14等版本号（目前测试过12、13）
vim Dockfile
# 执行构建
docker build -t postgres-13-zhparser-postgis:1.0 .
# 导出镜像
docker save -o postgres-13-zhparser-postgis-v1 postgres-13-zhparser-postgis:1.0
# 加载镜像
docker load -i postgres-13-zhparser-postgis-v1
# 首次启动
docker run -d \
  --name postgre-13 \
	-e POSTGRES_PASSWORD=123456 \
	-e PGDATA=/var/lib/postgresql/data/pgdata \
	postgres-13-zhparser-postgis:1.0;
# 复制data目录出来
docker cp postgre-13:/var/lib/postgresql/data/pgdata /root/pgdata
# 启动容器
docker run -d \
  --name postgre-13 \
	-e POSTGRES_PASSWORD=123456 \
	-e PGDATA=/var/lib/postgresql/data/pgdata \
	-p 54321:5432 \
	-v /root/pgdata:/var/lib/postgresql/data/pgdata \
	postgres-13-zhparser-postgis:1.0;
#############################################
# 修改远程配置，修改 postgresql.conf
#############################################
- listen_addresses = 'localhost'
+ listen_addresses = '*'
#############################################
# 如果是Windows的pgdata目录迁移到Linux的pgdata，下面的内存类型得修改
#############################################
dynamic_shared_memory_type = posix	# the default is the first option
					# supported by the operating system:
					#   posix
					#   sysv
					#   windows Windows版本的默认就是windows
					#   mmap
					# (change requires restart)
#############################################
# 修改 pg_hba.conf
#############################################
# "local" is for Unix domain socket connections only
local   all             all                                     md5
# IPv4 local connections:
host    all             all             127.0.0.1/32            md5
host    all             all             0.0.0.0/0            md5
# IPv6 local connections:
host    all             all             ::1/128                 md5
host    all             all             ::/0                 md5
# Allow replication connections from localhost, by a user with the
# replication privilege. # 复制权限（可以不配置）
local   replication     all                                     md5
host    replication     all             127.0.0.1/32            md5
host    replication     all             0.0.0.0/0            md5
host    replication     all             ::1/128                 md5
host    replication     all             ::/0                 md5
# 其实最下面加一行这个就可以了
host all all all md5
#############################################
# 重启数据库
docker restart postgre-13
# 防火墙
firewall-cmd --zone=public --add-port=54321/tcp --permanent
firewall-cmd --zone=public --remove-port=8080/tcp --permanent
firewall-cmd --reload
firewall-cmd --list-ports
```

**分词功能**
```sql
-- 创建数据库
psql -d <database>;
-- 创建分词扩展
CREATE EXTENSION zhparser;
CREATE TEXT SEARCH CONFIGURATION chinese (PARSER = zhparser);
ALTER TEXT SEARCH CONFIGURATION chinese
ADD MAPPING FOR a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z
WITH simple;

-- 如果不是默认的postgres库，还需要以下步骤（建议使用navicat迁移）
-- 新创建的库会自动执行下面的步骤，等待一会儿就行
-- 1.复制postgres库的模式zhparser
-- 2.复制postgres库的所有分词函数

-- 测试
SELECT to_tsvector('chinese', '人生苦短，乘早摸鱼，Good Morning~');
                      to_tsvector
--------------------------------------------------------
'good':8 'morning':9 '乘':4 '人生':1 '摸':6 '早':5 '短':3 '苦':2 '鱼':7

-- 添加自定义词典
insert into zhparser.zhprs_custom_word values ('摸鱼');
insert into zhparser.zhprs_custom_word values ('荒天帝');
insert into zhparser.zhprs_custom_word values ('独断万古');
insert into zhparser.zhprs_custom_word values ('人生苦短');
-- 词典生效
select sync_zhprs_custom_word();

-- test
SELECT * FROM ts_parse('zhparser', '人生苦短，爆炸吧，小宇宙，独断万古荒天帝，摸鱼ing，Good Morning~');
SELECT to_tsquery('chinese', '荒天帝石昊');
SELECT to_tsvector('chinese', '人生苦短，爆炸吧，小宇宙，独断万古荒天帝，摸鱼ing，Good Morning~');
```

**性能优化**
在线性能优选参数生成器：https://pgtune.leopard.in.ua/
Github项目地址：https://github.com/le0pard/pgtune
获取Linux服务器的性能配置，涉及以下指标
```shell
# CPU逻辑核心数
lscpu | grep "^CPU(s):" | head -1 | awk '{print $2}'
# 查看内存大小
free -h
# 查看硬盘是否是SSD 如果 RoTa 值为 1，说明硬盘是旋转的机械硬盘（HDD）；如果 RoTa 值为 0，说明硬盘是固态硬盘（SSD）
lsblk -d -o name,rota
```

**数据备份**
```shell
# 进入容器执行备份
docker exec -it <容器> pg_dump -h localhost -p 5432 -U postgres -W -Fc -f /backup.sql -d <database>
# 复制备份到宿主机
docker cp <容器>:/backup.sql /root/pgbackup/backup.sql
# 复制备份到新的容器
docker cp /root/pgbackup/backup.sql <容器>:/backup.sql
# 导入到数据库方式，注意需要先创建数据库
psql -U <用户名> -h <主机名> -p <端口号>
CREATE DATABASE <目标数据库名称>;
docker exec -it <新容器> pg_restore --verbose -U postgres -W -d <目标数据库名称> <容器内备份文件路径>
# 通过psql方式同理 docker exec -it <新容器> psql --verbose -U <用户名> -d <目标数据库名称> -f <容器内备份文件路径>
```

**Linux备份脚本**
```shell
#!/bin/bash

# 备份目录
BACKUP_DIR="/pgbackup/"
# 保留的备份文件数量
KEEP_COUNT=7
# 用户密码
username=postgres
password=dsjy@123
# HOST
HOSTNAME=localhost
PORT=54321
# 数据库名称
DB_NAME=gongyong_test
# pg_dump 命令路径
PG_DUMP="docker exec -it postgis pg_dump"

# 日期格式，用于命名备份文件
DATE=$(date +%Y%m%d)
# 备份文件名
BACKUP_FILE="${DB_NAME}_${DATE}.dump"

# 导出数据库
PGPASSWORD=${password} ${PG_DUMP} -h ${HOSTNAME} -p ${PORT} -U ${username} -Fc -f ${BACKUP_DIR}/${BACKUP_FILE} ${DB_NAME}

# 检查备份文件是否存在
if [ -f "${BACKUP_DIR}/${BACKUP_FILE}" ]; then
    echo "备份成功: ${BACKUP_DIR}/${BACKUP_FILE}" >> backup.log
else
    echo "备份失败: ${BACKUP_DIR}/${BACKUP_FILE}"  >> backup.log
fi

# 清理的文件名前缀
BACKUP_PREFIX="${DB_NAME}_"

# 遍历备份目录下所有以 BACKUP_PREFIX 开头的文件，并按修改时间排序
files=($(find "$BACKUP_DIR" -maxdepth 1 -name "${BACKUP_PREFIX}*.dump" -printf "%T@ %p\n" | sort -n | cut -d' ' -f2-))

# 如果备份文件数量大于 KEEP_COUNT，则删除除了最新的 KEEP_COUNT 个文件之外的所有文件
count=${#files[@]}
if [[ $count -gt $KEEP_COUNT ]]; then
    echo "保存最近 $KEEP_COUNT 个备份" >> backup-clean.log
    for ((i=0; i<count-KEEP_COUNT; i++))
    do
        echo "删除备份 ${files[i]}" >> backup-clean.log
        rm "${files[i]}"
    done
else
    echo "备份文件数量 (${#files[@]}) 小于等于 $KEEP_COUNT, 不执行清理操作." >> backup-clean.log
fi

```

**Windows备份脚本**
```cmd
echo Do backing...please waiting....
@echo off

set KEEP_NUMBER=7
set BACKUP_DIR=.
set DB_NAME=postgres
set HOSTNAME=localhost
set PORT=54321
set USERNAME=postgres
set PASSWORD=dsjy@123

set PG_DUMP="C:\Program Files\PostgreSQL\13\bin\pg_dump.exe"

set /A KEEP_INDEX=%KEEP_NUMBER%-1
for /f "skip=%KEEP_INDEX% delims=" %%F in ('dir /b *.backup /o-d') do del "%BACKUP_DIR%\%%F"

set CUR_DATE=%date:~0,4%-%date:~5,2%-%date:~8,2%
set CUR_TIME=%time:~0,2%%time:~3,2%%time:~6,2%
set BACKUP_FILE=%BACKUP_DIR%\%DB_NAME%_%CUR_DATE%_%CUR_TIME%.backup

SET PGPASSWORD=%PASSWORD%

%PG_DUMP% -h %HOSTNAME% -p %PORT% -U %USERNAME% -Z 9 -Fc -f %BACKUP_FILE% %DB_NAME%

```

修改历史:
* 2023-07-18: 添加分词功能PostgreSQL、各类脚本。
