//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.design.core.tool.utils;

import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class OkHttpUtil {
    private static final Logger log = LoggerFactory.getLogger(OkHttpUtil.class);
    public static MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static MediaType XML = MediaType.parse("application/xml; charset=utf-8");

    public OkHttpUtil() {
    }

    public static String get(String url, Map<String, String> queries) {
        return get(url, (Map)null, queries);
    }

    public static String get(String url, Map<String, String> header, Map<String, String> queries) {
        StringBuffer sb = new StringBuffer(url);
        if (queries != null && queries.keySet().size() > 0) {
            sb.append("?clientId=blade");
            queries.forEach((k, v) -> {
                sb.append("&").append(k).append("=").append(v);
            });
        }

        Request.Builder builder = new Request.Builder();
        if (header != null && header.keySet().size() > 0) {
            header.forEach(builder::addHeader);
        }

        Request request = builder.url(sb.toString()).build();
        return getBody(request);
    }

    public static String post(String url, Map<String, String> params) {
        return post(url, (Map)null, params);
    }

    public static String post(String url, Map<String, String> header, Map<String, String> params) {
        FormBody.Builder formBuilder = (new FormBody.Builder()).add("clientId", "blade");
        if (params != null && params.keySet().size() > 0) {
            params.forEach(formBuilder::add);
        }

        Request.Builder builder = new Request.Builder();
        if (header != null && header.keySet().size() > 0) {
            header.forEach(builder::addHeader);
        }

        Request request = builder.url(url).post(formBuilder.build()).build();
        return getBody(request);
    }

    public static String postJson(String url, String json) {
        return postJson(url, (Map)null, json);
    }

    public static String postJson(String url, Map<String, String> header, String json) {
        return postContent(url, header, json, JSON);
    }

    public static String postXml(String url, String xml) {
        return postXml(url, (Map)null, xml);
    }

    public static String postXml(String url, Map<String, String> header, String xml) {
        return postContent(url, header, xml, XML);
    }

    public static String postContent(String url, Map<String, String> header, String content, MediaType mediaType) {
        RequestBody requestBody = RequestBody.create(mediaType, content);
        Request.Builder builder = new Request.Builder();
        if (header != null && header.keySet().size() > 0) {
            header.forEach(builder::addHeader);
        }

        Request request = builder.url(url).post(requestBody).build();
        return getBody(request);
    }

    private static String getBody(Request request) {
        String responseBody = "";
        Response response = null;

        String var4;
        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            response = okHttpClient.newCall(request).execute();
            if (!response.isSuccessful()) {
                return responseBody;
            }

            var4 = response.body().string();
        } catch (Exception var8) {
            log.error("okhttp3 post error >> ex = {}", var8.getMessage());
            return responseBody;
        } finally {
            if (response != null) {
                response.close();
            }

        }

        return var4;
    }
}
