comment on table onl_cgform_head is 'online表单-主配置表';

alter table onl_cgform_head
    add if not exists data_rule_perms varchar(255);

comment on column onl_cgform_head.data_rule_perms is '数据权限标识';

alter table onl_cgform_head
    add if not exists online_init_query_param_getter varchar(2000);

comment on column onl_cgform_head.online_init_query_param_getter is '数据初始化JS增强';

alter table onl_cgform_head
    add if not exists online_vue_watch_js_str varchar(2000);

comment on column onl_cgform_head.online_vue_watch_js_str is 'Vue2监听器JS增强';

alter table onl_cgform_head
    add if not exists view_table boolean;

comment on column onl_cgform_head.view_table is '是否为视图表';

alter table onl_cgform_head
    add if not exists hide_action_button boolean;

comment on column onl_cgform_head.hide_action_button is '是否隐藏action按钮';

alter table onl_cgform_field
    add if not exists dict_lazy_load smallint;

comment on column onl_cgform_field.dict_lazy_load is '字典懒加载';

alter table onl_cgform_field
    add if not exists scoped_slots varchar(255);

comment on column onl_cgform_field.scoped_slots is '自定义scopedSlots插槽';

alter table onl_cgform_field
    add if not exists scoped_slots_render_code varchar(1000);

comment on column onl_cgform_field.scoped_slots_render_code is 'slot渲染Vue代码';



comment on table onl_auth_page is 'onl_auth_page对象-按钮权限配置表';

alter table onl_auth_page
    add if not exists alias varchar(255);

comment on column onl_auth_page.alias is '按钮别名';
