package com.trangle.common.filter;

import org.apache.catalina.connector.RequestFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author trangle
 */
public class TokenFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(TokenFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        long startTime = System.currentTimeMillis();
        if (log.isInfoEnabled()) {
            log.info("请求路径为： -- {}", ((RequestFacade) servletRequest).getRequestURI());
        }

        filterChain.doFilter(servletRequest, servletResponse);
        if (log.isInfoEnabled()) {
            long endTime = System.currentTimeMillis();
            log.info("请求时间为： -- {}ms", endTime - startTime);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
