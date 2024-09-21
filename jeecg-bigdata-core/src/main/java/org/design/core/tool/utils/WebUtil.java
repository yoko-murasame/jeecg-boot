package org.design.core.tool.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.Objects;
import java.util.function.Predicate;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.util.WebUtils;

/* loaded from: bigdata-core-tool-3.1.0.jar:org/design/core/tool/utils/WebUtil.class */
public class WebUtil extends WebUtils {
    public static final String USER_AGENT_HEADER = "user-agent";
    private static final Logger log = LoggerFactory.getLogger(WebUtil.class);
    private static final String[] IP_HEADER_NAMES = {"x-forwarded-for", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR"};
    private static final Predicate<String> IP_PREDICATE = ip -> {
        return StringUtil.isBlank(ip) || StringPool.UNKNOWN.equalsIgnoreCase(ip);
    };

    public static boolean isBody(HandlerMethod handlerMethod) {
        return ClassUtil.getAnnotation(handlerMethod, ResponseBody.class) != null;
    }

    @Nullable
    public static String getCookieVal(String name) {
        HttpServletRequest request = getRequest();
        Assert.notNull(request, "request from RequestContextHolder is null");
        return getCookieVal(request, name);
    }

    @Nullable
    public static String getCookieVal(HttpServletRequest request, String name) {
        Cookie cookie = getCookie(request, name);
        if (cookie != null) {
            return cookie.getValue();
        }
        return null;
    }

    public static void removeCookie(HttpServletResponse response, String key) {
        setCookie(response, key, null, 0);
    }

    public static void setCookie(HttpServletResponse response, String name, @Nullable String value, int maxAgeInSeconds) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath(StringPool.SLASH);
        cookie.setMaxAge(maxAgeInSeconds);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }

