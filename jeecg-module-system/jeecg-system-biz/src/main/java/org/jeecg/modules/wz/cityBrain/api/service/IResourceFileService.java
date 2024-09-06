package org.jeecg.modules.wz.cityBrain.api.service;

import org.design.core.mp.base.BaseService;
import org.jeecg.modules.wz.cityBrain.api.entity.ResourceFile;
import org.jeecg.modules.wz.cityBrain.api.vo.ResourceFileVO2;

import java.util.List;
import java.util.Map;


/**
 * 资源文件
 *
 * @author Circle
 * @since 2021-09-30
 */
public interface IResourceFileService extends BaseService<ResourceFile> {

    List<ResourceFile> getByLayerId(ResourceFile resourceFile);

    Map<Long, ResourceFileVO2> resourceIdMapFile(List<Long> ids);
}
