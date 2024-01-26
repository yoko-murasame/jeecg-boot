[返回](../)

# Oracle数据库安装参考文档

这里提供Oracle12c的Docker安装方式，其他版本的安装方式请自行参考。

* [11g备份路径(Jeecg官方)](https://github.com/yoko-murasame/jeecg-boot/blob/yoko-3.4.3last/db/Oracle)

修改历史:
* 2023-08-08: 新增
* 2023-08-09: 新增Oracle运维相关命令

## Oracle 12c的Docker版本安装步骤

1）镜像地址：

11g：https://hub.docker.com/r/wnameless/oracle-xe-11g-r2

12c：https://github.com/MaksymBilenko/docker-oracle-12c

2）下载镜像：

11g：`docker pull wnameless/oracle-xe-11g-r2`

12c：`docker pull quay.io/maksymbilenko/oracle-12c`

3）启动容器：
```shell
# 目录需要权限
mkdir -p /my/oracle/data
chmod 777 /my/oracle/data
#chown 54321:54322 /my/oracle/data

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
#Password for SYS & SYSTEM: oracle
#sqlplus sys as sysdba/oracle@//localhost:1521/xe
# sqlplus sys as sysdba
```

5）创建用户、表空间：
```sql
-- 创建用户和表空间 特殊符号需要用双引号
CREATE USER jeecgboot IDENTIFIED BY "1234@123aqq";
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
DROP TABLESPACE jeecgboot INCLUDING CONTENTS AND DATAFILES;
```

## 扩展

### 导入导出数据库

**密码带特殊符号时的规则**

windows os: `exp username/"""password"""@devdb` --3个双引号扩密码

linux/unix os: `exp 'username/"password"@devdb'` --1个双引号扩密码，1个单引号扩全部

```shell
-- 设置环境变量指定导入导出编码
export NLS_LANG=AMERICAN_AMERICA.UTF8


-- 完整导出命令，用户名即对应数据库，特殊密码需要用双引号扩起来，服务名一般为orcl、xe
-- exp 'username/"password@password"@127.0.0.1:15211/服务名' full=y file=export.dmp log=export.log
# 容器内的备份命令
docker exec -it <容器名> exp 'username/"password@password"@127.0.0.1:1521/服务名' full=y file=export.dmp log=export.log \ &&
docker cp <容器名>:/export.dmp ./ && docker exec -it <容器名> rm /export.dmp


-- 完整导入命令，用户名即对应数据库，特殊密码需要用双引号扩起来，服务名一般为orcl、xe
-- imp 'username/"password@password"@127.0.0.1:15211/服务名' full=y file=export.dmp log=import.log
# 容器内的导入命令
docker cp ./export.dmp <容器名>:/export.dmp && rm ./export.dmp \ &&
docker exec -it <容器名> imp 'username/"password@password"@127.0.0.1:1521/服务名' full=y file=export.dmp log=export.log && rm /export.dmp


-- 特殊情况 sys as sysdba 下的导出命令
# 一般不会拿sys as sysdba去导出，如果真有需求，参考下面命令
docker exec -it oracle-12c exp \'sys/\"带@的密码\"@127.0.0.1:1521/xe AS SYSDBA\' full=y file=export.dmp log=export.log

# 拿sys as sysdba导出时，可以指定scheme，注意 owner=(system,sys) 和 full=y 是冲突的
docker exec -it oracle-12c exp \'sys/\"带@的密码\"@127.0.0.1:1521/xe AS SYSDBA\' owner=system file=export.dmp log=export.log


-- 特殊情况 sys as sysdba 下的导入命令
# 拿sys as sysdba去导入时，可以指定scheme，需要指定 fromuser=(system,sys) 和 touser=(system,sys)
docker exec -it oracle-12c imp \'sys/\"带@的密码\"@127.0.0.1:1521/xe AS SYSDBA\' full=y file=export.dmp log=import.log

docker exec -it oracle-12c imp \'sys/\"带@的密码\"@127.0.0.1:1521/xe AS SYSDBA\' file=export.dmp log=import.log fromuser=system touser=system

# 在Windows上终端使用powershell时，需要先进容器，再执行导入命令
docker cp ./export.dmp oracle-12c:/export.dmp
docker exec -it oracle-12c bash
imp \'sys/\"带@的密码\"@127.0.0.1:1521/xe AS SYSDBA\' file=export.dmp log=import.log fromuser=zy touser=zy && rm /export.dmp
```

### 修改默认管理员账户密码

1. 首先，以具有 `SYSDBA` 角色的用户身份连接到 Oracle 数据库。可以使用以下命令连接到数据库：
   ```
   sqlplus / as sysdba
   ```
   这将启动 SQL*Plus 工具并使用操作系统认证连接到数据库。
2. 连接成功后，使用以下命令修改 `SYS` 用户的密码：
   ```
   ALTER USER sys IDENTIFIED BY "新密码翻入双引号可以写特殊符号如@";
   ALTER USER system IDENTIFIED BY "新密码翻入双引号可以写特殊符号如@";
   ```
   将 `new_password` 替换为你想要设置的新密码。
   注意：在修改 `SYS` 用户的密码时，请确保选择一个强密码，并妥善保管密码。
3. 修改密码后，你可以使用以下命令验证新密码是否起效：
   ```
   CONNECT sys/"带特殊符号的密码用双引号" AS SYSDBA;
   CONNECT system/"带特殊符号的密码用双引号";
   ```
   这将使用新密码连接到 `SYS` 用户。
4. 最后，退出 SQL*Plus 工具，可以使用以下命令：
   ```
   EXIT;
   ```

## 归档

### 11g备份导入到12c

要将 Oracle 11g 中编码为 `AMERICAN_AMERICA.ZHS16GBK` 的备份文件导入到 Oracle 12c 中编码为 `AL32UTF8` 的数据库，可以按照以下步骤进行操作：

1. 在 Oracle 12c 数据库中创建一个新的目标用户（schema），用于导入数据。

2. 确保 Oracle 12c 数据库的字符集编码已设置为 `AL32UTF8`。可以通过查询 `NLS_DATABASE_PARAMETERS` 视图来验证。

3. 在 Oracle 12c 数据库中创建一个目录对象，用于存储导入文件。可以使用 `CREATE DIRECTORY` 语句创建目录对象，指定一个合适的目录路径。

4. 将备份文件（`.dmp`）复制到 Oracle 12c 服务器上，并确保该文件可以被 Oracle 12c 用户访问。

5. 在 Oracle 12c 数据库中使用 `IMPDP` 工具执行导入操作。以下是一个示例命令：

```sql
impdp username/password@service_name directory=directory_name dumpfile=backup_file.dmp logfile=import.log remap_schema=source_user:target_user
```

其中：
- `username/password` 是 Oracle 12c 数据库的用户名和密码。
- `service_name` 是 Oracle 12c 数据库的服务名。
- `directory_name` 是步骤 3 中创建的目录对象的名称。
- `backup_file.dmp` 是备份文件的名称。
- `import.log` 是导入过程的日志文件名称。
- `source_user` 是备份文件中的源用户（schema）名称。
- `target_user` 是步骤 1 中创建的目标用户（schema）名称。

请根据实际情况替换上述命令中的参数，并确保正确设置了用户名、密码、服务名、目录对象和文件名等信息。

执行导入命令后，Oracle 12c 数据库将开始导入操作，并将数据转换为目标编码 `AL32UTF8`。

请注意，导入过程可能需要一些时间，具体取决于备份文件的大小和服务器性能。在导入过程中，可以查看导入日志文件以获取进度和错误信息。

在执行任何数据库操作之前，请务必备份数据库以防止数据丢失。

### 查询数据库编码

```sql
-- 查看数据库编码
SELECT value
FROM nls_database_parameters
WHERE parameter = 'NLS_CHARACTERSET';

-- 查看会话编码
SELECT value
FROM nls_session_parameters
WHERE parameter = 'NLS_CHARACTERSET';

```

### 迁移dbf文件目录（磁盘满的时候）

**[只支持同一个数据库示例下的dbf文件的不同目录之间的迁移，不支持跨数据库示例迁移](https://blog.51cto.com/u_15349616/3703787)**

检查目标数据库中是否已经存在同名的数据文件。可以使用以下查询语句检查：
```sql
SELECT file_name FROM dba_data_files WHERE file_name = '/u01/app/oracle/oradata/xe/ZY3.dbf';
```
如果查询结果返回了数据文件路径，表示该文件已经存在于目标数据库中。你可以选择使用现有的文件，或者选择不同的文件名进行迁移。
如果你希望使用现有的文件，你可以跳过添加数据文件的步骤。在目标数据库中，直接使用现有的数据文件进行恢复和打开表空间的操作。
如果你想使用不同的文件名进行迁移，你可以选择修改目标数据库中的数据文件名称。首先，使用以下语句将表空间设置为OFFLINE状态：
```sql
ALTER TABLESPACE ZY OFFLINE;
```
然后，使用以下语句修改数据文件的名称：
```sql
ALTER DATABASE RENAME FILE '/u01/app/oracle/oradata/xe/ZY3.dbf' TO '/u01/app/oracle/oradata/xe/NewFileName.dbf';
```
最后，使用以下语句将表空间设置为ONLINE状态：
```sql
ALTER TABLESPACE ZY ONLINE;
```
这样，你就可以在目标数据库中使用新的文件名进行迁移。
请注意，在进行任何数据库迁移操作之前，务必备份目标数据库以防止数据丢失或损坏。

**要迁移Oracle数据库的DBF文件，你可以按照以下步骤进行处理：**
1. 在目标数据库中创建相应的表空间：在目标数据库中使用`CREATE TABLESPACE`语句创建与源数据库相同的表空间。确保表空间的名称和数据文件的路径与源数据库相匹配。
2. 将源数据库的DBF文件复制到目标数据库：将源数据库的DBF文件复制到目标数据库的相应路径下。确保文件的权限和所有权与目标数据库所在的操作系统相匹配。
3. 在目标数据库中将DBF文件添加到表空间：使用`ALTER TABLESPACE`语句将复制的DBF文件添加到目标数据库的相应表空间中。例如：
   ```sql
   ALTER TABLESPACE your_tablespace_name ADD DATAFILE '/path/to/your/dbf/file.dbf' SIZE 100M;
   ```
   这将在目标数据库中将DBF文件添加到指定的表空间中，并指定文件的大小。
4. 在目标数据库中进行数据文件的恢复：如果DBF文件是从一个活动的源数据库复制过来的，你可能不需要进行数据文件的恢复。但如果DBF文件是从一个非活动的源数据库复制过来的，你需要使用Oracle的`RECOVER`命令对目标数据库中的数据文件进行恢复。例如：
   ```sql
   RECOVER DATAFILE '/path/to/your/dbf/file.dbf';
   ```
   这将对指定的数据文件进行恢复操作。
5. 在目标数据库中打开表空间：使用`ALTER TABLESPACE`语句将表空间设置为可用状态。例如：
   ```sql
   ALTER TABLESPACE your_tablespace_name ONLINE;
   ```
   这将打开指定的表空间，使其可供数据库使用。
完成以上步骤后，你的DBF文件应该已成功迁移到目标数据库，并可以在目标数据库中使用。请注意，在进行任何数据库迁移操作之前，务必备份源数据库以防止数据丢失或损坏。


### 特殊问题记录

1）[使用EasyConnect后，用navicat可以访问数据库，但是用Idea跑项目连接超时怎么办](https://blog.csdn.net/qq_32447361/article/details/121862199)

解决：添加jvm参数`-Djava.net.preferIPv4Stack=true`
![2023-08-08_16-03-25.png](./assets/2023-08-08_16-03-25.png)
