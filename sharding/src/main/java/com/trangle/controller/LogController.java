package com.trangle.controller;

import com.trangle.constant.ThreadPoolConstants;
import com.trangle.entity.SmsTo;
import com.trangle.util.JSONUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CountDownLatch;

/**
 * @Author: zhouyunxiang
 * @Date: 2024/12/12 18:24
 * @Description:
 */
@RestController
@RequestMapping("/log")
@Slf4j
public class LogController {

    @PostMapping("/test")
    public String insert(@RequestBody SmsTo smsTo) {
        int count = 100;
        CountDownLatch latch = new CountDownLatch(count);
        for (int i = 0; i < count; i++) {
            String traceId = MDC.get("traceId");
            ThreadPoolConstants.DEFAULT_POOL.submit(() -> {
                MDC.put("traceId", traceId);
                log(latch, 5000, smsTo);
                MDC.clear();
            });
        }
        try {
            latch.await();
        } catch (InterruptedException e) {

        }
        return "success";
    }

    public void log(CountDownLatch latch, int size, Object o) {
        for (int i = 0; i < size; i++) {
            log.info("新增成功,json={}", JSONUtils.toJSONString(o));
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {

            }
            log.error("新增成功,json={}", JSONUtils.toJSONString(o));
        }
        latch.countDown();
    }
}
