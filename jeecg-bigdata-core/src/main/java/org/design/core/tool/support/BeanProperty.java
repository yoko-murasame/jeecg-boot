package org.design.core.tool.support;

/* loaded from: bigdata-core-tool-3.1.0.jar:org/design/core/tool/support/BeanProperty.class */
public class BeanProperty {
    private final String name;
    private final Class<?> type;

    public BeanProperty(String name, Class<?> type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return this.name;
    }

    public Class<?> getType() {
        return this.type;
    }
}
