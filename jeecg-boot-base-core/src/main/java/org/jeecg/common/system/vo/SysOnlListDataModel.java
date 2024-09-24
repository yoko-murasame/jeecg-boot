package org.jeecg.common.system.vo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.util.ParameterizedTypeImpl;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Online列表查询结果model
 */
@ApiModel(value = "Online列表查询结果model")
@Data
public class SysOnlListDataModel {

    @ApiModelProperty(value = "总数")
    private long total = 0;
    @ApiModelProperty(value = "数据集合")
    private List<Map<String, Object>> records;
    @ApiModelProperty(value = "字典选项")
    private Map<String, List<DictModel>> dictOptions;

    // 获取records
    public <T> List<T> getRecords(Class<T> clazz) {
        if (this.records == null || this.records.isEmpty()) {
            return Collections.emptyList();
        }
        try {
            Type type = new ParameterizedTypeImpl(new Type[]{clazz}, null, List.class);
            return JSON.parseObject(JSON.toJSONString(this.records), type);
        } catch (Exception e) {
            throw new RuntimeException("类型转换错误", e);
        }
    }

}
