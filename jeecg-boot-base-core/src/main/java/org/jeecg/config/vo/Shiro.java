package org.jeecg.config.vo;

import java.io.Serializable;

/**
 * @Description: TODO
 * @author: scott
 * @date: 2022年01月21日 14:23
 */
public class Shiro implements Serializable {

    private static final long serialVersionUID = 1L;

    private String excludeUrls = "";

    public String getExcludeUrls() {
        return excludeUrls;
    }

    public void setExcludeUrls(String excludeUrls) {
        this.excludeUrls = excludeUrls;
    }
}
