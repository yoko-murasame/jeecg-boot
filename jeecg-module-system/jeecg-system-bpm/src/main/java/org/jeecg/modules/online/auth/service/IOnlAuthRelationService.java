//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jeecg.modules.online.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.online.auth.entity.OnlAuthRelation;

import java.util.List;

public interface IOnlAuthRelationService extends IService<OnlAuthRelation> {
    void saveRoleAuth(String var1, String var2, int var3, List<String> var4);
}
