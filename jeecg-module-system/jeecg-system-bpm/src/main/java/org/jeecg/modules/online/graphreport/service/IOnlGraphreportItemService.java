package org.jeecg.modules.online.graphreport.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.online.graphreport.entity.OnlGraphreportItem;

import java.util.List;

/* loaded from: hibernate-common-ol-5.4.74.jar:org/jeecg/modules/online/graphreport/service/IOnlGraphreportItemService.class */
public interface IOnlGraphreportItemService extends IService<OnlGraphreportItem> {
    List<OnlGraphreportItem> selectByMainId(String str);
}
