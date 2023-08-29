package org.jeecg.modules.online.graphreport.service;

import com.baomidou.mybatisplus.extension.service.IService;
import java.util.Map;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.online.graphreport.entity.OnlGraphreportHead;

/* loaded from: hibernate-common-ol-5.4.74.jar:org/jeecg/modules/online/graphreport/service/IOnlGraphreportApiService.class */
public interface IOnlGraphreportApiService extends IService<OnlGraphreportHead> {
    Map<String, Object> queryChartDataSource(OnlGraphreportHead onlGraphreportHead, String str);

    Result<?> getTempletChartsData(String str);
}
