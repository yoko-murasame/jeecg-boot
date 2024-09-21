package org.jeecg.modules.online.cgreport.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.online.cgreport.entity.OnlCgreportHead;
import org.jeecg.modules.online.cgreport.entity.OnlCgreportParam;

import java.util.List;
import java.util.Map;

/* loaded from: hibernate-common-ol-5.4.74(2).jar:org/jeecg/modules/online/cgreport/mapper/OnlCgreportHeadMapper.class */
public interface OnlCgreportHeadMapper extends BaseMapper<OnlCgreportHead> {
    List<Map<String, Object>> executeSelect(@Param("sql") String str);

    List<Map<?, ?>> executeSelete(@Param("sql") String str);

    IPage<Map<String, Object>> selectPageBySql(Page<Map<String, Object>> page, @Param("sqlStr") String str);

    Long queryCountBySql(@Param("sql") String str);

    Map<String, Object> queryCgReportMainConfig(@Param("reportId") String str);

    List<Map<String, Object>> queryCgReportItems(@Param("cgrheadId") String str);

    List<OnlCgreportParam> queryCgReportParams(@Param("cgrheadId") String str);
}
