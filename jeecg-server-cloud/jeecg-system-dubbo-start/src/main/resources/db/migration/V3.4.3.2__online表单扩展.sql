comment on table onl_cgform_head is 'online表单-主配置表';

alter table onl_cgform_head
    add if not exists data_rule_perms varchar(255);

comment on column onl_cgform_head.data_rule_perms is '数据权限标识';

alter table onl_cgform_head
    add if not exists online_init_query_param_getter varchar(2000);

comment on column onl_cgform_head.online_init_query_param_getter is '数据初始化JS增强';
