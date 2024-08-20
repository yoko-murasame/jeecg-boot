package org.jeecg.modules.online.cgreport.a;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.online.cgreport.entity.OnlCgreportParam;
import org.jeecg.modules.online.cgreport.service.IOnlCgreportParamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

/* compiled from: OnlCgreportParamController.java */
@RequestMapping({"/online/cgreport/param"})
@RestController("onlCgreportParamController")
/* loaded from: hibernate-common-ol-5.4.74(2).jar:org/jeecg/modules/online/cgreport/a/d.class */
public class d {
    private static final Logger a = LoggerFactory.getLogger(d.class);
    @Autowired
    private IOnlCgreportParamService onlCgreportParamService;

    @GetMapping({"/listByHeadId"})
    public Result<?> a(@RequestParam("headId") String str) {
        LambdaQueryWrapper queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq("cgrhead_id", str);
        queryWrapper.orderByAsc("order_num");
        List list = this.onlCgreportParamService.list(queryWrapper);
        Result<List> result = new Result<>();
        result.setSuccess(true);
        result.setResult(list);
        return result;
    }

    @GetMapping({"/list"})
    public Result<IPage<OnlCgreportParam>> a(OnlCgreportParam onlCgreportParam, @RequestParam(name = "pageNo", defaultValue = "1") Integer num, @RequestParam(name = "pageSize", defaultValue = "10") Integer num2, HttpServletRequest httpServletRequest) {
        Result<IPage<OnlCgreportParam>> result = new Result<>();
        Wrapper initQueryWrapper = QueryGenerator.initQueryWrapper(onlCgreportParam, httpServletRequest.getParameterMap());
        IPage page = this.onlCgreportParamService.page(new Page(num.intValue(), num2.intValue()), initQueryWrapper);
        result.setSuccess(true);
        result.setResult(page);
        return result;
    }

    @PostMapping({"/add"})
    public Result<?> a(@RequestBody OnlCgreportParam onlCgreportParam) {
        this.onlCgreportParamService.save(onlCgreportParam);
        return Result.ok("添加成功!");
    }

    @PutMapping({"/edit"})
    public Result<?> b(@RequestBody OnlCgreportParam onlCgreportParam) {
        this.onlCgreportParamService.updateById(onlCgreportParam);
        return Result.ok("编辑成功!");
    }

    @DeleteMapping({"/delete"})
    public Result<?> b(@RequestParam(name = "id", required = true) String str) {
        this.onlCgreportParamService.removeById(str);
        return Result.ok("删除成功!");
    }

    @DeleteMapping({"/deleteBatch"})
    public Result<?> c(@RequestParam(name = "ids", required = true) String str) {
        this.onlCgreportParamService.removeByIds(Arrays.asList(str.split(org.jeecg.modules.online.cgform.d.b.DOT_STRING)));
        return Result.ok("批量删除成功!");
    }

    @GetMapping({"/queryById"})
    public Result<OnlCgreportParam> d(@RequestParam(name = "id", required = true) String str) {
        Result<OnlCgreportParam> result = new Result<>();
        result.setResult((OnlCgreportParam) this.onlCgreportParamService.getById(str));
        return result;
    }
}
