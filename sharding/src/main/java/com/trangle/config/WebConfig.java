package com.trangle.config;

import com.trangle.interceptor.MDCInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: zhouyunxiang
 * @Date: 2024/12/11 10:55
 * @Description:
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MDCInterceptor())
                .addPathPatterns("/**")       // 拦截所有路径
                .excludePathPatterns("/login", "/static/**"); // 排除路径
    }
}
