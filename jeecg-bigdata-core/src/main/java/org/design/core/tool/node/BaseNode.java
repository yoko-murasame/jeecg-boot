package org.design.core.tool.node;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.util.ArrayList;
import java.util.List;
import org.design.core.tool.utils.StringPool;

/* loaded from: bigdata-core-tool-3.1.0.jar:org/design/core/tool/node/BaseNode.class */
public class BaseNode implements INode {
    private static final long serialVersionUID = 1;
    @JsonSerialize(using = ToStringSerializer.class)
    protected Long id;
    @JsonSerialize(using = ToStringSerializer.class)
    protected Long parentId;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    protected List<INode> children = new ArrayList();
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Boolean hasChildren;

    public void setId(Long id) {
        this.id = id;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public void setChildren(List<INode> children) {
        this.children = children;
    }

    public void setHasChildren(Boolean hasChildren) {
        this.hasChildren = hasChildren;
    }

    @Override // java.lang.Object
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof BaseNode)) {
            return false;
        }
        BaseNode other = (BaseNode) o;
        if (!other.canEqual(this)) {
            return false;
        }
        Object this$id = getId();
        Object other$id = other.getId();
        if (this$id == null) {
            if (other$id != null) {
                return false;
            }
        } else if (!this$id.equals(other$id)) {
            return false;
        }
        Object this$parentId = getParentId();
        Object other$parentId = other.getParentId();
        if (this$parentId == null) {
            if (other$parentId != null) {
                return false;
            }
        } else if (!this$parentId.equals(other$parentId)) {
            return false;
        }
        Object this$hasChildren = getHasChildren();
        Object other$hasChildren = other.getHasChildren();
        if (this$hasChildren == null) {
            if (other$hasChildren != null) {
                return false;
            }
        } else if (!this$hasChildren.equals(other$hasChildren)) {
            return false;
        }
        Object this$children = getChildren();
        Object other$children = other.getChildren();
        return this$children == null ? other$children == null : this$children.equals(other$children);
    }

    protected boolean canEqual(Object other) {
        return other instanceof BaseNode;
    }

    @Override // java.lang.Object
    public int hashCode() {
        Object $id = getId();
        int result = (1 * 59) + ($id == null ? 43 : $id.hashCode());
        Object $parentId = getParentId();
        int result2 = (result * 59) + ($parentId == null ? 43 : $parentId.hashCode());
        Object $hasChildren = getHasChildren();
        int result3 = (result2 * 59) + ($hasChildren == null ? 43 : $hasChildren.hashCode());
        Object $children = getChildren();
        return (result3 * 59) + ($children == null ? 43 : $children.hashCode());
    }

    @Override // java.lang.Object
    public String toString() {
        return "BaseNode(id=" + getId() + ", parentId=" + getParentId() + ", children=" + getChildren() + ", hasChildren=" + getHasChildren() + StringPool.RIGHT_BRACKET;
    }

    @Override // org.design.core.tool.node.INode
    public Long getId() {
        return this.id;
    }

    @Override // org.design.core.tool.node.INode
    public Long getParentId() {
        return this.parentId;
    }

    @Override // org.design.core.tool.node.INode
    public List<INode> getChildren() {
        return this.children;
    }

    @Override // org.design.core.tool.node.INode
    public Boolean getHasChildren() {
        if (this.children.size() > 0) {
            return true;
        }
        return this.hasChildren;
    }
}
