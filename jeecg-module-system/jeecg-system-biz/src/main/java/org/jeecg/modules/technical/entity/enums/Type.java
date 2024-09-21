package org.jeecg.modules.technical.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.util.StringUtils;

@Getter
@AllArgsConstructor
public enum Type {

    BLUEPRINT("blueprint", "CAD图纸", 1),
    MODEL("model", "BIM模型", 2),
    DOCUMENT("document", "文档", 3),
    PICTURE("picture", "图片", 4),
    VIDEO("video", "视频", 5);

    private String enName;
    private String cnName;
    @EnumValue
    private Integer code;

    public static Type of(String enName) {
        if (StringUtils.isEmpty(enName)) {
            return null;
        }
        for (Type type : Type.values()) {
            if (type.enName.equals(enName)) {
                return type;
            }
        }
        return null;
    }

}
