package com.trangle.pay.controller;

import com.trangle.pay.producer.PayProducer;
import feign.Param;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
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

    @GetMapping("getMoney")
    public Object getPayMoney(Long orderId){
        return new Random().nextDouble();
    }

    @GetMapping("producer")
    public void test(@Param("info") String info) throws Exception {
        payProducer.sendPayMsg(info);
    }
}
