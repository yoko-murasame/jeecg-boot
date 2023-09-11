-- 新增的附件管理表
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

