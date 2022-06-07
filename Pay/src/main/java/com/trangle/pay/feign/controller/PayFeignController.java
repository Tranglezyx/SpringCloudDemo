package com.trangle.pay.feign.controller;

import com.trangle.pay.dto.PayMoneyDTO;
import com.trangle.pay.entity.PayBasic;
import com.trangle.pay.service.PayService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author trangle
 */
@RestController
@RequestMapping("/feign/pay")
public class PayFeignController {

    @Resource
    private PayService payService;

    @PostMapping("/subMoney")
    public Boolean payMoney(@RequestBody PayMoneyDTO payMoneyDTO) {
        PayBasic payBasic = new PayBasic();
        payBasic.setMoney(payMoneyDTO.getMoney());
        payBasic.setUserId(payMoneyDTO.getUserId());
        payService.subUserMoney(payBasic);
        return true;
    }
}
