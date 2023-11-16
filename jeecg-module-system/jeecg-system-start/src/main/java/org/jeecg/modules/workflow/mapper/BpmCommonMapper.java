package org.jeecg.modules.workflow.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.workflow.entity.ActRuTaskDTO;
import org.jeecg.modules.workflow.entity.TaskEntity;
import org.jeecg.modules.workflow.entity.TaskQueryVO;

import java.util.List;

/**
 * @author Yoko
 */
public interface BpmCommonMapper extends BaseMapper<Object> {

    List<ActRuTaskDTO> getTimeoutTask();

    /**
     * 新的流程超时提醒sql，修复6个联系单的process_node_code一样时，左连接导致6条数据，从而发送6条冗余数据的问题
     *
     * @param
     * @return java.util.List<org.jeecg.modules.workflow.entity.ActRuTaskDTO>
     * @author Yoko
     * @since 2022/7/20 10:26
     */
    List<ActRuTaskDTO> getTimeoutTaskFixed();

    /**
     * 根据业务id获取当前运行中的流程任务
     *
     * @param ids
     * @return java.util.List<org.jeecg.modules.workflow.entity.ActRuTaskDTO>
     * @author Yoko
     * @since 2022/9/6 18:16
     */
    List<ActRuTaskDTO> getRunningTaskByBusinessIds(@Param("ids") List<String> ids);

    /**
     * 查询所有代办
     */
    @Deprecated
    List<TaskEntity> taskDTOAllList(@Param("vo") TaskQueryVO taskQueryVO);
}
