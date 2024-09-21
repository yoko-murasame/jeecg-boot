package org.jeecg.modules.wz.cityBrain.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jeecg.modules.wz.business.base.BaseEntity;

/**
 * @Description: 资源文件
 * @Author: Circle
 * @Date: 2021-09-30
 * @Version: V1.0
 */
@Data
@TableName("cb_resource_file")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "cb_resource_file对象", description = "资源文件")
public class ResourceFile extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 文件名
     */
    @ApiModelProperty(value = "文件名")
    private String fileName;
    /**
     * 文件路径
     */
    @ApiModelProperty(value = "文件路径")
    private String filePath;
    /**
     * 文件类型
     */
    @ApiModelProperty(value = "文件类型")
    private String fileType;
    /**
     * 文件描述
     */
    @ApiModelProperty(value = "文件描述")
    private String fileDescribe;
    /**
     * 所属表
     */
    @ApiModelProperty(value = "所属表")
    private String belongTable;
    /**
     * 表id
     */
    @ApiModelProperty(value = "表id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long tableId;
}
