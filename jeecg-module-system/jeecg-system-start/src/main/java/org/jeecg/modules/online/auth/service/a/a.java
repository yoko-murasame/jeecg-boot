//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jeecg.modules.online.auth.service.a;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.online.auth.entity.OnlAuthData;
import org.jeecg.modules.online.auth.entity.OnlAuthRelation;
import org.jeecg.modules.online.auth.mapper.OnlAuthDataMapper;
import org.jeecg.modules.online.auth.mapper.OnlAuthRelationMapper;
import org.jeecg.modules.online.auth.service.IOnlAuthDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("onlAuthDataServiceImpl")
public class a extends ServiceImpl<OnlAuthDataMapper, OnlAuthData> implements IOnlAuthDataService {
    @Autowired
    private OnlAuthRelationMapper onlAuthRelationMapper;

    public a() {
    }

    public void deleteOne(String id) {
        this.removeById(id);
        LambdaQueryWrapper<OnlAuthRelation> var2 = new LambdaQueryWrapper();
        this.onlAuthRelationMapper.delete((Wrapper)var2.eq(OnlAuthRelation::getAuthId, id));
    }
}
