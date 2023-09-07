package com.converter.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // RabbitMQ와 연동하려면 "rabbitmq"를 Message Broker로 설정
        config
                .enableStompBrokerRelay("message")
                .setRelayHost("localhost")
                .setRelayPort(15674)
                .setClientLogin("guest")
                .setClientPasscode("guest");

        // 클라이언트로부터 메시지를 받을 엔드포인트 설정
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // WebSocket 연결을 위한 엔드포인트 설정
        registry.addEndpoint("/ws")
                .setAllowedOrigins("*")
                .withSockJS();
    }
}
