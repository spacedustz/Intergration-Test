package com.converter.rabbit;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class RabbitService {
    private static final Logger log = LoggerFactory.getLogger(RabbitService.class);
    private final RabbitTemplate template;
    private final SimpMessagingTemplate messagingTemplate;
    private final String topic = "message";

    // 메시지 전송 테스트
    public void send(RabbitDTO message) {
        try {
            template.convertAndSend("x.frame", "qq.frame", message);
        } catch (Exception e) {
            log.error("RabbitMQ 메시지 전송 테스트 실패", e);
        }
    }

    // Subscribe
    @RabbitListener(queues = "q.frame")
    public void consume(String message) {
        messagingTemplate.convertAndSend("message", message);
        log.info("Received Message {}", message);
        System.out.println("Received Message {}" + message);
    }
}
