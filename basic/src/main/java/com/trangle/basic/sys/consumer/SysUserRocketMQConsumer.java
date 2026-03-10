package com.trangle.basic.sys.consumer;

import com.trangle.basic.common.constant.RocketMQConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RocketMQMessageListener(
        topic = RocketMQConstants.SYS_USER_TOPIC,
        consumerGroup = RocketMQConstants.SYS_USER_GROUP
)
public class SysUserRocketMQConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String s) {
        log.info("收到消息 >>> {}", s);
    }
}
