package com.trangle.basic.common.config;

import com.github.pagehelper.PageInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * PageHelper配置类
 * 用于在不同环境下控制explain日志的开关
 */
@Configuration
public class PageHelperConfig {

    @Value("${pagehelper.explain.enabled:true}")
    private boolean explainEnabled;

    @Bean
    public PageInterceptor pageInterceptor() {
        PageInterceptor interceptor = new PageInterceptor();
        Properties properties = new Properties();
        
        // 基本配置
        properties.setProperty("helperDialect", "mysql");
        properties.setProperty("offset-as-page-num", "true");
        properties.setProperty("row-bounds-with-count", "true");
        properties.setProperty("page-size-zero", "true");
        properties.setProperty("support-methods-arguments", "true");
        
        // 关键：控制explain日志
        if (!explainEnabled) {
            // 禁用explain功能
            properties.setProperty("auto-dialect", "false");
            properties.setProperty("auto-runtime-dialect", "false");
            properties.setProperty("close-conn", "true");
            // 设置一个空方言来禁用explain
            properties.setProperty("dialect", "com.github.pagehelper.dialect.helper.MySqlDialect");
        }
        
        interceptor.setProperties(properties);
        return interceptor;
    }
}
