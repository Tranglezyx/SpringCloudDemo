package com.trangle.order.controller;

import com.trangle.order.entity.OrderBasic;
import com.trangle.order.service.OrderService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author trangle
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Resource
    private OrderService orderService;

    /**
     * 获取订单
     *
     * @return
     */
    @GetMapping("/list")
    public List<OrderBasic> getOrder(HttpServletRequest request) throws InterruptedException {
        return orderService.getOrderList();
    }

    @PostMapping("/add")
    public Boolean payMoney(@RequestBody OrderBasic orderBasic) {
        orderService.addOrder(orderBasic);
        return true;
    }
}
