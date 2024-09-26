//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jeecg.modules.online.auth.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.lang.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.online.auth.entity.OnlAuthData;
import org.jeecg.modules.online.auth.entity.OnlAuthPage;
import org.jeecg.modules.online.auth.entity.OnlAuthRelation;
import org.jeecg.modules.online.auth.service.IOnlAuthDataService;
import org.jeecg.modules.online.auth.service.IOnlAuthPageService;
import org.jeecg.modules.online.auth.service.IOnlAuthRelationService;
import org.jeecg.modules.online.auth.vo.AuthColumnVO;
import org.jeecg.modules.online.auth.vo.AuthPageVO;
import org.jeecg.modules.online.cgform.entity.OnlCgformButton;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;
import org.jeecg.modules.online.cgform.entity.OnlCgformHead;
import org.jeecg.modules.online.cgform.service.IOnlCgformButtonService;
import org.jeecg.modules.online.cgform.service.IOnlCgformFieldService;
import org.jeecg.modules.online.cgform.service.IOnlCgformHeadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController("onlCgformAuthController")
@RequestMapping({"/online/cgform/api"})
public class OnlCgformAuthController {
    private static final Logger logger = LoggerFactory.getLogger(OnlCgformAuthController.class);
    @Autowired
    private IOnlCgformFieldService onlCgformFieldService;
    @Autowired
    private IOnlAuthDataService onlAuthDataService;
    @Autowired
    private IOnlAuthPageService onlAuthPageService;
    @Autowired
    private IOnlCgformButtonService onlCgformButtonService;
    @Autowired
    private IOnlAuthRelationService onlAuthRelationService;
    @Autowired
    private IOnlCgformHeadService onlCgformHeadService;

    public OnlCgformAuthController() {
    }

    @GetMapping({"/authData/{cgformId}"})
    public Result<List<OnlAuthData>> a(@PathVariable("cgformId") String var1) {
        LambdaQueryWrapper<OnlAuthData> wp = Wrappers.lambdaQuery(OnlAuthData.class).eq(OnlAuthData::getCgformId, var1);
        List<OnlAuthData> list = this.onlAuthDataService.list(wp);
        return Result.OK(list);
    }

    @PostMapping({"/authData"})
    public Result<OnlAuthData> a(@RequestBody OnlAuthData var1) {
        Result var2 = new Result();

        try {
            this.onlAuthDataService.save(var1);
            var2.success("添加成功！");
        } catch (Exception var4) {
            logger.error(var4.getMessage(), var4);
            var2.error500("操作失败");
        }

        return var2;
    }

    @PutMapping({"/authData"})
    public Result<OnlAuthData> b(@RequestBody OnlAuthData var1) {
        Result var2 = new Result();
        this.onlAuthDataService.updateById(var1);
        return var2;
    }

    @DeleteMapping({"/authData/{id}"})
    public Result<?> b(@PathVariable("id") String var1) {
        this.onlAuthDataService.deleteOne(var1);
        return Result.ok("删除成功!");
    }

    @GetMapping({"/authButton/{cgformId}"})
    public Result<Map<String, Object>> c(@PathVariable("cgformId") String var1) {
        LambdaQueryWrapper<OnlCgformButton> wp1 = Wrappers.lambdaQuery(OnlCgformButton.class)
                .eq(OnlCgformButton::getCgformHeadId, var1).eq(OnlCgformButton::getButtonStatus, 1)
                .select(OnlCgformButton::getButtonCode, OnlCgformButton::getButtonName, OnlCgformButton::getButtonStyle);
        List<OnlCgformButton> buttonList = this.onlCgformButtonService.list(wp1);
        LambdaQueryWrapper<OnlAuthPage> wp2 = Wrappers.lambdaQuery(OnlAuthPage.class)
                .eq(OnlAuthPage::getCgformId, var1).eq(OnlAuthPage::getType, 2);
        List<OnlAuthPage> authList = this.onlAuthPageService.list(wp2);
        HashMap<String, Object> res = new HashMap<>();
        res.put("buttonList", buttonList);
        res.put("authList", authList);
        return Result.OK(res);
    }

    @PostMapping({"/authButton"})
    public Result<OnlAuthPage> a(@RequestBody OnlAuthPage var1) {
        Result var2 = new Result();

        try {
            String var3 = var1.getId();
            boolean var4 = false;
            if (oConvertUtils.isNotEmpty(var3)) {
                OnlAuthPage var5 = (OnlAuthPage)this.onlAuthPageService.getById(var3);
                if (var5 != null) {
                    var4 = true;
                    var5.setStatus(1);
                    var5.setAlias(var1.getAlias());
                    this.onlAuthPageService.updateById(var5);
                }
            }

            if (!var4) {
                var1.setStatus(1);
                this.onlAuthPageService.save(var1);
            }

            var2.setResult(var1);
            var2.success("操作成功！");
        } catch (Exception var6) {
            logger.error(var6.getMessage(), var6);
            var2.error500("操作失败");
        }

        return var2;
    }

