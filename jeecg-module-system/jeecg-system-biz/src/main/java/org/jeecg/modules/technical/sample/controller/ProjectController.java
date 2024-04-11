package org.jeecg.modules.technical.sample.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.technical.sample.entity.Project;
import org.jeecg.modules.technical.sample.service.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
* 样例项目
*/
@Api(tags="样例项目")
@RestController("TechnicalSampleProjectController")
@RequestMapping("/project/project")
@Slf4j
public class ProjectController extends JeecgController<Project, IProjectService> {
   @Autowired
   private IProjectService projectService;

   /**
    * 分页列表查询
    *
    * @param project
    * @param pageNo
    * @param pageSize
    * @param req
    * @return
    */
   @AutoLog(value = "样例项目-分页列表查询")
   @ApiOperation(value="样例项目-分页列表查询", notes="样例项目-分页列表查询")
   @GetMapping(value = "/list")
   public Result<?> queryPageList(Project project,
                                  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                  HttpServletRequest req) {
       QueryWrapper<Project> queryWrapper = QueryGenerator.initQueryWrapper(project, req.getParameterMap());
       Page<Project> page = new Page<Project>(pageNo, pageSize);
       IPage<Project> pageList = projectService.page(page, queryWrapper);
       return Result.OK(pageList);
   }

   /**
    *   添加
    *
    * @param project
    * @return
    */
   @AutoLog(value = "样例项目-添加")
   @ApiOperation(value="样例项目-添加", notes="样例项目-添加")
   @PostMapping(value = "/add")
   public Result<?> add(@RequestBody Project project) {
       projectService.save(project);
       return Result.OK("添加成功！");
   }

   /**
    *  编辑
    *
    * @param project
    * @return
    */
   @AutoLog(value = "样例项目-编辑")
   @ApiOperation(value="样例项目-编辑", notes="样例项目-编辑")
   @PutMapping(value = "/edit")
   public Result<?> edit(@RequestBody Project project) {
       projectService.updateById(project);
       return Result.OK("编辑成功!");
   }

   /**
    *   通过id删除
    *
    * @param id
    * @return
    */
   @AutoLog(value = "样例项目-通过id删除")
   @ApiOperation(value="样例项目-通过id删除", notes="样例项目-通过id删除")
   @DeleteMapping(value = "/delete")
   public Result<?> delete(@RequestParam(name="id",required=true) String id) {
       projectService.removeById(id);
       return Result.OK("删除成功!");
   }

   /**
    *  批量删除
    *
    * @param ids
    * @return
    */
   @AutoLog(value = "样例项目-批量删除")
   @ApiOperation(value="样例项目-批量删除", notes="样例项目-批量删除")
   @DeleteMapping(value = "/deleteBatch")
   public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
       this.projectService.removeByIds(Arrays.asList(ids.split(",")));
       return Result.OK("批量删除成功!");
   }

   /**
    * 通过id查询
    *
    * @param id
    * @return
    */
   @AutoLog(value = "样例项目-通过id查询")
   @ApiOperation(value="样例项目-通过id查询", notes="样例项目-通过id查询")
   @GetMapping(value = "/queryById")
   public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
       Project project = projectService.getById(id);
       if(project==null) {
           return Result.error("未找到对应数据");
       }
       return Result.OK(project);
   }

   /**
   * 导出excel
   *
   * @param request
   * @param project
   */
   @RequestMapping(value = "/exportXls")
   public ModelAndView exportXls(HttpServletRequest request, Project project) {
       return super.exportXls(request, project, Project.class, "样例项目");
   }

   /**
     * 通过excel导入数据
   *
   * @param request
   * @param response
   * @return
   */
   @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
   public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
       return super.importExcel(request, response, Project.class);
   }

}
