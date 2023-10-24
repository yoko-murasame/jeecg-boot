package org.jeecg.modules.technical.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Level {
    /**
     * 目录层级
     */
    ROOT("根目录", "0", 0),
    FIRST("一级", "1", 1),
    SECOND("二级", "2", 2),
    THIRD("三级", "3", 3),
    FOURTH("四级", "4", 4),
    FIFTH("五级", "5", 5),
    SIXTH("六级", "6", 6);

    private String name;
    private String codeStr;
    @EnumValue
    private Integer code;

    public static Level child(Level level) {
        switch (level) {
            case ROOT:
                return FIRST;
            case FIRST:
                return SECOND;
            case SECOND:
                return THIRD;
            case THIRD:
                return FOURTH;
            case FOURTH:
                return FIFTH;
            case FIFTH:
                return SIXTH;
            default:
                return null;
        }
    }

    public static Level max() {
        return SIXTH;
    }

    public static Level min() {
        return ROOT;
    }
}
