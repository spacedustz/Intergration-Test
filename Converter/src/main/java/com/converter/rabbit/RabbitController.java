package com.converter.rabbit;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rabbit")
@RequiredArgsConstructor
public class RabbitController {
    private final RabbitService rabbitService;

    @PostMapping("send")
    public ResponseEntity<String> send(RabbitDTO message) {
        rabbitService.send(message);

        return ResponseEntity.ok().body("RabbitMQ - 메시지 전송 성공");
    }
}
