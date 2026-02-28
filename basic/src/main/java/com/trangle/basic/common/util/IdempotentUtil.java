package com.trangle.basic.common.util;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 幂等性工具类
 * 基于Redis实现接口幂等性校验
 */
@Slf4j
@Component
public class IdempotentUtil {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Value("${idempotent.redis.prefix:idempotent:}")
    private String keyPrefix;

    @Value("${idempotent.redis.expire-seconds:3600}")
    private long expireSeconds;

    /**
     * 检查并设置幂等key
     * 如果key不存在，则设置成功并返回true
     * 如果key已存在，则返回false，表示重复请求
     *
     * @param uniqueKey 唯一标识（如订单号、请求ID等）
     * @return true-首次请求，false-重复请求
     */
    public boolean checkAndSetIdempotent(String uniqueKey) {
        String redisKey = keyPrefix + uniqueKey;
        
        try {
            // 使用setIfAbsent实现原子性操作
            Boolean success = redisTemplate.opsForValue()
                    .setIfAbsent(redisKey, "1", expireSeconds, TimeUnit.SECONDS);
            
            if (Boolean.TRUE.equals(success)) {
                log.info("幂等校验通过，key: {}", redisKey);
                return true;
            } else {
                log.warn("检测到重复请求，key: {}", redisKey);
                return false;
            }
        } catch (Exception e) {
            log.error("Redis幂等校验异常, key: {}", redisKey, e);
            // 发生异常时，为了安全起见，允许请求继续执行
            return true;
        }
    }

    /**
     * 删除幂等key（用于业务完成后手动清理）
     *
     * @param uniqueKey 唯一标识
     */
    public void removeIdempotentKey(String uniqueKey) {
        String redisKey = keyPrefix + uniqueKey;
        try {
            redisTemplate.delete(redisKey);
            log.info("删除幂等key: {}", redisKey);
        } catch (Exception e) {
            log.error("删除幂等key失败, key: {}", redisKey, e);
        }
    }

    /**
     * 检查key是否存在
     *
     * @param uniqueKey 唯一标识
     * @return true-存在，false-不存在
     */
    public boolean isKeyExists(String uniqueKey) {
        String redisKey = keyPrefix + uniqueKey;
        try {
            return Boolean.TRUE.equals(redisTemplate.hasKey(redisKey));
        } catch (Exception e) {
            log.error("检查key存在性异常, key: {}", redisKey, e);
            return false;
        }
    }
}
