package com.trangle.common.config;

import com.trangle.common.filter.TokenFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

/**
 * @author trangle
 */
@Configuration
public class CommonConfig {

    @Bean
    @ConditionalOnMissingBean
    public Filter tokenFilter(){
        return new TokenFilter();
    }
}
