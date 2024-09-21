package org.jeecg.modules.joa.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * @Description: 加班申请单
 * @author： jeecg-boot
 * @date：   2019-04-01
 * @version： V1.0
 */
@Data
@TableName("joa_overtime")
public class JoaOvertime implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
	private String id;
	/**申请人*/
	private String applyUser;
	/**部门*/
	private String department;
	/**申请时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private java.util.Date applyTime;
	/**加班时段*/
	private String overtimeType;
	/**加班开始时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private java.util.Date beginTime;
	/**加班结束时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private java.util.Date endTime;
	/**加班原由*/
	private String reason;
	/**总加班时间 天*/
	private String totalDay;
	/**总加班时间 小时*/
	private String totalHour;
	/**直接领导审批*/
	private String leaderRemark;
	/**部门领导审批*/
	private String deptLeaderRemark;
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
	/**未补偿天数 */
	private Integer daysUnpaid;
	/**未补偿小时 */
	private Integer hourUnpaid;
	@TableField(exist = false)
	/**补偿天数 */
	private Integer day;
	@TableField(exist = false)
	/**补偿小时 */
	private Integer hour;
	@TableField(exist = false)
	/** key*/
	private Integer key;
}
