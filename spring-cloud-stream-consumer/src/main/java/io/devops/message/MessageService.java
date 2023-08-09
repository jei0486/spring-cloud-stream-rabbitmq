package io.devops.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Slf4j
@Service
public class MessageService {

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
