package com.converter.rabbit;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RabbitDTO {
    private String title;
    private String message;
}
