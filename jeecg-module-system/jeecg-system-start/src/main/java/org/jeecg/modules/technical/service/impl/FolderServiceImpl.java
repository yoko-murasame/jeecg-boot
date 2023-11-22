package org.jeecg.modules.technical.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.system.service.ISysPermissionService;
import org.jeecg.modules.system.util.ShiroUtil;
import org.jeecg.modules.technical.entity.File;
import org.jeecg.modules.technical.entity.Folder;
import org.jeecg.modules.technical.entity.FolderUserPermission;
import org.jeecg.modules.technical.entity.enums.*;
import org.jeecg.modules.technical.mapper.FileMapper;
import org.jeecg.modules.technical.mapper.FolderMapper;
import org.jeecg.modules.technical.sample.entity.Project;
import org.jeecg.modules.technical.sample.mapper.ProjectMapper;
import org.jeecg.modules.technical.service.FileService;
import org.jeecg.modules.technical.service.FolderService;
import org.jeecg.modules.technical.service.FolderUserPermissionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Service
@CacheConfig(cacheNames = "FolderServiceImpl")
public class FolderServiceImpl implements FolderService {

    @Autowired
    private FolderMapper folderMapper;
    @Autowired
    private FileMapper fileMapper;
    @Autowired
    @Lazy
    private FileService fileService;
    @Autowired
    @Lazy
    private FolderService folderService;

    @Resource
    private FolderUserPermissionService permissionService;

    @Resource
    private ISysPermissionService sysPermissionService;

    @Override
    public Folder findOne(String id) {
        Assert.state(StringUtils.hasText(id), "folderId不能为空");
        LambdaQueryChainWrapper<Folder> lqcw = new LambdaQueryChainWrapper<>(folderMapper).eq(Folder::getId, id);
        return lqcw.one();
    }

    /**
     * @author Yoko
     * @date 2022/4/25 10:26
     * @description 根据目录名称模糊或者ID查询所在目录
     */
    @Override
    public List<Map<String, Object>> findFolderIdsByFolderName(String folderName, String folderId, String businessId, String projectId) {

        List<Folder> folders = new LambdaQueryChainWrapper<>(folderMapper)
                .eq(Folder::getEnabled, Enabled.ENABLED)
                .eq(StringUtils.hasText(businessId), Folder::getBusinessId, businessId)
                .eq(StringUtils.hasText(projectId), Folder::getProjectId, projectId)
                .eq(StringUtils.hasText(folderId), Folder::getId, folderId)
                .like(StringUtils.hasText(folderName), Folder::getName, folderName)
                .list();

        List<Map<String, Object>> result = folders.stream().map(folder -> {
            Map<String, Object> res = new HashMap<>(2);
            res.put("folder", folder);
            List<String> folderTreeId = this.getFolderTreeId(folder.getId(), null);
            res.put("folderTreeId", folderTreeId);
            return res;
        }).collect(Collectors.toList());

        return result;
    }

    /**
     * @param fileName
     * @param businessId
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @author Yoko
     * @date 2022/4/25 10:08
     * @description 根据文件名称模糊或者ID查询所在目录
     */
    @Override
    public List<Map<String, Object>> findFolderIdsByFileName(String fileName, String fileId, String tags, String businessId, String projectId) {

        LambdaQueryChainWrapper<File> wp = new LambdaQueryChainWrapper<>(fileMapper)
                .eq(File::getEnabled, Enabled.ENABLED)
                .eq(File::getCurrent, Current.TRUE)
                .eq(StringUtils.hasText(businessId), File::getBusinessId, businessId)
                .eq(StringUtils.hasText(projectId), File::getProjectId, projectId)
                .eq(StringUtils.hasText(fileId), File::getId, fileId)
                .like(StringUtils.hasText(fileName), File::getName, fileName);

        if (StringUtils.hasText(tags)) {
            wp.and(wrapper -> {
                Arrays.stream(tags.split(",")).forEach(
                        tag -> wrapper.or().like(File::getTags, tag)
                );
            });
        }

        List<File> files = wp.list();

        List<Map<String, Object>> result = files.stream().map(file -> {
            Map<String, Object> res = new HashMap<>(2);
            res.put("file", file);
            List<String> folderTreeId = this.getFolderTreeId(file.getFolderId(), null);
            res.put("folderTreeId", folderTreeId);
            return res;
        }).collect(Collectors.toList());

        return result;
    }

    /**
     * 查询树中某个子节点及其所有父节点的id
     * 按照父节点 -> 子 -> 孙 顺序排列
     */
    public List<String> getFolderTreeId(String folderId, LinkedList<String> result) {
        if (result == null) {
            result = new LinkedList<>();
        }
        Folder folder = folderService.findOne(folderId);
        // 用链表查数据，往最队列头部插
        result.addFirst(folder.getId());
        if (StringUtils.hasText(folder.getParentId())) {
            return this.getFolderTreeId(folder.getParentId(), result);
        }
        return result;
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = {"FolderServiceImpl", "TechnicalCacheService"}, allEntries = true)
    public Folder refreshAllChild(String folderId) {
        return this.refreshAllChild(null, folderId);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = {"FolderServiceImpl", "TechnicalCacheService"}, allEntries = true)
    public Folder refreshAllChild(Folder folder) {
        return this.refreshAllChild(folder, null);
    }

