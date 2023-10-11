package io.devops.service;

import io.devops.model.BoardCreateEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.support.json.Jackson2JsonObjectMapper;

import java.util.function.Consumer;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class BoardService {

    private Jackson2JsonObjectMapper mapper = new Jackson2JsonObjectMapper();

    @Bean
    public Consumer<String> boardCreate(){
        return ((msg) -> {
            log.info("boardCreate 이벤트 수신: {}",msg);
            try {
                BoardCreateEvent event = mapper.fromJson(msg, BoardCreateEvent.class);
                log.info("================================BoardCreateEvent: {}", event.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
