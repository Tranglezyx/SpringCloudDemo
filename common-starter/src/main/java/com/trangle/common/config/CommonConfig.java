package com.trangle.common.config;

import com.trangle.common.filter.TokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

/**
 * @author trangle
 */
@Configuration
public class CommonConfig {

    @Bean
    public Filter tokenFilter(){
        return new TokenFilter();
    }
}
