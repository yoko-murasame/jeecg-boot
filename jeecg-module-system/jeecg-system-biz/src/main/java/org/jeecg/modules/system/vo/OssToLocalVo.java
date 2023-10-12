package org.jeecg.modules.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@ApiModel(description = "Oss文件转换到Local请求参数实体")
@Data
public class OssToLocalVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "表名")
    private String tableName;
    @ApiModelProperty(value = "主键字段")
    private String idField;
    @ApiModelProperty(value = "字段名")
    private List<String> fields;
    @ApiModelProperty(value = "字段类型")
    private List<String> fieldTypes;
    @ApiModelProperty(value = "JSON字段路径")
    private List<String> jsonPaths;
    @ApiModelProperty(value = "分页")
    private Integer page;
    @ApiModelProperty(value = "每页条数")
    private Integer pageSize;
    @ApiModelProperty(value = "限制下载流量")
    private Double limitMb;
    @ApiModelProperty(value = "业务路径")
    private String bizPath;
    @ApiModelProperty(value = "是否删除源文件")
    private Boolean deleteSource = false;

    @ApiModelProperty(value = "自定义oss endpoint")
    private String endpoint;
    @ApiModelProperty(value = "自定义 accessKeyId")
    private String accessKeyId;
    @ApiModelProperty(value = "自定义 accessKeySecret")
    private String accessKeySecret;
    @ApiModelProperty(value = "自定义 bucketName")
    private String bucketName;

    public static final String VARCHAR = "varchar";
    public static final String JSON_TYPE = "json";

}
