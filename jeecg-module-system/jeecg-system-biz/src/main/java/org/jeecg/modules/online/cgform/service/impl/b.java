package org.jeecg.modules.online.cgform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.online.cgform.entity.OnlCgformButton;
import org.jeecg.modules.online.cgform.mapper.OnlCgformButtonMapper;
import org.jeecg.modules.online.cgform.service.IOnlCgformButtonService;
import org.springframework.stereotype.Service;

/* compiled from: OnlCgformButtonServiceImpl.java */
@Service("onlCgformButtonServiceImpl")
/* loaded from: hibernate-common-ol-5.4.74(2).jar:org/jeecg/modules/online/cgform/service/impl/b.class */
public class b extends ServiceImpl<OnlCgformButtonMapper, OnlCgformButton> implements IOnlCgformButtonService {

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformButtonService
    public void saveButton(OnlCgformButton onlCgformButton) {
        Long selectCount =this.baseMapper.selectCount(new LambdaQueryWrapper<OnlCgformButton>()
                .eq(OnlCgformButton::getButtonCode, onlCgformButton.getButtonCode())
                .eq(OnlCgformButton::getCgformHeadId,onlCgformButton.getCgformHeadId()));
        if (selectCount == null || selectCount.intValue() == 0) {
            save(onlCgformButton);
        }
    }
}
