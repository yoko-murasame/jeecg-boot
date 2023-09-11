package org.jeecg.modules.online.cgform.service;

import com.alibaba.fastjson.JSONObject;
import java.util.List;
import org.jeecg.modules.online.cgform.entity.OnlCgformButton;
import org.jeecg.modules.online.cgform.entity.OnlCgformEnhanceJs;
import org.jeecg.modules.online.cgform.entity.OnlCgformHead;
import org.jeecg.modules.online.cgform.model.OnlComplexModel;

public abstract interface IOnlineService
{
  public abstract OnlComplexModel queryOnlineConfig(OnlCgformHead paramOnlCgformHead, String paramString);

  public abstract JSONObject queryOnlineFormObj(OnlCgformHead paramOnlCgformHead, OnlCgformEnhanceJs paramOnlCgformEnhanceJs);

  public abstract JSONObject queryOnlineFormObj(OnlCgformHead paramOnlCgformHead, String paramString);

  public abstract List<OnlCgformButton> queryFormValidButton(String paramString);

  public abstract JSONObject queryOnlineFormItem(OnlCgformHead paramOnlCgformHead, String paramString);

  public abstract JSONObject queryFlowOnlineFormItem(OnlCgformHead paramOnlCgformHead, String paramString1, String paramString2);

  public abstract String queryEnahcneJsString(String paramString1, String paramString2);
}

/* Location:           C:\Users\tx\Desktop\hibernate-common-ol-5.4.74(2).jar
 * Qualified Name:     org.jeecg.modules.online.cgform.service.IOnlineService
 * JD-Core Version:    0.6.2
 */