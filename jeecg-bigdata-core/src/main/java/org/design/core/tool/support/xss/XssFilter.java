package org.design.core.tool.support.xss;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.util.AntPathMatcher;

/* loaded from: bigdata-core-tool-3.1.0.jar:org/design/core/tool/support/xss/XssFilter.class */
public class XssFilter implements Filter {
    private final XssProperties xssProperties;
    private final XssUrlProperties xssUrlProperties;
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    public XssFilter(XssProperties xssProperties, XssUrlProperties xssUrlProperties) {
        this.xssProperties = xssProperties;
        this.xssUrlProperties = xssUrlProperties;
    }

    public void init(FilterConfig config) {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (isSkip(((HttpServletRequest) request).getServletPath())) {
            chain.doFilter(request, response);
        } else {
            chain.doFilter(new XssHttpServletRequestWrapper((HttpServletRequest) request), response);
        }
    }

    private boolean isSkip(String path) {
        return this.xssUrlProperties.getExcludePatterns().stream().anyMatch(pattern -> {
            return this.antPathMatcher.match(path, path);
        }) || this.xssProperties.getSkipUrl().stream().anyMatch(pattern -> {
            return this.antPathMatcher.match(path, path);
        });
    }

    public void destroy() {
    }
}
