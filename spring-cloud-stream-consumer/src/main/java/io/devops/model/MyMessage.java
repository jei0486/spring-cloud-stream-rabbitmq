package io.devops.model;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@ToString
public class MyMessage implements Serializable {

    private Long id;
    private String message;
}
