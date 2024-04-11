package org.jeecg.modules.online.graphreport.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.online.graphreport.entity.OnlGraphreportItem;

/* loaded from: hibernate-common-ol-5.4.74.jar:org/jeecg/modules/online/graphreport/mapper/OnlGraphreportItemMapper.class */
public interface OnlGraphreportItemMapper extends BaseMapper<OnlGraphreportItem> {
    boolean deleteByMainId(@Param("mainId") String str);

    List<OnlGraphreportItem> selectByMainId(@Param("mainId") String str);
}
