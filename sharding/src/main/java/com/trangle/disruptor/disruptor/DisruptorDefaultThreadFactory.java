package com.trangle.disruptor.disruptor;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: zhouyunxiang
 * @Date: 2025/8/13 15:03
 * @Description:
 */
@Slf4j
public class DisruptorDefaultThreadFactory implements ThreadFactory {

    private final AtomicInteger counter = new AtomicInteger(1);

    private String name;

    public DisruptorDefaultThreadFactory(String name) {
        this.name = name;
    }

    public DisruptorDefaultThreadFactory() {
    }

    @Override
    public Thread newThread(Runnable r) {
        String threadName = name + "-" + counter.getAndIncrement();
        log.info("disruptor构建处理线程 >>> {}", threadName);
        Thread t = new Thread(r, threadName);
        return t;
    }
}
