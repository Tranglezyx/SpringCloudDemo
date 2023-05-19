package com.trangle.gateway.filter;

import com.trangle.gateway.holder.GrayHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author Trangle
 */
@Component
@Slf4j
public class TokenFilter implements GlobalFilter, Ordered {

    private static final String TOKEN = "token";
    private static final String TOKEN_VALUE = "Bearer 123456";
    private static final String GRAY_TOKEN_VALUE = "Gray 123456";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        HttpHeaders headers = exchange.getRequest().getHeaders();
        if (CollectionUtils.isEmpty(headers.get(TOKEN))) {
            throw new RuntimeException("请先登录");
        }
        GrayHolder.set(0);
        String token = headers.get(TOKEN).stream().findFirst().orElse(null);
        log.info("token:{}", token);
        if (TOKEN_VALUE.equals(token)) {
            return chain.filter(exchange);
        }
        if (GRAY_TOKEN_VALUE.equals(token)) {
            GrayHolder.set(1);
            // 获取原始请求对象
            ServerHttpRequest request = exchange.getRequest();
            // 创建一个可变的请求副本
            ServerHttpRequest.Builder builder = request.mutate();
            // 修改Headers中的参数
            builder.header("gray", "1");
            // 创建一个新的请求对象
            ServerHttpRequest newRequest = builder.build();
            // 创建一个可变的交换副本
            ServerWebExchange.Builder exchangeBuilder = exchange.mutate();
            // 设置新的请求对象
            exchangeBuilder.request(newRequest);
            // 创建一个新的交换对象
            ServerWebExchange newExchange = exchangeBuilder.build();
            // 继续过滤器链
            Mono<Void> filter = chain.filter(newExchange);
            return filter;
        }
        throw new RuntimeException("请先登录");
    }

    @Override
    public int getOrder() {
        return -100;
    }
}
