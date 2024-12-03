package org.jeecg.common.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.RepeatSubmit;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.util.Md5Util;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.common.util.oConvertUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * AOP防重复提交
 * PS：不支持文件类型的POST请求
 *
 * @author Yoko
 * @since 2024/12/3 15:25
 */
@Aspect
@Slf4j
@Component
public class RepeatSubmitAspect {

    @Resource
    private RedisUtil redisUtil;

    @Around("@annotation(repeatSubmit)")
    public Object around(ProceedingJoinPoint joinPoint, RepeatSubmit repeatSubmit) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String serviceId = repeatSubmit.serviceId();
        boolean res;

        // 基于方法参数的防重提交
        String key;
        if (repeatSubmit.limitType() == RepeatSubmit.Type.PARAM) {
            String ipAddr = getIpAddr(request);
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            Method method = methodSignature.getMethod();
            // 老方式，有问题（有时获取的用户ip是经过代理的服务器，这时无法判断重复请求）
            // key = serviceId + ":repeat_submit_params:" + Md5Util.md5Encode(ipAddr + method.getName(), StandardCharsets.UTF_8.name());
            // 获取所有方法参数Map
            Map<String, Object> allParams = getUrlParams(request);
            allParams.put("ip", ipAddr);
            allParams.put("method", method.getName());

            // 获取方法参数
            Parameter[] parameters = method.getParameters();
            for (int i = 0; i < parameters.length; i++) {
                Parameter parameter = parameters[i];
                String paramName = parameter.getName();
                // 处理@PathVariable参数
                if (parameter.isAnnotationPresent(PathVariable.class)) {
                    Object pathVariableValue = joinPoint.getArgs()[i];
                    allParams.put(paramName, pathVariableValue);
                }
                // 处理@RequestBody参数
                if (parameter.isAnnotationPresent(RequestBody.class)) {
                    Object requestBody = joinPoint.getArgs()[i];
                    // allParams.putAll(paramName, requestBody != null ? requestBody.toString() : "null");
                    Map<String, Object> map = JSONObject.parseObject(JSON.toJSONString(requestBody), new TypeReference<Map<String, Object>>(){});
                    allParams.putAll(map);
                }
            }

            // md5作为防重复key
            String md5Encode = Md5Util.md5Encode(JSON.toJSONString(allParams), StandardCharsets.UTF_8.name());
            key = serviceId + ":repeat_submit_params:" + md5Encode;
        } else {
            // 基于Token的防重提交
            String requestToken = request.getHeader(CommonConstant.X_ACCESS_TOKEN);
            key = serviceId + ":repeat_submit_token:" + requestToken;
        }
        res = redisUtil.tryLock(key, "", repeatSubmit.lockTime());

        if (!res) {
            // log.error("请求重复提交");
            // return null;
            return Result.error("请勿重复提交，请稍后再试！");
        }

        return joinPoint.proceed();
    }

    public static Map<String, Object> getUrlParams(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>(16);
        if (oConvertUtils.isEmpty(request.getQueryString())) {
            return result;
        }
        String param = "";
        try {
            param = URLDecoder.decode(request.getQueryString(), "utf-8");
        } catch (UnsupportedEncodingException e) {
            log.error("URLDecoder.decode error:", e);
        }
        String[] params = param.split("&");
        for (String s : params) {
            int index = s.indexOf("=");
            result.put(s.substring(0, index), s.substring(index + 1));
        }
        return result;
    }

    public static String getIpAddr(HttpServletRequest request) {
        String ip = null;
        try {
            ip = request.getHeader("x-forwarded-for");
            if (StringUtils.isEmpty(ip) || CommonConstant.UNKNOWN.equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(ip) || ip.length() == 0 ||CommonConstant.UNKNOWN.equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(ip) || CommonConstant.UNKNOWN.equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (StringUtils.isEmpty(ip) || CommonConstant.UNKNOWN.equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (StringUtils.isEmpty(ip) || CommonConstant.UNKNOWN.equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        } catch (Exception e) {
            log.error("IPUtils ERROR ", e);
        }

       // 使用代理，则获取第一个IP地址
       if(StringUtils.isEmpty(ip) && ip.length() > 15) {
			if(ip.indexOf(",") > 0) {
				ip = ip.substring(0, ip.indexOf(","));
			}
		}

        return ip;
    }

}
