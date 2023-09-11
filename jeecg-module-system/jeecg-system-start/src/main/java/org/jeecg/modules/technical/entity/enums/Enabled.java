package org.jeecg.modules.technical.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Enabled {

    ENABLED("enabled", "启用", 1),
    DISABLED("disabled", "禁用", 0);

    private String enName;
    private String cnName;
    @EnumValue
    private Integer code;
}
