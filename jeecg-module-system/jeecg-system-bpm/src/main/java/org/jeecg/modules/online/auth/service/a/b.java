//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jeecg.modules.online.auth.service.a;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.online.auth.entity.OnlAuthPage;
import org.jeecg.modules.online.auth.entity.OnlAuthRelation;
import org.jeecg.modules.online.auth.mapper.OnlAuthPageMapper;
import org.jeecg.modules.online.auth.mapper.OnlAuthRelationMapper;
import org.jeecg.modules.online.auth.service.IOnlAuthPageService;
import org.jeecg.modules.online.auth.vo.AuthColumnVO;
import org.jeecg.modules.online.auth.vo.AuthPageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("onlAuthPageServiceImpl")
public class b extends ServiceImpl<OnlAuthPageMapper, OnlAuthPage> implements IOnlAuthPageService {
    @Autowired
    private OnlAuthRelationMapper onlAuthRelationMapper;

    public b() {
    }

    public void disableAuthColumn(AuthColumnVO authColumnVO) {
        LambdaUpdateWrapper<OnlAuthPage> wp = Wrappers.lambdaUpdate(OnlAuthPage.class)
                .eq(OnlAuthPage::getCgformId, authColumnVO.getCgformId())
                .eq(OnlAuthPage::getCode, authColumnVO.getCode())
                .eq(OnlAuthPage::getType, 1)
                .set(OnlAuthPage::getStatus, 0);
        this.update(wp);
    }

    @Transactional
    public void enableAuthColumn(AuthColumnVO authColumnVO) {
        String cgformId = authColumnVO.getCgformId();
        String code = authColumnVO.getCode();
        LambdaQueryWrapper<OnlAuthPage> var4 = Wrappers.lambdaQuery(OnlAuthPage.class)
                .eq(OnlAuthPage::getCgformId, cgformId)
                .eq(OnlAuthPage::getCode, code)
                .eq(OnlAuthPage::getType, 1);
        List<OnlAuthPage> var5 = this.list(var4);
        if (var5 != null && var5.size() > 0) {
            LambdaUpdateWrapper<OnlAuthPage> wp = Wrappers.lambdaUpdate(OnlAuthPage.class)
                    .eq(OnlAuthPage::getCgformId, cgformId)
                    .eq(OnlAuthPage::getCode, code)
                    .eq(OnlAuthPage::getType, 1)
                    .set(OnlAuthPage::getStatus, 1);
            this.update(wp);
        } else {
            ArrayList<OnlAuthPage> var7 = new ArrayList<>();
            var7.add(new OnlAuthPage(cgformId, code, 3, 5));
            var7.add(new OnlAuthPage(cgformId, code, 5, 5));
            var7.add(new OnlAuthPage(cgformId, code, 5, 3));
            this.saveBatch(var7);
        }

    }

    public void switchAuthColumn(AuthColumnVO authColumnVO) {
        String var2 = authColumnVO.getCgformId();
        String var3 = authColumnVO.getCode();
        int var4 = authColumnVO.getSwitchFlag();
        if (var4 == 1) {
            this.switchListShow(var2, var3, authColumnVO.isListShow());
        } else if (var4 == 2) {
            this.switchFormShow(var2, var3, authColumnVO.isFormShow());
        } else if (var4 == 3) {
            this.switchFormEditable(var2, var3, authColumnVO.isFormEditable());
        }

    }

    @Transactional
    public void switchFormShow(String cgformId, String code, boolean flag) {
        this.a(cgformId, code, 5, 5, flag);
    }

    @Transactional
    public void switchFormEditable(String cgformId, String code, boolean flag) {
        this.a(cgformId, code, 3, 5, flag);
    }

    @Transactional
    public void switchListShow(String cgformId, String code, boolean flag) {
        this.a(cgformId, code, 5, 3, flag);
    }

    public List<AuthPageVO> queryRoleAuthByFormId(String roleId, String cgformId, int type) {
        return ((OnlAuthPageMapper)this.baseMapper).queryRoleAuthByFormId(roleId, cgformId, type);
    }

    public List<AuthPageVO> queryRoleDataAuth(String roleId, String cgformId) {
        return ((OnlAuthPageMapper)this.baseMapper).queryRoleDataAuth(roleId, cgformId);
    }

