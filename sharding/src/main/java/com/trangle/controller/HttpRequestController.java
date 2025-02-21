package com.trangle.controller;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.google.common.collect.Lists;
import com.trangle.constant.ThreadPoolConstants;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okio.BufferedSink;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
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

    @PostMapping("/receive")
    public String receive(@RequestBody String request){
//        log.info("receive:{}", request);
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {

        }
        return "success";
    }

    @PostMapping("/localRequest")
    public String localRequest(@RequestBody String request) {
        String url = "http://192.168.40.98:8080/http/receive";
//        String url = "127.0.0.1:8185/mock/messaging/group/v1/outbound/sip:10681785700490@botplatform.rcs.chinamobile.com/requests";

        StopWatch stopWatch = new StopWatch();

        int forNum = 10;
        int num = 10000;
        List<String> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            list.add(request);
        }
        int batchSize = 100;
        for (int j = 1; j <= forNum; j++) {
            stopWatch.start(StrUtil.format("第{}次任务执行", j));
            List<List<String>> partition = Lists.partition(list, batchSize);
            long start = System.currentTimeMillis();
            CountDownLatch latch = new CountDownLatch(partition.size());
            for (List<String> stringList : partition) {
//                ThreadPoolConstants.DEFAULT_POOL.execute(() -> postWithHutool(url, stringList, latch));
                ThreadPoolConstants.DEFAULT_POOL.execute(() -> postWithOkHttp(url, stringList, latch));
//                ThreadPoolConstants.DEFAULT_POOL.execute(() -> postWithSyncOkHttp(url, stringList, latch));
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

    @PostMapping("/request")
    public String request(@RequestBody String request) {
        String url = "http://192.168.11.131:31286/mock/messaging/group/v1/outbound/sip:10681785700490@botplatform.rcs.chinamobile.com/requests";
//        String url = "127.0.0.1:8185/mock/messaging/group/v1/outbound/sip:10681785700490@botplatform.rcs.chinamobile.com/requests";

        StopWatch stopWatch = new StopWatch();

        int forNum = 10;
        int num = 10000;
        List<String> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            list.add(request);
        }
        int batchSize = 100;
        for (int j = 1; j <= forNum; j++) {
            stopWatch.start(StrUtil.format("第{}次任务执行", j));
            List<List<String>> partition = Lists.partition(list, batchSize);
            long start = System.currentTimeMillis();
            CountDownLatch latch = new CountDownLatch(partition.size());
            for (List<String> stringList : partition) {
//                ThreadPoolConstants.DEFAULT_POOL.execute(() -> postWithHutool(url, stringList, latch));
                ThreadPoolConstants.DEFAULT_POOL.execute(() -> postWithOkHttp(url, stringList, latch));
//                ThreadPoolConstants.DEFAULT_POOL.execute(() -> postWithSyncOkHttp(url, stringList, latch));
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

    private void postWithHutool(String url, List<String> list, CountDownLatch latch) {
        try {
            for (String str : list) {
                String post = HttpUtil.post(url, str);
            }
        } catch (Exception e) {
        } finally {
            latch.countDown();
        }
    }

    private static OkHttpClient okHttpClient = null;
    private static final Dispatcher dispatcher = new Dispatcher();

    static {
        dispatcher.setMaxRequests(128);
        dispatcher.setMaxRequestsPerHost(64);
        okHttpClient = new OkHttpClient.Builder()
                .dispatcher(dispatcher)
                .build();
    }


    private void postWithOkHttp(String url, List<String> list, CountDownLatch latch) {
        try {
            MediaType JSON = MediaType.get("application/json; charset=utf-8");
            for (String str : list) {
                Request request = new Request.Builder().url(url).post(okhttp3.RequestBody.create(str, JSON)).build();
                Response response = okHttpClient.newCall(request).execute();
                String post = response.body().string();
//                log.info("结果，result={}", post);
            }
        } catch (Exception e) {
            log.error("出错,error:", e);
        } finally {
            latch.countDown();
        }
    }

    private void postWithSyncOkHttp(String url, List<String> list, CountDownLatch latch) {
        try {

            MediaType JSON = MediaType.get("application/json; charset=utf-8");
            for (String str : list) {
                Request request = new Request.Builder().url(url).post(okhttp3.RequestBody.create(str, JSON)).build();
                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        log.error("出错,error:", e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) {
                        try {
                            String post = response.body().string();
//                            log.info("结果，result={}", post);
                        } catch (IOException e) {
                            log.error("出错,error:", e);
                        }
//                        try (ResponseBody responseBody = response.body()) {
//                            String responseText = responseBody.string();
//
//                        } catch (IOException e) {
//                            log.error("出错,error:", e);
//                        }
                    }
                });
            }
        } catch (Exception e) {
            log.error("出错,error:", e);
        } finally {
            latch.countDown();
        }
    }
}
