package org.jeecg.modules.online.graphreport.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.online.graphreport.entity.OnlGraphreportHead;

/* loaded from: hibernate-common-ol-5.4.74.jar:org/jeecg/modules/online/graphreport/mapper/OnlGraphreportHeadMapper.class */
public interface OnlGraphreportHeadMapper extends BaseMapper<OnlGraphreportHead> {
    List<Map<String, Object>> executeSelete(@Param("sql") String str);
}
