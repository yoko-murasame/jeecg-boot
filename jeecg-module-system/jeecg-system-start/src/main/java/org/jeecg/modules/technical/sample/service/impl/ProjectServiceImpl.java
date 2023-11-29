package org.jeecg.modules.technical.sample.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.technical.sample.entity.Project;
import org.jeecg.modules.technical.sample.mapper.ProjectMapper;
import org.jeecg.modules.technical.sample.service.IProjectService;
import org.springframework.stereotype.Service;

/**
 * @Description: 样例项目
 * @Author: jeecg-boot
 * @Date:   2021-11-20
 * @Version: V1.0
 */
@Service("TechnicalSampleProjectServiceImpl")
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements IProjectService {

}
