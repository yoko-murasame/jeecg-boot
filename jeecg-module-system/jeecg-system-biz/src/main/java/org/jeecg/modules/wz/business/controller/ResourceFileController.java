package org.jeecg.modules.wz.business.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.design.core.mp.support.Condition;
import org.design.core.mp.support.Query;
import org.design.core.tool.api.R;
import org.jeecg.modules.wz.cityBrain.api.entity.ResourceFile;
import org.jeecg.modules.wz.cityBrain.api.service.IResourceFileService;
import org.jeecg.modules.wz.cityBrain.api.vo.ResourceFileVO;
import org.jeecg.modules.wz.cityBrain.api.wrapper.ResourceFileWrapper;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 资源文件
 *
 * @author Circle
 * @since 2021-09-30
 */
@RestController
@AllArgsConstructor
@RequestMapping("business/resourceFile")
@Api(value = "资源文件", tags = "资源文件接口")
public class ResourceFileController {

    private IResourceFileService resourceFileService;

    /**
     * 详情
     */
    @GetMapping("/detail")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "详情", notes = "传入resourceFile")
    public R<ResourceFileVO> detail(ResourceFile resourceFile) {
        ResourceFile detail = resourceFileService.getOne(Condition.getQueryWrapper(resourceFile));
        return R.data(ResourceFileWrapper.build().entityVO(detail));
    }

    /**
     * 分页 资源文件
     */
    @GetMapping("/list")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "分页", notes = "传入resourceFile")
    public R<IPage<ResourceFileVO>> list(ResourceFile resourceFile, Query query) {
        IPage<ResourceFile> pages = resourceFileService.page(Condition.getPage(query), Condition.getQueryWrapper(resourceFile));
        return R.data(ResourceFileWrapper.build().pageVO(pages));
    }


    /**
     * 新增 资源文件
     */
    @PostMapping("/save")
    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "新增", notes = "传入resourceFile")
    public R save(@Valid @RequestBody ResourceFile resourceFile) {
        return R.status(resourceFileService.save(resourceFile));
    }

    /**
     * 修改 资源文件
     */
    @PostMapping("/update")
    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "修改", notes = "传入resourceFile")
    public R update(@Valid @RequestBody ResourceFile resourceFile) {
        return R.status(resourceFileService.updateById(resourceFile));
    }

    /**
     * 新增或修改 资源文件
     */
    @PostMapping("/submit")
    @ApiOperationSupport(order = 6)
    @ApiOperation(value = "新增或修改", notes = "传入resourceFile")
    public R submit(@Valid @RequestBody ResourceFile resourceFile) {
        return R.status(resourceFileService.saveOrUpdate(resourceFile));
    }


    /**
     * 删除 资源文件
     */
    @PostMapping("/remove")
    @ApiOperationSupport(order = 7)
    @ApiOperation(value = "逻辑删除", notes = "传入ids")
    public R remove(@ApiIgnore @RequestBody Map<String, Object> resourceFile) {
        List<String> ids = (List<String>) resourceFile.get("ids");
        List<Long> is = ids.stream().map(s -> Long.valueOf(s)).collect(Collectors.toList());
        return R.status(resourceFileService.deleteLogic(is));
    }

    /**
     * 根据 layerId查询详情
     */
    @GetMapping("/getByLayerId")
    @ApiOperationSupport(order = 8)
    @ApiOperation(value = "根据 layerId 查询详情", notes = "传入layerId")
    public R<List<ResourceFile>> getByLayerId(ResourceFile resourceFile) {
        List<ResourceFile> detail = resourceFileService.getByLayerId(resourceFile);
        return R.data(detail);
    }

    /**
     * 批量修改
     */
    @PostMapping("/updateList")
    @ApiOperationSupport(order = 9)
    @ApiOperation(value = "修改", notes = "传入fieldLayer List")
    public R updateList(@Valid @RequestBody List<ResourceFile> resourceFiles) {
        return R.status(resourceFileService.saveOrUpdateBatch(resourceFiles));
    }

}

