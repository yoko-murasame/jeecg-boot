package org.jeecg.modules.sakuga.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.jeecg.modules.sakuga.entity.SakugaContent;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: 作画内容管理
 * @Author: jeecg-boot
 * @Date:   2023-05-06
 * @Version: V1.0
 */
public interface ISakugaContentService extends IService<SakugaContent> {

    IPage<SakugaContent> pageHighlight(SakugaContent sakugaContent, Integer pageNo, Integer pageSize, HttpServletRequest req);
}