    public static HttpServletRequest getRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return null;
        }
        return ((ServletRequestAttributes) requestAttributes).getRequest();
    }

    public static void renderJson(HttpServletResponse response, Object result) {
        renderJson(response, result, "application/json");
    }

    /*  JADX ERROR: JadxRuntimeException in pass: BlockProcessor
        jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:12:0x0045
        	at jadx.core.dex.visitors.blocks.BlockProcessor.checkForUnreachableBlocks(BlockProcessor.java:86)
        	at jadx.core.dex.visitors.blocks.BlockProcessor.processBlocksTree(BlockProcessor.java:52)
        	at jadx.core.dex.visitors.blocks.BlockProcessor.visit(BlockProcessor.java:44)
        */
    public static void renderJson(javax.servlet.http.HttpServletResponse r4, java.lang.Object r5, java.lang.String r6) {
        /*
            r0 = r4
            java.lang.String r1 = "UTF-8"
            r0.setCharacterEncoding(r1)
            r0 = r4
            r1 = r6
            r0.setContentType(r1)
            r0 = r4
            java.io.PrintWriter r0 = r0.getWriter()     // Catch: IOException -> 0x0076
            r7 = r0
            r0 = 0
            r8 = r0
            r0 = r7
            r1 = r5
            java.lang.String r1 = org.design.core.tool.jackson.JsonUtil.toJson(r1)     // Catch: IOException -> 0x0076, Throwable -> 0x0045, all -> 0x004e
            java.io.PrintWriter r0 = r0.append(r1)     // Catch: IOException -> 0x0076, Throwable -> 0x0045, all -> 0x004e
            r0 = r7
            if (r0 == 0) goto L_0x0073
            r0 = r8
            if (r0 == 0) goto L_0x003e
            r0 = r7
            r0.close()     // Catch: IOException -> 0x0076, Throwable -> 0x0032
            goto L_0x0073
        L_0x0032:
            r9 = move-exception
            r0 = r8
            r1 = r9
            r0.addSuppressed(r1)     // Catch: IOException -> 0x0076
            goto L_0x0073
        L_0x003e:
            r0 = r7
            r0.close()     // Catch: IOException -> 0x0076
            goto L_0x0073
        L_0x0045:
            r9 = move-exception
            r0 = r9
            r8 = r0
            r0 = r9
            throw r0     // Catch: IOException -> 0x0076, all -> 0x004e
        L_0x004e:
            r10 = move-exception
            r0 = r7
            if (r0 == 0) goto L_0x0070
            r0 = r8
            if (r0 == 0) goto L_0x006c
            r0 = r7
            r0.close()     // Catch: IOException -> 0x0076, Throwable -> 0x0060
            goto L_0x0070
        L_0x0060:
            r11 = move-exception
            r0 = r8
            r1 = r11
            r0.addSuppressed(r1)     // Catch: IOException -> 0x0076
            goto L_0x0070
        L_0x006c:
            r0 = r7
            r0.close()     // Catch: IOException -> 0x0076
        L_0x0070:
            r0 = r10
            throw r0     // Catch: IOException -> 0x0076
        L_0x0073:
            goto L_0x0084
        L_0x0076:
            r7 = move-exception
            org.slf4j.Logger r0 = org.design.core.tool.utils.WebUtil.log
            r1 = r7
            java.lang.String r1 = r1.getMessage()
            r2 = r7
            r0.error(r1, r2)
        L_0x0084:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.design.core.tool.utils.WebUtil.renderJson(javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.String):void");
    }

    public static String getIP() {
        return getIP(getRequest());
    }

    @Nullable
    public static String getIP(@Nullable HttpServletRequest request) {
        if (request == null) {
            return StringPool.EMPTY;
        }
        String ip = null;
        for (String ipHeader : IP_HEADER_NAMES) {
            ip = request.getHeader(ipHeader);
            if (!IP_PREDICATE.test(ip)) {
                break;
            }
        }
        if (IP_PREDICATE.test(ip)) {
            ip = request.getRemoteAddr();
        }
        if (StringUtil.isBlank(ip)) {
            return null;
        }
        return StringUtil.splitTrim(ip, StringPool.COMMA).get(0);
    }

    public static String getHeader(String name) {
        return ((HttpServletRequest) Objects.requireNonNull(getRequest())).getHeader(name);
    }

    public static Enumeration<String> getHeaders(String name) {
        return ((HttpServletRequest) Objects.requireNonNull(getRequest())).getHeaders(name);
    }

    public static Enumeration<String> getHeaderNames() {
        return ((HttpServletRequest) Objects.requireNonNull(getRequest())).getHeaderNames();
    }

    public static String getParameter(String name) {
        return ((HttpServletRequest) Objects.requireNonNull(getRequest())).getParameter(name);
    }

    public static String getRequestBody(ServletInputStream servletInputStream) {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;
        try {
            try {
                reader = new BufferedReader(new InputStreamReader((InputStream) servletInputStream, StandardCharsets.UTF_8));
                while (true) {
                    String line = reader.readLine();
                    if (line == null) {
                        break;
                    }
                    sb.append(line);
                }
                if (servletInputStream != null) {
                    try {
                        servletInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                }
            } catch (IOException e3) {
                e3.printStackTrace();
                if (servletInputStream != null) {
                    try {
                        servletInputStream.close();
                    } catch (IOException e4) {
                        e4.printStackTrace();
                    }
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e5) {
                        e5.printStackTrace();
                    }
                }
            }
            return sb.toString();
        } catch (Throwable th) {
            if (servletInputStream != null) {
                try {
                    servletInputStream.close();
                } catch (IOException e6) {
                    e6.printStackTrace();
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e7) {
                    e7.printStackTrace();
                }
            }
            throw th;
        }
    }

    public static String getRequestContent(HttpServletRequest request) {
        try {
            String queryString = request.getQueryString();
            if (StringUtil.isNotBlank(queryString)) {
                return new String(queryString.getBytes(Charsets.ISO_8859_1), Charsets.UTF_8).replaceAll("&amp;", StringPool.AMPERSAND).replaceAll("%22", StringPool.QUOTE);
            }
            String charEncoding = request.getCharacterEncoding();
            if (charEncoding == null) {
                charEncoding = "UTF-8";
            }
            String str = new String(getRequestBody(request.getInputStream()).getBytes(), charEncoding).trim();
            if (StringUtil.isBlank(str)) {
                StringBuilder sb = new StringBuilder();
                Enumeration<String> parameterNames = request.getParameterNames();
                while (parameterNames.hasMoreElements()) {
                    String key = parameterNames.nextElement();
                    StringUtil.appendBuilder(sb, key, StringPool.EQUALS, request.getParameter(key), StringPool.AMPERSAND);
                }
                str = StringUtil.removeSuffix(sb.toString(), StringPool.AMPERSAND);
            }
            return str.replaceAll("&amp;", StringPool.AMPERSAND);
        } catch (Exception ex) {
            ex.printStackTrace();
            return StringPool.EMPTY;
        }
    }
}
