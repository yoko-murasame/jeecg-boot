package cn.com.hyit.config.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 查询多个字典时用到
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DictModelMany extends DictModel implements Serializable {

    private static final long serialVersionUID = -4220548251998618021L;

    /**
     * 字典code，根据多个字段code查询时才用到，用于区分不同的字典选项
     */
    private String dictCode;

}
