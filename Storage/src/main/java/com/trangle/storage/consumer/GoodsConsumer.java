package com.trangle.storage.consumer;

import com.alibaba.fastjson.JSONObject;
import com.trangle.storage.dto.OrderBasicDTO;
import com.trangle.storage.mapper.StorageBasicMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author trangle
 */
@Component
@Slf4j
@RocketMQMessageListener(topic = "storage_topic", selectorExpression = "goods_group", consumerGroup = "goods_group", consumeThreadMax = 5)
public class GoodsConsumer implements RocketMQListener<MessageExt> {

    @Resource
    private StorageBasicMapper storageBasicMapper;

    @Override
    public void onMessage(MessageExt messageExt) {
        String message = new String(messageExt.getBody());
        OrderBasicDTO basicDTO = JSONObject.parseObject(message, OrderBasicDTO.class);
        String threadName = Thread.currentThread().getName();
        log.info("{}接受到消息，message:{}", threadName, message);
        storageBasicMapper.updateGoodsNumByGoodsId(basicDTO);
    }
}
