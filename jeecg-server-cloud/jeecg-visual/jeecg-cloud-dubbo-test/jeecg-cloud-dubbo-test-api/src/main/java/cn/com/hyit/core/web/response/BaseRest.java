package cn.com.hyit.core.web.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 表控制层-基础类
 * FIXME 仅供参考，实际项目中应该使用hyit-core包中的BaseRest
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "system.run")
@AllArgsConstructor
@NoArgsConstructor
public class BaseRest {
    private String debuggerType;
    private String errInfo;

    public WebResponse<Void> buildSuccessResponse() {
        WebResponse<Void> webResponse = new WebResponse<>();
        webResponse.setMessage(ResponseCode.SUCCESS.getMessage());
        webResponse.setStatusCode(ResponseCode.SUCCESS.getCode());
        return webResponse;
    }

    public <T> WebResponse<T> buildSuccessResponse(T data) {
        WebResponse<T> webResponse = new WebResponse<>();
        webResponse.setResult(data);
        webResponse.setStatusCode(ResponseCode.SUCCESS.getCode());
        webResponse.setMessage(ResponseCode.SUCCESS.getMessage());
        return webResponse;
    }

    public <T> WebResponse<T> buildFailedResponse(String statusCode, String message) {
        WebResponse<T> webResponse = new WebResponse<>();
        if (null != this.debuggerType && this.debuggerType.equals("true")) {
            webResponse.setMessage(message);
        } else {
            webResponse.setMessage(this.errInfo);
        }

        webResponse.setStatusCode(statusCode);
        return webResponse;
    }

}
