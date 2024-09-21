package org.jeecg.modules.wz.business.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.design.core.mp.base.BaseService;
import org.design.core.tool.utils.DateUtil;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

/**
 * @Author: -Circle
 * @Date: 2021/7/27 17:36
 * @Description:
 */
@Validated
public class BaseServiceImpl<M extends BaseMapper<T>, T extends BaseEntity> extends ServiceImpl<M, T> implements BaseService<T> {

    @Override
    public boolean save(T entity) {
//		BladeUser user = SecureUtil.getUser();
//		if (user != null) {
//			entity.setCreateUser(user.getUserId());
//			entity.setUpdateUser(user.getUserId());
//		}
        Date now = DateUtil.now();
        entity.setCreateTime(now);
        entity.setUpdateTime(now);
        if (entity.getStatus() == null) {
            entity.setStatus(0);
        }
        entity.setIsDeleted(0);
        return super.save(entity);
    }

    @Override
    public boolean updateById(T entity) {
//		BladeUser user = SecureUtil.getUser();
//		if (user != null) {
//			entity.setUpdateUser(user.getUserId());
//		}
        entity.setUpdateTime(DateUtil.now());
        return super.updateById(entity);
    }

    @Override
    public boolean deleteLogic(@NotEmpty List<Long> ids) {
        return super.removeByIds(ids);
    }

}
