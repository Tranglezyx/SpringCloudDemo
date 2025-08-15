package com.trangle.disruptor.disruptor;

import com.lmax.disruptor.RingBuffer;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: zhouyunxiang
 * @Date: 2025/8/14 11:54
 * @Description:
 */
@Slf4j
public class DisruptorManager {

    /**
     * 获取未消费总量
     *
     * @param name
     * @return
     */
    public static long getTotal(String name) {
        RingBuffer<? extends DisruptorBatchEvent> ringBuffer = DisruptorHolder.getRingBuffer(name);
        long cursor = ringBuffer.getCursor();
        long minimumGatingSequence = ringBuffer.getMinimumGatingSequence();
        return cursor - minimumGatingSequence;
    }
}