    /**
     * 问题一：下面方式，会导致出现“斐波那契数列”，逻辑有误
     * Integer childFileSize = Optional.of(parent.getChildFileSize()).orElse(0) + Optional.of(child.getChildFileSize()).orElse(0);
     * 正确应该是：
     * 1.父亲节点手动去查询统计儿子目录的文件个数
     * 2.父亲节点查询自己的文件个数
     * 3.还需要更新父亲节点的子目录树（在目录初始化时，会有查询缓存，不在这里更新的话会都是0）
     * 问题二：初始化目录后，发现根节点的父子数据没出来
     * 1.调试分析后，发现数据库内数据是对的，因此是缓存问题导致的，代码逻辑正确无误
     * 2.可以见方法：
     * 2.1 FolderServiceImpl#initialFolders
     * 2.2 FolderServiceImpl#findRootBusiness
     */
    @Override
    @Transactional
    @CacheEvict(cacheNames = {"FolderServiceImpl", "TechnicalCacheService"}, allEntries = true)
    public Folder refreshAllChild(Folder folder, String folderId) {
        if (StringUtils.isEmpty(folderId) && folder == null) {
            return null;
        }
        if (folder == null) {
            folder = folderService.findOne(folderId);
        }
        if (folder == null) {
            return null;
        }

        long parentSelfFileCount = new LambdaQueryChainWrapper<>(fileMapper)
                .eq(File::getFolderId, folder.getId())
                .eq(File::getCurrent, Current.TRUE)
                .eq(File::getEnabled, Enabled.ENABLED).count();

        List<Folder> childFolders = new LambdaQueryChainWrapper<>(folderMapper)
                .eq(Folder::getParentId, folder.getId())
                .eq(Folder::getEnabled, Enabled.ENABLED).list();
        int childFileSize = childFolders.stream().map(f -> Optional.of(f.getChildFileSize()).orElse(0)).reduce(0, Integer::sum);

        folder.setChildFileSize(Math.toIntExact(parentSelfFileCount + childFileSize));
        folder.setChildFolderSize(childFolders.size());
        folderMapper.updateById(folder);

        // 递归（父节点的树就不返回了，只返回进入的那个节点）
        this.refreshAllChild(null, folder.getParentId());
        log.info("父目录文件数刷新成功");
        return folder;
    }

    @Override
    // @Cacheable
    public List<Folder> findByLevel(Folder folderParam) {
        Assert.state(folderParam.getLevel() != null, "level不能为空");
        Project existProject = folderService.existProject(folderParam);
        Assert.notNull(existProject, "关联项目不存在");
        LambdaQueryWrapper<Folder> qr = Wrappers.lambdaQuery(Folder.class)
                .eq(Folder::getEnabled, Enabled.ENABLED)
                .eq(Folder::getLevel, folderParam.getLevel())
                .eq(null != folderParam.getType(), Folder::getType, folderParam.getType())
                .eq(StringUtils.hasText(existProject.getId()), Folder::getProjectId, existProject.getId())
                .eq(StringUtils.hasText(existProject.getName()), Folder::getProjectName, existProject.getName())
                .eq(StringUtils.hasText(existProject.getBusinessId()), Folder::getBusinessId, existProject.getBusinessId())
                .eq(StringUtils.hasText(existProject.getBusinessName()), Folder::getBusinessName, existProject.getBusinessName())
                .eq(StringUtils.hasText(folderParam.getParentId()), Folder::getParentId, folderParam.getParentId())
                .orderByAsc(Folder::getLevel, Folder::getFolderOrder);
        // 区分全部列表和权限列表
        if (sysPermissionService.hasButtonPermission(ShiroUtil.getLoginUsername(), PermissionType.FULL.getPerm())) {
            return folderMapper.selectList(qr);
        }
        // 查询权限列表
        LambdaQueryWrapper<FolderUserPermission> permWp = Wrappers.lambdaQuery(FolderUserPermission.class)
                .eq(FolderUserPermission::getUsername, ShiroUtil.getLoginUsername());
        String permWpStr = permWp.getCustomSqlSegment().replaceAll("ew", "permWp");
        String fdWpStr = qr.getCustomSqlSegment().replaceAll("ew", "fdWp");
        return folderMapper.selectAuthList(qr, permWp, fdWpStr, permWpStr);
    }

    @Transactional(rollbackFor = Exception.class)
    public List<Folder> initialStringSubFolder(Folder folderParam, List<String> subFolders) {
        folderParam.setName(Optional.ofNullable(folderParam.getName()).orElse(DEFAULT_FOLDER_NAME));
        Folder initFolder = new Folder()
                .setProjectId(folderParam.getProjectId())
                .setProjectName(folderParam.getProjectName())
                .setBusinessId(folderParam.getBusinessId())
                .setBusinessName(folderParam.getBusinessName())
                .setName(folderParam.getName())
                .setType(folderParam.getType())
                .setLevel(Level.ROOT);
        this.save(initFolder);
        if (!CollectionUtils.isEmpty(subFolders)) {
            // 初始化子文件夹
            subFolders.forEach(subName -> {
                if (StringUtils.hasText(subName)) {
                    this.save(new Folder()
                            .setProjectId(folderParam.getProjectId())
                            .setProjectName(folderParam.getProjectName())
                            .setBusinessId(folderParam.getBusinessId())
                            .setBusinessName(folderParam.getBusinessName())
                            .setName(subName)
                            .setType(folderParam.getType())
                            .setLevel(Level.FIRST)
                            .setParentId(initFolder.getId()));
                }
            });
            this.refreshAllChild(initFolder.getId());
        }
        return Arrays.asList(initFolder);
    }

