
package org.jeecg.modules.wz.business.entity;

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
 * 城市大脑-资源图层实体类
 *
 * @author Blade
 * @since 2021-06-28
 */
@Data
@TableName("cb_resource_layer")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ResourceLayer对象", description = "城市大脑-资源图层")
public class ResourceLayer extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * 父级菜单
     */
    @ApiModelProperty(value = "父级菜单")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long parentId;
    /**
     * 菜单编号
     */
    @ApiModelProperty(value = "菜单编号")
    private String code;
    /**
     * 菜单名称
     */
    @ApiModelProperty(value = "菜单名称")
    private String name;
    /**
     * 数据源
     */
    @ApiModelProperty(value = "数据源")
    private String datasource;
    /**
     * 数据集
     */
    @ApiModelProperty(value = "数据集")
    private String dataset;
    /**
     * 额外数据
     */
    @ApiModelProperty(value = "额外数据")
    private String withExtraData;
    /**
     * 服务类型
     */
    @ApiModelProperty(value = "服务类型")
    private String type;
    /**
     * type涉及的配置
     */
    @ApiModelProperty(value = "type涉及的配置")
    private String typeJson;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer sort;

    /**
     * 服务地址
     */
    @ApiModelProperty(value = "服务地址")
    private String serviceAddress;

    /**
     * 菜单类型
     */
    @ApiModelProperty(value = "菜单类型")
    private String menuType;

    /**
     * 数据类型
     */
    @ApiModelProperty(value = "数据类型")
    private String dataType;

    /**
     * 显示方式
     */
    @ApiModelProperty(value = "显示方式")
    private String displayModal;

    /**
     * 排列方式
     */
    @ApiModelProperty(value = "排列方式")
    private String arrangement;

    /**
     * 样式
     */
    @ApiModelProperty(value = "样式")
    private String styleName;

    /**
     * 子级样式
     */
    @ApiModelProperty(value = "子级样式")
    private String childStyleName;

    /**
     * 底部类型
     */
    @ApiModelProperty(value = "底部类型")
    private String bottomType;

    /**
     * 坐标系
     */
    @ApiModelProperty(value = "坐标系")
    private String coordinate;

    /**
     * 属性数据地址
     */
    @ApiModelProperty(value = "属性数据地址")
    private String dataAddress;
    /**
     * 数据类型
     */
    @ApiModelProperty(value = "数据类型")
    private String sceneType;
    /**
     * 视角经度
     */
    @ApiModelProperty(value = "视角经度")
    private Float visualAngleLon;
    /**
     * 视角纬度
     */
    @ApiModelProperty(value = "视角纬度")
    private Float visualAngleLat;
    /**
     * 视角高度
     */
    @ApiModelProperty(value = "视角高度")
    private Float visualAngleAltitude;
    /**
     * 视角方位角
     */
    @ApiModelProperty(value = "视角方位角")
    private Float visualAngleAzimuth;
    /**
     * 视角倾斜角度
     */
    @ApiModelProperty(value = "视角倾斜角度")
    private Float visualAngleAngle;


    /**
     * 颜色组
     */
    @ApiModelProperty(value = "颜色组")
    private String colorArr;
    /**
     * 柱状图旋转角度
     */
    @ApiModelProperty(value = "柱状图旋转角度")
    private Integer rotate;

    /**
     * 显示图例模式：0不显示，1:显示（不显示数量）；2显示，并显示数量
     */
    @ApiModelProperty(value = "显示图例模式：0不显示，1:显示（不显示数量）；2显示，并显示数量")
    private Integer displayLegend;
    /**
     * 是否堆积
     */
    @ApiModelProperty(value = "是否堆积")
    private Boolean stacked;
    /**
     * 图标匹配字段
     */
    @ApiModelProperty(value = "图标匹配字段")
    private String iconMatchingField;

    /**
     * 是否显示标签
     */
    @ApiModelProperty(value = "是否显示标签")
    private Boolean showLabel;
    /**
     * 组件名称
     */
    @ApiModelProperty(value = "组件名称")
    private String assembly;
    /**
     * 列表是否显示表头
     */
    @ApiModelProperty(value = "列表是否显示表头")
    private Boolean hideHeader;
    /**
     * 图标高度
     */
    @ApiModelProperty(value = "图标高度")
    private String chartHeight;
    /**
     * 图形宽度
     */
    @ApiModelProperty(value = "图标高度")
    private String graphicalWidth;
    /**
     * 列表——显示名称
     */
    @ApiModelProperty(value = "列表——显示名称")
    private String showTitle;
    /**
     * 列表——显示字段
     */
    @ApiModelProperty(value = "列表——显示字段")
    private String showFiled;

    /**
     * 面、三维面——功能类型
     */
    @ApiModelProperty(value = "面、三维面——功能类型")
    private String functionType;
    /**
     * api接口查询条件
     */
    private String queryCriteria;
    /**
     * list是否显示排序图标
     */
    private Boolean  sortIcon;
    /**
     * 合计数量显示
     */
    private String showSum;
    /**
     * 提示
     * 约定字段：
     * 本年度第一天：yearFirstDay YYYY-MM-DD
     * 本年度第一个月：yearFirstMonth YYYY-MM
     * 当前日期：currDay YYYY-MM-DD
     * 当前月份：currMonth  YYYY-MM
     * 前一年： lastYear YYYY
     * 前一天：lastDay YYYY-MM-DD
     * 上月：lastMonth YYYY-MM
     *
     */
    private String tip;
    /**
     * 服务sql
     */
    private String querySql;
    /**
     * 最大层级
     */
    private  Integer maxLevel;
    /**
     * 最小层级
     */
    private  Integer minLevel;
    /**
     * 面镂空
     */
    private  String faceHollow;
    /**
     * 面范围线
     */
    private  String faceLine;
    /**
     * 线宽度
     */
    private  Double lineWidth;
    /**
     * 线型
     */
    private  String lineType;
    /**
     * 颜色
     */
    private  String sceneColor;
    /**
     * 是否显示明细
     */
    private  Boolean showChilden;
    /**
     * 明细Key
     */
    private  String childenKey;
    /**
     * 明细title
     */
    private  String childenTitle;
    /**
     * 是否是临时图层
     */
    private  Boolean isTempMap;

    @ApiModelProperty(value = "online列表code")
    private String onlineCode;

    @ApiModelProperty(value = "online列表条件")
    private String onlineCondition;

}
