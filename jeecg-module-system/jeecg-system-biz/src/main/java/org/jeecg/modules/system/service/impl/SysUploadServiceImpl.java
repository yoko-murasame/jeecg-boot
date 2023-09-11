package org.jeecg.modules.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.constant.SymbolConstant;
import org.jeecg.common.util.CommonUtils;
import org.jeecg.common.util.MinioUtil;
import org.jeecg.common.util.filter.FileTypeFilter;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.common.util.oss.OssBootUtil;
import org.jeecg.modules.system.entity.SysUpload;
import org.jeecg.modules.system.mapper.SysUploadMapper;
import org.jeecg.modules.system.service.ISysUploadService;
import org.jeecg.modules.system.util.UploadFileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * 重新实现文件上传
 * 可以参考common的文件上传实现 {@link org.jeecg.modules.system.controller.CommonController}
 *
 * @author Yoko
 * @date 2021/11/22 9:44
 */
@Slf4j
@Service
public class SysUploadServiceImpl extends ServiceImpl<SysUploadMapper, SysUpload> implements ISysUploadService {

    @Value(value = "${jeecg.path.upload}")
    private String uploadpath;

    /**
     * 本地：local 阿里：alioss
     */
    @Value(value = "${jeecg.uploadType}")
    private String uploadType;

    /**
     * 文件上传基础路径
     */
    @Value(value = "${jeecg.biz:}")
    private String biz;

    @Value(value = "${jeecg.projectName:chengfa}")
    private String projectName;

    private static final String DEFAULT_BIZ = "unsorted";
    private static final String TEMP_BIZ = "temp";

    public static final String FILE_SPLIT = "/";

    /**
     * 生成存储路径
     *
     * @param bizPath 业务路径名称
     * @return java.lang.String
     * @author Yoko
     * @since 2023/3/29 15:53
     */
    @Override
    public String generateBizPath(String bizPath) {
        String curBiz = bizPath;
        /**
         * 将当前日期作为基础路径，按照项目名称 -> 业务模块 -> 年月月份 划分区域
         */
        if (!StringUtils.hasText(curBiz) || TEMP_BIZ.equals(curBiz)) {
            curBiz = projectName + FILE_SPLIT + DEFAULT_BIZ + FILE_SPLIT + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM"));
        } else {
            curBiz = projectName + FILE_SPLIT + curBiz + FILE_SPLIT + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM"));
        }
        return curBiz;
    }

    @Override
    public SysUpload upload(MultipartFile multipartFile) throws Exception {
        return this.upload(multipartFile, this.biz);
    }

    @Override
    public SysUpload upload(MultipartFile multipartFile, String biz) throws Exception {
        String fileName = multipartFile.getOriginalFilename();
        fileName = CommonUtils.getFileName(fileName);

        // 扩展md5检测
        SysUpload sysUpload = null;
        String md5 = UploadFileUtil.calcMD5(multipartFile.getInputStream());
        sysUpload = this.queryByMd5(md5);
        if (sysUpload != null) {
            log.info("文件已存在,无需重复上传:{},md5:{}", fileName, md5);
            return sysUpload;
        }
        sysUpload = new SysUpload();
        sysUpload.setId(IdWorker.getIdStr());
        sysUpload.setMd5(md5);
        sysUpload.setFileName(fileName);

        biz = this.generateBizPath(biz);

        if(CommonConstant.UPLOAD_TYPE_LOCAL.equals(uploadType)){
            // 过滤上传文件类型
            FileTypeFilter.fileTypeFilter(multipartFile);
            // 本地文件上传需要捕获IOException
            String url = this.uploadLocal(multipartFile, biz);
            sysUpload.setUrl(url);
        } else {
            String url = CommonUtils.upload(multipartFile, biz, uploadType);
            sysUpload.setUrl(url);
            // 返回阿里云原生域名前缀URL
            // sysUpload.setUrl(OssBootUtil.getOriginalUrl(url));
        }
        this.save(sysUpload);
        return sysUpload;
    }

    @Override
    public SysUpload upload(InputStream is, String fileName, String biz) throws Exception {
        fileName = CommonUtils.getFileName(fileName);
        // 扩展md5检测
        SysUpload sysUpload = null;
        String md5 = UploadFileUtil.calcMD5(is);
        sysUpload = this.queryByMd5(md5);
        if (sysUpload != null) {
            log.info("文件已存在,无需重复上传:{},md5:{}", fileName, md5);
            return sysUpload;
        }
        sysUpload = new SysUpload();
        sysUpload.setId(IdWorker.getIdStr());
        sysUpload.setMd5(md5);
        sysUpload.setFileName(fileName);

        biz = this.generateBizPath(biz);

        if (CommonConstant.UPLOAD_TYPE_LOCAL.equals(uploadType)) {
            // 本地文件上传需要捕获IOException
            String url = this.uploadLocal(is, fileName, biz);
            sysUpload.setUrl(url);
        } else {
            String url = "";
            if (CommonConstant.UPLOAD_TYPE_MINIO.equals(uploadType)) {
                url = MinioUtil.upload(is, biz + FILE_SPLIT + fileName);
            } else {
                url = OssBootUtil.upload(is, biz + FILE_SPLIT + fileName);
                // 返回阿里云原生域名前缀URL
                // sysUpload.setUrl(OssBootUtil.getOriginalUrl(url));
            }
            sysUpload.setUrl(url);
        }
        this.save(sysUpload);
        return sysUpload;
    }

