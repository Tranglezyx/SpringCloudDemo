package com.trangle.pay.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("pay_detail")
public class PayDetail {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    /**
     * id
     */
    private Long id;

    /**
     * pay_money
     */
    private BigDecimal payMoney;

    /**
     * pay_time
     */
    private LocalDateTime payTime;

    /**
     * order_id
     */
    private String orderId;

    /**
     * user_id
     */
    private Long userId;
}
