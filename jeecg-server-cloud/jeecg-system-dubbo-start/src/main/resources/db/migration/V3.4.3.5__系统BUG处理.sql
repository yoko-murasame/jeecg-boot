-- 日志记录时，长度可能不够，调整为text类型
comment on column sys_log.log_content is '日志内容';
alter table sys_log
    alter column log_content type text using log_content::text;

-- online表单扩展字段：slot渲染Vue代码，调整为text类型
comment on column onl_cgform_field.scoped_slots_render_code is 'slot渲染Vue代码';
alter table onl_cgform_field
    alter column scoped_slots_render_code type text using scoped_slots_render_code::text;
