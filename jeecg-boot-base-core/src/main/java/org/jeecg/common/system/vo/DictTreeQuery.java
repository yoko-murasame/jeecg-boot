package org.jeecg.common.system.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 树型字典查询参数实体
 */
@Data
public class DictTreeQuery implements Serializable {

    private static final long serialVersionUID = -5886732227207390900L;

    private String dictCode;

    @ApiModelProperty(name = "结构码筛选(支持筛选到对应结构子树)")
    private String structCode;

}
