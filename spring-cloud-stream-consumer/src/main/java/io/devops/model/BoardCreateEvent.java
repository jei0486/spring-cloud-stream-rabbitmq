package io.devops.model;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@ToString
public class BoardCreateEvent {

    private String eventName = "boardCreate";
    private String title;
    private String contents;
    private String writeId;
    private String writeName;
    private String writeDate;
}
