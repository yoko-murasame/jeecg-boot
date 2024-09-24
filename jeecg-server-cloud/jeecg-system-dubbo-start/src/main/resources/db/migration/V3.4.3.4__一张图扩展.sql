-- app授权表
create table if not exists app_authorize
(
    id         varchar(36) not null
        primary key,
    app_key    varchar(255),
    app_secret varchar(255)
);

-- 资源文件表
create table if not exists cb_resource_file
(
    id            bigint not null
        constraint cb_video_copy1_pkey2
            primary key,
    create_by     varchar(64),
    create_time   timestamp(6),
    update_by     varchar(64),
    update_time   timestamp(6),
    status        integer,
    is_deleted    integer default 0,
    file_name     varchar(255),
    file_path     varchar(255),
    file_type     varchar(255),
    file_describe varchar(255),
    belong_table  varchar(255),
    table_id      bigint
);

comment on table cb_resource_file is '资源文件表';

comment on column cb_resource_file.id is '主键';

comment on column cb_resource_file.create_by is '创建人';

comment on column cb_resource_file.create_time is '创建时间';

comment on column cb_resource_file.update_by is '修改人';

comment on column cb_resource_file.update_time is '修改时间';

comment on column cb_resource_file.status is '状态';

comment on column cb_resource_file.is_deleted is '是否已删除';

comment on column cb_resource_file.file_name is '文件名';

comment on column cb_resource_file.file_path is '文件路径';

comment on column cb_resource_file.file_type is '文件类型';

comment on column cb_resource_file.file_describe is '文件描述';

comment on column cb_resource_file.belong_table is '所属表';

comment on column cb_resource_file.table_id is '表id';

-- 资源图层表
create table if not exists cb_resource_layer
(
    id                    bigint not null
        primary key,
    parent_id             bigint  default 0,
    code                  varchar(255),
    name                  varchar(255),
    datasource            varchar(3000),
    dataset               varchar(255),
    with_extra_data       varchar(255),
    type                  varchar(255),
    type_json             text,
    remark                varchar(255),
    is_deleted            integer default 0,
    sort                  integer,
    create_by             varchar(64),
    create_time           timestamp(6),
    update_by             varchar(64),
    update_time           timestamp(6),
    status                integer default 0,
    ding_user_id          varchar(64),
    service_address       varchar(255),
    menu_type             varchar(255),
    data_type             varchar(255),
    display_modal         varchar(255),
    arrangement           varchar(255),
    style_name            varchar(255),
    child_style_name      varchar(255),
    data_address          varchar(255),
    scene_type            varchar(255),
    visual_angle_lon      double precision,
    visual_angle_lat      double precision,
    visual_angle_altitude double precision,
    visual_angle_azimuth  double precision,
    visual_angle_angle    double precision,
    color_arr             varchar(255),
    rotate                integer,
    display_legend        smallint,
    stacked               smallint,
    icon_matching_field   varchar(255),
    show_label            smallint,
    assembly              varchar(255),
    hide_header           smallint,
    chart_height          varchar(255),
    graphical_width       varchar(255),
    show_title            varchar(255),
    show_filed            varchar(255),
    function_type         varchar(255),
    query_criteria        varchar(1000),
    sort_icon             smallint,
    show_sum              varchar(255),
    tip                   varchar,
    bottom_type           varchar(255),
    coordinate            varchar(255),
    query_sql             varchar(500),
    max_level             integer,
    min_level             integer,
    face_hollow           varchar(255),
    face_line             varchar(255),
    line_width            double precision,
    line_type             varchar(255),
    scene_color           varchar(255),
    show_childen          boolean,
    childen_key           varchar(255),
    childen_title         varchar(255),
    is_temp_map           boolean,
    online_code           varchar(100),
    online_condition      varchar(255),
    company               varchar(255),
    district_code         varchar(255),
    layer_show_name       varchar(255),
    is_default_overlay    boolean
);

comment on table cb_resource_layer is '城市大脑-资源图层';

comment on column cb_resource_layer.id is '主键';

comment on column cb_resource_layer.parent_id is '父级菜单';

comment on column cb_resource_layer.code is '菜单编号';

comment on column cb_resource_layer.name is '菜单名称';

comment on column cb_resource_layer.datasource is '数据源';

comment on column cb_resource_layer.dataset is '数据集';

comment on column cb_resource_layer.with_extra_data is '额外数据';

