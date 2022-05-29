package com.trangle.pay.config;

import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author trangle
 */
@Configuration
public class CustomConsumer extends DefaultConsumerConfigure implements ApplicationListener<ContextRefreshedEvent> {

    Logger log = LoggerFactory.getLogger(CustomConsumer.class);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent arg0) {
        try {
            super.listener("t_TopicTest", "Tag1");
        } catch (MQClientException e) {
            log.error("消费者监听器启动失败", e);
        }
    }

    @Override
    public ConsumeConcurrentlyStatus dealBody(List<MessageExt> msgs) {
        int num = 1;
        log.info("进入");
        for (MessageExt msg : msgs) {
            log.info("第" + num + "次消息");
            try {
                String msgStr = new String(msg.getBody(), "utf-8");
                log.info(msgStr);
            } catch (UnsupportedEncodingException e) {
                log.error("body转字符串解析失败");
            }
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
