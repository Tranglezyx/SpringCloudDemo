package com.trangle.pay.producer;

import org.apache.rocketmq.spring.core.RocketMQTemplate;

import javax.annotation.Resource;

/**
 * @author trangle
 */
public abstract class PayProducer {

    @Resource
    RocketMQTemplate rocketMQTemplate;

    public abstract void sendPayMsg(Object info);
}
