package org.jeecg.modules.appAuthorize.entity.VO;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class getTokenVO {
    @NotBlank
    private String appKey;
    @NotBlank
    private String appSecret;
}
