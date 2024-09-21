package org.jeecg.modules.workflow.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.workflow.entity.ProcessHisDTO;
import org.jeecg.modules.workflow.entity.TaskEntity;

import java.util.List;

/**
 * @author Yoko
 */
public interface TaskEntityMapper extends BaseMapper<TaskEntity> {
    /**
     * 查询所有代办，分页接口
     */
    <E extends IPage<TaskEntity>> E taskPage(E page, @Param(Constants.WRAPPER) Wrapper<TaskEntity> wrapper);
    List<TaskEntity> taskList(@Param(Constants.WRAPPER) Wrapper<TaskEntity> wrapper);
    /**
     * 获取我的待办列表-全条件版本
     */
    <E extends IPage<TaskEntity>> E myTaskListV2(E page, @Param(Constants.WRAPPER) Wrapper<TaskEntity> wrapper, @Param("username") String username);
    List<TaskEntity> myTaskListV2(@Param(Constants.WRAPPER) Wrapper<TaskEntity> wrapper, @Param("username") String username);
    <E extends IPage<TaskEntity>> E taskHistoryListV2(E page, @Param(Constants.WRAPPER) Wrapper<TaskEntity> wrapper, @Param("username") String username);

    /**
     * 获取我发起的流程列表-全条件版本
     */
    <E extends IPage<ProcessHisDTO>> E myApplyProcessListV2(E page, @Param(Constants.WRAPPER) QueryWrapper<ProcessHisDTO> queryWrapper);

    <E extends IPage<TaskEntity>> E taskAllCcHistoryListV2(E page, @Param(Constants.WRAPPER) Wrapper<TaskEntity> wrapper, @Param("username") String username);
}
