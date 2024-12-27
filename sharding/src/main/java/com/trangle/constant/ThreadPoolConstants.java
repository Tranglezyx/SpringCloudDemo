package com.trangle.constant;

import cn.hutool.core.thread.ThreadFactoryBuilder;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: zhouyunxiang
 * @Date: 2024/12/11 11:05
 * @Description:
 */
public class ThreadPoolConstants {

    public static final ThreadPoolExecutor DEFAULT_POOL = new ThreadPoolExecutor(300,
            300,
            60,
            TimeUnit.SECONDS,
            new SynchronousQueue<Runnable>(),
            ThreadFactoryBuilder.create().setNamePrefix("default-thread-pool-").build(),
            new ThreadPoolExecutor.AbortPolicy());
}