    @PutMapping({"/authButton/{id}"})
    public Result<?> d(@PathVariable("id") String id) {
        LambdaUpdateWrapper<OnlAuthPage> wp = Wrappers.lambdaUpdate(OnlAuthPage.class).eq(OnlAuthPage::getId, id).set(OnlAuthPage::getStatus, 0);
        this.onlAuthPageService.update(wp);
        return Result.ok("操作成功");
    }

    @GetMapping({"/authColumn/{cgformId}"})
    public Result<List<AuthColumnVO>> e(@PathVariable("cgformId") String var1) {

        LambdaQueryWrapper<OnlCgformField> wp1 = Wrappers.lambdaQuery(OnlCgformField.class)
                .eq(OnlCgformField::getCgformHeadId, var1)
                        .orderByAsc(OnlCgformField::getOrderNum);
        List<OnlCgformField> cgformFieldList = this.onlCgformFieldService.list(wp1);
        if (cgformFieldList == null || cgformFieldList.size() == 0) {
            Result.error("未找到对应字段信息!");
        }

        LambdaQueryWrapper<OnlAuthPage> wp2 = Wrappers.lambdaQuery(OnlAuthPage.class).eq(OnlAuthPage::getCgformId, var1).eq(OnlAuthPage::getType, 1);
        List<OnlAuthPage> authPageList = this.onlAuthPageService.list(wp2);
        List<AuthColumnVO> authColumnVOArrayList = new ArrayList<>();
        Iterator<OnlCgformField> iterator = cgformFieldList.iterator();

        while(iterator.hasNext()) {
            OnlCgformField cgformField = (OnlCgformField)iterator.next();
            AuthColumnVO authColumnVO = new AuthColumnVO(cgformField);
            Integer status = 0;
            boolean listShow = false;
            boolean formShow = false;
            boolean formEditable = false;

            for(int var15 = 0; var15 < authPageList.size(); ++var15) {
                OnlAuthPage var16 = (OnlAuthPage)authPageList.get(var15);
                if (cgformField.getDbFieldName().equals(var16.getCode())) {
                    status = var16.getStatus();
                    if (var16.getPage() == 3 && var16.getControl() == 5) {
                        listShow = true;
                    }

                    if (var16.getPage() == 5) {
                        if (var16.getControl() == 5) {
                            formShow = true;
                        } else if (var16.getControl() == 3) {
                            formEditable = true;
                        }
                    }
                }
            }

            authColumnVO.setStatus(status);
            authColumnVO.setListShow(listShow);
            authColumnVO.setFormShow(formShow);
            authColumnVO.setFormEditable(formEditable);
            authColumnVOArrayList.add(authColumnVO);
        }

        Result<List<AuthColumnVO>> ok = Result.OK(authColumnVOArrayList);
        ok.setMessage("加载字段权限数据完成");
        return ok;
    }

    @PutMapping({"/authColumn"})
    public Result<?> a(@RequestBody AuthColumnVO var1) {
        Result var2 = new Result();

        try {
            if (var1.getStatus() == 1) {
                this.onlAuthPageService.enableAuthColumn(var1);
            } else {
                this.onlAuthPageService.disableAuthColumn(var1);
            }

            var2.success("操作成功！");
        } catch (Exception var4) {
            logger.error(var4.getMessage(), var4);
            var2.error500("操作失败");
        }

        return var2;
    }

    @PostMapping({"/authColumn"})
    public Result<?> b(@RequestBody AuthColumnVO var1) {
        Result var2 = new Result();

        try {
            this.onlAuthPageService.switchAuthColumn(var1);
            var2.success("操作成功！");
        } catch (Exception var4) {
            logger.error(var4.getMessage(), var4);
            var2.error500("操作失败");
        }

        return var2;
    }

    @GetMapping({"/authPage/{cgformId}/{type}"})
    public Result<List<AuthPageVO>> a(@PathVariable("cgformId") String var1, @PathVariable("type") Integer var2) {
        Result var3 = new Result();
        List var4 = this.onlAuthPageService.queryAuthByFormId(var1, var2);
        var3.setResult(var4);
        var3.setSuccess(true);
        return var3;
    }

    @GetMapping({"/validAuthData/{cgformId}"})
    public Result<List<OnlAuthData>> f(@PathVariable("cgformId") String var1) {
        LambdaQueryWrapper<OnlAuthData> wp = Wrappers.lambdaQuery(OnlAuthData.class)
                .eq(OnlAuthData::getCgformId, var1).eq(OnlAuthData::getStatus, 1)
                .select(OnlAuthData::getId, OnlAuthData::getRuleName);
        List<OnlAuthData> res = this.onlAuthDataService.list(wp);
        return Result.OK(res);
    }

