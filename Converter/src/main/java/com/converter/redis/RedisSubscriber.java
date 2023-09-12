package com.converter.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisSubscriber implements MessageListener {

    private final RedisTemplate<String, Object> template;

    // Redis로부터 온 메시지를 역직렬화 하여 메시지 전달
    @Override
    public void onMessage(Message message) {
        String publishMessage = template.getStringSerializer().deserialize(message.getBody());
        assert publishMessage != null;
        template.convertAndSend("/topic/message", publishMessage);
    }
}
