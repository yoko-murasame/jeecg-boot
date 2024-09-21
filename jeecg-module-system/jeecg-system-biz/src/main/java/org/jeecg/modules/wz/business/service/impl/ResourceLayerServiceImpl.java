
package org.jeecg.modules.wz.business.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.AllArgsConstructor;


import org.design.core.tool.node.ForestNodeMerger;
import org.design.core.tool.utils.Func;
import org.jeecg.modules.wz.business.base.BaseServiceImpl;
import org.jeecg.modules.wz.business.entity.ResourceLayer;
import org.jeecg.modules.wz.business.entity.RoleLayer;
import org.jeecg.modules.wz.business.mapper.ResourceLayerMapper;
import org.jeecg.modules.wz.business.service.IResourceLayerService;
import org.jeecg.modules.wz.business.service.IRoleLayerService;


import org.jeecg.modules.wz.cityBrain.api.service.IResourceFileService;
import org.jeecg.modules.wz.cityBrain.api.vo.ResourceFileVO2;
import org.jeecg.modules.wz.business.vo.ResourceLayerVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 城市大脑-资源图层 服务实现类
 *
 * @author Blade
 * @since 2021-06-28
 */
@Service
@AllArgsConstructor
public class ResourceLayerServiceImpl extends BaseServiceImpl<ResourceLayerMapper, ResourceLayer> implements IResourceLayerService {

    IResourceFileService fileService;

    IRoleLayerService roleLayerService;

    @Override
    public IPage<ResourceLayerVO> selectResourceLayerPage(IPage<ResourceLayerVO> page, ResourceLayerVO resourceLayer) {
        return page.setRecords(baseMapper.selectResourceLayerPage(page, resourceLayer));
    }

    @Override
    public List<ResourceLayerVO> tree() {
        return ForestNodeMerger.merge(baseMapper.tree());
    }

    @Override
    public List<ResourceLayerVO> grantTree(String roleId, String code) {
        // todo 暂时先去除数据授权
        List<ResourceLayerVO> list;
//        if ("0".equals(roleId)) {
//            list = baseMapper.grantTree();
//        } else {
            list = baseMapper.grantTreeByRole(Func.toLongList(roleId), code);

            if (CollectionUtil.isNotEmpty(list)) {
                List<Long> ids = list.stream().map(ResourceLayerVO::getId).collect(Collectors.toList());

                Map<Long, ResourceFileVO2> map = fileService.resourceIdMapFile(ids);

                for (ResourceLayerVO resourceLayerVO : list) {
                    resourceLayerVO.setFiles(map.get(resourceLayerVO.getId()));
                }
            }
//        }

        return ForestNodeMerger.merge(list);
    }

    @Override
    public List<String> roleTreeKeys(String roleIds) {
        List<RoleLayer> roleLayers = roleLayerService.list(Wrappers.<RoleLayer>query().lambda().in(RoleLayer::getAppRoleId, Func.toLongList(roleIds)));
        return roleLayers.stream().map(roleLayer -> Func.toStr(roleLayer.getResourceLayerId())).collect(Collectors.toList());
    }

    @Override
    public String getTopic(ResourceLayer resourceLayer) {

        return baseMapper.getTopic(resourceLayer.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean copy(List<Long> longList) {
        List<ResourceLayer> layers = this.listByIds(longList);
        if (layers.isEmpty()) {
            return true;
        }
        this.doRecursionCopy(layers, 0L);
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteLogicRecursion(List<Long> longList) {
        if (longList.isEmpty()) {
            return true;
        }
        this.deleteLogic(longList);
        // 查询孩子
        LambdaQueryWrapper<ResourceLayer> wp = Wrappers.lambdaQuery(ResourceLayer.class).in(ResourceLayer::getParentId, longList);
        List<ResourceLayer> child = this.list(wp);
        if (!child.isEmpty()) {
            this.deleteLogicRecursion(child.stream().map(ResourceLayer::getId).collect(Collectors.toList()));
        }
        return true;
    }

    private void doRecursionCopy(List<ResourceLayer> layers, Long parentId) {
        if (layers.isEmpty()) {
            return;
        }
        layers.forEach(l -> {
            // 首节点加个copy标识
            if (Objects.equals(0L, parentId)) {
                l.setName(l.getName() + "_copy");
                l.setCode(l.getCode() + "_copy");
                l.setSort(Optional.ofNullable(l.getSort()).orElse(1) + 1);
            } else {
                l.setParentId(parentId);
            }
            Long oldId = l.getId();
            l.setId(null);
            // 保存复制节点
            this.save(l);
            Long newId = l.getId();
            // 查找孩子
            LambdaQueryWrapper<ResourceLayer> wp = Wrappers.lambdaQuery(ResourceLayer.class).eq(ResourceLayer::getParentId, oldId);
            List<ResourceLayer> child = this.list(wp);
            this.doRecursionCopy(child, newId);
        });
    }
}
