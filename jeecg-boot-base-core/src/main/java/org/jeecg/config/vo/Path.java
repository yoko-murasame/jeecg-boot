package org.jeecg.config.vo;

import java.io.Serializable;

/**
 *
 * @author: scott
 * @date: 2022年04月18日 20:35
 */
public class Path implements Serializable {

    private static final long serialVersionUID = 1L;

    private String upload;
    private String webapp;

    public String getUpload() {
        return upload;
    }

    public void setUpload(String upload) {
        this.upload = upload;
    }

    public String getWebapp() {
        return webapp;
    }

    public void setWebapp(String webapp) {
        this.webapp = webapp;
    }
}
