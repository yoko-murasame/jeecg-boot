[返回](../)

# Oracle数据库安装参考文档

这里提供Oracle12c的Docker安装方式，其他版本的安装方式请自行参考。

* [11g备份路径(Jeecg官方)](https://github.com/yoko-murasame/jeecg-boot/blob/yoko-3.4.3last/db/Oracle)

**Oracle 12c的Docker版本安装步骤**

1）镜像地址：

11g：https://hub.docker.com/r/wnameless/oracle-xe-11g-r2

12c：https://github.com/MaksymBilenko/docker-oracle-12c

2）下载镜像：

11g：`docker pull wnameless/oracle-xe-11g-r2`

12c：`docker pull quay.io/maksymbilenko/oracle-12c`

3）启动容器：
```shell
# 方式一 Run with data on host and reuse it:
docker run -d -p 8180:8080 -p 1521:1521 -v /my/oracle/data:/u01/app/oracle oracle-12c

# 方式二 Run with Custom DBCA_TOTAL_MEMORY (in Mb):
docker run -d -p 8180:8080 -p 1521:1521 -v /my/oracle/data:/u01/app/oracle -e DBCA_TOTAL_MEMORY=4096 quay.io/maksymbilenko/oracle-12c
```

4）连接数据库：
```text
hostname: localhost
port: 1521
sid: xe
service name: xe
username: system
password: oracle
```

```shell
sqlplus system/oracle@//localhost:1521/xe
Password for SYS & SYSTEM: oracle
```

5）创建用户、表空间：
```sql
-- 创建用户和表空间
CREATE USER jeecgboot IDENTIFIED BY 1234;
-- 权限
-- GRANT CONNECT, RESOURCE, DBA TO jeecgboot;
GRANT DBA TO jeecgboot;
-- 创建自动扩容表空间（用户名和表空间名称最好一致）
CREATE TABLESPACE jeecgboot
    DATAFILE '/u01/app/oracle/oradata/xe/jeecgboot.dbf' SIZE 500M
    AUTOEXTEND ON NEXT 100M MAXSIZE 8G;
ALTER USER jeecgboot QUOTA UNLIMITED ON jeecgboot;

-- 查看表空间位置
SELECT file_name, tablespace_name, bytes, autoextensible
FROM dba_data_files;
-- 删除表空间
DROP TABLESPACE ZY INCLUDING CONTENTS AND DATAFILES;
```

6）导入数据库
```shell
oracle导出编码：  export NLS_LANG=AMERICAN_AMERICA.ZHS16GBK
导出用户：  jeecgboot
导入命令：  imp scott/tiger@xe file=jeecgboot-oracle11g.dmp
```

修改历史:
* 2023-08-08: 新增
