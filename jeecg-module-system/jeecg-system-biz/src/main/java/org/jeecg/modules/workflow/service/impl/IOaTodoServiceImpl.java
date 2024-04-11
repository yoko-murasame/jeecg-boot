package org.jeecg.modules.workflow.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.workflow.service.IOaTodoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class IOaTodoServiceImpl implements IOaTodoService {
    @Override
    public void sendTodoAndSave(String title, List<String> usernames, List<String> phones, String processInstanceId) {
        // todo 对接自定义oa待办接口
    }

    @Override
    public void finishTodo(String processInstanceId) {
        // todo 对接自定义oa待办接口
    }
}
