package org.jeecg.modules.ztb.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.ztb.entity.ZtbXedj;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: 招投标-小额登记
 */
public interface IZtbXedjService extends IService<ZtbXedj> {

    <E extends IPage<ZtbXedj>> E pageCustom(E page, @Param(Constants.WRAPPER) Wrapper<ZtbXedj> queryWrapper);

    List<ZtbXedj> listCustom(Wrapper<ZtbXedj> queryWrapper);

    ZtbXedj getOneCustom(Wrapper<ZtbXedj> queryWrapper);

    ZtbXedj getByIdCustom(Serializable id);

}
