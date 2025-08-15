package com.trangle.disruptor.disruptor;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.lmax.disruptor.WorkHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: zhouyunxiang
 * @Date: 2025/8/13 16:09
 * @Description:
 */
@Slf4j
public class DisruptorEventDefaultWorkHandler implements WorkHandler<DisruptorBatchEvent> {

    private DisruptorBatchHandler batchHandler;

    public DisruptorEventDefaultWorkHandler(DisruptorBatchHandler batchHandler) {
        this.batchHandler = batchHandler;
    }

    @Override
    public void onEvent(DisruptorBatchEvent disruptorBatchEvent) {
        if (DisruptorHolder.isRunning()) {
            batchHandler.handle(disruptorBatchEvent.getList());
        } else {
            String nanoId = IdUtil.nanoId(10);
            log.warn("disruptor数据准备持久化,traceId={}", nanoId);
            try {
                batchHandler.persistence(disruptorBatchEvent.getList());
            } catch (Exception e) {
                log.error("disruptor持久化失败,traceId={},data={}", nanoId, JSONObject.toJSONString(disruptorBatchEvent));
            }
            log.warn("disruptor数据持久化完成,traceId={}", nanoId);
        }
    }
}
