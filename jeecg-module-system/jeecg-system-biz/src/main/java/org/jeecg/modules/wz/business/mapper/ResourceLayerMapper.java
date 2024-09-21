
package org.jeecg.modules.wz.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.wz.business.entity.ResourceLayer;
import org.jeecg.modules.wz.business.vo.ResourceLayerVO;


import java.util.List;

/**
 * 城市大脑-资源图层 Mapper 接口
 *
 * @author Blade
 * @since 2021-06-28
 */
public interface ResourceLayerMapper extends BaseMapper<ResourceLayer> {

    /**
     * 自定义分页
     *
     * @param page
     * @param resourceLayer
     * @return
     */
    List<ResourceLayerVO> selectResourceLayerPage(IPage page, ResourceLayerVO resourceLayer);


    List<ResourceLayerVO> tree();

    List<ResourceLayerVO> grantTree();

    List<ResourceLayerVO> grantTreeByRole(List<Long> toLongList, @Param("code")String code);

    String getTopic(Long id);
}
