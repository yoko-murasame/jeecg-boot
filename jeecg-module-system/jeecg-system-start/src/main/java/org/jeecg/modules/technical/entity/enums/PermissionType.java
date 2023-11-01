package org.jeecg.modules.technical.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PermissionType {

    PERSONAL("0", "个人权限", "KNOWLEDGE_FOLDER_USER_PERSONAL"),
    FULL("1", "全部权限", "KNOWLEDGE_FOLDER_USER_FULL");

    @EnumValue
    private final String value;
    private final String text;
    private final String perm;
}
