package org.jeecg.modules.online.cgform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.jeecg.modules.online.cgform.entity.OnlCgformHead;

import java.util.List;
import java.util.Map;

public abstract interface OnlCgformHeadMapper extends BaseMapper<OnlCgformHead>
{
  public abstract void executeDDL(@Param("sqlStr") String paramString);

  public abstract List<Map<String, Object>> queryList(@Param("sqlStr") String paramString);

  public abstract List<String> queryOnlinetables();

  public abstract Map<String, Object> queryOneByTableNameAndId(@Param("tbname") String paramString1, @Param("dataId") String paramString2);

  public abstract void deleteOne(@Param("sqlStr") String paramString);

  public abstract Integer queryMaxCopyVersion();

  @Select({"select max(copy_version) from onl_cgform_head where physic_id = #{physicId}"})
  public abstract Integer getMaxCopyVersion(@Param("physicId") String paramString);

  @Select({"select table_name from onl_cgform_head where physic_id = #{physicId}"})
  public abstract List<String> queryAllCopyTableName(@Param("physicId") String paramString);

  @Select({"select physic_id from onl_cgform_head GROUP BY physic_id"})
  public abstract List<String> queryCopyPhysicId();

  public abstract String queryCategoryIdByCode(@Param("code") String paramString);

  @Select({"select count(*) from ${tableName} where ${pidField} = #{pidValue}"})
  public abstract Integer queryChildNode(@Param("tableName") String paramString1, @Param("pidField") String paramString2, @Param("pidValue") String paramString3);
}

/* Location:           C:\Users\tx\Desktop\hibernate-common-ol-5.4.74(2).jar
 * Qualified Name:     org.jeecg.modules.online.cgform.mapper.OnlCgformHeadMapper
 * JD-Core Version:    0.6.2
 */
