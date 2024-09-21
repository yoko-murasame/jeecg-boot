
package org.jeecg.modules.wz.business.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.design.core.mp.base.BaseService;
import org.jeecg.modules.wz.business.entity.ResourceLayer;
import org.jeecg.modules.wz.business.vo.ResourceLayerVO;


import java.util.List;

/**
 * 城市大脑-资源图层 服务类
 *
 * @author Blade
 * @since 2021-06-28
 */
public interface IResourceLayerService extends BaseService<ResourceLayer> {

    /**
     * 自定义分页
     *
     * @param page
     * @param resourceLayer
     * @return
     */
    IPage<ResourceLayerVO> selectResourceLayerPage(IPage<ResourceLayerVO> page, ResourceLayerVO resourceLayer);

    List<ResourceLayerVO> tree();

    List<ResourceLayerVO> grantTree(String roldIds, String code);

    List<String> roleTreeKeys(String roleIds);

    String getTopic(ResourceLayer resourceLayer);

    boolean copy(List<Long> longList);

    boolean deleteLogicRecursion(List<Long> longList);
}
