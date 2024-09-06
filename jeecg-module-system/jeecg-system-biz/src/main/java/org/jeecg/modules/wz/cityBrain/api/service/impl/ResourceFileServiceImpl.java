
package org.jeecg.modules.wz.cityBrain.api.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.jeecg.modules.wz.business.base.BaseServiceImpl;
import org.jeecg.modules.wz.cityBrain.api.entity.ResourceFile;
import org.jeecg.modules.wz.cityBrain.api.mapper.ResourceFileMapper;
import org.jeecg.modules.wz.cityBrain.api.service.IResourceFileService;
import org.jeecg.modules.wz.cityBrain.api.vo.ResourceFileVO2;
import org.jeecg.modules.wz.cityBrain.api.vo.ResourceFileVO3;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 资源文件
 *
 * @author Circle
 * @since 2021-09-30
 */
@Service
public class ResourceFileServiceImpl extends BaseServiceImpl<ResourceFileMapper, ResourceFile> implements IResourceFileService {


    @Override
    public List<ResourceFile> getByLayerId(ResourceFile resourceFile) {
        return list(Wrappers.lambdaQuery(resourceFile));
    }

    @Override
    public Map<Long, ResourceFileVO2> resourceIdMapFile(List<Long> ids) {
        List<ResourceFile> resourceFiles = list(Wrappers.<ResourceFile>lambdaQuery().in(ResourceFile::getTableId, ids));
        Map<Long, ResourceFileVO2> map = new HashMap<>();

        for (ResourceFile file : resourceFiles) {
            ResourceFileVO2 resourceFileVO2 = map.get(file.getTableId());
            if (null == resourceFileVO2) {
                resourceFileVO2 = new ResourceFileVO2();
            }
            if ("菜单图标".equals(file.getFileType())) {
                resourceFileVO2.getMenu().add(new ResourceFileVO3(file));
            } else if ("图例图标".equals(file.getFileType())) {
                resourceFileVO2.getLegend().add(new ResourceFileVO3(file));
            } else if ("点位图标".equals(file.getFileType())) {
                resourceFileVO2.getPoint().add(new ResourceFileVO3(file));
            }else if ("背景图片".equals(file.getFileType())) {
                resourceFileVO2.getBgImg().add(new ResourceFileVO3(file));
            }else if ("数据图片".equals(file.getFileType())) {
                resourceFileVO2.getDataImg().add(new ResourceFileVO3(file));
            }
            map.put(file.getTableId(), resourceFileVO2);
        }
        return map;
    }
}
