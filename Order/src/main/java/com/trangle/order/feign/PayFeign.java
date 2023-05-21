package com.trangle.order.feign;

import com.trangle.order.balancer.GrayLoadBalancerConfig;
import com.trangle.order.dto.PayMoneyDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author trangle
 */
@FeignClient(name = "pay",configuration = GrayLoadBalancerConfig.class)
public interface PayFeign {

    @PostMapping("/feign/pay/subMoney")
    Boolean payMoney(@RequestBody PayMoneyDTO payMoneyDTO);
}
