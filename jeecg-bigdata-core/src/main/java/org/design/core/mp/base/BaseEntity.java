package org.design.core.mp.base;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/* loaded from: bigdata-core-mybatis-3.1.0.jar:org/design/core/mp/base/BaseEntity.class */
public class BaseEntity implements Serializable {
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("创建人")
    private Long createUser;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("创建时间")
    private Date createTime;
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("更新人")
    private Long updateUser;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("更新时间")
    private Date updateTime;
    @ApiModelProperty("业务状态")
    private Integer status;
    @TableLogic
    @ApiModelProperty("是否已删除")
    private Integer isDeleted;

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override // java.lang.Object
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof BaseEntity)) {
            return false;
        }
        BaseEntity other = (BaseEntity) o;
        if (!other.canEqual(this)) {
            return false;
        }
        Object this$createUser = getCreateUser();
        Object other$createUser = other.getCreateUser();
        if (this$createUser == null) {
            if (other$createUser != null) {
                return false;
            }
        } else if (!this$createUser.equals(other$createUser)) {
            return false;
        }
        Object this$updateUser = getUpdateUser();
        Object other$updateUser = other.getUpdateUser();
        if (this$updateUser == null) {
            if (other$updateUser != null) {
                return false;
            }
        } else if (!this$updateUser.equals(other$updateUser)) {
            return false;
        }
        Object this$status = getStatus();
        Object other$status = other.getStatus();
        if (this$status == null) {
            if (other$status != null) {
                return false;
            }
        } else if (!this$status.equals(other$status)) {
            return false;
        }
        Object this$isDeleted = getIsDeleted();
        Object other$isDeleted = other.getIsDeleted();
        if (this$isDeleted == null) {
            if (other$isDeleted != null) {
                return false;
            }
        } else if (!this$isDeleted.equals(other$isDeleted)) {
            return false;
        }
        Object this$createTime = getCreateTime();
        Object other$createTime = other.getCreateTime();
        if (this$createTime == null) {
            if (other$createTime != null) {
                return false;
            }
        } else if (!this$createTime.equals(other$createTime)) {
            return false;
        }
        Object this$updateTime = getUpdateTime();
        Object other$updateTime = other.getUpdateTime();
        return this$updateTime == null ? other$updateTime == null : this$updateTime.equals(other$updateTime);
    }

    protected boolean canEqual(Object other) {
        return other instanceof BaseEntity;
    }

    @Override // java.lang.Object
    public int hashCode() {
        Object $createUser = getCreateUser();
        int result = (1 * 59) + ($createUser == null ? 43 : $createUser.hashCode());
        Object $updateUser = getUpdateUser();
        int result2 = (result * 59) + ($updateUser == null ? 43 : $updateUser.hashCode());
        Object $status = getStatus();
        int result3 = (result2 * 59) + ($status == null ? 43 : $status.hashCode());
        Object $isDeleted = getIsDeleted();
        int result4 = (result3 * 59) + ($isDeleted == null ? 43 : $isDeleted.hashCode());
        Object $createTime = getCreateTime();
        int result5 = (result4 * 59) + ($createTime == null ? 43 : $createTime.hashCode());
        Object $updateTime = getUpdateTime();
        return (result5 * 59) + ($updateTime == null ? 43 : $updateTime.hashCode());
    }

    @Override // java.lang.Object
    public String toString() {
        return "BaseEntity(createUser=" + getCreateUser() + ", createTime=" + getCreateTime() + ", updateUser=" + getUpdateUser() + ", updateTime=" + getUpdateTime() + ", status=" + getStatus() + ", isDeleted=" + getIsDeleted() + ")";
    }

    public Long getCreateUser() {
        return this.createUser;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public Long getUpdateUser() {
        return this.updateUser;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public Integer getStatus() {
        return this.status;
    }

    public Integer getIsDeleted() {
        return this.isDeleted;
    }
}
