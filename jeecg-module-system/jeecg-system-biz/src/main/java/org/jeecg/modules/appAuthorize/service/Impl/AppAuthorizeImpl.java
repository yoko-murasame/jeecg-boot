package org.jeecg.modules.appAuthorize.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.appAuthorize.entity.DO.AppAuthorize;
import org.jeecg.modules.appAuthorize.mapper.AppAuthorizeMapper;
import org.jeecg.modules.appAuthorize.service.IAppAuthorizeService;
import org.springframework.stereotype.Service;

@Service
public class AppAuthorizeImpl extends ServiceImpl<AppAuthorizeMapper, AppAuthorize> implements IAppAuthorizeService {
}