    @Transactional(rollbackFor = Exception.class)
    public void initialJsonSubFolders(Folder folderParam, JSONArray initialFolders, Folder parent, List<Folder> rootFolders) {
        IntStream.range(0, initialFolders.size()).forEach(idx -> {
            JSONObject jsonObject = initialFolders.getJSONObject(idx);
            Folder folder = new Folder()
                    .setProjectId(folderParam.getProjectId())
                    .setProjectName(folderParam.getProjectName())
                    .setBusinessId(folderParam.getBusinessId())
                    .setBusinessName(folderParam.getBusinessName())
                    .setName(Optional.ofNullable(jsonObject.getString("name")).orElse(DEFAULT_FOLDER_NAME))
                    .setType(Optional.ofNullable(Type.of(jsonObject.getString("type"))).orElse(folderParam.getType()));
            // 没有指定父节点时，默认第一层就是父节点
            if (null == parent) {
                folder.setLevel(Level.ROOT);
                rootFolders.add(folder);
            } else {
                folder.setLevel(Level.child(parent.getLevel()))
                        .setParentId(parent.getId());
            }
            this.save(folder);
            this.refreshAllChild(folder);
            // 子目录
            JSONArray children = jsonObject.getJSONArray("children");
            if (children != null && children.size() > 0) {
                this.initialJsonSubFolders(folderParam, children, folder, rootFolders);
            }
        });
    }

    @Override
    // @Cacheable
    public List<Folder> findRoot(Folder folderParam) {
        return this.findRoot(folderParam, null, null);
    }

