package com.trangle.dto;

import java.math.BigDecimal;

/**
 * @author trangle
 */
public class PayMoneyDTO {

    private Long orderId;
    private BigDecimal money;

    public PayMoneyDTO() {
    }

    public PayMoneyDTO(Long orderId, BigDecimal money) {
        this.orderId = orderId;
        this.money = money;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }
}
