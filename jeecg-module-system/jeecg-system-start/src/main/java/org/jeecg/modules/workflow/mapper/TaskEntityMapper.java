package org.jeecg.modules.workflow.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.workflow.entity.TaskEntity;

/**
 * @author Yoko
 */
public interface TaskEntityMapper extends BaseMapper<TaskEntity> {
    /**
     * 查询所有代办，分页接口
     */
    <E extends IPage<TaskEntity>> E taskPage(E page, @Param(Constants.WRAPPER) Wrapper<TaskEntity> wrapper);
}
