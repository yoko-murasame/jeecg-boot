package org.jeecg.common.system.vo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * Online列表查询实体model
 */
@ApiModel(value = "Online列表查询实体model")
@Data
public class SysOnlListQueryModel {

    @ApiModelProperty(value = "表名")
    private String tableName;
    @ApiModelProperty(value = "总数")
    private String code;
    @ApiModelProperty(value = "查询参数，需要分页请传入：pageSize、pageNo")
    private Map<String, Object> params;
    @ApiModelProperty(value = "强制需要查询字段(不受列表隐藏影响)")
    private List<String> needList;
    @ApiModelProperty(value = "数据规则perms")
    private String dataRulePerms;
    @ApiModelProperty(value = "是否查询所有字段（1是，0否）")
    private String queryAllColumn;
    @ApiModelProperty(value = "是否分页")
    private Boolean needPage;
    @ApiModelProperty(value = "树型查询时，父节点字段")
    private String pidField;

    public void setParamsByObject(Object queryParam) {
        String params = JSON.toJSONString(queryParam);
        this.setParams(JSON.parseObject(params, new TypeReference<Map<String, Object>>() {}));
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
        this.setNeedPageVal(params.get("pageSize"));
    }

    // jeecg作者自己协定的不分页值
    public void setNeedPageVal(Object pageSize) {
        int finalSize = pageSize == null ? 10 : Integer.parseInt(pageSize.toString());
        // this.isPage = (valueOf != -521);
        this.needPage = (pageSize != null) && (finalSize > 0);
    }

}
