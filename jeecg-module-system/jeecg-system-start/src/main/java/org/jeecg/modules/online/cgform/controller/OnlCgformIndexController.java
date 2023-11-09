package org.jeecg.modules.online.cgform.controller;
 
 import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
 import com.baomidou.mybatisplus.core.metadata.IPage;
 import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
 import java.util.Arrays;
 import java.util.List;
 import javax.servlet.http.HttpServletRequest;
 import org.jeecg.common.api.vo.Result;
 import org.jeecg.common.constant.CommonConstant;
 import org.jeecg.common.system.query.QueryGenerator;
 import org.jeecg.modules.online.cgform.entity.OnlCgformIndex;
 import org.jeecg.modules.online.cgform.service.IOnlCgformIndexService;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.web.bind.annotation.DeleteMapping;
 import org.springframework.web.bind.annotation.GetMapping;
 import org.springframework.web.bind.annotation.PostMapping;
 import org.springframework.web.bind.annotation.PutMapping;
 import org.springframework.web.bind.annotation.RequestBody;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.RequestParam;
 import org.springframework.web.bind.annotation.RestController;
 
 @RestController("onlCgformIndexController")
 @RequestMapping({"/online/cgform/index"})
 public class OnlCgformIndexController
 {
   private static final Logger a = LoggerFactory.getLogger(org.jeecg.modules.online.cgform.controller.OnlCgformIndexController.class);
 
   @Autowired
   private IOnlCgformIndexService onlCgformIndexService;
 
   @GetMapping({"/listByHeadId"})
   public Result<?> a(@RequestParam("headId") String paramString)
   {
     QueryWrapper localQueryWrapper = new QueryWrapper();
     localQueryWrapper.eq("cgform_head_id", paramString);
     localQueryWrapper.eq("del_flag", CommonConstant.DEL_FLAG_0);
     localQueryWrapper.orderByDesc("create_time");
     List localList = this.onlCgformIndexService.list(localQueryWrapper);
     return Result.ok(localList);
   }
 
   @GetMapping({"/list"})
   public Result<IPage<OnlCgformIndex>> a(OnlCgformIndex paramOnlCgformIndex, @RequestParam(name="pageNo", defaultValue="1") Integer paramInteger1, @RequestParam(name="pageSize", defaultValue="10") Integer paramInteger2, HttpServletRequest paramHttpServletRequest)
   {
     Result localResult = new Result();
     QueryWrapper localQueryWrapper = QueryGenerator.initQueryWrapper(paramOnlCgformIndex, paramHttpServletRequest.getParameterMap());
     Page localPage = new Page(paramInteger1.intValue(), paramInteger2.intValue());
     IPage localIPage = this.onlCgformIndexService.page(localPage, localQueryWrapper);
 
     localResult.setSuccess(true);
     localResult.setResult(localIPage);
     return localResult;
   }
 
   @PostMapping({"/add"})
   public Result<OnlCgformIndex> a(@RequestBody OnlCgformIndex paramOnlCgformIndex)
   {
     Result localResult = new Result();
     try {
       this.onlCgformIndexService.save(paramOnlCgformIndex);
       localResult.success("添加成功！");
     } catch (Exception localException) {
       a.error(localException.getMessage(), localException);
       localResult.error500("操作失败");
     }
     return localResult;
   }
 
   @PutMapping({"/edit"})
   public Result<OnlCgformIndex> b(@RequestBody OnlCgformIndex paramOnlCgformIndex)
   {
     Result localResult = new Result();
     OnlCgformIndex localOnlCgformIndex = (OnlCgformIndex)this.onlCgformIndexService.getById(paramOnlCgformIndex.getId());
     if (localOnlCgformIndex == null) {
       localResult.error500("未找到对应实体");
     } else {
       boolean bool = this.onlCgformIndexService.updateById(paramOnlCgformIndex);
 
       if (bool) {
         localResult.success("修改成功!");
       }
     }
 
     return localResult;
   }
 
   @DeleteMapping({"/delete"})
   public Result<OnlCgformIndex> b(@RequestParam(name="id", required=true) String paramString)
   {
     Result localResult = new Result();
     OnlCgformIndex localOnlCgformIndex = (OnlCgformIndex)this.onlCgformIndexService.getById(paramString);
     if (localOnlCgformIndex == null) {
       localResult.error500("未找到对应实体");
     } else {
       boolean bool = this.onlCgformIndexService.removeById(paramString);
       if (bool) {
         localResult.success("删除成功!");
       }
     }
 
    return localResult;
   }
 
   @DeleteMapping({"/deleteBatch"})
   public Result<OnlCgformIndex> c(@RequestParam(name="ids", required=true) String paramString)
   {
     Result localResult = new Result();
     if ((paramString == null) || ("".equals(paramString.trim()))) {
      localResult.error500("参数不识别！");
     } else {
       this.onlCgformIndexService.removeByIds(Arrays.asList(paramString.split(",")));
       localResult.success("删除成功!");
     }
     return localResult;
   }
 
   @GetMapping({"/queryById"})
   public Result<OnlCgformIndex> d(@RequestParam(name="id", required=true) String paramString)
   {
     Result localResult = new Result();
     OnlCgformIndex localOnlCgformIndex = (OnlCgformIndex)this.onlCgformIndexService.getById(paramString);
     if (localOnlCgformIndex == null) {
      localResult.error500("未找到对应实体");
     } else {
       localResult.setResult(localOnlCgformIndex);
       localResult.setSuccess(true);
     }
     return localResult;
   }
 }
