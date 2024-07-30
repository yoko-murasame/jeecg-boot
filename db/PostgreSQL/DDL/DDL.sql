-- 完整DDL

-- smallint和boolean转换 BEGIN
DO $$
    begin
        --创建转换fun：smallint_to_boolean（对应错误: 字段 "is_active_" 的类型为 boolean, 但表达式的类型为 smallint）
        CREATE OR REPLACE FUNCTION "public"."smallint_to_boolean"("i" int2)
            RETURNS "pg_catalog"."bool" AS $BODY$
        BEGIN
            RETURN (i::int2)::integer::bool;
        END;
        $BODY$
            LANGUAGE plpgsql VOLATILE
                             COST 100;
        --创建cast规则
        IF EXISTS (
            SELECT *
            FROM pg_cast
                     JOIN pg_type src ON pg_cast.castsource = src.oid
                     JOIN pg_type tgt ON pg_cast.casttarget = tgt.oid
            WHERE src.typname = 'int2'
              AND tgt.typname = 'bool'
        ) THEN
            DROP CAST (SMALLINT as BOOLEAN);
        END IF;
        create cast (SMALLINT as BOOLEAN) with function smallint_to_boolean as ASSIGNMENT;

        --创建转换fun：boolean_to_smallint（对应错误: 字段 "is_active_" 的类型为 smallint, 但表达式的类型为 boolean）
        CREATE OR REPLACE FUNCTION "public"."boolean_to_smallint"("b" bool)
            RETURNS "pg_catalog"."int2" AS $BODY$
        BEGIN
            RETURN (b::boolean)::bool::int;
        END;
        $BODY$
            LANGUAGE plpgsql VOLATILE
                             COST 100;
        --创建cast规则
        IF EXISTS (
            SELECT *
            FROM pg_cast
                     JOIN pg_type src ON pg_cast.castsource = src.oid
                     JOIN pg_type tgt ON pg_cast.casttarget = tgt.oid
            WHERE src.typname = 'bool'
              AND tgt.typname = 'int2'
        ) THEN
            DROP CAST (BOOLEAN AS SMALLINT);
        END IF;
        create cast (BOOLEAN as SMALLINT) with function boolean_to_smallint as ASSIGNMENT;
    end;
    $$;
-- smallint和boolean转换 END


-- 分词功能-带分词模块 BEGIN
DO $$
    BEGIN
        IF NOT EXISTS (
            SELECT 1
            FROM pg_extension
            WHERE extname = 'zhparser'
        ) THEN
            CREATE EXTENSION zhparser;
        END IF;
        IF NOT EXISTS (
            SELECT 1
            FROM pg_ts_config
            WHERE cfgname = 'chinese'
        ) THEN
            CREATE TEXT SEARCH CONFIGURATION chinese (PARSER = zhparser);
            ALTER TEXT SEARCH CONFIGURATION chinese
                ADD MAPPING FOR a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z
                    WITH simple;
        END IF;
    END;
    $$;

-- 分词示例模块
drop function if exists update_content_tsv() cascade;
create function update_content_tsv() returns trigger
    language plpgsql
as
$$
BEGIN
    NEW.content_tsv := to_tsvector('chinese', COALESCE(NEW.content, ''));
    RETURN NEW;
END;
$$;

DO $$
    begin
        drop table if exists sakuga_content cascade;

        create table if not exists sakuga_content
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

        create index if not exists sakuga_content_content_gin_index
            on sakuga_content using gin (content_tsv);

        create trigger trg_update_content_tsv
            before insert or update
            on sakuga_content
            for each row
        execute procedure update_content_tsv();
    end;
    $$;
-- 分词功能-带分词模块 END

-- 删表 BEGIN
DO $$
    begin
        drop table if exists act_evt_log cascade;

        drop table if exists act_ge_property cascade;

        drop table if exists act_hi_actinst cascade;

        drop table if exists act_hi_attachment cascade;

        drop table if exists act_hi_comment cascade;

        drop table if exists act_hi_detail cascade;

        drop table if exists act_hi_identitylink cascade;

        drop table if exists act_hi_procinst cascade;

        drop table if exists act_hi_taskinst cascade;

        drop table if exists act_hi_varinst cascade;

        drop table if exists act_id_info cascade;

        drop table if exists act_id_membership cascade;

        drop table if exists act_id_group cascade;

        drop table if exists act_id_user cascade;

        drop table if exists act_procdef_info cascade;

        drop table if exists act_re_model cascade;

        drop table if exists act_ru_event_subscr cascade;

        drop table if exists act_ru_identitylink cascade;

        drop table if exists act_ru_job cascade;

        drop table if exists act_ru_task cascade;

        drop table if exists act_ru_variable cascade;

        drop table if exists act_ge_bytearray cascade;

        drop table if exists act_re_deployment cascade;

        drop table if exists act_ru_execution cascade;

        drop table if exists act_re_procdef cascade;

        drop table if exists ai_control_single cascade;

        drop table if exists ces_order_customer cascade;

        drop table if exists ces_order_goods cascade;

        drop table if exists ces_order_main cascade;

        drop table if exists ces_shop_goods cascade;

        drop table if exists ces_shop_type cascade;

        drop table if exists demo cascade;

        drop table if exists demo_field_def_val_main cascade;

        drop table if exists demo_field_def_val_sub cascade;

        drop table if exists design_form cascade;

        drop table if exists design_form_auth cascade;

        drop table if exists design_form_commuse cascade;

        drop table if exists design_form_data cascade;

        drop table if exists design_form_route cascade;

        drop table if exists design_form_templet cascade;

        drop table if exists design_form_url_auth cascade;

        drop table if exists ext_act_bpm_file cascade;

        drop table if exists ext_act_bpm_log cascade;

        drop table if exists ext_act_design_flow_data cascade;

        drop table if exists ext_act_expression cascade;

        drop table if exists ext_act_flow_data cascade;

        drop table if exists ext_act_listener cascade;

        drop table if exists ext_act_mutil_flow_data cascade;

        drop table if exists ext_act_process cascade;

        drop table if exists ext_act_process_form cascade;

        drop table if exists ext_act_process_node cascade;

        drop table if exists ext_act_process_node_auth cascade;

        drop table if exists ext_act_process_node_deploy cascade;

        drop table if exists ext_act_task_cc cascade;

        drop table if exists ext_act_task_notification cascade;

        drop table if exists ext_biz_leave cascade;

        drop table if exists jeecg_monthly_growth_analysis cascade;

        drop table if exists jeecg_order_customer cascade;

        drop table if exists jeecg_order_main cascade;

        drop table if exists jeecg_order_ticket cascade;

        drop table if exists jeecg_project_nature_income cascade;

        drop table if exists jimu_dict cascade;

        drop table if exists jimu_dict_item cascade;

        drop table if exists jimu_report cascade;

        drop table if exists jimu_report_data_source cascade;

        drop table if exists jimu_report_db cascade;

        drop table if exists jimu_report_db_field cascade;

        drop table if exists jimu_report_db_param cascade;

        drop table if exists jimu_report_link cascade;

        drop table if exists jimu_report_map cascade;

        drop table if exists jimu_report_share cascade;

        drop table if exists jmreport_big_screen cascade;

        drop table if exists jmreport_big_screen_category cascade;

        drop table if exists jmreport_big_screen_visual_map cascade;

        drop table if exists joa_demo cascade;

        drop table if exists onl_auth_data cascade;

        drop table if exists onl_auth_page cascade;

        drop table if exists onl_auth_relation cascade;

        drop table if exists onl_cgform_button cascade;

        drop table if exists onl_cgform_enhance_java cascade;

        drop table if exists onl_cgform_enhance_js cascade;

        drop table if exists onl_cgform_enhance_sql cascade;

        drop table if exists onl_cgform_field cascade;

        drop table if exists onl_cgform_head cascade;

        drop table if exists onl_cgform_index cascade;

        drop table if exists onl_cgreport_head cascade;

        drop table if exists onl_cgreport_item cascade;

        drop table if exists onl_cgreport_param cascade;

        drop table if exists onl_graphreport_head cascade;

        drop table if exists onl_graphreport_item cascade;

        drop table if exists onl_graphreport_templet cascade;

        drop table if exists onl_graphreport_templet_item cascade;

        drop table if exists oss_file cascade;

        drop table if exists qrtz_blob_triggers cascade;

        drop table if exists qrtz_calendars cascade;

        drop table if exists qrtz_cron_triggers cascade;

        drop table if exists qrtz_fired_triggers cascade;

        drop table if exists qrtz_locks cascade;

        drop table if exists qrtz_paused_trigger_grps cascade;

        drop table if exists qrtz_scheduler_state cascade;

        drop table if exists qrtz_simple_triggers cascade;

        drop table if exists qrtz_simprop_triggers cascade;

        drop table if exists qrtz_triggers cascade;

        drop table if exists qrtz_job_details cascade;

        drop table if exists rep_demo_dxtj cascade;

        drop table if exists rep_demo_employee cascade;

        drop table if exists rep_demo_gongsi cascade;

        drop table if exists rep_demo_jianpiao cascade;

        drop table if exists sys_announcement cascade;

        drop table if exists sys_announcement_send cascade;

        drop table if exists sys_category cascade;

        drop table if exists sys_check_rule cascade;

        drop table if exists sys_comment cascade;

        drop table if exists sys_data_log cascade;

        drop table if exists sys_data_source cascade;

        drop table if exists sys_depart cascade;

        drop table if exists sys_depart_permission cascade;

        drop table if exists sys_depart_role cascade;

        drop table if exists sys_depart_role_permission cascade;

        drop table if exists sys_depart_role_user cascade;

        drop table if exists sys_dict cascade;

        drop table if exists sys_dict_item cascade;

        drop table if exists sys_files cascade;

        drop table if exists sys_fill_rule cascade;

        drop table if exists sys_form_file cascade;

        drop table if exists sys_gateway_route cascade;

        drop table if exists sys_log cascade;

        drop table if exists sys_permission cascade;

        drop table if exists sys_permission_data_rule cascade;

        drop table if exists sys_permission_v3 cascade;

        drop table if exists sys_position cascade;

        drop table if exists sys_quartz_job cascade;

        drop table if exists sys_role cascade;

        drop table if exists sys_role_design cascade;

        drop table if exists sys_role_index cascade;

        drop table if exists sys_role_permission cascade;

        drop table if exists sys_sms cascade;

        drop table if exists sys_sms_template cascade;

        drop table if exists sys_tenant cascade;

        drop table if exists sys_third_account cascade;

        drop table if exists sys_user_agent cascade;

        drop table if exists sys_user_depart cascade;

        drop table if exists sys_user_role cascade;

        drop table if exists test_demo cascade;

        drop table if exists test_enhance_select cascade;

        drop table if exists test_note cascade;

        drop table if exists test_one cascade;

        drop table if exists test_online_link cascade;

        drop table if exists test_order_main cascade;

        drop table if exists test_order_product cascade;

        drop table if exists test_person cascade;

        drop table if exists test_shoptype_tree cascade;

        drop table if exists test_two cascade;

        drop table if exists tmp_report_data_1 cascade;

        drop table if exists tmp_report_data_income cascade;

        drop table if exists v3_demo1 cascade;

        drop table if exists v3_hello cascade;

        drop table if exists sys_upload cascade;

        drop table if exists technical_folder cascade;

        drop table if exists technical_file cascade;

        drop table if exists sys_user cascade;

        drop table if exists technical_folder_user_permission cascade;

        drop table if exists cb_resource_file cascade;

        drop table if exists cb_resource_layer cascade;

        drop table if exists cb_role_layer cascade;

        drop table if exists ztb_xedj cascade;

        drop table if exists app_authorize cascade;


    end;
    $$;
-- 删表 END


