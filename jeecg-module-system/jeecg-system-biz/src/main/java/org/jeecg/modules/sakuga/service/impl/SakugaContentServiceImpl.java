package org.jeecg.modules.sakuga.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.sakuga.entity.SakugaContent;
import org.jeecg.modules.sakuga.mapper.SakugaContentMapper;
import org.jeecg.modules.sakuga.service.ISakugaContentService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: 作画内容管理
 * @Author: jeecg-boot
 * @Date:   2023-05-06
 * @Version: V1.0
 */
@Service
public class SakugaContentServiceImpl extends ServiceImpl<SakugaContentMapper, SakugaContent> implements ISakugaContentService {

    @Override
    public IPage<SakugaContent> pageHighlight(SakugaContent sakugaContent, Integer pageNo, Integer pageSize, HttpServletRequest req) {

        String content = sakugaContent.getContent();
        IPage<SakugaContent> pageList = null;
        Page<SakugaContent> page = new Page<SakugaContent>(pageNo, pageSize);
        if (StringUtils.hasText(content)) {
            sakugaContent.setContent(null);
            QueryWrapper<SakugaContent> queryWrapper = QueryGenerator.initQueryWrapper(sakugaContent, req.getParameterMap());
            String realContent = content.replaceAll("[\\s\\n]+", "&");
            // 分词检索
            // queryWrapper.apply("content @@ to_tsquery('chinese', '" + realContent + "')"); // 旧方式，不需要创建向量字段，但每次查询都会分词导致效率低
            queryWrapper.apply("content_tsv @@ to_tsquery('chinese', '" + realContent + "')"); // 新方式，基于向量字段，并且走索引
            pageList = this.baseMapper.pageHighlight(page, queryWrapper, realContent);
        } else {
            QueryWrapper<SakugaContent> queryWrapper = QueryGenerator.initQueryWrapper(sakugaContent, req.getParameterMap());
            pageList = this.page(page, queryWrapper);
        }

        return pageList;
    }
}
