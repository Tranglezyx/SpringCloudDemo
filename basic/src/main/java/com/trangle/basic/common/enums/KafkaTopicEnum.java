package com.trangle.basic.common.enums;

import lombok.Getter;

/**
 * Kafka Topic 枚举类
 * 定义所有Kafka Topic及其相关配置
 */
@Getter
public enum KafkaTopicEnum {

    /**
     * 用户保存Topic
     */
    SYS_USER_SAVE(
            "sys_user_save",
            "sys_user_save_group",
            10,
            1,
            "用户保存队列，用于异步处理用户新增操作"
    );

    /**
     * Topic名称
     */
    private final String topicName;

    /**
     * 消费组ID
     */
    private final String consumerGroup;

    /**
     * 分区数
     */
    private final int partitions;

    /**
     * 副本数
     */
    private final int replicationFactor;

    /**
     * 描述信息
     */
    private final String description;

    KafkaTopicEnum(String topicName, String consumerGroup, int partitions,
                   int replicationFactor, String description) {
        this.topicName = topicName;
        this.consumerGroup = consumerGroup;
        this.partitions = partitions;
        this.replicationFactor = replicationFactor;
        this.description = description;
    }

    /**
     * 根据topic名称获取枚举
     *
     * @param topicName topic名称
     * @return KafkaTopicEnum
     */
    public static KafkaTopicEnum getByTopicName(String topicName) {
        for (KafkaTopicEnum topicEnum : values()) {
            if (topicEnum.getTopicName().equals(topicName)) {
                return topicEnum;
            }
        }
        return null;
    }
}
