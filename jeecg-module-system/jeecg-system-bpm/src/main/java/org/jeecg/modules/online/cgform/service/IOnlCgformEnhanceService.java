package org.jeecg.modules.online.cgform.service;

import org.jeecg.modules.online.cgform.entity.OnlCgformEnhanceJava;
import org.jeecg.modules.online.cgform.entity.OnlCgformEnhanceSql;

import java.util.List;

public abstract interface IOnlCgformEnhanceService
{
  public abstract List<OnlCgformEnhanceJava> queryEnhanceJavaList(String paramString);

  public abstract void saveEnhanceJava(OnlCgformEnhanceJava paramOnlCgformEnhanceJava);

  public abstract void updateEnhanceJava(OnlCgformEnhanceJava paramOnlCgformEnhanceJava);

  public abstract void deleteEnhanceJava(String paramString);

  public abstract void deleteBatchEnhanceJava(List<String> paramList);

  public abstract boolean checkOnlyEnhance(OnlCgformEnhanceJava paramOnlCgformEnhanceJava);

  public abstract boolean checkOnlyEnhance(OnlCgformEnhanceSql paramOnlCgformEnhanceSql);

  public abstract List<OnlCgformEnhanceSql> queryEnhanceSqlList(String paramString);

  public abstract void saveEnhanceSql(OnlCgformEnhanceSql paramOnlCgformEnhanceSql);

  public abstract void updateEnhanceSql(OnlCgformEnhanceSql paramOnlCgformEnhanceSql);

  public abstract void deleteEnhanceSql(String paramString);

  public abstract void deleteBatchEnhanceSql(List<String> paramList);
}

/* Location:           C:\Users\tx\Desktop\hibernate-common-ol-5.4.74(2).jar
 * Qualified Name:     org.jeecg.modules.online.cgform.service.IOnlCgformEnhanceService
 * JD-Core Version:    0.6.2
 */
