package org.jeecg.modules.online.cgform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.online.cgform.entity.OnlCgformButton;

/* loaded from: hibernate-common-ol-5.4.74(2).jar:org/jeecg/modules/online/cgform/service/IOnlCgformButtonService.class */
public interface IOnlCgformButtonService extends IService<OnlCgformButton> {
  void saveButton(OnlCgformButton onlCgformButton);
}