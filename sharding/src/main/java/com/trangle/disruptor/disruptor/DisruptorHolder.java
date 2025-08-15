package com.trangle.disruptor.disruptor;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import cn.hutool.core.util.StrUtil;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.lmax.disruptor.WorkHandler;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.ehcache.impl.internal.concurrent.ConcurrentHashMap;

import java.util.Map;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Author: zhouyunxiang
 * @Date: 2025/8/13 11:40
 * @Description:
 */
@Slf4j
public class DisruptorHolder {

    private static final AtomicBoolean RUN = new AtomicBoolean(true);
    private static final Map<String, Disruptor<? extends DisruptorBatchEvent>> DISRUPTOR_MAP = new ConcurrentHashMap<>();

    public static void initDisruptor(String name,
                                     int bufferSize,
                                     int threadNum,
                                     WorkHandler<? extends DisruptorBatchEvent> workHandler,
                                     Class<? extends DisruptorEventFactory> factoryClazz) throws InstantiationException, IllegalAccessException {
        if (DISRUPTOR_MAP.containsKey(name)) {
            throw new RuntimeException(StrUtil.format("disruptor-{}存在重复注册", name));
        }
        DisruptorEventFactory eventFactory = null;
        try {
            eventFactory = factoryClazz.newInstance();
        } catch (Exception e) {
            log.error("disruptor factory类构建失败,error:", e);
            throw e;
        }
        Disruptor<? extends DisruptorBatchEvent> disruptor = new Disruptor<>(
                eventFactory,
                bufferSize,
//                new DisruptorDefaultThreadFactory(name),
                new ThreadPoolExecutor(1,
                        10,
                        60,
                        TimeUnit.SECONDS,
                        new SynchronousQueue<Runnable>(),
                        ThreadFactoryBuilder.create().setNamePrefix(name + "-").build(),
                        new ThreadPoolExecutor.AbortPolicy()),
                // 单生产者
                ProducerType.SINGLE,
                // 等待策略
                new SleepingWaitStrategy()
        );

        WorkHandler[] array = new WorkHandler[threadNum];
        for (int i = 0; i < threadNum; i++) {
            array[i] = workHandler;
        }
        disruptor.handleEventsWithWorkerPool(array);

        disruptor.start();
        DISRUPTOR_MAP.put(name, disruptor);
        log.info("disruptor-{}启动成功", name);
    }

    public static RingBuffer<? extends DisruptorBatchEvent> getRingBuffer(String name) {
        Disruptor<? extends DisruptorBatchEvent> disruptor = DISRUPTOR_MAP.get(name);
        if (disruptor == null) {
            throw new RuntimeException(StrUtil.format("disruptor-{}未注册", name));
        }
        return disruptor.getRingBuffer();
    }

    public static void stop() {
        RUN.set(false);
        if (MapUtils.isEmpty(DISRUPTOR_MAP)) {
            return;
        }
        for (Map.Entry<String, Disruptor<? extends DisruptorBatchEvent>> entry : DISRUPTOR_MAP.entrySet()) {
            Disruptor<? extends DisruptorBatchEvent> value = entry.getValue();
            value.shutdown();
            log.info("disruptor-{}-停止成功", entry.getKey());
        }
    }

    public static boolean isRunning() {
        return RUN.get();
    }
}
