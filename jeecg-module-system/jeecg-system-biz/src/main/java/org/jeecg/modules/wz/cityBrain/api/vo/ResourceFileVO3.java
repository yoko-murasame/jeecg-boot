package org.jeecg.modules.wz.cityBrain.api.vo;

import lombok.Data;
import org.jeecg.modules.wz.cityBrain.api.entity.ResourceFile;


/**
 * @Author: -Circle
 * @Date: 2021/10/26 11:38
 * @Description:
 */
@Data
public class ResourceFileVO3 {
    private String fileName;
    private String filePath;

    public ResourceFileVO3(ResourceFile resourceFile) {
        this.fileName = resourceFile.getFileName();
        this.filePath = resourceFile.getFilePath();
    }
}
