package com.trangle.common.filter;

import org.apache.catalina.connector.RequestFacade;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author trangle
 */
public class TokenFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("请求路径为： -- " + ((RequestFacade) servletRequest).getRequestURI());
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
