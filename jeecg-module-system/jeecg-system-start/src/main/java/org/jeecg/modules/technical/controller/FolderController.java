package org.jeecg.modules.technical.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.technical.entity.Folder;
import org.jeecg.modules.technical.entity.enums.Enabled;
import org.jeecg.modules.technical.entity.enums.Level;
import org.jeecg.modules.technical.entity.enums.Type;
import org.jeecg.modules.technical.service.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/technical/folder")
@Api(tags = "技术管理-目录")
public class FolderController {

    @Autowired
    private FolderService folderService;

    @ApiOperation("根据树型层级目录名称查询目录,树型用','分割,多个树型用';'分割e.g. aaa,bbb;ccc,ddd")
    @RequestMapping(value = "queryTreeLastNodeByFolderTreeNames", method = {RequestMethod.POST, RequestMethod.GET})
    public Result queryTreeLastNodeByFolderTreeNames(@RequestBody Folder folderParams) {
        String[] split = Optional.ofNullable(folderParams.getFolderTreeNames()).orElse("").split(";");
        List<Folder> folders = folderService.queryTreeLastNodeByFolderTreeNames(folderParams, Arrays.asList(split));
        return Result.OK(folders);
    }

    @GetMapping("/business/search/folder")
    @ApiOperation("根据目录名称模糊查询所在目录")
    public Result findFolderIdsByFolderName(@RequestParam(required = false) String folderName,
                                            @RequestParam(required = false) String folderId,
                                            @RequestParam(required = false) String businessId,
                                            @RequestParam(required = false) String projectId) {

        List<Map<String, Object>> folderIdsByFolderName = folderService.findFolderIdsByFolderName(folderName, folderId, businessId, projectId);
        return Result.OK(folderIdsByFolderName);
    }

    @GetMapping("/business/search/file")
    @ApiOperation("根据文件名称模糊查询所在目录")
    public Result findFolderIdsByFileName(@RequestParam(required = false) String fileName,
                                          @RequestParam(required = false) String fileId,
                                          @RequestParam(required = false) String tags,
                                          @RequestParam(required = false) String businessId,
                                          @RequestParam(required = false) String projectId) {
        List<Map<String, Object>> folderIdsByFileName = folderService.findFolderIdsByFileName(fileName, fileId, tags, businessId, projectId);
        return Result.OK(folderIdsByFileName);
    }

    @DeleteMapping("/business/all/{businessId}")
    @ApiOperation("删除businessId关联的所有目录（真实删除）")
    public Result disableBusinessAll(@ApiParam(value = "目录id") @PathVariable String businessId) {
        folderService.deleteByBusinessId(businessId);
        return Result.OK();
    }

    @DeleteMapping("/business/all/deleteBatch")
    @ApiOperation("删除businessId(批量)关联的所有目录（真实删除）")
    public Result disableBusinessAllBatch(@RequestParam(name="ids") String ids) {
        folderService.deleteByBusinessIds(Arrays.asList(ids.split(",")));
        return Result.OK();
    }

    @DeleteMapping("/business/{id}")
    @ApiOperation("删除目录（真实删除）")
    public Result disableBusiness(@ApiParam(value = "目录id") @PathVariable String id) {
        folderService.delete(id, false);
        return Result.OK();
    }

    @RequestMapping(value = "/business/findRoot", method = {RequestMethod.GET, RequestMethod.POST})
    @ApiOperation(value = "查询根目录",
            notes = "第一次访问时，将自动初始化”默认分组“。")
    public Result findRootBusiness(@ApiParam(value = "项目ID") @RequestParam(required = false) String projectId,
                                   @ApiParam(value = "项目名") @RequestParam(required = false) String projectName,
                                   @ApiParam(value = "业务ID") @RequestParam(required = false) String businessId,
                                   @ApiParam(value = "业务名称") @RequestParam(required = false, defaultValue = "") String businessName,
                                   @ApiParam(value = "类型") @RequestParam(required = false) Type type,
                                   @RequestBody(required = false) Map<String, String> payload) {
        List<String> subFolders = null;
        JSONArray initialFolders = null;
        if (payload != null) {
            if (null != payload.get("initialFolders")) {
                initialFolders = JSON.parseArray(payload.get("initialFolders"));
            }
            if (null != payload.get("subFolders")) {
                subFolders = Arrays.asList(payload.get("subFolders").split(","));
            }
        }
        List<Folder> roots = folderService.findRoot(Folder.of().ofType(type).ofProject(projectId, projectName).ofBusiness(businessId, businessName),
                initialFolders, subFolders);
        // List<FolderVo> folderVos = new ArrayList<>();
        // BeanUtil.copyProperties(roots, folderVos);
        return Result.OK(roots);
    }

