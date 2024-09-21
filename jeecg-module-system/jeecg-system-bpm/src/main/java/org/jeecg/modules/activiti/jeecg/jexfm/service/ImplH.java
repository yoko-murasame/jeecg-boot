package org.jeecg.modules.activiti.jeecg.jexfm.service;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.modules.online.desform.entity.DesignForm;
import org.jeecg.modules.online.desform.entity.DesignFormUrlAuth;
import org.jeecg.modules.online.desform.mapper.DesignFormUrlAuthMapper;
import org.jeecg.modules.online.desform.service.IDesignFormService;
import org.jeecg.modules.online.desform.service.IDesignFormUrlAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("designFormUrlAuthServiceImpl")
public class ImplH extends ServiceImpl<DesignFormUrlAuthMapper, DesignFormUrlAuth> implements IDesignFormUrlAuthService {
    @Autowired
    DesignFormUrlAuthMapper mapper;
    @Autowired
    @Lazy
    IDesignFormService designFormService;

    public ImplH() {
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    public boolean initialUrlAuth(String desformCode) {
        DesignForm var2 = this.designFormService.getByCode(desformCode);
        if (var2 != null) {
            String var3 = var2.getId();
            String var4 = var2.getDesformCode();
            this.mapper.insert(new DesignFormUrlAuth(var3, var4, "add"));
            this.mapper.insert(new DesignFormUrlAuth(var3, var4, "edit"));
            this.mapper.insert(new DesignFormUrlAuth(var3, var4, "detail"));
            return true;
        } else {
            return false;
        }
    }

    public boolean urlIsPassed(String desformCode, String urlType) {
        Integer var3 = this.getUrlStatus(desformCode, urlType);
        return CommonConstant.DESIGN_FORM_URL_STATUS_PASSED.equals(var3);
    }

    public Integer getUrlStatus(String desformCode, String urlType) {
        if (this.a(urlType)) {
            LambdaQueryWrapper<DesignFormUrlAuth> var3 = this.b(desformCode);
            if (var3 != null) {
                var3.eq(DesignFormUrlAuth::getUrlType, urlType);
                DesignFormUrlAuth var4 = (DesignFormUrlAuth)this.mapper.selectOne(var3);
                if (var4 == null) {
                    this.initialUrlAuth(desformCode);
                    return CommonConstant.DESIGN_FORM_URL_STATUS_PASSED;
                }

                return var4.getUrlStatus();
            }
        }

        return null;
    }

    public boolean setUrlStatus(String desformCode, String urlType, Integer status) {
        LambdaQueryWrapper var4 = this.b(desformCode);
        return var4 != null ? this.setUrlStatus(var4, urlType, status) : false;
    }

    public boolean setUrlStatus(LambdaQueryWrapper<DesignFormUrlAuth> wrapper, String urlType, Integer status) {
        DesignFormUrlAuth var4 = new DesignFormUrlAuth();
        var4.setUrlStatus(status);
        wrapper.eq(DesignFormUrlAuth::getUrlType, urlType);
        return this.mapper.update(var4, wrapper) > 0;
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    public boolean setAllUrlStatus(String desformCode, Integer... status) {
        if (this.designFormService.getByCode(desformCode) != null) {
            this.setUrlStatus(this.c(desformCode), "add", status[0]);
            this.setUrlStatus(this.c(desformCode), "edit", status[1]);
            this.setUrlStatus(this.c(desformCode), "detail", status[2]);
            return true;
        } else {
            return false;
        }
    }

    private boolean a(String var1) {
        byte var3 = -1;
        switch(var1.hashCode()) {
            case -1335224239:
                if (var1.equals("detail")) {
                    var3 = 2;
                }
                break;
            case 96417:
                if (var1.equals("add")) {
                    var3 = 0;
                }
                break;
            case 3108362:
                if (var1.equals("edit")) {
                    var3 = 1;
                }
                break;
            case 3619493:
                if (var1.equals("view")) {
                    var3 = 3;
                }
        }

        switch(var3) {
            case 0:
            case 1:
            case 2:
            case 3:
                return true;
            default:
                return false;
        }
    }

    private LambdaQueryWrapper<DesignFormUrlAuth> b(String var1) {
        return this.designFormService.getByCode(var1) != null ? this.c(var1) : null;
    }

    private LambdaQueryWrapper<DesignFormUrlAuth> c(String var1) {
        LambdaQueryWrapper<DesignFormUrlAuth> var2 = new LambdaQueryWrapper<>();
        var2.eq(DesignFormUrlAuth::getDesformCode, var1);
        return var2;
    }
}
