package org.jeecg.modules.online.cgform.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jeecg.common.system.vo.SysOnlListQueryModel;

/**
 * Online列表查询实体model
 */
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "Online列表查询实体model")
@Data
public class OnlListQueryModel extends SysOnlListQueryModel {

}
