//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jeecg.modules.extbpm.process.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.extbpm.process.entity.ExtActFlowData;
import org.jeecg.modules.extbpm.process.entity.ExtActTaskNotification;
import org.jeecg.modules.extbpm.process.service.IExtActFlowDataService;
import org.jeecg.modules.extbpm.process.service.IExtActTaskNotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController("extActFlowDataController")
@RequestMapping({"/act/process/extActFlowData"})
public class e {
    private static final Logger a = LoggerFactory.getLogger(e.class);
    @Autowired
    private IExtActFlowDataService extActFlowDataService;
    @Autowired
    private IExtActTaskNotificationService extActTaskNotificationService;

    public e() {
    }

    @GetMapping({"/list"})
    public Result<IPage<ExtActFlowData>> a(ExtActFlowData var1, @RequestParam(name = "pageNo",defaultValue = "1") Integer var2, @RequestParam(name = "pageSize",defaultValue = "10") Integer var3, HttpServletRequest var4) {
        Result var5 = new Result();
        QueryWrapper var6 = new QueryWrapper(var1);
        Page var7 = new Page((long)var2, (long)var3);
        String var8 = var4.getParameter("column");
        String var9 = var4.getParameter("order");
        if (oConvertUtils.isNotEmpty(var8) && oConvertUtils.isNotEmpty(var9)) {
            if ("asc".equals(var9)) {
                var6.orderByAsc(oConvertUtils.camelToUnderline(var8));
            } else {
                var6.orderByDesc(oConvertUtils.camelToUnderline(var8));
            }
        }

        IPage var10 = this.extActFlowDataService.page(var7, var6);
        var5.setSuccess(true);
        var5.setResult(var10);
        return var5;
    }

    @PostMapping({"/add"})
    public Result<ExtActFlowData> a(@RequestBody ExtActFlowData var1) {
        Result var2 = new Result();

        try {
            this.extActFlowDataService.save(var1);
            var2.success("添加成功！");
        } catch (Exception var4) {
            a.error(var4.getMessage(), var4);
            var2.error500("操作失败");
        }

        return var2;
    }

    @PutMapping({"/edit"})
    public Result<ExtActFlowData> b(@RequestBody ExtActFlowData var1) {
        Result var2 = new Result();
        ExtActFlowData var3 = (ExtActFlowData)this.extActFlowDataService.getById(var1.getId());
        if (var3 == null) {
            var2.error500("未找到对应实体");
        } else {
            boolean var4 = this.extActFlowDataService.updateById(var1);
            if (var4) {
                var2.success("修改成功!");
            }
        }

        return var2;
    }

    @DeleteMapping({"/delete"})
    public Result<ExtActFlowData> a(@RequestParam(name = "id",required = true) String var1) {
        Result var2 = new Result();
        ExtActFlowData var3 = (ExtActFlowData)this.extActFlowDataService.getById(var1);
        if (var3 == null) {
            var2.error500("未找到对应实体");
        } else {
            boolean var4 = this.extActFlowDataService.removeById(var1);
            if (var4) {
                var2.success("删除成功!");
            }
        }

        return var2;
    }

    @DeleteMapping({"/deleteBatch"})
    public Result<ExtActFlowData> b(@RequestParam(name = "ids",required = true) String var1) {
        Result var2 = new Result();
        if (var1 != null && !"".equals(var1.trim())) {
            this.extActFlowDataService.removeByIds(Arrays.asList(var1.split(",")));
            var2.success("删除成功!");
        } else {
            var2.error500("参数不识别！");
        }

        return var2;
    }

    @GetMapping({"/queryById"})
    public Result<ExtActFlowData> c(@RequestParam(name = "id",required = true) String var1) {
        Result var2 = new Result();
        ExtActFlowData var3 = (ExtActFlowData)this.extActFlowDataService.getById(var1);
        if (var3 == null) {
            var2.error500("未找到对应实体");
        } else {
            var2.setResult(var3);
            var2.setSuccess(true);
        }

        return var2;
    }

    @GetMapping({"/getProcessInfo"})
    public Result<Map<String, Object>> a(HttpServletRequest var1) {
        Result var2 = new Result();
        HashMap var3 = new HashMap();
        String var4 = oConvertUtils.getString(var1.getParameter("flowCode"));
        String var5 = oConvertUtils.getString(var1.getParameter("dataId"));
        LambdaQueryWrapper<ExtActFlowData> var6 = new LambdaQueryWrapper();
        var6.eq(ExtActFlowData::getRelationCode, var4);
        var6.eq(ExtActFlowData::getFormDataId, var5);
        var6.orderByDesc(ExtActFlowData::getId);
        Optional<ExtActFlowData> var7 = this.extActFlowDataService.list(var6).stream().findFirst();
        if (var7.isPresent()) {
            ExtActFlowData extActFlowData = (ExtActFlowData)var7.get();
            var3.put("processInstanceId", extActFlowData.getProcessInstId());
            var2.setSuccess(true);
        } else {
            var2.error500("数据异常！");
        }

        var2.setResult(var3);
        return var2;
    }

    @GetMapping({"/queryFlowDataByCodeAndId"})
    public Result<ExtActFlowData> a(@RequestParam(name = "flowCode",required = true) String var1, @RequestParam(name = "dataId",required = true) String var2) {
        Result var3 = new Result();
        LambdaQueryWrapper<ExtActFlowData> var4 = new LambdaQueryWrapper();
        var4.eq(ExtActFlowData::getRelationCode, var1);
        var4.eq(ExtActFlowData::getFormDataId, var2);
        var4.orderByDesc(ExtActFlowData::getId);
        Optional<ExtActFlowData> var5 = this.extActFlowDataService.list(var4).stream().findFirst();
        if (!var5.isPresent()) {
            var3.error500("未找到对应实体");
        } else {
            var3.setResult(var5.get());
            var3.setSuccess(true);
        }

        return var3;
    }

    @GetMapping({"/checkNotify"})
    public Result<Boolean> a(@RequestParam(name = "flowCode",required = true) String var1, @RequestParam(name = "dataId",required = true) String var2, HttpServletRequest var3) {
        Result var4 = new Result();
        LambdaQueryWrapper<ExtActFlowData> var5 = new LambdaQueryWrapper();
        var5.eq(ExtActFlowData::getRelationCode, var1);
        var5.eq(ExtActFlowData::getFormDataId, var2);
        var5.orderByDesc(ExtActFlowData::getId);
        Optional<ExtActFlowData> var6 = this.extActFlowDataService.list(var5).stream().findFirst();
        LambdaQueryWrapper<ExtActTaskNotification> var7 = new LambdaQueryWrapper();
        var7.eq(ExtActTaskNotification::getProcInstId, ((ExtActFlowData)var6.get()).getProcessInstId());
        String var8 = JwtUtil.getUserNameByToken(var3);
        var7.eq(ExtActTaskNotification::getTaskAssignee, var8);
        long var9 = this.extActTaskNotificationService.count(var7);
        if (var9 > 0L) {
            var4.setResult(true);
        } else {
            var4.setResult(false);
        }

        return var4;
    }
}
