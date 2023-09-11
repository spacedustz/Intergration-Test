package com.converter.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisMessageReceiver {

    private final RedisTemplate<String, Object> template;

    public void receive(String message) {
        template.convertAndSend("channel", message);
    }
}
