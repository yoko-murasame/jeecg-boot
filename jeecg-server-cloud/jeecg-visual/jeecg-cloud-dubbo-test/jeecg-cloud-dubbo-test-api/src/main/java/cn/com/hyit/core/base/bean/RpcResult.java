package cn.com.hyit.core.base.bean;

import lombok.Data;

import java.io.Serializable;

/**
* RPC结果实体-RpcResult
* FIXME 仅供参考，实际项目中应该使用hyit-core包中的Result
*/
@Data
public class RpcResult<T> implements Serializable {
    private static final long serialVersionUID = 5851655962866773848L;
    private String code;
    private T data;
    private String message;
    private Boolean success;

    public static <T> RpcResult<T> create() {
        return new RpcResult<>();
    }

    public RpcResult<T> buildSuccess(T t, String code, String message) {
        RpcResult<T> rpcResult = new RpcResult<>();
        rpcResult.setData(t);
        rpcResult.setSuccess(Boolean.TRUE);
        rpcResult.setCode(code);
        rpcResult.setMessage(message);
        return rpcResult;
    }

    public RpcResult<T> buildSuccess(T t) {
        RpcResult<T> rpcResult = new RpcResult<>();
        rpcResult.setData(t);
        rpcResult.setSuccess(Boolean.TRUE);
        return rpcResult;
    }

    public RpcResult<T> buildSuccess() {
        RpcResult<T> rpcResult = new RpcResult<>();
        rpcResult.setSuccess(Boolean.TRUE);
        return rpcResult;
    }

    public RpcResult<T> buildFalse(T t, String code, String message) {
        RpcResult<T> rpcResult = new RpcResult<>();
        rpcResult.setData(t);
        rpcResult.setSuccess(Boolean.FALSE);
        rpcResult.setCode(code);
        rpcResult.setMessage(message);
        return rpcResult;
    }

    public RpcResult<T> buildFalse(String code, String message) {
        RpcResult<T> rpcResult = new RpcResult<>();
        rpcResult.setSuccess(Boolean.FALSE);
        rpcResult.setCode(code);
        rpcResult.setMessage(message);
        return rpcResult;
    }
}
