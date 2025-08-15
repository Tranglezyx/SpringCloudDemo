package com.trangle.disruptor.disruptor;

import com.lmax.disruptor.RingBuffer;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @Author: zhouyunxiang
 * @Date: 2025/8/13 17:15
 * @Description:
 */
@Slf4j
public class DisruptorProducer<T> {

    public static <T> void produce(String name, List<T> list) {
        RingBuffer<? extends DisruptorBatchEvent> ringBuffer = DisruptorHolder.getRingBuffer(name);
        long sequence = ringBuffer.next();
        try {
            DisruptorBatchEvent event = ringBuffer.get(sequence);
            event.setList(list);
        } catch (Exception e) {
            log.error("disruptor-{}发布消息失败,error=", name, e);
        } finally {
            ringBuffer.publish(sequence);
        }
    }
}
