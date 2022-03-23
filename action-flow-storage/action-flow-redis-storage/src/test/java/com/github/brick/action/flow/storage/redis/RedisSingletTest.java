package com.github.brick.action.flow.storage.redis;


import org.junit.Test;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisSingletTest {

    @Test
    public void tt() {
        RedisTemplate redisTemplate = RedisSinglet.redisTemplate();
        redisTemplate.opsForValue().set("a", "a");
    }
}