-- 建表 BEGIN
DO $$
    begin
        create table if not exists act_evt_log
        (
            log_nr_       serial
                primary key,
            type_         varchar(64),
            proc_def_id_  varchar(64),
            proc_inst_id_ varchar(64),
            execution_id_ varchar(64),
            task_id_      varchar(64),
            time_stamp_   timestamp not null,
            user_id_      varchar(255),
            data_         bytea,
            lock_owner_   varchar(255),
            lock_time_    timestamp,
            is_processed_ smallint default 0
        );

        create table if not exists act_ge_property
        (
            name_  varchar(64) not null
                primary key,
            value_ varchar(300),
            rev_   integer
        );

        create table if not exists act_hi_actinst
        (
            id_                varchar(64)  not null
                primary key,
            proc_def_id_       varchar(64)  not null,
            proc_inst_id_      varchar(64)  not null,
            execution_id_      varchar(64)  not null,
            act_id_            varchar(255) not null,
            task_id_           varchar(64),
            call_proc_inst_id_ varchar(64),
            act_name_          varchar(255),
            act_type_          varchar(255) not null,
            assignee_          varchar(255),
            start_time_        timestamp    not null,
            end_time_          timestamp,
            duration_          bigint,
            tenant_id_         varchar(255) default ''::character varying
        );

        create index if not exists act_idx_hi_act_inst_end
            on act_hi_actinst (end_time_);

        create index if not exists act_idx_hi_act_inst_exec
            on act_hi_actinst (execution_id_, act_id_);

        create index if not exists act_idx_hi_act_inst_procinst
            on act_hi_actinst (proc_inst_id_, act_id_);

        create index if not exists act_idx_hi_act_inst_start
            on act_hi_actinst (start_time_);

        create table if not exists act_hi_attachment
        (
            id_           varchar(64) not null
                primary key,
            rev_          integer,
            user_id_      varchar(255),
            name_         varchar(255),
            description_  varchar(4000),
            type_         varchar(255),
            task_id_      varchar(64),
            proc_inst_id_ varchar(64),
            url_          varchar(4000),
            content_id_   varchar(64),
            time_         timestamp
        );

        create table if not exists act_hi_comment
        (
            id_           varchar(64) not null
                primary key,
            type_         varchar(255),
            time_         timestamp   not null,
            user_id_      varchar(255),
            task_id_      varchar(64),
            proc_inst_id_ varchar(64),
            action_       varchar(255),
            message_      varchar(4000),
            full_msg_     bytea
        );

        create table if not exists act_hi_detail
        (
            id_           varchar(64)  not null
                primary key,
            type_         varchar(255) not null,
            proc_inst_id_ varchar(64),
            execution_id_ varchar(64),
            task_id_      varchar(64),
            act_inst_id_  varchar(64),
            name_         varchar(255) not null,
            var_type_     varchar(64),
            rev_          integer,
            time_         timestamp    not null,
            bytearray_id_ varchar(64),
            double_       double precision,
            long_         bigint,
            text_         varchar(4000),
            text2_        varchar(4000)
        );

        create index if not exists act_idx_hi_detail_act_inst
            on act_hi_detail (act_inst_id_);

        create index if not exists act_idx_hi_detail_name
            on act_hi_detail (name_);

        create index if not exists act_idx_hi_detail_proc_inst
            on act_hi_detail (proc_inst_id_);

        create index if not exists act_idx_hi_detail_task_id
            on act_hi_detail (task_id_);

        create index if not exists act_idx_hi_detail_time
            on act_hi_detail (time_);

        create table if not exists act_hi_identitylink
        (
            id_           varchar(64) not null
                primary key,
            group_id_     varchar(255),
            type_         varchar(255),
            user_id_      varchar(255),
            task_id_      varchar(64),
            proc_inst_id_ varchar(64)
        );

        create index if not exists act_idx_hi_ident_lnk_procinst
            on act_hi_identitylink (proc_inst_id_);

        create index if not exists act_idx_hi_ident_lnk_task
            on act_hi_identitylink (task_id_);

        create index if not exists act_idx_hi_ident_lnk_user
            on act_hi_identitylink (user_id_);

        create table if not exists act_hi_procinst
        (
            id_                        varchar(64) not null
                primary key,
            proc_inst_id_              varchar(64) not null
                unique,
            business_key_              varchar(255),
            proc_def_id_               varchar(64) not null,
            start_time_                timestamp   not null,
            end_time_                  timestamp,
            duration_                  bigint,
            start_user_id_             varchar(255),
            start_act_id_              varchar(255),
            end_act_id_                varchar(255),
            super_process_instance_id_ varchar(64),
            delete_reason_             varchar(4000),
            tenant_id_                 varchar(255) default ''::character varying,
            name_                      varchar(255)
        );

        create index if not exists act_idx_hi_pro_i_buskey
            on act_hi_procinst (business_key_);

        create index if not exists act_idx_hi_pro_inst_end
            on act_hi_procinst (end_time_);

        create table if not exists act_hi_taskinst
        (
            id_             varchar(64) not null
                primary key,
            proc_def_id_    varchar(64),
            task_def_key_   varchar(255),
            proc_inst_id_   varchar(64),
            execution_id_   varchar(64),
            name_           varchar(255),
            parent_task_id_ varchar(64),
            description_    varchar(4000),
            owner_          varchar(255),
            assignee_       varchar(255),
            start_time_     timestamp   not null,
            claim_time_     timestamp,
            end_time_       timestamp,
            duration_       bigint,
            delete_reason_  varchar(4000),
            priority_       integer,
            due_date_       timestamp,
            form_key_       varchar(255),
            category_       varchar(255),
            tenant_id_      varchar(255) default ''::character varying
        );

        create index if not exists act_idx_hi_task_inst_procinst
            on act_hi_taskinst (proc_inst_id_);

        create table if not exists act_hi_varinst
        (
            id_                varchar(64)  not null
                primary key,
            proc_inst_id_      varchar(64),
            execution_id_      varchar(64),
            task_id_           varchar(64),
            name_              varchar(255) not null,
            var_type_          varchar(100),
            rev_               integer,
            bytearray_id_      varchar(64),
            double_            double precision,
            long_              bigint,
            text_              varchar(4000),
            text2_             varchar(4000),
            create_time_       timestamp,
            last_updated_time_ timestamp
        );

        create index if not exists act_idx_hi_procvar_name_type
            on act_hi_varinst (name_, var_type_);

        create index if not exists act_idx_hi_procvar_proc_inst
            on act_hi_varinst (proc_inst_id_);

        create index if not exists act_idx_hi_procvar_task_id
            on act_hi_varinst (task_id_);

        create table if not exists act_id_group
        (
            id_   varchar(64) not null
                primary key,
            rev_  integer,
            name_ varchar(255),
            type_ varchar(255)
        );

        create table if not exists act_id_info
        (
            id_        varchar(64) not null
                primary key,
            rev_       integer,
            user_id_   varchar(64),
            type_      varchar(64),
            key_       varchar(255),
            value_     varchar(255),
            password_  bytea,
            parent_id_ varchar(255)
        );

        create table if not exists act_id_user
        (
            id_         varchar(64) not null
                primary key,
            rev_        integer,
            first_      varchar(255),
            last_       varchar(255),
            email_      varchar(255),
            pwd_        varchar(255),
            picture_id_ varchar(64)
        );

        create table if not exists act_id_membership
        (
            user_id_  varchar(64) not null
                constraint act_fk_memb_user
                    references act_id_user,
            group_id_ varchar(64) not null
                constraint act_fk_memb_group
                    references act_id_group,
            primary key (user_id_, group_id_)
        );

        create index if not exists act_idx_memb_group
            on act_id_membership (group_id_);

        create index if not exists act_idx_memb_user
            on act_id_membership (user_id_);

        create table if not exists act_re_deployment
        (
            id_          varchar(64) not null
                primary key,
            name_        varchar(255),
            category_    varchar(255),
            tenant_id_   varchar(255) default ''::character varying,
            deploy_time_ timestamp
        );

        create table if not exists act_ge_bytearray
        (
            id_            varchar(64) not null
                primary key,
            rev_           integer,
            name_          varchar(255),
            deployment_id_ varchar(64)
                constraint act_fk_bytearr_depl
                    references act_re_deployment,
            bytes_         bytea,
            generated_     boolean
        );

        create index if not exists act_idx_bytear_depl
            on act_ge_bytearray (deployment_id_);

        create table if not exists act_re_model
        (
            id_                           varchar(64) not null
                primary key,
            rev_                          integer,
            name_                         varchar(255),
            key_                          varchar(255),
            category_                     varchar(255),
            create_time_                  timestamp,
            last_update_time_             timestamp,
            version_                      integer,
            meta_info_                    varchar(4000),
            deployment_id_                varchar(64)
                constraint act_fk_model_deployment
                    references act_re_deployment,
            editor_source_value_id_       varchar(64)
                constraint act_fk_model_source
                    references act_ge_bytearray,
            editor_source_extra_value_id_ varchar(64)
                constraint act_fk_model_source_extra
                    references act_ge_bytearray,
            tenant_id_                    varchar(255) default ''::character varying
        );

        create index if not exists act_idx_model_deployment
            on act_re_model (deployment_id_);

        create index if not exists act_idx_model_source
            on act_re_model (editor_source_value_id_);

        create index if not exists act_idx_model_source_extra
            on act_re_model (editor_source_extra_value_id_);

        create table if not exists act_re_procdef
        (
            id_                     varchar(64)  not null
                primary key,
            rev_                    integer,
            category_               varchar(255),
            name_                   varchar(255),
            key_                    varchar(255) not null,
            version_                integer      not null,
            deployment_id_          varchar(64),
            resource_name_          varchar(4000),
            dgrm_resource_name_     varchar(4000),
            description_            varchar(4000),
            has_start_form_key_     boolean,
            has_graphical_notation_ boolean,
            suspension_state_       integer,
            tenant_id_              varchar(255) default ''::character varying,
            constraint act_uniq_procdef
                unique (key_, version_, tenant_id_)
        );

        create table if not exists act_procdef_info
        (
            id_           varchar(64) not null
                primary key,
            proc_def_id_  varchar(64) not null
                constraint act_uniq_info_procdef
                    unique
                constraint act_fk_info_procdef
                    references act_re_procdef,
            rev_          integer,
            info_json_id_ varchar(64)
                constraint act_fk_info_json_ba
                    references act_ge_bytearray
        );

        create index if not exists act_idx_procdef_info_json
            on act_procdef_info (info_json_id_);

        create index if not exists act_idx_procdef_info_proc
            on act_procdef_info (proc_def_id_);

        create table if not exists act_ru_execution
        (
            id_               varchar(64) not null
                primary key,
            rev_              integer,
            proc_inst_id_     varchar(64)
                constraint act_fk_exe_procinst
                    references act_ru_execution,
            business_key_     varchar(255),
            parent_id_        varchar(64)
                constraint act_fk_exe_parent
                    references act_ru_execution,
            proc_def_id_      varchar(64)
                constraint act_fk_exe_procdef
                    references act_re_procdef,
            super_exec_       varchar(64)
                constraint act_fk_exe_super
                    references act_ru_execution,
            act_id_           varchar(255),
            is_active_        boolean,
            is_concurrent_    boolean,
            is_scope_         boolean,
            is_event_scope_   boolean,
            suspension_state_ integer,
            cached_ent_state_ integer,
            tenant_id_        varchar(255) default ''::character varying,
            name_             varchar(255),
            lock_time_        timestamp
        );

        create table if not exists act_ru_event_subscr
        (
            id_            varchar(64)  not null
                primary key,
            rev_           integer,
            event_type_    varchar(255) not null,
            event_name_    varchar(255),
            execution_id_  varchar(64)
                constraint act_fk_event_exec
                    references act_ru_execution,
            proc_inst_id_  varchar(64),
            activity_id_   varchar(64),
            configuration_ varchar(255),
            created_       timestamp    not null,
            proc_def_id_   varchar(64),
            tenant_id_     varchar(255) default ''::character varying
        );

        create index if not exists act_idx_event_subscr
            on act_ru_event_subscr (execution_id_);

        create index if not exists act_idx_event_subscr_config_
            on act_ru_event_subscr (configuration_);

        create index if not exists act_idx_exe_parent
            on act_ru_execution (parent_id_);

        create index if not exists act_idx_exe_procdef
            on act_ru_execution (proc_def_id_);

        create index if not exists act_idx_exe_procinst
            on act_ru_execution (proc_inst_id_);

        create index if not exists act_idx_exe_super
            on act_ru_execution (super_exec_);

        create index if not exists act_idx_exec_buskey
            on act_ru_execution (business_key_);

        create table if not exists act_ru_job
        (
            id_                  varchar(64)  not null
                primary key,
            rev_                 integer,
            type_                varchar(255) not null,
            lock_exp_time_       timestamp,
            lock_owner_          varchar(255),
            exclusive_           boolean,
            execution_id_        varchar(64),
            process_instance_id_ varchar(64),
            proc_def_id_         varchar(64),
            retries_             integer,
            exception_stack_id_  varchar(64)
                constraint act_fk_job_exception
                    references act_ge_bytearray,
            exception_msg_       varchar(4000),
            duedate_             timestamp,
            repeat_              varchar(255),
            handler_type_        varchar(255),
            handler_cfg_         varchar(4000),
            tenant_id_           varchar(255) default ''::character varying
        );

        create index if not exists act_idx_job_exception
            on act_ru_job (exception_stack_id_);

        create table if not exists act_ru_task
        (
            id_               varchar(64) not null
                primary key,
            rev_              integer,
            execution_id_     varchar(64)
                constraint act_fk_task_exe
                    references act_ru_execution,
            proc_inst_id_     varchar(64)
                constraint act_fk_task_procinst
                    references act_ru_execution,
            proc_def_id_      varchar(64)
                constraint act_fk_task_procdef
                    references act_re_procdef,
            name_             varchar(255),
            parent_task_id_   varchar(64),
            description_      varchar(4000),
            task_def_key_     varchar(255),
            owner_            varchar(255),
            assignee_         varchar(255),
            delegation_       varchar(64),
            priority_         integer,
            create_time_      timestamp,
            due_date_         timestamp,
            category_         varchar(255),
            suspension_state_ integer,
            tenant_id_        varchar(255) default ''::character varying,
            form_key_         varchar(255)
        );

        create table if not exists act_ru_identitylink
        (
            id_           varchar(64) not null
                primary key,
            rev_          integer,
            group_id_     varchar(255),
            type_         varchar(255),
            user_id_      varchar(255),
            task_id_      varchar(64)
                constraint act_fk_tskass_task
                    references act_ru_task,
            proc_inst_id_ varchar(64)
                constraint act_fk_idl_procinst
                    references act_ru_execution,
            proc_def_id_  varchar(64)
                constraint act_fk_athrz_procedef
                    references act_re_procdef
        );

        create index if not exists act_idx_athrz_procedef
            on act_ru_identitylink (proc_def_id_);

        create index if not exists act_idx_ident_lnk_group
            on act_ru_identitylink (group_id_);

        create index if not exists act_idx_ident_lnk_user
            on act_ru_identitylink (user_id_);

        create index if not exists act_idx_idl_procinst
            on act_ru_identitylink (proc_inst_id_);

        create index if not exists act_idx_tskass_task
            on act_ru_identitylink (task_id_);

        create index if not exists act_idx_task_create
            on act_ru_task (create_time_);

        create index if not exists act_idx_task_exec
            on act_ru_task (execution_id_);

        create index if not exists act_idx_task_procdef
            on act_ru_task (proc_def_id_);

        create index if not exists act_idx_task_procinst
            on act_ru_task (proc_inst_id_);

        create table if not exists act_ru_variable
        (
            id_           varchar(64)  not null
                primary key,
            rev_          integer,
            type_         varchar(255) not null,
            name_         varchar(255) not null,
            execution_id_ varchar(64)
                constraint act_fk_var_exe
                    references act_ru_execution,
            proc_inst_id_ varchar(64)
                constraint act_fk_var_procinst
                    references act_ru_execution,
            task_id_      varchar(64),
            bytearray_id_ varchar(64)
                constraint act_fk_var_bytearray
                    references act_ge_bytearray,
            double_       double precision,
            long_         bigint,
            text_         varchar(4000),
            text2_        varchar(4000)
        );

        create index if not exists act_idx_var_bytearray
            on act_ru_variable (bytearray_id_);

        create index if not exists act_idx_var_exe
            on act_ru_variable (execution_id_);

        create index if not exists act_idx_var_procinst
            on act_ru_variable (proc_inst_id_);

        create index if not exists act_idx_variable_task_id
            on act_ru_variable (task_id_);

        create table if not exists ai_control_single
        (
            id           varchar(36) not null
                primary key,
            create_by    varchar(50),
            create_time  timestamp,
            update_by    varchar(50),
            update_time  timestamp,
            sys_org_code varchar(50),
            price        numeric(10, 2),
            name         varchar(50),
            mi_ma        varchar(32),
            xiala        varchar(50),
            danxuan      varchar(50),
            duoxuan      varchar(50),
            kaiguan      varchar(50),
            riqi         date,
            nyrsfm       timestamp,
            shijian      varchar(50),
            wenjian      varchar(250),
            tupian       varchar(250),
            dhwb         varchar(250),
            xldx         varchar(250),
            xlss         varchar(50),
            popup        varchar(100),
            popback      varchar(100),
            flzds        varchar(100),
            bmxz         varchar(100),
            yhxz         varchar(100),
            fwb          text,
            markdown     bytea,
            shq          varchar(100),
            ldzuy        varchar(255),
            ldzje        varchar(255),
            ldzjs        varchar(255),
            zdys         varchar(255),
            yuanjia      double precision,
            geshu        integer,
            jycs         varchar(50),
            province     varchar(50),
            zdmrz        varchar(50),
            zdbxl        varchar(255),
            zdbdx        varchar(255),
            zdbduoxuan   varchar(255),
            zdbxldx      varchar(255),
            zddtjxl      varchar(255),
            zddtjdx      varchar(255),
            zddtjduox    varchar(255),
            zddtjxldx    varchar(255),
            zddtjxlss    varchar(255)
        );

        comment on column ai_control_single.create_by is '创建人';

        comment on column ai_control_single.create_time is '创建时间';

        comment on column ai_control_single.update_by is '更新人';

        comment on column ai_control_single.update_time is '更新时间';

        comment on column ai_control_single.sys_org_code is '所属部门';

        comment on column ai_control_single.price is '单价';

        comment on column ai_control_single.name is '用户名';

        comment on column ai_control_single.mi_ma is '密码';

        comment on column ai_control_single.xiala is '字典下拉';

        comment on column ai_control_single.danxuan is '字典单选';

        comment on column ai_control_single.duoxuan is '字典多选';

        comment on column ai_control_single.kaiguan is '开关';

        comment on column ai_control_single.riqi is '日期';

        comment on column ai_control_single.nyrsfm is '年月日时分秒';

        comment on column ai_control_single.shijian is '时间';

        comment on column ai_control_single.wenjian is '文件';

        comment on column ai_control_single.tupian is '图片';

        comment on column ai_control_single.dhwb is '多行文本框';

        comment on column ai_control_single.xldx is '字典下拉多选框';

        comment on column ai_control_single.xlss is '字典表下拉搜索框';

        comment on column ai_control_single.popup is 'popup弹窗';

        comment on column ai_control_single.popback is 'popback';

        comment on column ai_control_single.flzds is '分类字典树';

        comment on column ai_control_single.bmxz is '部门选择';

        comment on column ai_control_single.yhxz is '用户选择';

        comment on column ai_control_single.fwb is '富文本';

        comment on column ai_control_single.markdown is 'markdown';

        comment on column ai_control_single.shq is '省市区';

        comment on column ai_control_single.ldzuy is '联动组件一';

        comment on column ai_control_single.ldzje is '联动组件二';

        comment on column ai_control_single.ldzjs is '联动组件三';

        comment on column ai_control_single.zdys is '自定义树';

        comment on column ai_control_single.yuanjia is 'double类型';

        comment on column ai_control_single.geshu is 'integer类型';

        comment on column ai_control_single.jycs is '唯一检验';

        comment on column ai_control_single.province is '输入2到10位的字母';

        comment on column ai_control_single.zdmrz is '自定义查询';

        comment on column ai_control_single.zdbxl is '字典表下拉';

        comment on column ai_control_single.zdbdx is '字典表单选';

        comment on column ai_control_single.zdbduoxuan is '字典表多选';

        comment on column ai_control_single.zdbxldx is '字典表下拉多选';

        comment on column ai_control_single.zddtjxl is '字典表带条件下拉';

        comment on column ai_control_single.zddtjdx is '字典表带条件单选';

        comment on column ai_control_single.zddtjduox is '字典表带条件多选';

        comment on column ai_control_single.zddtjxldx is '字典表带条件下拉多选';

        comment on column ai_control_single.zddtjxlss is '字典表带条件下拉搜索';

        create index if not exists index_jycs
            on ai_control_single (jycs);

        create table if not exists ces_order_customer
        (
            id            varchar(36) not null
                primary key,
            create_by     varchar(50),
            create_time   timestamp,
            update_by     varchar(50),
            update_time   timestamp,
            sys_org_code  varchar(64),
            name          varchar(32),
            sex           varchar(1),
            birthday      timestamp,
            age           integer,
            address       varchar(300),
            order_main_id varchar(32)
        );

        comment on column ces_order_customer.create_by is '创建人';

        comment on column ces_order_customer.create_time is '创建日期';

        comment on column ces_order_customer.update_by is '更新人';

        comment on column ces_order_customer.update_time is '更新日期';

        comment on column ces_order_customer.sys_org_code is '所属部门';

        comment on column ces_order_customer.name is '客户名字';

        comment on column ces_order_customer.sex is '客户性别';

        comment on column ces_order_customer.birthday is '客户生日';

        comment on column ces_order_customer.age is '年龄';

        comment on column ces_order_customer.address is '常用地址';

        comment on column ces_order_customer.order_main_id is '订单ID';

        create table if not exists ces_order_goods
        (
            id            varchar(36) not null
                primary key,
            create_by     varchar(50),
            create_time   timestamp,
            update_by     varchar(50),
            update_time   timestamp,
            sys_org_code  varchar(64),
            good_name     varchar(32),
            price         double precision,
            num           integer,
            zong_price    double precision,
            order_main_id varchar(32)
        );

        comment on column ces_order_goods.create_by is '创建人';

        comment on column ces_order_goods.create_time is '创建日期';

        comment on column ces_order_goods.update_by is '更新人';

        comment on column ces_order_goods.update_time is '更新日期';

        comment on column ces_order_goods.sys_org_code is '所属部门';

        comment on column ces_order_goods.good_name is '商品名字';

        comment on column ces_order_goods.price is '价格';

        comment on column ces_order_goods.num is '数量';

        comment on column ces_order_goods.zong_price is '单品总价';

        comment on column ces_order_goods.order_main_id is '订单ID';

        create table if not exists ces_order_main
        (
            id           varchar(36) not null
                primary key,
            create_by    varchar(50),
            create_time  timestamp,
            update_by    varchar(50),
            update_time  timestamp,
            sys_org_code varchar(64),
            order_code   varchar(32),
            xd_date      timestamp,
            money        double precision,
            remark       varchar(500)
        );

        comment on column ces_order_main.create_by is '创建人';

        comment on column ces_order_main.create_time is '创建日期';

        comment on column ces_order_main.update_by is '更新人';

        comment on column ces_order_main.update_time is '更新日期';

        comment on column ces_order_main.sys_org_code is '所属部门';

        comment on column ces_order_main.order_code is '订单编码';

        comment on column ces_order_main.xd_date is '下单时间';

        comment on column ces_order_main.money is '订单总额';

        comment on column ces_order_main.remark is '备注';

        create table if not exists ces_shop_goods
        (
            id           varchar(36) not null
                primary key,
            create_by    varchar(50),
            create_time  timestamp,
            update_by    varchar(50),
            update_time  timestamp,
            sys_org_code varchar(64),
            name         varchar(32),
            price        numeric(10, 5),
            chuc_date    timestamp,
            contents     text,
            good_type_id varchar(32)
        );

        comment on column ces_shop_goods.id is '主键';

        comment on column ces_shop_goods.create_by is '创建人';

        comment on column ces_shop_goods.create_time is '创建日期';

        comment on column ces_shop_goods.update_by is '更新人';

        comment on column ces_shop_goods.update_time is '更新日期';

        comment on column ces_shop_goods.sys_org_code is '所属部门';

        comment on column ces_shop_goods.name is '商品名字';

        comment on column ces_shop_goods.price is '价格';

        comment on column ces_shop_goods.chuc_date is '出厂时间';

        comment on column ces_shop_goods.contents is '商品简介';

        comment on column ces_shop_goods.good_type_id is '商品分类';

        create table if not exists ces_shop_type
        (
            id           varchar(36) not null
                primary key,
            create_by    varchar(50),
            create_time  timestamp,
            update_by    varchar(50),
            update_time  timestamp,
            sys_org_code varchar(64),
            name         varchar(32),
            content      varchar(200),
            pics         varchar(500),
            pid          varchar(32),
            has_child    varchar(3)
        );

        comment on column ces_shop_type.create_by is '创建人';

        comment on column ces_shop_type.create_time is '创建日期';

        comment on column ces_shop_type.update_by is '更新人';

        comment on column ces_shop_type.update_time is '更新日期';

        comment on column ces_shop_type.sys_org_code is '所属部门';

        comment on column ces_shop_type.name is '分类名字';

        comment on column ces_shop_type.content is '描述';

        comment on column ces_shop_type.pics is '图片';

        comment on column ces_shop_type.pid is '父级节点';

        comment on column ces_shop_type.has_child is '是否有子节点';

        create table if not exists demo
        (
            id           varchar(50) not null
                primary key,
            name         varchar(30),
            key_word     varchar(255),
            punch_time   timestamp,
            salary_money numeric(10, 3),
            bonus_money  double precision,
            sex          varchar(2),
            age          integer,
            birthday     date,
            email        varchar(50),
            content      varchar(1000),
            create_by    varchar(32),
            create_time  timestamp,
            update_by    varchar(32),
            update_time  timestamp,
            sys_org_code varchar(64),
            tenant_id    integer
        );

        comment on column demo.id is '主键ID';

        comment on column demo.name is '姓名';

        comment on column demo.key_word is '关键词';

        comment on column demo.punch_time is '打卡时间';

        comment on column demo.salary_money is '工资';

        comment on column demo.bonus_money is '奖金';

        comment on column demo.sex is '性别 {男:1,女:2}';

        comment on column demo.age is '年龄';

        comment on column demo.birthday is '生日';

        comment on column demo.email is '邮箱';

        comment on column demo.content is '个人简介';

        comment on column demo.create_by is '创建人';

        comment on column demo.create_time is '创建时间';

        comment on column demo.update_by is '修改人';

        comment on column demo.update_time is '修改时间';

        comment on column demo.sys_org_code is '所属部门编码';

        create table if not exists demo_field_def_val_main
        (
            id            varchar(36) not null
                primary key,
            code          varchar(200),
            name          varchar(200),
            sex           varchar(200),
            address       varchar(200),
            address_param varchar(32),
            create_by     varchar(50),
            create_time   timestamp,
            update_by     varchar(50),
            update_time   timestamp,
            sys_org_code  varchar(64)
        );

        comment on column demo_field_def_val_main.code is '编码';

        comment on column demo_field_def_val_main.name is '姓名';

        comment on column demo_field_def_val_main.sex is '性别';

        comment on column demo_field_def_val_main.address is '地址';

        comment on column demo_field_def_val_main.address_param is '地址（传参）';

        comment on column demo_field_def_val_main.create_by is '创建人';

        comment on column demo_field_def_val_main.create_time is '创建日期';

        comment on column demo_field_def_val_main.update_by is '更新人';

        comment on column demo_field_def_val_main.update_time is '更新日期';

        comment on column demo_field_def_val_main.sys_org_code is '所属部门';

        create table if not exists demo_field_def_val_sub
        (
            id           varchar(36) not null
                primary key,
            code         varchar(200),
            name         varchar(200),
            date         varchar(200),
            main_id      varchar(200),
            create_by    varchar(50),
            create_time  timestamp,
            update_by    varchar(50),
            update_time  timestamp,
            sys_org_code varchar(64)
        );

        comment on column demo_field_def_val_sub.code is '编码';

        comment on column demo_field_def_val_sub.name is '名称';

        comment on column demo_field_def_val_sub.date is '日期';

        comment on column demo_field_def_val_sub.main_id is '主表ID';

        comment on column demo_field_def_val_sub.create_by is '创建人';

        comment on column demo_field_def_val_sub.create_time is '创建日期';

        comment on column demo_field_def_val_sub.update_by is '更新人';

        comment on column demo_field_def_val_sub.update_time is '更新日期';

        comment on column demo_field_def_val_sub.sys_org_code is '所属部门';

        create table if not exists design_form
        (
            id                  varchar(32)  not null
                primary key,
            desform_code        varchar(200) not null,
            desform_name        varchar(200),
            desform_icon        varchar(50),
            desform_design_json text,
            cgform_code         varchar(32),
            parent_id           varchar(32),
            parent_code         varchar(200),
            desform_type        smallint,
            iz_oa_show          smallint,
            iz_mobile_view      smallint,
            create_by           varchar(32),
            create_time         timestamp(6),
            update_by           varchar(32),
            update_time         timestamp(6)
        );

        comment on column design_form.id is 'ID';

        comment on column design_form.desform_code is '表单编码';

        comment on column design_form.desform_name is '表单名称';

        comment on column design_form.desform_icon is '表单图标';

        comment on column design_form.desform_design_json is '表单设计JSON';

        comment on column design_form.cgform_code is '绑定的CgformCode';

        comment on column design_form.parent_id is '父表单id';

        comment on column design_form.parent_code is '父表单code';

        comment on column design_form.desform_type is '表单类型，1=主表，2=子表';

        comment on column design_form.iz_mobile_view is '是否是移动视图（1=是，0或null=否），一个 desform 只能有一个移动视图';

        comment on column design_form.create_by is '创建人';

        comment on column design_form.create_time is '创建时间';

        comment on column design_form.update_by is '修改人';

        comment on column design_form.update_time is '修改时间';

        create table if not exists design_form_auth
        (
            id                     varchar(32)  not null
                primary key,
            desform_id             varchar(32)  not null,
            desform_code           varchar(200) not null,
            permission_type        varchar(100) not null,
            auth_com_key           varchar(100),
            auth_title             varchar(100),
            auth_field             varchar(100),
            auth_type              varchar(50),
            auth_value             varchar(200),
            auth_scope_is_all      varchar(10),
            auth_scope_users_val   varchar(200),
            auth_scope_roles_val   varchar(200),
            auth_scope_departs_val varchar(200),
            sub_table              smallint,
            sub_title              varchar(100),
            sub_key                varchar(100),
            status                 smallint,
            create_by              varchar(32),
            create_time            timestamp(6),
            update_by              varchar(32),
            update_time            timestamp(6)
        );

        comment on table design_form_auth is '表单设计器权限表，字段级权限';

        comment on column design_form_auth.id is '主键ID';

        comment on column design_form_auth.desform_id is '表单设计ID';

        comment on column design_form_auth.desform_code is '表单设计编码';

        comment on column design_form_auth.permission_type is '权限类型（data、button、field）';

        comment on column design_form_auth.auth_com_key is '组件id json中的key';

        comment on column design_form_auth.auth_title is '权限名称';

        comment on column design_form_auth.auth_field is '权限字段';

        comment on column design_form_auth.auth_type is '授权类型（字段权限时存储可见1或可编辑2；数据权限存储查询类型（大于、小于、等于等规则）；按钮权限此项为空）';

        comment on column design_form_auth.auth_value is '授权规则值';

        comment on column design_form_auth.auth_scope_is_all is '授权范围【Y 所有人 ,N 不是所有人】默认所有人';

        comment on column design_form_auth.auth_scope_users_val is '授权范围值，保存user登录账户';

        comment on column design_form_auth.auth_scope_roles_val is '授权范围值，保存授权角色编码';

        comment on column design_form_auth.auth_scope_departs_val is '授权范围值，保存部门编码';

        comment on column design_form_auth.sub_table is '是否是子表内权限，1=true，0=false';

        comment on column design_form_auth.sub_title is '子表的标题';

        comment on column design_form_auth.sub_key is '子表的Key';

        comment on column design_form_auth.status is '状态（0=无效，1=有效）1';

        comment on column design_form_auth.create_by is '创建人';

        comment on column design_form_auth.create_time is '创建时间';

        comment on column design_form_auth.update_by is '修改人';

        comment on column design_form_auth.update_time is '修改时间';

        create table if not exists design_form_commuse
        (
            id          varchar(32) not null
                primary key,
            user_id     varchar(32),
            desform_id  varchar(32),
            create_by   varchar(32),
            create_time timestamp(6),
            update_by   varchar(32),
            update_time timestamp(6)
        );

        comment on table design_form_commuse is '常用流程表';

        comment on column design_form_commuse.user_id is '用户id';

        comment on column design_form_commuse.create_by is '创建人';

        comment on column design_form_commuse.create_time is '创建时间';

        comment on column design_form_commuse.update_by is '修改人';

        comment on column design_form_commuse.update_time is '修改时间';

        create table if not exists design_form_data
        (
            id                  varchar(32)  not null
                primary key,
            desform_id          varchar(32)  not null,
            desform_code        varchar(200) not null,
            desform_name        varchar(200),
            desform_data_json   text,
            online_form_code    varchar(200),
            online_form_data_id varchar(32),
            create_by           varchar(32),
            create_time         timestamp(6),
            update_by           varchar(32),
            update_time         timestamp(6)
        );

        comment on column design_form_data.id is 'ID';

        comment on column design_form_data.desform_id is '表单设计ID';

        comment on column design_form_data.desform_code is '表单设计编码';

        comment on column design_form_data.desform_name is '表单设计名称';

        comment on column design_form_data.desform_data_json is '表单数据JSON';

        comment on column design_form_data.online_form_code is 'Online表单的Code';

        comment on column design_form_data.online_form_data_id is 'Online数据表中的id，用于同步修改';

        comment on column design_form_data.create_by is '创建人';

        comment on column design_form_data.create_time is '创建时间';

        comment on column design_form_data.update_by is '修改人';

        comment on column design_form_data.update_time is '修改时间';

        create table if not exists design_form_route
        (
            id           varchar(32)  not null
                primary key,
            desform_id   varchar(32)  not null,
            desform_code varchar(200) not null,
            route_name   varchar(200),
            route_type   varchar(2),
            route_path   varchar(200),
            status       smallint,
            create_by    varchar(32),
            create_time  timestamp(6),
            update_by    varchar(32),
            update_time  timestamp(6)
        );

        comment on column design_form_route.id is 'ID';

        comment on column design_form_route.desform_id is '表单设计ID';

        comment on column design_form_route.desform_code is '表单设计编码';

        comment on column design_form_route.route_name is '路由名称';

        comment on column design_form_route.route_type is '路由跳转类型（1=表单跳转；2=菜单跳转；3=外部跳转）';

        comment on column design_form_route.route_path is '下一步路由地址（表单跳转时填表单的code；菜单跳转填菜单路径；外部跳转填全链接）';

        comment on column design_form_route.status is '状态（0=无效，1=有效）';

        comment on column design_form_route.create_by is '创建人';

        comment on column design_form_route.create_time is '创建时间';

        comment on column design_form_route.update_by is '修改人';

        comment on column design_form_route.update_time is '修改时间';

        create table if not exists design_form_templet
        (
            id           varchar(32)  not null
                primary key,
            templet_code varchar(200) not null,
            templet_name varchar(200),
            templet_json text,
            create_by    varchar(32),
            create_time  timestamp(6),
            update_by    varchar(32),
            update_time  timestamp(6)
        );

        comment on table design_form_templet is '表单设计器模板表';

        comment on column design_form_templet.id is 'ID';

        comment on column design_form_templet.templet_code is '模板编码';

        comment on column design_form_templet.templet_name is '模板名称';

        comment on column design_form_templet.templet_json is '模板JSON';

        comment on column design_form_templet.create_by is '创建人';

        comment on column design_form_templet.create_time is '创建时间';

        comment on column design_form_templet.update_by is '修改人';

        comment on column design_form_templet.update_time is '修改时间';

        create table if not exists design_form_url_auth
        (
            id           varchar(32)  not null
                primary key,
            desform_id   varchar(32)  not null,
            desform_code varchar(100) not null,
            url_type     varchar(50)  not null,
            url_status   smallint,
            create_by    varchar(32),
            create_time  timestamp(6),
            update_by    varchar(32),
            update_time  timestamp(6)
        );

        comment on column design_form_url_auth.desform_id is '表单ID';

        comment on column design_form_url_auth.desform_code is '表单CODE';

        comment on column design_form_url_auth.url_type is '链接类型';

        comment on column design_form_url_auth.url_status is '链接状态（1=有效，2=无效）';

        comment on column design_form_url_auth.create_by is '创建人';

        comment on column design_form_url_auth.create_time is '创建时间';

        comment on column design_form_url_auth.update_by is '修改人';

        comment on column design_form_url_auth.update_time is '修改时间';

        create table if not exists ext_act_bpm_file
        (
            id          varchar(32) not null
                primary key,
            bpm_log_id  varchar(32) not null,
            base_path   varchar(255),
            file_path   varchar(1000),
            file_name   varchar(1000),
            file_size   varchar(20),
            create_by   varchar(255),
            create_time timestamp(6),
            update_by   varchar(255),
            update_time timestamp(6)
        );

        comment on table ext_act_bpm_file is '审批日志文件';

        comment on column ext_act_bpm_file.bpm_log_id is '审批日志id';

        comment on column ext_act_bpm_file.base_path is '根目录';

        comment on column ext_act_bpm_file.file_path is '文件路径';

        comment on column ext_act_bpm_file.file_name is '文件名称';

        comment on column ext_act_bpm_file.file_size is '文件大小';

        comment on column ext_act_bpm_file.create_by is '创建人';

        comment on column ext_act_bpm_file.create_time is '创建时间';

        comment on column ext_act_bpm_file.update_by is '更新人';

        comment on column ext_act_bpm_file.update_time is '更新时间';

        create table if not exists ext_act_bpm_log
        (
            id           varchar(64) not null
                primary key,
            business_key varchar(64),
            proc_inst_id varchar(64) not null,
            proc_name    varchar(255),
            task_id      varchar(64),
            task_def_key varchar(100),
            task_name    varchar(255),
            op_user_id   varchar(100),
            op_user_name varchar(100),
            op_time      timestamp(6),
            op_type      varchar(100),
            op_status    varchar(64),
            remarks      varchar(4000)
        );

        comment on table ext_act_bpm_log is '流程审批日志';

        comment on column ext_act_bpm_log.business_key is '业务流程key';

        comment on column ext_act_bpm_log.proc_inst_id is '流程实例id';

        comment on column ext_act_bpm_log.proc_name is '流程名称';

        comment on column ext_act_bpm_log.task_id is '任务id';

        comment on column ext_act_bpm_log.task_def_key is '任务key';

        comment on column ext_act_bpm_log.task_name is '任务名称';

        comment on column ext_act_bpm_log.op_user_id is '操作人ID';

        comment on column ext_act_bpm_log.op_user_name is '操作人名称';

        comment on column ext_act_bpm_log.op_time is '操作时间';

        comment on column ext_act_bpm_log.op_type is '操作类型:1普通任务;2会签任务;3驳回;4作废;5取回';

        comment on column ext_act_bpm_log.op_status is '操作状态（自行结合业务定义）';

        comment on column ext_act_bpm_log.remarks is '意见';

        create table if not exists ext_act_design_flow_data
        (
            id              varchar(32) not null
                primary key,
            desform_id      varchar(32) not null,
            desform_code    varchar(200),
            desform_name    varchar(255),
            desform_data_id varchar(32),
            bpm_title       varchar(200),
            title_exp       varchar(255),
            process_name    varchar(200),
            flow_code       varchar(200),
            table_name      varchar(200),
            bpm_status      varchar(50),
            create_by       varchar(32),
            create_time     timestamp(6),
            update_by       varchar(32),
            update_time     timestamp(6)
        );

        comment on column ext_act_design_flow_data.desform_id is '表单ID';

        comment on column ext_act_design_flow_data.desform_code is '表单Code';

        comment on column ext_act_design_flow_data.desform_name is '表单名称';

        comment on column ext_act_design_flow_data.desform_data_id is '表单数据ID';

        comment on column ext_act_design_flow_data.bpm_title is '流程标题';

        comment on column ext_act_design_flow_data.title_exp is '业务标题表达式';

        comment on column ext_act_design_flow_data.process_name is '流程名称';

        comment on column ext_act_design_flow_data.flow_code is '流程编码';

        comment on column ext_act_design_flow_data.table_name is '存储主表名，一对多只存主表';

        comment on column ext_act_design_flow_data.bpm_status is '流程状态';

        comment on column ext_act_design_flow_data.create_by is '创建人';

        comment on column ext_act_design_flow_data.create_time is '创建时间';

        comment on column ext_act_design_flow_data.update_by is '修改人';

        comment on column ext_act_design_flow_data.update_time is '修改时间';

        create table if not exists ext_act_expression
        (
            id          varchar(32) not null
                primary key,
            name        varchar(255),
            expression  varchar(255),
            create_by   varchar(50),
            create_time timestamp(6),
            update_by   varchar(50),
            update_time timestamp(6)
        );

        comment on table ext_act_expression is '流程表达式';

        comment on column ext_act_expression.id is 'id';

        comment on column ext_act_expression.name is '表达式名称';

        comment on column ext_act_expression.expression is '表达式';

        comment on column ext_act_expression.create_by is '创建人登录名称';

        comment on column ext_act_expression.create_time is '创建日期';

        comment on column ext_act_expression.update_by is '更新人登录名称';

        comment on column ext_act_expression.update_time is '更新日期';

        create table if not exists ext_act_flow_data
        (
            id                varchar(32) not null
                primary key,
            form_data_id      varchar(32),
            process_key       varchar(100),
            process_name      varchar(200),
            process_exp_title varchar(255),
            process_inst_id   varchar(64),
            bpm_status_field  varchar(200),
            bpm_status        varchar(50),
            form_table_name   varchar(50),
            form_type         varchar(10),
            relation_code     varchar(32),
            user_id           varchar(200),
            process_form_id   varchar(36),
            create_by         varchar(50),
            create_time       timestamp(6),
            update_by         varchar(50),
            update_time       timestamp(6),
            oa_todo_id        varchar(2000)
        );

        comment on table ext_act_flow_data is '流程表单业务表';

        comment on column ext_act_flow_data.id is '序号';

        comment on column ext_act_flow_data.form_data_id is '多流程表单id';

        comment on column ext_act_flow_data.process_key is '流程key';

        comment on column ext_act_flow_data.process_name is '流程名称';

        comment on column ext_act_flow_data.process_exp_title is '流程业务标题(表达式值)';

        comment on column ext_act_flow_data.process_inst_id is '流程实例ID';

        comment on column ext_act_flow_data.bpm_status_field is '多流程状态列名';

        comment on column ext_act_flow_data.bpm_status is '流程状态1:待提交;2:处理中;3处理完毕;4流程作废;5流程挂起';

        comment on column ext_act_flow_data.form_table_name is '多流程表单表名';

        comment on column ext_act_flow_data.form_type is '表单类型：1Online表单,2自定义表单,3自定义开发';

        comment on column ext_act_flow_data.relation_code is '多流程表单编码';

        comment on column ext_act_flow_data.user_id is '流程发起人';

        comment on column ext_act_flow_data.process_form_id is '业务关联配置id';

        comment on column ext_act_flow_data.create_by is '创建人登录名称';

        comment on column ext_act_flow_data.create_time is '创建日期';

        comment on column ext_act_flow_data.update_by is '更新人登录名称';

        comment on column ext_act_flow_data.update_time is '更新日期';

        comment on column ext_act_flow_data.oa_todo_id is '城发oa消息外键';

        create table if not exists ext_act_listener
        (
            id                  varchar(32) not null
                primary key,
            listener_name       varchar(50),
            listener_type       smallint,
            listener_event      varchar(20),
            listener_value_type varchar(20),
            listener_value      varchar(100),
            listener_status     smallint,
            del_flag            varchar(10) not null,
            create_by           varchar(32),
            create_time         timestamp(6),
            update_by           varchar(32),
            update_time         timestamp(6)
        );

        comment on column ext_act_listener.listener_name is '监听器名称';

        comment on column ext_act_listener.listener_type is '监听类型 1执行监听 2任务监听';

        comment on column ext_act_listener.listener_event is 'Event属性 触发监听器的事件类型
