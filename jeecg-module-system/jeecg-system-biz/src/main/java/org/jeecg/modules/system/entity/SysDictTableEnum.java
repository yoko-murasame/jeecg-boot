package org.jeecg.modules.system.entity;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jeecg.common.system.vo.DictModel;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.modules.system.mapper.SysDepartMapper;
import org.jeecg.modules.system.mapper.SysDictItemMapper;
import org.jeecg.modules.system.mapper.SysDictMapper;
import org.jeecg.modules.system.mapper.SysUserMapper;
import org.jeecg.modules.system.service.ISysDepartService;
import org.jeecg.modules.system.service.ISysDictItemService;
import org.jeecg.modules.system.service.ISysDictService;
import org.jeecg.modules.system.service.ISysUserService;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 表字典查询枚举
 * 在这里限制可查询的表
 * 严格模式下，只允许这里配置的表参与动态字典搜索
 *
 * @author YOKO
 */
@AllArgsConstructor
@Getter
public enum SysDictTableEnum {

    /**
     * 字典项表
     */
    SYS_DICT_ITEM("sys_dict_item", SysDictItemMapper.class, ISysDictItemService.class),
    /**
     * 字典表
     */
    SYS_DICT("sys_dict", SysDictMapper.class, ISysDictService.class),
    /**
     * 用户表
     */
    SYS_USER("sys_user", SysUserMapper.class, ISysUserService.class),
    /**
     * 部门表
     */
    SYS_DEPART("sys_depart", SysDepartMapper.class, ISysDepartService.class),
    ;

    /**
     * 表名
     */
    private final String tableName;

    /**
     * 数据mapper接口类
     */
    private final Class<? extends BaseMapper<?>> mapperClass;
    private final Class<? extends IService<?>> serviceClass;

    /**
     * 批量执行更新
     *
     * @author Yoko
     * @since 2024/6/27 下午5:09
     * @param items 待更新对象
     */
    public void executeBatchUpdateById(List items) {
        if (items.isEmpty()) {
            return;
        }
        IService service = SpringContextUtils.getBean(serviceClass);
        items.forEach(service::updateById);
    }

    /**
     * 查询列表
     */
    public <T> List<T> executeList(Wrapper<T> query) {
        BaseMapper mapper = SpringContextUtils.getBean(mapperClass);
        return (List<T>) mapper.selectList(query);
    }

    /**
     * 字典数据查询方法
     *
     * @param textField 文本字段
     * @param codeField 值字段
     * @param keys      筛选值
     * @return java.util.List<cn.com.hyit.config.vo.DictModel>
     * @author Yoko
     * @since 2024/5/13 上午9:48
     */
    public List<DictModel> getDictModels(String textField, String codeField, List<String> keys, String filterSql) {
        BaseMapper<?> mapper = SpringContextUtils.getBean(mapperClass);
        QueryWrapper<?> query = Wrappers.query().select(textField, codeField);
        query.in(null != keys && !keys.isEmpty(), codeField, keys);
        query.and(StringUtils.hasText(filterSql), q -> q.apply(filterSql));
        List<?> results = mapper.selectList((Wrapper) query);
        return results.stream()
                .map(item -> new DictModel(getValue(item, codeField), getText(item, textField)))
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * 字典数据查询方法-分页
     *
     * @param textField 文本字段
     * @param codeField 值字段
     * @param keys      筛选值
     * @param page      分页对象
     * @return java.util.List<cn.com.hyit.config.vo.DictModel>
     * @author Yoko
     * @since 2024/5/13 上午9:48
     */
    public IPage<DictModel> getDictModelsPage(String textField, String codeField, List<String> keys, String filterSql, IPage page) {
        BaseMapper<?> mapper = SpringContextUtils.getBean(mapperClass);
        QueryWrapper<?> query = Wrappers.query().select(textField, codeField);
        query.in(null != keys && !keys.isEmpty(), codeField, keys);
        query.and(StringUtils.hasText(filterSql), q -> q.apply(filterSql));
        IPage<?> pageResource = mapper.selectPage(page, (Wrapper) query);
        List<DictModel> trans = pageResource.getRecords().stream()
                .map(item -> new DictModel(getValue(item, codeField), getText(item, textField)))
                .distinct()
                .collect(Collectors.toList());
        Page<DictModel> res = new Page<>();
        res.setRecords(trans);
        res.setCurrent(pageResource.getCurrent());
        res.setPages(pageResource.getPages());
        res.setSize(pageResource.getSize());
        res.setTotal(pageResource.getTotal());
        return res;
    }

    /**
     * 根据表名获取枚举
     *
     * @param tableName 表名
     * @return java.util.Optional<cn.com.hyit.entity.system.SysDictTableEnum>
     * @author Yoko
     * @since 2024/5/13 上午9:47
     */
    public static Optional<SysDictTableEnum> of(String tableName) {
        for (SysDictTableEnum value : SysDictTableEnum.values()) {
            if (value.tableName.equalsIgnoreCase(tableName)) {
                return Optional.of(value);
            }
        }
        return Optional.empty();
    }

    private String capitalize(String str) {
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }

    private String toCamelCase(String string) {
        StringBuilder result = new StringBuilder();
        boolean nextUpperCase = false;
        for (char c : string.toCharArray()) {
            if (c == '_') {
                nextUpperCase = true;
            } else {
                if (nextUpperCase) {
                    result.append(Character.toUpperCase(c));
                    nextUpperCase = false;
                } else {
                    result.append(c);
                }
            }
        }
        return result.toString();
    }

    private String getValue(Object item, String valueField) {
        try {
            return (String) item.getClass().getMethod("get" + capitalize(toCamelCase(valueField))).invoke(item);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get value from item: " + item, e);
        }
    }

    private String getText(Object item, String textField) {
        try {
            return (String) item.getClass().getMethod("get" + capitalize(toCamelCase(textField))).invoke(item);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get text from item: " + item, e);
        }
    }

}
