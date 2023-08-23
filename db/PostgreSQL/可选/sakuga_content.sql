DROP TABLE IF EXISTS "public"."sakuga_content";

create table sakuga_content
(
    id           varchar(36) not null
        primary key,
    create_by    varchar(50),
    create_time  timestamp(6),
    update_by    varchar(50),
    update_time  timestamp(6),
    sys_org_code varchar(64),
    title        varchar(100),
    anime_name   varchar(100),
    author       varchar(100),
    company      varchar(100),
    content      text,
    tags         text,
    picture      text,
    video        varchar(500),
    file         varchar(500),
    content_tsv  tsvector
);

comment on column sakuga_content.create_by is '创建人';

comment on column sakuga_content.create_time is '创建日期';

comment on column sakuga_content.update_by is '更新人';

comment on column sakuga_content.update_time is '更新日期';

comment on column sakuga_content.sys_org_code is '所属部门';

comment on column sakuga_content.title is '片段标题';

comment on column sakuga_content.anime_name is '动画名称';

comment on column sakuga_content.author is '作监';

comment on column sakuga_content.company is '动画公司';

comment on column sakuga_content.content is '内容描述';

comment on column sakuga_content.tags is '标签';

comment on column sakuga_content.picture is '图片';

comment on column sakuga_content.video is '视频';

comment on column sakuga_content.file is '附件';

alter table sakuga_content
    owner to postgres;

create index sakuga_content_content_gin_index
    on sakuga_content using gin (content_tsv);

-- 创建触发器
CREATE OR REPLACE FUNCTION update_content_tsv() RETURNS TRIGGER AS $$
BEGIN
    NEW.content_tsv := to_tsvector('chinese', COALESCE(NEW.content, ''));
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_update_content_tsv
    BEFORE INSERT OR UPDATE ON sakuga_content
    FOR EACH ROW
EXECUTE FUNCTION update_content_tsv();
