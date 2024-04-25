package cn.com.hyit.service.system;

import cn.com.hyit.dao.system.SysDictMapper;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.constant.DataBaseConstant;
import org.jeecg.common.system.vo.DictModel;
import org.jeecg.common.system.vo.DictModelMany;
import org.jeecg.common.util.SqlInjectionUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 字典功能服务
 */
@Slf4j
@Service
public class SysDictService implements ISysDictService{

    @Resource
    private SysDictMapper sysDictMapper;

    /**
     * 普通字典的翻译，根据多个dictCode和多条数据，多个以逗号分割
     * @param dictCodes 例如：user_status,sex
     * @param keysStr 例如：1,2,0
     */
    @Override
    public Map<String, List<DictModel>> translateManyDict(String dictCodes, String keysStr) {
        List<String> dictCodeList = Arrays.asList(dictCodes.split(","));
        List<String> keys = Arrays.asList(keysStr.split(","));
        List<DictModelMany> list = sysDictMapper.queryManyDictByKeys(dictCodeList, keys);
        Map<String, List<DictModel>> dictMap = new HashMap<>(5);
        for (DictModelMany dict : list) {
            List<DictModel> dictItemList = dictMap.computeIfAbsent(dict.getDictCode(), i -> new ArrayList<>());
            dictItemList.add(new DictModel(dict.getValue(), dict.getText()));
        }
        return dictMap;
    }

    /**
     * 数据字典查询
     *
     * @author Yoko
     * @since 2024/4/25 下午5:18
     * @param table 表名
     * @param text 显示字段
     * @param code 值字段
     * @param keys 查询条件
     */
    @Override
    public List<DictModel> translateDictFromTableByKeys(String table, String text, String code, String keys) {

        List<String> codeValues = Arrays.asList(keys.split(","));

        // 分割SQL获取表名和条件
        String filterSql = null;
        if(table.toLowerCase().indexOf(DataBaseConstant.SQL_WHERE)>0){
            String[] arr = table.split(" (?i)where ");
            table = arr[0];
            filterSql = arr[1];
        }

        // SQL注入check
        SqlInjectionUtil.filterContent(table, text, code);
        SqlInjectionUtil.specialFilterContentForDictSql(filterSql);

        // 针对采用 ${}写法的表名和字段进行转义和check
        table = SqlInjectionUtil.getSqlInjectTableName(table);
        text = SqlInjectionUtil.getSqlInjectField(text);
        code = SqlInjectionUtil.getSqlInjectField(code);

        return sysDictMapper.queryTableDictByKeysAndFilterSql(table, text, code, filterSql, codeValues);

    }

}
