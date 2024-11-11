package org.jeecg.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.util.RedisUtil;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Set;

/**
 * 临时token请求拦截器
 *
 * @author Yoko
 * @since 2024/11/11 16:32
 */
public class TempTokenRequestInterceptor implements RequestInterceptor {

    @Resource
    private RedisUtil redisUtil;

    /**
     * @description 添加临时token
     *
     * @author Yoko
     * @since 2024/11/11 16:27
     * @param template RequestTemplate
     */
    @Override
    public void apply(RequestTemplate template) {
        Collection<String> token = template.headers().get(CommonConstant.X_ACCESS_TOKEN);
        if (null == token || token.isEmpty()) {
            Set<String> keys = redisUtil.keys(CommonConstant.PREFIX_SSO_TEMP_TOKEN);
            if (!CollectionUtils.isEmpty(keys)) {
                for (String key : keys) {
                    String tempToken = redisUtil.get(key).toString();
                    template.header(CommonConstant.X_ACCESS_TOKEN, tempToken);
                    break;
                }
            }
        }
    }

}
