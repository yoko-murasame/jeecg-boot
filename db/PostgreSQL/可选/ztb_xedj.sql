DROP TABLE IF EXISTS "public"."ztb_xedj";

create table ztb_xedj
(
    id            varchar(36) not null
        primary key,
    create_by     varchar(50),
    create_time   timestamp,
    update_by     varchar(50),
    update_time   timestamp,
    sys_org_code  varchar(64),
    gcxmmc        varchar(255),
    gchtje        numeric(20, 4),
    gcxmfzr       varchar(255),
    fbfs          varchar(32),
    sgdw          varchar(255),
    sjkgsj        timestamp,
    gcnr          varchar(32),
    htwcjd        text,
    mqxxjd        text,
    mqxczp        text,
    xmczwt        text,
    gcezfjd       text,
    yssj          timestamp,
    wgyscl        text,
    wgzp          text,
    jszl          text,
    qtfj          text,
    current_state varchar(32)
);

comment on column ztb_xedj.create_by is '创建人';

comment on column ztb_xedj.create_time is '创建日期';

comment on column ztb_xedj.update_by is '更新人';

comment on column ztb_xedj.update_time is '更新日期';

comment on column ztb_xedj.sys_org_code is '所属部门';

comment on column ztb_xedj.gcxmmc is '工程项目名称';

comment on column ztb_xedj.gchtje is '工程合同金额';

comment on column ztb_xedj.gcxmfzr is '工程项目负责人';

comment on column ztb_xedj.fbfs is '发包方式';

comment on column ztb_xedj.sgdw is '施工单位';

comment on column ztb_xedj.sjkgsj is '实际开工时间';

comment on column ztb_xedj.gcnr is '工程内容';

comment on column ztb_xedj.htwcjd is '合同完成进度';

comment on column ztb_xedj.mqxxjd is '目前形象进度';

comment on column ztb_xedj.mqxczp is '目前现场照片';

comment on column ztb_xedj.xmczwt is '项目存在问题';

comment on column ztb_xedj.gcezfjd is '工程额支付进度';

comment on column ztb_xedj.yssj is '验收时间';

comment on column ztb_xedj.wgyscl is '完工验收材料';

comment on column ztb_xedj.wgzp is '完工照片';

comment on column ztb_xedj.jszl is '结算资料';

comment on column ztb_xedj.qtfj is '其他附件';

comment on column ztb_xedj.current_state is '当前阶段';

alter table ztb_xedj
    owner to postgres;

-- 需要的字典
INSERT INTO public.sys_dict (id, dict_name, dict_code, description, del_flag, create_by, create_time, update_by, update_time, type) VALUES ('1739102386330398721', '招投标-小额登记-发包方式', 'ztb_xedj_fbfs', '招投标-小额登记-发包方式', 0, 'admin', '2023-12-25 09:54:54.525000', null, null, null);
INSERT INTO public.sys_dict (id, dict_name, dict_code, description, del_flag, create_by, create_time, update_by, update_time, type) VALUES ('1737797096980955138', '招投标-小额登记-填写阶段', 'ztb_xedj_state', '招投标-小额登记-填写阶段', 0, 'admin', '2023-12-21 19:28:09.290000', null, null, null);

INSERT INTO public.sys_dict_item (id, dict_id, item_text, item_value, description, sort_order, status, create_by, create_time, update_by, update_time) VALUES ('1737797184415416321', '1737797096980955138', '工程开工登记', '1', '工程开工登记', 1, 1, 'admin', '2023-12-21 19:28:30.132000', null, null);
INSERT INTO public.sys_dict_item (id, dict_id, item_text, item_value, description, sort_order, status, create_by, create_time, update_by, update_time) VALUES ('1737797220410933249', '1737797096980955138', '工程过程登记', '2', '工程过程登记', 2, 1, 'admin', '2023-12-21 19:28:38.726000', null, null);
INSERT INTO public.sys_dict_item (id, dict_id, item_text, item_value, description, sort_order, status, create_by, create_time, update_by, update_time) VALUES ('1737797260412010498', '1737797096980955138', '完工登记', '3', '完工登记', 3, 1, 'admin', '2023-12-21 19:28:48.253000', null, null);
INSERT INTO public.sys_dict_item (id, dict_id, item_text, item_value, description, sort_order, status, create_by, create_time, update_by, update_time) VALUES ('1739102910899417090', '1739102386330398721', '直接委托', '1', '直接委托', 1, 1, 'admin', '2023-12-25 09:56:59.594000', null, null);
INSERT INTO public.sys_dict_item (id, dict_id, item_text, item_value, description, sort_order, status, create_by, create_time, update_by, update_time) VALUES ('1739102952007790594', '1739102386330398721', '公开招标', '2', '公开招标', 2, 1, 'admin', '2023-12-25 09:57:09.394000', null, null);
INSERT INTO public.sys_dict_item (id, dict_id, item_text, item_value, description, sort_order, status, create_by, create_time, update_by, update_time) VALUES ('1739102992298274817', '1739102386330398721', '竞争评选', '3', '竞争评选', 3, 1, 'admin', '2023-12-25 09:57:19.000000', null, null);
INSERT INTO public.sys_dict_item (id, dict_id, item_text, item_value, description, sort_order, status, create_by, create_time, update_by, update_time) VALUES ('1739103029145235458', '1739102386330398721', '随机抽取', '4', '随机抽取', 4, 1, 'admin', '2023-12-25 09:57:27.782000', null, null);
