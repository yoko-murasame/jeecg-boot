package org.jeecg.modules.wz.cityBrain.api.wrapper;

import org.design.core.mp.support.BaseEntityWrapper;
import org.design.core.tool.utils.BeanUtil;
import org.jeecg.modules.wz.cityBrain.api.entity.ResourceFile;
import org.jeecg.modules.wz.cityBrain.api.vo.ResourceFileVO;

/**
 * 数据传输对象实体类
 *
 * @author -Circle
 * @since 2021-09-30
 */
public class ResourceFileWrapper extends BaseEntityWrapper<ResourceFile, ResourceFileVO> {

    public static ResourceFileWrapper build() {
        return new ResourceFileWrapper();
    }

    @Override
    public ResourceFileVO entityVO(ResourceFile resourceFile) {
        ResourceFileVO resourceFileVO = BeanUtil.copy(resourceFile, ResourceFileVO.class);

        return resourceFileVO;
    }

}
