package org.jeecg.modules.system.job;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.system.service.ISysUploadService;
import org.jeecg.modules.system.vo.OssToLocalVo;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 转换OSS文件到本地-定时任务
 */
@Slf4j
@Component
public class TransferOssToLocalJob implements Job {

    @Autowired
    private ISysUploadService service;

    @Setter
    private String parameter;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        try {
            List<OssToLocalVo> vos = JSON.parseObject(parameter, new TypeReference<List<OssToLocalVo>>() {});
            service.transferOssToLocal(vos);
        } catch (Exception e) {
            throw new RuntimeException("定时任务参数异常" + e.getMessage());
        }
    }

}
