-- 1.5.6 升级sql --

-- 将 `jimu_report_data_source` 表添加 `type` 列，并设置为可空
ALTER TABLE jimu_report_data_source
    ADD COLUMN type varchar(10) NULL;
-- 为 `type` 列添加注释
COMMENT ON COLUMN jimu_report_data_source.type IS '类型(report:报表;drag:仪表盘)';
-- 更新 `jimu_report_data_source` 表的所有行的 `type` 列为 'report'
UPDATE jimu_report_data_source
SET type = 'report';

-- 1.6.x -> 1.7.0 升级sql --

-- 将 `jimu_report_share` 表的 `report_id` 列添加唯一索引
ALTER TABLE jimu_report_share
    ADD CONSTRAINT uniq_report_id UNIQUE (report_id);
-- 将 `jimu_report_share` 表添加 `share_token` 列，并设置为可空
ALTER TABLE jimu_report_share
    ADD COLUMN share_token varchar(50) NULL;
-- 为 `share_token` 列添加注释
COMMENT ON COLUMN jimu_report_share.share_token IS '分享token';
-- 为 `share_token` 列添加唯一索引
CREATE UNIQUE INDEX uniq_jrs_share_token ON jimu_report_share (share_token);
-- 将 `jimu_report` 表添加 `py_str` 列，可空
ALTER TABLE jimu_report ADD COLUMN py_str text NULL;
-- 为 `py_str` 列添加注释
COMMENT ON COLUMN jimu_report.py_str IS 'py增强';
