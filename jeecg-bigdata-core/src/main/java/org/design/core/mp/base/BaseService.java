package org.design.core.mp.base;

import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;
import javax.validation.constraints.NotEmpty;

/* loaded from: bigdata-core-mybatis-3.1.0.jar:org/design/core/mp/base/BaseService.class */
public interface BaseService<T> extends IService<T> {
    boolean deleteLogic(@NotEmpty List<Long> list);
}
