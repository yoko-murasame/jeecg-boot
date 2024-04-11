package org.jeecg.modules.technical.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.util.CommonUtils;
import org.jeecg.common.util.yoko.FormatUtil;
import org.jeecg.modules.system.entity.SysUpload;
import org.jeecg.modules.system.service.ISysPermissionService;
import org.jeecg.modules.system.service.ISysUploadService;
import org.jeecg.modules.system.util.DbUtil;
import org.jeecg.modules.system.util.ShiroUtil;
import org.jeecg.modules.system.util.UploadFileUtil;
import org.jeecg.modules.technical.entity.File;
import org.jeecg.modules.technical.entity.Folder;
import org.jeecg.modules.technical.entity.FolderUserPermission;
import org.jeecg.modules.technical.entity.enums.*;
import org.jeecg.modules.technical.mapper.FileMapper;
import org.jeecg.modules.technical.service.FileService;
import org.jeecg.modules.technical.service.FolderService;
import org.jeecg.modules.technical.service.FolderUserPermissionService;
import org.jeecg.modules.technical.vo.FileRequest;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service("TechnicalFileService")
@Slf4j
@CacheConfig(cacheNames = "TechnicalFileServiceImpl")
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements FileService {

    @Autowired
    private FileMapper fileMapper;
    @Autowired
    @Lazy
    private FolderService folderService;
    // @Resource(name = "BimFaceFileService")
    // @Lazy
    // private org.jeecg.modules.bimface.service.FileService bimfaceFileService;
    @Autowired
    private ISysUploadService uploadService;

    @Resource
    private ISysPermissionService sysPermissionService;

    @Resource
    private FolderUserPermissionService permissionService;

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @CacheEvict(allEntries = true)
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public synchronized File upload(MultipartFile multipartFile, Folder folderParam, String customFileName) throws Exception {
        Assert.state(multipartFile != null, "文件流为空");

        /**
         * 初始化文件信息
         * 1.文件顺序，初始0
         * 2.变更、批注、问题
         * 3.文件大小、文件名
         * 4.上传人信息
         */
        String fileName = CommonUtils.getFileName(Optional.ofNullable(customFileName).orElse(Optional.ofNullable(multipartFile.getOriginalFilename()).orElse(
                "未命名文件.a")));
        File file = new File()
                .setSortOrder(0)
                .setEnabled(Enabled.ENABLED)
                .setChanges(new ArrayList<String>())
                .setComments(new ArrayList<String>())
                .setProblems(new ArrayList<String>())
                .setSize(UploadFileUtil.getPrintSize(multipartFile.getSize()))
                .setName(fileName)
                .setUploadBy(ShiroUtil.getLoginRealname());

        /**
         * 文件目录处理 & 关联项目id/name处理
         * 1.不能将文件存储在根目录同级处
         * 2.没有传入folderId时，使用目录系统的初始化目录：默认分组
         * 3.folder为空时，不关联目录，projectId必需；
         */
        Folder folder = checkAndGetFolder(folderParam);
        file.setFolderId(folder.getId())
                .setProjectId(folder.getProjectId())
                .setProjectName(folder.getProjectName())
                .setBusinessId(folder.getBusinessId())
                .setBusinessName(folder.getBusinessName());

        /**
         * 类型和后缀处理
         * 1.后缀和类型必须匹配
         * 2.文件类型必须和目录类型相同
         * 3.文件名的后缀必须在当前类型的格式限制中，超出会给出提示
         */
        Suffix suffix = checkAndGetSuffix(file.getName(), folder);
        file.setSuffix(suffix);
        file.setType(folder.getType());

        /**
         * 版本号处理
         * 1.version，查询最新的记录号，不能通过缓存，无版本初始化0
         * 2.文件名相同，将做为新版本
         * 3.继承 变更、批注、问题
         * 4.设置成当前使用版本 current
         */
        checkAndSetNewVersion(file);

        /**
         * 保存
         * 1.上传至aliyun，必须成功
         * 2.插入数据库，插入失败时需要删除oss文件
         * 3.插入成功，切换到当前版本
         */
        persistFile(multipartFile, file);

        /**
         * 其他信息处理
         * 1.bimface相关的文件，交给bimface服务（通过异步方法，上传到bimface平台，然后更新文件，文件转化-沟通-在哪里执行）
         * 2.更新已上传文件关联的目录信息
         */
        // if (suffix.getBimfaceSupport()) {
        //     bimfaceFileService.upload(file);
        // }
        folderService.refreshAllChild(folder.getId());

        return file;
    }

    public void persistFile(MultipartFile multipartFile, File file) throws Exception {
        // 上传文件（自动判断本地还是OSS）
        SysUpload sysUpload = uploadService.upload(multipartFile);
        file.setOssFile(sysUpload);
        // 保存
        int insert = fileMapper.insert(file);
        if (insert < 1) {
            // 失败时需要删除已保存的文件信息
            uploadService.delete(sysUpload);
            throw new RuntimeException("文件上传失败，服务器繁忙，请稍后再试。");
        }

        //保存成功再切换版本
        //更新选中版本：其余版本current-false，新插入的设为true
        //boolean update = new LambdaUpdateChainWrapper<>(fileMapper).eq(File::getEnabled, Enabled.ENABLED)
        new LambdaUpdateChainWrapper<>(fileMapper).eq(File::getEnabled, Enabled.ENABLED)
                .eq(File::getName, file.getName())
                .eq(StringUtils.hasText(file.getProjectId()), File::getProjectId, file.getProjectId())
                .eq(StringUtils.hasText(file.getProjectName()), File::getProjectName, file.getProjectName())
                .eq(StringUtils.hasText(file.getBusinessId()), File::getBusinessId, file.getBusinessId())
                .eq(StringUtils.hasText(file.getBusinessName()), File::getBusinessName, file.getBusinessName())
                .eq(File::getCurrent, Current.TRUE)
                .eq(File::getType, file.getType())
                .eq(File::getSuffix, file.getSuffix())
                .eq(File::getFolderId, file.getFolderId())//注意：一定是当前目录中的文件，不添加此条件会插入同名文件时影响其它目录的文件
                .ne(File::getVersion, file.getVersion())//排除当前插入的版本
                .set(File::getCurrent, Current.FALSE).update();
        //Assert.state(update || file.getVersion() == 0 , "新增文件时版本切换错误"); //新插入数据时，更新影响行数为0也会导致异常，加上版本判断
    }

    private void checkAndSetNewVersion(File file) {
        int version = 0;
        File newestVersion = new LambdaQueryChainWrapper<>(fileMapper)
                .eq(File::getEnabled, Enabled.ENABLED)
                .eq(File::getName, file.getName())
                .eq(StringUtils.hasText(file.getProjectId()), File::getProjectId, file.getProjectId())
                .eq(StringUtils.hasText(file.getProjectName()), File::getProjectName, file.getProjectName())
                .eq(StringUtils.hasText(file.getBusinessId()), File::getBusinessId, file.getBusinessId())
                .eq(StringUtils.hasText(file.getBusinessName()), File::getBusinessName, file.getBusinessName())
                .eq(File::getFolderId, file.getFolderId())// 注意：一定是当前目录中的文件，不添加此条件会插入同名文件时影响其它目录的文件
                .orderByDesc(File::getVersion).last("limit 1").one();
        if (null != newestVersion) {
            version = newestVersion.getVersion() + 1;
            file.setVersion(version);
            if (newestVersion.getChanges() != null) {
                // addAll方法注意，当参数为null，会抛空指针，@Notnull注解
                file.getChanges().addAll(newestVersion.getChanges());
            }
            if (newestVersion.getProblems() != null) {
                file.getProblems().addAll(newestVersion.getProblems());
            }
            if (newestVersion.getComments() != null) {
                file.getComments().addAll(newestVersion.getComments());
            }
            file.getChanges().add("日期: " + LocalDateTime.now().format(dateTimeFormatter) + "更新了版本V" + file.getVersion());
        } else {
            file.setVersion(version);
        }
        //当前版本
        file.setCurrent(Current.TRUE);
    }

    @NotNull
    private Suffix checkAndGetSuffix(String name, Folder folder) {
        Assert.state(StringUtils.hasText(name) && name.contains("."), "请添加文件后缀.xxx");
        Suffix suffix = Suffix.getSuffix(name);
        Assert.notNull(suffix, "超出格式限制：" + Suffix.getSupportSuffix(folder.getType()).toString());
        boolean sameTypeFlag = false;
        for (Type type : suffix.getType()) {
            if (type == folder.getType()) {
                sameTypeFlag = true;
                break;
            }
        }
        Assert.state(sameTypeFlag, "目录类型和文件类型不匹配：" + Suffix.getSupportSuffix(folder.getType()).toString());
        return suffix;
    }

    private Folder checkAndGetFolder(Folder folderParam) {
        Folder folder;
        if (!StringUtils.hasText(folderParam.getId())) {
            List<Folder> root = folderService.findRoot(folderParam);
            folder = root.get(0);
        } else {
            folder = folderService.findOne(folderParam.getId());
        }
        Assert.notNull(folder, "未找到关联目录");
        return folder;
    }

    @CacheEvict(allEntries = true)
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public List<File> upload(List<MultipartFile> multipartFiles, String folderId) throws Exception {
        return this.upload(multipartFiles, Folder.of(folderId));
    }

    @CacheEvict(allEntries = true)
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public List<File> upload(List<MultipartFile> multipartFiles, Folder folder) throws Exception {
        Assert.state(null != multipartFiles && multipartFiles.size() > 0, "参数错误");
        List<File> files = new ArrayList<>(multipartFiles.size());
        for (MultipartFile multipartFile : multipartFiles) {
            File file = this.upload(multipartFile, folder, null);
            files.add(file);
        }
        return files;
    }

    @CacheEvict(allEntries = true)
    @Override
    @Transactional
    @Deprecated
    public int delete(File file) {
        Assert.notNull(file, "操作对象为null");
        QueryWrapper<File> qr = DbUtil.getAllEqQueryWraper(file);
        return fileMapper.delete(qr);
    }

    @CacheEvict(allEntries = true)
    @Override
    @Transactional
    public void changeStatus(String id, Enabled enabled) {
        Assert.state(StringUtils.hasText(id) && null != enabled, "参数错误");
        boolean update = new LambdaUpdateChainWrapper<>(fileMapper).eq(File::getId, id).set(File::getEnabled, enabled).update();
        if (!update) {
            throw new RuntimeException("文件" + enabled.getCnName() + "失败");
        }

        if (enabled == Enabled.DISABLED) {
            //目录子文件数--
            File file = this.findOne(id);
            folderService.refreshAllChild(file.getFolderId());// 刷新目录信息
        }
    }

    @Override
    // @Cacheable(key = "'findByFolder#' + #folderId")
    public List<File> findByFolder(String folderId) {
        Assert.state(StringUtils.hasText(folderId), "folderId不能为空");
        Folder folder = folderService.findOne(folderId);
        Assert.notNull(folder, "该目录不存在或已禁用");
        //List<File> files = fileMapper.findByFolder(folder.getId()); //旧版本，未加current字段时的聚合查询方法
        List<File> files = new LambdaQueryChainWrapper<>(fileMapper).eq(File::getEnabled, Enabled.ENABLED)
                .eq(File::getFolderId, folder.getId())
                .eq(File::getCurrent, Current.TRUE)
                .orderByDesc(File::getUpdateTime).list();
        return files;
    }

    @Override
    public List<File> findByParams(FileRequest fileRequest) {
        String folderId = fileRequest.getFolderId();
        Assert.state(StringUtils.hasText(folderId), "folderId不能为空");
        Folder folder = folderService.findOne(folderId);
        Assert.notNull(folder, "该目录不存在或已禁用");
        LambdaQueryWrapper<File> wp = Wrappers.lambdaQuery(File.class)
                .eq(File::getEnabled, Enabled.ENABLED)
                .eq(File::getFolderId, folder.getId())
                .eq(File::getCurrent, Current.TRUE)
                .orderByDesc(File::getUpdateTime);
        if (StringUtils.hasText(fileRequest.getId())) {
            wp.in(File::getId, Arrays.asList(fileRequest.getId().split(",")));
        }
        if (!CollectionUtils.isEmpty(fileRequest.getIds())) {
            wp.in(File::getId, fileRequest.getIds());
        }
        if (StringUtils.hasText(fileRequest.getName())) {
            wp.in(File::getName, Arrays.stream(fileRequest.getName().split(",")).map(FormatUtil::extractFileName).collect(Collectors.toList()));
        }
        if (!CollectionUtils.isEmpty(fileRequest.getNames())) {
            wp.in(File::getName, fileRequest.getNames().stream().map(FormatUtil::extractFileName).collect(Collectors.toList()));
        }
        // 区分全部列表和权限列表
        if (sysPermissionService.hasButtonPermission(ShiroUtil.getLoginUsername(), PermissionType.FULL.getPerm())) {
            return this.list(wp);
        }
        // 查询权限
        FolderUserPermission folderUserPermission = permissionService.queryPermission(Collections.singletonList(folderId)).get(0);
        // 如果个人的就仅看自己
        if (folderUserPermission == null || folderUserPermission.getDataPermissionType().equals(PermissionType.PERSONAL)) {
            wp.eq(File::getCreateBy, ShiroUtil.getLoginUsername());
        }
        return this.list(wp);
    }

    @Override
    @Cacheable(key = "'findPageByFolder#' + #folderId + #page + #pageSize")
    public Page<File> findPageByFolder(String folderId, int page, int pageSize) {
        Assert.state(StringUtils.hasText(folderId), "folderId不能为空");
        Folder folder = folderService.findOne(folderId);
        Assert.notNull(folder, "该目录不存在或已禁用");
        Page<File> files = new LambdaQueryChainWrapper<>(fileMapper).eq(File::getEnabled, Enabled.ENABLED)
                .eq(File::getFolderId, folder.getId())
                .eq(File::getCurrent, Current.TRUE)
                .orderByDesc(File::getUpdateTime).page(new Page<>(page, pageSize));
        return files;
    }

    @Override
    @Cacheable(key = "'findByFileNameAndFolder#' + #fileName +#folderId")
    public List<File> findByFileNameAndFolder(String fileName, String folderId) {
        Assert.state(StringUtils.hasText(fileName), "fileName不能为空");
        List<File> files = new LambdaQueryChainWrapper<>(fileMapper).eq(File::getEnabled, Enabled.ENABLED)
                .eq(File::getFolderId, folderId)
                .eq(File::getName, fileName)
                .eq(File::getCurrent, Current.TRUE)
                .orderByDesc(File::getUpdateTime).list();
        return files;
    }

    @Override
    public List<File> findAllVersion(String fileId, String oldName) {
        Assert.hasText(fileId, "文件id不能为空");
        File file = this.findOne(fileId);
        Assert.notNull(file, "找不到该文件信息");
        if (!StringUtils.hasText(oldName)) {
            oldName = file.getName();
        }
        List<File> files = new LambdaQueryChainWrapper<>(fileMapper)
                .eq(File::getEnabled, Enabled.ENABLED)
                .eq(File::getName, oldName)
                .eq(File::getType, file.getType())
                .eq(File::getSuffix, file.getSuffix())
                .eq(File::getFolderId, file.getFolderId())//同一个目录下的文件
                .orderByDesc(File::getVersion).list();
        return files;
    }

    @Override
    public List<File> findAllVersion(String fileId) {
        Assert.hasText(fileId, "文件id不能为空");
        File file = this.findOne(fileId);
        Assert.notNull(file, "找不到该文件信息");
        List<File> files = new LambdaQueryChainWrapper<>(fileMapper)
                .eq(File::getEnabled, Enabled.ENABLED)
                .eq(File::getName, file.getName())
                .eq(File::getType, file.getType())
                .eq(File::getSuffix, file.getSuffix())
                .eq(File::getFolderId, file.getFolderId())//同一个目录下的文件
                .orderByDesc(File::getVersion).list();
        if (files != null && files.size() > 0) {
            files = files.stream().filter(_file -> !_file.getId().equals(fileId)).collect(Collectors.toList());
        }
        return files;
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional
    public void reVersion(String fileId) {
        Assert.state(StringUtils.hasText(fileId), "文件id不能为空");
        File file = this.findOne(fileId);
        //更新选中版本：其余版本current-false，新插入的设为true
        boolean update = new LambdaUpdateChainWrapper<>(fileMapper).eq(File::getEnabled, Enabled.ENABLED)
                .eq(File::getName, file.getName())
                .eq(File::getFolderId, file.getFolderId())//同一个目录下的文件
                .eq(File::getCurrent, Current.TRUE)
                .set(File::getCurrent, Current.FALSE).update();
        Assert.state(update, "版本切换错误");
        file.setCurrent(Current.TRUE);
        fileMapper.updateById(file);
    }

    @Override
    @Cacheable(key = "#fileId")
    public File findOne(String fileId) {
        Assert.hasText(fileId, "文件id不能为空");
        return fileMapper.selectById(fileId);
    }

    @Override
    @Transactional
    @CacheEvict(allEntries = true)
    public void update(File file) {
        Assert.state(null != file && StringUtils.hasText(file.getId()), "文件不能为空");
        int i = fileMapper.updateById(file);
        Assert.state(i > 0, "文件更新失败");
    }

    @Override
    @Transactional
    @CacheEvict(allEntries = true)
    public void updateAllVersion(File file, String oldName) {
        List<File> allVersion = this.findAllVersion(file.getId(), oldName);
        allVersion.forEach(one -> {
            one.setName(file.getName());
            one.setProjectName(file.getProjectName());
            fileMapper.updateById(one);
        });
    }

    @Override
    @CacheEvict(allEntries = true)
    public void deleteAllVersion(String fileId, boolean logic) {
        if (logic) {
            this.deleteAllVersion(fileId);
        } else {
            Assert.hasText(fileId, "文件id不能为空");
            File file = this.findOne(fileId);
            if (null != file) {
                LambdaQueryWrapper<File> wrapper = new LambdaQueryWrapper<File>()
                        .eq(File::getName, file.getName())
                        .eq(File::getType, file.getType())
                        .eq(File::getSuffix, file.getSuffix())
                        .eq(File::getFolderId, file.getFolderId());
                int delete = this.fileMapper.delete(wrapper);
                if (delete > 0) {
                    log.info("所有版本删除成功,id: " + fileId);
                }
            } else {
                log.warn("所有版本删除异常,文件不存在: " + fileId);
            }
        }
    }

    @Override
    @CacheEvict(allEntries = true)
    public void deleteAllVersion(String fileId) {
        Assert.hasText(fileId, "文件id不能为空");
        File file = this.findOne(fileId);
        Assert.notNull(file, "找不到该文件信息");
        // 如果删除当前版本，就删了所有，否则只删除对应版本号
        LambdaUpdateChainWrapper<File> wp = new LambdaUpdateChainWrapper<>(fileMapper);
        if (file.getCurrent().equals(Current.TRUE)) {
            wp.eq(File::getName, file.getName())
                    .eq(File::getType, file.getType())
                    .eq(File::getSuffix, file.getSuffix())
                    .eq(File::getFolderId, file.getFolderId());// 同一个目录下的文件
        } else {
            wp.eq(File::getVersion, file.getVersion());
        }

        boolean update = wp.set(File::getEnabled, Enabled.DISABLED).update();
        folderService.refreshAllChild(file.getFolderId());// 刷新目录信息
        if (update) {
            log.info("所有版本删除成功");
        }
    }

    @CacheEvict(allEntries = true)
    @Override
    public void deleteAllByFolder(String folderId) {
        List<File> files = new LambdaQueryChainWrapper<>(fileMapper)
                .eq(File::getEnabled, Enabled.ENABLED)
                .eq(File::getFolderId, folderId).list();
        if (!files.isEmpty()) {
            files.forEach(file -> this.deleteAllVersion(file.getId()));
        }
    }

    @CacheEvict(allEntries = true)
    @Override
    public void deleteAllByFolder(String folderId, boolean logic) {
        List<File> files = new LambdaQueryChainWrapper<>(fileMapper)
                .eq(logic, File::getEnabled, Enabled.ENABLED)
                .eq(File::getCurrent, Current.TRUE)
                .eq(File::getFolderId, folderId).list();
        if (!files.isEmpty()) {
            files.forEach(file -> this.deleteAllVersion(file.getId(), logic));
        }
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional
    public void rename(String fileId, String name) {
        Assert.state(StringUtils.hasText(fileId) && StringUtils.hasText(name), "参数错误");
        File file = this.findOne(fileId);
        Assert.notNull(file, "找不到该文件信息");
        //处理文件和后缀
        String sourceName = file.getName();
        String suffix = sourceName.substring(sourceName.lastIndexOf("."), sourceName.length());
        if (!name.endsWith(suffix)) {
            name = name + suffix;
        }
        if (sourceName.equals(name)) {
            return;
        }
        //变更信息
        List<String> changes;
        if (null != file.getChanges()) {
            changes = new ArrayList<>(file.getChanges());
        } else {
            changes = new ArrayList<>();
        }
        changes.add("日期：" + LocalDateTime.now().format(dateTimeFormatter) + " 变更名称，原名称：" + sourceName + "，新名称：" + name);
        boolean update = new LambdaUpdateChainWrapper<>(fileMapper)
                .eq(File::getName, file.getName())
                .eq(File::getType, file.getType())
                .eq(File::getSuffix, file.getSuffix())
                .eq(File::getFolderId, file.getFolderId())// 同一个目录下的文件
                .set(File::getName, name) // 更新所有版本的文件名
                .set(File::getChanges, JSON.toJSONString(changes)) // 变更信息
                .update();
        folderService.refreshAllChild(file.getFolderId());// 刷新目录信息
        if (update) {
            log.info("文件重命名成功");
        }
    }

    @Override
    public Map<String, List<File>> queryTreeLastNodeFilesByFolderTreeNames(Folder folderParams, List<String> folderTreeNames) {
        List<Folder> folders = folderService.queryTreeLastNodeByFolderTreeNames(folderParams, folderTreeNames);
        if (null == folders || folders.size() == 0) {
            return new HashMap<>();
        }
        Map<String, List<File>> res = new HashMap<>(folders.size());
        Consumer<Folder> consumer = folder -> res.put(folder.getName(), this.findByFolder(folder.getId()));
        folders.forEach(consumer);
        return res;
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional
    public void reTags(String fileId, String tags) {
        Assert.state(StringUtils.hasText(fileId) && StringUtils.hasText(tags), "参数错误");
        File file = this.findOne(fileId);
        Assert.notNull(file, "找不到该文件信息");
        String sourceTags = file.getTags();
        //变更信息
        List<String> changes;
        if (null != file.getChanges()) {
            changes = new ArrayList<>(file.getChanges());
        } else {
            changes = new ArrayList<>();
        }
        changes.add("日期：" + LocalDateTime.now().format(dateTimeFormatter) + " 变更标签，原标签：" + sourceTags + "，新标签：" + tags);
        boolean update = new LambdaUpdateChainWrapper<>(fileMapper)
                .eq(File::getName, file.getName())
                .eq(File::getType, file.getType())
                .eq(File::getSuffix, file.getSuffix())
                .eq(File::getFolderId, file.getFolderId())// 同一个目录下的文件
                .set(File::getTags, tags) // 更新所有版本的标签
                .set(File::getChanges, JSON.toJSONString(changes)) // 变更信息
                .update();
        folderService.refreshAllChild(file.getFolderId());// 刷新目录信息
        if (update) {
            log.info("文件标签修改成功");
        }
    }

}
