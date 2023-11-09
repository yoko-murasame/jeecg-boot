package org.jeecg.modules.technical.sample.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.technical.sample.entity.Project;
import org.springframework.stereotype.Repository;

/**
 * @Description: 项目表_温州市放心征迁依法程序监管系统
 * @Author: jeecg-boot
 * @Date:   2021-11-20
 * @Version: V1.0
 */
@Repository("TechnicalSampleProjectMapper")
public interface ProjectMapper extends BaseMapper<Project> {

}
