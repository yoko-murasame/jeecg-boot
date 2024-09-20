package org.jeecg.modules.online.cgform.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecg.common.system.vo.DictModel;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;

import java.util.List;
import java.util.Map;

/**
 * Online列表查询结果model
 */
@ApiModel(value = "Online列表查询结果model")
@Data
public class OnlListDataModel {

    @ApiModelProperty(value = "总数")
    private long total = 0;
    @ApiModelProperty(value = "字段Meta")
    private List<OnlCgformField> fieldList;
    @ApiModelProperty(value = "数据集合")
    private List<Map<String, Object>> records;
    @ApiModelProperty(value = "字典选项")
    private Map<String, List<DictModel>> dictOptions;

    // 获取records
    public <T> List<T> getRecords(Class<T> clazz) {
        try {
            TypeReference<List<T>> typeReference = new TypeReference<List<T>>() {};
            return JSON.parseObject(JSON.toJSONString(this.records), typeReference);
        } catch (Exception e) {
            throw new RuntimeException("类型转换错误", e);
        }
    }

}
