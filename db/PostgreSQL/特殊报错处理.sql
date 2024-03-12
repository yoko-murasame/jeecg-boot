-- 1.Mybatis模糊查询无法确定参数$1的数据类型: ERROR: could not determine data type of parameter $1
-- 这个问题主要是由于jdbc:postgresql://127.0.0.1:54321/postgres?stringtype=unspecified中指定了?stringtype=unspecified导致的
-- 受影响的类: org.jeecg.modules.system.mapper.SysDepartMapper.getSubDepIdsByOrgCodes
-- 将 CONCAT(#{item},'%') -> CONCAT(#{item}::text,'%')
-- 除此之外，可以全局搜索“),'%'”，都加上“::text”，防止别的模块出现同样的问题


-- 2.错误: 字段 "is_leaf" 的类型为 smallint, 但表达式的类型为 boolean
-- 执行脚本 BEGIN --
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
create cast (BOOLEAN as SMALLINT) with function boolean_to_smallint as ASSIGNMENT;
-- 执行脚本 END --
