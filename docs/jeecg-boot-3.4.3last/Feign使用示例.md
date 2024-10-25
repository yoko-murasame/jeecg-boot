# Feign使用示例

## Basic Auth调用示例

1. 创建配置类

```java
/**
 * Feign客户端basicAuth配置
 *
 * @author Yoko
 * @since 2024/10/24 19:27
 */
public class BasicAuthFeignConfig {

    @Resource
    private IotZdzkProperty zdzkProperty;

    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        return new BasicAuthRequestInterceptor(zdzkProperty.getUsername(), zdzkProperty.getPassword());
    }

}

```

2. 创建Feign客户端

```java
import feign.Headers;
import org.jeecg.modules.iot.zdzk.config.BasicAuthFeignConfig;
import org.jeecg.modules.iot.zdzk.vo.ZdzkTokenVO;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.Map;

@ConditionalOnProperty(prefix = "smart-drain-reuse.iot.zdzk", name = "enabled", havingValue = "true")
@FeignClient(name = "iot-zdzk-token-api", url = "${smart-drain-reuse.iot.zdzk.thirdApiUrl}", configuration = BasicAuthFeignConfig.class)
public interface IotZdzkTokenApi {

    /**
     * 获取token
     *
     * @author Yoko
     * @since 2024/10/24 19:28
     * @param formParams form表单参数 form.put("grant_type", "client_credentials");
     * @return com.alibaba.fastjson.JSONObject
     */
    @PostMapping(value = "/api/xxx/oauth/token", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    ZdzkTokenVO getToken(@RequestBody Map<String, ?> formParams);

}

```

3. 使用

```java
    public String getToken(boolean refresh) {
        String token = (String) redisUtil.get(SmartDrainReuseConstant.REDIS_KEY_ZDZK_TOKEN);
        if (null != token && !refresh) {
            return token;
        }
        // 重新获取token
        Map<String, Object> form = new HashMap<>();
        form.put("grant_type", "client_credentials");
        ZdzkTokenVO vo = iotZdzkTokenApi.getToken(form);
        Long expiresIn = vo.getExpiresIn();
        // 如果token快过期了就重新获取
        while (expiresIn - 1 < 0) {
            vo = iotZdzkTokenApi.getToken(form);
            expiresIn = vo.getExpiresIn();
        }
        redisUtil.set(SmartDrainReuseConstant.REDIS_KEY_ZDZK_TOKEN, vo.getAccessToken(), expiresIn -1);
        return vo.getAccessToken();
    }
```

## 使用Header

1. 普通字段

```java
@PostMapping(value = "/api/xxx")
ZdzkDataResultVO<List<ZdzkDataRiverVO>> getRiverList(@RequestParam ZdzkRiverListQuery query, @RequestHeader("Authorization") String authorization, @RequestHeader("Client-Id") String clientId);
```

2. 通过Map传递

```java
@PostMapping(value = "/api/xxx", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
ZdzkDataResultVO<List<ZdzkDataTidalVO>> getTidalList(@ModelAttribute ZdzkTidalQuery query, @HeaderMap Map<String, String> headers);
```

3. 设置配置类全局注入

```java
public class BearerFeignConfig {

    @Bean
    public BearerFeignInterceptor bearerFeignInterceptor() {
        return new BearerFeignInterceptor();
    }

}

public class BearerFeignInterceptor implements RequestInterceptor {

    public final static String HEADER_CLIENT_ID =  "Client-Id";

    @Override
    public void apply(RequestTemplate template) {
        template.header(HEADER_CLIENT_ID, "xxxxxx");
    }

}
// 这是所有接口都会自动添加Header: Client-Id: xxxxxx
@ConditionalOnProperty(prefix = "smart-drain-reuse.iot.zdzk", name = "enabled", havingValue = "true")
@FeignClient(name = "iot-zdzk-data-api", url = "${smart-drain-reuse.iot.zdzk.thirdApiUrl}", configuration = BearerFeignConfig.class)
public interface IotZdzkDataApi {

    @PostMapping(value = "/api/xxx", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    ZdzkDataResultVO<List<ZdzkDataRiverVO>> getRiverList(@RequestParam Map<String, Object> query);

}

```

## Bearer Token使用示例

1. 定义拦截器

```java
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.Getter;
import lombok.Setter;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.config.constant.SmartDrainReuseConstant;
import org.jeecg.config.iot.IotZdzkProperty;

import javax.annotation.Resource;

/**
 * Feign客户端Bearer认证配置
 *
 * @author Yoko
 */
@Setter
@Getter
public class BearerFeignInterceptor implements RequestInterceptor {

    public final static String HEADER_CLIENT_ID =  "Client-Id";
    public final static String HEADER_AUTHORIZATION =  "Authorization";
    public final static String BEARER_PREFIX =  "Bearer ";

    @Resource
    private IotZdzkProperty iotZdzkProperty;

    @Resource
    private RedisUtil redisUtil;

    /**
     * Feign客户端Bearer认证配置
     *
     * @author Yoko
     * @since 2024/10/25 14:57
     * @param template 请求模板
     */
    @Override
    public void apply(RequestTemplate template) {
        template.header(HEADER_CLIENT_ID, iotZdzkProperty.getUsername());
        template.header(HEADER_AUTHORIZATION, BEARER_PREFIX + redisUtil.get(SmartDrainReuseConstant.REDIS_KEY_ZDZK_TOKEN));
    }

}

```

2. 定义配置类

```java
import org.springframework.context.annotation.Bean;

/**
 * Feign客户端Bearer认证配置
 *
 * @author Yoko
 */
public class BearerFeignConfig {

    @Bean
    public BearerFeignInterceptor bearerFeignInterceptor() {
        return new BearerFeignInterceptor();
    }

}

```

3. Feign客户端使用，所有接口都会自动添加Header: Client-Id: xxxxxx 和 Authorization: Bearer xxxxx

```java
@ConditionalOnProperty(prefix = "smart-drain-reuse.iot.zdzk", name = "enabled", havingValue = "true")
@FeignClient(name = "iot-zdzk-data-api", url = "${smart-drain-reuse.iot.zdzk.thirdApiUrl}", configuration = BearerFeignConfig.class)
public interface IotZdzkDataApi {

    @PostMapping(value = "/api/xxx", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    ZdzkDataResultVO<List<ZdzkDataRiverVO>> getRiverList(@RequestParam Map<String, Object> query);

    @PostMapping(value = "/api/xxx", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    ZdzkDataResultVO<List<ZdzkDataRainVO>> getRainList(@RequestBody ZdzkRainQuery query);

}

```

## @RequestParam标注Map

测试发现，Query的查询参数，使用Map传递，会自动拼接为key1=value1&key2=value2的形式

```java
// 可以正常处理query参数
@PostMapping(value = "/api/xxx", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
ZdzkDataResultVO<List<ZdzkDataRainVO>> getRainList(@RequestParam Map<String, Object> query);

// 传入对象时，就无法解析成query查询
@PostMapping(value = "/api/xxx", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
ZdzkDataResultVO<List<ZdzkDataRainVO>> getRainList(@RequestParam ZdzkRainQuery query);
```

# End
