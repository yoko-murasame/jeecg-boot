comment on table onl_cgform_head is 'online表单-主配置表';

alter table onl_cgform_head
    add if not exists action_column_fixed varchar(255);

comment on column onl_cgform_head.action_column_fixed is '操作列固定位置';
