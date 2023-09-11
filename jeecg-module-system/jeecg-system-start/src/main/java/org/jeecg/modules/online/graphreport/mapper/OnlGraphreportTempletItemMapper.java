package org.jeecg.modules.online.graphreport.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.online.graphreport.entity.OnlGraphreportTempletItem;

/* loaded from: hibernate-common-ol-5.4.74.jar:org/jeecg/modules/online/graphreport/mapper/OnlGraphreportTempletItemMapper.class */
public interface OnlGraphreportTempletItemMapper extends BaseMapper<OnlGraphreportTempletItem> {
    boolean deleteByMainId(@Param("mainId") String str);

    List<OnlGraphreportTempletItem> selectByMainId(@Param("mainId") String str);
}
