package com.converter.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/redis")
@RequiredArgsConstructor
public class RedisController {
    private final RedisTemplate<String, Object> template;

    @GetMapping("/mqtt")
    public String getData() {
        return Objects.requireNonNull(template.opsForValue().get("데이터")).toString();
    }
}
