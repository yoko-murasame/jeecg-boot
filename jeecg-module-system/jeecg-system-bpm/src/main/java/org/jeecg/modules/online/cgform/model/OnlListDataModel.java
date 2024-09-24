package org.jeecg.modules.online.cgform.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jeecg.common.system.vo.SysOnlListDataModel;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;

import java.util.List;

/**
 * Online列表查询结果model
 */
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "Online列表查询结果model")
@Data
public class OnlListDataModel extends SysOnlListDataModel {

    @ApiModelProperty(value = "字段Meta")
    private List<OnlCgformField> fieldList;

}
