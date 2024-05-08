package cn.com.hyit.config;

import cn.com.hyit.config.exception.Estate401Exception;
import cn.com.hyit.config.exception.EstateException;
import cn.com.hyit.config.exception.SqlInjectionException;
import cn.com.hyit.core.web.response.BaseRest;
import cn.com.hyit.core.web.response.ResponseCode;
import cn.com.hyit.core.web.response.WebResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.jeecg.common.exception.JeecgBootExceptionHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.connection.PoolException;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * 异常处理器
 */
@RestControllerAdvice
@ConditionalOnMissingBean(JeecgBootExceptionHandler.class) // todo 待删
@Slf4j
public class EstateExceptionHandler extends BaseRest {

    public EstateExceptionHandler() {
        this.setDebuggerType("true");
        log.info("==========加载 EstateExceptionHandler ===========");
    }

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(EstateException.class)
    public WebResponse<?> handleEstateException(EstateException e) {
        log.error(e.getMessage(), e);
        return buildFailedResponse(ResponseCode.EXCEPTION.getCode(), e.getMessage());
    }

    @ExceptionHandler(Estate401Exception.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public WebResponse<?> handleEstate401Exception(Estate401Exception e) {
        log.error(e.getMessage(), e);
        return buildFailedResponse(ResponseCode.NO_AUTH.getCode(), e.getMessage());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public WebResponse<?> handlerNoFoundException(Exception e) {
        log.error(e.getMessage(), e);
        return buildFailedResponse(ResponseCode.EXCEPTION.getCode(), "路径不存在，请检查路径是否正确");
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public WebResponse<?> handleDuplicateKeyException(DuplicateKeyException e) {
        log.error(e.getMessage(), e);
        return buildFailedResponse(ResponseCode.EXCEPTION.getCode(), "数据库中已存在该记录");
    }

    @ExceptionHandler({UnauthorizedException.class, AuthorizationException.class})
    public WebResponse<?> handleAuthorizationException(AuthorizationException e) {
        log.error(e.getMessage(), e);
        return buildFailedResponse(ResponseCode.NO_AUTH.getCode(), "没有权限，请联系管理员授权");
    }

    @ExceptionHandler(Exception.class)
    public WebResponse<?> handleException(Exception e) {
        log.error(e.getMessage(), e);
        return buildFailedResponse(ResponseCode.EXCEPTION.getCode(), "操作失败，" + e.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public WebResponse<?> httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        StringBuilder sb = new StringBuilder();
        sb.append("不支持");
        sb.append(e.getMethod());
        sb.append("请求方法，");
        sb.append("支持以下");
        String[] methods = e.getSupportedMethods();
        if (methods != null) {
            for (String str : methods) {
                sb.append(str);
                sb.append("、");
            }
        }
        log.error(sb.toString(), e);
        return buildFailedResponse("405", sb.toString());
    }

    /**
     * spring默认上传大小100MB 超出大小捕获异常MaxUploadSizeExceededException
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public WebResponse<?> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        log.error(e.getMessage(), e);
        return buildFailedResponse(ResponseCode.EXCEPTION.getCode(), "文件大小超出100MB限制, 请压缩或降低文件质量! ");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public WebResponse<?> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        log.error(e.getMessage(), e);
        return buildFailedResponse(ResponseCode.EXCEPTION.getCode(), "执行数据库异常,违反了完整性例如：违反惟一约束、违反非空限制、字段内容超出长度等");
    }

    @ExceptionHandler(PoolException.class)
    public WebResponse<?> handlePoolException(PoolException e) {
        log.error(e.getMessage(), e);
        return buildFailedResponse(ResponseCode.EXCEPTION.getCode(), "Redis 连接异常!");
    }


    /**
     * SQL注入风险，全局异常处理
     */
    @ExceptionHandler(SqlInjectionException.class)
    public WebResponse<?> handleSQLException(Exception exception) {
        String msg = exception.getMessage().toLowerCase();
        final String extractvalue = "extractvalue";
        final String updatexml = "updatexml";
        boolean hasSensitiveInformation = msg.indexOf(extractvalue) >= 0 || msg.indexOf(updatexml) >= 0;
        if (msg != null && hasSensitiveInformation) {
            log.error("校验失败，存在SQL注入风险！{}", msg);
            return buildFailedResponse(ResponseCode.EXCEPTION.getCode(), "校验失败，存在SQL注入风险！");
        }
        return buildFailedResponse(ResponseCode.EXCEPTION.getCode(), "校验失败，存在SQL注入风险！" + msg);
    }

}
