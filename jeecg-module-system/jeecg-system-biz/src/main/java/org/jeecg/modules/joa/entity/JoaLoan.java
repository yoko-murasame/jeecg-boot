package org.jeecg.modules.joa.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * @Description: 借款表
 * @author： jeecg-boot
 * @date：   2019-04-08
 * @version： V1.0
 */
@Data
@TableName("joa_loan")
public class JoaLoan implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
	private String id;
	/**申请编号*/
	private String applyNo;
	/**出差申请编号*/
	private String tripApplyNo;
	/**借款人id*/
	private String loanUserId;
	/**借款人*/
	private String loanUserName;
	/**部门id*/
	private String departId;
	/**部门名称*/
	private String departName;
	/**借款时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private java.util.Date loanTime;
	/**借款金额*/
	private java.math.BigDecimal loanAmount;
	/**借款用途*/
	private String loanUsage;
	/**备注*/
	private String remarks;
	/**部门领导id*/
	private String departLeaderId;
	/**部门领导审核*/
	private String departLeaderAudit;
	/**财务用户id*/
	private String financeUserId;
	/**财务审核*/
	private String financeAudit;
	/**出纳员id*/
	private String cashierId;
	/**出纳放款金额*/
	private java.math.BigDecimal cashierLoanAmount;
	/**出纳放款时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private java.util.Date cashierLoanTime;
	/**总经理id*/
	private String managerId;
	/**总经理审核*/
	private String managerAudit;
	/**流转状态*/
	private String bpmStatus;
	/**创建人登录名称*/
	private String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private java.util.Date createTime;
	/**更新人登录名称*/
	private String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private java.util.Date updateTime;
}
