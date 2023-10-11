package io.devops.model;

import java.io.Serializable;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@ToString
public class MyMessage implements Serializable {

    private Long id;
    private String message;
}
