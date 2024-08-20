package org.jeecg.modules.listener.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.listener.entity.OaOfficialdocDeUser;
import org.jeecg.modules.listener.mapper.OaOfficialdocDeUserMapper;
import org.jeecg.modules.listener.service.IOaOfficialdocDeUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OaOfficialdocDeUserServiceImpl extends ServiceImpl<OaOfficialdocDeUserMapper, OaOfficialdocDeUser> implements IOaOfficialdocDeUserService {

    @Autowired
    private OaOfficialdocDeUserMapper oaOfficialdocDeUserMapper;
    @Override
    public OaOfficialdocDeUser selectByDepartId(String DepartId) {
        return oaOfficialdocDeUserMapper.selectByDepartId(DepartId);
    }
}