create：在任务被创建，且所有的任务属性被设置后发生。
assignment：在任务分配给某人后发生。注意：当流程执行到 userTask，在触发 create 事件之前，首先触
发 assignment 事件。这像是一个反常的顺序，但原因很实际：接收到 create 事件，我们往往会要查看包括
代理人在内的所有任务的属性。
complete：在任务完成后，任务从运行时的数据中被删除之前发生。';

        comment on column ext_act_listener.listener_value_type is '值类型  javaClass JAVA类，expression 表达式';

        comment on column ext_act_listener.listener_value is '监听器表达式或类名';

        comment on column ext_act_listener.listener_status is '监听器状态
1：启用
2：禁用';

        comment on column ext_act_listener.del_flag is '删除状态（0，正常，1已删除）';

        comment on column ext_act_listener.create_by is '创建人';

        comment on column ext_act_listener.create_time is '创建时间';

        comment on column ext_act_listener.update_by is '修改人';

        comment on column ext_act_listener.update_time is '修改时间';

        create table if not exists ext_act_mutil_flow_data
        (
            id               varchar(32) not null
                primary key,
            form_data_id     varchar(32),
            process_key      varchar(300),
            process_inst_id  varchar(64),
            bpm_status_field varchar(200),
            bpm_status       varchar(50),
            form_table_name  varchar(50),
            relation_code    varchar(32),
            create_by        varchar(50),
            create_time      timestamp(6),
            update_by        varchar(50),
            update_time      timestamp(6)
        );

        comment on table ext_act_mutil_flow_data is '多流程表单业务表';

        comment on column ext_act_mutil_flow_data.id is '序号';

        comment on column ext_act_mutil_flow_data.form_data_id is '多流程表单id';

        comment on column ext_act_mutil_flow_data.process_key is '流程key';

        comment on column ext_act_mutil_flow_data.process_inst_id is '流程实例ID';

        comment on column ext_act_mutil_flow_data.bpm_status is '多流程状态列名';

        comment on column ext_act_mutil_flow_data.form_table_name is '多流程表单表名';

        comment on column ext_act_mutil_flow_data.relation_code is '多流程表单编码';

        comment on column ext_act_mutil_flow_data.create_by is '创建人登录名称';

        comment on column ext_act_mutil_flow_data.create_time is '创建日期';

        comment on column ext_act_mutil_flow_data.update_by is '更新人登录名称';

        comment on column ext_act_mutil_flow_data.update_time is '更新日期';

        create table if not exists ext_act_process
        (
            id               varchar(32) not null
                primary key,
            process_type     varchar(32),
            process_key      varchar(100),
            process_name     varchar(50),
            process_status   smallint,
            process_xml_path varchar(100),
            process_xml      bytea,
            note             varchar(200),
            pc_icon          varchar(50),
            app_icon         varchar(50),
            create_by        varchar(32),
            create_time      timestamp(6),
            update_time      timestamp(6),
            update_by        varchar(32)
        );

        comment on table ext_act_process is '流程设计表';

        comment on column ext_act_process.process_type is '流程类型';

        comment on column ext_act_process.process_key is '流程ID';

        comment on column ext_act_process.process_name is '流程名称';

        comment on column ext_act_process.process_status is '流程状态0未发布;1已发布';

        comment on column ext_act_process.process_xml_path is '流程XML路径';

        comment on column ext_act_process.process_xml is '流程内容';

        comment on column ext_act_process.note is '备注';

        comment on column ext_act_process.pc_icon is '表单图标(PC)';

        comment on column ext_act_process.app_icon is '表单图标(APP)';

        comment on column ext_act_process.create_by is '创建人id';

        comment on column ext_act_process.create_time is '创建时间';

        comment on column ext_act_process.update_time is '修改时间';

        comment on column ext_act_process.update_by is '修改人id';

        create table if not exists ext_act_process_form
        (
            id              varchar(32) not null
                primary key,
            relation_code   varchar(50) not null,
            biz_name        varchar(30),
            process_id      varchar(32) not null,
            form_table_name varchar(50),
            form_type       varchar(50),
            title_exp       varchar(150),
            form_deal_style varchar(100),
            flow_status_col varchar(50),
            circulate       boolean
        );

        comment on table ext_act_process_form is '业务基础表';

        comment on column ext_act_process_form.relation_code is '唯一编码';

        comment on column ext_act_process_form.biz_name is '业务名称描述';

        comment on column ext_act_process_form.process_id is '流程ID';

        comment on column ext_act_process_form.form_table_name is 'Online表名、自定义表单CODE、表名';

        comment on column ext_act_process_form.form_type is '表单类型：1Online表单,2自定义表单,3自定义开发';

        comment on column ext_act_process_form.title_exp is '标题表达式';

        comment on column ext_act_process_form.form_deal_style is '流程办理风格';

        comment on column ext_act_process_form.flow_status_col is '流程状态列名';

        comment on column ext_act_process_form.circulate is '流程是否可以循环发起';

        create table if not exists ext_act_process_node
        (
            id                    varchar(32) not null
                primary key,
            form_id               varchar(32),
            process_id            varchar(32),
            process_node_code     varchar(50),
            process_node_name     varchar(50),
            model_and_view        varchar(500),
            model_and_view_mobile varchar(500),
            node_timeout          integer,
            node_bpm_status       varchar(32)
        );

        comment on table ext_act_process_node is '流程节点表';

        comment on column ext_act_process_node.process_id is '流程ID';

        comment on column ext_act_process_node.process_node_code is '节点CODE';

        comment on column ext_act_process_node.process_node_name is '节点名称';

        comment on column ext_act_process_node.model_and_view_mobile is '外部表单跳转方法(移动端)';

        comment on column ext_act_process_node.node_timeout is '节点任务超时设置（单位时）';

        comment on column ext_act_process_node.node_bpm_status is '流程状态';

        create table if not exists ext_act_process_node_auth
        (
            id                varchar(32) not null
                primary key,
            process_id        varchar(32),
            process_node_code varchar(50),
            rule_code         varchar(255),
            rule_name         varchar(255),
            rule_type         varchar(255),
            status            varchar(2),
            create_by         varchar(50),
            create_time       timestamp(6),
            update_by         varchar(50),
            update_time       timestamp(6),
            form_type         varchar(50) not null,
            form_biz_code     varchar(200),
            desform_com_key   varchar(100)
        );

        comment on table ext_act_process_node_auth is '流程节点权限表';

        comment on column ext_act_process_node_auth.process_id is '流程ID';

        comment on column ext_act_process_node_auth.process_node_code is '节点编码';

        comment on column ext_act_process_node_auth.rule_code is '规则编码';

        comment on column ext_act_process_node_auth.rule_name is '规则名称';

        comment on column ext_act_process_node_auth.rule_type is '策略1显示2禁用';

        comment on column ext_act_process_node_auth.status is '状态(0无效1有效)';

        comment on column ext_act_process_node_auth.create_by is '创建人登录名称';

        comment on column ext_act_process_node_auth.create_time is '创建日期';

        comment on column ext_act_process_node_auth.update_by is '更新人登录名称';

        comment on column ext_act_process_node_auth.update_time is '更新日期';

        comment on column ext_act_process_node_auth.form_type is '表单类型：1Online表单,2自定义表单,3自定义开发';

        comment on column ext_act_process_node_auth.form_biz_code is '表单设计编码';

        comment on column ext_act_process_node_auth.desform_com_key is '组件id json中的key';

        create table if not exists ext_act_process_node_deploy
        (
            id                    varchar(32) not null
                primary key,
            form_id               varchar(32),
            process_id            varchar(32),
            process_node_code     varchar(50),
            process_node_name     varchar(50),
            model_and_view        varchar(500),
            model_and_view_mobile varchar(500),
            node_timeout          integer,
            node_bpm_status       varchar(32),
            deployment_id         varchar(64)
        );

        comment on table ext_act_process_node_deploy is '流程节点部署表';

        comment on column ext_act_process_node_deploy.process_id is '流程ID';

        comment on column ext_act_process_node_deploy.process_node_code is '节点CODE';

        comment on column ext_act_process_node_deploy.process_node_name is '节点名称';

        comment on column ext_act_process_node_deploy.model_and_view_mobile is '外部表单跳转方法(移动端)';

        comment on column ext_act_process_node_deploy.node_timeout is '节点任务超时设置（单位时）';

        comment on column ext_act_process_node_deploy.node_bpm_status is '流程状态';

        comment on column ext_act_process_node_deploy.deployment_id is '部署id';

        create table if not exists ext_act_task_cc
        (
            id             varchar(32) not null
                primary key,
            proc_def_id    varchar(64),
            task_def_key   varchar(255),
            proc_inst_id   varchar(64),
            execution_id   varchar(64),
            task_id        varchar(64),
            task_name      varchar(255),
            from_user_name varchar(100),
            cc_user_name   varchar(100),
            create_by      varchar(32),
            create_time    timestamp(6),
            update_by      varchar(32),
            update_time    timestamp(6)
        );

        comment on table ext_act_task_cc is '任务抄送表';

        comment on column ext_act_task_cc.id is '序号';

        comment on column ext_act_task_cc.proc_def_id is '流程定义ID';

        comment on column ext_act_task_cc.task_def_key is '节点定义ID';

        comment on column ext_act_task_cc.proc_inst_id is '流程实例ID';

        comment on column ext_act_task_cc.execution_id is '执行实例ID';

        comment on column ext_act_task_cc.task_id is '任务ID';

        comment on column ext_act_task_cc.task_name is '名称';

        comment on column ext_act_task_cc.from_user_name is '任务处理人';

        comment on column ext_act_task_cc.cc_user_name is '任务抄送人员';

        comment on column ext_act_task_cc.create_by is '创建人登录名称';

        comment on column ext_act_task_cc.create_time is '创建日期';

        comment on column ext_act_task_cc.update_by is '更新人登录名称';

        comment on column ext_act_task_cc.update_time is '更新日期';

        create table if not exists ext_act_task_notification
        (
            id               varchar(32) not null
                primary key,
            task_id          varchar(64),
            task_name        varchar(255),
            task_assignee    varchar(100),
            proc_inst_id     varchar(32),
            proc_name        varchar(255),
            op_time          timestamp(6),
            notify_type      varchar(10),
            remarks          varchar(255),
            create_by        varchar(50),
            create_time      timestamp(6),
            update_by        varchar(50),
            update_time      timestamp(6),
            sys_org_code     varchar(50),
            sys_company_code varchar(50)
        );

        comment on table ext_act_task_notification is '任务催办表';

        comment on column ext_act_task_notification.id is '序号';

        comment on column ext_act_task_notification.task_id is '任务ID';

        comment on column ext_act_task_notification.task_name is '任务名称';

        comment on column ext_act_task_notification.task_assignee is '任务处理人';

        comment on column ext_act_task_notification.proc_inst_id is '流程实例id';

        comment on column ext_act_task_notification.proc_name is '流程名称';

        comment on column ext_act_task_notification.op_time is '催办操作时间';

        comment on column ext_act_task_notification.notify_type is '催办类型1页面通知2邮件';

        comment on column ext_act_task_notification.remarks is '催办说明';

        comment on column ext_act_task_notification.create_by is '创建人登录名称';

        comment on column ext_act_task_notification.create_time is '创建日期';

        comment on column ext_act_task_notification.update_by is '更新人登录名称';

        comment on column ext_act_task_notification.update_time is '更新日期';

        comment on column ext_act_task_notification.sys_org_code is '所属部门';

        comment on column ext_act_task_notification.sys_company_code is '所属公司';

        create table if not exists ext_biz_leave
        (
            id          varchar(32) not null
                primary key,
            name        varchar(100),
            days        integer,
            begin_date  timestamp(6),
            end_date    timestamp(6),
            reason      varchar(500),
            bpm_status  varchar(50),
            create_by   varchar(32),
            create_time timestamp(6),
            update_time timestamp(6),
            update_by   varchar(32)
        );

        comment on table ext_biz_leave is '请假单';

        comment on column ext_biz_leave.id is 'ID';

        comment on column ext_biz_leave.name is '请假人';

        comment on column ext_biz_leave.days is '请假天数';

        comment on column ext_biz_leave.begin_date is '开始时间';

        comment on column ext_biz_leave.end_date is '请假结束时间';

        comment on column ext_biz_leave.reason is '请假原因';

        comment on column ext_biz_leave.bpm_status is '流程状态';

        comment on column ext_biz_leave.create_by is '创建人id';

        comment on column ext_biz_leave.create_time is '创建时间';

        comment on column ext_biz_leave.update_time is '修改时间';

        comment on column ext_biz_leave.update_by is '修改人id';

        create table if not exists jeecg_monthly_growth_analysis
        (
            id           integer not null
                primary key,
            year         varchar(50),
            month        varchar(50),
            main_income  numeric(18, 2),
            other_income numeric(18, 2)
        );

        comment on column jeecg_monthly_growth_analysis.month is '月份';

        comment on column jeecg_monthly_growth_analysis.main_income is '佣金/主营收入';

        comment on column jeecg_monthly_growth_analysis.other_income is '其他收入';

        create table if not exists jeecg_order_customer
        (
            id          varchar(32)  not null
                primary key,
            name        varchar(100) not null,
            sex         varchar(4),
            idcard      varchar(18),
            idcard_pic  varchar(500),
            telphone    varchar(32),
            order_id    varchar(32)  not null,
            create_by   varchar(32),
            create_time timestamp,
            update_by   varchar(32),
            update_time timestamp
        );

        comment on column jeecg_order_customer.id is '主键';

        comment on column jeecg_order_customer.name is '客户名';

        comment on column jeecg_order_customer.sex is '性别';

        comment on column jeecg_order_customer.idcard is '身份证号码';

        comment on column jeecg_order_customer.idcard_pic is '身份证扫描件';

        comment on column jeecg_order_customer.telphone is '电话1';

        comment on column jeecg_order_customer.order_id is '外键';

        comment on column jeecg_order_customer.create_by is '创建人';

        comment on column jeecg_order_customer.create_time is '创建时间';

        comment on column jeecg_order_customer.update_by is '修改人';

        comment on column jeecg_order_customer.update_time is '修改时间';

        create table if not exists jeecg_order_main
        (
            id          varchar(32) not null
                primary key,
            order_code  varchar(50),
            ctype       varchar(500),
            order_date  timestamp,
            order_money double precision,
            content     varchar(500),
            create_by   varchar(32),
            create_time timestamp,
            update_by   varchar(32),
            update_time timestamp,
            bpm_status  varchar(3)
        );

        comment on column jeecg_order_main.id is '主键';

        comment on column jeecg_order_main.order_code is '订单号';

        comment on column jeecg_order_main.ctype is '订单类型';

        comment on column jeecg_order_main.order_date is '订单日期';

        comment on column jeecg_order_main.order_money is '订单金额';

        comment on column jeecg_order_main.content is '订单备注';

        comment on column jeecg_order_main.create_by is '创建人';

        comment on column jeecg_order_main.create_time is '创建时间';

        comment on column jeecg_order_main.update_by is '修改人';

        comment on column jeecg_order_main.update_time is '修改时间';

        comment on column jeecg_order_main.bpm_status is '流程状态';

        create table if not exists jeecg_order_ticket
        (
            id           varchar(32)  not null
                primary key,
            ticket_code  varchar(100) not null,
            tickect_date timestamp,
            order_id     varchar(32)  not null,
            create_by    varchar(32),
            create_time  timestamp,
            update_by    varchar(32),
            update_time  timestamp
        );

        comment on column jeecg_order_ticket.id is '主键';

        comment on column jeecg_order_ticket.ticket_code is '航班号';

        comment on column jeecg_order_ticket.tickect_date is '航班时间';

        comment on column jeecg_order_ticket.order_id is '外键';

        comment on column jeecg_order_ticket.create_by is '创建人';

        comment on column jeecg_order_ticket.create_time is '创建时间';

        comment on column jeecg_order_ticket.update_by is '修改人';

        comment on column jeecg_order_ticket.update_time is '修改时间';

        create table if not exists jeecg_project_nature_income
        (
            id                       integer     not null
                primary key,
            nature                   varchar(50) not null,
            insurance_fee            numeric(18, 2),
            risk_consulting_fee      numeric(18, 2),
            evaluation_fee           numeric(18, 2),
            insurance_evaluation_fee numeric(18, 2),
            bidding_consulting_fee   numeric(18, 2),
            interol_consulting_fee   numeric(18, 2)
        );

        comment on column jeecg_project_nature_income.nature is '项目性质';

        comment on column jeecg_project_nature_income.insurance_fee is '保险经纪佣金费';

        comment on column jeecg_project_nature_income.risk_consulting_fee is '风险咨询费';

        comment on column jeecg_project_nature_income.evaluation_fee is '承保公估评估费';

        comment on column jeecg_project_nature_income.insurance_evaluation_fee is '保险公估费';

        comment on column jeecg_project_nature_income.bidding_consulting_fee is '投标咨询费';

        comment on column jeecg_project_nature_income.interol_consulting_fee is '内控咨询费';

        create table if not exists jimu_dict
        (
            id          varchar(32)  not null
                primary key,
            dict_name   varchar(100) not null,
            dict_code   varchar(100) not null,
            description varchar(255),
            del_flag    integer,
            create_by   varchar(32),
            create_time timestamp,
            update_by   varchar(32),
            update_time timestamp,
            type        integer,
            tenant_id   varchar(10)
        );

        comment on column jimu_dict.dict_name is '字典名称';

        comment on column jimu_dict.dict_code is '字典编码';

        comment on column jimu_dict.description is '描述';

        comment on column jimu_dict.del_flag is '删除状态';

        comment on column jimu_dict.create_by is '创建人';

        comment on column jimu_dict.create_time is '创建时间';

        comment on column jimu_dict.update_by is '更新人';

        comment on column jimu_dict.update_time is '更新时间';

        comment on column jimu_dict.type is '字典类型0为string,1为number';

        comment on column jimu_dict.tenant_id is '多租户标识';

        create index if not exists uk_sd_dict_code
            on jimu_dict (dict_code);

        create table if not exists jimu_dict_item
        (
            id          varchar(32)  not null
                primary key,
            dict_id     varchar(32),
            item_text   varchar(100) not null,
            item_value  varchar(100) not null,
            description varchar(255),
            sort_order  integer,
            status      integer,
            create_by   varchar(32),
            create_time timestamp,
            update_by   varchar(32),
            update_time timestamp
        );

        comment on column jimu_dict_item.dict_id is '字典id';

        comment on column jimu_dict_item.item_text is '字典项文本';

        comment on column jimu_dict_item.item_value is '字典项值';

        comment on column jimu_dict_item.description is '描述';

        comment on column jimu_dict_item.sort_order is '排序';

        comment on column jimu_dict_item.status is '状态（1启用 0不启用）';

        create index if not exists idx_sdi_dict_val
            on jimu_dict_item (dict_id, item_value);

        create index if not exists idx_sdi_role_dict_id
            on jimu_dict_item (dict_id);

        create index if not exists idx_sdi_role_sort_order
            on jimu_dict_item (sort_order);

        create index if not exists idx_sdi_status
            on jimu_dict_item (status);

        create table if not exists jimu_report
        (
            id          varchar(32) not null
                primary key,
            code        varchar(50),
            name        varchar(50),
            note        varchar(255),
            status      varchar(10),
            type        varchar(10),
            json_str    text,
            api_url     varchar(255),
            thumb       text,
            create_by   varchar(50),
            create_time timestamp,
            update_by   varchar(50),
            update_time timestamp,
            del_flag    smallint,
            api_method  varchar(255),
            api_code    varchar(255),
            template    smallint,
            view_count  bigint,
            css_str     text,
            js_str      text,
            tenant_id   varchar(10),
            py_str      text
        );

        comment on table jimu_report is '在线excel设计器';

        comment on column jimu_report.id is '主键';

        comment on column jimu_report.code is '编码';

        comment on column jimu_report.name is '名称';

        comment on column jimu_report.note is '说明';

        comment on column jimu_report.status is '状态';

        comment on column jimu_report.type is '类型';

        comment on column jimu_report.json_str is 'json字符串';

        comment on column jimu_report.api_url is '请求地址';

        comment on column jimu_report.thumb is '缩略图';

        comment on column jimu_report.create_by is '创建人';

        comment on column jimu_report.create_time is '创建时间';

        comment on column jimu_report.update_by is '修改人';

        comment on column jimu_report.update_time is '修改时间';

        comment on column jimu_report.del_flag is '删除标识0-正常,1-已删除';

        comment on column jimu_report.api_method is '请求方法0-get,1-post';

        comment on column jimu_report.api_code is '请求编码';

        comment on column jimu_report.template is '是否是模板 0不是,1是';

        comment on column jimu_report.view_count is '浏览次数';

        comment on column jimu_report.css_str is 'css增强';

        comment on column jimu_report.js_str is 'js增强';

        comment on column jimu_report.tenant_id is '多租户标识';

        comment on column jimu_report.py_str is 'py增强';

        create index if not exists uniq_jmreport_code
            on jimu_report (code);

        create index if not exists uniq_jmreport_createby
            on jimu_report (create_by);

        create index if not exists uniq_jmreport_delflag
            on jimu_report (del_flag);

        create table if not exists jimu_report_data_source
        (
            id            varchar(36) not null
                primary key,
            name          varchar(100),
            report_id     varchar(100),
            code          varchar(100),
            remark        varchar(200),
            db_type       varchar(10),
            db_driver     varchar(100),
            db_url        varchar(500),
            db_username   varchar(100),
            db_password   varchar(100),
            create_by     varchar(50),
            create_time   timestamp,
            update_by     varchar(50),
            update_time   timestamp,
            connect_times integer,
            tenant_id     varchar(10),
            type          varchar(10)
        );

        comment on column jimu_report_data_source.name is '数据源名称';

        comment on column jimu_report_data_source.report_id is '报表_id';

        comment on column jimu_report_data_source.code is '编码';

        comment on column jimu_report_data_source.remark is '备注';

        comment on column jimu_report_data_source.db_type is '数据库类型';

        comment on column jimu_report_data_source.db_driver is '驱动类';

        comment on column jimu_report_data_source.db_url is '数据源地址';

        comment on column jimu_report_data_source.db_username is '用户名';

        comment on column jimu_report_data_source.db_password is '密码';

        comment on column jimu_report_data_source.create_by is '创建人';

        comment on column jimu_report_data_source.create_time is '创建日期';

        comment on column jimu_report_data_source.update_by is '更新人';

        comment on column jimu_report_data_source.update_time is '更新日期';

        comment on column jimu_report_data_source.connect_times is '连接失败次数';

        comment on column jimu_report_data_source.tenant_id is '多租户标识';

        comment on column jimu_report_data_source.type is '类型(report:报表;drag:仪表盘)';

        create index if not exists idx_jmdatasource_code
            on jimu_report_data_source (code);

        create index if not exists idx_jmdatasource_report_id
            on jimu_report_data_source (report_id);

        create table if not exists jimu_report_db
        (
            id               varchar(36) not null
                primary key,
            jimu_report_id   varchar(32),
            create_by        varchar(50),
            update_by        varchar(50),
            create_time      timestamp,
            update_time      timestamp,
            db_code          varchar(32),
            db_ch_name       varchar(50),
            db_type          varchar(32),
            db_table_name    varchar(32),
            db_dyn_sql       text,
            db_key           varchar(32),
            tb_db_key        varchar(32),
            tb_db_table_name varchar(32),
            java_type        varchar(32),
            java_value       varchar(255),
            api_url          varchar(255),
            api_method       varchar(255),
            is_list          varchar(10),
            is_page          varchar(10),
            db_source        varchar(255),
            db_source_type   varchar(50),
            json_data        text,
            api_convert      varchar(255)
        );

        comment on column jimu_report_db.id is 'id';

        comment on column jimu_report_db.jimu_report_id is '主键字段';

        comment on column jimu_report_db.create_by is '创建人登录名称';

        comment on column jimu_report_db.update_by is '更新人登录名称';

        comment on column jimu_report_db.create_time is '创建日期';

        comment on column jimu_report_db.update_time is '更新日期';

        comment on column jimu_report_db.db_code is '数据集编码';

        comment on column jimu_report_db.db_ch_name is '数据集名字';

        comment on column jimu_report_db.db_type is '数据源类型';

        comment on column jimu_report_db.db_table_name is '数据库表名';

        comment on column jimu_report_db.db_dyn_sql is '动态查询SQL';

        comment on column jimu_report_db.db_key is '数据源KEY';

        comment on column jimu_report_db.tb_db_key is '填报数据源';

        comment on column jimu_report_db.tb_db_table_name is '填报数据表';

        comment on column jimu_report_db.java_type is 'java类数据集  类型（spring:springkey,class:java类名）';

        comment on column jimu_report_db.java_value is 'java类数据源  数值（bean key/java类名）';

        comment on column jimu_report_db.api_url is '请求地址';

        comment on column jimu_report_db.api_method is '请求方法0-get,1-post';

        comment on column jimu_report_db.is_list is '是否是列表0否1是 默认0';

        comment on column jimu_report_db.is_page is '是否作为分页,0:不分页，1:分页';

        comment on column jimu_report_db.db_source is '数据源';

        comment on column jimu_report_db.db_source_type is '数据库类型 MYSQL ORACLE SQLSERVER';

        comment on column jimu_report_db.json_data is 'json数据，直接解析json内容';

        comment on column jimu_report_db.api_convert is 'api转换器';

        create index if not exists idx_db_source_id
            on jimu_report_db (db_source);

        create index if not exists idx_jimu_report_id
            on jimu_report_db (jimu_report_id);

        create index if not exists idx_jmreportdb_db_key
            on jimu_report_db (db_key);

        create table if not exists jimu_report_db_field
        (
            id                varchar(36) not null
                primary key,
            create_by         varchar(50),
            create_time       timestamp,
            update_by         varchar(50),
            update_time       timestamp,
            jimu_report_db_id varchar(32),
            field_name        varchar(80),
            field_text        varchar(50),
            widget_type       varchar(50),
            widget_width      integer,
            order_num         integer,
            search_flag       integer,
            search_mode       integer,
            dict_code         varchar(255),
            search_value      varchar(100),
            search_format     varchar(50),
            ext_json          text
        );

        comment on column jimu_report_db_field.id is 'id';

        comment on column jimu_report_db_field.create_by is '创建人登录名称';

        comment on column jimu_report_db_field.create_time is '创建日期';

        comment on column jimu_report_db_field.update_by is '更新人登录名称';

        comment on column jimu_report_db_field.update_time is '更新日期';

        comment on column jimu_report_db_field.jimu_report_db_id is '数据源ID';

        comment on column jimu_report_db_field.field_name is '字段名';

        comment on column jimu_report_db_field.field_text is '字段文本';

        comment on column jimu_report_db_field.widget_type is '控件类型';

        comment on column jimu_report_db_field.widget_width is '控件宽度';

        comment on column jimu_report_db_field.order_num is '排序';

        comment on column jimu_report_db_field.search_flag is '查询标识0否1是 默认0';

        comment on column jimu_report_db_field.search_mode is '查询模式1简单2范围';

        comment on column jimu_report_db_field.dict_code is '字典编码支持从表中取数据';

        comment on column jimu_report_db_field.search_value is '查询默认值';

        comment on column jimu_report_db_field.search_format is '查询时间格式化表达式';

        comment on column jimu_report_db_field.ext_json is '参数配置';

        create index if not exists idx_dbfield_order_num
            on jimu_report_db_field (order_num);

        create index if not exists idx_jrdf_jimu_report_db_id
            on jimu_report_db_field (jimu_report_db_id);

        create table if not exists jimu_report_db_param
        (
            id                  varchar(36) not null
                primary key,
            jimu_report_head_id varchar(36) not null,
            param_name          varchar(32) not null,
            param_txt           varchar(32),
            param_value         varchar(1000),
            order_num           integer,
            create_by           varchar(50),
            create_time         timestamp,
            update_by           varchar(50),
            update_time         timestamp,
            search_flag         integer,
            widget_type         varchar(50),
            search_mode         integer,
            dict_code           varchar(255),
            search_format       varchar(50),
            ext_json            text
        );

        comment on column jimu_report_db_param.jimu_report_head_id is '动态报表ID';

        comment on column jimu_report_db_param.param_name is '参数字段';

        comment on column jimu_report_db_param.param_txt is '参数文本';

        comment on column jimu_report_db_param.param_value is '参数默认值';

        comment on column jimu_report_db_param.order_num is '排序';

        comment on column jimu_report_db_param.create_by is '创建人登录名称';

        comment on column jimu_report_db_param.create_time is '创建日期';

        comment on column jimu_report_db_param.update_by is '更新人登录名称';

        comment on column jimu_report_db_param.update_time is '更新日期';

        comment on column jimu_report_db_param.search_flag is '查询标识0否1是 默认0';

        comment on column jimu_report_db_param.widget_type is '查询控件类型';

        comment on column jimu_report_db_param.search_mode is '查询模式1简单2范围';

        comment on column jimu_report_db_param.dict_code is '字典';

        comment on column jimu_report_db_param.search_format is '查询时间格式化表达式';

        comment on column jimu_report_db_param.ext_json is '参数配置';

        create index if not exists idx_jrdp_jimu_report_head_id
            on jimu_report_db_param (jimu_report_head_id);

        create table if not exists jimu_report_link
        (
            id            varchar(32) not null
                primary key,
            report_id     varchar(32),
            parameter     text,
            eject_type    varchar(1),
            link_name     varchar(255),
            api_method    varchar(1),
            link_type     varchar(1),
            api_url       varchar(1000),
            link_chart_id varchar(50),
            requirement   varchar(255)
        );

        comment on table jimu_report_link is '超链接配置表';

        comment on column jimu_report_link.id is '主键id';

        comment on column jimu_report_link.report_id is '积木设计器id';

        comment on column jimu_report_link.parameter is '参数';

        comment on column jimu_report_link.eject_type is '弹出方式（0 当前页面 1 新窗口）';

        comment on column jimu_report_link.link_name is '链接名称';

        comment on column jimu_report_link.api_method is '请求方法0-get,1-post';

        comment on column jimu_report_link.link_type is '链接方式(0 网络报表 1 网络连接 2 图表联动)';

        comment on column jimu_report_link.api_url is '外网api';

        comment on column jimu_report_link.link_chart_id is '联动图表的ID';

        comment on column jimu_report_link.requirement is '条件';

        create index if not exists uniq_link_reportid
            on jimu_report_link (report_id);

        create table if not exists jimu_report_map
        (
            id           varchar(64) not null
                primary key,
            label        varchar(125),
            name         varchar(125),
            data         text,
            create_by    varchar(32),
            create_time  timestamp,
            update_by    varchar(32),
            update_time  timestamp,
            del_flag     varchar(1),
            sys_org_code varchar(64)
        );

        comment on table jimu_report_map is '地图配置表';

        comment on column jimu_report_map.id is '主键';

        comment on column jimu_report_map.label is '地图名称';

        comment on column jimu_report_map.name is '地图编码';

        comment on column jimu_report_map.data is '地图数据';

        comment on column jimu_report_map.create_by is '创建人';

        comment on column jimu_report_map.create_time is '创建时间';

        comment on column jimu_report_map.update_by is '修改人';

        comment on column jimu_report_map.update_time is '修改时间';

        comment on column jimu_report_map.del_flag is '0表示未删除,1表示删除';

        comment on column jimu_report_map.sys_org_code is '所属部门';

        create index if not exists uniq_jmreport_map_name
            on jimu_report_map (name);

        create table if not exists jimu_report_share
        (
            id                  varchar(32) not null
                primary key,
            report_id           varchar(32)
                constraint uniq_report_id
                    unique,
            preview_url         varchar(1000),
            preview_lock        varchar(4),
            last_update_time    timestamp,
            term_of_validity    varchar(1),
            status              varchar(1),
            preview_lock_status varchar(1),
            share_token         varchar(50)
        );

        comment on table jimu_report_share is '积木报表预览权限表';

        comment on column jimu_report_share.id is '主键';

        comment on column jimu_report_share.report_id is '在线excel设计器id';

        comment on column jimu_report_share.preview_url is '预览地址';

        comment on column jimu_report_share.preview_lock is '密码锁';

        comment on column jimu_report_share.last_update_time is '最后更新时间';

        comment on column jimu_report_share.term_of_validity is '有效期(0:永久有效，1:1天，2:7天)';

        comment on column jimu_report_share.status is '是否过期(0未过期，1已过期)';

        comment on column jimu_report_share.preview_lock_status is '是否为密码锁(0 否,1是)';

        comment on column jimu_report_share.share_token is '分享token';

        create unique index if not exists uniq_jrs_share_token
            on jimu_report_share (share_token);

        create table if not exists jmreport_big_screen
        (
            id                   varchar(36) not null,
            screen_name          varchar(255),
            screen_describe      varchar(100),
            image_url            varchar(255),
            status               varchar(2),
            des_screen_main_json text,
            des_screen_item_json text,
            type                 varchar(10),
            thumbnail            varchar(255),
            create_by            varchar(32),
            create_time          timestamp(6),
            update_by            varchar(32),
            update_time          timestamp(6),
            del_flag             smallint,
            protection_code      varchar(10),
            visits_num           integer
        );

        comment on table jmreport_big_screen is '大屏设计表';

        comment on column jmreport_big_screen.id is 'ID';

        comment on column jmreport_big_screen.screen_name is '大屏名字';

        comment on column jmreport_big_screen.screen_describe is '简介';

        comment on column jmreport_big_screen.image_url is '缩略图';

        comment on column jmreport_big_screen.status is '状态';

        comment on column jmreport_big_screen.des_screen_main_json is '大屏主配置JSON';

        comment on column jmreport_big_screen.des_screen_item_json is '大屏组件设计JSON';

        comment on column jmreport_big_screen.type is '大屏类型(0:模版,1:组件)';

        comment on column jmreport_big_screen.thumbnail is '缩略图';

        comment on column jmreport_big_screen.create_by is '创建人';

        comment on column jmreport_big_screen.create_time is '创建时间';

        comment on column jmreport_big_screen.update_by is '修改人';

        comment on column jmreport_big_screen.update_time is '修改时间';

        comment on column jmreport_big_screen.del_flag is '删除标识0-正常,1-已删除';

        comment on column jmreport_big_screen.protection_code is '保护码';

        comment on column jmreport_big_screen.visits_num is '访问次数';

        create table if not exists jmreport_big_screen_category
        (
            id             varchar(36) not null,
            category_key   varchar(12),
            category_value varchar(64),
            create_by      varchar(32),
            create_time    timestamp(6),
            update_by      varchar(32),
            update_time    timestamp(6),
            del_flag       varchar(1)
        );

        comment on table jmreport_big_screen_category is '可视化分类表';

        comment on column jmreport_big_screen_category.id is '主键';

        comment on column jmreport_big_screen_category.category_key is '分类键值';

        comment on column jmreport_big_screen_category.category_value is '分类名称';

        comment on column jmreport_big_screen_category.create_by is '创建人';

        comment on column jmreport_big_screen_category.create_time is '创建时间';

        comment on column jmreport_big_screen_category.update_by is '修改人';

        comment on column jmreport_big_screen_category.update_time is '修改时间';

        comment on column jmreport_big_screen_category.del_flag is '0表示未删除,1表示删除';

        create table if not exists jmreport_big_screen_visual_map
        (
            id          varchar(64) not null,
            name        varchar(255),
            data        text,
            create_by   varchar(32),
            create_time timestamp(6),
            update_by   varchar(32),
            update_time timestamp(6),
            del_flag    varchar(1)
        );

        comment on table jmreport_big_screen_visual_map is '可视化地图配置表';

        comment on column jmreport_big_screen_visual_map.id is '主键';

        comment on column jmreport_big_screen_visual_map.name is '地图名称';

        comment on column jmreport_big_screen_visual_map.data is '地图数据';

        comment on column jmreport_big_screen_visual_map.create_by is '创建人';

        comment on column jmreport_big_screen_visual_map.create_time is '创建时间';

        comment on column jmreport_big_screen_visual_map.update_by is '修改人';

        comment on column jmreport_big_screen_visual_map.update_time is '修改时间';

        comment on column jmreport_big_screen_visual_map.del_flag is '0表示未删除,1表示删除';

        create table if not exists joa_demo
        (
            id          varchar(32),
            name        varchar(100),
            days        integer,
            begin_date  timestamp,
            end_date    timestamp,
            reason      varchar(500),
            bpm_status  varchar(50),
            create_by   varchar(32),
            create_time timestamp,
            update_time timestamp,
            update_by   varchar(32)
        );

        comment on table joa_demo is '流程测试';

        comment on column joa_demo.id is 'ID';

        comment on column joa_demo.name is '请假人';

        comment on column joa_demo.days is '请假天数';

        comment on column joa_demo.begin_date is '开始时间';

        comment on column joa_demo.end_date is '请假结束时间';

        comment on column joa_demo.reason is '请假原因';

        comment on column joa_demo.bpm_status is '流程状态';

        comment on column joa_demo.create_by is '创建人id';

        comment on column joa_demo.create_time is '创建时间';

        comment on column joa_demo.update_time is '修改时间';

        comment on column joa_demo.update_by is '修改人id';

        create table if not exists onl_auth_data
        (
            id            varchar(32) not null
                primary key,
            cgform_id     varchar(32),
            rule_name     varchar(50),
            rule_column   varchar(50),
            rule_operator varchar(50),
            rule_value    varchar(255),
            status        integer,
            create_time   timestamp,
            create_by     varchar(50),
            update_by     varchar(50),
            update_time   timestamp
        );

        comment on column onl_auth_data.id is '主键';

        comment on column onl_auth_data.cgform_id is 'online表ID';

        comment on column onl_auth_data.rule_name is '规则名';

        comment on column onl_auth_data.rule_column is '规则列';

        comment on column onl_auth_data.rule_operator is '规则条件 大于小于like';

        comment on column onl_auth_data.rule_value is '规则值';

        comment on column onl_auth_data.status is '1有效 0无效';

        comment on column onl_auth_data.create_time is '创建时间';

        comment on column onl_auth_data.create_by is '创建人';

        comment on column onl_auth_data.update_by is '更新人';

        comment on column onl_auth_data.update_time is '更新日期';

        create table if not exists onl_auth_page
        (
            id          varchar(32) not null
                primary key,
            cgform_id   varchar(32),
            code        varchar(255),
            type        integer,
            control     integer,
            page        integer,
            status      integer,
            create_time timestamp,
            create_by   varchar(32),
            update_by   varchar(50),
            update_time timestamp
        );

        comment on column onl_auth_page.id is ' 主键';

        comment on column onl_auth_page.cgform_id is 'online表id';

        comment on column onl_auth_page.code is '字段名/按钮编码';

        comment on column onl_auth_page.type is '1字段 2按钮';

        comment on column onl_auth_page.control is '3可编辑 5可见(仅支持两种状态值3,5)';

        comment on column onl_auth_page.page is '3列表 5表单(仅支持两种状态值3,5)';

        comment on column onl_auth_page.status is '1有效 0无效';

        comment on column onl_auth_page.create_time is '创建时间';

        comment on column onl_auth_page.create_by is '创建人';

        comment on column onl_auth_page.update_by is '更新人';

        comment on column onl_auth_page.update_time is '更新日期';

        create table if not exists onl_auth_relation
        (
            id        varchar(32) not null
                primary key,
            role_id   varchar(32),
            auth_id   varchar(32),
            type      integer,
            cgform_id varchar(32),
            auth_mode varchar(50)
        );

        comment on column onl_auth_relation.role_id is '角色id';

        comment on column onl_auth_relation.auth_id is '权限id';

        comment on column onl_auth_relation.type is '1字段 2按钮 3数据权限';

        comment on column onl_auth_relation.cgform_id is 'online表单ID';

        comment on column onl_auth_relation.auth_mode is '授权方式role角色，depart部门，user人';

        create table if not exists onl_cgform_button
        (
            id             varchar(32) not null
                constraint onl_cgform_button_copy1_pkey
                    primary key,
            button_code    varchar(50),
            button_icon    varchar(20),
            button_name    varchar(50),
            button_status  integer,
            button_style   varchar(20),
            exp            varchar(255),
            cgform_head_id varchar(32),
            opt_type       varchar(20),
            order_num      integer,
            opt_position   varchar(3)
        );

        comment on table onl_cgform_button is 'Online表单自定义按钮';

        comment on column onl_cgform_button.id is '主键ID';

        comment on column onl_cgform_button.button_code is '按钮编码';

        comment on column onl_cgform_button.button_icon is '按钮图标';

        comment on column onl_cgform_button.button_name is '按钮名称';

        comment on column onl_cgform_button.button_status is '按钮状态';

        comment on column onl_cgform_button.button_style is '按钮样式';

        comment on column onl_cgform_button.exp is '表达式';

        comment on column onl_cgform_button.cgform_head_id is '表单ID';

        comment on column onl_cgform_button.opt_type is '按钮类型';

        comment on column onl_cgform_button.order_num is '排序';

        comment on column onl_cgform_button.opt_position is '按钮位置1侧面 2底部';

        create index if not exists "idx_ocb_BUTTON_CODE_copy1"
            on onl_cgform_button (button_code);

        create index if not exists "idx_ocb_BUTTON_STATUS_copy1"
            on onl_cgform_button (button_status);

        create index if not exists "idx_ocb_CGFORM_HEAD_ID_copy1"
            on onl_cgform_button (cgform_head_id);

        create index if not exists "idx_ocb_ORDER_NUM_copy1"
            on onl_cgform_button (order_num);

        create table if not exists onl_cgform_enhance_java
        (
            id             varchar(36)  not null
                constraint onl_cgform_enhance_java_copy1_pkey
                    primary key,
            button_code    varchar(32),
            cg_java_type   varchar(32)  not null,
            cg_java_value  varchar(200) not null,
            cgform_head_id varchar(32)  not null,
            active_status  varchar(2),
            event          varchar(10)  not null
        );

        comment on column onl_cgform_enhance_java.button_code is '按钮编码';

        comment on column onl_cgform_enhance_java.cg_java_type is '类型';

        comment on column onl_cgform_enhance_java.cg_java_value is '数值';

        comment on column onl_cgform_enhance_java.cgform_head_id is '表单ID';

        comment on column onl_cgform_enhance_java.active_status is '生效状态';

        comment on column onl_cgform_enhance_java.event is '事件状态(end:结束，start:开始)';

        create index if not exists idx_ejava_cgform_head_id_copy1
            on onl_cgform_enhance_java (cgform_head_id);

        create index if not exists "idx_ocej_ACTIVE_STATUS_copy1"
            on onl_cgform_enhance_java (active_status);

        create index if not exists "idx_ocej_BUTTON_CODE_copy1"
            on onl_cgform_enhance_java (button_code);

        create table if not exists onl_cgform_enhance_js
        (
            id             varchar(32) not null
                constraint onl_cgform_enhance_js_copy1_pkey
                    primary key,
            cg_js          text,
            cg_js_type     varchar(20),
            content        varchar(1000),
            cgform_head_id varchar(32)
        );

        comment on column onl_cgform_enhance_js.id is '主键ID';

        comment on column onl_cgform_enhance_js.cg_js is 'JS增强内容';

        comment on column onl_cgform_enhance_js.cg_js_type is '类型';

        comment on column onl_cgform_enhance_js.content is '备注';

        comment on column onl_cgform_enhance_js.cgform_head_id is '表单ID';

        create index if not exists idx_ejs_cg_js_type_copy1
            on onl_cgform_enhance_js (cg_js_type);

        create index if not exists idx_ejs_cgform_head_id_copy1
            on onl_cgform_enhance_js (cgform_head_id);

        create table if not exists onl_cgform_enhance_sql
        (
            id             varchar(32) not null
                constraint onl_cgform_enhance_sql_copy1_pkey
                    primary key,
            button_code    varchar(50),
            cgb_sql        text,
            cgb_sql_name   varchar(50),
            content        varchar(1000),
            cgform_head_id varchar(32)
        );

        comment on column onl_cgform_enhance_sql.id is '主键ID';

        comment on column onl_cgform_enhance_sql.button_code is '按钮编码';

        comment on column onl_cgform_enhance_sql.cgb_sql is 'SQL内容';

        comment on column onl_cgform_enhance_sql.cgb_sql_name is 'Sql名称';

        comment on column onl_cgform_enhance_sql.content is '备注';

        comment on column onl_cgform_enhance_sql.cgform_head_id is '表单ID';

        create index if not exists "idx_oces_CGFORM_HEAD_ID_copy1"
            on onl_cgform_enhance_sql (cgform_head_id);

        create table if not exists onl_cgform_field
        (
            id                  varchar(36) not null
                constraint onl_cgform_field_copy1_pkey
                    primary key,
            cgform_head_id      varchar(36) not null,
            db_field_name       varchar(36) not null,
            db_field_txt        varchar(200),
            db_field_name_old   varchar(36),
            db_is_key           smallint,
            db_is_null          smallint,
            db_is_persist       smallint,
            db_type             varchar(36) not null,
            db_length           integer     not null,
            db_point_length     integer,
            db_default_val      varchar(20),
            dict_field          varchar(100),
            dict_table          varchar(255),
            dict_text           varchar(100),
            field_show_type     varchar(20),
            field_href          varchar(200),
            field_length        integer,
            field_valid_type    varchar(300),
            field_must_input    varchar(2),
            field_extend_json   varchar(500),
            field_default_value varchar(100),
            is_query            smallint,
            is_show_form        smallint,
            is_show_list        smallint,
            is_read_only        smallint,
            query_mode          varchar(10),
            main_table          varchar(100),
            main_field          varchar(100),
            order_num           integer,
            update_by           varchar(36),
            update_time         timestamp(6),
            create_time         timestamp(6),
            create_by           varchar(255),
            converter           varchar(255),
            query_def_val       varchar(50),
            query_dict_text     varchar(100),
            query_dict_field    varchar(100),
            query_dict_table    varchar(500),
            query_show_type     varchar(50),
            query_config_flag   varchar(3),
            query_valid_type    varchar(50),
            query_must_input    varchar(3),
            sort_flag           varchar(3)
        );

        comment on column onl_cgform_field.id is '主键ID';

        comment on column onl_cgform_field.cgform_head_id is '表ID';

        comment on column onl_cgform_field.db_field_name is '字段名字';

        comment on column onl_cgform_field.db_field_txt is '字段备注';

        comment on column onl_cgform_field.db_field_name_old is '原字段名';

        comment on column onl_cgform_field.db_is_key is '是否主键 0否 1是';

        comment on column onl_cgform_field.db_is_null is '是否允许为空0否 1是';

        comment on column onl_cgform_field.db_is_persist is '是否需要同步数据库字段， 1是0否';

        comment on column onl_cgform_field.db_type is '数据库字段类型';

        comment on column onl_cgform_field.db_length is '数据库字段长度';

        comment on column onl_cgform_field.db_point_length is '小数点';

        comment on column onl_cgform_field.db_default_val is '表字段默认值';

        comment on column onl_cgform_field.dict_field is '字典code';

        comment on column onl_cgform_field.dict_table is '字典表';

        comment on column onl_cgform_field.dict_text is '字典Text';

        comment on column onl_cgform_field.field_show_type is '表单控件类型';

        comment on column onl_cgform_field.field_href is '跳转URL';

        comment on column onl_cgform_field.field_length is '表单控件长度';

        comment on column onl_cgform_field.field_valid_type is '表单字段校验规则';

        comment on column onl_cgform_field.field_must_input is '字段是否必填';

        comment on column onl_cgform_field.field_extend_json is '扩展参数JSON';

        comment on column onl_cgform_field.field_default_value is '控件默认值，不同的表达式展示不同的结果。
