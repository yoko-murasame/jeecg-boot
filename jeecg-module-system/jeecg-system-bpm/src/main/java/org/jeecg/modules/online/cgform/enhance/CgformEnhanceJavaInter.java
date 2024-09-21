package org.jeecg.modules.online.cgform.enhance;

import com.alibaba.fastjson.JSONObject;
import org.jeecg.modules.online.config.exception.BusinessException;

import java.util.Map;

public abstract interface CgformEnhanceJavaInter
{
  @Deprecated
  public abstract int execute(String paramString, Map<String, Object> paramMap)
    throws BusinessException;

  public abstract int execute(String paramString, JSONObject paramJSONObject)
    throws BusinessException;
}

/* Location:           C:\Users\tx\Desktop\hibernate-common-ol-5.4.74(2).jar
 * Qualified Name:     org.jeecg.modules.online.cgform.enhance.CgformEnhanceJavaInter
 * JD-Core Version:    0.6.2
 */
