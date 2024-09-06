package org.design.core.mp.base;

import io.swagger.annotations.ApiModelProperty;

/* loaded from: bigdata-core-mybatis-3.1.0.jar:org/design/core/mp/base/TenantEntity.class */
public class TenantEntity extends BaseEntity {
    @ApiModelProperty("租户ID")
    private String tenantId;

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    @Override // org.design.core.mp.base.BaseEntity, java.lang.Object
    public String toString() {
        return "TenantEntity(tenantId=" + getTenantId() + ")";
    }

    @Override // org.design.core.mp.base.BaseEntity, java.lang.Object
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TenantEntity)) {
            return false;
        }
        TenantEntity other = (TenantEntity) o;
        if (!other.canEqual(this) || !equals(o)) {
            return false;
        }
        Object this$tenantId = getTenantId();
        Object other$tenantId = other.getTenantId();
        return this$tenantId == null ? other$tenantId == null : this$tenantId.equals(other$tenantId);
    }

    @Override // org.design.core.mp.base.BaseEntity
    protected boolean canEqual(Object other) {
        return other instanceof TenantEntity;
    }

    @Override // org.design.core.mp.base.BaseEntity, java.lang.Object
    public int hashCode() {
        int result = hashCode();
        Object $tenantId = getTenantId();
        return (result * 59) + ($tenantId == null ? 43 : $tenantId.hashCode());
    }

    public String getTenantId() {
        return this.tenantId;
    }
}
