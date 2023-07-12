-- 部门表相关
alter table sys_user_depart
    rename column "ID" to id;

-- 修改表名为小写
ALTER TABLE onl_cgform_button RENAME TO onl_cgform_button_lower;
-- 修改字段名为小写
ALTER TABLE onl_cgform_button_lower RENAME COLUMN "ID" TO id;
ALTER TABLE onl_cgform_button_lower RENAME COLUMN "BUTTON_CODE" TO button_code;
ALTER TABLE onl_cgform_button_lower RENAME COLUMN "BUTTON_ICON" TO button_icon;
ALTER TABLE onl_cgform_button_lower RENAME COLUMN "BUTTON_NAME" TO button_name;
ALTER TABLE onl_cgform_button_lower RENAME COLUMN "BUTTON_STATUS" TO button_status;
ALTER TABLE onl_cgform_button_lower RENAME COLUMN "BUTTON_STYLE" TO button_style;
ALTER TABLE onl_cgform_button_lower RENAME COLUMN "EXP" TO exp;
ALTER TABLE onl_cgform_button_lower RENAME COLUMN "CGFORM_HEAD_ID" TO cgform_head_id;
ALTER TABLE onl_cgform_button_lower RENAME COLUMN "OPT_TYPE" TO opt_type;
ALTER TABLE onl_cgform_button_lower RENAME COLUMN "ORDER_NUM" TO order_num;
ALTER TABLE onl_cgform_button_lower RENAME COLUMN "OPT_POSITION" TO opt_position;
-- 修改表名为原表名
ALTER TABLE onl_cgform_button_lower RENAME TO onl_cgform_button;


-- 修改表名为小写
ALTER TABLE onl_cgform_enhance_java RENAME TO onl_cgform_enhance_java_lower;
-- 修改字段名为小写
ALTER TABLE onl_cgform_enhance_java_lower RENAME COLUMN "ID" TO id;
ALTER TABLE onl_cgform_enhance_java_lower RENAME COLUMN "BUTTON_CODE" TO button_code;
ALTER TABLE onl_cgform_enhance_java_lower RENAME COLUMN "CG_JAVA_TYPE" TO cg_java_type;
ALTER TABLE onl_cgform_enhance_java_lower RENAME COLUMN "CG_JAVA_VALUE" TO cg_java_value;
ALTER TABLE onl_cgform_enhance_java_lower RENAME COLUMN "CGFORM_HEAD_ID" TO cgform_head_id;
ALTER TABLE onl_cgform_enhance_java_lower RENAME COLUMN "ACTIVE_STATUS" TO active_status;
ALTER TABLE onl_cgform_enhance_java_lower RENAME COLUMN "EVENT" TO event;
-- 修改表名为原表名
ALTER TABLE onl_cgform_enhance_java_lower RENAME TO onl_cgform_enhance_java;


-- 修改表名为小写
ALTER TABLE onl_cgform_enhance_js RENAME TO onl_cgform_enhance_js_lower;
-- 修改字段名为小写
ALTER TABLE onl_cgform_enhance_js_lower RENAME COLUMN "ID" TO id;
ALTER TABLE onl_cgform_enhance_js_lower RENAME COLUMN "CG_JS" TO cg_js;
ALTER TABLE onl_cgform_enhance_js_lower RENAME COLUMN "CG_JS_TYPE" TO cg_js_type;
ALTER TABLE onl_cgform_enhance_js_lower RENAME COLUMN "CONTENT" TO content;
ALTER TABLE onl_cgform_enhance_js_lower RENAME COLUMN "CGFORM_HEAD_ID" TO cgform_head_id;
-- 修改表名为原表名
ALTER TABLE onl_cgform_enhance_js_lower RENAME TO onl_cgform_enhance_js;


-- 修改表名为小写
ALTER TABLE onl_cgform_enhance_sql RENAME TO onl_cgform_enhance_sql_lower;
-- 修改字段名为小写
ALTER TABLE onl_cgform_enhance_sql_lower RENAME COLUMN "ID" TO id;
ALTER TABLE onl_cgform_enhance_sql_lower RENAME COLUMN "BUTTON_CODE" TO button_code;
ALTER TABLE onl_cgform_enhance_sql_lower RENAME COLUMN "CGB_SQL" TO cgb_sql;
ALTER TABLE onl_cgform_enhance_sql_lower RENAME COLUMN "CGB_SQL_NAME" TO cgb_sql_name;
ALTER TABLE onl_cgform_enhance_sql_lower RENAME COLUMN "CONTENT" TO content;
ALTER TABLE onl_cgform_enhance_sql_lower RENAME COLUMN "CGFORM_HEAD_ID" TO cgform_head_id;
-- 修改表名为原表名
ALTER TABLE onl_cgform_enhance_sql_lower RENAME TO onl_cgform_enhance_sql;
