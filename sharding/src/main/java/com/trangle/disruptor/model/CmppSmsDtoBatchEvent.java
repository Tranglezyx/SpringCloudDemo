package com.trangle.disruptor.model;

import com.danmi.captcha.disruptor.DisruptorBatchEvent;
import com.danmi.captcha.disruptor.DisruptorEventFactory;
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
