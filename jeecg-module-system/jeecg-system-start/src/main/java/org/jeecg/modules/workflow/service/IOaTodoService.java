package org.jeecg.modules.workflow.service;

import java.util.List;

public interface IOaTodoService {
    // 发送待办
    void sendTodoAndSave(String title, List<String> usernames, List<String> phones, String processInstanceId);

    // 完成待办
    void finishTodo(String processInstanceId);

}
