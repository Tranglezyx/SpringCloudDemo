package com.trangle.pay.controller;

import com.trangle.pay.entity.PayBasic;
import com.trangle.pay.producer.PayProducer;
import com.trangle.pay.service.PayService;
import feign.Param;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Random;

/**
 * @author trangle
 */
@RestController
@RequestMapping("/pay")
public class PayController {

    @Resource
    @Qualifier("defaultPayProducer")
    private PayProducer payProducer;

    @Resource
    private PayService payService;

    @GetMapping("getMoney")
    public Object getPayMoney(Long orderId){
        return new Random().nextDouble();
    }

    @GetMapping("producer")
    public void test(@Param("info") String info) throws Exception {
        payProducer.sendPayMsg(info);
    }

    @PostMapping("/addUserPay")
    public void addUserPay(@RequestBody PayBasic payBasic) {
        payService.addUserPay(payBasic);
    }

    @PostMapping("/subUserMoney")
    public void subUserMoney(@RequestBody PayBasic payBasic) {
        payService.subUserMoney(payBasic);
    }


    @PostMapping("/getMoney")
    public BigDecimal getMoney(@RequestBody PayBasic payBasic) {
        return payService.getMoney(payBasic);
    }
}
