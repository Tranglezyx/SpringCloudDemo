package com.trangle.disruptor.model;

import com.trangle.disruptor.disruptor.DisruptorBatchEvent;
import com.trangle.disruptor.disruptor.DisruptorEventFactory;
import lombok.Data;

/**
 * @Author: zhouyunxiang
 * @Date: 2025/8/13 17:55
 * @Description:
 */
@Data
public class CmppSmsDtoBatchEvent extends DisruptorBatchEvent<CmppSmsDto> implements DisruptorEventFactory {

    @Override
    public DisruptorBatchEvent newInstance() {
        return new CmppSmsDtoBatchEvent();
    }
}
