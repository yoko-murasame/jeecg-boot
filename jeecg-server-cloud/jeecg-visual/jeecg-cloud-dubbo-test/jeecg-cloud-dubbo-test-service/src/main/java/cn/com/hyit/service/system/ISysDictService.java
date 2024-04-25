package cn.com.hyit.service.system;

import org.jeecg.common.system.vo.DictModel;

import java.util.List;
import java.util.Map;

public interface ISysDictService {

    /**
     * 普通字典的翻译，根据多个dictCode和多条数据，多个以逗号分割
     * @param dictCodes 例如：user_status,sex
     * @param keysStr 例如：1,2,0
     */
    Map<String, List<DictModel>> translateManyDict(String dictCodes, String keysStr);

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
    List<DictModel> translateDictFromTableByKeys(String table, String text, String code, String keys);

}
