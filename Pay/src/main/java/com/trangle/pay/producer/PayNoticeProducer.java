package com.trangle.pay.producer;

import com.trangle.pay.constant.PayRocketMQConstants;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * @author trangle
 */
@Component
public class PayNoticeProducer extends PayProducer{

    @Override
    public void sendPayMsg(Object info) {
        String topic = PayRocketMQConstants.TOPIC_NAME + ":" + PayRocketMQConstants.PAY_NOTICE_GROUP;
        rocketMQTemplate.syncSend(topic, MessageBuilder.withPayload(info).build());
    }
}
