-- 业务基础表
comment on table cb_resource_layer is '城市大脑-资源图层';

alter table cb_resource_layer
    add if not exists online_code varchar(100);

comment on column cb_resource_layer.online_code is 'online列表code';

alter table cb_resource_layer
    add if not exists online_condition varchar(255);

comment on column cb_resource_layer.online_condition is 'online列表条件';

alter table cb_resource_layer
    add if not exists layer_show_name varchar(255);

comment on column cb_resource_layer.layer_show_name is '图层展示名称';

alter table cb_resource_layer
    add if not exists is_default_overlay boolean;

comment on column cb_resource_layer.is_default_overlay is '是否默认叠加';
