package cn.com.hyit.entity.system;

import cn.com.hyit.config.util.SpringContextUtils;
import cn.com.hyit.config.vo.DictModel;
import cn.com.hyit.dao.test.TestOneMapper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 表字典查询枚举
 * 在这里限制可查询的表
 * @author YOKO
 */
@AllArgsConstructor
@Getter
public enum SysDictTableEnum {

    /**
     * 测试表（允许字典查询）
     */
    TEST_ONE("test_one", TestOneMapper.class),
    ;

    /**
     * 表名
     */
    private final String tableName;

    /**
     * 数据mapper接口类
     */
    private final Class<? extends BaseMapper<?>> mapperClass;

    /**
     * 字典数据查询方法
     *
     * @author Yoko
     * @since 2024/5/13 上午9:48
     * @param textField 文本字段
     * @param codeField 值字段
     * @param keys 筛选值
     * @return java.util.List<cn.com.hyit.config.vo.DictModel>
     */
    public List<DictModel> getDictModels(String textField, String codeField, List<String> keys, String filterSql) {
        BaseMapper<?> mapper = SpringContextUtils.getBean(mapperClass);
        QueryWrapper<?> query = Wrappers.query().select(textField, codeField);
        query.in(null != keys && !keys.isEmpty(), codeField, keys);
        query.and(StringUtils.hasText(filterSql), q -> q.apply(filterSql));
        List<?> results = mapper.selectList((Wrapper) query);
        return results.stream()
                .map(item -> new DictModel(getValue(item, codeField), getText(item, textField)))
                .collect(Collectors.toList());
    }

    /**
     * 根据表名获取枚举
     *
     * @author Yoko
     * @since 2024/5/13 上午9:47
     * @param tableName 表名
     * @return java.util.Optional<cn.com.hyit.entity.system.SysDictTableEnum>
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
