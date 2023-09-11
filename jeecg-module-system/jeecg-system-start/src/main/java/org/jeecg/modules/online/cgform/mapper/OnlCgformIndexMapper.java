package org.jeecg.modules.online.cgform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.online.cgform.entity.OnlCgformIndex;

public abstract interface OnlCgformIndexMapper extends BaseMapper<OnlCgformIndex>
{
  public abstract int queryIndexCount(@Param("sqlStr") String paramString);
}

/* Location:           C:\Users\tx\Desktop\hibernate-common-ol-5.4.74(2).jar
 * Qualified Name:     org.jeecg.modules.online.cgform.mapper.OnlCgformIndexMapper
 * JD-Core Version:    0.6.2
 */