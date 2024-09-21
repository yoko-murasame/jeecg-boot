package org.jeecg.config.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author taoYan
 * @Date 2022/7/5 21:16
 **/
@Data
public class DomainUrl implements Serializable {

    private static final long serialVersionUID = 1L;

    private String pc;

    private String app;
}
