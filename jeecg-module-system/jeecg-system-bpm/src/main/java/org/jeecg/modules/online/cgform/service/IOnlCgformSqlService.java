package org.jeecg.modules.online.cgform.service;

import org.jeecg.modules.online.cgform.entity.OnlCgformField;
import org.jeecg.modules.online.cgform.entity.OnlCgformHead;
import org.jeecg.modules.online.config.exception.BusinessException;

import java.util.List;
import java.util.Map;

public abstract interface IOnlCgformSqlService
{
  public abstract void saveBatchOnlineTable(OnlCgformHead paramOnlCgformHead, List<OnlCgformField> paramList, List<Map<String, Object>> paramList1)
    throws BusinessException;

  public abstract void saveOrUpdateSubData(String paramString, OnlCgformHead paramOnlCgformHead, List<OnlCgformField> paramList)
    throws BusinessException;
}

/* Location:           C:\Users\tx\Desktop\hibernate-common-ol-5.4.74(2).jar
 * Qualified Name:     org.jeecg.modules.online.cgform.service.IOnlCgformSqlService
 * JD-Core Version:    0.6.2
 */
