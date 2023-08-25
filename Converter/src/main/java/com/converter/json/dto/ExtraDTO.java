package com.converter.json.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ExtraDTO {
    private Long extraId;
    private BboxDTO bboxDTO;
    private VerticeDTO vertices;
}
