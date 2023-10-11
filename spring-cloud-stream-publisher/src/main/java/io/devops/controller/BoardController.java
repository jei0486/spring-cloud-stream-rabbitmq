package io.devops.controller;

import io.devops.model.BoardCreateEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/boards")
@RestController
public class BoardController {

    private final StreamBridge streamBridge;

    @PostMapping
    public ResponseEntity<?> boardCreate(@RequestBody BoardCreateEvent boardCreateEvent){
        boolean result = streamBridge.send("boardCreate", boardCreateEvent);
        log.info("boardCreate send to rabbitmq BoardCreateEvent: {} , result :{}", boardCreateEvent, result);
        return ResponseEntity.ok().build();
    }

}