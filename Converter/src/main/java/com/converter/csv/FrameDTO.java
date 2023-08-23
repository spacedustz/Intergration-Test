package com.converter.csv;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

public class FrameDTO {

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Response {
        private Long frameId;
        private int count;
        private Float frameTime;
        private String instanceId;
        private String systemDate;
        private Long systemTimestamp;

        public static FrameDTO.Response createOf(
                Long id,
                int count,
                Float frameTime,
                String instanceId,
                String systemDate,
                Long systemTimestamp) {
            return new FrameDTO.Response(id, count, frameTime, instanceId, systemDate, systemTimestamp);
        }

        public static FrameDTO.Response fromEntity(Frame entity) {
            return Response.createOf(
                    entity.getId(),
                    entity.getCount(),
                    entity.getFrameTime(),
                    entity.getInstanceId(),
                    String.valueOf(LocalDateTime.parse(entity.getSystemDate().toString())),
                    entity.getSystemTimestamp()
            );
        }
    }
}
