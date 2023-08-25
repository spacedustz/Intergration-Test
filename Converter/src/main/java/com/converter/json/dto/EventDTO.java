package com.converter.json.dto;

import com.converter.json.entity.Event;
import com.converter.json.entity.Extra;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class EventDTO {
    private Extra extra;
    private Double frameId;

    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Response {
        private Long eventId;
        private Extra extra;
        private Double frameId;

        public static Response of(Long eventId, Extra extra, Double frameId) {
            return new Response(eventId, extra, frameId);
        }

        public static Response fromEntity(Event entity) {
            return new Response(
                    entity.getId(),
                    entity.getExtra(),
                    entity.getFrameId()
            );
        }
    }
}
