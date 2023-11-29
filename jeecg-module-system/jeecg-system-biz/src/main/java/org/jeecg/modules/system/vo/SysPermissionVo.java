package org.jeecg.modules.system.vo;

import lombok.Data;

@Data
public class SysPermissionVo {

    private String perms;

    /**
     * 组件
     */
    private String component;

    /**
     * 删除状态 0正常 1已删除
     */
    private Integer delFlag = 0;

    /**
     * 按钮权限状态(0无效1有效)
     */
    private java.lang.String status;
}
