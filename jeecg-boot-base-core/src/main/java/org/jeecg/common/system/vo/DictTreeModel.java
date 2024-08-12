package org.jeecg.common.system.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 树字典
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DictTreeModel implements Serializable{
	private static final long serialVersionUID = 5683603798262937601L;

	@ApiModelProperty(value = "ID", name = "id", notes = "ID")
	private String id;

	@ApiModelProperty(value = "父ID(0为根节点)", name = "parentId", notes = "父ID(0为根节点)")
	private String parentId;

	@ApiModelProperty(value = "字典文本", name = "itemText", notes = "字典文本")
	private String itemText;

	@ApiModelProperty(value = "字典值", name = "itemValue", notes = "字典值")
	private String itemValue;

	@ApiModelProperty(value = "排序", name = "sortOrder", notes = "排序")
	private Integer sortOrder;

	@ApiModelProperty(value = "子字典", name = "children", notes = "子字典")
	private List<DictTreeModel> children;

}
