package org.design.core.tool.support.xss;

import java.util.ArrayList;
import java.util.List;
import org.design.core.tool.utils.StringPool;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("bigdata.xss.url")
/* loaded from: bigdata-core-tool-3.1.0.jar:org/design/core/tool/support/xss/XssUrlProperties.class */
public class XssUrlProperties {
    private final List<String> excludePatterns = new ArrayList();

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof XssUrlProperties)) {
            return false;
        }
        XssUrlProperties other = (XssUrlProperties) o;
        if (!other.canEqual(this)) {
            return false;
        }
        Object this$excludePatterns = getExcludePatterns();
        Object other$excludePatterns = other.getExcludePatterns();
        return this$excludePatterns == null ? other$excludePatterns == null : this$excludePatterns.equals(other$excludePatterns);
    }

    protected boolean canEqual(Object other) {
        return other instanceof XssUrlProperties;
    }

    public int hashCode() {
        Object $excludePatterns = getExcludePatterns();
        return (1 * 59) + ($excludePatterns == null ? 43 : $excludePatterns.hashCode());
    }

    public String toString() {
        return "XssUrlProperties(excludePatterns=" + getExcludePatterns() + StringPool.RIGHT_BRACKET;
    }

    public List<String> getExcludePatterns() {
        return this.excludePatterns;
    }
}
