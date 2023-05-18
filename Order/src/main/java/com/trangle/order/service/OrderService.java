package com.trangle.order.service;

import com.trangle.order.entity.OrderBasic;

import java.util.List;

/**
 * @author trangle
 */
public interface OrderService {

    void addOrder(OrderBasic orderBasic);

    void addOrderWithFeign(OrderBasic orderBasic);

    void addAndSendTransactionMessage(OrderBasic orderBasic);

    List<OrderBasic> getOrderList();
}
