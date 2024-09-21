package org.jeecg.modules.ztb.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.ztb.entity.ZtbXedj;
import org.jeecg.modules.ztb.service.IZtbXedjService;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Description: 招投标DEMO
 */
@Api(tags = "招投标DEMO")
@RestController
@RequestMapping("/ztb/ztbXedj")
@Slf4j
public class ZtbXedjController extends JeecgController<ZtbXedj, IZtbXedjService> {
    @Autowired
    private IZtbXedjService ztbXedjService;

    /**
     * 分页列表查询
     *
     * @param ztbXedj
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "招投标DEMO-分页列表查询")
    @ApiOperation(value = "招投标DEMO-分页列表查询", notes = "招投标DEMO-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(ZtbXedj ztbXedj,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
        QueryWrapper<ZtbXedj> queryWrapper = QueryGenerator.initQueryWrapper(ztbXedj, req.getParameterMap());
        queryWrapper.eq(StringUtils.hasText(ztbXedj.getOverdue()), "overdue", ztbXedj.getOverdue());
        Page<ZtbXedj> page = new Page<ZtbXedj>(pageNo, pageSize);
        IPage<ZtbXedj> pageList = ztbXedjService.pageCustom(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param ztbXedj
     * @return
     */
    @AutoLog(value = "招投标DEMO-添加")
    @ApiOperation(value = "招投标DEMO-添加", notes = "招投标DEMO-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody ZtbXedj ztbXedj) {
        ztbXedjService.save(ztbXedj);
        return Result.OK("添加成功！", ztbXedj);
    }

    /**
     * 编辑
     *
     * @param ztbXedj
     * @return
     */
    @AutoLog(value = "招投标DEMO-编辑")
    @ApiOperation(value = "招投标DEMO-编辑", notes = "招投标DEMO-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody ZtbXedj ztbXedj) {
        ztbXedjService.updateById(ztbXedj);
        return Result.OK("编辑成功!", this.ztbXedjService.getByIdCustom(ztbXedj.getId()));
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "招投标DEMO-通过id删除")
    @ApiOperation(value = "招投标DEMO-通过id删除", notes = "招投标DEMO-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        ztbXedjService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "招投标DEMO-批量删除")
    @ApiOperation(value = "招投标DEMO-批量删除", notes = "招投标DEMO-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.ztbXedjService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "招投标DEMO-通过id查询")
    @ApiOperation(value = "招投标DEMO-通过id查询", notes = "招投标DEMO-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        ZtbXedj ztbXedj = ztbXedjService.getByIdCustom(id);
        if (ztbXedj == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(ztbXedj);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param ztbXedj
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ZtbXedj ztbXedj) {
        // Step.1 组装查询条件
        QueryWrapper<ZtbXedj> queryWrapper = QueryGenerator.initQueryWrapper(ztbXedj, request.getParameterMap());

        // 过滤选中数据
        String selections = Optional.ofNullable(request.getParameter("selections")).orElse("");
        queryWrapper.in(oConvertUtils.isNotEmpty(selections), "id", Arrays.asList(selections.split(",")));

        // Step.2 获取导出数据
        List<ZtbXedj> exportList = ztbXedjService.listCustom(queryWrapper);

        // Step.3 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        mv.addObject(NormalExcelConstants.FILE_NAME, "招投标DEMO"); // 此处设置的filename无效 ,前端会重更新设置一下
        mv.addObject(NormalExcelConstants.CLASS, ZtbXedj.class);
        ExportParams exportParams = new ExportParams();
        exportParams.setSheetName("招投标DEMO");
        mv.addObject(NormalExcelConstants.PARAMS, exportParams);
        mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
        return mv;
    }

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private TransactionDefinition transactionDefinition;
    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            MultipartFile file = entity.getValue();// 获取上传文件对象
            ImportParams params = new ImportParams();
            // params.setTitleRows(2);
            params.setHeadRows(1);
            params.setNeedSave(true);
            // 手动事务
            TransactionStatus status = transactionManager.getTransaction(transactionDefinition);
            try {
                List<ZtbXedj> list = ExcelImportUtil.importExcel(file.getInputStream(), ZtbXedj.class, params);
                this.ztbXedjService.remove(Wrappers.lambdaQuery(ZtbXedj.class).in(ZtbXedj::getGcxmmc, list.stream().map(ZtbXedj::getGcxmmc).collect(Collectors.toList())));
                this.ztbXedjService.saveBatch(list);
                long start = System.currentTimeMillis();
                log.info("消耗时间" + (System.currentTimeMillis() - start) + "毫秒");
                transactionManager.commit(status);
                return Result.OK("文件导入成功！数据行数：" + list.size());
            } catch (Exception e) {
                transactionManager.rollback(status);
                log.error(e.getMessage(), e);
                return Result.error("文件导入失败:" + e.getMessage());
            } finally {
                try {
                    file.getInputStream().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return Result.error("文件导入失败！");
    }

}
