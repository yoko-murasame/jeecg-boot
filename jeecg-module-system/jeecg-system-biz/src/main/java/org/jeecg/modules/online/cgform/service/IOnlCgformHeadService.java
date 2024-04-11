package org.jeecg.modules.online.cgform.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.online.cgform.entity.OnlCgformButton;
import org.jeecg.modules.online.cgform.entity.OnlCgformEnhanceJava;
import org.jeecg.modules.online.cgform.entity.OnlCgformEnhanceJs;
import org.jeecg.modules.online.cgform.entity.OnlCgformEnhanceSql;
import org.jeecg.modules.online.cgform.entity.OnlCgformHead;
import org.jeecg.modules.online.cgform.model.OnlGenerateModel;
import org.jeecg.modules.online.cgform.model.a;
import org.jeecg.modules.online.config.exception.BusinessException;
import org.jeecg.modules.online.config.exception.DBException;

public abstract interface IOnlCgformHeadService extends IService<OnlCgformHead>
{
  public abstract Result<?> addAll(a parama);

  public abstract Result<?> editAll(a parama);

  public abstract void doDbSynch(String paramString1, String paramString2)
    throws HibernateException, IOException, TemplateException, SQLException, DBException;

  public abstract void deleteRecordAndTable(String paramString)
    throws DBException, SQLException;

  public abstract void deleteRecord(String paramString)
    throws DBException, SQLException;

  public abstract List<Map<String, Object>> queryListData(String paramString);

  public abstract OnlCgformEnhanceJs queryEnhance(String paramString1, String paramString2);

  public abstract void saveEnhance(OnlCgformEnhanceJs paramOnlCgformEnhanceJs);

  public abstract void editEnhance(OnlCgformEnhanceJs paramOnlCgformEnhanceJs);

  public abstract OnlCgformEnhanceSql queryEnhanceSql(String paramString1, String paramString2);

  public abstract OnlCgformEnhanceJava queryEnhanceJava(OnlCgformEnhanceJava paramOnlCgformEnhanceJava);

  public abstract List<OnlCgformButton> queryButtonList(String paramString, boolean paramBoolean);

  public abstract List<OnlCgformButton> queryButtonList(String paramString);

  public abstract List<String> queryOnlinetables();

  public abstract void saveDbTable2Online(String paramString);

  public abstract JSONObject queryFormItem(OnlCgformHead paramOnlCgformHead, String paramString);

  public abstract String saveManyFormData(String paramString1, JSONObject paramJSONObject, String paramString2)
    throws DBException, BusinessException;

  public abstract Map<String, Object> queryManyFormData(String paramString1, String paramString2)
    throws DBException;

  public abstract List<Map<String, Object>> queryManySubFormData(String paramString1, String paramString2)
    throws DBException;

  public abstract Map<String, Object> querySubFormData(String paramString1, String paramString2)
    throws DBException;

  public abstract String editManyFormData(String paramString, JSONObject paramJSONObject)
    throws DBException, BusinessException;

  public abstract int executeEnhanceJava(String paramString1, String paramString2, OnlCgformHead paramOnlCgformHead, JSONObject paramJSONObject)
    throws BusinessException;

  public abstract void executeEnhanceExport(OnlCgformHead paramOnlCgformHead, List<Map<String, Object>> paramList)
    throws BusinessException;

  public abstract void executeEnhanceList(OnlCgformHead paramOnlCgformHead, String paramString, List<Map<String, Object>> paramList)
    throws BusinessException;

  public abstract void executeEnhanceSql(String paramString1, String paramString2, JSONObject paramJSONObject);

  public abstract void executeCustomerButton(String paramString1, String paramString2, String paramString3)
    throws BusinessException;

  public abstract List<OnlCgformButton> queryValidButtonList(String paramString);

  public abstract OnlCgformEnhanceJs queryEnhanceJs(String paramString1, String paramString2);

  public abstract void deleteOneTableInfo(String paramString1, String paramString2)
    throws BusinessException;

  public abstract List<String> generateCode(OnlGenerateModel paramOnlGenerateModel)
    throws Exception;

  public abstract List<String> generateOneToMany(OnlGenerateModel paramOnlGenerateModel)
    throws Exception;

  public abstract void addCrazyFormData(String paramString, JSONObject paramJSONObject)
    throws DBException, UnsupportedEncodingException;

  public abstract void editCrazyFormData(String paramString, JSONObject paramJSONObject)
    throws DBException, UnsupportedEncodingException;

  public abstract Integer getMaxCopyVersion(String paramString);

  public abstract void copyOnlineTableConfig(OnlCgformHead paramOnlCgformHead)
    throws Exception;

  public abstract void initCopyState(List<OnlCgformHead> paramList);

  public abstract void deleteBatch(String paramString1, String paramString2);

  public abstract void updateParentNode(OnlCgformHead paramOnlCgformHead, String paramString);
}

/* Location:           C:\Users\tx\Desktop\hibernate-common-ol-5.4.74(2).jar
 * Qualified Name:     org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
 * JD-Core Version:    0.6.2
 */