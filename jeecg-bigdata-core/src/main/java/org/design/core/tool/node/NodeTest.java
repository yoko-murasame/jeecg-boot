package org.design.core.tool.node;

import java.util.ArrayList;
import java.util.List;
import org.design.core.tool.jackson.JsonUtil;

/* loaded from: bigdata-core-tool-3.1.0.jar:org/design/core/tool/node/NodeTest.class */
public class NodeTest {
    public static void main(String[] args) {
        List<ForestNode> list = new ArrayList<>();
        list.add(new ForestNode(1L, 0L, "1"));
        list.add(new ForestNode(2L, 0L, "2"));
        list.add(new ForestNode(3L, 1L, "3"));
        list.add(new ForestNode(4L, 2L, "4"));
        list.add(new ForestNode(5L, 3L, "5"));
        list.add(new ForestNode(6L, 4L, "6"));
        list.add(new ForestNode(7L, 3L, "7"));
        list.add(new ForestNode(8L, 5L, "8"));
        list.add(new ForestNode(9L, 6L, "9"));
        list.add(new ForestNode(10L, 9L, "10"));
        ForestNodeMerger.merge(list).forEach(node -> {
            System.out.println(JsonUtil.toJson(node));
        });
    }
}
