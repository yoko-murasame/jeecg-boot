package org.jeecg.modules.wz.cityBrain.api.vo;

import lombok.Data;

/**
 * @Author: -Circle
 * @Date: 2021/9/16 17:43
 * @Description:
 */
@Data
public class FieldLayerVO {
    /**
     * 字段描述
     */
    private String caption;
    /**
     * 字段名称
     */
    private String name;
    /**
     * 排序
     */
    private Integer sort;
}
