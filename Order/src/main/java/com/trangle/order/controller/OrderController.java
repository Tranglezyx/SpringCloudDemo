package com.trangle.order.controller;

import com.trangle.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
    public Object getOrder(HttpServletRequest request, Long id) throws InterruptedException {
        int port = request.getServerPort();
        return "order - " + id + " -- port : " + request.getServerPort();
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