    @GetMapping({"/roleAuth"})
    public Result<List<OnlAuthRelation>> a(@RequestParam("roleId") String roleId, @RequestParam("cgformId") String cgformId,
                                           @RequestParam("type") Integer type) {
        LambdaQueryWrapper<OnlAuthRelation> wp = Wrappers.lambdaQuery(OnlAuthRelation.class)
                .eq(OnlAuthRelation::getRoleId, roleId)
                .eq(OnlAuthRelation::getCgformId, cgformId)
                .eq(OnlAuthRelation::getType, type)
                .select(OnlAuthRelation::getAuthId);
        List<OnlAuthRelation> list = this.onlAuthRelationService.list(wp);
        return Result.OK(list);
    }

    @PostMapping({"/roleColumnAuth/{roleId}/{cgformId}"})
    public Result<?> a(@PathVariable("roleId") String roleId, @PathVariable("cgformId") String cgformId, @RequestBody JSONObject param) {
        JSONArray authId = param.getJSONArray("authId");
        List<String> list = authId.toJavaList(String.class);
        this.onlAuthRelationService.saveRoleAuth(roleId, cgformId, 1, list);
        return Result.OK();
    }

    @PostMapping({"/roleButtonAuth/{roleId}/{cgformId}"})
    public Result<?> b(@PathVariable("roleId") String roleId, @PathVariable("cgformId") String cgformId, @RequestBody JSONObject param) {
        JSONArray authId = param.getJSONArray("authId");
        List<String> list = authId.toJavaList(String.class);
        this.onlAuthRelationService.saveRoleAuth(roleId, cgformId, 2, list);
        return Result.OK();
    }

    @PostMapping({"/roleDataAuth/{roleId}/{cgformId}"})
    public Result<?> c(@PathVariable("roleId") String roleId, @PathVariable("cgformId") String cgformId, @RequestBody JSONObject param) {
        JSONArray authId = param.getJSONArray("authId");
        List<String> list = authId.toJavaList(String.class);
        this.onlAuthRelationService.saveRoleAuth(roleId, cgformId, 3, list);
        return Result.OK();
    }

    @GetMapping({"/getAuthColumn/{desformCode}"})
    public Result<List<AuthColumnVO>> g(@PathVariable("desformCode") String desformCode) {
        LambdaQueryWrapper<OnlCgformHead> wp1 = Wrappers.lambdaQuery(OnlCgformHead.class)
                .eq(OnlCgformHead::getTableName, desformCode);
        OnlCgformHead cgformHead = this.onlCgformHeadService.getOne(wp1);
        if (cgformHead == null) {
            Result.error("未找到对应字段信息!");
        }

        LambdaQueryWrapper<OnlCgformField> wp2 = Wrappers.lambdaQuery(OnlCgformField.class)
                .eq(OnlCgformField::getCgformHeadId, cgformHead.getId()).orderByAsc(OnlCgformField::getOrderNum);
        List<OnlCgformField> cgformFieldList = this.onlCgformFieldService.list(wp2);
        if (cgformFieldList == null || cgformFieldList.size() == 0) {
            Result.error("未找到对应字段信息!");
        }

        List<AuthColumnVO> authColumnVOS = new ArrayList<>();
        Iterator<OnlCgformField> cgformFieldIterator = cgformFieldList.iterator();

        while(cgformFieldIterator.hasNext()) {
            OnlCgformField cgformField = (OnlCgformField)cgformFieldIterator.next();
            AuthColumnVO authColumnVO = new AuthColumnVO(cgformField);
            authColumnVO.setTableName(cgformHead.getTableName());
            authColumnVO.setTableNameTxt(cgformHead.getTableTxt());
            authColumnVOS.add(authColumnVO);
        }

        if (oConvertUtils.isNotEmpty(cgformHead.getSubTableStr())) {
            String[] subTables = cgformHead.getSubTableStr().split(",");
            int length = subTables.length;

            for(int index = 0; index < length; ++index) {
                String subTable = subTables[index];
                LambdaQueryWrapper<OnlCgformHead> wp3 = Wrappers.lambdaQuery(OnlCgformHead.class).eq(OnlCgformHead::getTableName, subTable);
                OnlCgformHead one = this.onlCgformHeadService.getOne(wp3);
                if (one != null) {
                    LambdaQueryWrapper<OnlCgformField> wp4 = Wrappers.lambdaQuery(OnlCgformField.class).eq(OnlCgformField::getCgformHeadId, one.getId());
                    List<OnlCgformField> list = this.onlCgformFieldService.list(wp4);
                    if (list != null) {
                        Iterator<OnlCgformField> iterator = list.iterator();
                        while(iterator.hasNext()) {
                            OnlCgformField cgformField = (OnlCgformField)iterator.next();
                            AuthColumnVO authColumnVO = new AuthColumnVO(cgformField);
                            authColumnVO.setTableName(one.getTableName());
                            authColumnVO.setTableNameTxt(one.getTableTxt());
                            authColumnVOS.add(authColumnVO);
                        }
                    }
                }
            }
        }

        Result<List<AuthColumnVO>> res = Result.OK(authColumnVOS);
        res.setMessage("加载字段权限数据完成");
        return res;
    }
}
