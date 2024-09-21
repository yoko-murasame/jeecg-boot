package org.jeecg.modules.wz.business.base;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: -Circle
 * @Date: 2021/7/27 17:13
 * @Description:
 */
public class BaseEntity implements Serializable {
    @JsonSerialize(
            using = ToStringSerializer.class
    )
    @ApiModelProperty("创建人")
    private String createBy;
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @ApiModelProperty("创建时间")
    private Date createTime;
    @JsonSerialize(
            using = ToStringSerializer.class
    )
    @ApiModelProperty("更新人")
    private String updateBy;
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @ApiModelProperty("更新时间")
    private Date updateTime;
    @ApiModelProperty("业务状态")
    private Integer status;
    @TableLogic
    @ApiModelProperty("是否已删除")
    private Integer isDeleted;

    public BaseEntity() {
    }

    public String getCreateBy() {
        return this.createBy;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public String getUpdateBy() {
        return this.updateBy;
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

    public void setCreateBy(final String createBy) {
        this.createBy = createBy;
    }

    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    public void setCreateTime(final Date createTime) {
        this.createTime = createTime;
    }

    public void setUpdateBy(final String updateBy) {
        this.updateBy = updateBy;
    }

    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    public void setUpdateTime(final Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setStatus(final Integer status) {
        this.status = status;
    }

    public void setIsDeleted(final Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof BaseEntity)) {
            return false;
        } else {
            BaseEntity other = (BaseEntity) o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$createUser = this.getCreateBy();
                Object other$createUser = other.getCreateBy();
                if (this$createUser == null) {
                    if (other$createUser != null) {
                        return false;
                    }
                } else if (!this$createUser.equals(other$createUser)) {
                    return false;
                }

                Object this$updateUser = this.getUpdateBy();
                Object other$updateUser = other.getUpdateBy();
                if (this$updateUser == null) {
                    if (other$updateUser != null) {
                        return false;
                    }
                } else if (!this$updateUser.equals(other$updateUser)) {
                    return false;
                }

                Object this$status = this.getStatus();
                Object other$status = other.getStatus();
                if (this$status == null) {
                    if (other$status != null) {
                        return false;
                    }
                } else if (!this$status.equals(other$status)) {
                    return false;
                }

                label62:
                {
                    Object this$isDeleted = this.getIsDeleted();
                    Object other$isDeleted = other.getIsDeleted();
                    if (this$isDeleted == null) {
                        if (other$isDeleted == null) {
                            break label62;
                        }
                    } else if (this$isDeleted.equals(other$isDeleted)) {
                        break label62;
                    }

                    return false;
                }

                label55:
                {
                    Object this$createTime = this.getCreateTime();
                    Object other$createTime = other.getCreateTime();
                    if (this$createTime == null) {
                        if (other$createTime == null) {
                            break label55;
                        }
                    } else if (this$createTime.equals(other$createTime)) {
                        break label55;
                    }

                    return false;
                }

                Object this$updateTime = this.getUpdateTime();
                Object other$updateTime = other.getUpdateTime();
                if (this$updateTime == null) {
                    if (other$updateTime != null) {
                        return false;
                    }
                } else if (!this$updateTime.equals(other$updateTime)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof BaseEntity;
    }

    public int hashCode() {
        return super.hashCode();
//        int PRIME = true;
//        int result = 1;
//        Object $createUser = this.getCreateBy();
//        int result = result * 59 + ($createUser == null ? 43 : $createUser.hashCode());
//        Object $updateUser = this.getUpdateBy();
//        result = result * 59 + ($updateUser == null ? 43 : $updateUser.hashCode());
//        Object $status = this.getStatus();
//        result = result * 59 + ($status == null ? 43 : $status.hashCode());
//        Object $isDeleted = this.getIsDeleted();
//        result = result * 59 + ($isDeleted == null ? 43 : $isDeleted.hashCode());
//        Object $createTime = this.getCreateTime();
//        result = result * 59 + ($createTime == null ? 43 : $createTime.hashCode());
//        Object $updateTime = this.getUpdateTime();
//        result = result * 59 + ($updateTime == null ? 43 : $updateTime.hashCode());
//        return result;
    }

    public String toString() {
        return "BaseEntity(createUser=" + this.getCreateBy() + ", createTime=" + this.getCreateTime() + ", updateUser=" + this.getUpdateBy() + ", updateTime=" + this.getUpdateTime() + ", status=" + this.getStatus() + ", isDeleted=" + this.getIsDeleted() + ")";
    }
}
