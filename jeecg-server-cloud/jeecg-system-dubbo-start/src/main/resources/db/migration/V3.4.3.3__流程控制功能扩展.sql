-- 业务基础表
comment on table ext_act_process_form is '业务基础表';

-- 流程节点表
comment on table ext_act_process_node is '流程节点表';

alter table ext_act_process_node
    add if not exists model_and_view_type varchar(50);

comment on column ext_act_process_node.model_and_view_type is '表单类型 1 Online表单 2 kform设计器 3 自定义开发 4 online列表';

alter table ext_act_process_node
    add if not exists show_task boolean;

comment on column ext_act_process_node.show_task is '是否显示任务处理模块';

alter table ext_act_process_node
    add if not exists show_process boolean;

comment on column ext_act_process_node.show_process is '是否显示流程图模块';

alter table ext_act_process_node
    add if not exists online_code varchar(50);

comment on column ext_act_process_node.online_code is 'online列表编码';

alter table ext_act_process_node
    add if not exists online_form_config varchar(500);

comment on column ext_act_process_node.online_form_config is 'online表单、列表配置';

alter table ext_act_process_node
    add if not exists online_init_query_param_getter varchar(1000);

comment on column ext_act_process_node.online_init_query_param_getter is 'Online查询筛选参数获取器（JS增强支持await）';

alter table ext_act_process_node
    add if not exists show_reject boolean;

comment on column ext_act_process_node.show_reject is '是否显示驳回按钮';

alter table ext_act_process_node
    add if not exists custom_task_module varchar(500);

comment on column ext_act_process_node.custom_task_module is '自定义任务处理模块';


-- 流程节点部署表
comment on table ext_act_process_node_deploy is '流程节点部署表';

alter table ext_act_process_node_deploy
    add if not exists model_and_view_type varchar(50);

comment on column ext_act_process_node_deploy.model_and_view_type is '表单类型 1 Online表单 2 kform设计器 3 自定义开发 4 online列表';

alter table ext_act_process_node_deploy
    add if not exists show_task boolean;

comment on column ext_act_process_node_deploy.show_task is '是否显示任务处理模块';

alter table ext_act_process_node_deploy
    add if not exists show_process boolean;

comment on column ext_act_process_node_deploy.show_process is '是否显示流程图模块';

alter table ext_act_process_node_deploy
    add if not exists online_code varchar(50);

comment on column ext_act_process_node_deploy.online_code is 'online列表编码';

alter table ext_act_process_node_deploy
    add if not exists online_form_config varchar(500);

comment on column ext_act_process_node_deploy.online_form_config is 'online表单、列表配置';

alter table ext_act_process_node_deploy
    add if not exists online_init_query_param_getter varchar(1000);

comment on column ext_act_process_node_deploy.online_init_query_param_getter is 'Online查询筛选参数获取器（JS增强支持await）';

alter table ext_act_process_node_deploy
    add if not exists show_reject boolean;

comment on column ext_act_process_node_deploy.show_reject is '是否显示驳回按钮';

alter table ext_act_process_node_deploy
    add if not exists custom_task_module varchar(500);

comment on column ext_act_process_node_deploy.custom_task_module is '自定义任务处理模块';
