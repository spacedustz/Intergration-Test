package com.converter.json.controller;

import com.converter.json.dto.EventDTO;
import com.converter.json.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @GetMapping("/json")
    public ResponseEntity<List<EventDTO.Response>> getJson() throws Exception {
        return new ResponseEntity<>(eventService.parseJson(), HttpStatus.OK);
    }

    @GetMapping(value = "/video", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<String> getVideo() throws Exception {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.TEXT_PLAIN);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(eventService.convertRtsp());
    }

    @GetMapping(value = "/video2", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<String> getVideo2() {

        return ResponseEntity.ok().body(eventService.convertRtsp2());
    }
}
