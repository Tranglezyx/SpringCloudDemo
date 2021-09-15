package com.trangle.order.service;

/**
 * @author trangle
 */
public interface OrderService {

    /**
     * 支付订单
     *
     * @param id
     * @return
     */
    Boolean payMoney(Long id);
}
