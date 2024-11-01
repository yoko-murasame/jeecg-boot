-- 日志记录时，长度可能不够，调整为text类型
alter table sys_log
    alter column log_content type text using log_content::text;
