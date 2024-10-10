drop table if exists xxl_job_group cascade;

drop table if exists xxl_job_info cascade;

drop table if exists xxl_job_lock cascade;

drop table if exists xxl_job_log cascade;

drop table if exists xxl_job_log_report cascade;

drop table if exists xxl_job_logglue cascade;

drop table if exists xxl_job_registry cascade;

drop table if exists xxl_job_user cascade;

create table if not exists xxl_job_group
(
    id           integer not null
        primary key,
    app_name     varchar(64),
    title        varchar(12),
    address_type smallint,
    address_list varchar(512)
);

comment on column xxl_job_group.app_name is '执行器AppName';

comment on column xxl_job_group.title is '执行器名称';

comment on column xxl_job_group.address_type is '执行器地址类型：0=自动注册、1=手动录入';

comment on column xxl_job_group.address_list is '执行器地址列表，多地址逗号分隔';

create table if not exists xxl_job_info
(
    id                        integer not null
        primary key,
    job_group                 integer,
    job_cron                  varchar(128),
    job_desc                  varchar(255),
    add_time                  timestamp,
    update_time               timestamp,
    author                    varchar(64),
    alarm_email               varchar(255),
    executor_route_strategy   varchar(50),
    executor_handler          varchar(255),
    executor_param            varchar(512),
    executor_block_strategy   varchar(50),
    executor_timeout          integer,
    executor_fail_retry_count integer,
    glue_type                 varchar(50),
    glue_source               text,
    glue_remark               varchar(128),
    glue_updatetime           timestamp,
    child_jobid               varchar(255),
    trigger_status            smallint,
    trigger_last_time         bigint,
    trigger_next_time         bigint
);

comment on column xxl_job_info.job_group is '执行器主键ID';

comment on column xxl_job_info.job_cron is '任务执行CRON';

comment on column xxl_job_info.author is '作者';

comment on column xxl_job_info.alarm_email is '报警邮件';

comment on column xxl_job_info.executor_route_strategy is '执行器路由策略';

comment on column xxl_job_info.executor_handler is '执行器任务handler';

comment on column xxl_job_info.executor_param is '执行器任务参数';

comment on column xxl_job_info.executor_block_strategy is '阻塞处理策略';

comment on column xxl_job_info.executor_timeout is '任务执行超时时间，单位秒';

comment on column xxl_job_info.executor_fail_retry_count is '失败重试次数';

comment on column xxl_job_info.glue_type is 'GLUE类型';

comment on column xxl_job_info.glue_source is 'GLUE源代码';

comment on column xxl_job_info.glue_remark is 'GLUE备注';

comment on column xxl_job_info.glue_updatetime is 'GLUE更新时间';

comment on column xxl_job_info.child_jobid is '子任务ID，多个逗号分隔';

comment on column xxl_job_info.trigger_status is '调度状态：0-停止，1-运行';

comment on column xxl_job_info.trigger_last_time is '上次调度时间';

comment on column xxl_job_info.trigger_next_time is '下次调度时间';

create table if not exists xxl_job_lock
(
    lock_name varchar(50) not null
        primary key
);

comment on column xxl_job_lock.lock_name is '锁名称';

create table if not exists xxl_job_log
(
    id                        bigint not null
        primary key,
    job_group                 integer,
    job_id                    integer,
    executor_address          varchar(255),
    executor_handler          varchar(255),
    executor_param            varchar(512),
    executor_sharding_param   varchar(20),
    executor_fail_retry_count integer,
    trigger_time              timestamp,
    trigger_code              integer,
    trigger_msg               text,
    handle_time               timestamp,
    handle_code               integer,
    handle_msg                text,
    alarm_status              smallint
);

comment on column xxl_job_log.job_group is '执行器主键ID';

comment on column xxl_job_log.job_id is '任务，主键ID';

comment on column xxl_job_log.executor_address is '执行器地址，本次执行的地址';

comment on column xxl_job_log.executor_handler is '执行器任务handler';

comment on column xxl_job_log.executor_param is '执行器任务参数';

comment on column xxl_job_log.executor_sharding_param is '执行器任务分片参数，格式如 1/2';

comment on column xxl_job_log.executor_fail_retry_count is '失败重试次数';

comment on column xxl_job_log.trigger_time is '调度-时间';

comment on column xxl_job_log.trigger_code is '调度-结果';

comment on column xxl_job_log.trigger_msg is '调度-日志';

comment on column xxl_job_log.handle_time is '执行-时间';

comment on column xxl_job_log.handle_code is '执行-状态';

comment on column xxl_job_log.handle_msg is '执行-日志';

comment on column xxl_job_log.alarm_status is '告警状态：0-默认、1-无需告警、2-告警成功、3-告警失败';

create index if not exists "I_trigger_time"
    on xxl_job_log (trigger_time);

create index if not exists "I_handle_code"
    on xxl_job_log (handle_code);

create table if not exists xxl_job_log_report
(
    id            integer not null
        primary key,
    trigger_day   timestamp,
    running_count integer,
    suc_count     integer,
    fail_count    integer
);

