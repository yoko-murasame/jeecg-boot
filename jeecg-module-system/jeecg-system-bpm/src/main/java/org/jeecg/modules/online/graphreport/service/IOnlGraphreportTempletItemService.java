package org.jeecg.modules.online.graphreport.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.online.graphreport.entity.OnlGraphreportTempletItem;

import java.util.List;

/* loaded from: hibernate-common-ol-5.4.74.jar:org/jeecg/modules/online/graphreport/service/IOnlGraphreportTempletItemService.class */
public interface IOnlGraphreportTempletItemService extends IService<OnlGraphreportTempletItem> {
    List<OnlGraphreportTempletItem> selectByMainId(String str);
}
