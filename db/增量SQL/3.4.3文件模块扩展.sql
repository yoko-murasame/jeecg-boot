---------------- 新增的附件管理表 --------------------
drop table if exists sys_upload;

create table sys_upload
(
    id          varchar(32) not null,
    file_name   varchar(255),
    url         varchar(255),
    create_by   varchar(32),
    create_time timestamp(6),
    update_by   varchar(32),
    update_time timestamp(6),
    md5         varchar(200)
);

comment on table sys_upload is '文件信息表';

comment on column sys_upload.id is '主键id';

comment on column sys_upload.file_name is '文件名称';

comment on column sys_upload.url is '文件地址';

comment on column sys_upload.create_by is '创建人登录名称';

comment on column sys_upload.create_time is '创建日期';

comment on column sys_upload.update_by is '更新人登录名称';

comment on column sys_upload.update_time is '更新日期';

comment on column sys_upload.md5 is '文件md5值';

alter table sys_upload
    owner to postgres;



---------------- 知识库 - 目录表 --------------------
drop table if exists technical_folder;

create table technical_folder
(
    id                varchar(32) not null,
    create_by         varchar(255),
    create_time       timestamp(6),
    update_by         varchar(255),
    update_time       timestamp(6),
    name              varchar(255),
    level             integer,
    folder_order      integer,
    parent_id         varchar(32),
    project_id        varchar(32),
    project_name      varchar(255),
    child_folder_size integer,
    type              smallint,
    child_file_size   integer,
    enabled           smallint default 1,
    business_id       varchar(36),
    business_name     varchar(255),
    tags              varchar(255)
);

comment on column technical_folder.name is '目录名称';

comment on column technical_folder.level is '层级';

comment on column technical_folder.folder_order is '顺序：每个level&parentId下从0开始标记';

comment on column technical_folder.parent_id is '父层级';

comment on column technical_folder.project_id is '关联项目id';

comment on column technical_folder.project_name is '关联项目名称，冗余';

comment on column technical_folder.child_folder_size is '子目录个数，冗余';

comment on column technical_folder.type is '目录类型，枚举类：图纸1、模型2';

comment on column technical_folder.child_file_size is '子文件（关联）个数，冗余';

comment on column technical_folder.enabled is '是否启用 1启用 0禁用，用作删除判断';

comment on column technical_folder.business_id is '业务id（各类业务表单id）';

comment on column technical_folder.business_name is '业务名称';

comment on column technical_folder.tags is '标签';

alter table technical_folder
    owner to postgres;

-- 增量SQL
alter table technical_folder
    add tags varchar(255);
comment on column technical_folder.tags is '标签';



---------------- 知识库 - 文件表 --------------------
drop table if exists technical_file;

create table technical_file
(
    id            varchar(32) not null,
    create_by     varchar(255),
    create_time   timestamp(6),
    update_by     varchar(255),
    update_time   timestamp(6),
    name          varchar(255),
    version       integer,
    folder_id     varchar(32),
    file_order    integer,
    project_id    varchar(32),
    project_name  varchar(255),
    oss_file      text,
    bimface_file  text,
    type          integer,
    suffix        varchar(255),
    enabled       smallint,
    current_show  smallint,
    thumbnail     varchar(255),
    size          varchar(255),
    changes       text,
    comments      text,
    problems      text,
    upload_by     varchar(255),
    business_id   varchar(40),
    business_name varchar(255),
    tags          varchar(255)
);

comment on column technical_file.name is '文件名，相同时，版本号+1';

comment on column technical_file.version is '版本号';

comment on column technical_file.folder_id is '关联目录';

comment on column technical_file.file_order is '文件顺序，扩展字段，暂时不需要考虑';

comment on column technical_file.project_id is '关联项目id';

comment on column technical_file.project_name is '关联项目名';

comment on column technical_file.oss_file is '关联aliyun oss文件';

comment on column technical_file.bimface_file is '关联bimface file';

comment on column technical_file.type is '类型 int';

comment on column technical_file.suffix is '后缀 String';

comment on column technical_file.enabled is '逻辑删除';

comment on column technical_file.current_show is '当前使用的版本';

comment on column technical_file.thumbnail is '缩略图';

comment on column technical_file.size is '文件大小';

comment on column technical_file.changes is '变更';

comment on column technical_file.comments is '批注';

comment on column technical_file.problems is '问题';

comment on column technical_file.upload_by is '上传人，替代createBy';

comment on column technical_file.business_id is '业务id';

comment on column technical_file.business_name is '业务名称';

comment on column technical_file.tags is '标签';

alter table technical_file
    owner to postgres;

-- 增量SQL
alter table technical_file
    add tags varchar(255);
comment on column technical_file.tags is '标签';



---------------- 知识库 - 目录-用户权限表 --------------------
create table public.technical_folder_user_permission
(
    id varchar(36) not null
        constraint technical_folder_user_permission_pkey
            primary key,
    folder_id varchar(36),
    username varchar(255),
    data_permission_type varchar(36)
);

comment on table public.technical_folder_user_permission is '目录-用户权限表';
comment on column public.technical_folder_user_permission.id is '主键';
comment on column public.technical_folder_user_permission.folder_id is '目录id';
comment on column public.technical_folder_user_permission.username is '用户名';
comment on column public.technical_folder_user_permission.data_permission_type is '数据权限类型';
