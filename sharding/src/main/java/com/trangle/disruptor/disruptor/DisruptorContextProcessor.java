package com.trangle.disruptor.disruptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

/**
 * @Author: zhouyunxiang
 * @Date: 2025/8/13 18:18
 * @Description:
 */
@Component
@Slf4j
public class DisruptorContextProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (!bean.getClass().isAnnotationPresent(DisruptorBatchListener.class)) {
            return bean;
        }
        if (!(bean instanceof DisruptorBatchHandler)) {
            return bean;
        }
        DisruptorBatchListener listener = bean.getClass().getAnnotation(DisruptorBatchListener.class);
        String kafkaTopic = listener.kafkaTopic();
        if (StringUtils.isNotEmpty(kafkaTopic)) {
            // kafka消息消费者注册
        }
        try {
            DisruptorHolder.initDisruptor(listener.name(),
                    listener.total(),
                    listener.thread(),
                    new DisruptorEventDefaultWorkHandler((DisruptorBatchHandler) bean),
                    listener.modelFactory());
        } catch (Exception e) {
            log.error("disruptor factory类构建失败,error:", e);
            throw new RuntimeException(e);
        }
        log.info("disruptor初始化成功 >>> ");
        return bean;
    }

    @PreDestroy
    public void destroy() {
        log.info("收到服务停机信息，准备持久化disruptor队列 >>> ");
        DisruptorHolder.stop();
        try {
            Thread.sleep(5 * 1000);
        } catch (InterruptedException e) {

        }
    }
}
