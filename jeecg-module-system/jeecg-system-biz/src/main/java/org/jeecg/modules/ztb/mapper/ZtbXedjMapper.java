package org.jeecg.modules.ztb.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.ztb.entity.ZtbXedj;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: 招投标-小额登记
 */
public interface ZtbXedjMapper extends BaseMapper<ZtbXedj> {
    <E extends IPage<ZtbXedj>> E selectPageCustom(E page, @Param(Constants.WRAPPER) Wrapper<ZtbXedj> wrapper);

    List<ZtbXedj> selectListCustom(@Param(Constants.WRAPPER) Wrapper<ZtbXedj> queryWrapper);

    ZtbXedj selectOneCustom(@Param(Constants.WRAPPER) Wrapper<ZtbXedj> queryWrapper);

    ZtbXedj selectByIdCustom(@Param("id") Serializable id);
}
