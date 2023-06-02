package com.example.expense.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 基于redis的分布式锁
 * @author GLaDOS
 * @date 2023/5/28 17:51
 */
@Slf4j
public class RedisLock {

    private final RedisTemplate<String, Object> redisTemplate;

    private final String key;

    private Long timeout = 10L;

    private RedisLock(String key, RedisTemplate<String, Object> redisTemplate) {
        this.key = key;
        this.redisTemplate = redisTemplate;
    }

    private RedisLock(String key, RedisTemplate<String, Object> redisTemplate, Long timeout) {
        this.key = key;
        this.redisTemplate = redisTemplate;
        this.timeout = timeout;
    }

    public Boolean lock() {
        log.info("获取锁：{} 中", key);
        return redisTemplate.opsForValue().setIfAbsent(key, "1", timeout, TimeUnit.SECONDS);
    }

    public Boolean del() {
        log.info("解锁: {} 中", key);
        return redisTemplate.delete(key);
    }

    public static RedisLock get(String key, RedisTemplate<String, Object> redisTemplate) {
        return new RedisLock(key, redisTemplate);
    }

    public static RedisLock get(String key, RedisTemplate<String, Object> redisTemplate, Long timeout) {
        return new RedisLock(key, redisTemplate, timeout);
    }
}
