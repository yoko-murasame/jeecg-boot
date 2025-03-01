package cn.com.hyit.core.web.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 表控制层-WebResponse
 * FIXME 仅供参考，实际项目中应该使用hyit-core包中的WebResponse
 */
@Setter
@ApiModel(
        value = "WebResponse返回对象",
        description = "WebResponse返回对象"
)
public class WebResponse<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    @Getter
    @ApiModelProperty(
            value = "响应状态码",
            name = "statusCode",
            example = "200"
    )
    private String statusCode;
    @Getter
    @ApiModelProperty(
            value = "对result的状态文字描述",
            name = "message",
            example = "成功！"
    )
    private String message;
    @Getter
    @ApiModelProperty(
            value = "返回对象",
            name = "result"
    )
    private T result;
    @ApiModelProperty(
            value = "版本号",
            name = "version",
            example = "3.0.0"
    )
    private String version;

    public WebResponse() {
    }

    public WebResponse(String message, String apiName, T data) {
        this.message = message;
        this.result = data;
    }

    public WebResponse(String message, T data) {
        this.message = message;
        this.result = data;
    }

    public String getVersion() {
        return "";
    }

}
