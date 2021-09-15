package com.trangle.pay.feign.controller;

import com.trangle.dto.PayMoneyDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * @author trangle
 */
@RestController
@RequestMapping("/feign/pay")
public class PayFeignController {

    @PostMapping("pay-money")
    public Boolean payMoney(@RequestBody PayMoneyDTO payMoneyDTO) {
        return BigDecimal.valueOf(Math.random() * 100).compareTo(payMoneyDTO.getMoney()) > 0;
    }
}
