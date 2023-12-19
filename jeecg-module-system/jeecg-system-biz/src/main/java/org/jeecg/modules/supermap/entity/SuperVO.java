package org.jeecg.modules.supermap.entity;

import lombok.Data;

@Data
public class SuperVO {
    private String userName;
    private String password;
    private String clientType;
    private Long expiration;
}
