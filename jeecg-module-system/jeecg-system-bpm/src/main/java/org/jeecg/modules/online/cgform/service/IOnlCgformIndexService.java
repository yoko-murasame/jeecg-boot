package org.jeecg.modules.online.cgform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.online.cgform.entity.OnlCgformIndex;

import java.util.List;

public abstract interface IOnlCgformIndexService extends IService<OnlCgformIndex>
{
  public abstract void createIndex(String paramString1, String paramString2, String paramString3);

  public abstract boolean isExistIndex(String paramString);

  public abstract List<OnlCgformIndex> getCgformIndexsByCgformId(String paramString);
}

/* Location:           C:\Users\tx\Desktop\hibernate-common-ol-5.4.74(2).jar
 * Qualified Name:     org.jeecg.modules.online.cgform.service.IOnlCgformIndexService
 * JD-Core Version:    0.6.2
 */
