package com.trangle.disruptor.controller;

import cn.hutool.core.util.RandomUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: zhouyunxiang
 * @Date: 2025/8/12 16:17
 * @Description:
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @PostMapping("/get")
    public String get(@RequestBody String json) {
        return RandomUtil.randomString(10) + json;
    }
}