    @Override
    public boolean delete(SysUpload sysUpload) {
        try {
            this.removeById(sysUpload.getId());
            if (CommonConstant.UPLOAD_TYPE_OSS.equals(uploadType)) {
                OssBootUtil.deleteUrl(sysUpload.getUrl());
            }
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    @Override
    public List<SysUpload> uploadAll(MultipartFile[] multipartFiles, String uid) throws Exception {
        Assert.state(multipartFiles.length > 0, "待传文件数不能为0");
        List<SysUpload> sysUploads = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            SysUpload upload = this.upload(multipartFile);
            sysUploads.add(upload);
        }
        return sysUploads;
    }

    @Override
    public List<SysUpload> uploadAll(List<MultipartFile> multipartFiles, String uid) throws Exception {
        Assert.state(multipartFiles.size() > 0, "待传文件数不能为0");
        List<SysUpload> sysUploads = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            SysUpload upload = this.upload(multipartFile);
            sysUploads.add(upload);
        }
        return sysUploads;
    }


    @Override
    public SysUpload queryByMd5(String md5) {
        return this.baseMapper.queryByMd5(md5);
    }

    /**
     * 本地文件上传
     *
     * @param is 文件流
     * @param orgName 源文件名
     * @param bizPath 自定义路径
     * @return
     */
    private String uploadLocal(InputStream is, String orgName, String bizPath) throws IOException {
        try {
            String ctxPath = uploadpath;
            String fileName = null;
            File file = new File(ctxPath + FILE_SPLIT + bizPath + FILE_SPLIT);
            if (!file.exists()) {
                file.mkdirs();// 创建文件根目录
            }
            // 获取文件名
            orgName = CommonUtils.getFileName(orgName);
            if (orgName.contains(SymbolConstant.SPOT)) {
                fileName =
                        orgName.substring(0, orgName.lastIndexOf(".")) + "_" + System.currentTimeMillis() + orgName.substring(orgName.indexOf("."));
            } else {
                fileName = orgName + "_" + System.currentTimeMillis();
            }
            String savePath = file.getPath() + FILE_SPLIT + fileName;
            File savefile = new File(savePath);
            FileCopyUtils.copy(is, Files.newOutputStream(savefile.toPath()));
            String dbpath = null;
            if (oConvertUtils.isNotEmpty(bizPath)) {
                dbpath = bizPath + FILE_SPLIT + fileName;
            } else {
                dbpath = fileName;
            }
            if (dbpath.contains(SymbolConstant.DOUBLE_BACKSLASH)) {
                dbpath = dbpath.replace(SymbolConstant.DOUBLE_BACKSLASH, FILE_SPLIT);
            }
            return dbpath;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 本地文件上传
     *
     * @param mf 文件
     * @param bizPath 自定义路径
     * @return
     */
    private String uploadLocal(MultipartFile mf, String bizPath) throws IOException {
        try {
            String ctxPath = uploadpath;
            String fileName = null;
            File file = new File(ctxPath + FILE_SPLIT + bizPath + FILE_SPLIT);
            if (!file.exists()) {
                file.mkdirs();// 创建文件根目录
            }
            String orgName = mf.getOriginalFilename();// 获取文件名
            orgName = CommonUtils.getFileName(orgName);
            if (orgName.contains(".")) {
                fileName =
                        orgName.substring(0, orgName.lastIndexOf(".")) + "_" + System.currentTimeMillis() + orgName.substring(orgName.indexOf("."));
            } else {
                fileName = orgName + "_" + System.currentTimeMillis();
            }
            String savePath = file.getPath() + FILE_SPLIT + fileName;
            File savefile = new File(savePath);
            FileCopyUtils.copy(mf.getBytes(), savefile);
            String dbpath = null;
            if (oConvertUtils.isNotEmpty(bizPath)) {
                dbpath = bizPath + FILE_SPLIT + fileName;
            } else {
                dbpath = fileName;
            }
            if (dbpath.contains("\\")) {
                dbpath = dbpath.replace("\\", FILE_SPLIT);
            }
            return dbpath;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }
}
