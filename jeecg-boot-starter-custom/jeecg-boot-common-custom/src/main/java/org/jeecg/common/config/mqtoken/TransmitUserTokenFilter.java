package org.jeecg.common.config.mqtoken;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class TransmitUserTokenFilter implements Filter {
    private static String X_ACCESS_TOKEN = "X-Access-Token";

    public TransmitUserTokenFilter() {
    }

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        this.initUserInfo((HttpServletRequest)request);
        chain.doFilter(request, response);
    }

    private void initUserInfo(HttpServletRequest request) {
        String token = request.getHeader(X_ACCESS_TOKEN);
        if (token != null) {
            try {
                UserTokenContext.setToken(token);
            } catch (Exception ignore) {
            }
        }

    }

    public void destroy() {
    }
}
