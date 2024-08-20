package org.jeecg.modules.listener.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.listener.entity.OaOfficialdocDeUser;

public interface OaOfficialdocDeUserMapper extends BaseMapper<OaOfficialdocDeUser> {

    OaOfficialdocDeUser selectByDepartId(@Param("departId") String departId);
}
