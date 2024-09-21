package org.design.core.tool.node;

import java.util.ArrayList;
import java.util.List;
import org.design.core.tool.node.INode;

/* loaded from: bigdata-core-tool-3.1.0.jar:org/design/core/tool/node/ForestNodeManager.class */
public class ForestNodeManager<T extends INode> {
    private List<T> list;
    private List<Long> parentIds = new ArrayList();

    public ForestNodeManager(List<T> items) {
        this.list = items;
    }

    public INode getTreeNodeAT(Long id) {
        for (INode forestNode : this.list) {
            if (forestNode.getId().longValue() == id.longValue()) {
                return forestNode;
            }
        }
        return null;
    }

    public void addParentId(Long parentId) {
        this.parentIds.add(parentId);
    }

    public List<T> getRoot() {
        List<T> roots = new ArrayList<>();
        for (T forestNode : this.list) {
            if (forestNode.getParentId().longValue() == 0 || this.parentIds.contains(forestNode.getId())) {
                roots.add(forestNode);
            }
        }
        return roots;
    }
}
