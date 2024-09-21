package org.design.core.tool.api;

import org.design.core.tool.constant.BaseConstant;

/* loaded from: bigdata-core-tool-3.1.0.jar:org/design/core/tool/api/ResultCode.class */
public enum ResultCode implements IResultCode {
    SUCCESS(200, BaseConstant.DEFAULT_SUCCESS_MESSAGE),
    FAILURE(400, "业务异常"),
    UN_AUTHORIZED(401, "请求未授权"),
    NOT_FOUND(404, "404 没找到请求"),
    MSG_NOT_READABLE(400, "消息不能读取"),
    METHOD_NOT_SUPPORTED(405, "不支持当前请求方法"),
    MEDIA_TYPE_NOT_SUPPORTED(415, "不支持当前媒体类型"),
    REQ_REJECT(403, "请求被拒绝"),
    INTERNAL_SERVER_ERROR(500, "服务器异常"),
    PARAM_MISS(400, "缺少必要的请求参数"),
    PARAM_TYPE_ERROR(400, "请求参数类型错误"),
    PARAM_BIND_ERROR(400, "请求参数绑定错误"),
    PARAM_VALID_ERROR(400, "参数校验失败");
    
    final int code;
    final String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override // org.design.core.tool.api.IResultCode
    public int getCode() {
        return this.code;
    }

    @Override // org.design.core.tool.api.IResultCode
    public String getMessage() {
        return this.message;
    }
}
