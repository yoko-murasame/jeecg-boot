package org.jeecg.modules.technical.sample.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.technical.sample.entity.Project;
import org.springframework.stereotype.Repository;

/**
 * @Description: 样例项目
 * @Author: jeecg-boot
 * @Date:   2021-11-20
 * @Version: V1.0
 */
@Repository("TechnicalSampleProjectMapper")
public interface ProjectMapper extends BaseMapper<Project> {

}
