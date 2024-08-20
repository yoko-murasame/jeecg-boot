package org.jeecg.modules.online.cgform.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.online.cgform.a.a;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;
import org.jeecg.modules.online.cgform.entity.OnlCgformHead;
import org.jeecg.modules.online.cgform.model.TreeModel;

import java.util.List;
import java.util.Map;

public abstract interface IOnlCgformFieldService extends IService<OnlCgformField>
{
  public abstract Map<String, Object> queryAutolistPage(String paramString1, String paramString2, Map<String, Object> paramMap, List<String> paramList, String dataRulePerms);

  // org.jeecg.modules.online.cgform.service.IOnlCgformFieldService
  Map<String, Object> queryAutoExportlist(String tbname, String headId, Map<String, Object> params, List<String> needList);

  public abstract Map<String, Object> queryAutoTreeNoPage(String paramString1, String paramString2, Map<String, Object> paramMap, List<String> paramList, String paramString3);

  public abstract void deleteAutoListMainAndSub(OnlCgformHead paramOnlCgformHead, String paramString);

  public abstract void deleteAutoListById(String paramString1, String paramString2);

  public abstract void deleteAutoList(String paramString1, String paramString2, String paramString3);

  public abstract void saveFormData(String paramString1, String paramString2, JSONObject paramJSONObject, boolean paramBoolean);

  // org.jeecg.modules.online.cgform.service.IOnlCgformFieldService
  void saveFormDataForCyclePlan(String code, String tbname, JSONObject json, boolean isCrazy);

  public abstract void saveTreeFormData(String paramString1, String paramString2, JSONObject paramJSONObject, String paramString3, String paramString4);

  public abstract void saveFormData(List<OnlCgformField> paramList, String paramString, JSONObject paramJSONObject);

  public abstract List<OnlCgformField> queryFormFields(String paramString, boolean paramBoolean);

  public abstract List<OnlCgformField> queryFormFieldsByTableName(String paramString);

  public abstract OnlCgformField queryFormFieldByTableNameAndField(String paramString1, String paramString2);

  public abstract void editTreeFormData(String paramString1, String paramString2, JSONObject paramJSONObject, String paramString3, String paramString4);

  public abstract void editFormData(String paramString1, String paramString2, JSONObject paramJSONObject, boolean paramBoolean);

  public abstract Map<String, Object> queryFormData(String paramString1, String paramString2, String paramString3);

  public abstract Map<String, Object> queryFormData(List<OnlCgformField> paramList, String paramString1, String paramString2);

  public abstract List<Map<String, Object>> querySubFormData(List<OnlCgformField> paramList, String paramString1, String paramString2, String paramString3);

  public abstract List<Map<String, String>> getAutoListQueryInfo(String paramString);

  public abstract IPage<Map<String, Object>> selectPageBySql(Page<Map<String, Object>> paramPage, @Param("sqlStr") String paramString);

  public abstract List<String> selectOnlineHideColumns(String paramString);

  public abstract List<OnlCgformField> queryAvailableFields(String paramString1, String paramString2, String paramString3, boolean paramBoolean);

  public abstract List<String> queryDisabledFields(String paramString);

  public abstract List<String> queryDisabledFields(String paramString1, String paramString2);

  public abstract List<OnlCgformField> queryAvailableFields(String paramString, boolean paramBoolean, List<OnlCgformField> paramList, List<String> paramList1);

  public abstract List<OnlCgformField> queryAvailableFields(String paramString1, String paramString2, boolean paramBoolean, List<OnlCgformField> paramList, List<String> paramList1);

  // org.jeecg.modules.online.cgform.service.IOnlCgformFieldService
  List<OnlCgformField> queryAvailableExportFields(String cgformId, String tbname, boolean isList, List<OnlCgformField> List, List<String> needList);

  public abstract void executeInsertSQL(Map<String, Object> paramMap);

  public abstract void executeUpdatetSQL(Map<String, Object> paramMap);

  public abstract List<TreeModel> queryDataListByLinkDown(a parama);

  public abstract void updateTreeNodeNoChild(String paramString1, String paramString2, String paramString3);

  public abstract String queryTreeChildIds(OnlCgformHead paramOnlCgformHead, String paramString);

  public abstract String queryTreePids(OnlCgformHead paramOnlCgformHead, String paramString);

  public abstract String queryForeignKey(String paramString1, String paramString2);
}

/* Location:           C:\Users\tx\Desktop\hibernate-common-ol-5.4.74(2).jar
 * Qualified Name:     org.jeecg.modules.online.cgform.service.IOnlCgformFieldService
 * JD-Core Version:    0.6.2
 */
