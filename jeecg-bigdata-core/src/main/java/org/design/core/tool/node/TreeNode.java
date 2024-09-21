package org.design.core.tool.node;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.design.core.tool.utils.StringPool;

/* loaded from: bigdata-core-tool-3.1.0.jar:org/design/core/tool/node/TreeNode.class */
public class TreeNode extends BaseNode {
    private static final long serialVersionUID = 1;
    private String title;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long key;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long value;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    @Override // org.design.core.tool.node.BaseNode, java.lang.Object
    public String toString() {
        return "TreeNode(title=" + getTitle() + ", key=" + getKey() + ", value=" + getValue() + StringPool.RIGHT_BRACKET;
    }

    @Override // org.design.core.tool.node.BaseNode, java.lang.Object
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TreeNode)) {
            return false;
        }
        TreeNode other = (TreeNode) o;
        if (!other.canEqual(this)) {
            return false;
        }
        Object this$key = getKey();
        Object other$key = other.getKey();
        if (this$key == null) {
            if (other$key != null) {
                return false;
            }
        } else if (!this$key.equals(other$key)) {
            return false;
        }
        Object this$value = getValue();
        Object other$value = other.getValue();
        if (this$value == null) {
            if (other$value != null) {
                return false;
            }
        } else if (!this$value.equals(other$value)) {
            return false;
        }
        Object this$title = getTitle();
        Object other$title = other.getTitle();
        return this$title == null ? other$title == null : this$title.equals(other$title);
    }

    @Override // org.design.core.tool.node.BaseNode
    protected boolean canEqual(Object other) {
        return other instanceof TreeNode;
    }

    @Override // org.design.core.tool.node.BaseNode, java.lang.Object
    public int hashCode() {
        Object $key = getKey();
        int result = (1 * 59) + ($key == null ? 43 : $key.hashCode());
        Object $value = getValue();
        int result2 = (result * 59) + ($value == null ? 43 : $value.hashCode());
        Object $title = getTitle();
        return (result2 * 59) + ($title == null ? 43 : $title.hashCode());
    }

    public String getTitle() {
        return this.title;
    }

    public Long getKey() {
        return this.key;
    }

    public Long getValue() {
        return this.value;
    }
}
