package com.trangle.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.google.common.collect.Lists;
import com.trangle.constant.ThreadPoolConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @Author: zhouyunxiang
 * @Date: 2024/12/12 20:39
 * @Description:
 */
@RestController
@RequestMapping("/http")
@Slf4j
public class HttpRequestController {

    @PostMapping("/request")
    public String request(@RequestBody String request) {
        String url = "192.168.11.131:31285/mock/messaging/group/v1/outbound/sip:10681785700490@botplatform.rcs.chinamobile.com/requests";
//        String url = "127.0.0.1:8185/mock/messaging/group/v1/outbound/sip:10681785700490@botplatform.rcs.chinamobile.com/requests";

        StopWatch stopWatch = new StopWatch();

        int forNum = 20;
        int num = 100000;
        for (int j = 1; j <= forNum; j++) {
            stopWatch.start(StrUtil.format("第{}次任务执行", j));
            List<String> list = new ArrayList<>();
            for (int i = 0; i < num; i++) {
                list.add(request);
            }
            int batchSize = 1000;
            List<List<String>> partition = Lists.partition(list, batchSize);
            long start = System.currentTimeMillis();
            CountDownLatch latch = new CountDownLatch(partition.size());
            for (List<String> stringList : partition) {
                ThreadPoolConstants.DEFAULT_POOL.execute(() -> post(url, stringList, latch));
            }
            try {
                latch.await();
                log.info("调用完毕,耗时:{}ms,size:{}", System.currentTimeMillis() - start, num);
            } catch (InterruptedException e) {

            }
            stopWatch.stop();
        }
        double totalTimeSeconds = stopWatch.getTotalTimeSeconds();
        double speed = forNum * num / totalTimeSeconds;
        log.info("执行结束,平均速度:{} ,详情:{}", speed, stopWatch.prettyPrint());
        return "success";
    }

    private void post(String url, List<String> list, CountDownLatch latch) {
        try {
            for (String str : list) {
                String post = HttpUtil.post(url, str);
                log.info("post结果:{}", post);
            }
        } catch (Exception e) {
        } finally {
            latch.countDown();
        }
    }
}
