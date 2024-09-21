//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jeecg.modules.online.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.online.auth.entity.OnlAuthPage;
import org.jeecg.modules.online.auth.vo.AuthColumnVO;
import org.jeecg.modules.online.auth.vo.AuthPageVO;

import java.util.List;

public interface IOnlAuthPageService extends IService<OnlAuthPage> {
    void disableAuthColumn(AuthColumnVO var1);

    void enableAuthColumn(AuthColumnVO var1);

    void switchAuthColumn(AuthColumnVO var1);

    void switchFormShow(String var1, String var2, boolean var3);

    void switchFormEditable(String var1, String var2, boolean var3);

    void switchListShow(String var1, String var2, boolean var3);

    List<AuthPageVO> queryRoleAuthByFormId(String var1, String var2, int var3);

    List<AuthPageVO> queryRoleDataAuth(String var1, String var2);

    List<AuthPageVO> queryAuthByFormId(String var1, int var2);

    List<String> queryRoleNoAuthCode(String var1, Integer var2, Integer var3);

    List<String> queryFormDisabledCode(String var1);

    List<String> queryHideCode(String var1, String var2, boolean var3);

    List<String> queryListHideColumn(String var1, String var2);

    List<String> queryFormHideColumn(String var1, String var2);

    List<String> queryFormHideButton(String var1, String var2);

    List<String> queryHideCode(String var1, boolean var2);

    List<String> queryListHideButton(String var1, String var2);
}
