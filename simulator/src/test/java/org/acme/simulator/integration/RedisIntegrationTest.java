package org.acme.simulator.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class RedisIntegrationTest {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void testRedisSetAndGet() {
        redisTemplate.opsForValue().set("testKey", "testValue");
        String value = redisTemplate.opsForValue().get("testKey");
        assertThat(value).isEqualTo("testValue");
    }
}

