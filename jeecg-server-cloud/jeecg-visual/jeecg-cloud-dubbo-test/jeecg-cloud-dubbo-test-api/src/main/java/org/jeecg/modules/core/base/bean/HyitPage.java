package org.jeecg.modules.core.base.bean;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * 分页实体-HyitPage
 * FIXME 仅供参考，实际项目中应该使用hyit-core包中的HyitPage
 */
public class HyitPage<T> extends Page<T> {
    public static Integer DEFAULT_SIZE = 20;
}
