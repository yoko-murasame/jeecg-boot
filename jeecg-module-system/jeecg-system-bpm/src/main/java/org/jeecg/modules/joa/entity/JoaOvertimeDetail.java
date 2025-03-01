package org.jeecg.modules.joa.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * @Description: 调休明细表
 * @author： jeecg-boot
 * @date：   2019-04-03
 * @version： V1.0
 */
@Data
@TableName("joa_overtime_detail")
public class JoaOvertimeDetail implements Serializable {
    private static final long serialVersionUID = 1L;

	/**ID*/
	@TableId(type = IdType.ASSIGN_ID)
	private String id;
	/**调休申请ID*/
	private String overtimeLeaveId;
	/**加班ID*/
	private String overtimeId;
	/**调休申请（天）*/
	private Integer applyDay;
	/**调休申请（小时）*/
	private Integer applyHour;
	/**创建人*/
	private String createName;
	/**创建人id*/
	private String createBy;
	/**创建时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private java.util.Date createTime;
	/**修改人*/
	private String updateName;
	/**修改人id*/
	private String updateBy;
	/**修改时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private java.util.Date updateDate;
	/**所属部门*/
	private String sysOrgCode;
	/**所属公司*/
	private String sysCompanyCode;
	/**流程状态*/
	private String bpmStatus;
	/**逻辑删除标识0未删除1删除*/
	private Integer delFlag;
}
