package com.trangle.order.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author trangle
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @GetMapping("/get")
    public Object getOrder(Long id){
        return "order -" + id;
    }

    @GetMapping("/test-get")
    public Object getOrder1(Long id){
        System.out.println("测试 --- 测试 ");
        return "order -" + id;
    }
}