comment on column xxl_job_log_report.trigger_day is '调度-时间';

comment on column xxl_job_log_report.running_count is '运行中-日志数量';

comment on column xxl_job_log_report.suc_count is '执行成功-日志数量';

comment on column xxl_job_log_report.fail_count is '执行失败-日志数量';

create index if not exists i_trigger_day
    on xxl_job_log_report (trigger_day);

create table if not exists xxl_job_logglue
(
    id          integer not null
        primary key,
    job_id      integer,
    glue_type   varchar(50),
    glue_source text,
    glue_remark varchar(128),
    add_time    timestamp,
    update_time timestamp
);

comment on column xxl_job_logglue.job_id is '任务，主键ID';

comment on column xxl_job_logglue.glue_type is 'GLUE类型';

comment on column xxl_job_logglue.glue_source is 'GLUE源代码';

comment on column xxl_job_logglue.glue_remark is 'GLUE备注';

create table if not exists xxl_job_registry
(
    id             integer not null
        primary key,
    registry_group varchar(50),
    registry_key   varchar(255),
    registry_value varchar(255),
    update_time    timestamp
);

create index if not exists i_g_k_v
    on xxl_job_registry (registry_group, registry_key, registry_value);

create table if not exists xxl_job_user
(
    id         integer not null
        primary key,
    username   varchar(50),
    password   varchar(50),
    role       smallint,
    permission varchar(255)
);

comment on column xxl_job_user.username is '账号';

comment on column xxl_job_user.password is '密码';

comment on column xxl_job_user.role is '角色：0-普通用户、1-管理员';

comment on column xxl_job_user.permission is '权限：执行器ID列表，多个逗号分割';

create index if not exists i_username
    on xxl_job_user (username);

insert into xxl_job_group (id, app_name, title, address_type, address_list)
values (1, 'xxl-job-executor-sample', '示例执行器', 0, null);
insert into xxl_job_info (id, job_group, job_cron, job_desc, add_time, update_time, author, alarm_email,
                          executor_route_strategy, executor_handler, executor_param, executor_block_strategy,
                          executor_timeout, executor_fail_retry_count, glue_type, glue_source, glue_remark,
                          glue_updatetime, child_jobid, trigger_status, trigger_last_time, trigger_next_time)
values (1, 1, '0 0 0 * * ? *', '测试任务1', '2018-11-03 22:21:31.000000', '2018-11-03 22:21:31.000000', 'XXL', '',
        'FIRST', 'demoJobHandler', '', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化',
        '2018-11-03 22:21:31.000000', '', 0, 0, 0);
insert into xxl_job_lock (lock_name)
values ('schedule_lock');;;;;
insert into xxl_job_user (id, username, password, role, permission)
values (1, 'admin', 'e10adc3949ba59abbe56e057f20f883e', 1, null);

-- 主键自增
DROP SEQUENCE IF EXISTS xxl_job_group_id_seq CASCADE;
CREATE SEQUENCE xxl_job_group_id_seq
    START WITH 2
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
alter table xxl_job_group
    alter column id set default nextval('xxl_job_group_id_seq');

DROP SEQUENCE IF EXISTS xxl_job_info_id_seq CASCADE;
CREATE SEQUENCE xxl_job_info_id_seq
    START WITH 2
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
alter table xxl_job_info
    alter column id set default nextval('xxl_job_info_id_seq');

DROP SEQUENCE IF EXISTS xxl_job_log_id_seq CASCADE;
CREATE SEQUENCE xxl_job_log_id_seq
    START WITH 2
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
alter table xxl_job_log
    alter column id set default nextval('xxl_job_log_id_seq');

DROP SEQUENCE IF EXISTS xxl_job_log_report_id_seq CASCADE;
CREATE SEQUENCE xxl_job_log_report_id_seq
    START WITH 2
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
alter table xxl_job_log_report
    alter column id set default nextval('xxl_job_log_report_id_seq');

DROP SEQUENCE IF EXISTS xxl_job_logglue_id_seq CASCADE;
CREATE SEQUENCE xxl_job_logglue_id_seq
    START WITH 2
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
alter table xxl_job_logglue
    alter column id set default nextval('xxl_job_logglue_id_seq');

DROP SEQUENCE IF EXISTS xxl_job_registry_id_seq CASCADE;
CREATE SEQUENCE xxl_job_registry_id_seq
    START WITH 2
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
alter table xxl_job_registry
    alter column id set default nextval('xxl_job_registry_id_seq');

DROP SEQUENCE IF EXISTS xxl_job_user_id_seq CASCADE;
CREATE SEQUENCE xxl_job_user_id_seq
    START WITH 2
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
alter table xxl_job_user
    alter column id set default nextval('xxl_job_user_id_seq');

-- 取消一些字段的非空限制，会影响定时任务执行
alter table public.xxl_job_log
    alter column executor_fail_retry_count drop not null;

alter table public.xxl_job_log
    alter column trigger_code drop not null;

alter table public.xxl_job_log
    alter column handle_code drop not null;

alter table public.xxl_job_log
    alter column alarm_status drop not null;
