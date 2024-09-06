package org.design.core.tool.node;

import org.design.core.tool.utils.StringPool;

/* loaded from: bigdata-core-tool-3.1.0.jar:org/design/core/tool/node/ForestNode.class */
public class ForestNode extends BaseNode {
    private static final long serialVersionUID = 1;
    private Object content;

    public void setContent(Object content) {
        this.content = content;
    }

    @Override // org.design.core.tool.node.BaseNode, java.lang.Object
    public String toString() {
        return "ForestNode(content=" + getContent() + StringPool.RIGHT_BRACKET;
    }

    @Override // org.design.core.tool.node.BaseNode, java.lang.Object
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ForestNode)) {
            return false;
        }
        ForestNode other = (ForestNode) o;
        if (!other.canEqual(this)) {
            return false;
        }
        Object this$content = getContent();
        Object other$content = other.getContent();
        return this$content == null ? other$content == null : this$content.equals(other$content);
    }

    @Override // org.design.core.tool.node.BaseNode
    protected boolean canEqual(Object other) {
        return other instanceof ForestNode;
    }

    @Override // org.design.core.tool.node.BaseNode, java.lang.Object
    public int hashCode() {
        Object $content = getContent();
        return (1 * 59) + ($content == null ? 43 : $content.hashCode());
    }

    public Object getContent() {
        return this.content;
    }

    public ForestNode(Long id, Long parentId, Object content) {
        this.id = id;
        this.parentId = parentId;
        this.content = content;
    }
}