    @GetMapping("/business/saveRoot")
    @ApiOperation(value = "创建/更新根目录（业务id关联）",
            notes = "1.目前仅开放3级目录(根除外) 2.不同类型的根目录将限制不通格式的文件，超出格式限制时将给出错误提示 3.更新时仅建议更新目录名，需要操作目录的请使用目录移动/排序接口")
    public Result saveRootBusiness(@ApiParam(value = "项目ID") @RequestParam String projectId,
                                   @ApiParam(value = "项目名，可为空") @RequestParam(required = false) String projectName,
                                   @ApiParam(value = "业务ID，可为空") @RequestParam(required = false) String businessId,
                                   @ApiParam(value = "业务ID，可为空") @RequestParam(required = false) String businessName,
                                   @ApiParam(value = "目录名") @RequestParam String name,
                                   @ApiParam(value = "类型") @RequestParam Type type,
                                   @ApiParam(value = "id，留空表示新增") @RequestParam(required = false) String id) {
        Folder save = folderService.save(Folder.of(id).ofName(name).ofType(type).ofLevel(Level.ROOT)
                .ofProject(projectId, projectName)
                .ofBusiness(businessId, businessName));

        // FolderVo vo = new FolderVo();
        // BeanUtil.copyProperties(save, vo);
        return Result.OK(save);
    }

    @GetMapping("/findRoot")
    @ApiOperation(value = "查询根目录",
            notes = "第一次访问时，将自动初始化”默认分组“。")
    public Result findRoot(@ApiParam(value = "项目ID") @RequestParam(required = false) String projectId,
                           @ApiParam(value = "项目名") @RequestParam(required = false) String projectName,
                           @ApiParam(value = "类型") @RequestParam Type type) {
        List<Folder> roots = folderService.findRoot(Folder.of().ofProject(projectId, projectName).ofType(type));
        // List<FolderVo> folderVos = new ArrayList<>();
        // BeanUtil.copyProperties(roots, folderVos);
        return Result.OK(roots);
    }

    @GetMapping("/findOne")
    @ApiOperation("查询目录")
    public Result findOne(@ApiParam(value = "目录id") @RequestParam String folderId) {
        Folder folder = folderService.refreshAllChild(folderId);
        // FolderVo vo = new FolderVo();
        // BeanUtil.copyProperties(folder, vo);
        return Result.OK(folder);
    }

    @GetMapping("/findChild")
    @ApiOperation("查询子目录")
    public Result findChild(@ApiParam(value = "父目录id") @RequestParam String parentId) {
        List<Folder> roots = folderService.findChild(parentId);
        // List<FolderVo> folderVos = new ArrayList<>();
        // BeanUtil.copyProperties(roots, folderVos);
        return Result.OK(roots);
    }

    @GetMapping("/saveRoot")
    @ApiOperation(value = "创建/更新根目录",
            notes = "1.目前仅开放3级目录(根除外) 2.不同类型的根目录将限制不通格式的文件，超出格式限制时将给出错误提示 3.更新时仅建议更新目录名，需要操作目录的请使用目录移动/排序接口")
    public Result saveRoot(@ApiParam(value = "项目ID") @RequestParam String projectId,
                           @ApiParam(value = "项目名，可为空") @RequestParam(required = false) String projectName,
                           @ApiParam(value = "目录名") @RequestParam String name,
                           @ApiParam(value = "类型") @RequestParam Type type,
                           @ApiParam(value = "id，留空表示新增") @RequestParam(required = false) String id) {
        Folder save = folderService.save(Folder.of(id).ofName(name).ofType(type).ofLevel(Level.ROOT).ofProject(projectId, projectName));
        // FolderVo vo = new FolderVo();
        // BeanUtil.copyProperties(save, vo);
        return Result.OK(save);
    }

    @GetMapping("/saveChild")
    @ApiOperation(value = "创建/更新子目录",
            notes = "1.类型自动继承父目录 2.更新时仅建议更新目录名，需要操作目录的请使用目录移动/排序接口")
    public Result saveChild(@ApiParam(value = "目录名") @RequestParam String name,
                            @ApiParam(value = "父目录id") @RequestParam String parentId,
                            @ApiParam(value = "id，留空表示新增") @RequestParam(required = false) String id) {
        Folder saved = folderService.save(Folder.of(id).ofName(name).ofParent(parentId));
        // FolderVo vo = new FolderVo();
        // BeanUtil.copyProperties(saved, vo);
        return Result.OK(saved);
    }

    @GetMapping("/reOrder")
    @ApiOperation(value = "目录排序",
            notes = "仅支持同一父目录下的同层目录间的拖动排序")
    public Result saveChild(@ApiParam(value = "源目录id") @RequestParam String sourceId,
                            @ApiParam(value = "目标目录id") @RequestParam String targetId) {
        List<Folder> folders = folderService.reOrder(sourceId, targetId);
        // List<FolderVo> folderVos = new ArrayList<>();
        // BeanUtil.copyProperties(folders, folderVos);
        return Result.OK(folders);
    }

    @PostMapping("/moveFolder")
    @ApiOperation(value = "目录移动",
            notes = "目标目录id->填空字符串表示移动到根目录")
    public Result moveFolder(@ApiParam(value = "待移动目录id列表") @RequestParam List<String> sourceId,
                             @ApiParam(value = "目标目录id") @RequestParam String targetId) {
        List<Folder> folders = folderService.moveFolder(sourceId, targetId);
        // List<FolderVo> folderVos = new ArrayList<>();
        // BeanUtil.copyProperties(folders, folderVos);
        return Result.OK(folders);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除目录（逻辑删除）")
    public Result disable(@ApiParam(value = "目录id") @PathVariable String id) {
        folderService.changeStatus(id, Enabled.DISABLED);
        return Result.OK();
    }
}
