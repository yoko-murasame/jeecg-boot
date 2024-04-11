package org.jeecg.modules.online.graphreport.service;

import com.baomidou.mybatisplus.extension.service.IService;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.jeecg.modules.online.graphreport.entity.OnlGraphreportHead;
import org.jeecg.modules.online.graphreport.entity.OnlGraphreportItem;

/* loaded from: hibernate-common-ol-5.4.74.jar:org/jeecg/modules/online/graphreport/service/IOnlGraphreportHeadService.class */
public interface IOnlGraphreportHeadService extends IService<OnlGraphreportHead> {
    List<Map<String, Object>> executeSelete(String str);

    void saveMain(OnlGraphreportHead onlGraphreportHead, List<OnlGraphreportItem> list);

    void updateMain(OnlGraphreportHead onlGraphreportHead, List<OnlGraphreportItem> list);

    void delMain(String str);

    void delBatchMain(Collection<? extends Serializable> collection);

    OnlGraphreportHead queryByCode(String str);

    Map<String, Object> queryChartConfig(OnlGraphreportHead onlGraphreportHead);
}
