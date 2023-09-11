package org.jeecg.modules.technical.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Operation {
    PLUS(1,"+"){
        @Override
        public Integer calculate(Integer source, Integer value) {
            return source + value;
        }
    },
    SUB(2,"-"){
        @Override
        public Integer calculate(Integer source, Integer value) {
            int res = source - value;
            return res > -1 ? res : 0;
        }
    };
    private Integer code;
    private String operate;

    public abstract Integer calculate(Integer source,Integer value);
}
