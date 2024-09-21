comment on table sys_dict_item is '系统字典-字典项表';

alter table sys_dict_item
    add if not exists parent_id varchar(32);
comment on column sys_dict_item.parent_id is '父id';
