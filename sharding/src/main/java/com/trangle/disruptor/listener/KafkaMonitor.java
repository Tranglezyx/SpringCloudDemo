package com.trangle.disruptor.listener;

import com.alibaba.fastjson.JSONObject;
import com.danmi.captcha.disruptor.DisruptorProducer;
import com.danmi.captcha.model.CmppSmsDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author: zhouyunxiang
 * @Date: 2025/8/13 17:29
 * @Description:
 */
@Component
@Slf4j
public class KafkaMonitor {

    @KafkaListener(groupId = "group-1", concurrency = "1", topics = "test",properties = "")
    public void consumer(List<String> request, Acknowledgment ack) {
        try {
            List<CmppSmsDto> collect = request.stream().map(record -> JSONObject.parseObject(record, CmppSmsDto.class))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            DisruptorProducer.produce("DisruptorMonitor", collect);
            log.info("kafka消费者生产disruptor消息完成,size:{}", collect.size());
        } catch (Exception e) {
            log.error("消费失败,error:", e);
        } finally {
            ack.acknowledge();//手动提交偏移量
        }

    }
}
