package org.jeecg.modules.sakuga.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.core.enums.SqlKeyword;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.sakuga.entity.SakugaContent;
import org.jeecg.modules.sakuga.mapper.SakugaContentMapper;
import org.jeecg.modules.sakuga.service.ISakugaContentService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

/**
 * @Description: 作画内容管理
 * @Author: jeecg-boot
 * @Date: 2023-05-06
 * @Version: V1.0
 */
@Api(tags = "作画内容管理")
@RestController
@RequestMapping("/sakuga/sakugaContent")
@Slf4j
public class SakugaContentController extends JeecgController<SakugaContent, ISakugaContentService> {
    @Autowired
    private ISakugaContentService sakugaContentService;

    @Resource
    private SakugaContentMapper sakugaContentMapper;

    /**
     * 分页列表查询
     *
     * @param sakugaContent
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "作画内容管理-分页列表查询")
    @ApiOperation(value = "作画内容管理-分页列表查询", notes = "作画内容管理-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<SakugaContent>> queryPageList(SakugaContent sakugaContent,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
        return Result.OK(sakugaContentService.pageHighlight(sakugaContent, pageNo, pageSize, req));
    }

    /**
     * 添加
     *
     * @param sakugaContent
     * @return
     */
    @AutoLog(value = "作画内容管理-添加")
    @ApiOperation(value = "作画内容管理-添加", notes = "作画内容管理-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody SakugaContent sakugaContent) {
        sakugaContentService.save(sakugaContent);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param sakugaContent
     * @return
     */
    @AutoLog(value = "作画内容管理-编辑")
    @ApiOperation(value = "作画内容管理-编辑", notes = "作画内容管理-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody SakugaContent sakugaContent) {
        sakugaContentService.updateById(sakugaContent);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "作画内容管理-通过id删除")
    @ApiOperation(value = "作画内容管理-通过id删除", notes = "作画内容管理-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        sakugaContentService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "作画内容管理-批量删除")
    @ApiOperation(value = "作画内容管理-批量删除", notes = "作画内容管理-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.sakugaContentService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "作画内容管理-通过id查询")
    @ApiOperation(value = "作画内容管理-通过id查询", notes = "作画内容管理-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        SakugaContent sakugaContent = sakugaContentService.getById(id);
        if (sakugaContent == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(sakugaContent);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param sakugaContent
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, SakugaContent sakugaContent) {
        return super.exportXls(request, sakugaContent, SakugaContent.class, "作画内容管理");
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
        return super.importExcel(request, response, SakugaContent.class);
    }

}
