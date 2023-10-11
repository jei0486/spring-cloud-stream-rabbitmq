package io.devops.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.devops.model.MyMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MessageController {


    @Autowired
    private ObjectMapper jsonMapper;

    EmitterProcessor<MyMessage> directProcessor = EmitterProcessor.create();

    EmitterProcessor<MyMessage> broadcastProcessor = EmitterProcessor.create();

    private final EmitterProcessor<Message<?>> supplierProcessor = EmitterProcessor.create();

    @GetMapping(value = "/direct/{message}")
    public Mono<Void> directMessage(@PathVariable String message) {

        return Mono.just(message)
                   .doOnNext(s -> directProcessor.onNext(MyMessage.builder()
                           .message(message)
                           .id(new Random().nextLong(100))
                           .build()))
                   .then();
    }

    @GetMapping(value = "/broadcast/{message}")
    public Mono<Void> broadcastMessage(@PathVariable String message) {

        return Mono.just(message)
                   .doOnNext(s -> broadcastProcessor.onNext(MyMessage.builder().message(message).build()))
                   .then();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void handleRequest(@RequestBody String body, @RequestHeader(HttpHeaders.CONTENT_TYPE) Object contentType) throws Exception {
        Map<String, String> payload = jsonMapper.readValue(body, Map.class);
        String destinationName = payload.get("id");
        Message<?> message = MessageBuilder.withPayload(payload)
                .setHeader("spring.cloud.stream.sendto.destination", destinationName).build();
        supplierProcessor.onNext(message);
    }

    @Bean
    public Supplier<Flux<Message<?>>> supplier() {
        return () -> supplierProcessor;
    }

    @Bean
    public Supplier<Flux<MyMessage>> direct() {
        return () -> this.directProcessor;
    }

    @Bean
    public Supplier<Flux<MyMessage>> broadcast() {
        return () -> this.broadcastProcessor;
    }
}
