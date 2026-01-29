package com.trangle.disruptor.listener;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.trangle.disruptor.disruptor.DisruptorBatchHandler;
import com.trangle.disruptor.disruptor.DisruptorBatchListener;
import com.trangle.disruptor.entity.DisruptorSms;
import com.trangle.disruptor.mapper.DisruptorSmsMapper;
import com.trangle.disruptor.model.CmppSmsDto;
import com.trangle.disruptor.model.CmppSmsDtoBatchEvent;
import lombok.extern.slf4j.Slf4j;
//import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author: zhouyunxiang
 * @Date: 2025/8/13 17:37
 * @Description:
 */
@Component
@Slf4j
@DisruptorBatchListener(name = "DisruptorMonitor", total = 32, thread = 2, modelFactory = CmppSmsDtoBatchEvent.class)
public class DisruptorMonitor implements DisruptorBatchHandler<CmppSmsDto> {

    private static AtomicLong num = new AtomicLong(0);
    private static final String KEY_TEMPLATE = "dis:per:{}";
    private static final String QUEUE_NAME = "qqq";

//    @Resource
//    private RedisTemplate redisTemplate;
    @Resource
    private DisruptorSmsMapper smsMapper;

    @Override
    public void handle(List<CmppSmsDto> list) {
        String nanoId = IdUtil.nanoId(10);
        String key = StrUtil.format(KEY_TEMPLATE, QUEUE_NAME);
        log.info("模拟处理消息开始 >>> traceId:{}, size:{}", nanoId, list.size());
        for (CmppSmsDto cmppSmsDto : list) {
//            log.info("模拟处理消息 >>> {} - {}", cmppSmsDto.getDestId(), cmppSmsDto.getContent());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {

            }
        }
        log.info("模拟处理消息结束 >>> traceId:{}, size:{}", nanoId, list.size());
    }

    @Override
    public void persistence(List<CmppSmsDto> list) {
        String key = StrUtil.format(KEY_TEMPLATE, QUEUE_NAME);
        String nanoId = IdUtil.nanoId(10);
        log.info("准备持久化消息 >>> traceId:{}, size:{}", nanoId, list.size());
        for (CmppSmsDto cmppSmsDto : list) {
            String jsonString = JSONObject.toJSONString(cmppSmsDto);
            DisruptorSms disruptorSms = new DisruptorSms();
            disruptorSms.setValue(jsonString);
            smsMapper.insert(disruptorSms);
        }
        log.info("持久化消息结束 >>> traceId:{}", nanoId);
    }

    @Override
    public List<CmppSmsDto> reLoading() {
        log.info("准备加载持久化消息 >>> ");
        List<CmppSmsDto> list = new ArrayList<>();
        log.info("加载持久化消息完成 >>> ");
        for (int i = 0; i < 100; i++) {
            CmppSmsDto cmppSmsDto = new CmppSmsDto();
            cmppSmsDto.setDestId(String.valueOf(RandomUtil.randomLong(13100000000L, 19999999999L)));
            cmppSmsDto.setContent(RandomUtil.randomString(60));
            list.add(cmppSmsDto);
        }
        return list;
    }
}
