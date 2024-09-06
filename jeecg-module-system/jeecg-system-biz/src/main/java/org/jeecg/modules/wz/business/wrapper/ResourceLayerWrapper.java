
package org.jeecg.modules.wz.business.wrapper;

import org.design.core.mp.support.BaseEntityWrapper;

import org.design.core.tool.node.ForestNodeMerger;
import org.design.core.tool.utils.BeanUtil;
import org.jeecg.modules.wz.business.entity.ResourceLayer;
import org.jeecg.modules.wz.business.vo.ResourceLayerVO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 城市大脑-资源图层包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2021-06-28
 */
public class ResourceLayerWrapper extends BaseEntityWrapper<ResourceLayer, ResourceLayerVO> {

    public static ResourceLayerWrapper build() {
        return new ResourceLayerWrapper();
    }

    @Override
    public ResourceLayerVO entityVO(ResourceLayer resourceLayer) {
        ResourceLayerVO resourceLayerVO = BeanUtil.copy(resourceLayer, ResourceLayerVO.class);

        return resourceLayerVO;
    }

    public List<ResourceLayerVO> listNodeVO(List<ResourceLayer> list) {
        List<ResourceLayerVO> collect = list.stream().map(resourceLayer -> BeanUtil.copy(resourceLayer, ResourceLayerVO.class)).collect(Collectors.toList());
        return ForestNodeMerger.merge(collect);
    }
}
