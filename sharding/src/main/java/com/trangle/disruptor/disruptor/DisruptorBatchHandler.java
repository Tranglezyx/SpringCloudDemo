package com.trangle.disruptor.disruptor;

import java.util.List;

public interface DisruptorBatchHandler<T> {

    /**
     * 业务处理
     *
     * @param list
     */
    void handle(List<T> list);

    /**
     * 持久化处理
     *
     * @param list
     */
    void persistence(List<T> list);

    /**
     * 持久化加载
     *
     * @return
     */
    List<T> reLoading();
}
