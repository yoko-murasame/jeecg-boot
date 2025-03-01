package org.jeecg.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.system.entity.SysDictItem;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @Author zhangweijian
 * @since 2018-12-28
 */
public interface ISysDictItemService extends IService<SysDictItem> {

    /**
     * 通过字典id查询字典项
     * @param mainId 字典id
     * @return
     */
    public List<SysDictItem> selectItemsByMainId(String mainId);
    /**
     * 通过字典code查询字典项
     * @param code 字典code
     * @return
     */
    public List<SysDictItem> selectItemsByCode(String code);

    void buildLazyTree(List<SysDictItem> items);
}
