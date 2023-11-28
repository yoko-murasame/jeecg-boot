package org.jeecg.config.thirdapp;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 第三方APP配置
 *
 * @author sunjianlei
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "third-app.type")
public class ThirdAppTypeConfig {

    public static final String THIRD_APP_TYPE = "THIRD_APP_TYPE";
    public static final String TYPE_DINGTALK = "DINGTALK";
    public static final String TYPE_WECHAT_ENTERPRISE = "WECHAT_ENTERPRISE";
    public static final String TYPE_ALL = "ALL";

    /**
     * 对应企业微信配置
     */
    private ThirdAppTypeItemVo WECHAT_ENTERPRISE;
    /**
     * 对应钉钉配置
     */
    private ThirdAppTypeItemVo DINGTALK;

}
