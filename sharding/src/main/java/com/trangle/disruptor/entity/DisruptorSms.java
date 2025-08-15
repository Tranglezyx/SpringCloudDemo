package com.trangle.disruptor.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author: zhouyunxiang
 * @Date: 2025/8/14 17:39
 * @Description:
 */
@TableName("disruptor_sms")
@Data
public class DisruptorSms {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String value;
}
