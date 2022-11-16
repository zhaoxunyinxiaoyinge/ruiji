package com.aidouc;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
public  class SpringDataRedisTest {
    @Autowired
    private RedisTemplate redisTemplate;
    @Test
    public void Tostring() {
        redisTemplate.opsForValue().set("age", "100");
        System.out.println(redisTemplate.opsForValue().get("age"));
        redisTemplate.opsForSet().add("list",6,4);
        System.out.println(redisTemplate.opsForSet().members("list"));
        redisTemplate.opsForZSet().add("laji","a",24);
        redisTemplate.opsForZSet().add("laji","b",22);
        redisTemplate.opsForZSet().add("laji","c",23);
        redisTemplate.opsForZSet().add("laji","d",24);
        System.out.println(redisTemplate.opsForZSet().range("laji",0,-1));

    }
}
