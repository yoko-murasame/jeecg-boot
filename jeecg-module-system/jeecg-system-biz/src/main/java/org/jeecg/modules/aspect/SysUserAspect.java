//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jeecg.modules.aspect;

import com.alibaba.fastjson.JSONObject;
import java.util.Iterator;
import java.util.List;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.extbpm.process.pojo.UserInfo;
import org.jeecg.modules.extbpm.process.service.IExtActProcessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component("sysUserAspect")
public class SysUserAspect {
    private static final Logger log = LoggerFactory.getLogger(SysUserAspect.class);
    @Autowired
    private ISysBaseAPI sysBaseAPI;
    @Autowired
    private IExtActProcessService extActProcessService;

    public SysUserAspect() {
    }

    @Pointcut("execution(public * org.jeecg.modules.system.controller.SysUserController.add(..))")
    public void excudeServiceAdd() {
    }

    @Around("excudeServiceAdd()")
    public Object doAroundAdd(ProceedingJoinPoint pjp) throws Throwable {
        Object var2 = pjp.proceed();

        try {
            Object[] var3 = pjp.getArgs();
            JSONObject var4 = (JSONObject)var3[0];
            String var5 = var4.getString("username");
            log.info("------------SysUserAspect------add----" + var5);
            LoginUser var6 = this.sysBaseAPI.getUserByName(var5);
            if (var6 != null && CommonConstant.ACT_SYNC_1.equals(var6.getActivitiSync())) {
                UserInfo var7 = new UserInfo();
                var7.setUuid(var6.getId());
                var7.setFirstName(var6.getRealname());
                var7.setEmail(var6.getEmail());
                var7.setId(var6.getUsername());
                List var8 = this.extActProcessService.getSysRolesByUserId(var7.getUuid());
                this.extActProcessService.saveActIdMembership(var7, var8);
            }
        } catch (Exception var9) {
        }

        return var2;
    }

    @Pointcut("execution(public * org.jeecg.modules.system.controller.SysUserController.edit(..))")
    public void excudeServiceEdit() {
    }

    @Around("excudeServiceEdit()")
    public Object doAroundEdit(ProceedingJoinPoint pjp) throws Throwable {
        Object var2 = pjp.proceed();

        try {
            Object[] var3 = pjp.getArgs();
            JSONObject var4 = (JSONObject)var3[0];
            String var5 = var4.getString("username");
            log.info("------------SysUserAspect------edit----" + var5);
            LoginUser var6 = this.sysBaseAPI.getUserByName(var5);
            if (var6 != null) {
                if (CommonConstant.ACT_SYNC_1.equals(var6.getActivitiSync())) {
                    UserInfo var7 = new UserInfo();
                    var7.setUuid(var6.getId());
                    var7.setFirstName(var6.getRealname());
                    var7.setEmail(var6.getEmail());
                    var7.setId(var6.getUsername());
                    List var8 = this.extActProcessService.getSysRolesByUserId(var7.getUuid());
                    this.extActProcessService.saveActIdMembership(var7, var8);
                } else {
                    this.extActProcessService.deleteActIdMembership(var5);
                }
            }
        } catch (Exception var9) {
        }

        return var2;
    }

    @Pointcut("execution(public * org.jeecg.modules.system.controller.SysUserController.putRecycleBin(..))")
    public void excudeServicePutRecycleBin() {
    }

    @Around("excudeServicePutRecycleBin()")
    public Object doAroundPutRecycleBin(ProceedingJoinPoint pjp) throws Throwable {
        Object var2 = pjp.proceed();

        try {
            Object[] var3 = pjp.getArgs();
            JSONObject var4 = (JSONObject)var3[0];
            String var5 = var4.getString("userIds");
            log.info("------------SysUserAspect------PutRecycleBin----" + var5);
            if (oConvertUtils.isNotEmpty(var5)) {
                List var6 = this.sysBaseAPI.queryAllUserByIds(var5.split(","));
                Iterator var7 = var6.iterator();

                while(var7.hasNext()) {
                    LoginUser var8 = (LoginUser)var7.next();
                    if (CommonConstant.ACT_SYNC_1.equals(var8.getActivitiSync())) {
                        UserInfo var9 = new UserInfo();
                        var9.setUuid(var8.getId());
                        var9.setFirstName(var8.getRealname());
                        var9.setEmail(var8.getEmail());
                        var9.setId(var8.getUsername());
                        List var10 = this.extActProcessService.getSysRolesByUserId(var9.getUuid());
                        this.extActProcessService.saveActIdMembership(var9, var10);
                    }
                }
            }
        } catch (Exception var11) {
        }

        return var2;
    }

    @Pointcut("execution(public * org.jeecg.modules.system.controller.SysUserController.delete(..))")
    public void excudeServiceDelete() {
    }

    @Around("excudeServiceDelete()")
    public Object doAroundDelete(ProceedingJoinPoint pjp) throws Throwable {
        try {
            Object[] var2 = pjp.getArgs();
            String var3 = (String)var2[0];
            log.info("------------SysUserAspect------delete----" + var3);
            LoginUser var4 = this.sysBaseAPI.getUserById(var3);
            if (var4 != null) {
                this.extActProcessService.deleteActIdMembership(var4.getUsername());
            }
        } catch (Exception var5) {
        }

        Object var6 = pjp.proceed();
        return var6;
    }

    @Pointcut("execution(public * org.jeecg.modules.system.controller.SysUserController.deleteBatch(..))")
    public void excudeServiceDeleteBatch() {
    }

    @Around("excudeServiceDeleteBatch()")
    public Object doAroundDeleteBatch(ProceedingJoinPoint pjp) throws Throwable {
        try {
            Object[] var2 = pjp.getArgs();
            String var3 = (String)var2[0];
            log.info("------------SysUserAspect------deleteBatch----" + var3);
            String[] var4 = var3.split(",");
            String[] var5 = var4;
            int var6 = var4.length;

            for(int var7 = 0; var7 < var6; ++var7) {
                String var8 = var5[var7];
                LoginUser var9 = this.sysBaseAPI.getUserById(var8);
                if (var9 != null) {
                    this.extActProcessService.deleteActIdMembership(var9.getUsername());
                }
            }
        } catch (Exception var10) {
        }

        Object var11 = pjp.proceed();
        return var11;
    }
}