    public List<AuthPageVO> queryAuthByFormId(String cgformId, int type) {
        return type == 1 ? ((OnlAuthPageMapper)this.baseMapper).queryAuthColumnByFormId(cgformId) : ((OnlAuthPageMapper)this.baseMapper).queryAuthButtonByFormId(cgformId);
    }

    public List<String> queryRoleNoAuthCode(String cgformId, Integer control, Integer page) {
        LoginUser var4 = (LoginUser)SecurityUtils.getSubject().getPrincipal();
        String var5 = var4.getId();
        return ((OnlAuthPageMapper)this.baseMapper).queryRoleNoAuthCode(var5, cgformId, control, page, (Integer)null);
    }

    public List<String> queryFormDisabledCode(String cgformId) {
        return this.queryRoleNoAuthCode(cgformId, 3, 5);
    }

    public List<String> queryHideCode(String userId, String cgformId, boolean isList) {
        return ((OnlAuthPageMapper)this.baseMapper).queryRoleNoAuthCode(userId, cgformId, 5, isList ? 3 : 5, (Integer)null);
    }

    public List<String> queryListHideColumn(String userId, String cgformId) {
        return ((OnlAuthPageMapper)this.baseMapper).queryRoleNoAuthCode(userId, cgformId, 5, 3, 1);
    }

    public List<String> queryFormHideColumn(String userId, String cgformId) {
        return ((OnlAuthPageMapper)this.baseMapper).queryRoleNoAuthCode(userId, cgformId, 5, 5, 1);
    }

    public List<String> queryFormHideButton(String userId, String cgformId) {
        return ((OnlAuthPageMapper)this.baseMapper).queryRoleNoAuthCode(userId, cgformId, 5, 5, 2);
    }

    public List<String> queryHideCode(String cgformId, boolean isList) {
        LoginUser var3 = (LoginUser)SecurityUtils.getSubject().getPrincipal();
        String var4 = var3.getId();
        return ((OnlAuthPageMapper)this.baseMapper).queryRoleNoAuthCode(var4, cgformId, 5, isList ? 3 : 5, (Integer)null);
    }

    public List<String> queryListHideButton(String userId, String cgformId) {
        if (userId == null) {
            LoginUser var3 = (LoginUser)SecurityUtils.getSubject().getPrincipal();
            userId = var3.getId();
        }

        return ((OnlAuthPageMapper)this.baseMapper).queryRoleNoAuthCode(userId, cgformId, 5, 3, 2);
    }

    private void a(String var1, String var2, int var3, int var4, boolean var5) {
        LambdaQueryWrapper<OnlAuthPage> wp = Wrappers.lambdaQuery(OnlAuthPage.class)
                .eq(OnlAuthPage::getCgformId, var1).eq(OnlAuthPage::getCode, var2)
                .eq(OnlAuthPage::getControl, var3).eq(OnlAuthPage::getPage, var4).eq(OnlAuthPage::getType, 1);
        OnlAuthPage onlAuthPage = (OnlAuthPage)((OnlAuthPageMapper)this.baseMapper).selectOne(wp);
        if (var5) {
            if (onlAuthPage == null) {
                OnlAuthPage authPage = new OnlAuthPage();
                authPage.setCgformId(var1);
                authPage.setCode(var2);
                authPage.setControl(var3);
                authPage.setPage(var4);
                authPage.setType(1);
                authPage.setStatus(1);
                ((OnlAuthPageMapper)this.baseMapper).insert(authPage);
            } else if (onlAuthPage.getStatus() == 0) {
                onlAuthPage.setStatus(1);
                ((OnlAuthPageMapper)this.baseMapper).updateById(onlAuthPage);
            }
        } else if (!var5 && onlAuthPage != null) {
            String id = onlAuthPage.getId();
            ((OnlAuthPageMapper)this.baseMapper).deleteById(id);
            LambdaQueryWrapper<OnlAuthRelation> wp2 = Wrappers.lambdaQuery(OnlAuthRelation.class).eq(OnlAuthRelation::getAuthId, id);
            this.onlAuthRelationMapper.delete(wp2);
        }

    }
}
