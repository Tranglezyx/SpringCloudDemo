package com.trangle.order.controller;

import com.trangle.dto.ResultDTO;
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
    public ResultDTO<List<OrderBasic>> getOrder(HttpServletRequest request) throws InterruptedException {
        return ResultDTO.success(orderService.getOrderList()) ;
    }

    @PostMapping("/add")
    public ResultDTO<Void> payMoney(@RequestBody OrderBasic orderBasic) {
        orderService.addOrder(orderBasic);
        return ResultDTO.success();
    }

    @PostMapping("/addAndSendTransactionMessage")
    public ResultDTO<Void> addAndSendTransactionMessage(@RequestBody OrderBasic orderBasic) {
        orderService.addAndSendTransactionMessage(orderBasic);
        return ResultDTO.success();
    }

    @PostMapping("/addOrderWithFeign")
    public ResultDTO<Void> addOrderWithFeign(HttpServletRequest request,@RequestBody OrderBasic orderBasic) {
        orderService.addOrderWithFeign(orderBasic);
        return ResultDTO.success();
    }
}
