package org.jeecg.modules.appAuthorize.entity.DO;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AppAuthorize {
    private String id;
    private String appKey;
    private String appSecret;
}
