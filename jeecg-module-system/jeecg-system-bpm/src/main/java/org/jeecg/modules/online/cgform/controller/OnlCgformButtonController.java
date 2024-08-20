package org.jeecg.modules.online.cgform.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.online.cgform.entity.OnlCgformButton;
import org.jeecg.modules.online.cgform.service.IOnlCgformButtonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/* compiled from: OnlCgformButtonController.java */
@RequestMapping({"/online/cgform/button"})
@RestController("onlCgformButtonController")
/* loaded from: hibernate-common-ol-5.4.74(2).jar:org/jeecg/modules/online/cgform/b/b.class */
public class OnlCgformButtonController {
    private static final Logger a = LoggerFactory.getLogger(OnlCgformButtonController.class);
    @Autowired
    private IOnlCgformButtonService onlCgformButtonService;

    @GetMapping({"/list/{code}"})
    public Result<IPage<OnlCgformButton>> a(OnlCgformButton onlCgformButton, @RequestParam(name = "pageNo", defaultValue = "1") Integer num, @RequestParam(name = "pageSize", defaultValue = "10") Integer num2, HttpServletRequest httpServletRequest, @PathVariable("code") String str) {
        Result<IPage<OnlCgformButton>> result = new Result<>();
        onlCgformButton.setCgformHeadId(str);
        Wrapper initQueryWrapper = QueryGenerator.initQueryWrapper(onlCgformButton, httpServletRequest.getParameterMap());
        IPage page = this.onlCgformButtonService.page(new Page(num.intValue(), num2.intValue()), initQueryWrapper);
        result.setSuccess(true);
        result.setResult(page);
        return result;
    }

    @PostMapping({"/add"})
    public Result<OnlCgformButton> a(@RequestBody OnlCgformButton onlCgformButton) {
        Result<OnlCgformButton> result = new Result<>();
        try {
            this.onlCgformButtonService.save(onlCgformButton);
            result.success("添加成功！");
        } catch (Exception e) {
            a.error(e.getMessage(), e);
            result.error500("操作失败");
        }
        return result;
    }

    @PostMapping({"/aitest"})
    public Result<OnlCgformButton> a(@RequestBody JSONArray jSONArray) {
        Result<OnlCgformButton> result = new Result<>();
        for (int i = 0; i < jSONArray.size(); i++) {
            try {
                this.onlCgformButtonService.saveButton((OnlCgformButton) JSONObject.parseObject(jSONArray.getJSONObject(i).toJSONString(), OnlCgformButton.class));
            } catch (Exception e) {
                a.error(e.getMessage(), e);
                result.error500("操作失败");
            }
        }
        result.success("添加成功！");
        return result;
    }

    @PutMapping({"/edit"})
    public Result<OnlCgformButton> b(@RequestBody OnlCgformButton onlCgformButton) {
        Result<OnlCgformButton> result = new Result<>();
        if (((OnlCgformButton) this.onlCgformButtonService.getById(onlCgformButton.getId())) == null) {
            result.error500("未找到对应实体");
        } else if (this.onlCgformButtonService.updateById(onlCgformButton)) {
            result.success("修改成功!");
        }
        return result;
    }

    @DeleteMapping({"/delete"})
    public Result<OnlCgformButton> a(@RequestParam(name = "id", required = true) String str) {
        Result<OnlCgformButton> result = new Result<>();
        if (((OnlCgformButton) this.onlCgformButtonService.getById(str)) == null) {
            result.error500("未找到对应实体");
        } else if (this.onlCgformButtonService.removeById(str)) {
            result.success("删除成功!");
        }
        return result;
    }

    @DeleteMapping({"/deleteBatch"})
    public Result<OnlCgformButton> b(@RequestParam(name = "ids", required = true) String str) {
        Result<OnlCgformButton> result = new Result<>();
        if (str == null || "".equals(str.trim())) {
            result.error500("参数不识别！");
        } else {
            this.onlCgformButtonService.removeByIds(Arrays.asList(str.split(org.jeecg.modules.online.cgform.d.b.DOT_STRING)));
            result.success("删除成功!");
        }
        return result;
    }

    @GetMapping({"/queryById"})
    public Result<OnlCgformButton> c(@RequestParam(name = "id", required = true) String str) {
        Result<OnlCgformButton> result = new Result<>();
        OnlCgformButton onlCgformButton = (OnlCgformButton) this.onlCgformButtonService.getById(str);
        if (onlCgformButton == null) {
            result.error500("未找到对应实体");
        } else {
            result.setResult(onlCgformButton);
            result.setSuccess(true);
        }
        return result;
    }
}
