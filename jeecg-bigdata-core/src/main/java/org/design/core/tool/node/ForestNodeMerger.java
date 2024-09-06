package org.design.core.tool.node;

import java.util.List;

/* loaded from: bigdata-core-tool-3.1.0.jar:org/design/core/tool/node/ForestNodeMerger.class */
public class ForestNodeMerger {
    public static <T extends INode> List<T> merge(List<T> items) {
        ForestNodeManager<T> forestNodeManager = new ForestNodeManager<>(items);
        items.forEach(forestNode -> {
            if (forestNode.getParentId().longValue() != 0) {
                INode node = forestNodeManager.getTreeNodeAT(forestNode.getParentId());
                if (node != null) {
                    node.getChildren().add(forestNode);
                } else {
                    forestNodeManager.addParentId(forestNode.getId());
                }
            }
        });
        return forestNodeManager.getRoot();
    }
}
