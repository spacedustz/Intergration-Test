package com.converter.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisPublisher {
    private final RedisTemplate<String, Object> template;

    public void publish(ChannelTopic topic, String data) {
        log.info("Topic : " + topic.getTopic());
        log.info("Data : " + data);
        template.convertAndSend(topic.getTopic(), data);
    }
}