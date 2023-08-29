package org.jeecg.modules.online.graphreport.service;

import com.baomidou.mybatisplus.extension.service.IService;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import org.jeecg.modules.online.graphreport.entity.OnlGraphreportTemplet;
import org.jeecg.modules.online.graphreport.entity.OnlGraphreportTempletItem;

/* loaded from: hibernate-common-ol-5.4.74.jar:org/jeecg/modules/online/graphreport/service/IOnlGraphreportTempletService.class */
public interface IOnlGraphreportTempletService extends IService<OnlGraphreportTemplet> {
    void saveMain(OnlGraphreportTemplet onlGraphreportTemplet, List<OnlGraphreportTempletItem> list);

    void updateMain(OnlGraphreportTemplet onlGraphreportTemplet, List<OnlGraphreportTempletItem> list);

    void delMain(String str);

    void delBatchMain(Collection<? extends Serializable> collection);
}
