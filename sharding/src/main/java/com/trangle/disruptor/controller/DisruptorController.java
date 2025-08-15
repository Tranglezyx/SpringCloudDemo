package com.trangle.disruptor.controller;

import com.trangle.disruptor.disruptor.DisruptorManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: zhouyunxiang
 * @Date: 2025/8/13 17:24
 * @Description:
 */
@RestController
@RequestMapping("/disruptor")
public class DisruptorController {

    @GetMapping("/total")
    public Long getTotal(@RequestParam("name") String name) {
        return DisruptorManager.getTotal(name);
    }
}
