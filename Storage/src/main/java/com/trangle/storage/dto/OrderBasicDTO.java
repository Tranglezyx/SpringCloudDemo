package com.trangle.storage.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderBasicDTO {

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
