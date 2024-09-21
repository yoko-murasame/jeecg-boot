package org.design.core.mp.support;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;
import java.util.stream.Collectors;

/* loaded from: bigdata-core-mybatis-3.1.0.jar:org/design/core/mp/support/BaseEntityWrapper.class */
public abstract class BaseEntityWrapper<E, V> {
    public abstract V entityVO(E e);

    public List<V> listVO(List<E> list) {
        return (List) list.stream().map(this::entityVO).collect(Collectors.toList());
    }

    public IPage<V> pageVO(IPage<E> pages) {
        List<V> records = listVO(pages.getRecords());
        IPage<V> pageVo = new Page<>(pages.getCurrent(), pages.getSize(), pages.getTotal());
        pageVo.setRecords(records);
        return pageVo;
    }
}
