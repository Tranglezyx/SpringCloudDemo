package com.trangle.pay.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author trangle
 */
@Component
@Slf4j
@RocketMQMessageListener(topic = RocketmqConfig.TOPIC_NAME, consumerGroup = RocketmqConfig.GROUP_NAME, consumeThreadMax = 1)
public class CustomConsumer implements RocketMQListener<MessageExt> {

    @Override
    public void onMessage(MessageExt messageExt) {
        String message = new String(messageExt.getBody());
        System.out.println(message);
    }
}