    @Override
    // @Cacheable
    public synchronized List<Folder> findRoot(Folder folderParam, JSONArray jsonFolders, List<String> subFolders) {
        List<Folder> folders = this.findByLevel(folderParam.ofLevel(Level.ROOT));
        // 初始化默认文件夹
        try {
            // 查询是否有全部目录查看权限，有的话，才能初始化目录
            if ((null == folders || folders.isEmpty()) && sysPermissionService.hasButtonPermission(ShiroUtil.getLoginUsername(), PermissionType.FULL.getPerm())) {
                folders = new ArrayList<>();
                if (null != jsonFolders && !jsonFolders.isEmpty()) {
                    this.initialJsonSubFolders(folderParam, jsonFolders, null, folders);
                    // 再刷新父节点
                    folders.forEach(folderService::refreshAllChild);
                } else if (null != subFolders && !subFolders.isEmpty()) {
                    folders = this.initialStringSubFolder(folderParam, subFolders);
                } else {
                    Folder initFolder = new Folder()
                            .setProjectId(folderParam.getProjectId())
                            .setProjectName(folderParam.getProjectName())
                            .setBusinessId(folderParam.getBusinessId())
                            .setBusinessName(folderParam.getBusinessName())
                            .setType(folderParam.getType())
                            .setLevel(Level.ROOT)
                            .setName(DEFAULT_FOLDER_NAME);
                    this.save(initFolder);
                    folders.add(initFolder);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException("查询失败，并且初始化目录失败，错误：" + e.getMessage());
        }
        return folders;
    }

    // 查询子节点，parentId不能为空
    @Override
    // @Cacheable(key = "'childList#'+#parentId")
    public List<Folder> findChild(String parentId) {
        Assert.state(StringUtils.hasText(parentId), "parentId不能为空");
        LambdaQueryWrapper<Folder> qr = Wrappers.lambdaQuery(Folder.class)
                .eq(Folder::getParentId, parentId)
                .eq(Folder::getEnabled, Enabled.ENABLED)
                .orderByAsc(Folder::getLevel, Folder::getFolderOrder);

        // 区分全部列表和权限列表
        if (sysPermissionService.hasButtonPermission(ShiroUtil.getLoginUsername(), PermissionType.FULL.getPerm())) {
            return folderMapper.selectList(qr);
        }
        // 查询权限列表
        LambdaQueryWrapper<FolderUserPermission> permWp = Wrappers.lambdaQuery(FolderUserPermission.class)
                .eq(FolderUserPermission::getUsername, ShiroUtil.getLoginUsername());
        String permWpStr = permWp.getCustomSqlSegment().replaceAll("ew", "permWp");
        String fdWpStr = qr.getCustomSqlSegment().replaceAll("ew", "fdWp");
        return folderMapper.selectAuthList(qr, permWp, fdWpStr, permWpStr);
    }

    @Transactional
    @Override
    @CacheEvict(cacheNames = {"FolderServiceImpl", "TechnicalCacheService"}, allEntries = true)
    public List<Folder> reOrder(String sourceId, String targetId) {
        Assert.state(StringUtils.hasText(sourceId) && StringUtils.hasText(targetId) && !sourceId.equals(targetId), "源ID与目标ID参数错误");
        Folder source = this.findOne(sourceId);
        Folder target = this.findOne(targetId);
        Assert.state(source != null && target != null, "找不到相关目录信息，操作失败");
        Assert.state(source.getParentId().equals(target.getParentId()), "仅支持同级目录移动");

        // 找到两个目录中间的
        final int maxOrder = source.getFolderOrder() > target.getFolderOrder() ? source.getFolderOrder() : target.getFolderOrder();
        final int minOrder = source.getFolderOrder() < target.getFolderOrder() ? source.getFolderOrder() : target.getFolderOrder();
        List<Folder> folders = new LambdaQueryChainWrapper<>(folderMapper)
                .eq(Folder::getEnabled, Enabled.ENABLED)
                .and(wrapper -> {
                    wrapper.eq(Folder::getParentId, source.getParentId());
                    if (source.getLevel() == Level.ROOT) {
                        wrapper.eq(StringUtils.hasText(source.getProjectId()), Folder::getProjectId, source.getProjectId())
                                .eq(StringUtils.hasText(source.getBusinessId()), Folder::getBusinessId, source.getBusinessId())
                                .eq(Folder::getType, source.getType());
                    }
                })
                .between(Folder::getFolderOrder, minOrder, maxOrder)
                .orderByAsc(Folder::getFolderOrder).list();
        Assert.state(folders.size() > 0, "待移动列表为空");
        if (source.getFolderOrder() > target.getFolderOrder()) { // 上移动：max换成min，剩余下移1位（+1）
            folders.forEach(folder -> {
                Integer order = folder.getFolderOrder();
                folder.setFolderOrder(order == maxOrder ? minOrder : order + 1);
                boolean update = new LambdaUpdateChainWrapper<>(folderMapper)
                        .eq(Folder::getId, folder.getId())
                        .set(Folder::getFolderOrder, folder.getFolderOrder()).update();
                if (!update) {
                    throw new RuntimeException("目录移动失败，更新目录时顺序时发生错误");
                }
            });
        } else { // 下移动
            folders.forEach(folder -> {
                Integer order = folder.getFolderOrder();
                folder.setFolderOrder(order == minOrder ? maxOrder : order - 1);
                boolean update = new LambdaUpdateChainWrapper<>(folderMapper)
                        .eq(Folder::getId, folder.getId())
                        .set(Folder::getFolderOrder, folder.getFolderOrder()).update();
                if (!update) {
                    throw new RuntimeException("目录移动失败，更新目录时顺序发生错误");
                }
            });
        }

        List<Folder> newFolders = new LambdaQueryChainWrapper<>(folderMapper)
                .eq(Folder::getEnabled, Enabled.ENABLED)
                .and(wrapper -> {
                    wrapper.eq(Folder::getParentId, source.getParentId());
                    if (source.getLevel() == Level.ROOT) {
                        wrapper.eq(StringUtils.hasText(source.getProjectId()), Folder::getProjectId, source.getProjectId())
                                .eq(StringUtils.hasText(source.getBusinessId()), Folder::getBusinessId, source.getBusinessId())
                                .eq(Folder::getType, source.getType());
                    }
                })
                .orderByAsc(Folder::getFolderOrder).list();

        return newFolders;
    }

    /**
     * 目录移动
     *
     * @param ids 待移动目录列表
     * @param targetId 传入空字符串表示根目录
     * @return
     */
    @Transactional
    @Override
    @CacheEvict(cacheNames = {"FolderServiceImpl", "TechnicalCacheService"}, allEntries = true)
    public List<Folder> moveFolder(List<String> ids, String targetId) {
        // 移动目录：parentId、order、Level
        Assert.state(ids.size() > 0, "参数错误");
        // 找到所有目录
        List<Folder> sources = folderMapper.selectBatchIds(ids);
        Assert.state(sources.size() > 0, "没有找到选择的移动目录");
        // 重置参数
        if (StringUtils.hasText(targetId)) { // 非根目录情况
            Folder target = this.findOne(targetId);
            Assert.notNull(target, "目标对象为空");
            String newParentId = target.getId();
            Level newLevel = Level.child(target.getLevel());
            Assert.notNull(newLevel, "移动失败，超出目录层级");
            // 目标节点的最大子排序

            Folder maxOrderFolder = new LambdaQueryChainWrapper<>(folderMapper)
                    .eq(Folder::getParentId, target.getId())
                    .select(Folder::getFolderOrder)
                    .orderByDesc(Folder::getFolderOrder)
                    .last("limit 1").one();
            AtomicReference<Integer> maxOrder =
                    null == maxOrderFolder ? new AtomicReference<>(-1) : new AtomicReference<>(maxOrderFolder.getFolderOrder());

            // 更新被移动的目录信息
            sources.forEach(folder -> {
                // 源目录的父目录的子目录数操作--
                if (StringUtils.hasText(folder.getParentId())) {
                    this.operateChildFolderSize(folder.getParentId(), 1, Operation.SUB);
                }

                folder.setParentId(newParentId);
                Integer order = maxOrder.get();
                maxOrder.set(++order);
                folder.setFolderOrder(order);
                folder.setLevel(newLevel);
                folderMapper.updateById(folder);
            });
            // 目标目录的子目录数操作++
            this.operateChildFolderSize(targetId, sources.size(), Operation.PLUS);

            return this.findChild(targetId);
        } else { // 根目录情况
            Folder tempFolder = sources.get(0);
            // 目标节点的最大子排序
            Folder maxOrderFolder = new LambdaQueryChainWrapper<>(folderMapper)
                    .eq(Folder::getParentId, "")
                    .eq(Folder::getLevel, Level.ROOT)
                    .eq(StringUtils.hasText(tempFolder.getProjectId()), Folder::getProjectId, tempFolder.getProjectId())
                    .eq(StringUtils.hasText(tempFolder.getBusinessId()), Folder::getBusinessId, tempFolder.getBusinessId())
                    .eq(Folder::getType, tempFolder.getType())
                    .select(Folder::getFolderOrder)
                    .orderByDesc(Folder::getFolderOrder)
                    .last("limit 1").one();
            AtomicReference<Integer> maxOrder =
                    null == maxOrderFolder ? new AtomicReference<>(-1) : new AtomicReference<>(maxOrderFolder.getFolderOrder());
            sources.forEach(folder -> {
                // 源目录的父目录的子目录数操作--
                if (StringUtils.hasText(folder.getParentId())) {
                    this.operateChildFolderSize(folder.getParentId(), 1, Operation.SUB);
                }

                folder.setParentId("");
                Integer order = maxOrder.get();
                maxOrder.set(++order);
                folder.setFolderOrder(order);
                folder.setLevel(Level.ROOT);
                folderMapper.updateById(folder);
            });
            return this.findRoot(tempFolder);
        }
    }

    @Transactional
    @Override
    @CacheEvict(cacheNames = {"FolderServiceImpl", "TechnicalCacheService"}, allEntries = true)
    public Folder save(Folder folder) {
        Assert.notNull(folder, "操作对象不能为空");
        Assert.state(StringUtils.hasText(folder.getName().trim()), "目录名称不能为空");

        if (StringUtils.hasText(folder.getParentId())) {
            // 已指定父节点：确保子节点类型和父节点一致，level必须比父节点level小1
            try {
                Folder parent = this.findOne(folder.getParentId());
                Assert.state(parent.getLevel() != Level.max(), "目录创建失败，目录层级超过限制");
                folder.setLevel(Level.child(parent.getLevel()))
                        .setType(parent.getType()) // 直接将父节点类型赋给子节点
                        .setProjectId(parent.getProjectId())
                        .setProjectName(parent.getProjectName())
                        .setBusinessId(parent.getBusinessId())
                        .setBusinessName(parent.getBusinessName());
            } catch (Exception e) {
                throw new RuntimeException("parentId错误：" + e.getMessage());
            }
        } else {
            // 未指定父节点：做逻辑判断，一般用于处理ROOT的新增，level.ROOT
            Assert.notNull(folder.getType(), "类型不能为空");
            Assert.notNull(folder.getLevel(), "level不能为空");
            // 关联项目判断
            Project existProject = folderService.existProject(folder);
            Assert.notNull(existProject, "关联项目不存在，无法操作目录");
            folder.setProjectId(existProject.getId())
                    .setProjectName(existProject.getName())
                    .setBusinessId(existProject.getBusinessId())
                    .setBusinessName(existProject.getBusinessName());
            // ROOT的新增时 Business 已经默认有了
        }

        // 查询基础条件：project_id&level&parentId&type
        QueryWrapper<Folder> baseQr = new QueryWrapper<Folder>()
                .eq("enabled", Enabled.ENABLED)
                .eq("level", folder.getLevel())
                .eq(null != folder.getType(), "type", folder.getType())
                .eq(StringUtils.hasText(folder.getProjectId()), "project_id", folder.getProjectId())
                .eq(StringUtils.hasText(folder.getProjectName()), "project_name", folder.getProjectName())
                .eq(StringUtils.hasText(folder.getBusinessId()), "business_id", folder.getBusinessId())
                .eq(StringUtils.hasText(folder.getBusinessName()), "business_name", folder.getBusinessName());

        // 预处理
        folder.setEnabled(Enabled.ENABLED);
        if (null == folder.getChildFileSize() || folder.getChildFileSize() < 0) {
            folder.setChildFileSize(0);
        }
        if (null == folder.getChildFolderSize() || folder.getChildFolderSize() < 0) {
            folder.setChildFolderSize(0);
        }
        if (null == folder.getParentId()) {
            folder.setParentId("");
        }

        // 更新或新增
        if (StringUtils.hasText(folder.getId())) {
            // 新逻辑：只允许修改name
            Folder existFolder = this.findOne(folder.getId());
            // 判断重名
            Folder sameName = folderMapper.selectOne(baseQr.clone()
                    .eq("parent_id", existFolder.getParentId())
                    .eq("name", folder.getName())
                    .last("limit 1"));
            Assert.state(null == sameName || sameName.getId().equals(folder.getId()), "同级存在同名目录，请重新命名");
            // 保存
            existFolder.setName(folder.getName());
            folderMapper.updateById(existFolder);
            BeanUtils.copyProperties(existFolder, folder);
        } else {
            // 判断重名
            Folder sameName = folderMapper.selectOne(baseQr.clone()
                    .eq("parent_id", folder.getParentId())
                    .eq("name", folder.getName())
                    .last("limit 1"));
            Assert.state(null == sameName || sameName.getId().equals(folder.getId()), "同级存在同名目录，请重新命名");
            // Order处理 每次新增设置最大值
            List<Map<String, Object>> maxOrderObj = folderMapper.selectMaps(baseQr.clone()
                    .orderByAsc("folder_order")
                    .select("max(folder_order)+1 as folder_order"));
            int maxOrder;
            try {
                maxOrder = (null != maxOrderObj && !maxOrderObj.isEmpty()) ? Integer.parseInt(maxOrderObj.get(0).get("folder_order") + "") : 0;
            } catch (Exception e) {
                log.info("该层级下无任何目录，初始化order=0。");
                maxOrder = 0;
            }
            folder.setFolderOrder(maxOrder);
            folderMapper.insert(folder);
            // 添加权限
            permissionService.savePersonalPermission(Collections.singletonList(folder.getId()), null);
        }

        // 更新父目录的状态
        this.refreshAllChild(folder.getParentId());

        return folder;
    }

    @Transactional
    @Override
    @CacheEvict(cacheNames = {"FolderServiceImpl", "TechnicalCacheService"}, allEntries = true)
    public int delete(Folder folder) {
        Assert.notNull(folder, "操作对象为null");
        return this.delete(folder.getId());
        // QueryWrapper<Folder> qr = DbUtil.getAllEqQueryWraper(folder);
        // int i = folderMapper.delete(qr);
        // //删除成功后，父目录的子目录树--
        // if (StringUtils.hasText(folder.getParentId()) && i > 0) {
        //     this.operateChildFolderSize(folder.getParentId(), 1, Operation.SUB);
        // }
        // return 0;
    }

    @Transactional
    @Override
    @CacheEvict(cacheNames = {"FolderServiceImpl", "TechnicalCacheService"}, allEntries = true)
    public int delete(String id) {
        Assert.state(StringUtils.hasText(id), "参数错误");
        Folder folder = folderService.findOne(id);
        if (null == folder) {
            return 0;
        }
        int i = folderMapper.deleteById(id);
        if (i > 0) {
            // 删除权限
            permissionService.removePermission(Collections.singletonList(id));
            // 删除成功后，父目录的子目录树--
            this.operateChildFolderSize(folder.getParentId(), 1, Operation.SUB);
            // 删除相关文件，真实删除
            fileService.deleteAllByFolder(folder.getId(), false);
            // 递归找到所有子树，并删除
            List<Folder> childs = this.findChild(id);
            childs.forEach(child -> this.delete(child.getId()));
        }
        return i;
    }

    @Transactional
    @Override
    @CacheEvict(cacheNames = {"FolderServiceImpl", "TechnicalCacheService"}, allEntries = true)
    public int delete(String id, boolean logic) {
        if (logic) {
            this.changeStatus(id, Enabled.DISABLED);
        } else {
            this.delete(id);
        }
        return 1;
    }

    @Transactional
    @Override
    @CacheEvict(cacheNames = {"FolderServiceImpl", "TechnicalCacheService"}, allEntries = true)
    public void changeStatus(String id, Enabled enabled) {
        Assert.state(StringUtils.hasText(id) && null != enabled, "参数错误");
        Folder folder = folderService.findOne(id);
        if (null == folder) {
            return;
        }
        boolean update = new LambdaUpdateChainWrapper<>(folderMapper).eq(Folder::getId, id).set(Folder::getEnabled, enabled).update();
        if (!update) {
            throw new RuntimeException("目录" + enabled.getCnName() + "失败");
        }
        // 删除权限
        permissionService.removePermission(Collections.singletonList(id));
        if (enabled == Enabled.DISABLED) {
            // 删除成功后，父目录的子目录树--
            this.operateChildFolderSize(folder.getParentId(), 1, Operation.SUB);
            // 删除相关文件
            fileService.deleteAllByFolder(folder.getId());
            // 递归找到所有子树，并删除
            List<Folder> childs = this.findChild(id);
            childs.forEach(child -> this.changeStatus(child.getId(), Enabled.DISABLED));
        }
    }

    @Transactional
    @Override
    @CacheEvict(cacheNames = {"FolderServiceImpl", "TechnicalCacheService"}, allEntries = true)
    @Deprecated
    public Folder operateChildFolderSize(String id, Integer value, Operation operation) {
        /**
         * 全部走自维护方法，旧的逻辑不适用了（复杂切易错）
         */
        if (true) {
            return this.refreshAllChild(id);
        }
        /**
         * 下面方法废弃了
         */
        // 此目录非根目录
        if (!StringUtils.hasText(id)) {
            log.info("id为空，可能是根目录相关操作，无需操作父节点改变子项数量");
            return null;
        }
        Assert.state(value != null && value >= 0, "操作数不能为负数");
        Folder folder = folderService.findOne(id);
        // 真实删除后需要判断空
        if (folder != null) {
            folder.setChildFolderSize(operation.calculate(folder.getChildFolderSize(), value));
            int isOk = folderMapper.updateById(folder);
            Assert.state(isOk > 0, "目录：" + id + " 子目录数更新失败");
        }
        return folder;
    }

    @Transactional
    @Override
    @CacheEvict(cacheNames = {"FolderServiceImpl", "TechnicalCacheService"}, allEntries = true)
    public Folder operateChildFileSize(String id, Integer value, Operation operation) {
        // 此目录非根目录
        if (!StringUtils.hasText(id)) {
            log.info("id为空，可能是根目录相关操作，无需操作父节点改变子项数量");
            return null;
        }
        Assert.state(value != null && value >= 0, "操作数不能为负数");
        Folder folder = folderService.findOne(id);
        folder.setChildFileSize(operation.calculate(folder.getChildFileSize(), value));
        folderMapper.updateById(folder);
        return folder;
    }

    @Override
    @Transactional
    public void deleteByBusinessId(String businessId) {
        Assert.state(StringUtils.hasText(businessId), "参数错误");
        List<Folder> folders = new LambdaQueryChainWrapper<>(this.folderMapper)
                .eq(Folder::getBusinessId, businessId)
                .list();
        folders.forEach(fd -> {
            int i = folderMapper.deleteById(fd.getId());
            if (i > 0) {
                // 删除权限
                permissionService.removePermission(Collections.singletonList(fd.getId()));
                // 删除相关文件，真实删除
                fileService.deleteAllByFolder(fd.getId(), false);
                // 递归找到所有子树，并删除
                List<Folder> childs = this.findChild(fd.getId());
                childs.forEach(child -> this.delete(child.getId()));
            }
        });
    }

    @Override
    @Transactional
    public void deleteByBusinessIds(List<String> ids) {
        ids.forEach(id -> {
            this.deleteByBusinessId(id);
        });
    }

    @Autowired
    private ProjectMapper projectMapper;

    /**
     * 查询自定义项目
     *
     * @param folderParam
     * @return org.jeecg.modules.technical.sample.entity.Project
     * @author Yoko
     * @since 2022/12/7 16:10
     */
    @Override
    @Cacheable(key = "'existProject#'+#folderParam.businessId + '#' + #folderParam.projectId")
    public Project existProject(Folder folderParam) {
        String projectId = folderParam.getProjectId();
        String projectName = folderParam.getProjectName();
        String businessId = folderParam.getBusinessId();
        String businessName = folderParam.getBusinessName();
        // 先找常量业务
        Project project = ConstantBusiness.getConstantBusiness(businessId, Project.class);
        // 后找自定义业务
        if (null == project) {
            LambdaQueryWrapper<Project> projectQr = new LambdaQueryWrapper<Project>().last("limit 1");
            Project exist = projectMapper.selectOne(projectQr
                    .eq(StringUtils.hasText(businessId) && !NULL.equals(businessId), Project::getId, businessId)
                    .and(StringUtils.isEmpty(businessId) && StringUtils.hasText(businessName), ew ->
                            ew.like(Project::getName, businessName))
                    .and(StringUtils.hasText(projectId) && !NULL.equals(projectId), ew -> ew.eq(Project::getId, projectId))
                    .and(StringUtils.isEmpty(projectId) && StringUtils.hasText(projectName), ew ->
                            ew.like(Project::getName, projectName).or().like(Project::getName, projectName)));
            if (exist != null) {
                project = new Project().setBusinessName(exist.getName()).setBusinessId(String.valueOf(exist.getId()))
                        .setId(exist.getId()).setName(exist.getName());
            }
            // 封装数据
            Optional.ofNullable(project)
                    .ifPresent(res -> res
                            .setId(Optional.ofNullable(StringUtils.hasText(projectId) ? projectId : null)
                                    .orElse(StringUtils.hasText(res.getId()) ? res.getId() : null))
                            .setName(Optional.ofNullable(StringUtils.hasText(projectName) ? projectName : null)
                                    .orElse(StringUtils.hasText(projectId) ? projectMapper.selectById(projectId).getName() :
                                            StringUtils.hasText(res.getName()) ? res.getName() : null))
                            .setBusinessId(StringUtils.hasText(businessId) ? businessId : res.getBusinessId())
                            .setBusinessName(StringUtils.hasText(businessName) ? businessName : res.getBusinessName())
                            // 处理标准业务名称封装
                            .setBusinessName(StringUtils.hasText(res.getBusinessName()) ? res.getBusinessName() :
                                    Optional.ofNullable(ConstantBusiness.of(businessId)).orElse(ConstantBusiness.EMPTY).name()));
        } else {
            // 常量数据
            Optional.of(project)
                    .ifPresent(res -> res
                            .setId(res.getId())
                            .setName(res.getName())
                            .setBusinessId(res.getBusinessId())
                            // 处理标准业务名称封装
                            .setBusinessName(StringUtils.hasText(res.getBusinessName()) ? res.getBusinessName() :
                                    Optional.ofNullable(ConstantBusiness.of(businessId)).orElse(ConstantBusiness.EMPTY).name()));
        }


        return project;
    }

    /**
     * 递归查找树形目录中某段枝叶
     *
     * @param folderParam 搜索条件
     * @param folderTreeName 必须是完整的树形父子孙结构，逗号分割 a,b,c
     * @return java.util.List<org.jeecg.modules.technical.entity.Folder>
     * @author Yoko
     * @since 2022/12/14 16:16
     */
    @Override
    public List<Folder> queryTreeListByFolderTreeName(Folder folderParam, String folderTreeName) {
        if (null == folderTreeName) {
            return null;
        }
        List<String> folderNameArr = Arrays.asList(folderTreeName.split(","));
        if (folderNameArr.size() == 0) {
            return null;
        }
        LambdaQueryWrapper<Folder> commonWp = Wrappers.lambdaQuery(folderParam)
                .eq(Folder::getEnabled, Enabled.ENABLED)
                .eq(StringUtils.hasText(folderParam.getProjectId()), Folder::getProjectId, folderParam.getProjectId())
                .eq(StringUtils.hasText(folderParam.getProjectName()), Folder::getProjectName, folderParam.getProjectName())
                .eq(StringUtils.hasText(folderParam.getBusinessId()), Folder::getBusinessId, folderParam.getBusinessId())
                .eq(StringUtils.hasText(folderParam.getBusinessName()), Folder::getBusinessName, folderParam.getBusinessName())
                .eq(!ObjectUtils.isEmpty(folderParam.getType()), Folder::getType, folderParam.getType());
        // begin end
        LambdaQueryWrapper<Folder> endWp = commonWp.clone();
        LambdaQueryWrapper<Folder> beginWp = commonWp.clone();
        endWp.eq(Folder::getName, folderNameArr.get(folderNameArr.size() - 1));
        beginWp.eq(Folder::getName, folderNameArr.get(0));

        String endWpstr = endWp.getCustomSqlSegment().replaceAll("ew", "endWp");
        String beginWpstr = beginWp.getCustomSqlSegment().replaceAll("ew", "beginWp");
        List<Folder> result = folderMapper.queryTreeListByFolderNames(endWp, beginWp, endWpstr, beginWpstr);

        return result;
    }

    @Override
    public Folder queryTreeLastNodeByFolderTreeName(Folder folderParam, String folderTreeName) {
        List<Folder> folders = this.queryTreeListByFolderTreeName(folderParam, folderTreeName);
        return (folders == null || folders.size() == 0) ? null : folders.get(folders.size() - 1);
    }

    /**
     * 递归查找树形目录中某段枝叶
     *
     * @param folderParam 搜索条件
     * @param folderTreeNames 多个完整的树形父子孙结构 ["a,a1,a2","b,b1,b2"]
     * @return java.util.List<org.jeecg.modules.technical.entity.Folder>
     * @author Yoko
     * @since 2022/12/14 16:16
     */
    @Override
    public List<Folder> queryTreeLastNodeByFolderTreeNames(Folder folderParam, List<String> folderTreeNames) {
        Function<String, Folder> func = e -> this.queryTreeLastNodeByFolderTreeName(folderParam, e);
        List<Folder> res = folderTreeNames.stream().map(func).filter(Objects::nonNull).collect(Collectors.toList());
        return res;
    }

    @Override
    public List<String> findPath(String folderId) {

        LambdaQueryWrapper<Folder> commonWp = Wrappers.lambdaQuery(Folder.class)
                .eq(Folder::getEnabled, Enabled.ENABLED);
        // begin end
        LambdaQueryWrapper<Folder> endWp = commonWp.clone();
        LambdaQueryWrapper<Folder> beginWp = commonWp.clone();
        endWp.eq(Folder::getId, folderId);
        beginWp.eq(Folder::getParentId, "");

        String endWpstr = endWp.getCustomSqlSegment().replaceAll("ew", "endWp");
        String beginWpstr = beginWp.getCustomSqlSegment().replaceAll("ew", "beginWp");
        List<Folder> result = folderMapper.queryTreeListByFolderNames(endWp, beginWp, endWpstr, beginWpstr);

        return result.stream().map(Folder::getId).collect(Collectors.toList());
    }

    /**
     * @param projectId
     * @param projectName
     * @return org.jeecg.modules.technical.sample.entity.Project
     * @author Yoko
     * @date 2022/2/15 12:07
     * @description 查询项目是否存在 Todo 改成自己的业务项目表
     * @deprecated
     */
    private Project existProject(String projectId, String projectName) {
        Assert.state(StringUtils.hasText(projectId) || StringUtils.hasText(projectName), "关联项目id或名称不能全为空");
        // QueryWrapper<Project> projectQr = new QueryWrapper<Project>().last("limit 1");
        // 通过项目确定作为项目表查询
        LambdaQueryWrapper<Project> projectQr = new LambdaQueryWrapper<Project>().last("limit 1");
        boolean existProjectId = StringUtils.hasText(projectId);
        projectQr.eq(existProjectId && !"null".equals(projectId), Project::getId, projectId);
        projectQr.and(!existProjectId && StringUtils.hasText(projectName), wrapper -> {
            wrapper.like(Project::getName, projectName).or().like(Project::getName, projectName);
        });

        Project exist = projectMapper.selectOne(projectQr);
        if (exist == null) {
            return null;
        }
        return new Project().setName(exist.getName()).setId(exist.getId() + "");
    }

    /**
     * @param businessId
     * @return org.jeecg.modules.technical.sample.entity.Project
     * @author Yoko
     * @date 2022/2/15 12:05
     * @description 查询项目是否存在（业务id，可以自定义关联，这里用教投项目表关联） Todo 改成自己的业务项目表
     * @deprecated
     */
    private Project existProject(String businessId) {
        Assert.state(StringUtils.hasText(businessId), "业务id或名称不能全为空");
        LambdaQueryWrapper<Project> projectQr = new LambdaQueryWrapper<Project>().last("limit 1");
        Project exist = projectMapper.selectOne(projectQr.eq(Project::getId, businessId));
        if (exist == null) {
            // 继续查找标准业务
            return ConstantBusiness.getConstantBusiness(businessId, "id", "name", Project.class);
        }
        return new Project().setName(exist.getName()).setId(exist.getId() + "");
    }

}
