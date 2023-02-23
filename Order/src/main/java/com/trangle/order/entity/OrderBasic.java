package com.trangle.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("order_basic")
public class OrderBasic {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    /**
     * id
     */
    private Long id;

    private Long userId;

    /**
     * order_id
     */
    private String orderId;


    /**
     * order_date
     */
    private LocalDateTime orderDate;

    private Long goodsId;

    /**
     * goods_num
     */
    private Integer goodsNum;

    /**
     * money
     */
    private BigDecimal money;
}