1. 纯字符串直接赋给默认值；
2. #{普通变量}；
3. {{ 动态JS表达式 }}；
4. ${填值规则编码}；
填值规则表达式只允许存在一个，且不能和其他规则混用。';

        comment on column onl_cgform_field.is_query is '是否查询条件0否 1是';

        comment on column onl_cgform_field.is_show_form is '表单是否显示0否 1是';

        comment on column onl_cgform_field.is_show_list is '列表是否显示0否 1是';

        comment on column onl_cgform_field.is_read_only is '是否是只读（1是 0否）';

        comment on column onl_cgform_field.query_mode is '查询模式';

        comment on column onl_cgform_field.main_table is '外键主表名';

        comment on column onl_cgform_field.main_field is '外键主键字段';

        comment on column onl_cgform_field.order_num is '排序';

        comment on column onl_cgform_field.update_by is '修改人';

        comment on column onl_cgform_field.update_time is '修改时间';

        comment on column onl_cgform_field.create_time is '创建时间';

        comment on column onl_cgform_field.create_by is '创建人';

        comment on column onl_cgform_field.converter is '自定义值转换器';

        comment on column onl_cgform_field.query_def_val is '查询默认值';

        comment on column onl_cgform_field.query_dict_text is '查询配置字典text';

        comment on column onl_cgform_field.query_dict_field is '查询配置字典code';

        comment on column onl_cgform_field.query_dict_table is '查询配置字典table';

        comment on column onl_cgform_field.query_show_type is '查询显示控件';

        comment on column onl_cgform_field.query_config_flag is '是否启用查询配置1是0否';

        comment on column onl_cgform_field.query_valid_type is '查询字段校验类型';

        comment on column onl_cgform_field.query_must_input is '查询字段是否必填1是0否';

        comment on column onl_cgform_field.sort_flag is '是否支持排序1是0否';

        create index if not exists idx_ocf_cgform_head_id_copy1
            on onl_cgform_field (cgform_head_id);

        create table if not exists onl_cgform_head
        (
            id                   varchar(36)  not null
                constraint onl_cgform_head_copy1_pkey
                    primary key,
            table_name           varchar(50)  not null,
            table_type           integer      not null,
            table_version        integer,
            table_txt            varchar(200) not null,
            is_checkbox          varchar(5)   not null,
            is_db_synch          varchar(20)  not null,
            is_page              varchar(5)   not null,
            is_tree              varchar(5)   not null,
            id_sequence          varchar(200),
            id_type              varchar(100),
            query_mode           varchar(10),
            relation_type        integer,
            sub_table_str        varchar(1000),
            tab_order_num        integer,
            tree_parent_id_field varchar(50),
            tree_id_field        varchar(50),
            tree_fieldname       varchar(50),
            form_category        varchar(50)  not null,
            form_template        varchar(50),
            form_template_mobile varchar(50),
            scroll               integer,
            copy_version         integer,
            copy_type            integer,
            physic_id            varchar(36),
            ext_config_json      varchar(1000),
            update_by            varchar(36),
            update_time          timestamp(6),
            create_by            varchar(32),
            create_time          timestamp(6),
            theme_template       varchar(50),
            is_des_form          varchar(2),
            des_form_code        varchar(50),
            low_app_id           varchar(36)
        );

        comment on column onl_cgform_head.id is '主键ID';

        comment on column onl_cgform_head.table_name is '表名';

        comment on column onl_cgform_head.table_type is '表类型: 0单表、1主表、2附表';

        comment on column onl_cgform_head.table_version is '表版本';

        comment on column onl_cgform_head.table_txt is '表说明';

        comment on column onl_cgform_head.is_checkbox is '是否带checkbox';

        comment on column onl_cgform_head.is_db_synch is '同步数据库状态';

        comment on column onl_cgform_head.is_page is '是否分页';

        comment on column onl_cgform_head.is_tree is '是否是树';

        comment on column onl_cgform_head.id_sequence is '主键生成序列';

        comment on column onl_cgform_head.id_type is '主键类型';

        comment on column onl_cgform_head.query_mode is '查询模式';

        comment on column onl_cgform_head.relation_type is '映射关系 0一对多  1一对一';

        comment on column onl_cgform_head.sub_table_str is '子表';

        comment on column onl_cgform_head.tab_order_num is '附表排序序号';

        comment on column onl_cgform_head.tree_parent_id_field is '树形表单父id';

        comment on column onl_cgform_head.tree_id_field is '树表主键字段';

        comment on column onl_cgform_head.tree_fieldname is '树开表单列字段';

        comment on column onl_cgform_head.form_category is '表单分类';

        comment on column onl_cgform_head.form_template is 'PC表单模板';

        comment on column onl_cgform_head.form_template_mobile is '表单模板样式(移动端)';

        comment on column onl_cgform_head.scroll is '是否有横向滚动条';

        comment on column onl_cgform_head.copy_version is '复制版本号';

        comment on column onl_cgform_head.copy_type is '复制表类型1为复制表 0为原始表';

        comment on column onl_cgform_head.physic_id is '原始表ID';

        comment on column onl_cgform_head.ext_config_json is '扩展JSON';

        comment on column onl_cgform_head.update_by is '修改人';

        comment on column onl_cgform_head.update_time is '修改时间';

        comment on column onl_cgform_head.create_by is '创建人';

        comment on column onl_cgform_head.create_time is '创建时间';

        comment on column onl_cgform_head.theme_template is '主题模板';

        comment on column onl_cgform_head.is_des_form is '是否用设计器表单';

        comment on column onl_cgform_head.des_form_code is '设计器表单编码';

        comment on column onl_cgform_head.low_app_id is '关联的应用ID';

        create index if not exists idx_och_cgform_head_id_copy1
            on onl_cgform_head (table_name);

        create index if not exists idx_och_form_template_mobile_copy1
            on onl_cgform_head (form_template_mobile);

        create index if not exists idx_och_table_name_copy1
            on onl_cgform_head (form_template);

        create index if not exists idx_och_table_version_copy1
            on onl_cgform_head (table_version);

        create table if not exists onl_cgform_index
        (
            id             varchar(36) not null
                constraint onl_cgform_index_copy1_pkey
                    primary key,
            cgform_head_id varchar(36),
            index_name     varchar(100),
            index_field    varchar(500),
            index_type     varchar(36),
            create_by      varchar(50),
            create_time    timestamp(6),
            update_by      varchar(50),
            update_time    timestamp(6),
            is_db_synch    varchar(2),
            del_flag       integer
        );

        comment on column onl_cgform_index.id is '主键';

        comment on column onl_cgform_index.cgform_head_id is '主表id';

        comment on column onl_cgform_index.index_name is '索引名称';

        comment on column onl_cgform_index.index_field is '索引栏位';

        comment on column onl_cgform_index.index_type is '索引类型';

        comment on column onl_cgform_index.create_by is '创建人登录名称';

        comment on column onl_cgform_index.create_time is '创建日期';

        comment on column onl_cgform_index.update_by is '更新人登录名称';

        comment on column onl_cgform_index.update_time is '更新日期';

        comment on column onl_cgform_index.is_db_synch is '是否同步数据库 N未同步 Y已同步';

        comment on column onl_cgform_index.del_flag is '是否删除 0未删除 1删除';

        create index if not exists idx_oci_cgform_head_id_copy1
            on onl_cgform_index (cgform_head_id);

        create table if not exists onl_cgreport_head
        (
            id               varchar(36)   not null
                primary key,
            code             varchar(100)  not null,
            name             varchar(100)  not null,
            cgr_sql          varchar(1000) not null,
            return_val_field varchar(100),
            return_txt_field varchar(100),
            return_type      varchar(2),
            db_source        varchar(100),
            content          varchar(1000),
            low_app_id       varchar(32),
            update_time      timestamp,
            update_by        varchar(32),
            create_time      timestamp,
            create_by        varchar(32)
        );

        comment on column onl_cgreport_head.code is '报表编码';

        comment on column onl_cgreport_head.name is '报表名字';

        comment on column onl_cgreport_head.cgr_sql is '报表SQL';

        comment on column onl_cgreport_head.return_val_field is '返回值字段';

        comment on column onl_cgreport_head.return_txt_field is '返回文本字段';

        comment on column onl_cgreport_head.return_type is '返回类型，单选或多选';

        comment on column onl_cgreport_head.db_source is '动态数据源';

        comment on column onl_cgreport_head.content is '描述';

        comment on column onl_cgreport_head.low_app_id is '关联的应用ID';

        comment on column onl_cgreport_head.update_time is '修改时间';

        comment on column onl_cgreport_head.update_by is '修改人id';

        comment on column onl_cgreport_head.create_time is '创建时间';

        comment on column onl_cgreport_head.create_by is '创建人id';

        create index if not exists idx_och_code
            on onl_cgreport_head (code);

        create index if not exists index_onlinereport_code
            on onl_cgreport_head (code);

        create table if not exists onl_cgreport_item
        (
            id          varchar(36) not null
                primary key,
            cgrhead_id  varchar(36) not null,
            field_name  varchar(36) not null,
            field_txt   varchar(300),
            field_width integer,
            field_type  varchar(10),
            search_mode varchar(10),
            is_order    integer,
            is_search   integer,
            dict_code   varchar(500),
            field_href  varchar(120),
            is_show     integer,
            order_num   integer,
            replace_val varchar(200),
            is_total    varchar(2),
            group_title varchar(50),
            create_by   varchar(32),
            create_time timestamp,
            update_by   varchar(32),
            update_time timestamp
        );

        comment on column onl_cgreport_item.cgrhead_id is '报表ID';

        comment on column onl_cgreport_item.field_name is '字段名字';

        comment on column onl_cgreport_item.field_txt is '字段文本';

        comment on column onl_cgreport_item.field_type is '字段类型';

        comment on column onl_cgreport_item.search_mode is '查询模式';

        comment on column onl_cgreport_item.is_order is '是否排序  0否,1是';

        comment on column onl_cgreport_item.is_search is '是否查询  0否,1是';

        comment on column onl_cgreport_item.dict_code is '字典CODE';

        comment on column onl_cgreport_item.field_href is '字段跳转URL';

        comment on column onl_cgreport_item.is_show is '是否显示  0否,1显示';

        comment on column onl_cgreport_item.order_num is '排序';

        comment on column onl_cgreport_item.replace_val is '取值表达式';

        comment on column onl_cgreport_item.is_total is '是否合计 0否,1是（仅对数值有效）';

        comment on column onl_cgreport_item.group_title is '分组标题';

        comment on column onl_cgreport_item.create_by is '创建人';

        comment on column onl_cgreport_item.create_time is '创建时间';

        comment on column onl_cgreport_item.update_by is '修改人';

        comment on column onl_cgreport_item.update_time is '修改时间';

        create index if not exists idx_oci_cgrhead_id
            on onl_cgreport_item (cgrhead_id);

        create index if not exists idx_oci_is_show
            on onl_cgreport_item (is_show);

        create index if not exists idx_oci_order_num
            on onl_cgreport_item (order_num);

        create table if not exists onl_cgreport_param
        (
            id          varchar(36) not null
                primary key,
            cgrhead_id  varchar(36) not null,
            param_name  varchar(32) not null,
            param_txt   varchar(32),
            param_value varchar(32),
            order_num   integer,
            create_by   varchar(50),
            create_time timestamp,
            update_by   varchar(50),
            update_time timestamp
        );

        comment on column onl_cgreport_param.cgrhead_id is '动态报表ID';

        comment on column onl_cgreport_param.param_name is '参数字段';

        comment on column onl_cgreport_param.param_txt is '参数文本';

        comment on column onl_cgreport_param.param_value is '参数默认值';

        comment on column onl_cgreport_param.order_num is '排序';

        comment on column onl_cgreport_param.create_by is '创建人登录名称';

        comment on column onl_cgreport_param.create_time is '创建日期';

        comment on column onl_cgreport_param.update_by is '更新人登录名称';

        comment on column onl_cgreport_param.update_time is '更新日期';

        create index if not exists idx_ocp_cgrhead_id
            on onl_cgreport_param (cgrhead_id);

        create table if not exists onl_graphreport_head
        (
            id               varchar(32)   not null,
            name             varchar(100)  not null,
            code             varchar(36)   not null,
            cgr_sql          varchar(5000) not null,
            xaxis_field      varchar(100)  not null,
            yaxis_field      varchar(100)  not null,
            yaxis_text       varchar(100)  not null,
            content          varchar(1000),
            extend_js        varchar(1000),
            graph_type       varchar(50),
            is_combination   varchar(50),
            display_template varchar(50),
            data_type        varchar(50),
            db_source        varchar(100),
            create_time      timestamp(6),
            create_by        varchar(50),
            update_time      timestamp(6),
            update_by        varchar(50)
        );

        comment on column onl_graphreport_head.id is 'id';

        comment on column onl_graphreport_head.name is '图表名称';

        comment on column onl_graphreport_head.code is '图表编码';

        comment on column onl_graphreport_head.cgr_sql is '查询数据SQL';

        comment on column onl_graphreport_head.xaxis_field is 'X轴数据字段';

        comment on column onl_graphreport_head.yaxis_field is 'Y轴数据字段';

        comment on column onl_graphreport_head.yaxis_text is 'y轴文字描述';

        comment on column onl_graphreport_head.content is '描述';

        comment on column onl_graphreport_head.extend_js is '扩展JS';

        comment on column onl_graphreport_head.graph_type is '图表类型';

        comment on column onl_graphreport_head.is_combination is '是否组合';

        comment on column onl_graphreport_head.display_template is '展示模板';

        comment on column onl_graphreport_head.data_type is '数据类型';

        comment on column onl_graphreport_head.db_source is '动态数据源';

        create table if not exists onl_graphreport_item
        (
            id                  varchar(32) not null,
            graphreport_head_id varchar(36) not null,
            field_name          varchar(36),
            field_txt           varchar(1000),
            is_show             varchar(5),
            is_total            varchar(5),
            search_flag         varchar(2),
            search_mode         varchar(10),
            dict_code           varchar(500),
            field_href          varchar(120),
            field_type          varchar(10),
            order_num           integer,
            replace_val         varchar(36),
            create_by           varchar(32),
            create_time         timestamp(6),
            update_by           varchar(32),
            update_time         timestamp(6)
        );

        comment on table onl_graphreport_item is 'jform_graphreport_item';

        comment on column onl_graphreport_item.id is 'id';

        comment on column onl_graphreport_item.graphreport_head_id is '主表ID';

        comment on column onl_graphreport_item.field_name is '字段名';

        comment on column onl_graphreport_item.field_txt is '字段文本';

        comment on column onl_graphreport_item.is_show is '是否列表显示';

        comment on column onl_graphreport_item.is_total is '是否计算总计（仅对数值有效）';

        comment on column onl_graphreport_item.search_flag is '是否查询';

        comment on column onl_graphreport_item.search_mode is '查询模式';

        comment on column onl_graphreport_item.dict_code is '字典Code';

        comment on column onl_graphreport_item.field_href is '字段href';

        comment on column onl_graphreport_item.field_type is '字段类型';

        comment on column onl_graphreport_item.order_num is '排序';

        comment on column onl_graphreport_item.replace_val is '取值表达式';

        comment on column onl_graphreport_item.create_by is '创建人';

        comment on column onl_graphreport_item.create_time is '创建时间';

        comment on column onl_graphreport_item.update_by is '修改人';

        comment on column onl_graphreport_item.update_time is '修改时间';

        create table if not exists onl_graphreport_templet
        (
            id            varchar(32) not null,
            templet_code  varchar(200),
            templet_name  varchar(200),
            templet_style varchar(20),
            create_time   timestamp(6),
            create_by     varchar(50),
            update_time   timestamp(6),
            update_by     varchar(50)
        );

        comment on column onl_graphreport_templet.templet_name is '报表名称';

        comment on column onl_graphreport_templet.templet_style is '报表风格模板（单排、双排、Tab模式、分组模式-根据配置动态展示、可自定义...）';

        create table if not exists onl_graphreport_templet_item
        (
            id                     varchar(32) not null,
            graphreport_templet_id varchar(32) not null,
            graphreport_code       varchar(100),
            graphreport_type       varchar(10),
            group_num              integer,
            group_style            varchar(10),
            group_txt              varchar(50),
            order_num              integer,
            is_show                varchar(1),
            create_time            timestamp(6),
            create_by              varchar(50),
            update_time            timestamp(6),
            update_by              varchar(50)
        );

        comment on column onl_graphreport_templet_item.graphreport_code is '图表编码';

        comment on column onl_graphreport_templet_item.graphreport_type is '图表类型（饼状图、曲线图、柱状图、数据列表等）';

        comment on column onl_graphreport_templet_item.group_num is '组合数字，默认值0 非必填';

        comment on column onl_graphreport_templet_item.group_style is '组合展示风格（1 卡片，2 tab）非必填';

        comment on column onl_graphreport_templet_item.group_txt is '分组描述';

        comment on column onl_graphreport_templet_item.order_num is '排序';

        comment on column onl_graphreport_templet_item.is_show is '是否显示 1显示 0不显示，默认1';

        create table if not exists oss_file
        (
            id          varchar(32) not null
                primary key,
            file_name   varchar(255),
            url         varchar(1000),
            create_by   varchar(32),
            create_time timestamp,
            update_by   varchar(32),
            update_time timestamp,
            md5         varchar(200)
        );

        comment on table oss_file is 'Oss File';

        comment on column oss_file.id is '主键id';

        comment on column oss_file.file_name is '文件名称';

        comment on column oss_file.url is '文件地址';

        comment on column oss_file.create_by is '创建人登录名称';

        comment on column oss_file.create_time is '创建日期';

        comment on column oss_file.update_by is '更新人登录名称';

        comment on column oss_file.update_time is '更新日期';

        comment on column oss_file.md5 is '文件md5值';

        create table if not exists qrtz_calendars
        (
            sched_name    varchar(120) not null,
            calendar_name varchar(200) not null,
            calendar      bytea        not null,
            primary key (sched_name, calendar_name)
        );

        create table if not exists qrtz_fired_triggers
        (
            sched_name        varchar(120) not null,
            entry_id          varchar(95)  not null,
            trigger_name      varchar(200) not null,
            trigger_group     varchar(200) not null,
            instance_name     varchar(200) not null,
            fired_time        bigint       not null,
            sched_time        bigint       not null,
            priority          integer      not null,
            state             varchar(16)  not null,
            job_name          varchar(200),
            job_group         varchar(200),
            is_nonconcurrent  boolean,
            requests_recovery boolean,
            primary key (sched_name, entry_id)
        );

        create index if not exists idx_qrtz_ft_inst_job_req_rcvry
            on qrtz_fired_triggers (sched_name, instance_name, requests_recovery);

        create index if not exists idx_qrtz_ft_j_g
            on qrtz_fired_triggers (sched_name, job_name, job_group);

        create index if not exists idx_qrtz_ft_jg
            on qrtz_fired_triggers (sched_name, job_group);

        create index if not exists idx_qrtz_ft_t_g
            on qrtz_fired_triggers (sched_name, trigger_name, trigger_group);

        create index if not exists idx_qrtz_ft_tg
            on qrtz_fired_triggers (sched_name, trigger_group);

        create index if not exists idx_qrtz_ft_trig_inst_name
            on qrtz_fired_triggers (sched_name, instance_name);

        create table if not exists qrtz_job_details
        (
            sched_name        varchar(120) not null,
            job_name          varchar(200) not null,
            job_group         varchar(200) not null,
            description       varchar(250),
            job_class_name    varchar(250) not null,
            is_durable        boolean      not null,
            is_nonconcurrent  boolean      not null,
            is_update_data    boolean      not null,
            requests_recovery boolean      not null,
            job_data          bytea,
            primary key (sched_name, job_name, job_group)
        );

        create index if not exists idx_qrtz_j_grp
            on qrtz_job_details (sched_name, job_group);

        create index if not exists idx_qrtz_j_req_recovery
            on qrtz_job_details (sched_name, requests_recovery);

        create table if not exists qrtz_locks
        (
            sched_name varchar(120) not null,
            lock_name  varchar(40)  not null,
            primary key (sched_name, lock_name)
        );

        create table if not exists qrtz_paused_trigger_grps
        (
            sched_name    varchar(120) not null,
            trigger_group varchar(200) not null,
            primary key (sched_name, trigger_group)
        );

        create table if not exists qrtz_scheduler_state
        (
            sched_name        varchar(120) not null,
            instance_name     varchar(200) not null,
            last_checkin_time bigint       not null,
            checkin_interval  bigint       not null,
            primary key (sched_name, instance_name)
        );

        create table if not exists qrtz_triggers
        (
            sched_name     varchar(120) not null,
            trigger_name   varchar(200) not null,
            trigger_group  varchar(200) not null,
            job_name       varchar(200) not null,
            job_group      varchar(200) not null,
            description    varchar(250),
            next_fire_time bigint,
            prev_fire_time bigint,
            priority       integer,
            trigger_state  varchar(16)  not null,
            trigger_type   varchar(8)   not null,
            start_time     bigint       not null,
            end_time       bigint,
            calendar_name  varchar(200),
            misfire_instr  smallint,
            job_data       bytea,
            primary key (sched_name, trigger_name, trigger_group),
            foreign key (sched_name, job_name, job_group) references qrtz_job_details
        );

        create table if not exists qrtz_blob_triggers
        (
            sched_name    varchar(120) not null,
            trigger_name  varchar(200) not null,
            trigger_group varchar(200) not null,
            blob_data     bytea,
            primary key (sched_name, trigger_name, trigger_group),
            foreign key (sched_name, trigger_name, trigger_group) references qrtz_triggers
        );

        create table if not exists qrtz_cron_triggers
        (
            sched_name      varchar(120) not null,
            trigger_name    varchar(200) not null,
            trigger_group   varchar(200) not null,
            cron_expression varchar(120) not null,
            time_zone_id    varchar(80),
            primary key (sched_name, trigger_name, trigger_group),
            foreign key (sched_name, trigger_name, trigger_group) references qrtz_triggers
        );

        create table if not exists qrtz_simple_triggers
        (
            sched_name      varchar(120) not null,
            trigger_name    varchar(200) not null,
            trigger_group   varchar(200) not null,
            repeat_count    bigint       not null,
            repeat_interval bigint       not null,
            times_triggered bigint       not null,
            primary key (sched_name, trigger_name, trigger_group),
            foreign key (sched_name, trigger_name, trigger_group) references qrtz_triggers
        );

        create table if not exists qrtz_simprop_triggers
        (
            sched_name    varchar(120) not null,
            trigger_name  varchar(200) not null,
            trigger_group varchar(200) not null,
            str_prop_1    varchar(512),
            str_prop_2    varchar(512),
            str_prop_3    varchar(512),
            int_prop_1    integer,
            int_prop_2    integer,
            long_prop_1   bigint,
            long_prop_2   bigint,
            dec_prop_1    numeric(13, 4),
            dec_prop_2    numeric(13, 4),
            bool_prop_1   boolean,
            bool_prop_2   boolean,
            primary key (sched_name, trigger_name, trigger_group),
            constraint qrtz_simprop_triggers_sched_name_trigger_name_trigger_grou_fkey
                foreign key (sched_name, trigger_name, trigger_group) references qrtz_triggers
        );

        create index if not exists idx_qrtz_t_c
            on qrtz_triggers (sched_name, calendar_name);

        create index if not exists idx_qrtz_t_g
            on qrtz_triggers (sched_name, trigger_group);

        create index if not exists idx_qrtz_t_j
            on qrtz_triggers (sched_name, job_name, job_group);

        create index if not exists idx_qrtz_t_jg
            on qrtz_triggers (sched_name, job_group);

        create index if not exists idx_qrtz_t_n_g_state
            on qrtz_triggers (sched_name, trigger_group, trigger_state);

        create index if not exists idx_qrtz_t_n_state
            on qrtz_triggers (sched_name, trigger_name, trigger_group, trigger_state);

        create index if not exists idx_qrtz_t_next_fire_time
            on qrtz_triggers (sched_name, next_fire_time);

        create index if not exists idx_qrtz_t_nft_misfire
            on qrtz_triggers (sched_name, misfire_instr, next_fire_time);

        create index if not exists idx_qrtz_t_nft_st
            on qrtz_triggers (sched_name, trigger_state, next_fire_time);

        create index if not exists idx_qrtz_t_nft_st_misfire
            on qrtz_triggers (sched_name, misfire_instr, next_fire_time, trigger_state);

        create index if not exists idx_qrtz_t_nft_st_misfire_grp
            on qrtz_triggers (sched_name, misfire_instr, next_fire_time, trigger_group, trigger_state);

        create index if not exists idx_qrtz_t_state
            on qrtz_triggers (sched_name, trigger_state);

        create table if not exists rep_demo_dxtj
        (
            id        varchar(32) not null
                primary key,
            name      varchar(50),
            gtime     timestamp,
            update_by varchar(50),
            jphone    varchar(125),
            birth     timestamp,
            hukou     varchar(32),
            laddress  varchar(125),
            jperson   varchar(32),
            sex       varchar(32)
        );

        comment on column rep_demo_dxtj.id is '主键';

        comment on column rep_demo_dxtj.name is '姓名';

        comment on column rep_demo_dxtj.gtime is '雇佣日期';

        comment on column rep_demo_dxtj.update_by is '职务';

        comment on column rep_demo_dxtj.jphone is '家庭电话';

        comment on column rep_demo_dxtj.birth is '出生日期';

        comment on column rep_demo_dxtj.hukou is '户口所在地';

        comment on column rep_demo_dxtj.laddress is '联系地址';

        comment on column rep_demo_dxtj.jperson is '紧急联系人';

        comment on column rep_demo_dxtj.sex is 'xingbie';

        create table if not exists rep_demo_employee
        (
            id                     varchar(10) not null
                primary key,
            num                    varchar(50),
            name                   varchar(100),
            sex                    varchar(10),
            birthday               timestamp,
            nation                 varchar(30),
            political              varchar(30),
            native_place           varchar(30),
            height                 varchar(30),
            weight                 varchar(30),
            health                 varchar(30),
            id_card                varchar(80),
            education              varchar(30),
            school                 varchar(80),
            major                  varchar(80),
            address                varchar(100),
            zip_code               varchar(30),
            email                  varchar(30),
            phone                  varchar(30),
            foreign_language       varchar(30),
            foreign_language_level varchar(30),
            computer_level         varchar(30),
            graduation_time        timestamp,
            arrival_time           timestamp,
            positional_titles      varchar(30),
            education_experience   text,
            work_experience        text,
            create_by              varchar(32),
            create_time            timestamp,
            update_by              varchar(32),
            update_time            timestamp,
            del_flag               smallint
        );

        comment on column rep_demo_employee.id is '主键';

        comment on column rep_demo_employee.num is '编号';

        comment on column rep_demo_employee.name is '姓名';

        comment on column rep_demo_employee.sex is '性别';

        comment on column rep_demo_employee.birthday is '出生日期';

        comment on column rep_demo_employee.nation is '民族';

        comment on column rep_demo_employee.political is '政治面貌';

        comment on column rep_demo_employee.native_place is '籍贯';

        comment on column rep_demo_employee.height is '身高';

        comment on column rep_demo_employee.weight is '体重';

        comment on column rep_demo_employee.health is '健康状况';

        comment on column rep_demo_employee.id_card is '身份证号';

        comment on column rep_demo_employee.education is '学历';

        comment on column rep_demo_employee.school is '毕业学校';

        comment on column rep_demo_employee.major is '专业';

        comment on column rep_demo_employee.address is '联系地址';

        comment on column rep_demo_employee.zip_code is '邮编';

        comment on column rep_demo_employee.email is 'Email';

        comment on column rep_demo_employee.phone is '手机号';

        comment on column rep_demo_employee.foreign_language is '外语语种';

        comment on column rep_demo_employee.foreign_language_level is '外语水平';

        comment on column rep_demo_employee.computer_level is '计算机水平';

        comment on column rep_demo_employee.graduation_time is '毕业时间';

        comment on column rep_demo_employee.arrival_time is '到职时间';

        comment on column rep_demo_employee.positional_titles is '职称';

        comment on column rep_demo_employee.education_experience is '教育经历';

        comment on column rep_demo_employee.work_experience is '工作经历';

        comment on column rep_demo_employee.create_by is '创建人';

        comment on column rep_demo_employee.create_time is '创建时间';

        comment on column rep_demo_employee.update_by is '修改人';

        comment on column rep_demo_employee.update_time is '修改时间';

        comment on column rep_demo_employee.del_flag is '删除标识0-正常,1-已删除';

        create table if not exists rep_demo_gongsi
        (
            id      integer      not null
                primary key,
            gname   varchar(125) not null,
            gdata   varchar(255) not null,
            tdata   varchar(125) not null,
            didian  varchar(255) not null,
            zhaiyao varchar(255) not null,
            num     varchar(255) not null
        );

        comment on column rep_demo_gongsi.gname is '货品名称';

        comment on column rep_demo_gongsi.gdata is '返利';

        comment on column rep_demo_gongsi.tdata is '备注';

        create table if not exists rep_demo_jianpiao
        (
            id       integer      not null
                primary key,
            bnum     varchar(125) not null,
            ftime    varchar(125) not null,
            sfkong   varchar(125) not null,
            kaishi   varchar(125) not null,
            jieshu   varchar(125) not null,
            hezairen varchar(125) not null,
            jpnum    varchar(125) not null,
            shihelv  varchar(125) not null,
            s_id     integer      not null
        );

        create table if not exists sys_announcement
        (
            id           varchar(32) not null
                primary key,
            titile       varchar(100),
            msg_content  text,
            start_time   timestamp,
            end_time     timestamp,
            sender       varchar(100),
            priority     varchar(255),
            msg_category varchar(10) not null,
            msg_type     varchar(10),
            send_status  varchar(10),
            send_time    timestamp,
            cancel_time  timestamp,
            del_flag     varchar(1),
            bus_type     varchar(20),
            bus_id       varchar(50),
            open_type    varchar(20),
            open_page    varchar(255),
            create_by    varchar(32),
            create_time  timestamp,
            update_by    varchar(32),
            update_time  timestamp,
            user_ids     text,
            msg_abstract text,
            dt_task_id   varchar(100)
        );

        comment on table sys_announcement is '系统通告表';

        comment on column sys_announcement.titile is '标题';

        comment on column sys_announcement.msg_content is '内容';

        comment on column sys_announcement.start_time is '开始时间';

        comment on column sys_announcement.end_time is '结束时间';

        comment on column sys_announcement.sender is '发布人';

        comment on column sys_announcement.priority is '优先级（L低，M中，H高）';

        comment on column sys_announcement.msg_category is '消息类型1:通知公告2:系统消息';

        comment on column sys_announcement.msg_type is '通告对象类型（USER:指定用户，ALL:全体用户）';

        comment on column sys_announcement.send_status is '发布状态（0未发布，1已发布，2已撤销）';

        comment on column sys_announcement.send_time is '发布时间';

        comment on column sys_announcement.cancel_time is '撤销时间';

        comment on column sys_announcement.del_flag is '删除状态（0，正常，1已删除）';

        comment on column sys_announcement.bus_type is '业务类型(email:邮件 bpm:流程)';

        comment on column sys_announcement.bus_id is '业务id';

        comment on column sys_announcement.open_type is '打开方式(组件：component 路由：url)';

        comment on column sys_announcement.open_page is '组件/路由 地址';

        comment on column sys_announcement.create_by is '创建人';

        comment on column sys_announcement.create_time is '创建时间';

        comment on column sys_announcement.update_by is '更新人';

        comment on column sys_announcement.update_time is '更新时间';

        comment on column sys_announcement.user_ids is '指定用户';

        comment on column sys_announcement.msg_abstract is '摘要';

        comment on column sys_announcement.dt_task_id is '钉钉task_id，用于撤回消息';

        create table if not exists sys_announcement_send
        (
            id          varchar(32),
            annt_id     varchar(32),
            user_id     varchar(32),
            read_flag   varchar(10),
            read_time   timestamp,
            create_by   varchar(32),
            create_time timestamp,
            update_by   varchar(32),
            update_time timestamp,
            star_flag   varchar(10)
        );

        comment on table sys_announcement_send is '用户通告阅读标记表';

        comment on column sys_announcement_send.annt_id is '通告ID';

        comment on column sys_announcement_send.user_id is '用户id';

        comment on column sys_announcement_send.read_flag is '阅读状态（0未读，1已读）';

        comment on column sys_announcement_send.read_time is '阅读时间';

        comment on column sys_announcement_send.create_by is '创建人';

        comment on column sys_announcement_send.create_time is '创建时间';

        comment on column sys_announcement_send.update_by is '更新人';

        comment on column sys_announcement_send.update_time is '更新时间';

        comment on column sys_announcement_send.star_flag is '标星状态( 1为标星 空/0没有标星)';

        create table if not exists sys_category
        (
            id           varchar(36) not null
                primary key,
            pid          varchar(36),
            name         varchar(100),
            code         varchar(100),
            create_by    varchar(50),
            create_time  timestamp,
            update_by    varchar(50),
            update_time  timestamp,
            sys_org_code varchar(64),
            has_child    varchar(3)
        );

        comment on column sys_category.pid is '父级节点';

        comment on column sys_category.name is '类型名称';

        comment on column sys_category.code is '类型编码';

        comment on column sys_category.create_by is '创建人';

        comment on column sys_category.create_time is '创建日期';

        comment on column sys_category.update_by is '更新人';

        comment on column sys_category.update_time is '更新日期';

        comment on column sys_category.sys_org_code is '所属部门';

        comment on column sys_category.has_child is '是否有子节点';

        create index if not exists idx_sc_code
            on sys_category (code);

        create index if not exists index_code
            on sys_category (code);

        create table if not exists sys_check_rule
        (
            id               varchar(32) not null
                primary key,
            rule_name        varchar(100),
            rule_code        varchar(100),
            rule_json        varchar(1024),
            rule_description varchar(200),
            update_by        varchar(32),
            update_time      timestamp,
            create_by        varchar(32),
            create_time      timestamp
        );

        comment on column sys_check_rule.id is '主键id';

        comment on column sys_check_rule.rule_name is '规则名称';

        comment on column sys_check_rule.rule_code is '规则Code';

        comment on column sys_check_rule.rule_json is '规则JSON';

        comment on column sys_check_rule.rule_description is '规则描述';

        comment on column sys_check_rule.update_by is '更新人';

        comment on column sys_check_rule.update_time is '更新时间';

        comment on column sys_check_rule.create_by is '创建人';

        comment on column sys_check_rule.create_time is '创建时间';

        create index if not exists uk_scr_rule_code
            on sys_check_rule (rule_code);

        create table if not exists sys_comment
        (
            id              varchar(32) not null
                primary key,
            table_name      varchar(50) not null,
            table_data_id   varchar(32) not null,
            from_user_id    varchar(32) not null,
            to_user_id      varchar(32),
            comment_id      varchar(32),
            comment_content varchar(255),
            create_by       varchar(50),
            create_time     timestamp,
            update_by       varchar(50),
            update_time     timestamp
        );

        comment on table sys_comment is '系统评论回复表';

        comment on column sys_comment.table_name is '表名';

        comment on column sys_comment.table_data_id is '数据id';

        comment on column sys_comment.from_user_id is '来源用户id';

        comment on column sys_comment.to_user_id is '发送给用户id(允许为空)';

        comment on column sys_comment.comment_id is '评论id(允许为空，不为空时，则为回复)';

        comment on column sys_comment.comment_content is '回复内容';

        comment on column sys_comment.create_by is '创建人';

        comment on column sys_comment.create_time is '创建日期';

        comment on column sys_comment.update_by is '更新人';

        comment on column sys_comment.update_time is '更新日期';

        create index if not exists idx_table_data_id
            on sys_comment (table_name, table_data_id);

        create table if not exists sys_data_log
        (
            id           varchar(32) not null
                primary key,
            create_by    varchar(32),
            create_time  timestamp,
            update_by    varchar(32),
            update_time  timestamp,
            data_table   varchar(32),
            data_id      varchar(32),
            data_content text,
            data_version integer,
            type         varchar(20)
        );

        comment on column sys_data_log.id is 'id';

        comment on column sys_data_log.create_by is '创建人登录名称';

        comment on column sys_data_log.create_time is '创建日期';

        comment on column sys_data_log.update_by is '更新人登录名称';

        comment on column sys_data_log.update_time is '更新日期';

        comment on column sys_data_log.data_table is '表名';

        comment on column sys_data_log.data_id is '数据ID';

        comment on column sys_data_log.data_content is '数据内容';

        comment on column sys_data_log.data_version is '版本号';

        comment on column sys_data_log.type is '类型';

        create index if not exists idx_sdl_data_table_id
            on sys_data_log (data_table, data_id);

        create table if not exists sys_data_source
        (
            id           varchar(36) not null
                primary key,
            code         varchar(100),
            name         varchar(100),
            remark       varchar(200),
            db_type      varchar(10),
            db_driver    varchar(100),
            db_url       varchar(500),
            db_name      varchar(100),
            db_username  varchar(100),
            db_password  varchar(100),
            create_by    varchar(50),
            create_time  timestamp,
            update_by    varchar(50),
            update_time  timestamp,
            sys_org_code varchar(64)
        );

        comment on column sys_data_source.code is '数据源编码';

        comment on column sys_data_source.name is '数据源名称';

        comment on column sys_data_source.remark is '备注';

        comment on column sys_data_source.db_type is '数据库类型';

        comment on column sys_data_source.db_driver is '驱动类';

        comment on column sys_data_source.db_url is '数据源地址';

        comment on column sys_data_source.db_name is '数据库名称';

        comment on column sys_data_source.db_username is '用户名';

        comment on column sys_data_source.db_password is '密码';

        comment on column sys_data_source.create_by is '创建人';

        comment on column sys_data_source.create_time is '创建日期';

        comment on column sys_data_source.update_by is '更新人';

        comment on column sys_data_source.update_time is '更新日期';

        comment on column sys_data_source.sys_org_code is '所属部门';

        create index if not exists uk_sdc_rule_code
            on sys_data_source (code);

        create table if not exists sys_depart
        (
            id               varchar(32)  not null
                primary key,
            parent_id        varchar(32),
            depart_name      varchar(100) not null,
            depart_name_en   varchar(500),
            depart_name_abbr varchar(500),
            depart_order     integer,
            description      varchar(500),
            org_category     varchar(10)  not null,
            org_type         varchar(10),
            org_code         varchar(64)  not null,
            mobile           varchar(32),
            fax              varchar(32),
            address          varchar(100),
            memo             varchar(500),
            status           varchar(1),
            del_flag         varchar(1),
            qywx_identifier  varchar(100),
            create_by        varchar(32),
            create_time      timestamp,
            update_by        varchar(32),
            update_time      timestamp
        );

        comment on table sys_depart is '组织机构表';

        comment on column sys_depart.id is 'ID';

        comment on column sys_depart.parent_id is '父机构ID';

        comment on column sys_depart.depart_name is '机构/部门名称';

        comment on column sys_depart.depart_name_en is '英文名';

        comment on column sys_depart.depart_name_abbr is '缩写';

        comment on column sys_depart.depart_order is '排序';

        comment on column sys_depart.description is '描述';

        comment on column sys_depart.org_category is '机构类别 1公司，2组织机构，2岗位';

        comment on column sys_depart.org_type is '机构类型 1一级部门 2子部门';

        comment on column sys_depart.org_code is '机构编码';

        comment on column sys_depart.mobile is '手机号';

        comment on column sys_depart.fax is '传真';

        comment on column sys_depart.address is '地址';

        comment on column sys_depart.memo is '备注';

        comment on column sys_depart.status is '状态（1启用，0不启用）';

        comment on column sys_depart.del_flag is '删除状态（0，正常，1已删除）';

        comment on column sys_depart.qywx_identifier is '对接企业微信的ID';

        comment on column sys_depart.create_by is '创建人';

        comment on column sys_depart.create_time is '创建日期';

        comment on column sys_depart.update_by is '更新人';

        comment on column sys_depart.update_time is '更新日期';

        create index if not exists idx_sd_depart_order
            on sys_depart (depart_order);

        create index if not exists idx_sd_org_code
            on sys_depart (org_code);

        create index if not exists idx_sd_parent_id
            on sys_depart (parent_id);

        create index if not exists uniq_depart_org_code
            on sys_depart (org_code);

        create table if not exists sys_depart_permission
        (
            id            varchar(32) not null
                primary key,
            depart_id     varchar(32),
            permission_id varchar(32),
            data_rule_ids varchar(1000)
        );

        comment on table sys_depart_permission is '部门权限表';

        comment on column sys_depart_permission.depart_id is '部门id';

        comment on column sys_depart_permission.permission_id is '权限id';

        comment on column sys_depart_permission.data_rule_ids is '数据规则id';

        create table if not exists sys_depart_role
        (
            id          varchar(32) not null
                primary key,
            depart_id   varchar(32),
            role_name   varchar(200),
            role_code   varchar(100),
            description varchar(255),
            create_by   varchar(32),
            create_time timestamp,
            update_by   varchar(32),
            update_time timestamp
        );

        comment on table sys_depart_role is '部门角色表';

        comment on column sys_depart_role.depart_id is '部门id';

        comment on column sys_depart_role.role_name is '部门角色名称';

        comment on column sys_depart_role.role_code is '部门角色编码';

        comment on column sys_depart_role.description is '描述';

        comment on column sys_depart_role.create_by is '创建人';

        comment on column sys_depart_role.create_time is '创建时间';

        comment on column sys_depart_role.update_by is '更新人';

        comment on column sys_depart_role.update_time is '更新时间';

        create table if not exists sys_depart_role_permission
        (
            id            varchar(32) not null
                primary key,
            depart_id     varchar(32),
            role_id       varchar(32),
            permission_id varchar(32),
            data_rule_ids varchar(1000),
            operate_date  timestamp,
            operate_ip    varchar(20)
        );

        comment on table sys_depart_role_permission is '部门角色权限表';

        comment on column sys_depart_role_permission.depart_id is '部门id';

        comment on column sys_depart_role_permission.role_id is '角色id';

        comment on column sys_depart_role_permission.permission_id is '权限id';

        comment on column sys_depart_role_permission.data_rule_ids is '数据权限ids';

        comment on column sys_depart_role_permission.operate_date is '操作时间';

        comment on column sys_depart_role_permission.operate_ip is '操作ip';

        create index if not exists idx_sdrp_per_id
            on sys_depart_role_permission (permission_id);

        create index if not exists idx_sdrp_role_id
            on sys_depart_role_permission (role_id);

        create index if not exists idx_sdrp_role_per_id
            on sys_depart_role_permission (role_id, permission_id);

        create table if not exists sys_depart_role_user
        (
            id       varchar(32) not null
                primary key,
            user_id  varchar(32),
            drole_id varchar(32)
        );

        comment on table sys_depart_role_user is '部门角色用户表';

        comment on column sys_depart_role_user.id is '主键id';

        comment on column sys_depart_role_user.user_id is '用户id';

        comment on column sys_depart_role_user.drole_id is '角色id';

        create table if not exists sys_dict
        (
            id          varchar(32)  not null
                primary key,
            dict_name   varchar(100) not null,
            dict_code   varchar(100) not null,
            description varchar(255),
            del_flag    integer,
            create_by   varchar(32),
            create_time timestamp,
            update_by   varchar(32),
            update_time timestamp,
            type        integer
        );

        comment on column sys_dict.dict_name is '字典名称';

        comment on column sys_dict.dict_code is '字典编码';

        comment on column sys_dict.description is '描述';

        comment on column sys_dict.del_flag is '删除状态';

        comment on column sys_dict.create_by is '创建人';

        comment on column sys_dict.create_time is '创建时间';

        comment on column sys_dict.update_by is '更新人';

        comment on column sys_dict.update_time is '更新时间';

        comment on column sys_dict.type is '字典类型0为string,1为number';

        create table if not exists sys_dict_item
        (
            id          varchar(32)  not null
                primary key,
            dict_id     varchar(32),
            item_text   varchar(100) not null,
            item_value  varchar(100) not null,
            description varchar(255),
            sort_order  integer,
            status      integer,
            create_by   varchar(32),
            create_time timestamp,
            update_by   varchar(32),
            update_time timestamp
        );

        comment on column sys_dict_item.dict_id is '字典id';

        comment on column sys_dict_item.item_text is '字典项文本';

        comment on column sys_dict_item.item_value is '字典项值';

        comment on column sys_dict_item.description is '描述';

        comment on column sys_dict_item.sort_order is '排序';

        comment on column sys_dict_item.status is '状态（1启用 0不启用）';

        create table if not exists sys_files
        (
            id             varchar(32) not null
                primary key,
            file_name      varchar(255),
            url            varchar(1000),
            file_type      varchar(20),
            store_type     varchar(20),
            parent_id      varchar(32),
            tenant_id      varchar(100),
            file_size      double precision,
            iz_folder      varchar(2),
            iz_root_folder varchar(2),
            iz_star        varchar(2),
            down_count     integer,
            read_count     integer,
            share_url      varchar(255),
            share_perms    varchar(2),
            enable_down    varchar(2),
            enable_updat   varchar(2),
            del_flag       varchar(2),
            create_by      varchar(32),
            create_time    timestamp,
            update_by      varchar(32),
            update_time    timestamp
        );

        comment on table sys_files is '知识库-文档管理';

        comment on column sys_files.id is '主键id';

        comment on column sys_files.file_name is '文件名称';

        comment on column sys_files.url is '文件地址';

        comment on column sys_files.file_type is '文档类型（folder:文件夹 excel:excel doc:word ppt:ppt image:图片  archive:其他文档 video:视频 pdf:pdf）';

        comment on column sys_files.store_type is '文件上传类型(temp/本地上传(临时文件) manage/知识库)';

        comment on column sys_files.parent_id is '父级id';

        comment on column sys_files.tenant_id is '租户id';

        comment on column sys_files.file_size is '文件大小（kb）';

        comment on column sys_files.iz_folder is '是否文件夹(1：是  0：否)';

        comment on column sys_files.iz_root_folder is '是否为1级文件夹，允许为空 (1：是 )';

        comment on column sys_files.iz_star is '是否标星(1：是  0：否)';

        comment on column sys_files.down_count is '下载次数';

        comment on column sys_files.read_count is '阅读次数';

        comment on column sys_files.share_url is '分享链接';

        comment on column sys_files.share_perms is '分享权限(1.关闭分享 2.允许所有联系人查看 3.允许任何人查看)';

        comment on column sys_files.enable_down is '是否允许下载(1：是  0：否)';

        comment on column sys_files.enable_updat is '是否允许修改(1：是  0：否)';

        comment on column sys_files.del_flag is '删除状态(0-正常,1-删除至回收站)';

        comment on column sys_files.create_by is '创建人登录名称';

        comment on column sys_files.create_time is '创建日期';

        comment on column sys_files.update_by is '更新人登录名称';

        comment on column sys_files.update_time is '更新日期';

        create index if not exists index_del_flag
            on sys_files (del_flag);

        create index if not exists index_tenant_id
            on sys_files (tenant_id);

        create table if not exists sys_fill_rule
        (
            id          varchar(32) not null
                primary key,
            rule_name   varchar(100),
            rule_code   varchar(100),
            rule_class  varchar(100),
            rule_params varchar(200),
            update_by   varchar(32),
            update_time timestamp,
            create_by   varchar(32),
            create_time timestamp
        );

        comment on column sys_fill_rule.id is '主键ID';

        comment on column sys_fill_rule.rule_name is '规则名称';

        comment on column sys_fill_rule.rule_code is '规则Code';

        comment on column sys_fill_rule.rule_class is '规则实现类';

        comment on column sys_fill_rule.rule_params is '规则参数';

        comment on column sys_fill_rule.update_by is '修改人';

        comment on column sys_fill_rule.update_time is '修改时间';

        comment on column sys_fill_rule.create_by is '创建人';

        comment on column sys_fill_rule.create_time is '创建时间';

        create index if not exists uk_sfr_rule_code
            on sys_fill_rule (rule_code);

        create table if not exists sys_form_file
        (
            id            varchar(32) not null
                primary key,
            table_name    varchar(50) not null,
            table_data_id varchar(32) not null,
            file_id       varchar(32),
            file_type     varchar(20),
            create_by     varchar(32),
            create_time   timestamp
        );

        comment on column sys_form_file.table_name is '表名';

        comment on column sys_form_file.table_data_id is '数据id';

        comment on column sys_form_file.file_id is '关联文件id';

        comment on column sys_form_file.file_type is '文件类型(text:文本, excel:excel doc:word ppt:ppt image:图片  archive:其他文档 video:视频 pdf:pdf）)';

        comment on column sys_form_file.create_by is '创建人登录名称';

        comment on column sys_form_file.create_time is '创建日期';

        create index if not exists idx_table_form
            on sys_form_file (table_name, table_data_id);

        create index if not exists index_file_id
            on sys_form_file (file_id);

        create table if not exists sys_gateway_route
        (
            id           varchar(36) not null
                primary key,
            router_id    varchar(50),
            name         varchar(32),
            uri          varchar(32),
            predicates   text,
            filters      text,
            retryable    integer,
            strip_prefix integer,
            persistable  integer,
            show_api     integer,
            status       integer,
            create_by    varchar(50),
            create_time  timestamp,
            update_by    varchar(50),
            update_time  timestamp,
            sys_org_code varchar(64)
        );

        comment on column sys_gateway_route.router_id is '路由ID';

        comment on column sys_gateway_route.name is '服务名';

        comment on column sys_gateway_route.uri is '服务地址';

        comment on column sys_gateway_route.predicates is '断言';

        comment on column sys_gateway_route.filters is '过滤器';

        comment on column sys_gateway_route.retryable is '是否重试:0-否 1-是';

        comment on column sys_gateway_route.strip_prefix is '是否忽略前缀0-否 1-是';

        comment on column sys_gateway_route.persistable is '是否为保留数据:0-否 1-是';

        comment on column sys_gateway_route.show_api is '是否在接口文档中展示:0-否 1-是';

        comment on column sys_gateway_route.status is '状态:0-无效 1-有效';

        comment on column sys_gateway_route.create_by is '创建人';

        comment on column sys_gateway_route.create_time is '创建日期';

        comment on column sys_gateway_route.update_by is '更新人';

        comment on column sys_gateway_route.update_time is '更新日期';

        comment on column sys_gateway_route.sys_org_code is '所属部门';

        create table if not exists sys_log
        (
            id            varchar(32) not null
                primary key,
            log_type      integer,
            log_content   varchar(1000),
            operate_type  integer,
            userid        varchar(32),
            username      varchar(100),
            ip            varchar(100),
            method        varchar(500),
            request_url   varchar(255),
            request_param text,
            request_type  varchar(10),
            cost_time     bigint,
            create_by     varchar(32),
            create_time   timestamp,
            update_by     varchar(32),
            update_time   timestamp
        );

        comment on table sys_log is '系统日志表';

        comment on column sys_log.log_type is '日志类型（1登录日志，2操作日志）';

        comment on column sys_log.log_content is '日志内容';

        comment on column sys_log.operate_type is '操作类型';

        comment on column sys_log.userid is '操作用户账号';

        comment on column sys_log.username is '操作用户名称';

        comment on column sys_log.ip is 'IP';

        comment on column sys_log.method is '请求java方法';

        comment on column sys_log.request_url is '请求路径';

        comment on column sys_log.request_param is '请求参数';

        comment on column sys_log.request_type is '请求类型';

        comment on column sys_log.cost_time is '耗时';

        comment on column sys_log.create_by is '创建人';

        comment on column sys_log.create_time is '创建时间';

        comment on column sys_log.update_by is '更新人';

        comment on column sys_log.update_time is '更新时间';

        create index if not exists idx_sl_create_time
            on sys_log (create_time);

        create index if not exists idx_sl_log_type
            on sys_log (log_type);

        create index if not exists idx_sl_operate_type
            on sys_log (operate_type);

        create index if not exists idx_sl_userid
            on sys_log (userid);

        create table if not exists sys_permission
        (
            id                   varchar(32) not null
                primary key,
            parent_id            varchar(32),
            name                 varchar(100),
            url                  varchar(255),
            component            varchar(255),
            component_name       varchar(100),
            redirect             varchar(255),
            menu_type            integer,
            perms                varchar(255),
            perms_type           varchar(10),
            sort_no              double precision,
            always_show          smallint,
            icon                 varchar(100),
            is_route             smallint,
            is_leaf              smallint,
            keep_alive           smallint,
            hidden               smallint,
            hide_tab             smallint,
            description          varchar(255),
            create_by            varchar(32),
            create_time          timestamp,
            update_by            varchar(32),
            update_time          timestamp,
            del_flag             integer,
            rule_flag            integer,
            status               varchar(2),
            internal_or_external smallint
        );

        comment on table sys_permission is '菜单权限表';

        comment on column sys_permission.id is '主键id';

        comment on column sys_permission.parent_id is '父id';

        comment on column sys_permission.name is '菜单标题';

        comment on column sys_permission.url is '路径';

        comment on column sys_permission.component is '组件';

        comment on column sys_permission.component_name is '组件名字';

        comment on column sys_permission.redirect is '一级菜单跳转地址';

        comment on column sys_permission.menu_type is '菜单类型(0:一级菜单; 1:子菜单:2:按钮权限)';

        comment on column sys_permission.perms is '菜单权限编码';

        comment on column sys_permission.perms_type is '权限策略1显示2禁用';

        comment on column sys_permission.sort_no is '菜单排序';

        comment on column sys_permission.always_show is '聚合子路由: 1是0否';

        comment on column sys_permission.icon is '菜单图标';

        comment on column sys_permission.is_route is '是否路由菜单: 0:不是  1:是（默认值1）';

        comment on column sys_permission.is_leaf is '是否叶子节点:    1:是   0:不是';

        comment on column sys_permission.keep_alive is '是否缓存该页面:    1:是   0:不是';

        comment on column sys_permission.hidden is '是否隐藏路由: 0否,1是';

        comment on column sys_permission.hide_tab is '是否隐藏tab: 0否,1是';

        comment on column sys_permission.description is '描述';

        comment on column sys_permission.create_by is '创建人';

        comment on column sys_permission.create_time is '创建时间';

        comment on column sys_permission.update_by is '更新人';

        comment on column sys_permission.update_time is '更新时间';

        comment on column sys_permission.del_flag is '删除状态 0正常 1已删除';

        comment on column sys_permission.rule_flag is '是否添加数据权限1是0否';

        comment on column sys_permission.status is '按钮权限状态(0无效1有效)';

        comment on column sys_permission.internal_or_external is '外链菜单打开方式 0/内部打开 1/外部打开';

        create index if not exists idx_sp_del_flag
            on sys_permission (del_flag);

        create index if not exists idx_sp_hidden
            on sys_permission (hidden);

        create index if not exists idx_sp_is_leaf
            on sys_permission (is_leaf);

        create index if not exists idx_sp_is_route
            on sys_permission (is_route);

        create index if not exists idx_sp_menu_type
            on sys_permission (menu_type);

        create index if not exists idx_sp_parent_id
            on sys_permission (parent_id);

        create index if not exists idx_sp_sort_no
            on sys_permission (sort_no);

        create index if not exists idx_sp_status
            on sys_permission (status);

        create table if not exists sys_permission_data_rule
        (
            id              varchar(32) not null
                primary key,
            permission_id   varchar(32),
            rule_name       varchar(50),
            rule_column     varchar(50),
            rule_conditions varchar(50),
            rule_value      varchar(300),
            status          varchar(3),
            create_time     timestamp,
            create_by       varchar(32),
            update_time     timestamp,
            update_by       varchar(32)
        );

        comment on column sys_permission_data_rule.id is 'ID';

        comment on column sys_permission_data_rule.permission_id is '菜单ID';

        comment on column sys_permission_data_rule.rule_name is '规则名称';

        comment on column sys_permission_data_rule.rule_column is '字段';

        comment on column sys_permission_data_rule.rule_conditions is '条件';

        comment on column sys_permission_data_rule.rule_value is '规则值';

        comment on column sys_permission_data_rule.status is '权限有效状态1有0否';

        comment on column sys_permission_data_rule.create_time is '创建时间';

        comment on column sys_permission_data_rule.update_time is '修改时间';

        comment on column sys_permission_data_rule.update_by is '修改人';

        create index if not exists idx_spdr_permission_id
            on sys_permission_data_rule (permission_id);

        create table if not exists sys_permission_v3
        (
            id                   varchar(32) not null
                primary key,
            parent_id            varchar(32),
            name                 varchar(255),
            url                  varchar(255),
            component            varchar(255),
            is_route             smallint,
            component_name       varchar(255),
            redirect             varchar(255),
            menu_type            integer,
            perms                varchar(255),
            perms_type           varchar(10),
            sort_no              double precision,
            always_show          smallint,
            icon                 varchar(255),
            is_leaf              smallint,
            keep_alive           smallint,
            hidden               integer,
            hide_tab             integer,
            description          varchar(255),
            create_by            varchar(255),
            create_time          timestamp,
            update_by            varchar(255),
            update_time          timestamp,
            del_flag             integer,
            rule_flag            integer,
            status               varchar(2),
            internal_or_external smallint
        );

        comment on table sys_permission_v3 is '菜单权限表';

        comment on column sys_permission_v3.id is '主键id';

        comment on column sys_permission_v3.parent_id is '父id';

        comment on column sys_permission_v3.name is '菜单标题';

        comment on column sys_permission_v3.url is '路径';

        comment on column sys_permission_v3.component is '组件';

        comment on column sys_permission_v3.is_route is '是否路由菜单: 0:不是  1:是（默认值1）';

        comment on column sys_permission_v3.component_name is '组件名字';

        comment on column sys_permission_v3.redirect is '一级菜单跳转地址';

        comment on column sys_permission_v3.menu_type is '菜单类型(0:一级菜单; 1:子菜单:2:按钮权限)';

        comment on column sys_permission_v3.perms is '菜单权限编码';

        comment on column sys_permission_v3.perms_type is '权限策略1显示2禁用';

        comment on column sys_permission_v3.sort_no is '菜单排序';

        comment on column sys_permission_v3.always_show is '聚合子路由: 1是0否';

        comment on column sys_permission_v3.icon is '菜单图标';

        comment on column sys_permission_v3.is_leaf is '是否叶子节点:    1是0否';

        comment on column sys_permission_v3.keep_alive is '是否缓存该页面:    1:是   0:不是';

        comment on column sys_permission_v3.hidden is '是否隐藏路由: 0否,1是';

        comment on column sys_permission_v3.hide_tab is '是否隐藏tab: 0否,1是';

        comment on column sys_permission_v3.description is '描述';

        comment on column sys_permission_v3.create_by is '创建人';

        comment on column sys_permission_v3.create_time is '创建时间';

        comment on column sys_permission_v3.update_by is '更新人';

        comment on column sys_permission_v3.update_time is '更新时间';

        comment on column sys_permission_v3.del_flag is '删除状态 0正常 1已删除';

        comment on column sys_permission_v3.rule_flag is '是否添加数据权限1是0否';

        comment on column sys_permission_v3.status is '按钮权限状态(0无效1有效)';

        comment on column sys_permission_v3.internal_or_external is '外链菜单打开方式 0/内部打开 1/外部打开';

        create index if not exists index_menu_hidden
            on sys_permission_v3 (hidden);

        create index if not exists index_menu_status
            on sys_permission_v3 (status);

        create index if not exists index_menu_type
            on sys_permission_v3 (menu_type);

        create table if not exists sys_position
        (
            id           varchar(32) not null
                primary key,
            code         varchar(100),
            name         varchar(100),
            post_rank    varchar(2),
            company_id   varchar(255),
            create_by    varchar(50),
            create_time  timestamp,
            update_by    varchar(50),
            update_time  timestamp,
            sys_org_code varchar(50)
        );

        comment on column sys_position.code is '职务编码';

        comment on column sys_position.name is '职务名称';

        comment on column sys_position.post_rank is '职级';

        comment on column sys_position.company_id is '公司id';

        comment on column sys_position.create_by is '创建人';

        comment on column sys_position.create_time is '创建时间';

        comment on column sys_position.update_by is '修改人';

        comment on column sys_position.update_time is '修改时间';

        comment on column sys_position.sys_org_code is '组织机构编码';

        create index if not exists uniq_code
            on sys_position (code);

        create table if not exists sys_quartz_job
        (
            id              varchar(32) not null
                primary key,
            create_by       varchar(32),
            create_time     timestamp,
            del_flag        integer,
            update_by       varchar(32),
            update_time     timestamp,
            job_class_name  varchar(255),
            cron_expression varchar(255),
            parameter       varchar(255),
            description     varchar(255),
            status          integer
        );

        comment on column sys_quartz_job.create_by is '创建人';

        comment on column sys_quartz_job.create_time is '创建时间';

        comment on column sys_quartz_job.del_flag is '删除状态';

        comment on column sys_quartz_job.update_by is '修改人';

        comment on column sys_quartz_job.update_time is '修改时间';

        comment on column sys_quartz_job.job_class_name is '任务类名';

        comment on column sys_quartz_job.cron_expression is 'cron表达式';

        comment on column sys_quartz_job.parameter is '参数';

        comment on column sys_quartz_job.description is '描述';

        comment on column sys_quartz_job.status is '状态 0正常 -1停止';

        create table if not exists sys_role
        (
            id          varchar(32)  not null
                primary key,
            role_name   varchar(200),
            role_code   varchar(100) not null,
            description varchar(255),
            create_by   varchar(32),
            create_time timestamp,
            update_by   varchar(32),
            update_time timestamp
        );

        comment on table sys_role is '角色表';

        comment on column sys_role.id is '主键id';

        comment on column sys_role.role_name is '角色名称';

        comment on column sys_role.role_code is '角色编码';

        comment on column sys_role.description is '描述';

        comment on column sys_role.create_by is '创建人';

        comment on column sys_role.create_time is '创建时间';

        comment on column sys_role.update_by is '更新人';

        comment on column sys_role.update_time is '更新时间';

        create index if not exists idx_sr_role_code
            on sys_role (role_code);

        create index if not exists uniq_sys_role_role_code
            on sys_role (role_code);

        create table if not exists sys_role_design
        (
            id        varchar(32) not null
                primary key,
            role_id   varchar(32),
            design_id varchar(32)
        );

        comment on column sys_role_design.id is '主键';

        comment on column sys_role_design.role_id is '角色id';

        comment on column sys_role_design.design_id is '表单设计器id';

        create table if not exists sys_role_index
        (
            id           varchar(32) not null
                primary key,
            role_code    varchar(50),
            url          varchar(100),
            component    varchar(255),
            is_route     smallint,
            priority     integer,
            status       varchar(2),
            create_by    varchar(50),
            create_time  timestamp,
            update_by    varchar(50),
            update_time  timestamp,
            sys_org_code varchar(64)
        );

        comment on table sys_role_index is '角色首页表';

        comment on column sys_role_index.role_code is '角色编码';

        comment on column sys_role_index.url is '路由地址';

        comment on column sys_role_index.component is '组件';

        comment on column sys_role_index.is_route is '是否路由菜单: 0:不是  1:是（默认值1）';

        comment on column sys_role_index.priority is '优先级';

        comment on column sys_role_index.status is '状态0:无效 1:有效';

        comment on column sys_role_index.create_by is '创建人登录名称';

        comment on column sys_role_index.create_time is '创建日期';

        comment on column sys_role_index.update_by is '更新人登录名称';

        comment on column sys_role_index.update_time is '更新日期';

        comment on column sys_role_index.sys_org_code is '所属部门';

        create table if not exists sys_role_permission
        (
            id            varchar(32) not null
                primary key,
            role_id       varchar(32),
            permission_id varchar(32),
            data_rule_ids varchar(1000),
            operate_date  timestamp,
            operate_ip    varchar(100)
        );

        comment on table sys_role_permission is '角色权限表';

        comment on column sys_role_permission.role_id is '角色id';

        comment on column sys_role_permission.permission_id is '权限id';

        comment on column sys_role_permission.data_rule_ids is '数据权限ids';

        comment on column sys_role_permission.operate_date is '操作时间';

        comment on column sys_role_permission.operate_ip is '操作ip';

        create index if not exists idx_srp_permission_id
            on sys_role_permission (permission_id);

        create index if not exists idx_srp_role_id
            on sys_role_permission (role_id);

        create index if not exists idx_srp_role_per_id
            on sys_role_permission (role_id, permission_id);

        create table if not exists sys_sms
        (
            id             varchar(32) not null
                primary key,
            es_title       varchar(100),
            es_type        varchar(50),
            es_receiver    varchar(50),
            es_param       varchar(1000),
            es_content     text,
            es_send_time   timestamp,
            es_send_status varchar(1),
            es_send_num    integer,
            es_result      varchar(255),
            remark         varchar(500),
            create_by      varchar(32),
            create_time    timestamp,
            update_by      varchar(32),
            update_time    timestamp
        );

        comment on column sys_sms.id is 'ID';

        comment on column sys_sms.es_title is '消息标题';

        comment on column sys_sms.es_type is '发送方式：参考枚举MessageTypeEnum';

        comment on column sys_sms.es_receiver is '接收人';

        comment on column sys_sms.es_param is '发送所需参数Json格式';

        comment on column sys_sms.es_content is '推送内容';

        comment on column sys_sms.es_send_time is '推送时间';

        comment on column sys_sms.es_send_status is '推送状态 0未推送 1推送成功 2推送失败 -1失败不再发送';

        comment on column sys_sms.es_send_num is '发送次数 超过5次不再发送';

        comment on column sys_sms.es_result is '推送失败原因';

        comment on column sys_sms.remark is '备注';

        comment on column sys_sms.create_by is '创建人登录名称';

        comment on column sys_sms.create_time is '创建日期';

        comment on column sys_sms.update_by is '更新人登录名称';

        comment on column sys_sms.update_time is '更新日期';

        create index if not exists idx_ss_es_receiver
            on sys_sms (es_receiver);

        create index if not exists idx_ss_es_send_status
            on sys_sms (es_send_status);

        create index if not exists idx_ss_es_send_time
            on sys_sms (es_send_time);

        create index if not exists idx_ss_es_type
            on sys_sms (es_type);

        create table if not exists sys_sms_template
        (
            id                 varchar(32)   not null
                primary key,
            template_name      varchar(50),
            template_code      varchar(32)   not null,
            template_type      varchar(1)    not null,
            template_content   varchar(1000) not null,
            template_test_json varchar(1000),
            create_time        timestamp,
            create_by          varchar(32),
            update_time        timestamp,
            update_by          varchar(32),
            use_status         varchar(1)
        );

        comment on column sys_sms_template.id is '主键';

        comment on column sys_sms_template.template_name is '模板标题';

        comment on column sys_sms_template.template_code is '模板CODE';

        comment on column sys_sms_template.template_type is '模板类型：1短信 2邮件 3微信';

        comment on column sys_sms_template.template_content is '模板内容';

        comment on column sys_sms_template.template_test_json is '模板测试json';

        comment on column sys_sms_template.create_time is '创建日期';

        comment on column sys_sms_template.create_by is '创建人登录名称';

        comment on column sys_sms_template.update_time is '更新日期';

        comment on column sys_sms_template.update_by is '更新人登录名称';

        comment on column sys_sms_template.use_status is '是否使用中 1是0否';

        create index if not exists uk_sst_template_code
            on sys_sms_template (template_code);

        create table if not exists sys_tenant
        (
            id          integer not null
                primary key,
            name        varchar(100),
            create_time timestamp,
            create_by   varchar(100),
            begin_date  timestamp,
            end_date    timestamp,
            status      integer
        );

        comment on table sys_tenant is '多租户信息表';

        comment on column sys_tenant.id is '租户编码';

        comment on column sys_tenant.name is '租户名称';

        comment on column sys_tenant.create_time is '创建时间';

        comment on column sys_tenant.create_by is '创建人';

        comment on column sys_tenant.begin_date is '开始时间';

        comment on column sys_tenant.end_date is '结束时间';

        comment on column sys_tenant.status is '状态 1正常 0冻结';

        create table if not exists sys_third_account
        (
            id              varchar(32) not null
                primary key,
            sys_user_id     varchar(32),
            avatar          varchar(255),
            status          smallint,
            del_flag        smallint,
            realname        varchar(100),
            third_user_uuid varchar(100),
            third_user_id   varchar(100),
            create_by       varchar(32),
            create_time     timestamp,
            update_by       varchar(32),
            update_time     timestamp,
            third_type      varchar(50)
        );

        comment on column sys_third_account.id is '编号';

        comment on column sys_third_account.sys_user_id is '第三方登录id';

        comment on column sys_third_account.avatar is '头像';

        comment on column sys_third_account.status is '状态(1-正常,2-冻结)';

        comment on column sys_third_account.del_flag is '删除状态(0-正常,1-已删除)';

        comment on column sys_third_account.realname is '真实姓名';

        comment on column sys_third_account.third_user_uuid is '第三方账号';

        comment on column sys_third_account.third_user_id is '第三方app用户账号';

        comment on column sys_third_account.create_by is '创建人登录名称';

        comment on column sys_third_account.create_time is '创建日期';

        comment on column sys_third_account.update_by is '更新人登录名称';

        comment on column sys_third_account.update_time is '更新日期';

        comment on column sys_third_account.third_type is '登录来源';

        create index if not exists uniq_sys_third_account_third_type_third_user_id
            on sys_third_account (third_type, third_user_id);

        create table if not exists sys_user_agent
        (
            id               varchar(32) not null
                primary key,
            user_name        varchar(100),
            agent_user_name  varchar(100),
            start_time       timestamp,
            end_time         timestamp,
            status           varchar(2),
            create_name      varchar(50),
            create_by        varchar(50),
            create_time      timestamp,
            update_name      varchar(50),
            update_by        varchar(50),
            update_time      timestamp,
            sys_org_code     varchar(50),
            sys_company_code varchar(50)
        );

        comment on table sys_user_agent is '用户代理人设置';

        comment on column sys_user_agent.id is '序号';

        comment on column sys_user_agent.user_name is '用户名';

        comment on column sys_user_agent.agent_user_name is '代理人用户名';

        comment on column sys_user_agent.start_time is '代理开始时间';

        comment on column sys_user_agent.end_time is '代理结束时间';

        comment on column sys_user_agent.status is '状态0无效1有效';

        comment on column sys_user_agent.create_name is '创建人名称';

        comment on column sys_user_agent.create_by is '创建人登录名称';

        comment on column sys_user_agent.create_time is '创建日期';

        comment on column sys_user_agent.update_name is '更新人名称';

        comment on column sys_user_agent.update_by is '更新人登录名称';

        comment on column sys_user_agent.update_time is '更新日期';

        comment on column sys_user_agent.sys_org_code is '所属部门';

        comment on column sys_user_agent.sys_company_code is '所属公司';

        create index if not exists idx_sug_end_time
            on sys_user_agent (end_time);

        create index if not exists idx_sug_start_time
            on sys_user_agent (start_time);

        create index if not exists idx_sug_status
            on sys_user_agent (status);

        create index if not exists uk_sug_user_name
            on sys_user_agent (user_name);

        create table if not exists sys_user_depart
        (
            id      varchar(32) not null
                primary key,
            user_id varchar(32),
            dep_id  varchar(32)
        );

        comment on column sys_user_depart.id is 'id';

        comment on column sys_user_depart.user_id is '用户id';

        comment on column sys_user_depart.dep_id is '部门id';

        create index if not exists idx_sud_dep_id
            on sys_user_depart (dep_id);

        create index if not exists idx_sud_user_dep_id
            on sys_user_depart (user_id, dep_id);

        create index if not exists idx_sud_user_id
            on sys_user_depart (user_id);

        create table if not exists sys_user_role
        (
            id      varchar(32) not null
                primary key,
            user_id varchar(32),
            role_id varchar(32)
        );

        comment on table sys_user_role is '用户角色表';

        comment on column sys_user_role.id is '主键id';

        comment on column sys_user_role.user_id is '用户id';

        comment on column sys_user_role.role_id is '角色id';

        create index if not exists idx_sur_role_id
            on sys_user_role (role_id);

        create index if not exists idx_sur_user_id
            on sys_user_role (user_id);

        create index if not exists idx_sur_user_role_id
            on sys_user_role (user_id, role_id);

        create table if not exists test_demo
        (
            id          varchar(36) not null
                primary key,
            create_by   varchar(50),
            create_time timestamp,
            update_by   varchar(50),
            update_time timestamp,
            name        varchar(200),
            sex         varchar(32),
            age         integer,
            descc       varchar(500),
            birthday    timestamp,
            user_code   varchar(32),
            file_kk     varchar(500),
            top_pic     varchar(500),
            chegnshi    varchar(300),
            ceck        varchar(32),
            xiamuti     varchar(100),
            search_sel  varchar(100),
            pop         varchar(32),
            sel_table   varchar(32)
        );

        comment on column test_demo.id is '主键';

        comment on column test_demo.create_by is '创建人登录名称';

        comment on column test_demo.create_time is '创建日期';

        comment on column test_demo.update_by is '更新人登录名称';

        comment on column test_demo.update_time is '更新日期';

        comment on column test_demo.name is '用户名';

        comment on column test_demo.sex is '性别';

        comment on column test_demo.age is '年龄';

        comment on column test_demo.descc is '描述';

        comment on column test_demo.birthday is '生日';

        comment on column test_demo.user_code is '用户编码';

        comment on column test_demo.file_kk is '附件';

        comment on column test_demo.top_pic is '头像';

        comment on column test_demo.chegnshi is '城市';

        comment on column test_demo.ceck is 'checkbox';

        comment on column test_demo.xiamuti is '下拉多选';

        comment on column test_demo.search_sel is '搜索下拉';

        comment on column test_demo.pop is '弹窗';

        comment on column test_demo.sel_table is '下拉字典表';

        create table if not exists test_enhance_select
        (
            id          varchar(36) not null
                primary key,
            province    varchar(100),
            city        varchar(100),
            area        varchar(100),
            create_time timestamp,
            create_by   varchar(50)
        );

        comment on column test_enhance_select.province is '省份';

        comment on column test_enhance_select.city is '市';

        comment on column test_enhance_select.area is '区';

        comment on column test_enhance_select.create_time is '创建日期';

        comment on column test_enhance_select.create_by is '创建人';

        create table if not exists test_note
        (
            id           varchar(36) not null
                primary key,
            create_by    varchar(50),
            create_time  timestamp,
            update_by    varchar(50),
            update_time  timestamp,
            sys_org_code varchar(64),
            name         varchar(32),
            age          integer,
            sex          varchar(32),
            birthday     timestamp,
            contents     varchar(500)
        );

        comment on column test_note.id is '主键';

        comment on column test_note.create_by is '创建人';

        comment on column test_note.create_time is '创建日期';

        comment on column test_note.update_by is '更新人';

        comment on column test_note.update_time is '更新日期';

        comment on column test_note.sys_org_code is '所属部门';

        comment on column test_note.name is '用户名';

        comment on column test_note.age is '年龄';

        comment on column test_note.sex is '性别';

        comment on column test_note.birthday is '生日';

        comment on column test_note.contents is '请假原因';

        create table if not exists test_one
        (
            id           varchar(36) not null
                primary key,
            create_by    varchar(50),
            create_time  timestamp,
            update_by    varchar(50),
            update_time  timestamp,
            sys_org_code varchar(64),
            name         varchar(32),
            sex          varchar(32),
            sj           timestamp,
            bpm_status   varchar(32),
            lala         varchar(32),
            file         varchar(300),
            image        varchar(300),
            new_one      varchar(500)
        );

        comment on column test_one.id is '主键';

        comment on column test_one.create_by is '创建人';

        comment on column test_one.create_time is '创建日期';

        comment on column test_one.update_by is '更新人';

        comment on column test_one.update_time is '更新日期';

        comment on column test_one.sys_org_code is '所属部门';

        comment on column test_one.name is '名称';

        comment on column test_one.sex is '性别';

        comment on column test_one.sj is '时间2';

        comment on column test_one.bpm_status is '流程状态2';

        comment on column test_one.lala is '辣辣2';

        comment on column test_one.file is '文件2';

        comment on column test_one.image is '图片2';

        comment on column test_one.new_one is '测试';

        create table if not exists test_online_link
        (
            id   varchar(32) not null
                primary key,
            pid  varchar(32),
            name varchar(255)
        );

        comment on column test_online_link.pid is 'pid';

        comment on column test_online_link.name is 'name';

        create table if not exists test_order_main
        (
            id          varchar(36) not null
                primary key,
            create_by   varchar(50),
            create_time timestamp,
            update_by   varchar(50),
            update_time timestamp,
            order_code  varchar(32),
            order_date  timestamp,
            descc       varchar(100),
            xiala       varchar(32)
        );

        comment on column test_order_main.id is '主键';

        comment on column test_order_main.create_by is '创建人';

        comment on column test_order_main.create_time is '创建日期';

        comment on column test_order_main.update_by is '更新人';

        comment on column test_order_main.update_time is '更新日期';

        comment on column test_order_main.order_code is '订单编码';

        comment on column test_order_main.order_date is '下单时间';

        comment on column test_order_main.descc is '描述';

        comment on column test_order_main.xiala is '下拉多选';

        create table if not exists test_order_product
        (
            id           varchar(36) not null
                primary key,
            create_by    varchar(50),
            create_time  timestamp,
            update_by    varchar(50),
            update_time  timestamp,
            product_name varchar(32),
            price        double precision,
            num          integer,
            descc        varchar(32),
            order_fk_id  varchar(32) not null,
            pro_type     varchar(32)
        );

        comment on column test_order_product.id is '主键';

        comment on column test_order_product.create_by is '创建人';

        comment on column test_order_product.create_time is '创建日期';

        comment on column test_order_product.update_by is '更新人';

        comment on column test_order_product.update_time is '更新日期';

        comment on column test_order_product.product_name is '产品名字';

        comment on column test_order_product.price is '价格';

        comment on column test_order_product.num is '数量';

        comment on column test_order_product.descc is '描述';

        comment on column test_order_product.order_fk_id is '订单外键ID';

        comment on column test_order_product.pro_type is '产品类型';

        create table if not exists test_person
        (
            id          varchar(36) not null
                primary key,
            create_by   varchar(50),
            create_time timestamp,
            update_by   varchar(50),
            update_time timestamp,
            sex         varchar(32),
            name        varchar(200),
            content     text,
            be_date     timestamp,
            qj_days     integer
        );

        comment on column test_person.create_by is '创建人';

        comment on column test_person.create_time is '创建日期';

        comment on column test_person.update_by is '更新人';

        comment on column test_person.update_time is '更新日期';

        comment on column test_person.sex is '性别';

        comment on column test_person.name is '用户名';

        comment on column test_person.content is '请假原因';

        comment on column test_person.be_date is '请假时间';

        comment on column test_person.qj_days is '请假天数';

        create table if not exists test_shoptype_tree
        (
            id           varchar(36) not null
                primary key,
            create_by    varchar(50),
            create_time  timestamp,
            update_by    varchar(50),
            update_time  timestamp,
            sys_org_code varchar(64),
            type_name    varchar(32),
            pic          varchar(500),
            pid          varchar(32),
            has_child    varchar(3)
        );

        comment on column test_shoptype_tree.id is '主键';

        comment on column test_shoptype_tree.create_by is '创建人';

        comment on column test_shoptype_tree.create_time is '创建日期';

        comment on column test_shoptype_tree.update_by is '更新人';

        comment on column test_shoptype_tree.update_time is '更新日期';

        comment on column test_shoptype_tree.sys_org_code is '所属部门';

        comment on column test_shoptype_tree.type_name is '商品分类';

        comment on column test_shoptype_tree.pic is '分类图片';

        comment on column test_shoptype_tree.pid is '父级节点';

        comment on column test_shoptype_tree.has_child is '是否有子节点';

        create table if not exists test_two
        (
            id           varchar(36) not null
                primary key,
            create_by    varchar(50),
            create_time  timestamp,
            update_by    varchar(50),
            update_time  timestamp,
            sys_org_code varchar(64),
            name         varchar(32),
            content      text,
            code         varchar(255)
        );

        comment on column test_two.id is 'id';

        comment on column test_two.create_by is '创建人';

        comment on column test_two.create_time is '创建日期';

        comment on column test_two.update_by is '更新人';

        comment on column test_two.update_time is '更新日期';

        comment on column test_two.sys_org_code is '所属部门';

        comment on column test_two.name is '名称';

        comment on column test_two.content is '内容';

        comment on column test_two.code is '编号';

        create table if not exists tmp_report_data_1
        (
            monty       varchar(255),
            main_income numeric(10, 2),
            total       numeric(10, 2),
            his_lowest  numeric(10, 2),
            his_average numeric(10, 2),
            his_highest numeric(10, 2)
        );

        comment on column tmp_report_data_1.monty is '月份';

        create table if not exists tmp_report_data_income
        (
            biz_income        varchar(100),
            bx_jj_yongjin     numeric(10, 2),
            bx_zx_money       numeric(10, 2),
            chengbao_gz_money numeric(10, 2),
            bx_gg_moeny       numeric(10, 2),
            tb_zx_money       numeric(10, 2),
            neikong_zx_money  numeric(10, 2),
            total             numeric(10, 2)
        );

        create table if not exists v3_demo1
        (
            id           varchar(36) not null
                primary key,
            create_by    varchar(50),
            create_time  timestamp,
            update_by    varchar(50),
            update_time  timestamp,
            sys_org_code varchar(64),
            name         varchar(32),
            sex          varchar(32),
            age          integer,
            birthday     date,
            salary       double precision,
            cccc         varchar(1000)
        );

        comment on column v3_demo1.create_by is '创建人';

        comment on column v3_demo1.create_time is '创建日期';

        comment on column v3_demo1.update_by is '更新人';

        comment on column v3_demo1.update_time is '更新日期';

        comment on column v3_demo1.sys_org_code is '所属部门';

        comment on column v3_demo1.name is '名字';

        comment on column v3_demo1.sex is '性别';

        comment on column v3_demo1.age is '年龄';

        comment on column v3_demo1.birthday is '生日';

        comment on column v3_demo1.salary is '工资';

        comment on column v3_demo1.cccc is '备注';

        create table if not exists v3_hello
        (
            id           varchar(36) not null
                primary key,
            create_by    varchar(50),
            create_time  timestamp,
            update_by    varchar(50),
            update_time  timestamp,
            sys_org_code varchar(64),
            name         varchar(32),
            age          integer,
            sex          varchar(32),
            birthday     date,
            cc           varchar(1000),
            rel_user     varchar(32)
        );

        comment on column v3_hello.create_by is '创建人';

        comment on column v3_hello.create_time is '创建日期';

        comment on column v3_hello.update_by is '更新人';

        comment on column v3_hello.update_time is '更新日期';

        comment on column v3_hello.sys_org_code is '所属部门';

        comment on column v3_hello.name is '名字';

        comment on column v3_hello.age is '年龄';

        comment on column v3_hello.sex is '性别';

        comment on column v3_hello.birthday is '生日';

        comment on column v3_hello.cc is '备注';

        comment on column v3_hello.rel_user is '关联记录';

        create table if not exists sys_upload
        (
            id          varchar(32) not null,
            file_name   varchar(255),
            url         varchar(255),
            create_by   varchar(32),
            create_time timestamp(6),
            update_by   varchar(32),
            update_time timestamp(6),
            md5         varchar(200)
        );

        comment on table sys_upload is '文件信息表';

        comment on column sys_upload.id is '主键id';

        comment on column sys_upload.file_name is '文件名称';

        comment on column sys_upload.url is '文件地址';

        comment on column sys_upload.create_by is '创建人登录名称';

        comment on column sys_upload.create_time is '创建日期';

        comment on column sys_upload.update_by is '更新人登录名称';

        comment on column sys_upload.update_time is '更新日期';

        comment on column sys_upload.md5 is '文件md5值';

        create table if not exists technical_folder
        (
            id                varchar(32) not null,
            create_by         varchar(255),
            create_time       timestamp(6),
            update_by         varchar(255),
            update_time       timestamp(6),
            name              varchar(255),
            level             integer,
            folder_order      integer,
            parent_id         varchar(32),
            project_id        varchar(32),
            project_name      varchar(255),
            child_folder_size integer,
            type              smallint,
            child_file_size   integer,
            enabled           smallint default 1,
            business_id       varchar(36),
            business_name     varchar(255),
            tags              varchar(200)
        );

        comment on column technical_folder.name is '目录名称';

        comment on column technical_folder.level is '层级';

        comment on column technical_folder.folder_order is '顺序：每个level&parentId下从0开始标记';

        comment on column technical_folder.parent_id is '父层级';

        comment on column technical_folder.project_id is '关联项目id';

        comment on column technical_folder.project_name is '关联项目名称，冗余';

        comment on column technical_folder.child_folder_size is '子目录个数，冗余';

        comment on column technical_folder.type is '目录类型，枚举类：图纸1、模型2';

        comment on column technical_folder.child_file_size is '子文件（关联）个数，冗余';

        comment on column technical_folder.enabled is '是否启用 1启用 0禁用，用作删除判断';

        comment on column technical_folder.business_id is '业务id（各类业务表单id）';

        comment on column technical_folder.business_name is '业务名称';

        comment on column technical_folder.tags is '标签';

        create table if not exists technical_file
        (
            id            varchar(32) not null,
            create_by     varchar(255),
            create_time   timestamp(6),
            update_by     varchar(255),
            update_time   timestamp(6),
            name          varchar(255),
            version       integer,
            folder_id     varchar(32),
            file_order    integer,
            project_id    varchar(32),
            project_name  varchar(255),
            oss_file      text,
            bimface_file  text,
            type          integer,
            suffix        varchar(255),
            enabled       smallint,
            current_show  smallint,
            thumbnail     varchar(255),
            size          varchar(255),
            changes       text,
            comments      text,
            problems      text,
            upload_by     varchar(255),
            business_id   varchar(40),
            business_name varchar(255),
            tags          varchar(255)
        );

        comment on column technical_file.name is '文件名，相同时，版本号+1';

        comment on column technical_file.version is '版本号';

        comment on column technical_file.folder_id is '关联目录';

        comment on column technical_file.file_order is '文件顺序，扩展字段，暂时不需要考虑';

        comment on column technical_file.project_id is '关联项目id';

        comment on column technical_file.project_name is '关联项目名';

        comment on column technical_file.oss_file is '关联aliyun oss文件';

        comment on column technical_file.bimface_file is '关联bimface file';

        comment on column technical_file.type is '类型 int';

        comment on column technical_file.suffix is '后缀 String';

        comment on column technical_file.enabled is '逻辑删除';

        comment on column technical_file.current_show is '当前使用的版本';

        comment on column technical_file.thumbnail is '缩略图';

        comment on column technical_file.size is '文件大小';

        comment on column technical_file.changes is '变更';

        comment on column technical_file.comments is '批注';

        comment on column technical_file.problems is '问题';

        comment on column technical_file.upload_by is '上传人，替代createBy';

        comment on column technical_file.business_id is '业务id';

        comment on column technical_file.business_name is '业务名称';

        comment on column technical_file.tags is '标签';

        create table if not exists sys_user
        (
            id             varchar(32) not null,
            username       varchar(100),
            realname       varchar(100),
            password       varchar(255),
            salt           varchar(45),
            avatar         varchar(255),
            birthday       timestamp,
            sex            smallint,
            email          varchar(45),
            phone          varchar(45),
            org_code       varchar(64),
            status         smallint,
            del_flag       smallint,
            third_id       varchar(100),
            third_type     varchar(100),
            activiti_sync  smallint,
            work_no        varchar(100),
            post           varchar(100),
            telephone      varchar(45),
            create_by      varchar(32),
            create_time    timestamp,
            update_by      varchar(32),
            update_time    timestamp,
            user_identity  smallint,
            depart_ids     text,
            rel_tenant_ids varchar(100),
            client_id      varchar(64)
        );

        comment on table sys_user is '用户表';

        comment on column sys_user.id is '主键id';

        comment on column sys_user.username is '登录账号';

        comment on column sys_user.realname is '真实姓名';

        comment on column sys_user.password is '密码';

        comment on column sys_user.salt is 'md5密码盐';

        comment on column sys_user.avatar is '头像';

        comment on column sys_user.birthday is '生日';

        comment on column sys_user.sex is '性别(0-默认未知,1-男,2-女)';

        comment on column sys_user.email is '电子邮件';

        comment on column sys_user.phone is '电话';

        comment on column sys_user.org_code is '登录会话的机构编码';

        comment on column sys_user.status is '性别(1-正常,2-冻结)';

        comment on column sys_user.del_flag is '删除状态(0-正常,1-已删除)';

        comment on column sys_user.third_id is '第三方登录的唯一标识';

        comment on column sys_user.third_type is '第三方类型';

        comment on column sys_user.activiti_sync is '同步工作流引擎(1-同步,0-不同步)';

        comment on column sys_user.work_no is '工号，唯一键';

        comment on column sys_user.post is '职务，关联职务表';

        comment on column sys_user.telephone is '座机号';

        comment on column sys_user.create_by is '创建人';

        comment on column sys_user.create_time is '创建时间';

        comment on column sys_user.update_by is '更新人';

        comment on column sys_user.update_time is '更新时间';

        comment on column sys_user.user_identity is '身份（1普通成员 2上级）';

        comment on column sys_user.depart_ids is '负责部门';

        comment on column sys_user.rel_tenant_ids is '多租户标识';

        comment on column sys_user.client_id is '设备ID';

        create table if not exists technical_folder_user_permission
        (
            id                   varchar(36) not null
                primary key,
            folder_id            varchar(36),
            username             varchar(255),
            data_permission_type varchar(36)
        );

        comment on table technical_folder_user_permission is '目录-用户权限表';

        comment on column technical_folder_user_permission.id is '主键';

        comment on column technical_folder_user_permission.folder_id is '目录id';

        comment on column technical_folder_user_permission.username is '用户名';

        comment on column technical_folder_user_permission.data_permission_type is '数据权限类型';

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
            is_temp_map           boolean
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

        create table if not exists ztb_xedj
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

        create table if not exists app_authorize
        (
            id         varchar(36) not null
                primary key,
            app_key    varchar(255),
            app_secret varchar(255)
        );


    end;
    $$;
-- 建表 END
