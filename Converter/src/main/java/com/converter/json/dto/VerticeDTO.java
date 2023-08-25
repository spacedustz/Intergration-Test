package com.converter.json.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class VerticeDTO {
    private Long verticeId;
    private Double x;
    private Double y;
}
