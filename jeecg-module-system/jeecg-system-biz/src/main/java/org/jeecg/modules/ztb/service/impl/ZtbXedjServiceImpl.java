package org.jeecg.modules.ztb.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.ztb.entity.ZtbXedj;
import org.jeecg.modules.ztb.mapper.ZtbXedjMapper;
import org.jeecg.modules.ztb.service.IZtbXedjService;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: 招投标-小额登记
 * @Author: jeecg-boot
 * @Date:   2023-12-21
 * @Version: V1.0
 */
@Service
public class ZtbXedjServiceImpl extends ServiceImpl<ZtbXedjMapper, ZtbXedj> implements IZtbXedjService {

    @Override
    public <E extends IPage<ZtbXedj>> E pageCustom(E page, Wrapper<ZtbXedj> queryWrapper) {
        return this.baseMapper.selectPageCustom(page, queryWrapper);
    }

    @Override
    public List<ZtbXedj> listCustom(Wrapper<ZtbXedj> queryWrapper){
        return this.baseMapper.selectListCustom(queryWrapper);
    }

    @Override
    public ZtbXedj getOneCustom(Wrapper<ZtbXedj> queryWrapper) {
        return this.baseMapper.selectOneCustom(queryWrapper);
    }

    @Override
    public ZtbXedj getByIdCustom(Serializable id) {
        return this.baseMapper.selectByIdCustom(id);
    }

}
