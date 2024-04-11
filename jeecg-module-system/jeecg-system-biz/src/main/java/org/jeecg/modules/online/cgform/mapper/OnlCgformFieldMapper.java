package org.jeecg.modules.online.cgform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.online.cgform.a.a;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;
import org.jeecg.modules.online.cgform.model.TreeModel;

public abstract interface OnlCgformFieldMapper extends BaseMapper<OnlCgformField>
{
  public abstract List<Map<String, Object>> queryListBySql(@Param("sqlStr") String paramString);

  public abstract Integer queryCountBySql(@Param("sqlStr") String paramString);

  public abstract void deleteAutoList(@Param("sqlStr") String paramString);

  public abstract void saveFormData(@Param("sqlStr") String paramString);

  public abstract void editFormData(@Param("sqlStr") String paramString);

  public abstract Map<String, Object> queryFormData(@Param("sqlStr") String paramString);

  public abstract List<Map<String, Object>> queryListData(@Param("sqlStr") String paramString);

  public abstract IPage<Map<String, Object>> selectPageBySql(Page<Map<String, Object>> paramPage, @Param("sqlStr") String paramString);

  public abstract void executeInsertSQL(Map<String, Object> paramMap);

  public abstract void executeUpdatetSQL(Map<String, Object> paramMap);

  public abstract List<String> selectOnlineHideColumns(@Param("user_id") String paramString1, @Param("online_tbname") String paramString2);

  public abstract List<String> selectOnlineDisabledColumns(@Param("user_id") String paramString1, @Param("online_tbname") String paramString2);

  public abstract List<String> selectFlowAuthColumns(@Param("table_name") String paramString1, @Param("task_id") String paramString2, @Param("rule_type") String paramString3);

  @Deprecated
  public abstract List<TreeModel> queryDataListByLinkDown(@Param("query") a parama);
}

/* Location:           C:\Users\tx\Desktop\hibernate-common-ol-5.4.74(2).jar
 * Qualified Name:     org.jeecg.modules.online.cgform.mapper.OnlCgformFieldMapper
 * JD-Core Version:    0.6.2
 */