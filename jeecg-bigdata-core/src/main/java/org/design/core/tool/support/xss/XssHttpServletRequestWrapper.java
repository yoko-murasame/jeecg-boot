package org.design.core.tool.support.xss;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import org.design.core.tool.utils.StringUtil;

/* loaded from: bigdata-core-tool-3.1.0.jar:org/design/core/tool/support/xss/XssHttpServletRequestWrapper.class */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
    HttpServletRequest orgRequest;
    private static final HtmlFilter HTML_FILTER = new HtmlFilter();

    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
        this.orgRequest = request;
    }

    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    public ServletInputStream getInputStream() throws IOException {
        if (null == getHeader("Content-Type")) {
            return getInputStream();
        }
        if (getHeader("Content-Type").startsWith("multipart/form-data")) {
            return getInputStream();
        }
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(inputHandlers(getInputStream()).getBytes());
        return new ServletInputStream() { // from class: org.design.core.tool.support.xss.XssHttpServletRequestWrapper.1
            public int read() {
                return byteArrayInputStream.read();
            }

            public boolean isFinished() {
                return false;
            }

            public boolean isReady() {
                return false;
            }

            public void setReadListener(ReadListener readListener) {
            }
        };
    }

    private String inputHandlers(ServletInputStream servletInputStream) {
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
            return xssEncode(sb.toString());
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

    public String getParameter(String name) {
        String value = getParameter(xssEncode(name));
        if (StringUtil.isNotBlank(value)) {
            value = xssEncode(value);
        }
        return value;
    }

    public String[] getParameterValues(String name) {
        String[] parameters = getParameterValues(name);
        if (parameters == null || parameters.length == 0) {
            return null;
        }
        for (int i = 0; i < parameters.length; i++) {
            parameters[i] = xssEncode(parameters[i]);
        }
        return parameters;
    }

    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> map = new LinkedHashMap<>();
        Map<String, String[]> parameters = getParameterMap();
        for (String key : parameters.keySet()) {
            String[] values = parameters.get(key);
            for (int i = 0; i < values.length; i++) {
                values[i] = xssEncode(values[i]);
            }
            map.put(key, values);
        }
        return map;
    }

    public String getHeader(String name) {
        String value = getHeader(xssEncode(name));
        if (StringUtil.isNotBlank(value)) {
            value = xssEncode(value);
        }
        return value;
    }

    private String xssEncode(String input) {
        return HTML_FILTER.filter(input);
    }

    public HttpServletRequest getOrgRequest() {
        return this.orgRequest;
    }

    public static HttpServletRequest getOrgRequest(HttpServletRequest request) {
        if (request instanceof XssHttpServletRequestWrapper) {
            return ((XssHttpServletRequestWrapper) request).getOrgRequest();
        }
        return request;
    }
}
