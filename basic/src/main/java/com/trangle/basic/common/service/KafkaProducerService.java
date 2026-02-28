package com.trangle.basic.common.service;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
 * Kafka生产者服务
 */
@Slf4j
@Service
public class KafkaProducerService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    /**
     * 发送消息到指定Topic
     *
     * @param topic   Topic名称
     * @param key     消息Key
     * @param message 消息内容（对象）
     * @return 是否发送成功
     */
    public boolean sendMessage(String topic, String key, Object message) {
        try {
            String jsonMessage = JSON.toJSONString(message);
            log.info("发送消息到Kafka, topic: {}, key: {}, message: {}", topic, key, jsonMessage);

            CompletableFuture<SendResult<String, String>> future =
                    kafkaTemplate.send(topic, key, jsonMessage);

            future.whenComplete((result, ex) -> {
                if (ex == null) {
                    log.info("消息发送成功, topic: {}, partition: {}, offset: {}",
                            result.getRecordMetadata().topic(),
                            result.getRecordMetadata().partition(),
                            result.getRecordMetadata().offset());
                } else {
                    log.error("消息发送失败, topic: {}, key: {}", topic, key, ex);
                }
            });

            return true;
        } catch (Exception e) {
            log.error("发送消息异常, topic: {}, key: {}", topic, key, e);
            return false;
        }
    }

    /**
     * 发送消息到指定Topic（自动生成key）
     *
     * @param topic   Topic名称
     * @param message 消息内容
     * @return 是否发送成功
     */
    public boolean sendMessage(String topic, Object message) {
        String key = String.valueOf(System.currentTimeMillis());
        return sendMessage(topic, key, message);
    }

    /**
     * 同步发送消息（阻塞等待发送结果）
     *
     * @param topic   Topic名称
     * @param key     消息Key
     * @param message 消息内容
     * @return 是否发送成功
     */
    public boolean sendMessageSync(String topic, String key, Object message) {
        try {
            String jsonMessage = JSON.toJSONString(message);
            log.info("同步发送消息到Kafka, topic: {}, key: {}", topic, key);

            CompletableFuture<SendResult<String, String>> future =
                    kafkaTemplate.send(topic, key, jsonMessage);

            SendResult<String, String> result = future.get();
            log.info("同步发送消息成功, topic: {}, partition: {}, offset: {}",
                    result.getRecordMetadata().topic(),
                    result.getRecordMetadata().partition(),
                    result.getRecordMetadata().offset());

            return true;
        } catch (Exception e) {
            log.error("同步发送消息失败, topic: {}, key: {}", topic, key, e);
            return false;
        }
    }
}
