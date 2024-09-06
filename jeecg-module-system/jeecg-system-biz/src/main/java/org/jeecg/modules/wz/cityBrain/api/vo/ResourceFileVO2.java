package org.jeecg.modules.wz.cityBrain.api.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: -Circle
 * @Date: 2021/10/11 9:58
 * @Description: 资源文件 部分字段VO
 */
@Data
public class ResourceFileVO2 {
    private List<ResourceFileVO3> menu;
    private List<ResourceFileVO3> legend;
    private List<ResourceFileVO3> point;
    private List<ResourceFileVO3> bgImg;
    private List<ResourceFileVO3> dataImg;

    public ResourceFileVO2() {
        this.menu = new ArrayList<>();
        this.legend = new ArrayList<>();
        this.point = new ArrayList<>();
        this.bgImg = new ArrayList<>();
        this.dataImg = new ArrayList<>();
    }
}
