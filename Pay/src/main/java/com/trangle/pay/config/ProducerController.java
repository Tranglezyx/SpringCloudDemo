package com.trangle.pay.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author trangle
 */
@RestController
@Slf4j
public class ProducerController {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @GetMapping("/msg/product")
    public void test(String info) throws Exception {
        String topic = RocketmqConfig.TOPIC_NAME + ":" + RocketmqConfig.GROUP_NAME;
        SendResult sendResult = rocketMQTemplate.syncSend(topic, MessageBuilder.withPayload(info).build());
    }
}
