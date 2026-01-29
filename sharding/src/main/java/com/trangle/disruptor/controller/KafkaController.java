package com.trangle.disruptor.controller;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import com.trangle.disruptor.model.CmppSmsDto;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
//import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: zhouyunxiang
 * @Date: 2025/8/12 17:37
 * @Description:
 */
@RestController
@RequestMapping("/kafka")
@Slf4j
public class KafkaController {

//    @Resource
//    private KafkaTemplate kafkaTemplate;
//    @Resource
//    private KafkaProperties kafkaProperties;

    @PostMapping("/produce")
    public void produce(@RequestBody ProduceDTO produceDTO) {
        String topic = produceDTO.getTopic();
        int num = produceDTO.getNum();
        int length = produceDTO.getLength();
        String nanoId = IdUtil.nanoId(10);
        log.info("traceId:{}", nanoId);
        for (int i = 0; i < num; i++) {
            CmppSmsDto cmppSmsDto = new CmppSmsDto();
            cmppSmsDto.setDestId(String.valueOf(RandomUtil.randomLong(13100000000L, 19999999999L)));
            cmppSmsDto.setContent(RandomUtil.randomString(60));
//            kafkaTemplate.send(topic, JSONObject.toJSONString(cmppSmsDto));
        }
    }

    @Data
    static class ProduceDTO {
        private String topic;
        private int num = 1000;
        private int length = 10;
    }
}
