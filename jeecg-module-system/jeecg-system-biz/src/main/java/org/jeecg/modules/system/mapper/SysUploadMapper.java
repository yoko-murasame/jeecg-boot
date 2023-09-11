package org.jeecg.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.jeecg.modules.system.entity.SysUpload;

public interface SysUploadMapper extends BaseMapper<SysUpload> {

    @Select("SELECT * FROM sys_upload WHERE md5 = #{md5} LIMIT 1")
    SysUpload queryByMd5(@Param("md5") String md5);

}
