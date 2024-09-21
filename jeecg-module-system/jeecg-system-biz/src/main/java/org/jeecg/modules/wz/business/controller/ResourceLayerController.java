
package org.jeecg.modules.wz.business.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.design.core.mp.support.Condition;
import org.design.core.mp.support.Query;

import org.design.core.tool.api.R;
import org.design.core.tool.utils.Func;
import org.jeecg.modules.wz.business.entity.ResourceLayer;
import org.jeecg.modules.wz.business.service.IResourceLayerService;
import org.jeecg.modules.wz.business.vo.ResourceLayerVO;
import org.jeecg.modules.wz.business.wrapper.ResourceLayerWrapper;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 城市大脑-资源图层 控制器
 *
 * @author Blade
 * @since 2021-06-28
 */
@RestController
@AllArgsConstructor
@RequestMapping("business/resourceLayer")
@Api(value = "城市大脑-资源图层", tags = "城市大脑-资源图层接口")
public class ResourceLayerController {

    private IResourceLayerService resourceLayerService;

    /**
     * 详情
     */
    @GetMapping("/detail")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "详情", notes = "传入resourceLayer")
    public R<ResourceLayerVO> detail(ResourceLayer resourceLayer) {
        ResourceLayer detail = resourceLayerService.getOne(Condition.getQueryWrapper(resourceLayer));
        return R.data(ResourceLayerWrapper.build().entityVO(detail));
    }

    /**
     * 分页 城市大脑-资源图层
     */
    @GetMapping("/list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "菜单编号", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "name", value = "菜单名称", paramType = "query", dataType = "string")
    })
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "列表", notes = "传入resourceLayer")
    public R<List<ResourceLayerVO>> list(@ApiIgnore @RequestParam Map<String, Object> resourceLayer) {
        List<ResourceLayer> list = resourceLayerService.list(Condition.getQueryWrapper(resourceLayer, ResourceLayer.class).lambda().orderByAsc(ResourceLayer::getSort));
        return R.data(ResourceLayerWrapper.build().listNodeVO(list));
    }


    /**
     * 自定义分页 城市大脑-资源图层
     */
    @GetMapping("/page")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "分页", notes = "传入resourceLayer")
    public R<IPage<ResourceLayerVO>> page(ResourceLayerVO resourceLayer, Query query) {
        IPage<ResourceLayerVO> pages = resourceLayerService.selectResourceLayerPage(Condition.getPage(query), resourceLayer);
        return R.data(pages);
    }

    /**
     * 新增 城市大脑-资源图层
     */
    @PostMapping("/save")
    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "新增", notes = "传入resourceLayer")
    public R save(@Valid @RequestBody ResourceLayer resourceLayer) {
        return R.status(resourceLayerService.save(resourceLayer));
    }

    /**
     * 修改 城市大脑-资源图层
     */
    @PostMapping("/update")
    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "修改", notes = "传入resourceLayer")
    public R update(@Valid @RequestBody ResourceLayer resourceLayer) {
        return R.status(resourceLayerService.updateById(resourceLayer));
    }

    /**
     * 新增或修改 城市大脑-资源图层
     */
    @PostMapping("/submit")
    @ApiOperationSupport(order = 6)
    @ApiOperation(value = "新增或修改", notes = "传入resourceLayer")
    public R submit(@Valid @RequestBody ResourceLayer resourceLayer) {
        return R.status(resourceLayerService.saveOrUpdate(resourceLayer));
    }


    /**
     * 删除 城市大脑-资源图层
     */
    @PostMapping("/remove")
    @ApiOperationSupport(order = 7)
    @ApiOperation(value = "逻辑删除", notes = "传入ids")
    public R remove(@ApiIgnore @RequestBody Map<String, Object> resourceLayer) {
        String ids = Func.toStr(resourceLayer.get("ids"));
        return R.status(resourceLayerService.deleteLogicRecursion(Func.toLongList(ids)));
    }

    /**
     * 树结构
     */
    @GetMapping("/tree")
    @ApiOperationSupport(order = 8)
    @ApiOperation(value = "树形结构", notes = "树形结构")
    public R<List<ResourceLayerVO>> tree() {
        List<ResourceLayerVO> tree = resourceLayerService.tree();
        return R.data(tree);
    }

    /**
     * 获取权限分配树形结构
     */
    @GetMapping("/grant-tree")
    @ApiOperationSupport(order = 9)
    @ApiOperation(value = "权限分配树型结构", notes = "权限分配树形结构")
    public R<List<ResourceLayerVO>> grantTree(@RequestParam(required = false) String code) {
        return R.data(resourceLayerService.grantTree("0",code ));
    }
    /**
     * 获取权限分配树形结构
     */
    @GetMapping("role-tree-keys")
    @ApiOperationSupport(order = 10)
    @ApiOperation(value = "角色所分配的树", notes = "角色所分配的树")
    public R<List<String>> roleTreeKeys(String roleId) {
        return R.data(resourceLayerService.roleTreeKeys(roleId));
    }

    @PostMapping("/copy")
    @ApiOperationSupport(order = 11)
    @ApiOperation(value = "递归复制", notes = "递归复制")
    public R copy(@ApiIgnore @RequestBody Map<String, Object> resourceLayer) {
        String ids = Func.toStr(resourceLayer.get("ids"));
        return R.status(resourceLayerService.copy(Func.toLongList(ids)));
    }

}
