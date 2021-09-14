package com.trangle.pay.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * @author trangle
 */
@RestController
@RequestMapping("/pay1")
public class PayController {

    @GetMapping("getMoney")
    public Object getPayMoney(Long orderId){
        return new Random().nextDouble();
    }
}
