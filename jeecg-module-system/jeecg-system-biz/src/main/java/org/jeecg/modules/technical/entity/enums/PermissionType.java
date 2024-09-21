package org.jeecg.modules.technical.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PermissionType {

    /**
     * 知识库-目录-个人查看用户权限
     * 默认值，不需要设置
     */
    PERSONAL("0", "个人权限", "KNOWLEDGE_FOLDER_USER_PERSONAL"),
    /**
     * 知识库-目录-全目录查看用户权限
     * 一般给admin用户使用
     * 会影响目录查询列表的展示数据
     */
    FULL("1", "全部权限", "KNOWLEDGE_FOLDER_USER_FULL");

    @EnumValue
    private final String value;
    private final String text;
    private final String perm;
}
