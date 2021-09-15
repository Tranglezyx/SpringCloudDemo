package com.trangle.order.feign;

import com.trangle.dto.PayMoneyDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author trangle
 */
@FeignClient(name = "pay")
@RequestMapping("/feign/pay")
public interface PayFeign {

    @PostMapping("/pay-money")
    Boolean payMoney(@RequestBody PayMoneyDTO payMoneyDTO);
}
