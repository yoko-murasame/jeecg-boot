package org.jeecg.modules.technical.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Current {

    TRUE("true", "是", 1),
    FALSE("false", "否", 0);

    private String enName;
    private String cnName;
    @EnumValue
    private Integer code;
}
