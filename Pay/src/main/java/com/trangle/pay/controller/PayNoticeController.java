package com.trangle.pay.controller;

import com.trangle.pay.producer.PayProducer;
import feign.Param;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author trangle
 */
@RestController
@Slf4j
@RequestMapping("/pay/notice")
public class PayNoticeController {

    @Resource
    @Qualifier("payNoticeProducer")
    private PayProducer payProducer;

    @GetMapping("producer")
    public void test(@Param("info") String info) {
        payProducer.sendPayMsg(info);
    }
}
