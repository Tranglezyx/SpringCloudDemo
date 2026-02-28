package com.trangle.basic.sys.consumer;

import com.alibaba.fastjson2.JSON;
import com.trangle.basic.common.util.IdempotentUtil;
import com.trangle.basic.sys.dto.SysUserSaveMessage;
import com.trangle.basic.sys.entity.SysUser;
import com.trangle.basic.sys.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * 用户保存消息消费者
 * Topic: sys_user_save
 * GroupId: sys_user_save_group
 * 并发线程数: 10
 */
@Slf4j
@Component
public class SysUserSaveConsumer {

    /**
     * Topic名称常量
     */
    public static final String TOPIC = "sys_user_save";

    /**
     * 消费组ID常量
     */
    public static final String GROUP_ID = "sys_user_save_group";

    /**
     * 并发消费线程数
     */
    public static final int CONCURRENCY = 10;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private IdempotentUtil idempotentUtil;

    /**
     * 消费用户保存消息
     * 使用10个线程并发消费
     *
     * @param record 消息记录
     */
    @KafkaListener(
            topics = TOPIC,
            groupId = GROUP_ID,
            concurrency = "10"
    )
    public void consume(ConsumerRecord<String, String> record) {
        String topic = record.topic();
        int partition = record.partition();
        long offset = record.offset();
        String key = record.key();
        String value = record.value();

        log.info("收到Kafka消息, topic: {}, partition: {}, offset: {}, key: {}",
                topic, partition, offset, key);

        try {
            // 解析消息
            SysUserSaveMessage message = JSON.parseObject(value, SysUserSaveMessage.class);
            
            if (message == null || message.getMessageId() == null) {
                log.error("消息格式错误，缺少messageId, value: {}", value);
                return;
            }

            // 幂等校验
            String idempotentKey = "sys_user_save:" + message.getMessageId();
            boolean isFirstRequest = idempotentUtil.checkAndSetIdempotent(idempotentKey);
            
            if (!isFirstRequest) {
                log.warn("消息重复消费，跳过处理, messageId: {}", message.getMessageId());
                return;
            }

            // 执行业务逻辑
            processUserSave(message);

            log.info("消息处理成功, messageId: {}", message.getMessageId());

        } catch (Exception e) {
            log.error("消息处理异常, topic: {}, partition: {}, offset: {}",
                    topic, partition, offset, e);
            // 这里可以添加重试逻辑或发送到死信队列
        }
    }

    /**
     * 处理用户保存逻辑
     *
     * @param message 用户保存消息
     */
    private void processUserSave(SysUserSaveMessage message) {
        log.info("开始处理用户保存, account: {}, mobile: {}", message.getAccount(), message.getMobile());

        // 构建用户实体
        SysUser user = new SysUser();
        user.setAccount(message.getAccount());
        user.setMobile(message.getMobile());
        user.setPassword(message.getPassword());
        user.setNickName(message.getNickName());
        user.setCreateTime(System.currentTimeMillis());
        user.setUpdateTime(System.currentTimeMillis());

        // 保存用户
        boolean success = sysUserService.save(user);
        
        if (success) {
            log.info("用户保存成功, userId: {}", user.getId());
        } else {
            log.error("用户保存失败, account: {}", message.getAccount());
            throw new RuntimeException("用户保存失败");
        }
    }
}