package org.jeecg.modules.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.util.concurrent.AtomicDouble;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.dto.message.MessageDTO;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.constant.SymbolConstant;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.util.CommonUtils;
import org.jeecg.common.util.MinioUtil;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.common.util.filter.FileTypeFilter;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.common.util.oss.OssBootUtil;
import org.jeecg.common.util.yoko.FormatUtil;
import org.jeecg.modules.system.entity.SysUpload;
import org.jeecg.modules.system.mapper.SysUploadMapper;
import org.jeecg.modules.system.service.ISysUploadService;
import org.jeecg.modules.system.util.UploadFileUtil;
import org.jeecg.modules.system.vo.OssToLocalVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.jeecg.modules.system.vo.OssToLocalVo.*;

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

    @Value(value = "${jeecg.projectName:new_project}")
    private String projectName;

    private static final String DEFAULT_BIZ = "unsorted";
    private static final String TEMP_BIZ = "temp";

    public static final String FILE_SPLIT = "/";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ISysBaseAPI sysBaseAPI;

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
            /**
             * 直接的inputStream，在经过md5计算后，会导致流关闭，所以需要重置
             * 而来自 multipartFile.getInputStream() 每次调用会重新获取流，所以不需要重置
             * 后续如果有文件流调用此接口产生的写入异常，可以优先考虑这个情况。
             */
            is.reset();
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

    @Async
    @Override
    public void transferOssToLocal(List<OssToLocalVo> vos,
                                   String endpoint,
                                   String accessKeyId,
                                   String accessKeySecret,
                                   String bucketName) {

        List<String> logs = new ArrayList<>();

        for (int i = 0; i < vos.size(); i++) {
            OssToLocalVo vo = vos.get(i);

            // 每个任务可能有自己的oss连接
            endpoint = Optional.ofNullable(vo.getEndpoint()).orElse(endpoint);
            accessKeyId = Optional.ofNullable(vo.getAccessKeyId()).orElse(accessKeyId);
            accessKeySecret = Optional.ofNullable(vo.getAccessKeySecret()).orElse(accessKeySecret);
            bucketName = Optional.ofNullable(vo.getBucketName()).orElse(bucketName);
            OSSClient ossClient = null;

            String startMsg = String.format("transferOssToLocal::===================第 %s 个任务开始执行===================", i + 1);
            log.info(startMsg);
            logs.add(startMsg);
            String paramMsg = String.format("transferOssToLocal::任务参数：%s", JSON.toJSONString(vo));
            log.info(paramMsg);
            logs.add(paramMsg);
            long startTotal = System.currentTimeMillis();

            AtomicDouble costBytes = new AtomicDouble(0);

            String tableName = vo.getTableName();
            String idField = vo.getIdField();
            List<String> fields = vo.getFields();
            List<String> fieldTypes = vo.getFieldTypes();
            List<String> jsonPaths = vo.getJsonPaths();
            Integer page = vo.getPage();
            Integer pageSize = vo.getPageSize();
            double limitBytes = vo.getLimitMb() * 1024 * 1024;
            String bizPath = this.generateBizPath(vo.getBizPath());
            String splitChar = vo.getSplitChar();

            String listSql = String.format("SELECT %s FROM %s WHERE",
                    String.join(SPLIT_CHAR_DEFAULT, idField, fields.stream().map(e -> e + "::text").collect(Collectors.joining(SPLIT_CHAR_DEFAULT))), tableName);

            // 根据fields拼接 where 模糊查询
            listSql += fields.stream().map(field -> String.format(" %s::text LIKE '%%http%%'", field)).reduce((a, b) -> a + " OR " + b).orElse(" 1 = 1");
            // 拼接分页
            if (page > 0 && pageSize > 0) {
                listSql += String.format(" LIMIT %s OFFSET %s", pageSize, (page - 1) * pageSize);
            }

            String queryMsg = String.format("transferOssToLocal::查询SQL：%s", listSql);
            log.info(queryMsg);
            logs.add(queryMsg);

            try {
                // 异常放这里
                Assert.hasText(endpoint, "endpoint不能为空");
                Assert.hasText(accessKeyId, "accessKeyId不能为空");
                Assert.hasText(accessKeySecret, "accessKeySecret不能为空");
                Assert.hasText(bucketName, "bucketName不能为空");
                ossClient = new OSSClient(endpoint,
                        new DefaultCredentialProvider(accessKeyId, accessKeySecret),
                        new ClientConfiguration());
                String finalBucketName = bucketName;
                OSSClient finalOssClient = ossClient;
                // 查询结果
                jdbcTemplate.queryForList(listSql).forEach(row -> {
                    String id = (String) row.get(idField);
                    // 处理每个字段
                    IntStream.range(0, fields.size()).forEach(idx -> {
                        // 超出处理限制大小时，直接结束任务
                        if (costBytes.get() > limitBytes) {
                            logs.add("==================OSS文件====================");
                            String msg = String.format("transferOssToLocal::超出处理限制大小，直接结束任务，当前已处理：%.2f MB，流量限制：%.2f MB", costBytes.get() / 1024 / 1024, limitBytes / 1024 / 1024);
                            log.info(msg);
                            logs.add(msg);
                            throw new RuntimeException(msg);
                        }
                        String field = fields.get(idx);
                        String type = fieldTypes.get(idx);
                        String fieldValue = (String) row.get(field);
                        if (StringUtils.isEmpty(fieldValue) || StringUtils.isEmpty(fieldValue.trim())) {
                            return;
                        }
                        String updateSql = null;
                        List<String> transferredUrls;
                        logs.add("==================OSS文件====================");
                        switch (type) {
                            case VARCHAR:
                                // url可能是逗号分隔的数组
                                transferredUrls = doTransfer(fieldValue, logs, finalOssClient, finalBucketName, costBytes, limitBytes, bizPath, splitChar);
                                // 以字段为基准更新数据
                                updateSql = String.format("UPDATE %s SET %s = '%s' WHERE %s = '%s'",
                                        tableName, field, String.join(splitChar, transferredUrls), idField, id);
                                break;
                            case JSON_TYPE:
                                // 取出实际字段值
                                String jsonPath = jsonPaths.get(idx);
                                String realValue = Arrays.stream(jsonPath.split(SPLIT_CHAR_DEFAULT)).reduce(fieldValue, (a, b) -> {
                                    try {
                                        JSONObject aJson = JSON.parseObject(a);
                                        Object bJson = aJson.get(b);
                                        return bJson.getClass().getTypeName().equals("java.lang.String") ? (String) bJson : JSON.toJSONString(bJson);
                                    } catch (Exception e) {
                                        return a;
                                    }
                                }, (a, b) -> a);
                                String jsonMsg = String.format("transferOssToLocal::处理JSON数据::取值路径: %s，实际取值: %s", jsonPath, realValue);
                                log.info(jsonMsg);
                                logs.add(jsonMsg);
                                List<String> transferredValues = doTransfer(realValue, logs, finalOssClient, finalBucketName, costBytes, limitBytes, bizPath, splitChar);
                                // 以字段为基准更新数据
                                updateSql = String.format("UPDATE %s SET %s = '%s' WHERE %s = '%s'",
                                        tableName, field, fieldValue.replace(realValue, String.join(splitChar, transferredValues)), idField, id);
                                fieldValue = realValue;
                                break;
                            default:
                                throw new RuntimeException("暂不支持的字段类型：" + type + "，目前已支持类型：varchar、json");
                        }
                        String updateMsg = String.format("transferOssToLocal::更新SQL：%s", updateSql);
                        log.info(updateMsg);
                        logs.add(updateMsg);
                        int update = jdbcTemplate.update(updateSql);
                        // 删除oss文件
                        if (update > 0 && vo.getDeleteSource()) {
                            for (String objectName : fieldValue.split(splitChar)) {
                                finalOssClient.deleteObject(finalBucketName, objectName);
                            }
                        }
                    });
                });
            } catch (Exception e) {
                String errorMsg = String.format("transferOssToLocal::第 %s 个任务已终止，原因：%s", i + 1, e.getMessage());
                log.error(errorMsg);
                logs.add(errorMsg);
                // 任务执行终止
                // break;
            } finally {
                long endTotal = System.currentTimeMillis();
                long timeTotal = endTotal - startTotal;
                String endMsg = String.format("transferOssToLocal::===================第 %s 个任务执行完毕=================== 总耗时：%s<br/><br/><br/>", i + 1, FormatUtil.formatTimeDuration(timeTotal / 1000));
                log.info(endMsg);
                logs.add(endMsg);
                // 关闭ossClient
                ossClient.shutdown();
            }
        }

        // 系统日志
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setTitle("系统提示");
        messageDTO.setFromUser("system");
        messageDTO.setToUser("admin");
        messageDTO.setCategory(CommonConstant.MSG_CATEGORY_2);
        messageDTO.setContent(String.join("<br/>", logs));
        sysBaseAPI.sendSysAnnouncement(messageDTO);
    }

    private List<String> doTransfer(String urls, List<String> logs, OSSClient finalOssClient, String finalBucketName, AtomicDouble costBytes, double limitBytes, String bizPath, String splitChar) {
        if (!StringUtils.hasText(urls)) {
            return Collections.emptyList();
        }
        String[] urlArray = urls.split(splitChar);
        List<String> transferredUrls = new ArrayList<>(urlArray.length);

        for (String url : urlArray) {
            if (!url.contains("http")) {
                // 保持一致的顺序
                transferredUrls.add(url);
                continue;
            }
            String fileName = url.substring(url.lastIndexOf("/") + 1).replaceAll("_\\d*?\\.", ".");

            try {
                URL parsedUrl = new URL(url);
                String objectName = parsedUrl.getPath().substring(1);
                String fileNameMsg = String.format("transferOssToLocal::OSS文件名称: %s", objectName);
                log.info(fileNameMsg);
                logs.add(fileNameMsg);
                ObjectMetadata objectMetadata = null;
                try {
                    objectMetadata = finalOssClient.getObjectMetadata(finalBucketName, objectName);
                } catch (Exception e) {
                    String errorMsg = String.format("transferOssToLocal::OSS文件元信息获取失败，objectName: %s，原因：%s", objectName, e.getMessage());
                    log.error(errorMsg);
                    logs.add(errorMsg);
                }
                if (objectMetadata != null) {
                    // 获取的大小为字节（官方文档说明）
                    double contentLength = objectMetadata.getContentLength();
                    costBytes.addAndGet(contentLength);
                    String costMsg = String.format("transferOssToLocal::OSS文件大小：%.2f MB，累计消耗：%.2f MB，流量限制：%.2f MB，剩余：%.2f MB",
                            contentLength / 1024 / 1024, costBytes.get() / 1024 / 1024, limitBytes / 1024 / 1024, (limitBytes - costBytes.get()) / 1024 / 1024);
                    // log.info("transferOssToLocal::OSS文件大小：{} Bytes，累计消耗：{} Bytes，上限：{} Bytes，剩余：{} Bytes", contentLength, costBytes.get(), limitBytes, limitBytes - costBytes.get());
                    log.info(costMsg);
                    logs.add(costMsg);
                    // 保存到本地
                    OSSObject ossObject = finalOssClient.getObject(finalBucketName, objectName);
                    BufferedInputStream is = new BufferedInputStream(ossObject.getObjectContent());
                    String savePath = this.uploadLocal(is, fileName, bizPath);
                    String saveMsg = String.format("transferOssToLocal::本地文件保存路径：%s", savePath);
                    log.info(saveMsg);
                    logs.add(saveMsg);
                    transferredUrls.add(savePath);
                } else {
                    // 保持一致的顺序
                    transferredUrls.add(url);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return transferredUrls;
    }

    /**
     * 转换oss文件到本地
     *
     * <a href="https://help.aliyun.com/zh/oss/developer-reference/manage-object-metadata-2#section-2uf-52u-nx5">oss文档</a>
     *
     * @param vos 转换oss文件到本地请求实体
     * @author Yoko
     * @since 2023/10/12 10:12
     */
    @Async
    @Override
    public void transferOssToLocal(List<OssToLocalVo> vos) {
        // 从application配置读取
        Environment env = SpringContextUtils.getBean(Environment.class);
        String endpoint = env.getProperty("jeecg.oss.endpoint");
        String accessKeyId = env.getProperty("jeecg.oss.accessKey");
        String accessKeySecret = env.getProperty("jeecg.oss.secretKey");
        String bucketName = env.getProperty("jeecg.oss.bucketName");
        this.transferOssToLocal(vos, endpoint, accessKeyId, accessKeySecret, bucketName);
    }

}
