package com.trangle.gateway.filter;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author Trangle
 */
@Component
public class TokenFilter implements GlobalFilter {

    private static final String TOKEN = "Authorization";
    private static final String TOKEN_VALUE = "Bearer 123456";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (CollectionUtils.isEmpty(exchange.getRequest().getHeaders().get(TOKEN))) {
            throw new RuntimeException("请先登录");
        }
        String token = exchange.getRequest().getHeaders().get(TOKEN).stream().findFirst().orElse(null);
        if (TOKEN_VALUE.equals(token)) {
            return chain.filter(exchange);
        }
        throw new RuntimeException("请先登录");
    }
}
