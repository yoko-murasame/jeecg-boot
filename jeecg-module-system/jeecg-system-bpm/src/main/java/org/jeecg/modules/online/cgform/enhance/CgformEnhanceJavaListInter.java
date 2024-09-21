package org.jeecg.modules.online.cgform.enhance;

import org.jeecg.modules.online.config.exception.BusinessException;

import java.util.List;
import java.util.Map;

public abstract interface CgformEnhanceJavaListInter
{
  public abstract void execute(String paramString, List<Map<String, Object>> paramList)
    throws BusinessException;
}

/* Location:           C:\Users\tx\Desktop\hibernate-common-ol-5.4.74(2).jar
 * Qualified Name:     org.jeecg.modules.online.cgform.enhance.CgformEnhanceJavaListInter
 * JD-Core Version:    0.6.2
 */
