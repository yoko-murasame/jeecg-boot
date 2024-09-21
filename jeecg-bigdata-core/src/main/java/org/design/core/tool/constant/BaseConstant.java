package org.design.core.tool.constant;

/* loaded from: bigdata-core-tool-3.1.0.jar:org/design/core/tool/constant/BaseConstant.class */
public interface BaseConstant {
    public static final String UTF_8 = "UTF-8";
    public static final String CONTENT_TYPE_NAME = "Content-type";
    public static final String CONTENT_TYPE = "application/json;charset=utf-8";
    public static final String SECURITY_ROLE_PREFIX = "ROLE_";
    public static final String DB_PRIMARY_KEY = "id";
    public static final int DB_STATUS_NORMAL = 1;
    public static final int DB_NOT_DELETED = 0;
    public static final int DB_IS_DELETED = 1;
    public static final int DB_ADMIN_NON_LOCKED = 0;
    public static final int DB_ADMIN_LOCKED = 1;
    public static final String ADMIN_TENANT_ID = "000000";
    public static final String LOG_NORMAL_TYPE = "1";
    public static final String DEFAULT_NULL_MESSAGE = "暂无承载数据";
    public static final String DEFAULT_SUCCESS_MESSAGE = "操作成功";
    public static final String DEFAULT_FAILURE_MESSAGE = "操作失败";
    public static final String DEFAULT_UNAUTHORIZED_MESSAGE = "签名认证失败";
}
