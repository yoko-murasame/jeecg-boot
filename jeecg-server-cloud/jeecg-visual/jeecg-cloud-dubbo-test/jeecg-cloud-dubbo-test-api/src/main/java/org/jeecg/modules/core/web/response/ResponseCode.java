package org.jeecg.modules.core.web.response;
/**
 * 表控制层-ResponseCode
 * FIXME 仅供参考，实际项目中应该使用hyit-core包中的ResponseCode
 */
public enum ResponseCode {
    SUCCESS("200", "成功"),
    NO_DATA("1001", "无数据"),
    NO_AUTH("1002", "无操作权限"),
    NO_ADMIN_USER("1003", "无该用户信息"),
    NO_ADMIN_ROLE("1004", "无角色信息"),
    EXCEPTION("1005", "系统异常"),
    PARAM_EXCEPTION("1006", "传入参数有误"),
    INVALD_TOKEN("0000002", "无效的token"),
    INDEX_NULL("001", "索引值不能为空"),
    INDEX_EMPTY("002", "索引值不存在"),
    PORTAL_LOGOUT_FAILED("40105_logout_001", "退出失败"),
    PORTAL_LOGIN_USER_PASSWORD_FILED("40105_user_password_002", "用户名密码错误"),
    PORTAL_LOGIN_REFRESHTOKEN_FILED("0000001", "用户名密码错误"),
    SYSTEM_FAILED("500_inner_fail", "内部服务异常！");

    private String code;
    private String message;

    private ResponseCode(final String code, final String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}
