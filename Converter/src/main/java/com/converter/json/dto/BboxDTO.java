package com.converter.json.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BboxDTO {
    private Long bboxId;
    private Double height;
    private Double width;
    private Double x;
    private Double y;
}