comment on column cb_resource_layer.type is '数据类型';

comment on column cb_resource_layer.type_json is 'type涉及的配置';

comment on column cb_resource_layer.remark is '备注';

comment on column cb_resource_layer.is_deleted is '是否删除';

comment on column cb_resource_layer.sort is '排序';

comment on column cb_resource_layer.create_by is '创建人';

comment on column cb_resource_layer.create_time is '创建事件';

comment on column cb_resource_layer.update_by is '更新人';

comment on column cb_resource_layer.update_time is '更新事件';

comment on column cb_resource_layer.status is '状态';

comment on column cb_resource_layer.service_address is '服务地址';

comment on column cb_resource_layer.menu_type is '菜单类型';

comment on column cb_resource_layer.data_type is '数据类型';

comment on column cb_resource_layer.display_modal is '显示方式';

comment on column cb_resource_layer.arrangement is '排列方式';

comment on column cb_resource_layer.style_name is '样式';

comment on column cb_resource_layer.child_style_name is '子级样式';

comment on column cb_resource_layer.data_address is '属性数据地址';

comment on column cb_resource_layer.scene_type is '数据类型';

comment on column cb_resource_layer.visual_angle_lon is '视角经度';

comment on column cb_resource_layer.visual_angle_lat is '视角纬度';

comment on column cb_resource_layer.visual_angle_altitude is '视角高度';

comment on column cb_resource_layer.visual_angle_azimuth is '视角方位角';

comment on column cb_resource_layer.visual_angle_angle is '视角倾斜角度';

comment on column cb_resource_layer.color_arr is '颜色组';

comment on column cb_resource_layer.rotate is '柱状图旋转角度';

comment on column cb_resource_layer.display_legend is '显示图例模式：0不显示，1:显示（不显示数量）；2显示，并显示数量';

comment on column cb_resource_layer.stacked is '是否堆积';

comment on column cb_resource_layer.icon_matching_field is '图标匹配字段';

comment on column cb_resource_layer.show_label is '是否显示标签';

comment on column cb_resource_layer.assembly is '组件名称';

comment on column cb_resource_layer.hide_header is '列表是否显示表头';

comment on column cb_resource_layer.chart_height is '图标高度';

comment on column cb_resource_layer.graphical_width is '图标高度';

comment on column cb_resource_layer.show_title is '列表——显示名称';

comment on column cb_resource_layer.show_filed is '列表——显示字段';

comment on column cb_resource_layer.function_type is '面、三维面——功能类型';

comment on column cb_resource_layer.query_criteria is 'api接口查询条件';

comment on column cb_resource_layer.sort_icon is 'list是否显示排序图标';

comment on column cb_resource_layer.show_sum is '是否显示合计';

comment on column cb_resource_layer.bottom_type is '底部类型';

comment on column cb_resource_layer.coordinate is '坐标系';

comment on column cb_resource_layer.query_sql is '服务sql';

comment on column cb_resource_layer.max_level is '最大层级';

comment on column cb_resource_layer.min_level is '最小层级';

comment on column cb_resource_layer.face_hollow is '面镂空';

comment on column cb_resource_layer.face_line is '面范围线';

comment on column cb_resource_layer.line_width is '线宽度';

comment on column cb_resource_layer.line_type is '线型';

comment on column cb_resource_layer.scene_color is '颜色';

comment on column cb_resource_layer.show_childen is '是否显示明细';

comment on column cb_resource_layer.childen_key is '明细Key';

comment on column cb_resource_layer.childen_title is '明细title';

comment on column cb_resource_layer.is_temp_map is '是否是临时图层';

comment on column cb_resource_layer.online_code is 'online列表code';

comment on column cb_resource_layer.online_condition is 'online列表条件';

comment on column cb_resource_layer.company is '所属公司';

comment on column cb_resource_layer.district_code is '所属区域';

comment on column cb_resource_layer.layer_show_name is '图层展示名称';

comment on column cb_resource_layer.is_default_overlay is '是否默认叠加';

-- 资源权限表
create table if not exists cb_role_layer
(
    id                bigint not null
        primary key,
    resource_layer_id bigint,
    app_role_id       bigint
);

comment on table cb_role_layer is '资源权限表';

comment on column cb_role_layer.id is 'id';

comment on column cb_role_layer.resource_layer_id is '图层id';

comment on column cb_role_layer.app_role_id is '权限id';


-- 补充字段

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
