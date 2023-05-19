package com.trangle.order.feign.interceptor;

import com.trangle.order.holder.GrayHolder;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GrayInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        Integer gray = GrayHolder.get();
        if (gray != null) {
            requestTemplate.header("gray", gray.toString());
        }
        log.info("FeignInterceptor --- ");
    }
}
