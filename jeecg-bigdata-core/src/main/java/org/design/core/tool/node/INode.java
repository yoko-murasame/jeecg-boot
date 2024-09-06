package org.design.core.tool.node;

import java.io.Serializable;
import java.util.List;

/* loaded from: bigdata-core-tool-3.1.0.jar:org/design/core/tool/node/INode.class */
public interface INode extends Serializable {
    Long getId();

    Long getParentId();

    List<INode> getChildren();

    default Boolean getHasChildren() {
        return false;
    }
}
