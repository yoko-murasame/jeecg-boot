 package org.jeecg.modules.online.cgform.b;
 
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
 public class e
 {
/*  37 */   private static final Logger a = LoggerFactory.getLogger(e.class);
 
   @Autowired
   private IOnlCgformIndexService onlCgformIndexService;
 
   @GetMapping({"/listByHeadId"})
   public Result<?> a(@RequestParam("headId") String paramString)
   {
/*  50 */     QueryWrapper localQueryWrapper = new QueryWrapper();
/*  51 */     localQueryWrapper.eq("cgform_head_id", paramString);
/*  52 */     localQueryWrapper.eq("del_flag", CommonConstant.DEL_FLAG_0);
/*  53 */     localQueryWrapper.orderByDesc("create_time");
/*  54 */     List localList = this.onlCgformIndexService.list(localQueryWrapper);
/*  55 */     return Result.ok(localList);
   }
 
   @GetMapping({"/list"})
   public Result<IPage<OnlCgformIndex>> a(OnlCgformIndex paramOnlCgformIndex, @RequestParam(name="pageNo", defaultValue="1") Integer paramInteger1, @RequestParam(name="pageSize", defaultValue="10") Integer paramInteger2, HttpServletRequest paramHttpServletRequest)
   {
/*  72 */     Result localResult = new Result();
/*  73 */     QueryWrapper localQueryWrapper = QueryGenerator.initQueryWrapper(paramOnlCgformIndex, paramHttpServletRequest.getParameterMap());
/*  74 */     Page localPage = new Page(paramInteger1.intValue(), paramInteger2.intValue());
/*  75 */     IPage localIPage = this.onlCgformIndexService.page(localPage, localQueryWrapper);
 
/*  77 */     localResult.setSuccess(true);
/*  78 */     localResult.setResult(localIPage);
/*  79 */     return localResult;
   }
 
   @PostMapping({"/add"})
   public Result<OnlCgformIndex> a(@RequestBody OnlCgformIndex paramOnlCgformIndex)
   {
/*  90 */     Result localResult = new Result();
     try {
/*  92 */       this.onlCgformIndexService.save(paramOnlCgformIndex);
/*  93 */       localResult.success("添加成功！");
     } catch (Exception localException) {
/*  95 */       a.error(localException.getMessage(), localException);
/*  96 */       localResult.error500("操作失败");
     }
/*  98 */     return localResult;
   }
 
   @PutMapping({"/edit"})
   public Result<OnlCgformIndex> b(@RequestBody OnlCgformIndex paramOnlCgformIndex)
   {
/* 109 */     Result localResult = new Result();
/* 110 */     OnlCgformIndex localOnlCgformIndex = (OnlCgformIndex)this.onlCgformIndexService.getById(paramOnlCgformIndex.getId());
/* 111 */     if (localOnlCgformIndex == null) {
/* 112 */       localResult.error500("未找到对应实体");
     } else {
/* 114 */       boolean bool = this.onlCgformIndexService.updateById(paramOnlCgformIndex);
 
/* 116 */       if (bool) {
/* 117 */         localResult.success("修改成功!");
       }
     }
 
/* 121 */     return localResult;
   }
 
   @DeleteMapping({"/delete"})
   public Result<OnlCgformIndex> b(@RequestParam(name="id", required=true) String paramString)
   {
/* 132 */     Result localResult = new Result();
/* 133 */     OnlCgformIndex localOnlCgformIndex = (OnlCgformIndex)this.onlCgformIndexService.getById(paramString);
/* 134 */     if (localOnlCgformIndex == null) {
/* 135 */       localResult.error500("未找到对应实体");
     } else {
/* 137 */       boolean bool = this.onlCgformIndexService.removeById(paramString);
/* 138 */       if (bool) {
/* 139 */         localResult.success("删除成功!");
       }
     }
 
/* 143 */     return localResult;
   }
 
   @DeleteMapping({"/deleteBatch"})
   public Result<OnlCgformIndex> c(@RequestParam(name="ids", required=true) String paramString)
   {
/* 154 */     Result localResult = new Result();
/* 155 */     if ((paramString == null) || ("".equals(paramString.trim()))) {
/* 156 */       localResult.error500("参数不识别！");
     } else {
/* 158 */       this.onlCgformIndexService.removeByIds(Arrays.asList(paramString.split(",")));
/* 159 */       localResult.success("删除成功!");
     }
/* 161 */     return localResult;
   }
 
   @GetMapping({"/queryById"})
   public Result<OnlCgformIndex> d(@RequestParam(name="id", required=true) String paramString)
   {
/* 172 */     Result localResult = new Result();
/* 173 */     OnlCgformIndex localOnlCgformIndex = (OnlCgformIndex)this.onlCgformIndexService.getById(paramString);
/* 174 */     if (localOnlCgformIndex == null) {
/* 175 */       localResult.error500("未找到对应实体");
     } else {
/* 177 */       localResult.setResult(localOnlCgformIndex);
/* 178 */       localResult.setSuccess(true);
     }
/* 180 */     return localResult;
   }
 }

/* Location:           C:\Users\tx\Desktop\hibernate-common-ol-5.4.74(2).jar
 * Qualified Name:     org.jeecg.modules.online.cgform.b.e
 * JD-Core Version:    0.6.2
 */