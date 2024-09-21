package org.jeecg.modules.joa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.joa.entity.JoaOvertimeLeave;

/**
 * @Description: 调休申请表
 * @author： jeecg-boot
 * @date：   2019-04-08
 * @version： V1.0
 */
public interface IJoaOvertimeLeaveService extends IService<JoaOvertimeLeave> {

	public boolean joaOvertimeLeaveAdd(JoaOvertimeLeave joaOvertimeLeave);

}
