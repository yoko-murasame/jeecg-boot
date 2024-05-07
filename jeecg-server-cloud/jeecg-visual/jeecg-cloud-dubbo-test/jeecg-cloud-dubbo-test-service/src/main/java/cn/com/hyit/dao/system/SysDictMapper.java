package cn.com.hyit.dao.system;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import cn.com.hyit.config.vo.DictModel;
import cn.com.hyit.config.vo.DictModelMany;

import java.util.List;

/**
 * 功能描述
 *
 * @author: uwm
 * @date: 2024年04月24日 14:29
 */
@Mapper
public interface SysDictMapper {

    /**
     * 可通过多个字典code查询翻译文本
     * @param dictCodeList 多个字典code
     * @param keys 数据列表
     */
    List<DictModelMany> queryManyDictByKeys(@Param("dictCodeList") List<String> dictCodeList, @Param("keys") List<String> keys);

    /**
     * 查询特殊表的字典数据
     * @param table 表名
     * @param text   显示字段名
     * @param code   存储字段名
     * @param filterSql 条件sql
     * @param codeValues 存储字段值 作为查询条件in
     */
    List<DictModel> queryTableDictByKeysAndFilterSql(@Param("table") String table, @Param("text") String text, @Param("code") String code, @Param("filterSql") String filterSql,
                                                     @Param("codeValues") List<String> codeValues);

}
