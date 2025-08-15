package com.trangle.disruptor.disruptor;

import lombok.Data;

import java.util.List;

/**
 * @Author: zhouyunxiang
 * @Date: 2025/8/13 13:58
 * @Description:
 */
@Data
public class DisruptorBatchEvent<T> {

    private List<T> list;
}
