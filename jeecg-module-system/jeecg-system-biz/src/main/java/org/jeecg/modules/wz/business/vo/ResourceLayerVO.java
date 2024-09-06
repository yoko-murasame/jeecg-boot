
package org.jeecg.modules.wz.business.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import org.design.core.tool.node.INode;
import org.jeecg.modules.wz.business.entity.ResourceLayer;
import org.jeecg.modules.wz.cityBrain.api.vo.ResourceFileVO2;

import java.util.ArrayList;
import java.util.List;

/**
 * 城市大脑-资源图层视图实体类
 *
 * @author Blade
 * @since 2021-06-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ResourceLayerVO对象", description = "城市大脑-资源图层")
public class ResourceLayerVO extends ResourceLayer implements INode {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 父节点ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long parentId;

    /**
     * 子孙节点
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<INode> children;

    /**
     * 上级菜单
     */
    private String parentName;

    @Override
    public List<INode> getChildren() {
        if (this.children == null) {
            this.children = new ArrayList<>();
        }
        return this.children;
    }

    /**
     * 资源图片合集
     */
    private ResourceFileVO2 files;
}
