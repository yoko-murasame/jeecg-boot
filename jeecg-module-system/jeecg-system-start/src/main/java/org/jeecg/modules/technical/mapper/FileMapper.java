package org.jeecg.modules.technical.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.jeecg.modules.technical.entity.File;

import java.util.List;

/**
 * com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor是导致net.sf.jsqlparser.parser.ParseException的原因
 * 主要是 List<String> 这个类型的字段会产生影响
 */
@InterceptorIgnore(tenantLine = "true")
public interface FileMapper extends BaseMapper<File> {

    @Results(value = {
            @Result(column = "file_order",property = "order"),
            @Result(column = "oss_file",property = "ossFile",typeHandler = JacksonTypeHandler.class),
            @Result(column = "bimface_file",property = "bimfaceFile",typeHandler = JacksonTypeHandler.class)
    })
    @Select("select tf.* from technical_file tf INNER JOIN (SELECT max(version) max,name from technical_file where folder_id=#{folderId} AND enabled=1 GROUP BY name ) temp on tf.version=temp.max AND tf.name=temp.name where folder_id=#{folderId} AND enabled=1 ORDER BY tf.update_time desc")
    List<File> findByFolder(String folderId);
}
