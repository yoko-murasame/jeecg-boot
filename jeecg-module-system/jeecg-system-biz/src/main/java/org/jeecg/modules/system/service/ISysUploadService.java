package org.jeecg.modules.system.service;


import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.system.entity.SysUpload;
import org.jeecg.modules.system.vo.OssToLocalVo;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

public interface ISysUploadService extends IService<SysUpload> {

    String generateBizPath(String bizPath);

    SysUpload upload(MultipartFile multipartFile) throws Exception;

    SysUpload upload(MultipartFile multipartFile, String biz) throws Exception;

    SysUpload upload(InputStream is, String fileName, String biz) throws Exception;

    boolean delete(SysUpload ossFile);

    List<SysUpload> uploadAll(MultipartFile[] multipartFiles, String uid) throws Exception;

    List<SysUpload> uploadAll(List<MultipartFile> multipartFiles, String uid) throws Exception;

    SysUpload queryByMd5(String md5);

    @Async
    void transferOssToLocal(List<OssToLocalVo> vos,
                            String endpoint,
                            String accessKeyId,
                            String accessKeySecret,
                            String bucketName);

    @Async
    void transferOssToLocal(List<OssToLocalVo> vos);
}
