package org.jeecg.config.shiro.third;

import lombok.Data;

/**
 * 第三方系统简单配置型认证配置
 */
@Data
public class ThirdSystemProperty {

    /**
     * 第三方系统名称
     */
    private String name;
    /**
     * 第三方系统key
     */
    private String key;
    /**
     * 第三方系统可访问的url
     */
    private String urls;

}
