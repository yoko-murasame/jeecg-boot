package org.jeecg.modules.workflow.entity;

import lombok.Data;

import java.util.Date;

/**
 * Jeecg的流程超时类
 *
 * @author Yoko
 * @since 2022/7/18 10:46
 */
@Data
public class ActRuTaskDTO {

    private Date createTime;
    private String id;
    private String name;
    private String assignee;
    private String owner;
    private String procInstId;
    private String executionId;
    private Integer nodeTimeout;
    private String businessId;

}
