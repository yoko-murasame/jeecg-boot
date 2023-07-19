//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jeecg.modules.online.auth.service.a;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.online.auth.entity.OnlAuthRelation;
import org.jeecg.modules.online.auth.mapper.OnlAuthRelationMapper;
import org.jeecg.modules.online.auth.service.IOnlAuthRelationService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service("onlAuthRelationServiceImpl")
public class OnlAuthRelationServiceImpl extends ServiceImpl<OnlAuthRelationMapper, OnlAuthRelation> implements IOnlAuthRelationService {
    public OnlAuthRelationServiceImpl() {
    }

    public void saveRoleAuth(String roleId, String cgformId, int type, List<String> authIds) {
        LambdaQueryWrapper<OnlAuthRelation> wp = Wrappers.lambdaQuery(OnlAuthRelation.class)
                .eq(OnlAuthRelation::getCgformId, cgformId)
                .eq(OnlAuthRelation::getType, type)
                .eq(OnlAuthRelation::getRoleId, roleId);
        this.baseMapper.delete(wp);

        ArrayList<OnlAuthRelation> onlAuthRelations = new ArrayList<>();
        Iterator<String> stringIterator = authIds.iterator();

        while(stringIterator.hasNext()) {
            String next = (String)stringIterator.next();
            OnlAuthRelation onlAuthRelation = new OnlAuthRelation();
            onlAuthRelation.setAuthId(next);
            onlAuthRelation.setCgformId(cgformId);
            onlAuthRelation.setRoleId(roleId);
            onlAuthRelation.setType(type);
            onlAuthRelations.add(onlAuthRelation);
        }

        this.saveBatch(onlAuthRelations);
    }
}
