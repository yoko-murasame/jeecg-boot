package org.design.core.tool.support.xss;

import java.util.ArrayList;
import java.util.List;
import org.design.core.tool.utils.StringPool;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("bigdata.xss")
/* loaded from: bigdata-core-tool-3.1.0.jar:org/design/core/tool/support/xss/XssProperties.class */
public class XssProperties {
    private Boolean enabled = true;
    private List<String> skipUrl = new ArrayList();

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public void setSkipUrl(List<String> skipUrl) {
        this.skipUrl = skipUrl;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof XssProperties)) {
            return false;
        }
        XssProperties other = (XssProperties) o;
        if (!other.canEqual(this)) {
            return false;
        }
        Object this$enabled = getEnabled();
        Object other$enabled = other.getEnabled();
        if (this$enabled == null) {
            if (other$enabled != null) {
                return false;
            }
        } else if (!this$enabled.equals(other$enabled)) {
            return false;
        }
        Object this$skipUrl = getSkipUrl();
        Object other$skipUrl = other.getSkipUrl();
        return this$skipUrl == null ? other$skipUrl == null : this$skipUrl.equals(other$skipUrl);
    }

    protected boolean canEqual(Object other) {
        return other instanceof XssProperties;
    }

    public int hashCode() {
        Object $enabled = getEnabled();
        int result = (1 * 59) + ($enabled == null ? 43 : $enabled.hashCode());
        Object $skipUrl = getSkipUrl();
        return (result * 59) + ($skipUrl == null ? 43 : $skipUrl.hashCode());
    }

    public String toString() {
        return "XssProperties(enabled=" + getEnabled() + ", skipUrl=" + getSkipUrl() + StringPool.RIGHT_BRACKET;
    }

    public Boolean getEnabled() {
        return this.enabled;
    }

    public List<String> getSkipUrl() {
        return this.skipUrl;
    }
}
