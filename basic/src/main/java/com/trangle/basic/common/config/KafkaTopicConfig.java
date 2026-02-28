package com.trangle.basic.common.config;

import com.trangle.basic.common.enums.KafkaTopicEnum;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Kafka Topic 配置类
 * 服务启动时自动创建所需的Topic
 */
@Slf4j
@Configuration
public class KafkaTopicConfig {

    /**
     * 定义KafkaAdmin Bean
     *
     * @return KafkaAdmin
     */
//    @Bean
//    public KafkaAdmin kafkaAdmin() {
//        return new KafkaAdmin(Collections.emptyMap());
//    }

    /**
     * 创建sys_user_save Topic
     *
     * @return NewTopic
     */
    @Bean
    public NewTopic sysUserSaveTopic() {
        return TopicBuilder.name(KafkaTopicEnum.SYS_USER_SAVE.getTopicName())
                .partitions(KafkaTopicEnum.SYS_USER_SAVE.getPartitions())
                .replicas(KafkaTopicEnum.SYS_USER_SAVE.getReplicationFactor())
                .compact()
                .build();
    }

    /**
     * 服务启动时自动创建所有Topic
     *
     * @param kafkaAdmin KafkaAdmin
     * @return CommandLineRunner
     */
    @Bean
    public CommandLineRunner createTopics(KafkaAdmin kafkaAdmin) {
        return args -> {
            log.info("开始检查并创建Kafka Topics...");
            
            try (AdminClient adminClient = AdminClient.create(kafkaAdmin.getConfigurationProperties())) {
                // 获取已存在的Topic列表
                ListTopicsResult listTopicsResult = adminClient.listTopics();
                Set<String> existingTopics = listTopicsResult.names()
                        .get(30, TimeUnit.SECONDS);
                
                log.info("已存在的Topics: {}", existingTopics);
                
                // 遍历所有枚举定义，检查并创建缺失的Topic
                for (KafkaTopicEnum topicEnum : KafkaTopicEnum.values()) {
                    String topicName = topicEnum.getTopicName();
                    
                    if (existingTopics.contains(topicName)) {
                        log.info("Topic [{}] 已存在，跳过创建", topicName);
                    } else {
                        log.info("创建Topic: {}, 分区数: {}, 副本数: {}",
                                topicName, topicEnum.getPartitions(), topicEnum.getReplicationFactor());
                        
                        NewTopic newTopic = TopicBuilder.name(topicName)
                                .partitions(topicEnum.getPartitions())
                                .replicas(topicEnum.getReplicationFactor())
                                .build();
                        
                        adminClient.createTopics(Collections.singleton(newTopic))
                                .all()
                                .get(30, TimeUnit.SECONDS);
                        
                        log.info("Topic [{}] 创建成功", topicName);
                    }
                }
                
                log.info("Kafka Topics检查完成");
                
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                log.error("创建Kafka Topics失败: {}", e.getMessage(), e);
                // 不抛出异常，让应用继续启动，因为Topic可能已经存在或者稍后手动创建
            }
        };
    }
}
