package org.design.core.mp.support;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "查询条件")
/* loaded from: bigdata-core-mybatis-3.1.0.jar:org/design/core/mp/support/Query.class */
public class Query {
    @ApiModelProperty("当前页")
    private Integer current;
    @ApiModelProperty("每页的数量")
    private Integer size;
    @ApiModelProperty(hidden = true)
    private String ascs;
    @ApiModelProperty(hidden = true)
    private String descs;

    public Query setCurrent(Integer current) {
        this.current = current;
        return this;
    }

    public Query setSize(Integer size) {
        this.size = size;
        return this;
    }

    public Query setAscs(String ascs) {
        this.ascs = ascs;
        return this;
    }

    public Query setDescs(String descs) {
        this.descs = descs;
        return this;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Query)) {
            return false;
        }
        Query other = (Query) o;
        if (!other.canEqual(this)) {
            return false;
        }
        Object this$current = getCurrent();
        Object other$current = other.getCurrent();
        if (this$current == null) {
            if (other$current != null) {
                return false;
            }
        } else if (!this$current.equals(other$current)) {
            return false;
        }
        Object this$size = getSize();
        Object other$size = other.getSize();
        if (this$size == null) {
            if (other$size != null) {
                return false;
            }
        } else if (!this$size.equals(other$size)) {
            return false;
        }
        Object this$ascs = getAscs();
        Object other$ascs = other.getAscs();
        if (this$ascs == null) {
            if (other$ascs != null) {
                return false;
            }
        } else if (!this$ascs.equals(other$ascs)) {
            return false;
        }
        Object this$descs = getDescs();
        Object other$descs = other.getDescs();
        return this$descs == null ? other$descs == null : this$descs.equals(other$descs);
    }

    protected boolean canEqual(Object other) {
        return other instanceof Query;
    }

    public int hashCode() {
        Object $current = getCurrent();
        int result = (1 * 59) + ($current == null ? 43 : $current.hashCode());
        Object $size = getSize();
        int result2 = (result * 59) + ($size == null ? 43 : $size.hashCode());
        Object $ascs = getAscs();
        int result3 = (result2 * 59) + ($ascs == null ? 43 : $ascs.hashCode());
        Object $descs = getDescs();
        return (result3 * 59) + ($descs == null ? 43 : $descs.hashCode());
    }

    public String toString() {
        return "Query(current=" + getCurrent() + ", size=" + getSize() + ", ascs=" + getAscs() + ", descs=" + getDescs() + ")";
    }

    public Integer getCurrent() {
        return this.current;
    }

    public Integer getSize() {
        return this.size;
    }

    public String getAscs() {
        return this.ascs;
    }

    public String getDescs() {
        return this.descs;
    }
}
