package io.devops.service;

import io.devops.model.MyMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;
import java.util.function.Function;

@Slf4j
@Service
public class MessageService {

    //Following sink is used as test consumer. It logs the data received through the consumer.
    @Bean
    public Consumer<String> receive1() {
        return data -> log.info("Data received from customer-1..." + data);
    }

    @Bean
    public Consumer<String> receive2() {
        return data -> log.info("Data received from customer-2..." + data);
    }

    @Bean
    public Function<Flux<MyMessage>, Mono<Void>> direct() {
        return myMessageFlux -> myMessageFlux.doOnNext(
                    myMessage -> {
                        log.info("-----------------------------------------------------");
                        log.info("##### message for direct : {}", myMessage.getMessage());
                        log.info("##### id for direct : {}", myMessage.getId());
                        log.info("-----------------------------------------------------");
                    }
                )
                .then();
    }

    @Bean
    public Function<Flux<MyMessage>, Mono<Void>> broadcast() {
        return myMessageFlux -> myMessageFlux.doOnNext(myMessage -> log.info("##### message for broadcast : {}", myMessage.getMessage()))
                .then();
    }

}
