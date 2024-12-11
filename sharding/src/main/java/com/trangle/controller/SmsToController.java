package com.trangle.controller;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.trangle.constant.ThreadPoolConstants;
import com.trangle.entity.SmsTo;
import com.trangle.mapper.SmsToMapper;
import com.trangle.util.JSONUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@RestController
@RequestMapping("/smsTo")
@Slf4j
public class SmsToController {

    @Resource
    private SmsToMapper smsToMapper;

    @PostMapping("/insert")
    public String insert(@RequestBody SmsTo smsTo) {
        smsTo.setId(new Snowflake().nextId());
        smsToMapper.insert(smsTo);

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
            log.error("新增成功,json={}", JSONUtils.toJSONString(o));
        }
        latch.countDown();
    }

    @PostMapping("/select")
    public List<SmsTo> select(@RequestBody SmsTo smsTo) {
        return smsToMapper.selectList(Wrappers.<SmsTo>lambdaQuery()
                .ge(SmsTo::getSubmitDate, smsTo.getSubmitDate())
                .le(SmsTo::getSubmitDate, smsTo.getSubmitDate())
                .eq(StringUtils.isNotBlank(smsTo.getPhone()), SmsTo::getPhone, smsTo.getPhone()));
    }
}
