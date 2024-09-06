package org.jeecg.modules.wz.cityBrain.api.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jeecg.modules.wz.cityBrain.api.entity.ResourceFile;


/**
 * 数据传输对象实体类
 *
 * @author -Circle
 * @since 2021-09-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ResourceFileVO对象", description = "ResourceFileVO对象")
public class ResourceFileVO extends ResourceFile {
    private static final long serialVersionUID = 1L;
}
