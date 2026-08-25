package net.engineeringdigest.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public <T> T get(String key, Class<T> entityClass) {

        try {
            Object value = redisTemplate.opsForValue().get(key);

            if (value == null) {
                return null;
            }

            return entityClass.cast(value);

        } catch (Exception e) {
            log.error(
                    "Error while getting value from Redis for key: {}",
                    key,
                    e
            );
            return null;
        }
    }

    public void set(String key, Object value, Long ttl) {

        try {
            redisTemplate.opsForValue().set(
                    key,
                    value,
                    ttl,
                    TimeUnit.SECONDS
            );

        } catch (Exception e) {
            log.error(
                    "Error while setting value in Redis for key: {}",
                    key,
                    e
            );
        }
    }
}