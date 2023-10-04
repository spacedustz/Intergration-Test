package com.converter.config;

import com.converter.redis.RedisSubscriber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.port}")
    private int port;

    // Redis 연결 설정
    @Bean(name = "redisFactory")
    public RedisConnectionFactory factory() {
        return new LettuceConnectionFactory(host, port);
    }

    @Bean(name = "redisListener")
    public MessageListenerAdapter listener(RedisSubscriber subscriber) {
        return new MessageListenerAdapter(subscriber, "onMessage");
    }

    // Redis Channel(Topic)로 부터 메시지를 받고, 주입된 리스너들에게 비동기로 Dispatch 하는 역할
    // Pub & Sub을 처리하는 Listener
    @Bean
    public RedisMessageListenerContainer listenerContainer() {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(factory());
        return container;
    }

    // 어플리케이션에서 사용할 Redis Template
    @Bean(name = "redisTemplate")
    public RedisTemplate<String, Object> template() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory());
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(String.class));
        return template;
    }

    @Bean
    ChannelTopic topic() {
        return new ChannelTopic("message");
    }
}
