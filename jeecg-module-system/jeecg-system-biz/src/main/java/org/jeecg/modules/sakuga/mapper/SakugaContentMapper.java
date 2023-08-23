package org.jeecg.modules.sakuga.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.sakuga.entity.SakugaContent;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 作画内容管理
 * @Author: jeecg-boot
 * @Date:   2023-05-06
 * @Version: V1.0
 */
public interface SakugaContentMapper extends BaseMapper<SakugaContent> {

    IPage<SakugaContent> pageHighlight(Page<SakugaContent> page, @Param(Constants.WRAPPER) QueryWrapper<SakugaContent> queryWrapper, @Param("realContent") String realContent);
}
