package com.trangle.order.controller;

import com.trangle.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author trangle
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 获取订单
     *
     * @param id
     * @return
     */
    @GetMapping("/get")
    public Object getOrder(Long id) {
        return "order -" + id;
    }

    /**
     * 支付订单
     *
     * @param id
     * @return
     */
    @PostMapping("/payMoney")
    public Boolean payMoney(@RequestBody Long id) {
        return orderService.payMoney(id);
    }
}
