package org.jeecg.modules.workflow.job;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.TaskService;
import org.jeecg.common.api.dto.message.TemplateMessageDTO;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.workflow.entity.ActRuTaskDTO;
import org.jeecg.modules.workflow.mapper.BpmCommonMapper;
import org.jeecg.modules.workflow.utils.RsaUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Consumer;

/**
 * 自定义流程超时监听实现
 *
 * @author Yoko
 * @since 2022/7/18 11:27
 */
@Slf4j
public class TimeOutTask implements Job {

    @Resource
    private TaskService taskService;
    @Resource
    private ISysBaseAPI sysBaseAPI;
    @Resource
    private BpmCommonMapper bpmCommonMapper;
    @Resource
    private RsaUtils rsaUtils;

    @Value("${spring.profiles.active}")
    private String env;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            log.info("----------【定时任务】--任务超时提醒开始--------------");
            // List<ActRuTaskDTO> timeoutTask = commonMapper.getTimeoutTask();
            List<ActRuTaskDTO> timeoutTask = bpmCommonMapper.getTimeoutTaskFixed();
            if (timeoutTask != null && timeoutTask.size() > 0) {
                // this.sendMessage(timeoutTask);
            }
            log.info("-----------【定时任务】--任务超时提醒结束---------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(List<ActRuTaskDTO> timeoutTask) {
        try {
            Iterator iterator = timeoutTask.iterator();

            while (iterator.hasNext()) {
                ActRuTaskDTO actRuTaskDTO = (ActRuTaskDTO) iterator.next();
                int nodeTimeout = actRuTaskDTO.getNodeTimeout();
                Date createTime = actRuTaskDTO.getCreateTime();
                if (this.judgeOvertime(createTime, nodeTimeout)) {
                    log.info("-------------任务超时提醒-----startTime=" + createTime.toString() + ",nodeTimeout=" + nodeTimeout);
                    LoginUser userInfo = sysBaseAPI.getUserByName(actRuTaskDTO.getAssignee());
                    Optional.ofNullable(userInfo).ifPresent(user -> {
                        String id = actRuTaskDTO.getId();
                        String bpmBizTitle = (String) taskService.getVariable(id, "bpm_biz_title");
                        HashMap map = new HashMap();
                        map.put("title", bpmBizTitle);
                        map.put("task", actRuTaskDTO.getName());
                        map.put("user", user.getRealname());
                        map.put("time", TimeOutTask.DATE_TIME_FORMAT.format(actRuTaskDTO.getCreateTime()));
                        TemplateMessageDTO messageDTO = new TemplateMessageDTO("系统", user.getUsername(), "任务超时提醒"
                                , map,
                                "bpm_chaoshi_tip");
                        sysBaseAPI.sendTemplateAnnouncement(messageDTO);
                        // 发送oa推送
                        this.insteadIf("prod".equals(env),
                                e -> rsaUtils.sendTodo("您有超时任务待处理！", user.getUsername(), user.getPhone()));
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void insteadIf(Boolean condition, Consumer consumer) {
        if (condition) {
            consumer.accept(null);
        }
    }

    private boolean judgeOvertime(Date createTime, Integer nodeTimeout) {
        boolean flag = false;

        try {
            if (nodeTimeout == null || createTime == null) {
                return flag;
            }

            Calendar current = Calendar.getInstance();
            Calendar createCalendar = Calendar.getInstance();
            createCalendar.setTime(createTime);
            int diff = TimeOutTask.dateDiff('h', current, createCalendar);
            if (diff >= nodeTimeout) {
                flag = true;
            }
        } catch (Exception e) {
        }

        return flag;
    }

    private static final long DAY_IN_MILLIS = 24 * 3600 * 1000;
    private static final long HOUR_IN_MILLIS = 3600 * 1000;
    private static final long MINUTE_IN_MILLIS = 60 * 1000;
    private static final long SECOND_IN_MILLIS = 1000;

    private static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static int dateDiff(char flag, Calendar calSrc, Calendar calDes) {

        long millisDiff = getMillis(calSrc) - getMillis(calDes);

        if (flag == 'y') {
            return (calSrc.get(Calendar.YEAR) - calDes.get(Calendar.YEAR));
        }

        if (flag == 'd') {
            return (int) (millisDiff / DAY_IN_MILLIS);
        }

        if (flag == 'h') {
            return (int) (millisDiff / HOUR_IN_MILLIS);
        }

        if (flag == 'm') {
            return (int) (millisDiff / MINUTE_IN_MILLIS);
        }

        if (flag == 's') {
            return (int) (millisDiff / SECOND_IN_MILLIS);
        }

        return 0;
    }

    public static long getMillis(Calendar cal) {
        // --------------------return cal.getTimeInMillis();
        return cal.getTime().getTime();
    }
}
