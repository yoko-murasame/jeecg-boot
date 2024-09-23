comment on table sys_dict_item is '系统字典-字典项表';

alter table sys_dict_item
    add if not exists parent_id varchar(32);
comment on column sys_dict_item.parent_id is '父id';

-- 新增操作类型：SMS短信发送
DELETE FROM sys_dict WHERE id = '880a895c98afeca9d9ac39f29e67c13e';
INSERT INTO sys_dict (id, dict_name, dict_code, description, del_flag, create_by, create_time, update_by, update_time, type) VALUES ('880a895c98afeca9d9ac39f29e67c13e', '操作类型', 'operate_type', '操作类型', 0, 'admin', '2019-07-22 10:54:29.000000', null, null, 0);
DELETE FROM sys_dict_item WHERE dict_id = '880a895c98afeca9d9ac39f29e67c13e';
INSERT INTO sys_dict_item (id, dict_id, item_text, item_value, description, sort_order, status, create_by, create_time, update_by, update_time, parent_id) VALUES ('5d833f69296f691843ccdd0c91212b6b', '880a895c98afeca9d9ac39f29e67c13e', '修改', '3', '', 3, 1, 'admin', '2019-07-22 10:55:07.000000', 'admin', '2019-07-22 10:55:41.000000', null);
INSERT INTO sys_dict_item (id, dict_id, item_text, item_value, description, sort_order, status, create_by, create_time, update_by, update_time, parent_id) VALUES ('948923658baa330319e59b2213cda97c', '880a895c98afeca9d9ac39f29e67c13e', '添加', '2', '', 2, 1, 'admin', '2019-07-22 10:54:59.000000', 'admin', '2019-07-22 10:55:36.000000', null);
INSERT INTO sys_dict_item (id, dict_id, item_text, item_value, description, sort_order, status, create_by, create_time, update_by, update_time, parent_id) VALUES ('a1e7d1ca507cff4a480c8caba7c1339e', '880a895c98afeca9d9ac39f29e67c13e', '导出', '6', '', 6, 1, 'admin', '2019-07-22 12:06:50.000000', null, null, null);
INSERT INTO sys_dict_item (id, dict_id, item_text, item_value, description, sort_order, status, create_by, create_time, update_by, update_time, parent_id) VALUES ('bcec04526b04307e24a005d6dcd27fd6', '880a895c98afeca9d9ac39f29e67c13e', '导入', '5', '', 5, 1, 'admin', '2019-07-22 12:06:41.000000', null, null, null);
INSERT INTO sys_dict_item (id, dict_id, item_text, item_value, description, sort_order, status, create_by, create_time, update_by, update_time, parent_id) VALUES ('c53da022b9912e0aed691bbec3c78473', '880a895c98afeca9d9ac39f29e67c13e', '查询', '1', '', 1, 1, 'admin', '2019-07-22 10:54:51.000000', null, null, null);
INSERT INTO sys_dict_item (id, dict_id, item_text, item_value, description, sort_order, status, create_by, create_time, update_by, update_time, parent_id) VALUES ('f80a8f6838215753b05e1a5ba3346d22', '880a895c98afeca9d9ac39f29e67c13e', '删除', '4', '', 4, 1, 'admin', '2019-07-22 10:55:14.000000', 'admin', '2019-07-22 10:55:30.000000', null);
INSERT INTO sys_dict_item (id, dict_id, item_text, item_value, description, sort_order, status, create_by, create_time, update_by, update_time, parent_id) VALUES ('f80a8f6838215753b05e1a5ba3446d25', '880a895c98afeca9d9ac39f29e67c13e', '短信', '7', '', 7, 1, 'admin', '2019-07-22 10:55:14.000000', 'admin', '2019-07-22 10:55:30.000000', null);
