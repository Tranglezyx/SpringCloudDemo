package com.trangle.order.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author trangle
 */
@Data
public class PayMoneyDTO {

    private String orderId;
    private BigDecimal money;

    private Long userId;

    public PayMoneyDTO() {
    }

    public PayMoneyDTO(Long userId, String orderId, BigDecimal money) {
        this.userId = userId;
        this.orderId = orderId;
        this.money = money;
    }
}
