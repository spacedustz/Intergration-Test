package com.converter.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class RedisController {
    private final RedisTemplate<String, Object> template;

    @MessageMapping("/topic/message")
    @SendTo("/topic/message")
    public String getData() {
        return Objects.requireNonNull(template.opsForValue().get("데이터")).toString();
    }
}
