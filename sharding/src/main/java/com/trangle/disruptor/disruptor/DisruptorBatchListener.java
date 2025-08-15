package com.trangle.disruptor.disruptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DisruptorBatchListener {

    /**
     * kafka topic
     *
     * @return
     */
    String kafkaTopic() default "";

    /**
     * kafka group
     *
     * @return
     */
    String kafkaGroup() default "";

    /**
     * 队列名称
     *
     * @return
     */
    String name();

    /**
     * 队列容量上限
     *
     * @return
     */
    int total() default 10;

    /**
     * 最大线程数量
     *
     * @return
     */
    int thread() default 1;

    Class<? extends DisruptorEventFactory> modelFactory();
}
