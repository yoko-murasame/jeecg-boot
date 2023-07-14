package org.jeecg.modules.listener.service;


import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.listener.entity.OaOfficialdocDeUser;

public interface IOaOfficialdocDeUserService extends IService<OaOfficialdocDeUser> {
    /*根据部门id获取用户信息*/
    OaOfficialdocDeUser selectByDepartId(String DepartId);
}
