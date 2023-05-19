package com.trangle.pay.filter;

import com.trangle.pay.holder.GrayHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.RequestFacade;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author Trangle
 */
@Component
@Slf4j
public class TokenFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String gray = ((RequestFacade) servletRequest).getHeader("gray");
        if (StringUtils.isNotEmpty(gray)) {
            log.info("获取到灰度信息-gray :{}", gray);
            GrayHolder.set(Integer.valueOf(gray));
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
