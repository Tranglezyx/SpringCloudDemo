package com.trangle.pay.consumer;

import com.trangle.pay.constant.PayRocketMQConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author trangle
 */
//@Component
@Slf4j
@RocketMQMessageListener(topic = PayRocketMQConstants.TOPIC_NAME, selectorExpression = PayRocketMQConstants.PAY_NOTICE_GROUP, consumerGroup = PayRocketMQConstants.PAY_NOTICE_GROUP, consumeThreadMax = 1)
public class PayNoticeConsumer implements RocketMQListener<MessageExt> {

    @Override
    public void onMessage(MessageExt messageExt) {
        String message = new String(messageExt.getBody());
        System.out.println("支付提醒收到消息：" + message);
    }
}
