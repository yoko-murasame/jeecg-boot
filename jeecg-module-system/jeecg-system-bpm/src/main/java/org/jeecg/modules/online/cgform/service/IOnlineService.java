package org.jeecg.modules.online.cgform.service;

import com.alibaba.fastjson.JSONObject;
import org.jeecg.modules.online.cgform.entity.OnlCgformButton;
import org.jeecg.modules.online.cgform.entity.OnlCgformEnhanceJs;
import org.jeecg.modules.online.cgform.entity.OnlCgformHead;
import org.jeecg.modules.online.cgform.model.OnlComplexModel;

import java.util.List;

public abstract interface IOnlineService
{
   abstract OnlComplexModel queryOnlineConfig(OnlCgformHead paramOnlCgformHead, String paramString);

   abstract JSONObject queryOnlineFormObj(OnlCgformHead paramOnlCgformHead, OnlCgformEnhanceJs paramOnlCgformEnhanceJs);

   abstract JSONObject queryOnlineFormObj(OnlCgformHead paramOnlCgformHead, String paramString);

   abstract List<OnlCgformButton> queryFormValidButton(String paramString);

  abstract JSONObject queryOnlineFormItem(OnlCgformHead paramOnlCgformHead, String paramString);

  abstract JSONObject queryFlowOnlineFormItem(OnlCgformHead paramOnlCgformHead, String paramString1, String paramString2);

  abstract String queryEnahcneJsString(String paramString1, String paramString2);
}
