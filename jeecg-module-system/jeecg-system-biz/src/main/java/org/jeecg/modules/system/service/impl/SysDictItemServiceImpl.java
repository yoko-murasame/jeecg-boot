package org.jeecg.modules.system.service.impl;

import cn.hutool.core.collection.IterUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.system.entity.SysDict;
import org.jeecg.modules.system.entity.SysDictItem;
import org.jeecg.modules.system.mapper.SysDictItemMapper;
import org.jeecg.modules.system.mapper.SysDictMapper;
import org.jeecg.modules.system.service.ISysDictItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @Author zhangweijian
 * @since 2018-12-28
 */
@Service
public class SysDictItemServiceImpl extends ServiceImpl<SysDictItemMapper, SysDictItem> implements ISysDictItemService {

    @Autowired
    private SysDictItemMapper sysDictItemMapper;
    @Autowired
    private SysDictMapper sysDictMapper;

    @Override
    public List<SysDictItem> selectItemsByMainId(String mainId) {
        return sysDictItemMapper.selectItemsByMainId(mainId);
    }
    @Override
    public List<SysDictItem> selectItemsByCode(String code) {
        String id = sysDictMapper.selectOne(new QueryWrapper<SysDict>().eq("dict_code", code)).getId();
        List<SysDictItem> list = sysDictItemMapper.selectList(new QueryWrapper<SysDictItem>().eq("dict_id", id).orderByAsc("sort_order"));
        return list;
    }

    @Override
    public void buildLazyTree(List<SysDictItem> items) {
        if (null == items || items.isEmpty()) {
            return;
        }
        try {
            items.sort(Comparator.comparing(SysDictItem::getSortOrder));
        } catch (Exception ignore) {
        }
        // 通过父项找子项
        List<String> parentIds = items.stream().map(SysDictItem::getId).collect(Collectors.toList());
        List<SysDictItem> child = sysDictItemMapper.selectList(Wrappers.lambdaQuery(SysDictItem.class).in(SysDictItem::getParentId, parentIds));
        if (null != child && !child.isEmpty()) {
            Map<String, SysDictItem> pMap = IterUtil.toMap(items, SysDictItem::getId);
            child.forEach(item -> {
                SysDictItem parent = pMap.get(item.getParentId());
                if (null != parent) {
                    if (null == parent.getChildren()) {
                        parent.setChildren(new ArrayList<>());
                    }
                    parent.getChildren().add(item);
                }
            });
            items.forEach(item -> buildLazyTree(item.getChildren()));
        }
    }
}
