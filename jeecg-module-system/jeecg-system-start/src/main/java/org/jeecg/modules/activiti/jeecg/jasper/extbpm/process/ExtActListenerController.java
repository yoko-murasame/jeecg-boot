package org.jeecg.modules.activiti.jeecg.jasper.extbpm.process;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.extbpm.process.entity.ExtActListener;
import org.jeecg.modules.extbpm.process.service.IExtActListenerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@RestController("extActListenerController")
@RequestMapping({"/act/process/extActListener"})
public class ExtActListenerController {
    private static final Logger a = LoggerFactory.getLogger(ExtActListenerController.class);
    @Autowired
    private IExtActListenerService extActListenerService;

    public ExtActListenerController() {
    }

    @GetMapping({"/list"})
    public Result<IPage<ExtActListener>> a(ExtActListener var1, @RequestParam(name = "pageNo",defaultValue = "1") Integer var2, @RequestParam(name = "pageSize",defaultValue = "10") Integer var3, HttpServletRequest var4) {
        Result var5 = new Result();
        QueryWrapper var6 = new QueryWrapper(var1);
        Page var7 = new Page((long)var2, (long)var3);
        var6.eq("del_flag", "0");
        String var8 = var4.getParameter("column");
        String var9 = var4.getParameter("order");
        if (oConvertUtils.isNotEmpty(var8) && oConvertUtils.isNotEmpty(var9)) {
            if ("asc".equals(var9)) {
                var6.orderByAsc(oConvertUtils.camelToUnderline(var8));
            } else {
                var6.orderByDesc(oConvertUtils.camelToUnderline(var8));
            }
        }

        IPage var10 = this.extActListenerService.page(var7, var6);
        var5.setSuccess(true);
        var5.setResult(var10);
        return var5;
    }

    @PostMapping({"/add"})
    public Result<ExtActListener> a(@RequestBody ExtActListener var1) {
        Result var2 = new Result();

        try {
            var1.setListenerStatus(0);
            var1.setDelFlag("0");
            this.extActListenerService.save(var1);
            var2.success("添加成功！");
        } catch (Exception var4) {
            a.error(var4.getMessage(), var4);
            var2.error500("操作失败");
        }

        return var2;
    }

    @PutMapping({"/edit"})
    public Result<ExtActListener> b(@RequestBody ExtActListener var1) {
        Result var2 = new Result();
        ExtActListener var3 = (ExtActListener)this.extActListenerService.getById(var1.getId());
        if (var3 == null) {
            var2.error500("未找到对应实体");
        } else {
            boolean var4 = this.extActListenerService.updateById(var1);
            if (var4) {
                var2.success("修改成功!");
            }
        }

        return var2;
    }

    @DeleteMapping({"/delete"})
    public Result<ExtActListener> a(@RequestParam(name = "id",required = true) String var1) {
        Result var2 = new Result();
        ExtActListener var3 = (ExtActListener)this.extActListenerService.getById(var1);
        if (var3 == null) {
            var2.error500("未找到对应实体");
        } else {
            var3.setDelFlag("1");
            boolean var4 = this.extActListenerService.updateById(var3);
            if (var4) {
                var2.success("删除成功!");
            }
        }

        return var2;
    }

    @DeleteMapping({"/deleteBatch"})
    public Result<ExtActListener> b(@RequestParam(name = "ids",required = true) String var1) {
        Result var2 = new Result();
        if (var1 != null && !"".equals(var1.trim())) {
            var2.success("删除成功!");
        } else {
            var2.error500("参数不识别！");
        }

        return var2;
    }

    @GetMapping({"/queryById"})
    public Result<ExtActListener> c(@RequestParam(name = "id",required = true) String var1) {
        Result var2 = new Result();
        ExtActListener var3 = (ExtActListener)this.extActListenerService.getById(var1);
        if (var3 == null) {
            var2.error500("未找到对应实体");
        } else {
            var2.setResult(var3);
            var2.setSuccess(true);
        }

        return var2;
    }

    @PutMapping({"/changeStatus"})
    public Result<ExtActListener> a(@RequestBody HashMap<String, String> var1) {
        String var2 = (String)var1.get("id");
        Result var3 = new Result();
        ExtActListener var4 = (ExtActListener)this.extActListenerService.getById(var2);
        if (var4 == null) {
            var3.error500("未找到对应实体");
        } else {
            if (var4.getListenerStatus() == 0) {
                var4.setListenerStatus(1);
            } else if (var4.getListenerStatus() == 1) {
                var4.setListenerStatus(0);
            }

            this.extActListenerService.updateById(var4);
            var3.success("操作成功!");
        }

        return var3;
    }
}
